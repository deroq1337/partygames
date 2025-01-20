package com.github.deroq1337.partygames.game.base.states;

import com.github.bukkitnews.partygames.common.events.PartyGameEndEvent;
import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.Countdownable;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameEndingCountdown;
import com.github.deroq1337.partygames.game.base.user.PartyGameUserBase;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

@Getter
public class PartyGameEndingState implements PartyGameState, Countdownable {

    protected final @NotNull PartyGame<?, ? extends PartyGameConfig, ? extends PartyGameUserBase> partyGame;
    private final @NotNull Countdown countdown;

    public PartyGameEndingState(@NotNull PartyGame<?, ? extends PartyGameConfig, ? extends PartyGameUserBase> partyGame) {
        this.partyGame = partyGame;
        this.countdown = new PartyGameEndingCountdown(this);
    }

    @Override
    public void enter() {
        partyGame.setCurrentState(this);
        countdown.start();
    }

    @Override
    public void leave() {
        Bukkit.getPluginManager().callEvent(new PartyGameEndEvent(new HashMap<>()));
    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.empty();
    }

    @Override
    public boolean canStart() {
        return true;
    }
}
