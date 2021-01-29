package com.p3ng00.efsc.module.mineablespawners;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.mineablespawners.command.SpawnersCommand;
import com.p3ng00.efsc.module.mineablespawners.listener.MineableSpawnersListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class MineableSpawners extends Module {

    public static final String ERROR_ALREADY_TYPE = ChatColor.RED + "You are not allowed to rename spawners.";
    public static final String ERROR_NO_RENAME = ChatColor.RED + "Spawner already holds this entity.";
    public static final String BROADCAST_EGG_CHANGE = ChatColor.AQUA + "%s has changed their %s spawner into a %s spawner!"; // String.format(BROADCAST_EGG_CHANGE, player, originalMob, newMob)

    public static ChatColor MOB_NAME_COLOR;
    public static double DROP_CHANCE;

    public MineableSpawners() {
        super("Mineable Spawners", new MineableSpawnersListener(), new SpawnersCommand());
    }

    @Override
    public boolean enable() {
        String string = CONFIG.getString(createPath("mob_name_color"));
        if (string == null) {
            printToConsole("'mineable_spawners.mob_name_color' does not exist. Using default color: 'AQUA'");
        } else {
            try {
                MOB_NAME_COLOR = ChatColor.valueOf(string.toUpperCase());
            } catch (IllegalArgumentException e) {
                printToConsole("Invalid mob color name in 'mineable_spawners.mob_name_color. Using default color: 'AQUA'");
            }
        }
        if (MOB_NAME_COLOR == null) {
            MOB_NAME_COLOR = ChatColor.AQUA;
        }
        DROP_CHANCE = CONFIG.getDouble(createPath("drop_chance"));
        if (DROP_CHANCE < 0) {
            DROP_CHANCE = 0;
        } else if (DROP_CHANCE > 1) {
            DROP_CHANCE = 1;
        }
        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("mob_name_color"), MOB_NAME_COLOR.name());
        CONFIG.set(createPath("drop_chance"), DROP_CHANCE);
    }

    public static String getCapitalizedMobName(EntityType et) {
        return et.name().substring(0, 1).toUpperCase() + et.name().substring(1).toLowerCase().replace("_", " ");
    }
}
