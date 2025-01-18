package com.github.deroq1337.partygames.game.base.countdowns;

import com.github.bukkitnews.partygames.common.tasks.CountdownTask;
import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.user.PartyGameUser;
import com.github.deroq1337.partygames.game.base.PartyGameBase;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGameStartingCountdown extends Countdown {

    private final @NotNull PartyGame<?, ?, ?> partyGame;
    private final @NotNull Plugin plugin;

    private Optional<BukkitTask> task = Optional.empty();

    public PartyGameStartingCountdown(@NotNull PartyGame<?, ? extends PartyGameConfig, ? extends PartyGameUser> partyGame, @NotNull GameState gameState) {
        super(gameState, partyGame.getGameConfig().getStartingTicks(), partyGame.getGameConfig().getStartingSpecialTicks());
        this.partyGame = partyGame;
        this.plugin = PartyGameBase.getPlugin();
    }

    @Override
    public void initBukkitTask() {
        this.task = Optional.of(Bukkit.getScheduler().runTaskTimer(plugin, new CountdownTask(this), 20L, 20L));
    }

    @Override
    public void cancelBukkitTask() {
        task.ifPresent(BukkitTask::cancel);
        this.task = Optional.empty();
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onSpecialTick(int tick) {
        String countdownMessage = "game_starting_countdown" + (tick == 1 ? "_one" : "");
        partyGame.getUserRegistry().getUsers().forEach(user -> user.sendMessage(countdownMessage, tick));
    }
}
