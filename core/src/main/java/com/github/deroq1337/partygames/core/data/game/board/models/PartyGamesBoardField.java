package com.github.deroq1337.partygames.core.data.game.board.models;

import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PartyGamesBoardField {

    private @NotNull MapDirectedLocation location;
    private boolean eventField;
}
