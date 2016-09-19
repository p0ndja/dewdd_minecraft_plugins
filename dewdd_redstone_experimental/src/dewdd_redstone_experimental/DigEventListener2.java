/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_redstone_experimental;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

class Delayed implements Runnable {
	public Delayed() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		while (DigEventListener2.ac == null) {
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (DigEventListener2.redex == null) {
			DigEventListener2.redex = new Redex(Bukkit.getWorld("world"));
		}
		RedstoneTimer ti = new RedstoneTimer();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(DigEventListener2.ac,
				ti, 0, 2);
	}

}

public class DigEventListener2 implements Listener {

	public static JavaPlugin	ac	= null;

	public static Redex			redex;

	Random						rnd	= new Random();

	public DigEventListener2() {
		Delayed ti = new Delayed();
		Thread th = new Thread(ti);
		th.start();

	}

	@EventHandler
	public void eventja(BlockPistonExtendEvent e) {
		if (!this.isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}

		Block b = e.getBlock();

		// check 12 block

		Block b2 = b;

		int curid = DigEventListener2.redex
				.getIdOfThisLocation(b.getLocation());
		if (curid == -1) {
			return;
		}

		// dprint.r.printAll("curBlock " +
		// tr.locationToString(b.getLocation()));

		for (int lop = 0; lop < e.getBlocks().size(); lop++) {
			b2 = e.getBlocks().get(lop);

			if (b2.getType() == Material.AIR) {
				continue;
			}

			int extendid = DigEventListener2.redex
					.getIdOfThisLocation(b2.getLocation());
			// dprint.r.printAll(tr.locationToString(b2.getLocation()));
			if (extendid != curid) {

				// dprint.r.printAll("Block Piston Extend out of area " + curid
				// + " out " + extendid);
				// game Over

				DigEventListener2.redex.listEx.get(curid).isRunning = false;
				DigEventListener2.redex.listEx.get(curid).outOfRange = true;
				e.setCancelled(true);
				return;
			}

			if (lop == (e.getBlocks().size() - 1)) {
				b2 = b2.getRelative(e.getDirection());
				extendid = DigEventListener2.redex
						.getIdOfThisLocation(b2.getLocation());
				// dprint.r.printAll(tr.locationToString(b2.getLocation()));
				if (extendid != curid) {

					// dprint.r.printAll("Block Piston Extend out of area " +
					// curid + " out " + extendid);
					// game Over

					DigEventListener2.redex.listEx.get(curid).isRunning = false;
					DigEventListener2.redex.listEx.get(curid).outOfRange = true;
					e.setCancelled(true);

					return;
				}
			}

		}

		DigEventListener2.redex.listEx
				.get(curid).lastRedstoneActivity = Redex.redstoneTimer;

		/*
		 * 
		 * 
		 * for (int lop = 0; lop <= 12; lop++) { b2 =
		 * b2.getRelative(e.getDirection()); if (b2.getType() == Material.AIR) {
		 * continue; }
		 * 
		 * int extendid = redex.getIdOfThisLocation(b2.getLocation());
		 * dprint.r.printAll(tr.locationToString(b2.getLocation())); if
		 * (extendid != curid) {
		 * 
		 * dprint.r.printAll("Block Piston Extend out of area " + curid +
		 * " out " + extendid); // game Over
		 * 
		 * redex.listEx.get(curid).isRunning = false;
		 * 
		 * return; }
		 * 
		 * }
		 */
	}

	@EventHandler
	public void eventja(BlockPistonRetractEvent e) {
		if (!this.isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}
	}

	@EventHandler
	public void eventja(BlockRedstoneEvent e) {
		if (!this.isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}

		Block b = e.getBlock();

		int curid = DigEventListener2.redex
				.getIdOfThisLocation(b.getLocation());
		if (curid == -1) {
			return;
		}

		DigEventListener2.redex.listEx
				.get(curid).lastRedstoneActivity = Redex.redstoneTimer;
	}

	/*
	 * @EventHandler public void eventja(BlockPistonEvent e) { if
	 * (!isrunworld(e.getBlock().getWorld().getName())) { return; } }
	 */

	@EventHandler
	public void eventja(ChunkUnloadEvent e) {
		if (!this.isrunworld(e.getChunk().getWorld().getName())) {
			return;
		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!this.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		CommandRuning newThread = new CommandRuning(m, player,
				DigEventListener2.redex);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
				newThread);

	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {

		if (!this.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Action act;
		act = e.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK)
				|| (act == Action.LEFT_CLICK_BLOCK)) == false) {
			return;
		}

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

	}

	public boolean isrunworld(String worldName) {
		return tr.isrunworld(DigEventListener2.ac.getName(), worldName);
	}
} // class
