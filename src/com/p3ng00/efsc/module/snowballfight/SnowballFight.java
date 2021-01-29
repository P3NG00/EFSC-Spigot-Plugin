package com.p3ng00.efsc.module.snowballfight;

import com.p3ng00.efsc.module.GameModule;
import com.p3ng00.efsc.module.snowballfight.command.SnowballFightCommand;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class SnowballFight extends GameModule {

    public GameMode gamemode;

    public SnowballFight() {
        super("Snowball Fight", 2, null, new SnowballFightCommand());
    }

    @Override
    public boolean enable() {
        gamemode = GameMode.valueOf(CONFIG.getString(createPath("gamemode"))); // todo test
        return super.enable();
    }

    private enum GameMode {
        INFECTION,
        TEAM_DEATH_MATCH,
        HIDE_N_SEEK
    }
}
