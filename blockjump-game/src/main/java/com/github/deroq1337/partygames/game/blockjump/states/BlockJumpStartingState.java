package com.github.deroq1337.partygames.game.blockjump.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameStartingCountdown;
import com.github.deroq1337.partygames.game.base.states.PartyGameStartingState;
import com.github.deroq1337.partygames.game.blockjump.countdowns.BlockJumpStartingCountdown;

import com.github.deroq1337.partygames.game.blockjump.map.BlockJumpMap;
import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlockJumpStartingState extends PartyGameStartingState<BlockJumpMap, BlockJumpUser> {

    public BlockJumpStartingState(@NotNull PartyGame<BlockJumpMap, ? extends PartyGameConfig, BlockJumpUser> partyGame) {
        super(partyGame);
    }

    @Override
    public void enter() {
        super.enter();

        partyGame.getUsers().forEach(user -> {
            partyGame.getScoreboard().setScoreboard(user);

            Optional.ofNullable(partyGame.getMap().getSpawnLocation()).ifPresent(spawnLocation ->
                    Optional.ofNullable(Bukkit.getPlayer(user.getPartyGamesUser().getUuid())).ifPresent(player ->
                            player.teleport(spawnLocation.toBukkitLocation())));
        });

        getCountdown().start();
    }

    @Override
    public void leave() {

    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.of(new BlockJumpRunningState(partyGame));
    }

    @Override
    public @NotNull PartyGameStartingCountdown initCountdown() {
        return new BlockJumpStartingCountdown(partyGame, this);
    }
}
