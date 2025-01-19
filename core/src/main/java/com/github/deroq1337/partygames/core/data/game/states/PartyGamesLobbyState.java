package com.github.deroq1337.partygames.core.data.game.states;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.Countdownable;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.countdowns.PartyGamesLobbyCountdown;
import com.github.deroq1337.partygames.core.data.game.scoreboard.PartyGamesLobbyScoreboard;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import lombok.Getter;
import org.bukkit.*;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Getter
public class PartyGamesLobbyState implements PartyGamesState, Countdownable {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull Countdown countdown;
    private final @NotNull GameScoreboard<DefaultPartyGamesUser> scoreboard;

    public PartyGamesLobbyState(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        this.countdown = new PartyGamesLobbyCountdown(game, this);
        this.scoreboard = new PartyGamesLobbyScoreboard(game);
    }

    @Override
    public void enter() {

    }

    @Override
    public void leave() {
        scoreboard.cancelScoreboardUpdate();
    }

    @Override
    public void onPlayerJoin(@NotNull UUID uuid) {
        DefaultPartyGamesUser user = game.getUserRegistry().addUser(uuid, true);
        user.getBukkitPlayer().ifPresent(player -> {
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);
            player.setFlying(false);
            //player.setOp(false);
            player.setGameMode(GameMode.SURVIVAL);

            Optional.ofNullable(game.getMainConfig().getSpawnLocation()).ifPresent(spawnLocation -> {
                player.teleport(spawnLocation.toBukkitLocation());
            });

            scoreboard.setScoreboard(user);
        });
    }

    @Override
    public void onPlayerQuit(@NotNull UUID player) {

    }

    @Override
    public boolean canStart() {
        return game.getCurrentState() instanceof PartyGamesLobbyState
                && game.getUserRegistry().getUsers().size() == game.getMainConfig().getMinPlayers()
                && game.getBoard().isPresent();
    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.of(new PartyGamesInGameState(game));
    }
}
