package com.github.deroq1337.partygames.core.data.game.tasks;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.models.PartyGamesBoardField;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class FieldEventTask extends BukkitRunnable implements Task {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull Player player;
    private final @NotNull PartyGamesBoardField field;

    private int ticks = 0;

    @Override
    public void start() {
        runTaskTimer(game.getPartyGames(), 0L, 15L);
    }

    @Override
    public void run() {
        if (ticks == 4) {
            System.out.println("REVEAL");
            cancel();
            return;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
        //field.glow(player);
        ticks++;
    }
}
