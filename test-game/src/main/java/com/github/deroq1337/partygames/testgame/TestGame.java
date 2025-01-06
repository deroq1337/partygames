package com.github.deroq1337.partygames.testgame;

import com.github.deroq1337.partygames.api.game.PartyGame;

public class TestGame implements PartyGame {

    @Override
    public void onLoad() {
        System.out.println("lil test");
    }

    @Override
    public void onUnload() {

    }
}
