package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DiceTask extends BukkitRunnable {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull Dice dice;
    private final @NotNull Player player;

    @Override
    public void run() {
        dice.spawn(player);
        dice.startAnimation(player);

        cancel();
    }

    public void start() {
        runTaskLater(game.getPartyGames(), 2 * 20L);
    }
}