package com.github.deroq1337.partygames.core.data.game.user;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class PartyGamesUserRegistry {

    private final @NotNull Map<UUID, PartyGamesUser> userMap = new ConcurrentHashMap<>();

    public @NotNull PartyGamesUser addUser(@NotNull UUID uuid){
        final PartyGamesUser user = new PartyGamesUser(uuid);
        userMap.put(uuid, user);
        return user;
    }

    public void removeUser(@NotNull UUID uuid){
        userMap.remove(uuid);
    }

    public Optional<PartyGamesUser> getUser(@NotNull UUID uuid){
        return Optional.ofNullable(userMap.get(uuid));
    }

    public @NotNull Collection<PartyGamesUser> getUsers() {
        return userMap.values();
    }
}
