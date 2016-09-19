package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class FitnessAllArea implements Runnable {
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
		
		curId++;
		
		if (curId < Redex.maxPopulation) {
			FitnessAllArea recall = new FitnessAllArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, recall,1);
		}

	}
}