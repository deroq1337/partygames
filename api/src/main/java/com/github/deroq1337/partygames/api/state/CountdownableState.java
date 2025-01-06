package com.github.deroq1337.partygames.api.state;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import org.jetbrains.annotations.NotNull;

public interface CountdownableState {

    @NotNull Countdown getCountdown();

    boolean canStart();

    default void check() {

    }
}
