package com.github.deroq1337.partygames.core.data.game.countdowns;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.state.BaseState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.tasks.CountdownTask;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PartyGamesLobbyCountdown extends Countdown {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private Optional<BukkitTask> task = Optional.empty();

    public PartyGamesLobbyCountdown(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull BaseState gameState) {
        super(gameState, 60, 60, 30, 10, 5, 4, 3, 2, 1);
        this.game = game;
    }

    @Override
    public void startBukkitTask() {
        this.task = Optional.of(Bukkit.getScheduler().runTaskTimer(game.getPartyGames(), new CountdownTask(this), 20L, 20L));
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
        String countdownMessage = "lobby_countdown" + (tick == 1 ? "_one" : "");

        game.getUserRegistry().getUsers().forEach(user -> {
            user.sendMessage(countdownMessage, tick);
            user.getBukkitPlayer().ifPresent(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f));
        });
    }
}
