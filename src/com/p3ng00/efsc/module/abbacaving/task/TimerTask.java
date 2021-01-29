package com.p3ng00.efsc.module.abbacaving.task;

import com.p3ng00.efsc.module.abbacaving.ABBACaving;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.p3ng00.efsc.EFSC.ABBA_CAVING;

public class TimerTask extends BukkitRunnable {

    private BossBar bar;
    private final int initialTime;
    private int timer;

    public TimerTask(int timer) {
        bar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SEGMENTED_10);
        for (Player p : ABBA_CAVING.PARTY) {
            bar.addPlayer(p);
        }
        initialTime = timer;
        this.timer = timer;
    }

    @Override
    public void run() {
        if (timer > 0) {
            bar.setTitle(String.format("%d:%02d", timer / 60, timer % 60));
            bar.setProgress((double)timer / (double)initialTime);
            if (timer == 300) {
                bar.setColor(BarColor.YELLOW);
            } else if (timer == 60) {
                bar.setColor(BarColor.RED);
            }
            timer--;
        } else {
            bar.removeAll();
            cancel();
        }
    }
}
