package com.p3ng00.efsc.module.chairs;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.chairs.command.ChairsCommand;
import com.p3ng00.efsc.module.chairs.listener.ChairsListener;

import java.util.List;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class Chairs extends Module {

    public List<String> OPT_OUT; // List of player who do not want to be able to sit on bottom slabs or stairs.

    public Chairs() {
        super("Chairs", new ChairsListener(), new ChairsCommand());
    }

    @Override
    public boolean enable() {

        OPT_OUT = CONFIG.getStringList("chairs.opt_out");
        return super.enable();

    }

    @Override
    public void disable() {

        super.disable();
        CONFIG.set("chairs.opt_out", OPT_OUT);

    }

}
