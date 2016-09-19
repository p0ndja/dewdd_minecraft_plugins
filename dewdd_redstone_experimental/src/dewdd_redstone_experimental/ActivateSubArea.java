package dewdd_redstone_experimental;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class ActivateSubArea implements Runnable {
	private Redex	redex;
	private int		curId	= 0;

	public ActivateSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {
		Block setBlock = null;

		if ((this.curId % 100) == 0) {
			dprint.r.printAll("Activate : curid " + this.curId);

		}

		AreaType at = this.redex.listEx.get(this.curId);
		setBlock = at.getBlocklxlylz().getRelative(this.redex.beaconX,
				this.redex.beaconY, this.redex.beaconZ);
		setBlock.setType(Material.REDSTONE_BLOCK);

	}
}