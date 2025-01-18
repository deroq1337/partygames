package com.github.deroq1337.partygames.game.base.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class PartyGameRunningState implements PartyGameState {

    private final @NotNull PartyGame<?, ?, ?> partyGame;

    @Override
    public void enter() {
        partyGame.setCurrentState(this);
        partyGame.getUserRegistry().getAliveUsers().forEach(user -> {
            Optional.ofNullable(Bukkit.getPlayer(user.getUuid())).ifPresent(player -> {
                player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN, 1f, 1f);
            });
        });
    }

    @Override
    public void leave() {

    }
}
