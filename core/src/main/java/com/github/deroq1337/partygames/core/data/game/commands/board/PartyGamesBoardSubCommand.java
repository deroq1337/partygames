package com.github.deroq1337.partygames.core.data.game.commands.board;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.board.PartyGamesBoardManager;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class PartyGamesBoardSubCommand {

    protected final @NotNull PartyGamesGame<PartyGamesUser> game;
    protected final @NotNull PartyGamesBoardManager boardManager;
    private final @NotNull String name;

    public PartyGamesBoardSubCommand(@NotNull PartyGamesGame<PartyGamesUser> game, @NotNull String name) {
        this.game = game;
        this.boardManager = game.getBoardManager();
        this.name = name;
    }

    protected abstract void execute(@NotNull PartyGamesUser user, @NotNull Player player, @NotNull String[] args);
}
