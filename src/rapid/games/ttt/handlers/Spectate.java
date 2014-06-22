package rapid.games.ttt.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Spectate {

	public static void setSpectate(Player player, boolean value) {
		PlayerInventory inv = player.getInventory();
		if (value == true) {
			inv.clear();
			inv.addItem(new ItemStack(Material.COMPASS));
			for (Player hidden : Bukkit.getOnlinePlayers()) {
				hidden.hidePlayer(player);
			}
		}
		else if(value == false) {
			inv.clear();
			for(Player all : Bukkit.getOnlinePlayers()){
				all.showPlayer(player);
			}
		}
	}

}
