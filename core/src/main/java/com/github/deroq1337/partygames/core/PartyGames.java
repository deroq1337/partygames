package com.github.deroq1337.partygames.core;

import com.github.deroq1337.partygames.core.data.game.DefaultPartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyGames extends JavaPlugin {

    private PartyGamesGame game;

    @Override
    public void onEnable() {
        this.game = new DefaultPartyGamesGame();
    }

    @Override
    public void onDisable() {
        game.getGameLoader().unloadGames();
    }
}
