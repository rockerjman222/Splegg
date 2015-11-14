package me.rockerjman222.Splegg.event;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private final Splegg splegg;

    public InventoryListener(Splegg splegg) {
        this.splegg = splegg;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if(!(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("splegg shop")))
            return;

        Player player = (Player) event.getWhoClicked();

        switch(event.getCurrentItem().getType()) {
            case DIAMOND_SPADE:
                //TODO: fix bug
                /*
                 * You can buy an item once, and it bothers me that
                 * you can buy the same item from your inventory
                 * in the shop inventory
                 */

                if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("diamond splegg")) {
                    event.setCancelled(true);
                    if (player.getInventory().firstEmpty() != -1) {
                        //TODO: Remove cash
                        player.getInventory().addItem(Utils.getDiamondSplegg());
                        player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the diamond splegg for " + ChatColor.DARK_AQUA + Utils.Config.DIAMOND_SPLEGG_COST + ChatColor.GOLD + " coins");
                    } else {
                        player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have nay room in your inventory!");
                    }
                }
                break;
            case GOLD_SPADE:

                if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("gold splegg")) {
                    event.setCancelled(true);
                    if (player.getInventory().firstEmpty() != -1) {
                        //TODO: Remove cash
                        player.getInventory().addItem(Utils.getGoldSplegg());
                        player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the gold splegg for " + ChatColor.DARK_AQUA + Utils.Config.GOLD_SPLEGG_COST + ChatColor.GOLD + " coins");
                    } else {
                        player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have nay room in your inventory!");
                    }
                }
                break;
            case STONE_SPADE:

                if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("stone splegg")) {
                    event.setCancelled(true);
                    if (player.getInventory().firstEmpty() != -1) {
                        //TODO: Remove cash
                        player.getInventory().addItem(Utils.getStoneSplegg());
                        player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the stone splegg for " + ChatColor.DARK_AQUA + Utils.Config.STONE_SPLEGG_COST + ChatColor.GOLD + " coins");
                    } else {
                        player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have nay room in your inventory!");
                    }
                }
                break;
        }

    }
}
