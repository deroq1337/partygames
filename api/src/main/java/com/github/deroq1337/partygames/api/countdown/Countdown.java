package com.github.deroq1337.partygames.api.countdown;

import com.github.deroq1337.partygames.api.state.GameState;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class Countdown {

    private final @NotNull GameState gameState;
    private final int startTick;
    private final @NotNull List<Integer> specialTicks;

    private int currentTick;
    private boolean started;
    private boolean running;

    public Countdown(@NotNull GameState gameState, int startTick, @NotNull List<Integer> specialTicks) {
        this.gameState = gameState;
        this.startTick = startTick;
        this.specialTicks = specialTicks;
    }

    public Countdown(@NotNull GameState gameState, int startTick, int... specialTicks) {
        this(gameState, startTick, Arrays.stream(specialTicks).boxed().toList());
    }

    public abstract void initBukkitTask();

    public abstract void cancelBukkitTask();

    public abstract void onTick();

    public abstract void onSpecialTick(int tick);

    public void start() {
        this.currentTick = startTick;
        this.started = true;
        this.running = true;
        initBukkitTask();
    }

    public void cancel() {
        this.running = false;
        this.started = false;
        this.currentTick = startTick;
        cancelBukkitTask();
    }

    public void pause() {
        this.running = false;
    }

    public void unpause() {
        this.running = true;
    }

    public void onEnd() {
        cancel();

        gameState.leave();
        gameState.getNextState().ifPresent(GameState::enter);
    }

    public void decrementCurrentTick() {
        this.currentTick--;
    }
}