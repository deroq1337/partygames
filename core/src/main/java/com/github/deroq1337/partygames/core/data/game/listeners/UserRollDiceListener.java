package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.events.UserRollDiceEvent;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class UserRollDiceListener implements Listener {

    public UserRollDiceListener(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onUserRollDice(UserRollDiceEvent event) {
        DefaultPartyGamesUser user = event.getUser();
        user.getExtraDice().ifPresentOrElse(extraDice -> {
            if (!event.getDice().equals(extraDice)) {
                extraDice.initiateRoll();
                return;
            }

            user.goToField();
        }, user::goToField);
    }
}
