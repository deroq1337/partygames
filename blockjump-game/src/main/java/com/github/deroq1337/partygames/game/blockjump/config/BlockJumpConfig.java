package com.github.deroq1337.partygames.game.blockjump.config;

import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockJumpConfig extends PartyGameConfig {

    public BlockJumpConfig(@NotNull File file) {
        this.file = file;
    }
}
