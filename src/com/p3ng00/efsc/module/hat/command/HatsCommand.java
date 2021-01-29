package com.p3ng00.efsc.module.hat.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.p3plugin.P3Command;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.HATS;

public class HatsCommand extends P3Command {

    public HatsCommand() {
        super("Hat");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (!HATS.isEnabled())
            return Module.DISABLED;

        if (sender.isOp() && args.length == 2 && args[0].equalsIgnoreCase("Riding")) {

            switch (args[1].toLowerCase()) {

                case "enable":

                    if (HATS.RIDING)
                        return ChatColor.YELLOW + "Already Enabled";
                    else {

                        HATS.RIDING = true;
                        return ChatColor.GREEN + "Riding Enabled";

                    }

                case "disable":

                    if (HATS.RIDING) {

                        HATS.RIDING = false;
                        return ChatColor.YELLOW + "Riding Disabled";

                    } else
                        return ChatColor.RED + "Already Disabled";

                default:
                    return ChatColor.RED + "/Hat Riding [Enable/Disable]";

            }

        }

        if (sender instanceof Player) {

            Player player = (Player)sender;

            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {

                ItemStack helmetHolder = player.getInventory().getHelmet();
                player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
                player.getInventory().setItemInMainHand(helmetHolder);
                return ChatColor.AQUA + "It's now on your head!";

            } else
                return ChatColor.RED + "Must have an item in hand";

        } else
            return EFSC.ERROR_SENDER;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        List<String> list = new ArrayList<>();

        if (sender.isOp()) {

            if (args.length == 1)
                list.add("Riding");
            else if (args.length == 2 && args[0].equalsIgnoreCase("Riding")) {

                list.add("Enable");
                list.add("Disable");

            }

        }

        return list;

    }

}
