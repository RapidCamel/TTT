package rapid.games.ttt.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import rapid.games.ttt.TTT;
import rapid.games.ttt.handlers.Game;
import rapid.games.ttt.listeners.MGListener;

public class PlayerJoin extends MGListener{

	public PlayerJoin(TTT pl) {
		super(pl);
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event){
		Game.setCanStart(Bukkit.getOnlinePlayers().length >= 8);
	}
}
