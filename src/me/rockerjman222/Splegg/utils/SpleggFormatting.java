package me.rockerjman222.Splegg.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class SpleggFormatting {

    public static TextComponent gameMessage(SpleggMessageType type, ArrayList<UUID> winners, UUID eliminated, int timeRemaining) {
        TextComponent message = null;

        switch (type) {
            case SPLEGG_START:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append("The game has started, good luck!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_DENY_START:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append("There is not enough players to start a match!").create());
                break;
            case SPLEGG_PLAYER_DEATH:

				String username;

				Player player = Bukkit.getPlayer(eliminated);

				if(player != null){
					username = player.getName();
				}else{
					username = Bukkit.getOfflinePlayer(eliminated).getName();
				}

                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append(username).color(ChatColor.DARK_AQUA)
                        .append(" has been eliminated!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_PLAYER_WIN:

                StringBuilder playerNames = new StringBuilder();

				String hasOrHave;

				if (winners.size() > 1) {

					for (int i = 0; i < (winners.size() - 1); i++) {
						player = Bukkit.getPlayer(winners.get(i));
						if(player != null) {
							playerNames.append(player.getName()).append(',').append(' ');
						}
					}

					player = Bukkit.getPlayer(winners.get(winners.size() - 1));

					playerNames.append("and").append(' ').append(player.getName());

                    hasOrHave = " have";
                } else {

					playerNames.append(Bukkit.getPlayer(winners.get(winners.size() - 1)).getName());

                    hasOrHave = " has";

                }

                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append(playerNames.toString()).color(ChatColor.DARK_AQUA)
                        .append(hasOrHave + " won the match!").color(ChatColor.GOLD).create());

                break;
            case SPLEGG_TIME_LEFT:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append("There " + (timeRemaining == 1 ? "is" : "are") + " only ").color(ChatColor.DARK_AQUA)
                        .append(Utils.getMinutes(timeRemaining) + " " + Utils.shouldBeMinutes(timeRemaining)).color(ChatColor.GOLD)
                        .append(" left!").color(ChatColor.DARK_AQUA).create());
                break;

        }

        return message;
    }

    public enum SpleggMessageType {
        SPLEGG_START,
        SPLEGG_DENY_START,
        SPLEGG_PLAYER_DEATH,
        SPLEGG_PLAYER_WIN,
        SPLEGG_TIME_LEFT
    }
}
