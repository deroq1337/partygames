package com.github.deroq1337.partygames.core.data.game.models;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameManifest;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CurrentGame {

    private final @NotNull PartyGame<?, ?> partyGame;
    private final @NotNull PartyGameManifest manifest;
}
