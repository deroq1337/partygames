package com.github.deroq1337.partygames.api.scoreboard;

import org.jetbrains.annotations.NotNull;

public interface GameScoreboard<U> {

    void setScoreboard(@NotNull U user);

    void updateScoreboard(@NotNull U user);

    void cancelScoreboardUpdate();
}