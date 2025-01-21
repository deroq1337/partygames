package com.github.deroq1337.partygames.game.blockjump.scoreboard;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.scoreboard.PartyGameScoreboard;
import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import org.jetbrains.annotations.NotNull;

public class BlockJumpScoreboard extends PartyGameScoreboard<BlockJumpUser> {

    public BlockJumpScoreboard(@NotNull PartyGame<?, ?, BlockJumpUser> partyGame) {
        super(partyGame, 4);
    }
}
