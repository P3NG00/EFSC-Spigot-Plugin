package com.p3ng00.efsc.module.fortunesilverfish.listener;

import com.p3ng00.efsc.EFSC;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FortuneSilverfishListener implements Listener {

    @EventHandler
    public void onBreakInfestedBlock(BlockBreakEvent event) {

        if (EFSC.FORTUNE_SILVERFISH.isEnabled() && event.getBlock().getType().name().contains("INFESTED") && event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {

            for (int i = 0; i < event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS); i++) {

                Silverfish fish = (Silverfish)event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation().clone().add(0.5, 0.01, 0.5), EntityType.SILVERFISH);
                fish.setTarget(event.getPlayer());

            }

        }

    }

}
