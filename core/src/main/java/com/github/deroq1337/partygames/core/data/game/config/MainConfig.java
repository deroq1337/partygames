package com.github.deroq1337.partygames.core.data.game.config;

import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MainConfig extends YamlConfig {

    private int minPlayers = 1;
    private @Nullable MapDirectedLocation spawnLocation;
    private int fieldJumpVectorY = 12;
    private double fieldJumpTeleportHeight = 140;

    public MainConfig(@NotNull File file) {
        this.file = file;
    }
}
