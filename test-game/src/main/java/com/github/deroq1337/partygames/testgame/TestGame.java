package com.github.deroq1337.partygames.testgame;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.testgame.config.TestGameConfig;
import com.github.deroq1337.partygames.testgame.map.TestMap;

import com.github.deroq1337.partygames.testgame.scoreboard.TestScoreboard;
import com.github.deroq1337.partygames.testgame.states.TestGameStartingState;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter
@Setter
public class TestGame extends PartyGame<TestMap, TestGameConfig> {

    private @NotNull PartyGameState currentState;

    public TestGame(@NotNull UserRegistry<? extends User> userRegistry, @NotNull TestMap map, @NotNull File directory, @NotNull TestGameConfig gameConfig) {
        super(userRegistry, map, directory, gameConfig);
    }

    @Override
    public void onLoad() {
        this.currentState = new TestGameStartingState(this);
        currentState.enter();
    }

    @Override
    public void onUnload() {

    }

    @Override
    public @NotNull GameScoreboard getScoreboard() {
        return new TestScoreboard();
    }
}
