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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;
import li.IDDataType;
import li.LXRXLZRZType;

public class MainLib extends dewset_interface {
	
	public dewset dew  = new dewset();
	
	
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
	
	class autosortchest2_class implements Runnable {
		private Block block;
		private Player player;

		public autosortchest2_class(Block block, Player player) {
			this.block = block;
			this.player = player;
		}
		

		@Override
		public void run() {

			if (block.getTypeId() != Material.CHEST.getId() && block.getTypeId() != Material.TRAPPED_CHEST.getId()) {

				player.sendMessage(dprint.r.color(tr.gettr("auto_sort_chest_2_is_not_chest")));
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
						if (sdata[l1].getData().getData() == it.getData().getData()) {

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
						player.sendMessage(dprint.r.color(tr.gettr("error_auto_sort_chest2_can't_find_empty_slot")));
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
					} else {
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
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
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
			
				// search nearby box and sign ... ummm yes
				block = player.getLocation().getBlock();

				/*
				 * if (checkpermissionarea(block)== false) { not protect area
				 * continue; }
				 */
				if (dew.cando_all(block, player, "build") == false) {
					// build
					continue;
				}

				for (int gx = 0 - d4; gx <= 0 + d4; gx++) {
					for (int gy = 0 - d4; gy <= 0 + d4; gy++) {
						for (int gz = 0 - d4; gz <= 0 + d4; gz++) {
							// first search sign
							block = player.getLocation().getBlock().getRelative(gx, gy, gz);
							if (block == null) {
								continue;
							}

							if (block.getTypeId() != 63 && block.getTypeId() != 68) {
								continue;
							}

							Sign sign = (Sign) block.getState();
							if (sign.getLine(0).equalsIgnoreCase("dewtobox")) {
								sign.setLine(0, "[dewtobox]");
								sign.update(true);
							}

							if (sign.getLine(0).equalsIgnoreCase("[dewtobox]") == true) {
								// player.sendMessage(dprint.r.color("found
								// dewtobox sign : "
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
											block2 = block.getRelative(ax, ay, az);
											if (block2 == null) {
												continue;
											}

											if (block2.getTypeId() != 54) {
												continue;
											}

											// player.sendMessage(dprint.r.color("found
											// dewtobox chest : "
											// +
											// block2.getLocation().getBlockX()
											// +
											// ","
											// +
											// block2.getLocation().getBlockY()
											// +
											// "," +
											// block2.getLocation().getBlockZ());

											slotp = player.getInventory().first(intb);
											if (slotp == -1) {
												continue;
											}

											chest = (Chest) block2.getState();

											int chestslot = -1;
											chestslot = chest.getInventory().firstEmpty();
											if (chestslot == -1) {
												continue;
											}

											// ready to move
											chest.getInventory().addItem(player.getInventory().getItem(slotp));

											player.getInventory().clear(slotp);

											player.sendMessage(
													dprint.r.color("[dewtobox] " + tr.gettr("moved") + intb));

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
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {

			long nn = System.currentTimeMillis();

			if (nn - lastsort2 < 100) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 10);
				return;
			}

			lastsort2 = nn;

			int d4 = 30;
			Block block = null;

			for (Player player : Bukkit.getOnlinePlayers()) {
				
				// search nearby box and sign ... ummm yes
				block = player.getLocation().getBlock();

				/*
				 * if (checkpermissionarea(block)== false) { not protect area
				 * continue; }
				 */
				if (dew.cando_all(block, player, "build") == false) {
					// build
					continue;
				}

				// player.sendMessage(dprint.r.color("searching... dewsortbox
				// sign");

				// search any sign near player
				for (int gx = 0 - d4; gx <= 0 + d4; gx++) {
					for (int gy = 0 - d4; gy <= 0 + d4; gy++) {
						for (int gz = 0 - d4; gz <= 0 + d4; gz++) {
							lastsort2 = nn;

							// first search sign
							block = player.getLocation().getBlock().getRelative(gx, gy, gz);

							if (block.getTypeId() != 63 && block.getTypeId() != 68) {
								continue;
							}

							// dewsortbox
							// dewsorttype

							Sign sign = (Sign) block.getState();
							if (sign.getLine(0).equalsIgnoreCase("dewsortbox")) {
								sign.setLine(0, "[dewsortbox]");
								sign.update(true);
							}

							if (sign.getLine(0).equalsIgnoreCase("[dewsortbox]") == true) {

								/*
								 * player.sendMessage(dprint.r.color(
								 * "cur found dewsortbox sign at " +
								 * block.getX() + "," + block.getY() + "," +
								 * block.getZ());
								 */

								String sorttype = sign.getLine(1);
								if (sorttype.equalsIgnoreCase("")) {
									player.sendMessage(dprint.r.color(tr.gettr("sorttype_name_must_not_null")));
									continue;
								}

								// got sign type
								// search current chest
								Block curchest = chestnearsign(block);

								if (curchest == null) {
									player.sendMessage(dprint.r.color("curchest == null"));

									continue;

								}

								Block curprochest = protochest(block, d4, sorttype);
								if (curprochest == null) {
									player.sendMessage(
											dprint.r.color("curprochest == null > " + d4 + " , " + sorttype));

									continue;

								}

								// player.sendMessage(dprint.r.color("opening
								// curchest..."+
								// curchest.getTypeId());
								Chest curchest1 = (Chest) curchest.getState();
								// player.sendMessage(dprint.r.color("opening
								// curchest done");

								// player.sendMessage(dprint.r.color("opening
								// curprochest..."+
								// curprochest.getTypeId());

								Chest curchestin = (Chest) curprochest.getState();
								// player.sendMessage(dprint.r.color("opening
								// curprochest done");

								// player.sendMessage(dprint.r.color("opened
								// both chest");
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
											temp = block.getLocation().getBlock().getRelative(jx, jy, jz);

											if (temp.getTypeId() != 63 && temp.getTypeId() != 68) {
												continue;
											}

											// dewsortbox
											// dewsorttype

											Sign js = (Sign) temp.getState();

											if (js.getLine(0).equalsIgnoreCase("[dewsortbox]") == true) {

												/*
												 * player.sendMessage(dprint.r.
												 * color (
												 * "swap found dewsortbox at " +
												 * temp.getX() + "," +
												 * temp.getY() + "," +
												 * temp.getZ());
												 */

												String jsorttype = js.getLine(1);
												if (jsorttype.equalsIgnoreCase("")) {
													// player.sendMessage(dprint.r.color("swap_sorttype_name_must_not_null");
													continue;
												}

												// got sign type
												// search current chest
												Block swapchest = chestnearsign(temp);
												if (swapchest == null) {
													// player.sendMessage(dprint.r.color("swapchest
													// == null");
													continue;
												}

												if (swapchest.getLocation().distance(curchest.getLocation()) <= 1) {
													continue;
												}

												Block swapprochest = protochest(temp, d4, jsorttype);
												if (swapprochest == null) {
													// player.sendMessage(dprint.r.color("swapprochest
													// == null");
													continue;
												}

												// player.sendMessage(dprint.r.color("opening
												// swapprochest...");

												Chest swapchestin = (Chest) swapprochest.getState();
												// player.sendMessage(dprint.r.color("opening
												// swapchest...");

												Chest swapchest1 = (Chest) swapchest.getState();

												// player.sendMessage(dprint.r.color("opened
												// both chest swap");

												for (int ikn = 0; ikn < curchest1.getInventory().getSize(); ikn++) {
													ItemStack i1 = curchest1.getInventory().getItem(ikn);

													if (i1 == null) {
														continue;
													}

													// search 1 is not in
													// 1prototype
													int i1proslot = curchestin.getInventory().first(i1.getType());
													if (i1proslot > -1) {
														// player.sendMessage(dprint.r.color("i1proslot
														// > -1");
														continue;
													}

													// if not found it's mean
													// wrong item in cur chest

													// check it that item in
													// second prototype

													if (swapchestin.getInventory().first(i1.getType()) == -1) {
														// player.sendMessage(dprint.r.color("swapchestin
														// can't found i1
														// item");
														continue;
													}

													// time to swap item
													// search free item on
													// second

													int freeslot = swapchest1.getInventory().firstEmpty();
													if (freeslot == -1) {
														// player.sendMessage(dprint.r.color("free
														// slot of swapchest 1
														// is -1");
														continue;
													}

													// time to move
													// player.sendMessage(dprint.r.color("moviing
													// item");
													swapchest1.getInventory().setItem(freeslot, i1);
													curchest1.getInventory().setItem(ikn, new ItemStack(0));

													player.sendMessage(dprint.r.color(
															"swaped item " + i1.getType().name() + " " + i1.getAmount())
															+ " from " + curchest.getX() + "," + curchest.getY() + ","
															+ curchest.getZ() + "[" + sorttype + "]" + " to "
															+ swapchest.getX() + "," + swapchest.getY() + ","
															+ swapchest.getZ() + "[" + jsorttype + "]");

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
		private EntityType EntityTypeGot;
		private Player player;

		public createmonster_c(Player player, EntityType EntityTypeGot, int count) {
			this.player = player;
			this.EntityTypeGot = EntityTypeGot;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
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
								Block block2 = player.getWorld().getBlockAt(lx, ly, lz);
								if (block2.getTypeId() == 0) {
									an = false;
								} else {
									an = true;
								}
							}
						}
					}

					if (an == true) {
						y++;
						if (y > 253 || y < 1)
							return;
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

	// cutwoodsub


	

	class gift_thread implements Runnable {
		private Player player;

		private int a1;
		private byte a2;

		public gift_thread(Player player, int a1, byte a2) {
			this.player = player;
			this.a1 = a1;
			this.a2 = a2;
			
			if (player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printAll(player.getName() + tr.gettr("has cancel gift"));
				
				return;
			}

			long lo = (rnd.nextInt(20) + 1) * 20;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, lo);

		}

		@Override
		public void run() {
			int moveyet = 0;
			boolean okok = false;

			Block b = null;

			ArrayList<Block> blockList = new ArrayList<Block>();
			blockList.clear();

			for (int y = -10; y <= +10; y++)
				for (int z = -10; z <= +10; z++)
					for (int x = -10; x <= +10; x++) {

						Block curBlock = giftblock.getRelative(x, y, z);
						dewset.searchRecursiveBlock(blockList, curBlock, Material.CHEST, (byte) -29);
						dewset.searchRecursiveBlock(blockList, curBlock, Material.TRAPPED_CHEST, (byte) -29);
					}

			for (int y = -10; y <= +10; y++)
				for (int z = -10; z <= +10; z++)
					for (int x = -10; x <= +10; x++) {

						Block curBlock = player.getLocation().getBlock().getRelative(x, y, z);
						dewset.searchRecursiveBlock(blockList, curBlock, Material.CHEST, (byte) -29);
						dewset.searchRecursiveBlock(blockList, curBlock, Material.TRAPPED_CHEST, (byte) -29);
					}

			player.sendMessage("blockList.size() == " + blockList.size() + "  , gift position " + giftblock.getX() + ","
					+ giftblock.getY() + "," + giftblock.getZ());

			for (int index = 0; index < blockList.size(); index++) {

				b = blockList.get(index);

				if (dew.cando_all(b, player, "right") == false) {
					continue;
				}

				if (b.getTypeId() == Material.CHEST.getId() || b.getTypeId() == Material.TRAPPED_CHEST.getId()) {
					Chest c = (Chest) b.getState();

					for (ItemStack ic : c.getInventory().getContents()) {
						if (ic == null) {
							continue;
						}

						okok = false;
						if (ic.getTypeId() == a1)
							if (a2 == -29) {
								okok = true;
							} else if (a2 == ic.getData().getData()) {
								okok = true;
							}

						if (moveyet > 10) {
							player.sendMessage(
									dprint.r.color("ptdew&dewdd : " + tr.gettr("gift_you_got_item_10_times_enough")));
							return;
						}

						if (okok == true) {
							ItemStack gj = new ItemStack(ic);
							player.getLocation().getWorld().dropItem(player.getLocation(), gj);
							moveyet++;
							c.getInventory().remove(ic);
							c.update();
							player.sendMessage(dprint.r.color(moveyet + " ... " + gj.getTypeId() + ":" + gj.getData()
									+ tr.gettr("amount") + " = " + gj.getAmount()));
						}

					}
				}

			}

			if (moveyet == 0) {
				player.sendMessage(dprint.r.color(tr.gettr("gift_not_found_item")));
			}

		}

	}

	class gotohellt implements Runnable {
		private Location lo1 = null;
		private Location lo2 = null;
		private Player player = null;

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
							blo = player.getWorld().getBlockAt(lo1).getRelative(x, y, z);
							if (d == 1) {
								if (blo.getType().isBlock() == false) {
									continue;
								}
							} else if (d == 2)
								if (blo.getType().isBlock() == true) {
									continue;
								}

							blo2 = Bukkit.getWorld("world_nether").getBlockAt(lo2).getRelative(x, y, z);

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
			dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "' " + tr.gettr("what_the_hell"));
			dprint.r.printC("ptdew&dewdd : go to hell '" + player.getName() + "'");
		}
	}

	class redtorchchestt implements Runnable {
		private Block block = null;
		private Player player = null;

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

					dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "' " + tr.gettr("gived_item") + " '"
							+ itm.getType().name() + "' to '" + "[" + pxpluck + "/" + pxpmax + "] "
							+ pxp[pxpluck].getName() + "'");
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

	
	public static JavaPlugin ac = null;

	

	// public double buy1blockmoney = 0.0890932504689118445732202345959;

	

	// *********

	// cutwoodsub

	// Chat Event.class
	// BlockBreakEvent
	public static boolean isrunworld(String worldName) {
		return tr.isrunworld(ac.getName(), worldName);
	}
	// randomplaynote

	

	// decrese item 1

	public long lastsort2 = 0;

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

	public static String folder_name = "plugins" + File.separator + "dewdd_main";
	
	public MainLib() {
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

	public void autosortchest2(Block block, Player player) {
		autosortchest2_class ar = new autosortchest2_class(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);
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

	

	/*public boolean cando_Main(Block block, Player player, String modeevent) {
		RSWorld worldid = getWorld(block.getWorld().getName());

		if (worldid == null)
			if (api_admin.dewddadmin.is2moderator(player) == true)
				return false;
			else
				return true;

		if (worldid.rs.size() == 0)
			if (api_admin.dewddadmin.is2moderator(player) == true)
				return false;
			else
				return true;

		block.getX();
		block.getY();
		block.getZ();

		int playerInSlot = -1;
		int playerInSlot2 = -1;

		int dewsignnow = getProtectid(block, worldid);

		boolean logic1 = false;

		if (dewsignnow >= 0) { // true if that is protect

			if (api_admin.dewddadmin.is2moderator(player) == true) {
				playerInSlot = getplayerinslot(player.getName(), dewsignnow, worldid);
				return !(playerInSlot == -1);
			}

			if (modeevent.equalsIgnoreCase("right") == true) { // right everyone
				playerInSlot = getplayerinslot(Constant_Protect.flag_everyone, dewsignnow, worldid);

				if (playerInSlot > -1) { // has flag everyone

					if (api_admin.dewddadmin.is2moderator(player) == true) {
						// staff
						// can't
						// do
						// it
						logic1 = false;
					} else {
						logic1 = true;
					}

				} else { // don't have flag everyone
					playerInSlot2 = getplayerinslot(player.getName(), dewsignnow, worldid);
					logic1 = !(playerInSlot2 == -1); // if have name return true
				}

				if (logic1 == false)
					if (player.hasPermission(pmainoveride) == true) {
						logic1 = true;
					}
				return logic1;

			} // right click
			else { // not right click

				playerInSlot = getplayerinslot(player.getName(), dewsignnow, worldid);

				logic1 = !(playerInSlot == -1); // don't have name can't !

				if (logic1 == false)
					if (player.hasPermission(pmainoveride) == true) {
						logic1 = true;
					}
				return logic1;

			} // right click or not

		} else { // If not who each home
					// staff should't have permission to place block
			if (api_admin.dewddadmin.is2moderator(player) == true) {
				return player.hasPermission(pmainplaceblocknoprotect);

			}

			if (modeevent.equalsIgnoreCase("dewset") == true)
				return player.hasPermission(pmaindewseteverywhere);

			return true;
		}

	}*/

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

	public LinkedList<String> getmaterialrealname(String gname) {
		LinkedList<String> ab = new LinkedList<String>();

		for (Material en : Material.values())
			if (en.name().toLowerCase().indexOf(gname.toLowerCase()) > -1) {

				dprint.r.printC("found material real name = " + en.name());
				ab.add(en.name());
			}

		return ab;
	}

	
	

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

	public void gift(Player player, int a1, byte a2) {
		gift_thread xyz = new gift_thread(player, a1, a2);

	}

	public void gotohell(Player player, Location lo1, Location lo2) {
		// copy to hell
		gotohellt ae = new gotohellt(player, lo1, lo2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ae);
	}
	
	class DeleteRecursive_Thread implements Runnable {
		private HashMap<String, Location> bd;
		private World world;
		private LXRXLZRZType ee;
		private ArrayList<IDDataType> item;
		private int chunklimit = 0;
		private int search = 10;

		public DeleteRecursive_Thread(HashMap<String, Location> bd, World world, int firstAdded, LXRXLZRZType ee,
				ArrayList<IDDataType> item, int chunklimit, int search) {
			this.bd = bd;
			this.world = world;
			this.ee = ee;
			this.item = item;
			this.chunklimit = chunklimit;
			this.search = search;

			// random add

		}

		@Override
		public void run() {
			long blockdo = 0;

			// dprint.r.printAll("nope.avi " + id.name());

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player == null) {
					continue;

				}

				if (player.getItemInHand().getType() == Material.STICK) {
					// bd = null;
					return;
				}
			}

			long startTime = System.currentTimeMillis();

			// dprint.r.printAll("start " + bd.size());

			if (bd.size() == 0) {

				bd = null;
				bd = new HashMap<String, Location>();
				// dprint.r.printAll("run () bd.size = " + bd.size());

				while (System.currentTimeMillis() - startTime < 1000 && world.getLoadedChunks().length <= chunklimit) {

					int x = li.useful.randomInteger(ee.lx, ee.rx);
					int z = li.useful.randomInteger(ee.lz, ee.rz);

					int y = li.useful.randomInteger(ee.ly, ee.rz);

					Block block = world.getBlockAt(x, y, z);
					if (IDDataType.isThisItemOnTheList(item, block.getTypeId(), block.getData())) {

						blockdo++;

						if (bd.get(tr.locationToString(block.getLocation())) == null) {
							bd.put(tr.locationToString(block.getLocation()), block.getLocation());
						}

						dprint.r.printAll("delete Recursive > first add > " + block.getX() + "," + block.getY() + ","
								+ block.getZ() + " size " + bd.size() + " , id data " + block.getTypeId() + ":"
								+ block.getData());

					}

				}

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player == null) {
						continue;

					}

					Block getStack = player.getLocation().getBlock();

					for (int x = -search; x <= search; x++)
						for (int y = -search; y <= search; y++) {

							for (int z = -search; z <= search; z++) {

								Block bo = player.getWorld().getBlockAt(getStack.getX() + x, getStack.getY() + y,
										getStack.getZ() + z);

								// dprint.r.printAll("near " +
								// tr.locationToString(bo.getLocation()) + "
								// block id " + bo.getType().name() + ":" +
								// bo.getData() + " > item "
								// + id.name() + ":" + data);

								if (IDDataType.isThisItemOnTheList(item, bo.getTypeId(), bo.getData())) {
									// dprint.r.printAll(bo.getType().name()
									// + " , " + id.name());
									// dprint.r.printAll("near " +
									// tr.locationToString(bo.getLocation())
									// + " block id " +
									// bo.getType().name());

									if (bd.get(tr.locationToString(bo.getLocation())) == null) {
										blockdo++;
										bd.put(tr.locationToString(bo.getLocation()), bo.getLocation());
										// dprint.r.printAll("added " +
										// bd.size() + " " +
										// tr.locationToString(bo.getLocation())
										// + " id " + bo.getType().name() +
										// ":" + bo.getData());
									}

								}

							}

						}

				}

				if (bd.size() == 0) {

					// dprint.r.printAll("recall ... " + bd.size() + " , blockdo
					// " + blockdo + "/" +
					// (System.currentTimeMillis() - startTime) + " avg = " +
					// (blockdo / (System.currentTimeMillis() - startTime +1))
					// );

					DeleteRecursive_Thread newRun = new DeleteRecursive_Thread(bd, world, 0, ee, item, chunklimit,
							search);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, newRun, sleeptime);

					return;
				}

			}

			startTime = System.currentTimeMillis();

			// blockdo = 0;

			int first = 0;
			while (bd.size() > 0 && System.currentTimeMillis() - startTime < 1000) {

				for (int i = 0; i < 1000000 && System.currentTimeMillis() - startTime < 1000; i++) {

					String forDeleteLoc = "";
					for (String locStr : bd.keySet()) {

						Location loc = bd.get(locStr);

						// bd.remove(locStr);
						forDeleteLoc = locStr;

						Block getStack = world.getBlockAt(loc);

						if (getStack.getWorld().getLoadedChunks().length > chunklimit) {
							break;
						}

						if (IDDataType.isThisItemOnTheList(item, getStack.getTypeId(), getStack.getData())) {

						} else {
							break;
						}

						if (first == 0) {
							dprint.r.printAll("delete recursive > break > " + getStack.getX() + "," + getStack.getY()
									+ "," + getStack.getZ() + " " + getStack.getType().name() + ":" + getStack.getData()
									+ " size " + bd.size() + " " + getStack.getWorld().getName() + " > id data "
									+ getStack.getTypeId() + ":" + getStack.getData() + " blockdo " + blockdo + "/"
									+ (System.currentTimeMillis() - startTime) + " avg = "
									+ (blockdo / (System.currentTimeMillis() - startTime + 1)));
							first = 1;
						}

						// getStack.breakNaturally();
						getStack.setType(Material.AIR);
						// dprint.r.printAdmin("break " +
						// getStack.getType().name() + " " +
						// tr.locationToString(getStack.getLocation()));

						blockdo++;

						for (int x = -search; x <= search; x++)
							for (int y = -search; y <= search; y++) {

								for (int z = -search; z <= search; z++) {

									Block bo = getStack.getWorld().getBlockAt(getStack.getX() + x, getStack.getY() + y,
											getStack.getZ() + z);
									if (bo.getX() < ee.lx || bo.getX() > ee.rx || bo.getZ() < ee.lz || bo.getZ() > ee.rz
											|| bo.getY() > 255 || bo.getY() < 0) {
										break;
									}

									if (IDDataType.isThisItemOnTheList(item, bo.getTypeId(), bo.getData())) {
										blockdo++;

										if (bd.get(tr.locationToString(bo.getLocation())) == null) {
											bd.put(tr.locationToString(bo.getLocation()), bo.getLocation());
										}

									}

								}

							}

						break;
					}

					if (forDeleteLoc.equalsIgnoreCase("")) {

					} else {
						Block bee = bd.get(forDeleteLoc).getBlock();
						// dprint.r.printAdmin("bd remove " +
						// tr.locationToString( bee.getLocation() ) + " " +
						// bee.getType().name() + ":" + bee.getData());
						bd.remove(forDeleteLoc);
					}

				}

			}

			DeleteRecursive_Thread newRun = new DeleteRecursive_Thread(bd, world, 0, ee, item, chunklimit, search);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, newRun, sleeptime);
			dprint.r.printAll("recalling > " + bd.size() + " " + " avg = "
					+ (int) ((double) blockdo / (System.currentTimeMillis() - startTime + 1)));

		}

	}
	
	public void DeleteRecursive_mom(HashMap<String, Location> bd, World world, int firstAdded, LXRXLZRZType ee,
			ArrayList<IDDataType> item, int chunklimit, int search) {
		DeleteRecursive_Thread dr = new DeleteRecursive_Thread(bd, world, firstAdded, ee, item, chunklimit, search);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, dr);

	}

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

	public void loadgiftfile() {
		String worldf = "ptdew_dewdd_gift_position.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			giftblock = null;
			fff.createNewFile();

			System.out.println("ptdeW&DewDD : " + tr.gettr("starting_loading_file") + filena);
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
				giftblock = Bukkit.getWorld(m[0]).getBlockAt(Integer.parseInt(m[1]), Integer.parseInt(m[2]),
						Integer.parseInt(m[3]));

			}

			System.out.println("ptdew&DewDD Main: Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println(tr.gettr("error_while_loading_file") + filena + " " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void loadmainfile() {

		loadgiftfile();
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

	public void redtorchchest(Block block, Player player) {

		// auto give item" + tr.gettr("for") + "all player on server
		redtorchchestt ee = new redtorchchestt(block, player);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ee);

	}

} // dew minecraft