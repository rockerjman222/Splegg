package me.rockerjman222.Splegg.event;

import me.rockerjman222.Splegg.Splegg;
import me.rockerjman222.Splegg.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

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

	@EventHandler
	public void onEggLaunch(ProjectileLaunchEvent event) {

		if (event.getEntity() instanceof Snowball) {

			Snowball s = ((Snowball) event.getEntity());
			if (s.getShooter() instanceof Player) {

				Player shooter = ((Player) s.getShooter());

				if(!this.splegg.dataHolder.players.contains(shooter.getUniqueId()))
					return;

					Location spawnLocation = shooter.getEyeLocation().toVector()
						.add(shooter.getLocation().getDirection().multiply(2)).
								toLocation(shooter.getWorld(), shooter.getLocation().getYaw(), shooter.getLocation().getPitch());

				if (shooter.getItemInHand() != null) {

					ItemStack hand = shooter.getItemInHand();


					if (hand.isSimilar(Utils.getDiamondSplegg())) {

						this.spawnExtraSnowballs(s.getVelocity(), s.getWorld(), spawnLocation, shooter, 2);

					}

					if (hand.isSimilar(Utils.getGoldSplegg())) {

						this.spawnExtraSnowballs(s.getVelocity(), s.getWorld(), spawnLocation, shooter, 1);

					}

					//The same can be done for gold or otherwise

				}

			}

		}

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
        if (!event.getPlayer().hasPermission("splegg.worldInteract") && !event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        if (!event.getPlayer().hasPermission("splegg.worldInteract") && !event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
	    if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
		    //TODO: Upgrades
		    //TODO: Drew did them above
		    //You can't call getDisplayName without checking if hasDisplayName, can cause NPE
		    if (hand.isSimilar(Utils.getDiamondSplegg())) {

			    //This should cause a projectilelaunch event, if it doesn't, we can call it manually
			    player.launchProjectile(Egg.class);
			    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);

            }
		    if (hand.isSimilar(Utils.getGoldSplegg())) {
			    player.launchProjectile(Egg.class);
			    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
		    }
		    if (hand.isSimilar(Utils.getStoneSplegg())) {

			    player.launchProjectile(Egg.class);
			    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
		    }
        }
    }

    @EventHandler
    public void onPlayerFallOutOfWorld(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        //TODO: set to specator
        //TODO: check for in game
        if (event.getTo().getY() < -5.0D) {
            player.getServer().broadcastMessage(Utils.getPrefix() + ChatColor.GOLD + player.getName() + " has fallen out of the match!");
            player.setFlying(true);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (this.splegg.dataHolder.queuedPlayers.contains(event.getPlayer().getUniqueId()))
            this.splegg.dataHolder.queuedPlayers.remove(event.getPlayer().getUniqueId());

        if (this.splegg.dataHolder.players.contains(event.getPlayer().getUniqueId()))
            this.splegg.dataHolder.queuedPlayers.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {
        //if(this.splegg.dataHolder.queuedPlayers.contains(event.getPlayer().getUniqueId()))
        //this.splegg.dataHolder.queuedPlayers.remove(event.getPlayer().getUniqueId());

        //if(this.splegg.dataHolder.players.contains(event.getPlayer().getUniqueId()))
        //this.splegg.dataHolder.queuedPlayers.remove(event.getPlayer().getUniqueId());
    }

    /*
     *
     * Inventory tasks
     *
     */

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase("splegg shop")) {

            Player player = (Player) event.getWhoClicked();

            switch (event.getCurrentItem().getType()) {
                case DIAMOND_SPADE:

                    if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("diamond splegg") && event.getCurrentItem().getItemMeta().getLore().contains("shopItem")) {
                        event.setCancelled(true);
                        if (player.getInventory().firstEmpty() != -1) {
                            //TODO: Remove cash
                            player.getInventory().addItem(Utils.getDiamondSplegg());
                            player.sendMessage(Utils.getPrefix() + ChatColor.GOLD + "You've bought the diamond splegg for " + ChatColor.DARK_AQUA + Utils.Config.DIAMOND_SPLEGG_COST + ChatColor.GOLD + " coins");
                        } else {
                            player.sendMessage(Utils.getPrefix() + ChatColor.RED + "You do not have any room in your inventory!");
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

    }

    /*
     *
     * Block tasks
     *
     */

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("splegg.worldInteract") && !event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("splegg.worldInteract") && !event.getPlayer().isOp())
            event.setCancelled(true);
    }

	public void spawnExtraSnowballs(Vector velocity, World world, Location original, Player shooter, int snowballs) {

		for (int i = 0; i <= snowballs; i++) {

			Snowball snowball = world.spawn(original, Snowball.class);

			snowball.setShooter(shooter);
			snowball.setBounce(false);

			snowball.setVelocity(velocity.add(new Vector(Splegg.RANDOM.nextDouble() - 0.5,
					Splegg.RANDOM.nextDouble() - 0.5,
					Splegg.RANDOM.nextDouble() - 0.5)));

		}

	}

}
