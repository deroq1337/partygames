package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.language.LanguageManager;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameProvider;
import org.jetbrains.annotations.NotNull;

public interface PartyGamesGame<U extends User> {

    @NotNull PartyGames getPartyGames();

    @NotNull UserRegistry<U> getUserRegistry();

    @NotNull PartyGameProvider getGameLoader();

    @NotNull LanguageManager getLanguageManager();

    @NotNull PartyGamesBoardManager getBoardManager();
}
