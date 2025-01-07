package com.github.deroq1337.partygames.core.data.game.board;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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
            } catch (InvalidConfigurationException e) {
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

            return Optional.of(load(board));
        });
    }

    @Override
    public @NotNull CompletableFuture<Optional<PartyGamesBoard>> getRandomBoard() {
        return CompletableFuture.supplyAsync(() -> {
            return Optional.ofNullable(boardsDirectory.listFiles()).map(files -> {
                List<File> fileList = new ArrayList<>(Arrays.stream(files).toList());
                File randomFile = fileList.get(ThreadLocalRandom.current().nextInt(fileList.size()));
                return load(new PartyGamesBoard(randomFile));
            });
        });
    }


    private @NotNull PartyGamesBoard load(@NotNull PartyGamesBoard board) {
        try {
            board.load();
            return board;
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
