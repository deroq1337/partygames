package com.github.deroq1337.partygames.api.game;

import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@RequiredArgsConstructor
@Getter
public abstract class PartyGame<M extends PartyGameMap> {

    private final @NotNull File directory;
    private final @NotNull UserRegistry<? extends User> userRegistry;
    private final @NotNull M map;

    public abstract void onLoad();

    public abstract void onUnload();

    public abstract @NotNull PartyGameState getState();

}
