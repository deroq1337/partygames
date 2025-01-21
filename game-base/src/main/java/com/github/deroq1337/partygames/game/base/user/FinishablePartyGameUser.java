package com.github.deroq1337.partygames.game.base.user;

import com.github.deroq1337.partygames.api.user.PartyGamesUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class FinishablePartyGameUser extends AbstractPartyGameUser {

    private boolean finished;
    private Optional<Long> finishedAfter = Optional.empty();

    public FinishablePartyGameUser(@NotNull PartyGamesUser user) {
        super(user);
    }
}
