package com.github.deroq1337.partygames.api.game;

import com.github.deroq1337.partygames.api.config.YamlConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class PartyGameMap extends YamlConfig {

    private @NotNull String name;
    private @NotNull String world;

    public PartyGameMap(@NotNull File file) {
        super(file);
    }
}
