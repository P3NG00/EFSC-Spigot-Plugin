package com.p3ng00.efsc.module.ultimatetag;

import com.p3ng00.efsc.module.GameModule;
import com.p3ng00.efsc.module.ultimatetag.command.UltimateTagCommand;
import com.p3ng00.efsc.module.ultimatetag.listener.UltimateTagListener;

public class UltimateTag extends GameModule {

    public UltimateTag() { // todo requires testing
        super("Ultimate Tag", 2, new UltimateTagListener(), new UltimateTagCommand());
    }
}
