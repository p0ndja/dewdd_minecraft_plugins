/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddforcespawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

class ForceSpawn implements Runnable {
	private Player player;

	public ForceSpawn(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		World world = Bukkit.getWorld(tr.gettr("CONFIG FORCESPAWN WORLD NAME").trim());
		if (world == null) {
			world = player.getWorld();
			dprint.r.printAll("ForecSpawn World '" + world.getName() + "' or '" + tr.gettr("CONFIG FORCESPAWN WORLD NAME") + "' == null" );
		}

		try {
			double x = Double.parseDouble(tr.gettr("CONFIG FORCESPAWR X"));
			double y = Double.parseDouble(tr.gettr("CONFIG FORCESPAWR Y"));
			double z = Double.parseDouble(tr.gettr("CONFIG FORCESPAWR Z"));

			Location loc2 = player.getLocation();
			loc2.setWorld(world);
			loc2.setX(x);
			loc2.setY(y);
			loc2.setZ(z);

			player.teleport(loc2);

		} catch (Exception e) {
			e.printStackTrace();
			dprint.r.printC("ForceSpawn error can't convert xyz to double type");

		}
	}
}

public class DigEventListener2 implements Listener {
	public static JavaPlugin ac = null;

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		ForceSpawn fs = new ForceSpawn(e.getPlayer());
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, fs);
	}

} // class
