/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package api_skyblock;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class api_skyblock {
	class addbox implements Runnable {
		private Player	player;

		public addbox(Player player) {
			this.player = player;
		}

		@Override
		public void run() {

			Block block = player.getLocation().getBlock();
			Block block2;

			// + x + "," + y + "," + z + ")");
			block2 = block.getRelative(0, 0, 1);
			block2.setTypeId(54);
			block2 = block.getRelative(0, 0, 0);
			block2.setTypeId(54);

			// + x + "," + y + "," + z + ")");
			block2 = block.getRelative(0, 0, 0);
			Chest chest = (Chest) block2.getState();
			chest.getInventory().clear();

			ItemStack itm = new ItemStack(6, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(6, 3);
			itm.getData().setData((byte) 1);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(6, 3);
			itm.getData().setData((byte) 2);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(6, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(391, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(392, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(361, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(362, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(295, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(338, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(260, 30);
			itm.getData().setData((byte) 30);
			chest.getInventory().addItem(itm.getData().toItemStack(30));
			itm = new ItemStack(50, 30);
			chest.getInventory().addItem(itm.getData().toItemStack(30));

			itm = new ItemStack(351, 64);
			itm.getData().setData((byte) 15);
			chest.getInventory().addItem(itm.getData().toItemStack(64));

			itm = new ItemStack(8, 3);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(10, 3);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			chest.update();
			player.sendMessage(dprint.r.color("addbox completed"));
		}
	}
											class autocalcpercent0 implements Runnable {

		public autocalcpercent0() {

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 1200);
		}

		@Override
		public void run() {

			for (int i = 0; i < rsmax; i++) {

				if (rs[i].percent == 0) {
					Block mid = Bukkit.getWorld("world").getBlockAt(rs[i].x,
							rs[i].y, rs[i].z);
					checkrateis(mid);
					autocalcpercent0 aa = new autocalcpercent0();
					return;
				}
			}

		}

	}

	class checkrateis implements Runnable {

		private Block	startblock;
		private int		mode;

		public checkrateis(Block startblock, int mode) {
			this.startblock = startblock;
			this.mode = mode;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this,
					rnd.nextInt(20) + 1);
		}

		public void run() {
			long now = System.currentTimeMillis();
			int maxblock = 50;
			if (lastmessage.equalsIgnoreCase("cancel")) {
				return;
			}

			if (dewddtps.tps.getTPS() < 18) {
				lastcri = now;
				checkrateis abc = new checkrateis(startblock, mode);
				return;
			}

			lastcri = now;

			int proid = getprotectid(startblock);
			if (proid == -1) {
				dprint.r.printAll(tr.gettr("this_is_not_anyone_zone"));
				return;
			}

			csid = proid;

			Block mid = startblock.getWorld().getBlockAt(rs[proid].x,
					rs[proid].y, rs[proid].z);
			// add some block near it
			Block t;

			boolean fo = false;

			if (mode == 0) {
				if (csmax == 0) {
					dprint.r.printAll(tr.gettr("starting_checkpercent_of_is")
							+ rs[proid].p[0]);
					dprint.r.printAll(tr.gettr("counting_amo_block"));

					cs = new Block[23040000];
					csnow = 0;

					for (int z = -5; z <= 5; z++)
						for (int y = -5; y <= 5; y++)
							for (int x = -5; x <= 5; x++) {
								t = mid.getRelative(x, y, z);
								if (t.getTypeId() == 0) continue;

								// check is it have that block
								fo = false;
								for (int i = 0; i < csmax; i++) {

									if (cs[i].getLocation().getBlockX() == t
											.getLocation().getBlockX()
											&& cs[i].getLocation().getBlockY() == t
													.getLocation().getBlockY()
											&& cs[i].getLocation().getBlockZ() == t
													.getLocation().getBlockZ()) {
										fo = true;

										break;
									}
								}

								if (fo == true) continue;

								csmax++;
								cs[csmax - 1] = t;

							}

					csnow = 0;
					mode = 1;
					dprint.r.printAll(tr
							.gettr("amo_of_block_of_this_island_is_loop1")
							+ csmax);

					dprint.r.printAll(tr.gettr("starting_searching_loop2")
							+ csmax);

				} // size is 0

			} // mode 0

			if (mode == 1) {
				int csstart = csnow;

				// loop until can't add new block

				while (csnow < csmax && csmax <= 23040000) {
					lastcri = System.currentTimeMillis();

					for (int z = -1; z <= 1; z++)
						for (int y = -1; y <= 1; y++)
							for (int x = -1; x <= 1; x++) {
								t = cs[csnow].getRelative(x, y, z);

								if (t.getTypeId() == 0) continue;

								// check is it have that block
								fo = false;
								for (int i = 0; i < csmax; i++) {

									if (cs[i].getLocation().getBlockX() == t
											.getLocation().getBlockX()
											&& cs[i].getLocation().getBlockY() == t
													.getLocation().getBlockY()
											&& cs[i].getLocation().getBlockZ() == t
													.getLocation().getBlockZ()) {
										fo = true;
										break;
									}
								}

								if (fo == true) continue;

								csmax++;
								cs[csmax - 1] = t;

								/*
								 * dprint.r.printAll("added " +
								 * t.getType().name() + " = " + t.getX() + "," +
								 * t.getY() + "," + t.getZ() + " total " +
								 * csmax);
								 */

							}

					csnow++;
					if (csnow - csstart > maxblock) {
						// pause system

						if (now - lastshow > 30000) {
							dprint.r.printAll(tr.gettr("counting_rate_is")
									+ csnow + "/" + csmax + "  > "
									+ ((csnow * 100) / csmax) + "%");
							lastshow = System.currentTimeMillis();
						}
						checkrateis abc = new checkrateis(startblock, mode);
						return;
					}

				}

				csnow = 0;
				mode = 2;

				dprint.r.printAll(tr
						.gettr("amo_of_block_of_this_island_is_loop2") + csmax);

				dprint.r.printAll(tr.gettr("starting_calc_rate_of_is"));

			} // mode 1
				// after got all of block check the score

			if (mode == 2) {

				if (csnow == 0) {

					String mr[] = tr.loadfile("dewdd_skyblock", "drate.txt");
					String m[];
					String m2;

					cri = new cridata[mr.length];

					for (int k = 0; k < mr.length; k++) {
						cri[k] = new cridata();

					}
					crimax = mr.length;
					dprint.r.printAll("crimax " + crimax);

					for (int i = 0; i < mr.length; i++) {
						m2 = mr[i];

						m = m2.split("\\s+");

						cri[i].id = Integer.parseInt(m[0]);
						cri[i].data = Byte.parseByte(m[1]);
						cri[i].targetamount = Integer.parseInt(m[2]);
						cri[i].nowamount = 0;

					}

					csnow = 0;
					dprint.r.printAll(tr.gettr("loaded_drate_file"));
					dprint.r.printAll(tr.gettr("starting_count_percent"));
				} // cs 0

				// time to count the block

				int csstart = csnow;

				while (csnow < csmax) {
					lastcri = System.currentTimeMillis();

					// search
					for (int j = 0; j < crimax; j++) {
						if (cs[csnow].getTypeId() == cri[j].id) {
							if (cs[csnow].getData() == cri[j].data
									|| cri[j].data == -29) {

								cri[j].nowamount++;
								break;

							}

						}
					}

					csnow++;

					if (now - lastshow > 30000) {
						dprint.r.printAll(tr.gettr("percent_of_processing_is")
								+ (csnow * 100) / csmax + "%");

						lastshow = System.currentTimeMillis();
					}

					if (csnow - csstart >= maxblock) {
						checkrateis abc = new checkrateis(startblock, mode);
						return;
					}
				}

				dprint.r.printAll(tr
						.gettr("calc_percent_of_each_block_complete"));
				dprint.r.printAll(tr
						.gettr("starting_showing_percent_of_each_block_type"));

				double eachper = getpercent(1);
				dprint.r.printAll(tr.gettr("eachper") + eachper);

				// count percent of each block
				for (int i = 0; i < crimax; i++) {

					if (cri[i].nowamount > cri[i].targetamount)
						cri[i].nowamount = cri[i].targetamount;

					cri[i].percent = (eachper * cri[i].nowamount)
							/ (cri[i].targetamount);

				}

				// bubble sort

				dprint.r.printAll(tr.gettr("sorting_rank_is"));
				int swapc = 0;

				boolean xx = false;
				if (xx == false) {
					for (int i = 0; i < crimax; i++) {
						for (int j = 0; j < (crimax - 1 - i); j++) {

							if (cri[j].percent < cri[j + 1].percent) {
								// swap it
								cridata ttt = new cridata();

								ttt.percent = cri[j].percent;
								ttt.id = cri[j].id;
								ttt.data = cri[j].data;
								ttt.targetamount = cri[j].targetamount;
								ttt.nowamount = cri[j].nowamount;

								cri[j].percent = cri[j + 1].percent;
								cri[j].id = cri[j + 1].id;
								cri[j].data = cri[j + 1].data;
								cri[j].nowamount = cri[j + 1].nowamount;
								cri[j].targetamount = cri[j + 1].targetamount;

								cri[j + 1].percent = ttt.percent;
								cri[j + 1].id = ttt.id;
								cri[j + 1].data = ttt.data;
								cri[j + 1].nowamount = ttt.nowamount;
								cri[j + 1].targetamount = ttt.targetamount;

								swapc++;
							}

						}

					}
				}

				dprint.r.printAll(tr.gettr("sorted_rank_amount_is") + swapc);
				lastcri = System.currentTimeMillis();
				for (int i = 0; i < crimax; i++) {

					if (cri[i].nowamount >= cri[i].targetamount
							|| cri[i].nowamount == 0) continue;

					String na = i + " _ " + cri[i].id + " > "
							+ Material.getMaterial(cri[i].id).name();
					dprint.r.printAll(na + " = " + cri[i].nowamount + "/"
							+ cri[i].targetamount + " (" + cri[i].percent
							+ "%)");
				}

				double sumper = 0.0;

				for (int i = 0; i < crimax; i++) {

					sumper += cri[i].percent;
				}

				dprint.r.printAll(tr.gettr("sum_per_rate_of_island")
						+ rs[proid].p[0] + " = " + sumper + "%");

				rs[proid].percent = sumper;

				if (sumper == 0) rs[proid].p[0] = "";
				saversprotectfile();
				loadrsprotectfile();

				return;

			} // mode 2

		}
	}

	class cleanthischunktc implements Runnable {
		private Chunk	chunk	= null;
		private boolean	runnow	= false;

		public cleanthischunktc(Chunk chunk, boolean runnow) {
			this.chunk = chunk;
			this.runnow = runnow;
		}

		@Override
		public void run() {

			if (mode2 == 0) {

				int xx = 0;
				xx = randomG.nextInt(100);
				dprint.r.printC("xx = " + xx);

				if (xx >= 70) {
					cleanthischunk90(chunk);
				}
				else if (xx >= 30) {
					cleanthischunkair(chunk);
				}
				else {
					cleanthischunkonlyore(chunk, runnow);
				}
			}
			else {
				cleanthischunkonlyore(chunk, runnow);
			}

		}
	}

	class cleanthischunktcrs implements Runnable {
		private Chunk	chunk	= null;
		private boolean	runnow	= false;

		public cleanthischunktcrs(Chunk chunk, boolean runnow) {
			this.chunk = chunk;
			this.runnow = runnow;
		}

		@Override
		public void run() {
			if (isrunworld(chunk.getWorld().getName()) == true
					&& isrunworld(chunk.getWorld().getName(), true) == true) {
				World world = chunk.getWorld();

				if (dewddtps.tps.getTPS() < 18 && runnow == false) {

					cleanthischunkrs(chunk, false);
					return;
				}

				if (System.currentTimeMillis() - lastclean < 1000
						&& runnow == false) {

					cleanthischunkrs(chunk, false);

					return;
				}
				dprint.r.printC("starting clean real skyblock : "
						+ (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
						+ " at " + chunk.getWorld().getName());

				// dprint.r.printAll("cleaning Area : " + (chunk.getX() *16) +
				// ",?," +
				// (chunk.getZ() *16));

				Block block = null;
				// save near air list block

				for (int y = 255; y >= 0; y--) {

					// dprint.r.printC("cleaning... : " + (chunk.getX() *16) +
					// "," + y + ","
					// + (chunk.getZ() *16)
					// +" > " + chunkdel_max);
					// dprint.r.printA("cleaning... : " + (chunk.getX() *16) +
					// "," + y + ","
					// + (chunk.getZ() *16));

					for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

						for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 16); z++) {

							block = world.getBlockAt(x, y, z);

							if (block.getTypeId() == 0) {
								continue;
							}

							if (block.getType() == Material.MOB_SPAWNER
									|| block.getType() == Material.CHEST
									|| block.getType() == Material.TRAPPED_CHEST
									|| block.getType() == Material.HOPPER
									|| block.getType() == Material.DISPENSER
									|| block.getType() == Material.DROPPER
									|| block.getType() == Material.HOPPER
									|| block.getType() == Material.ANVIL) {

							}
							else {
								block.setTypeId(0);
							}

						} // if
					}
				}
				// add to new chunk

				dprint.r.printC("cleaned real skyblock : "
						+ (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
						+ " at " + chunk.getWorld().getName());

				lastclean = System.currentTimeMillis();

			}

		}

	}

	class cleanthischunktcrs0 implements Runnable {
		private Chunk	chunk	= null;

		public cleanthischunktcrs0(Chunk chunk) {
			this.chunk = chunk;
		}

		@Override
		public void run() {
			if (isrunworld(chunk.getWorld().getName()) == true
					&& isrunworld(chunk.getWorld().getName(), true) == true) {
				World world = chunk.getWorld();

				dprint.r.printC("starting clean real skyblock 0 : "
						+ (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
						+ " at " + chunk.getWorld().getName());

				// dprint.r.printAll("cleaning Area : " + (chunk.getX() *16) +
				// ",?," +
				// (chunk.getZ() *16));

				Block block = null;
				// save near air list block

				for (int y = 255; y >= 0; y--) {

					// dprint.r.printC("cleaning... : " + (chunk.getX() *16) +
					// "," + y + ","
					// + (chunk.getZ() *16)
					// +" > " + chunkdel_max);
					// dprint.r.printA("cleaning... : " + (chunk.getX() *16) +
					// "," + y + ","
					// + (chunk.getZ() *16));

					for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

						for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 16); z++) {

							block = world.getBlockAt(x, y, z);

							block.setTypeId(0);

						} // if
					}
				}
				// add to new chunk

				dprint.r.printC("cleaned real skyblock 0 : "
						+ (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
						+ " at " + chunk.getWorld().getName());

				lastclean = System.currentTimeMillis();

			}

		}

	}

	class createskyblock implements Runnable {
		private Player	player;

		public createskyblock(Player player) {
			this.player = player;
		}

		@Override
		public void run() { // mainc
			if (player == null) {
				return;
			}

			if (System.currentTimeMillis() - lastcreate < 10000) {
				dprint.r.printAll("ptdew&dewdd : "
						+ tr.gettr("don't_spam_this_command"));
				lastcreate = System.currentTimeMillis();
				return;
			}

			dprint.r.printAll("ptdew&dewdd : "
					+ tr.gettr("searching_freezone_for_build_skyblock"));

			lastcreate = System.currentTimeMillis();

			long st = System.currentTimeMillis();
			long et = 0;

			player.getInventory().clear();
			try {
				Economy.setMoney(player.getName(), 2);
			}
			catch (UserDoesNotExistException | NoLoanPermittedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// random

			boolean isfreearea = false;
			int x = 5000;
			int z = 5000;
			int y = 255;

			World world = player.getWorld();
			Block block = world.getBlockAt(x, y, z);
			Block block2 = null;
			int counttry = 0;

			do { // search freearea

				x = (randomG.nextInt((maxest_canbuild) * 2));
				x -= (maxest_canbuild);
				z = (randomG.nextInt((maxest_canbuild) * 2));
				z -= (maxest_canbuild);
				y = randomG.nextInt(239);

				do {
					x = (randomG.nextInt((maxest_canbuild) * 2));
					x -= maxest_canbuild;

					z = (randomG.nextInt((maxest_canbuild) * 2));
					z -= maxest_canbuild;

				}
				while (distance2d(x, z, 0, 0) <= (city_area + 300)
						|| distance2d(x, z, 0, 0) > (maxest_canbuild - 300));

				while (y < 3 || y > 239) {
					y = randomG.nextInt(239);
					// dprint.r.printC("random Y = " + y);
				}

				while (iscleanedchunk(x, z, world) == false
						|| iscleanedchunk(x, z - 17, world) == false
						|| iscleanedchunk(x, z + 17, world) == false
						|| iscleanedchunk(x - 17, z, world) == false
						|| iscleanedchunk(x - 17, z - 17, world) == false
						|| iscleanedchunk(x - 17, z + 17, world) == false
						|| iscleanedchunk(x + 17, z, world) == false
						|| iscleanedchunk(x + 17, z - 17, world) == false
						|| iscleanedchunk(x + 17, z + 17, world) == false) {

					do {

						isfreearea = true;
						x = (randomG.nextInt((maxest_canbuild) * 2));
						x -= maxest_canbuild;

						z = (randomG.nextInt((maxest_canbuild) * 2));
						z -= maxest_canbuild;

					}
					while (distance2d(x, z, 0, 0) <= (city_area + 300)
							|| distance2d(x, z, 0, 0) > (maxest_canbuild - 300));

					while (y < 3 || y > 239) {
						y = randomG.nextInt(239);

					}

				}
				dprint.r.printAll(tr.gettr("searching") + counttry + "  (" + x
						+ "," + y + "," + z + ")");

				counttry++;
				if (counttry > 10) {
					dprint.r.printAll("ptdew&dewdd: "
							+ tr.gettr("skyblock_running_long_time_tryagain"));

					return;
				}
				// gereater sky block
				// search first

				int dx = 0;
				int dy = 0;
				int dz = 0;

				world = player.getWorld();
				world.getBlockAt(x, y, z).getChunk().load(true);
				block = world.getBlockAt(x, y, z);

				block2 = null;

				isfreearea = true;

				int freea = 20;

				for (dx = -freea; dx <= freea; dx++) {
					for (dz = -freea; dz <= freea; dz++) {
						for (dy = -freea; dy <= freea; dy++) {
							block2 = block.getRelative(dx, dy, dz);
							if (block2.getTypeId() != 0) {
								isfreearea = false;
								break;
							}
						}
						if (isfreearea == false) {
							break;
						}
					}
					if (isfreearea == false) {
						break;
					}
				}

			}
			while (isfreearea == false); // search free area

			int cg = randomG.nextInt(100);

			if (cg > 66) {

				for (int dy = 3; dy >= -3; dy--) {

					for (int dx = -3; dx <= 3; dx++) {

						for (int dz = -3; dz <= 3; dz++) {

							block2 = block.getRelative(dx, dy, dz);
							block2.setTypeId(2);

						}
					}

				}

			}// random
			else if (cg > 33) {
				// pyramid
				for (int dy = 3; dy >= -3; dy--) {

					for (int dx = -6; dx <= 6; dx++) {

						for (int dz = -6; dz <= 6; dz++) {

							if (dy == 3) {
								if (dx != 0) {
									continue;
								}
								if (dz != 0) {
									continue;
								}
							}
							else if (dy == 2) {
								if (dx < -1 || dx > 1) {
									continue;
								}
								if (dz < -1 || dz > 1) {
									continue;
								}
							}
							else if (dy == 1) {
								if (dx < -2 || dx > 2) {
									continue;
								}
								if (dz < -2 || dz > 2) {
									continue;
								}
							}
							else if (dy == 0) {
								if (dx < -3 || dx > 3) {
									continue;
								}
								if (dz < -3 || dz > 3) {
									continue;
								}
							}
							else if (dy == -1) {
								if (dx < -4 || dx > 4) {
									continue;
								}
								if (dz < -4 || dz > 4) {
									continue;
								}
							}
							else if (dy == -2) {
								if (dx < -5 || dx > 5) {
									continue;
								}
								if (dz < -5 || dz > 5) {
									continue;
								}
							}
							else if (dy == -3) {
								if (dx < -6 || dx > 6) {
									continue;
								}
								if (dz < -6 || dz > 6) {
									continue;
								}
							}

							/*
							 * 3 1 2 2 1 3 0 4 0 -1 5 -1 -2 6 -2 -3 7 -3
							 */

							block2 = block.getRelative(dx, dy, dz);
							block2.setTypeId(2);

						}
					}
				}
				// pyramid
			}
			else if (cg >= 0) {
				// pyramid
				for (int dy = 3; dy >= -3; dy--) {

					for (int dx = -6; dx <= 6; dx++) {

						for (int dz = -6; dz <= 6; dz++) {

							if (dy == -3) {
								if (dx != 0) {
									continue;
								}
								if (dz != 0) {
									continue;
								}
							}
							else if (dy == -2) {
								if (dx < -1 || dx > 1) {
									continue;
								}
								if (dz < -1 || dz > 1) {
									continue;
								}
							}
							else if (dy == -1) {
								if (dx < -2 || dx > 2) {
									continue;
								}
								if (dz < -2 || dz > 2) {
									continue;
								}
							}
							else if (dy == 0) {
								if (dx < -3 || dx > 3) {
									continue;
								}
								if (dz < -3 || dz > 3) {
									continue;
								}
							}
							else if (dy == 1) {
								if (dx < -4 || dx > 4) {
									continue;
								}
								if (dz < -4 || dz > 4) {
									continue;
								}
							}
							else if (dy == 2) {
								if (dx < -5 || dx > 5) {
									continue;
								}
								if (dz < -5 || dz > 5) {
									continue;
								}
							}
							else if (dy == 3) {
								if (dx < -6 || dx > 6) {
									continue;
								}
								if (dz < -6 || dz > 6) {
									continue;
								}
							}

							/*
							 * 3 1 2 2 1 3 0 4 0 -1 5 -1 -2 6 -2 -3 7 -3
							 */

							block2 = block.getRelative(dx, dy, dz);
							block2.setTypeId(2);

						}
					}
				}
				// pyramid
			}

			// x + "," + y + "," + z + ")");

			// + x + "," + y + "," + z + ")");
			block2 = block.getRelative(-2, 4, 3);
			block2.setTypeId(54);
			block2 = block.getRelative(-1, 4, 3);
			block2.setTypeId(54);

			block2 = block.getRelative(-2, 4, 2);
			block2.setTypeId(68);
			Sign sign = (Sign) block2.getState();
			sign.setLine(0, "[dewdd]");
			sign.setLine(1, player.getName());
			sign.update();

			// + x + "," + y + "," + z + ")");
			block2 = block.getRelative(-2, 4, 3);
			Chest chest = (Chest) block2.getState();
			chest.getInventory().clear();

			ItemStack itm = new ItemStack(6, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(6, 3);
			itm.getData().setData((byte) 1);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(6, 3);
			itm.getData().setData((byte) 2);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(6, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(391, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(392, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(361, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(362, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(295, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(338, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(260, 30);
			itm.getData().setData((byte) 30);
			chest.getInventory().addItem(itm.getData().toItemStack(30));
			itm = new ItemStack(50, 30);
			chest.getInventory().addItem(itm.getData().toItemStack(30));

			itm = new ItemStack(351, 64);
			itm.getData().setData((byte) 15);
			chest.getInventory().addItem(itm.getData().toItemStack(64));

			itm = new ItemStack(79, 2);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(327, 1);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(81, 1);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(39, 1);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(40, 1);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(12, 7);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			chest.update();

			// "," + y + "," + z + ")");
			world.loadChunk(block2.getX(), block2.getZ(), true);
			block2 = block.getRelative(0, 5, 0);
			player.teleport(block2.getLocation());
			Bukkit.dispatchCommand(player, "sethome");

			dprint.r.printA("good luck ... '" + player.getName() + "'(" + x
					+ "," + y + "," + z + ")");

			dprint.r.printA("อย่าลืม /sethome นะจ๊ะ");
			Bukkit.dispatchCommand(player, "sethome");

			lastcreate = System.currentTimeMillis();
		} // main c

	}
	class createskyblockrs implements Runnable {

		private Player	player;

		public createskyblockrs(Player player) {
			this.player = player;
		}

		@Override
		public void run() {

			if (System.currentTimeMillis() - lastcreate < 10000) {
				dprint.r.printAll("ptdew&dewdd : "
						+ tr.gettr("don't_spam_this_command"));

				lastcreate = System.currentTimeMillis();
				return;
			}

			dprint.r.printAll("ptdew&dewdd : "
					+ tr.gettr("searching_freezone_for_build_skyblock"));
			lastcreate = System.currentTimeMillis();

			player.getInventory().clear();
			try {
				Economy.setMoney(player.getName(), 2);
			}
			catch (UserDoesNotExistException | NoLoanPermittedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// random x , y ,z

			boolean buildcomplete = false;

			int x = 0;
			int y = 0;
			int z = 0;

			while (buildcomplete == false) {
				// random x y z this is not near old rs list
				buildcomplete = true;

				x = randomG.nextInt(20) * 300
						* (randomG.nextInt(1) == 1 ? 1 : -1);
				z = randomG.nextInt(20) * 300
						* (randomG.nextInt(1) == 1 ? 1 : -1);
				y = randomG.nextInt(200) + 50;

				boolean checkrs = true;

				for (int lop = 0; lop < rsmax; lop++) {
					if (rs[lop].x == x && rs[lop].z == z) {
						checkrs = false;
						break;
					}
				}

				if (checkrs == false) {
					buildcomplete = false;
				}
				else { // if x y z this can do
					dprint.r.printAll("random " + x + "," + y + "," + z);

					int newid = getnewrsid(player, true);
					// get free slot for this player
					rs[newid].x = x;
					rs[newid].y = y;
					rs[newid].z = z;

					for (int lop = 0; lop < rsmaxp; lop++) {
						rs[newid].p[lop] = "null";
					}
					rs[newid].p[0] = player.getName();
					rs[newid].p[1] = flag_autocut;
					rs[newid].p[2] = flag_autoabsorb;
					// clean target chunk and build island
					Chunk chunk = null;

					int minx = player.getWorld().getBlockAt(x - 16, y, z - 16)
							.getChunk().getX() * 16;
					int minz = player.getWorld().getBlockAt(x - 16, y, z - 16)
							.getChunk().getZ() * 16;

					int maxx = player.getWorld().getBlockAt(x + 16, y, z + 16)
							.getChunk().getX() * 16;
					int maxz = player.getWorld().getBlockAt(x + 16, y, z + 16)
							.getChunk().getZ() * 16;

					// clear for skyblock
					for (int nx = -60; nx <= 60; nx++) {
						for (int nz = -60; nz <= 60; nz++) {
							for (int ny = 0; ny <= 255; ny++) {

								player.getWorld()
										.getBlockAt(x + nx, y + ny, z + nz)
										.setTypeId(0);

							}
						}
					}

					// build skyblock
					Block block = player.getWorld().getBlockAt(x, y, z);
					Block block2 = null;

					for (int dy = 3; dy >= 0; dy--) {

						for (int dx = -3; dx <= 3; dx++) {

							for (int dz = -3; dz <= 0; dz++) {

								block2 = block.getRelative(dx, dy, dz);
								block2.setTypeId(2);

							}
						}

					}

					// add item
					block2 = block.getRelative(0, 4, 0);
					block2.setTypeId(54);
					block2 = block.getRelative(0, 4, 0);
					Chest chest = (Chest) block2.getState();
					chest.getInventory().clear();

					ItemStack itm = new ItemStack(6, 3);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(6, 3);
					itm.getData().setData((byte) 1);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(6, 3);
					itm.getData().setData((byte) 2);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(6, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(391, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(392, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(361, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(362, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(295, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(338, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(260, 30);
					itm.getData().setData((byte) 30);
					chest.getInventory().addItem(itm.getData().toItemStack(30));
					itm = new ItemStack(50, 30);
					chest.getInventory().addItem(itm.getData().toItemStack(30));

					itm = new ItemStack(351, 64);
					itm.getData().setData((byte) 15);
					chest.getInventory().addItem(itm.getData().toItemStack(64));

					itm = new ItemStack(79, 2);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(327, 1);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(81, 1);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(39, 1);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(40, 1);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(12, 7);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					chest.update();

					block.getWorld().loadChunk(block2.getX(), block2.getZ(),
							true);
					block2 = block.getRelative(0, 5, 0);
					player.teleport(block2.getLocation());

					dprint.r.printA("good luck ... '" + player.getName() + "'("
							+ x + "," + y + "," + z + ")");

					dprint.r.printA("จะกลับเกาะพิมพ์ /skyblock home");

					lastcreate = System.currentTimeMillis();

					// add item done

					// save file
					saversprotectfile();
					buildcomplete = true;
				}
			} // loop build complete
		}
	}

	class cridata {
		public int		id;
		public byte		data;
		public int		targetamount;
		public int		nowamount;
		public double	percent;

	}

	public class rsdata {
		public int		x;
		public int		y;
		public int		z;
		public double	percent;
		public String	p[]			= null;
		public int		autocutc	= 0;
		public long		autocutl	= 0;

	}
	long		lastcri		= 0;			// last checkrateis don't allow to
	// check if it is running
	Random		rnd			= new Random();
	int			maxdelaycri	= 60000;
	public int	maxautocut	= 5;

	cridata			cri[];

	int				crimax		= 0;

	public String	lastmessage	= "";

	Block[]			cs			= null;

	int				csmax		= 0;

	int				csnow		= 0;

	int				csid		= 0;

	long			lastshow	= 0;

	public static String	poveride				= "dewdd.skyblock.overide";

	public static boolean cando(Block block, Player player, String mode) {
		if ((player.hasPermission(poveride)) == true) {
			return true;
		}

		if (!isrunworld(block.getWorld().getName())) {
			return true;
		}
		if (!isrunworld(block.getWorld().getName(), true)) {
			return true;
		}

		int getid = getprotectid(block);

		if (getid == -1) {
			// player.sendMessage(dprint.r.color("this is not anyone skyblock");
			return true; // can't do it
		}

		// found
		int getslot = getplayerinslot(player.getName(), getid);
		if (getplayerinslot("<everyone>", getid) > -1
				&& mode.equalsIgnoreCase("right")) {

			// player.sendMessage(dprint.r.color("this is not your skyblock , host is "
			// +
			// dew.rs[getid].p[0]);
			return true;
		}

		if (getslot == -1) {

			// player.sendMessage(dprint.r.color("this is not your skyblock , host is "
			// +
			// dew.rs[getid].p[0]);
			return false;
		}
		else {

			return true;
		}

	}

	public String			psetsky					= "dewdd.skyblock.setsky";
	public String			pskyblock				= "dewdd.skyblock.skyblock";

	public String			flag_explode			= "<!explode>";

	public String			flag_monster			= "<!monster>";
	public String			flag_pvp				= "<pvp>";
	public String			pskybox					= "dewdd.skyblock.skybox";

	public String			flag_autocut			= "<autocut>";
	public String			flag_autoabsorb			= "<autoabsorb>";
	public String			flag_everyone			= "<everyone>";
	static String			isrunworld[]			= new String[10];

	static boolean[]		isrunworldrealskyblock	= new boolean[10];

	static int				isrunworldmax			= 0;

	public static int		rsmaxp					= 20;

	public String			folder_name				= "plugins"
															+ File.separator
															+ "dewdd_skyblock";

	public static rsdata	rs[]					= new rsdata[100];

	public static int		rsmax					= 0;

	public static int getplayerinslot(String player, Block block) {

		int getid = getprotectid(block);
		if (getid == -1) {
			return -2;
		}

		for (int lop = 0; lop < rsmaxp; lop++) {
			if (rs[getid].p[lop].equalsIgnoreCase(player)) {
				return lop;
			}
		}

		return -1;
	}

	public static int getplayerinslot(String player, int getid) {
		for (int lop = 0; lop < rsmaxp; lop++) {
			if (rs[getid].p[lop].equalsIgnoreCase(player)) {
				return lop;
			}
		}

		return -1;
	}

	public static int getprotectid(Block block) {
		// must check world

		for (int lop = 0; lop < rsmax; lop++) {
			if (block.getX() >= (rs[lop].x - 150)
					&& block.getX() <= (rs[lop].x + 150)
					&& block.getZ() >= (rs[lop].z - 150)
					&& block.getZ() <= (rs[lop].z + 150)) {
				return lop;
			}
		}

		return -1;
	}

	public static boolean isrunworld(String worldname) {

		for (int lop = 0; lop < isrunworldmax; lop++) {
			if (worldname.equalsIgnoreCase(isrunworld[lop])) {
				return true;
			}

		}

		return false;
	}

	public static boolean isrunworld(String worldname, boolean isrealskyblock) {

		for (int lop = 0; lop < isrunworldmax; lop++) {
			if (worldname.equalsIgnoreCase(isrunworld[lop])) {
				return isrunworldrealskyblock[lop];
			}

		}

		return false;

	}

	public JavaPlugin		ac						= null;

	public int				fongnam					= 62;

	long					lastcreate				= 0;

	long					lastclean				= 0;

	public int				lowest_canbuild			= 150;

	public int				city_area				= 150;

	public int				maxest_canbuild			= 2000;

	public Random			randomG					= new Random();

	int						amount					= 0;							// recusive
																					// count

	int						mode2					= 1;

	public api_skyblock() {
		loadskyblockfile();
		loadrsprotectfile();

	}

	public void addbox(Player player) {
		addbox ar = new addbox(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);
	}

	public void addsign(Block blockpo) {
		boolean eedone = false;
		Block block3 = null;
		Block block2 = null;

		for (int eex = -20; eex <= 20; eex++) {
			for (int eez = -20; eez <= 20; eez++) {
				block2 = blockpo.getWorld().getBlockAt(eex, 250, eez);

				if (block2.getTypeId() != 0) {
					continue;
				}
				block3 = block2.getRelative(0, -1, 0);

				if (block3.getTypeId() != 0 && block3.getTypeId() != 20) {
					continue;
				}
				block3.setTypeId(20);

				block2.setTypeId(63);
				Sign sign = (Sign) block2.getState();
				sign.setLine(0, "[dewskyblock]");
				sign.setLine(1, "" + blockpo.getX());
				sign.setLine(2, "" + (blockpo.getY() + 10));
				sign.setLine(3, "" + blockpo.getZ());
				sign.update();
				eedone = true;
				break;
			}
			if (eedone == true) {
				break;
			}
		}
	}

	public void autocalpercent0_run() {
		autocalcpercent0 aa = new autocalcpercent0();
	}

	public void checkrateis(Block startblock) {

		/*
		 * dprint.r.printAll(tr
		 * .gettr("starting_call_calc_rate_is_functino_of_this_island"));
		 */
		long now = System.currentTimeMillis();
		if (now - lastcri < maxdelaycri) {
			/*
			 * dprint.r.printAll(tr.gettr("you_need_to_wait_for_") + ((60000 -
			 * (now - lastcri)) / 1000) + tr.gettr("second"));
			 * 
			 * dprint.r.printAll(tr.gettr("scaning_block") + csnow + "/" + csmax
			 * + " island " + rs[csid].p[0]);
			 */

			return;
		}

		lastcri = now;
		csmax = 0;
		checkrateis abc = new checkrateis(startblock, 0);
	}

	public void cleanthischunk90(Chunk chunk) {

		World world = chunk.getWorld();

		if (isrunworld(world.getName()) == true) {
			if (iscleanedchunk(chunk.getX() * 16, chunk.getZ() * 16, world) == true) {
				return;
			}

			if (dewddtps.tps.getTPS() < 18) {
				dprint.r.printC("ptdew&dewdd: cleanthischunk90 server tps below than 18 so return "
						+ dewddtps.tps.getTPS());
				cleanthischunkt(chunk, false);
				return;
			}

			// dprint.r.printAll("cleaning Area : " + (chunk.getX() *16) + ",?,"
			// +
			// (chunk.getZ() *16));

			Block block = null;

			// save near air list block

			for (int y = 254; y >= 0; y--) {

				// dprint.r.printC("cleaning... : " + (chunk.getX() *16) + "," +
				// y + ","
				// + (chunk.getZ() *16)
				// +" > " + chunkdel_max);
				// dprint.r.printA("cleaning... : " + (chunk.getX() *16) + "," +
				// y + ","
				// + (chunk.getZ() *16));

				for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

					for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 16); z++) {

						block = world.getBlockAt(x, y, z);

						if (block.getTypeId() != 0) {

							switch (block.getTypeId()) {

							case 1:
							case 7:
								// if (randomG.nextInt(10000) <= 9990) {
								block.setTypeId(0);
								// }
								break;
							case 2:
							case 3:
								if (randomG.nextInt(10000) <= 9990) {
									block.setTypeId(0);
								}
								break;
							case 8:
							case 9:
							case 10:
							case 11:
								if (randomG.nextInt(10000) <= 9990) {
									block.setTypeId(0);
								}
								break;
							} // switch

							/*
							 * case 14:case 15:case 16:case 21:case 56:case
							 * 73:case 19: break; default: block.setTypeId(0);
							 * 
							 * 
							 * }
							 */
						}
					} // if
				}
			}
			// add to new chunk

			dprint.r.printC("skyblock cleaned Area 90 : " + (chunk.getX() * 16)
					+ ",?," + (chunk.getZ() * 16));
			block = world.getBlockAt(chunk.getX() * 16, 1, chunk.getZ() * 16);
			block.setTypeId(fongnam);
		}

	}

	public void cleanthischunkair(Chunk chunk) {

		World world = chunk.getWorld();

		if (isrunworld(world.getName()) == true) {

			if (iscleanedchunk(chunk.getX() * 16, chunk.getZ() * 16, world) == true) {
				return;
			}

			if (dewddtps.tps.getTPS() < 18) {
				dprint.r.printC("ptdew&dewdd: cleanthischunkair server tps below than 18 so return "
						+ dewddtps.tps.getTPS());
				cleanthischunkt(chunk, false);
				return;
			}

			dprint.r.printC("air cleaning Area : " + (chunk.getX() * 16)
					+ ",?," + (chunk.getZ() * 16));

			Block block = null;

			// save near air list block
			int blocknearairx[] = new int[25536];
			int blocknearairy[] = new int[25536];
			int blocknearairz[] = new int[25536];

			int blocknearair_max = -1;

			for (int y = 254; y >= 0; y--) {
				// dprint.r.printC("cleaning... : " + (chunk.getX() *16) + "," +
				// y + ","
				// + (chunk.getZ() *16)
				// +" > " + chunkdel_max);
				// dprint.r.printA("cleaning... : " + (chunk.getX() *16) + "," +
				// y + ","
				// + (chunk.getZ() *16));

				for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

					for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 16); z++) {
						block = world.getBlockAt(x, y, z);
						if (block.getTypeId() == 0) {
							continue;
						}
						boolean fx = false;
						for (int cdx = -1; cdx <= 1; cdx++) {
							for (int cdz = -1; cdz <= 1; cdz++) {
								for (int cdy = -1; cdy <= 1; cdy++) {
									if (block.getRelative(cdx, cdy, cdz)
											.getTypeId() == 0) {
										fx = true;
										break;
									}
								}
							}
						}
						// search air block
						if (fx == true) {
							blocknearair_max++;
							blocknearairx[blocknearair_max] = block.getX();
							blocknearairy[blocknearair_max] = block.getY();
							blocknearairz[blocknearair_max] = block.getZ();
							/*
							 * dprint.r.printAll ("3: " +
							 * blocknearairx[blocknearair_max] + "," +
							 * blocknearairy[blocknearair_max] + "," +
							 * blocknearairz[blocknearair_max]);
							 */
						}

					}
				}
			}
			dprint.r.printC("block near air max =" + blocknearair_max);
			// save near air list block

			for (int y = 254; y >= 0; y--) {
				// dprint.r.printC("cleaning... : " + (chunk.getX() *16) + "," +
				// y + ","
				// + (chunk.getZ() *16)
				// +" > " + chunkdel_max);
				// dprint.r.printA("cleaning... : " + (chunk.getX() *16) + "," +
				// y + ","
				// + (chunk.getZ() *16));

				for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

					for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 16); z++) {

						block = world.getBlockAt(x, y, z);
						if (block.getTypeId() != 0) {

							if (isprotectblockid(block.getTypeId()) == false) {
								boolean checkair = false;
								for (int lair = 0; lair <= blocknearair_max; lair++) {
									if (block.getX() == blocknearairx[lair]
											&& block.getY() == blocknearairy[lair]
											&& block.getZ() == blocknearairz[lair]) {
										checkair = true;
										// dprint.r.printAll("fontd");
										break;
									}
								}
								if (checkair == true) {
									// block.setTypeId(20);
								}
								else {
									block.setTypeId(0);
								}
							}
							else if (block.getTypeId() == 7) {
								block.breakNaturally();
							}

						}
					}
				}
			}
			// add to new chunk

			for (@SuppressWarnings("unused")
			Object obj : blocknearairx) {
				obj = null;
			}
			blocknearairx = null;
			for (@SuppressWarnings("unused")
			Object obj : blocknearairy) {
				obj = null;
			}
			blocknearairy = null;
			for (@SuppressWarnings("unused")
			Object obj : blocknearairz) {
				obj = null;
			}
			blocknearairz = null;

			dprint.r.printC("cleaned Area air : " + (chunk.getX() * 16) + ",?,"
					+ (chunk.getZ() * 16));
			block = world.getBlockAt(chunk.getX() * 16, 1, chunk.getZ() * 16);
			block.setTypeId(fongnam);

		}

	}

	public void cleanthischunkonlyore(Chunk chunk, boolean runnow) {

		World world = chunk.getWorld();

		if (isrunworld(world.getName()) == true) {

			if (iscleanedchunk(chunk.getX() * 16, chunk.getZ() * 16, world) == true) {
				return;
			}

			if (dewddtps.tps.getTPS() < 18 && runnow == false) {
				// dprint.r.printC("ptdew&dewdd: cleanthischunkonlyore server tps below than 18 so return "
				// + dewddtps.tps.getTPS());
				cleanthischunkt(chunk, false);
				return;
			}

			if (System.currentTimeMillis() - lastclean < 500) {

				cleanthischunkt(chunk, false);

				return;
			}

			// dprint.r.printAll("cleaning Area : " + (chunk.getX() *16) + ",?,"
			// +
			// (chunk.getZ() *16));
			dprint.r.printC("starting clean only ore skyblock : "
					+ (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
					+ " at " + chunk.getWorld().getName());

			Block block = null;
			Block block2 = null;
			boolean ff = false;
			// save near air list block

			for (int y = 80; y >= 0; y--) {

				for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

					for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 16); z++) {

						block = world.getBlockAt(x, y, z);

						if (block.getTypeId() == 0
								|| block.getTypeId() == fongnam) {
							continue;
						}

						switch (block.getTypeId()) {

						case 1:
						case 2:
						case 3:
						case 8:
						case 9:
						case 7:
						case 10:
						case 11:
						case 12:
						case 13:

							ff = false;

							for (int zx = -1; zx <= 1; zx++) {
								for (int zy = -1; zy <= 1; zy++) {
									for (int zz = -1; zz <= 1; zz++) {
										block2 = block.getRelative(zx, zy, zz);
										switch (block2.getTypeId()) {
										case 27:
										case 28:
										case 66:
										case 157:
										case 50:
										case 55:
										case 69:
										case 75:
										case 76:
										case 77:
										case 93:
										case 94:
										case 132:
										case 143:
										case 147:
										case 148:
										case 149:
										case 150:
										case 151:
										case 17:
										case 5:
										case 18:
										case 83:
										case 52:

											ff = true;
											break;
										}
										if (ff == true) break;

									}
									if (ff == true) break;
								}
								if (ff == true) break;
							}

							if (ff == true) continue;
							block.setTypeId(0);
							break;

						default:

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

								for (int x1 = ((chunk.getX() * 16)); x1 <= ((chunk
										.getX() * 16) + 16); x1++) {
									for (int z1 = ((chunk.getZ() * 16)); z1 <= ((chunk
											.getZ() * 16) + 16); z1++) {
										for (int y1 = 30; y1 >= 20; y1--) {

											if (block.getWorld()
													.getBlockAt(x1, y1, z1)
													.getTypeId() == 0) {
												block.getWorld()
														.getBlockAt(x1, y1, z1)
														.setTypeId(
																block.getTypeId());

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

						} // switch

					} // if
				}
			}
			// add to new chunk

			dprint.r.printC("only ore cleaned Area : " + (chunk.getX() * 16)
					+ ",?," + (chunk.getZ() * 16) + " at "
					+ chunk.getWorld().getName());
			block = world.getBlockAt(chunk.getX() * 16, 1, chunk.getZ() * 16);
			block.setTypeId(fongnam);

			lastclean = System.currentTimeMillis();

		}

	}

	public void cleanthischunkrs(Chunk chunk, boolean runnow) {
		cleanthischunktcrs ab = new cleanthischunktcrs(chunk, runnow);
		if (runnow == false) {

			Bukkit.getServer()
					.getScheduler()
					.scheduleSyncDelayedTask(ac, ab, randomG.nextInt(600) + 100);

		}
		else {

			ab.run();

		}

	}

	public void cleanthischunkrs0(Chunk chunk) {
		cleanthischunktcrs0 ab = new cleanthischunktcrs0(chunk);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

	public void cleanthischunkt(Chunk chunk, boolean runnow) {
		// cleanthischunkt2(chunk); // call normal
		if (isrunworld(chunk.getWorld().getName()) == true
				&& isrunworld(chunk.getWorld().getName(), true) == false) {

			cleanthischunktc ab = new cleanthischunktc(chunk, runnow);

			if (runnow == false) {

				Bukkit.getServer()
						.getScheduler()
						.scheduleSyncDelayedTask(ac, ab,
								randomG.nextInt(600) + 100);

			}
			else {

				Bukkit.getServer().getScheduler()
						.scheduleSyncDelayedTask(ac, ab);

			}

		}
	}

	public void createskyblock(Player player) {
		createskyblock ab = new createskyblock(player);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

	public void createskyblockrs(Player player) {
		createskyblockrs ab = new createskyblockrs(player);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	public int getnewrsid(Player player, boolean rebuild) {
		// loop for check exits rs id and return or net build new one

		// find
		for (int lop = 0; lop < rsmax; lop++) {
			if (rs[lop].p[0].equalsIgnoreCase(player.getName())) {
				return lop;
			}
		}

		if (rebuild == false) {
			return -1;
		}
		// build new one

		rsmax++;
		rs[rsmax - 1].p = new String[rsmaxp];
		for (int gg = 0; gg < rsmaxp; gg++) {
			rs[rsmax - 1].p[gg] = new String();
			rs[rsmax - 1].p[gg] = "null";
		}

		return rsmax - 1; // rsmax -1 ; (
	}

	public double getpercent(int criid) {
		// dprint.r.printAll("crimax getpercent is " + crimax);
		return (crimax + 1) / 100;
	}

	public boolean is8_10block(int impo) {

		switch (impo) {

		case 8:
		case 9:
		case 10:
		case 11:

			return true;
		default:
			return false;
		}
	}

	// Chat Event.class
	// BlockBreakEvent

	public boolean iscleanedchunk(int x, int z, World wo) {

		int zx = 5000;
		int zz = 5000;

		boolean foundza = false;

		for (int gx = -2000; gx <= 2000; gx += 16) {
			for (int gz = -2000; gz <= 2000; gz += 16) {
				if (x >= gx && (x <= gx + 15)) {
					if (z >= gz && (z <= gz + 15)) {
						zx = gx;
						zz = gz; // save

						foundza = true;
						break;
					}
				}
			}
			if (foundza == true) {
				break;
			}
		}

		if (foundza == true) {
			Block block = wo.getBlockAt(zx, 1, zz);
			if (block.getTypeId() == fongnam) {

				return true;
			}
			else {
				// dprint.r.printAll("not 19 foundza (" + zx + "," + zz + ") = "
				// +
				// block.getTypeId()) ;
			}

		}
		else {
			// dprint.r.printAll("not found foundza (" + x + "," + z + ") ");
		}

		// chunkdel_x < x < 16

		return false;
	}

	public boolean isprotectblockid(int id) {

		switch (id) {
		case 1:
		case 2:
		case 3:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
			// case 79:
			return false;
		}

		return true;

	}

	public void loadrsprotectfile() {
		String worldf = "ptdew_dewdd_rs_protect.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			rs = new rsdata[1000];
			for (int lop = 0; lop < 1000; lop++) {
				rs[lop] = new rsdata();
			}
			rsmax = 0;

			fff.createNewFile();

			dprint.r.printAll("ptdeW&DewDD Skyblock : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				// Print the content on the console
				rsmax++;
				rs[rsmax - 1].x = Integer.parseInt(m[0]);
				rs[rsmax - 1].y = Integer.parseInt(m[1]);
				rs[rsmax - 1].z = Integer.parseInt(m[2]);
				rs[rsmax - 1].p = new String[rsmaxp];

				for (int i = 0; i < rsmaxp; i++) {
					rs[rsmax - 1].p[i] = m[i + 3];
				}

				if (m.length == 24) {
					rs[rsmax - 1].percent = Double.parseDouble(m[23]);
				}

			}

			dprint.r.printAll("ptdew&DewDD Skyblock: Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + e.getMessage());
		}
	}

	public void loadskyblockfile() {
		String worldf = "ptdew_dewdd_skyblock_worlds.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			isrunworld = new String[10];
			isrunworldrealskyblock = new boolean[10];
			isrunworldmax = 0;
			fff.createNewFile();

			dprint.r.printAll("ptdeW&DewDD Skyblock : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				// Print the content on the console
				isrunworldmax++;
				isrunworld[isrunworldmax - 1] = m[0];

				if (m.length == 1) {
					isrunworldrealskyblock[isrunworldmax - 1] = false;
				}
				else if (m.length == 2) {
					if (m[1].equalsIgnoreCase("rs")) {
						isrunworldrealskyblock[isrunworldmax - 1] = true;
					}
					else {
						isrunworldrealskyblock[isrunworldmax - 1] = false;
					}
				}

			}

			dprint.r.printAll("ptdew&DewDD Skyblock: Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + e.getMessage());
		}
	}

	public int random16() {
		int g = randomG.nextInt(360);
		g -= 180;
		g = g * 16;
		return g;

		// 180
	}

	public void saversprotectfile() {

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator
				+ "ptdew_dewdd_rs_protect.txt";
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < rsmax; y++) {
				if (rs[y].p[0].equalsIgnoreCase("")) {
					continue;
				}
				String wr = "";
				wr = rs[y].x + " " + rs[y].y + " " + rs[y].z + " ";

				for (int y2 = 0; y2 < rsmaxp; y2++) {
					wr = wr + rs[y].p[y2];

					if (y2 != rsmaxp) wr = wr + " ";

				}

				wr = wr + " " + rs[y].percent;

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			dprint.r.printC("ptdew&dewdd:saved " + filena);
			return;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}
}