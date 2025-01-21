package com.github.deroq1337.partygames.game.base.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.user.FinishablePartyGameUser;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class FinishablePartyGameRunningState<U extends FinishablePartyGameUser> extends AbstractPartyGameRunningState<U> {

    public FinishablePartyGameRunningState(@NotNull PartyGame<?, ? extends PartyGameConfig, U> partyGame) {
        super(partyGame);
    }

    public void finish(@NotNull U user) {
        user.setFinished(true);
        user.setFinishedAfter(Optional.of(System.currentTimeMillis() - getStartedAt()));

        int finished = (int) partyGame.getUsers().stream()
                .filter(FinishablePartyGameUser::isFinished)
                .count();

        user.setPlacement(Optional.of(finished));
        user.getBukkitPlayer().ifPresent(player -> user.getPartyGamesUser().sendMessage("game_user_finished", player.getDisplayName()));

        if (finished >= partyGame.getGameConfig().getMaxFinishers() || finished >= partyGame.getUsers().size()) {
            leave();
        }
    }

    public abstract boolean isGoalReached(@NotNull U user);
}
