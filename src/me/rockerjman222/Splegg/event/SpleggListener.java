package me.rockerjman222.Splegg.event;

import me.rockerjman222.Splegg.Splegg;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.util.BlockIterator;

public class SpleggListener implements Listener {

    private final Splegg splegg;

    public SpleggListener(Splegg splegg) {
        this.splegg = splegg;
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if(event.getEntityType() != EntityType.EGG)
            return;

        if(!(event.getEntity().getShooter() instanceof Player))
            return;

        //TODO: If game has not started return

        BlockIterator blockIterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity(), 0.0D, 4);
        Block hit = null;
        while(blockIterator.hasNext()) {
            hit = blockIterator.next();
            if(hit.getType() != Material.AIR) {
                break;
            }
        }

        if(hit != null) {
            if(hit.getType() == Material.SNOW_BLOCK) {
                hit.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {
        event.setHatching(false);
    }






}
