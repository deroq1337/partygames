package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class FieldJumpTeleportTask extends BukkitRunnable {

    private final @NotNull Player player;
    private final @NotNull Location fieldLocation;
    private final @NotNull MainConfig mainConfig;

    @Override
    public void run() {
        double y = player.getLocation().getY();
        if (y > mainConfig.getFieldJumpTeleportHeight()) {
            player.teleport(fieldLocation.clone().add(0, Math.abs(y - fieldLocation.getY()), 0));
            cancel();
        }
    }
}
