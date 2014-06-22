package rapid.games.ttt;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rapid.games.ttt.handlers.Team;
import rapid.games.ttt.utils.ChatUtils;
import rapid.games.ttt.utils.LocationUtils;

public class NoPrefixCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("t")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 1)
					if (Team.getTeamType(player) == TeamType.TRAITOR) {
						String text = args[0];
						ChatUtils.tChat(player, text);
					}
				return true;
			}
		}
		if (label.equalsIgnoreCase("toggleT")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if(Team.getTeamType(player) == TeamType.TRAITOR){
					if (Traitor.enabledTChat == false) {
						Traitor.toggleTChat(player, true);
						ChatUtils.send(player, "Traitorchat aktiviert!");
					}else if(Traitor.enabledTChat == true){
						Traitor.toggleTChat(player, false);
						ChatUtils.send(player, "Traitorchat aus!");
					}
				}else ChatUtils.send(player, "Du musst Traitor dafür sein!");
			}else ChatUtils.onlyPlayer(sender);
			return true;
		} else if (label.equalsIgnoreCase("hub")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (Team.isInTeam(player))
					Team.removeFromTeams(player);
				Location hub = LocationUtils.getLocationFromString(TTT
						.getInstance().getConfig()
						.getString("Locations.Arena1.Backspawn"));
				player.teleport(hub);
				ChatUtils.send(player,
						"Du bist wieder zurück in der Hauptlobby");
			} else
				ChatUtils.onlyPlayer(sender);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("meinteam")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (Team.isInGameTeam(player))
					ChatUtils.send(player, Team.getTeamType(player).toString());
				if (!Team.isInGameTeam(player)
						|| Team.getTeamType(player) == null) {
					ChatUtils.send(player, "Du bist in keinem Team!");
				}
			} else
				ChatUtils.onlyPlayer(sender);
			return true;
		}
		return false;
	}
}
