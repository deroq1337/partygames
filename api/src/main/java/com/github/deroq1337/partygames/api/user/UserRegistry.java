package com.github.deroq1337.partygames.api.user;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserRegistry<U extends User> {

    void addUser(@NotNull UUID uuid);

    void removeUser(@NotNull UUID uuid);

    Optional<U> getUser(@NotNull UUID uuid);

    @NotNull Collection<U> getUsers();
}
