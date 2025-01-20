package com.github.deroq1337.partygames.game.base.listeners;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.PartyGameBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {

    private final @NotNull PartyGame<?, ?, ?> partyGame;

    public PlayerQuitListener(@NotNull PartyGame<?, ?, ?> partyGame) {
        this.partyGame = partyGame;
        PartyGameBase.registerListener(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        partyGame.removeUser(event.getPlayer().getUniqueId());
    }
}
