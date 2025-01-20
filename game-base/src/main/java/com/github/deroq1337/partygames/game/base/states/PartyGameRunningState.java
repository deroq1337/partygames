package com.github.deroq1337.partygames.game.base.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.user.PartyGameUserBase;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class PartyGameRunningState<U extends PartyGameUserBase> implements PartyGameState {

    protected final @NotNull PartyGame<?, ? extends PartyGameConfig, U> partyGame;

    @Override
    public void enter() {
        partyGame.setCurrentState(this);
        partyGame.getUsers().forEach(user ->
                user.getBukkitPlayer().ifPresent(player ->
                        player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN, 1f, 1f)));
    }

    @Override
    public void leave() {
        partyGame.getUsers().forEach(user -> {
            user.getPartyGamesUser().sendTitle("game_over_title");

            user.getBukkitPlayer().ifPresent(player ->
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f));
        });

        getNextState().ifPresent(GameState::enter);
    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.of(new PartyGameEndingState(partyGame));
    }
}
