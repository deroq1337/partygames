package com.github.deroq1337.partygames.game.base.scoreboard;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.game.base.user.PartyGameUserBase;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class PartyGameScoreboard<U extends PartyGameUserBase> implements GameScoreboard<U> {

    protected final @NotNull PartyGame<?, ?, U> partyGame;
    private final int topUsersDisplayCount;

    private Optional<BukkitTask> task = Optional.empty();

    @Override
    public void setScoreboard(@NotNull U user) {
        user.getBukkitPlayer().ifPresent(player -> {
            Optional.ofNullable(Bukkit.getScoreboardManager()).ifPresent(scoreboardManager -> {
                Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("blockjump", Criteria.DUMMY, "§lGommeHD Test");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                List<U> sortedUsers = getSortedUsers();
                int score = sortedUsers.size();
                boolean isInTopFour = displayTopFour(user, sortedUsers, objective, score);

                if (!isInTopFour) {
                    displayUsersAbove(user, sortedUsers, objective, score);
                    score--;

                    displayUserScore(user, objective, score);
                    score--;
                }

                displayUsersBelow(user, sortedUsers, objective, score);
                player.setScoreboard(scoreboard);
            });
        });
    }

    @Override
    public void updateScoreboard(@NotNull U user) {

    }

    @Override
    public void cancelScoreboardUpdate() {

    }

    public abstract @NotNull List<U> getSortedUsers();

    public abstract int getUsersAbove(@NotNull U user, @NotNull List<U> sortedUsers);

    public abstract int getUsersBelow(@NotNull U user, @NotNull List<U> sortedUsers);

    private boolean displayTopFour(@NotNull U user, @NotNull List<U> sortedUsers, @NotNull Objective objective, int score) {
        boolean isInTopFour = false;

        for (int i = 0; i < Math.min(4, sortedUsers.size()); i++) {
            int finalScore = score;
            U topFourUser = sortedUsers.get(i);
            topFourUser.getBukkitPlayer().ifPresent(player ->
                    objective.getScore(user.getPartyGamesUser().getMessage("game_scoreboard_user", topFourUser.getValue(), player.getName())).setScore(finalScore));

            if (!isInTopFour && user.getPartyGamesUser().equals(topFourUser.getPartyGamesUser())) {
                isInTopFour = true;
            }
            score--;
        }

        return isInTopFour;
    }

    private void displayUsersAbove(@NotNull U user, @NotNull List<U> sortedUsers, @NotNull Objective objective, int score) {
        long usersAbove = getUsersAbove(user, sortedUsers) - topUsersDisplayCount;
        if (usersAbove > 0) {
            objective.getScore(user.getPartyGamesUser().getMessage("game_scoreboard_users_above", getUsersAbove(user, sortedUsers))).setScore(score);
        }
    }

    private void displayUserScore(@NotNull U user, @NotNull Objective objective, int score) {
        user.getBukkitPlayer().ifPresent(player ->
                objective.getScore(user.getPartyGamesUser().getMessage("game_scoreboard_user", user.getValue(), player.getName())).setScore(score));
    }

    private void displayUsersBelow(@NotNull U user, @NotNull List<U> sortedUsers, @NotNull Objective objective, int score) {
        int usersBelow = getUsersBelow(user, sortedUsers);
        if (usersBelow > 0) {
            objective.getScore(user.getPartyGamesUser().getMessage("game_scoreboard_users_below", usersBelow)).setScore(score);
        }
    }
}