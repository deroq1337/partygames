package com.github.deroq1337.partygames.api.state;

import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import org.jetbrains.annotations.NotNull;

public interface GameState {

    void enter();

    void leave();

    @NotNull GameScoreboard getScoreboard();
}
