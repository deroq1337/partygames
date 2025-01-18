package com.github.deroq1337.partygames.core.data.game.provider;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PartyGameClassLoader {

    private final @NotNull Map<PartyGameManifest, URLClassLoader> classLoaders = new HashMap<>();
    private final @NotNull Map<PartyGameManifest, Map<String, Class<?>>> loadedClasses = new HashMap<>();

    public void loadClasses(@NotNull PartyGameManifest manifest, @NotNull File jarFile) throws Exception {
        URLClassLoader classLoader = classLoaders.get(manifest);
        if (classLoader == null) {
            classLoader = getClassLoader(jarFile);
            classLoaders.put(manifest, classLoader);
        }

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (!name.endsWith(".class")) {
                    continue;
                }

                String className = name.replace("/", ".").replace(".class", "");
                Class<?> loadedClass = classLoader.loadClass(className);
                loadedClasses.computeIfAbsent(manifest, o -> new HashMap<>()).put(className, loadedClass);
            }
        }
    }

    public void unloadClasses(@NotNull PartyGameManifest manifest) throws Exception {
        if (classLoaders.containsKey(manifest)) {
            classLoaders.get(manifest).close();
            classLoaders.remove(manifest);
        }

        loadedClasses.clear();
    }

    public Optional<Class<?>> getLoadedClass(@NotNull PartyGameManifest manifest, @NotNull String name) {
        return Optional.ofNullable(loadedClasses.get(manifest))
                .flatMap(classes -> Optional.ofNullable(classes.get(name)));
    }

    private @NotNull URLClassLoader getClassLoader(@NotNull File jarFile) throws IOException {
        return new URLClassLoader(
                new URL[]{jarFile.toURI().toURL()},
                getClass().getClassLoader()
        );
    }
}