package me.jack.infinitereach.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.google.common.base.Predicate;

import me.jack.infinitereach.Main;

public class BreakBlock implements Listener {

	private Main plugin;
	
	public BreakBlock(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void clickEvent(PlayerInteractEvent e) {
		Action a = e.getAction();
		int maxDistance = 100;
		
		if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
			
			Player p = e.getPlayer();
			Block b = p.getTargetBlock(null,maxDistance);
			ItemStack is = e.getItem();
			
			double dist = p.getLocation().distance(b.getLocation());
			if (dist > 4 && dist <= maxDistance) {
				
				Vector dir = p.getLocation().getDirection().normalize();
				Predicate<Entity> pred = i -> (i instanceof Damageable);
				RayTraceResult rtr = p.getWorld().rayTraceEntities(p.getLocation().add(dir.multiply(5)), dir, maxDistance, 1, pred);
				if (rtr != null) {
					Damageable mob = (Damageable) rtr.getHitEntity();
					double damageValue = 0;
					
					if (is.getType() == Material.WOODEN_SWORD || is.getType() == Material.WOODEN_AXE) {
			            damageValue = 5;
			        } else if (is.getType() == Material.STONE_SWORD || is.getType() == Material.STONE_AXE) {
			            damageValue = 6;
			        } else if (is.getType() == Material.IRON_SWORD || is.getType() == Material.IRON_AXE) {
			            damageValue = 7;
			        } else if (is.getType() == Material.GOLDEN_SWORD || is.getType() == Material.GOLDEN_AXE) {
			            damageValue = 7;
			        } else if (is.getType() == Material.DIAMOND_SWORD || is.getType() == Material.DIAMOND_AXE) {
			            damageValue = 8;
			        } else {
			            damageValue = 1; // other blocks & items
			        }
					mob.damage(damageValue);
				}else{
					b.breakNaturally(e.getItem());
				}
			}
		}
	}
}
