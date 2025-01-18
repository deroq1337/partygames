package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.deroq1337.partygames.api.state.Countdownable;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {

    private final @NotNull PartyGamesGame game;

    public PlayerJoinListener(@NotNull PartyGamesGame game) {
        this.game = game;
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PartyGamesState gameState = game.getCurrentState();
        gameState.onPlayerJoin(event.getPlayer().getUniqueId());

        if (gameState instanceof Countdownable) {
            ((Countdownable) gameState).check();
        }
    }
}