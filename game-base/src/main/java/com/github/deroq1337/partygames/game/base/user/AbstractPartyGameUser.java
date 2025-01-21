package com.github.deroq1337.partygames.game.base.user;

import com.github.deroq1337.partygames.api.user.PartyGameUser;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class AbstractPartyGameUser implements PartyGameUser {

    private final @NotNull PartyGamesUser partyGamesUser;
    private int value;
    private Optional<Integer> placement = Optional.empty();

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(partyGamesUser.getUuid()));
    }

    public void incrementValue() {
        value++;
    }
}
