package com.p3ng00.efsc.module.blessingsandcurses.time;

import com.p3ng00.efsc.module.blessingsandcurses.BlessingsAndCurses;

public enum MoonPhase {

    FULL_MOON,
    WANING_GIBBOUS,
    LAST_QUARTER,
    WANING_CRESCENT,
    NEW_MOON,
    WAXING_CRESCENT,
    FIRST_QUARTER,
    WAXING_GIBBOUS;

    public static MoonPhase getMoonPhase() {
        return MoonPhase.values()[(int)((BlessingsAndCurses.OVERWORLD.getFullTime() / 24000) % 8)];
    }
}
