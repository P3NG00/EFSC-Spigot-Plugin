package com.p3ng00.efsc.module.blessingsandcurses;

import com.p3ng00.P3Plugin.util.Sound;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.blessingsandcurses.command.CurseCommand;
import com.p3ng00.efsc.module.blessingsandcurses.command.StopCurseCommand;
import com.p3ng00.efsc.module.blessingsandcurses.curse.CurseType;
import com.p3ng00.efsc.module.blessingsandcurses.listener.BlessingsAndCursesListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class BlessingsAndCurses extends Module { // todo testing

    public static World OVERWORLD;

    public static CurseType CURRENT_CURSE = null;

    public static final Sound.Info SHRINE_CREATE_SOUND = new Sound.Info(org.bukkit.Sound.BLOCK_CONDUIT_ACTIVATE, 0.1f);

    public static final String SHRINE_CREATE_MESSAGE = ChatColor.AQUA + "Created a &d&lShrine of %s %s"; // String.format(SHRINE_CREATE_MESSAGE, shrineName, tier2)

    public static final String SHRINE_EXPLORE = ChatColor.GREEN.toString() + ChatColor.BOLD + "Exploration";
    public static final String SHRINE_FORTUNE = ChatColor.GOLD.toString() + ChatColor.BOLD + "Fortune";
    public static final String SHRINE_HEALTH = ChatColor.RED.toString() + ChatColor.BOLD + "Health";
    public static final String SHRINE_TIER2 = ChatColor.RESET.toString() + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Tier 2";

    public BlessingsAndCurses() { // todo show bossbar for current running curse
        super("Blessings And Curses", new BlessingsAndCursesListener(), new CurseCommand(), new StopCurseCommand()); // todo make commands better
    }

    @Override
    public boolean enable() {
        String name = EFSC.CONFIG.getString(createPath("overworld_name")); // todo make path not exist for testing
        if (name == null) {
            printToConsole("Path 'bac.overworld_name' not found", "Cannot use 'Blessings and Curses'");
            return false;
        }
        OVERWORLD = Bukkit.getWorld(name);
        if (OVERWORLD == null) {
            printToConsole("Overworld not found. Enter correct name at 'efsc.world_name' in config.yml", "Cannot use 'Blessings and Curses'");
            return false;
        }
        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        // todo
    }

    public boolean isCurrentlyCursed() {
        return CURRENT_CURSE != null;
    }
}
