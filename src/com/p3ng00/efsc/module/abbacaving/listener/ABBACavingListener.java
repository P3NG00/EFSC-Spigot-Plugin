package com.p3ng00.efsc.module.abbacaving.listener;

import com.p3ng00.efsc.module.abbacaving.util.GameItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import static com.p3ng00.p3plugin.util.Sound.broadcastSoundToList;
import static com.p3ng00.p3plugin.util.Sound.playSoundAtPlayer;
import static com.p3ng00.efsc.EFSC.ABBA_CAVING;
import static com.p3ng00.efsc.module.abbacaving.ABBACaving.*;

public class ABBACavingListener implements Listener {

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (ABBA_CAVING.isGameActive() && event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (ABBA_CAVING.PARTY.contains(player)) {
                for (GameItem gi : ITEM_LIST) {
                    for (Material m : gi.materials) {
                        ItemStack is = event.getItem().getItemStack();
                        if (is.getType() == m) {
                            if (ABBA_CAVING.isCaveSpiderSpawner(is)) {
                                playSoundAtPlayer(player, NOWTHISISEPIC);
                            } else if (gi.broadcast) {
                                broadcastSoundToList(ABBA_CAVING.PARTY, gi.soundInfo);
                            } else {
                                playSoundAtPlayer(player, gi.soundInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (!ABBA_CAVING.isGameActive() && ABBA_CAVING.PARTY.contains(event.getPlayer())) {
            ABBA_CAVING.partyLeave(event.getPlayer());
        }
    }
}
