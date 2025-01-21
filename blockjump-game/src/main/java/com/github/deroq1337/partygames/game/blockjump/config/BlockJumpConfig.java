package com.github.deroq1337.partygames.game.blockjump.config;

import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockJumpConfig extends PartyGameConfig {

    private @NotNull List<List<Integer>> jumps = Arrays.asList(
            Arrays.asList(4, 1, 0),
            Arrays.asList(-4, 1, 0),
            Arrays.asList(0, 1, 4),
            Arrays.asList(0, 1, -4),
            Arrays.asList(4, 0, 0),
            Arrays.asList(-4, 0, 0),
            Arrays.asList(0, 0, 4),
            Arrays.asList(0, 0, -4),
            Arrays.asList(3, 1, 0),
            Arrays.asList(-3, 1, 0),
            Arrays.asList(0, 1, -3),
            Arrays.asList(0, 1, 3)
    );

    public BlockJumpConfig(@NotNull File file) {
        this.file = file;
    }
}
