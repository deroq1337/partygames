package com.github.deroq1337.partygames.core.data.game.config;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MainConfig extends YamlConfig {
    
    private int fieldJumpVectorY = 12;
    private double fieldJumpTeleportHeight = 140; // y coordinate

    public MainConfig(@NotNull File file) {
        this.file = file;
    }
}
