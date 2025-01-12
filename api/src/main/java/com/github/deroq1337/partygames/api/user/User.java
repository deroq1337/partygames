package com.github.deroq1337.partygames.api.user;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;

public interface User {

    void sendMessage(@NotNull String key, Object... params);

    @NotNull UUID getUuid();

    @NotNull Locale getLocale();

    void setLocale(@NotNull Locale locale);

    @NotNull String getMessage(@NotNull String key, Object... params);

    boolean isAlive();
}
