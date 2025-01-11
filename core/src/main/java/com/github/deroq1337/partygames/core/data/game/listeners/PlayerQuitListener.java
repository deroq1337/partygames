package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.deroq1337.partygames.api.state.CountdownableState;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {

    private final @NotNull PartyGamesGame game;

    public PlayerQuitListener(@NotNull PartyGamesGame game) {
        this.game = game;
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PartyGamesState gameState = game.getCurrentState();
        gameState.onPlayerQuit(event.getPlayer().getUniqueId());

        if (gameState instanceof CountdownableState) {
            ((CountdownableState) gameState).check();
        }
    }
}
