package me.rockerjman222.Splegg.data;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.game.Game;
import me.rockerjman222.Splegg.utils.SpleggFormatting;
import me.rockerjman222.Splegg.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataHolder {

    private final Splegg splegg;

    public ArrayList<UUID> players = new ArrayList<>();
    public ArrayList<UUID> winningPlayers = new ArrayList<>();
    public ArrayList<UUID> queuedPlayers = new ArrayList<>();
    public ArrayList<UUID> spectatingPlayers = new ArrayList<>();

    //public Arena gameArena = new Arena("");

    public CopyOnWriteArrayList<Game> gameQueue = new CopyOnWriteArrayList<>();

    public DataHolder(Splegg splegg) {
        this.splegg = splegg;
    }

    public void sendSpleggMessage(SpleggFormatting.SpleggMessageType type, ArrayList<UUID> winners, UUID eliminated, int timeRemaining) {
        for (Player p : this.splegg.getServer().getOnlinePlayers()) {
            switch (type) {
                case SPLEGG_START:
                    p.spigot().sendMessage(SpleggFormatting.gameMessage(SpleggFormatting.SpleggMessageType.SPLEGG_START, null, null, 0));
                    break;
                case SPLEGG_PLAYER_DEATH:
                    p.spigot().sendMessage(SpleggFormatting.gameMessage(SpleggFormatting.SpleggMessageType.SPLEGG_PLAYER_DEATH, null, eliminated, 0));
                    break;
                case SPLEGG_PLAYER_WIN:
                    p.spigot().sendMessage(SpleggFormatting.gameMessage(SpleggFormatting.SpleggMessageType.SPLEGG_PLAYER_WIN, winners, null, 0));
                    break;
                case SPLEGG_TIME_LEFT:
                    p.spigot().sendMessage(SpleggFormatting.gameMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, timeRemaining));
            }
        }
    }

    public void generateNormalGame(Game game) {
        this.players = this.queuedPlayers;
        this.gameQueue.add(game);
    }

    public void cancelAllGames() {
        for (Game game : this.gameQueue) {
            for (UUID player : this.queuedPlayers) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(player);
                if (target.isOnline()) {
                    target.getPlayer().sendMessage(Utils.getPrefix() + ChatColor.RED + "Your game was canceled!");
                }
            }

            game.endGameTask(game);
        }
    }

    public void startNextGame() {
        if (this.gameQueue.size() >= 2) {
            //I assume you meant to use a copy of the list, an iterator will suffice that
            for (Iterator<Game> iterator = this.gameQueue.iterator(); iterator.hasNext(); ) {
                Game game = iterator.next();
                game.startGameTask();
                break;
            }
        } else {
            this.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_DENY_START, null, null, 0);
        }
    }

    public Game getCurrentGame() {
        for (Game game : this.gameQueue) {
            if (game.isInProgress())
                return game;
        }
        return null;
    }
}
