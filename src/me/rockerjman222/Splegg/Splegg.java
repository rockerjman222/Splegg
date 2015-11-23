package me.rockerjman222.Splegg;

import me.rockerjman222.Splegg.command.CommandSplegg;
import me.rockerjman222.Splegg.data.DataHolder;
import me.rockerjman222.Splegg.event.SpleggListener;
import me.rockerjman222.Splegg.game.Arena;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Splegg extends JavaPlugin {

    public final DataHolder dataHolder = new DataHolder(this);
	public static Random RANDOM = new Random();
	public Arena arena = null;

    public void onEnable() {
        if (this.getServer().getWorld("SpleggArena") == null)
            this.arena = new Arena("SpleggArena", this.getServer().createWorld(new WorldCreator("SpleggArena")));
        else
            this.arena = new Arena(this.getServer().getWorld("SpleggArena"));
        this.getCommand("splegg").setExecutor(new CommandSplegg(this));

        this.getServer().getPluginManager().registerEvents(new SpleggListener(this), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (dataHolder.getCurrentGame() == null)
                    dataHolder.startNextGame();

            }
        }, 0L, 20L);

    }

    public void onDisable() {

    }

    public static Splegg getInstance() {
        return Splegg.getPlugin(Splegg.class);
    }
}
