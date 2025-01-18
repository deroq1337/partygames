package com.github.deroq1337.partygames.game.base.listeners;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.PartyGameBase;
import com.github.deroq1337.partygames.game.base.states.PartyGameStartingState;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerPreventMoveListener implements Listener {

    private final @NotNull PartyGame<?, ?, ?> game;

    public PlayerPreventMoveListener(@NotNull PartyGame<?, ?, ?> game) {
        this.game = game;
        PartyGameBase.registerListener(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!(game.getCurrentState() instanceof PartyGameStartingState<?,?>)) {
            return;
        }

        Optional.ofNullable(game.getUserRegistry().getAliveUser(event.getPlayer().getUniqueId())).ifPresent(user -> {
            Location from = event.getFrom();

            Optional.ofNullable(event.getTo()).ifPresent(to -> {
                if (from.getX() != to.getX() || from.getZ() != to.getZ() || from.getY() != to.getY()) {
                    event.setTo(from.setDirection(to.getDirection()));
                }
            });
        });
    }
}
