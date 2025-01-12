package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DiceTask extends BukkitRunnable {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Dice dice;
    private final @NotNull Player player;

    @Override
    public void run() {
        ArmorStand armorStand = dice.spawn(player.getLocation());
        dice.hide(armorStand);

        new DiceAnimationTask(dice, player, armorStand).runTaskTimer(game.getPartyGames(), 0L, 1L);
        cancel();
    }
}