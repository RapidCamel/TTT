package rapid.games.ttt.threads;

import org.bukkit.Bukkit;

import rapid.games.ttt.GameState;
import rapid.games.ttt.handlers.Game;
import rapid.games.ttt.utils.ChatUtils;

public class StartCountdown implements Runnable {

	private static int timeUntilStart;

	public void run() {
		timeUntilStart = 60;
		while (true) {
			if (GameState.isState(GameState.IN_LOBBY))
				if(Game.canStart()) {
					timeUntilStart = 15;
					ChatUtils.broadcast("Es sind genügend Spieler da, Countdown startet...");
					ChatUtils.broadcast(timeUntilStart + " Sekunden bis zum Start!");
					for(; timeUntilStart >= 0; timeUntilStart--){
						if (!Game.canStart()){
							ChatUtils.broadcast("Es sind zu wenig Spieler da, Countdown gestoppt!");
							break;
						}
						if (timeUntilStart == 0){
							Game.start();
							break;
						}

						if (timeUntilStart % 10==0 || timeUntilStart < 10){
							ChatUtils.broadcast(timeUntilStart + " Sekunden bis zum Start!");
						}

						try {
							Thread.sleep(1000);
						}
						catch (InterruptedException e){
							e.printStackTrace();
							Bukkit.shutdown();
						}
					}
				}
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e){
				e.printStackTrace();
				Bukkit.shutdown();
			}
		}
	}
}
