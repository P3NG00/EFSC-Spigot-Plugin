package com.p3ng00.efsc.module.mineablespawners.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.mineablespawners.MineableSpawners;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.ERROR_NOT_OP;
import static com.p3ng00.efsc.module.mineablespawners.MineableSpawners.DROP_CHANCE;
import static com.p3ng00.efsc.module.mineablespawners.MineableSpawners.MOB_NAME_COLOR;

public class SpawnersCommand extends P3Command {

    public SpawnersCommand() {
        super("Spawners");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!EFSC.MINEABLE_SPAWNERS.isEnabled()) {
            return Module.DISABLED;
        } else if (!sender.isOp()) {
            return ERROR_NOT_OP;
        } else if (args.length > 0 && args.length <= 2) {
            switch (args[0].toLowerCase()) {
                case "chance":
                    if (args.length == 1) {
                        return ChatColor.AQUA + "The chance is currently " + ChatColor.LIGHT_PURPLE + DROP_CHANCE + ChatColor.AQUA + "/" + ChatColor.LIGHT_PURPLE + "1.0";
                    } else {
                        double chance;
                        String numErr = ChatColor.RED + "Chance must be a number between 0.0 and 1.0\n/Spawners Chance <number between 0.0 and 1.0>";
                        try {
                            chance = Double.parseDouble(args[1]);
                        } catch (NumberFormatException e) {
                            return numErr;
                        }
                        if (chance < 0.0 || chance > 1.0) {
                            return numErr;
                        }
                        DROP_CHANCE = chance;
                        return ChatColor.GREEN + "Spawner Drop Chance set to " + ChatColor.LIGHT_PURPLE + chance;
                    }
                case "color":
                    if (args.length == 1) {
                        return ChatColor.AQUA + "Spawner Titles are currently " + MOB_NAME_COLOR + MOB_NAME_COLOR.name();
                    } else {
                        ChatColor color;
                        try {
                            color = ChatColor.valueOf(args[1].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            return ChatColor.RED + "Invalid color";
                        }
                        MineableSpawners.MOB_NAME_COLOR = color;
                        return ChatColor.GREEN + "Spawner Titles changed to " + color + color.name();
                    }
            }
        }
        return ChatColor.RED + "/Spawners [Chance/Color]";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender.isOp()) {
            switch (args.length) {
                case 1:
                    list.add("chance");
                    list.add("color");
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("color")) {
                        list.add("BLACK");
                        list.add("DARK_BLUE");
                        list.add("DARK_GREEN");
                        list.add("DARK_AQUA");
                        list.add("DARK_RED");
                        list.add("DARK_PURPLE");
                        list.add("GOLD");
                        list.add("GRAY");
                        list.add("DARK_GRAY");
                        list.add("BLUE");
                        list.add("GREEN");
                        list.add("AQUA");
                        list.add("RED");
                        list.add("LIGHT_PURPLE");
                        list.add("YELLOW");
                        list.add("WHITE");
                    }
                    break;
            }
        }
        return list;
    }
}
