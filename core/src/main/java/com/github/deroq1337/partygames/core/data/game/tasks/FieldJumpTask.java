package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class FieldJumpTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull DefaultPartyGamesUser user;
    private final @NotNull Player player;
    private final @NotNull Location fieldLocation;
    private final @NotNull MainConfig mainConfig;

    public FieldJumpTask(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull DefaultPartyGamesUser user, @NotNull Player player, @NotNull Location fieldLocation) {
        this.game = game;
        this.user = user;
        this.player = player;
        this.fieldLocation = fieldLocation;
        this.mainConfig = game.getMainConfig();
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        }

        if (player.isFlying()) {
            player.setFlying(false);
        }

        player.setVelocity(new Vector(0, mainConfig.getFieldJumpVectorY(), 0));
        player.setFallDistance(0f);

        new FieldJumpTeleportTask(game, user,  player, fieldLocation, mainConfig).start();
        cancel();
    }

    @Override
    public void start() {
        runTaskLater(game.getPartyGames(), 2 * 20L);
    }
}
