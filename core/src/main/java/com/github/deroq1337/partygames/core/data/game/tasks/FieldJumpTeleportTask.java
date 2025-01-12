package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class FieldJumpTeleportTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Player player;
    private final @NotNull Location fieldLocation;
    private final @NotNull MainConfig mainConfig;

    @Override
    public void run() {
        double y = player.getLocation().getY();
        if (y > mainConfig.getFieldJumpTeleportHeight()) {
            int diffY = Math.abs(y - fieldLocation.getY());
            player.teleport(fieldLocation.clone().add(0, diffY, 0));
            cancel();
        }
    }

    @Override
    public void start() {
        runTaskTimer(game.getPartyGames(), 0L, 1L);
    }
}
