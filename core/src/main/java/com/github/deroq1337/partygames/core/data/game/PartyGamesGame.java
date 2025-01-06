package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.language.LanguageManager;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.loader.PartyGameLoader;
import org.jetbrains.annotations.NotNull;

public interface PartyGamesGame {

    @NotNull PartyGames getPartyGames();

    @NotNull LanguageManager getLanguageManager();

    @NotNull PartyGameLoader getGameLoader();
}
