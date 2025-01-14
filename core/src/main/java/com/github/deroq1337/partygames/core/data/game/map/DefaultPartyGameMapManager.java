package com.github.deroq1337.partygames.core.data.game.map;

import com.github.deroq1337.partygames.api.game.PartyGameMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultPartyGameMapManager implements PartyGameMapManager {

    private final @NotNull Class<? extends PartyGameMap> mapClass;
    private final @NotNull File mapsDirectory;

    public DefaultPartyGameMapManager(@NotNull Class<? extends PartyGameMap> mapClass, @NotNull File gameDirectory) {
        this.mapClass = mapClass;
        this.mapsDirectory = new File(gameDirectory, "maps/");
        if (!mapsDirectory.exists()) {
            mapsDirectory.mkdirs();
        }
    }

    @Override
    public @NotNull <M extends PartyGameMap> CompletableFuture<Optional<M>> getRandomMap() {
        return CompletableFuture.supplyAsync(() -> {
            return Optional.ofNullable(mapsDirectory.listFiles()).map(files -> {
                if (files.length == 0) {
                    System.err.println("No maps found");
                    return null;
                }

                List<File> fileList = new ArrayList<>(Arrays.stream(files).toList());
                File randomFile = fileList.get(ThreadLocalRandom.current().nextInt(fileList.size()));

                try {
                    return (M) mapClass.getDeclaredConstructor(File.class).newInstance(randomFile);
                } catch (Exception e) {
                    System.err.println("Could not instantiate map " + mapClass + ": " + e.getMessage());
                    return null;
                }
            });
        });
    }
}
