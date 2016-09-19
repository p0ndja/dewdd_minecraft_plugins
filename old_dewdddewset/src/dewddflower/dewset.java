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

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class dewset extends dewsetdatabase {

	class autosortchest2_class implements Runnable {
		private Block	block;
		private Player	player;

		public autosortchest2_class(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		public void run() {

			if (block.getTypeId() != Material.CHEST.getId()
					&& block.getTypeId() != Material.TRAPPED_CHEST.getId()) {
				printC("auto sort chest2  block is not a chest");
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

				for (l1 = 0; l1 < leng; l1++) {
					// player.sendMessage("finding old data " + l1);
					if (sid[l1] == it.getTypeId()) {
						// player.sendMessage("ax " + sid[l1]);
						if (sdata[l1].getData().getData() == it.getData()
								.getData()) {

							founded = true;

							// player.sendMessage("s=" + l1 + ",id:" + sid[l1] +
							// ",data:"
							// + sdata[l1] + ",amount" + samount[l1]);
							samount[l1] += it.getAmount();
							break;
						}
					}
				}

				// if not found
				if (founded == false) {
					// player.sendMessage("can't find old slot");

					founded = false;
					for (l1 = 0; l1 < leng; l1++) {

						// find empty
						if (sid[l1] == -1) {
							sid[l1] = it.getTypeId();
							sdata[l1] = it;
							samount[l1] = it.getAmount();
							founded = true;
							break;
						}
					}

					if (founded = false) {
						System.out
								.println("Error autosortchest: can't find empty slot");
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
				while (samount[l1] > 0) {
					if (samount[l1] >= 64) {
						// player.sendMessage("adding > " + sid[l1] +
						// " amount= " +
						// samount[l1]);
						sdata[l1].setAmount(64);
						chest.getInventory().addItem(sdata[l1]);
						samount[l1] -= 64;
					}
					else {
						// player.sendMessage("adding > " + sid[l1] +
						// " amount = " +
						// samount[l1]);

						sdata[l1].setAmount(samount[l1]);
						chest.getInventory().addItem(sdata[l1]);

						samount[l1] -= samount[l1];
					}
				}

				// player.sendMessage("x data " +
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

	// cutwoodsub
	class cutwoodsubc implements Runnable {
		private Player	player123	= null;
		private Block	block123	= null;
		private boolean	isfirsttime;

		public cutwoodsubc(Block block123, Player player123, boolean isfirsttime) {
			this.player123 = player123;
			this.block123 = block123;
			this.isfirsttime = isfirsttime;
		}

		public void run() {

			int idc = getfreeselect(player123);
			if (dewaxe[idc] == false) {
				return;
			}

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

			if (block123.getTypeId() == 0 && isfirsttime == false) {
				return;
			}

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
				if (block123.getRelative(0, -1, 0).getTypeId() != 83) {
					return;
				}
				break;
			case 59:
				if (block123.getData() != 7) {
					return;
				}
				break;
			case 141:
				if (block123.getData() != 7) {
					return;
				}
				break;
			case 142:
				if (block123.getData() != 7) {
					return;
				}
				break;

			default:
				if (isfirsttime == false) {
					return;
				}
			}

			if (checkpermissionarea(block123, player123, "delete") == true) {
				return;
			}

			switch (player123.getItemInHand().getTypeId()) {
			case 279:
			case 258:
			case 271:
			case 275:
			case 286:

				// player123.sendMessage("ptdew&dewdd: "+
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
						.getItemInHand().getType().getMaxDurability()) {
					return;
				}
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
		public Player	player	= null;
		public int		amount;

		public void run() {
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewa please set block1");

				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewa please set block2");

				return;
			}

			// find position

			if (amount == 0) {
				if (player.getItemInHand() == null) {
					player.sendMessage("ptdew&dewdd: Item in hand = amount   (but you don't have item in hand");

					return;
				}
				amount = player.getItemInHand().getAmount();

			}

			player.sendMessage("dewa amount = " + amount);

			Block block = player.getLocation().getBlock();

			if (dewddtps.tps.getTPS() < 17) {
				printAll("ptdew&dewdd: dew a system need to pause cuz tps of server below than 17 try again!"
						+ dewddtps.tps.getTPS());

				return;
			}

			if (selectblock[getid] == null) {
				player.sendMessage("ptdew&dewdd: dew a diamond sword selected block = null");

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
								|| (blockd.getLocation().getBlockX() == selectblock[getid]
										.getLocation().getBlockX()
										&& blockd.getLocation().getBlockY() == selectblock[getid]
												.getLocation().getBlockY() && blockd
										.getLocation().getBlockZ() == selectblock[getid]
										.getLocation().getBlockZ())) { // diamond
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
				player.sendMessage("diamond block can't found so... It mean upper");

				blockdy = 1;
			}

			player.sendMessage("diamond block axis = " + blockdx + ","
					+ blockdy + "," + blockdz);
			// after know axis and selected block ... so start copy
			// for amount
			// for all block ... to copy to next axis
			printAll("ptdew&dewdd : '" + player.getName() + "'starting dewa "
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
		public Player	player	= null;
		public int		getid;

		public int		amount;
		public int		blockdx;
		public int		blockdy;
		public int		blockdz;

		public int		amount1;

		public int		amountloop;

		public int		mx;
		public int		my;
		public int		mz;
		public int		lx;
		public int		ly;
		public int		lz;
		public int		xlx;
		public int		ylx;
		public int		zlx;

		public void run() {
			if (player == null) {
				printAll("dewset error  player not found...");
				return;
			}

			Block blockd = null;
			// amountloop = start with 1
			Block blb = null;

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (amountloop <= amount) {
				// printAll("amountloop = " + amountloop + " / " + amount);

				// player.getWorld().save();
				int ndx = 0; // now x y z
				if (blockdx != 0) {
					ndx = ((Math.abs(selectx1[getid] - selectx2[getid])) + 1);
					ndx = ndx * blockdx;
					ndx = ndx * amountloop;
				}

				int ndy = 0; // now x y z
				if (blockdy != 0) {
					ndy = ((Math.abs(selecty1[getid] - selecty2[getid])) + 1);
					ndy = ndy * blockdy;
					ndy = ndy * amountloop;
				}

				int ndz = 0; // now x y z
				if (blockdz != 0) {
					ndz = ((Math.abs(selectz1[getid] - selectz2[getid])) + 1);
					ndz = ndz * blockdz;
					ndz = ndz * amountloop;
				}

				player.sendMessage("blockdx = " + ndx);
				player.sendMessage("blockdy = " + ndy);
				player.sendMessage("blockdz = " + ndz);

				// printC("blockdx = " + ndx);
				// printC("blockdy = " + ndy);
				// printC("blockdz = " + ndz);

				while (amount1 <= 2) { // amount1 // amount1 = start with 1
					// printAll("amount1 = " + amount1);

					while (xlx <= mx) {

						while (ylx <= my) {
							while (zlx <= mz) {

								// printAll (xlx + "," + ylx + "," + zlx +
								// " mx " + mx + "," + my + "," + mz);

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

									printC("dewa recall " + xlx + " , " + ylx
											+ " , " + zlx);
									printC("low " + lx + " , " + ly + " , "
											+ lz + " high " + mx + "," + my
											+ "," + mz);

									Bukkit.getScheduler()
											.scheduleSyncDelayedTask(ac, xgn2,
													50L);

									return;
								}

								blb = player.getWorld().getBlockAt(xlx, ylx,
										zlx);

								if ((blb.getY() + ndy) > 253
										|| (blb.getY() + ndy) < 1) {
									zlx++;
									// printAll("out of range y");
									continue;
								}

								blockd = blb.getWorld().getBlockAt(
										(blb.getX() + ndx), (blb.getY() + ndy),
										(blb.getZ() + ndz));
								/*
								 * if (blockd.getTypeId() == 0) { continue; }
								 */

								if (amount1 == 1) { // if first round ... only
													// block
									if (blb.getType().isBlock() == false) {
										zlx++;
										// printAll("first is not a block");
										continue;
									}
									// blockd.setTypeId(0);

									if (api_admin.dewddadmin.is2admin(player) == false
											&& api_admin.dewddadmin
													.is2moderator(player) == false) {
										if (blockd.getTypeId() != blb
												.getTypeId()
												|| blockd.getData() != blb
														.getData()) {
											if (decreseitem1(player,
													blb.getTypeId(),
													blb.getData(), false) == false
													&& blb.getTypeId() != 0) {
												player.sendMessage("ptdew&dewdd: dont have enough item");
												player.sendMessage("block > "
														+ blb.getTypeId() + ","
														+ blb.getData());
												return;
											}
										}
									}
								}
								else { // if secord round ... only not block
										// block
									if (blb.getType().isBlock() == true) {
										zlx++;
										// printAll("second time is a block");
										continue;
									}
									// blockd.setTypeId(0);

									if (api_admin.dewddadmin.is2admin(player) == false
											&& api_admin.dewddadmin
													.is2moderator(player) == false) {
										if (blockd.getTypeId() != blb
												.getTypeId()
												|| blockd.getData() != blb
														.getData()) {
											if (decreseitem1(player,
													blb.getTypeId(),
													blb.getData(), false) == false
													&& blb.getTypeId() != 0) {
												player.sendMessage("ptdew&dewdd: dont have enough item");
												player.sendMessage("block > "
														+ blb.getTypeId() + ","
														+ blb.getData());
												return;
											}
										}
									}
								}

								blockd = blb.getWorld().getBlockAt(
										(blb.getX() + ndx), (blb.getY() + ndy),
										(blb.getZ() + ndz));
								if (checkpermissionarea(blockd, player,
										"dewset") == true) {
									return;
								}

								blockd.setTypeIdAndData(blb.getTypeId(),
										blb.getData(), false);

								// printAll ("comple  " + xlx + "," + ylx + ","
								// + zlx + " mx " + mx + "," + my + "," + mz);

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

			player.sendMessage("ptdew&dewdd: dewA done");
			printAll("ptdew&dewdd : " + player.getName() + " > dewa done");
		}
	}

	class dewbuy_class implements Runnable {

		private Player	player;
		public boolean	isok	= false;

		public dewbuy_class(Player player) {
			this.player = player;
		}

		public void run() {
			if (api_admin.dewddadmin.is2moderator(player) == true) {
				player.sendMessage("ptdew&dewdd: Staff can't buy area");
				isok = false;
				return;
			}

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewbuy please set block1");
				isok = false;
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewbuy please set block2");

				isok = false;
				return;
			}

			int countblock = -1;

			if (player.hasPermission(pmaindewbuynotcount) == false) {
				countblock = getselectblockforbuy(getid, player);
				if (countblock < 27) {
					player.sendMessage("ptdew&dewdd : smallest area you can buy is 27 blocks");
					isok = false;
					return;
				}

			}
			if (checkpermissionarea(player.getLocation().getBlock(), true) >= 0
					&& ((player.hasPermission(pmaindewbuyreplace)) == false)) {
				player.sendMessage("ptdew&dewdd : You can't buy these area... cuz There are another player's block(home)");
				isok = false;
				return;
			}

			player.sendMessage("ptdew&dewdd: Block 1 = (" + selectx1[getid]
					+ "," + selecty1[getid] + "," + selectz1[getid] + ") to ("
					+ selectx2[getid] + "," + selecty2[getid] + ","
					+ selectz2[getid] + ") = " + countblock);

			if (countblock == -1) {
				countblock = 1;
				player.sendMessage("countblock == -1 , but admin we change it to 1");
			}

			double buymoneyp = countblock
					* api_private.dewddprivate.buy1blockmoney;

			player.sendMessage("ptdew&dewdd : Buy '" + countblock
					+ "' use money = " + buymoneyp);

			try {
				if (Economy.getMoney(player.getName()) < buymoneyp) {

					player.sendMessage("ptdew&dewdd: you don't have enough money for buy these area... = "
							+ (Economy.getMoney(player.getName()) - buymoneyp));

					isok = false;
					return;
				}

				player.sendMessage("ptdew&dewdd : ok you have enough money please wait...");

				printAll("ptdew&dewdd : '" + player.getName()
						+ "'starting dewbuy "
						+ player.getItemInHand().getTypeId() + ":"
						+ player.getItemInHand().getData());

				player.sendMessage("ptdew&dewdd : "
						+ Economy.getMoney(player.getName()) + " - "
						+ buymoneyp + " = "
						+ (Economy.getMoney(player.getName()) - buymoneyp));

				Economy.setMoney(player.getName(),
						(Economy.getMoney(player.getName()) - buymoneyp));

			}
			catch (UserDoesNotExistException | NoLoanPermittedException err) {
				printAll("error economy while dewbuy "
						+ err.getCause().getMessage());
			}
			player.sendMessage("x" + ","
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
					+ dewsignz2[getworldid(player.getWorld().getName())][getid]);

			player.sendMessage("before add sign world " + dewworldlistmax
					+ " sign max "
					+ (dewsignmax[getworldid(player.getWorld().getName())] - 1));

			adderarraysignfile(getworldid(player.getWorld().getName()));

			player.sendMessage("after add sign world " + dewworldlistmax
					+ " sign max "
					+ (dewsignmax[getworldid(player.getWorld().getName())] - 1));

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

			for (int gggg = 1; gggg < (dewsignnamemax); gggg++) {
				dewsignname[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
						.getWorld().getName())] - 1][gggg] = "null";
			}

			dewsignloop[getworldid(player.getWorld().getName())][dewsignmax[getworldid(player
					.getWorld().getName())] - 1] = 0;

			savesignfile(-1, getworldid(player.getWorld().getName()));
			loadsignfile();
			printAll("ptdew&dewdd : " + player.getName() + "buy done");
			isok = true;
			printAll("dewbuy class is ok" + isok);
			return;
		}
	}

	class dewfill_mom implements Runnable {

		private Player	player	= null;
		private int		handid;
		private byte	handdata;

		public dewfill_mom(Player player, int handid, byte handdata) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
		}

		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewfill please set block1");
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewfill please set block2");
				return;
			}

			printAll("ptdew&dewdd : '" + player.getName()
					+ "'starting dewfill " + handid + ":" + handdata);
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

		private int		mx		= 0;
		private int		lx		= 0;
		private int		my		= 0;
		private int		ly		= 0;
		private int		mz		= 0;
		private int		lz		= 0;

		private int		xlx		= 0;
		private int		ylx		= 0;
		private int		zlx		= 0;

		private Player	player	= null;
		private int		handid;
		private byte	handdata;

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

		public void run() {
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewfill_thread xgn2 = new dewfill_thread(player,
									handid, handdata, mx, my, mz, lx, ly, lz,
									xlx, ylx, zlx);

							printC("dewfill recall " + xlx + " , " + ylx
									+ " , " + zlx);
							printC("low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, 50L);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (blb.getTypeId() != 0) {
							continue;
						}
						if (checkpermissionarea(blb, player, "dewset") == true) {
							return;
						}

						if (api_admin.dewddadmin.is2admin(player) == false
								&& api_admin.dewddadmin.is2moderator(player) == false) {
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage("ptdew&dewdd: dont have enough item");
								return;
							}
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

			printAll("ptdew&dewdd: dewfill done : " + player.getName());
		}
	}

	class dewset_mom implements Runnable {
		private Player	player	= null;
		private int		e1;
		private byte	e2;
		private int		e3;
		private byte	e4;
		private boolean	invert;

		public dewset_mom(Player player, int e1, byte e2, int e3, byte e4,
				boolean invert) {
			this.player = player;
			this.e1 = e1;
			this.e2 = e2;
			this.e3 = e3;
			this.e4 = e4;
			this.invert = invert;
		}

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
				player.sendMessage("ptdew&dewdd:dewset please set block1");
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd:dewset please set block2");
				return;
			}

			Material ik = Material.getMaterial(e3);
			if (ik == null) {
				player.sendMessage("what is this item ? " + e3 + ":" + e4);
				return;
			}

			if (ik.isBlock() == false) {
				player.sendMessage("it's not a block ? " + e3 + ":" + e4);
				return;
			}

			if (isdewset(e3) == false) {
				player.sendMessage("this item my plugin not allow for dewset "
						+ e3 + ":" + e4);
				return;
			}

			if (dewddtps.tps.getTPS() < 17) {
				printAll("ptdew&dewdd: dew set system need to pause cuz tps of server below than 17 try again!"
						+ dewddtps.tps.getTPS());

				return;
			}

			// player.sendMessage(". " + e1 + "," + e2 + "|" + e3 + "," + e4);

			if (invert == false) {
				printAll("ptdew&dewdd : '" + player.getName()
						+ "'starting dewset *search " + e1 + ":" + e2
						+ " replace with " + e3 + ":" + e4);
			}
			else {

				printAll("ptdew&dewdd : '" + player.getName()
						+ "'starting dewxet *search " + e1 + ":" + e2
						+ " replace with " + e3 + ":" + e4);
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

	class dewset_thread implements Runnable {
		private Player	player	= null;
		private int		e1;
		private byte	e2;
		private int		e3;
		private byte	e4;
		private boolean	invert;
		private int		getid;

		private int		xlx;
		private int		ylx;
		private int		zlx;

		private int		lx;
		private int		ly;
		private int		lz;

		private int		mx;
		private int		my;
		private int		mz;

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

		public void run() {

			if (player == null) {
				printAll("dewset error  player not found...");
				return;
			}

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

							printC("dewset recall " + xlx + " , " + ylx + " , "
									+ zlx);
							printC("low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, 50L);

							return;
						}

						if (e1 != -29) {
							if (invert == false) {
								if (blb.getTypeId() != e1) {
									zlx++;
									continue;
								}
							}
							else {
								if (blb.getTypeId() == e1) {
									zlx++;
									continue;
								}
							}
						}

						if (e2 != -29) {
							if (invert == false) {
								if (blb.getData() != e2) {
									zlx++;
									continue;
								}
							}
							else {
								if (blb.getData() == e2) {
									zlx++;
									continue;
								}
							}
						}

						if (checkpermissionarea(blb, player, "dewset") == true) {
							return;
						}

						if (blb.getTypeId() == e1 && blb.getData() == e2) {
							zlx++;
							continue;
						}

						if (e3 == 0) { // if delete
							if (player.hasPermission(pmaindelete) == false) {
								player.sendMessage("you don't have permission to access any delete commands");
								return;
							}

							blb.setTypeId(0);

						}

						else { // if place
							if (api_admin.dewddadmin.is2admin(player) == false
									&& api_admin.dewddadmin
											.is2moderator(player) == false) {
								if (decreseitem1(player, e3, e4, true) == false) {
									player.sendMessage("ptdew&dewdd: dont have enough item");
									return;
								}
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
				printAll("ptdew&dewdd: dewset done : " + player.getName());
			}
			else {
				printAll("ptdew&dewdd: dewxet done : " + player.getName());
			}
		}
	}

	class dewsetblock_mom implements Runnable {

		private Player	player		= null;
		private int		handid;
		private byte	handdata;
		private boolean	isfillmode	= false;

		public dewsetblock_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd : dewset(fill)block please set block1");
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd: dewset(fill)block please set block2");
				return;
			}

			printAll("ptdew&dewdd : '" + player.getName()
					+ "'starting dewset(fill)block " + handid + ":" + handdata);
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

		private int		mx			= 0;
		private int		lx			= 0;
		private int		my			= 0;
		private int		ly			= 0;
		private int		mz			= 0;
		private int		lz			= 0;

		private int		xlx			= 0;
		private int		ylx			= 0;
		private int		zlx			= 0;

		private Player	player		= null;
		private int		handid;
		private byte	handdata;

		private int		getid;

		private boolean	isfillmode	= false;

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

		public void run() {

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {
						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetblock_thread xgn2 = new dewsetblock_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode);

							printC("dewsetblock recall " + xlx + " , " + ylx
									+ " , " + zlx);
							printC("low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (isfillmode == true) {
							if (blb.getTypeId() != 0) {
								zlx++;
								continue;
							}

						}

						if (checkpermissionarea(blb, player, "dewset") == true) {
							return;
						}

						// coner

						if ((blb.getLocation().getBlockY() == selecty1[getid] || blb
								.getLocation().getBlockY() == selecty2[getid])) {

							if ((blb.getLocation().getBlockX() == selectx1[getid]
									|| blb.getLocation().getBlockX() == selectz1[getid]
									|| blb.getLocation().getBlockX() == selectx2[getid]
									|| blb.getLocation().getBlockX() == selectz2[getid]
									|| blb.getLocation().getBlockZ() == selectx1[getid]
									|| blb.getLocation().getBlockZ() == selectz1[getid]
									|| blb.getLocation().getBlockZ() == selectx2[getid] || blb
									.getLocation().getBlockZ() == selectz2[getid])

							) {

								if (api_admin.dewddadmin.is2admin(player) == false
										&& api_admin.dewddadmin
												.is2moderator(player) == false) {
									if (decreseitem1(player, handid, handdata,
											true) == false) {
										player.sendMessage("ptdew&dewdd: dont have enough item");
										return;
									}
								}

								blb.setTypeIdAndData(handid, handdata, false);
								//
							}

						} // up and down
						else { // conner
							if (blb.getLocation().getBlockX() == selectx1[getid]
									&& blb.getLocation().getBlockZ() == selectz1[getid]) {
								if (api_admin.dewddadmin.is2admin(player) == false
										&& api_admin.dewddadmin
												.is2moderator(player) == false) {
									if (decreseitem1(player, handid, handdata,
											true) == false) {
										player.sendMessage("ptdew&dewdd: dont have enough item");
										return;
									}
								}

								blb.setTypeIdAndData(handid, handdata, false);
							}
							if (blb.getLocation().getBlockX() == selectx1[getid]
									&& blb.getLocation().getBlockZ() == selectz2[getid]) {
								if (api_admin.dewddadmin.is2admin(player) == false
										&& api_admin.dewddadmin
												.is2moderator(player) == false) {
									if (decreseitem1(player, handid, handdata,
											true) == false) {
										player.sendMessage("ptdew&dewdd: dont have enough item");
										return;
									}
								}

								blb.setTypeIdAndData(handid, handdata, false);
							}
							if (blb.getLocation().getBlockX() == selectx2[getid]
									&& blb.getLocation().getBlockZ() == selectz1[getid]) {
								if (api_admin.dewddadmin.is2admin(player) == false
										&& api_admin.dewddadmin
												.is2moderator(player) == false) {
									if (decreseitem1(player, handid, handdata,
											true) == false) {
										player.sendMessage("ptdew&dewdd: dont have enough item");
										return;
									}
								}

								blb.setTypeIdAndData(handid, handdata, false);
							}
							if (blb.getLocation().getBlockX() == selectx2[getid]
									&& blb.getLocation().getBlockZ() == selectz2[getid]) {
								if (api_admin.dewddadmin.is2admin(player) == false
										&& api_admin.dewddadmin
												.is2moderator(player) == false) {
									if (decreseitem1(player, handid, handdata,
											true) == false) {
										player.sendMessage("ptdew&dewdd: dont have enough item");
										return;
									}
								}

								blb.setTypeIdAndData(handid, handdata, false);
							}

						} // coner

						// coner

						zlx++;
					}
					zlx = lz;

					ylx++;
				}
				ylx = ly;

				xlx++;
			}
			xlx = lx;

			printAll("ptdew&dewdd: dew(set)block done : " + player.getName());
		}
	}

	class dewsetroom_mom implements Runnable {

		private Player	player		= null;
		private int		handid;
		private byte	handdata;
		private boolean	isfillmode	= false;

		public dewsetroom_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd : dewset(fill)room please set block1");
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd: dewset(fill)room please set block2");
				return;
			}

			printAll("ptdew&dewdd : '" + player.getName()
					+ "'starting dewset(fill)room " + handid + ":" + handdata);
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

		private int		mx			= 0;
		private int		lx			= 0;
		private int		my			= 0;
		private int		ly			= 0;
		private int		mz			= 0;
		private int		lz			= 0;

		private int		xlx			= 0;
		private int		ylx			= 0;
		private int		zlx			= 0;

		private Player	player		= null;
		private int		handid;
		private byte	handdata;

		private int		getid;

		private boolean	isfillmode	= false;

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

		public void run() {

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetroom_thread xgn2 = new dewsetroom_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode);

							printC("dewsetroom recall " + xlx + " , " + ylx
									+ " , " + zlx);
							printC("low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (isfillmode == true) {
							if (blb.getTypeId() != 0) {
								zlx++;
								continue;
							}

						}

						if (blb.getLocation().getBlockX() == selectx1[getid]
								|| blb.getLocation().getBlockX() == selectz1[getid]
								|| blb.getLocation().getBlockX() == selectx2[getid]
								|| blb.getLocation().getBlockX() == selectz2[getid]
								|| blb.getLocation().getBlockZ() == selectx1[getid]
								|| blb.getLocation().getBlockZ() == selectz1[getid]
								|| blb.getLocation().getBlockZ() == selectx2[getid]
								|| blb.getLocation().getBlockZ() == selectz2[getid]
								|| blb.getLocation().getBlockY() == selecty1[getid]
								|| blb.getLocation().getBlockY() == selecty2[getid]

						) {

							if (checkpermissionarea(blb, player, "dewset") == true) {
								return;
							}

							if (api_admin.dewddadmin.is2admin(player) == false
									&& api_admin.dewddadmin
											.is2moderator(player) == false) {
								if (decreseitem1(player, handid, handdata, true) == false) {
									player.sendMessage("ptdew&dewdd: dont have enough item");
									return;
								}
							}

							blb.setTypeIdAndData(handid, handdata, false);

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

			printAll("ptdew&dewdd: dew(set)room done : " + player.getName());
		}
	}

	class dewsetwall_mom implements Runnable {

		private Player	player		= null;
		private int		handid;
		private byte	handdata;
		private boolean	isfillmode	= false;

		public dewsetwall_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		public void run() {

			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd : dewset(fill)wall please set block1");
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd: dewset(fill)wall please set block2");
				return;
			}

			printAll("ptdew&dewdd : '" + player.getName()
					+ "'starting dewset(fill)wall " + handid + ":" + handdata);
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

		private int		mx			= 0;
		private int		lx			= 0;
		private int		my			= 0;
		private int		ly			= 0;
		private int		mz			= 0;
		private int		lz			= 0;

		private int		xlx			= 0;
		private int		ylx			= 0;
		private int		zlx			= 0;

		private Player	player		= null;
		private int		handid;
		private byte	handdata;

		private int		getid;

		private boolean	isfillmode	= false;

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

		public void run() {
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;

			while (xlx <= mx) {
				while (ylx <= my) {
					while (zlx <= mz) {

						endtime = System.currentTimeMillis();
						if (endtime - starttime > runtime
								|| dewddtps.tps.getTPS() <= 18) {

							dewsetwall_thread xgn2 = new dewsetwall_thread(
									player, handid, handdata, mx, my, mz, lx,
									ly, lz, xlx, ylx, zlx, getid, isfillmode);

							printC("dewsetwall recall " + xlx + " , " + ylx
									+ " , " + zlx);
							printC("low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
									xgn2, randomG.nextInt(500) + 50);

							return;
						}

						blb = player.getWorld().getBlockAt(xlx, ylx, zlx);

						if (isfillmode == true) {
							if (blb.getTypeId() != 0) {
								zlx++;
								continue;
							}

						}

						if (!(blb.getLocation().getBlockX() == selectx1[getid]
								|| blb.getLocation().getBlockX() == selectz1[getid]
								|| blb.getLocation().getBlockX() == selectx2[getid]
								|| blb.getLocation().getBlockX() == selectz2[getid]
								|| blb.getLocation().getBlockZ() == selectx1[getid]
								|| blb.getLocation().getBlockZ() == selectz1[getid]
								|| blb.getLocation().getBlockZ() == selectx2[getid] || blb
								.getLocation().getBlockZ() == selectz2[getid]

						)) {

							zlx++;
							continue;
						}

						if (checkpermissionarea(blb, player, "dewset") == true) {
							return;
						}

						if (api_admin.dewddadmin.is2admin(player) == false
								&& api_admin.dewddadmin.is2moderator(player) == false) {
							if (decreseitem1(player, handid, handdata, true) == false) {
								player.sendMessage("ptdew&dewdd: dont have enough item");
								return;
							}
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

			printAll("ptdew&dewdd: dew(set)fill done : " + player.getName());
		}
	}

	class dewwallcircle_mom implements Runnable {

		private Player	player		= null;
		private int		handid;
		private byte	handdata;
		private boolean	isfillmode	= false;

		public dewwallcircle_mom(Player player, int handid, byte handdata,
				boolean isfillmode) {
			this.player = player;
			this.handid = handid;
			this.handdata = handdata;
			this.isfillmode = isfillmode;
		}

		public void run() {

			// ..........
			int getid = getfreeselect(player);
			if (selectx1[getid] == 0 && selecty1[getid] == 0
					&& selectz1[getid] == 0) {
				player.sendMessage("ptdew&dewdd: dewwallcircle please set block1");
				return;
			}
			if (selectx2[getid] == 0 && selecty2[getid] == 0
					&& selectz2[getid] == 0) {
				player.sendMessage("ptdew&dewdd: dewwallcircle please set block2");
				return;
			}

			double midx = ((double) (selectx1[getid]) + (double) (selectx2[getid])) / 2;
			double midy = ((double) (selecty1[getid]) + (double) (selecty2[getid])) / 2;
			double midz = ((double) (selectz1[getid]) + (double) (selectz2[getid])) / 2;

			if ((midx == selectx1[getid] && midy == selecty1[getid] && midz == selectz1[getid])
					|| (midx == selectx2[getid] && midy == selecty2[getid] && midz == selectz2[getid])) {
				player.sendMessage("ptdew&dewdd: small circle can't run program");
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) (selectx1[getid])
					- ((double) (selectx2[getid])), 2);

			temp2 = Math.pow((double) (selecty1[getid])
					- ((double) (selecty2[getid])), 2);

			temp3 = Math.pow((double) (selectz1[getid])
					- ((double) (selectz2[getid])), 2);

			double midty = ((selecty1[getid]) + (selecty2[getid])) / 2;

			double midtx = ((selectx1[getid]) + (selectx2[getid])) / 2;

			double midtz = ((selectz1[getid]) + (selectz2[getid])) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;

			player.sendMessage("cir=" + midtx + "," + midty + "," + midtz);
			player.sendMessage("R=" + temp5);

			printAll("ptdew&dewdd : '" + player.getName()
					+ "'starting dewwallcircle "
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

	// Bigdigthread

	class dewwallcircle_thread implements Runnable {

		private int		mx			= 0;
		private int		lx			= 0;
		private int		my			= 0;
		private int		ly			= 0;
		private int		mz			= 0;
		private int		lz			= 0;

		private int		xlx			= 0;
		private int		ylx			= 0;
		private int		zlx			= 0;

		private Player	player		= null;
		private int		handid;
		private byte	handdata;

		private int		getid;
		private double	midr;

		private boolean	isfillmode	= false;

		private double	midtx;
		private double	midty;
		private double	midtz;

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

							printC("dewwallcircle recall " + xlx + " , " + ylx
									+ " , " + zlx);
							printC("low " + lx + " , " + ly + " , " + lz
									+ " high " + mx + "," + my + "," + mz);

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

						if (isfillmode == true) {
							if (blb.getTypeId() != 0) {
								zlx++;
								continue;
							}

						}

						// wallc

						if (checkpermissionarea(blb, player, "dewset") == true) {
							return;
						}
						if (decreseitem1(player, handid, handdata, true) == false) {
							player.sendMessage("ptdew&dewdd: dont have enough item");
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

			printAll("ptdew&dewdd: dewwallcircle done : " + player.getName());
		}
	}

	class gotohellt implements Runnable {
		private Player		player	= null;
		private Location	lo2		= null;
		private Location	lo1		= null;

		public gotohellt(Player player, Location lo1, Location lo2) {
			this.player = player;
			this.lo1 = lo1;
			this.lo2 = lo2;
		}

		public void run() {
			int dx = 15;
			Block blo = null;
			Block blo2 = null;

			for (int d = 1; d <= 2; d++) {
				for (int x = (0 - dx); x <= (0 + dx); x++) {
					for (int z = (0 - dx); z <= (0 + dx); z++) {
						for (int y = (0 - dx); y <= (0 + dx); y++) {
							blo = Bukkit.getWorld("world").getBlockAt(lo1)
									.getRelative(x, y, z);
							if (d == 1) {
								if (blo.getType().isBlock() == false) {
									continue;
								}
							}
							else if (d == 2) {
								if (blo.getType().isBlock() == true) {
									continue;
								}
							}

							blo2 = Bukkit.getWorld("world_nether")
									.getBlockAt(lo2).getRelative(x, y, z);

							switch (blo.getTypeId()) {
							case 2:
								blo2.setTypeId(88);
								break;
							case 3:
								blo2.setTypeId(87);
								break;
							case 8:
							case 9:
							case 10:
							case 11:
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
			 * player.getInventory().getHelmet().setTypeId(0);
			 * }
			 * 
			 * ItemStack itm = new ItemStack(397, 1);
			 * player.getInventory().setHelmet(itm.getData().toItemStack());
			 */
			player.teleport(lo2);
			player.getInventory().remove(7);
			printAll("ptdew&dewdd: '" + player.getName() + "' !@#!@# ????");
			printC("ptdew&dewdd: go to hell '" + player.getName() + "'");
		}
	}

	// blockgoldsugarcane
	class harvestblockgoldsugarcanec implements Runnable {
		private Block	block	= null;
		private Player	player	= null;

		public harvestblockgoldsugarcanec(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		public void run() {

			int digX = block.getX();
			int digY = block.getY();
			int digZ = block.getZ();
			int d5 = d4[getfreeselect(player)];

			int ex = 0;
			int ey = 0;
			int ez = 0;

			try {
				World world = block.getWorld();

				for (ex = digX - d5; ex <= digX + d5; ex++) {
					for (ey = digY + 1; ey <= digY + 2; ey++) {
						for (ez = digZ - d5; ez <= digZ + d5; ez++) {

							Block blockCut = world.getBlockAt(ex, ey, ez);
							if (checkpermissionarea(blockCut, player, "destroy") == true) {
								continue;
							}

							if (blockCut.getTypeId() == 83) {

								Location ac = player.getLocation();
								ac.setX(player.getLocation().getX());
								ac.setY(player.getLocation().getY() + 1);
								ac.setZ(player.getLocation().getZ());

								Block aboveplayer = world.getBlockAt(ac);
								aboveplayer.setTypeId(83);
								blockCut.setTypeId(0);

								aboveplayer.breakNaturally();
								// money = money - 0.0359;

							}

						} // for
					} // for
				} // for

				player.sendMessage("ptdew&dewdd: BlockOfGold sugar cane done");

			}
			catch (Exception e) {
				return;
			}
		}
	}

	// blockgoldsugarcane
	class harvestgoldpumpkinc implements Runnable {
		Block	block	= null;
		Player	player	= null;

		public void run() {

			int digX = block.getX();
			int digY = block.getY();
			int digZ = block.getZ();
			int d5 = d4[getfreeselect(player)];
			int ex = 0;

			int ez = 0;

			try {
				World world = block.getWorld();
				d5 = d4[getfreeselect(player)];

				for (ex = digX - d5; ex <= digX + d5; ex++) {
					// for (ey = digY-d4; ey <= digY+d4; ey++) {
					for (ez = digZ - d5; ez <= digZ + d5; ez++) {

						Block blockCut = world.getBlockAt(ex, digY, ez);
						if (checkpermissionarea(blockCut, player, "destroy") == true) {
							continue;
						}

						if (blockCut.getTypeId() == 86) {
							Location ac = player.getLocation();
							ac.setX(player.getLocation().getX());
							ac.setY(player.getLocation().getY() + 1);
							ac.setZ(player.getLocation().getZ());

							Block aboveplayer = world.getBlockAt(ac);
							aboveplayer.setTypeId(86);
							blockCut.setTypeId(0);

							aboveplayer.breakNaturally();

							blockCut.breakNaturally();
							// money = money - 0.04875;
							// 0.734375
						}
					}
					// }
				}
				player.sendMessage("ptdew&dewdd:goldore Pumkin done");
			}
			catch (Exception e) {
				return;
			}
		}
	}

	// skyblock
	// nether
	// invert
	// old_1
	// flat
	// old_2
	// float

	// goldpumpkin
	class harvestriceblockironc implements Runnable {
		private Block	block	= null;
		private Player	player	= null;

		public harvestriceblockironc(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		public void run() {
			int digX = block.getX();
			int digY = block.getY();
			int digZ = block.getZ();
			int d5 = d4[getfreeselect(player)];
			int ex = 0;

			int ez = 0;

			try {

				World world = block.getWorld();
				d5 = d4[getfreeselect(player)];

				for (ex = digX - d5; ex <= digX + d5; ex++) {
					// for (ey = digY-d4; ey <= digY+d4; ey++) {
					for (ez = digZ - d5; ez <= digZ + d5; ez++) {

						Block blockCut = world.getBlockAt(ex, digY, ez);
						if (checkpermissionarea(blockCut, player, "destroy") == true) {
							continue;
						}

						if (blockCut.getTypeId() == 59
								&& blockCut.getData() == 7
								|| blockCut.getTypeId() == 141
								&& blockCut.getData() == 7
								|| blockCut.getTypeId() == 142
								&& blockCut.getData() == 7) {
							Location ac = player.getLocation();
							ac.setX(player.getLocation().getX());
							ac.setY(player.getLocation().getY() + 1);
							ac.setZ(player.getLocation().getZ());

							for (ItemStack dropja : blockCut.getDrops()) {

								world.dropItemNaturally(ac, dropja);
								if (blockCut.getTypeId() == 59) {
									dropja.setAmount(1);
									dropja.setTypeId(295);
									world.dropItemNaturally(ac, dropja);

									if (randomG.nextInt(100) < 50) {
										dropja.setAmount(1);
										dropja.setTypeId(295);
										world.dropItemNaturally(ac, dropja);
									}
								}

								if (blockCut.getTypeId() == 141) {
									dropja.setAmount(1);
									dropja.setTypeId(295);
									world.dropItemNaturally(ac, dropja);

									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(391);
										world.dropItemNaturally(ac, dropja);
									}
									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(391);
										world.dropItemNaturally(ac, dropja);
									}
									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(391);
										world.dropItemNaturally(ac, dropja);
									}
									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(391);
										world.dropItemNaturally(ac, dropja);
									}

								}

								if (blockCut.getTypeId() == 142) {
									dropja.setAmount(1);
									dropja.setTypeId(295);
									world.dropItemNaturally(ac, dropja);

									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(392);
										world.dropItemNaturally(ac, dropja);
									}
									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(392);
										world.dropItemNaturally(ac, dropja);
									}
									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(392);
										world.dropItemNaturally(ac, dropja);
									}
									if (randomG.nextInt(600) < 500) {
										dropja.setAmount(1);
										dropja.setTypeId(392);
										world.dropItemNaturally(ac, dropja);
									}

								}

							}

							blockCut.setTypeId(0);
						}
					}
					// }
				}
				player.sendMessage("ptdew&dewdd:Iron Rice done");
			}
			catch (Exception e) {
				return;
			}
		}
	}

	class harvestsandmelonc implements Runnable {
		private Block	block	= null;
		private Player	player	= null;

		public harvestsandmelonc(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		public void run() {
			int digX = block.getX();
			int digY = block.getY();
			int digZ = block.getZ();
			int d5 = d4[getfreeselect(player)];
			int ex = 0;

			int ez = 0;

			try {
				World world = block.getWorld();
				d5 = d4[getfreeselect(player)];

				for (ex = digX - d5; ex <= digX + d5; ex++) {
					// for (ey = digY-d4; ey <= digY+d4; ey++) {
					for (ez = digZ - d5; ez <= digZ + d5; ez++) {

						Block blockCut = world.getBlockAt(ex, digY, ez);
						if (checkpermissionarea(blockCut, player, "destroy") == true) {
							continue;
						}

						if (blockCut.getTypeId() == 103) {
							Location ac = player.getLocation();
							ac.setX(player.getLocation().getX());
							ac.setY(player.getLocation().getY() + 1);
							ac.setZ(player.getLocation().getZ());

							Block aboveplayer = world.getBlockAt(ac);
							aboveplayer.setTypeId(103);
							blockCut.setTypeId(0);

							aboveplayer.breakNaturally();

							blockCut.breakNaturally();

							// 2.03125

						}
					}
					// }
				}
				player.sendMessage("ptdew&dewdd:goldore Pumkin done");
			}
			catch (Exception e) {
				return;
			}

		}
	}

	class item55deletec implements Runnable {

		private Block	block	= null;
		private Player	player	= null;
		private int		id		= -29;
		private byte	dat		= -29;

		public item55deletec(Block block, Player player, int id, byte dat) {
			this.block = block;
			this.player = player;
			this.id = id;
			this.dat = dat;
		}

		public void run() {

			if (block.getTypeId() != id) {
				return;
			}

			if (dat != -29 && block.getData() != dat) {
				return;
			}

			try {
				if (Economy.getMoney(player.getName()) < 100) {
					return;
				}
			}
			catch (UserDoesNotExistException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (amount3 > player.getItemInHand().getAmount() * 100) {
				return;
			}

			if (checkpermissionarea(block, player, "delete") == true) {
				return;
			}
			if (isprotectitemid(block.getTypeId()) == true) {
				return;
			}

			try {
				Economy.subtract(player.getName(), 10);
			}
			catch (UserDoesNotExistException | NoLoanPermittedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			amount3++;

			block.breakNaturally();
			Block b2 = null;

			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						b2 = block.getRelative(x, y, z);

						if (amount2 > 100) {
							return;
						}
						amount2++;
						try {
							item55delete(b2, player, id, dat);
						}
						catch (UserDoesNotExistException
								| NoLoanPermittedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						amount2--;
					}
				}
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

					printAll("ptdew&dewdd: '" + player.getName()
							+ "' give item '" + itm.getType().name() + "' to '"
							+ "[" + pxpluck + "/" + pxpmax + "] "
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

	class runter_mom implements Runnable {

		private Player	player;
		private int		a1;
		private byte	a2;
		private String	e;

		public runter_mom(Player player, String e, int a1, byte a2) {
			this.player = player;
			this.a1 = a1;
			this.a2 = a2;
			this.e = e;
		}

		public void run() {
			Material ik = Material.getMaterial(a1);

			if (ik == null) {
				player.sendMessage("what the hellitem " + a1 + ":" + a2);
				return;
			}

			if (ik.isBlock() == false) {
				player.sendMessage("it's not a block ? " + a1 + ":" + a2);
				return;
			}

			if (isdewset(a1) == false) {
				player.sendMessage("this item my plugin not allow for dewset "
						+ a1 + ":" + a2);
				return;
			}

			if (dewddtps.tps.getTPS() < 17) {
				printAll("ptdew&dewdd: dew set system need to pause cuz tps of server below than 17 try again!"
						+ dewddtps.tps.getTPS());

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

	class soiladdseedc implements Runnable {
		private Block	block	= null;
		private Player	player	= null;
		private int		seedid	= 0;
		private boolean	first	= false;

		public soiladdseedc(Block block, Player player, int seedid,
				boolean first) {
			this.block = block;
			this.player = player;
			this.seedid = seedid;
			this.first = first;
		}

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

			if (blockCut.getRelative(0, -1, 0).getTypeId() != 60) {
				return;
			}

			if (blockCut.getTypeId() != 0) {
				return;
			}

			boolean haswater = false;

			for (int xx3 = 4; xx3 >= -4; xx3--) {
				for (int zz3 = 4; zz3 >= -4; zz3--) {
					if (blockCut.getRelative(xx3, -1, zz3).getTypeId() == 9
							|| blockCut.getRelative(xx3, -1, zz3).getTypeId() == 8
							|| blockCut.getRelative(xx3, 0, zz3).getTypeId() == 9
							|| blockCut.getRelative(xx3, 0, zz3).getTypeId() == 8) {
						haswater = true;
						break;
					}
				}

				if (haswater == true) {
					break;
				}
			}

			if (haswater == false) {
				return;
			}

			if (blockCut.getLightLevel() < 9) {
				return;
			}

			if (decreseitem1(player, seedid, 0, false) == false) {
				player.saveData();
				return;
			}

			blockCut.setTypeId(itemaddid);
			blockCut.setData(itemadddata);
			// printA(digX + "," + digY + "," + digZ);

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

	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}

	public JavaPlugin	ac							= null;
	// automessage
	public String		pmainoveride				= "dewdd.main.overide";
	public String		pmaindewbuyreplace			= "dewdd.main.dewbuy.replace";

	public String		pmaindewbuynotcount			= "dewdd.main.dewbuy.notcount";
	public String		pmaindewbuymodifymember		= "dewdd.main.dewbuy.modifymember";
	public String		pmaindelete					= "dewdd.main.delete";
	public String		pmaindewbuydelete			= "dewdd.main.dewbuydelete";
	public String		pmaindewbuychangehost		= "dewdd.main.dewbuy.changehost";

	public String		pmaindewseteverywhere		= "dewdd.main.dewset.everywhere";
	public int			runtime						= 500;
	public String		flag_everyone				= "everyone";

	public String		flag_pvp					= "<pvp>";

	// redim for each world each protect = 100

	public String		flag_monster				= "<monster>";

	public String		flag_stopwater				= "<stopwater>";
	public String		flag_noinwater				= "<noinwater>";
	public String		flag_nooutwater				= "<nooutwater>";

	public String		flag_protecthopper			= "<protecthopper>";
	public String		flag_sign					= "<sign>";

	public String		flag_sell					= "<sell>";

	public String		flag_protectanimal			= "<protectanimal>";
	// for staff
	public String		pmainplaceblocknoprotect	= "dewdd.main.placeblock.noprotect";

	public int			max_b						= 29000;

	public boolean		allownight					= true;
	public int			dewsignlimit				= 200;
	public String		dewsignname[][][];
	public int			dewsignnamemax				= 20;

	public int			dewsignmax[];
	public int			dewsignloop[][];
	public int			dewsignx1[][];

	public int			dewsigny1[][];
	public int			timechunkx[];

	public int			timechunkz[];
	public int			timechunkmax				= -1;

	public int			dewsignz1[][];

	public int			dewsignx2[][];
	public int			dewsigny2[][];
	public int			amount2						= 0;									// recursive
																							// 55

	// public double buy1blockmoney = 0.0890932504689118445732202345959;

	public int			amount3						= 0;									// count
																							// 55

	public int			dewsignz2[][];
	public boolean		allowfly					= true;

	public int			selectmax					= 29;
	public String		selectname[]				= new String[selectmax + 1];

	public int			selectx1[]					= new int[selectmax + 1];

	public int			selecty1[]					= new int[selectmax + 1];

	public int			selectz1[]					= new int[selectmax + 1];

	public Block		selectblock[]				= new Block[selectmax + 1];

	// autosearchsub

	public int			d4[]						= new int[selectmax + 1];

	public int			selectx2[]					= new int[selectmax + 1];

	public int			selecty2[]					= new int[selectmax + 1];

	public int			selectz2[]					= new int[selectmax + 1];

	public String		selectworldname[]			= new String[selectmax + 1];

	public boolean		hidemode[]					= new boolean[selectmax + 1];

	public boolean		dewaxe[]					= new boolean[selectmax + 1];

	public boolean		chatever[]					= new boolean[selectmax + 1];

	public boolean		moninvi						= false;

	public boolean		monfast						= false;

	public boolean		monjump						= true;

	// Check Permission Area block

	// Check Permission Area block player mode

	public String		dewworldlist[];

	public int			dewworldlistmax				= 0;

	public Random		randomG						= new Random();

	// dewbuy

	int					randomInt					= randomG.nextInt(500);

	public dewset() {
		// if (firstrun19 == false){

		// loadadminlist();

		timechunkx = new int[10000];
		timechunkz = new int[10000];

		// firstrun19 = true;
		// }
	}

	public void adderarraysignfile(int worldid) {
		this.dewsignmax[worldid]++;
	}

	// cut seem block

	// addfood withmoney
	public void addfoodwithmoney(Player player) {
		if (player.getName().equalsIgnoreCase("") == false) {
			return;
		}

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

	// cutwoodsub

	public void autosortchest2(Block block, Player player) {
		autosortchest2_class ar = new autosortchest2_class(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);
	}

	// *********

	public boolean canaddtorch(Block bbc) {
		if (bbc.getTypeId() != 0) {
			return false;
		}

		if (canaddtorchatblock(bbc.getRelative(0, -1, 0)) == true
				|| canaddtorchatblock(bbc.getRelative(1, 0, 0)) == true
				|| canaddtorchatblock(bbc.getRelative(-1, 0, 0)) == true
				|| canaddtorchatblock(bbc.getRelative(0, 0, 1)) == true
				|| canaddtorchatblock(bbc.getRelative(0, 0, -1)) == true) {
			return true;

		}

		return false;
	}

	// decrese item 1

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
			if (digx > x1 - 1 && digx < x2 + 1) {
				// x true

				if (y1 <= y2) {
					if (digy > y1 - 1 && digy < y2 + 1) {
						// y true
						if (z1 <= z2) {
							if (digz > z1 - 1 && digz < z2 + 1) {
								// z true
								goodc1 = true;
							}
						}
						else { // z1 > z2
							if (digz < z1 + 1 && digz > z2 - 1) {
								// z true
								goodc1 = true;
							}
						}

					}
				}
				else { // y1 > y2
					if (digy < y1 + 1 && digy > y2 - 1) {
						// y true
						if (z1 <= z2) {
							if (digz > z1 - 1 && digz < z2 + 1) {
								// z true
								goodc1 = true;
							}
						}
						else { // z1 > z2
							if (digz < z1 + 1 && digz > z2 - 1) {
								// z true
								goodc1 = true;
							}
						}
					}
				}

			}
		}
		else { // x1 > x2
			if (digx < x1 + 1 && digx > x2 - 1) {
				// x true

				if (y1 <= y2) {
					if (digy > y1 - 1 && digy < y2 + 1) {
						// y true
						if (z1 <= z2) {
							if (digz > z1 - 1 && digz < z2 + 1) {
								// z true
								goodc1 = true;
							}
						}
						else { // z1 > z2
							if (digz < z1 + 1 && digz > z2 - 1) {
								// z true
								goodc1 = true;
							}
						}

					}
				}
				else { // y1 > y2
					if (digy < y1 + 1 && digy > y2 - 1) {
						// y true
						if (z1 <= z2) {
							if (digz > z1 - 1 && digz < z2 + 1) {
								// z true
								goodc1 = true;
							}
						}
						else { // z1 > z2
							if (digz < z1 + 1 && digz > z2 - 1) {
								// z true
								goodc1 = true;
							}
						}
					}
				}

			}
		}

		return goodc1;

	}

	// Check Permission Area block
	public boolean checkpermissionarea(Block block) {

		if (getworldid(block.getWorld().getName()) == -1) {
			return false;
		}
		if (dewsignmax[getworldid(block.getWorld().getName())] == 0) {
			return false;
		}

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

			if (goodc1 == true) {
				return true;
			}

		}
		return false;

	}

	public int checkpermissionarea(Block block, boolean gethomeid) {

		if (getworldid(block.getWorld().getName()) == -1) {

			return -1;
		}
		if (dewsignmax[getworldid(block.getWorld().getName())] == 0) {
			return -1;

		}

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
					// printAll("dewsignnow " + dewsignnow );

					if (dewsignname[getworldid(block.getWorld().getName())][dewsignnow][0]
							.equalsIgnoreCase(flag_everyone) == true) {
						continue;
					}
				}
				else {
					if (dewsignname[getworldid(block.getWorld().getName())][dewsignnow][0]
							.equalsIgnoreCase(flag_everyone) == false) {
						continue;
					}
				}
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

				if (goodc1 == true) {
					dewsignloop[getworldid(block.getWorld().getName())][dewsignnow]++;
					break;
				}
			} // loog sign

			if (goodc1 == true) {
				return dewsignnow;
			}

		} // loop prio

		if (goodc1 == true) {
			return dewsignnow;
		}

		return -1;

	}

	// Check Permission Area block player mode
	public boolean checkpermissionarea(Block block, Player player,
			String modeevent) {

		if (getworldid(block.getWorld().getName()) == -1) {

			if (api_admin.dewddadmin.is2moderator(player) == true) {
				return true;
			}
			else {
				return false;
			}

		}

		if (dewsignmax[getworldid(block.getWorld().getName())] == -1) {

			if (api_admin.dewddadmin.is2moderator(player) == true) {
				return true;
			}
			else {
				return false;
			}

		}

		int digx = block.getX();
		int digy = block.getY();
		int digz = block.getZ();

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

				if (che1 == true) { // if for everyone
					goodc1 = false;

					if (api_admin.dewddadmin.is2moderator(player) == true) { // if
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
				else { // not for everyone
					che2 = havethisnameinthishome(getworldid(block.getWorld()
							.getName()), dewsignnow, player.getName());

					goodc1 = !che2;
				}

				if (goodc1 == true) { // don't have permission
					if (player.hasPermission(pmainoveride) == true) { // overide
						goodc1 = false;
					}
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

					if (che1 == true) { // if for <sign>
						goodc1 = false;
						if (api_admin.dewddadmin.is2moderator(player) == true) { // if
																					// staff
																					// can't
																					// do
							// it
							goodc1 = true;
						}

					}
					else { // if not for sign
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

				if (goodc1 == true) { // don't have permission
					if (player.hasPermission(pmainoveride) == true) { // overide
						goodc1 = false;
					}
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

				if (goodc1 == true) { // don't have permission
					if (player.hasPermission(pmainoveride) == true) { // overide
						goodc1 = false;
					}
				}
				return goodc1;

			} // right click or not
			else { // not right click

				che2 = havethisnameinthishome(getworldid(block.getWorld()
						.getName()), dewsignnow, player.getName());

				goodc1 = !che2;

				if (goodc1 == true) { // don't have permission
					if (player.hasPermission(pmainoveride) == true) { // overide
						goodc1 = false;
					}
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

			if (modeevent.equalsIgnoreCase("dewset") == true) {
				boolean isa = api_admin.dewddadmin.is2admin(player);
				// if can place and is dewset
				if (isa == false) {
					return !player.hasPermission(this.pmaindewseteverywhere);
				}
				else if (isa == true) {
					return !player.isOp();

				}
			}

			return goodc1;
		}

	}

	public void chestabsorb() {
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
			if (checkpermissionarea(block, player, "build") == true) { // can't
																		// build
				continue;
			}

			for (int gx = (0 - d4); gx <= (0 + d4); gx++) {
				for (int gy = (0 - d4); gy <= (0 + d4); gy++) {
					for (int gz = (0 - d4); gz <= (0 + d4); gz++) {
						// first search sign
						block = player.getLocation().getBlock()
								.getRelative(gx, gy, gz);
						if (block == null) {
							continue;
						}

						if (block.getTypeId() != 63 && block.getTypeId() != 68) {
							continue;
						}

						Sign sign = (Sign) block.getState();
						if (sign.getLine(0).equalsIgnoreCase("[dewtobox]") == true) {
							// player.sendMessage("found dewtobox sign : " +
							// block.getLocation().getBlockX() + ","
							// + block.getLocation().getBlockY() + "," +
							// block.getLocation().getBlockZ());

							int intb = Integer.parseInt(sign.getLine(1));
							if (intb == 0) {
								continue;
							}

							// after found sign so find box

							// box
							for (int ax = (0 - d5); ax <= (0 + d5); ax++) {
								for (int ay = (0 - d5); ay <= (0 + d5); ay++) {
									for (int az = (0 - d5); az <= (0 + d5); az++) {
										block2 = block.getRelative(ax, ay, az);
										if (block2 == null) {
											continue;
										}

										if (block2.getTypeId() != 54) {
											continue;
										}

										// player.sendMessage("found dewtobox chest : "
										// + block2.getLocation().getBlockX() +
										// ","
										// + block2.getLocation().getBlockY() +
										// "," +
										// block2.getLocation().getBlockZ());

										slotp = player.getInventory().first(
												intb);
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
												player.getInventory().getItem(
														slotp));

										player.getInventory().clear(slotp);
										player.updateInventory();

										player.sendMessage("[dewtobox] moved "
												+ intb);

										// added

										// if true
										// check is empty
										// check item of player

									}
								}
							}
							// box

						}

					}
				}
			}

		}

	}

	public void createmonster(Player player, EntityType EntityTypeGot, int count) {
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
							Block block2 = player.getWorld().getBlockAt(lx, ly,
									lz);
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
					if (y > 253 || y < 1) {
						return;
					}
				}

			}

			loca = player.getLocation();
			loca.setX(x);
			loca.setY(y);
			loca.setZ(z);

			player.getWorld().spawnCreature(loca, EntityTypeGot);

		} // loop monster all
	}

	// cut seem block
	public void cutseemblock(Block block123, Player player123, int countja) {
		try {
			int gx = 0;
			int gy = 0;
			int gz = 0;
			int g4 = 1;

			if (api_admin.dewddadmin.is2admin(player123) == false) {
				return;
			}

			if (checkpermissionarea(block123) == true) {
				return;
			}

			int seemblockid = block123.getTypeId();

			switch (player123.getItemInHand().getTypeId()) {
			case 7:
				// player123.sendMessage("ptdew&dewdd:" + countja +
				// " 7 cut seem block (" + block123.getX() + "," +
				// block123.getY() + "," + block123.getZ() + ")");
				block123.setTypeId(0);
				countja++;
				if (countja > 50) {
					return;

				}
				break;
			default:
				return;
			}

			World world = block123.getWorld();

			for (gx = block123.getX() - g4; gx <= block123.getX() + g4; gx++) {
				for (gy = block123.getY() - g4; gy <= block123.getY() + g4; gy++) {
					for (gz = block123.getZ() - g4; gz <= block123.getZ() + g4; gz++) {
						Block blockt1 = world.getBlockAt(gx, gy, gz);

						if (blockt1.getTypeId() == seemblockid) {
							if (checkpermissionarea(block123) == true) {
								continue;
							}

							countja++;
							if (countja > 50) {
								return;
							}
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

	public void cutwoodsub(Block block123, Player player123, boolean isfirsttime) {
		cutwoodsubc ab = new cutwoodsubc(block123, player123, isfirsttime);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	// decrese item 1
	public boolean decreseitem1(Player player, int itemid, int itemdata,
			boolean forcetruedata) {
		ItemStack itm = null;
		int len = player.getInventory().getContents().length;
		int lenl = 0;

		if (itemid == 0) {
			return false;
		}

		for (lenl = 0; lenl < len - 1; lenl++) {
			if (player.getInventory().getItem(lenl) == null) {
				continue;
			}

			itm = player.getInventory().getItem(lenl);

			if ((itemid == 8) || (itemid == 9) || (itemid == 326)) {
				if ((itm.getTypeId() != 8) && (itm.getTypeId() != 9)
						&& (itm.getTypeId() != 326)) {
					continue;
				}
			}

			else if ((itemid == 44) || (itemid == 43)) {
				if ((itm.getTypeId() != 44) && (itm.getTypeId() != 43)) {
					continue;
				}
			}

			// piston
			else if ((itemid == 33) || (itemid == 34) || (itemid == 29)) {
				if ((itm.getTypeId() != 33) && (itm.getTypeId() != 34)
						&& (itm.getTypeId() != 29)) {
					continue;
				}
			}

			// dirt
			else if ((itemid == 2) || (itemid == 3) || (itemid == 60)) {
				if ((itm.getTypeId() != 3) && (itm.getTypeId() != 2)
						&& (itm.getTypeId() != 60)) {
					continue;
				}
			}
			// repeater
			else if ((itemid == 356) || (itemid == 93) || (itemid == 94)) {
				if ((itm.getTypeId() != 356) && (itm.getTypeId() != 93)
						&& (itm.getTypeId() != 94)) {
					continue;
				}
			}
			// redstone torch
			else if ((itemid == 75) || (itemid == 76)) {
				if ((itm.getTypeId() != 75) && (itm.getTypeId() != 76)) {
					continue;
				}
			}
			// redstone wire
			else if ((itemid == 331) || (itemid == 55)) {
				if ((itm.getTypeId() != 331) && (itm.getTypeId() != 55)) {
					continue;
				}
			}
			// pumpkin
			else if ((itemid == 361) || (itemid == 104)) {
				if ((itm.getTypeId() != 361) && (itm.getTypeId() != 104)) {
					continue;
				}
			}
			// melon
			else if ((itemid == 362) || (itemid == 105)) {
				if ((itm.getTypeId() != 362) && (itm.getTypeId() != 105)) {
					continue;
				}
			}
			// wheat
			else if ((itemid == 295) || (itemid == 59)) {
				if ((itm.getTypeId() != 295) && (itm.getTypeId() != 59)) {
					continue;
				}
			}
			// carrot
			else if ((itemid == 391) || (itemid == 141)) {
				if ((itm.getTypeId() != 391) && (itm.getTypeId() != 141)) {
					continue;
				}
			}
			// potato
			else if ((itemid == 392) || (itemid == 142)) {
				if ((itm.getTypeId() != 392) && (itm.getTypeId() != 142)) {
					continue;
				}
			}
			else {
				if (itm.getTypeId() != itemid) {
					continue;
				}

			}

			if (forcetruedata == true) {
				if (itm.getData().getData() != itemdata) {
					continue;
				}
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

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewbreak please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewbreak please set block2");
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName() + "'starting dewbreak "
				+ handid + ":" + handdata);

		if (player.hasPermission(pmaindelete) == false) {
			player.sendMessage("you don't have permission to access any delete commands");
			return;
		}

		for (Block blb : getselectblock(getid, player)) {
			if (blb.getTypeId() != handid || blb.getData() != handdata) {
				continue;
			}
			if (checkpermissionarea(blb, player, "dewset") == true) {
				return;
			}

			/*
			 * if (PreciousStones.API().canBreak(player, blb.getLocation())==
			 * false) { player.sendMessage("ptdew&dewdd:Can't dewdelete here ("
			 * + blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			/*
			 * if
			 * (PreciousStones.API().isFieldProtectingArea(FieldFlag.PREVENT_PLACE
			 * , blb.getLocation())==false) { player.sendMessage(
			 * "ptdew&dewdd:Can't dewdelete if not your home zome (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			blb.breakNaturally();

		}

		printAll("ptdew&dewdd: dewbreak done : " + player.getName());
	}

	public boolean dewbuy(Player player, boolean runnow) {
		dewbuy_class ar = new dewbuy_class(player);

		if (runnow == false) {
			Bukkit.getScheduler().runTask(ac, ar);
		}
		else {
			ar.run();
		}

		return ar.isok;

	}

	public void dewbuydelete(Player player) {
		/*
		 * if (player.hasPermission(pmaindewbuydelete) == false) {
		 * player.sendMessage("ptdew&dewdd: only op can use dewbuydelete");
		 * return;
		 * }
		 */

		// find id home
		int xyz = checkpermissionarea(player.getLocation().getBlock(), true);
		if (xyz == -1) {
			player.sendMessage("ptdew&dewdd : this area is not protection ... nor home");
			return;
		}

		if (this.dewsignname[getworldid(player.getWorld().getName())][xyz][0]
				.equalsIgnoreCase(player.getName()) == false
				&& player.hasPermission(pmaindewbuydelete) == false) {
			player.sendMessage("this is not your protect! and you are not admin so can't delete protected zone");
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName()
				+ "'starting dewbuydelete "
				+ player.getItemInHand().getTypeId() + ":"
				+ player.getItemInHand().getData());

		savesignfile(xyz, getworldid(player.getWorld().getName()));
		loadsignfile();

		printAll("ptdew&dewdd : " + player.getName() + " dewbuydelete done");
	}

	public void dewbuyremove(Player player) {

		if (player.hasPermission(this.pmaindewbuydelete) == false) {
			player.sendMessage("ptdew&dewdd : you don't have permission for dewbuyremove");
			return;
		}

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewbuyremove please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewbuyremove please set block2");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

		printAll("ptdew&dewdd : '" + player.getName()
				+ "'starting dewbuydelete "
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

			printAll("removed zone id " + dd);

		}

		printAll("ptdew&dewdd: dewbuyremove done : " + player.getName());
	}

	public void dewbuyzone(Player player, Block block2)
			throws UserDoesNotExistException, NoLoanPermittedException {
		if (api_admin.dewddadmin.is2moderator(player) == true) {
			player.sendMessage("ptdew&dewdd: Staff can't buy zone");
			return;
		}

		int homeid = checkpermissionarea(block2, true);
		if (homeid == -1) {
			player.sendMessage("ptdew&dewdd : This is not protect zone...");
			return;
		}

		// String abab = dewsignname[homeid][18] ;
		if (dewsignname[getworldid(player.getWorld().getName())][homeid][18]
				.equalsIgnoreCase(flag_sell) == true) {
			printAll("ptdew&dewdd : '" + player.getName()
					+ "'starting dewbuyzone "
					+ player.getItemInHand().getTypeId() + ":"
					+ player.getItemInHand().getData());

			int mon = Integer.parseInt(dewsignname[getworldid(player.getWorld()
					.getName())][homeid][19]);
			player.sendMessage("ptdew&dewdd : This zone need money > " + mon);

			if (Economy.getMoney(player.getName()) < mon) {
				player.sendMessage("ptdew&dewdd: you don't have enough money for buy this zone > "
						+ mon);
				return;
			}

			Economy.subtract(player.getName(), mon);
			for (int g = 0; g <= 19; g++) {
				dewsignname[getworldid(player.getWorld().getName())][homeid][g] = "null";
			}

			dewsignname[getworldid(player.getWorld().getName())][homeid][0] = player
					.getName();
			printAll("ptdew&dewdd: " + player.getName()
					+ "dewbuy zone complete...");
			this.savesignfile(-1, getworldid(block2.getWorld().getName()));

		}
		else {
			player.sendMessage("ptdew&dewdd : This zone  don't allow you to buy sorry...");
			return;
		}

	}

	public void dewclearchunk(World world) {

		double temp1 = 0;
		double temp2 = 0;
		double temp4 = 0;
		double temp5 = 0;
		@SuppressWarnings("unused")
		boolean bo = false;

		for (Chunk chunk : world.getLoadedChunks()) {
			if (chunk == null) {
				continue;
			}
			bo = true;
			if (world.isChunkInUse(chunk.getX(), chunk.getZ()) == true) {
				continue;
			}

			for (Player pla : world.getPlayers()) {
				Block block = pla.getLocation().getBlock();

				int digX = block.getX();
				int digZ = block.getZ();
				temp1 = Math.pow(digX - chunk.getX(), 2);
				temp2 = Math.pow(digZ - chunk.getZ(), 2);
				temp5 = Math.pow(temp1 + temp2, 0.5);
				temp4 = (int) (temp5);

				if (temp4 < 160) {
					bo = false;
					break;
				}
			}

			if (bo = true) {
				if (chunk.isLoaded() == true) {
					printC("ptdew&dewdd:clean Chunk " + chunk.getX() + ",256,"
							+ chunk.getZ() + " >distance " + temp4);
					chunk.unload(true, true);
				}
			}

		}
		printC("ptdew&dewdd:Cleaned Chunk");
	}

	public void dewcopy(Player player) {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewcopy please set block1");

			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {

			return;
		}

		boolean xc = false;
		boolean yc = false;
		boolean zc = false;

		// x
		if (selectx1[getid] <= selectx2[getid]) {
			if ((player.getLocation().getBlockX() >= selectx1[getid])
					&& (player.getLocation().getBlockX() <= selectx2[getid])) {
				xc = true;
			}
		}
		else {
			if ((player.getLocation().getBlockX() <= selectx1[getid])
					&& (player.getLocation().getBlockX() >= selectx2[getid])) {
				xc = true;
			}
		}

		// y
		if (selecty1[getid] <= selecty2[getid]) {
			if ((player.getLocation().getBlockY() >= selecty1[getid])
					&& (player.getLocation().getBlockY() <= selecty2[getid])) {
				yc = true;
			}
		}
		else {
			if ((player.getLocation().getBlockY() <= selecty1[getid])
					&& (player.getLocation().getBlockY() >= selecty2[getid])) {
				yc = true;
			}
		}

		// z
		if (selectz1[getid] <= selectz2[getid]) {
			if ((player.getLocation().getBlockY() >= selectz1[getid])
					&& (player.getLocation().getBlockY() <= selectz2[getid])) {
				zc = true;
			}
		}
		else {
			if ((player.getLocation().getBlockY() <= selectz1[getid])
					&& (player.getLocation().getBlockY() >= selectz2[getid])) {
				zc = true;
			}
		}

		if ((xc == true && yc == true && zc == true) == true) {
			player.sendMessage("ptdew&dewdd: dewcopy can't run cuz you stand on copy area");
			return;
		}

		// Block bz = new Block();

		if (dewddtps.tps.getTPS() < 17) {
			printAll("ptdew&dewdd: dew copy system need to pause cuz tps of server below than 17 try again!"
					+ dewddtps.tps.getTPS());

			return;
		}

		printAll("ptdew&dewdd : '" + player.getName() + "'starting dewcopy "
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
				else if (amountloop == 2) {
					if (bbd.getType().isBlock() == true) {
						continue;
					}
				}

				/*
				 * if (player
				 * .getLocation()
				 * .getBlock()
				 * .getRelative(bbd.getX() - selectx1[getid],
				 * bbd.getY() - selecty1[getid],
				 * bbd.getZ() - selectz1[getid]).getTypeId() != 0) {
				 * continue;
				 * }
				 */

				if (checkpermissionarea(
						player.getLocation()
								.getBlock()
								.getRelative(bbd.getX() - selectx1[getid],
										bbd.getY() - selecty1[getid],
										bbd.getZ() - selectz1[getid]), player,
						"dewset") == true) {
					return;
				}

				if (api_admin.dewddadmin.is2admin(player) == false
						&& api_admin.dewddadmin.is2moderator(player) == false) {
					if (decreseitem1(player, bbd.getTypeId(), bbd.getData(),
							true) == false) {
						player.sendMessage("ptdew&dewdd : dewcopy not enought item   > "
								+ bbd.getTypeId() + ":" + bbd.getData());
						return;
					}
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
												bbd.getZ()).getData(), false);

			}
		}
		printAll("ptdew&dewdd: " + player.getName() + " dewcopy done");

	}

	public void dewdelete(Player player, int handid, byte handdata) {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewdelete please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewdelete please set block2");
			return;
		}

		if (player.hasPermission(pmaindelete) == false) {
			player.sendMessage("you don't have permission to access any delete commands");
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName() + "'starting dewdelete "
				+ handid + ":" + handdata);

		for (Block blb : getselectblock(getid, player)) {
			if (blb.getTypeId() != handid || blb.getData() != handdata) {
				continue;
			}
			if (checkpermissionarea(blb, player, "dewset") == true) {
				return;
			}

			if (checkpermissionarea(blb) == false) {
				continue;
			}

			/*
			 * if (PreciousStones.API().canBreak(player, blb.getLocation())==
			 * false) { player.sendMessage("ptdew&dewdd:Can't dewdelete here ("
			 * + blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			/*
			 * if
			 * (PreciousStones.API().isFieldProtectingArea(FieldFlag.PREVENT_PLACE
			 * , blb.getLocation())==false) { player.sendMessage(
			 * "ptdew&dewdd:Can't dewdelete if not your home zome (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			blb.setTypeId(0, false);

		}

		printAll("ptdew&dewdd: dewdelete done : " + player.getName());
	}

	public void dewdig(Player player) {
		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewdig please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewdig please set block2");
			return;
		}

		if (player.hasPermission(pmaindelete) == false) {
			player.sendMessage("you don't have permission to access any delete commands");
			return;
		}

		if (player.getItemInHand().getType().getMaxDurability() <= 0) {
			player.sendMessage("ptdew&dewdd:dewdig item : max Durability = "
					+ player.getItemInHand().getType().getMaxDurability());
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName() + "'starting dewdddig "
				+ player.getItemInHand().getTypeId() + ":"
				+ player.getItemInHand().getData());

		for (Block blb : getselectblock(getid, player)) {
			if (checkpermissionarea(blb, player, "dewset") == true) {
				return;
			}

			/*
			 * if (PreciousStones.API().canBreak(player, blb.getLocation())==
			 * false) { player.sendMessage("ptdew&dewdd:Can't dewdig here (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			if (blb.getTypeId() == 0) {
				continue;
			}

			if (player.getItemInHand().getDurability() > player.getItemInHand()
					.getType().getMaxDurability()) {

				return;
			}

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
							(short) ((short) (player.getItemInHand()
									.getDurability()) + ((short) (1))));

		}

		printAll("ptdew&dewdd: dewdig done : " + player.getName());
	}

	public void dewdot(Player player) throws UserDoesNotExistException,
			NoLoanPermittedException {

		int amo = 0;
		if (player.getInventory().getItem(0) != null) {
			amo += player.getInventory().getItem(0).getAmount();
		}
		if (player.getInventory().getItem(1) != null) {
			amo += player.getInventory().getItem(0).getAmount();
		}

		if (amo == 0) {
			player.sendMessage("ptdew&dewdd: dewdot count radius from amount of item1 and item2");
			player.sendMessage("ptdew&dewdd: dewdot you don't have any item");
			return;
		}

		if (Economy.getMoney(player.getName()) < 500) {
			player.sendMessage("ptdew&dewdd: dewdot need money 500");
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName() + "'starting dewdot "
				+ player.getItemInHand().getTypeId() + ":"
				+ player.getItemInHand().getData());

		Economy.setMoney(player.getName(),
				Economy.getMoney(player.getName()) - 500);

		int xx = (int) player.getLocation().getX();
		int yy = (int) player.getLocation().getY();
		int zz = (int) player.getLocation().getZ();

		for (int x = 0 - amo; x <= 0 + amo; x++) {
			if ((x != 0 - amo) && (x != 0 + amo) && (x != 0)) {
				continue;
			}

			for (int y = 0 - amo; y <= 0 + amo; y++) {
				if ((y != 0 - amo) && (y != 0 + amo) && (y != 0)) {
					continue;
				}

				for (int z = 0 - amo; z <= 0 + amo; z++) {
					if ((z != 0 - amo) && (z != 0 + amo) && (z != 0)) {
						continue;
					}

					Block blockk = player.getWorld().getBlockAt(xx, yy, zz)
							.getRelative(x, y, z);
					if (checkpermissionarea(blockk, player, "dewset") == true) {
						continue;
					}

					blockk.setTypeId(20);
					randomplaynote(blockk.getLocation());
				}
			}
		}

		printAll("ptdew&dewdd: " + player.getName() + "dewdot done!");

	}

	public void dewdown(Player player, int handid, byte handdata) {

		Block block = player.getLocation().getBlock().getRelative(0, -2, 0);
		if (isprotectitemid(block.getTypeId()) == true) {
			return;
		}

		if (checkpermissionarea(block, player, "dewset") == true) {
			player.sendMessage("ptdew&dewdd: dewdown not complete ; that is another player area");
			return;
		}

		printC("ptdew&dewdd : '" + player.getName() + "'starting dewdown "
				+ handid + ":" + handdata);

		block.setTypeId(handid);
		block.setData(handdata);

		if (decreseitem1(player, handid, handdata, true) == false) {
			player.sendMessage("ptdew&dewdd : dewdown not enought item");
			return;
		}

		printC("ptdew&dewdd : dewdown " + player.getName() + " complete");

	}

	public void dewextend(Player player) {
		player.sendMessage("ptdew&dewdd : dewextend starting");
		int getid = getfreeselect(player);
		selecty1[getid] = 1;
		selecty2[getid] = 254;

		player.sendMessage("ptdew&dewdd: selected area = (" + selectx1[getid]
				+ "," + selecty1[getid] + "," + selectz1[getid] + ") to ("
				+ selectx2[getid] + "," + selecty2[getid] + ","
				+ selectz2[getid] + ") = ");

		player.sendMessage("ptdew&dewdd : dewextend completed");
	}

	public void dewfill(Player player, int handid, byte handdata) {

		dewfill_mom aer = new dewfill_mom(player, handid, handdata);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewfullcircle(Player player, int handid, byte handdata) {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewfullcircle please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewfullcircle please set block2");
			return;
		}

		double midx = ((double) (selectx1[getid]) + (double) (selectx2[getid])) / 2;
		double midy = ((double) (selecty1[getid]) + (double) (selecty2[getid])) / 2;
		double midz = ((double) (selectz1[getid]) + (double) (selectz2[getid])) / 2;

		if ((midx == selectx1[getid] && midy == selecty1[getid] && midz == selectz1[getid])
				|| (midx == selectx2[getid] && midy == selecty2[getid] && midz == selectz2[getid])) {
			player.sendMessage("ptdew&dewdd: small circle can't run program");
			return;
		}

		double temp1 = 0;

		double temp5 = 0;
		double temp2 = 0;
		double temp3 = 0;
		temp1 = Math.pow((double) (selectx1[getid])
				- ((double) (selectx2[getid])), 2);

		temp2 = Math.pow((double) (selecty1[getid])
				- ((double) (selecty2[getid])), 2);

		temp3 = Math.pow((double) (selectz1[getid])
				- ((double) (selectz2[getid])), 2);

		double midty = ((selecty1[getid]) + (selecty2[getid])) / 2;

		double midtx = ((selectx1[getid]) + (selectx2[getid])) / 2;

		double midtz = ((selectz1[getid]) + (selectz2[getid])) / 2;
		temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

		double midr = temp5 / 3;
		Block blockmid = player.getWorld().getBlockAt((int) midtx, (int) midty,
				(int) midtz);

		player.sendMessage("cir=" + midtx + "," + midty + "," + midtz);
		player.sendMessage("R=" + temp5);

		printAll("ptdew&dewdd : '" + player.getName()
				+ "'starting dewfullcircle " + handid + ":" + handdata);

		for (Block blb : getselectblock(getid, player)) {

			/*
			 * if (PreciousStones.API().canPlace(player, blb.getLocation())==
			 * false) {
			 * player.sendMessage("ptdew&dewdd:Can't dewfullcircle here (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			if (blb.getLocation().distance(blockmid.getLocation()) > midr) {
				continue;
			}

			if (blb.getTypeId() != 0) {
				continue;
			}
			if (checkpermissionarea(blb, player, "dewset") == true) {
				return;
			}
			if (decreseitem1(player, handid, handdata, true) == false) {
				player.sendMessage("ptdew&dewdd: dont have enough item");
				return;
			}
			blb.setTypeIdAndData(handid, handdata, true);
			//
		} // for

		printAll("ptdew&dewdd: dewfullcircle done : " + player.getName());
	}

	public void dewselectcube(Player player, int rad) {
		player.sendMessage("ptdew&dewdd : dew select cube starting");
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

		player.sendMessage("ptdew&dewdd: selected area = (" + selectx1[getid]
				+ "," + selecty1[getid] + "," + selectz1[getid] + ") to ("
				+ selectx2[getid] + "," + selecty2[getid] + ","
				+ selectz2[getid] + ") = ");

		player.sendMessage("ptdew&dewdd : dew select cube completed");
	}

	public void dewselectprotect(Player player) {
		player.sendMessage("ptdew&dewdd : dew select protect starting");
		Block block = player.getLocation().getBlock();
		int getid = this.checkpermissionarea(block, true);
		if (getid == -1) {
			player.sendMessage("ptdew&dewdd : This block don't have protect");
			return;
		}

		int getid2 = this.getfreeselect(player);

		selectx1[getid2] = dewsignx1[getworldid(block.getWorld().getName())][getid];
		selectx2[getid2] = dewsignx2[getworldid(block.getWorld().getName())][getid];

		selecty1[getid2] = dewsigny1[getworldid(block.getWorld().getName())][getid];
		selecty2[getid2] = dewsigny2[getworldid(block.getWorld().getName())][getid];

		selectz1[getid2] = dewsignz1[getworldid(block.getWorld().getName())][getid];
		selectz2[getid2] = dewsignz2[getworldid(block.getWorld().getName())][getid];

		player.sendMessage("ptdew&dewdd: selected area = (" + selectx1[getid2]
				+ "," + selecty1[getid2] + "," + selectz1[getid2] + ") to ("
				+ selectx2[getid2] + "," + selecty2[getid2] + ","
				+ selectz2[getid2] + ") = ");

		player.sendMessage("ptdew&dewdd : dew select protect completed");
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

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewsetlight please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewsetlight please set block2");
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName()
				+ "'starting dewsetlight " + handid + ":" + handdata);

		boolean ne = false;

		for (Block blb : getselectblock(getid, player)) {

			if (blb.getTypeId() != 0) {
				continue;
			}

			/*
			 * printA(blb.getLocation().getBlockX() + "," +
			 * blb.getLocation().getBlockY()
			 * + blb.getLocation().getBlockZ() + " l = " + blb.getLightLevel());
			 */

			if (blb.getLightLevel() >= 9) {
				continue;
			}

			ne = false;
			ne = blb.getRelative(-1, 0, 0).getTypeId() != 0;
			ne = ne || blb.getRelative(1, 0, 0).getTypeId() != 0;
			ne = ne || blb.getRelative(0, 0, -1).getTypeId() != 0;
			ne = ne || blb.getRelative(0, 0, 1).getTypeId() != 0;
			ne = ne || blb.getRelative(0, -1, 0).getTypeId() != 0;

			if (ne == false) {
				continue;
			}

			if (checkpermissionarea(blb, player, "dewset") == true) {
				return;
			}

			if (api_admin.dewddadmin.is2admin(player) == false
					&& api_admin.dewddadmin.is2moderator(player) == false) {
				if (decreseitem1(player, handid, handdata, true) == false) {
					player.sendMessage("ptdew&dewdd: dont have enough item");
					return;
				}
			}

			blb.setTypeIdAndData(handid, handdata, false);
			//
		}

		printAll("ptdew&dewdd: dewsetlight done : " + player.getName());

	}

	public void dewsetprivate(Player player) throws UserDoesNotExistException,
			NoLoanPermittedException {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewsetprivate please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewsetprivate please set block2");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

		printAll("ptdew&dewdd : '" + player.getName()
				+ "'starting dewsetprivate "
				+ player.getItemInHand().getTypeId() + ":"
				+ player.getItemInHand().getData());

		for (Block blb : getselectblock(getid, player)) {

			if (blb.getTypeId() == 0) {
				continue;
			}

			if (checkpermissionarea(blb, player, "dewset") == true) {
				continue;
			}

			if (Economy.getMoney(player.getName()) < api_private.dewddprivate.buy1blockmoney) {
				player.sendMessage("ptdew&dewdd: dewsetprivate need more money...");
				return;
			}

			if (api_private.dewddprivate.checkpermissionareasign(blb) == true) {
				continue;
			}
			protectrailrun(blb, player);
			Economy.subtract(player.getName(),
					api_private.dewddprivate.buy1blockmoney);

		}

		printAll("ptdew&dewdd: dewsetprivate done : " + player.getName());
	}

	public void dewsetroom(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewsetroom_mom aer = new dewsetroom_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer, 100L);

	}

	public void dewsetwall(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewsetwall_mom aer = new dewsetwall_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public void dewspreadcircle(Player player, int handid, byte handdata) {

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

		if (bd.size() <= 0) {
			return;
		}

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
								player.sendMessage("not enough item");
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
						// printAll("ptdew&dewdd: delete near call sub (" +
						// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") "
						// + amount);

						bd.add(b2);

					}

				}

			}

		}// bll

		player.sendMessage("dewspreadcircle done");
	}

	public void dewspreadq(Player player, int handid, byte handdata) {

		Block block = player.getLocation().getBlock();
		Queue<Block> bd = new LinkedList<Block>();

		boolean ne = false;
		Block b2 = null;
		int x = 0;
		int z = 0;

		for (x = -1; x <= 1; x++) {

			for (z = -1; z <= 1; z++) {
				b2 = block.getRelative(x, 0, z);

				bd.add(b2);
			}

		}

		Block b3 = null;

		if (bd.size() <= 0) {
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
					player.sendMessage("not enough item");
					return;
				}

				b3.setTypeId(handid);
				b3.setData(handdata);

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
					// printAll("ptdew&dewdd: delete near call sub (" +
					// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") " +
					// amount);

					bd.add(b2);

				}

			}

		}// bll

		player.sendMessage("dewspreadq done");
	}

	public void dewtime(Player player) {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewtime please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd: dewtime please set block2");
			return;
		}

		printAll("ptdew&dewdd : '" + player.getName() + "'starting dewtime "
				+ player.getItemInHand().getTypeId() + ":"
				+ player.getItemInHand().getData());

		int mx = 0;
		int my = 0;
		int mz = 0;

		int lx = 0;
		int ly = 0;
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
			my = selecty1[getid];
			ly = selecty2[getid];
		}
		else {
			ly = selecty1[getid];
			my = selecty2[getid];
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

				for (int ddx = 0; ddx <= timechunkmax; ddx++) {
					if (chunk.getX() * 16 == timechunkx[ddx]
							&& chunk.getZ() * 16 == timechunkz[ddx]) {
						isad = true;
						break;
					}
				}

				if (isad == false) {
					timechunkmax++;
					timechunkx[timechunkmax] = chunk.getX() * 16;
					timechunkz[timechunkmax] = chunk.getZ() * 16;
					printAll("dewtime added new chunk = [" + timechunkmax
							+ "] > " + timechunkx[timechunkmax] + ","
							+ timechunkz[timechunkmax]);
				}

			}

		}

		printAll("ptdew&dewdd:  dewtime  done : " + player.getName());
	}

	public void dewwallcircle(Player player, int handid, byte handdata,
			boolean isfillmode) {

		dewwallcircle_mom aer = new dewwallcircle_mom(player, handid, handdata,
				isfillmode);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aer);

	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	public void fixfreeall() throws UserDoesNotExistException,
			NoLoanPermittedException {
		for (Player pd : Bukkit.getOnlinePlayers()) {

			Economy.add(pd.getName(), 500);

		}
		printC("dew fix all player");
	}

	// fixtool itemstack and player
	// getfreeselect
	public int getfreeselect(Player player) {
		int lp1 = 0;

		boolean foundza = false;
		// clear select array
		for (lp1 = 0; lp1 < selectmax; lp1++) {

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
		} // loop allselect

		// clear ofline player
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			foundza = false;
			// loop player for clear
			for (Player pla : Bukkit.getOnlinePlayers()) {
				if (pla.getName().equalsIgnoreCase(selectname[lp1]) == true) {
					foundza = true;
					break;
					// found
				}

			} // loop all player

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
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			if (player.getName().equalsIgnoreCase(selectname[lp1]) == true) {
				return lp1;
			}
		}

		// if not found
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			if (selectname[lp1].equalsIgnoreCase("null") == true) {
				selectname[lp1] = player.getName();
				return lp1;
			}
		}

		printC("ptdew&dewdd: GetFreeselect return -1");
		return -1;
	}

	// fixtool

	public String getmaterialrealname(String gname) {

		String xx = gname;

		for (Material en : Material.values()) {
			if (en.name().toLowerCase().indexOf(gname.toLowerCase()) > -1) {

				printC("found material real name = " + en.name());
				return en.name();
			}
		}

		return gname;
	}

	// getselectblock // for dewset or dewfill or dewdelete
	public Block[] getselectblock(int getid, Player player) {

		int adderB = -1;
		int countblock = ((2 + Math.abs(selectx1[getid] - selectx2[getid]))
				* (2 + Math.abs(selecty1[getid] - selecty2[getid])) * (2 + Math
				.abs(selectz1[getid] - selectz2[getid])));

		if (countblock > 2560000
				&& api_admin.dewddadmin.is2admin(player) == false) {
			player.sendMessage("only admin can dewset more than 2,560,000 block");
			Block blocktemp[] = null;

			return blocktemp;
		}

		player.sendMessage("countblock = " + countblock);

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
					 * if (isprotectitemid(blb.getTypeId()) == true) {
					 * continue;
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

	// getselectblock // for dewbuy check wa mee kee block
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
						// printAll("...");
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

	// getfreeselect

	public int getwallid() {
		int g = 0;
		Random rn = new Random();

		while (g <= 0 || isdewset(g) == false) {
			g = rn.nextInt(500);
		}

		return g;
	}

	public int getworldid(String wowo) {
		for (int d = 0; d < dewworldlistmax; d++) {
			if (wowo.equalsIgnoreCase(dewworldlist[d]) == true) {
				return d;
			}
		}

		// printAll("Error getworldid " + wowo + " so return -1");
		return -1;
	}

	public String getworldname(int idworld) {
		String aa = "ptdew_dewdd_" + dewworldlist[idworld] + ".txt";
		return aa;
	}

	public void gift(Player player, int a1, byte a2) {
		int moveyet = 0;
		boolean okok = false;

		Block b = null;
		for (int x = -311; x <= -288; x++) {
			for (int y = 189; y <= 254; y++) {
				for (int z = -338; z <= -291; z++) {
					b = player.getWorld().getBlockAt(x, y, z);
					if (b.getTypeId() == Material.CHEST.getId()
							|| b.getTypeId() == Material.TRAPPED_CHEST.getId()) {
						Chest c = (Chest) b.getState();

						for (ItemStack ic : c.getInventory().getContents()) {
							if (ic == null) {
								continue;
							}

							okok = false;
							if (ic.getTypeId() == a1) {
								if (a2 == -29) {
									okok = true;
								}
								else {
									if (a2 == ic.getData().getData()) {
										okok = true;
									}
								}

							}

							if (moveyet > 10) {
								player.sendMessage("ptdew&dewdd: you got item more than 10 times ... so.. bye bye");
								return;
							}

							if (okok == true) {
								ItemStack gj = new ItemStack(ic);
								player.getLocation().getWorld()
										.dropItem(player.getLocation(), gj);
								moveyet++;
								c.getInventory().remove(ic);
								c.update();
								player.sendMessage(moveyet + " ... "
										+ gj.getTypeId() + ":" + gj.getData()
										+ " amount = " + gj.getAmount());
							}

						}
					}

				}

			}

		}

		if (moveyet == 0) {
			player.sendMessage("not found item T_T ?");
		}

	}

	public void gotohell(Player player, Location lo1, Location lo2) {
		// copy to hell
		gotohellt ae = new gotohellt(player, lo1, lo2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ae);
	}

	public void harvestblockgoldsugarcane(Block block, Player player) {
		harvestblockgoldsugarcanec ab = new harvestblockgoldsugarcanec(block,
				player);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	// goldpumpkin
	public void harvestgoldpumpkin(Block block, Player player) {

		harvestgoldpumpkinc ab = new harvestgoldpumpkinc();
		ab.block = block;
		ab.player = player;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

	// riceblockiron
	public void harvestriceblockiron(Block block, Player player) {
		harvestriceblockironc ab = new harvestriceblockironc(block, player);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	// sandmelon
	public void harvestsandmelon(Block block, Player player) {
		harvestsandmelonc ab = new harvestsandmelonc(block, player);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	// riceblockiron

	// Check Permission Area block
	// checkidhome
	public boolean havethisnameinthishome(int worldid, int homeid, String namee) {
		if (homeid < 0 || homeid > (dewsignmax[worldid] - 1)) {
			return false;
		}
		for (int cheL = 0; cheL < dewsignnamemax; cheL++) {
			if (dewsignname[worldid][homeid][cheL].equalsIgnoreCase(namee) == true) {
				return true;
			}
		}
		return false;
	}

	public void hideshowrun(Player player) {
		// player.sendMessage("hide show run = " + player.getName());

		Player ptdewv = null;
		int gga = -1;
		for (Player pla : Bukkit.getOnlinePlayers()) {

			ptdewv = null;
			if (api_admin.dewddadmin.is2admin(pla) == true) { // if
																// admin
																// name

				ptdewv = pla;
				// printC("found fir'" + pla.getName() + "' ");
				// player.sendMessage("found fir '" + pla.getName() + "' ");

			}

			if (ptdewv != null) { // if found
				// printC("found '" + ptdewv.getName() + "' ");
				// player.sendMessage("found '" + ptdewv.getName() + "' ");

				for (Player pla2 : Bukkit.getOnlinePlayers()) {
					if (pla2.getName().equalsIgnoreCase(pla.getName()) == true) {
						continue;
					}

					// admin can see everyone
					if (api_admin.dewddadmin.is2admin(ptdewv) == true) {
						ptdewv.showPlayer(pla2); // make admin can see everyone
					}

					// ptdewv is not admin not spy
					if (api_admin.dewddadmin.is2admin(pla2) == false) {
						gga = getfreeselect(ptdewv);
						if (hidemode[gga] == true) {
							pla2.hidePlayer(ptdewv); // make member can't see
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

	// sandmelon

	// ironorefreeze

	// ironorefreeze
	public void ironorefreeze(Block block, Player player)
			throws UserDoesNotExistException, NoLoanPermittedException {
		int digX = block.getX();
		int digY = block.getY();
		int digZ = block.getZ();

		int ex = digX;
		int ey = digY;
		int ez = digZ;

		World world = block.getWorld();
		Block blockToChange = block;
		int d5 = d4[getfreeselect(player)];

		for (ex = digX - d5; ex <= digX + d5; ex++) {
			for (ey = digY - d5; ey <= digY + d5; ey++) {
				for (ez = digZ - d5; ez <= digZ + d5; ez++) {
					blockToChange = world.getBlockAt(ex, ey, ez);
					if (checkpermissionarea(blockToChange) == true) {
						continue;
					}
					if (blockToChange.getTypeId() == 8
							|| blockToChange.getTypeId() == 9) {

						world.getBlockAt(ex, ey, ez).setTypeId(0);
						blockToChange.setTypeId(9);
						world.getBlockAt(ex, ey, ez).setTypeId(9);

					}
				}
			}
		}
		player.sendMessage("IronoreFreezed Water");
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

	public boolean isunsortid(ItemStack impo) {

		if (impo.getType().getMaxDurability() > 0) {
			return true;
		}

		if (impo.getEnchantments().size() > 0) {
			return true;
		}

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

	// obsidianabsorb

	// boolean firstrun19 = false;

	public void linkurl(Player player, String url) {
		if (url.endsWith("?fb") == true || url.endsWith("?facebook") == true) {
			printA("ptdew&dewdd: my facebook > https://www.facebook.com/dewddminecraft");

		}

		if (url.endsWith("?e-mail") == true || url.endsWith("?mail") == true) {
			printA("ptdew&dewdd: my e-mail > dew_patipat@hotmail.com");
		}

		if (url.endsWith("?youtube") == true || url.endsWith("?video") == true) {
			printA("ptdew&dewdd: my youtube > http://www.youtube.com/ptdew");
			printA("ptdew&dewdd: my youtube 2 > http://www.youtube.com/ptdew2");
		}

		if (url.endsWith("?plugin") == true || url.endsWith("?pl") == true) {
			printA("ptdew&dewdd: my plugin > http://www.youtube.com/playlist?list=PLlM9Jjda8OZeMEuUtVxyXu2XF62rqzt2j");
		}

	}

	public void loadmainfile() {
		loadworldfile();
		loadsignfile();

		loaddewsetlistblockfile();
	}

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
		for (int cx = 0; cx < this.dewworldlistmax; cx++) {
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

				printC("ptdew&dewdd: Starting Loading " + filena);

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
						printC("loaded sign of world " + wlo + " sign id = "
								+ dewsignmax[wlo]);
						moden = 0;
						break;
					}

					moden++;

				}

				printC("ptdew&dewdd: loaded " + filena);

				in.close();
			}
			catch (Exception e) {// Catch exception if any
				System.err.println("LoadSignFile Error: " + e.getMessage());
			} // catch

			// ************************
		} // all world
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
				printC("world sign " + (dewworldlistmax - 1) + " = "
						+ dewworldlist[dewworldlistmax - 1]);
			}

			System.out.println("ptdew&DewDD Main: Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error load " + filena + e.getMessage());
		}
	}

	// savesignfiledefrag

	// obsidianabsorb
	public void obsidianabsorb(Block block, Player player) {
		int digX = block.getX();
		int digY = block.getY();
		int digZ = block.getZ();

		int ex = digX;
		int ey = digY;
		int ez = digZ;

		World world = block.getWorld();
		Block blockToChange = block;
		int d5 = d4[getfreeselect(player)];

		for (ex = digX - d5; ex <= digX + d5; ex++) {
			for (ey = digY - d5; ey <= digY + d5; ey++) {
				for (ez = digZ - d5; ez <= digZ + d5; ez++) {
					blockToChange = world.getBlockAt(ex, ey, ez);
					if (checkpermissionarea(blockToChange, player, "absorb") == true) {
						continue;
					}

					if (blockToChange.getTypeId() == 8
							|| blockToChange.getTypeId() == 9) {

						blockToChange.setTypeId(0);

					}

				}
			}

		}
		player.sendMessage("ptdew&dewdd:obsidian absorteb water");
	}

	public void printA(String abc) {
		for (Player pla : Bukkit.getOnlinePlayers()) {
			pla.sendMessage(abc);
		}
	}

	public void printADMIN(String abc) {
		for (Player pla : Bukkit.getOnlinePlayers()) {
			if (api_admin.dewddadmin.is2admin(pla) == true) {
				pla.sendMessage(abc);
			}
		}
	}

	public void printAll(String abc) {
		printA(abc);
		printC(abc);
	}

	public void printC(String abc) {
		System.out.println(abc);
	}

	public void protectrail(Block block, Player player) {
		boolean ok = false;

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

					printAll("runing " + b2.getX() + "," + b2.getY() + ","
							+ b2.getZ());
					protectrail(b2, player);
					printAll("runing done " + b2.getX() + "," + b2.getY() + ","
							+ b2.getZ());

				}
			}
		}
	}

	public boolean protectrailrun(Block block, Player player) {

		boolean ok = false;
		boolean spa = false;

		// searh near for space and create sign
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

						printAll("found space : " + b2.getX() + "," + b2.getY()
								+ "," + b2.getY());
						b2.setTypeId(63);
						Sign sign = (Sign) b2.getState();
						sign.setLine(0, "[dewdd]");
						sign.setLine(1, player.getName());
						sign.update(true);
						ok = true;
						printAll("created sign done at : " + b2.getX() + ","
								+ b2.getY() + "," + b2.getY());
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

		if (ok == true) {
			return true;
		}

		boolean ok2 = false;
		if (ok == false) { // loop when false
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						if (x == 0 && y == 0 && z == 0) {
							continue;
						}
						b2 = block.getRelative(x, y, z);

						ok2 = protectrailrun(b2, player);
						if (ok2 == true) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public int randomnumberint() {
		randomInt = randomG.nextInt(50);
		randomInt += 1;
		return randomInt;
	}

	// randomplaynote
	public void randomplaynote(Location player) {
		if (randomG.nextInt(100) > 10) {
			return;
		}

		for (Player pla : player.getWorld().getPlayers()) {
			pla.playSound(player, Sound.NOTE_PIANO, randomG.nextInt(50),
					randomG.nextFloat() + (float) 1);
		}
	}

	public void redstoneoredestroyworldnothread(Block block, Player player) {

		int digX = block.getX();
		int digY = block.getY();

		int digZ = block.getZ();

		int ex = digX;
		int ey = digY;
		int ez = digZ;

		World world = block.getWorld();

		Block blockCut = world.getBlockAt(ex, ey, ez);

		int d4loop = 1;

		ey = digY;
		int d5 = d4[getfreeselect(player)];

		while (d4loop <= d5 && ey > 0) {

			for (ex = digX - d4loop; ex <= digX + d4loop; ex++) {

				for (ez = digZ - d4loop; ez <= digZ + d4loop; ez++) {

					blockCut = world.getBlockAt(ex, ey, ez);

					if (checkpermissionarea(blockCut) == true
							|| blockCut.getLocation().distance(
									block.getLocation()) > d5 * 2) {
						continue;
					}

					blockCut.setTypeId(0);

				} // z

				// player.sendMessage("ptdew&dewdd:redstone delete :"+ (((ex-
				// (digX-d4)) * 100) / ((digX+d4)- (digX-d4))) + "%");
				// printC("ptdew&dewdd:redstone delete :"+ (((ex-
				// (digX-d4)) * 100) / ((digX+d4)- (digX-d4))) + "%");
			}

			ey--;
			d4loop++;
		}
	}

	public void redtorchchest(Block block, Player player) {

		// auto give item for all player on server
		redtorchchestt ee = new redtorchchestt(block, player);

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ee);

	}

	public void runter(String e, Player player, int a1, byte a2) {
		Material ik = Material.getMaterial(a1);

		if (ik == null) {
			player.sendMessage("what the hellitem " + a1 + ":" + a2);
			return;
		}

		if (ik.isBlock() == false) {
			player.sendMessage("it's not a block ? " + a1 + ":" + a2);
			return;
		}

		if (isdewset(a1) == false) {
			player.sendMessage("this item my plugin not allow for dewset " + a1
					+ ":" + a2);
			return;
		}

		if (dewddtps.tps.getTPS() < 17) {
			printAll("ptdew&dewdd: dew set system need to pause cuz tps of server below than 17 try again!"
					+ dewddtps.tps.getTPS());

			return;
		}

		if (a1 == 0) {
			if (player.hasPermission(pmaindelete) == false) {
				player.sendMessage("ptdew&dewdd:  you can't set block with 0  don't have permission for delete");
				return;
			}
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

			printC("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

			fwriter.write(dewsignmax[worldid]
					+ System.getProperty("line.separator"));

			for (int y = 0; y < dewsignmax[worldid]; y++) {
				if (exceptint > -1) {
					if (y == exceptint) {
						continue;
					}
				}

				for (int whome = 0; whome < (dewsignnamemax); whome++) {
					if (dewsignname[worldid][y][whome] != null) {
						fwriter.write(dewsignname[worldid][y][whome]
								+ System.getProperty("line.separator"));
					}
					else {
						fwriter.write("null"
								+ System.getProperty("line.separator"));
					}
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

				// printC ("ptdew&dewdd: Saved y= " + y );

			}

			fwriter.close();
			printC("ptdew&dewdd:saved " + filena);
			return;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		// printC ("ptdew&dewdd: save Done...");

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

	public void showwhohome(Block block, Player player) {
		int xyz = checkpermissionarea(block, true);
		if (xyz == -1) {
			player.sendMessage("ptdew&dewdd: This area , is not protection");
			return;
		}

		player.sendMessage("ptdew&dewdd: This area, protection by ");

		for (int xxx = 0; xxx < dewsignnamemax; xxx++) {
			if (dewsignname[getworldid(block.getWorld().getName())][xyz][xxx]
					.equalsIgnoreCase("null") == false) {
				player.sendMessage(xxx
						+ " = "
						+ dewsignname[getworldid(block.getWorld().getName())][xyz][xxx]);
			}

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

			if (block.getTypeId() != typeid || block.getData() != typedata) {
				return;
			}

			if (api_admin.dewddadmin.is2vip(player) == false) {
				return;
			}

			if (checkpermissionarea(block, player, "delete") == true) {
				return;
			}

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
						if (dleft == 0) {
							return;
						}
						superdestroy(block2, player, dleft, typeid, typedata);
					}
				}
			}

		}
	}

} // dew minecraft