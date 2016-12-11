package dewdd_redstone_experimental;

import java.util.LinkedList;

import org.bukkit.World;
import org.bukkit.block.Block;

import core_optimization_api.Chromosome;
import li.LXRXLZRZType;

public class AreaType {
	public int					id						= -1;

	public LXRXLZRZType			loc;

	public World				world;

	public long					curTick					= 0;
	public long					lastTimeBetter			= 0;
	public long					lastRedstoneActivity	= 0;
	public boolean				outOfRange				= false;

	public double				score					= 0;
	public double				lastCheckScore			= 0;

	public Chromosome			chro;

	public boolean				isRunning				= false;

	public LinkedList<Block>	processAllBlockInCurY;
	
	public int 					processCurY =  164;

	public Block getBlocklxlylz() {
		Block block = this.world.getBlockAt(this.loc.lx, this.loc.ly,
				this.loc.lz);
		return block;
	}

}