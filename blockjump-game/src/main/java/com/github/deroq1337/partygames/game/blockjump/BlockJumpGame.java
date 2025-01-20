package com.github.deroq1337.partygames.game.blockjump;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.PartyGameState;
import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.api.user.PartyGamesUserRegistry;
import com.github.deroq1337.partygames.game.base.PartyGameBase;
import com.github.deroq1337.partygames.game.blockjump.config.BlockJumpConfig;
import com.github.deroq1337.partygames.game.blockjump.listeners.PlayerMoveListener;
import com.github.deroq1337.partygames.game.blockjump.map.BlockJumpMap;

import com.github.deroq1337.partygames.game.blockjump.scoreboard.BlockJumpScoreboard;
import com.github.deroq1337.partygames.game.blockjump.states.BlockJumpStartingState;
import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

@Getter
@Setter
public class BlockJumpGame extends PartyGame<BlockJumpMap, BlockJumpConfig, BlockJumpUser> {

    private final @NotNull GameScoreboard<BlockJumpUser> scoreboard;
    private @NotNull PartyGameState currentState;

    public BlockJumpGame(@NotNull PartyGamesUserRegistry<? extends PartyGamesUser> userRegistry, @NotNull BlockJumpMap map, @NotNull File directory, @NotNull BlockJumpConfig gameConfig) {
        super(userRegistry, map, directory, gameConfig);
        this.scoreboard = new BlockJumpScoreboard(this);
    }

    @Override
    public void onLoad() {
        PartyGameBase.init(this);

        getUserRegistry().getAliveUsers().forEach(user -> addUser(new BlockJumpUser(user)));

        this.currentState = new BlockJumpStartingState(this);
        currentState.enter();

        new PlayerMoveListener(this);
    }

    @Override
    public void onUnload() {
        PartyGameBase.unregisterListeners();
        PartyGameBase.setGame(Optional.empty());
    }
}
