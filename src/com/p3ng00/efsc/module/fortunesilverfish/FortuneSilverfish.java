package com.p3ng00.efsc.module.fortunesilverfish;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.fortunesilverfish.listener.FortuneSilverfishListener;

public class FortuneSilverfish extends Module {

    public FortuneSilverfish() {
        super("Fortune Silverfish", new FortuneSilverfishListener());
    }

}
