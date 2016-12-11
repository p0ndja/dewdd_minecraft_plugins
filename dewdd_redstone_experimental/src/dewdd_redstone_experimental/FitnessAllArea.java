package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class FitnessAllArea implements Runnable {
	private Redex	redex;
	private int		curId	= 0;

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

		AreaType at = this.redex.listEx.get(this.curId);

		FitnessSubArea sub = new FitnessSubArea(this.redex, this.curId);
		sub.run();

		this.curId++;

		if (this.curId < Redex.maxPopulation) {
			FitnessAllArea recall = new FitnessAllArea(this.redex, this.curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					recall, 1);
		}

	}
}