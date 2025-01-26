package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.dice.config.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.utils.DiceTextureUtils;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class DiceDefaultAnimation extends DiceAnimation {

    public DiceDefaultAnimation(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull Dice dice, @NotNull DiceConfig config, @NotNull Player player,
                                @NotNull ArmorStand armorStand) {
        super(game, dice, player, armorStand, (long) config.getAnimationSpeed());
    }

    @Override
    public void animate() {
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 7);
        Optional.ofNullable(config.getTextures().get(randomNumber)).ifPresent(texture -> DiceTextureUtils.applyTextureToArmorStand(armorStand, texture));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }
}
