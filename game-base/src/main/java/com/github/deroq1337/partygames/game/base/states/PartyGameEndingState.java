package com.github.deroq1337.partygames.game.base.states;

import com.github.bukkitnews.partygames.common.events.PartyGameEndEvent;
import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.game.PartyGamePlacement;
import com.github.deroq1337.partygames.api.state.Countdownable;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameEndingCountdown;
import com.github.deroq1337.partygames.game.base.placement.DefaultPartyGameFinishPlacement;
import com.github.deroq1337.partygames.game.base.placement.DefaultPartyGamePlacement;
import com.github.deroq1337.partygames.game.base.user.FinishablePartyGameUser;
import com.github.deroq1337.partygames.game.base.user.AbstractPartyGameUser;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class PartyGameEndingState implements PartyGameState, Countdownable {

    protected final @NotNull PartyGame<?, ? extends PartyGameConfig, ? extends AbstractPartyGameUser> partyGame;
    private final @NotNull Countdown countdown;

    public PartyGameEndingState(@NotNull PartyGame<?, ? extends PartyGameConfig, ? extends AbstractPartyGameUser> partyGame) {
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
        Bukkit.getPluginManager().callEvent(new PartyGameEndEvent(getPlacements()));
    }

    private @NotNull Map<Integer, PartyGamePlacement> getPlacements() {
        return partyGame.getUsers().stream()
                .map(this::buildPlacement)
                .collect(Collectors.toMap(PartyGamePlacement::getPlacement, placement -> placement));
    }

    private @NotNull PartyGamePlacement buildPlacement(@NotNull AbstractPartyGameUser user) {
        if (user instanceof FinishablePartyGameUser finishableUser) {
            return new DefaultPartyGameFinishPlacement(
                    user.getPartyGamesUser().getUuid(),
                    user.getPlacement().orElse(-1),
                    finishableUser.getFinishedAfter());
        } else {
            return new DefaultPartyGamePlacement(
                    user.getPartyGamesUser().getUuid(),
                    user.getPlacement().orElse(-1));
        }
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
