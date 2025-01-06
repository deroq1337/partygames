package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.PartyGame;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RequiredArgsConstructor
public class PartyGameLoader {

    private final @NotNull File gameDirectory;
    private final @NotNull Map<String, PartyGame> loadedGames = new HashMap<>();

    public void loadGames() {
        findJars().forEach(this::loadGame);
    }

    public void unloadGames() {
        Iterator<String> iterator = loadedGames.keySet().iterator();
        while (iterator.hasNext()) {
            unloadGame(iterator.next());
            iterator.remove();
        }
    }

    private void loadGame(@NotNull File file) {
        Optional<PartyGameManifest> optionalManifest = getGameManifest(file);
        if (optionalManifest.isEmpty()) {
            System.err.println("File '" + file.getName() + "' does not have game manifest");
            return;
        }

        PartyGameManifest manifest = optionalManifest.get();
        String gameName = manifest.getName();
        try (URLClassLoader classLoader = getClassLoader(file)) {
            Class<?> mainClass = classLoader.loadClass(manifest.getMain());
            if (!PartyGame.class.isAssignableFrom(mainClass)) {
                System.err.println("Main class of game '" + gameName + "' does not implement PartyGame");
                return;
            }

            PartyGame game = (PartyGame) mainClass.getDeclaredConstructor().newInstance();
            game.onLoad();
            loadedGames.put(gameName, game);
            System.out.println("Loaded game '" + gameName + "' by " + manifest.getAuthor().orElse(null));
        } catch (Exception e) {
            System.err.println("Could not load game from file '" + file.getName() + "':");
            e.printStackTrace();
        }
    }

    private void unloadGame(@NotNull String name) {
        PartyGame game = loadedGames.get(name);
        game.onUnload();
        System.out.println("Unloaded game '" + name + "'");
    }

    private @NotNull List<File> findJars() {
        return Optional.ofNullable(gameDirectory.listFiles())
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
}
