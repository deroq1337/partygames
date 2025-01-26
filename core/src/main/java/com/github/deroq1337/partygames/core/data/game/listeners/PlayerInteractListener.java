package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesInGameState;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractListener implements Listener {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;

    public PlayerInteractListener(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        game.getUserRegistry().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (game.getCurrentState() instanceof PartyGamesInGameState inGameState) {
                if (inGameState.getCurrentGame().isPresent()) {
                    return;
                }

                user.getExtraDice().ifPresent(extraDice -> {
                    if (extraDice.isRolling() && !extraDice.isRolled()) {
                        extraDice.roll();
                    }
                });

                user.getDice().ifPresent(dice -> {
                    if (dice.isRolling() && !dice.isRolled()) {
                        dice.roll();
                    }
                });
            }
        });
    }
}
