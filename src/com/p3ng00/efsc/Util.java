package com.p3ng00.efsc;

import org.bukkit.ChatColor;

public final class Util {

    public static String getEnabledAsString(boolean enabled) {
        return (enabled ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled") + ChatColor.RESET;
    }

    public static boolean getEnabledOrDisabled(String enable) throws IllegalArgumentException {

        switch (enable.toLowerCase()) {

            case "enable":
                return true;

            case "disable":
                return false;

            default:
                throw new IllegalArgumentException("String must be 'Enable' or 'Disable'");

        }

    }

}
