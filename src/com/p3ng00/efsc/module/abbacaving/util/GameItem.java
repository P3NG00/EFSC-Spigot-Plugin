package com.p3ng00.efsc.module.abbacaving.util;

import com.p3ng00.p3plugin.util.Sound;
import org.bukkit.Material;

public class GameItem {

    public Sound.Info soundInfo;
    public int multiplier;
    public boolean broadcast;
    public Material[] materials;

    public GameItem(Sound.Info soundInfo, int multiplier, boolean broadcast, Material... materials) {
        this.soundInfo = soundInfo;
        this.multiplier = multiplier;
        this.broadcast = broadcast;
        this.materials = materials;
    }
}