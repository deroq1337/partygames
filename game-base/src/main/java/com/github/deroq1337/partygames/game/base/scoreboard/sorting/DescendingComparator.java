package com.github.deroq1337.partygames.game.base.scoreboard.sorting;

import com.github.deroq1337.partygames.game.base.user.AbstractPartyGameUser;

import java.util.Comparator;

public class DescendingComparator<U extends AbstractPartyGameUser> implements Comparator<U> {

    @Override
    public int compare(U o1, U o2) {
        return -o1.getValue() - o2.getValue();
    }
}
