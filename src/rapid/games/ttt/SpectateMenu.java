package rapid.games.ttt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rapid.games.ttt.utils.IconMenu;
import rapid.games.ttt.utils.IconMenu.OptionClickEvent;

public class SpectateMenu {
	
	private static IconMenu menu = new IconMenu(ChatColor.AQUA + "Spectator", 9, new IconMenu.OptionClickEventHandler() {

		@Override
		public void onOptionClick(OptionClickEvent event) {
			Player player = event.getPlayer();
			String name = event.getName();
			if (name.equalsIgnoreCase("Diamond")){
				player.getInventory().addItem(new ItemStack(Material.DIAMOND));
			}else if (name.equalsIgnoreCase("potato")){
				player.teleport(player.getWorld().getSpawnLocation());
			}else if (name.equalsIgnoreCase("die")){
				player.setHealth(0D);
			}
		}
	}, TTT.getInstance());
	
	public static void open(Player player){
		fillMenu();
		menu.open(player);
	}
	
	public static void fillMenu(){
		menu.setOption(3, new ItemStack(Material.DIAMOND), "Diamond", "gives Diamond");
		menu.setOption(4, new ItemStack(Material.BAKED_POTATO), "Potato", "tp to spawn");
		menu.setOption(5, new ItemStack(Material.SKULL_ITEM), "die", "kill Player!");
	}

}
