package com.github.deroq1337.partygames.core.data.game.commands.board;

import com.github.deroq1337.partygames.core.data.game.PartyGamesGame;
import com.github.deroq1337.partygames.core.data.game.commands.board.subcommands.PartyGamesBoardAddFieldSubCommand;
import com.github.deroq1337.partygames.core.data.game.commands.board.subcommands.PartyGamesBoardCreateSubCommand;
import com.github.deroq1337.partygames.core.data.game.commands.board.subcommands.PartyGamesBoardSetStartSubCommand;
import com.github.deroq1337.partygames.core.data.game.user.PartyGamesUser;
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

public class PartyGamesBoardCommand implements CommandExecutor, TabCompleter {

    private final @NotNull PartyGamesGame<PartyGamesUser> game;
    private final @NotNull Map<String, PartyGamesBoardSubCommand> subCommandMap;

    public PartyGamesBoardCommand(@NotNull PartyGamesGame<PartyGamesUser> game) {
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

        Optional<PartyGamesUser> optionalUser = game.getUserRegistry().getUser(player.getUniqueId());
        if (optionalUser.isEmpty()) {
            player.sendMessage("§cAn error occurred. Rejoin or contact an administrator.");
            return true;
        }

        PartyGamesUser user = optionalUser.get();
        if (!player.hasPermission("partygames.board")) {
            user.sendMessage("no_permission");
            return true;
        }

        if (args.length < 1) {
            user.sendMessage("command_not_found");
            return true;
        }

        String subCommandName = args[0].toLowerCase();
        Optional<PartyGamesBoardSubCommand> subCommand = Optional.ofNullable(subCommandMap.get(subCommandName));
        if (subCommand.isEmpty()) {
            user.sendMessage("command_not_found");
            return true;
        }

        String[] subCommandArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
        subCommand.get().execute(user, player, subCommandArgs);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(subCommandMap.keySet());
        }

        return null;
    }
}