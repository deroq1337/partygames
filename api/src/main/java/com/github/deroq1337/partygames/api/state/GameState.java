package com.github.deroq1337.partygames.api.state;

import java.util.Optional;

public interface GameState {

    void enter();

    void leave();

    Optional<GameState> getNextState();
}
