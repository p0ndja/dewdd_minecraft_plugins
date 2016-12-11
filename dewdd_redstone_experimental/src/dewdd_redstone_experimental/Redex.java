package dewdd_redstone_experimental;

import java.util.ArrayList;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import core_optimization_api.Chromosome;
import dewddtran.tr;
import ga_optimization_api.Hybrid;
import li.LXRXLZRZType;

public class Redex {
	// Clean
	// Decode
	// Activate Beacon
	// Fitness
	// update the best
	// produce next gen


	public static int				maxPopulation				= 100;

	public static long				maxNothingBetterInTick		= (5 * 1) * 10;				// 1
																								// minutes
	public static long				maxNoRedstoneActivityInTick	= (5 * 1) * 10;
	public static String			predex						= "dewdd.redex.run";

	public static int				spaceBlockEachArea			= 0;
	public static long				redstoneTimer				= 0;

	public static int				dnaLength					= 1000;							// 1125;
	public int						curMode						= 0;							// 0
																								// is
																								// redex
																								// is
																								// setup
																								// ,
																								// 1
																								// is
																								// after
																								// activating
	public int						beaconX						= 0;

	public int						beaconY						= 0;

	// public static long keepCheckingBetterInTick = (60)

	public int						beaconZ						= 0;
	public AreaType					output						= new AreaType();

	public AreaType					start						= new AreaType();

	public AreaType					topBest						=  null;

	public World					world;

	public LinkedList<AreaType>		listEx						= new LinkedList<AreaType>();


	public Hybrid					hybrid;
	
	public boolean 					isAutoMode = false;
	
	public EventListenerX eventListenerX = null;
	
	public int evolutionCount = 0;

	public Redex(World world) {
		this.world = world;

		this.hybrid = new Hybrid();
		this.hybrid.setDnaLength(Redex.dnaLength);
		this.hybrid.setPopulationSize(Redex.maxPopulation);
		this.hybrid.prepareToRunGA();
		
		hybrid.setMutatePercent(0.2);
		
		
		eventListenerX = new EventListenerX(this);
		this.hybrid.registerEvent(eventListenerX);


		// load Start Area
		this.start = new AreaType();
		int stlx = (int) tr.gettrint("CONFIG_REDEX_START_LX");
		int stly = (int) tr.gettrint("CONFIG_REDEX_START_LY");
		int stlz = (int) tr.gettrint("CONFIG_REDEX_START_LZ");

		int strx = (int) tr.gettrint("CONFIG_REDEX_START_RX");
		int stry = (int) tr.gettrint("CONFIG_REDEX_START_RY");
		int strz = (int) tr.gettrint("CONFIG_REDEX_START_RZ");

		this.start.world = this.world;
		this.start.loc = new LXRXLZRZType(stlx, stly, stlz, strx, stry, strz);

		// load output Area
		this.output = new AreaType();
		int oplx = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_LX");
		int oply = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_LY");
		int oplz = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_LZ");

		int oprx = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_RX");
		int opry = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_RY");
		int oprz = (int) tr.gettrint("CONFIG_REDEX_OUTPUT_RZ");

		this.output.world = this.world;
		this.output.loc = new LXRXLZRZType(oplx, oply, oplz, oprx, opry, oprz);

		// ...................................................................

		// Create All Area

		this.listEx.clear();

		int startWidthX = this.start.loc.rx - this.start.loc.lx;
		int startWidthZ = this.start.loc.rz - this.start.loc.lz;

		int square = (int) Math.sqrt(Redex.maxPopulation);

		int countX = -1;
		int countZ = 0;

		for (int lop = 0; lop < Redex.maxPopulation; lop++) {

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

			int tmpx = (startWidthX * countX)
					+ (Redex.spaceBlockEachArea * countX);
			int tmpz = (startWidthX * countZ)
					+ (Redex.spaceBlockEachArea * countZ);

			newArea.loc = new LXRXLZRZType(tmpx, 0, tmpz, tmpx + startWidthX,
					255, tmpz + startWidthZ);

			this.listEx.add(newArea);
		}

	}

	public void ActivateAllArea(boolean auto) {
		ActivateAllArea caa = new ActivateAllArea(this,auto);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa,
				1);
	}

	public void CheckAllLabDone() {
		CheckAllLabDone caa = new CheckAllLabDone(this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa,
				1);
	}

	public void CleanAllArea(boolean auto) {
		this.isAutoMode = auto;
		CleanAllArea caa = new CleanAllArea(this, 0, auto);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa,
				1);
	}

	public void DecodeAllArea(boolean auto) {
		// load new dna
		
		ArrayList <Chromosome> tmp = hybrid.getPopulation();
		for (int lop = 0 ; lop < maxPopulation ; lop ++ ) {
			listEx.get(lop).chro = tmp.get(lop);
		}

		dprint.r.printAll("DecodeAllArea first " + " , "
				+ this.hybrid.getPopulation().size());
		DecodeAllDNA caa = new DecodeAllDNA(this, 0,auto);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa,
				1);
	}

	public void FitnessAllArea() {

		FitnessAllArea caa = new FitnessAllArea(this, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa,
				1);
	}

	public int getIdOfThisLocation(Location loc) {
		for (int lop = 0; lop < this.listEx.size(); lop++) {
			AreaType at = this.listEx.get(lop);

			if (at.world.getName().equalsIgnoreCase(loc.getWorld().getName())) {
				if ((loc.getBlockX() >= at.loc.lx)
						&& (loc.getBlockX() <= at.loc.rx)) {
					if ((loc.getBlockY() >= at.loc.ly)
							&& (loc.getBlockY() <= at.loc.ry)) {
						if ((loc.getBlockZ() >= at.loc.lz)
								&& (loc.getBlockZ() <= at.loc.rz)) {
							return lop;
						}
					}

				}
			}

		}

		return -1;
	}

	public void nextGen() {
		this.hybrid.setDnaLength(Redex.dnaLength);
		this.hybrid.setPopulationSize(Redex.maxPopulation);
		this.hybrid.produceNextGen(1);

		// dnaList = hybrid.getPopulation();

	}
}