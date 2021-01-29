package com.p3ng00.efsc.module.abbacaving.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.GameCommand;
import com.p3ng00.efsc.module.abbacaving.task.GameTask;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.p3ng00.efsc.EFSC.ABBA_CAVING;

public class ABBACommand extends GameCommand {

    public ABBACommand() {
        super("ABBA");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        return onCommand(sender, args, ABBA_CAVING);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return onTabComplete(args, ABBA_CAVING);
    }

    @Override
    protected void startGame() {
        ABBA_CAVING.TASK_GAME = new GameTask().runTaskTimer(EFSC.INSTANCE, 5, 20);
    }
}
