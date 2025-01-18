package com.github.deroq1337.partygames.testgame.scoreboard;

import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.user.User;
import org.jetbrains.annotations.NotNull;

public class TestScoreboard implements GameScoreboard {

    @Override
    public <U extends User> void setScoreboard(@NotNull U user) {

    }

    @Override
    public <U extends User> void updateScoreboard(@NotNull U user) {

    }

    @Override
    public void cancelScoreboardUpdate() {

    }
}
