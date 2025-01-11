package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.utils.SkinTexture;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DiceTest {

    private final @NotNull PartyGamesGame game;
    private final @NotNull Player player;
    private Optional<ArmorStand> armorStand;

    public void test() {
        spawnDice(player.getLocation());

        new BukkitRunnable() {
            final double speed = 0.3;
            double xAngle = 0;
            double yAngle = 0;
            int ticks;

            @Override
            public void run() {
                xAngle += speed;
                yAngle += speed;

                armorStand.ifPresent(as -> {
                    as.setHeadPose(new EulerAngle(
                            xAngle,
                            yAngle,
                            0
                    ));
                });

                if (ticks % 4 == 0) {
                    player.playSound(player.getLocation(), Sound.BLOCK_WOOD_STEP, 1f, 1f);
                }

                ticks++;
            }
        }.runTaskTimer(game.getPartyGames(), 0L, 1L);
    }

    private void spawnDice(Location location) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        final PlayerProfile playerProfile = Bukkit.createPlayerProfile(
                UUID.randomUUID(),
                UUID.randomUUID().toString().substring(0, 16)
        );

        String s = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU5ODk5ZmI5ZTNhOTY0NDZlZGJjZjU5ZDJiNDM5OTNlOThjMWU5ZWM3ZDg3ZDE5M2RjMzBlNTVhNzhlOTQxZSJ9fX0=";
        SkinTexture.getUrlFromTexture(s).ifPresent(url -> {
            System.out.println("present : " + url.toString());
            playerProfile.getTextures().setSkin(url);
            skullMeta.setOwnerProfile(playerProfile);
            head.setItemMeta(skullMeta);
        });

        armorStand.getEquipment().setHelmet(head);
        this.armorStand = Optional.of(armorStand);
    }
}
