package com.p3ng00.efsc.module.chairs.listener;

import com.p3ng00.efsc.EFSC;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class ChairsListener implements Listener {

    // Listens for player who right clicked a bottom slab/stair and checks if they have nothing in their hand so they can sit down.
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (!EFSC.CHAIRS.isEnabled() || event.getClickedBlock() == null || EFSC.CHAIRS.OPT_OUT.contains(event.getPlayer().getUniqueId().toString()) || event.getPlayer().getPose() != Pose.STANDING || !event.getPlayer().isOnGround() || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR || event.getPlayer().getInventory().getItemInOffHand().getType() != Material.AIR)
            return;

        BlockData data = event.getClickedBlock().getBlockData();

        if (data instanceof Slab) {

            if (((Slab)data).getType() != Slab.Type.BOTTOM)
                return;

        } else if (data instanceof Stairs) {

            if (((Stairs)data).getHalf() != Stairs.Half.BOTTOM)
                return;

        } else
            return;

        if (event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getLocation().clone().add(0, 1, 0)).getType().isOccluding())
            return;

        Player player = event.getPlayer();
        Arrow arrow = (Arrow)player.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0.5, 0, 0.5), EntityType.ARROW);
        arrow.setGravity(false);
        arrow.setInvulnerable(true);
        arrow.setTicksLived(-9999); // todo test
        arrow.addPassenger(player);

    }

    // When a player dismounts an arrow, the arrow is removed from the world.
    @EventHandler
    public void onDismount(EntityDismountEvent event) {

        if (event.getEntity() instanceof Player && event.getDismounted() instanceof Arrow)
            event.getDismounted().remove();

    }

}
