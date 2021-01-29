package com.p3ng00.efsc.module.textcolors.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum ColorResource {

    BLACK(ChatColor.BLACK, Material.BLACK_DYE),
    DARK_BLUE(ChatColor.DARK_BLUE, Material.BLUE_DYE, Material.BLACK_DYE),
    DARK_GREEN(ChatColor.DARK_GREEN, Material.GREEN_DYE),
    DARK_AQUA(ChatColor.DARK_AQUA, Material.CYAN_DYE, Material.BLACK_DYE),
    DARK_RED(ChatColor.DARK_RED, Material.RED_DYE, Material.BLACK_DYE),
    DARK_PURPLE(ChatColor.DARK_PURPLE, Material.PURPLE_DYE, Material.BLACK_DYE),
    GOLD(ChatColor.GOLD, Material.ORANGE_DYE),
    GRAY(ChatColor.GRAY, Material.LIGHT_GRAY_DYE),
    DARK_GRAY(ChatColor.DARK_GRAY, Material.GRAY_DYE),
    BLUE(ChatColor.BLUE, Material.BLUE_DYE),
    GREEN(ChatColor.GREEN, Material.LIME_DYE),
    AQUA(ChatColor.AQUA, Material.LIGHT_BLUE_DYE),
    RED(ChatColor.RED, Material.RED_DYE),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Material.PURPLE_DYE),
    YELLOW(ChatColor.YELLOW, Material.YELLOW_DYE),
    WHITE(ChatColor.WHITE, Material.WHITE_DYE);

    private final ChatColor COLOR;
    private final Material[] MATERIAL;

    ColorResource(ChatColor color, Material... material) {
        COLOR = color;
        MATERIAL = material;
    }
}
