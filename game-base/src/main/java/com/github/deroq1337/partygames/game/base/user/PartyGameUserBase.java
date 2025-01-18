package com.github.deroq1337.partygames.game.base.user;

import com.github.deroq1337.partygames.api.user.PartyGameUser;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public abstract class PartyGameUserBase implements PartyGameUser {

    private final @NotNull PartyGamesUser partyGamesUser;
    private int value;

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(partyGamesUser.getUuid()));
    }
}
