package me.rockerjman222.Splegg.game;


import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.runnable.GameTask;
import me.rockerjman222.Splegg.utils.Utils;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private BukkitTask runnableTask;
    private ArrayList<UUID> players;

    private ArrayList<String> winners;
    private boolean inProgress = false;

    private int gameTime = Utils.Config.DEFAULT_GAME_TIME;

    public Game(ArrayList<UUID> players, int gameTime) {
        this.players = players;
        this.gameTime = gameTime;
    }

    public void startGameTask() {
        GameTask task = new GameTask(this);
        this.runnableTask = task.runTaskTimer(Splegg.getInstance(), 0L, 20L);
        this.inProgress = true;
    }

    public void endGameTask(Game game) {
        this.runnableTask.cancel();
        Splegg.getInstance().dataHolder.gameQueue.remove(game);
    }

    public int getGameTime() {
        return gameTime;
    }


}
