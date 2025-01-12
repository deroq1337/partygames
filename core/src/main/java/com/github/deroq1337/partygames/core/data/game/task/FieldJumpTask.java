package com.github.deroq1337.partygames.core.data.game.task;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class FieldJumpTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Player player;
    private final @NotNull Location fieldLocation;
    private final @NotNull MainConfig mainConfig;

    public FieldJumpTask(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull Player player, @NotNull Location fieldLocation) {
        this.game = game;
        this.player = player;
        this.fieldLocation = fieldLocation;
        this.mainConfig = game.getMainConfig();
    }

    @Override
    public void run() {
        player.setVelocity(new Vector(0, mainConfig.getFieldJumpVectorY(), 0));
        player.setFallDistance(0f);

        new FieldJumpTeleportTask(game, player, fieldLocation, mainConfig).start();
        cancel();
    }

    @Override
    public void start() {
        runTaskLater(game.getPartyGames(), 2 * 20L);
    }
}
