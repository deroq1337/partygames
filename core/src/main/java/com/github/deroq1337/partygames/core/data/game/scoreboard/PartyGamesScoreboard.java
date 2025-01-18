package com.github.deroq1337.partygames.core.data.game.scoreboard;

import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.scoreboard.models.PartyGamesScoreboardScore;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PartyGamesScoreboard implements GameScoreboard<DefaultPartyGamesUser> {

    protected final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    protected final @NotNull List<PartyGamesScoreboardScore> scoreboardScores;

    private Optional<BukkitTask> task = Optional.empty();

    public PartyGamesScoreboard(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        this.scoreboardScores = getScoreboardScores();
    }

    @Override
    public void setScoreboard(@NotNull DefaultPartyGamesUser user) {
        user.getBukkitPlayer().ifPresent(player -> {
            Optional.ofNullable(Bukkit.getScoreboardManager()).ifPresent(scoreboardManager -> {
                Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("partygames", Criteria.DUMMY, "§lGommeHD Test");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                AtomicInteger scoreIndex = new AtomicInteger(scoreboardScores.size() * 2 + (scoreboardScores.size() - 1));
                setEmptyScore(objective, scoreIndex.getAndDecrement());

                for (PartyGamesScoreboardScore scoreboardScore : scoreboardScores) {
                    scoreboardScore.getName().ifPresent(scoreName -> addScore(objective, user.getMessage(scoreName), scoreIndex.getAndDecrement()));

                    Team team = getTeam(scoreboard, scoreboardScore);
                    String entry = generateRandomEntry(scoreboard);
                    team.addEntry(entry);
                    team.setPrefix(user.getMessage(scoreboardScore.getValue()));

                    addScore(objective, entry, scoreIndex.getAndDecrement());
                    scoreboardScore.setEntry(Optional.of(entry));

                    if (scoreboardScore.isFreeSpace()) {
                        setEmptyScore(objective, scoreIndex.getAndDecrement());
                    }
                }

                player.setScoreboard(scoreboard);
                startUpdateScoreboardTask(user);
            });
        });
    }

    @Override
    public abstract void updateScoreboard(@NotNull DefaultPartyGamesUser user);

    @Override
    public void cancelScoreboardUpdate() {
        task.ifPresent(BukkitTask::cancel);
        this.task = Optional.empty();
    }

    public abstract @NotNull List<PartyGamesScoreboardScore> getScoreboardScores();

    private void startUpdateScoreboardTask(@NotNull DefaultPartyGamesUser user) {
        this.task = Optional.of(new BukkitRunnable() {
            @Override
            public void run() {
                updateScoreboard(user);
            }
        }.runTaskTimer(game.getPartyGames(), 0L, 20L));
    }

    private void setEmptyScore(@NotNull Objective objective, int scoreIndex) {
        Score score = objective.getScore(" ".repeat(Math.max(0, scoreIndex)));
        score.setScore(scoreIndex);
    }

    private @NotNull Team getTeam(@NotNull Scoreboard scoreboard, @NotNull PartyGamesScoreboardScore scoreboardScore) {
        return Optional.ofNullable(scoreboard.getTeam(scoreboardScore.getTeamName()))
                .orElseGet(() -> scoreboard.registerNewTeam(scoreboardScore.getTeamName()));
    }

    private void addScore(@NotNull Objective objective, @NotNull String name, int score) {
        Score scoreEntry = objective.getScore(name);
        scoreEntry.setScore(score);
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