package com.github.deroq1337.partygames.game.base.scoreboard.sorting;

import com.github.deroq1337.partygames.game.base.user.PartyGameUserBase;

import java.util.Comparator;

public class AscendingComparator implements Comparator<PartyGameUserBase> {

    @Override
    public int compare(PartyGameUserBase o1, PartyGameUserBase o2) {
        return o1.getValue() - o2.getValue();
    }
}
