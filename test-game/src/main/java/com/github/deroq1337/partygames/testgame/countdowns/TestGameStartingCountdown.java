package com.github.deroq1337.partygames.testgame.countdowns;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameStartingCountdown;
import org.jetbrains.annotations.NotNull;

public class TestGameStartingCountdown extends PartyGameStartingCountdown {

    public TestGameStartingCountdown(@NotNull PartyGame<?, ? extends PartyGameConfig> partyGame, @NotNull GameState gameState) {
        super(partyGame, gameState);
    }
}
