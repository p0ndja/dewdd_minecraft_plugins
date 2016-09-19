package dewddcreative;

import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public  class api_creative {
	public static String perdewremove = "dewdd.creative.dewremove";
	public static String perchangehost = "dewdd.creative.changehost";
	public static String perdoprotecty = "dewdd.creative.doprotecty";
	public static String perdounprotecty = "dewdd.creative.dounprotecty";
	public static String peroveride = "dewdd.creative.overide";

	public static int max_b = 2000;// 19900;
	private Random randomG = new Random();

	public static JavaPlugin ac;

	public static boolean cando(int x, int y, int z, Player player) {
		if (!tr.isrunworld(ac.getName(), player.getWorld().getName())) {
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
			e = player.getLocation().getWorld().getBlockAt(x, 70, z);
			cd = isprotectedarea(e);

		} while (cd == false);

		dprint.r.printAll("ptdew&dewdd: " + player.getName() + tr.gettr("Creative Found freeZone at") + " (" + x + ",?,"
				+ z + ")");
		e.getChunk().load();
		player.teleport(e.getLocation());

	}



	public boolean isprotectedarea(Block block) {

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



}