package com.github.deroq1337.partygames.core.data.game.events;

import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class UserRollDiceEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @NotNull DefaultPartyGamesUser user;
    private final @NotNull Dice dice;

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
