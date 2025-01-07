package com.github.deroq1337.partygames.testgame;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import org.jetbrains.annotations.NotNull;

public class TestGame implements PartyGame {

    @Override
    public void onLoad() {
        System.out.println("lil test");
    }

    @Override
    public void onUnload() {

    }

    @Override
    public @NotNull PartyGameState getState() {
        return null;
    }
}
