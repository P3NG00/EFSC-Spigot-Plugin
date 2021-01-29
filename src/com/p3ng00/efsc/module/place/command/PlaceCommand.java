package com.p3ng00.efsc.module.place.command;

import com.p3ng00.p3plugin.P3Command;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.*;
import static com.p3ng00.efsc.module.place.Place.CHECK;

public class PlaceCommand extends P3Command {

    public PlaceCommand() {
        super("Place");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (!PLACE.isEnabled())
            return "Disabled";
        else if (!sender.isOp())
            return ERROR_NOT_OP;
        else if (!(sender instanceof Player))
            return ERROR_SENDER;
        else if (args.length == 0)
            return String.format(CHECK, PLACE.active ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");
        else {

            switch (args[0].toLowerCase()) {

                case "setbound":

                    if (args.length != 5)
                        return "/Place SetBound [Bound1/Bound2] <x> <y> <z>";

                    int x, y, z;

                    try {
                        x = Integer.parseInt(args[2]);
                        y = Integer.parseInt(args[3]);
                        z = Integer.parseInt(args[4]);
                    } catch (NumberFormatException e) {
                        return ChatColor.RED + "Coordinates must be integers";
                    }

                    switch (args[1].toLowerCase()) {

                        case "bound1":

                            PLACE.bounds[0] = new Location(((Player)sender).getWorld(), x, y, z);
                            PLACE.yLevel = y;
                            PLACE.worldName = ((Player)sender).getWorld().getName();
                            break;

                        case "bound2":

                            PLACE.bounds[1] = new Location(((Player)sender).getWorld(), x, y, z);
                            PLACE.worldName = ((Player)sender).getWorld().getName();
                            break;

                    }

                    return "Set " + args[1].toLowerCase() + ".";

                case "enable":

                    if (PLACE.bounds[0] == null || PLACE.bounds[1] == null)
                        return ChatColor.RED + "Bounds are not set";

                    if (PLACE.bounds[1].getBlockX() < PLACE.bounds[0].getBlockX()) {

                        int i = PLACE.bounds[0].getBlockX();
                        PLACE.bounds[0].setX(PLACE.bounds[1].getBlockX());
                        PLACE.bounds[1].setX(i);

                    }

                    if (PLACE.bounds[1].getBlockZ() < PLACE.bounds[0].getBlockZ()) {

                        int i = PLACE.bounds[0].getBlockZ();
                        PLACE.bounds[0].setZ(PLACE.bounds[1].getBlockZ());
                        PLACE.bounds[1].setZ(i);

                    }

                    PLACE.active = true;
                    return String.format(CHECK, "Enabled");

                case "disable":

                    if (!PLACE.active)
                        return ChatColor.RED + "Already disabled";
                    else {

                        PLACE.active = false;
                        return String.format(CHECK, "Disabled");

                    }

                default:
                    return ChatColor.RED + "/Place [Enable/Disable]";

            }

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        List<String> list = new ArrayList<>();

        if (sender.isOp() && args.length <= 5) {

            if (args.length == 1) {

                list.add("SetBound");
                list.add("Enable");
                list.add("Disable");

            } else if (args.length == 2 && args[0].equalsIgnoreCase("SetBound")) {

                list.add("Bound1");
                list.add("Bound2");

            } else if (sender instanceof Player && args.length >= 3) {

                Block target = ((Player)sender).getTargetBlockExact(5, FluidCollisionMode.NEVER);

                if (target != null) {

                    switch (args.length) {

                        case 3:

                            list.add(String.valueOf(target.getX()));
                            break;

                        case 4:

                            list.add(String.valueOf(target.getY()));
                            break;

                        case 5:

                            list.add(String.valueOf(target.getZ()));
                            break;

                    }

                }

            }

        }

        return list;

    }

}
