package com.github.deroq1337.partygames.game.blockjump.scoreboard;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.scoreboard.PartyGameScoreboard;
import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockJumpScoreboard extends PartyGameScoreboard<BlockJumpUser> {

    public BlockJumpScoreboard(@NotNull PartyGame<?, ?, BlockJumpUser> partyGame) {
        super(partyGame, 4);
    }

    @Override
    public @NotNull List<BlockJumpUser> getSortedUsers() {
        return partyGame.getUsers().stream()
                .sorted((o1, o2) -> -o1.getValue() - o2.getValue())
                .toList();
    }

    @Override
    public int getUsersAbove(@NotNull BlockJumpUser user, @NotNull List<BlockJumpUser> sortedUsers) {
        return (int) sortedUsers.stream()
                .filter(sortedUser -> sortedUser.getValue() > user.getValue())
                .count();
    }

    @Override
    public int getUsersBelow(@NotNull BlockJumpUser user, @NotNull List<BlockJumpUser> sortedUsers) {
        return (int) sortedUsers.stream()
                .filter(sortedUser -> sortedUser.getValue() < user.getValue())
                .count();
    }
}
