package com.github.deroq1337.partygames.game.base.states;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.Countdownable;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.user.AbstractPartyGameUser;
import lombok.Getter;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Getter
public abstract class AbstractPartyGameRunningState<U extends AbstractPartyGameUser> implements PartyGameState, Countdownable {

    protected final @NotNull PartyGame<?, ? extends PartyGameConfig, U> partyGame;
    private final @NotNull Countdown countdown;

    private long startedAt;

    public AbstractPartyGameRunningState(@NotNull PartyGame<?, ? extends PartyGameConfig, U> partyGame) {
        this.partyGame = partyGame;
        this.countdown = initCountdown();
    }

    @Override
    public void enter() {
        this.startedAt = System.currentTimeMillis();

        partyGame.setCurrentState(this);
        partyGame.getUsers().forEach(user ->
                user.getBukkitPlayer().ifPresent(player -> player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN, 1f, 1f)));
    }

    @Override
    public void leave() {
        partyGame.getUsers().forEach(user -> {
            user.getPartyGamesUser().sendTitle("game_over_title");
            user.getBukkitPlayer().ifPresent(player -> player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f));
        });

        if (!(this instanceof FinishablePartyGameRunningState)) {
            determinePlacements();
        }

        getNextState().ifPresent(GameState::enter);
    }

    private void determinePlacements() {
        List<U> sortedUsers = partyGame.getSortedUsers();

        for (int i = 0; i < sortedUsers.size(); i++) {
            U user = sortedUsers.get(i);
            if (user.getPlacement().isPresent()) {
                continue;
            }

            user.setPlacement(Optional.of(i + 1));
        }
    }

    public abstract @NotNull Countdown initCountdown();

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.of(new PartyGameEndingState(partyGame));
    }
}
