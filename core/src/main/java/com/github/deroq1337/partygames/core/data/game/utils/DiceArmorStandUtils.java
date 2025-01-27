package com.github.deroq1337.partygames.core.data.game.utils;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DiceArmorStandUtils {

    public static ArmorStand spawnArmorStand(@NotNull Location location, @NotNull String texture) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        armorStand.getEquipment().setHelmet(head);
        DiceTextureUtils.applyTextureToArmorStand(armorStand, texture);

        return armorStand;
    }

    public static void teleportAboveHead(@NotNull Player player, @NotNull ArmorStand armorStand, double offset) {
        armorStand.teleport(player.getLocation().clone().add(0, offset, 0));
    }

    public static void showArmorStand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull ArmorStand armorStand) {
        Bukkit.getOnlinePlayers().forEach(player -> player.showEntity(game.getPartyGames(), armorStand));
    }

    public static void hideArmorStand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull ArmorStand armorStand, @NotNull Player owner) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.getUniqueId().equals(owner.getUniqueId()))
                .forEach(player -> player.hideEntity(game.getPartyGames(), armorStand));
    }

    public static void destroyArmorStand(@NotNull Optional<ArmorStand> armorStand) {
        armorStand.ifPresent(stand -> {
            stand.setHealth(0);
            stand.remove();
        });
    }

    public static void teleportIntoView(@NotNull Player player, @NotNull ArmorStand armorStand, double offset) {
        Location playerLocation = player.getLocation();
        Location targetLocation = playerLocation.clone().add(playerLocation.getDirection().multiply(offset));
        armorStand.teleport(targetLocation);
    }

    public static void fixAngle(@NotNull ArmorStand armorStand) {
        armorStand.setHeadPose(new EulerAngle(0, 0, 0));
    }
}

