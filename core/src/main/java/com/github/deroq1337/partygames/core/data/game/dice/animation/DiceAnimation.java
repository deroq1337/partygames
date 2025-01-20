package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.dice.DiceConfig;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public abstract class DiceAnimation extends BukkitRunnable {
    protected final @NotNull Dice dice;
    protected final @NotNull DiceConfig config;
    protected final @NotNull Player player;
    protected final @NotNull ArmorStand armorStand;

    protected int ticks = 0;

    public DiceAnimation(@NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand) {
        this.dice = dice;
        this.config = dice.getConfig();
        this.player = player;
        this.armorStand = armorStand;
    }

    public abstract void animate();

    @Override
    public void run() {
        if (!player.isOnline() || player.isDead() || armorStand.isDead()) {
            dice.destroy();
            cancel();
            return;
        }

        if (dice.isRolled()) {
            dice.teleportAboveHead(player, armorStand);
            cancel();
            return;
        }

        if (ticks >= config.getRollTime() * 20L) {
            dice.roll();
            cancel();
            return;
        }

        animate();
        ticks++;
    }
}