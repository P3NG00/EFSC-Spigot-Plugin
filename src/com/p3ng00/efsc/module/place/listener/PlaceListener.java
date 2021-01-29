package com.p3ng00.efsc.module.place.listener;

import com.p3ng00.efsc.EFSC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static com.p3ng00.efsc.EFSC.PLACE;
import static com.p3ng00.efsc.module.place.Place.placeUUIDs;

public class PlaceListener implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {

        if (PLACE.isEnabled() && PLACE.active && event.getBlockPlaced().getWorld().getName().equals(PLACE.worldName) && isWithinBoundaries(event.getBlockPlaced().getX(), event.getBlockPlaced().getY(), event.getBlockPlaced().getZ())) {

            if (placeUUIDs.contains(event.getPlayer().getUniqueId())) {

                event.setBuild(false);
                event.getPlayer().sendMessage(ChatColor.RED + "Must wait 5 minutes to place again");

            } else {

                Location l = event.getBlockPlaced().getLocation().clone();
                l.setY(PLACE.yLevel);
                l.getBlock().setType(event.getBlockPlaced().getType());
                l.getBlock().setBlockData(event.getBlockPlaced().getBlockData());
                event.getBlockPlaced().setType(Material.AIR);
                placeUUIDs.add(event.getPlayer().getUniqueId());

                new BukkitRunnable() {
                    private final UUID remove = event.getPlayer().getUniqueId();

                    @Override
                    public void run() {

                        placeUUIDs.remove(remove);
                        Player p = Bukkit.getPlayer(remove);

                        if (p != null)
                            p.sendMessage(ChatColor.GREEN + "You may place a block at the Place area again");

                    }

                }.runTaskLater(EFSC.INSTANCE, 6000);

            }

        }

    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {

        if (PLACE.isEnabled() && PLACE.active && event.getBlock().getWorld().getName().equals(PLACE.worldName) && isWithinBoundaries(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ())) {

            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Not allowed to break blocks here");

        }

    }

    private boolean isWithinBoundaries(int x, int y, int z) {
        return x >= PLACE.bounds[0].getBlockX() && x <= PLACE.bounds[1].getBlockX() && y >= PLACE.yLevel && z >= PLACE.bounds[0].getBlockZ() && z <= PLACE.bounds[1].getBlockZ();
    }

}
