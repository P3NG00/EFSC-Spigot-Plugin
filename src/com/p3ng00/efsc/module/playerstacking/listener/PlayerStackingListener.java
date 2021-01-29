package com.p3ng00.efsc.module.playerstacking.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.p3ng00.efsc.EFSC.PLAYER_STACKING;

public class PlayerStackingListener implements Listener {

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent event) {
        if (PLAYER_STACKING.isEnabled() && event.getRightClicked() instanceof Player && event.getPlayer().getPose() == Pose.SNEAKING) {
            event.getPlayer().addPassenger(event.getRightClicked());
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR && event.getPlayer().getPassengers().size() > 0) {
            event.getPlayer().removePassenger(event.getPlayer().getPassengers().get(0));
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (PLAYER_STACKING.isEnabled() && event.getEntity().getShooter() instanceof Player && ((Player)event.getEntity().getShooter()).getPassengers().size() > 0) {
            event.getEntity().addPassenger(((Player)event.getEntity().getShooter()).getPassengers().get(0));
        }
    }

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (PLAYER_STACKING.isEnabled() && event.getEntity().getPassengers().size() > 0) {
            event.getEntity().removePassenger(event.getEntity().getPassengers().get(0));
        }
    }
}
