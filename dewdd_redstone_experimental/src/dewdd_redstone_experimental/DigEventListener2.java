/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_redstone_experimental;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import core_optimization_api.Chromosome;
import dewddtran.tr;
import ga_optimization_api.Hybrid;
import li.LXRXLZRZType;

class AreaType {
	public int id = -1;

	public LXRXLZRZType loc;

	public World world;

	public long curTick = 0;
	public long lastTimeBetter = 0;

	public double score = 0;

	public Chromosome chro;

	public boolean isRunning = false;

	public Block getBlocklxlylz() {
		Block block = world.getBlockAt(loc.lx, loc.ly, loc.lz);
		return block;
	}

}

class FitnessAllArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public FitnessAllArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		// dprint.r.printAll("CleanAllArea curid " + curId);

		AreaType at = redex.listEx.get(curId);

		FitnessSubArea sub = new FitnessSubArea(redex, curId);
		sub.run();

	}
}

class FitnessSubArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public FitnessSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		dprint.r.printAll("Fitness curid " + curId);

		AreaType at = redex.listEx.get(curId);

		int countTrue = 0;
		int countFalse = 0;

		boolean clean1 = false;

		for (int x = redex.start.loc.lx; x <= redex.start.loc.rx; x++) {

			for (int y = redex.start.loc.ly; y <= redex.start.loc.ry; y++) {

				for (int z = redex.start.loc.lz; z <= redex.start.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - redex.start.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - redex.start.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == Material.AIR) {
						continue;
					}

					if (hostBlock.getType() == setBlock.getType() && hostBlock.getData() == setBlock.getData()) {
						countTrue++;
						continue;
					} else {
						countFalse++;
					}

					/*
					 * clean1 = true;
					 * setBlock.setTypeIdAndData(hostBlock.getType().getId(),
					 * hostBlock.getData(), true);
					 */
				}
			}
		}

		double score = (countTrue * 100) / (countTrue + countFalse);
		at.score = score;

	}
}

class CleanAllArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public CleanAllArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		// dprint.r.printAll("CleanAllArea curid " + curId);

		AreaType at = redex.listEx.get(curId);

		CleanSubArea sub = new CleanSubArea(redex, curId);
		sub.run();

		curId++;
		if (curId < redex.listEx.size()) {
			// recall own self
			CleanAllArea caa = new CleanAllArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
		}

	}
}

class CleanSubArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public CleanSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		dprint.r.printAll("Cleaning curid " + curId);

		AreaType at = redex.listEx.get(curId);

		boolean clean1 = false;

		for (int x = redex.start.loc.lx; x <= redex.start.loc.rx; x++) {

			for (int y = redex.start.loc.ly; y <= redex.start.loc.ry; y++) {

				for (int z = redex.start.loc.lz; z <= redex.start.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - redex.start.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - redex.start.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == setBlock.getType() && hostBlock.getData() == setBlock.getData()) {
						continue;
					}

					clean1 = true;
					setBlock.setTypeIdAndData(hostBlock.getType().getId(), hostBlock.getData(), true);

				}
			}
		}

		// if still has to clean try again
		if (clean1 == true) {
			CleanSubArea sub2 = new CleanSubArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, sub2);

			sub2 = new CleanSubArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, sub2);
		}
	}
}

class CommandRuning implements Runnable {
	private String m[];
	private Player p;
	private Redex redex;

	public CommandRuning(String m[], Player p, Redex redex) {
		this.m = m;
		this.p = p;
		this.redex = redex;

	}

