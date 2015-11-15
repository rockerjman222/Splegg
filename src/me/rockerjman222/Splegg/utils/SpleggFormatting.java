package me.rockerjman222.Splegg.utils;

import me.rockerjman222.Splegg.Splegg;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;

public class SpleggFormatting {

    public static TextComponent gameMessage(SpleggMessageType type, ArrayList<UUID> winners, UUID eliminated, int timeRemaining) {
        TextComponent message = null;

        switch (type) {
            case SPLEGG_START:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append("The game has started, good luck!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_PLAYER_DEATH:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append(Bukkit.getOfflinePlayer(eliminated).getName()).color(ChatColor.DARK_AQUA)
                        .append(" has been eliminated!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_PLAYER_WIN:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append(winners.toString()).color(ChatColor.DARK_AQUA)
                        .append(" have won the match!!").color(ChatColor.GOLD).create());
                break;
            case SPLEGG_TIME_LEFT:
                message = new TextComponent(new ComponentBuilder(Utils.getPrefix()).append("There is only ").color(ChatColor.DARK_AQUA)
                        .append(Utils.getMinutes(timeRemaining) + Utils.shouldBeMinutes(timeRemaining)).color(ChatColor.GOLD)
                        .append(" left!").color(ChatColor.DARK_AQUA).create());
                break;

        }

        return message;
    }

    public enum SpleggMessageType {
        SPLEGG_START,
        SPLEGG_PLAYER_DEATH,
        SPLEGG_PLAYER_WIN,
        SPLEGG_TIME_LEFT
    }
}
