package dewdd_redstone_experimental;

import org.bukkit.Material;
import org.bukkit.block.Block;

import dewddtran.tr;

public class FitnessSubArea implements Runnable {
	private Redex	redex;
	private int		curId	= 0;

	public FitnessSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		dprint.r.printAll("Fitness : curid " + this.curId);

		AreaType at = this.redex.listEx.get(this.curId);

		double countTrue = 0;
		double countFalse = 0;

		for (int x = this.redex.output.loc.lx; x <= this.redex.output.loc.rx; x++) {

			for (int y = this.redex.output.loc.ly; y <= this.redex.output.loc.ry; y++) {

				for (int z = this.redex.output.loc.lz; z <= this.redex.output.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - this.redex.output.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - this.redex.output.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == Material.AIR) {
						continue;
					}

					if (hostBlock.getType() == Material.BED) {
						dprint.r.printAll(
								tr.locationToString(hostBlock.getLocation())
										+ " = " + hostBlock.getType().name()
										+ ":" + hostBlock.getData() + " to "
										+ tr.locationToString(
												setBlock.getLocation())
										+ " = " + setBlock.getType().name()
										+ ":" + setBlock.getData());

					}

					if ((hostBlock.getType() == setBlock.getType())
							&& (hostBlock.getData() == setBlock.getData())) {
						countTrue++;
						continue;
					}
					else {
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

		double score = (countTrue / (countTrue + countFalse)) * 100;
		at.score = score;

		dprint.r.printAll("Score " + this.curId + " = " + at.score);

	}
}