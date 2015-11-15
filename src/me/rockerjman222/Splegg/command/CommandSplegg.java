package me.rockerjman222.Splegg.command;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.game.Game;
import me.rockerjman222.Splegg.utils.SpleggFormatting;
import me.rockerjman222.Splegg.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandSplegg implements TabCompleter, CommandExecutor {

    private final Splegg splegg;

    private final String[] usages = {
            Utils.getHeader("Splegg Help"),
            ChatColor.GOLD + "/splegg help" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Lists the help menu for splegg",
            ChatColor.GOLD + "/splegg shop" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Opens the shop Gui for splegg",
            Utils.getEnd()
    };

    public CommandSplegg(Splegg splegg) {
        this.splegg = splegg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;

        Player player = ((Player) sender);

        if(args.length < 1) {
            player.sendMessage(usages);
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "help":

                player.sendMessage(usages);
                break;
            case "shop":

                Utils.openShopGui(player.getUniqueId());
                break;
            case "start":

                this.splegg.dataHolder.generateNormalGame(new Game(this.splegg.dataHolder.queuedPlayers, Utils.Config.DEFAULT_GAME_TIME));
                break;

            case "cancel":

                player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "Canceled all games.");
                this.splegg.dataHolder.cancelAllGames();
                break;

            case "join":

                player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've joined the game queue!");
                this.splegg.dataHolder.queuedPlayers.add(player.getUniqueId());
                player.sendMessage(this.splegg.dataHolder.queuedPlayers.toString());
                break;

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
