package com.p3ng00.efsc.module.tag.listener;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.tag.task.TagBackProtectionTask;
import com.p3ng00.p3plugin.util.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.p3ng00.efsc.EFSC.TAG;

public class TagListener implements Listener {

    @EventHandler
    public void onPlayerHitPlayer(EntityDamageByEntityEvent event) {

        if (!TAG.isEnabled() || !(event.getEntity() instanceof Player))
            return;

        Player tagged = (Player)event.getEntity();
        Player tagger;

        if (event.getDamager() instanceof Snowball)
            tagger = (Player)((Projectile)event.getDamager()).getShooter();
        else if (event.getDamager() instanceof Player && event.getCause() != EntityDamageEvent.DamageCause.THORNS && ((Player)event.getDamager()).getInventory().getItemInMainHand().getType() == Material.AIR)
            tagger = (Player)event.getDamager();
        else
            return;

        if (tagger == null || !tagger.getName().equals(TAG.IT))
            return;

        if (TAG.PROTECTED.contains(tagged) || TAG.OPT_OUT.contains(tagged.getName())) {

            tagger.sendMessage(TAG.PROTECTED.contains(tagged) ? ChatColor.YELLOW + "Cannot tag this player yet!" : TAG.PLAYER_OPTED_OUT);
            return;

        }

        TAG.broadcastToOptedInPlayers(String.format(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "%s " + ChatColor.RESET + ChatColor.AQUA + "has tagged " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "%s" + ChatColor.RESET + ChatColor.BOLD + "!", tagger.getName(), tagged.getName()));
        TAG.setIt(tagged);
        TAG.PROTECTION_TASKS.add(new TagBackProtectionTask(tagger).runTaskTimer(EFSC.INSTANCE, 0, 200));

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (TAG.isEnabled() && event.getPlayer().getName().equals(TAG.IT))
            event.setJoinMessage(Text.colorFormat(event.getJoinMessage() + " and is tagged!"));

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        if (TAG.VOTE_TASK != null && !TAG.VOTE_TASK.isCancelled() && TAG.VOTERS.contains(event.getPlayer())) {

            TAG.VOTERS.remove(event.getPlayer());
            TAG.broadcastToOptedInPlayers(String.format(ChatColor.YELLOW + "%s left! " + ChatColor.LIGHT_PURPLE + "%d/%d " + ChatColor.AQUA + "(/Tag Vote)", event.getPlayer().getName(), TAG.VOTERS.size(), TAG.VOTE_REQUIREMENT));

        }

    }

}
