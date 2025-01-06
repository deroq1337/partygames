package com.github.deroq1337.partygames.core.data.game.countdowns;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.countdown.CountdownTask;
import com.github.deroq1337.partygames.api.state.BaseState;
import com.github.deroq1337.partygames.core.PartyGames;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGamesLobbyCountdown extends Countdown {

    private final @NotNull PartyGames partyGames;
    private Optional<BukkitTask> task = Optional.empty();

    public PartyGamesLobbyCountdown(@NotNull PartyGames partyGames, @NotNull BaseState gameState) {
        super(gameState, 60, 20, 60, 30, 10, 5, 4, 3, 2, 1);
        this.partyGames = partyGames;
    }

    @Override
    public void startBukkitTask() {
        this.task = Optional.of(Bukkit.getScheduler().runTaskTimer(partyGames, new CountdownTask(this), 20L, 20L));
    }

    @Override
    public void cancelBukkitTask() {
        task.ifPresent(BukkitTask::cancel);
        this.task = Optional.empty();
    }

    @Override
    public void onTick() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setLevel(getCurrentTick());
            player.setExp((float) getCurrentTick() / getStartTick());
        });
    }

    @Override
    public void onSpecialTick(int tick) {
    }
}
