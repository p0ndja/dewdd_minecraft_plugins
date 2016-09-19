/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddflower;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import api_skyblock.api_skyblock;
import dewddtran.tr;
import li.IDDataType;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class dewset extends dewset_interface {

	public boolean saveHistory(CoreProtectAPI CoreProtect, Player player, Block hostBlock, Block setBlock,
			boolean saveHistoryOnPlacing) {
		if (CoreProtect != null) { // Ensure we have
			// access
			// to the API
			boolean success = false;
			// mean set 0
			if (hostBlock.getType() == Material.AIR && setBlock.getType() != Material.AIR) {
				success = CoreProtect.logPlacement(player.getName(), setBlock.getLocation(), setBlock.getType(),
						setBlock.getData());
				setBlock.setTypeIdAndData(hostBlock.getTypeId(), hostBlock.getData(), false);
				return success;
			} else {
				setBlock.setTypeIdAndData(hostBlock.getTypeId(), hostBlock.getData(), false);
				if (saveHistoryOnPlacing == true) {

					success = CoreProtect.logPlacement(player.getName(), setBlock.getLocation(), setBlock.getType(),
							setBlock.getData());

				}
				return success;
			}

		} else {
			setBlock.setTypeIdAndData(hostBlock.getTypeId(), hostBlock.getData(), false);

		}
		return false;
	}

	public boolean decreseitem1(Player player, int itemid, int itemdata, boolean forcetruedata) {
		ItemStack itm = null;
		int lenl = 0;

		if (itemid == 0)
			return false;

		for (lenl = 0; lenl < player.getInventory().getContents().length; lenl++) {
			if (player.getInventory().getItem(lenl) == null) {
				continue;
			}

			itm = player.getInventory().getItem(lenl);

			if (itemid == 8 || itemid == 9 || itemid == 326)
				return true;
			else if (itemid == 44 || itemid == 43) {
				if (itm.getTypeId() != 44 && itm.getTypeId() != 43) {
					continue;
				}
			}

			// piston
			else if (itemid == 33 || itemid == 34 || itemid == 29) {
				if (itm.getTypeId() != 33 && itm.getTypeId() != 34 && itm.getTypeId() != 29) {
					continue;
				}
			}

			// dirt
			else if (itemid == 2 || itemid == 3 || itemid == 60) {
				if (itm.getTypeId() != 3 && itm.getTypeId() != 2 && itm.getTypeId() != 60) {
					continue;
				}
			}
			// repeater
			else if (itemid == 356 || itemid == 93 || itemid == 94) {
				if (itm.getTypeId() != 356 && itm.getTypeId() != 93 && itm.getTypeId() != 94) {
					continue;
				}
			}
			// redstone torch
			else if (itemid == 75 || itemid == 76) {
				if (itm.getTypeId() != 75 && itm.getTypeId() != 76) {
					continue;
				}
			}
			// redstone wire
			else if (itemid == 331 || itemid == 55) {
				if (itm.getTypeId() != 331 && itm.getTypeId() != 55) {
					continue;
				}
			}
			// pumpkin
			else if (itemid == 361 || itemid == 104) {
				if (itm.getTypeId() != 361 && itm.getTypeId() != 104) {
					continue;
				}
			}
			// melon
			else if (itemid == 362 || itemid == 105) {
				if (itm.getTypeId() != 362 && itm.getTypeId() != 105) {
					continue;
				}
			}

			// sugar
			else if (itemid == 338 || itemid == 83) {
				if (itm.getTypeId() != 338 && itm.getTypeId() != 83) {
					continue;
				}
			}

			// wheat
			else if (itemid == 295 || itemid == 59) {
				if (itm.getTypeId() != 295 && itm.getTypeId() != 59) {
					continue;
				}
			}
			// carrot
			else if (itemid == 391 || itemid == 141) {
				if (itm.getTypeId() != 391 && itm.getTypeId() != 141) {
					continue;
				}
			}
			// potato
			else if (itemid == 392 || itemid == 142) {
				if (itm.getTypeId() != 392 && itm.getTypeId() != 142) {
					continue;
				}
			} else if (itm.getTypeId() != itemid) {
				continue;
			}

			if (forcetruedata == true)
				if (itm.getData().getData() != itemdata) {
					continue;
				}

			if (itm.getAmount() != 1) {
				itm.setAmount(itm.getAmount() - 1);
				return true;
			} else {
				ItemStack emy = player.getItemInHand();
				emy.setTypeId(0);

				player.getInventory().setItem(lenl, emy);

				return true;
			}

		}
		return false;
	}

	// cutwoodsub

	class dewStack_mom implements Runnable {
		public int amount;
		public Player player = null;

		@Override
		public void run() {
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr("please_set_block_1")));

				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr("please_set_block_2")));

				return;
			}

			// find position

			if (amount == 0) {
				if (player.getItemInHand() == null) {
					player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("need_item_in_hand_=_amount")));

					return;
				}
				amount = player.getItemInHand().getAmount();

			}

			player.sendMessage(dprint.r.color("Stack amount = " + amount));

			Block block = player.getLocation().getBlock();

			if (selectblock[getid] == null) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr("Stack_diamond_sword_null")));
			}

			// find diamond
			Block blockd = null;
			int blockdx = 0;
			int blockdy = 0;
			int blockdz = 0;

			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -2; dy <= 0; dy++) {
					for (int dz = -1; dz <= 1; dz++) {
						blockd = block.getRelative(dx, dy, dz);
						if (blockd.getTypeId() == 57
								|| blockd.getLocation().getBlockX() == selectblock[getid].getLocation().getBlockX()
										&& blockd.getLocation().getBlockY() == selectblock[getid].getLocation()
												.getBlockY()
								&& blockd.getLocation().getBlockZ() == selectblock[getid].getLocation().getBlockZ()) { // diamond
																														// block
							blockdx = dx;
							blockdy = dy + 1;
							blockdz = dz;
							break;
						}
					}
				}
			}

			if (blockdx == 0 && blockdy == 0 && blockdz == 0) {
				player.sendMessage(dprint.r.color(tr.gettr("Stack_diamondsword_null_mean_upper")));

				blockdy = 1;
			}

			player.sendMessage(
					dprint.r.color(tr.gettr("Stack_diamond_axis_=") + blockdx + "," + blockdy + "," + blockdz));
			// after know axis and selected block ... so start copy
			// " + tr.gettr("for") + "amount
			// " + tr.gettr("for") + "all block ... to copy to next axis
			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " Stack "
					+ player.getItemInHand().getTypeId() + ":" + player.getItemInHand().getData());

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " Stack "
					+ player.getItemInHand().getTypeId() + ":" + player.getItemInHand().getData());
			// run
			int mx = 0;
			int lx = 0;

			int my = 0;
			int ly = 0;

			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			Stack_thread aer = new Stack_thread();
			aer.player = player;

			aer.blockdx = blockdx;
			aer.blockdy = blockdy;
			aer.blockdz = blockdz;

			aer.amount1 = 1;

			aer.lx = lx;
			aer.ly = ly;
			aer.lz = lz;
			aer.mx = mx;
			aer.my = my;
			aer.mz = mz;
			aer.xlx = lx;
			aer.ylx = ly;
			aer.zlx = lz;
			aer.playerLocation = player.getLocation();

			aer.amountloop = 1;
			aer.amount = amount;

			aer.getid = getid;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

		}

	}

	class Stack_thread implements Runnable {
		public int amount;
		public int amount1;

		public int amountloop;
		public int blockdx;
		public int blockdy;
		public int blockdz;

		public int getid;

		public int lx;

		public int ly;
		public int lz;
		public int mx;
		public int my;
		public int mz;
		public Player player = null;
		public Location playerLocation = null;
		public int xlx;
		public int ylx;
		public int zlx;

		@Override
		public void run() {

			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewset"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewset"));

				return;
			}

			CoreProtectAPI CoreProtect = getCoreProtect();

			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			Block setBlock = null;
			// amountloop = start with 1
			Block hostBlock = null;

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (amountloop <= amount) {
				// dprint.r.printAll("amountloop = " + amountloop + " / " +
				// amount);

				// player.getWorld().save();
				int ndx = 0; // now x y z
				if (blockdx != 0) {
					ndx = Math.abs(selectx1[getid] - selectx2[getid]) + 1;
					ndx = ndx * blockdx;
					ndx = ndx * amountloop;
				}

				int ndy = 0; // now x y z
				if (blockdy != 0) {
					ndy = Math.abs(selecty1[getid] - selecty2[getid]) + 1;
					ndy = ndy * blockdy;
					ndy = ndy * amountloop;
				}

				int ndz = 0; // now x y z
				if (blockdz != 0) {
					ndz = Math.abs(selectz1[getid] - selectz2[getid]) + 1;
					ndz = ndz * blockdz;
					ndz = ndz * amountloop;
				}

				// player.sendMessage(dprint.r.color("blockdx = " + ndx));
				// player.sendMessage(dprint.r.color("blockdy = " + ndy));
				// player.sendMessage(dprint.r.color("blockdz = " + ndz));

				// dprint.r.printC("blockdx = " + ndx);
				// dprint.r.printC("blockdy = " + ndy);
				// dprint.r.printC("blockdz = " + ndz);

				while (amount1 <= 4) { // amount1 // amount1 = start with 1
					// dprint.r.printAll("amount1 = " + amount1);

					while (xlx <= mx) {

						while (ylx <= my) {
							while (zlx <= mz) {

								// dprint.r.printAll (xlx + "," + ylx + "," +
								// zlx + " mx " + mx + "," + my + "," + mz);

								endtime = System.currentTimeMillis();
								if (endtime - starttime > runtime) {

									Stack_thread xgn2 = new Stack_thread();

									xgn2.amount = amount;
									xgn2.blockdx = blockdx;
									xgn2.blockdy = blockdy;
									xgn2.blockdz = blockdz;

									xgn2.amount1 = amount1;

									xgn2.amountloop = amountloop;

									xgn2.lx = lx;
									xgn2.ly = ly;
									xgn2.lz = lz;

									xgn2.getid = getid;
									xgn2.player = player;

									xgn2.mx = mx;
									xgn2.my = my;
									xgn2.mz = mz;

									xgn2.xlx = xlx;
									xgn2.ylx = ylx;
									xgn2.zlx = zlx;
									xgn2.playerLocation = playerLocation;

									dprint.r.printC(player.getName() + " Stack  " + tr.gettr("recall") + " " + xlx
											+ " , " + ylx + " , " + zlx);
									dprint.r.printC("low " + lx + " , " + ly + " , " + lz + " high " + mx + "," + my
											+ "," + mz);

									Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

									return;
								}

								hostBlock = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

								if (hostBlock.getY() + ndy > 253 || hostBlock.getY() + ndy < 1) {
									zlx++;
									// dprint.r.printAll("out of range y");
									continue;
								}

								setBlock = hostBlock.getWorld().getBlockAt(hostBlock.getX() + ndx,
										hostBlock.getY() + ndy, hostBlock.getZ() + ndz);
								/*
								 * if (blockd.getTypeId() == 0) { continue; }
								 */

								if (amount1 == 1 || amount1 == 3) { // if first
																	// round ...
																	// only
									// block
									if (hostBlock.getType().isBlock() == false) {
										zlx++;
										// dprint.r.printAll("first is not a
										// block");
										continue;
									}
									// blockd.setTypeId(0);

									if (arxx)
										if (setBlock.getTypeId() != hostBlock.getTypeId()
												|| setBlock.getData() != hostBlock.getData())
											if (decreseitem1(player, hostBlock.getTypeId(), hostBlock.getData(),
													false) == false && hostBlock.getTypeId() != 0) {
												player.sendMessage(dprint.r
														.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
												player.sendMessage(dprint.r.color("block > " + hostBlock.getTypeId()
														+ "," + hostBlock.getData()));
												return;
											}
								} else { // if secord round ... only not block
											// block
									if (hostBlock.getType().isBlock() == true) {
										zlx++;
										// dprint.r.printAll("second time is a
										// block");
										continue;
									}
									// blockd.setTypeId(0);

									if (arxx)
										if (setBlock.getTypeId() != hostBlock.getTypeId()
												|| setBlock.getData() != hostBlock.getData())
											if (decreseitem1(player, hostBlock.getTypeId(), hostBlock.getData(),
													false) == false && hostBlock.getTypeId() != 0) {
												player.sendMessage(dprint.r
														.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
												player.sendMessage(dprint.r.color("block > " + hostBlock.getTypeId()
														+ "," + hostBlock.getData()));
												return;
											}
								}

								setBlock = hostBlock.getWorld().getBlockAt(hostBlock.getX() + ndx,
										hostBlock.getY() + ndy, hostBlock.getZ() + ndz);
								if (cando_all(setBlock, player, "dewset") == false)
									return;

								if (setBlock.getType() != Material.AIR && hostBlock.getType() == Material.AIR) {
									if (!perDelete) {
										continue;
									}
								}

								saveHistory(CoreProtect, player, hostBlock, setBlock, false);

								// dprint.r.printAll ("comple " + xlx + "," +
								// ylx + "," + zlx + " mx " + mx + "," + my +
								// "," + mz);

								if (amount1 == 3 && player.hasPermission(pmainalsocopyinventoryblockwhenyouusedewset)) {

									switch (hostBlock.getType()) {
									case CHEST:
									case TRAPPED_CHEST:

										Chest hostChest = (Chest) hostBlock.getState();
										Chest setChest = (Chest) setBlock.getState();

										for (ItemStack itm : hostChest.getInventory().getContents()) {
											if (itm == null) {
												continue;
											}

											setChest.getInventory().addItem(itm);
											continue;

										}

										setChest.update(true);
										saveHistory(CoreProtect, player, hostBlock, setBlock, true);
										break;
									case DISPENSER:

										Dispenser hostDispenser = (Dispenser) hostBlock.getState();
										Dispenser setDispenser = (Dispenser) setBlock.getState();

										for (ItemStack itm : hostDispenser.getInventory().getContents()) {
											if (itm == null) {
												continue;
											}

											setDispenser.getInventory().addItem(itm);
											continue;

										}

										setDispenser.update(true);
										saveHistory(CoreProtect, player, hostBlock, setBlock, true);
										break;

									case HOPPER:
										Hopper hostHopper = (Hopper) hostBlock.getState();
										Hopper setHopper = (Hopper) setBlock.getState();

										for (ItemStack itm : hostHopper.getInventory().getContents()) {
											if (itm == null) {
												continue;
											}

											setHopper.getInventory().addItem(itm);
											continue;

										}

										setHopper.update(true);
										saveHistory(CoreProtect, player, hostBlock, setBlock, true);
										break;

									case DROPPER:

										Dropper hostDropper = (Dropper) hostBlock.getState();
										Dropper setDropper = (Dropper) setBlock.getState();

										for (ItemStack itm : hostDropper.getInventory().getContents()) {
											if (itm == null) {
												continue;
											}

											setDropper.getInventory().addItem(itm);
											continue;

										}

										setDropper.update(true);
										saveHistory(CoreProtect, player, hostBlock, setBlock, true);
										break;
									}

								}

								zlx++;
							} // z
							zlx = lz;

							ylx++;
						} // y
						ylx = ly;

						xlx++;
					} // x
					xlx = lx;

					amount1++;
				} // amount 1
				amount1 = 1;

				amountloop++;
			} // amount loop

			player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr(tr.gettr("done"))));
			dprint.r.printC("ptdew&dewdd : " + player.getName() + " > Stack " + tr.gettr(tr.gettr("done")));
		}
	}

	class dewcopy_c_mom implements Runnable {
		private Player player;

		public dewcopy_c_mom(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			boolean arxx = !player.hasPermission(pmaininfinite);

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewcopy " + tr.gettr("please_set_block_1")));

				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewcopy " + tr.gettr("please_set_block_2")));

				return;

			}

			boolean xc = false;
			boolean yc = false;
			boolean zc = false;

			// x
			if (selectx1[getid] <= selectx2[getid]) {
				if (player.getLocation().getBlockX() >= selectx1[getid]
						&& player.getLocation().getBlockX() <= selectx2[getid]) {
					xc = true;
				}
			} else if (player.getLocation().getBlockX() <= selectx1[getid]
					&& player.getLocation().getBlockX() >= selectx2[getid]) {
				xc = true;
			}

			// y
			if (selecty1[getid] <= selecty2[getid]) {
				if (player.getLocation().getBlockY() >= selecty1[getid]
						&& player.getLocation().getBlockY() <= selecty2[getid]) {
					yc = true;
				}
			} else if (player.getLocation().getBlockY() <= selecty1[getid]
					&& player.getLocation().getBlockY() >= selecty2[getid]) {
				yc = true;
			}

			// z
			if (selectz1[getid] <= selectz2[getid]) {
				if (player.getLocation().getBlockY() >= selectz1[getid]
						&& player.getLocation().getBlockY() <= selectz2[getid]) {
					zc = true;
				}
			} else if (player.getLocation().getBlockY() <= selectz1[getid]
					&& player.getLocation().getBlockY() >= selectz2[getid]) {
				zc = true;
			}

			if ((xc == true && yc == true && zc == true) == true) {
				player.sendMessage(
						dprint.r.color("ptdew&dewdd : " + tr.gettr("dewcopy_can't_run_cuz_you_stand_on_source_place")));
				return;
			}

			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewcopy "
					+ player.getItemInHand().getTypeId() + ":" + player.getItemInHand().getData());
			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewcopy "
					+ player.getItemInHand().getTypeId() + ":" + player.getItemInHand().getData());
			// grab mxlx
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewcopy_thread aer = new dewcopy_thread(player, mx, my, mz, lx, ly, lz, lx, ly, lz, 1, selectx1[getid],
					selecty1[getid], selectz1[getid], selectworldname[getid], player.getLocation().clone());

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

			// grab mxlx

		}
	}

	class dewcopy_thread implements Runnable {

		private int lx = 0;
		private int ly = 0;
		private int lz = 0;
		private int mx = 0;

		private int my = 0;
		private int mz = 0;
		private Player player = null;

		private int xlx = 0;
		private int ylx = 0;
		private int zlx = 0;
		private int amountloop = 1;
		private int selectx1 = 0;
		private int selecty1 = 0;
		private int selectz1 = 0;
		private String selectworldname = "";
		private Location playerLocation;

		public dewcopy_thread(Player player, int mx, int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx,
				int amountloop, int selectx1, int selecty1, int selectz1, String selectworldname,
				Location playerLocation) {
			this.player = player;
			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;
			this.amountloop = amountloop;
			this.selectx1 = selectx1;
			this.selecty1 = selecty1;

			this.selectz1 = selectz1;

			this.selectworldname = selectworldname;
			this.playerLocation = playerLocation;

			/*
			 * player.sendMessage("l " + lx + "," + ly + "," + lz + " ... m " +
			 * mx + "," + my + "," + mz + " = " + playerLocation.getX() + "," +
			 * playerLocation.getY() + "," + playerLocation.getZ() + " world " +
			 * selectworldname);
			 */
		}

		@Override
		public void run() {
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewset"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewset"));

				return;
			}

			CoreProtectAPI CoreProtect = getCoreProtect();

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block hostBlock = null;
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewcopy_thread xgn2 = new dewcopy_thread(player, mx, my, mz, lx, ly, lz, xlx, ylx, zlx,
									amountloop, selectx1, selecty1, selectz1, selectworldname, playerLocation);

							dprint.r.printC(player.getName() + " time out dewcopy  " + tr.gettr("recall") + " " + xlx
									+ " , " + ylx + " , " + zlx);
							dprint.r.printC(player.getName() + " recalling low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz + " amountloop " + amountloop);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						hostBlock = Bukkit.getWorld(selectworldname).getBlockAt(xlx, ylx, zlx);

						if (amountloop == 1 || amountloop == 3) { // if first
																	// round ...
																	// only
																	// block
							if (hostBlock.getType().isBlock() == false) {
								zlx++;
								continue;
							}
						}

						if (amountloop == 2 || amountloop == 4) {
							if (hostBlock.getType().isBlock() == true) {
								zlx++;
								continue;
							}
						}

						Block setBlock = playerLocation.getBlock().getRelative(hostBlock.getX() - selectx1,
								hostBlock.getY() - selecty1, hostBlock.getZ() - selectz1);

						if (cando_all(setBlock, player, "dewset") == false)
							return;

						if (arxx)
							if (decreseitem1(player, hostBlock.getTypeId(), hostBlock.getData(), true) == false) {
								player.sendMessage(
										dprint.r.color("ptdew&dewdd : dewcopy " + tr.gettr("don't_have_enough_item")
												+ hostBlock.getTypeId() + ":" + hostBlock.getData()));
								return;
							}

						// if (setBlock.getType() != hostBlock.getType() &&
						// setBlock.getData() != hostBlock.getData()) {
						saveHistory(CoreProtect, player, hostBlock, setBlock, false);

						// }

						if (hostBlock.getType() == Material.SIGN_POST || hostBlock.getType() == Material.WALL_SIGN) {

							Sign hostSign = (Sign) hostBlock.getState();
							Sign setSign = (Sign) setBlock.getState();

							for (int i = 0; i < 4; i++)
								setSign.setLine(i, hostSign.getLine(i));

							setSign.update(true);
							saveHistory(CoreProtect, player, hostBlock, setBlock, true);
						}

						if (amountloop == 3 && player.hasPermission(pmainalsocopyinventoryblockwhenyouusedewset)) {

							switch (hostBlock.getType()) {
							case CHEST:
							case TRAPPED_CHEST:

								Chest hostChest = (Chest) hostBlock.getState();
								Chest setChest = (Chest) setBlock.getState();

								for (ItemStack itm : hostChest.getInventory().getContents()) {
									if (itm == null) {
										continue;
									}

									setChest.getInventory().addItem(itm);

									continue;

								}

								setChest.update(true);
								saveHistory(CoreProtect, player, hostBlock, setBlock, true);
								break;
							case DISPENSER:

								Dispenser hostDispenser = (Dispenser) hostBlock.getState();
								Dispenser setDispenser = (Dispenser) setBlock.getState();

								for (ItemStack itm : hostDispenser.getInventory().getContents()) {
									if (itm == null) {
										continue;
									}

									setDispenser.getInventory().addItem(itm);
									continue;

								}

								setDispenser.update(true);
								saveHistory(CoreProtect, player, hostBlock, setBlock, true);
								break;

							case HOPPER:
								Hopper hostHopper = (Hopper) hostBlock.getState();
								Hopper setHopper = (Hopper) setBlock.getState();

								for (ItemStack itm : hostHopper.getInventory().getContents()) {
									if (itm == null) {
										continue;
									}

									setHopper.getInventory().addItem(itm);
									continue;

								}

								setHopper.update(true);
								saveHistory(CoreProtect, player, hostBlock, setBlock, true);
								break;

							case DROPPER:

								Dropper hostDropper = (Dropper) hostBlock.getState();
								Dropper setDropper = (Dropper) setBlock.getState();

								for (ItemStack itm : hostDropper.getInventory().getContents()) {
									if (itm == null) {
										continue;
									}

									setDropper.getInventory().addItem(itm);
									continue;

								}

								setDropper.update(true);
								saveHistory(CoreProtect, player, hostBlock, setBlock, true);
								break;
							}

						}

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}

			xlx = lx;

			amountloop++;

			if (amountloop <= 4) {
				dewcopy_thread xgn2 = new dewcopy_thread(player, mx, my, mz, lx, ly, lz, xlx, ylx, zlx, amountloop,
						selectx1, selecty1, selectz1, selectworldname, playerLocation);

				dprint.r.printC(
						player.getName() + " copy  " + tr.gettr("recall") + " " + xlx + " , " + ylx + " , " + zlx);
				dprint.r.printC(player.getName() + " low " + lx + " , " + ly + " , " + lz + " high " + mx + "," + my
						+ "," + mz + " amountloop " + amountloop);

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);
				return;
			}

			dprint.r.printC("ptdew&dewdd : dewcopy " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : dewcopy " + tr.gettr("done") + " : " + player.getName());

		}
	}

	class dewdown_c implements Runnable {
		private ArrayList<IDDataType> item;
		private Player player;

		public dewdown_c(Player player, ArrayList<IDDataType> item) {
			this.player = player;
			this.item = item;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			Block setBlock = player.getLocation().getBlock().getRelative(0, -2, 0);
			if (isprotectitemid(setBlock.getType()) == true)
				return;

			if (cando_all(setBlock, player, "dewset") == false) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdown " + tr.gettr("this_is_not_your_zone")));
				return;
			}

			addItemIfItemIsZero(item, player);
			if (item.size() == 0) {
				return;
			}
			int handid = item.get(0).id;
			byte handdata = item.get(0).data;

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewdown " + handid
					+ ":" + handdata);

			if (setBlock.getTypeId() == handid && setBlock.getData() == handdata) {
				// zlx++;
				return;
			}
			CoreProtectAPI CoreProtect = getCoreProtect();

			if (handid == 0) { // if delete
				if (!perDelete) {
					player.sendMessage(dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
					return;
				}
				if (CoreProtect != null) { // Ensure we have access
											// to the API
					boolean success = CoreProtect.logRemoval(player.getName(), setBlock.getLocation(),
							setBlock.getType(), setBlock.getData());
				}
				setBlock.setTypeId(0);

			}

			else { // if place
				if (arxx)
					if (decreseitem1(player, handid, handdata, true) == false) {
						player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
						return;
					}

				setBlock.setTypeIdAndData(handid, handdata, false);

				if (CoreProtect != null) { // Ensure we have access
											// to the API
					boolean success = CoreProtect.logPlacement(player.getName(), setBlock.getLocation(),
							setBlock.getType(), setBlock.getData());
				}

				//

			}

			dprint.r.printC("ptdew&dewdd : dewdown " + player.getName() + " " + tr.gettr("complete"));
		}
	}

	class dewextend_c implements Runnable {
		private Player player;

		public dewextend_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {
			player.sendMessage(dprint.r.color("ptdew&dewdd : dewextend" + tr.gettr("starting")));
			int getid = getfreeselect(player);
			selecty1[getid] = 0;
			selecty2[getid] = 255;

			player.sendMessage(dprint.r.color(
					"ptdew&dewdd : selected area = (" + selectx1[getid] + "," + selecty1[getid] + "," + selectz1[getid]
							+ ") to (" + selectx2[getid] + "," + selecty2[getid] + "," + selectz2[getid] + ") = "));

			player.sendMessage(dprint.r.color("ptdew&dewdd : dewextend " + tr.gettr("complete") + "d"));
		}
	}

	class dewselectcube_c implements Runnable {
		private Player player;
		private int rad;

		public dewselectcube_c(Player player, int rad) {
			this.player = player;
			this.rad = rad;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {
			player.sendMessage(dprint.r.color("ptdew&dewdd : dew select cube" + tr.gettr("starting")));
			int getid = getfreeselect(player);

			selecty1[getid] = player.getLocation().getBlockY();
			selecty2[getid] = player.getLocation().getBlockY();

			selectx1[getid] = player.getLocation().getBlockX();
			selectx2[getid] = player.getLocation().getBlockX();

			selectz1[getid] = player.getLocation().getBlockZ();
			selectz2[getid] = player.getLocation().getBlockZ();

			selecty1[getid] -= rad;
			selecty2[getid] += rad;
			selectx1[getid] -= rad;
			selectx2[getid] += rad;
			selectz1[getid] -= rad;
			selectz2[getid] += rad;

			player.sendMessage(dprint.r.color(
					"ptdew&dewdd : selected area = (" + selectx1[getid] + "," + selecty1[getid] + "," + selectz1[getid]
							+ ") to (" + selectx2[getid] + "," + selecty2[getid] + "," + selectz2[getid] + ") = "));

			player.sendMessage(dprint.r.color("ptdew&dewdd : dew select cube " + tr.gettr("complete") + "d"));
		}
	}

	// Bigdigthread

	class dewset_mom implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private boolean invert;
		private Player player = null;

		public dewset_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, boolean invert) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.invert = invert;
		}

		@Override
		public void run() {

			int lx = 0;
			int mx = 0;
			int ly = 0;
			int my = 0;
			int lz = 0;
			int mz = 0;

			int getid = getfreeselect(player);

			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset " + tr.gettr("please_set_block_2")));
				return;
			}

			// player.sendMessage(dprint.r.color(". " + e1 + "," + e2 + "|" + e3
			// + "," + e4);

			if (invert == false) {
				dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset "
						+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));
				player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset "
						+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			} else {
				dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewxet "
						+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));
				player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewxet "
						+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			}

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			Location playerLocation = player.getLocation();

			dewset_thread xgn = new dewset_thread(player, item, itemSearch, invert, mx, my, mz, lx, ly, lz, lx, ly, lz,
					getid, playerLocation);

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn);

			// > > >

			// run thread

		}
	}

	// skyblock
	// nether
	// invert
	// old_1
	// flat
	// old_2
	// float

	class dewset_thread implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private int getid;
		private boolean invert;
		private int lx;

		private int ly;
		private int lz;
		private int mx;

		private int my;
		private int mz;
		private Player player = null;

		private int xlx;
		private int ylx;
		private int zlx;
		private Location playerLocation;

		public dewset_thread(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch,
				boolean invert, int mx, int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid,
				Location playerLocation) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.invert = invert;
			this.playerLocation = playerLocation;

			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;

			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;

			this.getid = getid;

		}

		@Override
		public void run() {

			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewset"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewset"));

				return;
			}

			CoreProtectAPI CoreProtect = getCoreProtect();

			boolean arxx = !player.hasPermission(pmaininfinite);

			boolean perDelete = player.hasPermission(pmaindelete);

			Block blb = null;

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (xlx <= mx) {

				while (ylx <= my) {
					while (zlx <= mz) {

						blb = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewset_thread xgn2 =

							new dewset_thread(player, item, itemSearch, invert, mx, my, mz, lx, ly, lz, xlx, ylx, zlx,
									getid, playerLocation);

							dprint.r.printC(player.getName() + " dewset  " + tr.gettr("recall") + " " + xlx + " , "
									+ ylx + " , " + zlx);
							dprint.r.printC(player.getName() + "low " + lx + " , " + ly + " , " + lz + " high " + mx
									+ "," + my + "," + mz + " world " + playerLocation.getWorld().getName());

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						if (itemSearch.size() > 0) {
							boolean gen = IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData());
							if (gen == false && invert == false) {
								zlx++;
								continue;
							}
							if (gen == true && invert == true) {
								zlx++;
								continue;
							}
						}

						if (cando_all(blb, player, "dewset") == false)
							return;

						int randid = rnd.nextInt(item.size());
						int id = item.get(randid).id;
						byte data = item.get(randid).data;

						if (blb.getTypeId() == id && blb.getData() == data) {
							zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						zlx++;
					} // z
					zlx = lz;

					ylx++;
				} // y
				ylx = ly;

				xlx++;
			} // x
			xlx = lx;

			if (invert == false) {
				dprint.r.printC("ptdew&dewdd : set " + tr.gettr("done") + " : " + player.getName());
				player.sendMessage("ptdew&dewdd : set " + tr.gettr("done") + " : " + player.getName());

			} else {
				dprint.r.printC("ptdew&dewdd : xet " + tr.gettr("done") + " : " + player.getName());
				player.sendMessage("ptdew&dewdd : xet " + tr.gettr("done") + " : " + player.getName());

			}
		}
	}

	public static CoreProtectAPI getCoreProtect() {
		Plugin plugin = ac.getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (plugin == null || !(plugin instanceof CoreProtect)) {
			return null;
		}

		// Check that the API is enabled
		CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
		if (CoreProtect.isEnabled() == false) {
			return null;
		}

		// Check that a compatible version of the API is loaded
		if (CoreProtect.APIVersion() < 4) {
			return null;
		}

		return CoreProtect;
	}

	class dewsetblock_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private boolean isfillmode = false;
		private Player player = null;

		public dewsetblock_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.isfillmode = isfillmode;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset(fill)block " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset(fill)block " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset(fill)block "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));
			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset(fill)block "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			Location playerLocation = player.getLocation();
			dewsetblock_thread aer = new dewsetblock_thread(player, item, itemSearch, mx, my, mz, lx, ly, lz, lx, ly,
					lz, getid, playerLocation);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

		}
	}

	class dewsetblock_thread implements Runnable {

		private int lx = 0;
		private int ly = 0;

		private int lz = 0;
		private int mx = 0;
		private int my = 0;

		private int mz = 0;
		private Player player = null;
		private int xlx = 0;

		private int ylx = 0;

		private int zlx = 0;

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private int getid;
		private Location playerLocation;

		public dewsetblock_thread(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid, Location playerLocation) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.playerLocation = playerLocation;

			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;
			this.getid = getid;

		}

		@Override
		public void run() {
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewsetblock"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewsetblock"));

				return;
			}
			CoreProtectAPI CoreProtect = getCoreProtect();

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			boolean t1 = false;
			boolean t2 = false;

			addItemIfItemIsZero(item, player);

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {
						t1 = ylx == selecty1[getid] || ylx == selecty2[getid];

						t2 = xlx == selectx1[getid] || zlx == selectz1[getid] || xlx == selectx2[getid]
								|| zlx == selectz2[getid];

						if (!(t1 && t2 || !t1 && xlx == selectx1[getid] && zlx == selectz1[getid]
								|| xlx == selectx2[getid] && zlx == selectz2[getid]
								|| xlx == selectx1[getid] && zlx == selectz2[getid]
								|| xlx == selectx2[getid] && zlx == selectz1[getid]

						)

						) {
							zlx++;
							continue;
						}
						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewsetblock_thread xgn2 = new dewsetblock_thread(player, item, itemSearch, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, playerLocation);

							dprint.r.printC(player.getName() + " dewsetblock  " + tr.gettr("recall") + " " + xlx + " , "
									+ ylx + " , " + zlx);
							dprint.r.printC(player.getName() + " low " + lx + " , " + ly + " , " + lz + " high " + mx
									+ "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						blb = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

						/*
						 * if (isfillmode == true) if (blb.getTypeId() != 0) {
						 * zlx++; continue; }
						 */
						if (itemSearch.size() != 0) {
							if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
								zlx++;
								continue;
							}

						}

						if (cando_all(blb, player, "dewset") == false)
							return;

						// coner

						int randid = rnd.nextInt(item.size());
						int id = item.get(randid).id;
						byte data = item.get(randid).data;

						if (blb.getTypeId() == id && blb.getData() == data) {
							zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printC("ptdew&dewdd : dew(set)block " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : dew(set)block " + tr.gettr("done") + " : " + player.getName());
		}
	}

	class dewsetSphere_c implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player;

		public dewsetSphere_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : setSphere " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : setSphere " + tr.gettr("please_set_block_2")));
				return;
			}

			double midx = ((double) selectx1[getid] + (double) selectx2[getid]) / 2;
			double midy = ((double) selecty1[getid] + (double) selecty2[getid]) / 2;
			double midz = ((double) selectz1[getid] + (double) selectz2[getid]) / 2;

			if (midx == selectx1[getid] && midy == selecty1[getid] && midz == selectz1[getid]
					|| midx == selectx2[getid] && midy == selecty2[getid] && midz == selectz2[getid]) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("small_circle_can't_run_program")));
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) selectx1[getid] - (double) selectx2[getid], 2);

			temp2 = Math.pow((double) selecty1[getid] - (double) selecty2[getid], 2);

			temp3 = Math.pow((double) selectz1[getid] - (double) selectz2[getid], 2);

			double midty = (selecty1[getid] + selecty2[getid]) / 2;

			double midtx = (selectx1[getid] + selectx2[getid]) / 2;

			double midtz = (selectz1[getid] + selectz2[getid]) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;
			Block blockmid = player.getWorld().getBlockAt((int) midtx, (int) midty, (int) midtz);

			player.sendMessage(dprint.r.color("cir=" + midtx + "," + midty + "," + midtz));
			player.sendMessage(dprint.r.color("R=" + temp5));

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " setSphere "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " setSphere "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			CoreProtectAPI CoreProtect = getCoreProtect();

			for (Block blb : getselectblock(getid, player)) {

				/*
				 * if (PreciousStones.API().canPlace(player,
				 * blb.getLocation())== false) {
				 * player.sendMessage(dprint.r.color (
				 * "ptdew&dewdd :Can't setSphere here (" + blb.getX() + "," +
				 * blb.getY() + "," + blb.getZ() + ")"); continue; }
				 */

				if (blb.getLocation().distance(blockmid.getLocation()) > midr) {
					continue;
				}

				if (itemSearch.size() > 0) {
					if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
						continue;
					}
				}

				if (cando_all(blb, player, "dewset") == false)
					return;

				int ranslot = rnd.nextInt(item.size());
				int id = item.get(ranslot).id;
				byte data = item.get(ranslot).data;

				if (blb.getTypeId() == id && blb.getData() == data) {
					// zlx++;
					continue;
				}

				if (id == 0) { // if delete
					if (!perDelete) {
						player.sendMessage(dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
						return;
					}
					if (CoreProtect != null) { // Ensure we have access
												// to the API
						boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(), blb.getType(),
								blb.getData());
					}
					blb.setTypeId(0);

				}

				else { // if place
					if (arxx)
						if (decreseitem1(player, id, data, true) == false) {
							player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
							return;
						}

					blb.setTypeIdAndData(id, data, false);

					if (CoreProtect != null) { // Ensure we have access
												// to the API
						boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(), blb.getType(),
								blb.getData());
					}

					//

				}
				//
			} // for

			dprint.r.printC("ptdew&dewdd : setSphere " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : setSphere " + tr.gettr("done") + " : " + player.getName());

		}
	}

	class dewsetl_thread implements Runnable {

		private int getid;
		private ArrayList<IDDataType> item;
		private int lx = 0;
		private int ly = 0;
		private int lz = 0;

		private int mx = 0;
		private int my = 0;
		private int mz = 0;

		private Player player = null;
		private int xlx = 0;
		private int ylx = 0;

		private int zlx = 0;
		private Location playerLocation;

		public dewsetl_thread(Player player, ArrayList<IDDataType> item, int mx, int my, int mz, int lx, int ly, int lz,
				int xlx, int ylx, int zlx, int getid, Location playerLocation) {
			this.player = player;
			this.item = item;
			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;
			this.getid = getid;
			this.playerLocation = playerLocation;
		}

		@Override
		public void run() {
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewsetLight"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewsetLight"));

				return;
			}

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean ne = false;
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			addItemIfItemIsZero(item, player);

			CoreProtectAPI CoreProtect = getCoreProtect();

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewsetl_thread xgn2 = new dewsetl_thread(player, item, mx, my, mz, lx, ly, lz, xlx, ylx,
									zlx, getid, playerLocation);

							dprint.r.printC("dewsetl  " + tr.gettr("recall") + " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC(
									"low " + lx + " , " + ly + " , " + lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						blb = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

						if (blb.getTypeId() != 0) {
							zlx++;
							continue;
						}

						if (blb.getLightLevel() >= 9) {
							zlx++;
							continue;
						}

						ne = false;
						ne = blb.getRelative(-1, 0, 0).getTypeId() != 0;
						ne = ne || blb.getRelative(1, 0, 0).getTypeId() != 0;
						ne = ne || blb.getRelative(0, 0, -1).getTypeId() != 0;
						ne = ne || blb.getRelative(0, 0, 1).getTypeId() != 0;
						ne = ne || blb.getRelative(0, -1, 0).getTypeId() != 0;

						if (ne == false) {
							zlx++;
							continue;
						}

						if (cando_all(blb, player, "dewset") == false)
							return;

						int randid = rnd.nextInt(item.size());
						int id = item.get(randid).id;
						byte data = item.get(randid).data;

						if (blb.getTypeId() == id && blb.getData() == data) {
							zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}
						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printC("ptdew&dewdd : dewsetlight " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : dewsetlight " + tr.gettr("done") + " : " + player.getName());

		}
	}

	class dewsetLight_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private Player player = null;

		public dewsetLight_mom(Player player, ArrayList<IDDataType> item) {
			this.player = player;
			this.item = item;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewsetlight " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewsetlight " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewsetlight "
					+ IDDataType.arrayListToString(item) + " >_< ");
			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewsetlight "
					+ IDDataType.arrayListToString(item) + " >_< ");

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewsetl_thread aer = new dewsetl_thread(player, item, mx, my, mz, lx, ly, lz, lx, ly, lz, getid,
					player.getLocation());
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

		}
	}

	class dewsetroom_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player = null;

		public dewsetroom_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset(fill)room " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset(fill)room " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset(fill)room "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));
			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset(fill)room "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			Location playerLocation = player.getLocation();
			dewsetroom_thread aer = new dewsetroom_thread(player, item, itemSearch, mx, my, mz, lx, ly, lz, lx, ly, lz,
					getid, playerLocation);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

		}
	}

	class dewsetroom_thread implements Runnable {

		private int getid;
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;

		private boolean isfillmode = false;
		private int lx = 0;
		private int ly = 0;

		private int lz = 0;
		private int mx = 0;
		private int my = 0;

		private int mz = 0;
		private Player player = null;
		private int xlx = 0;

		private int ylx = 0;

		private int zlx = 0;
		private Location playerLocation;

		public dewsetroom_thread(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid, Location playerLocation) {
			this.player = player;
			this.isfillmode = isfillmode;
			this.item = item;
			this.itemSearch = itemSearch;
			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;
			this.getid = getid;
			this.playerLocation = playerLocation;
		}

		@Override
		public void run() {
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewsetRoom"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewsetRoom"));

				return;
			}
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			addItemIfItemIsZero(item, player);
			Block blb = null;

			CoreProtectAPI CoreProtect = getCoreProtect();

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						if (!(xlx == selectx1[getid] || xlx == selectz1[getid] || xlx == selectx2[getid]
								|| xlx == selectz2[getid] || zlx == selectx1[getid] || zlx == selectz1[getid]
								|| zlx == selectx2[getid] || zlx == selectz2[getid] || ylx == selecty1[getid]
								|| ylx == selecty2[getid]

						)) {
							zlx++;
							continue;
						}

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewsetroom_thread xgn2 = new dewsetroom_thread(player, item, itemSearch, mx, my, mz, lx, ly,
									lz, xlx, ylx, zlx, getid, playerLocation);

							dprint.r.printC(
									"dewsetroom  " + tr.gettr("recall") + " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC(
									"low " + lx + " , " + ly + " , " + lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						blb = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

						if (itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
								zlx++;
								continue;
							}
						}

						if (cando_all(blb, player, "dewset") == false)
							return;

						int randid = rnd.nextInt(item.size());
						int id = item.get(randid).id;
						byte data = item.get(randid).data;

						if (blb.getTypeId() == id && blb.getData() == data) {
							zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printC("ptdew&dewdd : dew(set)room " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : dew(set)room " + tr.gettr("done") + " : " + player.getName());

		}
	}

	class dewsetwall_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player = null;

		public dewsetwall_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset(fill)wall " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset(fill)wall " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset(fill)wall "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));
			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " dewset(fill)wall "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewsetwall_thread aer = new dewsetwall_thread(player, item, itemSearch, mx, my, mz, lx, ly, lz, lx, ly, lz,
					getid, player.getLocation());
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

		}
	}

	class dewsetwall_thread implements Runnable {

		private int getid;
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;

		private int lx = 0;
		private int ly = 0;

		private int lz = 0;
		private int mx = 0;
		private int my = 0;

		private int mz = 0;
		private Player player = null;
		private int xlx = 0;

		private int ylx = 0;
		private Location playerLocation;
		private int zlx = 0;

		public dewsetwall_thread(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid, Location playerLocation) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;
			this.getid = getid;
			this.playerLocation = playerLocation;
		}

		@Override
		public void run() {
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel dewsetWall"));
				player.sendMessage(player.getName() + tr.gettr("has cancel dewsetWall"));

				return;
			}

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			addItemIfItemIsZero(item, player);

			CoreProtectAPI CoreProtect = getCoreProtect();

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						if (!(xlx == selectx1[getid] || zlx == selectz1[getid] || xlx == selectx2[getid]
								|| zlx == selectz2[getid] || xlx == selectx1[getid] || zlx == selectz1[getid]
								|| xlx == selectx2[getid] || zlx == selectz2[getid])) {
							zlx++;
							continue;
						}

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewsetwall_thread xgn2 = new dewsetwall_thread(player, item, itemSearch, mx, my, mz, lx, ly,
									lz, xlx, ylx, zlx, getid, playerLocation);

							dprint.r.printC(
									"dewsetwall  " + tr.gettr("recall") + " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC(
									"low " + lx + " , " + ly + " , " + lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						blb = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

						if (itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
								zlx++;
								continue;
							}
						}

						if (cando_all(blb, player, "dewset") == false)
							return;

						int randid = rnd.nextInt(item.size());
						int id = item.get(randid).id;
						byte data = item.get(randid).data;

						if (blb.getTypeId() == id && blb.getData() == data) {
							zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printC("ptdew&dewdd : dew(set)fill " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : dew(set)fill " + tr.gettr("done") + " : " + player.getName());

		}
	}

	class dewsetWallSphere_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private boolean isfillmode = false;
		private Player player = null;

		public dewsetWallSphere_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			// ..........
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0 && selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : wallSphere " + tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0 && selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : wallSphere " + tr.gettr("please_set_block_2")));
				return;
			}

			double midx = ((double) selectx1[getid] + (double) selectx2[getid]) / 2;
			double midy = ((double) selecty1[getid] + (double) selecty2[getid]) / 2;
			double midz = ((double) selectz1[getid] + (double) selectz2[getid]) / 2;

			if (midx == selectx1[getid] && midy == selecty1[getid] && midz == selectz1[getid]
					|| midx == selectx2[getid] && midy == selecty2[getid] && midz == selectz2[getid]) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : small circle can't run program"));
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) selectx1[getid] - (double) selectx2[getid], 2);

			temp2 = Math.pow((double) selecty1[getid] - (double) selecty2[getid], 2);

			temp3 = Math.pow((double) selectz1[getid] - (double) selectz2[getid], 2);

			double midty = (selecty1[getid] + selecty2[getid]) / 2;

			double midtx = (selectx1[getid] + selectx2[getid]) / 2;

			double midtz = (selectz1[getid] + selectz2[getid]) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;

			player.sendMessage(dprint.r.color("cir=" + midtx + "," + midty + "," + midtz));
			player.sendMessage(dprint.r.color("R=" + temp5));

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " wallSphere "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			player.sendMessage("ptdew&dewdd : '" + player.getName() + "'" + tr.gettr("starting") + " wallSphere "
					+ IDDataType.arrayListToString(item) + " >_< " + IDDataType.arrayListToString(itemSearch));

			// ........

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			} else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			} else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			} else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewWallSphere_thread aer = new dewWallSphere_thread(player, item, itemSearch, mx, my, mz, lx, ly, lz, lx,
					ly, lz, getid, midr, midtx, midty, midtz, player.getLocation());
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, sleeptime);

		}
	}

	class dewSpreadSphere_thread implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;

		private Player player;

		public dewSpreadSphere_thread(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {
			Block block = player.getLocation().getBlock();
			Queue<Block> bd = new LinkedList<Block>();

			addItemIfItemIsZero(item, player);

			boolean ne = false;
			Block blb = null;
			int x = 0;
			int y = 0;
			int z = 0;

			for (x = -1; x <= 1; x++) {
				for (y = -1; y <= 1; y++) {
					for (z = -1; z <= 1; z++) {
						blb = block.getRelative(x, y, z);

						bd.add(blb);
					}
				}
			}

			Block b3 = null;

			if (bd.size() <= 0)
				return;

			CoreProtectAPI CoreProtect = getCoreProtect();
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			while (bd.size() > 0) { // bll
				b3 = bd.poll();

				ne = false;

				for (x = -1; x <= 1; x++) {
					for (y = -1; y <= 1; y++) {
						for (z = -1; z <= 1; z++) {
							blb = b3.getRelative(x, y, z);

							if (itemSearch.size() > 0) {
								if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
									continue;
								}
							}

							if (cando_all(blb, player, "dewset") == false) {
								continue;
							}

							int randid = rnd.nextInt(item.size());
							int id = item.get(randid).id;
							byte data = item.get(randid).data;

							// do not allow to spread Air Block ( it's will be
							// infinite loop)
							if (id == 0) {
								return;
							}

							if (blb.getTypeId() == id && blb.getData() == data) {
								// zlx++;
								continue;
							}

							if (id == 0) { // if delete
								if (!perDelete) {
									player.sendMessage(dprint.r
											.color(tr.gettr("don't_have_permission_for_access_delete_command")));
									return;
								}
								if (CoreProtect != null) { // Ensure we have
															// access
															// to the API
									boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
											blb.getType(), blb.getData());
								}
								blb.setTypeId(0);

							}

							else { // if place
									// if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

								blb.setTypeIdAndData(id, data, false);

								if (CoreProtect != null) { // Ensure we have
															// access
															// to the API
									boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
											blb.getType(), blb.getData());
								}

								//

							}

							ne = true;
							break;

						}
					}
				}

				if (ne == false) {
					continue;
				}

				for (x = -1; x <= 1; x++) {
					for (y = -1; y <= 1; y++) {
						if (y + b3.getY() < 1 || y + b3.getY() > 254) {
							continue;
						}

						for (z = -1; z <= 1; z++) {

							blb = b3.getRelative(x, y, z);
							// dprint.r.printAll("ptdew&dewdd : delete near call
							// sub ("
							// +
							// b2.getX() + "," + b2.getY() + "," + b2.getZ() +
							// ") "
							// + amount);

							bd.add(blb);

						}

					}
				}

			} // bll

			player.sendMessage(dprint.r.color("spreadSphere " + tr.gettr(tr.gettr("done"))));
		}
	}

	class dewspreadq_c implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player;
		private Boolean isfirst;
		private Queue<Block> bd;
		private Location playerLocation;

		public dewspreadq_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch,
				boolean isfirst, Queue<Block> bd, Location playerLocation) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.isfirst = isfirst;
			this.bd = bd;
			this.playerLocation = playerLocation;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {

			Block block = playerLocation.getBlock();
			addItemIfItemIsZero(item, player);

			boolean ne = false;
			Block b2 = null;
			int x = 0;
			int z = 0;

			if (isfirst == true) {
				isfirst = false;
				for (x = -1; x <= 1; x++) {
					for (z = -1; z <= 1; z++) {
						b2 = block.getRelative(x, 0, z);

						bd.add(b2);
					}
				}

			}

			Block blb = null;

			int ccc = 0;

			if (bd.size() <= 0) {
				// bd.clear();
				return;

			}

			CoreProtectAPI CoreProtect = getCoreProtect();
			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			while (bd.size() > 0) { // bll
				blb = bd.poll();

				ne = false;

				if (itemSearch.size() > 0) {
					if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
						continue;
					}
				}

				if (cando_all(blb, player, "dewset") == false) {
					continue;
				}

				int randid = rnd.nextInt(item.size());
				int id = item.get(randid).id;
				byte data = item.get(randid).data;

				// do not allow to spread Air Block ( it's will be infinite
				// loop)
				if (id == 0) {
					return;
				}

				if (blb.getTypeId() == id && blb.getData() == data) {
					// zlx++;
					continue;
				}

				if (id == 0) { // if delete
					if (!perDelete) {
						player.sendMessage(dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
						return;
					}
					if (CoreProtect != null) { // Ensure we have access
												// to the API
						boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(), blb.getType(),
								blb.getData());
					}
					blb.setTypeId(0);

				}

				else { // if place
						// if (arxx)
					if (decreseitem1(player, id, data, true) == false) {
						player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
						return;
					}

					blb.setTypeIdAndData(id, data, false);

					if (CoreProtect != null) { // Ensure we have access
												// to the API
						boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(), blb.getType(),
								blb.getData());
					}

					//

				}

				ccc++;
				ne = true;

				if (ne == false) {
					continue;
				}

				for (x = -1; x <= 1; x++) {
					for (z = -1; z <= 1; z++) {

						if (x != 0 && z != 0) {
							continue;
						}
						b2 = blb.getRelative(x, 0, z);
						// dprint.r.printAll("ptdew&dewdd : delete near call sub
						// ("
						// +
						// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") "
						// +
						// amount);

						bd.add(b2);

					}
				}

				if (ccc > 10) {
					new dewspreadq_c(player, item, itemSearch, false, bd, playerLocation);
					return;
				}

			} // bll

			player.sendMessage(dprint.r.color("dewspreadq " + tr.gettr(tr.gettr("done"))));
		}
	}

	class dewWallSphere_thread implements Runnable {

		private int getid;
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;

		private int lx = 0;
		private int ly = 0;

		private int lz = 0;
		private double midr;
		private double midtx;

		private double midty;
		private double midtz;
		private int mx = 0;

		private int my = 0;
		private int mz = 0;

		private Player player = null;

		private int xlx = 0;
		private int ylx = 0;
		private int zlx = 0;
		private Location playerLocation;

		public dewWallSphere_thread(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid, double midr, double midtx,
				double midty, double midtz, Location playerLocation) {
			this.player = player;
			this.item = item;
			this.playerLocation = playerLocation;
			this.itemSearch = itemSearch;
			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;
			this.getid = getid;
			this.midr = midr;
			this.midtx = midtx;
			this.midty = midty;
			this.midtz = midtz;
		}

		@Override
		public void run() {
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(player.getName() + tr.gettr("has cancel  wallSphere"));
				player.sendMessage(player.getName() + tr.gettr("has cancel  wallSphere"));

				return;
			}

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;

			addItemIfItemIsZero(item, player);
			Block blockmid = player.getWorld().getBlockAt((int) midtx, (int) midty, (int) midtz);

			boolean arxx = !player.hasPermission(pmaininfinite);
			boolean perDelete = player.hasPermission(pmaindelete);

			CoreProtectAPI CoreProtect = getCoreProtect();

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime) {

							dewWallSphere_thread xgn2 = new dewWallSphere_thread(player, item, itemSearch, mx, my, mz,
									lx, ly, lz, xlx, ylx, zlx, getid, midr, midtx, midty, midtz, playerLocation);

							dprint.r.printC(
									"wallSphere  " + tr.gettr("recall") + " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC(
									"low " + lx + " , " + ly + " , " + lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac, xgn2, sleeptime);

							return;
						}

						blb = playerLocation.getWorld().getBlockAt(xlx, ylx, zlx);

						if ((int) blb.getLocation().distance(blockmid.getLocation()) != (int) midr) {
							zlx++;
							continue;
						}

						if (itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(itemSearch, blb.getTypeId(), blb.getData())) {
								zlx++;
								continue;
							}
						}

						// wallc

						if (cando_all(blb, player, "dewset") == false)
							return;

						int randid = rnd.nextInt(item.size());
						int id = item.get(randid).id;
						byte data = item.get(randid).data;

						if (blb.getTypeId() == id && blb.getData() == data) {
							zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, id, data, true) == false) {
									player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printC("ptdew&dewdd : wallSphere " + tr.gettr("done") + " : " + player.getName());
			player.sendMessage("ptdew&dewdd : wallSphere " + tr.gettr("done") + " : " + player.getName());

		}
	}

	class randomplaynote_c implements Runnable {
		private Location player;

		public randomplaynote_c(Location player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (randomG.nextInt(100) > 10)
				return;

			for (Player pla : player.getWorld().getPlayers()) {
				pla.playSound(player, Sound.NOTE_PIANO, randomG.nextInt(50), randomG.nextFloat() + 1);
			}
		}
	}

	// redim" + tr.gettr("for") + "each world each protect = 100

	public static JavaPlugin ac = null;

	// Chat Event.class
	// BlockBreakEvent
	public static boolean isrunworld(String worldName) {
		return tr.isrunworld(ac.getName(), worldName);
	}
	// randomplaynote

	// decrese item 1

	// Check Permission Area block player mode

	Random rnd = new Random();

	public int amountRecursiveCount = 0; // recursive
	// 55
	// 55

	public int d4[] = new int[selectmax + 1];

	public Block giftblock = null;

	public Random randomG = new Random();

	// cut seem block

	public int runtime = (int) tr.gettrint("dewset runtime as milisecond");

	public long sleeptime = (int) tr.gettrint("dewset sleeptime as tick");

	public Block selectblock[] = new Block[selectmax + 1];

	public String selectname[] = new String[selectmax + 1];

	public String selectworldname[] = new String[selectmax + 1];

	public int selectx1[] = new int[selectmax + 1];

	// Check Permission Area block

	public int selectx2[] = new int[selectmax + 1];

	public int selecty1[] = new int[selectmax + 1];

	public int selecty2[] = new int[selectmax + 1];

	public int selectz1[] = new int[selectmax + 1];

	public int selectz2[] = new int[selectmax + 1];

	public dewset() {
		// if (firstrun19 == false){

		// loadadminlist();

		// firstrun19 = true;
		// }
	}

	public void addItemIfItemIsZero(ArrayList<IDDataType> item, Player player) {
		if (item.size() == 0) {
			if (player.getItemInHand() == null) {
				return;
			}

			IDDataType ne = new IDDataType(player.getItemInHand().getTypeId(),
					player.getItemInHand().getData().getData());
			item.add(ne);
		}
	}

	public boolean canaddtorch(Block bbc) {
		if (bbc.getTypeId() != 0)
			return false;

		if (canaddtorchatblock(bbc.getRelative(0, -1, 0)) == true
				|| canaddtorchatblock(bbc.getRelative(1, 0, 0)) == true
				|| canaddtorchatblock(bbc.getRelative(-1, 0, 0)) == true
				|| canaddtorchatblock(bbc.getRelative(0, 0, 1)) == true
				|| canaddtorchatblock(bbc.getRelative(0, 0, -1)) == true)
			return true;

		return false;
	}

	// canaddtorchatblock
	public boolean canaddtorchatblock(Block bbc) {
		if (bbc.getTypeId() != 0 && bbc.isLiquid() == false) {
			switch (bbc.getTypeId()) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 7:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 21:
			case 22:
			case 24:
			case 35:
			case 41:
			case 42:
			case 43:
			case 45:
			case 56:
			case 57:
			case 73:
			case 87:
			case 88:
			case 97:
			case 98:
			case 125:
			case 133:
			case 112:
				return true;
			}
		}
		return false;

	}

	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = ac.getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

	public WorldGuardPlugin wgapi = null;
	public boolean CONFIG_NEED_PROTECT = false;

	public boolean cando_all(Block block, Player player, String modeevent) {

		boolean wg = false;
		boolean wgHasProtect = false;

		if (wgapi == null) {
			wgapi = getWorldGuard();
		}

		if (wgapi != null) {
			if (CONFIG_NEED_PROTECT == true) {

			ApplicableRegionSet set = wgapi.getRegionManager(block.getWorld())
					.getApplicableRegions(block.getLocation());
			//player.sendMessage("rg size " + set.getRegions().size());
				if (set.getRegions().size() >= 1) {
					wgHasProtect = true;
				}

			}
			wg = wgapi.canBuild(player, block);

		}

		boolean sky = false;
		boolean skyHasProtect = false;
		if (Bukkit.getPluginManager().getPlugin("dewddskyblock") == null) {
			sky = true;
		} else {
			sky = api_skyblock.cando(block, player, modeevent);
			if (CONFIG_NEED_PROTECT == true) {
				 skyHasProtect = api_skyblock.getProtectid(block) > -1 ? true : false;
				
				
			}
		}

		boolean candoYap = wg && sky;
		boolean hasProtectYap = wgHasProtect || skyHasProtect;
		boolean output = false;
		
		if (CONFIG_NEED_PROTECT == true) {
			if (hasProtectYap == true ) {
				output = candoYap;
			}
			else {
				output = false;
			}
		}
		else {
			output = candoYap;
		}
		
		return  output;
	}

	/*
	 * public boolean cando_Main(Block block, Player player, String modeevent) {
	 * RSWorld worldid = getWorld(block.getWorld().getName());
	 * 
	 * if (worldid == null) if (api_admin.dewddadmin.is2moderator(player) ==
	 * true) return false; else return true;
	 * 
	 * if (worldid.rs.size() == 0) if (api_admin.dewddadmin.is2moderator(player)
	 * == true) return false; else return true;
	 * 
	 * block.getX(); block.getY(); block.getZ();
	 * 
	 * int playerInSlot = -1; int playerInSlot2 = -1;
	 * 
	 * int dewsignnow = getProtectid(block, worldid);
	 * 
	 * boolean logic1 = false;
	 * 
	 * if (dewsignnow >= 0) { // true if that is protect
	 * 
	 * if (api_admin.dewddadmin.is2moderator(player) == true) { playerInSlot =
	 * getplayerinslot(player.getName(), dewsignnow, worldid); return
	 * !(playerInSlot == -1); }
	 * 
	 * if (modeevent.equalsIgnoreCase("right") == true) { // right everyone
	 * playerInSlot = getplayerinslot(Constant_Protect.flag_everyone,
	 * dewsignnow, worldid);
	 * 
	 * if (playerInSlot > -1) { // has flag everyone
	 * 
	 * if (api_admin.dewddadmin.is2moderator(player) == true) { // staff //
	 * can't // do // it logic1 = false; } else { logic1 = true; }
	 * 
	 * } else { // don't have flag everyone playerInSlot2 =
	 * getplayerinslot(player.getName(), dewsignnow, worldid); logic1 =
	 * !(playerInSlot2 == -1); // if have name return true }
	 * 
	 * if (logic1 == false) if (player.hasPermission(pmainoveride) == true) {
	 * logic1 = true; } return logic1;
	 * 
	 * } // right click else { // not right click
	 * 
	 * playerInSlot = getplayerinslot(player.getName(), dewsignnow, worldid);
	 * 
	 * logic1 = !(playerInSlot == -1); // don't have name can't !
	 * 
	 * if (logic1 == false) if (player.hasPermission(pmainoveride) == true) {
	 * logic1 = true; } return logic1;
	 * 
	 * } // right click or not
	 * 
	 * } else { // If not who each home // staff should't have permission to
	 * place block if (api_admin.dewddadmin.is2moderator(player) == true) {
	 * return player.hasPermission(pmainplaceblocknoprotect);
	 * 
	 * }
	 * 
	 * if (modeevent.equalsIgnoreCase("dewset") == true) return
	 * player.hasPermission(pmaindewseteverywhere);
	 * 
	 * return true; }
	 * 
	 * }
	 */

	public boolean checkbetweenblock(int digx, int digy, int digz, int x1, int y1, int z1, int x2, int y2, int z2) {

		boolean goodc1 = false;

		goodc1 = false;

		// x 2 type
		// x1 <= x2
		if (x1 <= x2) {
			if (digx > x1 - 1 && digx < x2 + 1)
				if (y1 <= y2) {
					if (digy > y1 - 1 && digy < y2 + 1)
						// y true
						if (z1 <= z2) {
							if (digz > z1 - 1 && digz < z2 + 1) {
								// z true
								goodc1 = true;
							}
						} else if (digz < z1 + 1 && digz > z2 - 1) {
							// z true
							goodc1 = true;
						}
				} else if (digy < y1 + 1 && digy > y2 - 1)
					// y true
					if (z1 <= z2) {
						if (digz > z1 - 1 && digz < z2 + 1) {
							// z true
							goodc1 = true;
						}
					} else if (digz < z1 + 1 && digz > z2 - 1) {
						// z true
						goodc1 = true;
					}
		} else if (digx < x1 + 1 && digx > x2 - 1)
			if (y1 <= y2) {
				if (digy > y1 - 1 && digy < y2 + 1)
					// y true
					if (z1 <= z2) {
						if (digz > z1 - 1 && digz < z2 + 1) {
							// z true
							goodc1 = true;
						}
					} else if (digz < z1 + 1 && digz > z2 - 1) {
						// z true
						goodc1 = true;
					}
			} else if (digy < y1 + 1 && digy > y2 - 1)
				// y true
				if (z1 <= z2) {
					if (digz > z1 - 1 && digz < z2 + 1) {
						// z true
						goodc1 = true;
					}
				} else if (digz < z1 + 1 && digz > z2 - 1) {
					// z true
					goodc1 = true;
				}

		return goodc1;

	}

	public Block chestnearsign(Block temp) {
		int d5 = 1;
		Block typebox = null;

		Block b3 = null;
		for (int ax = -d5; ax <= d5; ax++) {
			for (int ay = -d5; ay <= d5; ay++) {
				for (int az = -d5; az <= +d5; az++) {

					b3 = temp.getRelative(ax, ay, az);

					if (b3.getTypeId() != 54) {
						continue;
					}

					/*
					 * dprint.r.printAll("chestnearsign " + b3.getTypeId() +
					 * " > " + b3.getX() + "," + b3.getZ());
					 */
					// search chest type
					typebox = b3;
					break;
				}
			}
		}

		return typebox;
	}

	// decrese item 1

	public void dewStack(Player player, int amount) {

		dewStack_mom r = new dewStack_mom();
		r.amount = amount;
		r.player = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, r);

	} // dew a

	public void dewcopy(Player player) {
		dewcopy_c_mom abr = new dewcopy_c_mom(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abr);
	}

	public void dewdown(Player player, ArrayList<IDDataType> item) {

		new dewdown_c(player, item);
	}

	// fixtool

	public void dewextend(Player player) {
		new dewextend_c(player);
	}

	// getfreeselect

	public void dewselectcube(Player player, int rad) {
		new dewselectcube_c(player, rad);
	}

	public void dewset(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, boolean invert) {
		dewset_mom aer = new dewset_mom(player, item, itemSearch, invert);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);
	}

	public void dewsetblock(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewsetblock_mom aer = new dewsetblock_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	// riceblockiron

	public void dewsetFullSphere(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
		dewsetSphere_c abx = new dewsetSphere_c(player, item, itemSearch);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abx);

	}

	public void dewsetLight(Player player, ArrayList<IDDataType> item) {

		dewsetLight_mom aer = new dewsetLight_mom(player, item);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetLightAround(Player player, ArrayList<IDDataType> item) {
		dewselectcube(player, 3);

		dewsetLight_mom aer = new dewsetLight_mom(player, item);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetroom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewsetroom_mom aer = new dewsetroom_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	// sandmelon

	// ironorefreeze

	public void dewsetwall(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewsetwall_mom aer = new dewsetwall_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetWallSphere(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewsetWallSphere_mom aer = new dewsetWallSphere_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewSpreadSphere(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		new dewSpreadSphere_thread(player, item, itemSearch);
	}

	// obsidianabsorb

	// boolean firstrun19 = false;

	// Check Permission Area block
	// checkidhome

	public void dewspreadq(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
		Queue<Block> bd = new LinkedList<Block>();

		dprint.r.printC("ptdew&dewdd: " + player.getName() + " starting dsq " + IDDataType.arrayListToString(item)
				+ " >_< " + IDDataType.arrayListToString(itemSearch));
		player.sendMessage("ptdew&dewdd: " + player.getName() + " starting dsq " + IDDataType.arrayListToString(item)
				+ " >_< " + IDDataType.arrayListToString(itemSearch));

		new dewspreadq_c(player, item, itemSearch, true, bd, player.getLocation());
	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	public ArrayList<Integer> getAllHomeIDThatHaveMyName(String playerName, int worldid) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();

		return null;
	}

	// fixtool itemstack and player
	// getfreeselect
	public int getfreeselect(Player player) {
		int lp1 = 0;

		boolean foundza = false;
		// clear select array
		for (lp1 = 0; lp1 < selectmax; lp1++)
			if (selectname[lp1] == null || selectname[lp1].equalsIgnoreCase("") == true) {
				selectname[lp1] = "null";
				selectx1[lp1] = 0;
				selecty1[lp1] = 0;
				selectz1[lp1] = 0;
				selectx2[lp1] = 0;
				selecty2[lp1] = 0;
				selectz2[lp1] = 0;

				d4[lp1] = 1;
				selectblock[lp1] = null;
			}

		// clear ofline player
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			foundza = false;
			// loop player" + tr.gettr("for") + "clear
			for (Player pla : Bukkit.getOnlinePlayers())
				if (pla.getName().equalsIgnoreCase(selectname[lp1]) == true) {
					foundza = true;
					break;
					// found
				}

			if (foundza == false) { // clear
				selectname[lp1] = "null";
				selectx1[lp1] = 0;
				selecty1[lp1] = 0;
				selectz1[lp1] = 0;
				selectx2[lp1] = 0;
				selecty2[lp1] = 0;
				selectz2[lp1] = 0;

				d4[lp1] = 1;
				selectblock[lp1] = null;
			}

		}

		// if fonund return
		for (lp1 = 0; lp1 < selectmax; lp1++)
			if (player.getName().equalsIgnoreCase(selectname[lp1]) == true)
				return lp1;

		// if not found
		for (lp1 = 0; lp1 < selectmax; lp1++)
			if (selectname[lp1].equalsIgnoreCase("null") == true) {
				selectname[lp1] = player.getName();
				return lp1;
			}

		dprint.r.printC("ptdew&dewdd : " + tr.gettr("error_getfreeselect_return_-1"));
		return -1;
	}

	public LinkedList<String> getmaterialrealname(String gname) {
		LinkedList<String> ab = new LinkedList<String>();

		for (Material en : Material.values())
			if (en.name().toLowerCase().indexOf(gname.toLowerCase()) > -1) {

				dprint.r.printC("found material real name = " + en.name());
				ab.add(en.name());
			}

		return ab;
	}

	// getselectblock //" + tr.gettr("for") + "dewset or dewfill or dewdelete
	public Block[] getselectblock(int getid, Player player) {

		int adderB = -1;
		int countblock = (2 + Math.abs(selectx1[getid] - selectx2[getid]))
				* (2 + Math.abs(selecty1[getid] - selecty2[getid])) * (2 + Math.abs(selectz1[getid] - selectz2[getid]));

		player.sendMessage(dprint.r.color("countblock = " + countblock));

		int lx = 0;
		int mx = 0;
		int ly = 0;
		int my = 0;
		int lz = 0;
		int mz = 0;

		if (selectx1[getid] >= selectx2[getid]) {
			mx = selectx1[getid];
			lx = selectx2[getid];
		} else {
			mx = selectx2[getid];
			lx = selectx1[getid];
		}

		if (selecty1[getid] >= selecty2[getid]) {
			my = selecty1[getid];
			ly = selecty2[getid];
		} else {
			my = selecty2[getid];
			ly = selecty1[getid];
		}

		if (selectz1[getid] >= selectz2[getid]) {
			mz = selectz1[getid];
			lz = selectz2[getid];
		} else {
			mz = selectz2[getid];
			lz = selectz1[getid];
		}

		Block blocktemp[] = new Block[countblock];
		// > > >

		for (int xl = lx; xl <= mx; xl++) {
			for (int yl = ly; yl <= my; yl++) {
				for (int zl = lz; zl <= mz; zl++) {
					Block blb = player.getWorld().getBlockAt(xl, yl, zl);
					if (blb == null) {
						continue;
					}

					/*
					 * if (isprotectitemid(blb.getTypeId()) == true) { continue;
					 * }
					 */

					adderB++;
					blocktemp[adderB] = blb;
				}
			}
		}

		Block blockmain[] = new Block[adderB + 1];
		int adderc = 0;

		for (adderc = 0; adderc <= adderB; adderc++) {
			blockmain[adderc] = blocktemp[adderc];
		}

		for (@SuppressWarnings("unused")
		Object obj : blocktemp) {
			obj = null;
		}
		blocktemp = null;

		return blockmain;

	} // getselectblock

	// getselectblock //" + tr.gettr("for") + "dewbuy check wa mee kee block
	public int getselectblockforbuy(int getid, Player player) {

		int countall = 0;

		int lx = 0;
		int mx = 0;
		int ly = 0;
		int my = 0;
		int lz = 0;
		int mz = 0;

		if (selectx1[getid] >= selectx2[getid]) {
			mx = selectx1[getid];
			lx = selectx2[getid];
		} else {
			mx = selectx2[getid];
			lx = selectx1[getid];
		}

		if (selecty1[getid] >= selecty2[getid]) {
			my = selecty1[getid];
			ly = selecty2[getid];
		} else {
			my = selecty2[getid];
			ly = selecty1[getid];
		}

		if (selectz1[getid] >= selectz2[getid]) {
			mz = selectz1[getid];
			lz = selectz2[getid];
		} else {
			mz = selectz2[getid];
			lz = selectz1[getid];
		}

		for (int xl = lx; xl <= mx; xl++) {
			for (int yl = ly; yl <= my; yl++) {
				for (int zl = lz; zl <= mz; zl++) {
					Block blb = player.getWorld().getBlockAt(xl, yl, zl);
					if (cando_all(blb, player, "buy") == false) {
						// dprint.r.printAll("...");
						countall = -1;
						return countall;
					} else {
						countall++;
					}

				}
			}
		}

		return countall;

	} // getselectblock

	/*
	 * public void protectrail(Block block, Player player) {
	 * 
	 * if (api_private.dewddprivate.checkpermissionareasign(block) == false) {
	 * protectrailrun(block, player); // add protect }
	 * 
	 * Block b2 = null;
	 * 
	 * for (int x = -4; x <= 4; x++) { for (int y = -4; y <= 4; y++) { for (int
	 * z = -4; z <= 4; z++) {
	 * 
	 * if (x == 0 && y == 0 && z == 0) { continue; }
	 * 
	 * b2 = block.getRelative(x, y, z);
	 * 
	 * if (b2.getTypeId() != 27 && b2.getTypeId() != 66) { continue; } if
	 * (api_private.dewddprivate.checkpermissionareasign(b2) == true) {
	 * continue; }
	 * 
	 * dprint.r.printAll(tr.gettr("running") + " " + b2.getX() + "," + b2.getY()
	 * + "," + b2.getZ()); protectrail(b2, player);
	 * dprint.r.printAll(tr.gettr("running") + " " + tr.gettr("done") + " " +
	 * b2.getX() + "," + b2.getY() + "," + b2.getZ());
	 * 
	 * } } } }
	 * 
	 * public boolean protectrailrun(Block block, Player player) {
	 * 
	 * boolean ok = false; boolean spa = false;
	 * 
	 * // searh near" + tr.gettr("for") + "space and create sign Block b2 =
	 * null; for (int x = -1; x <= 1; x++) { for (int y = -1; y <= 1; y++) { for
	 * (int z = -1; z <= 1; z++) { if (x == 0 && y == 0 && z == 0) { continue; }
	 * 
	 * b2 = block.getRelative(x, y, z);
	 * 
	 * if (b2.getTypeId() == 0) { // search near spa = false; if
	 * (b2.getRelative(0, -1, 0).getTypeId() == 0) { b2.getRelative(0, -1,
	 * 0).setTypeId(5); spa = true; } else { spa = true; }
	 * 
	 * if (spa == false) { continue; }
	 * 
	 * dprint.r.printAll(tr.gettr("found_space") + b2.getX() + "," + b2.getY() +
	 * "," + b2.getY()); b2.setTypeId(63); Sign sign = (Sign) b2.getState();
	 * sign.setLine(0, "[dewdd]"); sign.setLine(1, player.getName());
	 * sign.update(true); ok = true; dprint.r.printAll( tr.gettr("created_sign")
	 * + " at : " + b2.getX() + "," + b2.getY() + "," + b2.getY()); break; }
	 * 
	 * } if (ok == true) { break; }
	 * 
	 * } if (ok == true) { break; } }
	 * 
	 * if (ok == true) return true;
	 * 
	 * boolean ok2 = false; if (ok == false) { for (int x = -1; x <= 1; x++) {
	 * for (int y = -1; y <= 1; y++) { for (int z = -1; z <= 1; z++) { if (x ==
	 * 0 && y == 0 && z == 0) { continue; } b2 = block.getRelative(x, y, z);
	 * 
	 * ok2 = protectrailrun(b2, player); if (ok2 == true) return true; } } } }
	 * 
	 * return false; }
	 */

	public boolean isNumeric(String str) {
		for (char c : str.toCharArray())
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	public boolean isprotectitemid(Material importid) {
		switch (importid) {
		case CHEST: // chest
		case SPONGE: // sponge
		case DISPENSER: // dispenser
		case FURNACE: // fu
		case BURNING_FURNACE: // fu
		case SIGN_POST: // sign post
		case WALL_SIGN: // wall sign
		case TRAPPED_CHEST: // lock chest
		case ANVIL: // anvil
		case BEACON: // beacon
		case ENDER_CHEST: // enderchest
		case ENCHANTMENT_TABLE: // enchant
		case MOB_SPAWNER: // monster spawner
		case BREWING_STAND: // brewing
		case HOPPER: // hopper
		case DROPPER:

			return true;
		default:
			return false;
		}
	}

	// savesignfile

	public boolean isunsortid(ItemStack impo) {

		if (impo.getType().getMaxDurability() > 0)
			return true;

		if (impo.getEnchantments().size() > 0)
			return true;

		switch (impo.getTypeId()) {
		case 403: // enchant book
		case 373: // potion
		case 401: // rocket
		case 402: // firework star
		case 397: // mod head
		case 395: // empty map
		case 144: // head
		case 387: // writting book
		case 386: // book and quill
		case 351:// ink

			return true;
		default:
			return false;
		}
	}

	public void linkurl(Player player, String url) {
		if (url.endsWith("?fb") == true || url.endsWith("?facebook") == true) {
			dprint.r.printA("ptdew&dewdd : my facebook > https://www.facebook.com/dewddminecraft");
		}

		if (url.endsWith("?e-mail") == true || url.endsWith("?mail") == true) {
			dprint.r.printA("ptdew&dewdd : my e-mail > dewtx29@gmail.com");
		}

		if (url.endsWith("?youtube") == true || url.endsWith("?video") == true) {
			dprint.r.printA("ptdew&dewdd : my youtube > http://www.youtube.com/ptdew");
			dprint.r.printA("ptdew&dewdd : my youtube 2 > http://www.youtube.com/ptdew2");
		}

		if (url.endsWith("?plugin") == true || url.endsWith("?pl") == true) {
			dprint.r.printA(
					"ptdew&dewdd : my plugin > http://www.youtube.com/playlist?list=PLlM9Jjda8OZeMEuUtVxyXu2XF62rqzt2j");
		}

	}

	public Block protochest(Block block, int d4, String sorttype) {
		Block temp = null;
		Sign sign2 = null;
		Block typebox = null;

		// search sign

		for (int gx2 = 0 - d4; gx2 <= 0 + d4; gx2++) {
			for (int gy2 = 0 - d4; gy2 <= 0 + d4; gy2++) {
				for (int gz2 = 0 - d4; gz2 <= 0 + d4; gz2++) {
					temp = block.getRelative(gx2, gy2, gz2);
					if (temp.getTypeId() != 63 && temp.getTypeId() != 68) {
						continue;
					}

					sign2 = (Sign) temp.getState();
					if (sign2.getLine(0).equalsIgnoreCase("dewsorttype")) {
						sign2.setLine(0, "[dewsorttype]");
						sign2.update(true);
					}

					if (sign2.getLine(0).equalsIgnoreCase("[dewsorttype]")) {
						if (sign2.getLine(1).equalsIgnoreCase(sorttype)) {
							// found proto type

							// searh box of proto sign
							typebox = chestnearsign(temp);
							if (typebox != null) {
								return typebox;
							}

						}
					}
				} // search sign
			}
		}

		return typebox;

	}

	// savesignfiledefrag

	public int randomnumberint() {
		int randomInt = randomG.nextInt(50);
		randomInt += 1;
		return randomInt;
	}

	public void randomplaynote(Location player) {
		randomplaynote_c arr = new randomplaynote_c(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, arr);
	}

	public void saveworld() {
		for (World world : Bukkit.getWorlds()) {
			world.save();
			for (Player pla : world.getPlayers()) {
				pla.saveData();
			}
		}
	}

	public void searchRecursiveBlock(ArrayList<Block> blockList, Block curBlock, Material searchMaterial,
			Byte searchData) {
		int search = 3;

		/*
		 * dprint.r.printAll(curBlock.getX() + "," + curBlock.getY() + "," +
		 * curBlock.getZ() + " = " + curBlock.getType().name() + ":" +
		 * curBlock.getData());
		 */

		if (curBlock.getType() == searchMaterial)
			if (curBlock.getData() == searchData || searchData == -29) {

				boolean found = false;
				for (int i = 0; i < blockList.size(); i++) {
					Block bo = blockList.get(i);
					if (bo.getX() == curBlock.getX() && bo.getY() == curBlock.getY() && bo.getZ() == curBlock.getZ())

						found = true;
					break;

				}

				if (found == false) {

					blockList.add(curBlock);
					// dprint.r.printAll("gift " + curBlock.getX() + "," +
					// curBlock.getY() + "," + curBlock.getZ());
				}
			}

		Block tm;
		for (int x = -search; x <= search; x++)
			for (int y = -search; y <= search; y++)
				for (int z = -search; z <= search; z++) {

					tm = curBlock.getRelative(x, y, z);
					if (tm == null) {
						continue;
					}

					if (tm.getType() == searchMaterial)
						if (tm.getData() == searchData || searchData == -29) {

							// check Block
							boolean sea = false;
							for (int i = 0; i < blockList.size(); i++) {
								Block te = blockList.get(i);
								if (te.getX() == tm.getX() && te.getY() == tm.getY() && te.getZ() == tm.getZ()) {
									sea = true;
									break;
								}
							}

							if (sea == false) {

								searchRecursiveBlock(blockList, tm, searchMaterial, searchData);

								continue;
							}

						}

				}

	}

	public void showpos(Player player, int idc) {
		player.sendMessage("cur select " + "(" + selectx1[idc] + "," + selecty1[idc] + "," + selectz1[idc] + ") to ( "
				+ selectx2[idc] + "," + selecty2[idc] + "," + selectz2[idc] + ")");
	}

	public void superdestroy(Block block, Player player, int dleft, int typeid, byte typedata) {
		if (player.getItemInHand().getDurability() < player.getItemInHand().getType().getMaxDurability()) {

			if (block.getTypeId() != typeid || block.getData() != typedata)
				return;

			if (cando_all(block, player, "delete") == false)
				return;

			player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
			block.breakNaturally(player.getItemInHand());

			Block block2 = null;

			for (int gx = -1; gx <= 1; gx++) {
				for (int gy = -1; gy <= 1; gy++) {
					for (int gz = -1; gz <= 1; gz++) {
						block2 = block.getRelative(gx, gy, gz);

						if (block2.getTypeId() == 0) {
							continue;
						}
						dleft--;
						if (dleft == 0)
							return;
						superdestroy(block2, player, dleft, typeid, typedata);
					}
				}
			}

		}
	}

} // dew minecraft