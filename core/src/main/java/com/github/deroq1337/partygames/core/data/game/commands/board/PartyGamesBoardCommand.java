package com.github.deroq1337.partygames.core.data.game.commands.board;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.commands.board.subcommands.PartyGamesBoardAddFieldSubCommand;
import com.github.deroq1337.partygames.core.data.game.commands.board.subcommands.PartyGamesBoardCreateSubCommand;
import com.github.deroq1337.partygames.core.data.game.commands.board.subcommands.PartyGamesBoardSetStartSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.DefaultPartyGamesUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartyGamesBoardCommand implements CommandExecutor {

    private final @NotNull PartyGamesGame<DefaultPartyGamesUser> game;
    private final @NotNull Map<String, PartyGamesBoardSubCommand> subCommandMap;

    public PartyGamesBoardCommand(@NotNull PartyGamesGame<DefaultPartyGamesUser> game) {
        this.game = game;
        this.subCommandMap = Stream.of(
                new PartyGamesBoardCreateSubCommand(game),
                new PartyGamesBoardAddFieldSubCommand(game),
                new PartyGamesBoardSetStartSubCommand(game)
        ).collect(Collectors.toMap(subCommand -> subCommand.getName().toLowerCase(), subCommand -> subCommand));

        Optional.ofNullable(game.getPartyGames().getCommand("board")).ifPresent(pluginCommand -> pluginCommand.setExecutor(this));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        game.getUserRegistry().getUser(player.getUniqueId()).ifPresentOrElse(user -> {
            if (!player.hasPermission("partygames.board")) {
                user.sendMessage("no_permission");
                return;
            }

            if (args.length < 1) {
                user.sendMessage("command_not_found");
                return;
            }

            String subCommandName = args[0].toLowerCase();
            Optional.ofNullable(subCommandMap.get(subCommandName)).ifPresentOrElse(
                    subCommand -> subCommand.execute(user, player, buildSubCommandArgs(args)),
                    () -> user.sendMessage("command_not_found")
            );
        }, () -> player.sendMessage("§cAn error occurred. Rejoin or contact an administrator."));


        return true;
    }

    private @NotNull String[] buildSubCommandArgs(@NotNull String[] args) {
        return Arrays.stream(args)
                .skip(1)
                .toArray(String[]::new);
    }
}