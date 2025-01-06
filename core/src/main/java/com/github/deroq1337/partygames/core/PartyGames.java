package com.github.deroq1337.partygames.core;

import com.github.deroq1337.partygames.core.data.game.loader.PartyGameLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PartyGames extends JavaPlugin {

    private PartyGameLoader gameLoader;

    @Override
    public void onEnable() {
        this.gameLoader = new PartyGameLoader(new File("plugins/partygames/games/"));
        gameLoader.loadGames();
    }

    @Override
    public void onDisable() {

    }
}
