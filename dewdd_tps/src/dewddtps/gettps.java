/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtps;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class gettps implements Listener {
	class close_chunk implements Runnable {

		public void run() {

		/*	dprint.r.printC("Online Player = "
					+ Bukkit.getOnlinePlayers().length);
*/
			if (Bukkit.getOnlinePlayers().length == 0) {

				for (World w : Bukkit.getWorlds()) {
					for (org.bukkit.Chunk chunk : w.getLoadedChunks()) {
						/*dprint.r.printC("unload chunk cuz no player = "
								+ (chunk.getX() * 16) + ","
								+ (chunk.getZ() * 16) + " as  "
								+ chunk.getWorld().getName());*/
						chunk.unload(true);
					}
				}

			}
		}
	}

	class recu extends Thread {

		public void run() {

			while (true) {
				try {
					Thread.sleep(60000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				close_c(1200);
			}
		}
	}

	public JavaPlugin	ac	= null;

	public gettps() {
		recu arr = new recu();
		arr.start();
	}

	public void close_c(long delay) {
		close_chunk abc = new close_chunk();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, delay);
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {

		if (event.getMessage().equalsIgnoreCase("/dewtps") == true) {
			dprint.r.printAll("dew tps = " + tps.getTPS());
		}
	}

	@EventHandler
	public void eventja(PlayerQuitEvent event) {
		close_c(200);
	}

} // class

