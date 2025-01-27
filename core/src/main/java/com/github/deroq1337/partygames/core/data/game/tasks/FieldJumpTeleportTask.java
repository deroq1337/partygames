package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.models.PartyGamesBoardField;
import com.github.deroq1337.partygames.core.data.game.config.MainConfig;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class FieldJumpTeleportTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull DefaultPartyGamesUser user;
    private final @NotNull Player player;
    private final @NotNull PartyGamesBoardField field;
    private final @NotNull Location fieldLocation;
    private final @NotNull MainConfig mainConfig;

    private boolean jumped = false;

    public FieldJumpTeleportTask(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull DefaultPartyGamesUser user, @NotNull Player player, @NotNull PartyGamesBoardField field) {
        this.game = game;
        this.user = user;
        this.player = player;
        this.field = field;
        this.fieldLocation = field.getLocation().toBukkitLocation();
        this.mainConfig = game.getMainConfig();
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        }

        if (jumped) {
            if (!isInAir(player)) {
                if (field.isEventField()) {
                    new FieldEventTask(game, player, field).start();
                    cancel();
                    return;
                }

                user.setLanded(true);
                cancel();
            }
        } else {
            double y = player.getLocation().getY();
            if (y > mainConfig.getFieldJumpTeleportHeight()) {
                double diffY = Math.abs(y - fieldLocation.getY());
                player.teleport(fieldLocation.clone().add(0, diffY, 0));
                this.jumped = true;
            }
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
