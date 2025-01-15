package com.github.deroq1337.partygames.core.data.game.provider;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.game.PartyGameMap;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PartyGameProvider {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull File gamesDirectory;
    private final @NotNull Map<PartyGameManifest, File> foundGames = new ConcurrentHashMap<>();

    public PartyGameProvider(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull File gamesDirectory) {
        this.game = game;
        this.gamesDirectory = gamesDirectory;

        findGames();
    }

    public void findGames() {
        findJars().forEach(this::findGame);
    }

    private void findGame(@NotNull File file) {
        Optional<PartyGameManifest> optionalManifest = getGameManifest(file);
        if (optionalManifest.isEmpty()) {
            System.err.println("File '" + file.getName() + "' does not have game manifest");
            return;
        }

        PartyGameManifest manifest = optionalManifest.get();
        foundGames.put(manifest, file);
        System.out.println("Found game '" + manifest.getName() + "' by " + manifest.getAuthor().orElse(null));
    }

    public Optional<PartyGame<?>> loadGame(@NotNull PartyGameManifest manifest) {
        String gameName = manifest.getName();
        File file = Optional.ofNullable(foundGames.get(manifest))
                .orElseThrow(() -> new NoSuchElementException("Game '" + gameName + "' was not found"));

        try (URLClassLoader classLoader = getClassLoader(file)) {
            Class<?> mainClass = classLoader.loadClass(manifest.getMain());
            if (!PartyGame.class.isAssignableFrom(mainClass)) {
                System.err.println("Main class of game '" + gameName + "' does not implement PartyGame");
                return Optional.empty();
            }

            return instantiateGame(manifest, mainClass, gameName).join();
        } catch (Exception e) {
            System.err.println("Could not load game from file '" + file.getName() + "': " + e.getMessage());
            return Optional.empty();
        }
    }

    private @NotNull CompletableFuture<Optional<PartyGame<?>>> instantiateGame(@NotNull PartyGameManifest manifest, @NotNull Class<?> mainClass, @NotNull String gameName) {
        Class<? extends PartyGameMap> mapClass = getMapClass(mainClass);
        File gameDirectory = manifest.getDirectory(gamesDirectory);

        return game.getGameMapManager(mapClass, gameDirectory).getRandomMap().thenApply(gameMap -> {
            if (gameMap.isEmpty()) {
                System.err.println("Game '" + gameName + "' has no game maps");
                return Optional.empty();
            }

            try {
                PartyGame<? extends PartyGameMap> gameInstance = (PartyGame<? extends PartyGameMap>) mainClass
                        .getDeclaredConstructor(File.class, UserRegistry.class, mapClass)
                        .newInstance(gameDirectory, game.getUserRegistry(), gameMap.get());

                Bukkit.getScheduler().runTask(game.getPartyGames(), () -> {
                    gameInstance.onLoad();
                    System.out.println("Loaded game '" + gameName + "' by " + manifest.getAuthor().orElse(null));
                });
                return Optional.of(gameInstance);
            } catch (Exception e) {
                System.err.println("Error while loading the game: " + e.getMessage());
                return Optional.empty();
            }
        });
    }

    private @NotNull List<File> findJars() {
        return Optional.ofNullable(gamesDirectory.listFiles())
                .map(files -> Arrays.stream(files)
                        .filter(file -> file.getName().endsWith(".jar"))
                        .toList())
                .orElse(new ArrayList<>());
    }

    private Optional<PartyGameManifest> getGameManifest(@NotNull File file) {
        try (JarFile jar = new JarFile(file)) {
            return Optional.ofNullable(jar.getJarEntry("game.yml"))
                    .flatMap(entry -> parseGameManifest(jar, entry));
        } catch (IOException e) {
            System.err.println("Could not check whether file '" + file.getName() + "' is a game or not: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<PartyGameManifest> parseGameManifest(@NotNull JarFile jar, @NotNull JarEntry jarEntry) {
        try (InputStream inputStream = jar.getInputStream(jarEntry)) {
            Map<String, String> yaml = new Yaml().load(inputStream);
            return Optional.of(new PartyGameManifest(yaml));
        } catch (IOException e) {
            System.err.println("Could not parse game manifest from jar: " + e.getMessage());
            return Optional.empty();
        }
    }

    public @NotNull Set<PartyGameManifest> getPartyGameManifests() {
        return foundGames.keySet();
    }

    private @NotNull URLClassLoader getClassLoader(@NotNull File file) throws IOException {
        return new URLClassLoader(
                new URL[]{file.toURI().toURL()},
                getClass().getClassLoader()
        );
    }

    private @NotNull Class<?> loadClass(@NotNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private @NotNull Class<? extends PartyGameMap> getMapClass(@NotNull Class<?> mainClass) {
        ParameterizedType genericSuperclass = (ParameterizedType) mainClass.getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<? extends PartyGameMap>) actualTypeArguments[0];
    }
}
