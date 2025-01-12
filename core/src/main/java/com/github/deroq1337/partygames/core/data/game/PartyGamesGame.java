package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.language.LanguageManager;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.dice.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface PartyGamesGame<U extends User> {

    @NotNull PartyGames getPartyGames();

    @NotNull MainConfig getMainConfig();

    @NotNull DiceConfig getDiceConfig();

    @NotNull PartyGameProvider getGameProvider();

    @NotNull LanguageManager getLanguageManager();

    @NotNull PartyGamesBoardManager getBoardManager();

    @NotNull UserRegistry<U> getUserRegistry();

    @NotNull PartyGamesState getCurrentState();

    void setCurrentState(@NotNull PartyGamesState state);

    Optional<PartyGamesBoard> getBoard();

    boolean isForceMapped();

    void forceMap(@NotNull PartyGamesBoard board);
}
