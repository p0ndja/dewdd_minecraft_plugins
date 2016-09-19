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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(DigEventListener2.ac, mainLoop, 0, 20);

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
	public void eventja(AsyncPlayerChatEvent e) {
		if (e.getMessage().equalsIgnoreCase("gravity")) {
			e.getPlayer().sendMessage("0.6");
		}

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {

		if (!tr.isrunworld(DigEventListener2.ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;
		int counter = 0;
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {

					// b2 = block.getRelative(x, y, z);

					b2 = block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);

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
	public void eventja(BlockExplodeEvent e) {

		if (!tr.isrunworld(DigEventListener2.ac.getName(), e.getBlock().getWorld().getName())) {
			return;
		}

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;

		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {

					// b2 = block.getRelative(x, y, z);
					b2 = block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);

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
	public void eventja(BlockPlaceEvent e) {

		if (!tr.isrunworld(DigEventListener2.ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;
		int counter = 0;
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {

					// b2 = block.getRelative(x, y, z);

					b2 = block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);

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

class Gravity implements Runnable {
	public static int r = 1;

	public static int stick = 5;
	static long startTime = 0;

	static long countFailed = 0;

	static long countDone = 0;

	

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

	public Boolean canc = false;

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

		//Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, this);

	}

	public void recusiveSearchBlock(Block cur, Block start, LinkedList<Block> list) {
		// add

		Block tmp = null;
		int searchSpace = Gravity.r;
		for (int x = -searchSpace; x <= searchSpace; x++) {

			for (int z = -searchSpace; z <= searchSpace; z++) {
				// tmp = cur.getRelative(x, 0, z);
				tmp = cur.getWorld().getBlockAt(cur.getX() + x, cur.getY(), cur.getZ() + z);

				// dprint.r.printAll("loca " +
				// tr.locationToString(tmp.getLocation()));

				double xxx = Math.abs(tmp.getX() - start.getX());
				double zzz = Math.abs(tmp.getZ() - start.getZ());

				double dis = (xxx * xxx) + (zzz * zzz);
				dis = Math.pow(dis, 0.5);

				/*
				 * if (dis > stick) { continue; }
				 */

				if ((xxx > Gravity.stick) || (zzz > Gravity.stick)) {
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

					this.recusiveSearchBlock(tmp, start, list);

				} // chest
			}

		}

	}

	@Override
	public void run() {

		Gravity.startTime = System.currentTimeMillis();
		Gravity.countFailed = 0;
		Gravity.countDone = 0;

		if (MainLoop.lostTime > MainLoop.maxTime) {
			return;
		}
		// check it's has near block or not

		int r = Gravity.r;

		Block b2 = null;

		if (this.start.getY() == 0) {
			return;
		}
		if (Gravity.needBlock(this.start) == false) {
			return;
		}
		if (this.start.getRelative(0, -1, 0).getType() != Material.AIR) {
			return;
		}

		LinkedList<Block> list = new LinkedList<Block>();

		boolean found = UsefulFunction.isThisBlockHasRoot(this.start, this.start, list);

		long timeUsed = System.currentTimeMillis() - Gravity.startTime;
		MainLoop.lostTime += timeUsed;
		//dprint.r.printAll("><><> end " + (timeUsed) + " T " + Gravity.countDone + " F " + Gravity.countFailed + " sum "
		//		+ MainLoop.lostTime);

		if (found == false) {
			Material mat = this.start.getType();
			byte data = this.start.getData();
			this.start.setTypeId(0, true);
			this.start.getWorld().spawnFallingBlock(this.start.getLocation(), mat, data);

			int counter = 0;

			int tmpr = Gravity.stick;

			for (int x = -tmpr; x <= tmpr; x++) {
				for (int y = -tmpr; y <= tmpr; y++) {
					for (int z = -tmpr; z <= tmpr; z++) {
						counter++;
						// b2 = start.getRelative(x, y, z);

						b2 = this.start.getWorld().getBlockAt(this.start.getX() + x, this.start.getY() + y,
								this.start.getZ() + z);

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

class UsefulFunction {
	public static boolean isThisBlockHasRoot(Block cur, Block start, LinkedList<Block> usedList) {
		Block b2 = null;

		// add allow to usedList

		if (cur.getY() == 0) {
			return Gravity.needBlock(cur);
		}

		for (int y = -Gravity.r; y <= 0; y++) {
			for (int x = -Gravity.r; x <= Gravity.r; x++) {
				for (int z = -Gravity.r; z <= Gravity.r; z++) {

					// if (x == 0 && z == 0 && y== 0) { continue; }

					// b2 = cur.getRelative(x, y, z);

					b2 = cur.getWorld().getBlockAt(cur.getX() + x, cur.getY() + y, cur.getZ() + z);

					if (usedList.contains(b2) == true) {
						// dprint.r.printAll(tr.locationToString(b2.getLocation())
						// + " has it list continue");

						continue;
					}

					// b2.setType(Material.LOG);

					double xxx = Math.abs(b2.getX() - start.getX());
					double zzz = Math.abs(b2.getZ() - start.getZ());

					double dis = (xxx * xxx) + (zzz * zzz);
					dis = Math.pow(dis, 0.5);

					if ((xxx > Gravity.stick) || (zzz > Gravity.stick)) {
						// dprint.r.printAll("out of range");
						Gravity.countFailed++;
						continue;
					}

					// if (b2.getY() > start.getY() - Gravity.stick && b2.getY()
					// >
					// 0) {

					// more research !

					/*
					 * if (Gravity.needBlock(b2) == false) { continue; }
					 */

					usedList.add(b2);

					if (Gravity.needBlock(b2) == false) {
						Gravity.countFailed++;
						continue;
					}

					boolean ret = UsefulFunction.isThisBlockHasRoot(b2, start, usedList);

					if (ret == true) {
						// dprint.r.printAll(tr.locationToString(b2.getLocation())
						// + " = " + ret);
						Gravity.countDone++;
						return true;
					} else {
						Gravity.countFailed++;
						continue;
					}

				}

			}
		}
		// }
		return false;
	}
}

class MainLoop implements Runnable {
	public static TheJobType jobs = new TheJobType();

	public static long lostTime = 0;
	public static long maxTime = 50;
	public MainLoop() {

	}

	@Override
	public void run() {
		// always get Item from jobs
		

		long startTime = System.currentTimeMillis();

		//dprint.r.printAll("Recall " + MainLoop.jobs.getSize() + " " + MainLoop.lostTime);

		if (MainLoop.lostTime > maxTime) {
			MainLoop.lostTime -= maxTime;

			if (MainLoop.lostTime < 0) {
				MainLoop.lostTime = 0;
			}

			/*if (MainLoop.jobs.getSize() > 0) {
				dprint.r.printAll("(( sleep " + MainLoop.jobs.getSize() + " " + MainLoop.lostTime);
			}
*/
			return;
		}

		while (MainLoop.jobs.getSize() > 0 && lostTime < maxTime) {
			// dprint.r.printAll("done size " + done + " , " + jobs.getSize());

			if (MainLoop.lostTime > maxTime) {
				//dprint.r.printAll("__ break cuz time out " + (MainLoop.lostTime));
				return;
			}

			Location loc = MainLoop.jobs.get();

			if (loc != null) {

				Block blo = loc.getBlock();
				if (Gravity.needBlock(blo) == false) {
					continue;
				}

				Gravity noop = new Gravity(blo, null, 1);
				Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, noop);


			}

			if (MainLoop.jobs.getSize() > 0) {
				//dprint.r.printAll("*** size " + MainLoop.jobs.getSize() + " " + MainLoop.lostTime);
			}

		}

	}
}

class TheJobType {
	private LinkedList<Location> loc = new LinkedList<Location>();

	public synchronized Location get() {
		boolean found = false;
		Location tmp2 = null;

		while ((found == false) && (this.loc.size() > 0)) {
			// dprint.r.printAll("get()k size " + loc.size());

			Location tmp = this.loc.get(0);
			this.loc.remove(0);

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

	public int getSize() {
		return this.loc.size();
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

}