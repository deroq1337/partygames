package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.language.LanguageManager;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.board.DefaultPartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoard;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.commands.PartyGamesForceMapCommand;
import com.github.deroq1337.partygames.core.data.game.commands.PartyGamesPauseCommand;
import com.github.deroq1337.partygames.core.data.game.commands.PartyGamesStartCommand;
import com.github.deroq1337.partygames.core.data.game.commands.board.PartyGamesBoardCommand;
import com.github.deroq1337.partygames.core.data.game.dice.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.language.DefaultLanguageManager;
import com.github.deroq1337.partygames.core.data.game.listeners.PlayerJoinListener;
import com.github.deroq1337.partygames.core.data.game.listeners.PlayerQuitListener;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameProvider;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUserRegistry;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Getter
@Setter
public class DefaultPartyGamesGame implements PartyGamesGame<PartyGamesUser> {

    private final @NotNull PartyGames partyGames;
    private final @NotNull DiceConfig diceConfig;
    private final @NotNull PartyGameProvider gameLoader;
    private final @NotNull LanguageManager languageManager;
    private final @NotNull PartyGamesBoardManager boardManager;
    private final @NotNull UserRegistry<PartyGamesUser> userRegistry;

    private @NotNull PartyGamesState currentState;
    private Optional<PartyGamesBoard> board = Optional.empty();
    private boolean forceMapped;

    public DefaultPartyGamesGame(@NotNull PartyGames partyGames) {
        this.partyGames = partyGames;
        //this.diceConfig = new DiceConfig(new File("plugins/partygames/configs/dice.yml")).load();
        this.diceConfig = new DiceConfig(new File("plugins/partygames/configs/dice.yml"));
        this.gameLoader = new PartyGameProvider(this, new File("plugins/partygames/games/"));
        this.languageManager = new DefaultLanguageManager(new File("plugins/partygames/locales/"));
        this.boardManager = new DefaultPartyGamesBoardManager(new File("plugins/partygames/boards/"));
        this.userRegistry = new PartyGamesUserRegistry(this);

        this.currentState = new PartyGamesLobbyState(this);
        this.board = boardManager.getRandomBoard().join();

        new PlayerJoinListener(this);
        new PlayerQuitListener(this);

        new PartyGamesForceMapCommand(this);
        new PartyGamesStartCommand(this);
        new PartyGamesPauseCommand(this);
        new PartyGamesBoardCommand(this);
    }

    @Override
    public void forceMap(@NotNull PartyGamesBoard board) {
        this.board = Optional.of(board);
        this.forceMapped = true;
    }
}
