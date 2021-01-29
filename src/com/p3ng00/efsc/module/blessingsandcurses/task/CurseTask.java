package com.p3ng00.efsc.module.blessingsandcurses.task;

import com.p3ng00.efsc.module.blessingsandcurses.curse.Curse;
import org.bukkit.scheduler.BukkitRunnable;

import static com.p3ng00.efsc.EFSC.OVERWORLD;

public class CurseTask extends BukkitRunnable {

    private final Curse TYPE;

    public CurseTask(Curse type) {
        TYPE = type;
    }

    @Override
    public void run() {
        if (TYPE == null) {
            cancel();
            return;
        }
        TYPE.dothing();// todo
        if (OVERWORLD.getTime() < 12000) {
            cancel();
        }
    }
}
