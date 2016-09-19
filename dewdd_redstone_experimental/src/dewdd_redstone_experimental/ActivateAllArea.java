package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class ActivateAllArea implements Runnable {
	private Redex redex;

	public ActivateAllArea(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void run() {

		for (int lop = 0; lop < redex.maxPopulation; lop++) {
			AreaType at = redex.listEx.get(lop);

			// recall own self
			ActivateSubArea caa = new ActivateSubArea(redex, lop);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa, 1);
		}

		dprint.r.printAll("Acvitate Done");
	}
}