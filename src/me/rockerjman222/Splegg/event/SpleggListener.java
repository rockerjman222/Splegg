package me.rockerjman222.Splegg.event;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

public class SpleggListener implements Listener {

    private final Splegg splegg;

    public SpleggListener(Splegg splegg) {
        this.splegg = splegg;
    }

    /*
     *
     * Main game tasks
     *
     */
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

    /*
     *
     * Player tasks
     *
     */
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHungerEvent(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
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
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //TODO: Upgrades
            if (hand.getType() == Material.DIAMOND_SPADE) {
                if (ChatColor.stripColor(hand.getItemMeta().getDisplayName()).equalsIgnoreCase("diamond splegg")) {
                    player.launchProjectile(Egg.class);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
                }
            }
            if (hand.getType() == Material.GOLD_SPADE) {
                if (ChatColor.stripColor(hand.getItemMeta().getDisplayName()).equalsIgnoreCase("gold splegg")) {
                    player.launchProjectile(Egg.class);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
                }
            }
            if (hand.getType() == Material.STONE_SPADE) {
                if (ChatColor.stripColor(hand.getItemMeta().getDisplayName()).equalsIgnoreCase("stone splegg")) {
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
        if (player.getLocation().getY() < -5.0D) {
            player.getServer().broadcastMessage(Utils.getPrefix() + ChatColor.GOLD + player.getName() + " has fallen out of the match!");
            player.setFlying(true);
        }
    }


    /*
     *
     * Inventory tasks
     *
     */
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (!(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("splegg shop")))
            return;

        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case DIAMOND_SPADE:
                //TODO: fix bug
                /*
                 * You can buy an item once, and it bothers me that
                 * you can buy the same item from your inventory
                 * in the shop inventory
                 */

                if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("diamond splegg")) {
                    event.setCancelled(true);
                    if (player.getInventory().firstEmpty() != -1) {
                        //TODO: Remove cash
                        player.getInventory().addItem(Utils.getDiamondSplegg());
                        player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the diamond splegg for " + ChatColor.DARK_AQUA + Utils.Config.DIAMOND_SPLEGG_COST + ChatColor.GOLD + " coins");
                    } else {
                        player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have nay room in your inventory!");
                    }
                }
                break;
            case GOLD_SPADE:

                if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("gold splegg")) {
                    event.setCancelled(true);
                    if (player.getInventory().firstEmpty() != -1) {
                        //TODO: Remove cash
                        player.getInventory().addItem(Utils.getGoldSplegg());
                        player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the gold splegg for " + ChatColor.DARK_AQUA + Utils.Config.GOLD_SPLEGG_COST + ChatColor.GOLD + " coins");
                    } else {
                        player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have nay room in your inventory!");
                    }
                }
                break;
            case STONE_SPADE:

                if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("stone splegg")) {
                    event.setCancelled(true);
                    if (player.getInventory().firstEmpty() != -1) {
                        //TODO: Remove cash
                        player.getInventory().addItem(Utils.getStoneSplegg());
                        player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the stone splegg for " + ChatColor.DARK_AQUA + Utils.Config.STONE_SPLEGG_COST + ChatColor.GOLD + " coins");
                    } else {
                        player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have nay room in your inventory!");
                    }
                }
                break;
        }

    }

    /*
     *
     * Block tasks
     *
     */
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (!event.getPlayer().isOp()) ;
        event.setCancelled(true);
    }

}
