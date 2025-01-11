package com.github.deroq1337.partygames.core.data.game.scoreboard;

import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.scoreboard.models.PartyGamesScoreboardScore;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PartyGamesScoreboard implements GameScoreboard {

    protected final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Class<? extends PartyGamesState> gameState;
    protected final @NotNull List<PartyGamesScoreboardScore> scoreboardScores;

    public PartyGamesScoreboard(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull Class<? extends PartyGamesState> gameState) {
        this.game = game;
        this.gameState = gameState;
        this.scoreboardScores = getScoreboardScores();
    }

    @Override
    public <U extends User> void setScoreboard(@NotNull U user) {
        Optional.ofNullable(Bukkit.getScoreboardManager()).ifPresent(scoreboardManager -> {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("partygames", Criteria.DUMMY, "§lGommeHD Test");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            AtomicInteger scoreIndex = new AtomicInteger(scoreboardScores.size() * 2 + (scoreboardScores.size() - 1));
            // empty score as first line
            setEmptyScore(objective, scoreIndex.get());
            scoreIndex.getAndDecrement();

            for (PartyGamesScoreboardScore scoreboardScore : scoreboardScores) {
                scoreboardScore.getName().ifPresent(scoreName -> {
                    Score score = objective.getScore(user.getMessage(scoreName));
                    score.setScore(scoreIndex.get());
                    scoreIndex.set(scoreIndex.get() - 1);
                });

                Team team = getTeam(scoreboard, scoreboardScore);
                String entry = generateRandomEntry(scoreboard);
                team.addEntry(entry);
                team.setPrefix(user.getMessage(scoreboardScore.getValue()));
                objective.getScore(entry).setScore(scoreIndex.get());
                scoreboardScore.setEntry(Optional.of(entry));
                scoreIndex.getAndDecrement();

                if (scoreboardScore.isFreeSpace()) {
                    setEmptyScore(objective, scoreIndex.get());
                    scoreIndex.getAndDecrement();
                }
            }

            ((PartyGamesUser) user).getBukkitPlayer().ifPresent(player -> player.setScoreboard(scoreboard));
            startUpdateScoreboardTask(user);
        });
    }

    @Override
    public abstract <U extends User> void updateScoreboard(@NotNull U user);

    public abstract @NotNull List<PartyGamesScoreboardScore> getScoreboardScores();

    private void startUpdateScoreboardTask(@NotNull User user) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!game.getCurrentState().getClass().equals(gameState)) {
                    cancel();
                    return;
                }

                updateScoreboard(user);
            }
        }.runTaskTimer(game.getPartyGames(), 0, 20L);
    }

    private void setEmptyScore(@NotNull Objective objective, int scoreIndex) {
        Score score = objective.getScore(" ".repeat(Math.max(0, scoreIndex)));
        score.setScore(scoreIndex);
    }

    private @NotNull Team getTeam(@NotNull Scoreboard scoreboard, @NotNull PartyGamesScoreboardScore scoreboardScore) {
        return Optional.ofNullable(scoreboard.getTeam(scoreboardScore.getTeamName()))
                .orElseGet(() -> scoreboard.registerNewTeam(scoreboardScore.getTeamName()));
    }

    private @NotNull String generateRandomEntry(@NotNull Scoreboard scoreboard) {
        ChatColor[] values = ChatColor.values();
        for (int tries = 100; tries > 0; tries--) {
            String entry = values[ThreadLocalRandom.current().nextInt(values.length)] + "" +
                    values[ThreadLocalRandom.current().nextInt(values.length)] + "" +
                    values[ThreadLocalRandom.current().nextInt(values.length)];
            if (scoreboard.getEntryTeam(entry) == null) {
                return entry;
            }
        }

        System.err.println("All generated random entries were existing");
        return ChatColor.BLACK + "" + ChatColor.WHITE;
    }
}