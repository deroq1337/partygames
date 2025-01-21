package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class DiceRotatingAnimation extends DiceAnimation {
    private double xAngle = 0;
    private double yAngle = 0;

    public DiceRotatingAnimation(@NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand) {
        super(dice, player, armorStand);
    }

    @Override
    public void animate() {
        xAngle += config.getAnimationSpeed();
        yAngle += config.getAnimationSpeed();
        armorStand.setHeadPose(new EulerAngle(xAngle, yAngle, 0));

        dice.teleportIntoView(player, armorStand);

        if (ticks % 4 == 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_WOOD_STEP, 1f, 1f);
        }
    }
}
