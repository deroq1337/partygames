package com.github.deroq1337.partygames.api.state;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import org.jetbrains.annotations.NotNull;

public interface Countdownable {

    @NotNull Countdown getCountdown();

    boolean canStart();

    default void check() {
        Countdown countdown = getCountdown();
        if (countdown.isStarted() == canStart()) {
            return;
        }

        if (countdown.isStarted()) {
            countdown.cancel();
        } else {
            countdown.start();
        }
    }
}
