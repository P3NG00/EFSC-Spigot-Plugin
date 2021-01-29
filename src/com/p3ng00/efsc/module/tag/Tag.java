package com.p3ng00.efsc.module.tag;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.tag.command.TagCommand;
import com.p3ng00.efsc.module.tag.listener.TagListener;
import com.p3ng00.efsc.module.tag.task.VoteTask;
import com.p3ng00.p3plugin.util.Chat;
import com.p3ng00.p3plugin.util.Sound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class Tag extends Module {

    public final String MSG_WHO_IS_IT = ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "%s " + ChatColor.RESET + ChatColor.AQUA + "is it!"; // String.format(MSG_WHO_IS_IT, tagged)
    public final String PLAYER_OPTED_OUT = ChatColor.RED + "This person isn't playing Tag";

    public List<BukkitTask> PROTECTION_TASKS;
    public List<Player> PROTECTED;
    public BukkitTask VOTE_TASK;
    public ArrayList<Player> VOTERS;

    public int VOTE_REQUIREMENT;
    public boolean NO_TAG_BACKS;
    public List<String> OPT_OUT;

    public String IT;

    public Tag() {
        super("Tag", new TagListener(), new TagCommand());
    }

    @Override
    public boolean enable() {
        PROTECTION_TASKS = new ArrayList<>();
        PROTECTED = new ArrayList<>();
        VOTE_TASK = null;
        VOTERS = new ArrayList<>();

        VOTE_REQUIREMENT = CONFIG.getInt(createPath("vote_requirement"));
        NO_TAG_BACKS = CONFIG.getBoolean(createPath("no_tag_backs"));
        OPT_OUT = CONFIG.getStringList(createPath("opt_out"));

        IT = CONFIG.getString(createPath("it"));

        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("vote_requirement"), VOTE_REQUIREMENT);
        CONFIG.set(createPath("no_tag_backs"), NO_TAG_BACKS);
        CONFIG.set(createPath("opt_out"), OPT_OUT);
        CONFIG.set(createPath("it"), IT);
    }

    public List<Player> getOptedInPlayers() {
        List<Player> optedIn = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!OPT_OUT.contains(p.getName())) {
                optedIn.add(p);
            }
        }
        return optedIn;
    }

    public String vote(Player player) {
        if (VOTE_TASK == null || VOTE_TASK.isCancelled()) {
            VOTERS.clear();
            VOTE_TASK = new VoteTask().runTaskTimer(EFSC.INSTANCE, 20, 100);
            broadcastToOptedInPlayers(ChatColor.LIGHT_PURPLE + player.getName() + " " + ChatColor.AQUA + "has started a vote to change who is it!");
            voteAdd(player);
        } else {
            if (VOTERS.contains(player)) {
                return ChatColor.RED + "You've already voted.";
            } else {
                voteAdd(player);
            }
        }
        return null;
    }

    private void voteAdd(Player player) {
        VOTERS.add(player);
        broadcastToOptedInPlayers(String.format(ChatColor.AQUA + "%s has voted! " + ChatColor.LIGHT_PURPLE + "%d/%d " + ChatColor.AQUA + "(/Tag Vote)", player.getName(), VOTERS.size(), VOTE_REQUIREMENT));
    }

    public void voteEnd() {
        VOTE_TASK.cancel();
        VOTERS.clear();
    }

    public void setIt(Player player) {
        IT = player.getName();
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0, false, false));
        broadcastToOptedInPlayers(String.format(MSG_WHO_IS_IT, IT));
        player.sendTitle("", ChatColor.YELLOW.toString() + ChatColor.BOLD + "You've been tagged!", 0, 40, 20);
        Sound.playSoundAtPlayer(player, new Sound.Info(org.bukkit.Sound.ENTITY_WITCH_HURT, 0.5f, 0.5f));
    }

    public void broadcastToOptedInPlayers(String msg) {
        Chat.broadcastMessageToList(getOptedInPlayers(), msg);
    }
}
