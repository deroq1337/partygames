package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.models.CurrentGame;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesInGameState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor
public class PartyGameLoadTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull PartyGamesInGameState state;
    private final @NotNull CurrentGame currentGame;

    @Override
    public void start() {
        runTaskLater(game.getPartyGames(), 8 * 20L);
    }

    @Override
    public void run() {
        game.getUserRegistry().getAliveUsers().forEach(user -> user.getDice().ifPresent(Dice::destroy));
        currentGame.getPartyGame().onLoad();
        state.setCurrentGame(Optional.of(currentGame));
        cancel();
    }
}
