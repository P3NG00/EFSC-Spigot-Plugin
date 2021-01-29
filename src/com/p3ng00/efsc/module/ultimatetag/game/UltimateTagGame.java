package com.p3ng00.efsc.module.ultimatetag.game;

import org.bukkit.scheduler.BukkitRunnable;

import static com.p3ng00.efsc.EFSC.ULTIMATE_TAG;
import static com.p3ng00.efsc.module.ultimatetag.game.UltimateTagGame.Phase.PREGAME;

public class UltimateTagGame extends BukkitRunnable {

    /**
     * todo RUNS 5 TIMES PER SECOND
     */

    private Phase phase;
    private int timer;
    private final int gameLength;

    public UltimateTagGame() {
        phase = PREGAME;
        timer = 40;
        gameLength = ULTIMATE_TAG.GAME_LENGTH;
    }

    @Override
    public void run() { // todo finish/test
        switch (phase) {
            case PREGAME:

                break;
            case GAME:

                break;
            case TAG_WAIT:

                break;
            case POSTGAME:

                break;
        }
    }

    enum Phase {
        PREGAME, GAME, TAG_WAIT, POSTGAME
    }
}
