package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceDefaultAnimation;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceRotatingAnimation;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.utils.SkinTexture;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull DefaultPartyGamesUser user;

    @Getter
    private final @NotNull DiceConfig config;

    private Optional<ArmorStand> armorStand = Optional.empty();

    @Getter
    @Setter
    private boolean rolled;

    public Dice(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull DefaultPartyGamesUser user) {
        this.game = game;
        this.user = user;
        this.config = game.getDiceConfig();

        init();
    }

    public void init() {
        user.getBukkitPlayer().ifPresent(player ->
                new DiceTask(this, player).runTaskLater(game.getPartyGames(), 5 * 20L));
    }

    public void roll() {
        if (rolled) {
            return;
        }

        armorStand.ifPresent(armorStand -> {
            user.getBukkitPlayer().ifPresent(player -> {
                fixAngle(armorStand);
                teleportAboveHead(player, armorStand);
                show(armorStand);

                player.playSound(player.getLocation(), Sound.BLOCK_WOOD_BREAK, 1f, 1f);
            });

            int finalNumber = ThreadLocalRandom.current().nextInt(1, 7);
            Optional.ofNullable(config.getTextures().get(finalNumber)).ifPresent(texture -> setTexture(armorStand, texture));

            user.sendMessage("dice_rolled", finalNumber);
            user.goToField(finalNumber);
            this.rolled = true;
        });
    }

    public void startAnimation(@NotNull Player player) {
        armorStand.ifPresent(armorStand -> {
            if (config.isRollingDice()) {
                new DiceRotatingAnimation(this, player, armorStand).runTaskTimer(game.getPartyGames(), 0L, 1L);
            } else {
                new DiceDefaultAnimation(this, player, armorStand).runTaskTimer(game.getPartyGames(), 0L, config.getAnimationSpeed());
            }
        });
    }

    protected @NotNull ArmorStand spawn(@NotNull Location location) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        setTexture(armorStand, game.getDiceConfig().getTexture());

        this.armorStand = Optional.of(armorStand);
        return armorStand;
    }

    public void destroy() {
        armorStand.ifPresent(armorStand -> {
            armorStand.setHealth(0);
            armorStand.remove();
            user.setDice(Optional.empty());
        });
        this.armorStand = Optional.empty();
    }

    private void show(@NotNull ArmorStand armorStand) {
        Bukkit.getOnlinePlayers().forEach(player -> player.showEntity(game.getPartyGames(), armorStand));
    }

    protected void hide(@NotNull ArmorStand armorStand) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.getUniqueId().equals(user.getUuid()))
                .forEach(player -> player.hideEntity(game.getPartyGames(), armorStand));
    }

    public void teleportAboveHead(@NotNull Player player, @NotNull ArmorStand armorStand) {
        armorStand.teleport(player.getLocation().clone().add(0, config.getHeadHeightOffset(), 0));

        if (!config.isRollingDice()) {
            fixAngle(armorStand);
        }
    }

    private void fixAngle(@NotNull ArmorStand armorStand) {
        armorStand.setHeadPose(new EulerAngle(0, config.isRollingDice() ? 0 : Math.toRadians(0), 0));
    }

    public void teleportIntoView(@NotNull Player player, @NotNull ArmorStand armorStand) {
        Location playerLocation = player.getLocation();
        Location targetLocation = playerLocation.clone().add(playerLocation.getDirection().multiply(config.getViewDistanceOffset()));
        armorStand.teleport(targetLocation);
    }

    public void setTexture(@NotNull ArmorStand armorStand, @NotNull String texture) {
        Optional.ofNullable(armorStand.getEquipment()).ifPresent(equipment -> {
            Optional.ofNullable(equipment.getHelmet()).ifPresent(helmet -> {
                Optional.ofNullable((SkullMeta) helmet.getItemMeta()).ifPresent(skullMeta -> {
                    SkinTexture.getUrlFromTexture(texture).ifPresent(url -> {
                        PlayerProfile playerProfile = Bukkit.createPlayerProfile(UUID.randomUUID(), UUID.randomUUID().toString().substring(0, 16));
                        playerProfile.getTextures().setSkin(url);
                        skullMeta.setOwnerProfile(playerProfile);
                        helmet.setItemMeta(skullMeta);

                        equipment.setHelmet(helmet);
                    });
                });
            });
        });

    }
}
