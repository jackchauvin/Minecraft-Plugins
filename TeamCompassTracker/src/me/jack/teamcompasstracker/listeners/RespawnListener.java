package me.jack.teamcompasstracker.listeners;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.jack.teamcompasstracker.Main;

public class RespawnListener implements Listener{
		private Main plugin;
		
		public RespawnListener(Main plugin) {
			this.plugin = plugin;
			Bukkit.getPluginManager().registerEvents(this, plugin);
		}
		
		@EventHandler
		public void PlayerRespawn(PlayerRespawnEvent event) {
			
			Player p = (Player) event.getPlayer();
			p.getInventory().addItem(new ItemStack(Material.COMPASS,1));
			
			Scoreboard s = p.getServer().getScoreboardManager().getMainScoreboard();
			Team t = s.getEntryTeam(p.getName());
			
			//Spawn Location
			if(t.getName().equals("Hunters")) {
				
			}else {
				Set<String> hnters = s.getTeam("Hunters").getEntries();
				Player hnter = null;
				for(String pl: hnters) {
					if(!(p.getName().equals(pl))) {
						hnter = plugin.getServer().getPlayer(pl);
					}
				}
				p.sendMessage("teleporting to: "+hnter.getName());
				event.setRespawnLocation(hnter.getLocation());
			}
			// Change team if needed
			if(t.getName().equals("Hunted")) {
				p.sendMessage("You are Now a HUNTER! Use your compass to track Speedrunners!");
				t.removeEntry(p.getName());
				Team hunters = s.getTeam("Hunters");
				hunters.addEntry(p.getName());
			}
		}
}
