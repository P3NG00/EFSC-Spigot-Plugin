package com.p3ng00.efsc.module.abbacaving.task;

import com.p3ng00.p3plugin.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.MutablePair;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.p3ng00.efsc.EFSC.ABBA_CAVING;

public class ScoreboardTask extends BukkitRunnable {

    private org.bukkit.scoreboard.Scoreboard scoreboard;
    private Objective objective;
    private boolean shown;
    private final boolean showPoints;

    public ScoreboardTask(boolean showPoints) {

        shown = false;
        this.showPoints = showPoints;

    }

    @Override
    public void run() {

        if (!shown) {

            ScoreboardManager manager = Bukkit.getScoreboardManager();

            if (manager == null) {

                Chat.broadcastMessageToList(ABBA_CAVING.PARTY, ChatColor.RED + "Error getting scoreboard manager.");
                shown = true;
                return;

            }

            scoreboard = manager.getMainScoreboard();
            objective = scoreboard.getObjective("abbacaving");

            if (objective != null)
                objective.unregister();

            objective = scoreboard.registerNewObjective("abbacaving", "dummy", "AbbaCaving");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            if (showPoints) {

                for (Player p : ABBA_CAVING.PARTY) {

                    if (p.isOnline()) {

                        Score score = objective.getScore(p.getDisplayName());
                        score.setScore(ABBA_CAVING.calculateScore(p));
                        p.setScoreboard(scoreboard);

                    }

                }

            } else {

                List<MutablePair<Player, Integer>> scoreList = new ArrayList<>();

                for (Player p : ABBA_CAVING.PARTY)
                    scoreList.add(new MutablePair<>(p, ABBA_CAVING.calculateScore(p)));

                scoreList.sort(Comparator.comparingInt(MutablePair::getRight));

                for (int i = 0; i < scoreList.size(); i++) {

                    Player p = scoreList.get(i).getLeft();
                    Score score = objective.getScore(p.getDisplayName());
                    score.setScore(i + 1);
                    p.setScoreboard(scoreboard);

                }

            }

        } else {

            objective.unregister();

            for (Player p : ABBA_CAVING.PARTY)
                p.setScoreboard(scoreboard);

            cancel();

        }

        shown = true;

    }

}
