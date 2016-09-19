/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_redstone_experimental;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.defaults.ClearCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;
import li.LXRXLZRZType;

class Redex {
	public  AreaType output = new AreaType();
	public  AreaType start = new AreaType();

	public static int maxPopulation = 1000;
	public static long maxNothingBetterInTick = (60 * 5) * 20;

	// public static long keepCheckingBetterInTick = (60)

	public static String predex = "dewdd.redex.run";
	public static int spaceBlockEachArea = 5 ;

	public World world;
	
	public LinkedList <AreaType> listEx = new LinkedList<AreaType>();
	
	public Player player;
	

	
	public Redex(World world , Player player) {
		this.world = world;
		this.player = player;
		
		
		// load Start Area
		start = new AreaType();
		int stlx = (int)tr.gettrint("CONFIG_REDEX_START_LX");
		int stly = (int)tr.gettrint("CONFIG_REDEX_START_LY");
		int stlz = (int)tr.gettrint("CONFIG_REDEX_START_LZ");
		
		int strx = (int)tr.gettrint("CONFIG_REDEX_START_RX");
		int stry = (int)tr.gettrint("CONFIG_REDEX_START_RY");
		int strz = (int)tr.gettrint("CONFIG_REDEX_START_RZ");
		
		start.world = this.world;
		start.loc = new LXRXLZRZType(stlx, stly, stlz, strx, stry, strz);
		
		// load output Area
		output = new AreaType();
		int oplx = (int)tr.gettrint("CONFIG_REDEX_OUTPUT_LX");
		int oply = (int)tr.gettrint("CONFIG_REDEX_OUTPUT_LY");
		int oplz = (int)tr.gettrint("CONFIG_REDEX_OUTPUT_LZ");
		
		int oprx = (int)tr.gettrint("CONFIG_REDEX_OUTPUT_RX");
		int opry = (int)tr.gettrint("CONFIG_REDEX_OUTPUT_RY");
		int oprz = (int)tr.gettrint("CONFIG_REDEX_OUTPUT_RZ");
		
		output.world = this.world;
		output.loc = new LXRXLZRZType(oplx, oply, oplz, oprx, opry, oprz);
		
		//...................................................................
		
		// Create All Area
		
		listEx.clear();
		
		int startWidthX = start.loc.rx - start.loc.lx;
		int startWidthZ = start.loc.rz - start.loc.lz;
		
		
		
		int square = (int) Math.sqrt(maxPopulation);
		
		int countX = -1;
		int countZ = 0;
		
		for (int lop = 0 ; lop < maxPopulation ; lop ++) {
			
			countX ++;
			if (countX > square) {
				
				countX = 0;
				countZ ++;
			}
			
			AreaType newArea = new AreaType();
			newArea.curTick = 0;
			newArea.id = lop;
			newArea.lastTimeBetter= 0;
			newArea.score = 0;
			newArea.world = this.world;
			
			int tmpx = (startWidthX * countX) + (spaceBlockEachArea * countX) ;
			int tmpz = (startWidthX * countZ) + (spaceBlockEachArea * countZ) ;
			
			
			newArea.loc = new LXRXLZRZType(tmpx, 0, tmpz, tmpx + startWidthX, 255,tmpz + startWidthZ);
			
			listEx.add(newArea);
		}
		
		
	}
	
	public void CleanAllArea() {
		
		
		CleanAllArea caa = new CleanAllArea(this	, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
	}
}

class AreaType {
	public int id = -1;

	public LXRXLZRZType loc;
	
	public World world;

	public long curTick = 0;
	public long lastTimeBetter = 0;

	public long score = 0;

}

class CleanAllArea implements Runnable {
	private Redex redex;
	private int curId = 0 ;

	public CleanAllArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;
		
		dprint.r.printAll("curid " + curId);
		
		AreaType at = redex.listEx.get(curId);

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
					
					setBlock.setTypeIdAndData(hostBlock.getType().getId(), hostBlock.getData(), true);
					

				}
			}
		}
		
		curId ++;
		if (curId < redex.listEx.size()) {
			// recall own self
			CleanAllArea caa = new CleanAllArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
		}

	}
}

class CommandRuning implements Runnable {
	private String m[];
	private Player p;

	public CommandRuning(String m[], Player p) {
		this.m = m;
		this.p = p;

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

				} else if (m.length == 2) {
					if (m[1].equalsIgnoreCase("start")) {

						// start process
						Redex redex = new Redex(p.getWorld(), p);
						redex.CleanAllArea();
								

					}
				}
			}
		}

	}

}

public class DigEventListener2 implements Listener {

	public static JavaPlugin ac = null;

	Random rnd = new Random();

	public boolean isrunworld(String worldName) {
		return tr.isrunworld(ac.getName(), worldName);
	}

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

		CommandRuning newThread = new CommandRuning(m, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, newThread);

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
} // class
