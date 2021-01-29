package com.p3ng00.efsc.module.ultimatetag.command;

import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.GameCommand;
import com.p3ng00.efsc.module.ultimatetag.game.UltimateTagGame;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.p3ng00.efsc.EFSC.ULTIMATE_TAG;

public class UltimateTagCommand extends GameCommand {

    public UltimateTagCommand() {
        super("UltimateTag");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        return onCommand(sender, args, ULTIMATE_TAG);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return onTabComplete(sender, args, ULTIMATE_TAG);
    }

    @Override
    protected void startGame() {
        ULTIMATE_TAG.TASK_GAME = new UltimateTagGame().runTaskTimer(EFSC.INSTANCE, 5, 1); // todo
    }
}
