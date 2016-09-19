/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddfindspawner;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class findore implements Runnable {

		private Player	p;
		private int		mx;
		private int		my;
		private int		mz;
		private int		lx;
		private int		ly;
		private int		lz;
		private int		nx;
		private int		ny;
		private int		nz;

		public findore(Player player, int minx, int miny, int minz, int maxx,
				int maxy, int maxz, int nowx, int nowy, int nowz) {

			this.p = player;
			this.mx = maxx;
			this.my = maxy;
			this.mz = maxz;
			this.lx = minx;
			this.ly = miny;
			this.lz = minz;
			this.nx = nowx;
			this.ny = nowy;
			this.nz = nowz;

		}

		public void run() {
			Block block = null;

			long st = System.currentTimeMillis();
			long et = 0;

			while (nx <= mx) {

				// findspawner.sleep(randomGenerator.nextInt(200));

				while (nz <= mz) {
					// check lowest
					// printC("x,z = " + x + ","+ z + " = " + oldper);

					// findspawner.sleep(randomGenerator.nextInt(200));

					while (ny <= my) {
						et = System.currentTimeMillis();

						if (et - st > 250 || dewddtps.tps.getTPS() < 18) {
							dprint.r.printAll("re call find ore " + nx + ","
									+ ny + "," + nz + " max " + mx + "," + my
									+ "," + mz + " low  " + lx + "," + ly + ","
									+ lz);

							findore er = new findore(p, lx, ly, lz, mx, my, mz,
									nx, ny, nz);
							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									er, (rnd.nextInt(300) + 100));

							return;
						}

						block = p.getWorld().getBlockAt(nx, ny, nz);
						if (block.getType() == Material.IRON_ORE
								|| block.getType() == Material.IRON_ORE
								|| block.getType() == Material.COAL_ORE
								|| block.getType() == Material.GOLD_ORE
								|| block.getType() == Material.DIAMOND_ORE
								|| block.getType() == Material.REDSTONE_ORE
								|| block.getType() == Material.LAPIS_ORE
								|| block.getType() == Material.EMERALD_ORE
								|| block.getType() == Material.OBSIDIAN) {

							// add to free area
							boolean added = false;

							for (int x1 = lx; x1 <= mx; x1++) {
								for (int z1 = lz; z1 <= mz; z1++) {
									for (int y1 = 254; y1 >= 80; y1--) {

										if (block.getWorld()
												.getBlockAt(x1, y1, z1)
												.getTypeId() == 0) {
											block.getWorld()
													.getBlockAt(x1, y1, z1)
													.setTypeId(
															block.getTypeId());
											dprint.r.printC("found ore "
													+ block.getType().name()
													+ " at " + nx + "," + ny
													+ "," + nz + " added to "
													+ x1 + "," + y1 + "," + z1);

											block.setTypeId(0);
											added = true;
											break;
										}

									}

									if (added == true) break;
								}
								if (added == true) break;
							}

						}
						if (block.getType() == Material.DIRT
								|| block.getType() == Material.GRASS) {

							// add to free area
							boolean added = false;

							for (int x1 = lx; x1 <= mx; x1++) {
								for (int z1 = lz; z1 <= mz; z1++) {
									for (int y1 = 79; y1 >= 70; y1--) {

										if (block.getWorld()
												.getBlockAt(x1, y1, z1)
												.getTypeId() == 0) {
											block.getWorld()
													.getBlockAt(x1, y1, z1)
													.setTypeId(
															block.getTypeId());
											dprint.r.printC("found dirt "
													+ block.getType().name()
													+ " at " + nx + "," + ny
													+ "," + nz + " added to "
													+ x1 + "," + y1 + "," + z1);

											block.setTypeId(0);
											added = true;
											break;
										}

									}

									if (added == true) break;
								}
								if (added == true) break;
							}

						}
						else if (block.getTypeId() == 1
								|| block.getTypeId() == 7
								|| block.getTypeId() == 8
								|| block.getTypeId() == 9
								|| block.getTypeId() == 10
								|| block.getTypeId() == 11) {

							block.setTypeId(0);
						}

						ny++;
					} // y
					ny = ly;

					nz++;
				} // z
				nz = lz;

				nx++;
			} // x
			nx = lx;

			dprint.r.printAll("dew find ore: done");

		}

	}

	class findspawner implements Runnable {

		private Player	p;
		private int		mx;
		private int		my;
		private int		mz;
		private int		lx;
		private int		ly;
		private int		lz;
		private int		nx;
		private int		ny;
		private int		nz;

		public findspawner(Player player, int minx, int miny, int minz,
				int maxx, int maxy, int maxz, int nowx, int nowy, int nowz) {

			this.p = player;
			this.mx = maxx;
			this.my = maxy;
			this.mz = maxz;
			this.lx = minx;
			this.ly = miny;
			this.lz = minz;
			this.nx = nowx;
			this.ny = nowy;
			this.nz = nowz;

		}

		public void run() {
			Block block = null;
			Block block2 = null;

			long st = System.currentTimeMillis();
			long et = 0;

			while (nx <= mx) {

				// findspawner.sleep(randomGenerator.nextInt(200));

				while (nz <= mz) {
					// check lowest
					// printC("x,z = " + x + ","+ z + " = " + oldper);

					// findspawner.sleep(randomGenerator.nextInt(200));

					while (ny <= my) {
						et = System.currentTimeMillis();

						if (et - st > 250 || dewddtps.tps.getTPS() < 18) {
							dprint.r.printAll("re call find spawner " + nx
									+ "," + ny + "," + nz + " max " + mx + ","
									+ my + "," + mz + " low  " + lx + "," + ly
									+ "," + lz);

							findspawner er = new findspawner(p, lx, ly, lz, mx,
									my, mz, nx, ny, nz);
							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									er, (rnd.nextInt(300) + 100));

							return;
						}

						block = p.getWorld().getBlockAt(nx, ny, nz);
						if (block.getTypeId() == 52) {
							dprint.r.printAll("dewddfindspawner: เจอกรงมอนสเตอร์ที่ ตำแหน่ง  ("
									+ nx + "," + ny + "," + nz);

							// make glass
							for (int yg = block.getY(); yg <= 253; yg++) {
								block2 = p.getWorld().getBlockAt(nx, yg, nz);
								if (block2.getTypeId() == 0) {
									block2.setTypeId(20);
								}
							}

							dprint.r.printAll("dewddfindspawner: สร้างเสากระจกเสร็จแล้ว  ("
									+ nx + "," + ny + "," + nz);

						}

						ny++;
					} // y
					ny = ly;

					nz++;
				} // z
				nz = lz;

				nx++;
			} // x
			nx = lx;

			dprint.r.printAll("dewfindspawer: down");

		}

	}

	public JavaPlugin	ac	= null;

	public Random		rnd	= new Random();

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("/dewddfindspawner") == true) {

			int d4 = 50;
			for (Player pr : Bukkit.getOnlinePlayers()) {
				findspawner er = new findspawner(pr, pr.getLocation()
						.getBlockX() - d4, 0,
						pr.getLocation().getBlockZ() - d4,

						pr.getLocation().getBlockX() + d4, 80, pr.getLocation()
								.getBlockZ() + d4,

						pr.getLocation().getBlockX() - d4, 0, pr.getLocation()
								.getBlockZ() - d4);

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, er, 200L);
				// runmode = true;
				dprint.r.printAll("dewddfindspawner:Starting running...");
				dprint.r.printAll("dewddfindspawner: เริ่มการค้นหากรงมอนสเตอร์...");

				dprint.r.printAll("dewddfindspawner : เริ่มการทำงานแบบขนาน");

				return;

			}

			dprint.r.printAll("dewddfindspawner : not found spawner");

		}
		if (event.getMessage().equalsIgnoreCase("/dewddfindore") == true) {

			int d4 = 50;
			for (Player pr : Bukkit.getOnlinePlayers()) {
				findore er = new findore(pr, pr.getLocation().getBlockX() - d4,
						0, pr.getLocation().getBlockZ() - d4,

						pr.getLocation().getBlockX() + d4, 80, pr.getLocation()
								.getBlockZ() + d4,

						pr.getLocation().getBlockX() - d4, 0, pr.getLocation()
								.getBlockZ() - d4);

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, er, 200L);
				// runmode = true;
				dprint.r.printAll("dewdd ore :Starting running...");
				dprint.r.printAll("dewdd ore...");

				dprint.r.printAll("dewdd ore : เริ่มการทำงานแบบขนาน");

				return;

			}

		}

	}

} // class

