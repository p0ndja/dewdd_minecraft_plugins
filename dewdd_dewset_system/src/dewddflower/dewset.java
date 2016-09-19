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
import org.bukkit.inventory.Inventory;
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

	
	class dewset_block_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private boolean isfillmode = false;
		private Player player = null;

		public dewset_block_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.isfillmode = this.isfillmode;
		}

		@Override
		public void run() {

			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : dewset(fill)block " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : dewset(fill)block " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewset(fill)block " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));
			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewset(fill)block " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			Location playerLocation = this.player.getLocation();
			dewsetblock_c aer = new dewsetblock_c(this.player, this.item, this.itemSearch, mx, my, mz, lx, ly, lz, lx,
					ly, lz, getid, playerLocation);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

		}
	}

	class dewset_c implements Runnable {
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

		public dewset_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, boolean invert,
				int mx, int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid,
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

			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewset"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewset"));

				return;
			}

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);

			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			Block blb = null;

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (this.xlx <= this.mx) {

				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewset_c xgn2 =

							new dewset_c(this.player, this.item, this.itemSearch, this.invert, this.mx, this.my,
									this.mz, this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx, this.getid,
									this.playerLocation);

							dprint.r.printC(this.player.getName() + " dewset  " + tr.gettr("recall") + " " + this.xlx
									+ " , " + this.ylx + " , " + this.zlx);
							dprint.r.printC(this.player.getName() + "low " + this.lx + " , " + this.ly + " , " + this.lz
									+ " high " + this.mx + "," + this.my + "," + this.mz + " world "
									+ this.playerLocation.getWorld().getName());

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						if (this.itemSearch.size() > 0) {
							boolean gen = IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(),
									blb.getData());
							if ((gen == false) && (this.invert == false)) {
								this.zlx++;
								continue;
							}
							if ((gen == true) && (this.invert == true)) {
								this.zlx++;
								continue;
							}
						}

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						int randid = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(randid).id;
						byte data = this.item.get(randid).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						this.zlx++;
					} // z
					this.zlx = this.lz;

					this.ylx++;
				} // y
				this.ylx = this.ly;

				this.xlx++;
			} // x
			this.xlx = this.lx;

			if (this.invert == false) {
				dprint.r.printC("ptdew&dewdd : set " + tr.gettr("done") + " : " + this.player.getName());
				this.player.sendMessage("ptdew&dewdd : set " + tr.gettr("done") + " : " + this.player.getName());

			} else {
				dprint.r.printC("ptdew&dewdd : xet " + tr.gettr("done") + " : " + this.player.getName());
				this.player.sendMessage("ptdew&dewdd : xet " + tr.gettr("done") + " : " + this.player.getName());

			}
		}
	}

	// cutwoodsub

	class dewset_copy_c implements Runnable {

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

		public dewset_copy_c(Player player, int mx, int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx,
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
			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewset"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewset"));

				return;
			}

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block hostBlock = null;
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			while (this.xlx <= this.mx) {
				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewset_copy_c xgn2 = new dewset_copy_c(this.player, this.mx, this.my, this.mz, this.lx,
									this.ly, this.lz, this.xlx, this.ylx, this.zlx, this.amountloop, this.selectx1,
									this.selecty1, this.selectz1, this.selectworldname, this.playerLocation);

							dprint.r.printC(this.player.getName() + " time out dewcopy  " + tr.gettr("recall") + " "
									+ this.xlx + " , " + this.ylx + " , " + this.zlx);
							dprint.r.printC(this.player.getName() + " recalling low " + this.lx + " , " + this.ly
									+ " , " + this.lz + " high " + this.mx + "," + this.my + "," + this.mz
									+ " amountloop " + this.amountloop);

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						hostBlock = Bukkit.getWorld(this.selectworldname).getBlockAt(this.xlx, this.ylx, this.zlx);

						if ((this.amountloop == 1) || (this.amountloop == 3)) { // if
																				// first
							// round ...
							// only
							// block
							if (hostBlock.getType().isBlock() == false) {
								this.zlx++;
								continue;
							}
						}

						if ((this.amountloop == 2) || (this.amountloop == 4)) {
							if (hostBlock.getType().isBlock() == true) {
								this.zlx++;
								continue;
							}
						}

						Block setBlock = this.playerLocation.getBlock().getRelative(hostBlock.getX() - this.selectx1,
								hostBlock.getY() - this.selecty1, hostBlock.getZ() - this.selectz1);

						if (dewset.this.cando_all(setBlock, this.player, "dewset") == false) {
							return;
						}

						if (arxx) {
							if (dewset.this.decreseitem1(this.player, hostBlock.getTypeId(), hostBlock.getData(),
									true) == false) {
								this.player.sendMessage(
										dprint.r.color("ptdew&dewdd : dewcopy " + tr.gettr("don't_have_enough_item")
												+ hostBlock.getTypeId() + ":" + hostBlock.getData()));
								return;
							}
						}

						// if (setBlock.getType() != hostBlock.getType() &&
						// setBlock.getData() != hostBlock.getData()) {
						dewset.this.saveHistory(CoreProtect, this.player, hostBlock, setBlock, false);

						// }

						if ((hostBlock.getType() == Material.SIGN_POST)
								|| (hostBlock.getType() == Material.WALL_SIGN)) {

							Sign hostSign = (Sign) hostBlock.getState();
							Sign setSign = (Sign) setBlock.getState();

							for (int i = 0; i < 4; i++) {
								setSign.setLine(i, hostSign.getLine(i));
							}

							setSign.update(true);
							dewset.this.saveHistory(CoreProtect, this.player, hostBlock, setBlock, true);
						}

						if ((this.amountloop == 3) && this.player
								.hasPermission(dewset_interface.pmainalsocopyinventoryblockwhenyouusedewset)) {

							copyInventory(hostBlock, setBlock, player, CoreProtect);
							

						}

						this.zlx++;
					}
					this.zlx = this.lz;

					this.ylx++;
				}
				this.ylx = this.ly;

				this.xlx++;
			}

			this.xlx = this.lx;

			this.amountloop++;

			if (this.amountloop <= 4) {
				dewset_copy_c xgn2 = new dewset_copy_c(this.player, this.mx, this.my, this.mz, this.lx, this.ly,
						this.lz, this.xlx, this.ylx, this.zlx, this.amountloop, this.selectx1, this.selecty1,
						this.selectz1, this.selectworldname, this.playerLocation);

				dprint.r.printC(this.player.getName() + " copy  " + tr.gettr("recall") + " " + this.xlx + " , "
						+ this.ylx + " , " + this.zlx);
				dprint.r.printC(this.player.getName() + " low " + this.lx + " , " + this.ly + " , " + this.lz + " high "
						+ this.mx + "," + this.my + "," + this.mz + " amountloop " + this.amountloop);

				Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);
				return;
			}

			dprint.r.printC("ptdew&dewdd : dewcopy " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : dewcopy " + tr.gettr("done") + " : " + this.player.getName());

		}
	}

	
	public void copyInventory(Block hostBlock , Block setBlock ,Player player , CoreProtectAPI coreProtect) {
		switch (hostBlock.getType()) {
		case CHEST:
		case TRAPPED_CHEST:

			Chest hostChest = (Chest) hostBlock.getState();
			Chest setChest = (Chest) setBlock.getState();
			
			Inventory hostInv = hostChest.getInventory();
			Inventory setInv = setChest.getInventory();

			for (int lop = 0 ; lop < hostInv.getSize(); lop ++) {
				ItemStack itm = hostInv.getItem(lop);
				if (itm == null) {
					continue;
				}

				setInv.setItem(lop,itm);

				continue;

			}

		
			setChest.update(true);
			dewset.this.saveHistory(coreProtect,player, hostBlock, setBlock, true);
			
			break;
		case DISPENSER:

			Dispenser hostDispenser = (Dispenser) hostBlock.getState();
			Dispenser setDispenser = (Dispenser) setBlock.getState();
			
			 hostInv = hostDispenser.getInventory();
			 setInv = setDispenser.getInventory();

			for (int lop = 0 ; lop < hostInv.getSize(); lop ++) {
				ItemStack itm = hostInv.getItem(lop);
				if (itm == null) {
					continue;
				}

				setInv.setItem(lop,itm);

				continue;

			}

			setDispenser.update(true);
			dewset.this.saveHistory(coreProtect, player, hostBlock, setBlock, true);
			break;

		case HOPPER:
			Hopper hostHopper = (Hopper) hostBlock.getState();
			Hopper setHopper = (Hopper) setBlock.getState();

			hostInv = hostHopper.getInventory();
			 setInv = setHopper.getInventory();

			for (int lop = 0 ; lop < hostInv.getSize(); lop ++) {
				ItemStack itm = hostInv.getItem(lop);
				if (itm == null) {
					continue;
				}

				setInv.setItem(lop,itm);

				continue;

			}

			setHopper.update(true);
			dewset.this.saveHistory(coreProtect, player, hostBlock, setBlock, true);
			break;

		case DROPPER:

			Dropper hostDropper = (Dropper) hostBlock.getState();
			Dropper setDropper = (Dropper) setBlock.getState();

			hostInv = hostDropper.getInventory();
			 setInv = setDropper.getInventory();

			for (int lop = 0 ; lop < hostInv.getSize(); lop ++) {
				ItemStack itm = hostInv.getItem(lop);
				if (itm == null) {
					continue;
				}

				setInv.setItem(lop,itm);

				continue;

			}

			setDropper.update(true);
			dewset.this.saveHistory(coreProtect, player, hostBlock, setBlock, true);
			break;
		
		default:
			dewset.this.saveHistory(coreProtect, player, hostBlock, setBlock, true);
			break;
			
		}
		
	}
	
	class dewset_copy_mom implements Runnable {
		private Player player;

		public dewset_copy_mom(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);

			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewcopy " + tr.gettr("please_set_block_1")));

				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewcopy " + tr.gettr("please_set_block_2")));

				return;

			}

			boolean xc = false;
			boolean yc = false;
			boolean zc = false;

			// x
			if (dewset.this.selectx1[getid] <= dewset.this.selectx2[getid]) {
				if ((this.player.getLocation().getBlockX() >= dewset.this.selectx1[getid])
						&& (this.player.getLocation().getBlockX() <= dewset.this.selectx2[getid])) {
					xc = true;
				}
			} else if ((this.player.getLocation().getBlockX() <= dewset.this.selectx1[getid])
					&& (this.player.getLocation().getBlockX() >= dewset.this.selectx2[getid])) {
				xc = true;
			}

			// y
			if (dewset.this.selecty1[getid] <= dewset.this.selecty2[getid]) {
				if ((this.player.getLocation().getBlockY() >= dewset.this.selecty1[getid])
						&& (this.player.getLocation().getBlockY() <= dewset.this.selecty2[getid])) {
					yc = true;
				}
			} else if ((this.player.getLocation().getBlockY() <= dewset.this.selecty1[getid])
					&& (this.player.getLocation().getBlockY() >= dewset.this.selecty2[getid])) {
				yc = true;
			}

			// z
			if (dewset.this.selectz1[getid] <= dewset.this.selectz2[getid]) {
				if ((this.player.getLocation().getBlockY() >= dewset.this.selectz1[getid])
						&& (this.player.getLocation().getBlockY() <= dewset.this.selectz2[getid])) {
					zc = true;
				}
			} else if ((this.player.getLocation().getBlockY() <= dewset.this.selectz1[getid])
					&& (this.player.getLocation().getBlockY() >= dewset.this.selectz2[getid])) {
				zc = true;
			}

			if (((xc == true) && (yc == true) && (zc == true)) == true) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : " + tr.gettr("dewcopy_can't_run_cuz_you_stand_on_source_place")));
				return;
			}

			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " dewcopy "
					+ this.player.getItemInHand().getTypeId() + ":" + this.player.getItemInHand().getData());
			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " dewcopy "
					+ this.player.getItemInHand().getTypeId() + ":" + this.player.getItemInHand().getData());
			// grab mxlx
			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			dewset_copy_c aer = new dewset_copy_c(this.player, mx, my, mz, lx, ly, lz, lx, ly, lz, 1,
					dewset.this.selectx1[getid], dewset.this.selecty1[getid], dewset.this.selectz1[getid],
					dewset.this.selectworldname[getid], this.player.getLocation().clone());

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

			// grab mxlx

		}
	}

	class dewset_down_c implements Runnable {
		private ArrayList<IDDataType> item;
		private Player player;

		public dewset_down_c(Player player, ArrayList<IDDataType> item) {
			this.player = player;
			this.item = item;

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, this, dewset.this.sleeptime);
		}

		@Override
		public void run() {
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			Block setBlock = this.player.getLocation().getBlock().getRelative(0, -2, 0);
			if (dewset.this.isprotectitemid(setBlock.getType()) == true) {
				return;
			}

			if (dewset.this.cando_all(setBlock, this.player, "dewset") == false) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewdown " + tr.gettr("this_is_not_your_zone")));
				return;
			}

			dewset.this.addItemIfItemIsZero(this.item, this.player);
			if (this.item.size() == 0) {
				return;
			}
			int handid = this.item.get(0).id;
			byte handdata = this.item.get(0).data;

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " dewdown "
					+ handid + ":" + handdata);

			if ((setBlock.getTypeId() == handid) && (setBlock.getData() == handdata)) {
				// zlx++;
				return;
			}
			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			if (handid == 0) { // if delete
				if (!perDelete) {
					this.player
							.sendMessage(dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
					return;
				}
				if (CoreProtect != null) { // Ensure we have access
											// to the API
					boolean success = CoreProtect.logRemoval(this.player.getName(), setBlock.getLocation(),
							setBlock.getType(), setBlock.getData());
				}
				setBlock.setTypeId(0);

			}

			else { // if place
				if (arxx) {
					if (dewset.this.decreseitem1(this.player, handid, handdata, true) == false) {
						this.player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
						return;
					}
				}

				setBlock.setTypeIdAndData(handid, handdata, false);

				if (CoreProtect != null) { // Ensure we have access
											// to the API
					boolean success = CoreProtect.logPlacement(this.player.getName(), setBlock.getLocation(),
							setBlock.getType(), setBlock.getData());
				}

				//

			}

			dprint.r.printC("ptdew&dewdd : dewdown " + this.player.getName() + " " + tr.gettr("complete"));
		}
	}

	class dewset_extend_c implements Runnable {
		private Player player;

		public dewset_extend_c(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, this, dewset.this.sleeptime);
		}

		@Override
		public void run() {
			this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewextend" + tr.gettr("starting")));
			int getid = dewset.this.getfreeselect(this.player);
			dewset.this.selecty1[getid] = 0;
			dewset.this.selecty2[getid] = 255;

			this.player.sendMessage(dprint.r.color(
					"ptdew&dewdd : selected area = (" + dewset.this.selectx1[getid] + "," + dewset.this.selecty1[getid]
							+ "," + dewset.this.selectz1[getid] + ") to (" + dewset.this.selectx2[getid] + ","
							+ dewset.this.selecty2[getid] + "," + dewset.this.selectz2[getid] + ") = "));

			this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewextend " + tr.gettr("complete") + "d"));
		}
	}

	class dewset_light_c implements Runnable {

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

		public dewset_light_c(Player player, ArrayList<IDDataType> item, int mx, int my, int mz, int lx, int ly, int lz,
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
			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewsetLight"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewsetLight"));

				return;
			}

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean ne = false;
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			dewset.this.addItemIfItemIsZero(this.item, this.player);

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			while (this.xlx <= this.mx) {
				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewset_light_c xgn2 = new dewset_light_c(this.player, this.item, this.mx, this.my, this.mz,
									this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx, this.getid,
									this.playerLocation);

							dprint.r.printC("dewsetl  " + tr.gettr("recall") + " " + this.xlx + " , " + this.ylx + " , "
									+ this.zlx);
							dprint.r.printC("low " + this.lx + " , " + this.ly + " , " + this.lz + " high " + this.mx
									+ "," + this.my + "," + this.mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						if (blb.getTypeId() != 0) {
							this.zlx++;
							continue;
						}

						if (blb.getLightLevel() >= 9) {
							this.zlx++;
							continue;
						}

						ne = false;
						ne = blb.getRelative(-1, 0, 0).getTypeId() != 0;
						ne = ne || (blb.getRelative(1, 0, 0).getTypeId() != 0);
						ne = ne || (blb.getRelative(0, 0, -1).getTypeId() != 0);
						ne = ne || (blb.getRelative(0, 0, 1).getTypeId() != 0);
						ne = ne || (blb.getRelative(0, -1, 0).getTypeId() != 0);

						if (ne == false) {
							this.zlx++;
							continue;
						}

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						int randid = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(randid).id;
						byte data = this.item.get(randid).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}
						this.zlx++;
					}
					this.zlx = this.lz;

					this.ylx++;
				}
				this.ylx = this.ly;

				this.xlx++;
			}
			this.xlx = this.lx;

			dprint.r.printC("ptdew&dewdd : dewsetlight " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : dewsetlight " + tr.gettr("done") + " : " + this.player.getName());

		}
	}

	class dewset_light_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private Player player = null;

		public dewset_light_mom(Player player, ArrayList<IDDataType> item) {
			this.player = player;
			this.item = item;
		}

		@Override
		public void run() {

			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewsetlight " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewsetlight " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " dewsetlight "
					+ IDDataType.arrayListToString(this.item) + " >_< ");
			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewsetlight " + IDDataType.arrayListToString(this.item) + " >_< ");

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			dewset_light_c aer = new dewset_light_c(this.player, this.item, mx, my, mz, lx, ly, lz, lx, ly, lz, getid,
					this.player.getLocation());
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

		}
	}

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

			int getid = dewset.this.getfreeselect(this.player);

			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewset " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : dewset " + tr.gettr("please_set_block_2")));
				return;
			}

			// player.sendMessage(dprint.r.color(". " + e1 + "," + e2 + "|" + e3
			// + "," + e4);

			if (this.invert == false) {
				dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " dewset "
						+ IDDataType.arrayListToString(this.item) + " >_< "
						+ IDDataType.arrayListToString(this.itemSearch));
				this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
						+ " dewset " + IDDataType.arrayListToString(this.item) + " >_< "
						+ IDDataType.arrayListToString(this.itemSearch));

			} else {
				dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " dewxet "
						+ IDDataType.arrayListToString(this.item) + " >_< "
						+ IDDataType.arrayListToString(this.itemSearch));
				this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
						+ " dewxet " + IDDataType.arrayListToString(this.item) + " >_< "
						+ IDDataType.arrayListToString(this.itemSearch));

			}

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			Location playerLocation = this.player.getLocation();

			dewset_c xgn = new dewset_c(this.player, this.item, this.itemSearch, this.invert, mx, my, mz, lx, ly, lz,
					lx, ly, lz, getid, playerLocation);

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn);

			// > > >

			// run thread

		}
	}

	// Bigdigthread

	class dewset_room_c implements Runnable {

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

		public dewset_room_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
				int my, int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid, Location playerLocation) {
			this.player = player;
			this.isfillmode = this.isfillmode;
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
			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewsetRoom"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewsetRoom"));

				return;
			}
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			dewset.this.addItemIfItemIsZero(this.item, this.player);
			Block blb = null;

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			while (this.xlx <= this.mx) {
				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						if (!((this.xlx == dewset.this.selectx1[this.getid])
								|| (this.xlx == dewset.this.selectz1[this.getid])
								|| (this.xlx == dewset.this.selectx2[this.getid])
								|| (this.xlx == dewset.this.selectz2[this.getid])
								|| (this.zlx == dewset.this.selectx1[this.getid])
								|| (this.zlx == dewset.this.selectz1[this.getid])
								|| (this.zlx == dewset.this.selectx2[this.getid])
								|| (this.zlx == dewset.this.selectz2[this.getid])
								|| (this.ylx == dewset.this.selecty1[this.getid])
								|| (this.ylx == dewset.this.selecty2[this.getid])

						)) {
							this.zlx++;
							continue;
						}

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewset_room_c xgn2 = new dewset_room_c(this.player, this.item, this.itemSearch, this.mx,
									this.my, this.mz, this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx,
									this.getid, this.playerLocation);

							dprint.r.printC("dewsetroom  " + tr.gettr("recall") + " " + this.xlx + " , " + this.ylx
									+ " , " + this.zlx);
							dprint.r.printC("low " + this.lx + " , " + this.ly + " , " + this.lz + " high " + this.mx
									+ "," + this.my + "," + this.mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						if (this.itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
								this.zlx++;
								continue;
							}
						}

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						int randid = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(randid).id;
						byte data = this.item.get(randid).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						this.zlx++;
					}
					this.zlx = this.lz;

					this.ylx++;
				}
				this.ylx = this.ly;

				this.xlx++;
			}
			this.xlx = this.lx;

			dprint.r.printC("ptdew&dewdd : dew(set)room " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : dew(set)room " + tr.gettr("done") + " : " + this.player.getName());

		}
	}

	// skyblock
	// nether
	// invert
	// old_1
	// flat
	// old_2
	// float

	class dewset_room_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player = null;

		public dewset_room_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : dewset(fill)room " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : dewset(fill)room " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewset(fill)room " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));
			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewset(fill)room " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			Location playerLocation = this.player.getLocation();
			dewset_room_c aer = new dewset_room_c(this.player, this.item, this.itemSearch, mx, my, mz, lx, ly, lz, lx,
					ly, lz, getid, playerLocation);
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

		}
	}

	class dewset_selectCube_c implements Runnable {
		private Player player;
		private int rad;

		public dewset_selectCube_c(Player player, int rad) {
			this.player = player;
			this.rad = rad;
			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, this, dewset.this.sleeptime);
		}

		@Override
		public void run() {
			this.player.sendMessage(dprint.r.color("ptdew&dewdd : dew select cube" + tr.gettr("starting")));
			int getid = dewset.this.getfreeselect(this.player);

			dewset.this.selecty1[getid] = this.player.getLocation().getBlockY();
			dewset.this.selecty2[getid] = this.player.getLocation().getBlockY();

			dewset.this.selectx1[getid] = this.player.getLocation().getBlockX();
			dewset.this.selectx2[getid] = this.player.getLocation().getBlockX();

			dewset.this.selectz1[getid] = this.player.getLocation().getBlockZ();
			dewset.this.selectz2[getid] = this.player.getLocation().getBlockZ();

			dewset.this.selecty1[getid] -= this.rad;
			dewset.this.selecty2[getid] += this.rad;
			dewset.this.selectx1[getid] -= this.rad;
			dewset.this.selectx2[getid] += this.rad;
			dewset.this.selectz1[getid] -= this.rad;
			dewset.this.selectz2[getid] += this.rad;

			this.player.sendMessage(dprint.r.color(
					"ptdew&dewdd : selected area = (" + dewset.this.selectx1[getid] + "," + dewset.this.selecty1[getid]
							+ "," + dewset.this.selectz1[getid] + ") to (" + dewset.this.selectx2[getid] + ","
							+ dewset.this.selecty2[getid] + "," + dewset.this.selectz2[getid] + ") = "));

			this.player.sendMessage(dprint.r.color("ptdew&dewdd : dew select cube " + tr.gettr("complete") + "d"));
		}
	}

	class dewset_sphere_c implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player;

		private int lx;

		private int ly;
		private int lz;
		private int mx;

		private int my;
		private int mz;
		private int xlx;
		private int ylx;
		private int zlx;

		private Location playerLocation;
		private int getid;

		public dewset_sphere_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
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

			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : setSphere " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : setSphere " + tr.gettr("please_set_block_2")));
				return;
			}

			double midx = ((double) dewset.this.selectx1[getid] + (double) dewset.this.selectx2[getid]) / 2;
			double midy = ((double) dewset.this.selecty1[getid] + (double) dewset.this.selecty2[getid]) / 2;
			double midz = ((double) dewset.this.selectz1[getid] + (double) dewset.this.selectz2[getid]) / 2;

			if (((midx == dewset.this.selectx1[getid]) && (midy == dewset.this.selecty1[getid])
					&& (midz == dewset.this.selectz1[getid]))
					|| ((midx == dewset.this.selectx2[getid]) && (midy == dewset.this.selecty2[getid])
							&& (midz == dewset.this.selectz2[getid]))) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("small_circle_can't_run_program")));
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) dewset.this.selectx1[getid] - (double) dewset.this.selectx2[getid], 2);

			temp2 = Math.pow((double) dewset.this.selecty1[getid] - (double) dewset.this.selecty2[getid], 2);

			temp3 = Math.pow((double) dewset.this.selectz1[getid] - (double) dewset.this.selectz2[getid], 2);

			double midty = (dewset.this.selecty1[getid] + dewset.this.selecty2[getid]) / 2;

			double midtx = (dewset.this.selectx1[getid] + dewset.this.selectx2[getid]) / 2;

			double midtz = (dewset.this.selectz1[getid] + dewset.this.selectz2[getid]) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;
			Block blockmid = this.player.getWorld().getBlockAt((int) midtx, (int) midty, (int) midtz);

			this.player.sendMessage(dprint.r.color("cir=" + midtx + "," + midty + "," + midtz));
			this.player.sendMessage(dprint.r.color("R=" + temp5));

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " setSphere "
					+ IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " setSphere " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			Block blb = null;
			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (this.xlx <= this.mx) {

				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewset_sphere_c xgn2 =

							new dewset_sphere_c(this.player, this.item, this.itemSearch, this.mx, this.my, this.mz,
									this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx, this.getid,
									this.playerLocation);

							dprint.r.printC(this.player.getName() + " dewset  " + tr.gettr("recall") + " " + this.xlx
									+ " , " + this.ylx + " , " + this.zlx);
							dprint.r.printC(this.player.getName() + "low " + this.lx + " , " + this.ly + " , " + this.lz
									+ " high " + this.mx + "," + this.my + "," + this.mz + " world "
									+ this.playerLocation.getWorld().getName());

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						/*
						 * if (PreciousStones.API().canPlace(player,
						 * blb.getLocation())== false) {
						 * player.sendMessage(dprint.r.color (
						 * "ptdew&dewdd :Can't setSphere here (" + blb.getX() +
						 * "," + blb.getY() + "," + blb.getZ() + ")"); continue;
						 * }
						 */

						if (blb.getLocation().distance(blockmid.getLocation()) > midr) {
							this.zlx++;
							continue;
						}

						if (this.itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
								this.zlx++;
								continue;
							}
						}

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						int ranslot = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(ranslot).id;
						byte data = this.item.get(ranslot).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}
						//
						this.zlx++;
					} // z
					this.zlx = this.lz;

					this.ylx++;
				} // y
				this.ylx = this.ly;

				this.xlx++;
			} // x
			this.xlx = this.lx;

			dprint.r.printC("ptdew&dewdd : setSphere " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : setSphere " + tr.gettr("done") + " : " + this.player.getName());

		}
	}

	class dewset_sphere_mom implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player = null;

		public dewset_sphere_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			int lx = 0;
			int mx = 0;
			int ly = 0;
			int my = 0;
			int lz = 0;
			int mz = 0;

			int getid = dewset.this.getfreeselect(this.player);

			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : setSphere " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : setSphere " + tr.gettr("please_set_block_2")));
				return;
			}

			// player.sendMessage(dprint.r.color(". " + e1 + "," + e2 + "|" + e3
			// + "," + e4);

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " setSphere "
					+ IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));
			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " setSphere " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			Location playerLocation = this.player.getLocation();

			dewset_sphere_c xgn = new dewset_sphere_c(this.player, this.item, this.itemSearch, mx, my, mz, lx, ly, lz,
					lx, ly, lz, getid, playerLocation);

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn);

			// > > >

			// run thread

		}
	}

	class dewset_spreadq_c implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player;
		private Boolean isfirst;
		private Queue<Block> bd;
		private Location playerLocation;

		public dewset_spreadq_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch,
				boolean isfirst, Queue<Block> bd, Location playerLocation) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			this.isfirst = isfirst;
			this.bd = bd;
			this.playerLocation = playerLocation;

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, this, dewset.this.sleeptime);
		}

		@Override
		public void run() {

			Block block = this.playerLocation.getBlock();
			dewset.this.addItemIfItemIsZero(this.item, this.player);

			boolean ne = false;
			Block b2 = null;
			int x = 0;
			int z = 0;

			if (this.isfirst == true) {
				this.isfirst = false;
				for (x = -1; x <= 1; x++) {
					for (z = -1; z <= 1; z++) {
						b2 = block.getRelative(x, 0, z);

						this.bd.add(b2);
					}
				}

			}

			Block blb = null;

			int ccc = 0;

			if (this.bd.size() <= 0) {
				// bd.clear();
				return;

			}

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			while (this.bd.size() > 0) { // bll
				blb = this.bd.poll();

				ne = false;

				if (this.itemSearch.size() > 0) {
					if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
						continue;
					}
				}

				if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
					continue;
				}

				int randid = dewset.this.rnd.nextInt(this.item.size());
				int id = this.item.get(randid).id;
				byte data = this.item.get(randid).data;

				// do not allow to spread Air Block ( it's will be infinite
				// loop)
				if (id == 0) {
					return;
				}

				if ((blb.getTypeId() == id) && (blb.getData() == data)) {
					// zlx++;
					continue;
				}

				if (id == 0) { // if delete
					if (!perDelete) {
						this.player.sendMessage(
								dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
						return;
					}
					if (CoreProtect != null) { // Ensure we have access
												// to the API
						boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
								blb.getType(), blb.getData());
					}
					blb.setTypeId(0);

				}

				else { // if place
						// if (arxx)
					if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
						this.player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
						return;
					}

					blb.setTypeIdAndData(id, data, false);

					if (CoreProtect != null) { // Ensure we have access
												// to the API
						boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
								blb.getType(), blb.getData());
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

						if ((x != 0) && (z != 0)) {
							continue;
						}
						b2 = blb.getRelative(x, 0, z);
						// dprint.r.printAll("ptdew&dewdd : delete near call sub
						// ("
						// +
						// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") "
						// +
						// amount);

						this.bd.add(b2);

					}
				}

				if (ccc > 10) {
					new dewset_spreadq_c(this.player, this.item, this.itemSearch, false, this.bd, this.playerLocation);
					return;
				}

			} // bll

			this.player.sendMessage(dprint.r.color("dewspreadq " + tr.gettr(tr.gettr("done"))));
		}
	}

	class dewset_spreadSphere_c implements Runnable {
		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;

		private Player player;

		public dewset_spreadSphere_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, this, dewset.this.sleeptime);
		}

		@Override
		public void run() {
			Block block = this.player.getLocation().getBlock();
			Queue<Block> bd = new LinkedList<Block>();

			dewset.this.addItemIfItemIsZero(this.item, this.player);

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

			if (bd.size() <= 0) {
				return;
			}

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			while (bd.size() > 0) { // bll
				b3 = bd.poll();

				ne = false;

				for (x = -1; x <= 1; x++) {
					for (y = -1; y <= 1; y++) {
						for (z = -1; z <= 1; z++) {
							blb = b3.getRelative(x, y, z);

							if (this.itemSearch.size() > 0) {
								if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
									continue;
								}
							}

							if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
								continue;
							}

							int randid = dewset.this.rnd.nextInt(this.item.size());
							int id = this.item.get(randid).id;
							byte data = this.item.get(randid).data;

							// do not allow to spread Air Block ( it's will be
							// infinite loop)
							if (id == 0) {
								return;
							}

							if ((blb.getTypeId() == id) && (blb.getData() == data)) {
								// zlx++;
								continue;
							}

							if (id == 0) { // if delete
								if (!perDelete) {
									this.player.sendMessage(dprint.r
											.color(tr.gettr("don't_have_permission_for_access_delete_command")));
									return;
								}
								if (CoreProtect != null) { // Ensure we have
															// access
															// to the API
									boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
											blb.getType(), blb.getData());
								}
								blb.setTypeId(0);

							}

							else { // if place
									// if (arxx)
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}

								blb.setTypeIdAndData(id, data, false);

								if (CoreProtect != null) { // Ensure we have
															// access
															// to the API
									boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
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
						if (((y + b3.getY()) < 1) || ((y + b3.getY()) > 254)) {
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

			this.player.sendMessage(dprint.r.color("spreadSphere " + tr.gettr(tr.gettr("done"))));
		}
	}

	class dewset_stack_c implements Runnable {
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

			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewset"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewset"));

				return;
			}

			CoreProtectAPI coreProtect = dewset.getCoreProtect();

			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			Block setBlock = null;
			// amountloop = start with 1
			Block hostBlock = null;

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			while (this.amountloop <= this.amount) {
				// dprint.r.printAll("amountloop = " + amountloop + " / " +
				// amount);

				// player.getWorld().save();
				int ndx = 0; // now x y z
				if (this.blockdx != 0) {
					ndx = Math.abs(dewset.this.selectx1[this.getid] - dewset.this.selectx2[this.getid]) + 1;
					ndx = ndx * this.blockdx;
					ndx = ndx * this.amountloop;
				}

				int ndy = 0; // now x y z
				if (this.blockdy != 0) {
					ndy = Math.abs(dewset.this.selecty1[this.getid] - dewset.this.selecty2[this.getid]) + 1;
					ndy = ndy * this.blockdy;
					ndy = ndy * this.amountloop;
				}

				int ndz = 0; // now x y z
				if (this.blockdz != 0) {
					ndz = Math.abs(dewset.this.selectz1[this.getid] - dewset.this.selectz2[this.getid]) + 1;
					ndz = ndz * this.blockdz;
					ndz = ndz * this.amountloop;
				}

				// player.sendMessage(dprint.r.color("blockdx = " + ndx));
				// player.sendMessage(dprint.r.color("blockdy = " + ndy));
				// player.sendMessage(dprint.r.color("blockdz = " + ndz));

				// dprint.r.printC("blockdx = " + ndx);
				// dprint.r.printC("blockdy = " + ndy);
				// dprint.r.printC("blockdz = " + ndz);

				while (this.amount1 <= 4) { // amount1 // amount1 = start with 1
					// dprint.r.printAll("amount1 = " + amount1);

					while (this.xlx <= this.mx) {

						while (this.ylx <= this.my) {
							while (this.zlx <= this.mz) {

								// dprint.r.printAll (xlx + "," + ylx + "," +
								// zlx + " mx " + mx + "," + my + "," + mz);

								endtime = System.currentTimeMillis();
								if ((endtime - starttime) > dewset.this.runtime) {

									dewset_stack_c xgn2 = new dewset_stack_c();

									xgn2.amount = this.amount;
									xgn2.blockdx = this.blockdx;
									xgn2.blockdy = this.blockdy;
									xgn2.blockdz = this.blockdz;

									xgn2.amount1 = this.amount1;

									xgn2.amountloop = this.amountloop;

									xgn2.lx = this.lx;
									xgn2.ly = this.ly;
									xgn2.lz = this.lz;

									xgn2.getid = this.getid;
									xgn2.player = this.player;

									xgn2.mx = this.mx;
									xgn2.my = this.my;
									xgn2.mz = this.mz;

									xgn2.xlx = this.xlx;
									xgn2.ylx = this.ylx;
									xgn2.zlx = this.zlx;
									xgn2.playerLocation = this.playerLocation;

									dprint.r.printC(this.player.getName() + " Stack  " + tr.gettr("recall") + " "
											+ this.xlx + " , " + this.ylx + " , " + this.zlx);
									dprint.r.printC("low " + this.lx + " , " + this.ly + " , " + this.lz + " high "
											+ this.mx + "," + this.my + "," + this.mz);

									Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2,
											dewset.this.sleeptime);

									return;
								}

								hostBlock = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

								if (((hostBlock.getY() + ndy) > 253) || ((hostBlock.getY() + ndy) < 1)) {
									this.zlx++;
									// dprint.r.printAll("out of range y");
									continue;
								}

								setBlock = hostBlock.getWorld().getBlockAt(hostBlock.getX() + ndx,
										hostBlock.getY() + ndy, hostBlock.getZ() + ndz);
								/*
								 * if (blockd.getTypeId() == 0) { continue; }
								 */

								if ((this.amount1 == 1) || (this.amount1 == 3)) { // if
																					// first
									// round ...
									// only
									// block
									if (hostBlock.getType().isBlock() == false) {
										this.zlx++;
										// dprint.r.printAll("first is not a
										// block");
										continue;
									}
									// blockd.setTypeId(0);

									if (arxx) {
										if ((setBlock.getTypeId() != hostBlock.getTypeId())
												|| (setBlock.getData() != hostBlock.getData())) {
											if ((dewset.this.decreseitem1(this.player, hostBlock.getTypeId(),
													hostBlock.getData(), false) == false)
													&& (hostBlock.getTypeId() != 0)) {
												this.player.sendMessage(dprint.r
														.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
												this.player.sendMessage(dprint.r.color("block > "
														+ hostBlock.getTypeId() + "," + hostBlock.getData()));
												return;
											}
										}
									}
								} else { // if secord round ... only not block
											// block
									if (hostBlock.getType().isBlock() == true) {
										this.zlx++;
										// dprint.r.printAll("second time is a
										// block");
										continue;
									}
									// blockd.setTypeId(0);

									if (arxx) {
										if ((setBlock.getTypeId() != hostBlock.getTypeId())
												|| (setBlock.getData() != hostBlock.getData())) {
											if ((dewset.this.decreseitem1(this.player, hostBlock.getTypeId(),
													hostBlock.getData(), false) == false)
													&& (hostBlock.getTypeId() != 0)) {
												this.player.sendMessage(dprint.r
														.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
												this.player.sendMessage(dprint.r.color("block > "
														+ hostBlock.getTypeId() + "," + hostBlock.getData()));
												return;
											}
										}
									}
								}

								setBlock = hostBlock.getWorld().getBlockAt(hostBlock.getX() + ndx,
										hostBlock.getY() + ndy, hostBlock.getZ() + ndz);
								if (dewset.this.cando_all(setBlock, this.player, "dewset") == false) {
									return;
								}

								if ((setBlock.getType() != Material.AIR) && (hostBlock.getType() == Material.AIR)) {
									if (!perDelete) {
										continue;
									}
								}

								dewset.this.saveHistory(coreProtect, this.player, hostBlock, setBlock, false);

								// dprint.r.printAll ("comple " + xlx + "," +
								// ylx + "," + zlx + " mx " + mx + "," + my +
								// "," + mz);

								if ((this.amount1 == 3) && this.player
										.hasPermission(dewset_interface.pmainalsocopyinventoryblockwhenyouusedewset)) {

									copyInventory(hostBlock, setBlock, player, coreProtect);
									

								} else {
									if ((hostBlock.getType() == Material.SIGN_POST)
											|| (hostBlock.getType() == Material.WALL_SIGN)) {

										Sign hostSign = (Sign) hostBlock.getState();
										Sign setSign = (Sign) setBlock.getState();

										for (int i = 0; i < 4; i++) {
											setSign.setLine(i, hostSign.getLine(i));
										}

										setSign.update(true);
										dewset.this.saveHistory(coreProtect, this.player, hostBlock, setBlock, true);
									}
									
									dewset.this.saveHistory(coreProtect, this.player, hostBlock, setBlock, true);
									
									
								}

								this.zlx++;
							} // z
							this.zlx = this.lz;

							this.ylx++;
						} // y
						this.ylx = this.ly;

						this.xlx++;
					} // x
					this.xlx = this.lx;

					this.amount1++;
				} // amount 1
				this.amount1 = 1;

				this.amountloop++;
			} // amount loop

			this.player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr(tr.gettr("done"))));
			dprint.r.printC("ptdew&dewdd : " + this.player.getName() + " > Stack " + tr.gettr(tr.gettr("done")));
		}
	}

	class dewset_stack_mom implements Runnable {
		public int amount;
		public Player player = null;

		@Override
		public void run() {
			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr("please_set_block_1")));

				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr("please_set_block_2")));

				return;
			}

			// find position

			if (this.amount == 0) {
				if (this.player.getItemInHand() == null) {
					this.player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("need_item_in_hand_=_amount")));

					return;
				}
				this.amount = this.player.getItemInHand().getAmount();

			}

			this.player.sendMessage(dprint.r.color("Stack amount = " + this.amount));

			Block block = this.player.getLocation().getBlock();

			if (dewset.this.selectblock[getid] == null) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : Stack " + tr.gettr("Stack_diamond_sword_null")));
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
						if ((blockd.getTypeId() == 57) || ((blockd.getLocation()
								.getBlockX() == dewset.this.selectblock[getid].getLocation().getBlockX())
								&& (blockd.getLocation().getBlockY() == dewset.this.selectblock[getid].getLocation()
										.getBlockY())
								&& (blockd.getLocation().getBlockZ() == dewset.this.selectblock[getid].getLocation()
										.getBlockZ()))) { // diamond
							// block
							blockdx = dx;
							blockdy = dy + 1;
							blockdz = dz;
							break;
						}
					}
				}
			}

			if ((blockdx == 0) && (blockdy == 0) && (blockdz == 0)) {
				this.player.sendMessage(dprint.r.color(tr.gettr("Stack_diamondsword_null_mean_upper")));

				blockdy = 1;
			}

			this.player.sendMessage(
					dprint.r.color(tr.gettr("Stack_diamond_axis_=") + blockdx + "," + blockdy + "," + blockdz));
			// after know axis and selected block ... so start copy
			// " + tr.gettr("for") + "amount
			// " + tr.gettr("for") + "all block ... to copy to next axis
			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " Stack "
					+ this.player.getItemInHand().getTypeId() + ":" + this.player.getItemInHand().getData());

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " Stack "
					+ this.player.getItemInHand().getTypeId() + ":" + this.player.getItemInHand().getData());
			// run
			int mx = 0;
			int lx = 0;

			int my = 0;
			int ly = 0;

			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			dewset_stack_c aer = new dewset_stack_c();
			aer.player = this.player;

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
			aer.playerLocation = this.player.getLocation();

			aer.amountloop = 1;
			aer.amount = this.amount;

			aer.getid = getid;
			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

		}

	}

	class dewset_wall_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private Player player = null;

		public dewset_wall_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : dewset(fill)wall " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(
						dprint.r.color("ptdew&dewdd : dewset(fill)wall " + tr.gettr("please_set_block_2")));
				return;
			}

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewset(fill)wall " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));
			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " dewset(fill)wall " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			dewsetwall_c aer = new dewsetwall_c(this.player, this.item, this.itemSearch, mx, my, mz, lx, ly, lz, lx, ly,
					lz, getid, this.player.getLocation());
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

		}
	}

	class dewset_wallSphere_c implements Runnable {

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

		public dewset_wallSphere_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
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
			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel  wallSphere"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel  wallSphere"));

				return;
			}

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;

			dewset.this.addItemIfItemIsZero(this.item, this.player);
			Block blockmid = this.player.getWorld().getBlockAt((int) this.midtx, (int) this.midty, (int) this.midtz);

			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			while (this.xlx <= this.mx) {
				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewset_wallSphere_c xgn2 = new dewset_wallSphere_c(this.player, this.item, this.itemSearch,
									this.mx, this.my, this.mz, this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx,
									this.getid, this.midr, this.midtx, this.midty, this.midtz, this.playerLocation);

							dprint.r.printC("wallSphere  " + tr.gettr("recall") + " " + this.xlx + " , " + this.ylx
									+ " , " + this.zlx);
							dprint.r.printC("low " + this.lx + " , " + this.ly + " , " + this.lz + " high " + this.mx
									+ "," + this.my + "," + this.mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						if ((int) blb.getLocation().distance(blockmid.getLocation()) != (int) this.midr) {
							this.zlx++;
							continue;
						}

						if (this.itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
								this.zlx++;
								continue;
							}
						}

						// wallc

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						int randid = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(randid).id;
						byte data = this.item.get(randid).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						this.zlx++;
					}
					this.zlx = this.lz;

					this.ylx++;
				}
				this.ylx = this.ly;

				this.xlx++;
			}
			this.xlx = this.lx;

			dprint.r.printC("ptdew&dewdd : wallSphere " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : wallSphere " + tr.gettr("done") + " : " + this.player.getName());

		}
	}

	class dewset_wallSphere_mom implements Runnable {

		private ArrayList<IDDataType> item;
		private ArrayList<IDDataType> itemSearch;
		private boolean isfillmode = false;
		private Player player = null;

		public dewset_wallSphere_mom(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
			this.player = player;
			this.item = item;
			this.itemSearch = itemSearch;
		}

		@Override
		public void run() {

			// ..........
			int getid = dewset.this.getfreeselect(this.player);
			if ((dewset.this.selectx1[getid] == 0) && (dewset.this.selecty1[getid] == 0)
					&& (dewset.this.selectz1[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : wallSphere " + tr.gettr("please_set_block_1")));
				return;
			}
			if ((dewset.this.selectx2[getid] == 0) && (dewset.this.selecty2[getid] == 0)
					&& (dewset.this.selectz2[getid] == 0)) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : wallSphere " + tr.gettr("please_set_block_2")));
				return;
			}

			double midx = ((double) dewset.this.selectx1[getid] + (double) dewset.this.selectx2[getid]) / 2;
			double midy = ((double) dewset.this.selecty1[getid] + (double) dewset.this.selecty2[getid]) / 2;
			double midz = ((double) dewset.this.selectz1[getid] + (double) dewset.this.selectz2[getid]) / 2;

			if (((midx == dewset.this.selectx1[getid]) && (midy == dewset.this.selecty1[getid])
					&& (midz == dewset.this.selectz1[getid]))
					|| ((midx == dewset.this.selectx2[getid]) && (midy == dewset.this.selecty2[getid])
							&& (midz == dewset.this.selectz2[getid]))) {
				this.player.sendMessage(dprint.r.color("ptdew&dewdd : small circle can't run program"));
				return;
			}

			double temp1 = 0;

			double temp5 = 0;
			double temp2 = 0;
			double temp3 = 0;
			temp1 = Math.pow((double) dewset.this.selectx1[getid] - (double) dewset.this.selectx2[getid], 2);

			temp2 = Math.pow((double) dewset.this.selecty1[getid] - (double) dewset.this.selecty2[getid], 2);

			temp3 = Math.pow((double) dewset.this.selectz1[getid] - (double) dewset.this.selectz2[getid], 2);

			double midty = (dewset.this.selecty1[getid] + dewset.this.selecty2[getid]) / 2;

			double midtx = (dewset.this.selectx1[getid] + dewset.this.selectx2[getid]) / 2;

			double midtz = (dewset.this.selectz1[getid] + dewset.this.selectz2[getid]) / 2;
			temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

			double midr = temp5 / 3;

			this.player.sendMessage(dprint.r.color("cir=" + midtx + "," + midty + "," + midtz));
			this.player.sendMessage(dprint.r.color("R=" + temp5));

			dprint.r.printC("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting") + " wallSphere "
					+ IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			this.player.sendMessage("ptdew&dewdd : '" + this.player.getName() + "'" + tr.gettr("starting")
					+ " wallSphere " + IDDataType.arrayListToString(this.item) + " >_< "
					+ IDDataType.arrayListToString(this.itemSearch));

			// ........

			int mx = 0;
			int lx = 0;
			int my = 0;
			int ly = 0;
			int mz = 0;
			int lz = 0;

			if (dewset.this.selectx1[getid] >= dewset.this.selectx2[getid]) {
				mx = dewset.this.selectx1[getid];
				lx = dewset.this.selectx2[getid];
			} else {
				mx = dewset.this.selectx2[getid];
				lx = dewset.this.selectx1[getid];
			}

			if (dewset.this.selecty1[getid] >= dewset.this.selecty2[getid]) {
				my = dewset.this.selecty1[getid];
				ly = dewset.this.selecty2[getid];
			} else {
				my = dewset.this.selecty2[getid];
				ly = dewset.this.selecty1[getid];
			}

			if (dewset.this.selectz1[getid] >= dewset.this.selectz2[getid]) {
				mz = dewset.this.selectz1[getid];
				lz = dewset.this.selectz2[getid];
			} else {
				mz = dewset.this.selectz2[getid];
				lz = dewset.this.selectz1[getid];
			}

			dewset_wallSphere_c aer = new dewset_wallSphere_c(this.player, this.item, this.itemSearch, mx, my, mz, lx,
					ly, lz, lx, ly, lz, getid, midr, midtx, midty, midtz, this.player.getLocation());
			// Player player, int handid , byte handdata , int mx , int my , int
			// mz
			// , int lx , int ly , int lz , int xlx , int ylx , int zlx , int
			// getid , boolean isfillmode

			Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer, dewset.this.sleeptime);

		}
	}

	class dewsetblock_c implements Runnable {

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

		public dewsetblock_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx,
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
			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewsetblock"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewsetblock"));

				return;
			}
			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			long endtime = 0;
			long starttime = System.currentTimeMillis();
			Block blb = null;
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			boolean t1 = false;
			boolean t2 = false;

			dewset.this.addItemIfItemIsZero(this.item, this.player);

			while (this.xlx <= this.mx) {
				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {
						t1 = (this.ylx == dewset.this.selecty1[this.getid])
								|| (this.ylx == dewset.this.selecty2[this.getid]);

						t2 = (this.xlx == dewset.this.selectx1[this.getid])
								|| (this.zlx == dewset.this.selectz1[this.getid])
								|| (this.xlx == dewset.this.selectx2[this.getid])
								|| (this.zlx == dewset.this.selectz2[this.getid]);

						if (!((t1 && t2)
								|| (!t1 && (this.xlx == dewset.this.selectx1[this.getid])
										&& (this.zlx == dewset.this.selectz1[this.getid]))
								|| ((this.xlx == dewset.this.selectx2[this.getid])
										&& (this.zlx == dewset.this.selectz2[this.getid]))
								|| ((this.xlx == dewset.this.selectx1[this.getid])
										&& (this.zlx == dewset.this.selectz2[this.getid]))
								|| ((this.xlx == dewset.this.selectx2[this.getid])
										&& (this.zlx == dewset.this.selectz1[this.getid]))

						)

						) {
							this.zlx++;
							continue;
						}
						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewsetblock_c xgn2 = new dewsetblock_c(this.player, this.item, this.itemSearch, this.mx,
									this.my, this.mz, this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx,
									this.getid, this.playerLocation);

							dprint.r.printC(this.player.getName() + " dewsetblock  " + tr.gettr("recall") + " "
									+ this.xlx + " , " + this.ylx + " , " + this.zlx);
							dprint.r.printC(this.player.getName() + " low " + this.lx + " , " + this.ly + " , "
									+ this.lz + " high " + this.mx + "," + this.my + "," + this.mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						/*
						 * if (isfillmode == true) if (blb.getTypeId() != 0) {
						 * zlx++; continue; }
						 */
						if (this.itemSearch.size() != 0) {
							if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
								this.zlx++;
								continue;
							}

						}

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						// coner

						int randid = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(randid).id;
						byte data = this.item.get(randid).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						this.zlx++;
					}
					this.zlx = this.lz;

					this.ylx++;
				}
				this.ylx = this.ly;

				this.xlx++;
			}
			this.xlx = this.lx;

			dprint.r.printC("ptdew&dewdd : dew(set)block " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : dew(set)block " + tr.gettr("done") + " : " + this.player.getName());
		}
	}

	class dewsetwall_c implements Runnable {

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

		public dewsetwall_c(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, int mx, int my,
				int mz, int lx, int ly, int lz, int xlx, int ylx, int zlx, int getid, Location playerLocation) {
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
			if (this.player.getItemInHand().getType() == Material.AIR) {
				dprint.r.printC(this.player.getName() + tr.gettr("has cancel dewsetWall"));
				this.player.sendMessage(this.player.getName() + tr.gettr("has cancel dewsetWall"));

				return;
			}

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			Block blb = null;
			boolean arxx = !this.player.hasPermission(dewset_interface.pmaininfinite);
			boolean perDelete = this.player.hasPermission(dewset_interface.pmaindelete);

			dewset.this.addItemIfItemIsZero(this.item, this.player);

			CoreProtectAPI CoreProtect = dewset.getCoreProtect();

			while (this.xlx <= this.mx) {
				while (this.ylx <= this.my) {
					while (this.zlx <= this.mz) {

						if (!((this.xlx == dewset.this.selectx1[this.getid])
								|| (this.zlx == dewset.this.selectz1[this.getid])
								|| (this.xlx == dewset.this.selectx2[this.getid])
								|| (this.zlx == dewset.this.selectz2[this.getid])
								|| (this.xlx == dewset.this.selectx1[this.getid])
								|| (this.zlx == dewset.this.selectz1[this.getid])
								|| (this.xlx == dewset.this.selectx2[this.getid])
								|| (this.zlx == dewset.this.selectz2[this.getid]))) {
							this.zlx++;
							continue;
						}

						endtime = System.currentTimeMillis();
						if ((endtime - starttime) > dewset.this.runtime) {

							dewsetwall_c xgn2 = new dewsetwall_c(this.player, this.item, this.itemSearch, this.mx,
									this.my, this.mz, this.lx, this.ly, this.lz, this.xlx, this.ylx, this.zlx,
									this.getid, this.playerLocation);

							dprint.r.printC("dewsetwall  " + tr.gettr("recall") + " " + this.xlx + " , " + this.ylx
									+ " , " + this.zlx);
							dprint.r.printC("low " + this.lx + " , " + this.ly + " , " + this.lz + " high " + this.mx
									+ "," + this.my + "," + this.mz);

							Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, xgn2, dewset.this.sleeptime);

							return;
						}

						blb = this.playerLocation.getWorld().getBlockAt(this.xlx, this.ylx, this.zlx);

						if (this.itemSearch.size() > 0) {
							if (!IDDataType.isThisItemOnTheList(this.itemSearch, blb.getTypeId(), blb.getData())) {
								this.zlx++;
								continue;
							}
						}

						if (dewset.this.cando_all(blb, this.player, "dewset") == false) {
							return;
						}

						int randid = dewset.this.rnd.nextInt(this.item.size());
						int id = this.item.get(randid).id;
						byte data = this.item.get(randid).data;

						if ((blb.getTypeId() == id) && (blb.getData() == data)) {
							this.zlx++;
							continue;
						}

						if (id == 0) { // if delete
							if (!perDelete) {
								this.player.sendMessage(
										dprint.r.color(tr.gettr("don't_have_permission_for_access_delete_command")));
								return;
							}
							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logRemoval(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}
							blb.setTypeId(0);

						}

						else { // if place
							if (arxx) {
								if (dewset.this.decreseitem1(this.player, id, data, true) == false) {
									this.player.sendMessage(
											dprint.r.color("ptdew&dewdd : " + tr.gettr("don't_have_enough_item")));
									return;
								}
							}

							blb.setTypeIdAndData(id, data, false);

							if (CoreProtect != null) { // Ensure we have access
														// to the API
								boolean success = CoreProtect.logPlacement(this.player.getName(), blb.getLocation(),
										blb.getType(), blb.getData());
							}

							//

						}

						this.zlx++;
					}
					this.zlx = this.lz;

					this.ylx++;
				}
				this.ylx = this.ly;

				this.xlx++;
			}
			this.xlx = this.lx;

			dprint.r.printC("ptdew&dewdd : dew(set)fill " + tr.gettr("done") + " : " + this.player.getName());
			this.player.sendMessage("ptdew&dewdd : dew(set)fill " + tr.gettr("done") + " : " + this.player.getName());

		}
	}

	class randomplaynote_c implements Runnable {
		private Location player;

		public randomplaynote_c(Location player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (dewset.this.randomG.nextInt(100) > 10) {
				return;
			}

			for (Player pla : this.player.getWorld().getPlayers()) {
				pla.playSound(this.player, Sound.BLOCK_NOTE_HARP, dewset.this.randomG.nextInt(50),
						dewset.this.randomG.nextFloat() + 1);
			}
		}
	}

	public static JavaPlugin ac = null;

	public static CoreProtectAPI getCoreProtect() {
		Plugin plugin = dewset.ac.getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if ((plugin == null) || !(plugin instanceof CoreProtect)) {
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

	// redim" + tr.gettr("for") + "each world each protect = 100

	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = dewset.ac.getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if ((plugin == null) || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

	// Chat Event.class
	// BlockBreakEvent
	public static boolean isrunworld(String worldName) {
		return tr.isrunworld(dewset.ac.getName(), worldName);
	}
	// randomplaynote

	// decrese item 1

	// Check Permission Area block player mode

	Random rnd = new Random();

	public int amountRecursiveCount = 0; // recursive
	// 55
	// 55

	public int d4[] = new int[dewset_interface.selectmax + 1];

	public Block giftblock = null;

	public Random randomG = new Random();

	// cut seem block

	public int runtime = (int) tr.gettrint("dewset runtime as milisecond");

	public long sleeptime = (int) tr.gettrint("dewset sleeptime as tick");

	public Block selectblock[] = new Block[dewset_interface.selectmax + 1];

	public String selectname[] = new String[dewset_interface.selectmax + 1];

	public String selectworldname[] = new String[dewset_interface.selectmax + 1];

	public int selectx1[] = new int[dewset_interface.selectmax + 1];

	// Check Permission Area block

	public int selectx2[] = new int[dewset_interface.selectmax + 1];

	public int selecty1[] = new int[dewset_interface.selectmax + 1];

	public int selecty2[] = new int[dewset_interface.selectmax + 1];

	public int selectz1[] = new int[dewset_interface.selectmax + 1];

	public int selectz2[] = new int[dewset_interface.selectmax + 1];

	public  WorldGuardPlugin wgapi = null;

	public  boolean CONFIG_NEED_PROTECT = false;

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
		if (bbc.getTypeId() != 0) {
			return false;
		}

		if ((this.canaddtorchatblock(bbc.getRelative(0, -1, 0)) == true)
				|| (this.canaddtorchatblock(bbc.getRelative(1, 0, 0)) == true)
				|| (this.canaddtorchatblock(bbc.getRelative(-1, 0, 0)) == true)
				|| (this.canaddtorchatblock(bbc.getRelative(0, 0, 1)) == true)
				|| (this.canaddtorchatblock(bbc.getRelative(0, 0, -1)) == true)) {
			return true;
		}

		return false;
	}

	// canaddtorchatblock
	public boolean canaddtorchatblock(Block bbc) {
		if ((bbc.getTypeId() != 0) && (bbc.isLiquid() == false)) {
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

	public  boolean cando_all(Block block, Player player, String modeevent) {

		boolean wg = false;
		boolean wgHasProtect = false;

		if (wgapi == null) {
			wgapi = dewset.getWorldGuard();
		}

	
		
		
		if (wgapi != null) {
			if (CONFIG_NEED_PROTECT == true) {

				ApplicableRegionSet set = wgapi.getRegionManager(block.getWorld())
						.getApplicableRegions(block.getLocation());
				// player.sendMessage("rg size " + set.getRegions().size());
				if (set.getRegions().size() >= 1) {
					wgHasProtect = true;
				}

			}
			wg = wgapi.canBuild(player, block);

		}
		
		//*****************************************

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
		
		
		//....................
		
		boolean cre = false;
		boolean creHasProtect = false;
		if (Bukkit.getPluginManager().getPlugin("dewddcreative") == null) {
			cre = true;
		} else {
			cre = dewddcreative.api_creative.cando(block.getX()	, block.getY(), block.getZ(), player) ;// .cando(block, player, modeevent);
			if (CONFIG_NEED_PROTECT == true) {
				creHasProtect = dewddcreative.api_creative.isProtectedArea(block);

			}
		}
		
		//............................
		
		boolean pri = false;
		boolean priHasProtect = false;
		if (Bukkit.getPluginManager().getPlugin("dewddprivate") == null) {
			pri= true;
		} else {
			pri = api_private.DewddPrivate.cando( block, player) ;// .cando(block, player, modeevent);
			if (CONFIG_NEED_PROTECT == true) {
				priHasProtect = api_private.DewddPrivate.hasProtect(block);

			}
		}
		
		
		
		
		//........................

		boolean candoYap = wg && sky && cre && pri;
		boolean hasProtectYap = wgHasProtect || skyHasProtect || creHasProtect || priHasProtect ;
		boolean output = false;

		if (CONFIG_NEED_PROTECT == true) {
			if (hasProtectYap == true) {
				output = candoYap;
			} else {
				output = false;
			}
		} else {
			output = candoYap;
		}

		return output;
	}

	public boolean checkbetweenblock(int digx, int digy, int digz, int x1, int y1, int z1, int x2, int y2, int z2) {

		boolean goodc1 = false;

		goodc1 = false;

		// x 2 type
		// x1 <= x2
		if (x1 <= x2) {
			if ((digx > (x1 - 1)) && (digx < (x2 + 1))) {
				if (y1 <= y2) {
					if ((digy > (y1 - 1)) && (digy < (y2 + 1))) {
						// y true
						if (z1 <= z2) {
							if ((digz > (z1 - 1)) && (digz < (z2 + 1))) {
								// z true
								goodc1 = true;
							}
						} else if ((digz < (z1 + 1)) && (digz > (z2 - 1))) {
							// z true
							goodc1 = true;
						}
					}
				} else if ((digy < (y1 + 1)) && (digy > (y2 - 1))) {
					// y true
					if (z1 <= z2) {
						if ((digz > (z1 - 1)) && (digz < (z2 + 1))) {
							// z true
							goodc1 = true;
						}
					} else if ((digz < (z1 + 1)) && (digz > (z2 - 1))) {
						// z true
						goodc1 = true;
					}
				}
			}
		} else if ((digx < (x1 + 1)) && (digx > (x2 - 1))) {
			if (y1 <= y2) {
				if ((digy > (y1 - 1)) && (digy < (y2 + 1))) {
					// y true
					if (z1 <= z2) {
						if ((digz > (z1 - 1)) && (digz < (z2 + 1))) {
							// z true
							goodc1 = true;
						}
					} else if ((digz < (z1 + 1)) && (digz > (z2 - 1))) {
						// z true
						goodc1 = true;
					}
				}
			} else if ((digy < (y1 + 1)) && (digy > (y2 - 1))) {
				// y true
				if (z1 <= z2) {
					if ((digz > (z1 - 1)) && (digz < (z2 + 1))) {
						// z true
						goodc1 = true;
					}
				} else if ((digz < (z1 + 1)) && (digz > (z2 - 1))) {
					// z true
					goodc1 = true;
				}
			}
		}

		return goodc1;

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

	public boolean decreseitem1(Player player, int itemid, int itemdata, boolean forcetruedata) {
		ItemStack itm = null;
		int lenl = 0;

		if (itemid == 0) {
			return false;
		}

		for (lenl = 0; lenl < player.getInventory().getContents().length; lenl++) {
			if (player.getInventory().getItem(lenl) == null) {
				continue;
			}

			itm = player.getInventory().getItem(lenl);

			if ((itemid == 8) || (itemid == 9) || (itemid == 326)) {
				return true;
			} else if ((itemid == 44) || (itemid == 43)) {
				if ((itm.getTypeId() != 44) && (itm.getTypeId() != 43)) {
					continue;
				}
			}

			// piston
			else if ((itemid == 33) || (itemid == 34) || (itemid == 29)) {
				if ((itm.getTypeId() != 33) && (itm.getTypeId() != 34) && (itm.getTypeId() != 29)) {
					continue;
				}
			}

			// dirt
			else if ((itemid == 2) || (itemid == 3) || (itemid == 60)) {
				if ((itm.getTypeId() != 3) && (itm.getTypeId() != 2) && (itm.getTypeId() != 60)) {
					continue;
				}
			}
			// repeater
			else if ((itemid == 356) || (itemid == 93) || (itemid == 94)) {
				if ((itm.getTypeId() != 356) && (itm.getTypeId() != 93) && (itm.getTypeId() != 94)) {
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

			// sugar
			else if ((itemid == 338) || (itemid == 83)) {
				if ((itm.getTypeId() != 338) && (itm.getTypeId() != 83)) {
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
			} else if (itm.getTypeId() != itemid) {
				continue;
			}

			if (forcetruedata == true) {
				if (itm.getData().getData() != itemdata) {
					continue;
				}
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

	// decrese item 1

	public void dewset(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch, boolean invert) {
		dewset_mom aer = new dewset_mom(player, item, itemSearch, invert);

		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);
	}

	public void dewset_block(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewset_block_mom aer = new dewset_block_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);

	}

	public void dewset_copy(Player player) {
		dewset_copy_mom abr = new dewset_copy_mom(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, abr);
	}

	// fixtool

	public void dewset_down(Player player, ArrayList<IDDataType> item) {

		new dewset_down_c(player, item);
	}

	// getfreeselect

	public void dewset_extend(Player player) {
		new dewset_extend_c(player);
	}

	public void dewset_light(Player player, ArrayList<IDDataType> item) {

		dewset_light_mom aer = new dewset_light_mom(player, item);
		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);

	}

	public void dewset_room(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewset_room_mom aer = new dewset_room_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);

	}

	// riceblockiron

	public void dewset_selectCube(Player player, int rad) {
		new dewset_selectCube_c(player, rad);
	}

	public void dewset_sphere(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
		dewset_sphere_mom abx = new dewset_sphere_mom(player, item, itemSearch);
		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, abx);

	}

	public void dewset_spreadq(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {
		Queue<Block> bd = new LinkedList<Block>();

		dprint.r.printC("ptdew&dewdd: " + player.getName() + " starting dsq " + IDDataType.arrayListToString(item)
				+ " >_< " + IDDataType.arrayListToString(itemSearch));
		player.sendMessage("ptdew&dewdd: " + player.getName() + " starting dsq " + IDDataType.arrayListToString(item)
				+ " >_< " + IDDataType.arrayListToString(itemSearch));

		new dewset_spreadq_c(player, item, itemSearch, true, bd, player.getLocation());
	}

	public void dewset_spreadSphere(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		new dewset_spreadSphere_c(player, item, itemSearch);
	}

	public void dewset_stack(Player player, int amount) {

		dewset_stack_mom r = new dewset_stack_mom();
		r.amount = amount;
		r.player = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, r);

	} // dew a

	// sandmelon

	// ironorefreeze

	public void dewset_wall(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewset_wall_mom aer = new dewset_wall_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);

	}

	public void dewset_wallSphere(Player player, ArrayList<IDDataType> item, ArrayList<IDDataType> itemSearch) {

		dewset_wallSphere_mom aer = new dewset_wallSphere_mom(player, item, itemSearch);

		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);

	}

	public void dewsetLightAround(Player player, ArrayList<IDDataType> item) {
		this.dewset_selectCube(player, 3);

		dewset_light_mom aer = new dewset_light_mom(player, item);
		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, aer);

	}

	// obsidianabsorb

	// boolean firstrun19 = false;

	// Check Permission Area block
	// checkidhome

	public static int distance2d(int x1, int z1, int x2, int z2) {
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
		for (lp1 = 0; lp1 < dewset_interface.selectmax; lp1++) {
			if ((this.selectname[lp1] == null) || (this.selectname[lp1].equalsIgnoreCase("") == true)) {
				this.selectname[lp1] = "null";
				this.selectx1[lp1] = 0;
				this.selecty1[lp1] = 0;
				this.selectz1[lp1] = 0;
				this.selectx2[lp1] = 0;
				this.selecty2[lp1] = 0;
				this.selectz2[lp1] = 0;

				this.d4[lp1] = 1;
				this.selectblock[lp1] = null;
			}
		}

		// clear ofline player
		for (lp1 = 0; lp1 < dewset_interface.selectmax; lp1++) {
			foundza = false;
			// loop player" + tr.gettr("for") + "clear
			for (Player pla : Bukkit.getOnlinePlayers()) {
				if (pla.getName().equalsIgnoreCase(this.selectname[lp1]) == true) {
					foundza = true;
					break;
					// found
				}
			}

			if (foundza == false) { // clear
				this.selectname[lp1] = "null";
				this.selectx1[lp1] = 0;
				this.selecty1[lp1] = 0;
				this.selectz1[lp1] = 0;
				this.selectx2[lp1] = 0;
				this.selecty2[lp1] = 0;
				this.selectz2[lp1] = 0;

				this.d4[lp1] = 1;
				this.selectblock[lp1] = null;
			}

		}

		// if fonund return
		for (lp1 = 0; lp1 < dewset_interface.selectmax; lp1++) {
			if (player.getName().equalsIgnoreCase(this.selectname[lp1]) == true) {
				return lp1;
			}
		}

		// if not found
		for (lp1 = 0; lp1 < dewset_interface.selectmax; lp1++) {
			if (this.selectname[lp1].equalsIgnoreCase("null") == true) {
				this.selectname[lp1] = player.getName();
				return lp1;
			}
		}

		dprint.r.printC("ptdew&dewdd : " + tr.gettr("error_getfreeselect_return_-1"));
		return -1;
	}

	public LinkedList<String> getmaterialrealname(String gname) {
		LinkedList<String> ab = new LinkedList<String>();

		for (Material en : Material.values()) {
			if (en.name().toLowerCase().indexOf(gname.toLowerCase()) > -1) {

				dprint.r.printC("found material real name = " + en.name());
				ab.add(en.name());
			}
		}

		return ab;
	}

	// getselectblock //" + tr.gettr("for") + "dewset or dewfill or dewdelete
	/*
	 * public Block[] getselectblock(int getid, Player player) {
	 * 
	 * int adderB = -1; int countblock = (2 + Math.abs(selectx1[getid] -
	 * selectx2[getid])) (2 + Math.abs(selecty1[getid] - selecty2[getid])) * (2
	 * + Math.abs(selectz1[getid] - selectz2[getid]));
	 * 
	 * player.sendMessage(dprint.r.color("countblock = " + countblock));
	 * 
	 * int lx = 0; int mx = 0; int ly = 0; int my = 0; int lz = 0; int mz = 0;
	 * 
	 * if (selectx1[getid] >= selectx2[getid]) { mx = selectx1[getid]; lx =
	 * selectx2[getid]; } else { mx = selectx2[getid]; lx = selectx1[getid]; }
	 * 
	 * if (selecty1[getid] >= selecty2[getid]) { my = selecty1[getid]; ly =
	 * selecty2[getid]; } else { my = selecty2[getid]; ly = selecty1[getid]; }
	 * 
	 * if (selectz1[getid] >= selectz2[getid]) { mz = selectz1[getid]; lz =
	 * selectz2[getid]; } else { mz = selectz2[getid]; lz = selectz1[getid]; }
	 * 
	 * Block blocktemp[] = new Block[countblock]; // > > >
	 * 
	 * for (int xl = lx; xl <= mx; xl++) { for (int yl = ly; yl <= my; yl++) {
	 * for (int zl = lz; zl <= mz; zl++) { Block blb =
	 * player.getWorld().getBlockAt(xl, yl, zl); if (blb == null) { continue; }
	 * 
	 * 
	 * if (isprotectitemid(blb.getTypeId()) == true) { continue; }
	 * 
	 * 
	 * adderB++; blocktemp[adderB] = blb; } } }
	 * 
	 * Block blockmain[] = new Block[adderB + 1]; int adderc = 0;
	 * 
	 * for (adderc = 0; adderc <= adderB; adderc++) { blockmain[adderc] =
	 * blocktemp[adderc]; }
	 * 
	 * for (@SuppressWarnings("unused") Object obj : blocktemp) { obj = null; }
	 * blocktemp = null;
	 * 
	 * return blockmain;
	 * 
	 * } // getselectblock
	 */
	// getselectblock //" + tr.gettr("for") + "dewbuy check wa mee kee block
	public int getselectblockforbuy(int getid, Player player) {

		int countall = 0;

		int lx = 0;
		int mx = 0;
		int ly = 0;
		int my = 0;
		int lz = 0;
		int mz = 0;

		if (this.selectx1[getid] >= this.selectx2[getid]) {
			mx = this.selectx1[getid];
			lx = this.selectx2[getid];
		} else {
			mx = this.selectx2[getid];
			lx = this.selectx1[getid];
		}

		if (this.selecty1[getid] >= this.selecty2[getid]) {
			my = this.selecty1[getid];
			ly = this.selecty2[getid];
		} else {
			my = this.selecty2[getid];
			ly = this.selecty1[getid];
		}

		if (this.selectz1[getid] >= this.selectz2[getid]) {
			mz = this.selectz1[getid];
			lz = this.selectz2[getid];
		} else {
			mz = this.selectz2[getid];
			lz = this.selectz1[getid];
		}

		for (int xl = lx; xl <= mx; xl++) {
			for (int yl = ly; yl <= my; yl++) {
				for (int zl = lz; zl <= mz; zl++) {
					Block blb = player.getWorld().getBlockAt(xl, yl, zl);
					if (this.cando_all(blb, player, "buy") == false) {
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

	public boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
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

	// savesignfile


	public Block protochest(Block block, int d4, String sorttype) {
		Block temp = null;
		Sign sign2 = null;
		Block typebox = null;

		// search sign

		for (int gx2 = 0 - d4; gx2 <= (0 + d4); gx2++) {
			for (int gy2 = 0 - d4; gy2 <= (0 + d4); gy2++) {
				for (int gz2 = 0 - d4; gz2 <= (0 + d4); gz2++) {
					temp = block.getRelative(gx2, gy2, gz2);
					if ((temp.getTypeId() != 63) && (temp.getTypeId() != 68)) {
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
							typebox = this.chestnearsign(temp);
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
		int randomInt = this.randomG.nextInt(50);
		randomInt += 1;
		return randomInt;
	}

	// savesignfiledefrag

	public void randomplaynote(Location player) {
		randomplaynote_c arr = new randomplaynote_c(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(dewset.ac, arr);
	}

	public boolean saveHistoryBreaking(CoreProtectAPI CoreProtect, Player player, Block hostBlock, Block setBlock
			) {
		if (CoreProtect != null) { // Ensure we have
			// access
			// to the API
			boolean success = false;
			// mean set 0
			success =success = CoreProtect.logRemoval(player.getName(), hostBlock.getLocation(),
					hostBlock.getType(), hostBlock.getData());
					return success;
		

		} else {
			setBlock.setTypeIdAndData(hostBlock.getTypeId(), hostBlock.getData(), false);
			

		}
		return false;
	}
	public boolean saveHistory(CoreProtectAPI CoreProtect, Player player, Block hostBlock, Block setBlock,
			boolean saveHistoryOnPlacing) {
		if (CoreProtect != null) { // Ensure we have
			// access
			// to the API
			boolean success = false;
			// mean set 0
			if ((hostBlock.getType() == Material.AIR) && (setBlock.getType() != Material.AIR)) {
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

	public void saveworld() {
		for (World world : Bukkit.getWorlds()) {
			world.save();
			for (Player pla : world.getPlayers()) {
				pla.saveData();
			}
		}
	}

	public static void searchRecursiveBlock(ArrayList<Block> blockList, Block curBlock, Material searchMaterial,
			Byte searchData) {
		int search = 3;

		/*
		 * dprint.r.printAll(curBlock.getX() + "," + curBlock.getY() + "," +
		 * curBlock.getZ() + " = " + curBlock.getType().name() + ":" +
		 * curBlock.getData());
		 */

		if (curBlock.getType() == searchMaterial) {
			if ((curBlock.getData() == searchData) || (searchData == -29)) {

				boolean found = false;
				for (int i = 0; i < blockList.size(); i++) {
					Block bo = blockList.get(i);
					if ((bo.getX() == curBlock.getX()) && (bo.getY() == curBlock.getY())
							&& (bo.getZ() == curBlock.getZ())) {
						found = true;
					}
					break;

				}

				if (found == false) {

					blockList.add(curBlock);
					// dprint.r.printAll("gift " + curBlock.getX() + "," +
					// curBlock.getY() + "," + curBlock.getZ());
				}
			}
		}

		Block tm;
		for (int x = -search; x <= search; x++) {
			for (int y = -search; y <= search; y++) {
				for (int z = -search; z <= search; z++) {

					tm = curBlock.getRelative(x, y, z);
					if (tm == null) {
						continue;
					}

					if (tm.getType() == searchMaterial) {
						if ((tm.getData() == searchData) || (searchData == -29)) {

							// check Block
							boolean sea = false;
							for (int i = 0; i < blockList.size(); i++) {
								Block te = blockList.get(i);
								if ((te.getX() == tm.getX()) && (te.getY() == tm.getY()) && (te.getZ() == tm.getZ())) {
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
			}
		}

	}

	public void showpos(Player player, int idc) {
		player.sendMessage(
				"cur select " + "(" + this.selectx1[idc] + "," + this.selecty1[idc] + "," + this.selectz1[idc]
						+ ") to ( " + this.selectx2[idc] + "," + this.selecty2[idc] + "," + this.selectz2[idc] + ")");
	}

	public void superdestroy(Block block, Player player, int dleft, int typeid, byte typedata) {
		if (player.getItemInHand().getDurability() < player.getItemInHand().getType().getMaxDurability()) {

			if ((block.getTypeId() != typeid) || (block.getData() != typedata)) {
				return;
			}

			if (this.cando_all(block, player, "delete") == false) {
				return;
			}

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
						if (dleft == 0) {
							return;
						}
						this.superdestroy(block2, player, dleft, typeid, typedata);
					}
				}
			}

		}
	}

} // dew minecraft