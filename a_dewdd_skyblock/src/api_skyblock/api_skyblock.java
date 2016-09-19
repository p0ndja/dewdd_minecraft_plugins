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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import dewddskyblock.AllBlockInGameType;
import dewddtran.tr;
import li.Constant_Protect;
import li.LXRXLZRZType;
import li.Useful;

public class api_skyblock {

	/*
	 * class LoListType { public Location location ; public boolean searchYet =
	 * false; }
	 * 
	 * class SkyLVType { public int id; public byte data; public int
	 * targetamount; public int nowamount; public double percent;
	 * 
	 * }
	 * 
	 * public String getKeyFromLocation(Location lo) { String abc =
	 * lo.getBlockX() + "," + lo.getBlockY() + "," + lo.getBlockZ(); return abc;
	 * }
	 * 
	 * class checkrateis implements Runnable {
	 * 
	 * private Block startblock; private int mode; private ArrayList<SkyLVType>
	 * skyLVType = new ArrayList<SkyLVType>(); private
	 * HashMap<String,LoListType> loList = new HashMap<String,LoListType>();
	 * private int curloid = 0;
	 * 
	 * public checkrateis(Block startblock, int mode,ArrayList<SkyLVType>
	 * skyLVType, HashMap<String,LoListType> loList,int curloid) {
	 * this.startblock = startblock; this.mode = mode; this.skyLVType =
	 * skyLVType; this.loList = loList; this.curloid = curloid;
	 * 
	 * Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, rnd.nextInt(20) +
	 * 1); }
	 * 
	 * 
	 * 
	 * public void run() { long startTime = System.currentTimeMillis(); long
	 * nowTime = System.currentTimeMillis(); long maxTime = 1000;
	 * 
	 * int maxblock = 50; if (lastMessage.equalsIgnoreCase("cancel")) { return;
	 * }
	 * 
	 * if (dewddtps.tps.getTPS() < 18) { lastcri = now; checkrateis abc = new
	 * checkrateis(startblock, mode); return; }
	 * 
	 * startTime = nowTime;
	 * 
	 * int proid = getProtectid(startblock); if (proid == -1) {
	 * dprint.r.printAll(tr.gettr("this_is_not_anyone_zone")); return; }
	 * 
	 * 
	 * Block mid = startblock.getWorld().getBlockAt(rs[proid].x, rs[proid].y,
	 * rs[proid].z); // add some block near it Block t;
	 * 
	 * boolean fo = false;
	 * 
	 * if (mode == 0) { if (loList.size() == 0) {
	 * dprint.r.printAll(tr.gettr("starting_checkpercent_of_is") +
	 * rs[proid].p[0]); dprint.r.printAll(tr.gettr("counting_amo_block"));
	 * 
	 * loList.clear();
	 * 
	 * for (int z = -5; z <= 5; z++) for (int y = -5; y <= 5; y++) for (int x =
	 * -5; x <= 5; x++) { t = mid.getRelative(x, y, z); if (t.getTypeId() == 0)
	 * continue;
	 * 
	 * // check is it have that block
	 * 
	 * 
	 * fo = false; String theKey = getKeyFromLocation(t.getLocation());
	 * LoListType theLo = loList.get(theKey); if (theLo == null) { fo = false;
	 * 
	 * } else { fo = true; }
	 * 
	 * if (fo == true) continue;
	 * 
	 * LoListType newLo = new LoListType(); newLo.location = t.getLocation();
	 * newLo.searchYet = false;
	 * 
	 * }
	 * 
	 * mode = 1; dprint.r.printAll(tr
	 * .gettr("amo_of_block_of_this_island_is_loop1" + proid + " = ") +
	 * loList.size());
	 * 
	 * dprint.r.printAll(tr.gettr("starting_searching_loop2" + proid ) );
	 * 
	 * } // size is 0
	 * 
	 * } // mode 0
	 * 
	 * // search another block left if (mode == 1) { boolean stillFound = false;
	 * startTime = System.currentTimeMillis(); // loop until can't add new block
	 * 
	 * long curLo = 0; for (LoListType sk : loList.values()) { curLo ++;
	 * 
	 * if (sk.searchYet == true) { continue; }
	 * 
	 * for (int z = -1; z <= 1; z++) for (int y = -1; y <= 1; y++) for (int x =
	 * -1; x <= 1; x++) { t = sk.location.getBlock().getRelative(x, y, z);
	 * 
	 * if (t.getTypeId() == 0) continue;
	 * 
	 * // check is it have that block fo = false;
	 * 
	 * String theKey = getKeyFromLocation(t.getLocation()); LoListType lo =
	 * loList.get(theKey); fo = lo == null ? false: true;
	 * 
	 * if (fo == true) continue;
	 * 
	 * loList.put(theKey, lo) ;
	 * 
	 * sk.searchYet = true;
	 * 
	 * dprint.r.printAll("added " + t.getType().name() + " = " + t.getX() + ","
	 * + t.getY() + "," + t.getZ() + " total " + csmax);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * // pause system nowTime = System.currentTimeMillis(); if (nowTime -
	 * startTime > maxTime) { dprint.r.printAll(tr.gettr("counting_rate_is") +
	 * curLo + "/" + loList.size() + "  > " + ((curLo * 100) /
	 * (loList.size()+1)) + "%"); startTime = System.currentTimeMillis(); }
	 * checkrateis abc = new checkrateis(startblock,
	 * mode,skyLVType,loList,curloid); curloid = 0; return;
	 * 
	 * 
	 * }
	 * 
	 * mode = 2;
	 * 
	 * dprint.r.printAll(tr .gettr("amo_of_block_of_this_island_is_loop2") +
	 * loList.size());
	 * 
	 * dprint.r.printAll(tr.gettr("starting_calc_rate_of_is"));
	 * 
	 * } // mode 1 // after got all of block check the score
	 * 
	 * if (mode == 2) {
	 * 
	 * if (curloid == 0) {
	 * 
	 * String mr[] = tr.loadfile("dewdd_skyblock", "drate.txt"); String m[];
	 * String m2;
	 * 
	 * skyLVType.clear();
	 * 
	 * 
	 * //dprint.r.printAll("lvBlocksMax " + crimax);
	 * 
	 * for (int i = 0; i < mr.length; i++) { m2 = mr[i];
	 * 
	 * m = m2.split("\\s+");
	 * 
	 * SkyLVType cri = new SkyLVType();
	 * 
	 * cri.id = Integer.parseInt(m[0]); cri.data = Byte.,,,,parseByte(m[1]);
	 * cri.targetamount = Integer.parseInt(m[2]); cri.nowamount = 0;
	 * 
	 * skyLVType.add(cri); }
	 * 
	 * curLoID = 0; dprint.r.printAll(tr.gettr("loaded_drate_file"));
	 * dprint.r.printAll(tr.gettr("starting_count_percent")); } // cs 0
	 * 
	 * // time to count the block
	 * 
	 * int csstart = curLoID;
	 * 
	 * while (curLoID < csmax) { startTime = System.currentTimeMillis();
	 * 
	 * // search for (int j = 0; j < crimax; j++) { if (cs[curLoID].getTypeId()
	 * == cri[j].id) { if (cs[curLoID].getData() == cri[j].data || cri[j].data
	 * == -29) {
	 * 
	 * cri[j].nowamount++; break;
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * curLoID++;
	 * 
	 * if (nowTime - startTime > 30000) {
	 * dprint.r.printAll(tr.gettr("percent_of_processing_is") + (curLoID * 100)
	 * / csmax + "%");
	 * 
	 * startTime = System.currentTimeMillis(); }
	 * 
	 * if (curLoID - csstart >= maxblock) { checkrateis abc = new
	 * checkrateis(startblock, mode); return; } }
	 * 
	 * dprint.r.printAll(tr .gettr("calc_percent_of_each_block_complete"));
	 * dprint.r.printAll(tr
	 * .gettr("starting_showing_percent_of_each_block_type"));
	 * 
	 * double eachper = getpercent(1); dprint.r.printAll(tr.gettr("eachper") +
	 * eachper);
	 * 
	 * // count percent of each block for (int i = 0; i < crimax; i++) {
	 * 
	 * if (cri[i].nowamount > cri[i].targetamount) cri[i].nowamount =
	 * cri[i].targetamount;
	 * 
	 * cri[i].percent = (eachper * cri[i].nowamount) / (cri[i].targetamount);
	 * 
	 * }
	 * 
	 * // bubble sort
	 * 
	 * dprint.r.printAll(tr.gettr("sorting_rank_is")); int swapc = 0;
	 * 
	 * boolean xx = false; if (xx == false) { for (int i = 0; i < crimax; i++) {
	 * for (int j = 0; j < (crimax - 1 - i); j++) {
	 * 
	 * if (cri[j].percent < cri[j + 1].percent) { // swap it cridata ttt = new
	 * cridata();
	 * 
	 * ttt.percent = cri[j].percent; ttt.id = cri[j].id; ttt.data = cri[j].data;
	 * ttt.targetamount = cri[j].targetamount; ttt.nowamount = cri[j].nowamount;
	 * 
	 * cri[j].percent = cri[j + 1].percent; cri[j].id = cri[j + 1].id;
	 * cri[j].data = cri[j + 1].data; cri[j].nowamount = cri[j + 1].nowamount;
	 * cri[j].targetamount = cri[j + 1].targetamount;
	 * 
	 * cri[j + 1].percent = ttt.percent; cri[j + 1].id = ttt.id; cri[j + 1].data
	 * = ttt.data; cri[j + 1].nowamount = ttt.nowamount; cri[j + 1].targetamount
	 * = ttt.targetamount;
	 * 
	 * swapc++; }
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * dprint.r.printAll(tr.gettr("sorted_rank_amount_is") + swapc); lastcri =
	 * System.currentTimeMillis(); for (int i = 0; i < crimax; i++) {
	 * 
	 * if (cri[i].nowamount >= cri[i].targetamount || cri[i].nowamount == 0)
	 * continue;
	 * 
	 * String na = i + " _ " + cri[i].id + " > " +
	 * Material.getMaterial(cri[i].id).name(); dprint.r.printAll(na + " = " +
	 * cri[i].nowamount + "/" + cri[i].targetamount + " (" + cri[i].percent +
	 * "%)"); }
	 * 
	 * double sumper = 0.0;
	 * 
	 * for (int i = 0; i < crimax; i++) {
	 * 
	 * sumper += cri[i].percent; }
	 * 
	 * dprint.r.printAll(tr.gettr("sum_per_rate_of_island") + rs[proid].p[0] +
	 * " = " + sumper + "%");
	 * 
	 * rs[proid].percent = sumper;
	 * 
	 * if (sumper == 0) rs[proid].p[0] = ""; saversprotectfile();
	 * loadrsprotectfile();
	 * 
	 * return;
	 * 
	 * } // mode 2
	 * 
	 * } }
	 */

	class SmallIslandType {
		public HashMap<String, AllBlockInGameType> allBlockInGame;
		public ArrayList<AllBlockInGameType> allBlockInGameAsList;
	}

