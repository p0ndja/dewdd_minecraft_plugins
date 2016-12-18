package piston_ruin_the_world;

import java.util.LinkedList;

public class Simulate implements Runnable {
	
	private World world = null;
	private Location startBlock = null;
	
	
	private LinkedList <Location> nextTick = new LinkedList<Location>();
	
	public Simulate(World world , Location startBlock) {
		this.world = world;
		this.startBlock = startBlock;
	}
	
	public void startSimulate() {
		nextTick.add(startBlock);
	}
	
	
	
	
	public void run() {
		LinkedList<Location> sec = new LinkedList<Location>(); 
		// loop until all circuit stop
		while (nextTick.size() > 0) {
			sec.clear();
			
			// check all possible
			
			Location cur = nextTick.getFirst();
			nextTick.removeFirst();
			
			Block b = cur.getBlock();
			
			switch (b.getType()) {
			case RedStoneBlock:
				// check near piston
				
				
				
				
				
				break;
			
			}
			
		}
	}
	

}
