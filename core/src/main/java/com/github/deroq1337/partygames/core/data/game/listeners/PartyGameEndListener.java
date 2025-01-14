package com.github.deroq1337.partygames.core.data.game.listeners;

import com.github.bukkitnews.partygames.common.events.PartyGameEndEvent;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesInGameState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PartyGameEndListener implements Listener {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;

    public PartyGameEndListener(@NotNull PartyGamesGame<PartyGamesUser> game) {
        this.game = game;
        game.getPartyGames().getServer().getPluginManager().registerEvents(this, game.getPartyGames());
    }

    @EventHandler
    public void onPartyGameEnd(PartyGameEndEvent event) {
        if (!(game.getCurrentState() instanceof PartyGamesInGameState inGameState)) {
            return;
        }

        inGameState.onGameEnd();
    }
}
