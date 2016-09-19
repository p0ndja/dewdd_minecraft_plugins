package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class CleanAllArea implements Runnable {
	private Redex	redex;
	private int		curId	= 0;
	private boolean auto = false;

	public CleanAllArea(Redex redex, int curId,boolean auto) {
		this.redex = redex;
		this.curId = curId;
		this.auto = auto;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		// dprint.r.printAll("CleanAllArea curid " + curId);

		AreaType at = this.redex.listEx.get(this.curId);

		CleanSubArea sub = new CleanSubArea(this.redex, this.curId);
		sub.run();

		this.curId++;
		if (this.curId < this.redex.listEx.size()) {
			// recall own self
			CleanAllArea caa = new CleanAllArea(this.redex, this.curId,auto);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					caa, 1);
			return;
		}

		dprint.r.printAll("Cleaning Done");
		if (auto == true ) {
			redex.DecodeAllArea(auto);
		}
	}
}