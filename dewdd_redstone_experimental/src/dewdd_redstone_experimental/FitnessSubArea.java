package dewdd_redstone_experimental;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class FitnessSubArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public FitnessSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		dprint.r.printAll("Fitness curid " + curId);

		AreaType at = redex.listEx.get(curId);

		int countTrue = 0;
		int countFalse = 0;

		boolean clean1 = false;

		for (int x = redex.start.loc.lx; x <= redex.start.loc.rx; x++) {

			for (int y = redex.start.loc.ly; y <= redex.start.loc.ry; y++) {

				for (int z = redex.start.loc.lz; z <= redex.start.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - redex.start.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - redex.start.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == Material.AIR) {
						continue;
					}

					if (hostBlock.getType() == setBlock.getType() && hostBlock.getData() == setBlock.getData()) {
						countTrue++;
						continue;
					} else {
						countFalse++;
					}

					/*
					 * clean1 = true;
					 * setBlock.setTypeIdAndData(hostBlock.getType().getId(),
					 * hostBlock.getData(), true);
					 */
				}
			}
		}

		double score = (countTrue * 100) / (countTrue + countFalse);
		at.score = score;

	}
}