	public SmallIslandType loadMissionBlockFile() {

		HashMap<String, AllBlockInGameType> allBlockInGame = new HashMap<String, AllBlockInGameType>();
		ArrayList<AllBlockInGameType> allBlockInGameAsList = new ArrayList<AllBlockInGameType>();

		String filena = Constant.folder_name + File.separator + Constant.rsGenerateListBlock_filename;

		File fff = new File(filena);

		try {

			allBlockInGame.clear();

			fff.createNewFile();

			dprint.r.printC("loading mission file : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				m = strLine.split(":");
				// Print the content on the console

				AllBlockInGameType miss = new AllBlockInGameType();
				miss.theName = m[0];
				miss.data = Byte.parseByte(m[1]);

				miss.maxStack = Integer.parseInt(m[2]);
				miss.isBlock = Boolean.parseBoolean(m[3]);

				if (m.length == 5) {
					miss.limitSell = Integer.parseInt(m[4]);
				} else {
					miss.limitSell = 0;
				}

				// d.pl("...");
				// rs[rsMax - 1].mission = 0;

				if (allBlockInGame.get(miss.theName + ":" + miss.data) != null) {
					dprint.r.printC("loading not null");

				}
				allBlockInGame.put(miss.theName + ":" + miss.data, miss);
				allBlockInGameAsList.add(miss);
			}

			dprint.r.printC(" Loaded " + filena);

			in.close();

			SmallIslandType sit = new SmallIslandType();
			sit.allBlockInGame = allBlockInGame;
			sit.allBlockInGameAsList = allBlockInGameAsList;

			return sit;
		} catch (Exception e) {// Catch exception if any
			dprint.r.printC("Error load " + filena + e.getMessage());
			return null;
		}
	}

