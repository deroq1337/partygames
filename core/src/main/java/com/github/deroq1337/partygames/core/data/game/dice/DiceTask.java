package com.github.deroq1337.partygames.core.data.game.dice;

import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DiceTask extends BukkitRunnable {

    private static final double ROTATION_SPEED = 0.3;

    private final @NotNull Dice dice;
    private final @NotNull Player player;
    private final @NotNull ArmorStand armorStand;

    private double xAngle = 0;
    private double yAngle = 0;
    private int ticks = 0;

    @Override
    public void run() {
        if (!player.isOnline() || armorStand.isDead()) {
            dice.destroy(armorStand);
            cancel();
            return;
        }

        if (dice.isRolled()) {
            dice.teleportAboveHead(player, armorStand);
            return;
        }

        if (ticks >= dice.getConfig().getDiceRollTime() * 20L) {
            dice.roll();
            cancel();
            return;
        }

        xAngle += ROTATION_SPEED;
        yAngle += ROTATION_SPEED;
        armorStand.setHeadPose(new EulerAngle(xAngle, yAngle, 0));

        dice.teleportIntoView(player, armorStand);
        if (ticks % 4 == 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_WOOD_STEP, 1f, 1f);
        }

        ticks++;
    }
}
