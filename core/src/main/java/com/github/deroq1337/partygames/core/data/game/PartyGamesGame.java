package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.LanguageManager;
import com.github.deroq1337.partygames.core.data.game.loader.PartyGameLoader;
import org.jetbrains.annotations.NotNull;

public interface PartyGamesGame {

    @NotNull LanguageManager getLanguageManager();

    @NotNull PartyGameLoader getGameLoader();
}
