package com.github.deroq1337.partygames.game.base.config;

import com.github.deroq1337.partygames.api.config.YamlConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class PartyGameConfig extends YamlConfig {

    private int startDelay = 10;
    private @NotNull List<Integer> startingSpecialTicks = Arrays.asList(10, 5, 4, 3, 2, 1);
    private boolean canMoveWhileStarting = true;
    private int maxFinishers = 3;
    private int gameDuration = 3 * 60;
    private @NotNull List<Integer> specialTicks = Arrays.asList(90, 60, 30, 10, 5, 4, 3, 2, 1);

    public PartyGameConfig(@NotNull File file) {
        this.file = file;
    }
}
