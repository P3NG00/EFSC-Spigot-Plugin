package com.p3ng00.efsc.module.abbacaving.task;

import com.p3ng00.efsc.EFSC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.p3ng00.efsc.EFSC.ABBA_CAVING;
import static com.p3ng00.efsc.module.abbacaving.ABBACaving.*;
import static com.p3ng00.efsc.module.abbacaving.task.GameTask.Phase.*;
import static com.p3ng00.p3plugin.util.Chat.broadcastMessageToList;
import static com.p3ng00.p3plugin.util.Sound.broadcastSoundToList;

public class GameTask extends BukkitRunnable {

    public static final String[] TITLES = {
        ChatColor.RED.toString() + ChatColor.BOLD + "1",
        ChatColor.RED + "2",
        ChatColor.RED + "3",
        ChatColor.YELLOW + "4",
        ChatColor.YELLOW + "5",
        ChatColor.YELLOW + "6",
        ChatColor.GREEN + "7",
        ChatColor.GREEN + "8",
        ChatColor.GREEN + "9",
        ChatColor.GREEN + "10"
    };

    private Phase phase;
    private int timer;
    private final int gameLength;
    private final int halfway;

    private Player winner;

    public GameTask() {
        phase = PREGAME;

        timer = 10; // Pregame

        gameLength = ABBA_CAVING.GAME_LENGTH * 60;
        halfway = gameLength / 2;

        winner = null;

    }

    @Override
    public void run() {

        switch (phase) {

            case PREGAME:

                if (timer > 0) {

                    displayTitleToParty(TITLES[timer - 1], "", 1);
                    broadcastSoundToList(ABBA_CAVING.PARTY, TICK);
                    timer--;

                } else {

                    displayTitleToParty("", ChatColor.GREEN.toString() + ChatColor.BOLD + "GO!", 1);
                    broadcastSoundToList(ABBA_CAVING.PARTY, START);
                    timer = gameLength;
                    phase = GAME;
                    String s = "";

                    if (timer > 60)
                        s = "s";

                    broadcastMessageToList(ABBA_CAVING.PARTY, String.format(GAME_START, gameLength / 60, s));
                    new TimerTask(timer).runTaskTimer(EFSC.INSTANCE, 0, 20);

                }

                break;

            case GAME:

                if (timer > 1) {

                    timer--;

                    if (timer == halfway) {

                        broadcastMessageToList(ABBA_CAVING.PARTY, GAME_HALF);
                        broadcastSoundToList(ABBA_CAVING.PARTY, START);
                        ABBA_CAVING.showScoreboard(false);

                    }

                    if (timer == 300 || timer == 180 || timer == 60) {

                        String s = "";

                        if (timer != 60)
                            s = "s";

                        broadcastMessageToList(ABBA_CAVING.PARTY, String.format(GAME_TIME_WARN, timer / 60, s));

                    }

                } else {

                    winner = ABBA_CAVING.getWinner();
                    broadcastSoundToList(ABBA_CAVING.PARTY, SUSPENSE);
                    displayTitleToParty(GAME_END_TITLE, GAME_END_SUBTITLE, 3);
                    timer = 6;
                    phase = POSTGAME;

                }

                break;

            case POSTGAME:

                switch (timer) {

                    case 1:

                        broadcastSoundToList(ABBA_CAVING.PARTY, END);
                        displayTitleToParty(String.format(GAME_WIN_TITLE, winner.getName()), String.format(GAME_WIN_SUBTITLE, ABBA_CAVING.calculateScore(winner)), 5);
                        ABBA_CAVING.showScoreboard(true);
                        break;

                    case 0:

                        ABBA_CAVING.PARTY.clear();
                        cancel();
                        break;

                }

                timer--;

                break;

        }

    }

    public static void displayTitleToParty(String title, String subtitle, int length) {

        for (Player p : ABBA_CAVING.PARTY)
            p.sendTitle(title, subtitle, 0, length * 20, 20);

    }

    enum Phase {

        PREGAME,
        GAME,
        POSTGAME

    }

}
