package rapid.games.ttt.listeners.ingame;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import rapid.games.ttt.Traitor;
import rapid.games.ttt.utils.ChatUtils;

public class Traitorchat implements Listener{
	@EventHandler
	public void onText(AsyncPlayerChatEvent event){
		if(Traitor.enabledTChat == true){
			Player player = event.getPlayer();
			String message = event.getMessage();
			ChatUtils.tChat(player, message);
			event.setCancelled(true);
		}
	}

}
