package com.github.deroq1337.partygames.core.data.game.dice;

import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DiceAnimationTask extends BukkitRunnable {

    private final @NotNull Dice dice;
    private final @NotNull DiceConfig config;
    private final @NotNull Player player;
    private final @NotNull ArmorStand armorStand;

    private double xAngle = 0;
    private double yAngle = 0;
    private int ticks = 0;

    public DiceAnimationTask(@NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand) {
        this.dice = dice;
        this.config = dice.getConfig();
        this.player = player;
        this.armorStand = armorStand;
    }

    @Override
    public void run() {
        if (!player.isOnline() || player.isDead() || armorStand.isDead()) {
            dice.destroy();
            cancel();
            return;
        }

        if (dice.isRolled()) {
            dice.teleportAboveHead(player, armorStand);
            return;
        }

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
