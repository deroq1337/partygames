package com.github.deroq1337.partygames.game.base.placement;

import com.github.deroq1337.partygames.api.game.PartyGamePlacement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class DefaultPartyGamePlacement implements PartyGamePlacement {

    private final @NotNull UUID uuid;
    private final int placement;
}
