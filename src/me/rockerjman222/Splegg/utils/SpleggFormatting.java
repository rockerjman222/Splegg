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
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append(Bukkit.getOfflinePlayer(eliminated).getName()).color(ChatColor.DARK_AQUA)
                        .append(" has been eliminated!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_PLAYER_WIN:
                ArrayList<Player> players = new ArrayList<>();
                for (int i = 0; i < winners.size(); i++) {
                    Player player = Bukkit.getPlayer(winners.get(i));
                    players.add(player);
                }

                String hasOrHave;

                if (players.size() <= 1) {
                    hasOrHave = " has";
                } else {
                    hasOrHave = " have";
                }

                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append(players.toString().replace("[", "").replace("]", "").replace(",", " and").replace("CraftPlayer{name=", "").replace("}", "")).color(ChatColor.DARK_AQUA)
                        .append(hasOrHave + " won the match!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_TIME_LEFT:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append("There is only ").color(ChatColor.DARK_AQUA)
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