	@Override
	public void run() {
		if (m[0].equalsIgnoreCase("/redex")) {
			if (p.hasPermission(Redex.predex) == false) {
				p.sendMessage(tr.gettr("you don't have permission" + Redex.predex));
				return;
			} else {
				if (m.length == 1) {
					p.sendMessage("/redex start");
					p.sendMessage("/redex clean");
					p.sendMessage("/redex decode");

				} else if (m.length >= 2) {

					// if (rene)
					dprint.r.printAll("command run redex null == " + (redex == null ? "yes" : "no") + " ");

					if (m[1].equalsIgnoreCase("start")) {

						// start process
						redex = new Redex(p.getWorld(), p);
						redex.CleanAllArea();

					} else if (m[1].equalsIgnoreCase("clean")) {
						if (m.length == 2) {

							// start process
							redex = new Redex(p.getWorld(), p);
							redex.CleanAllArea();
						} else if (m.length == 3) {
							redex = new Redex(p.getWorld(), p);
							CleanSubArea cc = new CleanSubArea(redex, Integer.parseInt(m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);
						}

					} else if (m[1].equalsIgnoreCase("decode")) {
						if (m.length == 2) {
							// start process
							redex = new Redex(p.getWorld(), p);
							redex.DecodeAllArea();
						} else if (m.length == 3) {
							redex = new Redex(p.getWorld(), p);
							DecodeSubDNA cc = new DecodeSubDNA(redex, Integer.parseInt(m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);

						}
					}

				}

			}
		}

	}

}

class DecodeAllDNA implements Runnable {
	private Redex redex;
	private int curId = 0;

	public DecodeAllDNA(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		// dprint.r.printAll("Decoding curid " + curId);

		AreaType at = redex.listEx.get(curId);

		DecodeSubDNA sub = new DecodeSubDNA(redex, curId);
		sub.run();

		curId++;
		if (curId < redex.listEx.size()) {
			// recall own self
			DecodeAllDNA caa = new DecodeAllDNA(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
		}

	}
}

class DecodeSubDNA implements Runnable {
	private Redex redex;
	private int curId = 0;

	public DecodeSubDNA(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {
		/*
		 * if (curId >= 1) { return; }
		 */
		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		dprint.r.printAll("Decoding curid " + curId);

		dprint.r.printAll("dnaList Length " + redex.dnaList.size());
		AreaType at = redex.listEx.get(curId);
		at.isRunning = true;
		Chromosome dna = redex.dnaList.get(curId);

		int diffX = redex.output.loc.rx - redex.output.loc.lx;
		int diffY = redex.output.loc.ry - redex.output.loc.ly;
		int diffZ = redex.output.loc.rz - redex.output.loc.lz;

		// decoding DNA in these range

		int startPoint = 0;

		// 0 piston
		// 1 sticky Piston
		// 2 Slime Block
		// 3 RedStone Block

		Block leftTopBlock = at.getBlocklxlylz();

		while (startPoint + 4 < Redex.dnaLength) {
			double tmpx = Math.abs(dna.dna[startPoint]) * diffX;
			tmpx = tmpx % diffX;
			int posx = (int) tmpx;

			double tmpy = Math.abs(dna.dna[startPoint + 1]) * diffY;
			tmpy = tmpy % diffY;
			int posy = (int) tmpy;

			double tmpz = Math.abs(dna.dna[startPoint + 2]) * diffZ;
			tmpx = tmpz % diffZ;
			int posz = (int) tmpz;

			double tmpb = Math.abs(dna.dna[startPoint + 3]) * 4;
			tmpb = tmpb % 4;
			int tmpb2 = (int) tmpb;

			byte pistonFace = 0; // piston face

			Material setType = null;
			switch (tmpb2) {
			case 0:

				if (startPoint + 4 >= Redex.dnaLength) {
					return;
				}

				setType = Material.PISTON_BASE;

				// dprint.r.printAll("piston grep " +
				// Math.abs(dna.dna[startPoint + 4]));

				int tmpFace = (int) Math.round(Math.abs(dna.dna[startPoint + 4]) * 6);
				if (tmpFace < 0) {
					tmpFace = 0;
				}
				if (tmpFace > 5) {
					tmpFace = 5;
				}
				pistonFace = (byte) tmpFace;

				// dprint.r.printAll("piston face " + pistonFace);

				break;
			case 1:
				if (startPoint + 4 >= Redex.dnaLength) {
					return;
				}

				setType = Material.PISTON_STICKY_BASE;

				// dprint.r.printAll("piston grep " +
				// Math.abs(dna.dna[startPoint + 4]));

				tmpFace = (int) Math.round(Math.abs(dna.dna[startPoint + 4]) * 6);
				if (tmpFace < 0) {
					tmpFace = 0;
				}
				if (tmpFace > 5) {
					tmpFace = 5;
				}
				pistonFace = (byte) tmpFace;
				// dprint.r.printAll("piston face " + pistonFace);

				break;
			case 2:
				setType = Material.SLIME_BLOCK;
				break;
			case 3:
				setType = Material.REDSTONE_BLOCK;
				break;
			default:
				setType = Material.AIR;
				break;

			}

			// time to set Block
			Block b2 = leftTopBlock.getRelative(posx, posy, posz);

			if (b2.getType() == Material.AIR) {
				b2.setType(setType);
				b2.setData(pistonFace);

			}
			dprint.r.printC(posx + "," + posy + "," + posz + " = " + setType.name());

			startPoint += 4;
		}

	}
}

public class DigEventListener2 implements Listener {

	public static JavaPlugin ac = null;

	Random rnd = new Random();

	Redex redex;

	@EventHandler
	public void eventja(ChunkUnloadEvent e) {
		if (!isrunworld(e.getChunk().getWorld().getName())) {
			return;
		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		if (redex == null) {
			redex = new Redex(player.getWorld(), player);
		}
		CommandRuning newThread = new CommandRuning(m, player, redex);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, newThread);

	}

	@EventHandler
	public void eventja(BlockRedstoneEvent e) {
		if (!isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}
	}

	/*
	 * @EventHandler public void eventja(BlockPistonEvent e) { if
	 * (!isrunworld(e.getBlock().getWorld().getName())) { return; } }
	 */

	@EventHandler
	public void eventja(BlockPistonExtendEvent e) {
		if (!isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}

		Block b = e.getBlock();

		// check 12 block

		Block b2 = b;

		int curid = redex.getIdOfThisLocation(b.getLocation());
		if (curid == -1) {
			return;
		}

		//dprint.r.printAll("curBlock " + tr.locationToString(b.getLocation()));

		for (int lop = 0; lop < e.getBlocks().size(); lop++) {
			b2 = e.getBlocks().get(lop);

			if (b2.getType() == Material.AIR) {
				continue;
			}

			int extendid = redex.getIdOfThisLocation(b2.getLocation());
			//dprint.r.printAll(tr.locationToString(b2.getLocation()));
			if (extendid != curid) {

				//dprint.r.printAll("Block Piston Extend out of area " + curid + " out " + extendid);
				// game Over

				redex.listEx.get(curid).isRunning = false;
				e.setCancelled(true);
				return;
			}

			if (lop == e.getBlocks().size() - 1) {
				b2 = b2.getRelative(e.getDirection());
				extendid = redex.getIdOfThisLocation(b2.getLocation());
				//dprint.r.printAll(tr.locationToString(b2.getLocation()));
				if (extendid != curid) {

					//dprint.r.printAll("Block Piston Extend out of area " + curid + " out " + extendid);
					// game Over

					redex.listEx.get(curid).isRunning = false;
					e.setCancelled(true);

					return;
				}
			}

		}

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
		if (!isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}
	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {

		if (!isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Action act;
		act = e.getAction();

		if ((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)
			return;

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

	}

	public boolean isrunworld(String worldName) {
		return tr.isrunworld(ac.getName(), worldName);
	}
} // class

class Redex {
	public static int maxPopulation = 1000;
	public static long maxNothingBetterInTick = (60 * 5) * 20;

	public static String predex = "dewdd.redex.run";
	public static int spaceBlockEachArea = 5;

	// public static long keepCheckingBetterInTick = (60)

	public static int dnaLength = 300;// 1125;
	public AreaType output = new AreaType();

	public AreaType start = new AreaType();
	public World world;

	public LinkedList<AreaType> listEx = new LinkedList<AreaType>();

	public Player player;

	public ArrayList<Chromosome> dnaList;

	public Hybrid hybrid;

	public Redex(World world, Player player) {
		this.world = world;
		this.player = player;

		hybrid = new Hybrid();
		hybrid.setDnaLength(Redex.dnaLength);
		hybrid.setPopulationSize(Redex.maxPopulation);
		hybrid.prepareToRunGA();

		dnaList = hybrid.getPopulation();

		// load Start Area
		start = new AreaType();
		int stlx = (int) tr.gettrint("CONFIG_REDEX_START_LX");
		int stly = (int) tr.gettrint("CONFIG_REDEX_START_LY");
		int stlz = (int) tr.gettrint("CONFIG_REDEX_START_LZ");

		int strx = (int) tr.gettrint("CONFIG_REDEX_START_RX");
		int stry = (int) tr.gettrint("CONFIG_REDEX_START_RY");
		int strz = (int) tr.gettrint("CONFIG_REDEX_START_RZ");

		start.world = this.world;
		start.loc = new LXRXLZRZType(stlx, stly, stlz, strx, stry, strz);

		// load output Area
		output = new AreaType();
		int oplx = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_LX");
		int oply = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_LY");
		int oplz = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_LZ");

		int oprx = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_RX");
		int opry = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_RY");
		int oprz = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_RZ");

		output.world = this.world;
		output.loc = new LXRXLZRZType(oplx, oply, oplz, oprx, opry, oprz);

		// ...................................................................

		// Create All Area

		listEx.clear();

		int startWidthX = start.loc.rx - start.loc.lx;
		int startWidthZ = start.loc.rz - start.loc.lz;

		int square = (int) Math.sqrt(maxPopulation);

		int countX = -1;
		int countZ = 0;

		for (int lop = 0; lop < maxPopulation; lop++) {

			countX++;
			if (countX > square) {

				countX = 0;
				countZ++;
			}

			AreaType newArea = new AreaType();
			newArea.curTick = 0;
			newArea.id = lop;
			newArea.lastTimeBetter = 0;
			newArea.score = 0;
			newArea.world = this.world;

			int tmpx = (startWidthX * countX) + (spaceBlockEachArea * countX);
			int tmpz = (startWidthX * countZ) + (spaceBlockEachArea * countZ);

			newArea.loc = new LXRXLZRZType(tmpx, 0, tmpz, tmpx + startWidthX, 255, tmpz + startWidthZ);

			listEx.add(newArea);
		}

	}

	public int getIdOfThisLocation(Location loc) {
		for (int lop = 0; lop < listEx.size(); lop++) {
			AreaType at = listEx.get(lop);

			if (at.world.getName().equalsIgnoreCase(loc.getWorld().getName())) {
				if (loc.getBlockX() >= at.loc.lx && loc.getBlockX() <= at.loc.rx) {
					if (loc.getBlockY() >= at.loc.ly && loc.getBlockY() <= at.loc.ry) {
						if (loc.getBlockZ() >= at.loc.lz && loc.getBlockZ() <= at.loc.rz) {
							return lop;
						}
					}

				}
			}

		}

		return -1;
	}

	public void CleanAllArea() {

		CleanAllArea caa = new CleanAllArea(this, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
	}

	public void DecodeAllArea() {

		dnaList = hybrid.getPopulation();

		dprint.r.printAll("DecodeAllArea first " + dnaList.size() + " , " + hybrid.getPopulation().size());
		DecodeAllDNA caa = new DecodeAllDNA(this, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
	}

	public void nextGen() {
		hybrid.setDnaLength(dnaLength);
		hybrid.setPopulationSize(maxPopulation);
		hybrid.produceNextGen(1);

		// dnaList = hybrid.getPopulation();

	}
}
