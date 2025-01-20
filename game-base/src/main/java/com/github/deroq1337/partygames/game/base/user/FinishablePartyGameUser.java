package com.github.deroq1337.partygames.game.base.user;

import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class FinishablePartyGameUser extends PartyGameUserBase {

    private boolean finished;

    public FinishablePartyGameUser(@NotNull PartyGamesUser partyGamesUser) {
        super(partyGamesUser);
    }
}
