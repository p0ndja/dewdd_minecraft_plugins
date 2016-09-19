/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgravity;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

class Gravity implements Runnable {
	public Boolean canc = false;

	public static int r = 1;
	public static int stick = 5;

	private Block start;

	private Player player = null;
	private int curDelay = 40;

	public Gravity(Block start, Player player, int curDelay) {
		this.start = start;
		this.player = player;
		this.curDelay = curDelay;
		Random rnd = new Random();

		// Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
		// this, rnd.nextInt(100) + 20);

		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, this);

	}

	public boolean isThisBlockHasRoot(Block cur, Block start) {
		Block b2 = null;
		// cur.setType(Material.STAINED_GLASS_PANE);

		// Random rnd = new Random();
		// cur.setData((byte) rnd.nextInt(15));

		/*
		 * if (root.amount > 10) { root.amount -- ; return; }
		 */

		for (int x = -r; x <= r; x++) {

			for (int z = -r; z <= r; z++) {

				/*
				 * if (x == 0 && z == 0) { continue; }
				 */

				b2 = cur.getRelative(x, -1, z);
				// b2.setType(Material.LOG);

				double xxx = Math.abs(b2.getX() - start.getX());
				double zzz = Math.abs(b2.getZ() - start.getZ());

				double dis = (xxx * xxx) + (zzz * zzz);
				dis = Math.pow(dis, 0.5);

				if (xxx > stick || zzz > stick) {
					continue;
				}

				//if (b2.getY() > start.getY() - Gravity.stick && b2.getY() > 0) {
					 if (b2.getY() > 0) {
					// more research !

					/*
					 * if (Gravity.needBlock(b2) == false) { continue; }
					 */
					if (b2.getType() == Material.AIR) {
						continue;
					}

					boolean ret = isThisBlockHasRoot(b2, start);
					if (ret == true) {
						return true;
					} else {
						continue;
					}

				} else {

					return true;
				}

			}

		}
		// }
		return false;
	}

	public static boolean needBlock(Block block) {
		switch (block.getType()) {
		case STATIONARY_WATER:
		case WATER:
		case STATIONARY_LAVA:
		case LAVA:
		case AIR:
			// case STAINED_GLASS_PANE:
			return false;
		default:
			return true;
		}

	}

	public void recusiveSearchBlock(Block cur, Block start, LinkedList<Block> list) {
		// add

		Block tmp = null;
		int searchSpace = r;
		for (int x = -searchSpace; x <= searchSpace; x++) {

			for (int z = -searchSpace; z <= searchSpace; z++) {
				tmp = cur.getRelative(x, 0, z);
				// dprint.r.printAll("loca " +
				// tr.locationToString(tmp.getLocation()));

				double xxx = Math.abs(tmp.getX() - start.getX());
				double zzz = Math.abs(tmp.getZ() - start.getZ());

				double dis = (xxx * xxx) + (zzz * zzz);
				dis = Math.pow(dis, 0.5);

				/*
				 * if (dis > stick) { continue; }
				 */

				if (xxx > stick || zzz > stick) {
					continue;
				}

				if (Gravity.needBlock(tmp) == true) {
					// open it

					boolean searchChest = false;
					for (int i = 0; i < list.size(); i++) {
						Block inList = list.get(i);

						if (tmp.getLocation().getBlockX() == inList.getX()) {
							if (tmp.getLocation().getBlockY() == inList.getY()) {
								if (tmp.getLocation().getBlockZ() == inList.getZ()) {
									searchChest = true;
									break;
								}
							}
						}
					}
					if (searchChest == true) {
						continue;
					}

					list.add(tmp);

					// dprint.r.printAll("found block " +
					// tr.locationToString(tmp.getLocation())
					// + " size " + list.size());

					// call recursive

					recusiveSearchBlock(tmp, start, list);

				} // chest
			}

		}

	}

	@Override
	public void run() {

		// check it's has near block or not

		int r = Gravity.r;

		Block b2 = null;

		if (start.getY() == 0) {
			return;
		}
		if (Gravity.needBlock(start) == false) {
			return;
		}
		if (start.getRelative(0, -1, 0).getType() != Material.AIR) {
			return;
		}

		LinkedList<Block> list = new LinkedList<Block>();
		list.add(start);

		// dprint.r.printAll("start " +
		// tr.locationToString(start.getLocation()));

		recusiveSearchBlock(start, start, list);

		int gx = 0;
		if (gx == 0) {
			/*
			 * for (int l = 0 ; l < list.size() ; l ++) {
			 * dprint.r.printAll(tr.locationToString( list.get(l).getLocation())
			 * + " " + list.size()) ; } return;
			 */
		}

		Random rnd = new Random();

		for (int l = 0; l < list.size(); l++) {
			Block cur = list.get(l);

		}

		boolean found = false;
		for (int l = 0; l < list.size(); l++) {
			Block cur = list.get(l);
			// cur.setType(Material.LOG);
			// cur.setData((byte) rnd.nextInt(4));
			/*
			 * cur.setType(Material.STAINED_GLASS); cur.setData((byte)
			 * rnd.nextInt(15));
			 */

			for (int x = -r; x <= r; x++) {
				for (int z = -r; z <= r; z++) {

					b2 = cur.getRelative(x, -1, z);

					if (Gravity.needBlock(b2) == false) {
						continue;
					}

					found = isThisBlockHasRoot(b2, start);
					if (found == true) {
						break;
					}

				}
				if (found == true) {
					break;
				}
			}

			if (found == true) {
				break;
			}
		}

		if (found == false) {
			Material mat = start.getType();
			byte data = start.getData();
			start.setTypeId(0, true);
			start.getWorld().spawnFallingBlock(start.getLocation(), mat, data);

			int counter = 0;

			int tmpr = Gravity.stick;

			for (int x = -tmpr; x <= tmpr; x++) {
				for (int y = -tmpr; y <= tmpr; y++) {
					for (int z = -tmpr; z <= tmpr; z++) {
						counter++;
						b2 = start.getRelative(x, y, z);

						if (Gravity.needBlock(b2) == false) {
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

	public int getSize() {
		return loc.size();
	}

	public void put(Location loc) {
		Block blo = loc.getBlock();
		if (blo == null) {
			return;
		}
		/*
		 * if (blo.getType().isSolid() == false) { return; }
		 */

		if (Gravity.needBlock(blo) == false) {
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

		while (found == false && loc.size() > 0) {
			// dprint.r.printAll("get()k size " + loc.size());

			Location tmp = loc.get(0);
			loc.remove(0);

			if (tmp == null) {
				continue;
			}

			if (Gravity.needBlock(tmp.getBlock()) == false) {
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

		int done = 0;

		while (done <= 1 && jobs.getSize() > 0) {
			// dprint.r.printAll("done size " + done + " , " + jobs.getSize());

			Location loc = jobs.get();

			if (loc != null) {

				Block blo = loc.getBlock();
				if (Gravity.needBlock(blo) == false) {
					continue;
				}

				Gravity noop = new Gravity(blo, null, 1);
				
				
			}
done++;
		}

		if (jobs.getSize() > 0) {
			dprint.r.printC("size " + jobs.getSize());
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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(DigEventListener2.ac, mainLoop, 0, 5);

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
	public void eventja(BlockExplodeEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getBlock().getWorld().getName()))
			return;

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;

		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {

					b2 = block.getRelative(x, y, z);

					if (Gravity.needBlock(b2) == false) {
						continue;
					}

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

		int r = Gravity.r;
		int counter = 0;
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {

					b2 = block.getRelative(x, y, z);

					if (Gravity.needBlock(b2) == false) {
						continue;
					}

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
			e.getPlayer().sendMessage("0.3");
		}

	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;
		int counter = 0;
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {

					b2 = block.getRelative(x, y, z);

					if (Gravity.needBlock(b2) == false) {
						continue;
					}

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