package com.github.deroq1337.partygames.api;


import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface Language {

    @NotNull CompletableFuture<Void> loadMessages();

    void clearMessages();

    @NotNull String getMessage(@NotNull Locale locale, @NotNull String key);

    Optional<String> getMessageOptional(@NotNull Locale locale, @NotNull String key);

    @NotNull Set<Locale> getLocales();
}