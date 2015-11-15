package me.rockerjman222.Splegg.runnable;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.game.Game;
import me.rockerjman222.Splegg.utils.SpleggFormatting;
import me.rockerjman222.Splegg.utils.Utils;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private Game game;
    private int timeRemaining = Utils.Config.DEFAULT_GAME_TIME;
    private final int initialTime;

    public GameTask(Game game) {
        this.game = game;
        this.timeRemaining = this.game.getGameTime();
        this.initialTime = this.timeRemaining;
    }

    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    @Override
    public void run() {
        if (this.initialTime == this.timeRemaining)
            Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_START, null, null, 0);

        switch (this.timeRemaining) {
            case 180:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 120:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 60:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 30:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 10:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 5:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 4:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 3:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 2:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 1:
                Splegg.getInstance().dataHolder.sendSpleggMessage(SpleggFormatting.SpleggMessageType.SPLEGG_TIME_LEFT, null, null, this.timeRemaining);
                break;
            case 0:
                this.game.endGameTask(this.game);
                break;
        }

        --this.timeRemaining;
    }
}
