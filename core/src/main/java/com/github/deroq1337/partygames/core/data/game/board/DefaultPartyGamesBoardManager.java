package com.github.deroq1337.partygames.core.data.game.board;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultPartyGamesBoardManager implements PartyGamesBoardManager {

    private final @NotNull File boardsDirectory;

    public DefaultPartyGamesBoardManager(@NotNull File boardsDirectory) {
        this.boardsDirectory = boardsDirectory;
        boardsDirectory.mkdirs();
    }


    @Override
    public @NotNull CompletableFuture<Boolean> saveBoard(@NotNull PartyGamesBoard board) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                board.save();
                return true;
            } catch (IOException e) {
                System.err.println("Could not save board: " + e.getMessage());
                return false;
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> deleteBoard(@NotNull String name) {
        return CompletableFuture.supplyAsync(() -> new PartyGamesBoard(name).delete());
    }

    @Override
    public @NotNull CompletableFuture<Optional<PartyGamesBoard>> getBoardByName(@NotNull String name) {
        return CompletableFuture.supplyAsync(() -> {
            PartyGamesBoard board = new PartyGamesBoard(name);
            if (!board.exists()) {
                return Optional.empty();
            }

            return Optional.of(board.load(PartyGamesBoard.class));
        });
    }

    @Override
    public @NotNull CompletableFuture<Optional<PartyGamesBoard>> getRandomBoard() {
        return CompletableFuture.supplyAsync(() -> {
            return Optional.ofNullable(boardsDirectory.listFiles()).map(files -> {
                if (files.length == 0) {
                    System.err.println("No game boards found");
                    return null;
                }

                List<File> fileList = new ArrayList<>(Arrays.stream(files).toList());
                File randomFile = fileList.get(ThreadLocalRandom.current().nextInt(fileList.size()));
                return new PartyGamesBoard(randomFile).load(PartyGamesBoard.class);
            });
        });
    }

}
