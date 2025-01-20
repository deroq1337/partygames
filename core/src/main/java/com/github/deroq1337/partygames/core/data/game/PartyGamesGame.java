package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.game.PartyGameMap;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.api.user.PartyGamesUserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.dice.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.language.LanguageManager;
import com.github.deroq1337.partygames.core.data.game.map.PartyGameMapManager;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameProvider;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

public interface PartyGamesGame<U extends PartyGamesUser> {

    @NotNull PartyGames getPartyGames();

    @NotNull MainConfig getMainConfig();

    @NotNull DiceConfig getDiceConfig();

    @NotNull PartyGameProvider getGameProvider();

    @NotNull LanguageManager getLanguageManager();

    @NotNull PartyGamesBoardManager getBoardManager();

    @NotNull PartyGameMapManager getGameMapManager(@NotNull Class<? extends PartyGameMap> mapClass, @NotNull File gameDirectory);

    @NotNull PartyGamesUserRegistry<U> getUserRegistry();

    @NotNull PartyGamesState getCurrentState();

    void setCurrentState(@NotNull PartyGamesState state);

    Optional<PartyGamesBoard> getBoard();

    boolean isForceMapped();

    void forceMap(@NotNull PartyGamesBoard board);
}
