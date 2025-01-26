package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.dice.config.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public abstract class DiceAnimation extends BukkitRunnable {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    protected final @NotNull Dice dice;
    protected final @NotNull DiceConfig config;
    protected final @NotNull Player player;
    protected final @NotNull ArmorStand armorStand;
    private final long period;

    protected int ticks = 0;

    public DiceAnimation(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand, long period) {
        this.game = game;
        this.dice = dice;
        this.config = dice.getConfig();
        this.player = player;
        this.armorStand = armorStand;
        this.period = period;
    }

    public abstract void animate();

    public void start() {
        runTaskTimer(game.getPartyGames(), 0L, period);
    }

    @Override
    public void run() {
        if (!player.isOnline() || player.isDead() || armorStand.isDead()) {
            dice.destroy();
            cancel();
            return;
        }

        if (dice.isRolled()) {
            dice.teleportAboveHead(player);
            return;
        }

        if (ticks >= config.getRollTime() * 20L) {
            dice.roll();
            return;
        }

        animate();
        ticks++;
    }
}