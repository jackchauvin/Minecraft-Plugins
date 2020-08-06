package me.jack.teamcompasstracker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.jack.teamcompasstracker.Main;

public class DeathListener implements Listener {
	
	private Main plugin;
	
	public DeathListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerDeath(PlayerDeathEvent event) {
		Player p = (Player) event.getEntity();
		Scoreboard s = p.getServer().getScoreboardManager().getMainScoreboard();
		Team t = s.getEntryTeam(p.getName());
		if(t.getName().equals("Hunted")) {
			p.sendMessage("You are Now a HUNTER! Use your compass to track Speedrunners!");
			t.removeEntry(p.getName());
			Team hunters = s.getTeam("Hunters");
			hunters.addEntry(p.getName());
		}
	}
}
