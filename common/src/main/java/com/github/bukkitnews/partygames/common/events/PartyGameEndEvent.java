package com.github.bukkitnews.partygames.common.events;

import com.github.deroq1337.partygames.api.game.PartyGamePlacement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class PartyGameEndEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @NotNull Map<Integer, PartyGamePlacement> placements;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
