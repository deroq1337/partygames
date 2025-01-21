package com.github.deroq1337.partygames.api.game;

import java.util.Optional;

public interface PartyGameFinishPlacement extends PartyGamePlacement {

    Optional<Long> getFinishedAfter();
}
