package com.p3ng00.efsc.module.tag.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.Util;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.p3plugin.P3Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.*;

public class TagCommand extends P3Command {

    public TagCommand() {
        super("Tag");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (!TAG.isEnabled())
            return Module.DISABLED;
        else if (args.length == 0)
            return String.format(TAG.MSG_WHO_IS_IT, TAG.IT);
        else {

            switch (args[0].toLowerCase()) {

                case "vote":

                    if (!(sender instanceof Player))
                        return ERROR_SENDER;
                    else if (TAG.OPT_OUT.contains(sender.getName()))
                        return TAG.PLAYER_OPTED_OUT;
                    else
                        return TAG.vote((Player)sender);

                case "enable":

                    if (!(sender instanceof Player))
                        return ERROR_SENDER;
                    else if (TAG.OPT_OUT.remove(sender.getName()))
                        return ChatColor.GREEN + "You have enabled Tag!";
                    else
                        return ChatColor.YELLOW + "You're already opted in";

                case "disable":

                    if (!(sender instanceof Player))
                        return ERROR_SENDER;
                    else if (!TAG.OPT_OUT.contains(sender.getName())) {

                        TAG.OPT_OUT.add(sender.getName());
                        return ChatColor.YELLOW + "You have disabled Tag!";

                    } else
                        return ChatColor.YELLOW + "You're already opted out";

                case "setit":

                    if (!sender.isOp())
                        return ERROR_NOT_OP;
                    else if (args.length != 2)
                        return ChatColor.RED + "/Tag setIt <Player>";
                    else {

                        Player set = Bukkit.getPlayer(args[1]);

                        if (set != null) {

                            if (TAG.OPT_OUT.contains(set.getName()))
                                return TAG.PLAYER_OPTED_OUT;
                            else {

                                TAG.setIt(set);
                                return null;

                            }

                        } else
                            return EFSC.ERROR_OFFLINE;

                    }

                case "votereq":

                    if (!sender.isOp())
                        return ERROR_NOT_OP;
                    else if (args.length != 2)
                        return ChatColor.AQUA + "Vote Requirement - " + ChatColor.LIGHT_PURPLE + TAG.VOTE_REQUIREMENT + ChatColor.AQUA + "\n/Tag voteReq <Requirement>";
                    else {

                        int requirement;

                        try {
                            requirement = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            return ChatColor.RED + "Requirement must be a whole number";
                        }

                        TAG.VOTE_REQUIREMENT = requirement;
                        Bukkit.broadcastMessage(ChatColor.AQUA + "Tag Vote requirement changed to " + ChatColor.LIGHT_PURPLE + requirement);
                        return null;

                    }

                case "notagbacks":

                    if (!sender.isOp())
                        return ERROR_NOT_OP;
                    else if (args.length != 2)
                        return ChatColor.AQUA + "No Tag Backs - " + Util.getEnabledAsString(TAG.NO_TAG_BACKS) + ChatColor.AQUA + "\n/Tag noTagBacks [Enable/Disable]";
                    else {

                        boolean enable;

                        try {
                            enable = Util.getEnabledOrDisabled(args[1]);
                        } catch (IllegalArgumentException e) {
                            return ChatColor.RED + "[Enable/Disable]";
                        }

                        TAG.NO_TAG_BACKS = enable;
                        return "No Tag Backs - " + Util.getEnabledAsString(enable);

                    }

                default:
                    return ChatColor.RED + "/Tag [Vote/Enable/Disable]";

            }

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        List<String> list = new ArrayList<>();

        if (args.length == 1) {

            list.add("vote");
            list.add("enable");
            list.add("disable");

            if (sender.isOp()) {

                list.add("setIt");
                list.add("voteReq");
                list.add("noTagBacks");

            }

        } else if (args.length == 2 && sender.isOp()) {

            switch (args[0].toLowerCase()) {

                case "setit":

                    for (Player p : TAG.getOptedInPlayers())
                        list.add(p.getName());

                    break;

                case "notagbacks":

                    list.add("enable");
                    list.add("disable");
                    break;

            }

        }

        return list;

    }

}
