package com.p3ng00.efsc.module.snowballfight.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.GameCommand;
import com.p3ng00.efsc.module.snowballfight.task.SnowballFightTask;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.p3ng00.efsc.EFSC.SNOWBALL_FIGHT;

public class SnowballFightCommand extends GameCommand {

    public SnowballFightCommand() {
        super("SnowballFight");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        return onCommand(sender, args, SNOWBALL_FIGHT);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return onTabComplete(args, SNOWBALL_FIGHT);
    }

    @Override
    protected void startGame() {
        SNOWBALL_FIGHT.TASK_GAME = new SnowballFightTask().runTaskTimer(EFSC.INSTANCE, 5, 1);
    }

}
