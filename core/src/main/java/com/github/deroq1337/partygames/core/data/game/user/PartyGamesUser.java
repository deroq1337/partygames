package com.github.deroq1337.partygames.core.data.game.user;

import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    private final @NotNull PartyGamesGame game;
    private final @NotNull UUID uuid;
    private @NotNull Locale locale = Locale.forLanguageTag("de-DE");

    @Override
    public void sendMessage(@NotNull String key, Object... params) {
        getBukkitPlayer().ifPresent(player -> {
            player.sendMessage(getMessage(key, params));
        });
    }

    @Override
    public @NotNull String getMessage(@NotNull String key, Object... params) {
        return ChatColor.translateAlternateColorCodes('&', MessageFormat.format(game.getLanguageManager().getMessage(locale, key), params));
    }

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }
}
