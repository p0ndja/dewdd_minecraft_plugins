package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CleanSubArea implements Runnable {
	private Redex	redex;
	private int		curId	= 0;

	private AreaType areaType = null;
	private boolean isAreaTypeMode = false;
	
	public CleanSubArea(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}
	
	public CleanSubArea(Redex redex, AreaType areaType) {
		this.redex = redex;
		this.areaType = areaType;
		this.isAreaTypeMode = true;
	}

	@Override
	public void run() {

		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		if (this.isAreaTypeMode == false) {
		if ((this.curId % 100) == 0) {
			dprint.r.printAll("Cleaning : curid " + this.curId);
		}
		}

		AreaType at = isAreaTypeMode == false ? this.redex.listEx.get(this.curId) : areaType;
		

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

		for (int x = this.redex.start.loc.lx; x <= this.redex.start.loc.rx; x++) {

			for (int y = this.redex.start.loc.ly; y <= this.redex.start.loc.ry; y++) {

				for (int z = this.redex.start.loc.lz; z <= this.redex.start.loc.rz; z++) {
					hostBlock = at.world.getBlockAt(x, y, z);

					int gx = at.loc.lx + (x - this.redex.start.loc.lx);
					int gy = at.loc.ly + (y);
					int gz = at.loc.lz + (z - this.redex.start.loc.lz);

					setBlock = at.world.getBlockAt(gx, gy, gz);

					if (hostBlock.getType() == Material.BEACON) {
						// remember start position
						this.redex.beaconX = x - this.redex.start.loc.lx;
						this.redex.beaconY = y;
						this.redex.beaconZ = z - this.redex.start.loc.lz;
						setBlock.setType(Material.AIR);

						continue;
					}

					if ((hostBlock.getType() == setBlock.getType())
							&& (hostBlock.getData() == setBlock.getData())) {
						continue;
					}

					clean1 = true;
					setBlock.setTypeIdAndData(hostBlock.getType().getId(),
							hostBlock.getData(), true);

				}
			}
		}

		// if still has to clean try again
		if (clean1 == true) {
			CleanSubArea sub2 = new CleanSubArea(this.redex, this.curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					sub2);

			sub2 = new CleanSubArea(this.redex, this.curId);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					sub2);
		}
	}
}