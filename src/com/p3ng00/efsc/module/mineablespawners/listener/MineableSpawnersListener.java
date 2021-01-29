package com.p3ng00.efsc.module.mineablespawners.listener;

import com.p3ng00.p3plugin.util.Sound;
import com.p3ng00.p3plugin.util.Text;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.mineablespawners.MineableSpawners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.p3ng00.efsc.module.mineablespawners.MineableSpawners.*;

public class MineableSpawnersListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAnvilRename(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getInventory().getType() != InventoryType.ANVIL || event.getSlotType() == InventoryType.SlotType.RESULT || event.getCurrentItem().getType() != Material.SPAWNER) {
            return;
        }
        event.getWhoClicked().sendMessage(ERROR_NO_RENAME);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerMine(BlockBreakEvent event) {
        if (!EFSC.MINEABLE_SPAWNERS.isEnabled() || event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        Block block = event.getBlock();
        if (block.getType() != Material.SPAWNER || event.isCancelled()) {
            return;
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }
        if (DROP_CHANCE < 1.0) {
            if (DROP_CHANCE < Math.random()) {
                event.getBlock().getWorld().playSound(event.getBlock().getLocation(), org.bukkit.Sound.ENTITY_WITHER_SPAWN, 0.5f, 2.0f);
                return;
            }
        }
        ItemStack item = new ItemStack(block.getType());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.setDisplayName(ChatColor.DARK_GRAY + "[" + MOB_NAME_COLOR + getCapitalizedMobName(((CreatureSpawner)block.getState()).getSpawnedType()) + ChatColor.GRAY + " Spawner" + ChatColor.DARK_GRAY + "]");
        item.setItemMeta(meta);
        if (block.getLocation().getWorld() == null) {
            return;
        }
        block.getLocation().getWorld().dropItemNaturally(block.getLocation(), item);
        event.setExpToDrop(0);
        block.getWorld().playSound(event.getBlock().getLocation(), org.bukkit.Sound.ENTITY_WITHER_DEATH, 0.5f, 2.0f);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEggChange(PlayerInteractEvent e) {
        if (!EFSC.MINEABLE_SPAWNERS.isEnabled() || e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        ItemStack itemInHand = e.getItem();
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && itemInHand != null && itemInHand.getType().name().contains("SPAWN_EGG") && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.SPAWNER)) {
            return;
        }
        CreatureSpawner spawner = (CreatureSpawner)e.getClickedBlock().getState();
        EntityType entityTypeOriginal = spawner.getSpawnedType();
        EntityType entityTypeNew = EntityType.valueOf(itemInHand.getType().name().toUpperCase().replace("_SPAWN_EGG", ""));
        if (entityTypeOriginal == entityTypeNew) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Text.colorFormat(ERROR_ALREADY_TYPE));
            return;
        }
        Sound.broadcastSound(new Sound.Info(org.bukkit.Sound.ITEM_TRIDENT_THUNDER, 2.0f));
        Bukkit.broadcastMessage(String.format(BROADCAST_EGG_CHANGE, e.getPlayer().getName(), MineableSpawners.getCapitalizedMobName(entityTypeOriginal), MineableSpawners.getCapitalizedMobName(entityTypeNew)));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSpawnerPlace(BlockPlaceEvent e) {
        if (!EFSC.MINEABLE_SPAWNERS.isEnabled() || e.getBlockPlaced().getType() != Material.SPAWNER || e.getItemInHand().getItemMeta() == null) {
            return;
        }
        if (e.getItemInHand().getItemMeta().getDisplayName().equals("")) {
            return;
        }
        EntityType entity = EntityType.valueOf(ChatColor.stripColor(e.getItemInHand().getItemMeta().getDisplayName()).toUpperCase().substring(1).replace(" SPAWNER]", "").replace(" ", "_"));
        CreatureSpawner spawner = (CreatureSpawner)e.getBlockPlaced().getState();
        spawner.setSpawnedType(entity);
        spawner.update();
        Sound.playSoundAtLocation(e.getBlockPlaced().getLocation(), new Sound.Info(org.bukkit.Sound.BLOCK_LANTERN_PLACE, 1.0f, 0.1f));
    }
}
