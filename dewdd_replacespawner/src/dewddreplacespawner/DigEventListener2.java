/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddreplacespawner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {

	public JavaPlugin ac = null;
	boolean replacespawner = true;

	public String pseecommand = "dewdd.main.seecommand";

	public String getStringBlock(Block b) {
		String abc = b.getX() + "," + b.getY() + "," + b.getZ();
		return abc;
	}

	class replacespawner implements Runnable {
		private Block block;

		public replacespawner(Block block) {
			this.block = block;
		}

		@Override
		public void run() {
			int search = 10;

			for (int x = -search; x <= search; x++)
				for (int y = -search; y <= search; y++)
					for (int z = -search; z <= search; z++) {
						// System.out.println("checking " +
						// getStringBlock(block)+ " = " + x + "," + y + "," + z
						// );

						Block t = block.getRelative(x, y, z);
						if (t.getType() == Material.MOB_SPAWNER) {
							// System.out.println("found spawner " +
							// getStringBlock(t));

							for (int x2 = -search / 2; x2 <= search / 2; x2++)
								for (int y2 = -search / 2; y2 <= search / 2; y2++)
									for (int z2 = -search / 2; z2 <= search / 2; z2++) {

										Block r = t.getRelative(x2, y2, z2);
										r.setType(Material.STONE);
										// System.out.println("replace " +
										// getStringBlock(r));

									}
						}

					}
		}
	}

	@EventHandler
	public void eventja(BlockBreakEvent event) {
		Block block = event.getBlock();

		replacespawner rs = new replacespawner(block);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, rs,20);
	}
	

	@EventHandler
	public void eventja(BlockPlaceEvent event) {
		Block block = event.getBlock();

		replacespawner rs = new replacespawner(block);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, rs,20);
	}

	@EventHandler
	public void eventja(CreatureSpawnEvent event) {
		Block block = event.getLocation().getBlock();

		replacespawner rs = new replacespawner(block);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, rs,20);
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("/replacespawner") == true) {
			replacespawner = !replacespawner;
			event.getPlayer().sendMessage("ptdew&dewdd : replacespawner = " + replacespawner);
			return;
		}
	}

	// PlayerDeathEvent

	// PlayerInteractEvent

	// PlayerRespawnEvent

} // class
