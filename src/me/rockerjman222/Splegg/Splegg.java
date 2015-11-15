package me.rockerjman222.Splegg;

import me.rockerjman222.Splegg.command.CommandSplegg;
import me.rockerjman222.Splegg.data.DataHolder;
import me.rockerjman222.Splegg.event.SpleggListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Splegg extends JavaPlugin {

    public final DataHolder dataHolder = new DataHolder(this);

    public void onEnable() {
        this.getCommand("splegg").setExecutor(new CommandSplegg(this));

        this.getServer().getPluginManager().registerEvents(new SpleggListener(this), this);

    }

    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        return true;
    }

    public static Splegg getInstance() {
        return Splegg.getPlugin(Splegg.class);
    }
}
