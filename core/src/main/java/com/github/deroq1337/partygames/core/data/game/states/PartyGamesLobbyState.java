package com.github.deroq1337.partygames.core.data.game.states;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.CountdownableState;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.countdowns.PartyGamesLobbyCountdown;
import com.github.deroq1337.partygames.core.data.game.scoreboard.PartyGamesLobbyScoreboard;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.Getter;
import org.bukkit.*;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Getter
public class PartyGamesLobbyState implements PartyGamesState, CountdownableState {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Countdown countdown;
    private final @NotNull GameScoreboard scoreboard;

    public PartyGamesLobbyState(@NotNull PartyGamesGame<PartyGamesUser> game) {
        this.game = game;
        this.countdown = new PartyGamesLobbyCountdown(game, this);
        this.scoreboard = new PartyGamesLobbyScoreboard(game);
    }

    @Override
    public void enter() {

    }

    @Override
    public void leave() {

    }

    @Override
    public void onPlayerJoin(@NotNull UUID uuid) {
        PartyGamesUser user = game.getUserRegistry().addUser(uuid, true);
        user.getBukkitPlayer().ifPresent(player -> {
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);
            player.setFlying(false);
            player.setOp(false);
            player.setGameMode(GameMode.SURVIVAL);

            scoreboard.setScoreboard(user);
        });
    }

    @Override
    public void onPlayerQuit(@NotNull UUID player) {

    }

    @Override
    public boolean canStart() {
        return game.getCurrentState() instanceof PartyGamesLobbyState
                && game.getUserRegistry().getUsers().size() == 1
                && game.getBoard().isPresent();
    }

    @Override
    public Optional<PartyGamesState> getNextState() {
        return Optional.of(new PartyGamesInGameState(game));
    }
}
