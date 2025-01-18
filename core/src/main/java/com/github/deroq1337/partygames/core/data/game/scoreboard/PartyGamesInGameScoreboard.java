package com.github.deroq1337.partygames.core.data.game.scoreboard;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.scoreboard.models.PartyGamesScoreboardScore;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PartyGamesInGameScoreboard extends PartyGamesScoreboard {

    public PartyGamesInGameScoreboard(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        super(game);
    }

    @Override
    public void updateScoreboard(@NotNull DefaultPartyGamesUser user) {
        user.getBukkitPlayer().ifPresent(player -> {
            Scoreboard scoreboard = player.getScoreboard();

            String playersCountPrefix = user.getMessage("scoreboard_in_game_players_count_value", game.getUserRegistry().getAliveUsers().size());
            updatePrefix(scoreboard, "playersCount", playersCountPrefix);

            String rankingPrefix = user.getMessage("scoreboard_in_game_ranking_value", user.getFieldRanking());
            updatePrefix(scoreboard, "ranking", rankingPrefix);

            String currentFieldPrefix = user.getMessage("scoreboard_in_game_current_field_value", user.getCurrentField());
            updatePrefix(scoreboard, "currentField", currentFieldPrefix);
        });
    }

    @Override
    public @NotNull List<PartyGamesScoreboardScore> getScoreboardScores() {
        return List.of(
                new PartyGamesScoreboardScore(Optional.of("scoreboard_in_game_players_count"), "playersCount", "scoreboard_in_game_players_count_value", true),
                new PartyGamesScoreboardScore(Optional.of("scoreboard_in_game_ranking"), "ranking", "scoreboard_in_game_ranking_value", true),
                new PartyGamesScoreboardScore(Optional.of("scoreboard_in_game_current_field"), "currentField", "scoreboard_in_game_current_field_value", false)
        );
    }

    private void updatePrefix(@NotNull Scoreboard scoreboard, @NotNull String teamName, @NotNull String prefix) {
        Optional.ofNullable(scoreboard.getTeam(teamName)).ifPresent(team -> team.setPrefix(prefix));
    }
}
