package com.p3ng00.efsc.module.blessingsandcurses.listener;

import com.p3ng00.P3Plugin.util.Sound;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.blessingsandcurses.shrine.Shrine;
import com.p3ng00.efsc.module.blessingsandcurses.time.MoonPhase;
import com.p3ng00.efsc.module.blessingsandcurses.time.Time;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.Random;

import static com.p3ng00.efsc.module.blessingsandcurses.BlessingsAndCurses.*;
import static com.p3ng00.efsc.module.blessingsandcurses.curse.CurseType.STRONG_NIGHT;
import static com.p3ng00.efsc.module.blessingsandcurses.curse.CurseType.CORRUPTED_BLOOD;

public class BlessingsAndCursesListener implements Listener {

    // Shrine Listener
    @EventHandler
    public void onWaterPlace(PlayerBucketEmptyEvent event) {
        if (!EFSC.BLESSINGS_AND_CURSES.isEnabled()) {
            return;
        }
        Shrine shrine = Shrine.getShrine(event.getBlock().getLocation().clone());
        if (shrine != null) {
            Sound.playSoundAtLocation(event.getBlock().getLocation().clone().add(0, 2, 0), SHRINE_CREATE_SOUND);
            event.getPlayer().sendMessage(String.format(SHRINE_CREATE_MESSAGE, shrine.type.getTitle(), shrine.tier2 ? SHRINE_TIER2 : ""));
        }
    }

    // Offering Listener
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (EFSC.BLESSINGS_AND_CURSES.isEnabled() && event.getItemDrop().getWorld() == OVERWORLD) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getItemDrop().isValid() || event.getItemDrop().getLocation().getBlock().getType() != Material.WATER) {
                        return;
                    }
                    MoonPhase phase = MoonPhase.getMoonPhase();
                    Time time = Time.getTime();
                    Shrine shrine = Shrine.getShrine(event.getItemDrop().getLocation());
                    if (shrine == null) {
                        return;
                    }
                    /* todo remove comments and combine checks
                    if ((phase != MoonPhase.FULL_MOON && phase != MoonPhase.NEW_MOON) || (time != NIGHT && time != MEGA_NIGHT) || !event.getPlayer().isValid() || !event.getItemDrop().isValid()) {
                        return;
                    }
                     */
                    event.getPlayer().addPotionEffects(shrine.getPotionEffects());
                    event.getItemDrop().getWorld().strikeLightning(event.getItemDrop().getLocation());
                    event.getItemDrop().remove();
                }
            }.runTaskLater(EFSC.INSTANCE, 30);
        }
    }

    @EventHandler
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (!EFSC.BLESSINGS_AND_CURSES.isEnabled() || CURRENT_CURSE == null || event.getEntity().getWorld() != OVERWORLD) {
            return;
        }
        switch (CURRENT_CURSE) {
            case DOUBLE_PHANTOMS: // Double Phantoms
                if (event.getEntity() instanceof Phantom && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                    event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.PHANTOM);
                }
                break;
            case UNDYING_NIGHT: // Undying Night
                if (event.getEntity() instanceof Monster) {
                    event.getEntity().setCanPickupItems(false);
                    event.getEntity().getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                }
                break;
            case STRONG_NIGHT: // Strong Night pt 1
                if (event.getEntity() instanceof Monster) {
                    event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
                }
                break;
            case ANGRY_ENDERMEN: // Angry Endermen
                if (event.getEntity() instanceof Enderman) {
                    for (Entity e : event.getEntity().getNearbyEntities(100, 100, 100)) {
                        if (e instanceof Player) {
                            ((Enderman)event.getEntity()).setTarget((Player)e);
                            return;
                        }
                    }
                }
                break;
            case DROWNED_JOCKEYS: // Drowned Jockeys pt 1
                if (event.getEntity() instanceof Guardian || event.getEntity() instanceof ElderGuardian || event.getEntity() instanceof Dolphin) {
                    Drowned drowned = (Drowned)event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.DROWNED);
                    ItemStack trident = new ItemStack(Material.TRIDENT);
                    trident.addEnchantment(Enchantment.LOYALTY, 1);
                    trident.addEnchantment(Enchantment.IMPALING, new Random().nextInt(3) + 2);
                    trident.addEnchantment(Enchantment.VANISHING_CURSE, 1);
                    if (drowned.getEquipment() == null) {
                        break;
                    }
                    drowned.getEquipment().setItemInMainHand(trident);
                    drowned.getEquipment().setItemInMainHandDropChance(0);
                    event.getEntity().addPassenger(drowned);
                }
                break;
            case KILLER_BUNNIES: // Killer Bunnies
                if (event.getEntity() instanceof Monster && Math.random() < 0.1) {
                    Rabbit rabbit = (Rabbit)event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.RABBIT);
                    rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
                }
                break;
            default:
        }
    }

    @EventHandler
    public void onSkeletonShootArrow(ProjectileLaunchEvent event) {
        if (!EFSC.BLESSINGS_AND_CURSES.isEnabled() || CURRENT_CURSE == null || event.getEntity().getWorld() != OVERWORLD) {
            return;
        }
        if (CURRENT_CURSE == STRONG_NIGHT && event.getEntity().getShooter() instanceof Skeleton && event.getEntity() instanceof Arrow) { // Strong Night pt 2
            ((Arrow)event.getEntity()).addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 0), true);
        }
    }

    @EventHandler
    public void onMonsterDeath(EntityDeathEvent event) {
        if (!EFSC.BLESSINGS_AND_CURSES.isEnabled() || CURRENT_CURSE == null || event.getEntity().getWorld() != OVERWORLD) {
            return;
        }
        if (CURRENT_CURSE == CORRUPTED_BLOOD && event.getEntity() instanceof Monster) { // Toxic Gas
            AreaEffectCloud cloud = (AreaEffectCloud)event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.AREA_EFFECT_CLOUD);
            cloud.setColor(Color.RED);
            cloud.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 90, 0), true);
            cloud.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 90, 0), true);
            cloud.setRadius(5);
            cloud.setWaitTime(10);
            cloud.setDuration(30);
            Sound.playSoundAtLocation(event.getEntity().getLocation(), new Sound.Info(org.bukkit.Sound.ENTITY_WITCH_CELEBRATE, 0.25f, 0.1f)); // todo ask arb, sound only on this one may be annoying/weird
        }
    }

    @EventHandler
    public void onDrownedDismount(EntityDismountEvent event) { // todo test
        if (EFSC.BLESSINGS_AND_CURSES.isEnabled() && event.getEntity() instanceof Drowned) { // Drowned Jockey pt 2
            event.setCancelled(true);
        }
    }

    /*
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (CURRENT_CURSE == HUNGERY) { // Hungery
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 1, 0, false, false));
        }
    }
     */
}
