package com.p3ng00.efsc;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.MODULES;

public class EFSCCommand extends P3Command {

    public EFSCCommand() {
        super("EFSC");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (sender.isOp() && args.length == 2) {

            Module module = null;

            for (Module m : EFSC.MODULES) {

                if (args[0].equalsIgnoreCase(m.TITLE.replace(" ", ""))) {

                    module = m;
                    break;

                }

            }

            if (module == null)
                return ChatColor.RED + "Invalid Module";

            switch (args[1].toLowerCase()) {

                case "enable":

                    if (module.isEnabled())
                        return ChatColor.GREEN + "Module already enabled";
                    else {

                        module.setEnabled(module.enable());
                        return String.format("[%s] %s", module.TITLE, module.isEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");

                    }

                case "disable":

                    if (module.isEnabled()) {

                        module.disable();
                        return String.format("[%s] disabled", module.TITLE);

                    } else
                        return ChatColor.YELLOW + "Module already disabled";

                default:
                    return ChatColor.RED + "/EFSC <Module> [Enable/Disable]";

            }

        } else {

            boolean first = true;
            StringBuilder enabled = new StringBuilder();

            for (Module m : MODULES) {

                if (first)
                    first = false;
                else
                    enabled.append(ChatColor.RESET).append(", ");

                enabled.append(m.isEnabled() ? ChatColor.GREEN : ChatColor.RED).append(m.TITLE);

            }

            return "Version: " + EFSC.INSTANCE.getDescription().getVersion() + " of the 9th Reclamation\nEnabled: " + enabled.toString();

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        List<String> list = new ArrayList<>();

        if (sender.isOp()) {

            switch (args.length) {

                case 1:

                    for (Module m : EFSC.MODULES)
                        list.add(m.TITLE.replace(" ", ""));

                    break;

                case 2:

                    list.add("Enable");
                    list.add("Disable");
                    break;

            }

        }

        return list;

    }

}
