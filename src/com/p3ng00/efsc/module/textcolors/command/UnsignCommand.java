package com.p3ng00.efsc.module.textcolors.command;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.EFSC;
import com.p3ng00.efsc.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class UnsignCommand extends P3Command {

    public UnsignCommand() {
        super("Unsign");
    }

    @Override
    public String onCommand(CommandSender sender, String[] args) {
        if (!EFSC.TEXT_COLORS.isEnabled()) {
            return Module.DISABLED;
        } else if (!(sender instanceof Player)) {
            return EFSC.ERROR_SENDER;
        } else if (((Player)sender).getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK) {
            ItemStack book = ((Player)sender).getInventory().getItemInMainHand();
            BookMeta meta = (BookMeta)book.getItemMeta();
            if (meta == null) {
                return ChatColor.RED + "Error: no meta";
            } else if (((Player)sender).getDisplayName().equals(meta.getAuthor())) {
                ((Player)sender).getInventory().getItemInMainHand().setType(Material.WRITABLE_BOOK);
                return ChatColor.GREEN + "Unsigned";
            } else {
                return ChatColor.RED + "Must be the author of the book";
            }
        } else {
            return ChatColor.RED + "Must be holding a written book!";
        }
    }
}
