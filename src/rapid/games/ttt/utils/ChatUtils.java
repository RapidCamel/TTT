package rapid.games.ttt.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rapid.games.ttt.TeamType;
import rapid.games.ttt.handlers.Team;

public class ChatUtils {

	public static void broadcast(String msg) {
		for (Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(starter() + msg);
	}

	public static void send(Player player, String msg) {
		player.sendMessage(starter() + msg);
	}

	private static String starter() {
		return ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TTT"
				+ ChatColor.DARK_GRAY + "] " + ChatColor.AQUA;
	}

	public static void onlyPlayer(CommandSender sender) {
		sender.sendMessage("Du musst ein Spieler für diesen Befehl sein!");
	}

	public static void log(CommandSender sender, String msg) {
		sender.sendMessage(starter() + ChatColor.YELLOW + msg);
	}

	public static void tChat(Player sender, String text) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Team.getTeamType(player) == TeamType.TRAITOR) {
				player.sendMessage(ChatColor.RED + "[T]" + ChatColor.GRAY
						+ sender.getName() + ">" + ChatColor.WHITE + text);
			}
		}
	}
}
