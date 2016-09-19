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

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import dewddtran.tr;
import li.Constant_Protect;
import li.LXRXLZRZType;

public abstract class dewset_interface extends dewsetdatabase {

	public int selectmax = 29;

	public int dewsignlimit = 200;

	public int dewsignnamemax = 20;

	public String pmaindelete = "dewdd.main.delete";
	public String pmaindewbuychangehost = "dewdd.main.dewbuy.changehost";
	public String pmaindewbuydelete = "dewdd.main.dewbuydelete";
	public String pmaindewbuymodifymember = "dewdd.main.dewbuy.modifymember";

	public String pmaindewbuynotcount = "dewdd.main.dewbuy.notcount";

	public String pmaindewbuyreplace = "dewdd.main.dewbuy.replace";

	public String pmaindewseteverywhere = "dewdd.main.dewset.everywhere";

	public String pmaininfinite = "dewdd.main.dewset.infinite";

	public String pmainoveride = "dewdd.main.overide";

	public String pmainplaceblocknoprotect = "dewdd.main.placeblock.noprotect";

	public String pmainalsocopyinventoryblockwhenyouusedewset = "dewdd.main.dewset.copyinventoryblocks";

	public String puseitem55 = "dewdd.main.dewset.item55";

	
	public int dewsignloop[][];

	public int dewsignmax[];

	public String dewsignname[][][];
	public int dewsignx1[][];

	public int dewsignx2[][];

	public int dewsigny1[][];
	public int dewsigny2[][];
	public int dewsignz1[][];
	
	public int dewsignz2[][];

	public String dewworldlist[];

	// autosearchsub

	public int dewworldlistmax = 0;
	
