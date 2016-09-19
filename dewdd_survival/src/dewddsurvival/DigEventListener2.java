/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddsurvival;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

class Dewminecraft {

	Random			randomGenerator		= new Random();

	public int		adminlistadminmax	= -1;

	public String	adminlistadmin[];

	public int		adminliststaffmax	= -1;

	public String	adminliststaff[];

	public int		selectmax		= 29;

	public String	selectname[]	= new String[selectmax + 1];

	public int		selectx1[]		= new int[selectmax + 1];

	public int		selecty1[]		= new int[selectmax + 1];

	public int		selectz1[]		= new int[selectmax + 1];

	public int		d4[]			= new int[selectmax + 1];
	public int		selectx2[]		= new int[selectmax + 1];
	public int		selecty2[]		= new int[selectmax + 1];
	public int		selectz2[]		= new int[selectmax + 1];
	public Dewminecraft() {
		loadadminfile();
	}

	public void autosortchest(Block block, Player player) {
		// player.sendMessage("ptdew&dewdd: auto sort chest");
		// player.sendMessage("ptdew&dewdd: แสดงความขอบคุณให้ ปลั๊กอินเรียงของในกล่องอัตโนมัติ by (ptdew&dewdd)");
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

			// player.sendMessage("ID" + it.getTypeId() + ",leng = " + leng);
			// add to my array
			// find

			boolean founded = false;

			for (l1 = 0; l1 < leng; l1++) {
				// player.sendMessage("finding old data " + l1);
				if (sid[l1] == it.getTypeId()) {
					// player.sendMessage("ax " + sid[l1]);
					if (sdata[l1].getData().getData() == it.getData().getData()) {

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
						sdata[l1] = it.getData().toItemStack();
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
					// player.sendMessage("adding > " + sid[l1] + " amount= " +
					// samount[l1]);
					sdata[l1].setAmount(64);
					chest.getInventory().addItem(sdata[l1]);
					samount[l1] -= 64;
				}
				else {
					// player.sendMessage("adding > " + sid[l1] + " amount = " +
					// samount[l1]);

					sdata[l1].setAmount(samount[l1]);
					chest.getInventory().addItem(sdata[l1]);

					samount[l1] -= samount[l1];
				}
			}

			// player.sendMessage("x data " +
			// chest.getInventory().getItem(0).getData().getData());

		}
	}
	public boolean checkpermissionarea(Block block, Player player,
			String modeevent) {
		return false;
	}
	public boolean decreseitem1(Player player, int itemid, int itemdata) {
		ItemStack itm = null;
		int len = player.getInventory().getContents().length;
		int lenl = 0;

		for (lenl = 0; lenl < len - 1; lenl++) {
			if (player.getInventory().getItem(lenl) == null) {
				continue;
			}

			itm = player.getInventory().getItem(lenl);
			if (itm.getTypeId() == itemid
					&& itm.getData().getData() == itemdata) {

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

		}
		return false;
	}

	public void dewfill(Player player) {
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

		if (player.getItemInHand().getType().isBlock() == false) {
			player.sendMessage("ptdew&dewdd:dewfill item "
					+ player.getItemInHand().getType().name()
					+ " is not a block");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

		for (Block blb : getselectblock(getid, player)) {

			/*
			 * if (PreciousStones.API().canPlace(player, blb.getLocation())==
			 * false) { player.sendMessage("ptdew&dewdd:Can't dewfill here (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			if (blb.getTypeId() != 0) {
				continue;
			}
			if (checkpermissionarea(blb, player, "build") == true) {
				return;
			}

			if (isadminname(player.getName()) == false) {
				if (decreseitem1(player, handid, handdata) == false) {
					player.sendMessage("ptdew&dewdd: dont have enough item");
					return;
				}
			}
			blb.setTypeIdAndData(handid, handdata, false);
		}

		player.sendMessage("ptdew&dewdd: dewfill done!");

	}

	public void dewfillwall(Player player) {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewfillwall please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewfillwall please set block2");
			return;
		}

		if (player.getItemInHand().getType().isBlock() == false) {
			player.sendMessage("ptdew&dewdd:dewfillwall item "
					+ player.getItemInHand().getType().name()
					+ " is not a block");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

		for (Block blb : getselectblock(getid, player)) {

			/*
			 * 
			 * if (PreciousStones.API().canPlace(player, blb.getLocation())==
			 * false) {
			 * player.sendMessage("ptdew&dewdd:Can't dewfillwall here (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			if (blb.getTypeId() != 0) {
				continue;
			}

			if (checkpermissionarea(blb, player, "build") == true) {
				return;
			}

			if (blb.getLocation().getBlockX() == selectx1[getid]
					|| blb.getLocation().getBlockX() == selectz1[getid]
					|| blb.getLocation().getBlockX() == selectx2[getid]
					|| blb.getLocation().getBlockX() == selectz2[getid]
					|| blb.getLocation().getBlockZ() == selectx1[getid]
					|| blb.getLocation().getBlockZ() == selectz1[getid]
					|| blb.getLocation().getBlockZ() == selectx2[getid]
					|| blb.getLocation().getBlockZ() == selectz2[getid]

			) {

				if (isadminname(player.getName()) == false) {
					if (decreseitem1(player, handid, handdata) == false) {
						player.sendMessage("ptdew&dewdd: dont have enough item");
						return;
					}
				}
				blb.setTypeIdAndData(handid, handdata, true);

			}
		}

		player.sendMessage("ptdew&dewdd: dewfillwall done!");
	}

	public void dewfullcircle(Player player) {

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

		if (player.getItemInHand().getType().isBlock() == false) {
			player.sendMessage("ptdew&dewdd:dewfullcircle item "
					+ player.getItemInHand().getType().name()
					+ " is not a block");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

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
			if (checkpermissionarea(blb, player, "build") == true) {
				continue;
			}
			if (decreseitem1(player, handid, handdata) == false) {
				player.sendMessage("ptdew&dewdd: dont have enough item");
				return;
			}
			blb.setTypeIdAndData(handid, handdata, true);

		} // for

		player.sendMessage("ptdew&dewdd: dewfullcircle done!");
	}

	public void dewset(Player player, int e1, byte e2, int e3, byte e4) {
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

		int handid = e3;
		byte handdata = e4;
		// player.sendMessage(". " + e1 + "," + e2 + "|" + e3 + "," + e4);

		for (Block blb : getselectblock(getid, player)) {

			/*
			 * if (PreciousStones.API().canPlace(player, blb.getLocation())==
			 * false) { player.sendMessage("ptdew&dewdd:Can't dewset here (" +
			 * blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			if (e1 != -29) {
				if (blb.getTypeId() != e1) {
					continue;
				}
			}

			if (e2 != -29) {
				if (blb.getData() != e2) {
					continue;
				}
			}

			if (checkpermissionarea(blb, player, "build") == true) {
				return;
			}

			if (blb.getTypeId() == handid && blb.getData() == handdata) {
				continue;
			}

			if (e3 == 0) { // if delete
				if (isadminname(player.getName()) == false
						&& issubsubadminname(player.getName()) == false) {
					player.sendMessage("ptdew&dewdd: dewsetadvance member can't use");
					return;
				}
				blb.breakNaturally();
			}
			else { // if place
				if (isadminname(player.getName()) == false) {
					if (decreseitem1(player, handid, handdata) == false) {
						player.sendMessage("ptdew&dewdd: dont have enough item");
						return;
					}
				}

				blb.setTypeIdAndData(handid, handdata, false);
			}

		}
		player.sendMessage("ptdew&dewdd: dewset done!");
	}

	public void dewsetwall(Player player) {

		int getid = getfreeselect(player);
		if (selectx1[getid] == 0 && selecty1[getid] == 0
				&& selectz1[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewsetwall please set block1");
			return;
		}
		if (selectx2[getid] == 0 && selecty2[getid] == 0
				&& selectz2[getid] == 0) {
			player.sendMessage("ptdew&dewdd:dewsetwall please set block2");
			return;
		}

		if (player.getItemInHand().getType().isBlock() == false) {
			player.sendMessage("ptdew&dewdd:dewsetwall item "
					+ player.getItemInHand().getType().name()
					+ " is not a block");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

		for (Block blb : getselectblock(getid, player)) {

			/*
			 * if (PreciousStones.API().canPlace(player, blb.getLocation())==
			 * false) { player.sendMessage("ptdew&dewdd:Can't dewsetwall here ("
			 * + blb.getX() + "," + blb.getY() + "," + blb.getZ() + ")");
			 * continue; }
			 */

			if (blb.getTypeId() == handid && blb.getData() == handdata) {
				continue;
			}

			if (checkpermissionarea(blb, player, "build") == true) {
				return;
			}

			if (blb.getLocation().getBlockX() == selectx1[getid]
					|| blb.getLocation().getBlockX() == selectz1[getid]
					|| blb.getLocation().getBlockX() == selectx2[getid]
					|| blb.getLocation().getBlockX() == selectz2[getid]
					|| blb.getLocation().getBlockZ() == selectx1[getid]
					|| blb.getLocation().getBlockZ() == selectz1[getid]
					|| blb.getLocation().getBlockZ() == selectx2[getid]
					|| blb.getLocation().getBlockZ() == selectz2[getid]

			) {

				if (isadminname(player.getName()) == false) {
					if (decreseitem1(player, handid, handdata) == false) {
						player.sendMessage("ptdew&dewdd: dont have enough item");
						return;
					}
				}

				blb.setTypeIdAndData(handid, handdata, false);

			}
		}

		player.sendMessage("ptdew&dewdd: dewsetwall done!");
	}

	public String engtothai(String st) {
		if (st.length() > 1) {
			// System.out.println("" + st.substring(0,1));
			if (st.substring(0, 1).equalsIgnoreCase("'") == true) {
				String stok = "";

				for (int stl = 1; stl < st.length(); stl++) {
					String stt = st.substring(stl, stl + 1);

					// eeee

					if (stt.equals("1") == true) {
						stok = stok + "=";
						continue;
					}
					if (stt.equals("2") == true) {
						stok = stok + "๒";
						continue;
					}
					if (stt.equals("3") == true) {
						stok = stok + "๓";
						continue;
					}
					if (stt.equals("4") == true) {
						stok = stok + "๔";
						continue;
					}
					if (stt.equals("5") == true) {
						stok = stok + "๕";
						continue;
					}
					if (stt.equals("6") == true) {
						stok = stok + "ู";
						continue;
					}
					if (stt.equals("7") == true) {
						stok = stok + "๗";
						continue;
					}
					if (stt.equals("8") == true) {
						stok = stok + "๘";
						continue;
					}
					if (stt.equals("9") == true) {
						stok = stok + "๙";
						continue;
					}
					if (stt.equals("0") == true) {
						stok = stok + "๐";
						continue;
					}
					if (stt.equals("[") == true) {
						stok = stok + "๑";
						continue;
					}
					if (stt.equals("]") == true) {
						stok = stok + "๖";
						continue;
					}
					if (stt.equals("!") == true) {
						stok = stok + "+";
						continue;
					}
					if (stt.equals("@") == true) {
						stok = stok + "\"";
						continue;
					}
					if (stt.equals("#") == true) {
						stok = stok + "/";
						continue;
					}
					if (stt.equals("$") == true) {
						stok = stok + ",";
						continue;
					}
					if (stt.equals("%") == true) {
						stok = stok + "?";
						continue;
					}
					if (stt.equals("^") == true) {
						stok = stok + "ุ";
						continue;
					}
					if (stt.equals("&") == true) {
						stok = stok + "_";
						continue;
					}
					if (stt.equals("*") == true) {
						stok = stok + ".";
						continue;
					}
					if (stt.equals("(") == true) {
						stok = stok + "(";
						continue;
					}
					if (stt.equals(")") == true) {
						stok = stok + ")";
						continue;
					}
					if (stt.equals("{") == true) {
						stok = stok + "-";
						continue;
					}
					if (stt.equals("}") == true) {
						stok = stok + "%";
						continue;
					}
					if (stt.equals("'") == true) {
						stok = stok + "็";
						continue;
					}
					if (stt.equals(",") == true) {
						stok = stok + "ต";
						continue;
					}
					if (stt.equals(".") == true) {
						stok = stok + "ย";
						continue;
					}
					if (stt.equals("p") == true) {
						stok = stok + "อ";
						continue;
					}
					if (stt.equals("y") == true) {
						stok = stok + "ร";
						continue;
					}
					if (stt.equals("f") == true) {
						stok = stok + "่";
						continue;
					}
					if (stt.equals("g") == true) {
						stok = stok + "ด";
						continue;
					}
					if (stt.equals("c") == true) {
						stok = stok + "ม";
						continue;
					}
					if (stt.equals("r") == true) {
						stok = stok + "ว";
						continue;
					}
					if (stt.equals("l") == true) {
						stok = stok + "แ";
						continue;
					}
					if (stt.equals("/") == true) {
						stok = stok + "ใ";
						continue;
					}
					if (stt.equals("=") == true) {
						stok = stok + "ฌ";
						continue;
					}
					if (stt.equals("\\") == true) {
						stok = stok + "";
						continue;
					}
					if (stt.equals("\"") == true) {
						stok = stok + "๊";
						continue;
					}
					if (stt.equals("<") == true) {
						stok = stok + "ฤ";
						continue;
					}
					if (stt.equals(">") == true) {
						stok = stok + "ๆ";
						continue;
					}
					if (stt.equals("P") == true) {
						stok = stok + "ญ";
						continue;
					}
					if (stt.equals("Y") == true) {
						stok = stok + "ษ";
						continue;
					}
					if (stt.equals("F") == true) {
						stok = stok + "ึ";
						continue;
					}
					if (stt.equals("G") == true) {
						stok = stok + "ฝ";
						continue;
					}
					if (stt.equals("C") == true) {
						stok = stok + "ซ";
						continue;
					}
					if (stt.equals("R") == true) {
						stok = stok + "ถ";
						continue;
					}
					if (stt.equals("L") == true) {
						stok = stok + "ฒ";
						continue;
					}
					if (stt.equals("?") == true) {
						stok = stok + "ฯ";
						continue;
					}
					if (stt.equals("+") == true) {
						stok = stok + "ฦ";
						continue;
					}
					if (stt.equals("|") == true) {
						stok = stok + "ํ";
						continue;
					}
					if (stt.equals("a") == true) {
						stok = stok + "้";
						continue;
					}
					if (stt.equals("o") == true) {
						stok = stok + "ท";
						continue;
					}
					if (stt.equals("e") == true) {
						stok = stok + "ง";
						continue;
					}
					if (stt.equals("u") == true) {
						stok = stok + "ก";
						continue;
					}
					if (stt.equals("i") == true) {
						stok = stok + "ั";
						continue;
					}
					if (stt.equals("d") == true) {
						stok = stok + "ี";
						continue;
					}
					if (stt.equals("h") == true) {
						stok = stok + "า";
						continue;
					}
					if (stt.equals("t") == true) {
						stok = stok + "น";
						continue;
					}
					if (stt.equals("n") == true) {
						stok = stok + "เ";
						continue;
					}
					if (stt.equals("s") == true) {
						stok = stok + "ไ";
						continue;
					}
					if (stt.equals("-") == true) {
						stok = stok + "ข";
						continue;
					}
					if (stt.equals("A") == true) {
						stok = stok + "๋";
						continue;
					}
					if (stt.equals("O") == true) {
						stok = stok + "ธ";
						continue;
					}
					if (stt.equals("E") == true) {
						stok = stok + "ำ";
						continue;
					}
					if (stt.equals("U") == true) {
						stok = stok + "ณ";
						continue;
					}
					if (stt.equals("I") == true) {
						stok = stok + "์";
						continue;
					}
					if (stt.equals("D") == true) {
						stok = stok + "ื";
						continue;
					}
					if (stt.equals("H") == true) {
						stok = stok + "ผ";
						continue;
					}
					if (stt.equals("T") == true) {
						stok = stok + "ช";
						continue;
					}
					if (stt.equals("N") == true) {
						stok = stok + "โ";
						continue;
					}
					if (stt.equals("S") == true) {
						stok = stok + "ฆ";
						continue;
					}
					if (stt.equals("_") == true) {
						stok = stok + "ฑ";
						continue;
					}
					if (stt.equals(";") == true) {
						stok = stok + "บ";
						continue;
					}
					if (stt.equals("q") == true) {
						stok = stok + "ป";
						continue;
					}
					if (stt.equals("j") == true) {
						stok = stok + "ล";
						continue;
					}
					if (stt.equals("k") == true) {
						stok = stok + "ห";
						continue;
					}
					if (stt.equals("x") == true) {
						stok = stok + "ิ";
						continue;
					}
					if (stt.equals("b") == true) {
						stok = stok + "ค";
						continue;
					}
					if (stt.equals("m") == true) {
						stok = stok + "ส";
						continue;
					}
					if (stt.equals("w") == true) {
						stok = stok + "ะ";
						continue;
					}
					if (stt.equals("v") == true) {
						stok = stok + "จ";
						continue;
					}
					if (stt.equals("z") == true) {
						stok = stok + "พ";
						continue;
					}
					if (stt.equals(":") == true) {
						stok = stok + "ฎ";
						continue;
					}
					if (stt.equals("Q") == true) {
						stok = stok + "ฏ";
						continue;
					}
					if (stt.equals("J") == true) {
						stok = stok + "ฐ";
						continue;
					}
					if (stt.equals("K") == true) {
						stok = stok + "ภ";
						continue;
					}
					if (stt.equals("X") == true) {
						stok = stok + "ั";
						continue;
					}
					if (stt.equals("B") == true) {
						stok = stok + "ศ";
						continue;
					}
					if (stt.equals("M") == true) {
						stok = stok + "ฮ";
						continue;
					}
					if (stt.equals("W") == true) {
						stok = stok + "ฟ";
						continue;
					}
					if (stt.equals("V") == true) {
						stok = stok + "ฉ";
						continue;
					}
					if (stt.equals("Z") == true) {
						stok = stok + "ฬ";
						continue;
					}

					// jjj

					stok = stok + stt;
				}

				return (stok);

			}
		}

		// . devorok

		// . devorok

		// normal
		// normal
		if (st.length() > 1) {
			// System.out.println("" + st.substring(0,1));
			if (st.substring(0, 1).equalsIgnoreCase(";") == true) {
				String stok = "";

				for (int stl = 1; stl < st.length(); stl++) {
					String stt = st.substring(stl, stl + 1);

					// eeee
					if (stt.equals("1") == true) {
						stok = stok + "ๅ";
						continue;
					}
					if (stt.equals("2") == true) {
						stok = stok + "/";
						continue;
					}
					if (stt.equals("3") == true) {
						stok = stok + "-";
						continue;
					}
					if (stt.equals("4") == true) {
						stok = stok + "ภ";
						continue;
					}
					if (stt.equals("5") == true) {
						stok = stok + "ถ";
						continue;
					}
					if (stt.equals("6") == true) {
						stok = stok + "ุ";
						continue;
					}
					if (stt.equals("7") == true) {
						stok = stok + "ึ";
						continue;
					}
					if (stt.equals("8") == true) {
						stok = stok + "ค";
						continue;
					}
					if (stt.equals("9") == true) {
						stok = stok + "ต";
						continue;
					}
					if (stt.equals("0") == true) {
						stok = stok + "จ";
						continue;
					}
					if (stt.equals("-") == true) {
						stok = stok + "ข";
						continue;
					}
					if (stt.equals("=") == true) {
						stok = stok + "ช";
						continue;
					}
					if (stt.equals("!") == true) {
						stok = stok + "+";
						continue;
					}
					if (stt.equals("@") == true) {
						stok = stok + "๑";
						continue;
					}
					if (stt.equals("#") == true) {
						stok = stok + "๒";
						continue;
					}
					if (stt.equals("$") == true) {
						stok = stok + "๓";
						continue;
					}
					if (stt.equals("%") == true) {
						stok = stok + "๔";
						continue;
					}
					if (stt.equals("^") == true) {
						stok = stok + "ู";
						continue;
					}
					if (stt.equals("&") == true) {
						stok = stok + "฿";
						continue;
					}
					if (stt.equals("*") == true) {
						stok = stok + "๕";
						continue;
					}
					if (stt.equals("(") == true) {
						stok = stok + "๖";
						continue;
					}
					if (stt.equals(")") == true) {
						stok = stok + "๗";
						continue;
					}
					if (stt.equals("_") == true) {
						stok = stok + "๘";
						continue;
					}
					if (stt.equals("+") == true) {
						stok = stok + "๙";
						continue;
					}
					if (stt.equals("q") == true) {
						stok = stok + "ๆ";
						continue;
					}
					if (stt.equals("w") == true) {
						stok = stok + "ไ";
						continue;
					}
					if (stt.equals("e") == true) {
						stok = stok + "ำ";
						continue;
					}
					if (stt.equals("r") == true) {
						stok = stok + "พ";
						continue;
					}
					if (stt.equals("t") == true) {
						stok = stok + "ะ";
						continue;
					}
					if (stt.equals("y") == true) {
						stok = stok + "ั";
						continue;
					}
					if (stt.equals("u") == true) {
						stok = stok + "ี";
						continue;
					}
					if (stt.equals("i") == true) {
						stok = stok + "ร";
						continue;
					}
					if (stt.equals("o") == true) {
						stok = stok + "น";
						continue;
					}
					if (stt.equals("p") == true) {
						stok = stok + "ย";
						continue;
					}
					if (stt.equals("[") == true) {
						stok = stok + "บ";
						continue;
					}
					if (stt.equals("]") == true) {
						stok = stok + "ล";
						continue;
					}
					if (stt.equals("\\") == true) {
						stok = stok + "ฃ";
						continue;
					}
					if (stt.equals("Q") == true) {
						stok = stok + "๐";
						continue;
					}
					if (stt.equals("W") == true) {
						stok = stok + "\"";
						continue;
					}
					if (stt.equals("E") == true) {
						stok = stok + "ฎ";
						continue;
					}
					if (stt.equals("R") == true) {
						stok = stok + "ฑ";
						continue;
					}
					if (stt.equals("T") == true) {
						stok = stok + "ธ";
						continue;
					}
					if (stt.equals("Y") == true) {
						stok = stok + "ํ";
						continue;
					}
					if (stt.equals("U") == true) {
						stok = stok + "๊";
						continue;
					}
					if (stt.equals("I") == true) {
						stok = stok + "ณ";
						continue;
					}
					if (stt.equals("O") == true) {
						stok = stok + "ฯ";
						continue;
					}
					if (stt.equals("P") == true) {
						stok = stok + "ญ";
						continue;
					}
					if (stt.equals("{") == true) {
						stok = stok + "ฐ";
						continue;
					}
					if (stt.equals("}") == true) {
						stok = stok + ",";
						continue;
					}
					if (stt.equals("|") == true) {
						stok = stok + "ฅ";
						continue;
					}
					if (stt.equals("a") == true) {
						stok = stok + "ฟ";
						continue;
					}
					if (stt.equals("s") == true) {
						stok = stok + "ห";
						continue;
					}
					if (stt.equals("d") == true) {
						stok = stok + "ก";
						continue;
					}
					if (stt.equals("f") == true) {
						stok = stok + "ด";
						continue;
					}
					if (stt.equals("g") == true) {
						stok = stok + "เ";
						continue;
					}
					if (stt.equals("h") == true) {
						stok = stok + "้";
						continue;
					}
					if (stt.equals("j") == true) {
						stok = stok + "่";
						continue;
					}
					if (stt.equals("k") == true) {
						stok = stok + "า";
						continue;
					}
					if (stt.equals("l") == true) {
						stok = stok + "ส";
						continue;
					}
					if (stt.equals(";") == true) {
						stok = stok + "ว";
						continue;
					}
					if (stt.equals("'") == true) {
						stok = stok + "ง";
						continue;
					}

					if (stt.equals("A") == true) {
						stok = stok + "ฤ";
						continue;
					}
					if (stt.equals("S") == true) {
						stok = stok + "ฆ";
						continue;
					}
					if (stt.equals("D") == true) {
						stok = stok + "ฏ";
						continue;
					}
					if (stt.equals("F") == true) {
						stok = stok + "โ";
						continue;
					}
					if (stt.equals("G") == true) {
						stok = stok + "ฌ";
						continue;
					}
					if (stt.equals("H") == true) {
						stok = stok + "็";
						continue;
					}
					if (stt.equals("J") == true) {
						stok = stok + "๋";
						continue;
					}
					if (stt.equals("K") == true) {
						stok = stok + "ษ";
						continue;
					}
					if (stt.equals("L") == true) {
						stok = stok + "ศ";
						continue;
					}
					if (stt.equals(":") == true) {
						stok = stok + "ซ";
						continue;
					}
					if (stt.equals("z") == true) {
						stok = stok + "ผ";
						continue;
					}
					if (stt.equals("x") == true) {
						stok = stok + "ป";
						continue;
					}
					if (stt.equals("c") == true) {
						stok = stok + "แ";
						continue;
					}
					if (stt.equals("v") == true) {
						stok = stok + "อ";
						continue;
					}
					if (stt.equals("b") == true) {
						stok = stok + "ิ";
						continue;
					}
					if (stt.equals("n") == true) {
						stok = stok + "ื";
						continue;
					}
					if (stt.equals("m") == true) {
						stok = stok + "ท";
						continue;
					}
					if (stt.equals(",") == true) {
						stok = stok + "ม";
						continue;
					}
					if (stt.equals(".") == true) {
						stok = stok + "ใ";
						continue;
					}
					if (stt.equals("/") == true) {
						stok = stok + "ฝ";
						continue;
					}
					if (stt.equals("Z") == true) {
						stok = stok + "(";
						continue;
					}
					if (stt.equals("X") == true) {
						stok = stok + ")";
						continue;
					}
					if (stt.equals("C") == true) {
						stok = stok + "ฉ";
						continue;
					}
					if (stt.equals("V") == true) {
						stok = stok + "ฮ";
						continue;
					}
					if (stt.equals("B") == true) {
						stok = stok + "ฺ";
						continue;
					}
					if (stt.equals("N") == true) {
						stok = stok + "์";
						continue;
					}
					if (stt.equals("M") == true) {
						stok = stok + "?";
						continue;
					}
					if (stt.equals("<") == true) {
						stok = stok + "ฒ";
						continue;
					}
					if (stt.equals(">") == true) {
						stok = stok + "ฬ";
						continue;
					}
					if (stt.equals("?") == true) {
						stok = stok + "ฦ";
						continue;
					}

					// jjj

					stok = stok + stt;
				}

				return (stok);

			}

		}
		// , normal
		return st;
	}

	public int getfreeselect(Player player) {
		int lp1 = 0;

		@SuppressWarnings("unused")
		Boolean foundza = false;
		// clear select array
		for (lp1 = 0; lp1 < selectmax; lp1++) {

			if (selectname[lp1] == null) {
				selectname[lp1] = "null";
				selectx1[lp1] = 0;
				selecty1[lp1] = 0;
				selectz1[lp1] = 0;
				selectx2[lp1] = 0;
				selecty2[lp1] = 0;
				selectz2[lp1] = 0;

				d4[lp1] = 1;

			}
		} // loop allselect

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

			if (foundza = false) { // clear
				selectname[lp1] = "null";
				selectx1[lp1] = 0;
				selecty1[lp1] = 0;
				selectz1[lp1] = 0;
				selectx2[lp1] = 0;
				selecty2[lp1] = 0;
				selectz2[lp1] = 0;

				d4[lp1] = 1;

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

		System.out.println("ptdew&dewdd: GetFreeselect return -1");
		return -1;
	}

	public Block[] getselectblock(int getid, Player player) {
		int adderB = -1;

		Block blocktemp[] = new Block[20000001];
		// > > >
		if (selectx1[getid] >= selectx2[getid]
				&& selecty1[getid] >= selecty2[getid]
				&& selectz1[getid] >= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : >>>");
			for (int xl = selectx2[getid]; xl <= selectx1[getid]; xl++) {
				for (int yl = selecty2[getid]; yl <= selecty1[getid]; yl++) {
					for (int zl = selectz2[getid]; zl <= selectz1[getid]; zl++) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}

						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}
		}
		// > > <
		if (selectx1[getid] >= selectx2[getid]
				&& selecty1[getid] >= selecty2[getid]
				&& selectz1[getid] <= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : >><");

			for (int xl = selectx2[getid]; xl <= selectx1[getid]; xl++) {
				for (int yl = selecty2[getid]; yl <= selecty1[getid]; yl++) {
					for (int zl = selectz2[getid]; zl >= selectz1[getid]; zl--) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}
		}
		// > < >
		if (selectx1[getid] >= selectx2[getid]
				&& selecty1[getid] <= selecty2[getid]
				&& selectz1[getid] >= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : ><>");
			for (int xl = selectx2[getid]; xl <= selectx1[getid]; xl++) {
				for (int yl = selecty2[getid]; yl >= selecty1[getid]; yl--) {
					for (int zl = selectz2[getid]; zl <= selectz1[getid]; zl++) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}
		}
		// > < <
		if (selectx1[getid] >= selectx2[getid]
				&& selecty1[getid] <= selecty2[getid]
				&& selectz1[getid] <= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : ><<");
			for (int xl = selectx2[getid]; xl <= selectx1[getid]; xl++) {
				for (int yl = selecty2[getid]; yl >= selecty1[getid]; yl--) {
					for (int zl = selectz2[getid]; zl >= selectz1[getid]; zl--) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}
		}

		// < > >
		if (selectx1[getid] <= selectx2[getid]
				&& selecty1[getid] >= selecty2[getid]
				&& selectz1[getid] >= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : <>>");
			for (int xl = selectx2[getid]; xl >= selectx1[getid]; xl--) {
				for (int yl = selecty2[getid]; yl <= selecty1[getid]; yl++) {
					for (int zl = selectz2[getid]; zl <= selectz1[getid]; zl++) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}

		}

		// < > <
		if (selectx1[getid] <= selectx2[getid]
				&& selecty1[getid] >= selecty2[getid]
				&& selectz1[getid] <= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : <><");
			for (int xl = selectx2[getid]; xl >= selectx1[getid]; xl--) {
				for (int yl = selecty2[getid]; yl <= selecty1[getid]; yl++) {
					for (int zl = selectz2[getid]; zl >= selectz1[getid]; zl--) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}
		}

		// < < >
		if (selectx1[getid] <= selectx2[getid]
				&& selecty1[getid] <= selecty2[getid]
				&& selectz1[getid] >= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd : <<>");
			for (int xl = selectx2[getid]; xl >= selectx1[getid]; xl--) {
				for (int yl = selecty2[getid]; yl >= selecty1[getid]; yl--) {
					for (int zl = selectz2[getid]; zl <= selectz1[getid]; zl++) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}
		}

		// < < <
		if (selectx1[getid] <= selectx2[getid]
				&& selecty1[getid] <= selecty2[getid]
				&& selectz1[getid] <= selectz2[getid]) {
			player.sendMessage("ptdew&dewdd :<<<");
			for (int xl = selectx2[getid]; xl >= selectx1[getid]; xl--) {
				for (int yl = selecty2[getid]; yl >= selecty1[getid]; yl--) {
					for (int zl = selectz2[getid]; zl >= selectz1[getid]; zl--) {
						Block blb = player.getWorld().getBlockAt(xl, yl, zl);
						if (blb == null) {
							continue;
						}

						if (isprotectitemid(blb.getTypeId()) == true) {
							continue;
						}
						adderB++;
						blocktemp[adderB] = blb;
					}
				}
			}

		}

		Block blockmain[] = new Block[adderB + 1];
		int adderc = 0;

		for (adderc = 0; adderc <= adderB; adderc++) {
			blockmain[adderc] = blocktemp[adderc];
		}

		return blockmain;

	} // getselectblock

	public boolean isadminname(String name) {
		for (int i = 0; i <= adminlistadminmax; i++) {
			if (name.equalsIgnoreCase(adminlistadmin[i]) == true) {
				return true;
			}
		}

		return false;
	}

	public boolean isprotectitemid(int importid) {
		switch (importid) {
		case 7: // bedrock
		case 54: // chest
		case 43: // coblestone
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
		case 323: // sign

			return true;
		default:
			return false;
		}
	}

	public boolean isptdewadminname(String name) {

		if (name.equalsIgnoreCase("ptdew") == true) {
			return true;
		}

		return false;
	}

	public boolean issubadminname(String name, String signname) {
		if (signname.equalsIgnoreCase("admin") == false) {
			return false;
		}

		for (int i = 0; i <= adminlistadminmax; i++) {
			if (name.equalsIgnoreCase(adminlistadmin[i]) == true) {
				return true;
			}
		}
		return issubsubadminname(name);
	}

	public boolean issubsubadminname(String name) {

		for (int i = 0; i <= adminliststaffmax; i++) {
			if (name.equalsIgnoreCase(adminliststaff[i]) == true) {
				return true;
			}
		}

		return false;
	}

	public boolean isunsortid(ItemStack impo) {
		if (impo.getType().getMaxDurability() > 0) {
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

			return true;
		default:
			return false;
		}
	}

	public void loadadminfile() {

		try {

			System.out
					.println("ptdeW&DewDD Main : Starting Loading ptdew&dewddadmin.txt");
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					"ptdew&dewddadmin.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			adminlistadminmax = -1;
			adminlistadmin = new String[20];
			adminliststaffmax = -1;
			adminliststaff = new String[20];

			// sthae
			// aosthoeau
			// * save
			String nowmode = "";

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.equalsIgnoreCase("*") == true) {
					nowmode = "admin";
					adminlistadminmax++;
				}
				else if (strLine.equalsIgnoreCase("**") == true) {
					nowmode = "staff";
					adminliststaffmax++;
				}
				else {
					if (nowmode.equalsIgnoreCase("admin") == true) {
						adminlistadmin[adminlistadminmax] = strLine;
						System.out.println("admin " + adminlistadminmax + " = "
								+ adminlistadmin[adminlistadminmax]);
					}
					else if (nowmode.equalsIgnoreCase("staff") == true) {
						adminliststaff[adminliststaffmax] = strLine;
						System.out.println("staff " + adminliststaffmax + " = "
								+ adminliststaff[adminliststaffmax]);
					}
					else {
						System.out
								.println("error while loading admin file ... * , **  nor this");
					}
				}
			}

			System.out
					.println("ptdew&DewDD AutoMessage: Loaded dewdd admin.txt");

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error: loading admin file" + e.getMessage());
		}
	}

	public int randomnumberint() {
		int randomInt = 0;
		randomInt = randomGenerator.nextInt(70);
		randomInt += 1;
		return randomInt;
	}
}

public class DigEventListener2 implements Listener {

	Dewminecraft	dew	= new Dewminecraft();

	@EventHandler(priority = EventPriority.LOWEST)
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		event.setMessage(dew.engtothai(event.getMessage()));

		player.setAllowFlight(true);

		if (event.getMessage().equalsIgnoreCase("dewreloadadminfile") == true) {
			dew.loadadminfile();
			player.sendMessage("ptdew&dewdd: Reloaded admin File");
			return;
		}

		if (event.getMessage().substring(0, 3).equalsIgnoreCase("go ") == true) {

			String na = event.getMessage().substring(3,
					event.getMessage().length());
			na = na.toLowerCase();
			player.sendMessage("go '" + na + "'");

			for (Player pp : Bukkit.getOnlinePlayers()) {
				if (pp.getName().toLowerCase().startsWith(na) == true) {
					player.teleport(pp.getLocation());
					player.sendMessage("ptdew&dewdd: teleported");
					break;
				}
			}

			player.sendMessage("ptdew&dewdd: can't find that player for teleport");
		}

		if (event.getMessage().equalsIgnoreCase("sethome") == true) {
			player.setBedSpawnLocation(player.getLocation(), true);
			player.sendMessage("ptdew&dewdd: sethome done");
			return;
		}
		if (event.getMessage().equalsIgnoreCase("home") == true) {
			player.teleport(player.getBedSpawnLocation());
			player.sendMessage("ptdew&dewdd: home done");
			return;
		}

		// player.sendMessage("leng" + event.getMessage().length());
		if (event.getMessage().length() == 22) {
			// String e0 = event.getMessage().substring(0, 7);
			// player.sendMessage( "e0 '" + e0 + "'");

			String e1 = event.getMessage().substring(7, 10);
			// player.sendMessage( "e1 '" + e1 + "'");

			String e2 = event.getMessage().substring(11, 14);
			// player.sendMessage( "e2 '" + e2 + "'");

			String e3 = event.getMessage().substring(15, 18);
			// player.sendMessage( "e3 '" + e3 + "'");

			String e4 = event.getMessage().substring(19, 22);
			// player.sendMessage( "e4 '" + e4 + "'");

			dew.dewset(player, Integer.parseInt(e1), Byte.parseByte(e2),
					Integer.parseInt(e3), Byte.parseByte(e4));
			event.setCancelled(true);
			return;
		}

		if (event.getMessage().equalsIgnoreCase("dewset") == true) {
			if (player.getItemInHand() == null) {
				player.sendMessage("ptdew&dewdd: 'dewset' your iteminhand == null");
				return;
			}
			if (player.getItemInHand().getType().isBlock() == false) {
				player.sendMessage("ptdew&dewdd:dewset item "
						+ player.getItemInHand().getType().name()
						+ " is not a block");
				return;
			}
			dew.dewset(player, -29, (byte) -29, player.getItemInHand()
					.getTypeId(), player.getItemInHand().getData().getData());
			event.setCancelled(true);
			return;
		}

		// dewfill
		if (event.getMessage().equalsIgnoreCase("dewfill") == true) {
			dew.dewfill(player);
			event.setCancelled(true);
			return;
		}

		// dewsetwall
		if (event.getMessage().equalsIgnoreCase("dewsetwall") == true) {
			dew.dewsetwall(player);
			event.setCancelled(true);
			return;
		}

		// dewfillwall
		if (event.getMessage().equalsIgnoreCase("dewfillwall") == true) {
			dew.dewfillwall(player);
			event.setCancelled(true);
			return;
		}

		// dewfullcircle
		if (event.getMessage().equalsIgnoreCase("dewfullcircle") == true) {
			dew.dewfullcircle(player);
			event.setCancelled(true);
			return;
		}

		for (World world : Bukkit.getWorlds()) {
			world.save();
			for (Player pla : world.getPlayers()) {
				pla.saveData();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void eventja(BlockDamageEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (player.getItemInHand() == null) {
			return;
		}
		/*
		 * if (player.getItemInHand().getType().getMaxDurability() > 0) {
		 * 
		 * 
		 * if (player.getItemInHand().getType().getMaxDurability() ==
		 * player.getItemInHand().getDurability()){
		 * player.setHealth((player.getHealth()-2);
		 * 
		 * player.sendMessage("ptdew&dewdd:oh no my item broke");
		 * return;
		 * }
		 * 
		 * if (player.getItemInHand().getDurability() >
		 * (player.getItemInHand().getType().getMaxDurability() *0.5)){
		 * switch (player.getItemInHand().getTypeId()){
		 * case 256:case 257:case 258:case 267:case 292: // iron
		 * //player.sendMessage("ptdew&dewdd : iron item");
		 * int xx = player.getInventory().first(15);
		 * 
		 * 
		 * //player.sendMessage("ptdew&dewdd : adder dura");
		 * 
		 * if (player.getInventory().getItem(xx).getAmount()> 1){
		 * player.getInventory().getItem(xx).setAmount(player.getInventory().getItem
		 * (xx).getAmount()-1);
		 * player.getItemInHand().setDurability((short) 0);
		 * }
		 * break;
		 * case 291:case 272:case 273:case 274:case 275: //stone
		 * xx = player.getInventory().first(4);
		 * 
		 * if (player.getInventory().getItem(xx).getAmount()> 1){
		 * player.getInventory().getItem(xx).setAmount(player.getInventory().getItem
		 * (xx).getAmount()-1);
		 * player.getItemInHand().setDurability((short) 0);
		 * }
		 * break;
		 * case 276:case 277:case 278:case 279:case 293: // diamond
		 * xx = player.getInventory().first(264);
		 * 
		 * 
		 * if (player.getInventory().getItem(xx).getAmount()> 1){
		 * player.getInventory().getItem(xx).setAmount(player.getInventory().getItem
		 * (xx).getAmount()-1);
		 * player.getItemInHand().setDurability((short) 0);
		 * }
		 * break;
		 * case 294:case 283:case 284:case 285:case 286: // gold
		 * xx = player.getInventory().first(14);
		 * 
		 * 
		 * if (player.getInventory().getItem(xx).getAmount()> 1){
		 * player.getInventory().getItem(xx).setAmount(player.getInventory().getItem
		 * (xx).getAmount()-1);
		 * player.getItemInHand().setDurability((short) 0);
		 * }
		 * break;
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * switch (player.getItemInHand().getTypeId()){
		 * case 267:case 268:case 272:case 276:
		 * return;
		 * }
		 * block.breakNaturally(player.getItemInHand());
		 * 
		 * 
		 * player.getItemInHand().setDurability((short)
		 * (player.getItemInHand().getDurability()+1));
		 * 
		 * 
		 * }
		 * if (player.getItemInHand().getDurability() <= 0) {
		 * player.getItemInHand().setDurability((short) 1);
		 * }
		 */
		/*
		 * if (block.getLightLevel()<7) {
		 * player.setHealth(player.getHealth()-2);
		 * player.sendMessage("ptdew&dewdd:oh no that block is dark");
		 * }
		 */

	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {

		Action act;
		act = event.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)) {
			return;
		}

		Player player = event.getPlayer();

		Block block = event.getClickedBlock();

		if (block.getTypeId() == 54) {
			dew.autosortchest(block, player);
			return;

		}

		if (player.getItemInHand().getTypeId() == 290
				&& act == Action.LEFT_CLICK_BLOCK) {
			// set x1y1z1
			int getid = dew.getfreeselect(player);
			dew.selectx1[getid] = block.getX();
			dew.selecty1[getid] = block.getY();
			dew.selectz1[getid] = block.getZ();
			int countblock = (int) (Math.abs(1 + dew.selectx1[getid]
					- dew.selectx2[getid]))
					* (int) (Math.abs(1 + dew.selecty1[getid]
							- dew.selecty2[getid]))
					* (int) (Math.abs(1 + dew.selectz1[getid]
							- dew.selectz2[getid]));

			player.sendMessage("ptdew&dewdd: Block 1 = (" + dew.selectx1[getid]
					+ "," + dew.selecty1[getid] + "," + dew.selectz1[getid]
					+ ") to (" + dew.selectx2[getid] + ","
					+ dew.selecty2[getid] + "," + dew.selectz2[getid] + ") = "
					+ countblock);
			event.setCancelled(true);
			return;
		}

		if (player.getItemInHand().getTypeId() == 290
				&& act == Action.RIGHT_CLICK_BLOCK) {
			// set x1y1z1

			int getid = dew.getfreeselect(player);
			dew.selectx2[getid] = block.getX();
			dew.selecty2[getid] = block.getY();
			dew.selectz2[getid] = block.getZ();
			int countblock = (int) (Math.abs(1 + dew.selectx1[getid]
					- dew.selectx2[getid]))
					* (int) (Math.abs(1 + dew.selecty1[getid]
							- dew.selecty2[getid]))
					* (int) (Math.abs(1 + dew.selectz1[getid]
							- dew.selectz2[getid]));

			player.sendMessage("ptdew&dewdd: Block 2 = (" + dew.selectx1[getid]
					+ "," + dew.selecty1[getid] + "," + dew.selectz1[getid]
					+ ") to (" + dew.selectx2[getid] + ","
					+ dew.selecty2[getid] + "," + dew.selectz2[getid] + ") = "
					+ countblock);
			event.setCancelled(true);
		}

	}

} // class

