package com.github.deroq1337.partygames.core.data.game.commands.board;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class PartyGamesBoardSubCommand {

    protected final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    protected final @NotNull PartyGamesBoardManager boardManager;
    private final @NotNull String name;

    public PartyGamesBoardSubCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game, @NotNull String name) {
        this.game = game;
        this.boardManager = game.getBoardManager();
        this.name = name;
    }

    protected abstract void execute(@NotNull DefaultPartyGamesUser user, @NotNull Player player, @NotNull String[] args);
}
