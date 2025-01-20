package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class DiceAnimationTask extends DiceAnimation {

    private double xAngle = 0;
    private double yAngle = 0;

    public DiceAnimationTask(@NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand) {
        super(dice, player, armorStand);
    }

    @Override
    public void animate() {
        if (ticks >= config.getRollTime() * 20L) {
            dice.roll();
            cancel();
            return;
        }

        xAngle += config.getRotationSpeed();
        yAngle += config.getRotationSpeed();
        armorStand.setHeadPose(new EulerAngle(xAngle, yAngle, 0));

        dice.teleportIntoView(player, armorStand);
        ticks++;
    }
}