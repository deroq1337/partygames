package com.github.deroq1337.partygames.core.data.game.user;

import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PartyGamesUserRegistry implements UserRegistry<PartyGamesUser> {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Map<UUID, PartyGamesUser> userMap = new ConcurrentHashMap<>();

    @Override
    public @NotNull PartyGamesUser addUser(@NotNull UUID uuid, boolean alive) {
        PartyGamesUser user = new PartyGamesUser(game, uuid, alive);
        userMap.put(uuid, user);
        return user;
    }

    @Override
    public void removeUser(@NotNull UUID uuid) {
        userMap.remove(uuid);
    }

    @Override
    public Optional<PartyGamesUser> getUser(@NotNull UUID uuid) {
        return Optional.ofNullable(userMap.get(uuid));
    }

    @Override
    public @NotNull Collection<PartyGamesUser> getAliveUsers() {
        return userMap.values().stream()
                .filter(PartyGamesUser::isAlive)
                .collect(Collectors.toSet());
    }

    @Override
    public @NotNull Collection<PartyGamesUser> getUsers() {
        return userMap.values();
    }
}
