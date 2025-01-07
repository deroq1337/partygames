package com.github.deroq1337.partygames.core.data.game.board.models;

import com.github.deroq1337.partygames.core.data.game.board.serialization.MapDirectedLocation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.cubespace.Yamler.Config.YamlConfig;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PartyGamesBoardField extends YamlConfig {

    private final @NotNull MapDirectedLocation location;
    private final boolean eventField;
}
