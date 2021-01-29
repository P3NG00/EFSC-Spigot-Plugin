package com.p3ng00.efsc.module.boop;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.boop.command.BoopCommand;

public class Boop extends Module {

    // todo opt in system

    public Boop() {
        super("Boop", null, new BoopCommand());
    }

}
