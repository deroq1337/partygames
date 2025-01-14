package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class FieldJumpTeleportTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull PartyGamesUser user;
    private final @NotNull Player player;
    private final @NotNull Location fieldLocation;
    private final @NotNull MainConfig mainConfig;

    private boolean jumped = false;

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        }

        if (jumped) {
            if (!isInAir(player)) {
                user.setLanded(true);
                cancel();
            }
            return;
        }

        double y = player.getLocation().getY();
        if (y > mainConfig.getFieldJumpTeleportHeight()) {
            double diffY = Math.abs(y - fieldLocation.getY());
            player.teleport(fieldLocation.clone().add(0, diffY, 0));
            this.jumped = true;
        }
    }

    @Override
    public void start() {
        runTaskTimer(game.getPartyGames(), 0L, 1L);
    }

    private boolean isInAir(@NotNull Player player) {
        return player.getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.AIR;
    }
}
