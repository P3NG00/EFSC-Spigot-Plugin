package com.p3ng00.efsc.module.playerheads.command;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.p3ng00.efsc.EFSC.PLAYER_HEADS;

public class HeadCommand extends P3Command {

    public HeadCommand() {
        super("Head");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!PLAYER_HEADS.isEnabled()) {
            return Module.DISABLED;
        } else if (sender.isOp() && args.length == 2) {
            int amount;
            String errorWhole = ChatColor.RED + "Must be a whole number";
            switch (args[0].toLowerCase()) {
                case "tradeusemin":
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return errorWhole;
                    }
                    PLAYER_HEADS.tradeSettings[0] = amount;
                    return ChatColor.AQUA + "Minimum Trade Uses set to " + ChatColor.LIGHT_PURPLE + amount;
                case "tradeusevary":
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return errorWhole;
                    }
                    PLAYER_HEADS.tradeSettings[1] = amount;
                    return ChatColor.AQUA + "Trade Use Vary set to " + ChatColor.LIGHT_PURPLE + amount;
                case "tradepricemin":
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return errorWhole;
                    }
                    PLAYER_HEADS.tradeSettings[2] = amount;
                    return ChatColor.AQUA + "Minimum Trade Price set to " + ChatColor.LIGHT_PURPLE + amount;
                case "tradepricevary":
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return errorWhole;
                    }
                    PLAYER_HEADS.tradeSettings[3] = amount;
                    return ChatColor.AQUA + "Trade Price Vary set to " + ChatColor.LIGHT_PURPLE + amount;
                case "tradechance":
                    double chance;
                    try {
                        chance = Double.parseDouble(args[1]);
                    } catch (NumberFormatException e) {
                        return ChatColor.RED + "Must be a number";
                    }
                    PLAYER_HEADS.tradeChance = chance;
                    return ChatColor.AQUA + "Trade Chance set to " + ChatColor.LIGHT_PURPLE + chance + ChatColor.AQUA + "/" + ChatColor.LIGHT_PURPLE + "1.0";
                default:
                    return ChatColor.RED + "Invalid Option";
            }
        } else if (sender instanceof Player) {
            Player player = (Player)sender;
            ItemStack stack = player.getInventory().getItemInMainHand();
            if (stack.hasItemMeta()) {
                return ChatColor.RED + "Head must be cleaned first";
            } else if (stack.getType() == Material.PLAYER_HEAD) {
                SkullMeta skullMeta = (SkullMeta)stack.getItemMeta();
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                profile.getProperties().put("textures", new Property("textures", args[0]));
                Field profileField;
                try {
                    assert skullMeta != null;
                    profileField = skullMeta.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    return e.toString();
                }
                profileField.setAccessible(true);
                try {
                    profileField.set(skullMeta, profile);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return e.toString();
                }
                stack.setItemMeta(skullMeta);
                return "Hope you like it!";
            } else {
                return ChatColor.RED + "Must be holding a player head";
            }
        } else {
            return EFSC.ERROR_SENDER;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender.isOp() && args.length == 1) {
            list.add("tradeUseMin");
            list.add("tradeUseVary");
            list.add("tradePriceMin");
            list.add("tradePriceVary");
            list.add("tradeChance");
        }
        return list;
    }
}
