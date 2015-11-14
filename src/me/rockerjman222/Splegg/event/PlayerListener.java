package me.rockerjman222.Splegg.event;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    private final Splegg splegg;

    public PlayerListener(Splegg splegg) {
        this.splegg = splegg;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHungerEvent(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onEntityDropItemEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //TODO: Upgrades
            if(hand.getType() == Material.DIAMOND_SPADE) {
                if(ChatColor.stripColor(hand.getItemMeta().getDisplayName()).equalsIgnoreCase("diamond splegg")) {
                    player.launchProjectile(Egg.class);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
                }
            }
            if(hand.getType() == Material.GOLD_SPADE) {
                if(ChatColor.stripColor(hand.getItemMeta().getDisplayName()).equalsIgnoreCase("gold splegg")) {
                    player.launchProjectile(Egg.class);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
                }
            }
            if(hand.getType() == Material.STONE_SPADE) {
                if(ChatColor.stripColor(hand.getItemMeta().getDisplayName()).equalsIgnoreCase("stone splegg")) {
                    player.launchProjectile(Egg.class);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFallOutOfWorld(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        //TODO: set to specator
        //TODO: check for in game
        if(player.getLocation().getY() < -5.0D) {
            player.getServer().broadcastMessage(Utils.getPrefix() + ChatColor.GOLD + player.getName() + " has fallen out of the match!");
            player.setFlying(true);
        }
    }
}
