package com.github.deroq1337.partygames.game.base;

import com.github.deroq1337.partygames.api.game.PartyGame;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class PartyGameBase {

    private static final @NotNull AtomicReference<Plugin> PLUGIN_REFERENCE = new AtomicReference<>();
    private static final @NotNull AtomicReference<Optional<PartyGame<?, ?, ?>>> GAME_REFERENCE = new AtomicReference<>();
    private static final @NotNull Map<PartyGame<?, ?, ?>, List<Listener>> REGISTERED_GAME_LISTENERS = new ConcurrentHashMap<>();

    static {
        PLUGIN_REFERENCE.set(Bukkit.getPluginManager().getPlugin("PartyGames"));
    }

    public static void setGame(Optional<PartyGame<?, ?, ?>> game) {
        GAME_REFERENCE.set(game);
    }

    public static @NotNull Plugin getPlugin() {
        return PLUGIN_REFERENCE.get();
    }

    public static void registerListener(@NotNull Listener listener) {
        PartyGame<?, ?, ?> game = GAME_REFERENCE.get()
                .orElseThrow(() -> new IllegalStateException("Could not register listener: PartyGameBase must set game reference"));

        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
        REGISTERED_GAME_LISTENERS.computeIfAbsent(game, o -> new ArrayList<>()).add(listener);
    }

    public static void unregisterListeners() {
        PartyGame<?, ?, ?> game = GAME_REFERENCE.get().orElseThrow(() -> new IllegalStateException("Could not unregister listeners: PartyGameBase must set game reference"));
        Optional.ofNullable(REGISTERED_GAME_LISTENERS.get(game)).ifPresent(listeners -> listeners.forEach(HandlerList::unregisterAll));
        REGISTERED_GAME_LISTENERS.remove(game);
    }
}
