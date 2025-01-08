package com.github.deroq1337.partygames.core.data.game.dice;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.cubespace.Yamler.Config.Path;
import net.cubespace.Yamler.Config.YamlConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DiceConfig extends YamlConfig {

    @Path("dice.texture.one")
    private @NotNull String diceOneTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3R" +
            "leHR1cmUvNzMwZDBkNGQwZTM3OTc0MzhjYzM3M2JiNTY0YzNjN2I1ZTg0M2IzNjRkZTUyYzhhZGU2MWQ2NWIxOTk2MWM2In19fQ==";

    @Path("dice.texture.two")
    private @NotNull String diceTwoTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rl" +
            "eHR1cmUvNzBmM2E3ZjdjZmM0NGE2YzI2M2M5ZTUwZmM0NWEzYjQ2Zjk0NDExZTMxZjc3YmIwZWQwY2ExZDE0MTc3NDFkNCJ9fX0=";

    @Path("dice.texture.three")
    private @NotNull String diceThreeTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3" +
            "RleHR1cmUvZThkODQyMzhhNTQ2MDMxNDM5Yjg2ZGYyZjI5YTAwZmM3YzY3NzU5YjkzMjIzOGEzY2E5ZWEwNmJlNWY1ODVmOSJ9fX0=";

    @Path("dice.texture.four")
    private @NotNull String diceFourTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
            "HR1cmUvYmJlZTcyYjQ5NDVlNzY0OWE5MTA3N2QwMzM1MmU0NjUwZmYxNGE0ODc4OGU2Y2JmMjAzNDUwMzZjMDU0ZWQ3In19fQ==";

    @Path("dice.texture.five")
    private @NotNull String diceFiveTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
            "HR1cmUvNTZlMmNjMmU3OWNlMDQwNmRjNDhkZDBiYTBlMGJjOThkZGRkOTdkNDRkNjZhYWJkM2NhMjEzZjIzNmExOWFiZCJ9fX0=";

    @Path("dice.texture.six")
    private @NotNull String diceSixTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleH" +
            "R1cmUvYWU2ZjA3Y2I5MjU4N2Y1NGNlYWU4MzIwM2Q0NTgyMDlmNDU1N2YzMDJmNDVhMmM3OGNlYzMxNzhmMWQ3ZjIxMiJ9fX0=";

    public DiceConfig(@NotNull File file) {
        this.CONFIG_FILE = file;

        try {
            init();
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
