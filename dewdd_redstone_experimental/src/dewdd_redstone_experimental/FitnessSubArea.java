package dewdd_redstone_experimental;

import org.bukkit.Material;
import org.bukkit.block.Block;

import dewddtran.tr;

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

		dprint.r.printAll("Fitness : curid " + curId);

		AreaType at = redex.listEx.get(curId);

		double countTrue = 0;
		double countFalse = 0;



		for (int x = redex.output.loc.lx; x <= redex.output.loc.rx; x++) {

			for (int y = redex.output.loc.ly; y <= redex.output.loc.ry; y++) {

				for (int z = redex.output.loc.lz; z <= redex.output.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - redex.output.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - redex.output.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == Material.AIR) {
						continue;
					}

					if (hostBlock.getType() == Material.BED) {
						dprint.r.printAll(
								tr.locationToString(hostBlock.getLocation()) + " = " + hostBlock.getType().name() + ":"
										+ hostBlock.getData() + " to " + tr.locationToString(setBlock.getLocation())
										+ " = " + setBlock.getType().name() + ":" + setBlock.getData());

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

		double score = (countTrue / (countTrue + countFalse)) * 100;
		at.score = score;

		dprint.r.printAll("Score " + curId  + " = " + at.score);
		
	}
}