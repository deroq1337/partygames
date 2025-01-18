package com.github.deroq1337.partygames.core.data.game.user;

import com.github.deroq1337.partygames.api.user.PartyGamesUserRegistry;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultPartyGamesUserRegistry implements PartyGamesUserRegistry<DefaultPartyGamesUser> {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull Map<UUID, DefaultPartyGamesUser> userMap = new ConcurrentHashMap<>();

    @Override
    public @NotNull DefaultPartyGamesUser addUser(@NotNull UUID uuid, boolean alive) {
        DefaultPartyGamesUser user = new DefaultPartyGamesUser(game, uuid, alive);
        userMap.put(uuid, user);
        return user;
    }

    @Override
    public void removeUser(@NotNull UUID uuid) {
        userMap.remove(uuid);
    }

    @Override
    public Optional<DefaultPartyGamesUser> getUser(@NotNull UUID uuid) {
        return Optional.ofNullable(userMap.get(uuid));
    }

    @Override
    public Optional<DefaultPartyGamesUser> getAliveUser(@NotNull UUID uuid) {
        return getUser(uuid).filter(DefaultPartyGamesUser::isAlive);
    }

    @Override
    public @NotNull Collection<DefaultPartyGamesUser> getAliveUsers() {
        return userMap.values().stream()
                .filter(DefaultPartyGamesUser::isAlive)
                .collect(Collectors.toSet());
    }

    @Override
    public @NotNull Collection<DefaultPartyGamesUser> getUsers() {
        return userMap.values();
    }
}
