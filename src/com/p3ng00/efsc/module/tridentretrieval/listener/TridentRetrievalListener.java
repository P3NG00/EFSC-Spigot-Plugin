package com.p3ng00.efsc.module.tridentretrieval.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import static com.p3ng00.efsc.EFSC.TRIDENT_RETRIEVAL;

public class TridentRetrievalListener implements Listener {

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {

        if (TRIDENT_RETRIEVAL.isEnabled() && event.getEntity() instanceof Trident && event.getEntity().getShooter() instanceof Player) {

            Trident trident = (Trident)event.getEntity();

            for (Entity e : trident.getNearbyEntities(TRIDENT_RETRIEVAL.distance, TRIDENT_RETRIEVAL.distance, TRIDENT_RETRIEVAL.distance)) {

                if (e instanceof Item) {

                    trident.addPassenger(e);
                    break;

                }

            }

        }

    }

}
