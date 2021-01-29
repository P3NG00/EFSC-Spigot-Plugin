package com.p3ng00.efsc.module.tag.task;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static com.p3ng00.efsc.EFSC.TAG;

public class VoteTask extends BukkitRunnable {

    private int timer;

    public VoteTask() {
        timer = 12;
    }

    @Override
    public void run() {
        if (timer <= 0) {
            TAG.broadcastToOptedInPlayers(ChatColor.YELLOW + "Not enough players voted");
            TAG.voteEnd();
        } else if (TAG.VOTERS.size() >= TAG.VOTE_REQUIREMENT) {
            TAG.setIt(TAG.VOTERS.get(new Random().nextInt(TAG.VOTERS.size())));
            TAG.voteEnd();
        }
        timer--;
    }
}
