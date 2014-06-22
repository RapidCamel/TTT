package rapid.games.ttt.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import rapid.games.ttt.GameState;
import rapid.games.ttt.TTT;
import rapid.games.ttt.TeamType;
import rapid.games.ttt.utils.ChatUtils;
import rapid.games.ttt.utils.LocationUtils;

public class Game {

	private static boolean canStart = false;


	public static void start(){
		Team.clearTeams();
		do{
			int r = (int) (Math.random()*Team.lobby.size());
			String playername = Team.lobby.get(r);
			Player player = Bukkit.getPlayer(playername);
			Team.addToTeam(TeamType.TRAITOR, player);
		} while (!Team.lobby.isEmpty());
		TTT.registerIngameListeners();
		GameState.setState(GameState.IN_GAME);
		for(Player player : Bukkit.getOnlinePlayers()){
			Location loc = LocationUtils.getLocationFromString(TTT.getInstance().getConfig().getString("Locations.Arena1.Gamespawn"));
			if(Team.getAllPlayersInTeams().contains(player.getName()))
				player.teleport(loc);
		}

		canStart = false;
	}

	public static void stop(){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.getInventory().clear();
			Location loc = LocationUtils.getLocationFromString(TTT.getInstance().getConfig().getString("Locations.Arena1.Backspawn"));
			if(Team.getAllPlayersInTeams().contains(player.getName()))
				player.teleport(loc);
		}
		Team.clearTeams();
		ChatUtils.broadcast("Das Spiel ist zu Ende");
		ChatUtils.broadcast("Du bist jetzt wieder in der Lobby");
	}
	
	

	public static boolean canStart() {
		if(Team.lobby.size() == 5)
			canStart = true;
		return canStart;
	}

	public static void setCanStart(boolean b){
		canStart = b;
	}
}


