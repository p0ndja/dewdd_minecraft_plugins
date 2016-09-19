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
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class api_skyblock {

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

			while (buildcomplete == false) {
				// random x y z this is not near old rs list
				buildcomplete = true;

				x = rnd.nextInt(20) * 300 * (rnd.nextInt(1) == 1 ? 1 : -1);
				z = rnd.nextInt(20) * 300 * (rnd.nextInt(1) == 1 ? 1 : -1);
				y = rnd.nextInt(200) + 50;

				boolean checkrs = true;

				for (int lop = 0; lop < rsMax; lop++) {
					if (rs[lop].x == x && rs[lop].z == z) {

						checkrs = false;
						break;
					}
				}

				/*
				 * if (checkrs == true) { // check top and down y section // I
				 * don't need to replace old skyblock anymore
				 * 
				 * for (int i = 0; i < 256; i++) {
				 * 
				 * if (player.getWorld().getBlockAt(x, i, z).getType() !=
				 * Material.AIR) { checkrs = false; break; } } }
				 */

				if (checkrs == false) {
					buildcomplete = false;
				} else { // if x y z this can do
					dprint.r.printAll("random " + x + "," + y + "," + z);

					int newid = getOWNIslandID(player, true);
					// get free slot for this player
					rs[newid].x = x;
					rs[newid].y = y;
					rs[newid].z = z;

					for (int lop = 0; lop < RSMaxPlayer; lop++) {
						rs[newid].p[lop] = "null";
					}
					rs[newid].p[0] = player.getName();
					rs[newid].p[1] = Constant.flag_autocut;
					rs[newid].p[2] = Constant.flag_autoabsorb;
					rs[newid].mission = Missional.LV_0_COBBLESTONE_MACHINE;
					// clean target chunk and build island
					Chunk chunk = null;

					int minx = player.getWorld().getBlockAt(x - 16, y, z - 16).getChunk().getX() * 16;
					int minz = player.getWorld().getBlockAt(x - 16, y, z - 16).getChunk().getZ() * 16;

					int maxx = player.getWorld().getBlockAt(x + 16, y, z + 16).getChunk().getX() * 16;
					int maxz = player.getWorld().getBlockAt(x + 16, y, z + 16).getChunk().getZ() * 16;

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

					dprint.r.printA(tr.gettr("if_you_want_to_back_to_is_type") + " /skyblock home");

					lastcreate = System.currentTimeMillis();

					player.sendMessage(getFullMissionHeadAndCurLevel(rs[newid].mission));

					// add item done

					// save file
					saveRSProtectFile();
					buildcomplete = true;
				}
			} // loop build complete
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

				int id = getprotectid(player.getLocation().getBlock());

				// island not found
				if (id == -1) {
					continue;
				}

				if (getplayerinslot(player.getName(), id) > -1) {

					player.sendMessage(getFullMissionHeadAndCurLevel(rs[id].mission));
				}

				// check his
			}

		}

	}

	public static int RSMaxPlayer = 20;

	public static RSData rs[] = new RSData[100];

	public static int rsMax = 0;

	public static JavaPlugin ac = null;

	public static boolean cando(Block block, Player player, String mode) {
		if ((player.hasPermission(Constant.poveride)) == true) {
			return true;
		}

		if (!isrunworld(block.getWorld().getName())) {
			return true;
		}

		int getid = getprotectid(block);

		if (getid == -1) {
			// player.sendMessage(dprint.r.color("this is not anyone skyblock");
			return true; // can't do it
		}

		// found
		int getslot = getplayerinslot(player.getName(), getid);
		if (getplayerinslot(Constant.flag_everyone, getid) > -1 && mode.equalsIgnoreCase("right")) {

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

	public static int getplayerinslot(String player, Block block) {

		int getid = getprotectid(block);
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

	public static int getprotectid(Block block) {
		// must check world

		for (int lop = 0; lop < rsMax; lop++) {
			if (block.getX() >= (rs[lop].x - 150) && block.getX() <= (rs[lop].x + 150)
					&& block.getZ() >= (rs[lop].z - 150) && block.getZ() <= (rs[lop].z + 150)) {
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

	public String lastmessage = "";

	long lastcreate = 0;

	long lastclean = 0;

	int amount = 0; // recusive
					// count

	public api_skyblock() {
		loadRSProtectFile();

	}

	public void applyReward(int rsID) {

		switch (rs[rsID].mission) {
		case LV_0_COBBLESTONE_MACHINE: // get cobble stone

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
		case LV_1_Break_STONE:

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

			rs[rsID].tmpValue1 = 0;

			break;
		case LV_2_USE_BONE_MEAL:

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

		case LV_3_DROP_TOUCH:

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

		case LV_4_Place_y1:

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

			bo2 = searchSpaceCube(bo, 5, 5);

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

			Block signAdder = bo.getWorld().getBlockAt(bo.getX(), 1, bo.getY());
			signAdder.setType(Material.WALL_SIGN);

			Sign sign = (Sign) signAdder.getState();
			sign.setLine(0, "" + rs[rsID].mission.toID());
			sign.setLine( 1, "" + bo3.getX());
			sign.setLine( 2, "" + bo3.getY());
			sign.setLine( 3, "" + bo3.getZ());
			sign.update();
			
			

			printToAllPlayerOnRS(rsID,
					tr.gettr("generated_small_island_at") + " " + bo2.getX() + "," + bo2.getY() + "," + bo2.getZ());

			// mon
			bo2 = searchSpaceCube(bo, 5, 5);
			

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;

		default:

			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));

			break;
		}
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

	public String getFullMissionHeadAndCurLevel(Missional mission) {
		String header = getMissionHeader(mission);
		String aa = tr.gettr("is_cur_level_mission_showing_") + " " + mission + " " + header;
		return aa;
	}

	public String getMissionHeader(Missional mission) {
		String header = "skyblock_mission_header_";

		String aa = "";
		switch (mission) {
		case LV_0_COBBLESTONE_MACHINE: // get cobble stone
			aa = tr.gettr(header + mission);

			break;
		case LV_1_Break_STONE:

			aa = tr.gettr(header + mission);
			break;
		case LV_2_USE_BONE_MEAL:
			aa = tr.gettr(header + mission);
			break;
		case LV_3_DROP_TOUCH:
			aa = tr.gettr(header + mission);
			break;
			
		case LV_4_Place_y1:
			aa = tr.gettr(header + mission);
			break;

		default:
			aa = tr.gettr(header + "default");
			break;
		}

		return aa;
	}

	public int getOWNIslandID(Player player, boolean rebuild) {
		// loop for check exits rs id and return or not build new one

		// find
		for (int lop = 0; lop < rsMax; lop++) {
			if (rs[lop].p[0].equalsIgnoreCase(player.getName())) {
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

	public void loadRSProtectFile() {
		String worldf = "ptdew_dewdd_rs_protect.txt";

		File dir = new File(Constant.folder_name);
		dir.mkdir();

		String filena = Constant.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			rs = new RSData[1000];
			for (int lop = 0; lop < 1000; lop++) {
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

				if (m.length == 24) {
					int bb = (int) Double.parseDouble(m[23]);
					rs[rsMax - 1].mission = Missional.idToMission(bb);

				}

				// rs[rsMax - 1].mission = 0;

			}

			dprint.r.printAll("ptdew&DewDD Skyblock: Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + e.getMessage());
		}
	}

	public synchronized void nextMission(int rsID) {
		printToAllPlayerOnRS(rsID, (getMissionHeader(rs[rsID].mission) + " " + tr.gettr("mission_complete")));

		// dprint.r.printAll("0 calling apply reward");
		applyReward(rsID);

		printToAllPlayerOnRS(rsID, tr.gettr("next_mission"));

		dprint.r.printAdmin(tr.gettr("owner_of_island_name") + rs[rsID].p[0] + " " + tr.gettr("did_mission_complete")
				+ " " + getMissionHeader(rs[rsID].mission));

		int tmpID = Missional.getID(rs[rsID].mission);
		tmpID++;
		rs[rsID].mission = Missional.idToMission(tmpID);

		printToAllPlayerOnRS(rsID, (getMissionHeader(rs[rsID].mission) + " ..."));

		saveRSProtectFile();

	}

	public void printToAllPlayerOnRS(int rsID, String message) {
		for (int i = 0; i < RSMaxPlayer; i++) {
			Player player = Bukkit.getPlayer(rs[rsID].p[i]);
			if (player == null) {
				continue;
			}

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

		String filena = Constant.folder_name + File.separator + "ptdew_dewdd_rs_protect.txt";
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

				wr = wr + " " + rs[y].mission.toID();

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
					for (int z3 = z2; z3 <= y2 + radiusCubeNeed; z3++) {
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

		Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, new MissionNotification(), 1, 2400);
	}
}