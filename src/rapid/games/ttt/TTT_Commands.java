package rapid.games.ttt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rapid.games.ttt.handlers.Game;
import rapid.games.ttt.handlers.Team;
import rapid.games.ttt.listeners.player.PlayerInteract;
import rapid.games.ttt.utils.ChatUtils;
import rapid.games.ttt.utils.LocationUtils;

public class TTT_Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args[0].equalsIgnoreCase("start")) {
			Game.setCanStart(true);
		}
		if (args[0].equalsIgnoreCase("stop")) {
			Game.setCanStart(false);
		}
		if (args[0].equalsIgnoreCase("playerstostart")) {
			if (args[1] == null) {
				sender.sendMessage(ChatColor.YELLOW
						+ "Du musst eine Zahl eingeben!");
				return false;
			}
			TTT.getInstance().getConfig().set("Players to Start", args[1]);
			TTT.getInstance().saveConfig();
			sender.sendMessage(ChatColor.YELLOW
					+ "Spieler zum Start benötigt: " + args[1]);
		}

		// Player Commands

		if (args[0].equalsIgnoreCase("test")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.getInventory().addItem(Items.getCustomItem(Items.Item.Sniper));
			} else
				ChatUtils.onlyPlayer(sender);
		}
		if(args[0].equalsIgnoreCase("map")){
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if(PlayerInteract.shots.containsKey(player.getName())){
					ChatUtils.send(player, "Du bist drinnen");}
				else ChatUtils.send(player, "Du bist nicht drin");
			}else
				ChatUtils.onlyPlayer(sender);
		}
		if (args[0].equalsIgnoreCase("traitor")) {
			Player player = (Player)sender;
			Team.addToTeam(TeamType.TRAITOR, player);
		}

		// Spawns setzen
		if (args[0].equalsIgnoreCase("set")) {
			if (args[1].equalsIgnoreCase("gamespawn")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Location loc = player.getLocation();
					TTT.getInstance()
							.getConfig()
							.set("Locations.Arena1.Gamespawn",
									LocationUtils.getStringFromLocation(loc));
					TTT.getInstance().saveConfig();
					player.sendMessage(ChatColor.YELLOW + "Gamespawn gesetzt!");
				} else
					ChatUtils.onlyPlayer(sender);
			} else if (args[1].equalsIgnoreCase("lobbyspawn")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Location loc = player.getLocation();
					TTT.getInstance()
							.getConfig()
							.set("Locations.Arena1.Lobbyspawn",
									LocationUtils.getStringFromLocation(loc));
					TTT.getInstance().saveConfig();
					player.sendMessage(ChatColor.YELLOW + "Lobbyspawn gesetzt!");
				} else
					ChatUtils.onlyPlayer(sender);

			} else if (args[1].equalsIgnoreCase("backspawn")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Location loc = player.getLocation();
					TTT.getInstance()
							.getConfig()
							.set("Locations.Arena1.Backspawn",
									LocationUtils.getStringFromLocation(loc));
					TTT.getInstance().saveConfig();
					player.sendMessage(ChatColor.YELLOW + "Backspawn gesetzt!");
				} else
					ChatUtils.onlyPlayer(sender);

			} else if (args[1].equalsIgnoreCase("deadspawn")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Location loc = player.getLocation();
					TTT.getInstance()
							.getConfig()
							.set("Locations.Arena1.Deadspawn",
									LocationUtils.getStringFromLocation(loc));
					TTT.getInstance().saveConfig();
					player.sendMessage(ChatColor.YELLOW + "Deadspawn gesetzt!");
				} else
					ChatUtils.onlyPlayer(sender);
			}
		}

		return false;
	}
}
