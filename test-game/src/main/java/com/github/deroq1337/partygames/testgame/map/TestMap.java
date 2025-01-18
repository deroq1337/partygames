package com.github.deroq1337.partygames.testgame.map;

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
public class TestMap extends PartyGameMap {

    private @Nullable MapDirectedLocation spawnLocation;

    public TestMap(@NotNull File file) {
        super(file);
    }
}
