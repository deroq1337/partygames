package com.github.deroq1337.partygames.core.data.game.dice;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.cubespace.Yamler.Config.YamlConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DiceConfig extends YamlConfig {

    private @NotNull Map<Integer, String> diceTextures = new HashMap<>();

    public DiceConfig(@NotNull File file) {
        this.CONFIG_FILE = file;

        diceTextures.put(1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3R" +
                "leHR1cmUvNzMwZDBkNGQwZTM3OTc0MzhjYzM3M2JiNTY0YzNjN2I1ZTg0M2IzNjRkZTUyYzhhZGU2MWQ2NWIxOTk2MWM2In19fQ==");
        diceTextures.put(2, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rl" +
                "eHR1cmUvNzBmM2E3ZjdjZmM0NGE2YzI2M2M5ZTUwZmM0NWEzYjQ2Zjk0NDExZTMxZjc3YmIwZWQwY2ExZDE0MTc3NDFkNCJ9fX0=");
        diceTextures.put(3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3" +
                "RleHR1cmUvZThkODQyMzhhNTQ2MDMxNDM5Yjg2ZGYyZjI5YTAwZmM3YzY3NzU5YjkzMjIzOGEzY2E5ZWEwNmJlNWY1ODVmOSJ9fX0=");
        diceTextures.put(4, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
                "HR1cmUvYmJlZTcyYjQ5NDVlNzY0OWE5MTA3N2QwMzM1MmU0NjUwZmYxNGE0ODc4OGU2Y2JmMjAzNDUwMzZjMDU0ZWQ3In19fQ==");
        diceTextures.put(5, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
                "HR1cmUvNTZlMmNjMmU3OWNlMDQwNmRjNDhkZDBiYTBlMGJjOThkZGRkOTdkNDRkNjZhYWJkM2NhMjEzZjIzNmExOWFiZCJ9fX0=");
        diceTextures.put(6, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleH" +
                "R1cmUvYWU2ZjA3Y2I5MjU4N2Y1NGNlYWU4MzIwM2Q0NTgyMDlmNDU1N2YzMDJmNDVhMmM3OGNlYzMxNzhmMWQ3ZjIxMiJ9fX0=");

        try {
            init();
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}