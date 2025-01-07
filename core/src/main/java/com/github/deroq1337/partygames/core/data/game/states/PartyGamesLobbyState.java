package com.github.deroq1337.partygames.core.data.game.states;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.state.CountdownableState;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.countdowns.PartyGamesLobbyCountdown;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PartyGamesLobbyState extends PartyGamesState implements CountdownableState {

    private final @NotNull PartyGamesGame game;

    @Getter
    private final @NotNull Countdown countdown;

    public PartyGamesLobbyState(@NotNull PartyGamesGame game) {
        this.game = game;
        this.countdown = new PartyGamesLobbyCountdown(game, this);
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
        return true;
    }

    @Override
    public Optional<PartyGamesState> getNextState() {
        return Optional.empty();
    }
}
