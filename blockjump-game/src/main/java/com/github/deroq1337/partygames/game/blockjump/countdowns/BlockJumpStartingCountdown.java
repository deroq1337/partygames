package com.github.deroq1337.partygames.game.blockjump.countdowns;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameStartingCountdown;
import com.github.deroq1337.partygames.game.base.user.AbstractPartyGameUser;
import org.jetbrains.annotations.NotNull;

public class BlockJumpStartingCountdown extends PartyGameStartingCountdown {

    public BlockJumpStartingCountdown(@NotNull PartyGame<?, ? extends PartyGameConfig, ? extends AbstractPartyGameUser> partyGame, @NotNull GameState gameState) {
        super(partyGame, gameState);
    }
}
