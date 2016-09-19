package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class DecodeAllDNA implements Runnable {
	private Redex	redex;
	private int		curId	= 0;

	public DecodeAllDNA(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		// dprint.r.printAll("Decoding curid " + curId);

		AreaType at = this.redex.listEx.get(this.curId);

		DecodeSubDNA sub = new DecodeSubDNA(this.redex, this.curId);
		sub.run();

		this.curId++;
		if (this.curId < this.redex.listEx.size()) {
			// recall own self
			DecodeAllDNA caa = new DecodeAllDNA(this.redex, this.curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					caa, 1);
			return;
		}

		dprint.r.printAll("Decoding Done");
		
		

	}
}