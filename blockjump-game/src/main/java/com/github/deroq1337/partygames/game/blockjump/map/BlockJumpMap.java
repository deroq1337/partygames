package com.github.deroq1337.partygames.game.blockjump.map;

import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import com.github.deroq1337.partygames.api.game.PartyGameMap;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockJumpMap extends PartyGameMap {

    private @Nullable MapDirectedLocation spawnLocation;
    private int goalBlocks;

    public BlockJumpMap(@NotNull File file) {
        super(file);
    }
}
