package com.p3ng00.efsc.module.blessingsandcurses.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.ModuleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.p3ng00.efsc.module.blessingsandcurses.BlessingsAndCurses.CURRENT_CURSE;

public class StopCurseCommand extends ModuleCommand {

    public StopCurseCommand() {
        super("StopCurse");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!EFSC.BLESSINGS_AND_CURSES.isEnabled()) {
            return DISABLED;
        }
        if (!sender.isOp()) {
            return EFSC.ERROR_NOT_OP;
        }
        if (CURRENT_CURSE != null) {
            CURRENT_CURSE = null;
            Bukkit.broadcastMessage("Curse stopped"); // todo move to config
            return null;
        } else {
            return "No active curse"; // todo config
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
