package com.github.deroq1337.partygames.core.data.game.scoreboard;

import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.user.User;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.scoreboard.models.PartyGamesScoreboardScore;
import com.github.deroq1337.partygames.core.data.game.states.PartyGamesLobbyState;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PartyGamesLobbyScoreboard extends PartyGamesScoreboard {

    public PartyGamesLobbyScoreboard(@NotNull PartyGamesGame<PartyGamesUser> game) {
        super(game, PartyGamesLobbyState.class);
    }

    @Override
    public <U extends User> void updateScoreboard(@NotNull U user) {
        ((PartyGamesUser) user).getBukkitPlayer().ifPresent(player -> {
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
    public @NotNull Class<? extends GameState> getState() {
        return PartyGamesLobbyState.class;
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
