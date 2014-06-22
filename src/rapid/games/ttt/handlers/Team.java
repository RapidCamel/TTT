package rapid.games.ttt.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rapid.games.ttt.TTT;
import rapid.games.ttt.TeamType;
import rapid.games.ttt.utils.ChatUtils;

public class Team {
	public static List<String> lobby = new ArrayList<String>();
	private static List<String> spectators = new ArrayList<String>();
	public static List<String> traitors = new ArrayList<String>();
	private static List<String> innocents = new ArrayList<String>();

	public static void addToTeam(TeamType type, Player player) {
		if (isInGameTeam(player)) {
			ChatUtils.send(player, "Du bist schon in einem Team!");
			return;
		}
		switch (type) {
		case LOBBY:
			lobby.add(player.getName());
			break;
		case INNOCENT:
			lobby.remove(player.getName());
			innocents.add(player.getName());
			ChatUtils.send(player, "Du bist " + ChatColor.GREEN + "Innocent");
			break;
		case TRAITOR:
			lobby.remove(player.getName());
			if (traitors.size() == 2) {
				ChatUtils.send(player, "Du bist " + ChatColor.GREEN
						+ "Innocent");
				innocents.add(player.getName());
				break;
			}
			traitors.add(player.getName());
			ChatUtils.send(player, "Du bist " + ChatColor.RED + "TRAITOR");
			break;
		case SPECTATOR:
			spectators.add(player.getName());
			player.getInventory().addItem(new ItemStack(Material.COMPASS));
			ChatUtils.send(player, "Du bist jetzt in der Spectate Lobby");
			break;
		}
	}

	public static boolean isInTeam(Player player) {
		return traitors.contains(player.getName())
				|| innocents.contains(player.getName())
				|| lobby.contains(player.getName());
	}

	public static boolean isInGameTeam(Player player) {
		return traitors.contains(player.getName())
				|| innocents.contains(player.getName());
	}

	public static void clearTeams() {
		traitors.clear();
		innocents.clear();
	}

	public static List<String> getTraitors() {
		return traitors;
	}

	public static List<String> getInnocents() {
		return innocents;
	}

	public static List<String> getAllPlayersInTeams() {
		List<String> combinedTeams = new ArrayList<String>();
		combinedTeams.addAll(innocents);
		combinedTeams.addAll(traitors);
		combinedTeams.addAll(spectators);
		return combinedTeams;
	}

	public static TeamType getTeamType(Player player) {
		if (!isInGameTeam(player))
			return null;
		return (traitors.contains(player.getName()) ? TeamType.TRAITOR
				: TeamType.INNOCENT);
	}

	public static void removeFromTeams(Player player) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		if (isInTeam(player)) {
			TeamType t = Team.getTeamType(player);
			switch (t) {
			case INNOCENT:
				innocents.remove(player.getName());
				if (innocents.isEmpty()) {
					spectators.add(player.getName());
					Bukkit.getScheduler().scheduleSyncDelayedTask(TTT.getInstance(), new Runnable(){
						public void run(){
							ChatUtils.broadcast("Die Traitor haben gewonnen!");
							Game.stop();
						}
					}, 3*20);
				}
				break;
			case TRAITOR:
				traitors.remove(player.getName());
				if (traitors.isEmpty()) {
					spectators.add(player.getName());
					Bukkit.getScheduler().scheduleSyncDelayedTask(TTT.getInstance(), new Runnable(){
						public void run(){
							ChatUtils.broadcast("Die Innocents haben gewonnen!");
							Game.stop();
						}
					}, 3*20);
				}
				break;
			case LOBBY:
				lobby.remove(player.getName());
				break;
			}
		}
		return;
	}

}
