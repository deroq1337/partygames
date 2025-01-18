package com.github.deroq1337.partygames.core.data.game.provider;

import com.github.deroq1337.partygames.api.config.YamlConfig;
import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.game.PartyGameMap;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PartyGameProvider {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull File gamesDirectory;
    private final @NotNull Map<PartyGameManifest, File> foundGames = new ConcurrentHashMap<>();
    private final @NotNull PartyGameClassLoader gameClassLoader = new PartyGameClassLoader();

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

    public Optional<PartyGame<?, ?>> loadGame(@NotNull PartyGameManifest manifest) {
        String gameName = manifest.getName();
        File file = Optional.ofNullable(foundGames.get(manifest))
                .orElseThrow(() -> new NoSuchElementException("Game '" + gameName + "' was not found"));

        try {
            gameClassLoader.loadClasses(manifest, file);

            Class<?> mainClass = gameClassLoader.getLoadedClass(manifest, manifest.getMain())
                    .orElseThrow(() -> new RuntimeException("Main class '" + manifest.getMain() + "' was not loaded"));
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

    public void unloadGame(@NotNull PartyGameManifest manifest) {
        try {
            gameClassLoader.unloadClasses(manifest);
            System.out.println("Unloaded game '" + manifest.getName() + "'");
        } catch (Exception e) {
            System.err.println("Could not unload classes of game '" + manifest.getName() + "': " + e.getMessage());
        }
    }

    private @NotNull CompletableFuture<Optional<PartyGame<?, ?>>> instantiateGame(@NotNull PartyGameManifest manifest, @NotNull Class<?> mainClass, @NotNull String gameName) {
        Class<? extends PartyGameMap> mapClass = getMapClass(mainClass);
        Class<? extends YamlConfig> configClass = getConfigClass(mainClass);
        File gameDirectory = manifest.getDirectory(gamesDirectory);

        return game.getGameMapManager(mapClass, gameDirectory).getRandomMap().thenApply(gameMap -> {
            if (gameMap.isEmpty()) {
                System.err.println("Game '" + gameName + "' has no game maps");
                return Optional.empty();
            }

            try {
                PartyGame<? extends PartyGameMap, ? extends YamlConfig> gameInstance = (PartyGame<? extends PartyGameMap, ? extends YamlConfig>) mainClass
                        .getDeclaredConstructor(UserRegistry.class, mapClass, File.class, configClass)
                        .newInstance(game.getUserRegistry(), gameMap.get(), gameDirectory, instantiateGameConfig(configClass, gameDirectory));

                System.out.println("Instantiated game '" + gameName + "' by " + manifest.getAuthor().orElse(null));
                return Optional.of(gameInstance);
            } catch (Exception e) {
                e.printStackTrace();
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

    private @NotNull Class<? extends PartyGameMap> getMapClass(@NotNull Class<?> mainClass) {
        ParameterizedType genericSuperclass = (ParameterizedType) mainClass.getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<? extends PartyGameMap>) actualTypeArguments[0];
    }

    private @NotNull Class<? extends YamlConfig> getConfigClass(@NotNull Class<?> mainClass) {
        ParameterizedType genericSuperclass = (ParameterizedType) mainClass.getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<? extends YamlConfig>) actualTypeArguments[1];
    }

    private @NotNull <C extends YamlConfig> C instantiateGameConfig(@NotNull Class<C> configClass, @NotNull File gameDirectory) throws Exception {
        return configClass.getDeclaredConstructor(File.class).newInstance(new File(gameDirectory, "configs/config.yml")).load(configClass);
    }
}
