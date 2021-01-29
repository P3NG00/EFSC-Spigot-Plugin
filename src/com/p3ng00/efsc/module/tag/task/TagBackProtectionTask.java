package com.p3ng00.efsc.module.tag.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static com.p3ng00.efsc.EFSC.TAG;

public class TagBackProtectionTask extends BukkitRunnable {

    private Player protect;
    private boolean ran;

    public TagBackProtectionTask(Player player) {
        protect = player;
        ran = false;
    }

    @Override
    public void run() {
        if (!ran) {
            TAG.PROTECTED.add(protect);
        } else {
            TAG.PROTECTED.remove(protect);
            cancel();
            TAG.PROTECTION_TASKS.removeIf(BukkitTask::isCancelled);
        }
        ran = true;
    }
}
