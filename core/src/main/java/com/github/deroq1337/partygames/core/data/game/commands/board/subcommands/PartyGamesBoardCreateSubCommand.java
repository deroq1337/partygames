package com.github.deroq1337.partygames.core.data.game.commands.board.subcommands;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PartyGamesBoardCreateSubCommand extends PartyGamesBoardSubCommand {

    public PartyGamesBoardCreateSubCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        super(game, "create");
    }

    @Override
    protected void execute(@NotNull DefaultPartyGamesUser user, @NotNull Player player, @NotNull String[] args) {
        if (args.length < 1) {
            user.sendMessage("command_board_create_syntax");
            return;
        }

        String boardName = args[0];

        boardManager.getBoardByName(boardName).thenCompose(optionalBoard -> {
            return optionalBoard.map(board -> {
                user.sendMessage("command_board_already_exists");
                return CompletableFuture.completedFuture((Void) null);
            }).orElseGet(() -> {
                return boardManager.saveBoard(new PartyGamesBoard(boardName)).thenAccept(success -> {
                    Bukkit.getScheduler().runTask(game.getPartyGames(), () -> {
                        if (!success) {
                            user.sendMessage("command_board_not_created");
                            return;
                        }

                        user.sendMessage("command_board_created");
                    });
                });
            });
        });
    }
}