	public int getProtectid(Block block) {
		int worldid = getworldid(block.getWorld().getName());

		if (worldid == -1)
			return -1;
		if (dewsignmax[worldid] == 0)
			return -1;

		boolean ee = false;
		int digx = block.getX();
		int digy = block.getY();
		int digz = block.getZ();

		boolean goodc1 = false;

		goodc1 = false;

		int dewsignnow = 0;

		for (int prioL = 1; prioL <= 2; prioL++) {

			for (dewsignnow = 0; dewsignnow < dewsignmax[worldid]; dewsignnow++) { // for
				if (prioL == 1) {
					// dprint.r.printAll("dewsignnow " + dewsignnow );

					if (dewsignname[worldid][dewsignnow][0].equalsIgnoreCase(Constant_Protect.flag_everyone) == true) {
						continue;
					}
				} else if (dewsignname[worldid][dewsignnow][0]
						.equalsIgnoreCase(Constant_Protect.flag_everyone) == false) {
					continue;
				}

				// check is this block on protect zone or not
				int x1m = 0;
				int x1l = 0;
				if (dewsignx1[worldid][dewsignnow] >= dewsignx2[worldid][dewsignnow]) {
					x1m = dewsignx1[worldid][dewsignnow];
					x1l = dewsignx2[worldid][dewsignnow];
				} else {
					x1l = dewsignx1[worldid][dewsignnow];
					x1m = dewsignx2[worldid][dewsignnow];
				}

				int y1m = 0;
				int y1l = 0;
				if (dewsigny1[worldid][dewsignnow] >= dewsigny2[worldid][dewsignnow]) {
					y1m = dewsigny1[worldid][dewsignnow];
					y1l = dewsigny2[worldid][dewsignnow];
				} else {
					y1l = dewsigny1[worldid][dewsignnow];
					y1m = dewsigny2[worldid][dewsignnow];

				}

				int z1m = 0;
				int z1l = 0;
				if (dewsignz1[worldid][dewsignnow] >= dewsignz2[worldid][dewsignnow]) {
					z1m = dewsignz1[worldid][dewsignnow];
					z1l = dewsignz2[worldid][dewsignnow];
				} else {
					z1l = dewsignz1[worldid][dewsignnow];
					z1m = dewsignz2[worldid][dewsignnow];

				}

				ee = digx >= x1l && digx <= x1m && digy >= y1l && digy <= y1m && digz >= z1l && digz <= z1m;

				goodc1 = ee;

				if (goodc1 == true) {
					dewsignloop[worldid][dewsignnow]++;
					break;
				}
			} // loog sign

			if (goodc1 == true)
				return dewsignnow;

		} // loop prio

		if (goodc1 == true)
			return dewsignnow;

		return -1;

	}

	
	public  boolean fw_add(String message, Player player) {
		int homeid = getProtectid(player.getLocation().getBlock());

		String m[] = message.split("\\s+");

		if (homeid == -1) {
			player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("this_area_don't_have_protect")));
			return false;
		}

		if (m.length != 3) {
			player.sendMessage(dprint.r.color("/fw add " + tr.gettr("need_argument_player_or_flag")));
			player.sendMessage(dprint.r.color("/fw add <name or flag>"));
			player.sendMessage(dprint.r.color("/fw add ptdew"));
			player.sendMessage(dprint.r.color("/fw add " + tr.gettr("your_friend_name")));

			return false;
		}
		int worldid = getworldid(player.getLocation().getWorld().getName());

		String nameToAdd = m[2];
		// got player name

		dprint.r.printC("ptdew&dewdd : dewadd name = '" + nameToAdd + "'");
		System.out.println("ptdew&dewdd : dewadd " + tr.gettr("home_id_is") + "  = '" + homeid + "'");

		if (dewsignname[worldid][homeid][0].equalsIgnoreCase(player.getName()) == false
				&& player.hasPermission(pmaindewbuymodifymember) == false) {
			player.sendMessage(dprint.r.color(tr.gettr("owner_is") + " ..." + dewsignname[worldid][homeid][0]));
			return false;
		}

		/*
		 * if (api_admin.dewddadmin.issubsubadminname(str11) == true &&
		 * api_admin.dewddadmin.is2admin(player) == false &&
		 * api_admin.dewddadmin.is2moderator(player) == false) {
		 * 
		 * player.sendMessage(dprint.r.color("ptdew&dewdd : " +
		 * tr.gettr("member_can't_add_staff_to_your_zone") +
		 * dewsignname[worldid][xyz][0]));
		 * 
		 * return false; }
		 */

		// add member but is staff
		/*
		 * if (api_admin.dewddadmin.issubsubadminname(str11) == false &&
		 * api_admin.dewddadmin.isadminname(str11) == false &&
		 * api_admin.dewddadmin.is2moderator(player) == true) {
		 * 
		 * if (str11.equalsIgnoreCase(Constant_Protect.flag_sell) == true) {
		 * dprint.r.printAll( "ptdew&dewdd : staff " + player.getName() +
		 * tr.gettr("try_to_add_sell_Constant_Protect.flag_to_his_protect"));
		 * return false; } player.sendMessage(dprint.r.color("ptdew&dewdd : " +
		 * tr.gettr("staff_can't_add_member_to_your_zone") +
		 * dewsignname[worldid][xyz][0]));
		 * 
		 * return false; }
		 */

		boolean subAdd = fw_add_sub(nameToAdd, worldid, homeid, -1);
		
		if (subAdd == false) {
		player.sendMessage(
				dprint.r.color(tr.gettr("can't_add_member_to_this_zone") + nameToAdd + " to  " + homeid));
		}
		else {
			player.sendMessage(dprint.r.color("added " + nameToAdd + " to  " + homeid));
			return true;
		}
		
		return false;
	}

	
public boolean fw_add_sub(String nameToAdd, int worldid, int homeid, int slot) {
		
		for (int ig = 1; ig < dewsignnamemax; ig++)
			if (dewsignname[worldid][homeid][ig].equalsIgnoreCase(nameToAdd) == true) {
				return false;
			}

		for (int ig = 1; ig < dewsignnamemax; ig++)
			if (dewsignname[worldid][homeid][ig].equalsIgnoreCase("") == true
					|| dewsignname[worldid][homeid][ig].equalsIgnoreCase("null") == true) {

				dewsignname[worldid][homeid][ig] = nameToAdd;
				savesignfile(-1, worldid);
				return true;
			}
		
		
		return false;
		
	}



