package rapid.games.ttt.listeners.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import rapid.games.ttt.TTT;
import rapid.games.ttt.TeamType;
import rapid.games.ttt.handlers.Team;
import rapid.games.ttt.listeners.MGListener;
import rapid.games.ttt.utils.ChatUtils;
import rapid.games.ttt.utils.LocationUtils;

public class PlayerInteract extends MGListener{

	public PlayerInteract(TTT pl) {
		super(pl);
	}
	
	public static Map<String, String> shots = new HashMap<String, String>();
	
	private boolean inZoom(Player player){
		if(player.getWalkSpeed() == -0.9F)
			return true;
		else return false;
	}
	

	@EventHandler
	public void SheepClick(PlayerInteractEntityEvent event){
		Player player = event.getPlayer();
		System.out.println("Interactevent");
		if ((event.getRightClicked()  instanceof Sheep)){
			System.out.println("instance of sheep");
			Team.addToTeam(TeamType.LOBBY, player);
			Location loc = LocationUtils.getLocationFromString(TTT.getInstance().getConfig().getString("Locations.Arena1.Lobbyspawn"));
			player.teleport(loc);
			System.out.println("tp");
			ChatUtils.send(player, "Du bist jetzt in der TTT-Lobby");
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action a = event.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){	
			if(player.getItemInHand().getItemMeta().hasDisplayName()){
				if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Sniper")){
					event.setCancelled(true);
					if(inZoom(player)){
					Entity arrow = player.getWorld().spawnArrow(player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection().normalize()), player.getLocation().getDirection(), 11F, 1F);
					((Projectile) arrow).setShooter(player);
					arrow.setVelocity(player.getLocation().getDirection().multiply(5));
					arrow.setFallDistance(0);
					}else{
						Entity arrow = player.getWorld().spawnArrow(player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection().normalize()), player.getLocation().getDirection(), 10F, 1F);
						((Projectile) arrow).setShooter(player);
						arrow.setVelocity(player.getLocation().getDirection().multiply(5));
						arrow.setFallDistance(0F);
					}
					shots.put(player.getName(), "snipe");
					for(Entity e : player.getNearbyEntities(10, 10, 10) ){
						if(e instanceof Player)
							((Player) e).playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 100F, 2F);
						player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 100F, 2F);
					}
				}
			}	
		}
		if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
			if(player.getItemInHand().getItemMeta().hasDisplayName()){
				if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Sniper")){
					if(!inZoom(player)){
						player.setWalkSpeed(-0.9F);
						return;
					}
					if(inZoom(player)){
						player.setWalkSpeed(0.2F);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		Entity arrow = e.getDamager();
		Entity victim = e.getEntity();
		if(arrow instanceof Arrow) {
			Arrow ar = (Arrow) arrow;
			if(ar.getShooter() instanceof Player){
				if(shots.get(((Player) ar.getShooter()).getName()) == "snipe"){
					shots.remove(((Player) ar.getShooter()).getName());
					double i = ((Damageable) victim).getHealth();
					((Damageable) victim).setHealth(i - 14);
				}
			}
		}
	}
}
