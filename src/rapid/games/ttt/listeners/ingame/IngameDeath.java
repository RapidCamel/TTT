package rapid.games.ttt.listeners.ingame;




import net.minecraft.server.v1_7_R1.EnumClientCommand;
import net.minecraft.server.v1_7_R1.PacketPlayInClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import rapid.games.ttt.TTT;
import rapid.games.ttt.TeamType;
import rapid.games.ttt.handlers.Team;
import rapid.games.ttt.utils.LocationUtils;

public class IngameDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		event.setDeathMessage(null);
		Location loc = player.getLocation();
		loc.setY(loc.getY() - 1);
		Block b = loc.getBlock();
		b.setType(Material.REDSTONE_BLOCK);
		Bukkit.getScheduler().runTaskLaterAsynchronously(TTT.getInstance(), new Runnable(){
			public void run(){
				PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				((CraftPlayer)player).getHandle().playerConnection.a(packet);
			}
		}, 1L);
	}

	@EventHandler
	public void PlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if(Team.isInGameTeam(player)){
		Location respawn = LocationUtils.getLocationFromString(TTT
				.getInstance().getConfig()
				.getString("Locations.Arena1.Deadspawn"));
		event.setRespawnLocation(respawn);
		Team.removeFromTeams(player);
		Team.addToTeam(TeamType.SPECTATOR, player);}
	}
}
