package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.language.LanguageManager;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.loader.PartyGameLoader;
import org.jetbrains.annotations.NotNull;

public interface PartyGamesGame<U extends User> {

    @NotNull PartyGames getPartyGames();

    @NotNull UserRegistry<U> getUserRegistry();

    @NotNull PartyGameLoader getGameLoader();

    @NotNull LanguageManager getLanguageManager();
}
