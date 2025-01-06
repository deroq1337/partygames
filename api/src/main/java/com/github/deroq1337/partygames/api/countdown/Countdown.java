package com.github.deroq1337.partygames.api.countdown;

import com.github.deroq1337.partygames.api.state.BaseState;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class Countdown {

    private final @NotNull BaseState gameState;
    private final int startTick;
    private final @NotNull List<Integer> specialTicks;

    private int currentTick;
    private boolean started;
    private boolean running;

    public Countdown(@NotNull BaseState gameState, int startTick, int... specialTicks) {
        this.gameState = gameState;
        this.startTick = startTick;
        this.specialTicks = Arrays.stream(specialTicks).boxed().toList();
    }

    public abstract void startBukkitTask();

    public abstract void cancelBukkitTask();

    public abstract void onTick();

    public abstract void onSpecialTick(int tick);

    public void start() {
        this.currentTick = startTick;
        this.started = true;
        this.running = true;
        startBukkitTask();
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

    protected void onEnd() {
        cancel();

        gameState.leave();
        if (gameState instanceof PartyGamesState) {
            ((PartyGamesState) gameState).getNextState().ifPresent(BaseState::enter);
        }
    }
}