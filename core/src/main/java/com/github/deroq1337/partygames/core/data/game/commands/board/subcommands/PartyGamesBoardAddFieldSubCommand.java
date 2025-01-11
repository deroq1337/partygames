package com.github.deroq1337.partygames.core.data.game.commands.board.subcommands;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGamesBoardAddFieldSubCommand extends PartyGamesBoardSubCommand {

    public PartyGamesBoardAddFieldSubCommand(@NotNull PartyGamesGame<PartyGamesUser> game) {
        super(game, "addField");
    }

    @Override
    protected void execute(@NotNull PartyGamesUser user, @NotNull Player player, @NotNull String[] args) {
        if (args.length < 2) {
            user.sendMessage("command_board_add_field_syntax");
            return;
        }

        String boardName = args[0];
        Optional<PartyGamesBoard> optionalBoard = boardManager.getBoardByName(boardName).join();
        if (optionalBoard.isEmpty()) {
            user.sendMessage("command_board_not_found");
            return;
        }

        PartyGamesBoard board = optionalBoard.get();
        board.addField(player.getLocation(), Boolean.parseBoolean(args[1]));
        if (!boardManager.saveBoard(board).join()) {
            user.sendMessage("command_board_not_updated");
            return;
        }

        user.sendMessage("command_board_field_added");
    }
}