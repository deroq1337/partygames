package com.github.deroq1337.partygames.core.data.game.commands.board.subcommands;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
        Optional<PartyGamesBoard> optionalBoard = boardManager.getBoardByName(boardName).join();
        if (optionalBoard.isEmpty()) {
            user.sendMessage("command_board_not_found");
            return;
        }

        PartyGamesBoard board = optionalBoard.get();
        board.setStartLocation(new MapDirectedLocation(player.getLocation()));
        if (!boardManager.saveBoard(board).join()) {
            user.sendMessage("command_board_not_updated");
            return;
        }

        user.sendMessage("command_board_start_set");
    }
}