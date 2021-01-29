package com.p3ng00.efsc.module;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.p3plugin.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.ERROR_SENDER;
import static com.p3ng00.efsc.module.GameModule.*;

public abstract class GameCommand extends P3Command {

    public GameCommand(String prefix) {
        super(prefix);
    }

    public final String onCommand(CommandSender sender, String[] args, GameModule game) {
        if (!game.isEnabled()) {
            return Module.DISABLED;
        } else if (!(sender instanceof Player)) {
            return ERROR_SENDER;
        } else {
            switch (args.length) {
                case 1:
                    switch (args[0].toLowerCase()) {
                        case "join":
                            return game.partyJoin(sender);
                        case "leave":
                            return game.partyLeave(sender);
                        case "list":
                            return game.getPartyList();
                        case "start":
                            if (game.PARTY.contains(sender)) {
                                if (game.isGameActive()) {
                                    return ERROR_ACTIVE;
                                } else if (game.PARTY.size() < 2) {
                                    return ERROR_NOT_ENOUGH_PLAYERS;
                                } else {
                                    startGame();
                                    Chat.broadcastMessageToList(game.PARTY, ChatColor.AQUA + "Starting game!");
                                    return null;
                                }
                            } else {
                                return ERROR_NOT_JOINED;
                            }
                        case "mins":
                            return game.showMinutes();
                    }
                case 2:
                    switch (args[0].toLowerCase()) {
                        case "kick":
                            return game.partyKick(Bukkit.getPlayer(args[1]));
                        case "mins":
                            if (game.PARTY.contains(sender)) {
                                try {
                                    return game.setMinutes((Player) sender, Integer.parseInt(args[1]));
                                } catch (NumberFormatException e) {
                                    return ERROR_NUMBER;
                                }
                            } else {
                                return ERROR_NOT_JOINED;
                            }
                    }
            }
            return ChatColor.RED + "/" + PREFIX + " [Join/Leave/List/Kick/Start/Mins]";
        }
    }

    public final List<String> onTabComplete(String[] args, GameModule game) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("join");
            list.add("leave");
            list.add("list");
            list.add("kick");
            list.add("start");
            list.add("mins");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("kick")) {
            for (Player p : game.PARTY) {
                list.add(p.getName());
            }
        }
        return list;
    }

    protected abstract void startGame();
}
