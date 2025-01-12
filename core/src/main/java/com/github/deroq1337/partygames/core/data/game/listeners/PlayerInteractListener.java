package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesInGameState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractListener implements Listener {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;

    public PlayerInteractListener(@NotNull PartyGamesGame<PartyGamesUser> game) {
        this.game = game;
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND
                || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        game.getUserRegistry().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (game.getCurrentState() instanceof PartyGamesInGameState inGameState) {
                if (inGameState.getCurrentGame().isPresent()) {
                    return;
                }

                if (user.getDice().isPresent()) {
                    user.getDice().get().roll();
                    return;
                }
            }
        });
    }
}