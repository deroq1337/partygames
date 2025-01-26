package com.github.deroq1337.partygames.core.data.game.states;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.api.game.PartyGamePlacement;
import com.github.deroq1337.partygames.api.scoreboard.GameScoreboard;
import com.github.deroq1337.partygames.api.state.GameState;
import com.github.deroq1337.partygames.api.state.PartyGamesState;
import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.dice.Dice;
import com.github.deroq1337.partygames.core.data.game.models.CurrentGame;
import com.github.deroq1337.partygames.core.data.game.provider.PartyGameManifest;
import com.github.deroq1337.partygames.core.data.game.scoreboard.PartyGamesInGameScoreboard;
import com.github.deroq1337.partygames.core.data.game.tasks.PartyGameChooseTask;
import com.github.deroq1337.partygames.core.data.game.tasks.PartyGameLoadTask;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class PartyGamesInGameState implements PartyGamesState {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull GameScoreboard<DefaultPartyGamesUser> scoreboard;
    private final @NotNull Set<PartyGameManifest> playableGames;

    private Optional<CurrentGame> currentGame = Optional.empty();

    public PartyGamesInGameState(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        this.scoreboard = new PartyGamesInGameScoreboard(game);
        this.playableGames = game.getGameProvider().getPartyGameManifests();
    }

    @Override
    public void enter() {
        game.setCurrentState(this);

        game.getBoard().ifPresent(board -> game.getUserRegistry().getAliveUsers().forEach(user -> {
            user.getBukkitPlayer().ifPresent(player ->
                    Optional.ofNullable(board.getStartLocation()).ifPresent(startLocation -> player.teleport(startLocation.toBukkitLocation())));
            user.initDices();
            user.getDice().ifPresent(Dice::initiateRoll);

            scoreboard.setScoreboard(user);
            new PartyGameChooseTask(game, this).start();
        }));
    }

    @Override
    public void leave() {
        currentGame.ifPresent(endedGame -> game.getGameProvider().unloadGame(endedGame.getManifest()));
        this.currentGame = Optional.empty();
        scoreboard.cancelScoreboardUpdate();
    }

    @Override
    public void onPlayerJoin(@NotNull UUID uuid) {
        DefaultPartyGamesUser user = game.getUserRegistry().addUser(uuid, false);
        scoreboard.setScoreboard(user);
    }

    @Override
    public void onPlayerQuit(@NotNull UUID uuid) {

    }

    public void playGame(@NotNull PartyGameManifest manifest) {
        PartyGame<?, ?, ?> partyGame = game.getGameProvider().loadGame(manifest)
                .orElseThrow(() -> new RuntimeException("Could not load game '" + manifest.getName() + "'"));
        announceGame(manifest);
        playableGames.remove(manifest);

        new PartyGameLoadTask(game, this, new CurrentGame(partyGame, manifest)).start();
    }

    public void onGameEnd(@NotNull Map<Integer, PartyGamePlacement> placements) {
        unloadCurrentGame();

        Map<UUID, PartyGamePlacement> placementMap = mapUserPlacements(placements.values());
        game.getUserRegistry().getAliveUsers().forEach(user ->
                user.addPlacement(Optional.ofNullable(placementMap.get(user.getUuid()))));

        game.getBoard().ifPresent(board -> game.getUserRegistry().getUsers().forEach(user ->
                handleUserGameEnd(user, placementMap, placements)));
    }

    private void unloadCurrentGame() {
        currentGame.ifPresent(endedGame -> {
            endedGame.getPartyGame().onUnload();
            game.getGameProvider().unloadGame(endedGame.getManifest());
        });

        this.currentGame = Optional.empty();
    }

    private void handleUserGameEnd(@NotNull DefaultPartyGamesUser user, @NotNull Map<UUID, PartyGamePlacement> placementMap,
                                   @NotNull Map<Integer, PartyGamePlacement> placements) {
        user.getBukkitPlayer().ifPresent(player -> player.teleport(user.getLastLocation()));
        sendPlacementOverviewMessage(user, placements);

        Optional.ofNullable(placementMap.get(user.getUuid())).ifPresentOrElse(
                placement -> user.sendMessage("game_placement", placement.getPlacement()),
                () -> user.sendMessage("game_no_placement")
        );

        if (user.isAlive()) {
            user.initDices();
            user.getDice().ifPresent(Dice::initiateRoll);
        }
    }

    private void sendPlacementOverviewMessage(@NotNull DefaultPartyGamesUser user, @NotNull Map<Integer, PartyGamePlacement> placements) {
        String firstPlace = getUsernameOfPlacement(placements, 1);
        String secondPlace = getUsernameOfPlacement(placements, 2);
        String thirdPlace = getUsernameOfPlacement(placements, 3);

        user.sendMessage("game_placement_overview", firstPlace, secondPlace, thirdPlace);
    }

    private @NotNull Map<UUID, PartyGamePlacement> mapUserPlacements(@NotNull Collection<PartyGamePlacement> placements) {
        return placements.stream()
                .collect(Collectors.toMap(PartyGamePlacement::getUuid, placement -> placement));
    }

    private @NotNull String getUsernameOfPlacement(@NotNull Map<Integer, PartyGamePlacement> placements, int finalPlacement) {
        return Optional.of(placements.get(finalPlacement)).flatMap(placement -> {
            return game.getUserRegistry().getUser(placement.getUuid()).flatMap(user -> {
                return user.getBukkitPlayer().map(player -> player.getName());
            });
        }).orElse("/");
    }

    private void announceGame(@NotNull PartyGameManifest manifest) {
        game.getUserRegistry().getUsers().forEach(user -> {
            user.getBukkitPlayer().ifPresent(player -> player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f));
            user.sendTitle("game_announcement_title", manifest.getName());
            user.sendMessage("game_announcement_name", manifest.getName());
            user.sendMessage("game_announcement_explanation", manifest.getDescription());
        });
    }

    @Override
    public Optional<GameState> getNextState() {
        return Optional.empty();
    }
}
