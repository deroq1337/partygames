package com.github.deroq1337.partygames.core.data.game.states;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PartyGamesInGameState implements PartyGamesState {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Countdown countdown;
    private final @NotNull GameScoreboard scoreboard;

    public PartyGamesInGameState(@NotNull PartyGamesGame<PartyGamesUser> game) {
        this.game = game;
        this.countdown = null;
        this.scoreboard = null;
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
    public Optional<PartyGamesState> getNextState() {
        return Optional.empty();
    }

    @Override
    public @NotNull GameScoreboard getScoreboard() {
        return null;
    }
}
