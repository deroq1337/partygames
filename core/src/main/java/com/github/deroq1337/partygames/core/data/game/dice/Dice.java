package com.github.deroq1337.partygames.core.data.game.dice;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceAnimation;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceDefaultAnimation;
import com.github.deroq1337.partygames.core.data.game.dice.animation.DiceRotatingAnimation;
import com.github.deroq1337.partygames.core.data.game.dice.config.DiceConfig;
import com.github.deroq1337.partygames.core.data.game.events.UserRollDiceEvent;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.utils.DiceArmorStandUtils;
import com.github.deroq1337.partygames.core.data.game.utils.DiceTextureUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Dice {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull DefaultPartyGamesUser user;
    private final @NotNull DiceConfig config;
    private final int min;
    private final int max;
    private final double headHeightOffset;

    private Optional<ArmorStand> armorStand = Optional.empty();
    private boolean rolling;
    private boolean rolled;

    public Dice(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull DefaultPartyGamesUser user, int min, int max, double headHeightOffset) {
        this.game = game;
        this.user = user;
        this.config = game.getDiceConfig();
        this.min = min;
        this.max = max;
        this.headHeightOffset = headHeightOffset;
    }

    public void initiateRoll() {
        this.rolling = true;
        user.getBukkitPlayer().ifPresent(player -> new DiceTask(game, this, player).start());
    }

    protected void spawn(@NotNull Player player) {
        ArmorStand armorStand = DiceArmorStandUtils.spawnArmorStand(player.getLocation(), config.getTexture());
        DiceArmorStandUtils.hideArmorStand(game, armorStand, player);
        this.armorStand = Optional.of(armorStand);
    }

    public void roll() {
        if (rolled) {
            return;
        }

        int numberOfEyes = ThreadLocalRandom.current().nextInt(min, max);
        user.setCurrentField(user.getCurrentField() + numberOfEyes);
        user.sendMessage("dice_rolled", numberOfEyes);

        armorStand.ifPresent(armorStand -> {
            DiceTextureUtils.applyTextureToArmorStand(armorStand, config.getTextures().get(numberOfEyes));

            user.getBukkitPlayer().ifPresent(player -> {
                DiceArmorStandUtils.teleportAboveHead(player, armorStand, config.getHeadHeightOffset());
                DiceArmorStandUtils.showArmorStand(game, armorStand);

                player.playSound(player.getLocation(), Sound.BLOCK_WOOD_BREAK, 1f, 1f);
            });
        });

        this.rolling = false;
        this.rolled = true;

        Bukkit.getPluginManager().callEvent(new UserRollDiceEvent(user, this));
    }

    public void destroy() {
        DiceArmorStandUtils.destroyArmorStand(armorStand);
        this.armorStand = Optional.empty();
    }

    protected void startAnimation(@NotNull Player player) {
        armorStand.ifPresent(armorStand -> {
            DiceAnimation animation = config.isRotatingDice()
                    ? new DiceRotatingAnimation(game, this, player, armorStand)
                    : new DiceDefaultAnimation(game, this, config, player, armorStand);
            animation.start();
        });
    }

    public void teleportAboveHead(@NotNull Player player) {
        armorStand.ifPresent(armorStand -> DiceArmorStandUtils.teleportAboveHead(player, armorStand, headHeightOffset));
    }

    public void teleportIntoView(@NotNull Player player) {
        armorStand.ifPresent(armorStand -> DiceArmorStandUtils.teleportIntoView(player, armorStand, config.getViewDistanceOffset()));
    }
}
