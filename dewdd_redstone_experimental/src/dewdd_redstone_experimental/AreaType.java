package dewdd_redstone_experimental;

import org.bukkit.World;
import org.bukkit.block.Block;

import core_optimization_api.Chromosome;
import li.LXRXLZRZType;

public class AreaType {
	public int id = -1;

	public LXRXLZRZType loc;

	public World world;

	public long curTick = 0;
	public long lastTimeBetter = 0;
	public long lastTimeCircuit = 0;

	public double score = 0;

	public Chromosome chro;

	public boolean isRunning = false;

	public Block getBlocklxlylz() {
		Block block = world.getBlockAt(loc.lx, loc.ly, loc.lz);
		return block;
	}

}