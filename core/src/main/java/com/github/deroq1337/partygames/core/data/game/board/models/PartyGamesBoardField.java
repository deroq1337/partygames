package com.github.deroq1337.partygames.core.data.game.board.models;

import com.github.deroq1337.partygames.core.data.game.board.serialization.MapDirectedLocation;
import lombok.*;
import net.cubespace.Yamler.Config.YamlConfig;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PartyGamesBoardField extends YamlConfig {

    private @NotNull MapDirectedLocation location;
    private boolean eventField;
}
