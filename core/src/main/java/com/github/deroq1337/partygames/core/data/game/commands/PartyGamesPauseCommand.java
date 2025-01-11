package com.github.deroq1337.partygames.core.data.game.commands;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGamesPauseCommand implements CommandExecutor {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;

    public PartyGamesPauseCommand(@NotNull PartyGamesGame<PartyGamesUser> game) {
        this.game = game;
        Optional.ofNullable(game.getPartyGames().getCommand("pause")).ifPresent(pluginCommand -> pluginCommand.setExecutor(this));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        Optional<PartyGamesUser> optionalUser = game.getUserRegistry().getUser(player.getUniqueId());
        if (optionalUser.isEmpty()) {
            player.sendMessage("§cAn error occurred. Rejoin or contact an administrator.");
            return true;
        }

        PartyGamesUser user = optionalUser.get();
        if (!player.hasPermission("partygames.pause")) {
            user.sendMessage("no_permission");
            return true;
        }

        PartyGamesState gameState = game.getCurrentState();
        if (!(gameState instanceof PartyGamesLobbyState lobbyState)) {
            user.sendMessage("game_already_started");
            return true;
        }

        Countdown countdown = lobbyState.getCountdown();
        if (countdown.isRunning()) {
            countdown.pause();
            user.sendMessage("countdown_paused");
        } else {
            countdown.unpause();
            user.sendMessage("countdown_resumed");
        }
        return true;
    }
}