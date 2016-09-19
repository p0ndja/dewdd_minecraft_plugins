/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddflower;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import api_skyblock.api_skyblock;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class dewset extends dewset_interface {

	class autosortchest2_class implements Runnable {
		private Block	block;
		private Player	player;

		public autosortchest2_class(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		@Override
		public void run() {

			if (block.getTypeId() != Material.CHEST.getId()
					&& block.getTypeId() != Material.TRAPPED_CHEST.getId()) {

				player.sendMessage(dprint.r.color(tr
						.gettr("auto_sort_chest_2_is_not_chest")));
				return;
			}

			Chest chest = (Chest) block.getState();
			int leng = chest.getInventory().getSize();

			int sid[] = new int[leng];
			ItemStack sdata[] = new ItemStack[leng];
			int samount[] = new int[leng];

			// clear
			int l1 = 0;
			for (l1 = 0; l1 < leng; l1++) {
				sid[l1] = -1;
				samount[l1] = 0;
			}

			// loop all
			for (ItemStack it : chest.getInventory().getContents()) {

				if (it == null) {
					continue;
				}

				if (isunsortid(it) == true) {
					continue;
				}

				// add to my array
				// find

				boolean founded = false;

				for (l1 = 0; l1 < leng; l1++)
					// player.sendMessage(dprint.r.color("finding old data " +
					// l1);
					if (sid[l1] == it.getTypeId())
					// player.sendMessage(dprint.r.color("ax " + sid[l1]);
						if (sdata[l1].getData().getData() == it.getData()
								.getData()) {

							founded = true;

							// player.sendMessage(dprint.r.color("s=" + l1 +
							// ",id:" + sid[l1] +
							// ",data:"
							// + sdata[l1] + ",amount" + samount[l1]);
						samount[l1] += it.getAmount();
						break;
					}

				// if not found
				if (founded == false) {
					// player.sendMessage(dprint.r.color("can't find old slot");

					founded = false;
					for (l1 = 0; l1 < leng; l1++)
						// find empty
						if (sid[l1] == -1) {
							sid[l1] = it.getTypeId();
							sdata[l1] = it;
							samount[l1] = it.getAmount();
							founded = true;
							break;
						}

					if (founded = false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("error_auto_sort_chest2_can't_find_empty_slot")));
						return;
					}

				}

			} // loop all itemstack

			// now add back : O

			// clear inventory chest
			for (int itx = 0; itx < chest.getInventory().getSize(); itx++) {

				if (chest.getInventory().getItem(itx) == null) {
					continue;
				}

				if (isunsortid(chest.getInventory().getItem(itx)) == true) {
					continue;
				}

				chest.getInventory().clear(itx);
			}
			// chest.getInventory().clear();

			// now add back : (

			for (l1 = 0; l1 < leng; l1++) {
				if (sid[l1] == -1) {
					continue;
				}

				// add until empty slot
				while (samount[l1] > 0)
					if (samount[l1] >= 64) {
						// player.sendMessage(dprint.r.color("adding > " +
						// sid[l1] +
						// tr.gettr("amount") + "= " +
						// samount[l1]);
						sdata[l1].setAmount(64);
						chest.getInventory().addItem(sdata[l1]);
						samount[l1] -= 64;
					}
					else {
						// player.sendMessage(dprint.r.color("adding > " +
						// sid[l1] +
						// tr.gettr("amount") + " = " +
						// samount[l1]);

						sdata[l1].setAmount(samount[l1]);
						chest.getInventory().addItem(sdata[l1]);

						samount[l1] -= samount[l1];
					}

				// player.sendMessage(dprint.r.color("x data " +
				// chest.getInventory().getItem(0).getData().getData());

			}

			for (@SuppressWarnings("unused")
			Object obj : sid) {
				obj = null;
			}
			sid = null;
			for (@SuppressWarnings("unused")
			Object obj : sdata) {
				obj = null;
			}
			sdata = null;
			for (@SuppressWarnings("unused")
			Object obj : samount) {
				obj = null;
			}
			samount = null;

		}
	}

	class chestabsorb_c implements Runnable {

		public chestabsorb_c() {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			int d4 = 20;
			Block block = null;
			Block block2 = null;
			int d5 = 1;
			Chest chest = null;
			int slotp = -1;

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (api_admin.dewddadmin.is2moderator(player) == true) {
					continue;
				}
				// search nearby box and sign ... ummm yes
				block = player.getLocation().getBlock();

				/*
				 * if (checkpermissionarea(block)== false) { not protect area
				 * continue; }
				 */
				if (checkpermissionarea(block, player, "build") == true) {
					// build
					continue;
				}

				for (int gx = 0 - d4; gx <= 0 + d4; gx++) {
					for (int gy = 0 - d4; gy <= 0 + d4; gy++) {
						for (int gz = 0 - d4; gz <= 0 + d4; gz++) {
							// first search sign
							block = player.getLocation().getBlock()
									.getRelative(gx, gy, gz);
							if (block == null) {
								continue;
							}

							if (block.getTypeId() != 63
									&& block.getTypeId() != 68) {
								continue;
							}

							Sign sign = (Sign) block.getState();
							if (sign.getLine(0).equalsIgnoreCase("[dewtobox]") == true) {
								// player.sendMessage(dprint.r.color("found dewtobox sign : "
								// +
								// block.getLocation().getBlockX() + ","
								// + block.getLocation().getBlockY() + "," +
								// block.getLocation().getBlockZ());

								int intb = Integer.parseInt(sign.getLine(1));
								if (intb == 0) {
									continue;
								}

								// after found sign so find box

								// box
								for (int ax = 0 - d5; ax <= 0 + d5; ax++) {
									for (int ay = 0 - d5; ay <= 0 + d5; ay++) {
										for (int az = 0 - d5; az <= 0 + d5; az++) {
											block2 = block.getRelative(ax, ay,
													az);
											if (block2 == null) {
												continue;
											}

											if (block2.getTypeId() != 54) {
												continue;
											}

											// player.sendMessage(dprint.r.color("found dewtobox chest : "
											// +
											// block2.getLocation().getBlockX()
											// +
											// ","
											// +
											// block2.getLocation().getBlockY()
											// +
											// "," +
											// block2.getLocation().getBlockZ());

											slotp = player.getInventory()
													.first(intb);
											if (slotp == -1) {
												continue;
											}

											chest = (Chest) block2.getState();

											int chestslot = -1;
											chestslot = chest.getInventory()
													.firstEmpty();
											if (chestslot == -1) {
												continue;
											}

											// ready to move
											chest.getInventory().addItem(
													player.getInventory()
															.getItem(slotp));

											player.getInventory().clear(slotp);

											player.sendMessage(dprint.r
													.color("[dewtobox] "
															+ tr.gettr("moved")
															+ intb));

											// added

											// if true
											// check is empty
											// check item of player

										}
									}
								}

							} // dew to box

						} // loop
					}
				}

			} // player
		}
	}

	class chestabsorb_c2 implements Runnable {

		public chestabsorb_c2() {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 1);
		}

		@Override
		public void run() {

			long nn = System.currentTimeMillis();

			if (nn - lastsort2 < 100) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 10);
				return;
			}

			lastsort2 = nn;

			int d4 = 20;
			Block block = null;

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (api_admin.dewddadmin.is2moderator(player) == true) {
					continue;
				}
				// search nearby box and sign ... ummm yes
				block = player.getLocation().getBlock();

				/*
				 * if (checkpermissionarea(block)== false) { not protect area
				 * continue; }
				 */
				if (checkpermissionarea(block, player, "build") == true) {
					// build
					continue;
				}

				// player.sendMessage(dprint.r.color("searching... dewsortbox sign");

				// search any sign near player
				for (int gx = 0 - d4; gx <= 0 + d4; gx++) {
					for (int gy = 0 - d4; gy <= 0 + d4; gy++) {
						for (int gz = 0 - d4; gz <= 0 + d4; gz++) {
							lastsort2 = nn;

							// first search sign
							block = player.getLocation().getBlock()
									.getRelative(gx, gy, gz);

							if (block.getTypeId() != 63
									&& block.getTypeId() != 68) {
								continue;
							}

							// dewsortbox
							// dewsorttype

							Sign sign = (Sign) block.getState();
							if (sign.getLine(0)
									.equalsIgnoreCase("[dewsortbox]") == true) {

								/*
								 * player.sendMessage(dprint.r.color(
								 * "cur found dewsortbox sign at " +
								 * block.getX() + "," + block.getY() + "," +
								 * block.getZ());
								 */

								String sorttype = sign.getLine(1);
								if (sorttype.equalsIgnoreCase("")) {
									player.sendMessage(dprint.r.color(tr
											.gettr("sorttype_name_must_not_null")));
									continue;
								}

								// got sign type
								// search current chest
								Block curchest = chestnearsign(block);

								if (curchest == null) {
									player.sendMessage(dprint.r
											.color("curchest == null"));

									continue;

								}

								Block curprochest = protochest(block, d4,
										sorttype);
								if (curprochest == null) {
									player.sendMessage(dprint.r
											.color("curprochest == null"));

									continue;

								}

								// player.sendMessage(dprint.r.color("opening curchest..."+
								// curchest.getTypeId());
								Chest curchest1 = (Chest) curchest.getState();
								// player.sendMessage(dprint.r.color("opening curchest done");

								// player.sendMessage(dprint.r.color("opening curprochest..."+
								// curprochest.getTypeId());

								Chest curchestin = (Chest) curprochest
										.getState();
								// player.sendMessage(dprint.r.color("opening curprochest done");

								// player.sendMessage(dprint.r.color("opened both chest");
								// after got cur chest
								// search another dewsortbox for swap
								// *********************
								Block temp = null;
								for (int jx = 0 - d4; jx <= 0 + d4; jx++) {
									for (int jy = 0 - d4; jy <= 0 + d4; jy++) {
										for (int jz = 0 - d4; jz <= 0 + d4; jz++) {
											if (jx == 0 && jy == 0 && jz == 0) {
												continue;
											}
											// first search sign
											temp = block.getLocation()
													.getBlock()
													.getRelative(jx, jy, jz);

											if (temp.getTypeId() != 63
													&& temp.getTypeId() != 68) {
												continue;
											}

											// dewsortbox
											// dewsorttype

											Sign js = (Sign) temp.getState();
											if (js.getLine(0).equalsIgnoreCase(
													"[dewsortbox]") == true) {

												/*
												 * player.sendMessage(dprint.r.color
												 * ("swap found dewsortbox at "
												 * + temp.getX() + "," +
												 * temp.getY() + "," +
												 * temp.getZ());
												 */

												String jsorttype = js
														.getLine(1);
												if (jsorttype
														.equalsIgnoreCase("")) {
													// player.sendMessage(dprint.r.color("swap_sorttype_name_must_not_null");
													continue;
												}

												// got sign type
												// search current chest
												Block swapchest = chestnearsign(temp);
												if (swapchest == null) {
													// player.sendMessage(dprint.r.color("swapchest  == null");
													continue;
												}

												if (swapchest
														.getLocation()
														.distance(
																curchest.getLocation()) <= 1) {
													continue;
												}

												Block swapprochest = protochest(
														temp, d4, jsorttype);
												if (swapprochest == null) {
													// player.sendMessage(dprint.r.color("swapprochest == null");
													continue;
												}

												// player.sendMessage(dprint.r.color("opening swapprochest...");

												Chest swapchestin = (Chest) swapprochest
														.getState();
												// player.sendMessage(dprint.r.color("opening swapchest...");

												Chest swapchest1 = (Chest) swapchest
														.getState();

												// player.sendMessage(dprint.r.color("opened both chest swap");

												for (int ikn = 0; ikn < curchest1
														.getInventory()
														.getSize(); ikn++) {
													ItemStack i1 = curchest1
															.getInventory()
															.getItem(ikn);

													if (i1 == null) {
														continue;
													}

													// search 1 is not in
													// 1prototype
													int i1proslot = curchestin
															.getInventory()
															.first(i1.getType());
													if (i1proslot > -1) {
														// player.sendMessage(dprint.r.color("i1proslot > -1");
														continue;
													}

													// if not found it's mean
													// wrong item in cur chest

													// check it that item in
													// second prototype

													if (swapchestin
															.getInventory()
															.first(i1.getType()) == -1) {
														// player.sendMessage(dprint.r.color("swapchestin can't found i1 item");
														continue;
													}

													// time to swap item
													// search free item on
													// second

													int freeslot = swapchest1
															.getInventory()
															.firstEmpty();
													if (freeslot == -1) {
														// player.sendMessage(dprint.r.color("free slot of swapchest 1 is -1");
														continue;
													}

													// time to move
													// player.sendMessage(dprint.r.color("moviing item");
													swapchest1.getInventory()
															.setItem(freeslot,
																	i1);
													curchest1
															.getInventory()
															.setItem(
																	ikn,
																	new ItemStack(
																			0));

													player.sendMessage(dprint.r
															.color("swaped item "
																	+ i1.getType()
																			.name()
																	+ " "
																	+ i1.getAmount())
															+ " from "
															+ curchest.getX()
															+ ","
															+ curchest.getY()
															+ ","
															+ curchest.getZ()
															+ "["
															+ sorttype
															+ "]"
															+ " to "
															+ swapchest.getX()
															+ ","
															+ swapchest.getY()
															+ ","
															+ swapchest.getZ()
															+ "["
															+ jsorttype
															+ "]");

													lastsort2 = nn;

													new chestabsorb_c2();
													return;

												}
												// how to swap
												// loop all of item of source

											}
										} // search another chest
									}
								}

								// **********************
								// search another chest block for swap item

							} // dew to box

						} // searh any sign near player
					}
				}

			} // player
		}
	}

	class createmonster_c implements Runnable {
		private EntityType	EntityTypeGot;
		private Player		player;

		public createmonster_c(Player player, EntityType EntityTypeGot,
				int count) {
			this.player = player;
			this.EntityTypeGot = EntityTypeGot;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			Location loca = player.getLocation();
			int x = (int) loca.getX();
			int y = (int) loca.getY();
			int z = (int) loca.getZ();
			for (int xx = 0; xx <= player.getItemInHand().getAmount(); xx++) {

				int lx = x;
				int ly = y;
				int lz = z;
				int d4 = 4;

				boolean an = true;
				z++;
				x++;

				while (an == true) {
					an = true;

					for (lx = x - d4; lx <= x + d4; lx++) {
						for (ly = y - d4; ly <= y + d4; ly++) {
							for (lz = z - d4; lz <= z + d4; lz++) {
								Block block2 = player.getWorld().getBlockAt(lx,
										ly, lz);
								if (block2.getTypeId() == 0) {
									an = false;
								}
								else {
									an = true;
								}
							}
						}
					}

					if (an == true) {
						y++;
						if (y > 253 || y < 1) return;
					}

				}

				loca = player.getLocation();
				loca.setX(x);
				loca.setY(y);
				loca.setZ(z);

				player.getWorld().spawnCreature(loca, EntityTypeGot);

			} // loop monster all
		}
	}

	class cutseemblock_c implements Runnable {
		private Block	block123;
		private int		countja;
		private Player	player123;

		public cutseemblock_c(Block block123, Player player123, int countja) {
			this.block123 = block123;
			this.player123 = player123;
			this.countja = countja;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			try {
				int gx = 0;
				int gy = 0;
				int gz = 0;
				int g4 = 1;

				if (api_admin.dewddadmin.is2admin(player123) == false) return;

				if (checkpermissionarea(block123) == true) return;

				int seemblockid = block123.getTypeId();

				switch (player123.getItemInHand().getTypeId()) {
				case 7:
					// player123.sendMessage(dprint.r.color("ptdew&dewdd :" +
					// countja +
					// " 7 cut seem block (" + block123.getX() + "," +
					// block123.getY() + "," + block123.getZ() + ")");
					block123.setTypeId(0);
					countja++;
					if (countja > 50) return;
					break;
				default:
					return;
				}

				World world = block123.getWorld();

				for (gx = block123.getX() - g4; gx <= block123.getX() + g4; gx++) {
					for (gy = block123.getY() - g4; gy <= block123.getY() + g4; gy++) {
						for (gz = block123.getZ() - g4; gz <= block123.getZ()
								+ g4; gz++) {
							Block blockt1 = world.getBlockAt(gx, gy, gz);

							if (blockt1.getTypeId() == seemblockid) {
								if (checkpermissionarea(block123) == true) {
									continue;
								}

								countja++;
								if (countja > 50) return;
								cutseemblock(blockt1, player123, countja);

							}

						}
					}
				}

				return;
			}
			catch (Exception e) {// Catch exception if any
				// System.err.println("Error: " + e.getMessage() );
			}
		}

	}

	// cutwoodsub
	class cutwoodsubc implements Runnable {
		private Block	block123	= null;
		private boolean	isfirsttime;
		private Player	player123	= null;

		public cutwoodsubc(Block block123, Player player123, boolean isfirsttime) {
			this.player123 = player123;
			this.block123 = block123;
			this.isfirsttime = isfirsttime;
		}

		@Override
		public void run() {

			int idc = getfreeselect(player123);
			if (dewaxe[idc] == false) return;

			switch (player123.getItemInHand().getTypeId()) {
			case 279:
			case 258:
			case 271:
			case 275:
			case 286:
				break;
			default:
				return;
			}

			int gx = 0;
			int gy = 0;
			int gz = 0;
			int g4 = 2;

			if (block123.getTypeId() == 0 && isfirsttime == false) return;

			switch (block123.getTypeId()) {
			case 103:
			case 86:
			case 17:

				for (int bx = -3; bx <= 3; bx++) {
					for (int by = -3; by <= 3; by++) {
						for (int bz = -3; bz <= 3; bz++) {
							Block block2 = block123.getRelative(bx, by, bz);
							if (block2.getTypeId() == 18) {
								block2.breakNaturally();
							}

						}
					}
				}

				break;
			case 83:
				if (block123.getRelative(0, -1, 0).getTypeId() != 83) return;
				break;
			case 59:
				if (block123.getData() != 7) return;
				break;
			case 141:
				if (block123.getData() != 7) return;
				break;
			case 142:
				if (block123.getData() != 7) return;
				break;

			default:
				if (isfirsttime == false) return;
			}

			if (checkpermissionarea(block123, player123, "delete") == true)
				return;

			switch (player123.getItemInHand().getTypeId()) {
			case 279:
			case 258:
			case 271:
			case 275:
			case 286:

				// player123.sendMessage(dprint.r.color("ptdew&dewdd : "+
				// block123.getTypeId() +
				// " cut wood (" + block123.getX() + "," + block123.getY() +
				// ","
				// + block123.getZ() + ")");

				switch (block123.getRelative(0, -1, 0).getTypeId()) {
				case 2:
				case 3:
					if (block123.getTypeId() == 17) {
						int datate = block123.getData();
						if (decreseitem1(player123, 6, datate, true) == true) {
							block123.breakNaturally(player123.getItemInHand());
							block123.setTypeId(6);
							block123.setData((byte) datate);
						}
						else {
							block123.breakNaturally(player123.getItemInHand());
							block123.setTypeId(6);
							block123.setData((byte) datate);

						}
					}
					else {
						block123.breakNaturally(player123.getItemInHand());
					}
					break;
				default:
					block123.breakNaturally(player123.getItemInHand());
					break;
				}

				if (player123.getItemInHand().getDurability() >= player123
						.getItemInHand().getType().getMaxDurability()) return;
				player123.getItemInHand()
						.setDurability(
								(short) (player123.getItemInHand()
										.getDurability() + 1));

				break;
			default:
				return;
			}

			World world = block123.getWorld();

			for (gx = block123.getX() - g4; gx <= block123.getX() + g4; gx++) {
				for (gz = block123.getZ() - g4; gz <= block123.getZ() + g4; gz++) {
					for (gy = block123.getY() + g4; gy >= block123.getY() - g4; gy--) {
						Block blockt1 = world.getBlockAt(gx, gy, gz);

						if (block123.getTypeId() == 17) {
							switch (block123.getRelative(0, -1, 0).getTypeId()) {
							case 17:
							case 2:
							case 3:
							case 0:
							case 18:
								break;
							default:
								continue;
							}
						}

						cutwoodsub(blockt1, player123, false);

					}
				}
			}

			return;

		}
	}

	class dewa_mom implements Runnable {
		public int		amount;
		public Player	player	= null;

		@Override
		public void run() {
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewa "
						+ tr.gettr("please_set_block_1")));

				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewa "
						+ tr.gettr("please_set_block_2")));

				return;
			}

			// find position

			if (amount == 0) {
				if (player.getItemInHand() == null) {
					player.sendMessage(dprint.r.color("ptdew&dewdd : "
							+ tr.gettr("need_item_in_hand_=_amount")));

					return;
				}
				amount = player.getItemInHand().getAmount();

			}

			player.sendMessage(dprint.r.color("dewa amount = " + amount));

			Block block = player.getLocation().getBlock();

			if (dewddtps.tps.getTPS() < 17) {
				dprint.r.printAll("ptdew&dewdd : "
						+ tr.gettr("tps_below_than_17") + dewddtps.tps.getTPS());

				return;
			}

			if (selectblock[getid] == null) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewa "
						+ tr.gettr("dewa_diamond_sword_null")));
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
								|| blockd.getLocation().getBlockX() == selectblock[getid]
										.getLocation().getBlockX()
								&& blockd.getLocation().getBlockY() == selectblock[getid]
										.getLocation().getBlockY()
								&& blockd.getLocation().getBlockZ() == selectblock[getid]
										.getLocation().getBlockZ()) { // diamond
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
				player.sendMessage(dprint.r.color(tr
						.gettr("dewa_diamondsword_null_mean_upper")));

				blockdy = 1;
			}

			player.sendMessage(dprint.r.color(tr.gettr("dewa_diamond_axis_=")
					+ blockdx + "," + blockdy + "," + blockdz));
			// after know axis and selected block ... so start copy
			// " + tr.gettr("for") + "amount
			// " + tr.gettr("for") + "all block ... to copy to next axis
			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewa "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

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
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewa_thread aer = new dewa_thread();
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

			aer.amountloop = 1;
			aer.amount = amount;

			aer.getid = getid;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}

	}

	class dewa_thread implements Runnable {
		public int		amount;
		public int		amount1;

		public int		amountloop;
		public int		blockdx;
		public int		blockdy;
		public int		blockdz;

		public int		getid;

		public int		lx;

		public int		ly;
		public int		lz;
		public int		mx;
		public int		my;
		public int		mz;
		public Player	player	= null;
		public int		xlx;
		public int		ylx;
		public int		zlx;

		@Override
		public void run() {

			boolean arxx = !player.hasPermission(pmaininfinite);

			Block blockd = null;
			// amountloop = start with 1
			Block blb = null;

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

				player.sendMessage(dprint.r.color("blockdx = " + ndx));
				player.sendMessage(dprint.r.color("blockdy = " + ndy));
				player.sendMessage(dprint.r.color("blockdz = " + ndz));

				// dprint.r.printC("blockdx = " + ndx);
				// dprint.r.printC("blockdy = " + ndy);
				// dprint.r.printC("blockdz = " + ndz);

				while (amount1 <= 2) { // amount1 // amount1 = start with 1
					// dprint.r.printAll("amount1 = " + amount1);

					while (xlx <= mx) {

						while (ylx <= my) {
							while (zlx <= mz) {

								// dprint.r.printAll (xlx + "," + ylx + "," +
								// zlx + " mx " + mx + "," + my + "," + mz);

								endtime = System.currentTimeMillis();
								if (endtime - starttime > runtime
										|| dewddtps.tps.getTPS() <= 18) {

									dewa_thread xgn2 = new dewa_thread();

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

									dprint.r.printC("dewa  "
											+ tr.gettr("recall") + " " + xlx
											+ " , " + ylx + " , " + zlx);
									dprint.r.printC("low " + lx + " , " + ly
											+ " , " + lz + " high " + mx + ","
											+ my + "," + mz);

									Bukkit.getScheduler()
											.scheduleSyncDelayedTask(ac, xgn2,
													50L);

									return;
								}

								blb = player.getWorld().getBlockAt(xlx, ylx,
										zlx);

								if (blb.getY() + ndy > 253
										|| blb.getY() + ndy < 1) {
									zlx++;
									// dprint.r.printAll("out of range y");
									continue;
								}

								blockd = blb.getWorld().getBlockAt(
										blb.getX() + ndx, blb.getY() + ndy,
										blb.getZ() + ndz);
								/*
								 * if (blockd.getTypeId() == 0) { continue; }
								 */

								if (amount1 == 1) { // if first round ... only
													// block
									if (blb.getType().isBlock() == false) {
										zlx++;
										// dprint.r.printAll("first is not a block");
										continue;
									}
									// blockd.setTypeId(0);

									if (arxx)
										if (blockd.getTypeId() != blb
												.getTypeId()
												|| blockd.getData() != blb
														.getData())
											if (decreseitem1(player,
													blb.getTypeId(),
													blb.getData(), false) == false
													&& blb.getTypeId() != 0) {
												player.sendMessage(dprint.r.color("ptdew&dewdd : "
														+ tr.gettr("don't_have_enough_item")));
												player.sendMessage(dprint.r.color("block > "
														+ blb.getTypeId()
														+ ","
														+ blb.getData()));
												return;
											}
								}
								else { // if secord round ... only not block
										// block
									if (blb.getType().isBlock() == true) {
										zlx++;
										// dprint.r.printAll("second time is a block");
										continue;
									}
									// blockd.setTypeId(0);

									if (arxx)
										if (blockd.getTypeId() != blb
												.getTypeId()
												|| blockd.getData() != blb
														.getData())
											if (decreseitem1(player,
													blb.getTypeId(),
													blb.getData(), false) == false
													&& blb.getTypeId() != 0) {
												player.sendMessage(dprint.r.color("ptdew&dewdd : "
														+ tr.gettr("don't_have_enough_item")));
												player.sendMessage(dprint.r.color("block > "
														+ blb.getTypeId()
														+ ","
														+ blb.getData()));
												return;
											}
								}

								blockd = blb.getWorld().getBlockAt(
										blb.getX() + ndx, blb.getY() + ndy,
										blb.getZ() + ndz);
								if (checkpermissionarea(blockd, player,
										"dewset") == true) return;

								blockd.setTypeIdAndData(blb.getTypeId(),
										blb.getData(), false);

								// dprint.r.printAll ("comple  " + xlx + "," +
								// ylx + "," + zlx + " mx " + mx + "," + my +
								// "," + mz);

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

			player.sendMessage(dprint.r.color("ptdew&dewdd : dewA "
					+ tr.gettr(tr.gettr("done"))));
			dprint.r.printAll("ptdew&dewdd : " + player.getName() + " > dewa "
					+ tr.gettr(tr.gettr("done")));
		}
	}

	class dewbreak_c implements Runnable {
		private byte	handdata;
		private int		handid;
		private Player	player;

		public dewbreak_c(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewbreak "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewbreak "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewbreak " + handid + ":"
					+ handdata);

			if (player.hasPermission(pmaindelete) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("don't_have_permission_for_access_delete_command")));
				return;
			}

			for (Block blb : getselectblock(getid, player)) {
				if (blb.getTypeId() != handid || blb.getData() != handdata) {
					continue;
				}
				if (checkpermissionarea(blb, player, "dewset") == true) return;

				/*
				 * if (PreciousStones.API().canBreak(player,
				 * blb.getLocation())== false) {
				 * player.sendMessage(dprint.r.color
				 * ("ptdew&dewdd :Can't dewdelete here (" + blb.getX() + "," +
				 * blb.getY() + "," + blb.getZ() + ")"); continue; }
				 */

				/*
				 * if (PreciousStones.API().isFieldProtectingArea(FieldFlag.
				 * PREVENT_PLACE , blb.getLocation())==false) {
				 * player.sendMessage(dprint.r.color(
				 * "ptdew&dewdd :Can't dewdelete if not your home zome (" +
				 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
				 * continue; }
				 */

				blb.breakNaturally();

			}

			dprint.r.printAll("ptdew&dewdd : dewbreak " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewbuy_class implements Runnable {

		public boolean	isok	= false;
		private Player	player;

		public dewbuy_class(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (api_admin.dewddadmin.is2moderator(player) == true) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("staff_can't_dewbuy")));
				isok = false;
				return;
			}

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewbuy "
						+ tr.gettr("please_set_block_1")));
				isok = false;
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewbuy "
						+ tr.gettr("please_set_block_2")));

				isok = false;
				return;
			}

			int countblock = -1;

			if (player.hasPermission(pmaindewbuynotcount) == false) {
				countblock = getselectblockforbuy(getid, player);
				if (countblock < 27) {
					player.sendMessage(dprint.r.color("ptdew&dewdd : "
							+ tr.gettr("dewbuy_smallest_block_is_27")));
					isok = false;
					return;
				}

			}
			if (checkpermissionarea(player.getLocation().getBlock(), true) >= 0
					&& player.hasPermission(pmaindewbuyreplace) == false) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("can't_dewbuy_replace_another_home_zone")));
				isok = false;
				return;
			}

			player.sendMessage(dprint.r.color("ptdew&dewdd : Block 1 = ("
					+ selectx1[getid] + "," + selecty1[getid] + ","
					+ selectz1[getid] + ") to (" + selectx2[getid] + ","
					+ selecty2[getid] + "," + selectz2[getid] + ") = "
					+ countblock));

			if (countblock == -1) {
				countblock = 1;
				player.sendMessage(dprint.r
						.color("countblock == -1 , but admin we change it to 1"));
			}

			double buymoneyp = countblock
					* api_private.dewddprivate.buy1blockmoney;

			player.sendMessage(dprint.r.color("ptdew&dewdd :" + tr.gettr("buy")
					+ "'" + countblock + "' use money = " + buymoneyp));

			try {
				if (Economy.getMoney(player.getName()) < buymoneyp) {

					player.sendMessage(dprint.r.color("ptdew&dewdd : "
							+ tr.gettr("don't_have_enough_money") + " for"
							+ tr.gettr("buy") + "these area... = "
							+ (Economy.getMoney(player.getName()) - buymoneyp)));

					isok = false;
					return;
				}

				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("dewbuy_have_enough_money_please_wait")));

				dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
						+ tr.gettr("starting") + " dewbuy "
						+ player.getItemInHand().getTypeId() + ":"
						+ player.getItemInHand().getData());

				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ Economy.getMoney(player.getName()) + " - "
						+ buymoneyp + " = "
						+ (Economy.getMoney(player.getName()) - buymoneyp)));

				Economy.setMoney(player.getName(),
						Economy.getMoney(player.getName()) - buymoneyp);

			}
			catch (UserDoesNotExistException | NoLoanPermittedException err) {
				dprint.r.printAll("error economy while dewbuy "
						+ err.getCause().getMessage());
			}
			player.sendMessage(dprint.r
					.color("x"
							+ ","
							+ dewsignx1[getworldid(player.getWorld().getName())][getid]
							+ ","
							+ dewsigny1[getworldid(player.getWorld().getName())][getid]
							+ ","
							+ dewsignz1[getworldid(player.getWorld().getName())][getid]
							+ " to "

							+ ","
							+ dewsignx2[getworldid(player.getWorld().getName())][getid]
							+ ","
							+ dewsigny2[getworldid(player.getWorld().getName())][getid]
							+ ","
							+ dewsignz2[getworldid(player.getWorld().getName())][getid]));

			player.sendMessage(dprint.r.color(tr
					.gettr("dewbuy_before_add_sign_world")
					+ dewworldlistmax
					+ tr.gettr("sign_max")
					+ (dewsignmax[getworldid(player.getWorld().getName())] - 1)));

			adderarraysignfile(getworldid(player.getWorld().getName()));

			player.sendMessage(dprint.r.color(tr
					.gettr("dewbuy_after_add_sign_world")
					+ dewworldlistmax
					+ tr.gettr("sign_max")
					+ (dewsignmax[getworldid(player.getWorld().getName())] - 1)));

			dewsignx1[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = selectx1[getid];
			dewsigny1[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = selecty1[getid];
			dewsignz1[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = selectz1[getid];

			dewsignx2[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = selectx2[getid];
			dewsigny2[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = selecty2[getid];
			dewsignz2[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = selectz2[getid];

			dewsignname[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1][0] = player.getName();

			for (int gggg = 1; gggg < dewsignnamemax; gggg++) {
				dewsignname[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
						.getWorld().getName())] - 1][gggg] = "null";
			}

			dewsignloop[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = 0;

			savesignfile(-1, getworldid(player.getWorld().getName()));
			loadsignfile();
			dprint.r.printAll("ptdew&dewdd : " + player.getName() + " "
					+ tr.gettr("buy") + tr.gettr(tr.gettr("done")));
			isok = true;
			dprint.r.printAll(tr.gettr("dewbuy_class_is_ok") + isok);
			return;
		}
	}

	class dewbuydelete_c implements Runnable {

		private Player	player;

		public dewbuydelete_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);

		}

		@Override
		public void run() {
			/*
			 * if (player.hasPermission(pmaindewbuydelete) == false) {
			 * player.sendMessage
			 * (dprint.r.color("ptdew&dewdd : only op can use dewbuydelete");
			 * return; }
			 */

			// find id home
			int xyz = checkpermissionarea(player.getLocation().getBlock(), true);
			if (xyz == -1) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("dewbuydelete_this_zone_don't_have_protection")));
				return;
			}

			if (dewsignname[getworldid(player.getWorld().getName())][xyz][0]
					.equalsIgnoreCase(player.getName()) == false
					&& player.hasPermission(pmaindewbuydelete) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("dewbuydelete_this_is_not_your_zone")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewbuydelete "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			savesignfile(xyz, getworldid(player.getWorld().getName()));
			loadsignfile();

			dprint.r.printAll("ptdew&dewdd : " + player.getName()
					+ " dewbuydelete " + tr.gettr(tr.gettr("done")));
		}
	}

	class dewbuyremove_c implements Runnable {
		private Player	player;

		public dewbuyremove_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			if (player.hasPermission(pmaindewbuydelete) == false) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("you_don't_have_permission")
						+ tr.gettr("for") + pmaindewbuydelete));
				return;
			}

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewbuyremove "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewbuyremove "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			player.getItemInHand().getTypeId();
			player.getItemInHand().getData().getData();

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewbuydelete "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			int dd = 0;

			for (Block blb : getselectblock(getid, player)) {
				dd = checkpermissionarea(blb, true);
				if (dd == -1) {
					continue;
				}

				// remove

				savesignfile(dd, getworldid(player.getWorld().getName()));
				loadsignfile();

				dprint.r.printAll("removed zone id " + dd);

			}

			dprint.r.printAll("ptdew&dewdd : dewbuyremove " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewbuyzone_c implements Runnable {
		private Block	block2;
		private Player	player;

		public dewbuyzone_c(Player player, Block block2) {
			this.player = player;
			this.block2 = block2;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			if (api_admin.dewddadmin.is2moderator(player) == true) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("staff_can't") + tr.gettr("buy") + "zone"));
				return;
			}

			int homeid = checkpermissionarea(block2, true);
			if (homeid == -1) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("this_area_don't_have_protect")));
				return;
			}

			// String abab = dewsignname[homeid][18] ;
			if (dewsignname[getworldid(player.getWorld().getName())][homeid][18]
					.equalsIgnoreCase(flag_sell) == true) {
				dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
						+ tr.gettr("starting") + " dewbuyzone "
						+ player.getItemInHand().getTypeId() + ":"
						+ player.getItemInHand().getData());

				int mon = Integer.parseInt(dewsignname[getworldid(player
						.getWorld().getName())][homeid][19]);
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("dewbuyzone_thiszonepriceis") + mon));

				try {
					if (Economy.getMoney(player.getName()) < mon) {
						player.sendMessage(dprint.r.color("ptdew&dewdd : "
								+ tr.gettr("don't_have_enough_money") + " for"
								+ tr.gettr("buy") + "this zone > " + mon));
						return;
					}
				}
				catch (UserDoesNotExistException e) {

					e.printStackTrace();
				}

				try {
					Economy.subtract(player.getName(), mon);
				}
				catch (UserDoesNotExistException | NoLoanPermittedException e) {

					e.printStackTrace();
				}
				for (int g = 0; g <= 19; g++) {
					dewsignname[getworldid(player.getWorld().getName())][homeid][g] = "null";
				}

				dewsignname[getworldid(player.getWorld().getName())][homeid][0] = player
						.getName();
				dprint.r.printAll("ptdew&dewdd : " + player.getName()
						+ "dewbuyzone " + tr.gettr("complete") + "...");
				savesignfile(-1, getworldid(block2.getWorld().getName()));

			}
			else {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("dewbuyzone_thiszone_not_for_sell")));
				return;
			}
		}
	}

	class dewcopy_c implements Runnable {
		private Player	player;

		public dewcopy_c(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			boolean arxx = !player.hasPermission(pmaininfinite);

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewcopy "
						+ tr.gettr("please_set_block_1")));

				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) return;

			boolean xc = false;
			boolean yc = false;
			boolean zc = false;

			// x
			if (selectx1[getid] <= selectx2[getid]) {
				if (player.getLocation().getBlockX() >= selectx1[getid]
						&& player.getLocation().getBlockX() <= selectx2[getid]) {
					xc = true;
				}
			}
			else if (player.getLocation().getBlockX() <= selectx1[getid]
					&& player.getLocation().getBlockX() >= selectx2[getid]) {
				xc = true;
			}

			// y
			if (selecty1[getid] <= selecty2[getid]) {
				if (player.getLocation().getBlockY() >= selecty1[getid]
						&& player.getLocation().getBlockY() <= selecty2[getid]) {
					yc = true;
				}
			}
			else if (player.getLocation().getBlockY() <= selecty1[getid]
					&& player.getLocation().getBlockY() >= selecty2[getid]) {
				yc = true;
			}

			// z
			if (selectz1[getid] <= selectz2[getid]) {
				if (player.getLocation().getBlockY() >= selectz1[getid]
						&& player.getLocation().getBlockY() <= selectz2[getid]) {
					zc = true;
				}
			}
			else if (player.getLocation().getBlockY() <= selectz1[getid]
					&& player.getLocation().getBlockY() >= selectz2[getid]) {
				zc = true;
			}

			if ((xc == true && yc == true && zc == true) == true) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("dewcopy_can't_run_cuz_you_stand_on_source_place")));
				return;
			}

			// Block bz = new Block();

			if (dewddtps.tps.getTPS() < 17) {
				dprint.r.printAll("ptdew&dewdd : dewcopy "
						+ tr.gettr("tps_below_than_17") + dewddtps.tps.getTPS());

				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewcopy "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			// 11 - 50
			// 66 = 11-66 = -55

			for (int amountloop = 1; amountloop <= 2; amountloop++) {
				for (Block bbd : getselectblock(getid, player)) {

					if (amountloop == 1) { // if first round ... only block
						if (bbd.getType().isBlock() == false) {
							continue;
						}
					}
					else if (amountloop == 2)
						if (bbd.getType().isBlock() == true) {
							continue;
						}

					/*
					 * if (player .getLocation() .getBlock()
					 * .getRelative(bbd.getX() - selectx1[getid], bbd.getY() -
					 * selecty1[getid], bbd.getZ() -
					 * selectz1[getid]).getTypeId() != 0) { continue; }
					 */

					if (checkpermissionarea(
							player.getLocation()
									.getBlock()
									.getRelative(bbd.getX() - selectx1[getid],
											bbd.getY() - selecty1[getid],
											bbd.getZ() - selectz1[getid]),
							player, "dewset") == true) return;

					if (arxx)
						if (decreseitem1(player, bbd.getTypeId(),
								bbd.getData(), true) == false) {
							player.sendMessage(dprint.r.color("ptdew&dewdd : dewcopy "
									+ tr.gettr("don't_have_enough_item")
									+ bbd.getTypeId() + ":" + bbd.getData()));
							return;
						}

					player.getLocation()
							.getBlock()
							.getRelative(bbd.getX() - selectx1[getid],
									bbd.getY() - selecty1[getid],
									bbd.getZ() - selectz1[getid])
							.setTypeIdAndData(
									Bukkit.getWorld(selectworldname[getid])
											.getBlockAt(bbd.getX(), bbd.getY(),
													bbd.getZ()).getTypeId(),
									Bukkit.getWorld(selectworldname[getid])
											.getBlockAt(bbd.getX(), bbd.getY(),
													bbd.getZ()).getData(),
									false);

				}
			}
			dprint.r.printAll("ptdew&dewdd : " + player.getName() + " dewcopy "
					+ tr.gettr(tr.gettr("done")));

		}
	}

	class dewdelete_c implements Runnable {
		private byte	handdata;
		private int		handid;
		private Player	player;

		public dewdelete_c(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);

		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdelete "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdelete "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			if (player.hasPermission(pmaindelete) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("don't_have_permission_for_access_delete_command")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewdelete " + handid + ":"
					+ handdata);

			for (Block blb : getselectblock(getid, player)) {
				if (blb.getTypeId() != handid || blb.getData() != handdata) {
					continue;
				}
				if (checkpermissionarea(blb, player, "dewset") == true) return;

				if (checkpermissionarea(blb) == false) {
					continue;
				}

				/*
				 * if (PreciousStones.API().canBreak(player,
				 * blb.getLocation())== false) {
				 * player.sendMessage(dprint.r.color
				 * ("ptdew&dewdd :Can't dewdelete here (" + blb.getX() + "," +
				 * blb.getY() + "," + blb.getZ() + ")"); continue; }
				 */

				/*
				 * if (PreciousStones.API().isFieldProtectingArea(FieldFlag.
				 * PREVENT_PLACE , blb.getLocation())==false) {
				 * player.sendMessage(dprint.r.color(
				 * "ptdew&dewdd :Can't dewdelete if not your home zome (" +
				 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
				 * continue; }
				 */

				blb.setTypeId(0, false);

			}

			dprint.r.printAll("ptdew&dewdd : dewdelete " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewdig_c implements Runnable {
		private Player	player;

		public dewdig_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdig "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdig "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			if (player.hasPermission(pmaindelete) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("don't_have_permission_for_access_delete_command")));
				return;
			}

			if (player.getItemInHand().getType().getMaxDurability() <= 0) {
				player.sendMessage(dprint.r.color(tr
						.gettr("dewdig_this_item_maxdura_=_0")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewdddig "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			for (Block blb : getselectblock(getid, player)) {
				if (checkpermissionarea(blb, player, "dewset") == true) return;

				/*
				 * if (PreciousStones.API().canBreak(player,
				 * blb.getLocation())== false) {
				 * player.sendMessage(dprint.r.color
				 * ("ptdew&dewdd :Can't dewdig here (" + blb.getX() + "," +
				 * blb.getY() + "," + blb.getZ() + ")"); continue; }
				 */

				if (blb.getTypeId() == 0) {
					continue;
				}

				if (player.getItemInHand().getDurability() > player
						.getItemInHand().getType().getMaxDurability()) return;

				if (api_admin.dewddadmin.is2moderator(player) == false) {
					for (ItemStack it : blb.getDrops(player.getItemInHand())) {
						player.getWorld().dropItem(player.getLocation(), it);
					}
					blb.setTypeId(0);

				}
				else {
					blb.setTypeId(0);
				}

				player.getItemInHand()
						.setDurability(
								(short) (player.getItemInHand().getDurability() + (short) 1));

			}

			dprint.r.printAll("ptdew&dewdd : dewdig " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewdown_c implements Runnable {
		private byte	handdata;
		private int		handid;
		private Player	player;

		public dewdown_c(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			Block block = player.getLocation().getBlock().getRelative(0, -2, 0);
			if (isprotectitemid(block.getTypeId()) == true) return;

			if (checkpermissionarea(block, player, "dewset") == true) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdown "
						+ tr.gettr("this_is_not_your_zone")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewdown " + handid + ":"
					+ handdata);

			block.setTypeId(handid);
			block.setData(handdata);

			if (decreseitem1(player, handid, handdata, true) == false) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewdown "
						+ tr.gettr("don't_have_enough_item")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : dewdown " + player.getName() + " "
					+ tr.gettr("complete"));
		}
	}

	class dewextend_c implements Runnable {
		private Player	player;

		public dewextend_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			player.sendMessage(dprint.r.color("ptdew&dewdd : dewextend"
					+ tr.gettr("starting")));
			int getid = getfreeselect(player);
			selecty1[getid] = 1;
			selecty2[getid] = 254;

			player.sendMessage(dprint.r.color("ptdew&dewdd : selected area = ("
					+ selectx1[getid] + "," + selecty1[getid] + ","
					+ selectz1[getid] + ") to (" + selectx2[getid] + ","
					+ selecty2[getid] + "," + selectz2[getid] + ") = "));

			player.sendMessage(dprint.r.color("ptdew&dewdd : dewextend "
					+ tr.gettr("complete") + "d"));
		}
	}

	class dewfill_mom implements Runnable {

		private byte	handdata;
		private int		handid;
		private Player	player	= null;

		public dewfill_mom(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewfill "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewfill "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewfill " + handid + ":"
					+ handdata);
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewfill_thread aer = new dewfill_thread(player, handid, handdata,
					mx, my, mz, lx, ly, lz, lx, ly, lz);

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}
	}

	class dewfill_thread implements Runnable {

		private byte	handdata;
		private int		handid;
		private int		lx		= 0;
		private int		ly		= 0;
		private int		lz		= 0;
		private int		mx		= 0;

		private int		my		= 0;
		private int		mz		= 0;
		private Player	player	= null;

		private int		xlx		= 0;
		private int		ylx		= 0;
		private int		zlx		= 0;

		public dewfill_thread(Player player, int handid, byte handdata, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx,
				int zlx) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.mx = mx;
			this.my = my;
			this.mz = mz;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.xlx = xlx;
			this.ylx = ylx;
			this.zlx = zlx;

		}

		@Override
		public void run() {
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean arxx = !player.hasPermission(pmaininfinite);

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewfill_thread xgn2 = new dewfill_thread(player,
									handid, handdata, mx, my, mz, lx, ly, lz,
									xlx, ylx, zlx);

							dprint.r.printC("dewfill  " + tr.gettr("recall")
									+ " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, 50L);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (blb.getTypeId() != 0) {
							zlx++;
							continue;
						}
						if (checkpermissionarea(blb, player, "dewset") == true)
							return;

						if (arxx)
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage(dprint.r.color("ptdew&dewdd : "
										+ tr.gettr("don't_have_enough_item")));
								return;
							}
						blb.setTypeIdAndData(handid, handdata, false);
						//

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printAll("ptdew&dewdd : dewfill " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewfullcircle_c implements Runnable {
		private byte	handdata;
		private int		handid;
		private Player	player;

		public dewfullcircle_c(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewfullcircle "
								+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewfullcircle "
								+ tr.gettr("please_set_block_2")));
				return;
			}

			double midx = ((double) selectx1[getid] + (double) selectx2[getid]) / 2;
			double midy = ((double) selecty1[getid] + (double) selecty2[getid]) / 2;
			double midz = ((double) selectz1[getid] + (double) selectz2[getid]) / 2;

			if (midx == selectx1[getid] && midy == selecty1[getid]
					&& midz == selectz1[getid] || midx == selectx2[getid]
					&& midy == selecty2[getid] && midz == selectz2[getid]) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("small_circle_can't_run_program")));
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) selectx1[getid]
					- (double) selectx2[getid], 2);

			temp2 = Math.pow((double) selecty1[getid]
					- (double) selecty2[getid], 2);

			temp3 = Math.pow((double) selectz1[getid]
					- (double) selectz2[getid], 2);

			double midty = (selecty1[getid] + selecty2[getid]) / 2;

			double midtx = (selectx1[getid] + selectx2[getid]) / 2;

			double midtz = (selectz1[getid] + selectz2[getid]) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;
			Block blockmid = player.getWorld().getBlockAt((int) midtx,
					(int) midty, (int) midtz);

			player.sendMessage(dprint.r.color("cir=" + midtx + "," + midty
					+ "," + midtz));
			player.sendMessage(dprint.r.color("R=" + temp5));

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewfullcircle " + handid + ":"
					+ handdata);

			for (Block blb : getselectblock(getid, player)) {

				/*
				 * if (PreciousStones.API().canPlace(player,
				 * blb.getLocation())== false) {
				 * player.sendMessage(dprint.r.color
				 * ("ptdew&dewdd :Can't dewfullcircle here (" + blb.getX() + ","
				 * + blb.getY() + "," + blb.getZ() + ")"); continue; }
				 */

				if (blb.getLocation().distance(blockmid.getLocation()) > midr) {
					continue;
				}

				if (blb.getTypeId() != 0) {
					continue;
				}
				if (checkpermissionarea(blb, player, "dewset") == true) return;
				if (decreseitem1(player, handid, handdata, true) == false) {
					player.sendMessage(dprint.r.color("ptdew&dewdd : "
							+ tr.gettr("don't_have_enough_item")));
					return;
				}
				blb.setTypeIdAndData(handid, handdata, true);
				//
			} // for

			dprint.r.printAll("ptdew&dewdd : dewfullcircle " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewselectcube_c implements Runnable {
		private Player	player;
		private int		rad;

		public dewselectcube_c(Player player, int rad) {
			this.player = player;
			this.rad = rad;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			player.sendMessage(dprint.r.color("ptdew&dewdd : dew select cube"
					+ tr.gettr("starting")));
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

			player.sendMessage(dprint.r.color("ptdew&dewdd : selected area = ("
					+ selectx1[getid] + "," + selecty1[getid] + ","
					+ selectz1[getid] + ") to (" + selectx2[getid] + ","
					+ selecty2[getid] + "," + selectz2[getid] + ") = "));

			player.sendMessage(dprint.r.color("ptdew&dewdd : dew select cube "
					+ tr.gettr("complete") + "d"));
		}
	}

	// Bigdigthread

	class dewselectprotect_c implements Runnable {
		private Player	player;

		public dewselectprotect_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			player.sendMessage(dprint.r
					.color("ptdew&dewdd : dew select protect"
							+ tr.gettr("starting")));
			Block block = player.getLocation().getBlock();
			int getid = checkpermissionarea(block, true);
			if (getid == -1) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("this_zone_don't_have_protect")));
				return;
			}

			int getid2 = getfreeselect(player);

			selectx1[getid2] = dewsignx1[getworldid(block.getWorld().getName())][getid];
			selectx2[getid2] = dewsignx2[getworldid(block.getWorld().getName())][getid];

			selecty1[getid2] = dewsigny1[getworldid(block.getWorld().getName())][getid];
			selecty2[getid2] = dewsigny2[getworldid(block.getWorld().getName())][getid];

			selectz1[getid2] = dewsignz1[getworldid(block.getWorld().getName())][getid];
			selectz2[getid2] = dewsignz2[getworldid(block.getWorld().getName())][getid];

			player.sendMessage(dprint.r.color("ptdew&dewdd : selected area = ("
					+ selectx1[getid2] + "," + selecty1[getid2] + ","
					+ selectz1[getid2] + ") to (" + selectx2[getid2] + ","
					+ selecty2[getid2] + "," + selectz2[getid2] + ") = "));

			player.sendMessage(dprint.r
					.color("ptdew&dewdd : dew select protect "
							+ tr.gettr("complete") + "d"));
		}
	}

	class dewset_mom implements Runnable {
		private int		e1;
		private byte	e2;
		private int		e3;
		private byte	e4;
		private boolean	invert;
		private Player	player	= null;

		public dewset_mom(Player player, int e1, byte e2, int e3, byte e4,
				boolean invert) {
			this.player = player;
			this.e1 = e1;
			this.e2 = e2;
			this.e3 = e3;
			this.e4 = e4;
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

			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewset "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			Material ik = Material.getMaterial(e3);
			if (ik == null) {
				player.sendMessage(dprint.r.color("what is this item ? " + e3
						+ ":" + e4));
				return;
			}

			if (ik.isBlock() == false) {
				player.sendMessage(dprint.r.color("it's not a block ? " + e3
						+ ":" + e4));
				return;
			}

			if (isdewset(e3) == false) {
				player.sendMessage(dprint.r
						.color("this item my plugin not allow"
								+ tr.gettr("for") + "dewset " + e3 + ":" + e4));
				return;
			}

			if (dewddtps.tps.getTPS() < 17) {
				dprint.r.printAll("ptdew&dewdd : dewset "
						+ tr.gettr("tps_below_than_17") + dewddtps.tps.getTPS());

				return;
			}

			// player.sendMessage(dprint.r.color(". " + e1 + "," + e2 + "|" + e3
			// + "," + e4);

			if (invert == false) {
				dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
						+ tr.gettr("starting") + " dewset *search " + e1 + ":"
						+ e2 + " replace with " + e3 + ":" + e4);
			}
			else {
				dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
						+ tr.gettr("starting") + " dewxet *search " + e1 + ":"
						+ e2 + " replace with " + e3 + ":" + e4);
			}

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewset_thread xgn = new dewset_thread(player, e1, e2, e3, e4,
					invert, mx, my, mz, lx, ly, lz, lx, ly, lz, getid);

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
		private int		e1;
		private byte	e2;
		private int		e3;
		private byte	e4;
		private int		getid;
		private boolean	invert;
		private int		lx;

		private int		ly;
		private int		lz;
		private int		mx;

		private int		my;
		private int		mz;
		private Player	player	= null;

		private int		xlx;
		private int		ylx;
		private int		zlx;

		public dewset_thread(Player player, int e1, byte e2, int e3, byte e4,
				boolean invert, int mx, int my, int mz, int lx, int ly, int lz,
				int xlx, int ylx, int zlx, int getid) {
			this.player = player;
			this.e1 = e1;
			this.e2 = e2;
			this.e3 = e3;
			this.e4 = e4;
			this.invert = invert;

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

			if (player == null) {
				dprint.r.printAll("dewset error  player not found...");
				return;
			}

			boolean arxx = !player.hasPermission(pmaininfinite);

			boolean pppp = player.hasPermission(pmaindelete) == false;

			Block blb = null;

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (xlx <= mx) {

				while (ylx <= my) {
					while (zlx <= mz) {

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewset_thread xgn2 =

							new dewset_thread(player, e1, e2, e3, e4, invert,
									mx, my, mz, lx, ly, lz, xlx, ylx, zlx,
									getid);

							dprint.r.printC("dewset  " + tr.gettr("recall")
									+ " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, 50L);

							return;
						}

						if (e1 != -29) if (invert == false) {
							if (blb.getTypeId() != e1) {
								zlx++;
								continue;
							}
						}
						else if (blb.getTypeId() == e1) {
							zlx++;
							continue;
						}

						if (e2 != -29) if (invert == false) {
							if (blb.getData() != e2) {
								zlx++;
								continue;
							}
						}
						else if (blb.getData() == e2) {
							zlx++;
							continue;
						}

						if (checkpermissionarea(blb, player, "dewset") == true)
							return;

						if (blb.getTypeId() == e1 && blb.getData() == e2) {
							zlx++;
							continue;
						}

						if (e3 == 0) { // if delete
							if (pppp) {
								player.sendMessage(dprint.r.color(tr
										.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}

							blb.setTypeId(0);

						}

						else { // if place
							if (arxx)
								if (decreseitem1(player, e3, e4, true) == false) {
									player.sendMessage(dprint.r.color("ptdew&dewdd : "
											+ tr.gettr("don't_have_enough_item")));
									return;
								}

							blb.setTypeIdAndData(e3, e4, false);
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
				dprint.r.printAll("ptdew&dewdd : dewset " + tr.gettr("done")
						+ " : " + player.getName());
			}
			else {
				dprint.r.printAll("ptdew&dewdd : dewxet " + tr.gettr("done")
						+ " : " + player.getName());
			}
		}
	}

	class dewsetblock_mom implements Runnable {

		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private Player	player		= null;

		public dewsetblock_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewset(fill)block "
								+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewset(fill)block "
								+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewset(fill)block " + handid
					+ ":" + handdata);
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewsetblock_thread aer = new dewsetblock_thread(player, handid,
					handdata, mx, my, mz, lx, ly, lz, lx, ly, lz, getid,
					isfillmode);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}
	}

	class dewsetblock_thread implements Runnable {

		private int		getid;
		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private int		lx			= 0;
		private int		ly			= 0;

		private int		lz			= 0;
		private int		mx			= 0;
		private int		my			= 0;

		private int		mz			= 0;
		private Player	player		= null;
		private int		xlx			= 0;

		private int		ylx			= 0;

		private int		zlx			= 0;

		public dewsetblock_thread(Player player, int handid, byte handdata,
				int mx, int my, int mz, int lx, int ly, int lz, int xlx,
				int ylx, int zlx, int getid, boolean isfillmode) {
			this.player = player;
			this.isfillmode = isfillmode;
			this.handid = handid;
			this.handdata = handdata;
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

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;
			boolean arxx = !player.hasPermission(pmaininfinite);

			boolean t1 = false;
			boolean t2 = false;

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {
						t1 = ylx == selecty1[getid] || ylx == selecty2[getid];

						t2 = xlx == selectx1[getid] || zlx == selectz1[getid]
								|| xlx == selectx2[getid]
								|| zlx == selectz2[getid];

						if (!(t1 && t2 || !t1 && xlx == selectx1[getid]
								&& zlx == selectz1[getid]
								|| xlx == selectx2[getid]
								&& zlx == selectz2[getid]
								|| xlx == selectx1[getid]
								&& zlx == selectz2[getid] || xlx == selectx2[getid]
								&& zlx == selectz1[getid]

						)

						) {
							zlx++;
							continue;
						}
						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetblock_thread xgn2 = new dewsetblock_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode);

							dprint.r.printC("dewsetblock  "
									+ tr.gettr("recall") + " " + xlx + " , "
									+ ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (isfillmode == true) if (blb.getTypeId() != 0) {
							zlx++;
							continue;
						}

						if (checkpermissionarea(blb, player, "dewset") == true)
							return;

						// coner

						if (arxx)
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage(dprint.r.color("ptdew&dewdd : "
										+ tr.gettr("don't_have_enough_item")));
								return;
							}

						blb.setTypeIdAndData(handid, handdata, false);

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printAll("ptdew&dewdd : dew(set)block " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewsetl_mom implements Runnable {

		private byte	handdata;
		private int		handid;
		private Player	player	= null;

		public dewsetl_mom(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewsetlight "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewsetlight "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewsetlight " + handid + ":"
					+ handdata);
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewsetl_thread aer = new dewsetl_thread(player, handid, handdata,
					mx, my, mz, lx, ly, lz, lx, ly, lz, getid);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}
	}

	class dewsetl_thread implements Runnable {

		private int		getid;
		private byte	handdata;
		private int		handid;
		private int		lx		= 0;
		private int		ly		= 0;
		private int		lz		= 0;

		private int		mx		= 0;
		private int		my		= 0;
		private int		mz		= 0;

		private Player	player	= null;
		private int		xlx		= 0;
		private int		ylx		= 0;

		private int		zlx		= 0;

		public dewsetl_thread(Player player, int handid, byte handdata, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx,
				int zlx, int getid) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
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
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean ne = false;
			boolean arxx = !player.hasPermission(pmaininfinite);

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetl_thread xgn2 = new dewsetl_thread(player,
									handid, handdata, mx, my, mz, lx, ly, lz,
									xlx, ylx, zlx, getid);

							dprint.r.printC("dewsetl  " + tr.gettr("recall")
									+ " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

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

						if (checkpermissionarea(blb, player, "dewset") == true)
							return;

						if (arxx)
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage(dprint.r.color("ptdew&dewdd : "
										+ tr.gettr("don't_have_enough_item")));
								return;
							}

						blb.setTypeIdAndData(handid, handdata, true);

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printAll("ptdew&dewdd : dewsetlight " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewsetprivate_c implements Runnable {
		private Player	player;

		public dewsetprivate_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewsetprivate "
								+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewsetprivate "
								+ tr.gettr("please_set_block_2")));
				return;
			}

			player.getItemInHand().getTypeId();
			player.getItemInHand().getData().getData();

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewsetprivate "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			for (Block blb : getselectblock(getid, player)) {

				if (blb.getTypeId() == 0) {
					continue;
				}

				if (checkpermissionarea(blb, player, "dewset") == true) {
					continue;
				}

				try {
					if (Economy.getMoney(player.getName()) < api_private.dewddprivate.buy1blockmoney) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd : dewsetprivate "
										+ tr.gettr("need_more_money")));
						return;
					}
				}
				catch (UserDoesNotExistException e) {

					e.printStackTrace();
				}

				if (api_private.dewddprivate.checkpermissionareasign(blb) == true) {
					continue;
				}
				protectrailrun(blb, player);
				try {
					Economy.subtract(player.getName(),
							api_private.dewddprivate.buy1blockmoney);
				}
				catch (UserDoesNotExistException | NoLoanPermittedException e) {

					e.printStackTrace();
				}

			}

			dprint.r.printAll("ptdew&dewdd : dewsetprivate " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewsetroom_mom implements Runnable {

		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private Player	player		= null;

		public dewsetroom_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewset(fill)room "
								+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewset(fill)room "
								+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewset(fill)room " + handid
					+ ":" + handdata);
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewsetroom_thread aer = new dewsetroom_thread(player, handid,
					handdata, mx, my, mz, lx, ly, lz, lx, ly, lz, getid,
					isfillmode);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}
	}

	class dewsetroom_thread implements Runnable {

		private int		getid;
		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private int		lx			= 0;
		private int		ly			= 0;

		private int		lz			= 0;
		private int		mx			= 0;
		private int		my			= 0;

		private int		mz			= 0;
		private Player	player		= null;
		private int		xlx			= 0;

		private int		ylx			= 0;

		private int		zlx			= 0;

		public dewsetroom_thread(Player player, int handid, byte handdata,
				int mx, int my, int mz, int lx, int ly, int lz, int xlx,
				int ylx, int zlx, int getid, boolean isfillmode) {
			this.player = player;
			this.isfillmode = isfillmode;
			this.handid = handid;
			this.handdata = handdata;
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

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			boolean arxx = !player.hasPermission(pmaininfinite);

			Block blb = null;

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						if (!(xlx == selectx1[getid] || xlx == selectz1[getid]
								|| xlx == selectx2[getid]
								|| xlx == selectz2[getid]
								|| zlx == selectx1[getid]
								|| zlx == selectz1[getid]
								|| zlx == selectx2[getid]
								|| zlx == selectz2[getid]
								|| ylx == selecty1[getid] || ylx == selecty2[getid]

						)) {
							zlx++;
							continue;
						}

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetroom_thread xgn2 = new dewsetroom_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode);

							dprint.r.printC("dewsetroom  " + tr.gettr("recall")
									+ " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (isfillmode == true) if (blb.getTypeId() != 0) {
							zlx++;
							continue;
						}

						if (checkpermissionarea(blb, player, "dewset") == true)
							return;

						if (arxx)
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage(dprint.r.color("ptdew&dewdd : "
										+ tr.gettr("don't_have_enough_item")));
								return;
							}

						blb.setTypeIdAndData(handid, handdata, false);

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printAll("ptdew&dewdd : dew(set)room " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewsetwall_mom implements Runnable {

		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private Player	player		= null;

		public dewsetwall_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewset(fill)wall "
								+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewset(fill)wall "
								+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewset(fill)wall " + handid
					+ ":" + handdata);
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewsetwall_thread aer = new dewsetwall_thread(player, handid,
					handdata, mx, my, mz, lx, ly, lz, lx, ly, lz, getid,
					isfillmode);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}
	}

	class dewsetwall_thread implements Runnable {

		private int		getid;
		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private int		lx			= 0;
		private int		ly			= 0;

		private int		lz			= 0;
		private int		mx			= 0;
		private int		my			= 0;

		private int		mz			= 0;
		private Player	player		= null;
		private int		xlx			= 0;

		private int		ylx			= 0;

		private int		zlx			= 0;

		public dewsetwall_thread(Player player, int handid, byte handdata,
				int mx, int my, int mz, int lx, int ly, int lz, int xlx,
				int ylx, int zlx, int getid, boolean isfillmode) {
			this.player = player;
			this.isfillmode = isfillmode;
			this.handid = handid;
			this.handdata = handdata;
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
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean arxx = !player.hasPermission(pmaininfinite);

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						if (!(xlx == selectx1[getid] || zlx == selectz1[getid]
								|| xlx == selectx2[getid]
								|| zlx == selectz2[getid]
								|| xlx == selectx1[getid]
								|| zlx == selectz1[getid]
								|| xlx == selectx2[getid] || zlx == selectz2[getid])) {
							zlx++;
							continue;
						}

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetwall_thread xgn2 = new dewsetwall_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode);

							dprint.r.printC("dewsetwall  " + tr.gettr("recall")
									+ " " + xlx + " , " + ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (isfillmode == true) if (blb.getTypeId() != 0) {
							zlx++;
							continue;
						}

						if (checkpermissionarea(blb, player, "dewset") == true)
							return;

						if (arxx)
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage(dprint.r.color("ptdew&dewdd : "
										+ tr.gettr("don't_have_enough_item")));
								return;
							}
						blb.setTypeIdAndData(handid, handdata, false);
						//

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printAll("ptdew&dewdd : dew(set)fill " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class dewspreadcircle_c implements Runnable {
		private byte	handdata;
		private int		handid;
		private Player	player;

		public dewspreadcircle_c(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			Block block = player.getLocation().getBlock();
			Queue<Block> bd = new LinkedList<Block>();

			boolean ne = false;
			Block b2 = null;
			int x = 0;
			int y = 0;
			int z = 0;

			for (x = -1; x <= 1; x++) {
				for (y = -1; y <= 1; y++) {
					for (z = -1; z <= 1; z++) {
						b2 = block.getRelative(x, y, z);

						bd.add(b2);
					}
				}
			}

			Block b3 = null;

			if (bd.size() <= 0) return;

			while (bd.size() > 0) { // bll
				b3 = bd.poll();

				ne = false;

				for (x = -1; x <= 1; x++) {
					for (y = -1; y <= 1; y++) {
						for (z = -1; z <= 1; z++) {
							b2 = b3.getRelative(x, y, z);

							if (b2.getTypeId() == 0) {
								if (checkpermissionarea(b2, player, "dewset") == true) {
									continue;
								}
								if (decreseitem1(player, handid, handdata, true) == false) {
									player.sendMessage(dprint.r.color(tr
											.gettr("don't_have_enough_item")));
									return;
								}
								b2.setTypeId(handid);
								b2.setData(handdata);

								ne = true;
								break;
							}

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

							b2 = b3.getRelative(x, y, z);
							// dprint.r.printAll("ptdew&dewdd : delete near call sub ("
							// +
							// b2.getX() + "," + b2.getY() + "," + b2.getZ() +
							// ") "
							// + amount);

							bd.add(b2);

						}

					}
				}

			}// bll

			player.sendMessage(dprint.r.color("dewspreadcircle "
					+ tr.gettr(tr.gettr("done"))));
		}
	}

	class dewspreadq_c implements Runnable {
		private byte	handdata;
		private int		handid;
		private Player	player;
		private Boolean	isfirst;

		public dewspreadq_c(Player player, int handid, byte handdata,
				boolean isfirst) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfirst = isfirst;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this,
					rnd.nextInt(20));
		}

		@Override
		public void run() {

			Block block = player.getLocation().getBlock();

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

			Block b3 = null;

			int ccc = 0;

			if (bd.size() <= 0) {
				bd.clear();
				return;

			}

			while (bd.size() > 0) { // bll
				b3 = bd.poll();

				ne = false;

				if (b3.getTypeId() == 0) {
					if (checkpermissionarea(b3, player, "dewset") == true) {
						continue;
					}
					if (decreseitem1(player, handid, handdata, true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("don't_have_enough_item")));
						return;
					}

					b3.setTypeId(handid);
					b3.setData(handdata);
					ccc++;
					ne = true;
				}

				if (ne == false) {
					continue;
				}

				for (x = -1; x <= 1; x++) {
					for (z = -1; z <= 1; z++) {

						if (x != 0 && z != 0) {
							continue;
						}
						b2 = b3.getRelative(x, 0, z);
						// dprint.r.printAll("ptdew&dewdd : delete near call sub ("
						// +
						// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") "
						// +
						// amount);

						bd.add(b2);

					}
				}

				if (ccc > 10) {
					new dewspreadq_c(player, handid, handdata, false);
					return;
				}

			}// bll

			player.sendMessage(dprint.r.color("dewspreadq "
					+ tr.gettr(tr.gettr("done"))));
		}
	}

	class dewtime_c implements Runnable {
		private Player	player;

		public dewtime_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewtime "
						+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : dewtime "
						+ tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewtime "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			int mx = 0;
			int mz = 0;

			int lx = 0;
			int lz = 0;

			if (selectx1[getid] >= selectx2[getid]) {
				mx = selectx1[getid];
				lx = selectx2[getid];
			}
			else {
				lx = selectx1[getid];
				mx = selectx2[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
			}
			else {
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				lz = selectz1[getid];
				mz = selectz2[getid];
			}

			Block block = null;
			Chunk chunk = null;

			for (int nx = lx; nx <= mx; nx += 16) {
				for (int nz = lz; nz <= mz; nz += 16) {

					block = player.getWorld().getBlockAt(nx, 1, nz);
					block.getChunk().load();
					chunk = block.getChunk();

					// add to new

					boolean isad = false;

					for (int ddx = 0; ddx <= timechunkmax; ddx++)
						if (chunk.getX() * 16 == timechunkx[ddx]
								&& chunk.getZ() * 16 == timechunkz[ddx]) {
							isad = true;
							break;
						}

					if (isad == false) {
						timechunkmax++;
						timechunkx[timechunkmax] = chunk.getX() * 16;
						timechunkz[timechunkmax] = chunk.getZ() * 16;
						dprint.r.printAll("dewtime "
								+ tr.gettr("add_new_chunk") + "  = ["
								+ timechunkmax + "] > "
								+ timechunkx[timechunkmax] + ","
								+ timechunkz[timechunkmax]);
					}

				}
			}

			dprint.r.printAll("ptdew&dewdd :  dewtime  " + tr.gettr("done")
					+ " : " + player.getName());

		}
	}

	class dewwallcircle_mom implements Runnable {

		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private Player	player		= null;

		public dewwallcircle_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		@Override
		public void run() {

			// ..........
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewwallcircle "
								+ tr.gettr("please_set_block_1")));
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : dewwallcircle "
								+ tr.gettr("please_set_block_2")));
				return;
			}

			double midx = ((double) selectx1[getid] + (double) selectx2[getid]) / 2;
			double midy = ((double) selecty1[getid] + (double) selecty2[getid]) / 2;
			double midz = ((double) selectz1[getid] + (double) selectz2[getid]) / 2;

			if (midx == selectx1[getid] && midy == selecty1[getid]
					&& midz == selectz1[getid] || midx == selectx2[getid]
					&& midy == selecty2[getid] && midz == selectz2[getid]) {
				player.sendMessage(dprint.r
						.color("ptdew&dewdd : small circle can't run program"));
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) selectx1[getid]
					- (double) selectx2[getid], 2);

			temp2 = Math.pow((double) selecty1[getid]
					- (double) selecty2[getid], 2);

			temp3 = Math.pow((double) selectz1[getid]
					- (double) selectz2[getid], 2);

			double midty = (selecty1[getid] + selecty2[getid]) / 2;

			double midtx = (selectx1[getid] + selectx2[getid]) / 2;

			double midtz = (selectz1[getid] + selectz2[getid]) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;

			player.sendMessage(dprint.r.color("cir=" + midtx + "," + midty
					+ "," + midtz));
			player.sendMessage(dprint.r.color("R=" + temp5));

			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "'"
					+ tr.gettr("starting") + " dewwallcircle "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

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
			}
			else {
				mx = selectx2[getid];
				lx = selectx1[getid];
			}

			if (selecty1[getid] >= selecty2[getid]) {
				my = selecty1[getid];
				ly = selecty2[getid];
			}
			else {
				my = selecty2[getid];
				ly = selecty1[getid];
			}

			if (selectz1[getid] >= selectz2[getid]) {
				mz = selectz1[getid];
				lz = selectz2[getid];
			}
			else {
				mz = selectz2[getid];
				lz = selectz1[getid];
			}

			dewwallcircle_thread aer = new dewwallcircle_thread(player, handid,
					handdata, mx, my, mz, lx, ly, lz, lx, ly, lz, getid,
					isfillmode, midr, midtx, midty, midtz);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

		}
	}

	class dewwallcircle_thread implements Runnable {

		private int		getid;
		private byte	handdata;
		private int		handid;
		private boolean	isfillmode	= false;
		private int		lx			= 0;
		private int		ly			= 0;

		private int		lz			= 0;
		private double	midr;
		private double	midtx;

		private double	midty;
		private double	midtz;
		private int		mx			= 0;

		private int		my			= 0;
		private int		mz			= 0;

		private Player	player		= null;

		private int		xlx			= 0;
		private int		ylx			= 0;
		private int		zlx			= 0;

		public dewwallcircle_thread(Player player, int handid, byte handdata,
				int mx, int my, int mz, int lx, int ly, int lz, int xlx,
				int ylx, int zlx, int getid, boolean isfillmode, double midr,
				double midtx, double midty, double midtz) {
			this.player = player;
			this.isfillmode = isfillmode;
			this.handid = handid;
			this.handdata = handdata;
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

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;

			Block blockmid = player.getWorld().getBlockAt((int) midtx,
					(int) midty, (int) midtz);

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewwallcircle_thread xgn2 = new dewwallcircle_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode,
									midr, midtx, midty, midtz);

							dprint.r.printC("dewwallcircle  "
									+ tr.gettr("recall") + " " + xlx + " , "
									+ ylx + " , " + zlx);
							dprint.r.printC("low " + lx + " , " + ly + " , "
									+ lz + " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, 50L);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if ((int) blb.getLocation().distance(
								blockmid.getLocation()) != (int) midr) {
							zlx++;
							continue;
						}

						if (isfillmode == true) if (blb.getTypeId() != 0) {
							zlx++;
							continue;
						}

						// wallc

						if (checkpermissionarea(blb, player, "dewset") == true)
							return;
						if (decreseitem1(player, handid, handdata, true) == false) {
							player.sendMessage(dprint.r.color("ptdew&dewdd : "
									+ tr.gettr("don't_have_enough_item")));
							return;
						}

						blb.setTypeIdAndData(handid, handdata, true);
						//

						// wallc

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			dprint.r.printAll("ptdew&dewdd : dewwallcircle " + tr.gettr("done")
					+ " : " + player.getName());
		}
	}

	class gotohellt implements Runnable {
		private Location	lo1		= null;
		private Location	lo2		= null;
		private Player		player	= null;

		public gotohellt(Player player, Location lo1, Location lo2) {
			this.player = player;
			this.lo1 = lo1;
			this.lo2 = lo2;
		}

		@Override
		public void run() {
			int dx = 15;
			Block blo = null;
			Block blo2 = null;

			for (int d = 1; d <= 2; d++) {
				for (int x = 0 - dx; x <= 0 + dx; x++) {
					for (int z = 0 - dx; z <= 0 + dx; z++) {
						for (int y = 0 - dx; y <= 0 + dx; y++) {
							blo = Bukkit.getWorld("world").getBlockAt(lo1)
									.getRelative(x, y, z);
							if (d == 1) {
								if (blo.getType().isBlock() == false) {
									continue;
								}
							}
							else if (d == 2)
								if (blo.getType().isBlock() == true) {
									continue;
								}

							blo2 = Bukkit.getWorld("world_nether")
									.getBlockAt(lo2).getRelative(x, y, z);

							switch (blo.getTypeId()) {
							case 2:
							case 35:
								blo2.setTypeId(88);
								break;

							case 3:
							case 60:
								blo2.setTypeId(87);
								break;
							case 8:
							case 9:
							case 10:
							case 11:
							case 159:
							case 97:
							case 24:
							case 89:

								blo2.setTypeId(20);
								break;
							case 41:
							case 42:
							case 57:
							case 56:
							case 133:
							case 155:

							case 15:
							case 16:
							case 14:
							case 121:
							case 129:
							case 112:
							case 125:
							case 138:
							case 124:
							case 145:
							case 84:
							case 130:
							case 47:
							case 123:
							case 122:

								blo2.setTypeId(46);
								break;
							default:
								blo2.setTypeId(blo.getTypeId());
							}
							blo2.setData(blo.getData());
						}
					}
				}
			}

			/*
			 * if (player.getInventory().getHelmet() != null) {
			 * Bukkit.getWorld("world_nether").dropItem(lo2,
			 * player.getInventory().getHelmet());
			 * player.getInventory().getHelmet().setTypeId(0); }
			 * 
			 * ItemStack itm = new ItemStack(397, 1);
			 * player.getInventory().setHelmet(itm.getData().toItemStack());
			 */
			player.teleport(lo2);
			player.getInventory().remove(7);
			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "' "
					+ tr.gettr("what_the_hell"));
			dprint.r.printC("ptdew&dewdd : go to hell '" + player.getName()
					+ "'");
		}
	}

	class hideshowrun_c implements Runnable {
		public hideshowrun_c(Player player) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			Player ptdewv = null;
			int gga = -1;
			for (Player pla : Bukkit.getOnlinePlayers()) {

				ptdewv = null;
				if (pla.hasPermission(phideshowrun)) {
					ptdewv = pla;
					// dprint.r.printC("found fir'" + pla.getName() + "' ");
					// player.sendMessage(dprint.r.color("found fir '" +
					// pla.getName() + "' ");
				}

				if (ptdewv != null) {
					for (Player pla2 : Bukkit.getOnlinePlayers()) {
						if (pla2.getName().equalsIgnoreCase(pla.getName()) == true) {
							continue;
						}

						// admin can see everyone
						if (ptdewv.hasPermission(phideshowrun) == true) {
							ptdewv.showPlayer(pla2); // make admin can see
														// everyone
						}

						// ptdewv is not admin not spy
						if (pla2.hasPermission(phideshowrun) == false) {
							gga = getfreeselect(ptdewv);
							if (hidemode[gga] == true) {
								pla2.hidePlayer(ptdewv); // make member can't
															// see
															// admin
							}
							else {
								pla2.showPlayer(ptdewv); // make
							}
						}
						// admin or not

					}
				}

			}

			// ***********
		}
	}

	class item55deletec implements Runnable {

		private Block	block	= null;
		private byte	dat		= -29;
		private int		id		= -29;
		private Player	player	= null;

		public item55deletec(Block block, Player player, int id, byte dat) {
			this.block = block;
			this.player = player;
			this.id = id;
			this.dat = dat;
		}

		@Override
		public void run() {

			if (block.getTypeId() != id) return;

			if (dat != -29 && block.getData() != dat) return;

			try {
				if (Economy.getMoney(player.getName()) < 100) return;
			}
			catch (UserDoesNotExistException e1) {

				e1.printStackTrace();
			}

			if (amount3 > player.getItemInHand().getAmount() * 100) return;

			if (checkpermissionarea(block, player, "delete") == true) return;
			if (isprotectitemid(block.getTypeId()) == true) return;

			try {
				Economy.subtract(player.getName(), 10);
			}
			catch (UserDoesNotExistException | NoLoanPermittedException e) {

				e.printStackTrace();
			}

			amount3++;

			block.breakNaturally();
			Block b2 = null;

			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						b2 = block.getRelative(x, y, z);

						if (amount2 > 100) return;
						amount2++;
						try {
							item55delete(b2, player, id, dat);
						}
						catch (UserDoesNotExistException
								| NoLoanPermittedException e) {

							e.printStackTrace();
						}
						amount2--;
					}
				}
			}

		}
	}

	class randomplaynote_c implements Runnable {
		private Location	player;

		public randomplaynote_c(Location player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (randomG.nextInt(100) > 10) return;

			for (Player pla : player.getWorld().getPlayers()) {
				pla.playSound(player, Sound.NOTE_PIANO, randomG.nextInt(50),
						randomG.nextFloat() + 1);
			}
		}
	}

	class redtorchchestt implements Runnable {
		private Block	block	= null;
		private Player	player	= null;

		public redtorchchestt(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		@Override
		public void run() {
			Chest chest = (Chest) block.getState();
			Player pxp[] = new Player[selectmax];
			int pxpmax = -1;
			for (Player pxpsub : Bukkit.getOnlinePlayers()) {
				if (pxpsub.getName().equalsIgnoreCase(player.getName())) {
					continue;
				}

				if (api_admin.dewddadmin.is2admin(pxpsub) == true) {
					continue;
				}
				if (api_admin.dewddadmin.is2moderator(pxpsub) == true) {
					continue;
				}

				// if normal player or vip so can do
				pxpmax++;
				pxp[pxpmax] = pxpsub;

			}
			// after get all normall playr all vip
			int pxpluck = -1;

			for (ItemStack itm : chest.getInventory().getContents()) {
				if (itm == null) {
					continue;
				}

				int itmmax = itm.getAmount();
				itm.setAmount(1);
				for (int ama = 1; ama <= itmmax; ama++) {
					pxpluck++;
					if (pxpluck > pxpmax) {
						pxpluck = 0;
					}

					dprint.r.printAll("ptdew&dewdd : '" + player.getName()
							+ "' " + tr.gettr("gived_item") + " '"
							+ itm.getType().name() + "' to '" + "[" + pxpluck
							+ "/" + pxpmax + "] " + pxp[pxpluck].getName()
							+ "'");
					Location lox = pxp[pxpluck].getLocation();
					lox.setY(lox.getY() + 5);
					pxp[pxpluck].getWorld().dropItem(lox, itm);

				}

			}

			// remove block
			chest.getInventory().clear();
			chest.update(true);
			for (@SuppressWarnings("unused")
			Object obj : pxp) {
				obj = null;
			}
			pxp = null;

		}
	}

	class runter_mom implements Runnable {

		private int		a1;
		private byte	a2;
		private String	e;
		private Player	player;

		public runter_mom(Player player, String e, int a1, byte a2) {
			this.player = player;
			this.a1 = a1;
			this.a2 = a2;
			this.e = e;
		}

		@Override
		public void run() {
			Material ik = Material.getMaterial(a1);

			if (ik == null) {
				player.sendMessage(dprint.r.color(tr
						.gettr("what_the_hell_item") + a1 + ":" + a2));
				return;
			}

			if (ik.isBlock() == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("this_item_not_a_block") + a1 + ":" + a2));
				return;
			}

			if (isdewset(a1) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("this_item_not_allow_for_dewset")
						+ tr.gettr("for") + "dewset " + a1 + ":" + a2));
				return;
			}

			if (dewddtps.tps.getTPS() < 17) {
				dprint.r.printAll("ptdew&dewdd : "
						+ tr.gettr("tps_below_than_17") + dewddtps.tps.getTPS());

				return;
			}

			if (e.equalsIgnoreCase("dewdown") || e.equalsIgnoreCase("dn")
					|| e.equalsIgnoreCase("down")) {
				dewdown(player, a1, a2);
				return;
			}

			if (e.equalsIgnoreCase("dewsetlight") || e.equalsIgnoreCase("dsl")) {
				dewsetl(player, a1, a2);
				return;
			}
			if (e.equalsIgnoreCase("dewspreadq") || e.equalsIgnoreCase("dsq")) {
				dewspreadq(player, a1, a2);
				return;
			}
			if (e.equalsIgnoreCase("dewspreadcircle")
					|| e.equalsIgnoreCase("dsc")) {
				dewspreadcircle(player, a1, a2);
				return;
			}

			if (e.equalsIgnoreCase("dewdelete") || e.equalsIgnoreCase("dd")) {
				dewdelete(player, a1, a2);
				return;
			}
			if (e.equalsIgnoreCase("dewbreak")) {
				dewbreak(player, a1, a2);
				return;
			}

		}
	}

	class seedglowc implements Runnable {
		private Block	block	= null;
		private Player	player	= null;

		public seedglowc(Block block, Player player) {
			this.player = player;
			this.block = block;
		}

		@Override
		public void run() {

			int d = 5;
			Block b2 = null;

			for (int gx = -d; gx <= d; gx++) {
				for (int gy = -d; gy <= d; gy++) {
					for (int gz = -d; gz <= d; gz++) {
						b2 = block.getRelative(gx, gy, gz);
						switch (b2.getTypeId()) {

						case 142:
						case 141:
						case 59:
						case 104:
						case 105:

							if (b2.getData() >= 7) {
								continue;
							}
							if (decreseitem1(player, 351, 15, true) == false)
								return;
							b2.setData((byte) 7);

							seedglow(b2, player);

							break;

						}

					}
				}
			}

		}
	}

	class soiladdseedc implements Runnable {
		private Block	block	= null;
		private boolean	first	= false;
		private Player	player	= null;
		private int		seedid	= 0;

		public soiladdseedc(Block block, Player player, int seedid,
				boolean first) {
			this.block = block;
			this.player = player;
			this.seedid = seedid;
			this.first = first;
		}

		@Override
		public void run() {
			if (first == true) {
				block.setTypeId(0);
			}
			int digX = block.getX();
			int digY = block.getY();
			int digZ = block.getZ();

			int itemaddid = 0;
			switch (seedid) {
			case 392: // potato
				itemaddid = 142;
				break;
			case 391: // carot
				itemaddid = 141;
				break;
			case 295:
				itemaddid = 59;
				break;
			/*
			 * case 361: itemaddid = 104; break; case 362: itemaddid = 105;
			 * break;
			 */
			default:
				return;
			}

			byte itemadddata = 0;

			World world = player.getWorld();

			Block blockCut = world.getBlockAt(digX, digY, digZ);

			if (blockCut.getRelative(0, -1, 0).getTypeId() != 60) return;

			if (blockCut.getTypeId() != 0) return;

			boolean haswater = false;

			for (int xx3 = 4; xx3 >= -4; xx3--) {
				for (int zz3 = 4; zz3 >= -4; zz3--)
					if (blockCut.getRelative(xx3, -1, zz3).getTypeId() == 9
							|| blockCut.getRelative(xx3, -1, zz3).getTypeId() == 8
							|| blockCut.getRelative(xx3, 0, zz3).getTypeId() == 9
							|| blockCut.getRelative(xx3, 0, zz3).getTypeId() == 8) {
						haswater = true;
						break;
					}

				if (haswater == true) {
					break;
				}
			}

			if (haswater == false) return;

			if (blockCut.getLightLevel() < 9) return;

			if (decreseitem1(player, seedid, 0, false) == false) {
				player.saveData();
				return;
			}

			blockCut.setTypeId(itemaddid);
			blockCut.setData(itemadddata);
			// dprint.r.printA(digX + "," + digY + "," + digZ);

			// randomplaynote(blockCut.getLocation());

			/*
			 * if (player.getFoodLevel() > 0) {
			 * player.setFoodLevel(player.getFoodLevel() - 1); }
			 */

			// start call sub
			for (int xx3 = digX + 4; xx3 >= digX - 4; xx3--) {
				for (int yy3 = digY - 4; yy3 <= digY + 4; yy3++) {
					for (int zz3 = digZ + 4; zz3 >= digZ - 4; zz3--) {
						if (digX == xx3 && digY == yy3 && digZ == zz3) {
							continue;
						}
						blockCut = world.getBlockAt(xx3, yy3, zz3);

						soiladdseedrecusive(blockCut, player, seedid, false);
					}
				}
			}

		}
	}

	// redim" + tr.gettr("for") + "each world each protect = 100

	public String	phideshowrun	= "dewdd.main.joinhide";

	long				lastsort2			= 0;

	Random				rnd					= new Random();

	Queue<Block>		bd					= new LinkedList<Block>();

	public JavaPlugin	ac					= null;

	public boolean		allowfly			= true;

	public boolean		allownight			= true;

	public int			amount2				= 0;							// recursive
																			// 55
	public int			amount3				= 0;							// count
																			// 55

	public boolean		chatever[]			= new boolean[selectmax + 1];

	public int			d4[]				= new int[selectmax + 1];
	public boolean		dewaxe[]			= new boolean[selectmax + 1];
	public Block		giftblock			= null;
	public int			dewsignloop[][];

	public int			dewsignmax[];

	public String		dewsignname[][][];
	public int			dewsignx1[][];

	public int			dewsignx2[][];

	public int			dewsigny1[][];
	public int			dewsigny2[][];

	public int			dewsignz1[][];

	public int			dewsignz2[][];
	public String		dewworldlist[];
	public int			dewworldlistmax		= 0;

	// public double buy1blockmoney = 0.0890932504689118445732202345959;

	public boolean		hidemode[]			= new boolean[selectmax + 1];

	public boolean		monfast				= false;

	// autosearchsub

	public boolean		moninvi				= false;

	public boolean		monjump				= true;

	public Random		randomG				= new Random();

	int					randomInt			= randomG.nextInt(500);

	// *********

	// cutwoodsub

	public int			runtime				= 500;

	public Block		selectblock[]		= new Block[selectmax + 1];

	// decrese item 1

	public String		selectname[]		= new String[selectmax + 1];

	public String		selectworldname[]	= new String[selectmax + 1];

	public int			selectx1[]			= new int[selectmax + 1];

	// Check Permission Area block player mode

	public int			selectx2[]			= new int[selectmax + 1];

	public int			selecty1[]			= new int[selectmax + 1];

	public int			selecty2[]			= new int[selectmax + 1];

	public int			selectz1[]			= new int[selectmax + 1];

	public int			selectz2[]			= new int[selectmax + 1];

	// cut seem block

	public int			timechunkmax		= -1;

	public int			timechunkx[];

	public int			timechunkz[];

	public dewset() {
		// if (firstrun19 == false){

		// loadadminlist();

		timechunkx = new int[10000];
		timechunkz = new int[10000];

		// firstrun19 = true;
		// }
	}

	public void adderarraysignfile(int worldid) {
		dewsignmax[worldid]++;
	}

	// addfood withmoney
	public void addfoodwithmoney(Player player) {
		if (player.getName().equalsIgnoreCase("") == false) return;

		try {
			while (player.getFoodLevel() < 20
					&& Economy.getMoney(player.getName()) > 1) {

				Economy.setMoney(player.getName(),
						Economy.getMoney(player.getName()) - 1);
				player.setFoodLevel(player.getFoodLevel() + 1);

			}
		}
		catch (UserDoesNotExistException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoLoanPermittedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void autosortchest2(Block block, Player player) {
		autosortchest2_class ar = new autosortchest2_class(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);
	}

	public boolean canaddtorch(Block bbc) {
		if (bbc.getTypeId() != 0) return false;

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

	public boolean checkbetweenblock(int digx, int digy, int digz, int x1,
			int y1, int z1, int x2, int y2, int z2) {

		boolean goodc1 = false;

		goodc1 = false;

		// x 2 type
		// x1 <= x2
		if (x1 <= x2) {
			if (digx > x1 - 1 && digx < x2 + 1) if (y1 <= y2) {
				if (digy > y1 - 1 && digy < y2 + 1)
				// y true
					if (z1 <= z2) {
						if (digz > z1 - 1 && digz < z2 + 1) {
							// z true
							goodc1 = true;
						}
					}
					else if (digz < z1 + 1 && digz > z2 - 1) {
						// z true
						goodc1 = true;
					}
			}
			else if (digy < y1 + 1 && digy > y2 - 1)
			// y true
				if (z1 <= z2) {
					if (digz > z1 - 1 && digz < z2 + 1) {
						// z true
						goodc1 = true;
					}
				}
				else if (digz < z1 + 1 && digz > z2 - 1) {
					// z true
					goodc1 = true;
				}
		}
		else if (digx < x1 + 1 && digx > x2 - 1) if (y1 <= y2) {
			if (digy > y1 - 1 && digy < y2 + 1)
			// y true
				if (z1 <= z2) {
					if (digz > z1 - 1 && digz < z2 + 1) {
						// z true
						goodc1 = true;
					}
				}
				else if (digz < z1 + 1 && digz > z2 - 1) {
					// z true
					goodc1 = true;
				}
		}
		else if (digy < y1 + 1 && digy > y2 - 1)
		// y true
			if (z1 <= z2) {
				if (digz > z1 - 1 && digz < z2 + 1) {
					// z true
					goodc1 = true;
				}
			}
			else if (digz < z1 + 1 && digz > z2 - 1) {
				// z true
				goodc1 = true;
			}

		return goodc1;

	}

	// Check Permission Area block
	public boolean checkpermissionarea(Block block) {

		if (getworldid(block.getWorld().getName()) == -1) return false;
		if (dewsignmax[getworldid(block.getWorld().getName())] == 0)
			return false;

		int digx = block.getX();
		int digy = block.getY();
		int digz = block.getZ();

		boolean goodc1 = false;

		goodc1 = false;

		int dewsignnow = 0;

		for (dewsignnow = 0; dewsignnow < dewsignmax[getworldid(block
				.getWorld().getName())]; dewsignnow++) { // for

			// x 2 type
			// x1 <= x2

			// check is this block on protect zone or not
			int x1m = 0;
			int x1l = 0;
			if (dewsignx1[getworldid(block.getWorld().getName())][dewsignnow] >= dewsignx2[getworldid(block
					.getWorld().getName())][dewsignnow]) {
				x1m = dewsignx1[getworldid(block.getWorld().getName())][dewsignnow];
				x1l = dewsignx2[getworldid(block.getWorld().getName())][dewsignnow];
			}
			else {
				x1l = dewsignx1[getworldid(block.getWorld().getName())][dewsignnow];
				x1m = dewsignx2[getworldid(block.getWorld().getName())][dewsignnow];
			}

			int y1m = 0;
			int y1l = 0;
			if (dewsigny1[getworldid(block.getWorld().getName())][dewsignnow] >= dewsigny2[getworldid(block
					.getWorld().getName())][dewsignnow]) {
				y1m = dewsigny1[getworldid(block.getWorld().getName())][dewsignnow];
				y1l = dewsigny2[getworldid(block.getWorld().getName())][dewsignnow];
			}
			else {
				y1l = dewsigny1[getworldid(block.getWorld().getName())][dewsignnow];
				y1m = dewsigny2[getworldid(block.getWorld().getName())][dewsignnow];

			}

			int z1m = 0;
			int z1l = 0;
			if (dewsignz1[getworldid(block.getWorld().getName())][dewsignnow] >= dewsignz2[getworldid(block
					.getWorld().getName())][dewsignnow]) {
				z1m = dewsignz1[getworldid(block.getWorld().getName())][dewsignnow];
				z1l = dewsignz2[getworldid(block.getWorld().getName())][dewsignnow];
			}
			else {
				z1l = dewsignz1[getworldid(block.getWorld().getName())][dewsignnow];
				z1m = dewsignz2[getworldid(block.getWorld().getName())][dewsignnow];

			}

			boolean ee = digx >= x1l && digx <= x1m && digy >= y1l
					&& digy <= y1m && digz >= z1l && digz <= z1m;

			goodc1 = ee;

			if (goodc1 == true) return true;

		}
		return false;

	}

	public int checkpermissionarea(Block block, boolean gethomeid) {

		if (getworldid(block.getWorld().getName()) == -1) return -1;
		if (dewsignmax[getworldid(block.getWorld().getName())] == 0) return -1;

		boolean ee = false;
		int digx = block.getX();
		int digy = block.getY();
		int digz = block.getZ();

		boolean goodc1 = false;

		goodc1 = false;

		int dewsignnow = 0;

		for (int prioL = 1; prioL <= 2; prioL++) {

			for (dewsignnow = 0; dewsignnow < dewsignmax[getworldid(block
					.getWorld().getName())]; dewsignnow++) { // for
				if (prioL == 1) {
					// dprint.r.printAll("dewsignnow " + dewsignnow );

					if (dewsignname[getworldid(block.getWorld().getName())][dewsignnow][0]
							.equalsIgnoreCase(flag_everyone) == true) {
						continue;
					}
				}
				else if (dewsignname[getworldid(block.getWorld().getName())][dewsignnow][0]
						.equalsIgnoreCase(flag_everyone) == false) {
					continue;
				}

				// check is this block on protect zone or not
				int x1m = 0;
				int x1l = 0;
				if (dewsignx1[getworldid(block.getWorld().getName())][dewsignnow] >= dewsignx2[getworldid(block
						.getWorld().getName())][dewsignnow]) {
					x1m = dewsignx1[getworldid(block.getWorld().getName())][dewsignnow];
					x1l = dewsignx2[getworldid(block.getWorld().getName())][dewsignnow];
				}
				else {
					x1l = dewsignx1[getworldid(block.getWorld().getName())][dewsignnow];
					x1m = dewsignx2[getworldid(block.getWorld().getName())][dewsignnow];
				}

				int y1m = 0;
				int y1l = 0;
				if (dewsigny1[getworldid(block.getWorld().getName())][dewsignnow] >= dewsigny2[getworldid(block
						.getWorld().getName())][dewsignnow]) {
					y1m = dewsigny1[getworldid(block.getWorld().getName())][dewsignnow];
					y1l = dewsigny2[getworldid(block.getWorld().getName())][dewsignnow];
				}
				else {
					y1l = dewsigny1[getworldid(block.getWorld().getName())][dewsignnow];
					y1m = dewsigny2[getworldid(block.getWorld().getName())][dewsignnow];

				}

				int z1m = 0;
				int z1l = 0;
				if (dewsignz1[getworldid(block.getWorld().getName())][dewsignnow] >= dewsignz2[getworldid(block
						.getWorld().getName())][dewsignnow]) {
					z1m = dewsignz1[getworldid(block.getWorld().getName())][dewsignnow];
					z1l = dewsignz2[getworldid(block.getWorld().getName())][dewsignnow];
				}
				else {
					z1l = dewsignz1[getworldid(block.getWorld().getName())][dewsignnow];
					z1m = dewsignz2[getworldid(block.getWorld().getName())][dewsignnow];

				}

				ee = digx >= x1l && digx <= x1m && digy >= y1l && digy <= y1m
						&& digz >= z1l && digz <= z1m;

				goodc1 = ee;

				if (goodc1 == true) {
					dewsignloop[getworldid(block.getWorld().getName())][dewsignnow]++;
					break;
				}
			} // loog sign

			if (goodc1 == true) return dewsignnow;

		} // loop prio

		if (goodc1 == true) return dewsignnow;

		return -1;

	}

	public boolean checkpermissionarea(Block block, Player player,
			String modeevent) {
		boolean ar = checkpermissionarea(block, player, modeevent, true);
		boolean sky = false;
		if (Bukkit.getPluginManager().getPlugin("dewddskyblock") == null) {
			sky = true;
		}
		else {
			sky = api_skyblock.cando(block, player, modeevent);
		}

		boolean pri = api_private.dewddprivate.checkpermissionareasign(block,
				player);

		return !(ar == false && sky == true && pri == false);
	}

	public boolean checkpermissionarea(Block block, Player player,
			String modeevent, boolean test) {

		if (getworldid(block.getWorld().getName()) == -1)
			if (api_admin.dewddadmin.is2moderator(player) == true)
				return true;
			else
				return false;

		if (dewsignmax[getworldid(block.getWorld().getName())] == -1)
			if (api_admin.dewddadmin.is2moderator(player) == true)
				return true;
			else
				return false;

		block.getX();
		block.getY();
		block.getZ();

		boolean goodc1 = false;

		int dewsignnow = checkpermissionarea(block, true);

		if (dewsignnow >= 0) { // true if that is protect
			boolean che1 = false;
			boolean che2 = false;

			if (api_admin.dewddadmin.is2moderator(player) == true) {
				goodc1 = !havethisnameinthishome(getworldid(block.getWorld()
						.getName()), dewsignnow, player.getName());
				return goodc1;
			}

			dewsignloop[getworldid(block.getWorld().getName())][dewsignnow]++;

			if (modeevent.equalsIgnoreCase("right") == true) { // right everyone
				che1 = havethisnameinthishome(getworldid(block.getWorld()
						.getName()), dewsignnow, flag_everyone);

				if (che1 == true) { // if" + tr.gettr("for") + "everyone
					goodc1 = false;

					if (api_admin.dewddadmin.is2moderator(player) == true) {
						// staff
						// can't
						// do
						// it
						goodc1 = true;
					}

					if (block.getTypeId() == 154) {
						goodc1 = havethisnameinthishome(getworldid(block
								.getWorld().getName()), dewsignnow,
								flag_protecthopper);
					}

				}
				else { // not" + tr.gettr("for") + "everyone
					che2 = havethisnameinthishome(getworldid(block.getWorld()
							.getName()), dewsignnow, player.getName());

					goodc1 = !che2;
				}

				if (goodc1 == true)
					if (player.hasPermission(pmainoveride) == true) {
						goodc1 = false;
					}
				return goodc1;

			} // right click
			else if (modeevent.equalsIgnoreCase("delete") == true
					|| modeevent.equalsIgnoreCase("build") == true
					|| modeevent.equalsIgnoreCase("damage") == true
					|| modeevent.equalsIgnoreCase("HangingBreakByEntity") == true
					|| modeevent.equalsIgnoreCase("HangingPlaceEvent") == true) { // build
																					// block
																					// delete
																					// block
																					// picture

				if (block.getTypeId() == 63
						|| block.getTypeId() == 68
						|| block.getTypeId() == 54
						|| block.getTypeId() == 146
						|| modeevent.equalsIgnoreCase("HangingBreakByEntity") == true
						|| modeevent.equalsIgnoreCase("HangingPlaceEvent") == true

				) { // paste chest sign

					che1 = havethisnameinthishome(getworldid(block.getWorld()
							.getName()), dewsignnow, flag_sign);

					if (che1 == true) { // if" + tr.gettr("for") + "<sign>
						goodc1 = false;
						if (api_admin.dewddadmin.is2moderator(player) == true) {
							// staff
							// can't
							// do
							// it
							goodc1 = true;
						}

					}
					else { // if not" + tr.gettr("for") + "sign
						che2 = havethisnameinthishome(getworldid(block
								.getWorld().getName()), dewsignnow,
								player.getName());

						goodc1 = !che2;
					}

				} // neary <sign>
				else { // not
					che2 = havethisnameinthishome(getworldid(block.getWorld()
							.getName()), dewsignnow, player.getName());

					goodc1 = !che2;
				}

				if (goodc1 == true)
					if (player.hasPermission(pmainoveride) == true) {
						goodc1 = false;
					}
				return goodc1;

			}
			else if (modeevent.equalsIgnoreCase("getentity") == true) { // get
																		// item
																		// drop
				goodc1 = false;
				return goodc1;
			}
			else if (modeevent.equalsIgnoreCase("dewset") == true) { // not
																		// right
																		// click

				che2 = havethisnameinthishome(getworldid(block.getWorld()
						.getName()), dewsignnow, player.getName());

				goodc1 = !che2;

				if (goodc1 == true)
					if (player.hasPermission(pmainoveride) == true) {
						goodc1 = false;
					}
				return goodc1;

			} // right click or not
			else { // not right click

				che2 = havethisnameinthishome(getworldid(block.getWorld()
						.getName()), dewsignnow, player.getName());

				goodc1 = !che2;

				if (goodc1 == true)
					if (player.hasPermission(pmainoveride) == true) {
						goodc1 = false;
					}
				return goodc1;

			} // right click or not

		}
		else { // If not who each home
			// staff should't have permission to place block
			if (api_admin.dewddadmin.is2moderator(player) == true) {
				goodc1 = !player.hasPermission(pmainplaceblocknoprotect);
				return goodc1;
			}

			if (modeevent.equalsIgnoreCase("dewset") == true)
				return !player.hasPermission(pmaindewseteverywhere);

			return goodc1;
		}

	}

	public void chestabsorb() {

		new chestabsorb_c();
	}

	public void chestabsorb2() {

		new chestabsorb_c2();
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

	public void createmonster(Player player, EntityType EntityTypeGot, int count) {
		new createmonster_c(player, EntityTypeGot, count);
	}

	public void cutseemblock(Block block123, Player player123, int countja) {
		new cutseemblock_c(block123, player123, countja);
	}

	public void cutwoodsub(Block block123, Player player123, boolean isfirsttime) {
		cutwoodsubc ab = new cutwoodsubc(block123, player123, isfirsttime);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	// decrese item 1
	public boolean decreseitem1(Player player, int itemid, int itemdata,
			boolean forcetruedata) {
		ItemStack itm = null;
		int lenl = 0;

		if (itemid == 0) return false;

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
				if (itm.getTypeId() != 33 && itm.getTypeId() != 34
						&& itm.getTypeId() != 29) {
					continue;
				}
			}

			// dirt
			else if (itemid == 2 || itemid == 3 || itemid == 60) {
				if (itm.getTypeId() != 3 && itm.getTypeId() != 2
						&& itm.getTypeId() != 60) {
					continue;
				}
			}
			// repeater
			else if (itemid == 356 || itemid == 93 || itemid == 94) {
				if (itm.getTypeId() != 356 && itm.getTypeId() != 93
						&& itm.getTypeId() != 94) {
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
			}
			else if (itm.getTypeId() != itemid) {
				continue;
			}

			if (forcetruedata == true)
				if (itm.getData().getData() != itemdata) {
					continue;
				}

			if (itm.getAmount() != 1) {
				itm.setAmount(itm.getAmount() - 1);
				return true;
			}
			else {
				ItemStack emy = player.getItemInHand();
				emy.setTypeId(0);

				player.getInventory().setItem(lenl, emy);

				return true;
			}

		}
		return false;
	}

	public void dewa(Player player, int amount) {

		dewa_mom r = new dewa_mom();
		r.amount = amount;
		r.player = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, r);

	} // dew a

	public void dewbreak(Player player, int handid, byte handdata) {
		new dewbreak_c(player, handid, handdata);
	}

	public boolean dewbuy(Player player) {
		dewbuy_class ar = new dewbuy_class(player);

		Bukkit.getScheduler().runTask(ac, ar);

		return ar.isok;

	}

	public void dewbuydelete(Player player) {
		new dewbuydelete_c(player);
	}

	public void dewbuyremove(Player player) {
		new dewbuyremove_c(player);
	}

	public void dewbuyzone(Player player, Block block2) {

		new dewbuyzone_c(player, block2);
	}

	public void dewcopy(Player player) {
		dewcopy_c abr = new dewcopy_c(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abr);
	}

	public void dewdelete(Player player, int handid, byte handdata) {
		new dewdelete_c(player, handid, handdata);
	}

	public void dewdig(Player player) {
		new dewdig_c(player);
	}

	public void dewdown(Player player, int handid, byte handdata) {

		new dewdown_c(player, handid, handdata);
	}

	public void dewextend(Player player) {
		new dewextend_c(player);
	}

	public void dewfill(Player player, int handid, byte handdata) {

		dewfill_mom aer = new dewfill_mom(player, handid, handdata);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewfullcircle(Player player, int handid, byte handdata) {
		new dewfullcircle_c(player, handid, handdata);
	}

	@Override
	public boolean dewps_add(String message, Player player) {
		int xyz = checkpermissionarea(player.getLocation().getBlock(), true);

		String m[] = message.split("\\s+");

		if (xyz == -1) {
			player.sendMessage(dprint.r.color("ptdew&dewdd : "
					+ tr.gettr("this_area_don't_have_protect")));
			return false;
		}

		if (m.length != 2) {
			player.sendMessage(dprint.r.color("dewadd "
					+ tr.gettr("need_argument_player_or_flag")));
			player.sendMessage(dprint.r.color("dewadd <name or flag>"));
			player.sendMessage(dprint.r.color("dewadd ptdew"));
			player.sendMessage(dprint.r.color("dewadd "
					+ tr.gettr("your_friend_name")));

			player.sendMessage(dprint.r.color("flags list :"));
			player.sendMessage(dprint.r.color(flag_monster + "  "
					+ tr.gettr("allow_monster_spawn_at_your_zone")));
			player.sendMessage(dprint.r.color(flag_pvp + "  "
					+ tr.gettr("allow_to_hit_player")));
			player.sendMessage(dprint.r.color(flag_stopwater
					+ tr.gettr("stop_lava_and_water_flowing")));
			player.sendMessage(dprint.r.color(flag_noinwater
					+ tr.gettr("not_allow_lava_and_water_flowing_to_your_zone")));
			player.sendMessage(dprint.r.color(flag_nooutwater
					+ tr.gettr("not_allow_lava_and_water_flowing_to_your_zone")));
			player.sendMessage(dprint.r.color(flag_everyone
					+ tr.gettr("allow_everyone_rightclick")));
			player.sendMessage(dprint.r.color(flag_sign
					+ tr.gettr("not_allow_lava_and_water_flowing_to_your_zone")));
			player.sendMessage(dprint.r.color(flag_sell
					+ tr.gettr("explain_sell_flag")
					+ tr.gettr("explain_sell_flag_2")));

			player.sendMessage(dprint.r.color(flag_protectanimal
					+ tr.gettr("not_allow_hit_animal")));
			player.sendMessage(dprint.r.color(flag_protecthopper
					+ tr.gettr("not_allow_another_right_click_at_hopper")));

			return false;
		}

		String str11 = m[1];
		// got player name

		dprint.r.printC("ptdew&dewdd : dewadd name = '" + str11 + "'");
		System.out.println("ptdew&dewdd : dewadd " + tr.gettr("home_id_is")
				+ "  = '" + xyz + "'");

		if (dewsignname[getworldid(player.getWorld().getName())][xyz][0]
				.equalsIgnoreCase(player.getName()) == false
				&& player.hasPermission(pmaindewbuymodifymember) == false) {
			player.sendMessage(dprint.r.color(tr.gettr("owner_is")
					+ " ..."
					+ dewsignname[getworldid(player.getWorld().getName())][xyz][0]));
			return false;
		}

		/*
		 * if (api_admin.dewddadmin.issubsubadminname(str11) == true
		 * && api_admin.dewddadmin.is2admin(player) == false
		 * && api_admin.dewddadmin.is2moderator(player) == false) {
		 * 
		 * player.sendMessage(dprint.r.color("ptdew&dewdd : "
		 * + tr.gettr("member_can't_add_staff_to_your_zone")
		 * + dewsignname[getworldid(player.getWorld().getName())][xyz][0]));
		 * 
		 * return false;
		 * }
		 */

		// add member but is staff
		/*
		 * if (api_admin.dewddadmin.issubsubadminname(str11) == false
		 * && api_admin.dewddadmin.isadminname(str11) == false
		 * && api_admin.dewddadmin.is2moderator(player) == true) {
		 * 
		 * if (str11.equalsIgnoreCase(flag_sell) == true) {
		 * dprint.r.printAll("ptdew&dewdd : staff " + player.getName()
		 * + tr.gettr("try_to_add_sell_flag_to_his_protect"));
		 * return false;
		 * }
		 * player.sendMessage(dprint.r.color("ptdew&dewdd : "
		 * + tr.gettr("staff_can't_add_member_to_your_zone")
		 * + dewsignname[getworldid(player.getWorld().getName())][xyz][0]));
		 * 
		 * return false;
		 * }
		 */

		for (int ig = 1; ig < dewsignnamemax; ig++)
			if (dewsignname[getworldid(player.getWorld().getName())][xyz][ig]
					.equalsIgnoreCase(str11) == true) {
				player.sendMessage(dprint.r.color(tr
						.gettr("this_name_already_added_can't_add")
						+ str11
						+ " to  " + xyz));
				return false;
			}

		for (int ig = 1; ig < dewsignnamemax; ig++)
			if (dewsignname[getworldid(player.getWorld().getName())][xyz][ig]
					.equalsIgnoreCase("") == true
					|| dewsignname[getworldid(player.getWorld().getName())][xyz][ig]
							.equalsIgnoreCase("null") == true) {

				dewsignname[getworldid(player.getWorld().getName())][xyz][ig] = str11;
				player.sendMessage(dprint.r.color("added " + str11 + " to  "
						+ xyz));
				savesignfile(-1, getworldid(player.getWorld().getName()));
				return true;
			}

		player.sendMessage(dprint.r.color(tr
				.gettr("can't_find_free_slot_for_add_this_player")));
		return false;
	}

	@Override
	public boolean dewps_list(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dewps_remove(String message, Player player) {
		String m[] = message.split("\\s+");
		if (m.length != 2) {
			player.sendMessage(dprint.r.color("/dewremove <name>"));
			return false;
		}

		String str11 = m[1];
		// got player name

		int xyz = checkpermissionarea(player.getLocation().getBlock(), true);

		dprint.r.printC("ptdew&dewdd : dewremove name = '" + str11 + "'");
		System.out.println("ptdew&dewdd : dewremove "
				+ tr.gettr("search_home_id") + "= '" + xyz + "'");

		if (xyz == -1) {
			player.sendMessage(dprint.r.color("ptdew&dewdd : "
					+ tr.gettr("this_area_don't_have_protect")));
			return false;
		}

		if (dewsignname[getworldid(player.getWorld().getName())][xyz][0]
				.equalsIgnoreCase(player.getName()) == false
				&& player.hasPermission(pmaindewbuymodifymember) == false) {
			player.sendMessage(dprint.r.color(tr.gettr("owner_is")
					+ " ..."
					+ dewsignname[getworldid(player.getWorld().getName())][xyz][0]));
			return false;
		}

		for (int ig = 1; ig < dewsignnamemax; ig++)
			if (dewsignname[getworldid(player.getWorld().getName())][xyz][ig]
					.equalsIgnoreCase(str11) == true) {

				dewsignname[getworldid(player.getWorld().getName())][xyz][ig] = "null";
				player.sendMessage(dprint.r.color("removed " + str11
						+ " from  " + xyz));
				return true;
			}

		player.sendMessage(dprint.r.color(tr
				.gettr("can't_find_this_player_on_this_zone")));
		return false;
	}

	public void dewselectcube(Player player, int rad) {
		new dewselectcube_c(player, rad);
	}

	public void dewselectprotect(Player player) {
		new dewselectprotect_c(player);
	}

	public void dewset(Player player, int e1, byte e2, int e3, byte e4,
			boolean invert) {
		dewset_mom aer = new dewset_mom(player, e1, e2, e3, e4, invert);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);
	}

	public void dewsetblock(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewsetblock_mom aer = new dewsetblock_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetl(Player player, int handid, byte handdata) {

		dewsetl_mom aer = new dewsetl_mom(player, handid, handdata);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetlaround(Player player, int handid, byte handdata) {
		dewselectcube(player, 3);

		dewsetl_mom aer = new dewsetl_mom(player, handid, handdata);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetprivate(Player player) {
		new dewsetprivate_c(player);
	}

	public void dewsetroom(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewsetroom_mom aer = new dewsetroom_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewsetwall(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewsetwall_mom aer = new dewsetwall_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewspreadcircle(Player player, int handid, byte handdata) {

		new dewspreadcircle_c(player, handid, handdata);
	}

	public void dewspreadq(Player player, int handid, byte handdata) {
		bd.clear();
		new dewspreadq_c(player, handid, handdata, true);
	}

	public void dewtime(Player player) {
		new dewtime_c(player);
	}

	public void dewwallcircle(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewwallcircle_mom aer = new dewwallcircle_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	// fixtool

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	// fixtool itemstack and player
	// getfreeselect
	public int getfreeselect(Player player) {
		int lp1 = 0;

		boolean foundza = false;
		// clear select array
		for (lp1 = 0; lp1 < selectmax; lp1++)
			if (selectname[lp1] == null
					|| selectname[lp1].equalsIgnoreCase("") == true) {
				selectname[lp1] = "null";
				selectx1[lp1] = 0;
				selecty1[lp1] = 0;
				selectz1[lp1] = 0;
				selectx2[lp1] = 0;
				selecty2[lp1] = 0;
				selectz2[lp1] = 0;
				dewaxe[lp1] = true;
				hidemode[lp1] = false;
				d4[lp1] = 1;
				chatever[lp1] = false;
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
				hidemode[lp1] = false;
				dewaxe[lp1] = true;
				d4[lp1] = 1;
				chatever[lp1] = false;
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

		dprint.r.printC("ptdew&dewdd : "
				+ tr.gettr("error_getfreeselect_return_-1"));
		return -1;
	}

	public String getmaterialrealname(String gname) {

		for (Material en : Material.values())
			if (en.name().toLowerCase().indexOf(gname.toLowerCase()) > -1) {

				dprint.r.printC("found material real name = " + en.name());
				return en.name();
			}

		return gname;
	}

	// getfreeselect

	// getselectblock //" + tr.gettr("for") + "dewset or dewfill or dewdelete
	public Block[] getselectblock(int getid, Player player) {

		int adderB = -1;
		int countblock = (2 + Math.abs(selectx1[getid] - selectx2[getid]))
				* (2 + Math.abs(selecty1[getid] - selecty2[getid]))
				* (2 + Math.abs(selectz1[getid] - selectz2[getid]));

		if (countblock > 2560000
				&& api_admin.dewddadmin.is2admin(player) == false) {
			player.sendMessage(dprint.r.color(tr
					.gettr("only_admin_can_dewset_more_than_limited_block")
					+ "2,560,000"));
			Block blocktemp[] = null;

			return blocktemp;
		}

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
		}
		else {
			mx = selectx2[getid];
			lx = selectx1[getid];
		}

		if (selecty1[getid] >= selecty2[getid]) {
			my = selecty1[getid];
			ly = selecty2[getid];
		}
		else {
			my = selecty2[getid];
			ly = selecty1[getid];
		}

		if (selectz1[getid] >= selectz2[getid]) {
			mz = selectz1[getid];
			lz = selectz2[getid];
		}
		else {
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
		}
		else {
			mx = selectx2[getid];
			lx = selectx1[getid];
		}

		if (selecty1[getid] >= selecty2[getid]) {
			my = selecty1[getid];
			ly = selecty2[getid];
		}
		else {
			my = selecty2[getid];
			ly = selecty1[getid];
		}

		if (selectz1[getid] >= selectz2[getid]) {
			mz = selectz1[getid];
			lz = selectz2[getid];
		}
		else {
			mz = selectz2[getid];
			lz = selectz1[getid];
		}

		for (int xl = lx; xl <= mx; xl++) {
			for (int yl = ly; yl <= my; yl++) {
				for (int zl = lz; zl <= mz; zl++) {
					Block blb = player.getWorld().getBlockAt(xl, yl, zl);
					if (checkpermissionarea(blb, player, "buy") == true) {
						// dprint.r.printAll("...");
						countall = -1;
						return countall;
					}
					else {
						countall++;
					}

				}
			}
		}

		return countall;

	} // getselectblock

	public int getwallid() {
		int g = 0;
		Random rn = new Random();

		while (g <= 0 || isdewset(g) == false) {
			g = rn.nextInt(500);
		}

		return g;
	}

	public int getworldid(String wowo) {
		for (int d = 0; d < dewworldlistmax; d++)
			if (wowo.equalsIgnoreCase(dewworldlist[d]) == true) return d;

		// dprint.r.printAll("Error getworldid " + wowo + " so return -1");
		return -1;
	}

	public String getworldname(int idworld) {
		String aa = "ptdew_dewdd_" + dewworldlist[idworld] + ".txt";
		return aa;
	}

	// riceblockiron

	public void gift(Player player, int a1, byte a2) {
		int moveyet = 0;
		boolean okok = false;

		Block b = null;
		for (int x = giftblock.getX() - 50; x <= giftblock.getX() + 50; x++) {

			for (int z = giftblock.getZ() - 50; z <= giftblock.getZ() + 50; z++) {

				for (int y = giftblock.getY() - 70; y <= giftblock.getY() + 70; y++) {
					if (y < 0 || y > 254) {
						continue;
					}

					b = giftblock.getWorld().getBlockAt(x, y, z);

					if (b.getTypeId() == Material.CHEST.getId()
							|| b.getTypeId() == Material.TRAPPED_CHEST.getId()) {
						Chest c = (Chest) b.getState();

						for (ItemStack ic : c.getInventory().getContents()) {
							if (ic == null) {
								continue;
							}

							okok = false;
							if (ic.getTypeId() == a1) if (a2 == -29) {
								okok = true;
							}
							else if (a2 == ic.getData().getData()) {
								okok = true;
							}

							if (moveyet > 10) {
								player.sendMessage(dprint.r.color("ptdew&dewdd : "
										+ tr.gettr("gift_you_got_item_10_times_enough")));
								return;
							}

							if (okok == true) {
								ItemStack gj = new ItemStack(ic);
								player.getLocation().getWorld()
										.dropItem(player.getLocation(), gj);
								moveyet++;
								c.getInventory().remove(ic);
								c.update();
								player.sendMessage(dprint.r.color(moveyet
										+ " ... " + gj.getTypeId() + ":"
										+ gj.getData() + tr.gettr("amount")
										+ " = " + gj.getAmount()));
							}

						}
					}
				}
			}

		}

		if (moveyet == 0) {
			player.sendMessage(dprint.r.color(tr.gettr("gift_not_found_item")));
		}

	}

	public void gotohell(Player player, Location lo1, Location lo2) {
		// copy to hell
		gotohellt ae = new gotohellt(player, lo1, lo2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ae);
	}

	// sandmelon

	// ironorefreeze

	// Check Permission Area block
	// checkidhome
	public boolean havethisnameinthishome(int worldid, int homeid, String namee) {
		if (homeid < 0 || homeid > dewsignmax[worldid] - 1) return false;
		for (int cheL = 0; cheL < dewsignnamemax; cheL++)
			if (dewsignname[worldid][homeid][cheL].equalsIgnoreCase(namee) == true)
				return true;
		return false;
	}

	public void hideshowrun(Player player) {
		new hideshowrun_c(player);

	}

	public boolean isNumeric(String str) {
		for (char c : str.toCharArray())
			if (!Character.isDigit(c)) return false;
		return true;
	}

	public boolean isprotectitemid(int importid) {
		switch (importid) {
		case 54: // chest
		case 19: // sponge
		case 23: // dispenser
		case 61: // fu
		case 62: // fu
		case 63: // sign post
		case 68: // wall sign
		case 95: // lock chest
		case 145: // anvil
		case 138: // beacon
		case 130: // enderchest
		case 116: // enchant
		case 52: // monster spawner
		case 117: // brewing
		case 154: // hopper

			return true;
		default:
			return false;
		}
	}

	// obsidianabsorb

	// boolean firstrun19 = false;

	public boolean isunsortid(ItemStack impo) {

		if (impo.getType().getMaxDurability() > 0) return true;

		if (impo.getEnchantments().size() > 0) return true;

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

	public void item55delete(Block block, Player player, int id, byte dat)
			throws UserDoesNotExistException, NoLoanPermittedException {
		item55deletec ab = new item55deletec(block, player, id, dat);

		amount3 = 0;
		amount2 = 0;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	public void linkurl(Player player, String url) {
		if (url.endsWith("?fb") == true || url.endsWith("?facebook") == true) {
			dprint.r.printA("ptdew&dewdd : my facebook > https://www.facebook.com/dewddminecraft");
		}

		if (url.endsWith("?e-mail") == true || url.endsWith("?mail") == true) {
			dprint.r.printA("ptdew&dewdd : my e-mail > dew_patipat@hotmail.com");
		}

		if (url.endsWith("?youtube") == true || url.endsWith("?video") == true) {
			dprint.r.printA("ptdew&dewdd : my youtube > http://www.youtube.com/ptdew");
			dprint.r.printA("ptdew&dewdd : my youtube 2 > http://www.youtube.com/ptdew2");
		}

		if (url.endsWith("?plugin") == true || url.endsWith("?pl") == true) {
			dprint.r.printA("ptdew&dewdd : my plugin > http://www.youtube.com/playlist?list=PLlM9Jjda8OZeMEuUtVxyXu2XF62rqzt2j");
		}

	}

	public void loadgiftfile() {
		String worldf = "ptdew_dewdd_gift_position.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			giftblock = null;
			fff.createNewFile();

			System.out.println("ptdeW&DewDD : "
					+ tr.gettr("starting_loading_file") + filena);
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

			String[] m;

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				m = strLine.split("\\s+");
				giftblock = Bukkit.getWorld(m[0]).getBlockAt(
						Integer.parseInt(m[1]), Integer.parseInt(m[2]),
						Integer.parseInt(m[3]));

			}

			System.out.println("ptdew&DewDD Main: Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println(tr.gettr("error_while_loading_file") + filena
					+ " " + e.getMessage());
		}
	}

	public void loadmainfile() {
		loadworldfile();
		loadsignfile();
		loadgiftfile();
		loaddewsetlistblockfile();
	}

	// savesignfiledefrag

	// loadsignfile
	public void loadsignfile() {

		dewsignx1 = new int[dewworldlistmax][dewsignlimit];
		dewsigny1 = new int[dewworldlistmax][dewsignlimit];
		dewsignz1 = new int[dewworldlistmax][dewsignlimit];

		dewsignx2 = new int[dewworldlistmax][dewsignlimit];
		dewsigny2 = new int[dewworldlistmax][dewsignlimit];
		dewsignz2 = new int[dewworldlistmax][dewsignlimit];

		dewsignloop = new int[dewworldlistmax][dewsignlimit];
		dewsignname = new String[dewworldlistmax][dewsignlimit][dewsignnamemax];

		dewsignmax = new int[dewworldlistmax];
		for (int cx = 0; cx < dewworldlistmax; cx++) {
			dewsignmax[cx] = 0;
		}

		File dir = new File(folder_name);
		dir.mkdir();

		int wlo = 0;
		for (wlo = 0; wlo < dewworldlistmax; wlo++) {
			// ****************************
			try { // try

				// Open the file that is the first
				// command line parameter

				String filena = folder_name + File.separator
						+ getworldname(wlo);
				File fff = new File(filena);
				fff.createNewFile();

				dewsignmax[wlo] = 0;

				dprint.r.printC("ptdew&dewdd : Starting Loading " + filena);

				FileInputStream fstream = new FileInputStream(filena);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;
				// Read File Line By Line
				int moden = 0;

				while ((strLine = br.readLine()) != null) {
					// Print the content on the console

					switch (moden) {
					case 0: // dewsignmax
						// dewsignmax[wlo] = Integer.parseInt(strLine);
						// dewsignmax[wlo] = 1;

						break;

					case 1: // name1
						dewsignmax[wlo]++;

						dewsignname[wlo][dewsignmax[wlo] - 1][0] = strLine;
						break;
					case 2: // name2
						dewsignname[wlo][dewsignmax[wlo] - 1][1] = strLine;
						break;
					case 3: // name3
						dewsignname[wlo][dewsignmax[wlo] - 1][2] = strLine;
						break;
					case 4: // name4
						dewsignname[wlo][dewsignmax[wlo] - 1][3] = strLine;
						break;
					case 5: // name5
						dewsignname[wlo][dewsignmax[wlo] - 1][4] = strLine;
						break;
					case 6: // name6
						dewsignname[wlo][dewsignmax[wlo] - 1][5] = strLine;
						break;
					case 7: // name7
						dewsignname[wlo][dewsignmax[wlo] - 1][6] = strLine;
						break;
					case 8: // name8
						dewsignname[wlo][dewsignmax[wlo] - 1][7] = strLine;
						break;
					case 9: // name9
						dewsignname[wlo][dewsignmax[wlo] - 1][8] = strLine;
						break;
					case 10: // name10
						dewsignname[wlo][dewsignmax[wlo] - 1][9] = strLine;
						break;
					case 11: // name11
						dewsignname[wlo][dewsignmax[wlo] - 1][10] = strLine;
						break;
					case 12: // name12
						dewsignname[wlo][dewsignmax[wlo] - 1][11] = strLine;
						break;
					case 13: // name13
						dewsignname[wlo][dewsignmax[wlo] - 1][12] = strLine;
						break;
					case 14: // name14
						dewsignname[wlo][dewsignmax[wlo] - 1][13] = strLine;
						break;
					case 15: // name15
						dewsignname[wlo][dewsignmax[wlo] - 1][14] = strLine;
						break;
					case 16: // name16
						dewsignname[wlo][dewsignmax[wlo] - 1][15] = strLine;
						break;
					case 17: // name17
						dewsignname[wlo][dewsignmax[wlo] - 1][16] = strLine;
						break;
					case 18: // name18
						dewsignname[wlo][dewsignmax[wlo] - 1][17] = strLine;
						break;
					case 19: // name19
						dewsignname[wlo][dewsignmax[wlo] - 1][18] = strLine;
						break;
					case 20: // name20
						dewsignname[wlo][dewsignmax[wlo] - 1][19] = strLine;
						break;

					case 21: // x1
						dewsignx1[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						break;
					case 22: // y1
						dewsigny1[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						break;
					case 23: // z1
						dewsignz1[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						break;
					case 24: // x2
						dewsignx2[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						break;
					case 25: // y2
						dewsigny2[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						break;
					case 26: // z2
						dewsignz2[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						break;
					case 27: // loop count
						dewsignloop[wlo][dewsignmax[wlo] - 1] = Integer
								.parseInt(strLine);
						dprint.r.printC("loaded sign of world " + wlo
								+ " sign id = " + dewsignmax[wlo]);
						moden = 0;
						break;
					}

					moden++;

				}

				dprint.r.printC("ptdew&dewdd : loaded " + filena);

				in.close();
			}
			catch (Exception e) {// Catch exception if any
				System.err.println("LoadSignFile Error: " + e.getMessage());
			} // catch
		}
	}

	public void loadworldfile() {
		String worldf = "ptdew_dewdd_worlds_sign.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			dewworldlist = new String[15];
			dewworldlistmax = 0;
			fff.createNewFile();

			System.out.println("ptdeW&DewDD Main : " + filena);
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

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				dewworldlistmax++;
				dewworldlist[dewworldlistmax - 1] = strLine;
				dprint.r.printC("world sign " + (dewworldlistmax - 1) + " = "
						+ dewworldlist[dewworldlistmax - 1]);
			}

			System.out.println("ptdew&DewDD Main: Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error load " + filena + e.getMessage());
		}
	}

	public void protectrail(Block block, Player player) {

		if (api_private.dewddprivate.checkpermissionareasign(block) == false) {
			protectrailrun(block, player); // add protect
		}

		Block b2 = null;

		for (int x = -4; x <= 4; x++) {
			for (int y = -4; y <= 4; y++) {
				for (int z = -4; z <= 4; z++) {

					if (x == 0 && y == 0 && z == 0) {
						continue;
					}

					b2 = block.getRelative(x, y, z);

					if (b2.getTypeId() != 27 && b2.getTypeId() != 66) {
						continue;
					}
					if (api_private.dewddprivate.checkpermissionareasign(b2) == true) {
						continue;
					}

					dprint.r.printAll(tr.gettr("running") + " " + b2.getX()
							+ "," + b2.getY() + "," + b2.getZ());
					protectrail(b2, player);
					dprint.r.printAll(tr.gettr("running") + " "
							+ tr.gettr("done") + " " + b2.getX() + ","
							+ b2.getY() + "," + b2.getZ());

				}
			}
		}
	}

	// randomplaynote

	public boolean protectrailrun(Block block, Player player) {

		boolean ok = false;
		boolean spa = false;

		// searh near" + tr.gettr("for") + "space and create sign
		Block b2 = null;
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}

					b2 = block.getRelative(x, y, z);

					if (b2.getTypeId() == 0) { // search near
						spa = false;
						if (b2.getRelative(0, -1, 0).getTypeId() == 0) {
							b2.getRelative(0, -1, 0).setTypeId(5);
							spa = true;
						}
						else {
							spa = true;
						}

						if (spa == false) {
							continue;
						}

						dprint.r.printAll(tr.gettr("found_space") + b2.getX()
								+ "," + b2.getY() + "," + b2.getY());
						b2.setTypeId(63);
						Sign sign = (Sign) b2.getState();
						sign.setLine(0, "[dewdd]");
						sign.setLine(1, player.getName());
						sign.update(true);
						ok = true;
						dprint.r.printAll(tr.gettr("created_sign") + " at : "
								+ b2.getX() + "," + b2.getY() + "," + b2.getY());
						break;
					}

				}
				if (ok == true) {
					break;
				}

			}
			if (ok == true) {
				break;
			}
		}

		if (ok == true) return true;

		boolean ok2 = false;
		if (ok == false) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						if (x == 0 && y == 0 && z == 0) {
							continue;
						}
						b2 = block.getRelative(x, y, z);

						ok2 = protectrailrun(b2, player);
						if (ok2 == true) return true;
					}
				}
			}
		}

		return false;
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

	public int randomnumberint() {
		randomInt = randomG.nextInt(50);
		randomInt += 1;
		return randomInt;
	}

	public void randomplaynote(Location player) {
		randomplaynote_c arr = new randomplaynote_c(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, arr);
	}

	public void redtorchchest(Block block, Player player) {

		// auto give item" + tr.gettr("for") + "all player on server
		redtorchchestt ee = new redtorchchestt(block, player);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ee);

	}

	public void runter(String e, Player player, int a1, byte a2) {
		Material ik = Material.getMaterial(a1);

		if (ik == null) {
			player.sendMessage(dprint.r.color(tr.gettr("what_the_hell_item")
					+ a1 + ":" + a2));
			return;
		}

		if (ik.isBlock() == false) {
			player.sendMessage(dprint.r.color(tr.gettr("it_is_not_a_block")
					+ a1 + ":" + a2));
			return;
		}

		if (isdewset(a1) == false) {
			player.sendMessage(dprint.r.color(tr
					.gettr("this_item_not_allow_for_dewset")
					+ tr.gettr("for")
					+ "dewset " + a1 + ":" + a2));
			return;
		}

		if (dewddtps.tps.getTPS() < 17) {
			dprint.r.printAll("ptdew&dewdd : dewset "
					+ tr.gettr("tps_below_than_17") + dewddtps.tps.getTPS());

			return;
		}

		if (a1 == 0)
			if (player.hasPermission(pmaindelete) == false) {
				player.sendMessage(dprint.r.color("ptdew&dewdd : "
						+ tr.gettr("you_don't_have_permission_for_delete")));
				return;
			}

		if (e.equalsIgnoreCase("dewsetwall") || e.equalsIgnoreCase("dsw")) {
			dewsetwall(player, a1, a2, false);
			return;
		}
		if (e.equalsIgnoreCase("dewfillwall") || e.equalsIgnoreCase("dfw")) {
			dewsetwall(player, a1, a2, true);
			return;
		}

		if (e.equalsIgnoreCase("dewfill") || e.equalsIgnoreCase("df")) {
			dewfill(player, a1, a2);
			return;
		}

		if (e.equalsIgnoreCase("dewsetblock") || e.equalsIgnoreCase("dsb")) {
			dewsetblock(player, a1, a2, false);
			return;
		}
		if (e.equalsIgnoreCase("dewfillblock") || e.equalsIgnoreCase("dfb")) {
			dewsetblock(player, a1, a2, true);
			return;
		}

		if (e.equalsIgnoreCase("dewsetroom") || e.equalsIgnoreCase("dsr")) {
			dewsetroom(player, a1, a2, false);
			return;
		}
		if (e.equalsIgnoreCase("dewfillroom") || e.equalsIgnoreCase("dfr")) {
			dewsetroom(player, a1, a2, true);
			return;
		}

		if (e.equalsIgnoreCase("dewfillcircle")
				|| e.equalsIgnoreCase("dewwallcircle")
				|| e.equalsIgnoreCase("dwc") || e.equalsIgnoreCase("dfc")) {
			dewwallcircle(player, a1, a2, true);
			return;
		}
		if (e.equalsIgnoreCase("dewsetcircle")) {
			dewwallcircle(player, a1, a2, false);
			return;
		}
		if (e.equalsIgnoreCase("dewfullcircle")) {
			dewfullcircle(player, a1, a2);
			return;
		}

		runter_mom aex = new runter_mom(player, e, a1, a2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aex);

	}

	// savesignfile
	public void savesignfile(int exceptint, int worldid) {

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + getworldname(worldid);
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd : Start saving " + filena);
			fwriter = new FileWriter(fff);

			fwriter.write(dewsignmax[worldid]
					+ System.getProperty("line.separator"));

			for (int y = 0; y < dewsignmax[worldid]; y++) {
				if (exceptint > -1) if (y == exceptint) {
					continue;
				}

				for (int whome = 0; whome < dewsignnamemax; whome++)
					if (dewsignname[worldid][y][whome] != null) {
						fwriter.write(dewsignname[worldid][y][whome]
								+ System.getProperty("line.separator"));
					}
					else {
						fwriter.write("null"
								+ System.getProperty("line.separator"));
					}

				fwriter.write(dewsignx1[worldid][y]
						+ System.getProperty("line.separator"));
				fwriter.write(dewsigny1[worldid][y]
						+ System.getProperty("line.separator"));
				fwriter.write(dewsignz1[worldid][y]
						+ System.getProperty("line.separator"));

				fwriter.write(dewsignx2[worldid][y]
						+ System.getProperty("line.separator"));
				fwriter.write(dewsigny2[worldid][y]
						+ System.getProperty("line.separator"));
				fwriter.write(dewsignz2[worldid][y]
						+ System.getProperty("line.separator"));

				fwriter.write(dewsignloop[worldid][y]
						+ System.getProperty("line.separator"));

				// dprint.r.printC ("ptdew&dewdd : Saved y= " + y );

			}

			fwriter.close();
			dprint.r.printC("ptdew&dewdd : saved " + filena);
			return;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		// dprint.r.printC ("ptdew&dewdd : save " + tr.gettr("done") + "...");

		// ***************************88

		// ******************************
	}

	public void saveworld() {
		for (World world : Bukkit.getWorlds()) {
			world.save();
			for (Player pla : world.getPlayers()) {
				pla.saveData();
			}
		}
	}

	public void seedglow(Block block, Player player) {
		seedglowc arr = new seedglowc(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, arr);
	}

	public void showwhohome(Block block, Player player) {
		int xyz = checkpermissionarea(block, true);
		if (xyz == -1) {
			player.sendMessage(dprint.r.color("ptdew&dewdd : "
					+ tr.gettr("tree_check_protect_and_not_found")));
			return;
		}

		player.sendMessage(dprint.r.color("ptdew&dewdd : "
				+ tr.gettr("tree_thiszoneprotectedby")));

		for (int xxx = 0; xxx < dewsignnamemax; xxx++)
			if (dewsignname[getworldid(block.getWorld().getName())][xyz][xxx]
					.equalsIgnoreCase("null") == false) {
				player.sendMessage(dprint.r
						.color(xxx
								+ " = "
								+ dewsignname[getworldid(block.getWorld()
										.getName())][xyz][xxx]));
			}

		return;
	}

	public void soiladdseedrecusive(Block block, Player player, int seedid,
			boolean first) {

		soiladdseedc ab = new soiladdseedc(block, player, seedid, first);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	public void superdestroy(Block block, Player player, int dleft, int typeid,
			byte typedata) {
		if (player.getItemInHand().getDurability() < player.getItemInHand()
				.getType().getMaxDurability()) {

			if (block.getTypeId() != typeid || block.getData() != typedata)
				return;

			if (api_admin.dewddadmin.is2vip(player) == false) return;

			if (checkpermissionarea(block, player, "delete") == true) return;

			player.getItemInHand().setDurability(
					(short) (player.getItemInHand().getDurability() + 1));
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
						if (dleft == 0) return;
						superdestroy(block2, player, dleft, typeid, typedata);
					}
				}
			}

		}
	}

} // dew minecraft