public String getworldname(int idworld) {
	String aa = "ptdew_dewdd_" + dewworldlist[idworld] + ".txt";
	return aa;
}


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

		fwriter.write(dewsignmax[worldid] + System.getProperty("line.separator"));

		for (int y = 0; y < dewsignmax[worldid]; y++) {
			if (exceptint > -1)
				if (y == exceptint) {
					continue;
				}

			for (int whome = 0; whome < dewsignnamemax; whome++)
				if (dewsignname[worldid][y][whome] != null) {
					fwriter.write(dewsignname[worldid][y][whome] + System.getProperty("line.separator"));
				} else {
					fwriter.write("null" + System.getProperty("line.separator"));
				}

			fwriter.write(dewsignx1[worldid][y] + System.getProperty("line.separator"));
			fwriter.write(dewsigny1[worldid][y] + System.getProperty("line.separator"));
			fwriter.write(dewsignz1[worldid][y] + System.getProperty("line.separator"));

			fwriter.write(dewsignx2[worldid][y] + System.getProperty("line.separator"));
			fwriter.write(dewsigny2[worldid][y] + System.getProperty("line.separator"));
			fwriter.write(dewsignz2[worldid][y] + System.getProperty("line.separator"));

			fwriter.write(dewsignloop[worldid][y] + System.getProperty("line.separator"));

			// dprint.r.printC ("ptdew&dewdd : Saved y= " + y );

		}

		fwriter.close();
		dprint.r.printC("ptdew&dewdd : saved " + filena);
		return;

	} catch (IOException e) {
		// Auto-generated catch block
		e.printStackTrace();
	}

	// dprint.r.printC ("ptdew&dewdd : save " + tr.gettr("done") + "...");

	// ***************************88

	// ******************************
}

