package rapid.games.ttt;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rapid.games.ttt.listeners.MGListener;

public class Items extends MGListener {
	public Items(TTT pl) {
		super(pl);}
	
	public enum Item {
		Sniper;}
	
	public static ItemStack getCustomItem(Item item) {
		ItemStack is = null;
		ItemMeta im;
		ArrayList<String> lore;
		switch (item) {
		case Sniper:
			is = new ItemStack(Material.DIAMOND_AXE);
			im = is.getItemMeta();
			im.setDisplayName("Sniper");
			lore = new ArrayList<String>();
			lore.add("Rechtsclick: Schiessen");
			lore.add("Linksclick: Nachladen");
			im.setLore(lore);
			is.setItemMeta(im);
			break;
		}
		return is;
	}	
	
}
