package com.github.deroq1337.partygames.game.blockjump.countdowns;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.base.countdowns.PartyGameRunningCountdown;

import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import org.jetbrains.annotations.NotNull;

public class BlockJumpRunningCountdown extends PartyGameRunningCountdown {

    private final @NotNull PartyGame<?, ? extends PartyGameConfig, BlockJumpUser> partyGame;

    public BlockJumpRunningCountdown(@NotNull PartyGame<?, ? extends PartyGameConfig, BlockJumpUser> partyGame, @NotNull GameState gameState) {
        super(partyGame.getGameConfig(), gameState);
        this.partyGame = partyGame;
    }

    @Override
    public void onSpecialTick(int tick) {
        String countdownMessage = "game_over_countdown" + (tick == 1 ? "_one" : "");
        partyGame.getUsers().forEach(user -> user.getPartyGamesUser().sendMessage(countdownMessage, tick));
    }
}
