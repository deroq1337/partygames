package com.github.deroq1337.partygames.api.state;

import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface PartyGamesState extends GameState {

    void onPlayerJoin(@NotNull UUID uuid);

    void onPlayerQuit(@NotNull UUID uuid);

    @NotNull GameScoreboard<? extends PartyGamesUser> getScoreboard();
}
