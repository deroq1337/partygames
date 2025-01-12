package com.github.deroq1337.partygames.core.data.game.task;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameManifest;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesInGameState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class PartyGameChooseTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull PartyGamesInGameState state;
    private final @NotNull Set<PartyGameManifest> playableGames;

    public PartyGameChooseTask(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull PartyGamesInGameState state) {
        this.game = game;
        this.state = state;
        this.playableGames = state.getPlayableGames();
    }

    @Override
    public void run() {
        if (playableGames.isEmpty()) {
            System.err.println("There are no more games to play");
            cancel();
            return;
        }

        long notRolled = game.getUserRegistry().getAliveUsers().stream()
                .filter(user -> user.getDice().map(dice -> !dice.isRolled()).orElse(true))
                .count();
        if (notRolled == 0) {
            PartyGameManifest manifest = new ArrayList<>(playableGames).get(ThreadLocalRandom.current().nextInt(playableGames.size()));
            state.playGame(manifest);
            cancel();
        }
    }

    @Override
    public void start() {
        runTaskTimer(game.getPartyGames(), 0L, 20L);
    }
}
