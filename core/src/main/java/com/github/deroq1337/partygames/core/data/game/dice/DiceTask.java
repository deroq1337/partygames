package com.github.deroq1337.partygames.core.data.game.dice;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DiceTask extends BukkitRunnable {

    private final @NotNull Dice dice;
    private final @NotNull Player player;

    @Override
    public void run() {
        ArmorStand armorStand = dice.spawn(player.getLocation());
        dice.hide(armorStand);

        dice.startAnimation(player);
        cancel();
    }
}