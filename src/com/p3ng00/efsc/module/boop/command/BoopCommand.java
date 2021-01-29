package com.p3ng00.efsc.module.boop.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.p3plugin.P3Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.p3ng00.efsc.EFSC.BOOP;

public class BoopCommand extends P3Command {

    public BoopCommand() {
        super("Boop");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (!BOOP.isEnabled())
            return Module.DISABLED;

        else if (!(sender instanceof Player))
            return EFSC.ERROR_SENDER;

        else if (args.length == 1) {

            Player booped = Bukkit.getPlayer(args[0]);

            if (booped != null) {

                booped.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.AQUA + " has booped you!");
                return ChatColor.AQUA + "You booped " + ChatColor.LIGHT_PURPLE + booped.getName() + ChatColor.AQUA + "!";

            } else return ChatColor.RED + "Player not found";

        } else return ChatColor.RED + "/Boop <player>";

    }

}
