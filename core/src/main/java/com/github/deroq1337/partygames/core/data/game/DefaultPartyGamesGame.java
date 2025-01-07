package com.github.deroq1337.partygames.core.data.game;

import com.github.deroq1337.partygames.api.language.LanguageManager;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.core.PartyGames;
import com.github.deroq1337.partygames.core.data.game.language.DefaultLanguageManager;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameProvider;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUserRegistry;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter
public class DefaultPartyGamesGame implements PartyGamesGame<PartyGamesUser> {

    private final @NotNull PartyGames partyGames;
    private final @NotNull UserRegistry<PartyGamesUser> userRegistry;
    private final @NotNull PartyGameProvider gameLoader;
    private final @NotNull LanguageManager languageManager;

    public DefaultPartyGamesGame() {
        this.partyGames = new PartyGames();
        this.userRegistry = new PartyGamesUserRegistry(this);
        this.gameLoader = new PartyGameProvider(this, new File("plugins/partygames/games/"));
        this.languageManager = new DefaultLanguageManager(new File("plugins/partygames/locales/"));
    }
}
