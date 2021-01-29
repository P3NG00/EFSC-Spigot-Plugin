package com.p3ng00.efsc.module.chairs.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.CHAIRS;
import static com.p3ng00.efsc.EFSC.ERROR_SENDER;

public class ChairsCommand extends P3Command {

    public ChairsCommand() {
        super("Chairs");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!CHAIRS.isEnabled()) {
            return Module.DISABLED;
        } else if (!(sender instanceof Player)) {
            return ERROR_SENDER;
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "enable":
                    if (CHAIRS.OPT_OUT.contains(((Player)sender).getUniqueId().toString())) {
                        CHAIRS.OPT_OUT.remove(((Player)sender).getUniqueId().toString());
                        return ChatColor.GREEN + "Enabled sitting";
                    } else {
                        return ChatColor.YELLOW + "Already opted in";
                    }
                case "disable":
                    if (CHAIRS.OPT_OUT.contains(((Player)sender).getUniqueId().toString())) {
                        return ChatColor.RED + "Already opted out";
                    } else {
                        CHAIRS.OPT_OUT.add(((Player)sender).getUniqueId().toString());
                        return ChatColor.YELLOW + "Disabled sitting";
                    }
            }
        }
        return "[Enable/Disable]";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("enable");
            list.add("disable");
        }
        return list;
    }
}
