package me.rockerjman222.Splegg;

import me.rockerjman222.Splegg.command.CommandSplegg;
import me.rockerjman222.Splegg.event.BlockListener;
import me.rockerjman222.Splegg.event.InventoryListener;
import me.rockerjman222.Splegg.event.PlayerListener;
import me.rockerjman222.Splegg.event.SpleggListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Splegg extends JavaPlugin {

    public void onEnable() {
        this.getCommand("splegg").setExecutor(new CommandSplegg(this));

        this.getServer().getPluginManager().registerEvents(new SpleggListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

    }

    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        return true;
    }
}
