package com.github.deroq1337.partygames.core.data.game.board;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PartyGamesBoardManager {

    @NotNull CompletableFuture<Boolean> saveBoard(@NotNull PartyGamesBoard board);

    @NotNull CompletableFuture<Boolean> deleteBoard(@NotNull String name);

    @NotNull CompletableFuture<Optional<PartyGamesBoard>> getBoardByName(@NotNull String name);

    @NotNull CompletableFuture<Optional<PartyGamesBoard>> getRandomBoard();
}
