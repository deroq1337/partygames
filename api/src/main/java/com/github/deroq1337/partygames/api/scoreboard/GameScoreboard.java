package com.github.deroq1337.partygames.api.scoreboard;

import com.github.deroq1337.partygames.api.user.User;
import org.jetbrains.annotations.NotNull;

public interface GameScoreboard {

    <U extends User> void setScoreboard(@NotNull U user);

    <U extends User> void updateScoreboard(@NotNull U user);

    void cancelScoreboardUpdate();
}