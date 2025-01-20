package com.github.deroq1337.partygames.api.game;

import com.github.deroq1337.partygames.api.config.YamlConfig;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.api.user.PartyGameUser;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.api.user.PartyGamesUserRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@RequiredArgsConstructor
@Getter
public abstract class PartyGame<M extends PartyGameMap, C extends YamlConfig, U extends PartyGameUser> {

    private final @NotNull PartyGamesUserRegistry<? extends PartyGamesUser> userRegistry;
    private final @NotNull M map;
    private final @NotNull File directory;
    private final @NotNull C gameConfig;
    private final @NotNull Map<UUID, U> users = new HashMap<>();

    public abstract void onLoad();

    public abstract void onUnload();

    public abstract @NotNull PartyGameState getCurrentState();

    public abstract void setCurrentState(@NotNull PartyGameState state);

    public abstract @NotNull GameScoreboard<U> getScoreboard();

    public void addUser(@NotNull U user) {
        users.put(user.getPartyGamesUser().getUuid(), user);
    }

    public void removeUser(@NotNull UUID uuid) {
        users.remove(uuid);
    }

    public Optional<U> getUser(@NotNull UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }

    public @NotNull Collection<U> getUsers() {
        return users.values();
    }
}
