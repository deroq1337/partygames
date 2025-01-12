package com.github.bukkitnews.partygames.common.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class PartyGameEndEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @NotNull Map<Integer, UUID> placements;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
