package rapid.games.ttt.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import rapid.games.ttt.GameState;
import rapid.games.ttt.TTT;
import rapid.games.ttt.handlers.Game;
import rapid.games.ttt.listeners.MGListener;

public class PlayerQuit extends MGListener{

	public PlayerQuit(TTT pl) {
		super(pl);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		if(GameState.isState(GameState.IN_LOBBY));
		Game.setCanStart(Bukkit.getOnlinePlayers().length -1 >=8);
	}
}
