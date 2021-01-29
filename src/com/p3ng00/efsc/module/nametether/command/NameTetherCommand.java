package com.p3ng00.efsc.module.nametether.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.Util;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.nametether.NameTether;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.ERROR_NOT_OP;
import static com.p3ng00.efsc.EFSC.NAME_TETHER;

public class NameTetherCommand extends P3Command {

    public NameTetherCommand() {
        super("NameTether");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!NAME_TETHER.isEnabled()) {
            return Module.DISABLED;
        } else if (!sender.isOp()) {
            return ERROR_NOT_OP;
        } else {
            switch (args.length) {
                case 0:
                    String format = ChatColor.RESET + "%s - %s";
                    return String.format("%s\n%s\n%s\n%s\n%s", String.format(format, "AnyItem", getEnabled(NameTether.ALLOW_ANY_ITEM)), String.format(format, "Bows", getEnabled(NameTether.ALLOW_BOWS)), String.format(format, "Crossbows", getEnabled(NameTether.ALLOW_CROSSBOWS)), String.format(format, "Tridents", getEnabled(NameTether.ALLOW_TRIDENTS)), ChatColor.RESET + "Use " + ChatColor.AQUA + "/NameTether <Option> [Enable/Disable]");
                case 2:
                    boolean enable;
                    try {
                        enable = Util.getEnabledOrDisabled(args[1]);
                    } catch (IllegalArgumentException e) {
                        return ChatColor.RED + "[Enable/Disable]";
                    }
                    switch (args[0].toLowerCase()) {
                        case "anyitem":
                            NameTether.ALLOW_ANY_ITEM = enable;
                            break;
                        case "bows":
                            NameTether.ALLOW_BOWS = enable;
                            break;
                        case "crossbows":
                            NameTether.ALLOW_CROSSBOWS = enable;
                            break;
                        case "tridents":
                            NameTether.ALLOW_TRIDENTS = enable;
                            break;
                        default:
                            return ChatColor.RED + "Invalid option";
                    }
                    return ChatColor.AQUA + args[0].toUpperCase() + " has been " + (enable ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");
            }
        }
        return ChatColor.RED + "/NameTether [AnyItem/Bow/Crossbow/Trident] [Enable/Disable]";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender.isOp()) {
            switch (args.length) {
                case 1:
                    list.add("anyItem");
                    list.add("bows");
                    list.add("crossbows");
                    list.add("tridents");
                    break;
                case 2:
                    list.add("enable");
                    list.add("disable");
            }
        }
        return list;
    }

    private String getEnabled(boolean enabled) {
        return enabled ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled";
    }
}
