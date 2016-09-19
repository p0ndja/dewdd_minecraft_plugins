package dewdd_redstone_experimental;

import org.bukkit.Bukkit;

public class ActivateAllArea implements Runnable {
	private Redex redex;

	public ActivateAllArea(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void run() {

		for (int lop = 0; lop < Redex.maxPopulation; lop++) {
			AreaType at = this.redex.listEx.get(lop);

			// recall own self
			ActivateSubArea caa = new ActivateSubArea(this.redex, lop);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					caa, 1);
		}

		this.redex.curMode = 1; // start calculating fitness
		dprint.r.printAll("Acvitate Done");
	}
}