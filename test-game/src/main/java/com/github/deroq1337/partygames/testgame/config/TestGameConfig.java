package com.github.deroq1337.partygames.testgame.config;

import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class TestGameConfig extends PartyGameConfig {

    public TestGameConfig(@NotNull File file) {
        this.file = file;
    }
}
