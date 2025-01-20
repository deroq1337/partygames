package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceAnimation;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceDefaultAnimation;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceRotatingAnimation;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.utils.SkinTexture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull DefaultPartyGamesUser user;

    @Getter(value = AccessLevel.PUBLIC)
    private final @NotNull DiceConfig config;

    private Optional<ArmorStand> armorStand = Optional.empty();

    private DiceAnimation currentAnimation;

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
        user.getBukkitPlayer().ifPresent(player -> {
            new DiceTask(game, this, player).runTaskLater(game.getPartyGames(), 5 * 20L);
        });
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

            stopDiceAnimation();

            int finalNumber = ThreadLocalRandom.current().nextInt(1, 7);
            Optional.ofNullable(armorStand.getEquipment()).ifPresent(equipment -> {
                Optional.ofNullable(equipment.getHelmet()).ifPresent(helmet -> {
                    setTexture(helmet, config.getTextures().get(finalNumber));
                    equipment.setHelmet(helmet);
                });
            });

            user.sendMessage("dice_rolled", finalNumber);
            user.goToField(finalNumber);
            this.rolled = true;
        });
    }

    public void startDiceAnimation(Player player) {
        armorStand.ifPresent(armorStand -> {
            if (currentAnimation != null) {
                currentAnimation.cancel();
            }

            if (config.isRollingDice()) {
                currentAnimation = new DiceRotatingAnimation(this, player, armorStand);
            } else {
                currentAnimation = new DiceDefaultAnimation(this, player, armorStand);
                currentAnimation.runTaskTimer(game.getPartyGames(), 0L, config.getAnimationSpeed());
                return;
            }

            currentAnimation.runTaskTimer(game.getPartyGames(), 0L, 1L);
        });
    }

    public void stopDiceAnimation() {
        if (currentAnimation != null) {
            currentAnimation.cancel();
            currentAnimation = null;
        }
    }

    protected @NotNull ArmorStand spawn(@NotNull Location location) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        setTexture(head, game.getDiceConfig().getTexture());

        armorStand.getEquipment().setHelmet(head);
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
        Location location = player.getLocation().add(
                player.getLocation().getDirection().multiply(config.getViewDistanceOffset()));
        armorStand.teleport(location);

        if (!config.isRollingDice()) {
            fixAngle(armorStand);
        }
    }

    public void setTexture(@NotNull ItemStack item, String texture) {
        Optional.ofNullable((SkullMeta) item.getItemMeta()).ifPresent(skullMeta -> {
            SkinTexture.getUrlFromTexture(texture).ifPresent(url -> {
                PlayerProfile playerProfile = Bukkit.createPlayerProfile(UUID.randomUUID(), UUID.randomUUID().toString().substring(0, 16));
                playerProfile.getTextures().setSkin(url);
                skullMeta.setOwnerProfile(playerProfile);
                item.setItemMeta(skullMeta);
            });
        });
    }
}
