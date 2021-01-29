package com.p3ng00.efsc.module.place;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.place.command.PlaceCommand;
import com.p3ng00.efsc.module.place.listener.PlaceListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class Place extends Module {

    public static final String CHECK = "Place: %s"; // String.format(CHECK, ENABLED/DISABLED)

    public String worldName;
    public int yLevel;
    // 0 - Bound 1
    // 1 - Bound 2
    public Location[] bounds;
    public boolean active = false;

    public static List<UUID> placeUUIDs;

    public Place() {
        super("Place", new PlaceListener(), new PlaceCommand());
    }

    @Override
    public boolean enable() {
        worldName = CONFIG.getString(createPath("bounds.world"));
        bounds = new Location[2];
        if (worldName == null) {
            printToConsole();
        }
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            yLevel = 0;
            Arrays.fill(bounds, null);
        } else {
            yLevel = getBound("y");
            bounds[0] = new Location(world, getBound("bound1.x"), yLevel, getBound("bound1.z"));
            bounds[1] = new Location(world, getBound("bound2.x"), yLevel, getBound("bound2.z"));
            active = CONFIG.getBoolean("place.active");
        }
        placeUUIDs = new ArrayList<>();
        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("active"), active);
        CONFIG.set(createPath("bounds.world"), worldName);
        CONFIG.set(createPath("bounds.y"), yLevel);
        if (bounds[0] != null) {
            CONFIG.set(createPath("bounds.bound1.x"), bounds[0].getBlockX());
            CONFIG.set(createPath("bounds.bound1.z"), bounds[0].getBlockZ());
        }
        if (bounds[1] != null) {
            CONFIG.set(createPath("bounds.bound2.x"), bounds[1].getBlockX());
            CONFIG.set(createPath("bounds.bound2.z"), bounds[1].getBlockZ());
        }
    }

    private int getBound(String loc) {
        return CONFIG.getInt(createPath("bounds.") + loc);
    }
}
