package com.p3ng00.efsc.module.hat;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.hat.command.HatsCommand;
import com.p3ng00.efsc.module.hat.listener.HatsListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class Hats extends Module {

    public boolean RIDING;

    public Hats() {
        super("Hats", new HatsListener(), new HatsCommand());
    }

    @Override
    public boolean enable() {
        RIDING = CONFIG.getBoolean(createPath("allow_riding"));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isEnabled()) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getPassengers().size() != 0) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, p.getPassengers().size() - 1));
                        }
                    }
                }
            }
        }.runTaskTimer(EFSC.INSTANCE, 20, 20);
        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("allow_riding"), RIDING);
    }
}
