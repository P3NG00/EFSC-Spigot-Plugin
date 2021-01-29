package com.p3ng00.efsc.module.playerheads.listener;

import com.p3ng00.efsc.EFSC;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.p3ng00.efsc.EFSC.PLAYER_HEADS;
import static com.p3ng00.efsc.EFSC.printToConsole;

public class PlayerHeadsListener implements Listener {

    @EventHandler
    public void onPlayerDieFromChargedCreeper(EntityDamageByEntityEvent event) {

        if (PLAYER_HEADS.isEnabled() && event.getDamager().getType() == EntityType.CREEPER && event.getEntity().getType() == EntityType.PLAYER) {

            Player player = (Player)event.getEntity();

            if (player.getHealth() - event.getFinalDamage() <= 0 && ((Creeper)event.getDamager()).isPowered()) {

                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerHeadMeta = (SkullMeta)playerHead.getItemMeta();

                if (playerHeadMeta != null)
                    playerHeadMeta.setOwningPlayer(player);

                playerHead.setItemMeta(playerHeadMeta);

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        if (player.getLocation().getWorld() != null)
                            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), playerHead);

                    }

                }.runTaskLater(EFSC.INSTANCE, 1);

            }

        }

    }

    @EventHandler
    public void onWanderingTraderSpawn(CreatureSpawnEvent event) {

        if (PLAYER_HEADS.isEnabled() && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DEFAULT && event.getEntity().getType() == EntityType.WANDERING_TRADER && Math.random() < PLAYER_HEADS.tradeChance) {

            WanderingTrader trader = (WanderingTrader) event.getEntity();
            Random random = new Random();
            MerchantRecipe headRecipe = new MerchantRecipe(new ItemStack(Material.PLAYER_HEAD), random.nextInt(PLAYER_HEADS.tradeSettings[1] + 1) + PLAYER_HEADS.tradeSettings[0]);
            headRecipe.addIngredient(new ItemStack(Material.EMERALD, random.nextInt(PLAYER_HEADS.tradeSettings[3] + 1) + PLAYER_HEADS.tradeSettings[2]));
            headRecipe.addIngredient(new ItemStack(Material.PLAYER_HEAD));
            List<MerchantRecipe> recipes = new ArrayList<>(trader.getRecipes());
            recipes.set(random.nextInt(recipes.size()), headRecipe);
            trader.setRecipes(recipes);

        }

    }

}
