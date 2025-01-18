package com.github.deroq1337.partygames.game.base.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.game.PartyGameMap;
import com.github.deroq1337.partygames.api.state.CountdownableState;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameStartingCountdown;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


@Getter
public abstract class PartyGameStartingState<M extends PartyGameMap> implements PartyGameState, CountdownableState {

    protected final @NotNull PartyGame<M, ? extends PartyGameConfig> partyGame;
    private final @NotNull PartyGameStartingCountdown countdown;

    public PartyGameStartingState(@NotNull PartyGame<M, ? extends PartyGameConfig> partyGame) {
        this.partyGame = partyGame;
        this.countdown = initCountdown();
    }

    @Override
    public void enter() {
        partyGame.setCurrentState(this);
    }

    @Override
    public boolean canStart() {
        return true;
    }

    public abstract @NotNull PartyGameStartingCountdown initCountdown();
}
