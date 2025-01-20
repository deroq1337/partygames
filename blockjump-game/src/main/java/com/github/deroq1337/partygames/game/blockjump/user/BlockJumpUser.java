package com.github.deroq1337.partygames.game.blockjump.user;

import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.game.base.user.FinishablePartyGameUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockJumpUser extends FinishablePartyGameUser {

    private Optional<Block> currentBlock = Optional.empty();
    private Optional<Block> previousBlock = Optional.empty();

    public BlockJumpUser(@NotNull PartyGamesUser partyGamesUser) {
        super(partyGamesUser);
    }
}
