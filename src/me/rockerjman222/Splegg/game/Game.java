package me.rockerjman222.Splegg.game;


import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.runnable.GameTask;
import me.rockerjman222.Splegg.utils.SpleggFormatting;
import me.rockerjman222.Splegg.utils.Utils;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

public class Game {

    private BukkitTask runnableTask;

    private Arena arena;

	//This has essentially no value, is it planned to be used?
    private ArrayList<UUID> players = Splegg.getInstance().dataHolder.players;

    private boolean inProgress = false;

    private int gameTime = Utils.Config.DEFAULT_GAME_TIME;

    public Game(Arena arena, ArrayList<UUID> players, int gameTime) {
        this.arena = arena;
        this.players = players;
        this.gameTime = gameTime;
    }

    public void startGameTask() {
        GameTask task = new GameTask(this, this.arena);
        this.runnableTask = task.runTaskTimer(Splegg.getInstance(), 0L, 20L);
        this.inProgress = true;
    }

    public void endGameTask(Game game) {
        this.runnableTask.cancel();
        Splegg.getInstance().dataHolder.winningPlayers = Splegg.getInstance().dataHolder.players;
        Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_PLAYER_WIN, Splegg.getInstance().dataHolder.winningPlayers, null, 0);
        Splegg.getInstance().dataHolder.players.clear();
        Splegg.getInstance().dataHolder.gameQueue.remove(game);
        //TODO Return players from whence they came
    }

    public int getGameTime() {
        return gameTime;
    }

    public boolean isInProgress() {
        return this.inProgress;
    }


}
