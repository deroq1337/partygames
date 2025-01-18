package com.github.deroq1337.partygames.game.blockjump.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.user.PartyGameUser;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.states.PartyGameRunningState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlockJumpRunningState extends PartyGameRunningState {

    public BlockJumpRunningState(@NotNull PartyGame<?, ? extends PartyGameConfig, ? extends PartyGameUser> partyGame) {
        super(partyGame);
    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.empty();
    }
}
