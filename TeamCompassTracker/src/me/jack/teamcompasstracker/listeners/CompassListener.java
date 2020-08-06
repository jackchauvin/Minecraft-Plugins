package me.jack.teamcompasstracker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.jack.teamcompasstracker.Main;

public class CompassListener implements Listener {
	
	private Main plugin;
	
	public CompassListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void CompassClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Material holding = event.getMaterial();
		Action a = event.getAction();
		
		if(holding == Material.COMPASS && ( a == Action.RIGHT_CLICK_AIR || a== Action.RIGHT_CLICK_BLOCK )) {
			Scoreboard s = p.getServer().getScoreboardManager().getMainScoreboard();
			Team t = s.getEntryTeam(p.getName());
			
			// If holding compass, right click, and on Hunters team
			if (t.getName().equals("Hunters")) {
				
				Player closest = null;
				double lastDistance = Double.MAX_VALUE;
				for(Player pl : p.getWorld().getPlayers()) {
				    if(s.getEntryTeam(pl.getName()).getName().equals("Hunted")){
				    	double distance = pl.getLocation().distance(p.getLocation());
					    if(distance < lastDistance) {
					        lastDistance = distance;
					        closest = pl;
					    }
				    }   
				}
				if(closest != null) {
					if(lastDistance < 30) {
						p.sendMessage("Player within 30 blocks...");
					}else {
						p.setCompassTarget(closest.getLocation());
					    p.sendMessage("Pinged "+ closest.getName() +"'s location!");
					}
				} else {
				    p.sendMessage("Couldn't find a player to track");
				}
			}
		}
	}
}
