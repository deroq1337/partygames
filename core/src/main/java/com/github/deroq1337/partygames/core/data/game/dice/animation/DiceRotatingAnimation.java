package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.utils.DiceArmorStandUtils;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class DiceRotatingAnimation extends DiceAnimation {
    private double xAngle = 0;
    private double yAngle = 0;

    public DiceRotatingAnimation(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand) {
        super(game, dice, player, armorStand, 1L);
    }

    @Override
    public void animate() {
        xAngle += config.getAnimationSpeed();
        yAngle += config.getAnimationSpeed();
        armorStand.setHeadPose(new EulerAngle(xAngle, yAngle, 0));

        dice.teleportIntoView(player);

        if (ticks % 4 == 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_WOOD_STEP, 1f, 1f);
        }
    }
}
