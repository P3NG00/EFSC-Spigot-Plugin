package com.p3ng00.efsc.module.textcolors.listener;

import com.p3ng00.p3plugin.util.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static com.p3ng00.efsc.EFSC.TEXT_COLORS;

public class TextColorsListener implements Listener {

    @EventHandler
    public void onItemRename(PrepareAnvilEvent event) {

        if (!TEXT_COLORS.isEnabled() || !TEXT_COLORS.getAnvil())
            return;

        ItemStack result = event.getResult();

        if (result == null)
            return;

        ItemMeta meta = result.getItemMeta();

        if (meta == null)
            return;

        meta.setDisplayName(Text.colorFormat(meta.getDisplayName()));
        result.setItemMeta(meta);
        event.setResult(result);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!TEXT_COLORS.isEnabled() || !TEXT_COLORS.getAnvil() || !(event.getClickedInventory() instanceof AnvilInventory) || event.getSlotType() != InventoryType.SlotType.RESULT)
            return;

        ItemStack stack = event.getCurrentItem();

        if (stack == null)
            return;

        ItemMeta meta = stack.getItemMeta();

        if (meta == null)
            return;

        Material[] dyes = {
                Material.BLACK_DYE,     //  0
                Material.BLUE_DYE,      //  1
                Material.GREEN_DYE,     //  2
                Material.CYAN_DYE,      //  3
                Material.RED_DYE,       //  4
                Material.PURPLE_DYE,    //  5
                Material.ORANGE_DYE,    //  6
                Material.LIGHT_GRAY_DYE,//  7
                Material.GRAY_DYE,      //  8
                Material.LIME_DYE,      //  9
                Material.LIGHT_BLUE_DYE,// 10
                Material.YELLOW_DYE,    // 11
                Material.WHITE_DYE      // 12
        };

        int[] need = new int[dyes.length];
        int[] has = new int[dyes.length];
        Arrays.fill(need, 0);
        Arrays.fill(has, 0);
        char[] array = meta.getDisplayName().toCharArray();

        for (int i = 0; i < array.length; i++) {

            if (array[i] == '§' && i + 1 <= array.length - 1 && ((array[i + 1] >= '0' && array[i + 1] <= '9') || (array[i + 1] >= 'a' && array[i + 1] <= 'f'))) {

                ChatColor color = ChatColor.getByChar(array[++i]);

                if (color == null)
                    return;

                switch (color) {

                    case BLACK:

                        need[0]++;
                        break;

                    case DARK_BLUE:

                        need[1]++;
                        need[0]++;
                        break;

                    case DARK_GREEN:

                        need[2]++;
                        break;

                    case DARK_AQUA:

                        need[3]++;
                        break;

                    case DARK_RED:

                        need[4]++;
                        need[0]++;
                        break;

                    case DARK_PURPLE:

                        need[5]++;
                        need[0]++;
                        break;

                    case GOLD:

                        need[6]++;
                        break;

                    case GRAY:

                        need[7]++;
                        break;

                    case DARK_GRAY:

                        need[8]++;
                        break;

                    case BLUE:

                        need[1]++;
                        break;

                    case GREEN:

                        need[9]++;
                        break;

                    case AQUA:

                        need[10]++;
                        break;

                    case RED:

                        need[4]++;
                        break;

                    case LIGHT_PURPLE:

                        need[5]++;
                        break;

                    case YELLOW:

                        need[11]++;
                        break;

                    case WHITE:

                        need[12]++;
                        break;

                }

            }

        }

        for (ItemStack is : event.getWhoClicked().getInventory().getContents()) {

            if (is != null) {

                switch (is.getType()) {

                    case BLACK_DYE:

                        has[0] += is.getAmount();
                        break;

                    case BLUE_DYE:

                        has[1] += is.getAmount();
                        break;

                    case GREEN_DYE:

                        has[2] += is.getAmount();
                        break;

                    case CYAN_DYE:

                        has[3] += is.getAmount();
                        break;

                    case RED_DYE:

                        has[4] += is.getAmount();
                        break;

                    case PURPLE_DYE:

                        has[5] += is.getAmount();
                        break;

                    case ORANGE_DYE:

                        has[6] += is.getAmount();
                        break;

                    case LIGHT_GRAY_DYE:

                        has[7] += is.getAmount();
                        break;

                    case GRAY_DYE:

                        has[8] += is.getAmount();
                        break;

                    case LIME_DYE:

                        has[9] += is.getAmount();
                        break;

                    case LIGHT_BLUE_DYE:

                        has[10] += is.getAmount();
                        break;

                    case YELLOW_DYE:

                        has[11] += is.getAmount();
                        break;

                    case WHITE_DYE:
                        has[12] += is.getAmount();
                        break;

                }

            }

        }

        if (((Player)event.getWhoClicked()).getLevel() >= ((AnvilInventory)event.getClickedInventory()).getRepairCost()) {

            boolean hasNeed = true;

            for (int i = 0; i < dyes.length; i++) {

                if (has[i] < need[i]) {

                    hasNeed = false;
                    event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Need " + need[i] + " " + dyes[i].name());
                    event.setCancelled(true);

                }

            }

            if (hasNeed) {

                for (int i = 0; i < dyes.length; i++)
                    event.getWhoClicked().getInventory().removeItem(new ItemStack(dyes[i], need[i]));

            }

        }

    }

    @EventHandler
    public void onBookSigned(PlayerEditBookEvent event) {

        if (TEXT_COLORS.isEnabled() && TEXT_COLORS.getBook() && event.isSigning()) {

            BookMeta book = event.getNewBookMeta();

            for (int i = 1; i <= book.getPageCount(); i++)
                book.setPage(i, Text.colorFormat(book.getPage(i)));

            book.setTitle(Text.colorFormat(book.getTitle()));
            event.setNewBookMeta(book);

        }

    }

    @EventHandler
    public void onChatSent(AsyncPlayerChatEvent event) {

        if (TEXT_COLORS.isEnabled() && TEXT_COLORS.getChat())
            event.setMessage(Text.colorFormat(event.getMessage()));

    }

    @EventHandler
    public void onSignWritten(SignChangeEvent event) {

        if (TEXT_COLORS.isEnabled() && TEXT_COLORS.getSign()) {

            for (int i = 0; i < event.getLines().length; i++)
                event.setLine(i, Text.colorFormat(event.getLine(i)));

        }

    }

}
