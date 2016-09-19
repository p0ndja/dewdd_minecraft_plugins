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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class api_skyblock {

	class cleanthischunktcrs0 implements Runnable {
		private Chunk chunk = null;

		public cleanthischunktcrs0(Chunk chunk) {
			this.chunk = chunk;
		}

		@Override
		public void run() {
			if (isrunworld(chunk.getWorld().getName()) == true) {
				World world = chunk.getWorld();

				dprint.r.printC("starting clean real skyblock 0 : " + (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
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

							block.setType(Material.AIR);

						} // if
					}
				}
				// add to new chunk

				dprint.r.printC("cleaned real skyblock 0 : " + (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16)
						+ " at " + chunk.getWorld().getName());

				lastclean = System.currentTimeMillis();

			}

		}

	}

	class createskyblockrs implements Runnable {

		private Player player;

		public createskyblockrs(Player player) {
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

				x = randomG.nextInt(20) * 300 * (randomG.nextInt(1) == 1 ? 1 : -1);
				z = randomG.nextInt(20) * 300 * (randomG.nextInt(1) == 1 ? 1 : -1);
				y = randomG.nextInt(200) + 50;

				boolean checkrs = true;

				for (int lop = 0; lop < rsMax; lop++) {
					if (rs[lop].x == x && rs[lop].z == z) {

						checkrs = false;
						break;
					}
				}

				if (checkrs == false) {
					// check top and down y section
					// I don't need to replace old skyblock anymore

					for (int i = 0; i < 256; i++) {

						if (player.getWorld().getBlockAt(x, i, z).getType() != Material.AIR) {
							checkrs = true;
							break;
						}
					}
				}

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
					rs[newid].p[1] = flag_autocut;
					rs[newid].p[2] = flag_autoabsorb;
					rs[newid].mission = 0;
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
					saversprotectfile();
					buildcomplete = true;
				}
			} // loop build complete
		}
	}

	class cridata {
		public int id;
		public byte data;
		public int targetamount;
		public int nowamount;
		public double percent;

	}

	public void nextMission(int rsID) {
		printToAllPlayerOnRS(rsID, (getMissionHeader(rs[rsID].mission) + " " + tr.gettr("mission_complete")));

		applyReward(rs[rsID].mission);

		printToAllPlayerOnRS(rsID, tr.gettr("next_mission"));

		rs[rsID].mission++;

	}
	
	
	public void giveItemToAllPlayerInRS(int rsID , ItemStack itm) {
		for (int i = 0; i < RSMaxPlayer; i++) {
			Player player = Bukkit.getPlayer(rs[rsID].p[i]);
			
			
			if (player == null) {
				continue;
			}

			player.getInventory().addItem(itm);
		}
		
	}

	public void applyReward(int rsID) {
		
		dprint.r.printAll("appyreward " + rsID + " mission " + rs[rsID].mission);
		switch (rs[rsID].mission) {
		case 0: // get cobble stone
			
			dprint.r.printAll("nope");
			
			ItemStack itm = new ItemStack(Material.IRON_AXE , 1);
			
			giveItemToAllPlayerInRS(rsID, itm.getData().toItemStack(1));
			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));
			

			break;
		case 1:


			 itm = new ItemStack(Material.IRON_AXE , 1);
			giveItemToAllPlayerInRS(rsID, itm);
			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));
			

			break;
		case 2:

			 itm = new ItemStack(Material.IRON_AXE , 1);
			giveItemToAllPlayerInRS(rsID, itm);
			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));
			

			break;
		default:

			 itm = new ItemStack(Material.IRON_AXE , 1);
			giveItemToAllPlayerInRS(rsID, itm);
			printToAllPlayerOnRS(rsID, tr.gettr("got_reward_lv_" + rs[rsID].mission));
			

			break;
		}
	}

	public void printToAllPlayerOnRS(int rsID, String message) {
		for (int i = 0; i < RSMaxPlayer; i++) {
			Player player = Bukkit.getPlayer(rs[rsID].p[i]);
			if (player == null) {
				continue;
			}

			player.sendMessage(message);
		}
	}

	public String getMissionHeader(int mission) {
		String header = "skyblock_mission_header_";

		String aa = "";
		switch (mission) {
		case 0: // get cobble stone
			aa = tr.gettr(header + mission);

			break;
		case 1:

			aa = tr.gettr(header + mission);
			break;
		case 2:
			aa = tr.gettr(header + mission);
			break;
		default:
			aa = tr.gettr(header + "default");
			break;
		}

		return aa;
	}

	public void startMissionNotificationLoopShowing() {
		Bukkit.getScheduler().cancelTasks(ac);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, new missionNotification(), 1, 2400);
	}

	class missionNotification implements Runnable {

		@Override
		public void run() {

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player == null) {
					continue;
				}

				int id = getOWNIslandID(player, false);
				// island not found
				if (id == -1) {
					continue;
				}

				player.sendMessage(getFullMissionHeadAndCurLevel(rs[id].mission));

				// check his
			}

		}

	}

	public String getFullMissionHeadAndCurLevel(int mission) {
		String header = getMissionHeader(mission);
		String aa = tr.gettr("is_cur_level_mission_showing_") + " " + mission + " " + header;
		return aa;
	}

	public class rsdata {
		public int x;
		public int y;
		public int z;

		public int mission = 0;

		public String p[] = null;
		public int autoCutCount = 0;
		public long autoCutLastTime = 0;

	}

	public static boolean cando(Block block, Player player, String mode) {
		if ((player.hasPermission(poveride)) == true) {
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
		if (getplayerinslot(flag_everyone, getid) > -1 && mode.equalsIgnoreCase("right")) {

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

	public static int getplayerinslot(String player, int getid) {
		for (int lop = 0; lop < RSMaxPlayer; lop++) {
			if (rs[getid].p[lop].equalsIgnoreCase(player)) {
				return lop;
			}
		}

		return -1;
	}

	long lastcri = 0; // last checkrateis don't allow to

	// check if it is running
	Random rnd = new Random();

	int maxdelaycri = 60000;
	public int maxautocut = 5;
	cridata cri[];
	int crimax = 0;
	public String lastmessage = "";

	Block[] cs = null;

	int csmax = 0;

	int csnow = 0;

	int csid = 0;

	long lastshow = 0;

	public String pskyblock = "dewdd.skyblock.skyblock";

	public static String poveride = "dewdd.skyblock.overide";

	public String flag_explode = "<!explode>";
	public String flag_monster = "<!monster>";
	public String flag_pvp = "<pvp>";

	public String flag_autocut = "<autocut>";
	public String flag_autoabsorb = "<autoabsorb>";
	public static String flag_everyone = "<everyone>";

	public static int RSMaxPlayer = 20;

	public String folder_name = "plugins" + File.separator + "dewdd_skyblock";

	public static rsdata rs[] = new rsdata[100];

	public static int rsMax = 0;

	public static JavaPlugin ac = null;

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

	long lastcreate = 0;

	long lastclean = 0;

	public Random randomG = new Random();

	int amount = 0; // recusive
					// count

	int mode2 = 1;

	public api_skyblock() {
		loadrsprotectfile();

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
					rs[rsMax - 1].mission = (int) Double.parseDouble(m[23]);
				}

			}

			dprint.r.printAll("ptdew&DewDD Skyblock: Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
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

		String filena = folder_name + File.separator + "ptdew_dewdd_rs_protect.txt";
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
}