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
    public void onPlayerJoin(@NotNull UUID player) {

    }

    @Override
    public void onPlayerQuit(@NotNull UUID player) {

    }

    @Override
    public boolean canStart() {
        return game.getCurrentState() instanceof PartyGamesLobbyState
                && game.getUserRegistry().getUsers().size() == 1;
    }

    @Override
    public Optional<PartyGamesState> getNextState() {
        return Optional.empty();
    }
}
