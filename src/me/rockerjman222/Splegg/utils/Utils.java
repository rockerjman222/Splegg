package me.rockerjman222.Splegg.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {

    private static ItemStack diamondSplegg;
    private static ItemStack goldSplegg;
    private static ItemStack stoneSplegg;


    static {

        diamondSplegg = new ItemStack(Material.DIAMOND_SPADE);
        ItemMeta diamondSpleggMeta = diamondSplegg.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        diamondSpleggMeta.setDisplayName(ChatColor.DARK_AQUA + "Diamond Splegg");
        lore.add(ChatColor.DARK_PURPLE + "Costs 100 coins!");
        lore.add(ChatColor.AQUA + "Shoots three snowballs!");

        diamondSpleggMeta.setLore(lore);
        diamondSplegg.setItemMeta(diamondSpleggMeta);


        goldSplegg = new ItemStack(Material.GOLD_SPADE);
        ItemMeta goldSpleggMeta = goldSplegg.getItemMeta();
        lore.clear();

        goldSpleggMeta.setDisplayName(ChatColor.GOLD + "Gold Splegg");
        lore.add(ChatColor.DARK_PURPLE + "Costs 75 coins!");
        lore.add(ChatColor.AQUA + "Shoots two snowballs!");

        goldSpleggMeta.setLore(lore);
        goldSplegg.setItemMeta(goldSpleggMeta);

        stoneSplegg = new ItemStack(Material.STONE_SPADE);
        ItemMeta stoneSpleggMeta = stoneSplegg.getItemMeta();
        lore.clear();

        stoneSpleggMeta.setDisplayName(ChatColor.GRAY + "Stone Splegg");
        lore.add(ChatColor.DARK_PURPLE + "Costs 50 coins!");
        lore.add(ChatColor.AQUA + "Shoots one snowball!");

        stoneSpleggMeta.setLore(lore);
        stoneSplegg.setItemMeta(stoneSpleggMeta);

    }

    public static String getPrefix() {
        return ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "Splegg" + ChatColor.DARK_AQUA + "] ";
    }

    public static String getName() {
        return ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "Splegg" + ChatColor.DARK_AQUA + "]";
    }

    public static void openShopGui(UUID playerUUID) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);

        if(!player.isOnline()) {
            Log.info("Tried to open shop inventory to offline player: " + player.getName());
            return;
        }

        Inventory i = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Splegg Shop");

        ItemStack diamond = Utils.getDiamondSplegg();

        ItemMeta diamondMeta = diamond.getItemMeta();

        //this won't work
        //diamondMeta.getLore().add("shopItem");
        List<String> lore = diamondMeta.getLore();
        lore.add("shopItem");
        diamondMeta.setLore(lore);
        diamond.setItemMeta(diamondMeta);

        i.setItem(5, diamond);
        i.setItem(4, Utils.getGoldSplegg());
        i.setItem(3, Utils.getStoneSplegg());

        player.getPlayer().openInventory(i);
    }

    public static String getHeader(String title) {
        String insert = ChatColor.GOLD + "[" + ChatColor.AQUA + title + ChatColor.GOLD + "]";

        int insertLength = ChatColor.stripColor(insert).length();
        int s = (53 - insertLength);
        int ta = Math.round((s / 2));

        String o = "";

        for (int i = 0; i < ta; i++) {
            o = o + "-";
        }

        return ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + o + insert + ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + o;

    }

    public static String getEnd() {
        return ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------";
    }

    public static int getMinutes(int time) {
        if (time < 60)
            return time;

        return time / 60;
    }

    public static String shouldBeMinutes(int amount) {
        if (amount > 60)
            return "minutes";

        if (amount == 60)
            return "minute";

        if (amount == 1)
            return "second";

        return "seconds";
    }

    public static ItemStack getDiamondSplegg() {

        return diamondSplegg;
    }
    public static ItemStack getGoldSplegg() {

        return goldSplegg;
    }
    public static ItemStack getStoneSplegg() {

        return stoneSplegg;
    }

    public static class Config {
        public static int DEFAULT_GAME_TIME = 20; //300
        public static int MAX_PLAYERS = 15;
        public static int MIN_PLAYERS = 2;

        public static int DIAMOND_SPLEGG_COST = 100;
        public static int GOLD_SPLEGG_COST = 75;
        public static int STONE_SPLEGG_COST = 50;
    }

}
