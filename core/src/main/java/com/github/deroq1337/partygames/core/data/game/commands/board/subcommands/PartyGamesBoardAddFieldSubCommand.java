package com.github.deroq1337.partygames.core.data.game.commands.board.subcommands;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PartyGamesBoardAddFieldSubCommand extends PartyGamesBoardSubCommand {

    public PartyGamesBoardAddFieldSubCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        super(game, "addField");
    }

    @Override
    protected void execute(@NotNull DefaultPartyGamesUser user, @NotNull Player player, @NotNull String[] args) {
        if (args.length < 2) {
            user.sendMessage("command_board_add_field_syntax");
            return;
        }

        String boardName = args[0];
        Location playerLocation = player.getLocation();

        boardManager.getBoardByName(boardName).thenCompose(optionalBoard -> {
            return optionalBoard.map(board -> {
                int id = board.addField(playerLocation, Boolean.parseBoolean(args[1]));

                return boardManager.saveBoard(board).thenAccept(success -> {
                    Bukkit.getScheduler().runTask(game.getPartyGames(), () -> {
                        if (success) {
                            user.sendMessage("command_board_not_updated");
                            return;
                        }

                        user.sendMessage("command_board_field_added", id);
                    });
                });
            }).orElseGet(() -> {
                user.sendMessage("command_board_not_found");
                return CompletableFuture.completedFuture(null);
            });
        });
    }
}