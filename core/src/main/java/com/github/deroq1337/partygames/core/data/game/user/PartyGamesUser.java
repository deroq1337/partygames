package com.github.deroq1337.partygames.core.data.game.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Getter @Setter
@RequiredArgsConstructor
public class PartyGamesUser {

    private final @NotNull UUID uuid;
    private @NotNull Locale locale = Locale.forLanguageTag("de-DE");

    public void sendMessage(@NotNull String key, Object... params){
        getBukkitPlayer().ifPresent(player -> {
            player.sendMessage(getMessage(key, params));
        });
    }

    public @NotNull String getMessage(@NotNull String key, Object... params){
        return "";
    }

    public Optional<Player> getBukkitPlayer(){
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }
}
