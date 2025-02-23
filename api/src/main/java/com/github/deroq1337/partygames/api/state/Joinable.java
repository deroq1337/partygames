package com.github.deroq1337.partygames.api.state;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Joinable {

    void onJoin(@NotNull UUID player);

    void onQuit(@NotNull UUID player);
}
