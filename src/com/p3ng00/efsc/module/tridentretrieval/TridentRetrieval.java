package com.p3ng00.efsc.module.tridentretrieval;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.tridentretrieval.command.TridentRetrievalCommand;
import com.p3ng00.efsc.module.tridentretrieval.listener.TridentRetrievalListener;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class TridentRetrieval extends Module {

    public double distance;

    public TridentRetrieval() {
        super("Trident Retrieval", new TridentRetrievalListener(), new TridentRetrievalCommand());
    }

    @Override
    public boolean enable() {
        distance = CONFIG.getDouble(createPath("distance"));
        return super.enable();
    }

    @Override
    public void disable() {
        CONFIG.set(createPath("distance"), distance);
    }
}
