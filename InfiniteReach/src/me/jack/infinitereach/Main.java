package me.jack.infinitereach;

import org.bukkit.plugin.java.JavaPlugin;

import me.jack.infinitereach.listeners.BreakBlock;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		new BreakBlock(this);
	}
}
