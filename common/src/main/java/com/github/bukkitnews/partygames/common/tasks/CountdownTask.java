package com.github.bukkitnews.partygames.common.tasks;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CountdownTask implements Runnable {

    private final @NotNull Countdown countdown;

    @Override
    public void run() {
        if (!countdown.isRunning()) {
            return;
        }

        int currentTick = countdown.getCurrentTick();
        if (currentTick <= 0) {
            countdown.onEnd();
            return;
        }

        if (countdown.getSpecialTicks().contains(currentTick)) {
            countdown.onSpecialTick(currentTick);
        }

        countdown.onTick();
        countdown.decrementCurrentTick();
    }
}
