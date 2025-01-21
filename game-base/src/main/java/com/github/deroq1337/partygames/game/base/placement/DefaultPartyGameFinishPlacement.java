package com.github.deroq1337.partygames.game.base.placement;

import com.github.deroq1337.partygames.api.game.PartyGameFinishPlacement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaultPartyGameFinishPlacement extends DefaultPartyGamePlacement implements PartyGameFinishPlacement {

    private final Optional<Long> finishedAfter;

    public DefaultPartyGameFinishPlacement(@NotNull UUID uuid, int placement, Optional<Long> finishedAfter) {
        super(uuid, placement);
        this.finishedAfter = finishedAfter;
    }
}
