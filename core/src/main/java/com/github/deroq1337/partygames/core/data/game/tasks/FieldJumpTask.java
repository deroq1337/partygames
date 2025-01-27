package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.models.PartyGamesBoardField;
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
    private final @NotNull PartyGamesBoardField field;
    private final @NotNull MainConfig mainConfig;

    public FieldJumpTask(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull DefaultPartyGamesUser user, @NotNull Player player,
                         @NotNull PartyGamesBoardField field) {
        this.game = game;
        this.user = user;
        this.player = player;
        this.field = field;
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

        new FieldJumpTeleportTask(game, user,  player, field).start();
        cancel();
    }

    @Override
    public void start() {
        runTaskLater(game.getPartyGames(), 2 * 20L);
    }
}
