package com.p3ng00.efsc.module.blessingsandcurses.command;

import com.p3ng00.P3Plugin.util.Text;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.ModuleCommand;
import com.p3ng00.efsc.module.blessingsandcurses.BlessingsAndCurses;
import com.p3ng00.efsc.module.blessingsandcurses.curse.CurseType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CurseCommand extends ModuleCommand {

    public CurseCommand() {
        super("Curse");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!EFSC.BLESSINGS_AND_CURSES.isEnabled()) {
            return DISABLED;
        }
        if (!sender.isOp()) {
            return EFSC.ERROR_NOT_OP;
        }
        if (args.length == 1) {
            try {
                BlessingsAndCurses.CURRENT_CURSE = CurseType.valueOf(args[0]);
                Bukkit.broadcastMessage("Curse set to " + BlessingsAndCurses.CURRENT_CURSE.name()); // todo move to config
                return null;
            } catch (IllegalArgumentException e) {
                List<String> msgs = new ArrayList<>();
                for (CurseType ct : CurseType.values()) {
                    msgs.add(ct.name());
                }
                return Text.createList(msgs);

                /* todo remove??
                StringBuilder list = new StringBuilder();
                boolean first = true;
                for (CurseType ct : CurseType.values()) {
                    if (first) {
                        first = false;
                    } else {
                        list.append(", ");
                    }
                    list.append(ct.name());
                }
                return list.toString();
                 */
            }
        }
        return null; // todo may need a value
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender.isOp() && args.length == 1) {
            for (CurseType ct : CurseType.values()) {
                list.add(ct.name());
            }
        }
        return list;
    }
}
