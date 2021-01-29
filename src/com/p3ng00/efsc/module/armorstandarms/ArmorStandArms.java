package com.p3ng00.efsc.module.armorstandarms;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.armorstandarms.listener.ArmorStandArmsListener;

public class ArmorStandArms extends Module {

    public ArmorStandArms() {
        super("Armor Stand Arms", new ArmorStandArmsListener());
    }
}
