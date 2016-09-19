package dewdd_redstone_experimental;

import org.bukkit.Material;
import org.bukkit.block.Block;

import core_optimization_api.Chromosome;

public class ActivateSubArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public ActivateSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {
		Block setBlock = null;

		if (curId % 100 == 0) {
			dprint.r.printAll("Activate : curid " + curId);

		}

		AreaType at = redex.listEx.get(curId);
		setBlock = at.getBlocklxlylz().getRelative(redex.beaconX, redex.beaconY, redex.beaconZ);
		setBlock.setType(Material.REDSTONE_BLOCK);

	}
}