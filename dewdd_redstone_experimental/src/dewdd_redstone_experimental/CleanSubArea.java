package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CleanSubArea implements Runnable {
	private Redex redex;
	private int curId = 0;

	public CleanSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		if (curId % 100 == 0) {
			dprint.r.printAll("Cleaning : curid " + curId);
		}

		AreaType at = redex.listEx.get(curId);
		
		// Clean Or Data
		at.curTick = 0;
		at.isRunning = false;
		at.lastCheckScore = 0;
		Redex.redstoneTimer = 0;
		at.lastRedstoneActivity = Redex.redstoneTimer;
		at.lastTimeBetter = Redex.redstoneTimer;
		at.outOfRange = false;
		at.score = 0;

		boolean clean1 = false;

		for (int x = redex.start.loc.lx; x <= redex.start.loc.rx; x++) {

			for (int y = redex.start.loc.ly; y <= redex.start.loc.ry; y++) {

				for (int z = redex.start.loc.lz; z <= redex.start.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - redex.start.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - redex.start.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == Material.BEACON) {
						// remember start position
						redex.beaconX = x - redex.start.loc.lx;
						redex.beaconY = y;
						redex.beaconZ = z - redex.start.loc.lz;
						setBlock.setType(Material.AIR);

						continue;
					}

					if (hostBlock.getType() == setBlock.getType() && hostBlock.getData() == setBlock.getData()) {
						continue;
					}

					clean1 = true;
					setBlock.setTypeIdAndData(hostBlock.getType().getId(), hostBlock.getData(), true);

				}
			}
		}

		// if still has to clean try again
		if (clean1 == true) {
			CleanSubArea sub2 = new CleanSubArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, sub2);

			sub2 = new CleanSubArea(redex, curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, sub2);
		}
	}
}