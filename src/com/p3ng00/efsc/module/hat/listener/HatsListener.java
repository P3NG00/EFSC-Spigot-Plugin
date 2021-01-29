package com.p3ng00.efsc.module.hat.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import static com.p3ng00.efsc.EFSC.HATS;

public class HatsListener implements Listener {

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent event) {
        if (HATS.isEnabled() && HATS.RIDING && event.getRightClicked() instanceof Player) {
            ItemStack helmet = ((Player)event.getRightClicked()).getInventory().getHelmet();
            if (helmet != null && helmet.getType() == Material.SADDLE) {
                event.getRightClicked().addPassenger(event.getPlayer());
            }
        }
    }
}
