package com.github.deroq1337.partygames.testgame.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameStartingCountdown;
import com.github.deroq1337.partygames.game.base.states.PartyGameStartingState;
import com.github.deroq1337.partygames.testgame.countdowns.TestGameStartingCountdown;

import com.github.deroq1337.partygames.testgame.map.TestMap;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TestGameStartingState extends PartyGameStartingState<TestMap> {

    public TestGameStartingState(@NotNull PartyGame<TestMap, ? extends PartyGameConfig> partyGame) {
        super(partyGame);
    }

    @Override
    public void enter() {
        super.enter();
        partyGame.getUserRegistry().getAliveUsers().forEach(user -> {
            Optional.ofNullable(partyGame.getMap().getSpawnLocation()).ifPresent(spawnLocation -> {
                Optional.ofNullable(Bukkit.getPlayer(user.getUuid())).ifPresent(player -> {
                    player.teleport(spawnLocation.toBukkitLocation());
                });
            });
        });

        getCountdown().start();
    }

    @Override
    public void leave() {

    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.of(new TestGameRunningState(partyGame));
    }

    @Override
    public @NotNull PartyGameStartingCountdown initCountdown() {
        return new TestGameStartingCountdown(partyGame, this);
    }
}
