package io.github.ratemynoot.randomquotes;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.configuration.file.FileConfiguration;

public final class RandomQuotes extends JavaPlugin implements Listener {
	
	protected static List<String> quotes;
	protected static boolean random;
	protected static int rLast;
	protected static int count;
	
	@Override
	public void onDisable() {	
		getLogger().info("RandomQuotes Disabled!");
		quotes = null;
	}
    
	@Override
	public void onEnable() {	
		getLogger().info("RandomQuotes Enabled!");
		buildCfg();
		loadSettings();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			sender.sendMessage(ChatColor.RED + "Too many arguments - better off saying nothing!");
			return false;
		} 

		if (quotes == null || quotes.isEmpty()) {
			sender.sendMessage(ChatColor.RED + "There is nothing to quote.. how strange!");
			return false;
		}

		String send = "";

		if (random) {

			int s = new Random().nextInt(quotes.size());

			if (rLast == s) {
				s = new Random().nextInt(quotes.size());
			}

			rLast = s;
			send = quotes.get(s);
		} else {

			if (count < quotes.size()) {
				count = count + 1;
				send = quotes.get(count - 1);
			} else {
				count = 0;
				send = quotes.get(0);
			}
     
		}
		getServer().broadcastMessage(send);
        return true; 
	}
	private void loadSettings() {
		quotes = getConfig().getStringList("quotes");
	}

	private void buildCfg() {
		FileConfiguration cfg = getConfig();
		cfg.options().header(
				"RandomQuotes v" + getDescription().getVersion());

		cfg.addDefault("quotes", Arrays.asList(new String[] {
				"Random Quote: The cure for boredom is curiosity. There is no cure for curiosity.",
				"Random Quote: Time is the most valuable thing a man can spend.",
				"Random Quote: Never believe anything until it has been officially denied.",
				"Random Quote: Life is a long lesson in humility."}));
		cfg.options().copyDefaults(true);
		saveConfig();
	}

}
