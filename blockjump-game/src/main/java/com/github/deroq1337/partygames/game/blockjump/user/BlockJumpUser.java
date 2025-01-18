package com.github.deroq1337.partygames.game.blockjump.user;

import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import com.github.deroq1337.partygames.game.base.user.PartyGameUserBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockJumpUser extends PartyGameUserBase {

    public BlockJumpUser(@NotNull PartyGamesUser partyGamesUser) {
        super(partyGamesUser);
    }
}
