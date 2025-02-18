package com.github.deroq1337.partygames.core.data.game.commands;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGamesStartCommand implements CommandExecutor {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;

    public PartyGamesStartCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        Optional.ofNullable(game.getPartyGames().getCommand("start")).ifPresent(pluginCommand -> pluginCommand.setExecutor(this));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        game.getUserRegistry().getUser(player.getUniqueId()).ifPresentOrElse(user -> {
            if (!player.hasPermission("partygames.start")) {
                user.sendMessage("no_permission");
                return;
            }

            PartyGamesState gameState = game.getCurrentState();
            if (!(gameState instanceof PartyGamesLobbyState lobbyState)) {
                user.sendMessage("game_already_started");
                return;
            }

            if (!lobbyState.canStart()) {
                user.sendMessage("not_enough_players");
                return;
            }

            Countdown countdown = lobbyState.getCountdown();
            if (countdown.getCurrentTick() <= 10) {
                user.sendMessage("game_already_starting");
                return;
            }

            countdown.setCurrentTick(10);
            if (!countdown.isRunning()) {
                countdown.setRunning(true);
            }

            user.sendMessage("game_force_started");
        }, () -> player.sendMessage("§cAn error occurred. Rejoin or contact an administrator."));

        return true;
    }
}
