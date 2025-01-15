package com.github.deroq1337.partygames.core.data.game.user;

import com.github.deroq1337.partygames.api.user.User;
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
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class PartyGamesUser implements User {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull UUID uuid;
    private final boolean alive;

    private @NotNull Locale locale = Locale.forLanguageTag("de-DE");
    private @NotNull Location lastLocation;
    private int currentField;
    private Optional<Dice> dice;
    private boolean landed;

    public PartyGamesUser(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull UUID uuid, boolean alive) {
        this.game = game;
        this.uuid = uuid;
        this.alive = alive;

        getBukkitPlayer().ifPresent(player -> setLastLocation(player.getLocation()));
    }

    @Override
    public void sendMessage(@NotNull String key, Object... params) {
        getBukkitPlayer().ifPresent(player -> {
            player.sendMessage(getMessage(key, params));
        });
    }

    @Override
    public void sendTitle(@NotNull String key, Object... params) {
        getBukkitPlayer().ifPresent(player -> {
            player.sendTitle(getMessage(key, params), null, 10, 70, 20);
        });
    }

    @Override
    public @NotNull String getMessage(@NotNull String key, Object... params) {
        return ChatColor.translateAlternateColorCodes('&', MessageFormat.format(game.getLanguageManager().getMessage(locale, key), params));
    }

    public void goToField(int numberOfEyes) {
        this.landed = false;
        this.currentField += numberOfEyes;

        getBukkitPlayer().ifPresent(player -> {
            game.getBoard().flatMap(board -> board.getField(currentField)).ifPresent(field -> {
                Location fieldLocation = field.getLocation().toBukkitLocation();
                new FieldJumpTask(game, this, player, fieldLocation).start();
            });
        });
    }

    public int getFieldRanking() {
        return (int) game.getUserRegistry().getAliveUsers().stream()
                .filter(user -> user.getCurrentField() > currentField)
                .count() + 1;
    }

    public void initDice() {
        this.dice = Optional.of(new Dice(game, this));
    }

    public boolean hasDiceRolled() {
        return dice
                .map(Dice::isRolled)
                .orElse(false);
    }

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }
}
