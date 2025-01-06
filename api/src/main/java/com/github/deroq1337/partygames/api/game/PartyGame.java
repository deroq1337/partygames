package com.github.deroq1337.partygames.api.game;

import com.github.deroq1337.partygames.api.state.PartyGameState;
import org.jetbrains.annotations.NotNull;

public interface PartyGame {

    void onLoad();

    void onUnload();

    @NotNull PartyGameState getState();
}
