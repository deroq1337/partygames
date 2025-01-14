package com.github.deroq1337.partygames.core.data.game.map;

import com.github.deroq1337.partygames.api.game.PartyGameMap;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PartyGameMapManager {

    @NotNull <M extends PartyGameMap> CompletableFuture<Optional<M>> getRandomMap();
}
