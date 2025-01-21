package com.github.deroq1337.partygames.api.game;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface PartyGamePlacement {

    @NotNull UUID getUuid();

    int getPlacement();
}
