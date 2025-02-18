package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.game.PartyGameMap;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.api.user.PartyGamesUserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.board.DefaultPartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.commands.PartyGamesForceMapCommand;
import com.github.deroq1337.partygames.core.data.game.commands.PartyGamesPauseCommand;
import com.github.deroq1337.partygames.core.data.game.commands.PartyGamesStartCommand;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardCommand;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.dice.config.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.language.LanguageManager;
import com.github.deroq1337.partygames.core.data.game.listeners.*;
import com.github.deroq1337.partygames.core.data.game.map.DefaultPartyGameMapManager;
import com.github.deroq1337.partygames.core.data.game.map.PartyGameMapManager;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameProvider;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUserRegistry;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class DefaultPartyGamesGame implements PartyGamesGame<DefaultPartyGamesUser> {

    private static final String MAIN_CONFIG_PATH = "plugins/partygames/configs/config.yml";
    private static final String DICE_CONFIG_PATH = "plugins/partygames/configs/dice.yml";
    private static final String GAMES_DIRECTORY = "plugins/partygames/games/";
    private static final String LOCALES_DIRECTORY = "plugins/partygames/locales/";
    private static final String BOARDS_DIRECTORY = "plugins/partygames/boards/";

    private final @NotNull PartyGames partyGames;
    private final @NotNull MainConfig mainConfig;
    private final @NotNull DiceConfig diceConfig;
    private final @NotNull PartyGamesUserRegistry<DefaultPartyGamesUser> userRegistry;
    private final @NotNull PartyGameProvider gameProvider;
    private final @NotNull LanguageManager languageManager;
    private final @NotNull PartyGamesBoardManager boardManager;

    private final @NotNull Map<Class<? extends PartyGameMap>, PartyGameMapManager> gameMapManagerMap = new HashMap<>();

    private @NotNull PartyGamesState currentState;
    private Optional<PartyGamesBoard> board;
    private boolean forceMapped;

    public DefaultPartyGamesGame(@NotNull PartyGames partyGames) {
        this.partyGames = partyGames;
        this.mainConfig = new MainConfig(new File(MAIN_CONFIG_PATH)).load(MainConfig.class);
        this.diceConfig = new DiceConfig(new File(DICE_CONFIG_PATH)).load(DiceConfig.class);
        this.userRegistry = new DefaultPartyGamesUserRegistry(this);
        this.gameProvider = new PartyGameProvider(this, new File(GAMES_DIRECTORY));
        this.languageManager = new LanguageManager(new File(LOCALES_DIRECTORY));
        this.boardManager = new DefaultPartyGamesBoardManager(new File(BOARDS_DIRECTORY));

        this.currentState = new PartyGamesLobbyState(this);
        this.board = boardManager.getRandomBoard().join();

        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new PlayerInteractListener(this);
        new EntityDamageListener(this);
        new PartyGameEndListener(this);
        new UserRollDiceListener(this);

        new PartyGamesForceMapCommand(this);
        new PartyGamesStartCommand(this);
        new PartyGamesPauseCommand(this);
        new PartyGamesBoardCommand(this);
    }

    @Override
    public @NotNull PartyGameMapManager getGameMapManager(@NotNull Class<? extends PartyGameMap> mapClass, @NotNull File gameDirectory) {
        return gameMapManagerMap.computeIfAbsent(mapClass, k -> new DefaultPartyGameMapManager(mapClass, gameDirectory));
    }

    @Override
    public void forceMap(@NotNull PartyGamesBoard board) {
        this.board = Optional.of(board);
        this.forceMapped = true;
    }
}
