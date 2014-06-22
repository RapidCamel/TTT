package rapid.games.ttt;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import rapid.games.ttt.handlers.Team;
import rapid.games.ttt.listeners.ingame.IngameDeath;
import rapid.games.ttt.listeners.ingame.InteractEvent;
import rapid.games.ttt.listeners.ingame.Traitorchat;
import rapid.games.ttt.listeners.player.PlayerInteract;
import rapid.games.ttt.listeners.player.PlayerJoin;
import rapid.games.ttt.listeners.player.PlayerQuit;
import rapid.games.ttt.threads.StartCountdown;

public class TTT extends JavaPlugin {
	public static TTT instance;
	public static Logger log;

	@Override
	public void onEnable() {
		instance = this;
		log = getLogger();
		createConfig();
		TTT_Commands cmd = new TTT_Commands();
		getCommand("ttt").setExecutor(cmd);	
		registerNoPrefCmd();
		GameState.setState(GameState.IN_LOBBY);
		new Thread(new StartCountdown()).start();
		registerListeners();
		Team.clearTeams();
		Bukkit.getPluginManager().registerEvents(new Traitorchat(), instance);
	}

	@Override
	public void onDisable() {
		Team.clearTeams();
		instance = null;
	}

	private void registerNoPrefCmd(){
		NoPrefixCmd noprefcmd = new NoPrefixCmd();
		getCommand("t").setExecutor(noprefcmd);
		getCommand("hub").setExecutor(noprefcmd);
		getCommand("meinteam").setExecutor(noprefcmd);
		getCommand("toggleT").setExecutor(noprefcmd);
	}
	public void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerQuit(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new Items(this), this);
	}

	public static void registerIngameListeners() {
		Bukkit.getPluginManager().registerEvents(new IngameDeath(), instance);
		Bukkit.getPluginManager().registerEvents(new InteractEvent(), instance);
	}

	private void createConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
				File file = new File(getDataFolder(), "config.yml");
				if (file.exists()) {
					getLogger().info(
							"Config.yml existiert nicht! Erstelle neue...");
					saveConfig();
				} else
					getLogger().info("Config.yml gefunden!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TTT getInstance() {
		return instance;
	}

}