//loadsignfile
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

				String filena = folder_name + File.separator + getworldname(wlo);
				File fff = new File(filena);
				fff.createNewFile();

				dewsignmax[wlo] = 0;

				dprint.r.printC("ptdew&dewdd : Starting Loading " + filena);

				FileInputStream fstream = new FileInputStream(filena);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
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
						dewsignx1[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						break;
					case 22: // y1
						dewsigny1[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						break;
					case 23: // z1
						dewsignz1[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						break;
					case 24: // x2
						dewsignx2[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						break;
					case 25: // y2
						dewsigny2[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						break;
					case 26: // z2
						dewsignz2[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						break;
					case 27: // loop count
						dewsignloop[wlo][dewsignmax[wlo] - 1] = Integer.parseInt(strLine);
						dewsignloop[wlo][dewsignmax[wlo] - 1] = 0;
						dprint.r.printC("loaded sign of world " + wlo + " sign id = " + dewsignmax[wlo]);
						moden = 0;
						break;
					}

					/*
					 * if (dewsignx1[wlo][dewsignmax[wlo] - 1]== 0 &&
					 * dewsigny1[wlo][dewsignmax[wlo] - 1] == 0 &&
					 * dewsignz1[wlo][dewsignmax[wlo] - 1] == 0 &&
					 * dewsignx2[wlo][dewsignmax[wlo] - 1] == 0 &&
					 * dewsigny2[wlo][dewsignmax[wlo] - 1] == 0 &&
					 * dewsignz2[wlo][dewsignmax[wlo] - 1] == 0) {
					 * dewsignmax[wlo]--; }
					 */

					moden++;

				}

				dprint.r.printC("ptdew&dewdd : loaded " + filena);

				in.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("LoadSignFile Error: " + e.getMessage());
			} // catch
		}
	}

	// savesignfiledefrag

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
				dprint.r.printC("world sign " + (dewworldlistmax - 1) + " = " + dewworldlist[dewworldlistmax - 1]);
			}

			System.out.println("ptdew&DewDD Main: Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error load " + filena + e.getMessage());
		}
	}

	
	public boolean fw_goid(Player player, int id) {

		int worldid = getworldid(player.getLocation().getWorld().getName());


		if (getworldid(player.getWorld().getName()) == -1) {
			player.sendMessage("ptdew&dewdd : " + tr.gettr("this_world_don't_have_protect"));

			return false;
		}

		player.sendMessage("/fw goid '" + id + "'" + "/"
				+ (dewsignmax[getworldid(player.getWorld().getName())] - 1));

		if (id < -1 || id > dewsignmax[getworldid(player.getWorld().getName())] - 1) {
			player.sendMessage("ptdew&dewdd : dewgo   -1 < ? < "
					+ (dewsignmax[getworldid(player.getWorld().getName())] - 1));
			return false;
		}

		teleportPlayerToProtectID(id, player);

		return true;
	}
	
	public void teleportPlayerToProtectID(int id, Player player) {
		int worldid = getworldid(player.getWorld().getName());

		LXRXLZRZType ee = new LXRXLZRZType(dewsignx1[worldid][id], dewsigny1[worldid][id], dewsignz1[worldid][id],

				dewsignx2[worldid][id], dewsigny2[worldid][id], dewsignz2[worldid][id]);
		int mid[] = ee.getmiddle();

		Location lox = player.getWorld().getBlockAt(mid[0], mid[1], mid[2]).getLocation();

		player.getWorld().loadChunk((int) lox.getX(), (int) lox.getZ());
		player.teleport(lox);
		player.sendMessage("ptdew&dewdd : " + tr.gettr("teleported_your_to_zone_id") + id);

	}

	public boolean fw_list(Player player) {

		Block block = player.getLocation().getBlock();

		int xyz = getProtectid(block);
		if (xyz == -1) {
			player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("tree_check_protect_and_not_found")));
			return false;
		}

		int worldid = getworldid(player.getWorld().getName());
		player.sendMessage(dprint.r
				.color("ptdew&dewdd : " + tr.gettr("tree_thiszoneprotectedby") + " worldID " + worldid + " id " + xyz));

		for (int xxx = 0; xxx < dewsignnamemax; xxx++)
			if (dewsignname[worldid][xyz][xxx].equalsIgnoreCase("null") == false) {
				player.sendMessage(dprint.r.color(xxx + " = " + dewsignname[worldid][xyz][xxx]));
			}

		return true;
	}

	public boolean fw_remove(String message, Player player) {
		String m[] = message.split("\\s+");
		if (m.length != 3) {
			player.sendMessage(dprint.r.color("/fw remove <name>"));
			return false;
		}

		String nameToRemove = m[2];
		int worldid = getworldid(player.getLocation().getWorld().getName());
		// got player name

		int xyz = getProtectid(player.getLocation().getBlock());

		dprint.r.printC("ptdew&dewdd : dewremove name = '" + nameToRemove + "'");
		System.out.println("ptdew&dewdd : dewremove " + tr.gettr("search_home_id") + "= '" + xyz + "'");

		if (xyz == -1) {
			player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("this_area_don't_have_protect")));
			return false;
		}

		if (dewsignname[worldid][xyz][0].equalsIgnoreCase(player.getName()) == false
				&& player.hasPermission(pmaindewbuymodifymember) == false) {
			player.sendMessage(dprint.r.color(tr.gettr("owner_is") + " ..." + dewsignname[worldid][xyz][0]));
			return false;
		}

		for (int ig = 1; ig < dewsignnamemax; ig++)
			if (dewsignname[worldid][xyz][ig].equalsIgnoreCase(nameToRemove) == true) {

				dewsignname[worldid][xyz][ig] = "null";
				player.sendMessage(dprint.r.color("removed " + nameToRemove + " from  " + xyz));
				return true;
			}

		player.sendMessage(dprint.r.color(tr.gettr("can't_find_this_player_on_this_zone")));
		return false;
	}
	
	public boolean fw_exitFromThisZone(String message, Player player) {
		String m[] = message.split(" ");
		if (m.length != 3) {
			player.sendMessage(dprint.r.color(tr.gettr("not enough argument") + 
					"/fw exitFromThisZone <owner>"));
			return false;
		}
		
		 // search that name
		int worldid = getworldid(player.getWorld().getName());
		if (worldid == -1) {
			player.sendMessage(dprint.r.color(tr.gettr("this world don't have fw protect")));
			return false;
		}
		
		for (int i = 0 ; i < this.dewsignmax[worldid] ; i ++ ) {
			
		}
		
		return false;
		
	}
	
	
	public int getworldid(String wowo) {
		for (int d = 0; d < dewworldlistmax; d++)
			if (wowo.equalsIgnoreCase(dewworldlist[d]) == true)
				return d;

		// dprint.r.printAll("Error getworldid " + wowo + " so return -1");
		return -1;
	}


}
