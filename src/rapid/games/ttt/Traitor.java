package rapid.games.ttt;

import org.bukkit.entity.Player;

public class Traitor {
	public static boolean enabledTChat = false;
	
	public static void toggleTChat(Player player, boolean b){
		enabledTChat = b;
	}
}
