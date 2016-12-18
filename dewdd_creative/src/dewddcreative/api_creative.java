package dewddcreative;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import dewddtran.tr;
import li.Useful;

public  class api_creative {
	
	
	
	public static String perdewremove = "dewdd.creative.dewremove";
	public static String perchangehost = "dewdd.creative.changehost";
	public static String perdoprotecty = "dewdd.creative.doprotecty";
	public static String perdounprotecty = "dewdd.creative.dounprotecty";
	public static String peroveride = "dewdd.creative.overide";

	public static int signY = 254;
	public static int max_b = 2600;// 19900;
	private Random randomG = new Random();

	public static JavaPlugin ac;

	public static boolean cando(Block block, Player player) {
		return cando(block.getX() , block.getY()  , block.getZ(),player);
	}
	
	public static boolean cando(int x, int y, int z, Player player) {
		if (!tr.isrunworld(ac.getName(), player.getWorld().getName())) {
			return true;
		}
		
		if (player.isOp() ) {
			return true;
		}

		int zx = 5;
		int zz = 5;

		boolean foundza = false;

		for (int gx = -max_b; gx <= max_b; gx += 100) {
			for (int gz = -max_b; gz <= max_b; gz += 100) {
				if (x >= gx && (x <= gx + 99)) {
					if (z >= gz && (z <= gz + 99)) {
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

		if (zx == 5 && zz == 5) {
			player.sendMessage("out of " + (max_b) + " area");
			return false; // not protect
		}

		// check permission
		// zx,254,zz
		if (x == zx && z == zz) { // do anything at protect zone
			if (y == 254 || y == 253) {

				// changehost
				if (player.hasPermission(perchangehost) == false) {
					return false;
				}
			}
		}

		if (y == 254 || y == 253) { // y
			if (player.getWorld().getBlockAt(zx, 254, zz).getTypeId() == 63) { // changehost
				Sign sign = (Sign) player.getWorld().getBlockAt(zx, 254, zz).getState();
				if (sign.getLine(1).equalsIgnoreCase(player.getName()) == false
						&& player.hasPermission(perdoprotecty) == false) {
					return false;
				}
			} else { // do any thing at protecty not protect
				if (player.hasPermission(perdounprotecty) == false) {
					return false;
				}
			}
		}

		for (int cx = zx; (cx <= zx + 99); cx++) { // loop sign
			if (player.getWorld().getBlockAt(cx, 254, zz).getTypeId() == 63) { // found
																				// sign
				Sign sign = (Sign) player.getWorld().getBlockAt(cx, 254, zz).getState();
				if (sign.getLine(0).equalsIgnoreCase(player.getName()) == true) {
					return true;
				}
				if (sign.getLine(1).equalsIgnoreCase(player.getName()) == true) {
					return true;
				}
				if (sign.getLine(2).equalsIgnoreCase(player.getName()) == true) {
					return true;
				}
				if (sign.getLine(3).equalsIgnoreCase(player.getName()) == true) {
					return true;
				}
				// in zone can do any thing
				if (player.hasPermission(peroveride) == true) {
					return true;
				}
			}

		} // loop sign

		return false;
		// not found

		// 0 , 0 to 100 , 100
	}

	public void gotofreezone(Player player) {

		Block e = null;

		int x;
		int z;

		boolean cd = false;

		do {

			x = this.randomG.nextInt(max_b * 2) - max_b;
			z = this.randomG.nextInt(max_b * 2) - max_b;
			e = player.getLocation().getWorld().getBlockAt(x, 150, z);
			e = Useful.getTopBlockHigh(e);
			
			cd = isProtectedArea(e);

		} while (cd == false);

		dprint.r.printAll("ptdew&dewdd: " + player.getName() + tr.gettr("Creative Found freeZone at") + " (" + x + ",?,"
				+ z + ")");
		e.getChunk().load();
		player.teleport(e.getLocation());

	}



	public static boolean isProtectedArea(Block block) {

		int zx = 5;
		int zz = 5;
		int x = 0;
		int z = 0;

		boolean foundza = false;

		for (int gx = -max_b; gx <= max_b; gx += 100) {
			for (int gz = -max_b; gz <= max_b; gz += 100) {
				if (x >= gx && (x <= gx + 99)) {
					if (z >= gz && (z <= gz + 99)) {
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

		if (zx == 5 && zz == 5) {
			return false; // not protect
		}

		if (block.getWorld().getBlockAt(zx, 254, zz).getTypeId() == 63) {
			return true;
		}

		return false;
	}

	
	public static void saveRSProtectFile() {

		File dir = new File(Constant.folder_name);
		dir.mkdir();

		String filena = Constant.folder_name + File.separator + Constant.rsProtect_filename;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

		
			for (int y = 0; y < DigEventListener2.allProtect.size(); y++) {
				Location lo = DigEventListener2.allProtect.get(y);
				
				lo =  Useful.getTopBlockHigh(lo.getBlock()).getLocation();
				
				String wr = lo.getBlockX() + " " + lo.getBlockY() + " " + lo.getBlockZ(); 

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
	
	public static void loadRSProtectFile() {
		String worldf = Constant.rsProtect_filename;

		File dir = new File(Constant.folder_name);
		dir.mkdir();
		System.out.println(dir.getAbsolutePath());

		String filena = Constant.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			
			fff.createNewFile();

			dprint.r.printAll("ptdeW&DewDD load Creative : " + filena);
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
			
			DigEventListener2.allProtect.clear();

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				// Print the content on the console
				
				
				int x =  Integer.parseInt(m[0]);
				int y = Integer.parseInt(m[1]);
				int z = Integer.parseInt(m[2]);
				
				Location loc = Bukkit.getPlayer("ddlovedew").getWorld().getBlockAt(x, y, z).getLocation();
				DigEventListener2.allProtect.add(loc);
				
				dprint.r.printAll(DigEventListener2.allProtect.size() + " = " + x + "," + y  + "," + z);
				

				// rs[rsMax - 1].mission = 0;

			}


			dprint.r.printAll("ptdew&DewDD Skyblock: Loaded " + filena);


			in.close();
		} catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + e.getMessage());
			e.printStackTrace();
		}
	}
	
	


}