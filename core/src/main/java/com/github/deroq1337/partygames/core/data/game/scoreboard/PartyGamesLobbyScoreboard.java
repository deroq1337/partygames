package com.github.deroq1337.partygames.core.data.game.scoreboard;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.scoreboard.models.PartyGamesScoreboardScore;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PartyGamesLobbyScoreboard extends PartyGamesScoreboard {

    public PartyGamesLobbyScoreboard(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        super(game);
    }

    @Override
    public void updateScoreboard(@NotNull DefaultPartyGamesUser user) {
        user.getBukkitPlayer().ifPresent(player -> {
            Scoreboard scoreboard = player.getScoreboard();

            String mapPrefix = game.getBoard()
                    .map(board -> user.getMessage("scoreboard_lobby_board_value", board.getName()))
                    .orElseGet(() -> user.getMessage("scoreboard_lobby_board_value", "§cNo board"));
            updatePrefix(scoreboard, "board", mapPrefix);

            String numberOfFieldsPrefix = game.getBoard()
                    .map(board -> user.getMessage("scoreboard_lobby_number_of_fields_value", board.getNumberOfFields()))
                    .orElseGet(() -> user.getMessage("scoreboard_lobby_number_of_fields_value", "§cNo board"));
            updatePrefix(scoreboard, "numberOfFields", numberOfFieldsPrefix);
        });
    }

    @Override
    public @NotNull List<PartyGamesScoreboardScore> getScoreboardScores() {
        return List.of(
                new PartyGamesScoreboardScore(Optional.of("scoreboard_lobby_board"), "board", "scoreboard_lobby_board_value", true),
                new PartyGamesScoreboardScore(Optional.of("scoreboard_lobby_number_of_fields"), "numberOfFields", "scoreboard_lobby_number_of_fields_value", false)
        );
    }

    private void updatePrefix(@NotNull Scoreboard scoreboard, @NotNull String teamName, @NotNull String prefix) {
        Optional.ofNullable(scoreboard.getTeam(teamName)).ifPresent(team -> team.setPrefix(prefix));
    }
}
