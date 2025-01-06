package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.LanguageManager;
import com.github.deroq1337.partygames.core.data.game.language.DefaultLanguageManager;
import com.github.deroq1337.partygames.core.data.game.loader.PartyGameLoader;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter
public class DefaultPartyGamesGame implements PartyGamesGame {

    private final @NotNull PartyGameLoader gameLoader;
    private final @NotNull LanguageManager languageManager;

    public DefaultPartyGamesGame() {
        this.gameLoader = new PartyGameLoader(new File("plugins/partygames/games/"));
        gameLoader.loadGames();

        this.languageManager = new DefaultLanguageManager(new File("plugins/partygames/locales/"));
        languageManager.loadMessages();
    }
}
