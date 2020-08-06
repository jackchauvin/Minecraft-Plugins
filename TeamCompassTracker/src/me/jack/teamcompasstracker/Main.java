package me.jack.teamcompasstracker;

import org.bukkit.plugin.java.JavaPlugin;

import me.jack.teamcompasstracker.commands.HunterCommand;
import me.jack.teamcompasstracker.listeners.CompassListener;
import me.jack.teamcompasstracker.listeners.DeathListener;
import me.jack.teamcompasstracker.listeners.RespawnListener;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		new HunterCommand(this);
		new CompassListener(this);
		//new DeathListener(this);
		new RespawnListener(this);
	}
	
}
