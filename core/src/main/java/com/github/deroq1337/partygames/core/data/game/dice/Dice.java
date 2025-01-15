package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
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
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class Dice {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull PartyGamesUser user;

    @Getter(value = AccessLevel.PROTECTED)
    private final @NotNull DiceConfig config;

    private Optional<ArmorStand> armorStand;

    @Getter
    @Setter
    private boolean rolled;

    public Dice(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull PartyGamesUser user) {
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
            // int numberOfEyes = ThreadLocalRandom.current().nextInt(1, 6);
            int numberOfEyes = 1;
            Optional.ofNullable(armorStand.getEquipment()).ifPresent(equipment -> {
                Optional.ofNullable(equipment.getHelmet()).ifPresent(helmet -> {
                    setTexture(helmet, config.getTexture());
                    equipment.setHelmet(helmet);
                });
            });

            user.getBukkitPlayer().ifPresent(player -> {
                fixAngle(armorStand);
                teleportAboveHead(player, armorStand);
                show(armorStand);

                player.playSound(player.getLocation(), Sound.BLOCK_WOOD_BREAK, 1f, 1f);
            });

            user.sendMessage("dice_rolled", numberOfEyes);
            user.goToField(numberOfEyes);
            this.rolled = true;
        });
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

    protected void teleportIntoView(@NotNull Player player, @NotNull ArmorStand armorStand) {
        Location location = player.getLocation();
        Vector direction = location.getDirection();

        Vector offset = direction.multiply(config.getViewDistanceOffset());
        Location targetLocation = location.clone().add(offset);
        armorStand.teleport(targetLocation);
    }

    protected void teleportAboveHead(@NotNull Player player, @NotNull ArmorStand armorStand) {
        armorStand.teleport(player.getLocation().clone().add(0, config.getHeadHeightOffset(), 0));
    }

    private void fixAngle(@NotNull ArmorStand armorStand) {
        armorStand.setHeadPose(new EulerAngle(0, 0, 0));
    }

    private void setTexture(@NotNull ItemStack item, String texture) {
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
