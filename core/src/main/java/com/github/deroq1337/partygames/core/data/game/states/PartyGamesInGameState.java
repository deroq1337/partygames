package com.github.deroq1337.partygames.core.data.game.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.scoreboard.PartyGamesInGameScoreboard;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Getter
public class PartyGamesInGameState implements PartyGamesState {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull GameScoreboard scoreboard;

    private Optional<PartyGame> currentGame = Optional.empty();

    public PartyGamesInGameState(@NotNull PartyGamesGame<PartyGamesUser> game) {
        this.game = game;
        this.scoreboard = new PartyGamesInGameScoreboard(game);
    }

    @Override
    public void enter() {
        game.setCurrentState(this);
        game.getBoard().ifPresent(board -> {
            game.getUserRegistry().getAliveUsers().forEach(user -> {
                scoreboard.setScoreboard(user);
                user.initDice();

                user.getBukkitPlayer().ifPresent(player -> {
                    Optional.ofNullable(board.getStartLocation()).ifPresent(startLocation -> player.teleport(startLocation.toBukkitLocation()));
                });
            });
        });
    }

    @Override
    public void leave() {

    }

    @Override
    public void onPlayerJoin(@NotNull UUID uuid) {
        PartyGamesUser user = game.getUserRegistry().addUser(uuid, false);
        scoreboard.setScoreboard(user);
    }

    @Override
    public void onPlayerQuit(@NotNull UUID uuid) {

    }

    @Override
    public Optional<PartyGamesState> getNextState() {
        return Optional.empty();
    }
}
