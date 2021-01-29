package com.p3ng00.efsc.module.blessingsandcurses.time;

import com.p3ng00.efsc.module.blessingsandcurses.BlessingsAndCurses;

public enum Time {

    MORNING,
    AFTERNOON,
    NIGHT,
    MEGA_NIGHT; // Earl named this lol

    public static Time getTime() {
        return values()[((int)BlessingsAndCurses.OVERWORLD.getTime() / 6000)];
    }
}
