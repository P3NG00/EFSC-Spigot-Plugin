package com.p3ng00.efsc.module.nametether;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.nametether.command.NameTetherCommand;
import com.p3ng00.efsc.module.nametether.listener.NameTetherListener;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class NameTether extends Module {

    public static boolean ALLOW_ANY_ITEM;
    public static boolean ALLOW_BOWS;
    public static boolean ALLOW_CROSSBOWS;
    public static boolean ALLOW_TRIDENTS;

    public NameTether() {
        super("Name Tether", new NameTetherListener(), new NameTetherCommand());
    }

    @Override
    public boolean enable() {

        ALLOW_ANY_ITEM = CONFIG.isBoolean(createPath("allow_any_item")) && CONFIG.getBoolean(createPath("allow_any_item"));
        ALLOW_BOWS = CONFIG.isBoolean(createPath("allow.bow")) && CONFIG.getBoolean(createPath("allow.bow"));
        ALLOW_CROSSBOWS = CONFIG.isBoolean(createPath("allow.crossbow")) && CONFIG.getBoolean(createPath("allow.crossbow"));
        ALLOW_TRIDENTS = CONFIG.isBoolean(createPath("allow.trident")) && CONFIG.getBoolean(createPath("allow.trident"));
        return super.enable();

    }

    @Override
    public void disable() {

        super.disable();
        CONFIG.set(createPath("allow_any_item"), ALLOW_ANY_ITEM);
        CONFIG.set(createPath("allow.bow"), ALLOW_BOWS);
        CONFIG.set(createPath("allow.crossbow"), ALLOW_CROSSBOWS);
        CONFIG.set(createPath("allow.trident"), ALLOW_TRIDENTS);

    }
}