	public void addSmallIslandNearThisBlock(Block block, long delay) {
		SmallIslandType sit = loadMissionBlockFile();
		AddSmallIslandNearThisPosition aa = new AddSmallIslandNearThisPosition(block, sit, true);
		Thread abc = new Thread(aa);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, delay);

	}

	class AddSmallIslandNearThisPosition implements Runnable {
		private SmallIslandType sit;
		private Block block;

		public AddSmallIslandNearThisPosition(Block block, SmallIslandType sit, boolean firstTime) {
			this.sit = sit;
			this.block = block;
			if (firstTime == true) {

				for (int i = 0; i < sit.allBlockInGameAsList.size(); i++) {
					AllBlockInGameType a = sit.allBlockInGameAsList.get(i);

					if (a.isBlock == true) {
						a.curAmount = 64 * 5;
					} else if (a.isBlock == false) {
						a.curAmount = 64 * 10;
					}

				}

				boolean foundChest = true;
				do {
					foundChest = false;
					for (int i = 0; i < sit.allBlockInGameAsList.size(); i++) {
						AllBlockInGameType a = sit.allBlockInGameAsList.get(i);
						if (Material.getMaterial(a.theName) == Material.CHEST) {
							sit.allBlockInGameAsList.remove(i);
							foundChest = true;
							break;
						}

						if (Material.getMaterial(a.theName) == Material.TRAPPED_CHEST) {
							sit.allBlockInGameAsList.remove(i);
							foundChest = true;
							break;
						}

					}

				} while (foundChest == true);

				// remove chest

			}

		}

		@Override
		public void run() {

			long start = System.currentTimeMillis();

			boolean isDone = true;

			// check
			for (int i = 0; i < sit.allBlockInGameAsList.size(); i++) {
				AllBlockInGameType a = sit.allBlockInGameAsList.get(i);

				if (a.isBlock == true) {
					if (a.curAmount > 0) {
						isDone = false;
						break;
					}

				}
			}

			int maxCubeSize = 10;
			int minCubeSize = 1;

			if (isDone == false) {

				// place untill all block done

				// search space

				boolean foundSpace = false;
				int x1 = 0;
				int y1 = 0;
				int z1 = 0;

				int curCubeSize = rnd.nextInt(maxCubeSize) + minCubeSize;

				int tmpX = block.getX() + x1;
				int tmpZ = block.getZ() + z1;

				int countFoundSpace = 0;
				do {
					foundSpace = true;
					int maxRadi = 120;

					int radi = rnd.nextInt(maxRadi) + minCubeSize;

					x1 = rnd.nextInt(radi * 2) - radi;
					z1 = rnd.nextInt(radi * 2) - radi;
					y1 = rnd.nextInt(255 - 10) + 5;

					tmpX = block.getX() + x1;
					tmpZ = block.getZ() + z1;

					if (Math.abs(tmpX - block.getX()) <= 25 && Math.abs(tmpZ - block.getZ()) <= 25) {
						foundSpace = false;
						continue;
					}

					// check space
					for (int x2 = tmpX - curCubeSize; x2 <= tmpX + curCubeSize; x2++) {
						for (int z2 = tmpZ - curCubeSize; z2 <= tmpZ + curCubeSize; z2++) {
							for (int y2 = y1 - curCubeSize; y2 <= y1 + curCubeSize; y2++) {
								Block b2 = block.getWorld().getBlockAt(x2, y2, z2);
								if (b2.getType() != Material.AIR) {
									foundSpace = false;
									break;
								}

							}
						}
					}
					countFoundSpace++;
				} while (foundSpace == false && countFoundSpace < 10000);

				if (foundSpace == false) {
					return;
				}
				// random Add Block

				int curSkip = rnd.nextInt(100);
				for (int x2 = tmpX - curCubeSize; x2 <= tmpX + curCubeSize; x2++) {
					for (int z2 = tmpZ - curCubeSize; z2 <= tmpZ + curCubeSize; z2++) {
						for (int y2 = y1 - curCubeSize; y2 <= y1 + curCubeSize; y2++) {

							if (rnd.nextInt() >= curSkip) {
								continue;
							}

							Block b2 = block.getWorld().getBlockAt(x2, y2, z2);
							// add random Block
							int rand = 0;
							AllBlockInGameType a = null;
							int count = 0;
							do {
								rand = rnd.nextInt(sit.allBlockInGameAsList.size());

								a = sit.allBlockInGameAsList.get(rand);
								if (a.curAmount <= 0) {
									sit.allBlockInGameAsList.remove(rand);
								} else {
									if (a.isBlock == true) {
										break;
									}
								}
								count++;

							} while (sit.allBlockInGameAsList.size() > 0 && count <= 1000);

							if (a == null) {
								break;
							}

							b2.setType(Material.getMaterial(a.theName));
							b2.setData(a.data);
							a.curAmount--;

						}
					}
				}

				/*
				 * dprint.r.printAll("block is null " + (block == null));
				 * dprint.r.printAll("block location is null " +
				 * (block.getLocation() == null));
				 * 
				 * 
				 * dprint.r.printAll( tr.gettr("skyblock_added_small_Island_at")
				 * + tr.locationToString(block.getLocation()));
				 */

				// after create done 1 island
				// thread sleep

				// clear drop

				for (Entity en : block.getWorld().getEntities()) {
					if (en == null) {
						continue;
					}

					if (en.getType() == EntityType.DROPPED_ITEM) {
						Block b2 = block.getWorld().getBlockAt(tmpX, y1, tmpZ);
						if (en.getLocation().distance(b2.getLocation()) <= 20) {
							en.remove();
						}
					}
				}

				Block b2 = block.getWorld().getBlockAt(tmpX, y1, tmpZ);
				// dprint.r.printAll("added new small island at " + b2.getX() +
				// "," + b2.getY() + "," + b2.getZ());

				AddSmallIslandNearThisPosition theNew = new AddSmallIslandNearThisPosition(block, sit, false);

				Thread abc = new Thread(theNew);

				long end = System.currentTimeMillis();
				long diff = end - start;

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, (diff / 20) * 3);

			} else if (isDone == true) {

				boolean isDone2 = true;

				do {

					// check
					for (int i = 0; i < sit.allBlockInGameAsList.size(); i++) {
						AllBlockInGameType a = sit.allBlockInGameAsList.get(i);

						if (a.isBlock == false) {
							if (a.curAmount > 0) {
								isDone2 = false;
								break;
							}

						}
					}

					// dprint.r.printAll("adding inventory");

					// time to add chest
					boolean foundSpace = true;
					int x1 = 0;
					int z1 = 0;
					int y1 = 0;

					int tmpX = 0;
					int tmpZ = 0;

					do {
						foundSpace = true;
						int maxRadi = 120;

						int radi = rnd.nextInt(maxRadi) + 3;

						x1 = rnd.nextInt(radi * 2) - radi;
						z1 = rnd.nextInt(radi * 2) - radi;
						y1 = rnd.nextInt(255 - 10) + 5;

						tmpX = block.getX() + x1;
						tmpZ = block.getZ() + z1;

						// check space

						for (int xxx = -1; xxx <= 1; xxx++) {
							Block b2 = block.getWorld().getBlockAt(tmpX + xxx, y1, tmpZ);
							if (b2.getType() != Material.AIR) {
								foundSpace = false;
							}

						}

						for (int xxx = -1; xxx <= 1; xxx++) {
							Block b2 = block.getWorld().getBlockAt(tmpX, y1, tmpZ + xxx);
							if (b2.getType() != Material.AIR) {
								foundSpace = false;
							}

						}

					} while (foundSpace == false);

					Block b2 = block.getWorld().getBlockAt(tmpX, y1, tmpZ);
					b2.setType(Material.CHEST);
					// b2.setData((byte)rnd.nextInt(4));

					Chest chest = (Chest) b2.getState();
					Inventory inv = chest.getInventory();
					int count = 0;
					do {

						int rand = 0;
						AllBlockInGameType a = null;
						do {
							if (sit.allBlockInGameAsList.size() > 0) {
								rand = rnd.nextInt(sit.allBlockInGameAsList.size());
							} else {
								chest.update();
								return;
							}
							a = sit.allBlockInGameAsList.get(rand);
							if (a.curAmount <= 0) {
								sit.allBlockInGameAsList.remove(rand);
							} else {
								if (a.isBlock == false) {
									break;
								}
							}

						} while (sit.allBlockInGameAsList.size() > 0);

						ItemStack itm = new ItemStack(Material.getMaterial(a.theName), 1);
						itm.getData().setData((byte) a.data);
						int tmpSize = rnd.nextInt(itm.getType().getMaxStackSize() + 1) + 1;
						inv.addItem(itm.getData().toItemStack(tmpSize));
						
						
						a.curAmount -= 64;
						count++;

					} while (inv.firstEmpty() > -1 && count < 100);

					chest.update();
					/*
					 * dprint.r.printAll(
					 * tr.gettr("skyblock_added_small_Island_at") +
					 * tr.locationToString(block.getLocation()));
					 * 
					 */

				} while (isDone2 == false);

			}

			// if not

			// create Island

		}
	}

	class AdjustProtect implements Runnable {
		private Block midBlockX0Z0;
		private Player player;

		private int curI = 0;
		private RSData tmprs[];
		private int curNewID = 0;

		public AdjustProtect(Block midBlockX0Z0, Player player) {
			this.midBlockX0Z0 = midBlockX0Z0;
			this.player = player;

		}

		public AdjustProtect(Block midBlockX0Z0, Player player, int curI, RSData tmprs[], int curNewID) {
			this.midBlockX0Z0 = midBlockX0Z0;
			this.player = player;
			this.curI = curI;
			this.tmprs = tmprs;
			this.curNewID = curNewID;
		}

		@Override
		public void run() {

			if (curI == 0) {
				tmprs = new RSData[Constant.rsBuffer];
				curNewID = 0;

				for (int i = 0; i < Constant.rsBuffer; i++) {
					tmprs[i] = new RSData();
					tmprs[i].p = new String[RSMaxPlayer];

				}

			}

			while (curI < rsMax) {

				Block bo = midBlockX0Z0.getWorld().getBlockAt(rs[curI].x, rs[curI].y, rs[curI].z);

				boolean thereBlock = checkIsThatAreBlockOrNot(bo, player);
				if (thereBlock == true) {
					// copy

					tmprs[curNewID] = rs[curI].copyIt();

					curNewID++;
					dprint.r.printAll("curI " + curI + " /curNewID " + curNewID + " stil hastBlock");

					curI++;
				} else {
					// delete protec
					dprint.r.printAll("curI " + curI + " /curNewID " + curNewID + " nope");

					curI++;

				}

				for (Chunk chu : Bukkit.getWorld("world").getLoadedChunks()) {
					if (chu == null) {
						continue;
					}
					chu.unload();
				}

				AdjustProtect abc = new AdjustProtect(bo, player, curI, tmprs, curNewID);
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, 1);

				return;

			}

			if (curI == rsMax) {

				// copyBack
				rsMax = curNewID;
				for (int i = 0; i < curNewID; i++) {
					rs[i] = tmprs[i].copyIt();
				}

			}

			// player.sendMessage("thereBlock " + thereBlock);

		}
	}

	class AdjustProtect2 implements Runnable {
		private Block midBlockX0Z0;
		private Player player;

		private int curx = 0;
		private int curz = 0;

		public AdjustProtect2(Block midBlockX0Z0, Player player) {
			this.midBlockX0Z0 = midBlockX0Z0;
			this.player = player;

			LXRXLZRZType ee = getPositionLXRXLZRZ();
			this.curx = ee.lx;
			this.curz = ee.lz;
		}

		public AdjustProtect2(Block midBlockX0Z0, Player player, int curx, int curz) {
			this.midBlockX0Z0 = midBlockX0Z0;
			this.player = player;
			this.curx = curx;
			this.curz = curz;

		}

		@Override
		public void run() {

			// search top left

			// search top left

			LXRXLZRZType ee = getPositionLXRXLZRZ();

			Block bb = null;

			for (int tmpx = curx; tmpx <= ee.rx; tmpx += 300) {

				for (int tmpz = curz; tmpz <= ee.rz; tmpz += 300) {

					// check protect if there so skip
					bb = midBlockX0Z0.getLocation().getWorld().getBlockAt(tmpx, 150, tmpz);
					int checkid = getProtectid(bb);
					if (checkid > -1) {
						dprint.r.printC("searching no protect zone " + tmpx + ",150," + tmpz);

						continue;
					}

					player.sendMessage(dprint.r.color("searching no protect zone " + tmpx + ",150," + tmpz));

					boolean thereBlock = checkIsThatAreBlockOrNot(bb, player);

					if (thereBlock == true) {
						// buy new protect

						rs[rsMax] = new RSData();

						rs[rsMax].x = tmpx;
						rs[rsMax].z = tmpz;
						rs[rsMax].y = 150;
						rs[rsMax].p = new String[RSMaxPlayer];

						for (int i = 0; i < RSMaxPlayer; i++) {
							rs[rsMax].p[i] = "null";
						}

						rs[rsMax].p[0] = getNewOwnerName();

						player.sendMessage(dprint.r.color(
								"bought new protect " + tmpx + ",150," + tmpz + " owner name " + rs[rsMax].p[0]));

						rsMax++;

					}

					tmpz += 300;
					if (tmpz > ee.rz) {
						tmpx += 300;
						tmpz = ee.lz;
					}

					for (Chunk chu : Bukkit.getWorld("world").getLoadedChunks()) {
						if (chu == null) {
							continue;
						}
						chu.unload();
					}

					AdjustProtect2 abc = new AdjustProtect2(bb, player, tmpx, tmpz);

					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, 1);
					return;

				}
			}

		}
	}

	class CreateSkyblockRS implements Runnable {

		private Player player;

		public CreateSkyblockRS(Player player) {
			this.player = player;

		}

		@Override
		public void run() {

			if (System.currentTimeMillis() - lastcreate < 10000) {
				dprint.r.printAll("ptdew&dewdd : " + tr.gettr("don't_spam_this_command"));

				lastcreate = System.currentTimeMillis();
				return;
			}

			dprint.r.printAll("ptdew&dewdd : " + tr.gettr("searching_freezone_for_build_skyblock"));
			lastcreate = System.currentTimeMillis();

			player.getInventory().clear();

			// random x , y ,z

			boolean buildcomplete = false;

			int x = 0;
			int y = 0;
			int z = 0;

			int searchRadius = 2;
			int searchCount = 0;

			double thelx = tr.gettrint("sky random search lx");
			double therx = tr.gettrint("sky random search rx");
			double thelz = tr.gettrint("sky random search lz");
			double therz = tr.gettrint("sky random search rz");

			while (buildcomplete == false) {
				searchCount++;

				if (searchCount > 1000) {
					searchRadius += 1;
				}

				// random x y z this is not near old rs list
				buildcomplete = true;

				x = rnd.nextInt(searchRadius) * 300 * (rnd.nextInt(2) == 1 ? 1 : -1);
				z = rnd.nextInt(searchRadius) * 300 * (rnd.nextInt(2) == 1 ? 1 : -1);

				if (x < thelx || x > therx) {
					continue;
				}
				if (z < thelz || z > therz) {
					continue;
				}

				y = rnd.nextInt(200) + 50;

				// dprint.r.printAll("searching..." + x + "," + y + "," + z + "
				// searchCount " + searchCount + ", " + searchRadius);

				boolean checkrs = true;

				for (int lop = 0; lop < rsMax; lop++) {
					if (rs[lop].x == x && rs[lop].z == z) {
						// dprint.r.printAll(">>> owner " + rs[lop].p[0]);
						checkrs = false;
						break;
					}
				}

				if (checkrs == true) {
					for (int i = 1; i < 200; i++) {

						Block bd = player.getWorld().getBlockAt(x, i, z);
						if (bd.getType() != Material.AIR) {
							checkrs = false;
						}
					}
				}

				// dprint.r.printAll("checkRS " + checkrs + ", buildComplete" +
				// buildcomplete);

				if (checkrs == false) {
					buildcomplete = false;
					// dprint.r.printAll("checkrs = false " + buildcomplete);

				} else { // if x y z this can do

					dprint.r.printAll("random " + x + "," + y + "," + z);

					int oldID = getOWNIslandID(player.getName(), false);
					if (oldID > -1) {
						rs[oldID].p[0] = getNewOwnerName();

					}

					int newid = getOWNIslandID(player.getName(), true);
					// get free slot for this player
					rs[newid].x = x;
					rs[newid].y = y;
					rs[newid].z = z;

					for (int lop = 0; lop < RSMaxPlayer; lop++) {
						rs[newid].p[lop] = "null";
					}
					rs[newid].p[0] = player.getName();
					rs[newid].p[1] = Constant_Protect.flag_autocut;
					rs[newid].p[2] = Constant_Protect.flag_autoabsorb;
					rs[newid].mission = 0;
					player.getWorld().getBlockAt(x - 16, y, z - 16).getChunk().getX();
					player.getWorld().getBlockAt(x - 16, y, z - 16).getChunk().getZ();

					player.getWorld().getBlockAt(x + 16, y, z + 16).getChunk().getX();
					player.getWorld().getBlockAt(x + 16, y, z + 16).getChunk().getZ();

					// clear for skyblock
					for (int nx = -60; nx <= 60; nx++) {
						for (int nz = -60; nz <= 60; nz++) {
							for (int ny = 0; ny <= 255; ny++) {

								player.getWorld().getBlockAt(x + nx, y + ny, z + nz).setType(Material.AIR);

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
								block2.setType(Material.GRASS);

							}
						}

					}

					// add item
					block2 = block.getRelative(0, 4, 0);

					block2.setType(Material.CHEST);
					block2 = block.getRelative(0, 4, 0);
					Chest chest = (Chest) block2.getState();
					chest.getInventory().clear();
					
					ItemStack itm = new ItemStack(Material.DIAMOND, 3);
					chest.getInventory().addItem(itm.getData().toItemStack(3));
					
					 itm = new ItemStack(Material.COAL, 3);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					 itm = new ItemStack(Material.SAPLING, 3);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.SAPLING, 3);
					itm.getData().setData((byte) 1);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.SAPLING, 3);
					itm.getData().setData((byte) 2);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.SAPLING, 3);
					itm.getData().setData((byte) 3);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.SAPLING, 3);
					itm.getData().setData((byte) 4);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.SAPLING, 3);
					itm.getData().setData((byte) 5);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.CARROT, 3);

					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(Material.POTATO, 3);

					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(Material.PUMPKIN_SEEDS, 3);

					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(Material.MELON_SEEDS, 3);

					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(Material.SEEDS, 3);

					chest.getInventory().addItem(itm.getData().toItemStack(10));
					itm = new ItemStack(Material.SUGAR_CANE, 3);
					chest.getInventory().addItem(itm.getData().toItemStack(10));

					itm = new ItemStack(Material.TORCH, 30);
					chest.getInventory().addItem(itm.getData().toItemStack(3));

					itm = new ItemStack(Material.INK_SACK, 64);
					itm.getData().setData((byte) 15);
					chest.getInventory().addItem(itm.getData().toItemStack(64));

					itm = new ItemStack(Material.ICE, 2);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(Material.LAVA_BUCKET, 1);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(Material.CACTUS, 1);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(Material.BROWN_MUSHROOM, 2);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(Material.RED_MUSHROOM, 2);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					itm = new ItemStack(Material.SAND, 7);

					itm.setData(itm.getData());
					chest.getInventory().addItem(itm);

					chest.update();

					block.getWorld().loadChunk(block2.getX(), block2.getZ(), true);
					block2 = block.getRelative(0, 5, 0);
					player.teleport(block2.getLocation());

					dprint.r.printA(tr.gettr("good_luck_createlsdone") + " '" + player.getName() + "'(" + x + "," + y
							+ "," + z + ")");

					dprint.r.printA(tr.gettr("if_you_want_to_back_to_is_type") + " /skyblock home " + player.getName());

					dprint.r.printA(tr.gettr("anyway_you_shold_sethome_and_home_for_shorten_typing")
							+ " /skyblock home " + player.getName());

					lastcreate = System.currentTimeMillis();

					player.sendMessage(tr.gettr("cur_c_is") + (rs[newid].mission));

					// add item done

					// save file
					saveRSProtectFile();
					buildcomplete = true;

				//	addSmallIslandNearThisBlock(block, 20);

				}

				// dprint.r.printAll("looping " + checkrs + " , " +
				// buildcomplete);

			} // loop build complete

			// dprint.r.printAll("build comople " + buildcomplete + " , " );
		}
	}

	class MissionNotification implements Runnable {

		@Override
		public void run() {

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player == null) {
					continue;
				}

				// int id = getOWNIslandID(player, false);

				int id = getProtectid(player.getLocation().getBlock());

				// island not found
				if (id == -1) {
					continue;
				}

				if (getplayerinslot(player.getName(), id) > -1) {

					player.sendMessage(dprint.r.color(tr.gettr("cur_c_is") + (rs[id].mission)));
				}

				// check his
			}

		}

	}

	public static int RSMaxPlayer = 20;

	public static RSData rs[] = new RSData[100];

	public static int rsMax = 0;

	public static JavaPlugin ac = null;

	public static ArrayList<LV1000Type> lv1000 = new ArrayList<LV1000Type>();

	public static boolean cando(Block block, Player player, String mode) {
		if ((player.hasPermission(Constant.poveride)) == true) {
			return true;
		}

		if (!isrunworld(block.getWorld().getName())) {
			return true;
		}

		int getid = getProtectid(block);

		if (getid == -1) {
			// player.sendMessage(dprint.r.color("this is not anyone skyblock");
			return true; // can't do it
		}

		// found
		int getslot = getplayerinslot(player.getName(), getid);
		rs[getid].lastUsed = System.currentTimeMillis();

		if (getplayerinslot(Constant_Protect.flag_noprotect, getid) > -1) {

			return true;
		}

		if (getplayerinslot(Constant_Protect.flag_everyone, getid) > -1 && mode.equalsIgnoreCase("right")) {

			// player.sendMessage(dprint.r.color("this is not your skyblock ,
			// host is "
			// +
			// dew.rs[getid].p[0]);
			return true;
		}

		if (getslot == -1) {

			// player.sendMessage(dprint.r.color("this is not your skyblock ,
			// host is "
			// +
			// dew.rs[getid].p[0]);
			return false;
		} else {

			return true;
		}

	}

	public static String getNewOwnerName() {
		String abc = "";
		boolean found = false;
		do {
			found = false;
			Random rnd = new Random();
			abc = "freeZone" + rnd.nextInt(1000);
			for (int i = 0; i < rsMax; i++) {
				if (rs[i].p[0].equalsIgnoreCase(abc) == true) {

					found = true;
					break;
				}
			}

		} while (found == true);

		return abc;
	}

	public static int getplayerinslot(String player, Block block) {

		int getid = getProtectid(block);
		if (getid == -1) {
			return -2;
		}

		for (int lop = 0; lop < RSMaxPlayer; lop++) {
			if (rs[getid].p[lop].equalsIgnoreCase(player)) {
				return lop;
			}
		}

		return -1;
	}

	public static int getplayerinslot(String player, int rsID) {
		for (int lop = 0; lop < RSMaxPlayer; lop++) {
			if (rs[rsID].p[lop].equalsIgnoreCase(player)) {
				return lop;
			}
		}

		return -1;
	}

	public static LXRXLZRZType getPositionLXRXLZRZ() {
		LXRXLZRZType ee = new LXRXLZRZType(rs[0].x, rs[0].y, rs[0].z, rs[0].x, rs[0].y, rs[0].z);

		ee.lx = rs[0].x;
		ee.rx = rs[0].x;
		ee.lz = rs[0].z;
		ee.rz = rs[0].z;

		for (int i = 0; i < rsMax; i++) {
			if (rs[i].x < ee.lx) {
				ee.lx = rs[i].x;
			}

			if (rs[i].x > ee.rx) {
				ee.rx = rs[i].x;
			}

			if (rs[i].z < ee.lz) {
				ee.lz = rs[i].z;
			}

			if (rs[i].z > ee.rz) {
				ee.rz = rs[i].z;
			}

		}

		return ee;
	}

	public static int getProtectid(Block block) {
		// must check world

		for (int lop = 0; lop < rsMax; lop++) {
			if (block.getX() >= (rs[lop].x - 150) && block.getX() <= (rs[lop].x + 149)
					&& block.getZ() >= (rs[lop].z - 150) && block.getZ() <= (rs[lop].z + 149)) {
				return lop;
			}
		}

		return -1;
	}

	// Chat Event.class
	// BlockBreakEvent
	public static boolean isrunworld(String worldName) {
		return tr.isrunworld(ac.getName(), worldName);
	}

	// check if it is running
	Random rnd = new Random();

	public int maxautocut = 5;

	public String lastMessage = "";

	long lastcreate = 0;

	public static String sky_c_inv_name = "sky c";
	long lastclean = 0;

	int amount = 0; // recusive
					// count

	public api_skyblock() {
		loadRSProtectFile();
		loadLVFile();
	}

	public void adjustProtect(Block block, Player player) {

		AdjustProtect abc = new AdjustProtect(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, 1);

	}

	public void adjustProtect2(Block block, Player player) {

		AdjustProtect2 abc = new AdjustProtect2(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, 1);

	}

	public void applyReward(int rsID) {

		switch (rs[rsID].mission) {
		case 0: // get cobble stone

			Block bo = getBlockMiddleRS(rsID);
			Block bo2 = searchSpaceCube(bo, 5, 5);

			for (int i = 0; i < 5; i++) {
				for (int i2 = 0; i2 < 5; i2++) {
					for (int i3 = 0; i3 < 5; i3++) {

						Block bo3 = bo2.getRelative(i, i2, i3);
						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						if (rnd.nextInt(100) > 80) {
							bo3.setType(Material.STONE);
						}

					}
				}
			}

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;
		case 1:

			bo = getBlockMiddleRS(rsID);
			bo2 = searchSpaceCube(bo, 5, 5);

			for (int i = 0; i < 5; i++) {
				for (int i2 = 0; i2 < 5; i2++) {
					for (int i3 = 0; i3 < 5; i3++) {

						Block bo3 = bo2.getRelative(i, i2, i3);
						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						if (rnd.nextInt(100) > 80) {
							bo3.setType(Material.GRASS);
						}

					}
				}
			}

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			rs[rsID].tmpForCountingBone1 = 0;

			break;
		case 2:

			bo = getBlockMiddleRS(rsID);
			bo2 = searchSpaceCube(bo, 5, 5);

			bo2.setType(Material.CHEST);

			Chest chest = (Chest) bo2.getState();

			ItemStack itm = new ItemStack(Material.SAPLING, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(Material.SAPLING, 3);
			itm.getData().setData((byte) 1);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(Material.SAPLING, 3);
			itm.getData().setData((byte) 2);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(Material.SAPLING, 3);
			itm.getData().setData((byte) 3);
			chest.getInventory().addItem(itm.getData().toItemStack(3));

			itm = new ItemStack(Material.CARROT, 3);

			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(Material.POTATO, 3);

			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(Material.PUMPKIN_SEEDS, 3);

			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(Material.MELON_SEEDS, 3);

			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(Material.SEEDS, 3);

			chest.getInventory().addItem(itm.getData().toItemStack(10));
			itm = new ItemStack(Material.SUGAR_CANE, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));

			itm = new ItemStack(Material.CACTUS, 1);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(Material.BROWN_MUSHROOM, 2);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(Material.RED_MUSHROOM, 2);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			itm = new ItemStack(Material.SAND, 7);

			itm.setData(itm.getData());
			chest.getInventory().addItem(itm);

			bo2.getRelative(BlockFace.DOWN).setType(Material.BOOKSHELF);
			bo2.getRelative(0, -1, 1).setType(Material.TORCH);

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;

		case 3:

			bo = getBlockMiddleRS(rsID);
			bo2 = searchSpaceCube(bo, 5, 5);

			for (int i = 0; i < 5; i++) {
				for (int i2 = 0; i2 < 5; i2++) {
					for (int i3 = 0; i3 < 5; i3++) {

						Block bo3 = bo2.getRelative(i, i2, i3);
						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						if (rnd.nextInt(100) > 90) {
							bo3.setType(Material.IRON_ORE);
						}

					}
				}
			}

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;

		case 4:

			bo = getBlockMiddleRS(rsID);
			bo2 = searchSpaceCube(bo, 5, 5);

			for (int i = 0; i < 5; i++) {
				for (int i2 = 0; i2 < 5; i2++) {
					for (int i3 = 0; i3 < 5; i3++) {

						Block bo3 = bo2.getRelative(i, i2, i3);
						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						if (rnd.nextInt(100) > 91) {
							bo3.setType(Material.GOLD_ORE);
						}

					}
				}
			}

			bo2 = searchSpaceCube(bo, 5, 5);

			for (int i = 0; i < 5; i++) {
				for (int i2 = 0; i2 < 5; i2++) {
					for (int i3 = 0; i3 < 5; i3++) {

						Block bo3 = bo2.getRelative(i, i2, i3);
						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						if (rnd.nextInt(100) > 90) {
							bo3.setType(Material.COAL_ORE);
						}

					}
				}
			}

			// add mon

			int count = 0;
			do {
				bo2 = searchSpaceCube(bo, 5, 5);
				count++;

				if (count > 5000) {
					dprint.r.printAll(tr.gettr("this_is_land_cannot_search_anyblock_for_applyReward_LV4" + " " + rsID
							+ " xyz = " + rs[rsID].x + "," + rs[rsID].y + "," + rs[rsID].z));
					break;
				}
			} while (bo2.getRelative(-1, 0, 0).getType() == Material.AIR);

			for (int i = 0; i < 5; i++) {
				for (int i2 = 0; i2 <= 0; i2++) {
					for (int i3 = 0; i3 < 5; i3++) {

						Block bo3 = bo2.getRelative(i, i2, i3);
						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						bo3.setType(Material.EMERALD_ORE);

					}
				}
			}

			Block bo3 = bo2.getRelative(2, 1, 2);
			bo3.setType(Material.NETHERRACK);
			bo3.getRelative(BlockFace.UP).setType(Material.FIRE);

			// add sign

			bo.getWorld().getBlockAt(bo.getX(), 0, bo.getZ()).setType(Material.BEDROCK);

			Block signAdder = bo.getWorld().getBlockAt(bo.getX(), 1, bo.getZ());
			signAdder.setType(Material.SIGN_POST);

			Sign sign = (Sign) signAdder.getState();
			sign.setLine(0, "" + rs[rsID].mission);
			sign.setLine(1, "" + bo3.getX());
			sign.setLine(2, "" + bo3.getY());
			sign.setLine(3, "" + bo3.getZ());
			sign.update(true);

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			// mon
			bo2 = searchSpaceCube(bo, 5, 5);

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;

		case 5:

			bo = getBlockMiddleRS(rsID);
			bo2 = searchSpaceCube(bo, 5, 5);

			bo2.setType(Material.CHEST);

			chest = (Chest) bo2.getState();

			itm = new ItemStack(Material.LAVA_BUCKET, 1);
			chest.getInventory().addItem(itm.getData().toItemStack(2));

			itm = new ItemStack(Material.ICE, 1);
			chest.getInventory().addItem(itm.getData().toItemStack(2));

			itm = new ItemStack(Material.GRAVEL, 32);
			chest.getInventory().addItem(itm.getData().toItemStack(32));

			itm = new ItemStack(Material.SAND, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(17));

			itm = new ItemStack(Material.FEATHER, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(10));

			itm = new ItemStack(Material.NAME_TAG, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(1));

			itm = new ItemStack(Material.MOSSY_COBBLESTONE, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(4));

			itm = new ItemStack(Material.WEB, 3);
			chest.getInventory().addItem(itm.getData().toItemStack(2));

			bo2.getRelative(BlockFace.DOWN).setType(Material.LAPIS_ORE);
			bo2.getRelative(0, -1, 1).setType(Material.TORCH);

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;

		case 6:

			bo = getBlockMiddleRS(rsID);
			// bo2 = searchSpaceCube(bo, 5, 5);

			for (int i = -10; i < 10; i++) {
				for (int i2 = 255; i2 <= 255; i2++) {
					for (int i3 = -10; i3 < 10; i3++) {

						bo3 = bo.getWorld().getBlockAt(bo.getX() + i, i2, bo.getZ() + i3);

						if (bo3.getType() != Material.AIR) {
							dprint.r.printAll(tr.gettr("error while applyReward lv 0 block is != air"));
							break;
						}

						bo3.setType(Material.GRAVEL);

					}
				}
			}

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;
		default:

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;
		}
	}

	public boolean checkIsThatAreBlockOrNot_old(Block midBlockX0Z0, Player player) {
		int count = 0;
		boolean thereBlock = false;

		int search = 10;
		for (int x = -search; x <= search; x++) {
			for (int y = 30; y <= 256; y++) {

				for (int z = -search; z <= search; z++) {

					Block bbo = midBlockX0Z0.getWorld().getBlockAt(midBlockX0Z0.getX() + x, y, midBlockX0Z0.getZ() + z);

					if (bbo.getType() != Material.AIR) {

						player.sendMessage(bbo.getX() + "," + bbo.getY() + "," + bbo.getZ() + " = "
								+ bbo.getType().name() + ":" + bbo.getData());
						thereBlock = true;
						break;
					}
				}

				if (thereBlock == true) {
					break;
				}
			}

			if (thereBlock == true) {
				break;
			}

		}

		while (count < 100000 && thereBlock == false) {
			count++;

			int x = rnd.nextInt(300) - 150;
			int y = rnd.nextInt(256);
			int z = rnd.nextInt(300) - 150;

			Block bbo = midBlockX0Z0.getWorld().getBlockAt(midBlockX0Z0.getX() + x, y, midBlockX0Z0.getZ() + z);

			if (bbo.getType() != Material.AIR) {

				player.sendMessage(bbo.getX() + "," + bbo.getY() + "," + bbo.getZ() + " = " + bbo.getType().name() + ":"
						+ bbo.getData());
				thereBlock = true;
				break;
			}

		}

		return thereBlock;
	}

	public boolean checkIsThatAreBlockOrNot(Block midBlockX0Z0, Player player) {
		int count = 0;
		boolean thereBlock = false;

		int search = 10;
		for (int x = -search; x <= search; x++) {
			for (int y = 40; y <= 256; y++) {

				for (int z = -search; z <= search; z++) {

					Block bbo = midBlockX0Z0.getWorld().getBlockAt(midBlockX0Z0.getX() + x, y, midBlockX0Z0.getZ() + z);

					if (bbo.getType() != Material.AIR) {

						player.sendMessage(bbo.getX() + "," + bbo.getY() + "," + bbo.getZ() + " = "
								+ bbo.getType().name() + ":" + bbo.getData());
						thereBlock = true;
						break;
					}
				}

				if (thereBlock == true) {
					break;
				}
			}

			if (thereBlock == true) {
				break;
			}

		}

		return thereBlock;
	}

	public void createSkyblockRS(Player player) {
		CreateSkyblockRS ab = new CreateSkyblockRS(player);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	public Block getBlockMiddleRS(int rsID) {

		Block b = Bukkit.getWorld("world").getBlockAt(rs[rsID].x, rs[rsID].y, rs[rsID].z);

		return b;
	}

	public int getOWNIslandID(String player, boolean rebuild) {
		// loop for check exits rs id and return or not build new one

		// find
		for (int lop = 0; lop < rsMax; lop++) {
			if (rs[lop].p[0].equalsIgnoreCase(player)) {
				return lop;
			}
		}

		if (rebuild == false) {
			return -1;
		}
		// build new one

		rsMax++;
		rs[rsMax - 1].p = new String[RSMaxPlayer];
		for (int gg = 0; gg < RSMaxPlayer; gg++) {
			rs[rsMax - 1].p[gg] = new String();
			rs[rsMax - 1].p[gg] = "null";
		}

		return rsMax - 1; // rsmax -1 ; (
	}

	public void giveItemToAllPlayerInRS(int rsID, ItemStack itm) {
		for (int i = 0; i < RSMaxPlayer; i++) {
			Player player = Bukkit.getPlayer(rs[rsID].p[i]);

			if (player == null) {
				continue;
			}

			player.getInventory().addItem(itm);
		}

	}

	public String ItemStackToStringWithTypeIDAndData(ItemStack itm) {
		String oo = "";

		if (itm == null) {

		} else {
			oo = itm.getType().name() + ":" + itm.getData().getData() + ":" + itm.getType().getMaxStackSize() + ":"
					+ itm.getType().isBlock();
		}

		return oo;
	}

	class drawProtectLine implements Runnable {

		private Block block;
		private Player player;
		private boolean drawOrDelete = false;

		public drawProtectLine(Block block, Player player, boolean drawOrDelete) {
			this.block = block;
			this.player = player;
			this.drawOrDelete = drawOrDelete;
		}

		@Override
		public void run() {

			int getid = api_skyblock.getProtectid(block);
			if (getid == -1) {
				player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_any_protect")));
				return;
			}

			// start draw Line

			int lx = api_skyblock.rs[getid].x - 150;
			int rx = api_skyblock.rs[getid].x + 149;

			int lz = api_skyblock.rs[getid].z - 150;
			int rz = api_skyblock.rs[getid].z + 149;

			for (int x = lx; x <= rx; x++) {

				for (int z = lz; z <= rz; z++) {

					for (int y = 0; y < 256; y += 64) {

						if ((z > lz && z < rz) && (x > lx && x < rx)) {
							continue;
						}

						Block cc = block.getWorld().getBlockAt(x, y, z);
						if (drawOrDelete == true) {
							if (cc.getType() == Material.AIR) {
								cc.setType(player.getItemInHand().getType());

							}
						} else {
							if (cc.getType() == player.getItemInHand().getType()) {
								cc.setType(Material.AIR);

							}

						}
					}
				}

			}

		}

	}

	public int[] recusiveSearchItemInChest(Block chestpls, String[] stringItemStack, Block[] bBlock,
			int[] stringSizeAndBlockSize) {
		// add

		Block tmp = null;
		int searchSpace = 10;
		for (int x = -searchSpace; x <= searchSpace; x++) {
			for (int y = -searchSpace; y <= searchSpace; y++) {
				for (int z = -searchSpace; z <= searchSpace; z++) {
					tmp = chestpls.getRelative(x, y, z);
					if (tmp.getType() == Material.CHEST) {
						// open it
						Chest chest = (Chest) tmp.getState();

						boolean searchChest = false;
						for (int i = 0; i < stringSizeAndBlockSize[1]; i++) {
							if (tmp.getLocation().getBlockX() == bBlock[i].getX()) {
								if (tmp.getLocation().getBlockY() == bBlock[i].getY()) {
									if (tmp.getLocation().getBlockZ() == bBlock[i].getZ()) {
										searchChest = true;
										break;
									}
								}
							}
						}
						if (searchChest == true) {
							continue;
						}

						bBlock[stringSizeAndBlockSize[1]] = tmp;
						stringSizeAndBlockSize[1]++;

						dprint.r.printAll("found chest " + tmp.getX() + "," + tmp.getY() + "," + tmp.getZ());

						// loop itemstack
						for (ItemStack itm : chest.getInventory().getContents()) {
							if (itm == null) {
								continue;
							}

							String curItm = ItemStackToStringWithTypeIDAndData(itm);

							boolean foundx = false;
							for (int ii = 0; ii < stringSizeAndBlockSize[0]; ii++) {
								// dprint.r.printAll("curItm = " + curItm + " ,
								// " + stringSizeAndBlockSize[0] + "/" +
								// stringSizeAndBlockSize[1]);

								if (curItm.equalsIgnoreCase(stringItemStack[ii])) {
									foundx = true;
									break;
								}
							}

							if (foundx == false) {
								stringItemStack[stringSizeAndBlockSize[0]] = curItm;
								stringSizeAndBlockSize[0]++;

							}
						}

						// call recursive

						int returner[] = recusiveSearchItemInChest(tmp, stringItemStack, bBlock,
								stringSizeAndBlockSize);
						stringSizeAndBlockSize[0] = returner[0];
						stringSizeAndBlockSize[1] = returner[1];
					} // chest
				}

			}

		}
		return stringSizeAndBlockSize;
	}

	public void commandRuning(String m[], Player player) {

		if (m[0].equalsIgnoreCase("/skyblock") || m[0].equalsIgnoreCase("/sky")) {

			if (api_skyblock.isrunworld(player.getLocation().getWorld().getName()) == false) {
				player.sendMessage(dprint.r.color(tr.gettr("this_world_is_not_skyblock")));
				return;
			}

			if (m.length == 1) {
				player.sendMessage(dprint.r.color("/skyblock new"));
				player.sendMessage(dprint.r.color("/skyblock home [name]"));
				player.sendMessage(dprint.r.color("/skyblock add <player>"));
				player.sendMessage(dprint.r.color("/skyblock remove <player>"));
				player.sendMessage(dprint.r.color("/skyblock list"));
				player.sendMessage(dprint.r.color("/skyblock owner <player>"));
				player.sendMessage(dprint.r.color("/skyblock exitFromThisHome <owner name>"));

				player.sendMessage(dprint.r.color("/skyblock c"));
				player.sendMessage(dprint.r.color("/skyblock go <player>"));
				player.sendMessage(dprint.r.color("/skyblock goid <id>"));
				player.sendMessage(dprint.r.color("/skyblock gorandom"));
				player.sendMessage(dprint.r.color("/skyblock flag"));

				player.sendMessage(dprint.r.color("/skyblock delete"));
				player.sendMessage(dprint.r.color("/skyblock buyhere"));

				player.sendMessage(dprint.r.color("/skyblock max"));

				return;

			} else if (m.length == 2 || m.length == 3)
				if (m[1].equalsIgnoreCase("max"))
					player.sendMessage(dprint.r.color(tr.gettr("amount_of_unique_island_is") + api_skyblock.rsMax));
				else if (m[1].equalsIgnoreCase("generatelistBlock")) {

					Block chestpls = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
					if (chestpls.getType() != Material.CHEST) {

						player.sendMessage("/skyblock generatelistBlock you need to stand on chest");
						return;

					}

					String stringItemStack[] = new String[1000];
					Block bBlock[] = new Block[1000]; // chest
					int stringSizeAndBlockSize[] = new int[2];
					stringSizeAndBlockSize[0] = 0;
					stringSizeAndBlockSize[1] = 0;

					int aabbout[] = recusiveSearchItemInChest(chestpls, stringItemStack, bBlock,
							stringSizeAndBlockSize);

					File dir = new File(Constant.folder_name);
					dir.mkdir();

					String filena = Constant.folder_name + File.separator + Constant.rsGenerateListBlock_filename;
					File fff = new File(filena);

					FileWriter fwriter;

					try {
						fff.createNewFile();

						dprint.r.printC("ptdew&dewdd:Start saving " + filena);

						fwriter = new FileWriter(fff);

						for (int i = 0; i < aabbout[0]; i++) {
							dprint.r.printAll("i = " + i + " = " + stringItemStack[i]);
							fwriter.write(stringItemStack[i] + System.getProperty("line.separator"));
						}

						fwriter.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (m[1].equalsIgnoreCase("adjustprotect")) {
					if (player.hasPermission(Constant.poveride) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_dont_have_permission")));
						return;
					}
					adjustProtect(player.getLocation().getBlock(), player);
				} else if (m[1].equalsIgnoreCase("smallIsland")) {
					if (player.hasPermission(Constant.poveride) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_dont_have_permission")));
						return;
					}

					long delay = 20;
					for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {
						Block blo = player.getWorld().getBlockAt(api_skyblock.rs[lop2].x, api_skyblock.rs[lop2].y,
								api_skyblock.rs[lop2].z);

						delay += 200;
						addSmallIslandNearThisBlock(blo, delay);
					}

				} else if (m[1].equalsIgnoreCase("deleteybelow5")) {
					if (player.hasPermission(Constant.poveride) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_dont_have_permission")));
						return;
					}

					HashMap<String, Location> bd = new HashMap<String, Location>();

					DeleteYBelow5 deleteYBelow5 = new DeleteYBelow5(bd, player.getWorld(), Integer.parseInt(m[2]));
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, deleteYBelow5, 1);

				} else if (m[1].equalsIgnoreCase("adjustprotect2")) {
					if (player.hasPermission(Constant.poveride) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_dont_have_permission")));
						return;
					}
					adjustProtect2(player.getLocation().getBlock(), player);

				} else if (m[1].equalsIgnoreCase("position")) {
					LXRXLZRZType ee = api_skyblock.getPositionLXRXLZRZ();

					player.sendMessage("xy " + ee.lx + "," + ee.lz + " to " + ee.rx + "," + ee.rz);
				} else if (m[1].equalsIgnoreCase("flag")) {
					player.sendMessage(
							dprint.r.color(tr.gettr("skyblock add these flag to your zone to activate something")));

					player.sendMessage(dprint.r.color("/sky <add> <flag>"));
					player.sendMessage(dprint.r.color("/sky <remove> <flag>"));

					tr.showFlagToPlayer(player);

				} else if (m[1].equalsIgnoreCase("buyhere")) {
					// for buy these zone

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());
					if (getid > -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_someone_bought_it")));
						return;
					}

					// buy this zone

					if (getOWNIslandID(player.getName(), false) > -1) {
						player.sendMessage(dprint.r.color(tr.gettr("you_can_buyhere_cuz_you_have_own_island")));
						player.sendMessage(dprint.r
								.color(tr.gettr("if_you_need_to_buyhere_this_zone_delete_own_island_first_type_here"
										+ " /sky delete")));
						return;
					}

					api_skyblock.rsMax++;
					api_skyblock.rs[api_skyblock.rsMax - 1].p = new String[api_skyblock.RSMaxPlayer];
					for (int lop = 0; lop < api_skyblock.RSMaxPlayer; lop++)
						api_skyblock.rs[api_skyblock.rsMax - 1].p[lop] = "null";

					api_skyblock.rs[api_skyblock.rsMax - 1].p[0] = player.getName();
					// time to find x and z

					api_skyblock.rs[api_skyblock.rsMax - 1].y = player.getLocation().getBlockY();

					// x

					boolean found = false;

					do {
						found = false;
						int tmpX = rnd.nextInt(100) * 300 * (rnd.nextInt(2) == 0 ? -1 : 1);
						int tmpZ = rnd.nextInt(100) * 300 * (rnd.nextInt(2) == 0 ? -1 : 1);
						Block block = player.getLocation().getBlock();

						if (block.getX() >= (tmpX - 150) && block.getX() <= (tmpX + 149) && block.getZ() >= (tmpZ - 150)
								&& block.getZ() <= (tmpZ + 149)) {

							api_skyblock.rs[api_skyblock.rsMax - 1].x = tmpX;
							api_skyblock.rs[api_skyblock.rsMax - 1].z = tmpZ;

							found = true;
							break;
						}

					} while (found == false);

					// z

					//
					player.sendMessage(dprint.r
							.color("ptdew&dewdd : bought island at (" + api_skyblock.rs[api_skyblock.rsMax - 1].x + ","
									+ api_skyblock.rs[api_skyblock.rsMax - 1].y + ","
									+ api_skyblock.rs[api_skyblock.rsMax - 1].z + ") host is "
									+ api_skyblock.rs[api_skyblock.rsMax - 1].p[0]));

					saveRSProtectFile();
					return;
				} else if (m[1].equalsIgnoreCase("drawprotect")) {

					if (player.hasPermission(Constant.poveride) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_dont_have_permisison")));
						return;
					}

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_protect")));
						return;

					}

					if (m.length != 3) {
						player.sendMessage(dprint.r.color("/skyblock drawprotect true/false"));
						return;
					}

					drawProtectLine dp = new drawProtectLine(player.getLocation().getBlock(), player,
							Boolean.parseBoolean(m[2]));

					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, dp);
				}

				else if (m[1].equalsIgnoreCase("resetc")) {
					if (player.hasPermission(Constant.presetc) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_don't_have_permission") + Constant.pskyblock));
						return;
					}

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_protect")));
						return;

					}

					if (m.length == 2) {

						api_skyblock.rs[getid].mission = 0;
						dprint.r.printAll(tr.gettr("reseted_c_of_is_this_guys") + api_skyblock.rs[getid].p[0]);

						printToAllPlayerOnRS(getid, tr.gettr("cur_c_is") + (api_skyblock.rs[getid].mission));

					} else if (m.length == 3) {
						api_skyblock.rs[getid].mission = (Integer.parseInt(m[2]));
						dprint.r.printAll(tr.gettr("reseted_c_of_is_this_guys") + api_skyblock.rs[getid].p[0]);

						printToAllPlayerOnRS(getid, tr.gettr("cur_c_is") + (api_skyblock.rs[getid].mission));

					} else {
						player.sendMessage(tr.gettr("/sky resetlv <lv>"));
						return;
					}

				} else if (m[1].equalsIgnoreCase("go")) {
					// go

					if (m.length != 3) {
						player.sendMessage(dprint.r.color("need 3 arguments   /skyblock go <player>"));

						return;
					}

					int counttruename = 0;
					int nearname = 0;

					for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {

						if (api_skyblock.rs[lop2].p[0].equalsIgnoreCase(m[2])) {
							counttruename++;
						}

						if (api_skyblock.rs[lop2].p[0].toLowerCase().indexOf(m[2].toLowerCase()) > -1) {

							// player.sendMessage(dprint.r.color("" +
							// api_skyblock.rs[lop2].p[0]));
							nearname++;

						}

					}

					if (nearname >= 1) {
						player.sendMessage(dprint.r.color(tr.gettr("list of sky protect name like your search name")));

						for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {

							if (api_skyblock.rs[lop2].p[0].toLowerCase().indexOf(m[2].toLowerCase()) > -1) {

								player.sendMessage(dprint.r.color("" + api_skyblock.rs[lop2].p[0]));

							}

						}

					}

					for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {

						if (api_skyblock.rs[lop2].p[0].equalsIgnoreCase(m[2])) {
							Block block = player.getWorld().getBlockAt(api_skyblock.rs[lop2].x,
									api_skyblock.rs[lop2].y + 10, api_skyblock.rs[lop2].z);
							block.getChunk().load();
							player.teleport(block.getLocation());
							player.sendMessage(dprint.r.color("teleported you to (" + block.getX() + "," + block.getY()
									+ "," + block.getZ() + ") of " + api_skyblock.rs[lop2].p[0]));

							return;
						}

					}

				} else if (m[1].equalsIgnoreCase("goid")) {
					// go

					if (m.length != 3) {
						player.sendMessage(dprint.r.color("/sky goid <id>"));
						player.sendMessage(dprint.r.color("/sky goid <0 to " + api_skyblock.rsMax + ">"));

						return;
					}

					int idid = Integer.parseInt(m[2]);
					if (idid < 0 || idid >= api_skyblock.rsMax) {
						player.sendMessage(dprint.r.color("/sky goid <0 to " + api_skyblock.rsMax + ">"));

						return;
					}

					Block block = player.getWorld().getBlockAt(api_skyblock.rs[idid].x, api_skyblock.rs[idid].y + 10,
							api_skyblock.rs[idid].z);
					block.getChunk().load();

					Block thetop = Useful.getTopBlockHigh(block);
					if (thetop == null) {

						block.setType(Material.GLASS);
						player.sendMessage(dprint.r.color(
								tr.gettr("can't warp to this sky island cuz there are no middle block to stand")));
						// return;
						thetop = Useful.getTopBlockHigh(block);
					}

					player.teleport(thetop.getLocation());

					player.sendMessage(dprint.r.color("teleported you to (" + block.getX() + "," + block.getY() + ","
							+ block.getZ() + ") of " + api_skyblock.rs[idid].p[0]));
					return;
				} else if (m[1].equalsIgnoreCase("gorandom")) {
					// go

					int idid = rnd.nextInt(api_skyblock.rsMax);

					Block block = player.getWorld().getBlockAt(api_skyblock.rs[idid].x, api_skyblock.rs[idid].y + 10,
							api_skyblock.rs[idid].z);
					block.getChunk().load();

					Block thetop = Useful.getTopBlockHigh(block);
					if (thetop == null) {

						block.setType(Material.GLASS);
						player.sendMessage(dprint.r.color(
								tr.gettr("can't warp to this sky island cuz there are no middle block to stand")));
						// return;
						thetop = Useful.getTopBlockHigh(block);
					}

					player.teleport(thetop.getLocation());

					player.sendMessage(dprint.r.color("teleported you to (" + block.getX() + "," + block.getY() + ","
							+ block.getZ() + ") of " + api_skyblock.rs[idid].p[0]));
					return;
				}

				else if (m[1].equalsIgnoreCase("home")) {
					if (m.length != 3) {
						player.sendMessage(dprint.r.color("need 3 arguments   /skyblock home <player>"));

						for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {
							if (api_skyblock.getplayerinslot(player.getName(), lop2) == -1) {
								continue;
							}

							player.sendMessage(dprint.r.color("/skyblock home " + api_skyblock.rs[lop2].p[0]));

						}

						return;
					}

					int counttruename = 0;
					int nearname = 0;

					int homehashim = 0;

					for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {
						if (api_skyblock.getplayerinslot(player.getName(), lop2) == -1) {
							continue;
						}

						homehashim++;

						if (api_skyblock.rs[lop2].p[0].equalsIgnoreCase(m[2])) {
							counttruename++;
						}

						if (api_skyblock.rs[lop2].p[0].toLowerCase().indexOf(m[2].toLowerCase()) > -1) {

							// player.sendMessage(dprint.r.color("" +
							// api_skyblock.rs[lop2].p[0]));
							nearname++;

						}

					}

					if (homehashim == 0) {
						player.sendMessage(
								dprint.r.color(tr.gettr("you_don't_have_any_skyblock_create_it_or_join_friend")));
						return;

					}

					if (counttruename == 1 || nearname == 1) {
						for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {
							if (api_skyblock.getplayerinslot(player.getName(), lop2) == -1) {
								continue;
							}

							if (((api_skyblock.rs[lop2].p[0].equalsIgnoreCase(m[2])) && counttruename == 1)
									|| (api_skyblock.rs[lop2].p[0].toLowerCase().indexOf(m[2].toLowerCase()) > -1)
											&& counttruename != 1) {
								Block block = player.getWorld().getBlockAt(api_skyblock.rs[lop2].x,
										api_skyblock.rs[lop2].y + 10, api_skyblock.rs[lop2].z);
								block.getChunk().load();
								Block thetop = Useful.getTopBlockHigh(block);
								if (thetop == null) {

									block.setType(Material.GLASS);
									player.sendMessage(dprint.r.color(tr.gettr(
											"can't warp to this sky island cuz there are no middle block to stand")));
									// return;
									thetop = Useful.getTopBlockHigh(block);
								}
								player.teleport(thetop.getLocation());
								player.sendMessage(dprint.r.color("teleported you to (" + block.getX() + ","
										+ block.getY() + "," + block.getZ() + ") of " + api_skyblock.rs[lop2].p[0]));

								return;
							}

						}

					}

					if (nearname > 1) {
						player.sendMessage(dprint.r.color(tr.gettr("list of sky protect name like your search name")));

						for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {
							if (api_skyblock.getplayerinslot(player.getName(), lop2) == -1) {
								continue;
							}

							if (api_skyblock.rs[lop2].p[0].toLowerCase().indexOf(m[2].toLowerCase()) > -1) {

								player.sendMessage(dprint.r.color("/skyblock home " + api_skyblock.rs[lop2].p[0]));

							}

						}

						return;
					}

					if (nearname == 0) {
						player.sendMessage(dprint.r
								.color(tr.gettr("you_have_more_than_1_home_so_choose_it") + " /skyblock home <name>"));

						for (int lop2 = 0; lop2 < api_skyblock.rsMax; lop2++) {
							if (api_skyblock.getplayerinslot(player.getName(), lop2) == -1) {
								continue;
							}

							player.sendMessage(dprint.r.color("/skyblock home " + api_skyblock.rs[lop2].p[0]));

						}

						return;

					}

					player.sendMessage(dprint.r.color(tr.gettr("not_found_skyblock_of") + m[2]));

				}

				else if (m[1].equalsIgnoreCase("c")) {

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_protect")));
						return;

					}

					if (api_skyblock.getplayerinslot(player.getName(), getid) == -1) {
						player.sendMessage(dprint.r
								.color(tr.gettr("owner_of_this_island_name") + " = " + api_skyblock.rs[getid].p[0]));
					}

					if (api_skyblock.rs[getid].mission >= api_skyblock.lv1000.size()) {
						player.sendMessage(dprint.r.color(tr.gettr("sky_c_all_c_done_thanks_to_play")));
						return;

					}

					player.sendMessage(dprint.r.color(tr.gettr("cur_c_is") + (api_skyblock.rs[getid].mission)));
					printToAllPlayerOnRS(getid, (tr.gettr("cur_c_is") + api_skyblock.rs[getid].mission));

					Inventory inv = Bukkit.createInventory(null, 54, sky_c_inv_name);
					updateLVInventory(inv, player);

					player.openInventory(inv);

				}

				else if (m[1].equalsIgnoreCase("owner")) {

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_protect")));
						return;
					} else {
						// check host
						boolean ch = api_skyblock.rs[getid].p[0].equalsIgnoreCase(player.getName());

						if (ch == false)
							if (player.hasPermission(Constant.poveride) == false) {
								player.sendMessage(dprint.r.color(
										tr.gettr("host_is") + api_skyblock.rs[getid].p[0] + tr.gettr("not_you!")));

								return;
							} else
								player.sendMessage(dprint.r.color(tr.gettr("overide_this_zone")));

						if (m.length != 3) {
							player.sendMessage(dprint.r.color("/skyblock owner <playername>"));
							return;
						}

						// check that player online

						// check if he already has own protect

						int getido = getOWNIslandID(m[2], false);
						if (getido != -1) {
							player.sendMessage(dprint.r.color(tr.gettr("can't_give_owner_to_this_guy") + " " + m[2]
									+ " " + tr.gettr("cuz_he_already_owner_another_island")));
							return;

						}
						api_skyblock.rs[getid].p[0] = m[2];

						player.sendMessage(
								dprint.r.color(tr.gettr("this_skyblock_owner_is") + api_skyblock.rs[getid].p[0]));
						saveRSProtectFile();
						return;

					}
				}

				else if (m[1].equalsIgnoreCase("exitFromThisHome")) {

					if (m.length != 3) {
						player.sendMessage(dprint.r.color(
								tr.gettr("not enought argument type this") + "/sky exitfromthishome <owner name>"));
						return;
					}

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_any_protect")));
						return;
					} else {
						// check host
						int doesIInThisHome = api_skyblock.getplayerinslot(player.getName(), getid);

						if (doesIInThisHome == -1) {
							player.sendMessage(dprint.r.color(tr.gettr("don't have your name on this protect")));
							return;
						}

						// ....

						boolean ch = api_skyblock.rs[getid].p[0].equalsIgnoreCase(player.getName());

						if (ch == true) {
							player.sendMessage(dprint.r.color(tr.gettr(
									"you_can't_exit_from_your_own_protect_if_you_want_try_another command such as delete or owner")));
							return;

						}

						// ....
						// remove my name
						api_skyblock.rs[getid].p[doesIInThisHome] = "null";
						saveRSProtectFile();

						player.sendMessage(dprint.r.color(tr.gettr("you exited from sky protect of ")
								+ api_skyblock.rs[getid].p[0] + " id " + getid));

					}

				} else if (m[1].equalsIgnoreCase("add")) {

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_any_protect")));
						return;
					} else {
						// check host
						boolean ch = api_skyblock.rs[getid].p[0].equalsIgnoreCase(player.getName());

						if (ch == false)
							if (player.hasPermission(Constant.poveride) == false) {
								player.sendMessage(dprint.r.color(
										tr.gettr("host_is") + api_skyblock.rs[getid].p[0] + tr.gettr("not_you")));

								return;
							} else
								player.sendMessage(dprint.r.color(tr.gettr("overide_this_zone")));

					}

					if (m.length != 3) {
						player.sendMessage(dprint.r.color("/skyblock add <playername>"));
						return;
					}
					// if found his skyblock teleport him

					// check free slot

					for (int i = 1; i < api_skyblock.RSMaxPlayer; i++)
						if (api_skyblock.rs[getid].p[i].equalsIgnoreCase(m[2])) {
							player.sendMessage(dprint.r.color(tr.gettr("already_added_this_name_so_not_work")));
							return;
						}

					for (int i = 1; i < api_skyblock.RSMaxPlayer; i++)
						if (api_skyblock.rs[getid].p[i].equalsIgnoreCase("null")) {
							api_skyblock.rs[getid].p[i] = m[2];
							player.sendMessage(dprint.r.color(tr.gettr("added") + m[2] + tr.gettr("to_your_skyblock")));
							saveRSProtectFile();
							return;
						}

				} else if (m[1].equalsIgnoreCase("remove")) {

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_protect")));
						return;
					} else {
						// check host
						boolean ch = api_skyblock.rs[getid].p[0].equalsIgnoreCase(player.getName());

						if (ch == false)
							if (player.hasPermission(Constant.poveride) == false) {
								player.sendMessage(dprint.r.color(
										tr.gettr("host_is") + api_skyblock.rs[getid].p[0] + tr.gettr("not_you")));
								return;
							} else
								player.sendMessage(dprint.r.color(tr.gettr("overide_this_zone")));

					}

					if (m.length != 3) {
						player.sendMessage(dprint.r.color("/skyblock remove <playername>"));
						return;
					}

					if (m[2].equalsIgnoreCase(player.getName())) {
						player.sendMessage(dprint.r.color(tr.gettr("you_can't_remove_your_name_from_your_zone")));
						return;
					}
					// if found his skyblock teleport him

					// check free slot

					for (int i = 1; i < api_skyblock.RSMaxPlayer; i++)
						if (api_skyblock.rs[getid].p[i].equalsIgnoreCase(m[2])) {
							api_skyblock.rs[getid].p[i] = "null";
							player.sendMessage(
									dprint.r.color(tr.gettr("removed") + m[2] + tr.gettr("from_your_skyblock")));
							saveRSProtectFile();
							return;
						}

					player.sendMessage(dprint.r.color(tr.gettr("this_protected_don't_have_this_name") + m[2]));
					return;
				} else if (m[1].equalsIgnoreCase("list")) {

					int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr.gettr("this_zone_don't_have_any_protect")));
						return;
					}

					player.sendMessage(
							dprint.r.color("protect id ") + getid + " , position (" + api_skyblock.rs[getid].x + ","
									+ api_skyblock.rs[getid].y + "," + api_skyblock.rs[getid].z + ")");

					for (int i = 0; i < api_skyblock.RSMaxPlayer; i++)
						if (api_skyblock.rs[getid].p[i].equalsIgnoreCase("null") == false)
							player.sendMessage(dprint.r.color("Member " + i + " = " + api_skyblock.rs[getid].p[i]));

				} else if (m[1].equalsIgnoreCase("new")) { // new

					if (player.hasPermission(Constant.pskyblock) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_don't_have_permission") + Constant.pskyblock));
						return;
					}

					// check item on player

					boolean hav = false;

					for (ItemStack itm : player.getInventory().getContents()) {
						if (itm != null) {
							
							if (itm.getType() == Material.CHEST) {
								continue;
							}
							
							hav = true;
							break;
						}
					}

					if (hav == true) {
						player.sendMessage(dprint.r.color(tr.gettr("can_not_skynew_if_have_item")));
						return;
					}

					player.sendMessage(dprint.r.color(tr.gettr("this_is_hardcore_skyblock")));

					createSkyblockRS(player);

					return;

				} else if (m[1].equalsIgnoreCase("delete")) { // new

					if (player.hasPermission(Constant.pskyblock) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you_don't_have_permission") + Constant.pskyblock));
						return;
					}

					// check item on player

					int getido = api_skyblock.getProtectid(player.getLocation().getBlock());
					if (getido == -1) {
						player.sendMessage(
								dprint.r.color(tr.gettr("this_zone_don't_have_any_protect_so_can't_delete")));
						return;
					}

					if (api_skyblock.rs[getido].p[0].equalsIgnoreCase(player.getName()) == false) {

						if (player.hasPermission(Constant.poveride) == false) {
							player.sendMessage(dprint.r.color(tr.gettr("you_can't_deleteisland_cuz_owner_here_is") + " "
									+ api_skyblock.rs[getido].p[0]));
							return;

						} else {
							player.sendMessage(
									dprint.r.color(tr.gettr("override_to_delete_this_island_protect_owner_is") + " "
											+ api_skyblock.rs[getido].p[0]));
						}

					}

					boolean hav = false;

					for (ItemStack itm : player.getInventory().getContents()) {
						if (itm != null) {
							hav = true;
							break;
						}
					}

					if (hav == true) {
						player.sendMessage(dprint.r.color(tr.gettr("can_not_deleteskyprotect_if_have_item")));
						return;
					}

					api_skyblock.rs[getido].p[0] = api_skyblock.getNewOwnerName();
					saveRSProtectFile();

					return;

				} else if (m[1].equalsIgnoreCase("reload") == true) {

					loadRSProtectFile();
					loadLVFile();
				}
		}
	}

	public void updateLVInventory(Inventory inv, Player player) {
		int idx = api_skyblock.getProtectid(player.getLocation().getBlock());
		int id = api_skyblock.getplayerinslot(player.getName(), idx);

		if (id == -1) {
			return;
		}

		// add item

		inv.clear();
		LV1000Type lv = api_skyblock.lv1000.get(api_skyblock.rs[idx].mission);

		int curSlot = 0;
		for (int i = 0; i < lv.needSize; i++) {

			ItemStack itm = new ItemStack(lv.getMaterial(lv.needNameData[i]),

					lv.needAmount[i], lv.getData(lv.needNameData[i]));

			//itm.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1);

			ItemMeta mm = itm.getItemMeta();
			mm.setDisplayName(lv.needNameData[i] + " NEED");

			itm.setItemMeta(mm);

			inv.setItem(curSlot, itm);
			curSlot++;
		}

		curSlot = 53;

		for (int i = 0; i < lv.rewardSize; i++) {

			ItemStack itm = new ItemStack(lv.getMaterial(lv.rewardNameData[i]),

					lv.rewardAmount[i], lv.getData(lv.rewardNameData[i]));

			itm.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);

			ItemMeta mm = itm.getItemMeta();
			mm.setDisplayName(lv.rewardNameData[i] + " as reward");

			itm.setItemMeta(mm);

			inv.setItem(curSlot, itm);
			curSlot--;
		}

		// update inventory

	}

	class DeleteYBelow5 implements Runnable {
		private HashMap<String, Location> bd;
		private World world;

		public DeleteYBelow5(HashMap<String, Location> bd, World world, int firstAdded) {
			this.bd = bd;
			this.world = Bukkit.getWorld("world");

			// random add

		}

		@Override
		public void run() {

			if (Bukkit.getOnlinePlayers().size() != 1) {
				return;
			}

			int search = 10;

			long startTime = System.currentTimeMillis();

			// dprint.r.printAll("start " + bd.size());

			if (bd.size() == 0) {
				bd = null;
				bd = new HashMap<String, Location>();
				// dprint.r.printAll("run () bd.size = " + bd.size());

				LXRXLZRZType ee = api_skyblock.getPositionLXRXLZRZ();

				while (System.currentTimeMillis() - startTime < 1000) {

					int x = li.Useful.randomInteger(ee.lx, ee.rx);
					int z = li.Useful.randomInteger(ee.lz, ee.rz);

					int y = li.Useful.randomInteger(0, 40);

					Block block = world.getBlockAt(x, y, z);
					if (block.getType() != Material.AIR) {
						bd.put(tr.locationToString(block.getLocation()), block.getLocation());
						dprint.r.printAll("deleteybelow5 > first add > " + block.getX() + "," + block.getY() + ","
								+ block.getZ() + " size " + bd.size());

					}

				}

				if (bd.size() == 0) {

					dprint.r.printAll("recall ... " + bd.size());
					DeleteYBelow5 newRun = new DeleteYBelow5(bd, world, 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, newRun, 20L);
					return;
				}

			}

			startTime = System.currentTimeMillis();

			int first = 0;
			while (bd.size() > 0 && System.currentTimeMillis() - startTime < 1000) {

				for (int i = 0; i < 1000000 && System.currentTimeMillis() - startTime < 1000; i++) {
					String forDeleteLoc = "";
					for (String locStr : bd.keySet()) {

						Location loc = bd.get(locStr);
						// bd.remove(locStr);
						forDeleteLoc = locStr;

						Block getStack = world.getBlockAt(loc);
						if (getStack == null) {
							continue;
						}

						if (getStack.getType() == Material.AIR) {
							continue;
						}

						if (first == 0) {
							dprint.r.printAll("deleteybelow5 > break > " + getStack.getX() + "," + getStack.getY() + ","
									+ getStack.getZ() + " " + getStack.getType().name() + ":" + getStack.getData()
									+ " size " + bd.size());
							first = 1;
						}

						getStack.breakNaturally();
						getStack.setType(Material.AIR);

						for (int x = -search; x <= search; x++)
							for (int y = 0; y <= 40; y++)
								for (int z = -search; z <= search; z++) {
									if (x == 0 && y == 0 && z == 0) {
										continue;
									}

									Block bo = getStack.getWorld().getBlockAt(getStack.getX() + x, y,
											getStack.getZ() + z);
									if (bo.getType() != Material.AIR) {
										bd.put(tr.locationToString(bo.getLocation()), bo.getLocation());

									}

								}

						for (int x = -50; x <= 50; x++) {

							Block bo = getStack.getRelative(x, 0, 0);
							if (bo.getType() != Material.AIR) {
								bd.put(tr.locationToString(bo.getLocation()), bo.getLocation());

							}
						}

						for (int x = -50; x <= 50; x++) {

							Block bo = getStack.getRelative(0, 0, x);
							if (bo.getType() != Material.AIR) {
								bd.put(tr.locationToString(bo.getLocation()), bo.getLocation());

							}
						}

						break;
					}

					bd.remove(forDeleteLoc);

				}

			}

			if (bd.size() >= 0) {
				DeleteYBelow5 newRun = new DeleteYBelow5(bd, world, 0);
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, newRun, 20L);
				return;
			}

		}

	}

	public void loadLVFile() {
		String worldf = Constant.rsLV_filename;

		File dir = new File(Constant.folder_name);
		dir.mkdir();

		String filena = Constant.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			lv1000.clear();

			fff.createNewFile();

			System.out.println("ptdeW&DewDD skyblock , lv loading : " + filena);
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
				LV1000Type lvo = new LV1000Type();

				m = strLine.split("\\s+");

				lvo.needSize = Integer.parseInt(m[0]);
				lvo.rewardSize = Integer.parseInt(m[1]);

				for (int i = 0; i < lvo.needSize; i++) {
					strLine = br.readLine();

					String bem[] = strLine.split(" ");

					lvo.needNameData[i] = bem[0];
					lvo.needAmount[i] = Integer.parseInt(bem[1]);

				}

				for (int i = 0; i < lvo.rewardSize; i++) {
					strLine = br.readLine();

					String bem[] = strLine.split(" ");
					lvo.rewardNameData[i] = bem[0];
					lvo.rewardAmount[i] = Integer.parseInt(bem[1]);

				}

				lv1000.add(lvo);

			}

			System.out.println("ptdew&DewDD skyblock : lv loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error load " + filena + " " + e.getMessage());
		}
	}

	public static void loadRSProtectFile() {
		String worldf = Constant.rsProtect_filename;

		File dir = new File(Constant.folder_name);
		dir.mkdir();
		System.out.println(dir.getAbsolutePath());

		String filena = Constant.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			rs = new RSData[Constant.rsBuffer];
			for (int lop = 0; lop < Constant.rsBuffer; lop++) {
				rs[lop] = new RSData();
			}
			rsMax = 0;

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
				rsMax++;
				rs[rsMax - 1].x = Integer.parseInt(m[0]);
				rs[rsMax - 1].y = Integer.parseInt(m[1]);
				rs[rsMax - 1].z = Integer.parseInt(m[2]);
				rs[rsMax - 1].p = new String[RSMaxPlayer];

				for (int i = 0; i < RSMaxPlayer; i++) {
					rs[rsMax - 1].p[i] = m[i + 3];

				}

				if (m.length >= 24) {
					int bb = (int) Double.parseDouble(m[23]);

					// bb = 0;
					rs[rsMax - 1].mission = (bb);

				}
				if (m.length == 25) {
					long bb = Long.parseLong(m[24]);

					// bb = 0;
					rs[rsMax - 1].lastUsed = (bb);
				}

				// rs[rsMax - 1].mission = 0;

			}

			// rename All Duplicate owner
			for (int i = 0; i < rsMax; i++) {
				boolean fou = false;

				for (int j = 0; j < rsMax; j++) {
					if (j == i) {
						continue;
					}

					if (rs[i].p[0].equalsIgnoreCase(rs[j].p[0])) {
						fou = true;
						break;
					}
				}

				if (fou == true) { // duplicate
					// rename it
					Random rnd = new Random();
					String abc = "dupi" + rnd.nextInt(10000);

					// search

					boolean kfou = false;

					do {
						abc = "dupi" + rnd.nextInt(10000);
						kfou = false;

						for (int k = 0; k < rsMax; k++) {
							if (rs[k].p[0].equalsIgnoreCase(abc)) {
								kfou = true;
								break;
							}

						}

					} while (kfou == true);

					dprint.r.printAll("duplicate rs owner name > " + rs[i].p[0] + " renamed to " + abc);
					rs[i].p[0] = abc;
				}

			}

			dprint.r.printAll("ptdew&DewDD Skyblock: Loaded " + filena);

			for (int i = rsMax - 1; i >= 0; i--) {

				for (int j = 0; j < i; j++) {

					if (rs[i].x == rs[j].x && rs[i].z == rs[j].z) {
						dprint.r.printAll("duplicate x y on id " + j + " and " + i);
						continue;
					}
				}

			}

			in.close();
		} catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void loadRSProtectFile2() {
		String worldf = Constant.rsProtect_filename;

		File dir = new File(Constant.folder_name);
		dir.mkdir();
		System.out.println(dir.getAbsolutePath());

		String filena = Constant.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			rs = new RSData[Constant.rsBuffer];
			for (int lop = 0; lop < Constant.rsBuffer; lop++) {
				rs[lop] = new RSData();
			}
			rsMax = 0;

			fff.createNewFile();

			System.out.println("ptdeW&DewDD Skyblock : " + filena);
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
				rsMax++;
				rs[rsMax - 1].x = Integer.parseInt(m[0]);
				rs[rsMax - 1].y = Integer.parseInt(m[1]);
				rs[rsMax - 1].z = Integer.parseInt(m[2]);
				rs[rsMax - 1].p = new String[RSMaxPlayer];

				for (int i = 0; i < RSMaxPlayer; i++) {
					rs[rsMax - 1].p[i] = m[i + 3];

				}

				if (m.length >= 24) {
					int bb = (int) Double.parseDouble(m[23]);

					// bb = 0;
					rs[rsMax - 1].mission = (bb);

				}
				if (m.length == 25) {
					long bb = Long.parseLong(m[24]);

					// bb = 0;
					rs[rsMax - 1].lastUsed = (bb);
				}

				// rs[rsMax - 1].mission = 0;

			}

			// rename All Duplicate owner
			for (int i = 0; i < rsMax; i++) {
				boolean fou = false;

				for (int j = 0; j < rsMax; j++) {
					if (j == i) {
						continue;
					}

					if (rs[i].p[0].equalsIgnoreCase(rs[j].p[0])) {
						fou = true;
						break;
					}
				}

				if (fou == true) { // duplicate
					// rename it
					Random rnd = new Random();
					String abc = "dupi" + rnd.nextInt(10000);

					// search

					boolean kfou = false;

					do {
						abc = "dupi" + rnd.nextInt(10000);
						kfou = false;

						for (int k = 0; k < rsMax; k++) {
							if (rs[k].p[0].equalsIgnoreCase(abc)) {
								kfou = true;
								break;
							}

						}

					} while (kfou == true);

					System.out.println("duplicate rs owner name > " + rs[i].p[0] + " renamed to " + abc);
					rs[i].p[0] = abc;
				}

			}

			System.out.println("ptdew&DewDD Skyblock: Loaded " + filena);

			for (int i = rsMax - 1; i >= 0; i--) {

				for (int j = 0; j < i; j++) {

					if (rs[i].x == rs[j].x && rs[i].z == rs[j].z) {
						System.out.println("duplicate x y on id " + j + " and " + i);
						continue;
					}
				}

			}

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.out.println("Error load " + filena + e.getMessage());
			e.printStackTrace();
		}
	}

	public synchronized void nextMission(int rsID, int cur) {
		printToAllPlayerOnRS(rsID, ((rs[rsID].mission) + " " + tr.gettr("mission_complete")));

		// dprint.r.printAll("0 calling apply reward");
		applyReward(rsID);

		printToAllPlayerOnRS(rsID, tr.gettr("next_mission"));

		dprint.r.printC(tr.gettr("owner_of_island_name") + rs[rsID].p[0] + " " + tr.gettr("did_mission_complete") + " "
				+ (rs[rsID].mission));

		int tmpID = (cur);
		tmpID++;
		rs[rsID].mission = (tmpID);

		printToAllPlayerOnRS(rsID, ((rs[rsID].mission) + " ..."));

		saveRSProtectFile();

	}

	public void printToAllPlayerOnRS(int rsID, String message) {
		// dprint.r.printA("printToallplayeronrs " + rsID + " , " + message);

		for (int i = 0; i < RSMaxPlayer; i++) {

			Player player = Bukkit.getPlayer(rs[rsID].p[i]);

			if (player == null) {
				continue;
			}

			// dprint.r.printA("player " + i + " = " + player.getName());

			if (player.isOnline() == false) {
				continue;
			}

			// dprint.r.printA("online player " + i + " = " + player.getName());

			player.sendMessage(dprint.r.color(message));
		}
	}

	public int random16() {
		int g = rnd.nextInt(360);
		g -= 180;
		g = g * 16;
		return g;

		// 180
	}

	public void saveRSProtectFile() {

		File dir = new File(Constant.folder_name);
		dir.mkdir();

		String filena = Constant.folder_name + File.separator + Constant.rsProtect_filename;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < rsMax; y++) {
				if (rs[y].p[0].equalsIgnoreCase("")) {
					continue;
				}
				String wr = "";
				wr = rs[y].x + " " + rs[y].y + " " + rs[y].z + " ";

				for (int y2 = 0; y2 < RSMaxPlayer; y2++) {
					wr = wr + rs[y].p[y2];

					if (y2 != RSMaxPlayer)
						wr = wr + " ";

				}

				wr = wr + " " + rs[y].mission;
				wr = wr + " " + rs[y].lastUsed;

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			dprint.r.printC("ptdew&dewdd:saved " + filena);
			return;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Block searchSpaceCube(Block startPoint, int radiusCubeNeed, int rad) {
		int x = startPoint.getX();
		int y = startPoint.getY();
		int z = startPoint.getZ();

		// rad = 100;

		boolean foundx = false;
		Block b;

		int timeSearch = 0;
		do {

			timeSearch++;
			if (timeSearch > 100) {
				rad++;
			}

			int x2 = x + (rnd.nextInt(rad * 2) - rad);
			int y2 = y + (rnd.nextInt(rad * 2) - rad);
			int z2 = z + (rnd.nextInt(rad * 2) - rad);

			b = startPoint.getWorld().getBlockAt(x2, y2, z2);
			foundx = true;
			for (int x3 = x2; x3 <= x2 + radiusCubeNeed; x3++) {
				for (int y3 = y2; y3 <= y2 + radiusCubeNeed; y3++) {
					for (int z3 = z2; z3 <= z2 + radiusCubeNeed; z3++) {
						Block bSpace = b.getWorld().getBlockAt(x3, y3, z3);
						if (bSpace.getType() != Material.AIR) {
							foundx = false;
							break;
						}

					}

					if (foundx == false) {
						break;
					}

				}

				if (foundx == false) {
					break;
				}

			}

		} while (foundx == false);

		return b;

	}

	public void startMissionNotificationLoopShowing() {
		Bukkit.getScheduler().cancelTasks(ac);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, new MissionNotification(), 1, 12000);
	}
}