package com.p3ng00.efsc.module.tridentretrieval.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.ERROR_NOT_OP;
import static com.p3ng00.efsc.EFSC.TRIDENT_RETRIEVAL;

public class TridentRetrievalCommand extends P3Command {

    public TridentRetrievalCommand() {
        super("TridentRetrieval");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (!TRIDENT_RETRIEVAL.isEnabled())
            return Module.DISABLED;
        else if (!sender.isOp())
            return ERROR_NOT_OP;
        else if (args.length > 2)
            return ChatColor.RED + "/TridentRetrieval distance <distance>";
        else {

            switch (args.length) {

                case 0:
                case 1:

                    return ChatColor.AQUA + "Distance is currently - " + TRIDENT_RETRIEVAL.distance + "\nUse '/TridentRetrieval distance <distance>' to change it";

                case 2:

                    try {
                        TRIDENT_RETRIEVAL.distance = Double.parseDouble(args[1]);
                    } catch (NumberFormatException e) {
                        return ChatColor.RED + "Distance must be a number";
                    }

                    return ChatColor.GREEN + "Distance changed to " + TRIDENT_RETRIEVAL.distance;

                default:
                    return ChatColor.RED + "/TridentRetrieval distance";

            }

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        List<String> list = new ArrayList<>();

        if (sender.isOp() && args.length == 1)
            list.add("distance");

        return list;

    }

}
