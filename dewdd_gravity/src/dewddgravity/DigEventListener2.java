/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgravity;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

class Gravity implements Runnable {
	public Boolean canc = false;

	public static int r = 1;
	public static int search = 3;
	public static int stick = 4;
	public static int rRoot = 10;

	private Block block;
	private Block middle;

	private Player player = null;
	private int curDelay = 40;

	public Gravity(Block block, Player player, Block middle, int curDelay) {
		this.block = block;
		this.middle = middle;
		this.player = player;
		this.curDelay = curDelay;
		Random rnd = new Random();

		// Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
		// this, rnd.nextInt(100) + 20);

		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, this);

	}

	class RootType {
		boolean foundRoot = false;
		int amount = 0;

	}

	public void isThisBlockHasRoot(Block cur, Block start, RootType root) {
		Block b2 = null;

		root.amount++;
		/*
		 * if (root.amount > 10) { root.amount -- ; return; }
		 */

		for (int y = -r; y <= -1 && cur.getY() + y >= 0; y++) {
			for (int x = -r; x <= r; x++) {
				for (int z = -r; z <= r; z++) {
					if (root.foundRoot == true) {
						return;
					}
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}

					b2 = cur.getRelative(x, y, z);
					// b2.setType(Material.LOG);

					double xxx = Math.abs(b2.getX() - start.getX());
					double zzz = Math.abs(b2.getZ() - start.getZ());

					double dis = (xxx * xxx) + (zzz * xxx);
					dis = Math.pow(dis, 0.5);

					if (dis > 5) {
						continue;
					}

					/*
					 * if (b2.getLocation().distance(start.getLocation()) > 5) {
					 * continue; }
					 */

					if (b2.getType() == Material.AIR) {
						continue;
					}

					/*
					 * if (b2.getType().isSolid() == false) { continue; }
					 */

					if (b2.getType() != Material.AIR) {
						if (b2.getY() > start.getY() - rRoot && b2.getY() > 0) {
							// more research !

							isThisBlockHasRoot(b2, start, root);
						} else {
							root.foundRoot = true;
							return;
						}
					}

				}
			}
		}

		root.amount--;
	}

	@Override
	public void run() {

		// check it's has near block or not

		int r = Gravity.r;

		Block b2 = null;

		if (block.getY() == 0) {
			return;
		}
		if (block.getType() == Material.AIR) {
			return;
		}
		if (block.getRelative(0, -1, 0).getType() != Material.AIR) {
			return;
		}

		RootType root = new RootType();
		root.foundRoot = false;

		for (int x = -stick; x <= stick; x++) {
			for (int z = -stick; z <= stick; z++) {
				if (root.foundRoot == true) {
					return;
				}

				b2 = block.getRelative(x, 0, z);
				isThisBlockHasRoot(b2, block, root);
			}
		}

		if (root.foundRoot == false) {
			Material mat = block.getType();
			byte data = block.getData();
			block.setTypeId(0, false);
			block.getWorld().spawnFallingBlock(block.getLocation(), mat, data);

			int counter = 0;

			for (int x = -search; x <= search; x++) {
				for (int y = -search; y <= search; y++) {
					for (int z = -search; z <= search; z++) {
						counter++;
						b2 = block.getRelative(x, y, z);

						if (b2.getType() == Material.AIR) {
							continue;
						}

						MainLoop.jobs.put(b2.getLocation());
						// Gravity noop = new Gravity(b2, player, block, counter
						// * 30);

					}
				}

			}

		} // sync
	}
}

class TheJobType {
	private LinkedList<Location> loc = new LinkedList<Location>();

	public synchronized int getSize() {
		return loc.size();
	}

	public synchronized void put(Location loc) {
		Block blo = loc.getBlock();
		if (blo == null) {
			return;
		}
		/*
		 * if (blo.getType().isSolid() == false) { return; }
		 */

		if (blo.getType() == Material.AIR) {
			return;

		}
		
		// search
		if (this.loc.contains(loc) == false) {
		
		this.loc.add(loc);

		}
		// dprint.r.printAll("put new " + MainLoop.jobs.getSize());
	}

	public synchronized Location get() {
		boolean found = false;
		Location tmp2 = null;

		while (found == false && loc.size() >= 1) {

			Location tmp = loc.get(0);
			loc.remove(0);

			if (tmp == null) {
				continue;
			}

			if (tmp.getBlock().getType() == Material.AIR) {
				continue;
			}

			/*
			 * if (tmp.getBlock().getType().isSolid() == false) { continue; }
			 */

			tmp2 = tmp;
			found = true;

		}

		return tmp2;
	}

}

class MainLoop implements Runnable {
	public static TheJobType jobs = new TheJobType();

	public MainLoop() {

	}

	@Override
	public void run() {
		// always get Item from jobs
	
		
			Location loc = jobs.get();

			if (loc != null) {
				Block blo = loc.getBlock();
				Gravity noop = new Gravity(blo, null, blo, 1);
				dprint.r.printAll("size " + jobs.getSize());
			}

	}
}

class Delay implements Runnable {
	@Override
	public void run() {
		while (DigEventListener2.ac == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		MainLoop mainLoop = new MainLoop();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(DigEventListener2.ac, mainLoop, 0, 1);

	}
}

public class DigEventListener2 implements Listener {

	public static JavaPlugin ac = null;

	private Random rnd = new Random();

	public DigEventListener2() {
		Delay delay = new Delay();
		Thread th = new Thread(delay);
		th.start();

	}

	// BlockPlaceEvent

	@EventHandler
	public void eventja(BlockPhysicsEvent e) {

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;
		int counter = 0;

		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					counter++;
					b2 = block.getRelative(x, y, z);
					/*
					 * if (b2.getType() == Material.AIR) { continue; }
					 */
					// Gravity noop = new Gravity(b2, null, block, counter *
					// 25);
					MainLoop.jobs.put(b2.getLocation());

				}
			}

		}

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.search;
		int counter = 0;

		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					counter++;

					b2 = block.getRelative(x, y, z);

					/*
					 * if (b2.getType() == Material.AIR) { continue; }
					 */
					// Gravity noop = new Gravity(b2, null, block, counter *
					// 25);
					MainLoop.jobs.put(b2.getLocation());

				}
			}

		}

	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent e) {
		if (e.getMessage().equalsIgnoreCase("gravity")) {
			e.getPlayer().sendMessage("0.2");
		}

	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.search;
		int counter = 0;
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					counter++;
					b2 = block.getRelative(x, y, z);

					/*
					 * if (b2.getType() == Material.AIR) { continue; }
					 */
					// Gravity noop = new Gravity(b2, null, block, counter *
					// 25);
					MainLoop.jobs.put(b2.getLocation());

				}
			}

		}

	}

	// PlayerDeathEvent

	// PlayerInteractEvent

} // class