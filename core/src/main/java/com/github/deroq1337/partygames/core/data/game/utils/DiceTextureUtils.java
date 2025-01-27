package com.github.deroq1337.partygames.core.data.game.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class DiceTextureUtils {

    public static void applyTextureToArmorStand(@NotNull ArmorStand armorStand, @NotNull String texture) {
        Optional.ofNullable(armorStand.getEquipment()).ifPresent(equipment -> {
            Optional.ofNullable(equipment.getHelmet()).ifPresent(helmet -> {
                Optional.ofNullable((SkullMeta) helmet.getItemMeta()).ifPresent(skullMeta -> {
                    applyTexture(skullMeta, texture);
                    helmet.setItemMeta(skullMeta);
                    equipment.setHelmet(helmet);
                });
            });
        });
    }

    private static void applyTexture(@NotNull SkullMeta skullMeta, @NotNull String texture) {
        SkinTexture.getUrlFromTexture(texture).ifPresent(url -> {
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), UUID.randomUUID().toString().substring(0, 16));
            profile.getTextures().setSkin(url);
            skullMeta.setOwnerProfile(profile);
        });
    }
}
