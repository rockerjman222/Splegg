package me.rockerjman222.Splegg.game;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Arena {

    private String name;
    private World world;
    private ArenaState state = ArenaState.OPEN;


    public Arena(String name, World world) {
        this.name = name;
        this.world = world;
        this.world.setAutoSave(false);
    }

    public Arena(World world) {
        this(world.getName(), world);
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public ArenaState getState() {
        return this.state;
    }

    public void unloadMap(Plugin p, String map) {
        if(Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(map), false)){
            p.getLogger().info("Successfully unloaded " + map);
        }else{
            p.getLogger().severe("COULD NOT UNLOAD " + map);
        }
    }

    public void loadMap(String mapname) {
        Bukkit.getServer().createWorld(new WorldCreator(mapname));
        Bukkit.getServer().getWorld(mapname).setAutoSave(false);
    }

    public void rollbackArena(Plugin plugin, String map) {

        for(Player p : this.world.getPlayers()) {
            p.teleport(plugin.getServer().getWorlds().get(0).getSpawnLocation());
        }

        unloadMap(plugin, map);
        loadMap(map);
    }

}