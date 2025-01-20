package com.github.deroq1337.partygames.core.data.game.dice.animation;

import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class DiceDefaultAnimation extends DiceAnimation {

    public DiceDefaultAnimation(@NotNull Dice dice, @NotNull Player player, @NotNull ArmorStand armorStand) {
        super(dice, player, armorStand);
    }

    @Override
    public void animate() {
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 7);

        if (armorStand.getEquipment() != null) {
            EntityEquipment equipment = armorStand.getEquipment();

            if (equipment.getHelmet() != null && equipment.getHelmet().getType() == Material.PLAYER_HEAD) {
                ItemStack helmet = equipment.getHelmet();

                dice.setTexture(helmet, config.getTextures().get(randomNumber));
                equipment.setHelmet(helmet);
            }

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        }
    }
}
