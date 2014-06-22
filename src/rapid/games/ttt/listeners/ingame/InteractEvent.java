package rapid.games.ttt.listeners.ingame;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import rapid.games.ttt.SpectateMenu;
import rapid.games.ttt.TeamType;
import rapid.games.ttt.handlers.Team;

public class InteractEvent implements Listener{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event){
		final Player player = event.getPlayer();
		Action a = event.getAction();
		if(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)){
			Material m = player.getItemInHand().getType();
			if(m == Material.COMPASS || Team.getTeamType(player) == TeamType.SPECTATOR){
				SpectateMenu.open(player);
			}
		}
	}
}
