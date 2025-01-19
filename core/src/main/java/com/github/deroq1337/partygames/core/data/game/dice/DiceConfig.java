package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.api.config.YamlConfig;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DiceConfig extends YamlConfig {

    private long rollTime = 7; // in seconds
    private double headHeightOffset = 1.5;
    private double viewDistanceOffset = 2;
    private double rotationSpeed = 0.5;
    private long animationSpeed = 10;
    private boolean rollingDice = true;
    private @NotNull String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU5ODk5ZmI5ZTNhOTY0NDZlZGJjZjU5ZDJiNDM5OTNlOThjMWU5ZWM3ZDg3ZDE5M2RjMzBlNTVhNzhlOTQxZSJ9fX0=";
    private @NotNull Map<Integer, String> textures = new HashMap<>();

    public DiceConfig(@NotNull File file) {
        super(file);

        textures.put(1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3R" +
                "leHR1cmUvNzMwZDBkNGQwZTM3OTc0MzhjYzM3M2JiNTY0YzNjN2I1ZTg0M2IzNjRkZTUyYzhhZGU2MWQ2NWIxOTk2MWM2In19fQ==");
        textures.put(2, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rl" +
                "eHR1cmUvNzBmM2E3ZjdjZmM0NGE2YzI2M2M5ZTUwZmM0NWEzYjQ2Zjk0NDExZTMxZjc3YmIwZWQwY2ExZDE0MTc3NDFkNCJ9fX0=");
        textures.put(3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3" +
                "RleHR1cmUvZThkODQyMzhhNTQ2MDMxNDM5Yjg2ZGYyZjI5YTAwZmM3YzY3NzU5YjkzMjIzOGEzY2E5ZWEwNmJlNWY1ODVmOSJ9fX0=");
        textures.put(4, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
                "HR1cmUvYmJlZTcyYjQ5NDVlNzY0OWE5MTA3N2QwMzM1MmU0NjUwZmYxNGE0ODc4OGU2Y2JmMjAzNDUwMzZjMDU0ZWQ3In19fQ==");
        textures.put(5, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
                "HR1cmUvNTZlMmNjMmU3OWNlMDQwNmRjNDhkZDBiYTBlMGJjOThkZGRkOTdkNDRkNjZhYWJkM2NhMjEzZjIzNmExOWFiZCJ9fX0=");
        textures.put(6, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleH" +
                "R1cmUvYWU2ZjA3Y2I5MjU4N2Y1NGNlYWU4MzIwM2Q0NTgyMDlmNDU1N2YzMDJmNDVhMmM3OGNlYzMxNzhmMWQ3ZjIxMiJ9fX0=");
    }
}
