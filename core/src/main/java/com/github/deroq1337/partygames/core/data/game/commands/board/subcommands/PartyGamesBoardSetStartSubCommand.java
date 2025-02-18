package com.github.deroq1337.partygames.core.data.game.commands.board.subcommands;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PartyGamesBoardSetStartSubCommand extends PartyGamesBoardSubCommand {

    public PartyGamesBoardSetStartSubCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        super(game, "setStart");
    }

    @Override
    protected void execute(@NotNull DefaultPartyGamesUser user, @NotNull Player player, @NotNull String[] args) {
        if (args.length < 1) {
            user.sendMessage("command_board_set_start_syntax");
            return;
        }

        String boardName = args[0];
        Location playerLocation = player.getLocation();

        boardManager.getBoardByName(boardName).thenCompose(optionalBoard -> {
            return optionalBoard.map(board -> {
                board.setStartLocation(new MapDirectedLocation(playerLocation));

                return boardManager.saveBoard(board).thenAccept(success -> {
                    if (!success) {
                        user.sendMessage("command_board_not_updated");
                        return;
                    }

                    user.sendMessage("command_board_start_set");
                });
            }).orElseGet(() -> {
                user.sendMessage("command_board_not_found");
                return CompletableFuture.completedFuture(null);
            });
        });
    }
}