package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesInGameState;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDamageListener implements Listener {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;

    public EntityDamageListener(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (game.getCurrentState() instanceof PartyGamesLobbyState) {
            event.setCancelled(true);
            return;
        }

        if (game.getCurrentState() instanceof PartyGamesInGameState inGameState) {
            if (inGameState.getCurrentGame().isEmpty()) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
