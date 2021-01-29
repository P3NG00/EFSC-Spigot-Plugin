package com.p3ng00.efsc.module.textcolors.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.Util;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.p3ng00.efsc.EFSC.ERROR_NOT_OP;
import static com.p3ng00.efsc.EFSC.TEXT_COLORS;

public class TextColorsCommand extends P3Command {

    public TextColorsCommand() {
        super("TextColors");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {

        if (!TEXT_COLORS.isEnabled())
            return Module.DISABLED;
        else if (!sender.isOp())
            return ERROR_NOT_OP;
        else if (args.length != 2) {

            StringBuilder sb = new StringBuilder();
            sb.append(  "Anvil - ").append(Util.getEnabledAsString(TEXT_COLORS.getAnvil()));
            sb.append("\nBook - ").append(Util.getEnabledAsString(TEXT_COLORS.getBook()));
            sb.append("\nChat - ").append(Util.getEnabledAsString(TEXT_COLORS.getChat()));
            sb.append("\nSign - ").append(Util.getEnabledAsString(TEXT_COLORS.getSign()));
            return sb.append(ChatColor.AQUA).append("\n/TextColors <Option> [Enable/Disable]").toString();

        } else {

            boolean enable;

            try {
                enable = Util.getEnabledOrDisabled(args[1]);
            } catch (IllegalArgumentException e) {
                return ChatColor.RED + "[Enable/Disable]";
            }

            String enabled = enable ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled";
            switch (args[0].toLowerCase()) {

                case "anvil":

                    TEXT_COLORS.setAnvil(enable);
                    return "Anvil - " + enabled;

                case "book":

                    TEXT_COLORS.setBook(enable);
                    return "Book - " + enabled;

                case "chat":

                    TEXT_COLORS.setChat(enable);
                    return "Chat - " + enabled;

                case "sign":

                    TEXT_COLORS.setSign(enable);
                    return "Sign - " + enabled;

                default:
                    return ChatColor.RED + "/TextColors [Anvil/Book/Chat/Sign] [Enable/Disable]";

            }

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        List<String> list = new ArrayList<>();

        if (sender.isOp()) {

            switch (args.length) {

                case 1:

                    list.add("anvil");
                    list.add("book");
                    list.add("chat");
                    list.add("sign");
                    break;

                case 2:

                    list.add("enable");
                    list.add("disable");
                    break;

            }

        }

        return list;

    }

}
