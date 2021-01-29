package com.p3ng00.efsc.module.abbacaving;

import com.p3ng00.p3plugin.util.Sound.Info;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.GameModule;
import com.p3ng00.efsc.module.abbacaving.command.ABBACommand;
import com.p3ng00.efsc.module.abbacaving.listener.ABBACavingListener;
import com.p3ng00.efsc.module.abbacaving.task.ScoreboardTask;
import com.p3ng00.efsc.module.abbacaving.util.GameItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;

import static org.bukkit.Material.*;

public class ABBACaving extends GameModule {

    // Sounds
    public static Info TICK = new Info(Sound.BLOCK_NOTE_BLOCK_BIT);
    public static Info START = new Info(Sound.ENTITY_PLAYER_LEVELUP, 1.25f, 1.5f);
    public static Info SUSPENSE = new Info(Sound.BLOCK_BELL_RESONATE, 0.75f, 0.25f);
    public static Info END = new Info(Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.2f, 1.0f);

    public static Info COMMON = new Info(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f);
    public static Info UNCOMMON = new Info(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f);
    public static Info RARE = new Info(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
    public static Info EPIC = new Info(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5f);
    public static Info NOWTHISISEPIC = new Info(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f);
    public static Info NUMBER1VICTORYROYALE = new Info(Sound.BLOCK_BELL_USE, 2.0f);

    // Game variables and objects
    public static final GameItem[] ITEM_LIST = new GameItem[]{
            new GameItem(COMMON, 1, false, IRON_ORE),
            new GameItem(UNCOMMON, 2, false, GOLD_ORE, LAPIS_ORE, MUSIC_DISC_13, MUSIC_DISC_CAT, MUSIC_DISC_BLOCKS, MUSIC_DISC_CHIRP, MUSIC_DISC_FAR, MUSIC_DISC_MALL, MUSIC_DISC_MELLOHI, MUSIC_DISC_STAL, MUSIC_DISC_STRAD, MUSIC_DISC_WARD, MUSIC_DISC_11, MUSIC_DISC_WAIT),
            new GameItem(RARE, 3, false, REDSTONE_ORE, NAME_TAG),
            new GameItem(EPIC, 5, false, DIAMOND_ORE, EMERALD_ORE),
            new GameItem(NOWTHISISEPIC, 6, false, GOLDEN_APPLE),
            new GameItem(NUMBER1VICTORYROYALE, 20, true, ENCHANTED_GOLDEN_APPLE, SPAWNER)
    };

    public ABBACaving() {
        super("ABBA Caving", 2, new ABBACavingListener(), new ABBACommand());
    }

    @Override
    public boolean enable() {

        ScoreboardManager manager = Bukkit.getScoreboardManager();

        if (manager != null) {

            org.bukkit.scoreboard.Scoreboard scoreboard = manager.getMainScoreboard();
            Objective objective = scoreboard.getObjective("abbacaving");

            if (objective != null)
                objective.unregister();

        }

        return super.enable();

    }

    public int calculateScore(Player player) {

        int score = 0;

        for (ItemStack itemStack : player.getInventory().getContents()) {

            if (itemStack != null) {

                if (isCaveSpiderSpawner(itemStack))
                    score += itemStack.getAmount() * 15;
                else {

                    for (GameItem gi : ITEM_LIST) {

                        for (Material m : gi.materials) {

                            if (itemStack.getType() == m)
                                score += itemStack.getAmount() * gi.multiplier;

                        }

                    }

                }

            }

        }

        return score;

    }

    public Player getWinner() {

        Player player = EFSC.ABBA_CAVING.PARTY.get(0);
        int[] p = new int[2];

        for (Player pl : EFSC.ABBA_CAVING.PARTY) {

            p[0] = calculateScore(player);
            p[1] = calculateScore(pl);

            if (p[1] > p[0])
                player = pl;

        }

        return player;
    }

    public void showScoreboard(boolean showPoints) {
        new ScoreboardTask(showPoints).runTaskTimer(EFSC.INSTANCE, 0, 200);
    }

    public boolean isCaveSpiderSpawner(ItemStack itemStack) {
        return itemStack.getType() == SPAWNER && itemStack.getItemMeta() != null && ChatColor.stripColor(itemStack.getItemMeta().getDisplayName().toLowerCase()).equals("[cave spider spawner]");
    }

}
