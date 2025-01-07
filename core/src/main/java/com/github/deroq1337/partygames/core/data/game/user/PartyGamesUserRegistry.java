package com.github.deroq1337.partygames.core.data.game.user;

import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class PartyGamesUserRegistry implements UserRegistry<PartyGamesUser> {

    private final @NotNull PartyGamesGame game;
    private final @NotNull Map<UUID, PartyGamesUser> userMap = new ConcurrentHashMap<>();

    public void addUser(@NotNull UUID uuid){
        userMap.put(uuid, new PartyGamesUser(game, uuid));
    }

    @Override
    public void removeUser(@NotNull UUID uuid){
        userMap.remove(uuid);
    }

    @Override
    public Optional<PartyGamesUser> getUser(@NotNull UUID uuid){
        return Optional.ofNullable(userMap.get(uuid));
    }

    @Override
    public @NotNull Collection<PartyGamesUser> getUsers() {
        return userMap.values();
    }
}
