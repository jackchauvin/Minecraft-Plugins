package me.jack.teamcompasstracker.commands;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.jack.teamcompasstracker.Main;

public class HunterCommand implements CommandExecutor {
	private Main plugin;
	
	public HunterCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("hunter").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		List<Player> players = p.getWorld().getPlayers();
		Player hnter = plugin.getServer().getPlayer(args[0]);
		if (players.contains(hnter)) {
			p.sendMessage("Hunter Found");
			Scoreboard s = p.getServer().getScoreboardManager().getMainScoreboard();
			Set<Team> ot= s.getTeams();
			for(Team td: ot ) {
				td.unregister();
			}
			
			p.sendMessage("Registering Hunters");
			Team hunters = s.registerNewTeam("Hunters");
			hunters.addEntry(hnter.getName());
			hnter.sendMessage("You are the Hunter!");
			hnter.getInventory().addItem(new ItemStack(Material.COMPASS,1));
			hnter.getInventory().addItem(new ItemStack(Material.STONE_SWORD,1));
			hnter.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			hnter.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			hnter.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			hnter.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			
			p.sendMessage("Registering Hunted");
			Team hunted = s.registerNewTeam("Hunted");
			for(Player pl: players) {
				if(!(pl.getName().equals(hnter.getName()))) {
					pl.sendMessage("You are being hunted!");
					hunted.addEntry(pl.getName());
				}
			}
			hnter.setNoDamageTicks(800); // 20 ticks/s   40 seconds
			hnter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,800,1));
			Bukkit.broadcastMessage("The Game Has Started!");
			Bukkit.broadcastMessage("The Hunter is blinded!");
			Bukkit.broadcastMessage("Speedrunners have 40 seconds to RUN!");
	
		}else{
			p.sendMessage("Player not found, retry!");
		}
		return false;
	}
}
