/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package api_private;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.block.Sign;

public class DewddPrivate {

	public static int signprotectr = 2;
	// public static double buy1blockmoney = 0.1; // 0.7;
	
	public static boolean nearPrivateSign(Block block) {

		for (int xb = -1 ; xb <= 1 ; xb ++) {
			for (int yb = -1 ; yb <= 1 ; yb ++) {
				for (int zb = -1 ; zb <= 1 ; zb ++) {
					
					if (!((xb == 1 && yb == 0 && zb == 0) ||
							(xb == -1 && yb == 0 && zb == 0)||
							(xb == 0 && yb == 0 && zb == 1) ||
							(xb == 0 && yb == 0 && zb == -1)||
							(xb == 0  && yb == 1 && zb == 0)  ||
							(xb == 0 && yb == -1 && zb == 0))) {
						continue;
					}

					if (block.getRelative(xb, yb, zb).getTypeId() == 63
							|| block.getRelative(xb, yb, zb).getTypeId() == 68) {

						Sign sign = (Sign) block.getRelative(xb, yb, zb).getState();
						if (sign.getLine(0).toLowerCase().endsWith("[dewdd]") == true
								|| sign.getLine(0).toLowerCase().endsWith("[private]") == true
								|| sign.getLine(0).equalsIgnoreCase("[protection]") == true) {
							return true;

						}
					}

				}
			}
		}

		return false;
	}

	public static boolean hasProtect(Block block) {

		for (int xb = (0 - signprotectr); xb <= (0 + signprotectr); xb++) {
			for (int yb = (0 - signprotectr); yb <= (0 + signprotectr); yb++) {
				for (int zb = (0 - signprotectr); zb <= (0 + signprotectr); zb++) {

					if (block.getRelative(xb, yb, zb).getTypeId() == 63
							|| block.getRelative(xb, yb, zb).getTypeId() == 68) {

						Sign sign = (Sign) block.getRelative(xb, yb, zb).getState();
						if (sign.getLine(0).toLowerCase().endsWith("[dewdd]") == true
								|| sign.getLine(0).toLowerCase().endsWith("[private]") == true
								|| sign.getLine(0).equalsIgnoreCase("[protection]") == true) {
							return true;

						}
					}

				}
			}
		}

		return false;
	}

	public static boolean cando(Block block, Player player) {

		for (int xb = (0 - signprotectr); xb <= (0 + signprotectr); xb++) {
			for (int yb = (0 - signprotectr); yb <= (0 + signprotectr); yb++) {
				for (int zb = (0 - signprotectr); zb <= (0 + signprotectr); zb++) {

					if (block.getRelative(xb, yb, zb).getTypeId() == 63
							|| block.getRelative(xb, yb, zb).getTypeId() == 68) {

						Sign sign = (Sign) block.getRelative(xb, yb, zb).getState();
						if (sign.getLine(0).toLowerCase().endsWith("[dewdd]") == true
								|| sign.getLine(0).toLowerCase().endsWith("[private]") == true
								|| sign.getLine(0).equalsIgnoreCase("[protection]") == true) {
							if ((sign.getLine(1).equalsIgnoreCase(player.getName()) == false)
									&& (sign.getLine(2).equalsIgnoreCase(player.getName()) == false)
									&& (sign.getLine(3).equalsIgnoreCase(player.getName()) == false)) {

								if (player.hasPermission("dewdd.private.overide") == true) {
									
									  dprint.r.printAll("ptdew&dewdd: admin " +
									  player.getName() +
									  " overide.. sign protection.. at " +
									  block.getX() + "," + block.getY() + "," +
									  block.getZ()); dprint.r.printAll(
									  "sign protected by [" + sign.getLine(1) +
									  "," + sign.getLine(2) + "," +
									  sign.getLine(3) + "]");
									 

									return true;
								}
								player.sendMessage("ptdew&dewdd: sign protected by [" + sign.getLine(1) + ","
										+ sign.getLine(2) + "," + sign.getLine(3) + "]");
								return false;
							}

						}

					}

				}
			}
		}

		return true;
	}

}
