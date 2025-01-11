package com.github.deroq1337.partygames.api.state;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface PartyGamesState extends GameState {

    void onPlayerJoin(@NotNull UUID uuid);

    void onPlayerQuit(@NotNull UUID uuid);

    Optional<PartyGamesState> getNextState();
}
