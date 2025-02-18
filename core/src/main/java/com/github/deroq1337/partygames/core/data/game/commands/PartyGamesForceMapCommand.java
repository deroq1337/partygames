package com.github.deroq1337.partygames.core.data.game.commands;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGamesForceMapCommand implements CommandExecutor {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;

    public PartyGamesForceMapCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        Optional.ofNullable(game.getPartyGames().getCommand("forcemap")).ifPresent(pluginCommand -> pluginCommand.setExecutor(this));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        game.getUserRegistry().getUser(player.getUniqueId()).ifPresentOrElse(user -> {
            if (!player.hasPermission("partygames.forcemap")) {
                user.sendMessage("no_permission");
                return;
            }

            if (args.length < 1) {
                user.sendMessage("command_forcemap_syntax");
                return;
            }

            if (!(game.getCurrentState() instanceof PartyGamesLobbyState)) {
                user.sendMessage("game_already_started");
                return;
            }

            if (game.isForceMapped()) {
                user.sendMessage("already_forcemapped");
                return;
            }

            String boardName = args[0];
            if (game.getBoard().isPresent() && game.getBoard().get().getName().equals(boardName)) {
                user.sendMessage("already_forcemapped");
                return;
            }

            game.getBoardManager().getBoardByName(boardName).thenAccept(optionalBoard -> {
                Bukkit.getScheduler().runTask(game.getPartyGames(), () -> {
                    optionalBoard.ifPresentOrElse(board -> {
                        game.forceMap(board);
                        user.sendMessage("game_force_mapped");
                    }, () -> user.sendMessage("command_board_not_found"));
                });
            });
        }, () -> player.sendMessage("§cAn error occurred. Rejoin or contact an administrator."));

        return true;
    }
}
