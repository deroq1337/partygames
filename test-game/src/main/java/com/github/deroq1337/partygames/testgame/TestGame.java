package com.github.deroq1337.partygames.testgame;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.api.user.UserRegistry;
import com.github.deroq1337.partygames.testgame.map.TestMap;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

public class TestGame extends PartyGame<TestMap> {

    public TestGame(@NotNull File directory, @NotNull UserRegistry<? extends User> userRegistry, @NotNull TestMap map) {
        super(directory, userRegistry, map);
    }

    @Override
    public void onLoad() {
        userRegistry.getAliveUsers().forEach(user -> {
            Optional.ofNullable(map.getSpawnLocation()).ifPresent(spawnLocation -> {
                Optional.ofNullable(Bukkit.getPlayer(user.getUuid())).ifPresent(player -> {
                    player.teleport(spawnLocation.toBukkitLocation());
                });
            });
        });
    }

    @Override
    public void onUnload() {

    }

    @Override
    public @NotNull PartyGameState getState() {
        return null;
    }
}
