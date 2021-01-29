package com.p3ng00.efsc.module.playerstacking;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.playerstacking.command.PlayerStackingCommand;
import com.p3ng00.efsc.module.playerstacking.listener.PlayerStackingListener;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class PlayerStacking extends Module {

    // 0 - Player Launching
    // 1 - Multi-Stacking
    public boolean[] checks = new boolean[2];

    public PlayerStacking() {
        super("Player Stacking", new PlayerStackingListener(), new PlayerStackingCommand());
    }

    @Override
    public boolean enable() {

        checks[0] = CONFIG.getBoolean(createPath("player_launch"));
        checks[1] = CONFIG.getBoolean(createPath("multi_stack"));
        return super.enable();

    }

    @Override
    public void disable() {

        super.disable();
        CONFIG.set(createPath("player_launch"), checks[0]);
        CONFIG.set(createPath("multi_stack"), checks[1]);

    }

}
