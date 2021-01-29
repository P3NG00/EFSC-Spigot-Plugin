package com.p3ng00.efsc.module.playerstacking.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.Util;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.PLAYER_STACKING;

public class PlayerStackingCommand extends P3Command {

    public PlayerStackingCommand() {
        super("Stacking");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!PLAYER_STACKING.isEnabled()) {
            return Module.DISABLED;
        } else if (!sender.isOp()) {
            return EFSC.ERROR_NOT_OP;
        } else {
            switch (args.length) {
                case 0:
                    sender.sendMessage("Player Launching: " + PLAYER_STACKING.checks[0] + "\nMulti Stacking: " + PLAYER_STACKING.checks[1]);
                    break;
                case 2:
                    boolean enable;
                    try {
                        enable = Util.getEnabledOrDisabled(args[1]);
                    } catch (IllegalArgumentException e) {
                        return ChatColor.RED + "[Enable/Disable]";
                    }
                    switch (args[0].toLowerCase()) {
                        case "playerlaunching":
                            PLAYER_STACKING.checks[0] = enable;
                            return "Player Launching - " + (enable ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");
                        case "multistacking":
                            PLAYER_STACKING.checks[1] = enable;
                            return "Multi Stacking - " + (enable ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");
                    }
                    break;
            }
        }
        return "/Stacking [PlayerLaunching/MultiStacking]";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender.isOp() && args.length == 1) {
            list.add("playerLaunching");
            list.add("multiStacking");
        }
        return list;
    }
}
