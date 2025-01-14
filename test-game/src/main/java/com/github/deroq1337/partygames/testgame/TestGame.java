package com.github.deroq1337.partygames.testgame;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.testgame.map.TestMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class TestGame extends PartyGame<TestMap> {

    public TestGame(@NotNull File directory, @NotNull UserRegistry<? extends User> userRegistry, @NotNull TestMap map) {
        super(directory, userRegistry, map);
    }

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
