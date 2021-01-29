package com.p3ng00.efsc.module.playerheads;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.playerheads.command.HeadCommand;
import com.p3ng00.efsc.module.playerheads.listener.PlayerHeadsListener;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class PlayerHeads extends Module {

    // 0 - Trade Use Min
    // 1 - Trade Use Vary
    // 2 - Trade Price Min
    // 3 - Trade Price Vary
    public int[] tradeSettings;
    public double tradeChance;

    public PlayerHeads() {
        super("Player Heads", new PlayerHeadsListener(), new HeadCommand());
    }

    @Override
    public boolean enable() {
        tradeSettings = new int[4];
        tradeSettings[0] = CONFIG.getInt(createPath("trade_use_min"));
        tradeSettings[1] = CONFIG.getInt(createPath("trade_use_vary"));
        tradeSettings[2] = CONFIG.getInt(createPath("trade_price_min"));
        tradeSettings[3] = CONFIG.getInt(createPath("trade_price_vary"));
        tradeChance = CONFIG.getDouble(createPath("trade_chance"));
        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("trade_use_min"), tradeSettings[0]);
        CONFIG.set(createPath("trade_use_vary"), tradeSettings[1]);
        CONFIG.set(createPath("trade_price_min"), tradeSettings[2]);
        CONFIG.set(createPath("trade_price_vary"), tradeSettings[3]);
        CONFIG.set(createPath("trade_chance"), tradeChance);
    }
}
