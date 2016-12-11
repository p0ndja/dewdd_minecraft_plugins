package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import li.LXRXLZRZType;

public class CleanSpace implements Runnable {
	private Redex			redex;
	private LXRXLZRZType	all;
	private LXRXLZRZType	cur;

	public static int		maxDo	= 1000;

	public CleanSpace(Redex redex, LXRXLZRZType all, LXRXLZRZType cur) {
		this.redex = redex;
		this.all = all;
		this.cur = cur;
	}

	@Override
	public void run() {
		int curdo = 0;

		for (int x = cur.lx; x <= all.rx; x++) {

			for (int z = cur.lz; z < all.rz; z++) {

				boolean there = false;

				for (int lop = 0; lop < redex.listEx.size(); lop++) {
					AreaType at2 = redex.listEx.get(lop);
					if (x >= at2.loc.lx && x <= at2.loc.rx) {
						if (z >= at2.loc.lz && z <= at2.loc.rz) {
							there = true;
							break;
						}
					}
				}
				
					if (there == true) {
						continue;
					}
					for (int y = cur.ly; y < all.ry; y++) {
						// clean
						Block bl = redex.world.getBlockAt(x, y, z);

						curdo++;
						
						if (bl.getType() != Material.AIR) {
							bl.setType(Material.AIR);
						

							if (curdo >= maxDo) {
								cur.lx = x;
								cur.ly = y;
								cur.lz = z;
								CleanSpace cs = new CleanSpace(redex, all, cur);
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										DigEventListener2.ac, cs, 1);
								return;
							}
						}

					
				}
			}
		}
	}
}
