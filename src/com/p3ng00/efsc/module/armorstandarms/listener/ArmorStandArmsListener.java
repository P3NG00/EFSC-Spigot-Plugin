package com.p3ng00.efsc.module.armorstandarms.listener;

import com.p3ng00.efsc.EFSC;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ArmorStandArmsListener implements Listener {

    // Listens for when an Armor Stand is spawned and gives it arms.
    @EventHandler
    public void onSpawnArmorStand(EntitySpawnEvent event) {

        if (EFSC.ARMOR_STAND_ARMS.isEnabled() && event.getEntity() instanceof ArmorStand)
            ((ArmorStand)event.getEntity()).setArms(true);

    }

}
