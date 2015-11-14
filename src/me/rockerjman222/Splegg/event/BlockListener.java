package me.rockerjman222.Splegg.event;

import me.rockerjman222.Splegg.Splegg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private final Splegg splegg;

    public BlockListener(Splegg splegg) {
        this.splegg = splegg;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
       event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
       if(!event.getPlayer().isOp());
            event.setCancelled(true);
    }
}
