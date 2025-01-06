package com.github.deroq1337.partygames.api.state;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public abstract class PartyGamesState extends BaseState {

    public abstract void onPlayerJoin(@NotNull UUID player);

    public abstract void onPlayerQuit(@NotNull UUID player);

    public abstract Optional<PartyGamesState> getNextState();
}
