package me.rockerjman222.Splegg.command;

import com.google.common.collect.ImmutableList;
import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.game.Game;
import me.rockerjman222.Splegg.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSplegg implements CommandExecutor, TabCompleter {

    private final Splegg splegg;

    private final String[] tabCompletes = {
		    "help",
		    "shop",
		    "join",
		    "leave",
		    "spectate"
    };

    private final String[] usages = {
            Utils.getHeader("Splegg Help"),
            ChatColor.GOLD + "/splegg help" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Lists the help menu for splegg",
            ChatColor.GOLD + "/splegg shop" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Opens the shop Gui for splegg",
            ChatColor.GOLD + "/splegg join" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Joins the queue for the game",
            ChatColor.GOLD + "/splegg leave" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Leaves the queue for the game",
            ChatColor.GOLD + "/splegg spectate" + ChatColor.DARK_GRAY + "-" + ChatColor.ITALIC + "" + ChatColor.GRAY + "Spectates an ongoing game",
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
            case "join":

                if (!this.splegg.dataHolder.queuedPlayers.contains(player.getUniqueId())) {
                    player.sendMessage(Utils.getPrefix() + ChatColor.DARK_AQUA + "You've joined the game queue!");
                    this.splegg.dataHolder.queuedPlayers.add(player.getUniqueId());
                } else {
                    player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You've already joined the queue!");
                }
                player.sendMessage(this.splegg.dataHolder.queuedPlayers.toString());
                break;
            case "leave":

                player.sendMessage(Utils.getPrefix() + ChatColor.DARK_AQUA + "You've left the game queue!");
                if (this.splegg.dataHolder.queuedPlayers.contains(player.getUniqueId()))
                    this.splegg.dataHolder.queuedPlayers.remove(player.getUniqueId());
                else
                    player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You've not joined the game queue!");
                break;
            case "spectate":

                if (!this.splegg.dataHolder.spectatingPlayers.contains(player.getUniqueId())) {
                    player.sendMessage(Utils.getPrefix() + ChatColor.DARK_AQUA + "You are now spectating the current game!");
                    this.splegg.dataHolder.spectatingPlayers.add(player.getUniqueId());
                } else {
                    player.sendMessage(Utils.getPrefix() + ChatColor.DARK_AQUA + "You are no longer spectating the current game!");
                    this.splegg.dataHolder.spectatingPlayers.remove(player.getUniqueId());
                }
                break;
            case "start":
                this.splegg.dataHolder.players = this.splegg.dataHolder.queuedPlayers;
                this.splegg.dataHolder.generateNormalGame(new Game(this.splegg.arena, this.splegg.dataHolder.players, Utils.Config.DEFAULT_GAME_TIME));
                break;

            case "cancel":

	            if(!player.hasPermission("splegg.admin")){

		            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
		            return true;
	            }
                player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "Canceled all games.");
                this.splegg.dataHolder.cancelAllGames();
                break;
            default:
                player.sendMessage(usages);
                break;

        }

        return true;
    }

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {

		if (commandSender instanceof Player && args.length == 1) {

			String search = args[0].toLowerCase();

			List<String> stringSet = new ArrayList<>();

			for (String s : tabCompletes) {

				if (s.startsWith(search))
					stringSet.add(s);
			}

			return stringSet;

		}else{

			return ImmutableList.of();
		}
	}
}
