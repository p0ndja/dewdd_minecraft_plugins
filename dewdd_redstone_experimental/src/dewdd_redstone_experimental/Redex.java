package dewdd_redstone_experimental;

import java.util.ArrayList;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import core_optimization_api.Chromosome;
import dewddtran.tr;
import ga_optimization_api.Hybrid;
import li.LXRXLZRZType;

public class Redex {
	
	
	public static int maxPopulation = 1000;
	public static long maxNothingBetterInTick = (60 * 5) * 20;

	public static String predex = "dewdd.redex.run";
	public static int spaceBlockEachArea = 5;
	
	public static long redstoneTimer = 0;

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