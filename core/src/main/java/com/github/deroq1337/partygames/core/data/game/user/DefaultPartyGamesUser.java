package com.github.deroq1337.partygames.core.data.game.user;

import com.github.deroq1337.partygames.api.game.PartyGamePlacement;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.tasks.FieldJumpTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class DefaultPartyGamesUser implements PartyGamesUser {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull UUID uuid;
    private final boolean alive;
    private final @NotNull List<Optional<PartyGamePlacement>> placements = new ArrayList<>();

    private @NotNull Locale locale = Locale.forLanguageTag("de-DE");
    private Optional<Dice> dice = Optional.empty();
    private Optional<Dice> extraDice = Optional.empty();
    private @NotNull Location lastLocation;
    private int currentField;
    private boolean landed;

    public DefaultPartyGamesUser(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull UUID uuid, boolean alive) {
        this.game = game;
        this.uuid = uuid;
        this.alive = alive;

        getBukkitPlayer().ifPresent(player -> setLastLocation(player.getLocation()));
    }

    @Override
    public void sendMessage(@NotNull String key, Object... params) {
        getBukkitPlayer().ifPresent(player -> player.sendMessage(getMessage(key, params)));
    }

    @Override
    public void sendTitle(@NotNull String key, Object... params) {
        getBukkitPlayer().ifPresent(player -> player.sendTitle(getMessage(key, params), null, 10, 70, 20));
    }

    @Override
    public @NotNull String getMessage(@NotNull String key, Object... params) {
        return ChatColor.translateAlternateColorCodes('&', MessageFormat.format(game.getLanguageManager().getMessage(locale, key), params));
    }

    public void jumpToField() {
        this.landed = false;

        getBukkitPlayer().ifPresent(player -> game.getBoard().flatMap(board -> board.getField(currentField)).ifPresent(field ->
                new FieldJumpTask(game, this, player, field).start()));
    }

    public int getFieldRanking() {
        return (int) game.getUserRegistry().getAliveUsers().stream()
                .filter(user -> user.getCurrentField() > currentField)
                .count() + 1;
    }

    public void initDices() {
        this.dice = Optional.of(new Dice(game, this, 1, 2, game.getDiceConfig().getHeadHeightOffset()));

        if (!placements.isEmpty()) {
            initExtraDice();
        }
    }

    private void initExtraDice() {
        placements.getLast().ifPresent(placement -> {
            int finalPlacement = placement.getPlacement();
            if (!(finalPlacement > 0 && finalPlacement < 4)) {
                return;
            }

            Optional.ofNullable(game.getDiceConfig().getExtraDices().get(finalPlacement)).ifPresent(extraDiceSetting -> {
                this.extraDice = Optional.of(new Dice(game, this, extraDiceSetting.getMin(), extraDiceSetting.getMax(),
                        game.getDiceConfig().getExtraDiceHeadHeightOffset()
                ));
            });
        });
    }

    public boolean hasDiceRolled() {
        return isMainDiceRolled() && isExtraDiceRolled();
    }

    private boolean isMainDiceRolled() {
        return dice.map(Dice::isRolled)
                .orElse(false);
    }

    private boolean isExtraDiceRolled() {
        return extraDice.map(Dice::isRolled)
                .orElse(true);
    }

    public void addPlacement(Optional<PartyGamePlacement> placement) {
        placements.add(placement);
    }

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }
}
