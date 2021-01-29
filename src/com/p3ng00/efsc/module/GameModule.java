package com.p3ng00.efsc.module;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.p3plugin.util.Text;
import com.p3ng00.efsc.EFSC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;
import static com.p3ng00.p3plugin.util.Chat.broadcastMessageToList;

public abstract class GameModule extends Module {

    public static final String GAME_START = ChatColor.GREEN + "Game of %d minute%s has started!"; // String.format(GAME_START, gameLength, plural?)
    public static final String GAME_END_TITLE = ChatColor.DARK_RED + "GAME HAS ENDED!";
    public static final String GAME_END_SUBTITLE = ChatColor.GOLD + "Winner is being decided!";
    public static final String GAME_WIN_TITLE = ChatColor.GOLD + "%s won!"; // String.format(GAME_WIN_TITLE, winnerName)
    public static final String GAME_WIN_SUBTITLE = ChatColor.LIGHT_PURPLE + "%d"; // String.format(GAME_WIN_SUBTITLE, winnerPoints)
    public static final String GAME_HALF = ChatColor.AQUA + "Game is halfway over!";
    public static final String GAME_TIME_WARN = ChatColor.RED + "%d minute%s remaining!"; // String.format(GAME_TIME_WARN, remainingMins, plural?)

    public static final String PARTY_JOIN = ChatColor.GREEN + "%s has joined the party!"; // String.format(PARTY_JOIN, player)
    public static final String PARTY_LEAVE = ChatColor.YELLOW + "%s has left the party."; // String.format(PARTY_LEAVE, player)
    public static final String PARTY_KICK = ChatColor.YELLOW + "%s was kicked from the party."; // String.format(PARTY_KICK, player)

    public static final String ERROR_JOINED = ChatColor.RED + "Already in party.";
    public static final String ERROR_NOT_JOINED = ChatColor.RED + "Not in party";
    public static final String ERROR_ACTIVE = ChatColor.RED + "Game is currently in progress.";
    public static final String ERROR_EMPTY = ChatColor.RED + "No one in party.";
    public static final String ERROR_NUMBER = ChatColor.RED + "Must enter an integer.";
    public static final String ERROR_NOT_ENOUGH_PLAYERS = ChatColor.RED + "Not enough players in party.";

    public static final String SETTING_LENGTH_SET = ChatColor.AQUA + "%s set the game length to %d minutes."; // String.format(SETTING_LENGTH_SET, player, mins)
    public static final String SETTING_LENGTH_DISPLAY = ChatColor.AQUA + "Game is currently set to %d minutes."; // String.format(SETTING_LENGTH_DISPLAY, mins)

    public List<Player> PARTY = new ArrayList<>();
    public BukkitTask TASK_GAME = null;
    public int GAME_LENGTH;
    public int MIN_PARTY_SIZE;

    public GameModule(String title, int minPartySize, Listener listener, P3Command... commands) {
        super(title, listener, commands);
        GAME_LENGTH = CONFIG.getInt(createPath("game_length"));
        MIN_PARTY_SIZE = minPartySize;
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("game_length"), GAME_LENGTH);
    }

    public boolean isGameActive() {
        return TASK_GAME != null && !TASK_GAME.isCancelled();
    }

    public String partyJoin(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (isGameActive()) {
                return ERROR_ACTIVE;
            } else if (PARTY.contains(player)) {
                return ERROR_JOINED;
            } else {
                PARTY.add(player);
                broadcastMessageToList(PARTY, String.format(PARTY_JOIN, player.getName()));
                return null;
            }
        } else {
            return EFSC.ERROR_SENDER;
        }
    }

    public String partyLeave(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (PARTY.contains(player)) {
                broadcastMessageToList(PARTY, String.format(PARTY_LEAVE, player.getName()));
                PARTY.remove(player);
                return null;
            } else {
                return ERROR_NOT_JOINED;
            }
        } else {
            return EFSC.ERROR_SENDER;
        }
    }

    public String partyKick(Player kick) {
        if (PARTY.contains(kick)) {
            broadcastMessageToList(PARTY, String.format(PARTY_KICK, kick.getName()));
            PARTY.remove(kick);
            return null;
        } else {
            return ERROR_NOT_JOINED;
        }
    }

    public String getPartyList() {
        if (PARTY.size() == 0) {
            return ERROR_EMPTY;
        }
        List<String> names = new ArrayList<>();
        for (Player p : PARTY) {
            names.add(p.getName());
        }
        return Text.colorFormat(Text.createList(PARTY.size() >= MIN_PARTY_SIZE ? ChatColor.GREEN.toString() : ChatColor.YELLOW.toString(), "&r, ", names));
    }

    public String setMinutes(Player player, int minutes) {
        if (!isGameActive()) {
            GAME_LENGTH = minutes;
            broadcastMessageToList(PARTY, String.format(SETTING_LENGTH_SET, player.getName(), GAME_LENGTH));
            return null;
        } else {
            return ERROR_ACTIVE;
        }
    }

    public String showMinutes() {
        return String.format(SETTING_LENGTH_DISPLAY, GAME_LENGTH);
    }
}
