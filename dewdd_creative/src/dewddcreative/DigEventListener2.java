/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddcreative;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

class Dewminecraft extends dewddflower.dewset {
	public String	perdewremove		= "dewdd.creative.dewremove";
	public String	perchangehost		= "dewdd.creative.changehost";
	public String	perdoprotecty		= "dewdd.creative.doprotecty";
	public String	perdounprotecty		= "dewdd.creative.dounprotecty";
	public String	peroveride			= "dewdd.creative.overide";
	public String	perdewseteverywhere	= "dewdd.creative.dewseteverywhere";

	public int		min_b				= -20000;
	public int		max_b				= 19900;

	public Dewminecraft() {
		loaddewsetlistblockfile();
	}

	public boolean canbuild(int x, int y, int z, Player player, boolean isdewset) {

		if (player.getWorld().getName().equalsIgnoreCase("world_the_end") == true) {
			return true;
		}

		int zx = 5;
		int zz = 5;

		boolean foundza = false;

		for (int gx = min_b; gx <= max_b; gx += 100) {
			for (int gz = min_b; gz <= max_b; gz += 100) {
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
			player.sendMessage("out of " + (-min_b) + " area");
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
				Sign sign = (Sign) player.getWorld().getBlockAt(zx, 254, zz)
						.getState();
				if (sign.getLine(1).equalsIgnoreCase(player.getName()) == false
						&& player.hasPermission(perdoprotecty) == false) {
					return false;
				}
			}
			else { // do any thing at protecty not protect
				if (player.hasPermission(perdounprotecty) == false) {
					return false;
				}
			}
		}

		for (int cx = zx; (cx <= zx + 99); cx++) { // loop sign
			if (player.getWorld().getBlockAt(cx, 254, zz).getTypeId() == 63) { // found
																				// sign
				Sign sign = (Sign) player.getWorld().getBlockAt(cx, 254, zz)
						.getState();
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

		if (player.getWorld().getBlockAt(zx, 254, zz).getTypeId() != 63) { // don't
																			// have
																			// sign
			// allow dewset un protect zone for op
			if (isdewset == true
					&& player.hasPermission(perdewseteverywhere) == true) {
				return true;
			}
			else if (isdewset == false) { // what?
				return true;

			}
		} // not found sign

		return false;
		// not found

		// 0 , 0 to 100 , 100
	}

	public boolean checkpermissionarea(Block block, Player player,
			String modeevent) {
		return !canbuild(block.getX(), block.getY(), block.getZ(), player, true);
	}

	public boolean decreseitem1(Player player, int itemid, int itemdata,
			boolean forcetruedata) {
		return true;
	}

	public boolean decreseitem2(Player player, int itemid, int itemdata,
			boolean forcetruedata) {
		ItemStack itm = null;
		int len = player.getInventory().getContents().length;
		int lenl = 0;

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

		dprint.r.printAll("ptdew&dewdd : '" + player.getName()
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
			if (decreseitem2(player, handid, handdata, true) == false) {
				player.sendMessage("ptdew&dewdd: dont have enough item");
				return;
			}
			blb.setTypeIdAndData(handid, handdata, true);
			//
		} // for

		dprint.r.printAll("ptdew&dewdd: dewfullcircle done : "
				+ player.getName());
	}

	public void dewselectprotect(Player player) {

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
							if (decreseitem2(player, handid, handdata, true) == false) {
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
						// dprint.r.printAll("ptdew&dewdd: delete near call sub ("
						// +
						// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") "
						// + amount);

						bd.add(b2);

					}

				}

			}

		}// bll

		player.sendMessage("dewspreadcircle done");
	}

	public void dewspreadq(Player player) {
		if (isdewset(player.getItemInHand().getTypeId()) == false) {
			player.sendMessage("ptdew&dewdd: error  not allow this item for dewset");
			return;
		}

		int handid = player.getItemInHand().getTypeId();
		byte handdata = player.getItemInHand().getData().getData();

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
				if (checkpermissionarea(b3, player, "build") == true) {
					continue;
				}
				if (decreseitem2(player, handid, handdata, true) == false) {
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
					// dprint.r.printAll("ptdew&dewdd: delete near call sub (" +
					// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") " +
					// amount);

					bd.add(b2);

				}

			}

		}// bll

		player.sendMessage("dewspreadq done");
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
				if (decreseitem2(player, handid, handdata, true) == false) {
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
					// dprint.r.printAll("ptdew&dewdd: delete near call sub (" +
					// b2.getX() + "," + b2.getY() + "," + b2.getZ() + ") " +
					// amount);

					bd.add(b2);

				}

			}

		}// bll

		player.sendMessage("dewspreadq done");
	}

	public void gotofreezone(Player player) {

		Block e = null;

		int x;
		int z;

		boolean cd = false;

		do {

			x = this.randomG.nextInt(10000) - 5000;
			z = this.randomG.nextInt(10000) - 5000;
			e = player.getLocation().getWorld().getBlockAt(x, 70, z);
			cd = isprotectedarea(e);

		}
		while (cd == false);

		dprint.r.printAll("ptdew&dewdd: Found free zone at (" + x + ",?," + z
				+ ")");
		e.getChunk().load();
		player.teleport(e.getLocation());

	}

	public boolean isadminname(String playername) {
		return true;
	}

	public boolean isprotectedarea(Block block) {
		if (block.getWorld().getName().equalsIgnoreCase("world_the_end") == true) {
			return true;
		}

		int zx = 5;
		int zz = 5;
		int x = 0;
		int z = 0;

		boolean foundza = false;

		for (int gx = min_b; gx <= max_b; gx += 100) {
			for (int gz = min_b; gz <= max_b; gz += 100) {
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

public class DigEventListener2 implements Listener {
	class chatx implements Runnable {
		Player		player	= null;
		String		message	= "";
		String[]	m		= message.split("\\s+");

		Boolean		canc	= false;

		@Override
		public void run() {
			m = message.split("\\s+");
			if (message.equalsIgnoreCase("dewdd help") == true) {
				player.sendMessage(">dewdd main");
				canc = true;
			}

			if (m[0].equalsIgnoreCase("dslb")) {
				dew.showdewsetlistblock();
				return;
			}

			if (m[0].equalsIgnoreCase("dslb2")) {
				dew.showdewsetlistblock(player);
				return;
			}

			if (message.equalsIgnoreCase("dewdd main") == true) {
				player.sendMessage("ปลั๊กอิน main เป็นปลั๊กอินแรกของเรา   ตอนแรกสร้างขึ้นเพื่อเป็นระบบโพเทค ป้าย แต่มันทำงานช้า > จึงเปลี่ยนมาเป็น"
						+ "โพเทค วงกลม  แต่มันกำหนดเขตยาก จึงเปลี่ยนมาเป็น โพเทคลูกบาศก์");
				player.sendMessage("ปลั๊กอินนี้ มีรายละเอียดเยอะที่สุด  รายระเอียดมีเยอะลองเอง");
				player.sendMessage(">>dewdd flower");

				canc = true;
			}

			if (message.equalsIgnoreCase("loadmainfile") == true) {
				dprint.r.printAll("loading main file");
				dew.loadmainfile();
				dprint.r.printAll("loaded main file");
			}

			if (message.equalsIgnoreCase("dewdd flower") == true) {
				player.sendMessage(">dewdd flower");
				player.sendMessage("dewdd flower ระบบ ดอกไม้   เป็นการ  กำหนดเขตด้วย Wooden hoe  คลิกซ้ายที่หนึ่ง ขวาอีกที่  (เหมือนขวานไม้ world edit)");
				player.sendMessage("จากนั้น ให้พิมพ์คำสั่ง ซึ่งมีหลายคำสั่งด้วยกัน");
				player.sendMessage(".dewbuy   เป็นการซื้อที่ดิน ซื้อโพเทคตามเขตที่กำหนด");
				player.sendMessage(".dewset   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแทนที่ทับทุกสิ่งในนั้น");
				player.sendMessage(".dewfill   ถือบล็อคแล้วพิมพ์ จะวางบล็อค เติม ในพื้นที่นั้น จนเต็ม");
				player.sendMessage(".dewsetwall   ถือบล็อคแล้วพิมพ์ เป็นการสร้างกำแพง โดยทับกำแพง");
				player.sendMessage(".dewfillwall  ถือบล็อคแล้วพิมพ์ เป็นการสร้างกำแพง โดยเติมบล็อคที่ขาดไป");

				player.sendMessage(".dewsetblock   ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้องมีรู  โดยทับกำแพง");
				player.sendMessage(".dewfillblock  ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้องมีรู โดยเติมบล็อคที่ขาดไป");
				player.sendMessage(".dewsetroom   ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้องมี โดยทับกำแพง");
				player.sendMessage(".dewfillroom  ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้อง โดยเติมบล็อคที่ขาดไป");

				player.sendMessage(".dewdelete   ถือบล็อคแล้วพิมพ์ เป็นการลบบล็อคที่ตรงกับที่เราถือ  (ใช้ได้ในเขตโพเทคที่ตัวเองมีสิทธิ)");
				player.sendMessage(".dewfullcircle   ถือบล็อคแล้วพิมพ์ จะเป็นการวาดวงกลม ในพื้นที่");
				player.sendMessage(".dewspread4   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแบบแพร่กระจาย สี่ทิศ");
				player.sendMessage(".dewspread9   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแบบแพร่กระจาย 9 ทิศ");
				player.sendMessage(".dewcopy   ไปยืนที่ต้องการแล้วพิมพ์ จะเป็นการคัดลอกมาวาง");
				player.sendMessage(".dewspreadcircle   ไว้แพร่กระจายบล็อคไปรอบทิศจากตัวเรา  ระวังโดนหนีบ");
				player.sendMessage(".dewspreadq   ระบบแพ่รกระจายบล็อคในชั้นที่เรายืนแบบใหม่ คาดว่าจะทำงานเร็วขึ้น");
				player.sendMessage(".dewa   ยืนบนบล็อคมรกต    บล็อคเพชร 1 บล็อคติดรอบ บล็อคมรกตเพื่อกำหนดทิศ   จากนั้นพิมพ์   จำนวนของในมือ"
						+ " เป็นจำนวนครั้งในการ ขยายสิ่งของ ไปในทิศที่ต้องการ ทิศตามบล็อคเพชร    ถ้าอยากยืดข่้นฟ้า  ไม่ต้องมีบล็อคเพชร");

				canc = true;
			}

			if (message.equalsIgnoreCase("pmain") == true) {

				player.sendMessage("overide = "
						+ Boolean.toString(player
								.hasPermission(dew.pmainoveride)));
				player.sendMessage("dewbuy replace = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewbuyreplace)));
				player.sendMessage("dewbuy notcount = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewbuynotcount)));

				player.sendMessage("dew modify member = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewbuymodifymember)));

				player.sendMessage("dewbuy changehost = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewbuydelete)));
				player.sendMessage("dewbuy delete = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewbuydelete)));
				player.sendMessage("dew modify member = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewbuymodifymember)));

				player.sendMessage("dew dewset everywhere = "
						+ Boolean.toString(player
								.hasPermission(dew.pmaindewseteverywhere)));

				return;

			}

			// ***********************

			dew.hideshowrun(player);

			int idc = dew.getfreeselect(player);
			if (dew.chatever[idc] == false) {
				player.sendMessage("ptdew&dewdd:please drop item one time, before chat");
				player.sendMessage("ptdew&dewdd:กรุณาทิ้งของลงพื้น ก่อนแชทกับผู้อื่นครับ");
				canc = true;
				return;
			}

			// cleardrop

			// dewdeleteblock

			String strword = "";

			strword = message;

			if (message.equalsIgnoreCase("dewreloadworldfile") == true) {
				dew.loadworldfile();
				return;
			}

			// dewreloadsignfile
			if (message.equalsIgnoreCase("dewreloadsignfile") == true) {
				dew.loadsignfile();
				player.sendMessage("ptdew&dewdd: Reloaded Sign File");
				return;
			}

			// savesignfile
			if (message.equalsIgnoreCase("dewsavesignfile") == true) {
				dew.savesignfile(-1,
						dew.getworldid(player.getWorld().getName()));
				player.sendMessage("ptdew&dewdd: Saved Sign File");
				return;
			}

			if (message.equalsIgnoreCase("dewaxe") == true
					|| message.equalsIgnoreCase("dew_axe") == true) {

				dew.dewaxe[idc] = !dew.dewaxe[idc];
				player.sendMessage("ptdew&dewdd : '" + player.getName()
						+ "' dewaxe (super axe) = "
						+ Boolean.toString(dew.dewaxe[idc]));
				dprint.r.printC("ptdew&dewdd : '" + player.getName()
						+ "' dewaxe (super axe) = "
						+ Boolean.toString(dew.dewaxe[idc]));
				canc = true;
			}

			dew.linkurl(player, message);

			/*
			 * if (player.getItemInHand().getTypeId() == 46) { canc = true;
			 * return; }
			 */

			if (message.equalsIgnoreCase("select[]") == true) {
				for (int lp1 = 0; lp1 < dew.selectmax; lp1++) {
					player.sendMessage(lp1 + "=" + dew.selectname[lp1]);
				}
				canc = true;
			}

			if (m[0].equalsIgnoreCase("gift") == true) {
				int a1 = 5;
				int a2 = -29;
				int a3 = 0; // amount

				if (m.length == 1) { // gift
					dew.gift(player, player.getItemInHand().getTypeId(), player
							.getItemInHand().getData().getData());
				}
				else if (m.length == 2) { // gift 5:1 , gift cobblestone:?
					String[] m2 = m[1].split(":");

					int itemid = 0;
					byte dataid = 0;

					player.sendMessage("m2[0] = " + m2[0] + " search.. = "
							+ dew.getmaterialrealname(m2[0]));

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							itemid = Material.getMaterial(
									Integer.parseInt(m2[0])).getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							itemid = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					if (m2.length == 2) {
						dataid = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + itemid + ", dataid = "
							+ dataid);
					dew.gift(player, itemid, dataid);
				}
			}

			/*
			 * if (message.equalsIgnoreCase("checkhome") == true) {
			 * 
			 * int co = 0;
			 * 
			 * for (int wla = 0; wla < dew.dewworldlistmax + 1; wla++) {
			 * 
			 * for (int ie = 0; ie < dew.dewsignmax[wla]; ie++) {
			 * dprint.r.printAll("" + ie);
			 * if (ie != dew.getworldid(player.getLocation()
			 * .getWorld().getName())) {
			 * continue;
			 * }
			 * 
			 * boolean stafffound = false;
			 * boolean memberfound = false;
			 * 
			 * for (String whoin : dew.dewsignname[wla][ie]) {
			 * co++;
			 * 
			 * // dprint.r.printAll(whoin);
			 * if (api_admin.dewddadmin.issubsubadminname(whoin) == true) {
			 * stafffound = true;
			 * dprint.r.printAll("staff found +" + whoin);
			 * 
			 * }
			 * 
			 * if (api_admin.dewddadmin.issubsubadminname(whoin) == false
			 * && api_admin.dewddadmin.isadminname(whoin) == false
			 * && whoin.startsWith("<") == false
			 * && whoin.equalsIgnoreCase("everyone") == false
			 * && whoin.equalsIgnoreCase("null") == false) {
			 * memberfound = true;
			 * dprint.r.printAll("member = " + whoin);
			 * }
			 * }
			 * 
			 * // dprint.r.printAll(Boolean.toString(stafffound) + ", "
			 * // +
			 * // Boolean.toString(memberfound) );
			 * if (stafffound == true && memberfound == true) {
			 * player.sendMessage("/* id home = " + ie);
			 * player.sendMessage("this home staff live with member!!!");
			 * player.sendMessage("world = " + wla);
			 * 
			 * for (String ab : dew.dewsignname[wla][ie]) {
			 * player.sendMessage(ab);
			 * }
			 * 
			 * player.sendMessage("xyz1=" + dew.dewsignx1[wla][ie]
			 * + "," + dew.dewsigny1[wla][ie] + ","
			 * + dew.dewsignz1[wla][ie]);
			 * player.sendMessage("xyz2=" + dew.dewsignx2[wla][ie]
			 * + "," + dew.dewsigny2[wla][ie] + ","
			 * + dew.dewsignz2[wla][ie]);
			 * player.sendMessage("/");
			 * return;
			 * }
			 * 
			 * } // all sign in world
			 * 
			 * } // loop all world
			 * dprint.r.printAll("" + co);
			 * 
			 * dprint.r.printAll("check home done");
			 * canc = true;
			 * return;
			 * 
			 * } // check home
			 */
			// dewbuy

			// dewbuyzone
			if (message.equalsIgnoreCase("dewbuyzone") == true
					|| message.equalsIgnoreCase("dbz") == true) {
				dew.dewbuyzone(player, player.getLocation().getBlock());
				canc = true;
				return;
			}

			// dewbuyremove
			if (message.equalsIgnoreCase("dewbuyremove") == true
					|| message.equalsIgnoreCase("dbr") == true) {

				dew.dewbuyremove(player);

				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("dewextend") == true
					|| message.equalsIgnoreCase("de") == true) {
				dew.dewextend(player);
				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("dewselectprotect") == true) {
				dew.dewselectprotect(player);
				canc = true;
				return;
			}

			if (m[0].equalsIgnoreCase("dewselectcube") == true) {
				if (m.length == 1) {

					player.sendMessage("item on your hand = radius  = "
							+ player.getItemInHand().getAmount());
					dew.dewselectcube(player, player.getItemInHand()
							.getAmount());
					canc = true;
					return;

				}
				else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("only number please");
						return;
					}

					player.sendMessage("argument 2 = radius  = " + m[1]);
					dew.dewselectcube(player, Integer.parseInt(m[1]));
					canc = true;
					return;
				}
			}

			// dewinvert

			if (message.equalsIgnoreCase("dewtime") == true) {
				dew.dewtime(player);
				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("time") == true) {

				for (int c = 0; c <= dew.timechunkmax; c++) {
					dprint.r.printAll("dewtime list [" + c + "] = "
							+ dew.timechunkx[c] + "," + dew.timechunkz[c]);
				}

				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("cleartime") == true) {
				dew.timechunkx = new int[10000];
				dew.timechunkz = new int[10000];

				dew.timechunkmax = -1;
				dprint.r.printAll("dewtime cleared");

				canc = true;
				return;
			}

			// dewfillwall

			// dewfly อยากบินได้
			if (message.equalsIgnoreCase("dewfly") == true
					|| message.equalsIgnoreCase("fly") == true
					|| message.equalsIgnoreCase("wing") == true
					|| message.equalsIgnoreCase("i need fly") == true
					|| message.equalsIgnoreCase("123") == true) {
				if (dew.allowfly == false
						&& api_admin.dewddadmin.is2moderator(player) == false
						&& api_admin.dewddadmin.is2admin(player) == false) {
					player.sendMessage("ptdew&dewdd: Flying mode is disable sorry : D");
					player.sendMessage("ptdew&dewdd: โหมดการบินถูกแอดมินปิด ขอโทษนะ : D");

					return;
				}
				if (player.getAllowFlight() == true) {
					player.sendMessage("ptdew&dewdd: You are Flying Mode");
					player.sendMessage("ptdew&dewdd: คุณอยู่ในโหมดบินอยู่แล้วครับ");
					return;
				}
				try {
					if (Economy.getMoney(player.getName()) < 11111) {
						player.sendMessage("ptdew&dewdd: DewFly Use Money $11111");
						player.sendMessage("ptdew&dewdd: ใช้คำสั่ง DewFly ใช้เงิน 11111");
						return;
					}
					else {
						try {
							Economy.setMoney(player.getName(),
									Economy.getMoney(player.getName()) - 11111);
						}
						catch (UserDoesNotExistException
								| NoLoanPermittedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						player.setAllowFlight(true);
						player.sendMessage("ptdew&dewdd: Start Flying!");
						player.sendMessage("ptdew&dewdd: เริ่มบินได้แล้ววว");
					}
				}
				catch (UserDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (message.equalsIgnoreCase(": O") == true) {
				int ra = dew.randomG.nextInt(100);
				if (ra < 30) {
					try {
						Economy.add(player.getName(), 500);
					}
					catch (UserDoesNotExistException | NoLoanPermittedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dprint.r.printAll("ptdew&dewdd: " + player.getName()
							+ " ได้รับเงิน 500 จากการพนัน");

				}
				else {
					try {
						Economy.subtract(player.getName(), 2000);
					}
					catch (UserDoesNotExistException | NoLoanPermittedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dprint.r.printAll("ptdew&dewdd: " + player.getName()
							+ " เสียพนัน 2000");
				}
				return;
			}

			// hi
			if (message.equalsIgnoreCase("drop") == true) {
				if (api_admin.dewddadmin.is2moderator(player) == true) {
					player.sendMessage("ptdew&dewdd: staff can't say drop for drop item na ja joob joob");
					player.sendMessage("ptdew&dewdd: สตาฟห้ามใช้คำสั่ง hi เพื่อทิ้งของนะจ๊ะ");

					return;
				}

				for (int xx = 0; xx <= 39; xx++) {

					ItemStack itm = player.getInventory().getItem(xx);
					if (itm == null) {
						continue;
					}

					Location la = player.getLocation();
					la.setY(200);
					player.sendMessage("ptdew&dewdd: xx >"
							+ xx
							+ " = "
							+ player.getInventory().getItem(xx).getType()
									.name());
					player.getWorld().dropItem(la, itm);
					itm.setTypeId(0);
					player.getInventory().setItem(xx, itm);

				}

				return;
			}

			// d4
			if (message.equalsIgnoreCase("d4") == true) {
				player.sendMessage("ptdew&dewdd:Now D4 of " + player.getName()
						+ " = " + dew.d4[dew.getfreeselect(player)]);
				return;
			}
			// d4

			String strword1 = message;
			if (strword1.length() == 6) {
				String str11 = strword1.substring(0, 2);
				dprint.r.printC("ptdew&dewdd:d4 " + str11);
				if (str11.equalsIgnoreCase("d4") == true) {
					int id1 = 0;

					str11 = strword1.substring(2, 6);
					dprint.r.printC("ptdew&dewdd:d4 = '" + str11 + "'");

					id1 = Integer.parseInt(str11);

					dew.d4[dew.getfreeselect(player)] = id1;
					player.sendMessage("ptdew&dewdd:d4="
							+ dew.d4[dew.getfreeselect(player)] + " | d4 of "
							+ player.getName() + " | tempip = "
							+ dew.getfreeselect(player));

					return;
				}
			}

			// add player to home
			// dewallow2=ptdew
			// dewallow3=candyman
			// d40123

			if (strword1.length() > 7) {
				// "dewadd="
				if (strword1.substring(0, 7).equalsIgnoreCase("dewadd=") == true) {

					String str11 = strword1.substring(7, strword1.length());
					// got player name

					int xyz = dew.checkpermissionarea(player.getLocation()
							.getBlock(), true);

					dprint.r.printC("ptdew&dewdd: dewadd name = '" + str11
							+ "'");
					System.out.println("ptdew&dewdd: dewadd seacrh home id = '"
							+ xyz + "'");

					if (xyz == -1) {
						player.sendMessage("ptdew&dewdd: This area don't have protection");
						return;
					}

					if (dew.dewsignname[dew.getworldid(player.getWorld()
							.getName())][xyz][0].equalsIgnoreCase(player
							.getName()) == false
							&& player
									.hasPermission(dew.pmaindewbuymodifymember) == false) {
						player.sendMessage("owner is ..."
								+ dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][0]);
						return;
					}

					/*
					 * if ((api_admin.dewddadmin.issubsubadminname(str11) ==
					 * true)
					 * && (api_admin.dewddadmin.is2admin(player) == false &&
					 * api_admin.dewddadmin
					 * .is2moderator(player) == false)) {
					 * 
					 * player.sendMessage(
					 * "ptdew&dewdd: Member can't add staff to your home > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * player.sendMessage(
					 * "ptdew&dewdd: ผู้เล่นธรรมดา ห้ามเพิ่มสตาฟเข้าบ้านของคุณนะ > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * 
					 * return;
					 * }
					 */

					// add member but is staff
					/*
					 * if ((api_admin.dewddadmin.issubsubadminname(str11) ==
					 * false && api_admin.dewddadmin
					 * .isadminname(str11) == false)
					 * && (api_admin.dewddadmin.is2moderator(player) == true)) {
					 * 
					 * if (str11.equalsIgnoreCase("<sell>") == true) {
					 * dprint.r.printAll("ptdew&dewdd: staff "
					 * + player.getName()
					 * + " try to add <sell> to protect");
					 * return;
					 * }
					 * player.sendMessage(
					 * "ptdew&dewdd: Staff can't add Member to your home > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * player.sendMessage(
					 * "ptdew&dewdd: สตาฟห้ามเพิ่มคนธรมมดาเข้าบ้านของคุณนะ > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * 
					 * return;
					 * }
					 */

					for (int ig = 1; ig < dew.dewsignnamemax; ig++) {
						if (dew.dewsignname[dew.getworldid(player.getWorld()
								.getName())][xyz][ig].equalsIgnoreCase(str11) == true) {
							player.sendMessage("this name already in this zone "
									+ str11 + " to  " + xyz);
							return;
						}
					}

					for (int ig = 1; ig < dew.dewsignnamemax; ig++) {
						if (dew.dewsignname[dew.getworldid(player.getWorld()
								.getName())][xyz][ig].equalsIgnoreCase("") == true
								|| dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][ig]
										.equalsIgnoreCase("null") == true) {

							dew.dewsignname[dew.getworldid(player.getWorld()
									.getName())][xyz][ig] = str11;
							player.sendMessage("added " + str11 + " to  " + xyz);
							dew.savesignfile(-1,
									dew.getworldid(player.getWorld().getName()));
							return;
						}
					}

					player.sendMessage("can't find free slot for add this player");
				}
			}

			// "dewremove="
			if (strword1.length() > 10) {
				// "dewadd="
				if (strword1.substring(0, 10).equalsIgnoreCase("dewremove=") == true) {

					String str11 = strword1.substring(10, strword1.length());
					// got player name

					int xyz = dew.checkpermissionarea(player.getLocation()
							.getBlock(), true);

					dprint.r.printC("ptdew&dewdd: dewremove name = '" + str11
							+ "'");
					System.out
							.println("ptdew&dewdd: dewremove seacrh home id = '"
									+ xyz + "'");

					if (xyz == -1) {
						player.sendMessage("ptdew&dewdd: This area don't have protection");
						return;
					}

					if (dew.dewsignname[dew.getworldid(player.getWorld()
							.getName())][xyz][0].equalsIgnoreCase(player
							.getName()) == false
							&& player
									.hasPermission(dew.pmaindewbuymodifymember) == false) {
						player.sendMessage("owner is ..."
								+ dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][0]);
						return;
					}

					for (int ig = 1; ig < dew.dewsignnamemax; ig++) {
						if (dew.dewsignname[dew.getworldid(player.getWorld()
								.getName())][xyz][ig].equalsIgnoreCase(str11) == true) {

							dew.dewsignname[dew.getworldid(player.getWorld()
									.getName())][xyz][ig] = "null";
							player.sendMessage("removed " + str11 + " from  "
									+ xyz);
							return;
						}
					}

					player.sendMessage("can't find this player on this zone");
				}
			}

			if (strword1.equalsIgnoreCase("dewlist") == true) {
				dew.showwhohome(player.getLocation().getBlock(), player);
			}

			// allow 1
			if (strword1.length() > 10) {
				String str11 = strword1.substring(0, 10);

				if (str11.equalsIgnoreCase("dewallow1=") == true) {
					if (player.hasPermission(dew.pmaindewbuychangehost) == false) {
						player.sendMessage("ptdew&dewdd: only op can use dewallow1");
						player.sendMessage("ptdew&dewdd: op เท่านั้น ที่ใช้คำสั่ง  dewallow1 ได้");
						return;
					}

					str11 = strword1.substring(10, strword1.length());
					int xyz = dew.checkpermissionarea(player.getLocation()
							.getBlock(), true);

					dprint.r.printC("ptdew&dewdd:dewallow1 name = '" + str11
							+ "'");
					System.out
							.println("ptdew&dewdd:dewallow1 seacrh home id = '"
									+ xyz + "'");

					if (xyz == -1) {
						player.sendMessage("ptdew&dewdd: This area don't have protection");
						return;
					}

					// change name 2
					dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][0] = str11;
					player.sendMessage("ptdew&dewdd: This area, protection by ...");
					for (int ici = 0; ici < dew.dewsignnamemax; ici++) {
						player.sendMessage(ici
								+ " = "
								+ dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][ici]);
					}
					dew.savesignfile(-1,
							dew.getworldid(player.getWorld().getName()));

					return;
				}

				str11 = strword1.substring(0, 8);
				// dewallow2
				if (str11.equalsIgnoreCase("dewallow") == true) {
					/*
					 * if (api_admin.dewddadmin.is2moderator(player.getName())
					 * == true) { player.sendMessage(
					 * "ptdew&dewdd: staff can't allow another player to your home zone"
					 * ); player.sendMessage(
					 * "ptdew&dewdd: staff ห้ามเพิ่มคนเข้าบ้าน ห้ามใช้คำสั่งนี้"
					 * );
					 * 
					 * return; }
					 */

					int homeslot = Integer.parseInt(strword1.substring(8, 10));
					if (homeslot < 2 || homeslot > (dew.dewsignnamemax)) {
						player.sendMessage("homeslot = 1 < ? < "
								+ (dew.dewsignnamemax));
						return;
					}
					player.sendMessage("homeslot = " + homeslot);

					str11 = strword1.substring(11, strword1.length());
					int xyz = dew.checkpermissionarea(player.getLocation()
							.getBlock(), true);

					// id of home

					dprint.r.printC("ptdew&dewdd:dewallow " + homeslot
							+ " name = '" + str11 + "'");
					System.out.println("ptdew&dewdd:dewallow " + homeslot
							+ " seacrh home id = '" + xyz + "'");

					if (xyz == -1) {
						player.sendMessage("ptdew&dewdd: This area don't have protection");
						player.sendMessage("ptdew&dewdd: พื้นที่นี้ไม่มีใครเป็นเจ้าของ");

						return;
					}

					// check host
					if (dew.dewsignname[dew.getworldid(player.getWorld()
							.getName())][xyz][0].equalsIgnoreCase(player
							.getName()) == false
							&& player
									.hasPermission(dew.pmaindewbuymodifymember) == false) {
						player.sendMessage("ptdew&dewdd: This area, Host is "
								+ dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][0]);
						player.sendMessage("ptdew&dewdd: พื้นที่นี้, เจ้าของคือ "
								+ dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][0]);

						return;
					}

					// change name 2
					// add staff but not admin or staff
					/*
					 * if ((api_admin.dewddadmin.issubsubadminname(str11) ==
					 * true)
					 * && (api_admin.dewddadmin.is2admin(player) == false &&
					 * api_admin.dewddadmin
					 * .is2moderator(player) == false)) {
					 * 
					 * player.sendMessage(
					 * "ptdew&dewdd: Member can't add staff to your home > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * player.sendMessage(
					 * "ptdew&dewdd: ผู้เล่นธรรมดา ห้ามเพิ่มสตาฟเข้าบ้านของคุณนะ > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * 
					 * return;
					 * }
					 */

					// add member but is staff
					/*
					 * if ((api_admin.dewddadmin.issubsubadminname(str11) ==
					 * false && api_admin.dewddadmin
					 * .isadminname(str11) == false)
					 * && (api_admin.dewddadmin.is2moderator(player) == true)) {
					 * if (str11.equalsIgnoreCase("<sell>") == true) {
					 * dprint.r.printAll("ptdew&dewdd: staff "
					 * + player.getName()
					 * + " try to add <sell> to protect");
					 * return;
					 * }
					 * player.sendMessage(
					 * "ptdew&dewdd: Staff can't add Member to your home > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * player.sendMessage(
					 * "ptdew&dewdd: สตาฟห้ามเพิ่มคนธรมมดาเข้าบ้านของคุณนะ > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * 
					 * return;
					 * }
					 */

					dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][homeslot - 1] = str11;

					player.sendMessage("ptdew&dewdd: This area, protection by ...");
					player.sendMessage("ptdew&dewdd: พื้นที่นี้, ถูกปกป้อง โดย ...");

					for (int ici = 0; ici < dew.dewsignnamemax; ici++) {
						player.sendMessage(ici
								+ " = "
								+ dew.dewsignname[dew.getworldid(player
										.getWorld().getName())][xyz][ici]);
					}

					dew.savesignfile(-1,
							dew.getworldid(player.getWorld().getName()));
					return;
				}

			}

			if (message.equalsIgnoreCase("dewbuydelete") == true) {
				dew.dewbuydelete(player);
				return;
			}
			// ***************************************
			// ***************************************

			if (api_admin.dewddadmin.is2admin(player.getPlayer()) == true
					|| api_admin.dewddadmin.is2moderator(player.getPlayer()) == true) {
				// clearmon
				if (message.equalsIgnoreCase("clear") == true) {
					player.getInventory().clear();
					player.sendMessage("ptdew&dewdd: Your Inventory has been removed");
					player.sendMessage("ptdew&dewdd: ล้างของในกระเป๋าแล้ว");
					return;
				}
				// dewgoxxx

				if (message.length() == 9) {
					// player.sendMessage("'" + message.substring(0, 6) + "'");
					if (message.substring(0, 6).equalsIgnoreCase("dewgo ") == true) {
						int gotox = Integer.parseInt(message.substring(6, 9));

						if (dew.getworldid(player.getWorld().getName()) == -1) {
							player.sendMessage("ptdew&dewdd: This world don't have protect... ok?");
							return;
						}

						player.sendMessage("dewgo '"
								+ gotox
								+ "'"
								+ "/"
								+ (dew.dewsignmax[dew.getworldid(player
										.getWorld().getName())] - 1));

						if (gotox < -1
								|| gotox > (dew.dewsignmax[dew
										.getworldid(player.getWorld().getName())] - 1)) {
							player.sendMessage("ptdew&dewdd: dewgo   -1 < ? < "
									+ (dew.dewsignmax[dew.getworldid(player
											.getWorld().getName())] - 1));
							return;
						}

						Location lox = player.getLocation();
						player.sendMessage("goto : "
								+ dew.dewsignx1[dew.getworldid(player
										.getWorld().getName())][gotox]
								+ ",?,"
								+ dew.dewsignz1[dew.getworldid(player
										.getWorld().getName())][gotox]
								+ " and "
								+ dew.dewsignx2[dew.getworldid(player
										.getWorld().getName())][gotox]
								+ ",?,"
								+ dew.dewsignz2[dew.getworldid(player
										.getWorld().getName())][gotox]);

						if (dew.dewsigny1[dew.getworldid(player.getWorld()
								.getName())][gotox] >= dew.dewsigny2[dew
								.getworldid(player.getWorld().getName())][gotox]) {
							lox.setX(dew.dewsignx1[dew.getworldid(player
									.getWorld().getName())][gotox]);
							lox.setY(dew.dewsigny1[dew.getworldid(player
									.getWorld().getName())][gotox]);
							lox.setZ(dew.dewsignz1[dew.getworldid(player
									.getWorld().getName())][gotox]);
						}
						else {
							lox.setX(dew.dewsignx2[dew.getworldid(player
									.getWorld().getName())][gotox]);
							lox.setY(dew.dewsigny2[dew.getworldid(player
									.getWorld().getName())][gotox]);
							lox.setZ(dew.dewsignz2[dew.getworldid(player
									.getWorld().getName())][gotox]);
						}

						player.getWorld().loadChunk((int) lox.getX(),
								(int) lox.getZ());
						player.teleport(lox);
						player.sendMessage("ptdew&dewdd: teleported to protect zone id "
								+ gotox);

						return;
					}
				}

				if (message.equalsIgnoreCase("clearmon") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.CREEPER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SKELETON) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ZOMBIE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SPIDER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.CAVE_SPIDER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.BLAZE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.BAT) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SLIME) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.GHAST) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.GIANT) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.PIG_ZOMBIE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.WITCH) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.MAGMA_CUBE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.WITHER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDER_DRAGON) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SILVERFISH) {
							ent.remove();
						}

					}
					player.sendMessage("DewDD:ClearMon done");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("pvp=false") == true) {
					player.getWorld().setPVP(false);
					player.sendMessage("disable-pvp");
				}
				if (message.equalsIgnoreCase("pvp=true") == true) {
					player.getWorld().setPVP(true);
					player.sendMessage("enable-pvp");
				}

				if (message.equalsIgnoreCase("clearpig") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.PIG) {
							ent.remove();
						}

					}
					player.sendMessage("ptdew&dewdd:Clearpig");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("clearcow") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.COW) {
							ent.remove();
						}

					}
					player.sendMessage("ptdew&dewdd:Clearcow");
					canc = true;
					return;

				}
				if (message.equalsIgnoreCase("clearchicken") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.CHICKEN) {
							ent.remove();
						}

					}
					player.sendMessage("ptdew&dewdd:Clearchicken");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("clearsheep") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.SHEEP) {
							ent.remove();
						}

					}
					player.sendMessage("ptdew&dewdd:ClearSHEEP");
					canc = true;
					return;

				}
				if (message.equalsIgnoreCase("clearvillager") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.VILLAGER) {
							ent.remove();
						}

					}
					player.sendMessage("ptdew&dewdd:ClearVillager");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("clearminecart") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.MINECART) {
							ent.remove();
						}

					}
					player.sendMessage("ptdew&dewdd:Clearminecart");
					canc = true;
					return;

				}
			}

			// pt dew
			if (api_admin.dewddadmin.is2admin(player.getPlayer()) == true) {

				if (message.equalsIgnoreCase("moninvi") == true) {
					dew.moninvi = !dew.moninvi;
					dprint.r.printC("ptdew&dewdd: moninvi = "
							+ Boolean.toString(dew.moninvi));
					dprint.r.printA("ptdew&dewdd: moninvi = "
							+ Boolean.toString(dew.moninvi));
					dprint.r.printA("ptdew&dewdd: โหมดมอนสเตอร์ หายตัว  = "
							+ Boolean.toString(dew.moninvi));
					return;
				}

				if (message.equalsIgnoreCase("monjump") == true) {
					dew.monjump = !dew.monjump;
					dprint.r.printC("ptdew&dewdd: monjump = "
							+ Boolean.toString(dew.monjump));
					dprint.r.printA("ptdew&dewdd: monjump = "
							+ Boolean.toString(dew.monjump));
					dprint.r.printA("ptdew&dewdd: โหมด มอนสเตอร์ กระโดดสูง  = "
							+ Boolean.toString(dew.monjump));
					return;
				}

				if (message.equalsIgnoreCase("monfast") == true) {
					dew.monfast = !dew.monfast;
					dprint.r.printC("ptdew&dewdd: monfast = "
							+ Boolean.toString(dew.monfast));
					dprint.r.printA("ptdew&dewdd: monfast = "
							+ Boolean.toString(dew.monfast));
					dprint.r.printA("ptdew&dewdd: โหมด มอนสเตอร์ ความเร็วสูง  = "
							+ Boolean.toString(dew.monfast));

					return;
				}

				// dewpicturemap

				if (message.equalsIgnoreCase("?50") == true) {
					player.getItemInHand().setAmount(50);
					player.sendMessage("set 50 done");
					return;
				}
				if (message.equalsIgnoreCase("?64") == true) {
					player.getItemInHand().setAmount(64);
					player.sendMessage("set 64 done");
					return;
				}
				if (message.equalsIgnoreCase("?3456") == true) {
					player.getItemInHand().setAmount(3456);
					player.sendMessage("set 3456 done");
					return;
				}
				if (message.equalsIgnoreCase("?1000") == true) {
					player.getItemInHand().setAmount(1000);
					player.sendMessage("set 1000 done");
					return;
				}
				// ปิดการบิน
				if (message.equalsIgnoreCase("ปิดการบิน") == true
						|| message.equalsIgnoreCase("disable-fly") == true) {
					dew.allowfly = false;
					for (Player ppp : player.getWorld().getPlayers()) {
						if (api_admin.dewddadmin.is2moderator(player) == true
								|| api_admin.dewddadmin.is2admin(player) == true) {
							continue;
						}
						ppp.setAllowFlight(false);
						ppp.sendMessage("ptdew&dewdd: Flying Mode is disable");
					}
					player.sendMessage("ptdew&dewdd: disabled - fly");
				}
				if (message.equalsIgnoreCase("เปิดการบิน") == true
						|| message.equalsIgnoreCase("enable-fly") == true) {
					player.sendMessage("ptdew&dewdd: enabled - fly");
					dew.allowfly = true;
				}

				if (message.equalsIgnoreCase("ปิดกลางคืน") == true
						|| message.equalsIgnoreCase("disable-night") == true) {
					dew.allownight = false;
					player.sendMessage("ptdew&dewdd: No time night");
					return;
				}
				if (message.equalsIgnoreCase("เปิดกลางคืน") == true
						|| message.equalsIgnoreCase("enable-night") == true) {
					dew.allownight = true;
					player.sendMessage("ptdew&dewdd: Allow Nightmare");
					return;
				}

				// hide
				if (message.equalsIgnoreCase("hide") == true
						&& api_admin.dewddadmin.is2admin(player) == true) {

					int gga = dew.getfreeselect(player);
					dew.hidemode[gga] = true;

					for (Player pp : Bukkit.getOnlinePlayers()) {
						if (api_admin.dewddadmin.is2admin(pp) == true) {
							pp.sendMessage("ptdew&dewdd : '" + player.getName()
									+ "' Invisible Mode = True");
						}
					}
					dew.hideshowrun(player);

					canc = true;
					return;
				}
				// show
				if (message.equalsIgnoreCase("show") == true
						&& api_admin.dewddadmin.is2admin(player) == true) {

					int gga = dew.getfreeselect(player);
					dew.hidemode[gga] = false;
					for (Player pp : Bukkit.getOnlinePlayers()) {
						if (api_admin.dewddadmin.is2admin(pp) == true) {
							pp.sendMessage("ptdew&dewdd : '" + player.getName()
									+ "' Invisible Mode = False");

						}
					}
					dew.hideshowrun(player);

					canc = true;
					return;
				}

				// getalldrop

				// flyspeed

				strword = message;

			}

			// *****************************************

		} // sync
	}
	class chatz extends Thread {
		Player	player	= null;
		String	message	= "";

		public void run() {
			String[] m = message.split("\\s+");

			String h[] = new String[50];
			h[0] = "dewsetwall";
			h[1] = "dewfillwall";
			h[2] = "dsw";
			h[3] = "dfw";

			h[4] = "dewsetblock";
			h[5] = "dsb";
			h[6] = "dewfillblock";
			h[7] = "dfb";

			h[8] = "dewspreadq";
			h[9] = "dsq";

			h[10] = "dewdown";
			h[11] = "dn";
			h[12] = "down";

			h[13] = "dewsetroom";
			h[14] = "dewfillroom";
			h[15] = "dsr";
			h[16] = "dfr";

			h[17] = "dewspreadcircle";
			h[18] = "dsc";

			h[19] = "dewwallcircle";
			h[20] = "dwc";

			h[21] = "dewbreak";
			h[22] = "dewsetlight";
			h[23] = "dsl";

			h[24] = "dewdelete";
			h[25] = "dd";

			h[26] = "dewfill";
			h[27] = "df";

			h[28] = "dewsetcircle";
			h[29] = "dewfillcircle";
			h[30] = "dfc";

			for (int ccc = 0; ccc < h.length; ccc++) {
				if (m[0].equalsIgnoreCase(h[ccc]) == true) {
					if (m.length == 1) {
						dew.runter(h[ccc], player, player.getItemInHand()
								.getTypeId(), player.getItemInHand().getData()
								.getData());
					}
					else if (m.length == 2) {
						String[] m2 = m[1].split(":");

						int itemid = 0;
						byte dataid = 0;

						player.sendMessage("m2[0] = " + m2[0] + " search.. = "
								+ dew.getmaterialrealname(m2[0]));

						if (dew.isNumeric(m2[0]) == true) {
							if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
								player.sendMessage("error argument 1  what the hell item?!");
							}
							else {
								itemid = Material.getMaterial(
										Integer.parseInt(m2[0])).getId();
							}
						}
						else {

							if (Material.getMaterial(dew
									.getmaterialrealname(m2[0])) == null) { // name
								player.sendMessage("error argument 1  what the hell item?!");
							}
							else {
								itemid = Material.getMaterial(
										dew.getmaterialrealname(m2[0])).getId();
							}

						}

						if (m2.length == 2) {
							dataid = Byte.parseByte(m2[1]);
						}

						player.sendMessage("itemid = " + itemid + ", dataid = "
								+ dataid);
						dew.runter(h[ccc], player, itemid, dataid);
					}

					return;
				}
			}

			// dewset
			if (m[0].equalsIgnoreCase("dewset") == true
					|| m[0].equalsIgnoreCase("ds") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1) {
					dew.dewset(player, -29, (byte) -29, player.getItemInHand()
							.getTypeId(), player.getItemInHand().getData()
							.getData(), false);
				}
				else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// data if 2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and "
							+ a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, false);
				}
				else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					// a3
					m2 = m[2].split(":");
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a2 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and "
							+ a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, false);
				}

				return;
			} // dewset

			// dewset 444 00 555 00
			if (m[0].equalsIgnoreCase("dewxet") == true
					|| m[0].equalsIgnoreCase("dx") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1) {
					dew.dewset(player, -29, (byte) -29, player.getItemInHand()
							.getTypeId(), player.getItemInHand().getData()
							.getData(), true);
				}
				else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// data if 2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and "
							+ a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, true);
				}
				else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					// a3
					m2 = m[2].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a2 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and "
							+ a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, true);
				}

				return;
			} // dewset

			// dewdig
			if (message.equalsIgnoreCase("dewdddig") == true
					|| message.equalsIgnoreCase("ddd") == true) {

				dew.dewdig(player);

				return;
			}

			// dewcopy
			if (message.equalsIgnoreCase("dewcopy") == true
					|| message.equalsIgnoreCase("dc") == true) {
				dew.dewcopy(player);

				return;
			}

			// dewa
			if (m[0].equalsIgnoreCase("dewa") == true
					|| m[0].equalsIgnoreCase("da") == true) {

				int amo = 0;
				if (m.length == 1) {

					dew.dewa(player, 0);
				}
				else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("/dewa amount(it's not number T_T)");
						return;
					}

					amo = Integer.parseInt(m[1]);
					dew.dewa(player, amo);
				}

				return;
			}

			if (message.equalsIgnoreCase("dewsetprivate") == true
					|| message.equalsIgnoreCase("dsp") == true) {
				dew.dewsetprivate(player);

				return;
			}

		}
	}

	class delay extends Thread {

		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping ac +"
									+ i);

				}

				while (dew == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping dew +"
									+ i);

					dew = new Dewminecraft();

				}

				while (dew.ac == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping dew ac +"
									+ i);

					dew.ac = ac;

				}

				dew.loadmainfile();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out
						.println("dew main waiting for create dewset sleeping error");

				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	class runproc implements Runnable {
		String	message	= "";
		Player	player;

		public void run() {
			String m[] = message.split("\\s+");
			if (m[0].equalsIgnoreCase("/dewfreezone") == true) {
				if (m.length == 1) {
					dew.gotofreezone(player);
					return;
				}
				else if (m.length == 3) {
					if (m[1].equalsIgnoreCase("ph")
							&& !m[2].equalsIgnoreCase("max")) {

						int id = Integer.parseInt(m[2]);
						// goto that player
						// -5000 to 5000

						int count = -1;
						Block b = null;

						for (int x = -5000; x <= 5000; x += 100) {
							for (int z = -5000; z <= 5000; z += 100) {
								b = player.getWorld().getBlockAt(x, 254, z);
								if (b.getTypeId() == 63 || b.getTypeId() == 68) {
									count++;
									Sign sign = (Sign) b.getState();
									dprint.r.printAll(sign.getLine(1) + " at "
											+ b.getX() + "," + b.getZ()
											+ "  id " + count);

									if (count == id) {
										// teleport
										player.teleport(b.getLocation());

										return;
									}
								}

							}

						}

					}
					else if (m[1].equalsIgnoreCase("ph")
							&& m[2].equalsIgnoreCase("max")) {
						int count = 0;
						Block b = null;

						for (int x = -5000; x <= 5000; x += 100) {
							for (int z = -5000; z <= 5000; z += 100) {
								b = player.getWorld().getBlockAt(x, 254, z);
								if (b.getTypeId() == 63 || b.getTypeId() == 68) {
									count++;

									Sign sign = (Sign) b.getState();
									dprint.r.printAll(sign.getLine(1) + " at "
											+ b.getX() + "," + b.getZ()
											+ "  id " + count);
								}

							}

						}
					}
					else if (m[1].equalsIgnoreCase("ph")
							&& m[2].equalsIgnoreCase("load")) {
						int count = 0;
						Block b = null;

						for (int x = -5000; x <= 5000; x += 100) {
							for (int z = -5000; z <= 5000; z += 100) {
								b = player.getWorld().getBlockAt(x, 254, z);
								if (b.getTypeId() == 63 || b.getTypeId() == 68) {
									count++;

									Sign sign = (Sign) b.getState();
									dprint.r.printAll(sign.getLine(1) + " at "
											+ b.getX() + "," + b.getZ()
											+ "  id " + count);
									b.getChunk().load();
								}

							}

						}
					}

				}
			}

			if (m[0].equalsIgnoreCase("/dewremove") == true) {

				if (player.hasPermission("dewdd.creative.dewremove") == false) {
					player.sendMessage("you don't have permission for use 'dewremove'");
					return;
				}
				int zx = 5;
				int zz = 5;
				int x = (int) player.getLocation().getX();
				int z = (int) player.getLocation().getZ();
				boolean foundza = false;

				for (int gx = dew.min_b; gx <= dew.max_b; gx += 100) {
					for (int gz = dew.min_b; gz <= dew.max_b; gz += 100) {
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
					dprint.r.printAll("ptdew&dewdd: can't remove area  x < "
							+ dew.min_b + " or x > " + dew.max_b + " or z < "
							+ dew.min_b + " or z > " + dew.max_b);
					dprint.r.printAll("ptdew&dewdd: ห้ามลบที่ดินนอกเขต  "
							+ (-dew.min_b) + " ช่องจากจุด 0");
					return; // not protect
				}

				dprint.r.printAll("ptdew&dewdd: deleting... " + zx + "," + zz);
				dprint.r.printA("ptdew&dewdd: กำลังลบเขต..." + zx + "," + zz);
				Block bc;

				for (int ix = zx; ix <= zx + 99; ix++) {
					for (int iz = zz; iz <= zz + 99; iz++) {
						for (int iy = 255; iy >= 250; iy--) {
							bc = player.getWorld().getBlockAt(ix, iy, iz);
							bc.setTypeId(0);
						}
					}
				}

				for (int iy = 249; iy >= 0; iy--) {
					for (int ix = zx; ix <= zx + 99; ix++) {
						for (int iz = zz; iz <= zz + 99; iz++) {

							bc = player.getWorld().getBlockAt(ix, iy, iz);
							if (bc.getTypeId() == 20) {
								bc.setTypeId(0);
								continue;
							}
						}
					}
				}

				dprint.r.printAll("ptdew&dewdd : removeed protect area ");
				return;

			}

			// dewbuy

			if (message.equalsIgnoreCase("/dewbuy") == true) {
				try {
					if (Economy.getMoney(player.getName()) == 1) {
						// Economy.setMoney(player.getName(), -1);
						if (player.getWorld().getName()
								.equalsIgnoreCase("world_nether") == false) {
							player.sendMessage("ptdew&dewdd: you must buy area at 'world_nether'");
							player.sendMessage("ptdew&dewdd: ซื้อโพเทคต่อไปได้ที่โลกนรก 'world_nether'");
							return;
						}
					}
					else if (Economy.getMoney(player.getName()) == 29) {
						if (player.getWorld().getName()
								.equalsIgnoreCase("world") == false) {
							player.sendMessage("ptdew&dewdd: your first area must buy at 'world'");
							player.sendMessage("ptdew&dewdd: บ้านหลังแรกของคุณต้องซื้อที่พื้นโลก 'world'");
							return;
						}
					}
					else if (Economy.getMoney(player.getName()) == 2) {
						player.sendMessage("ptdew&dewdd: คุณไม่อาจซื้อบ้านได้อีกแล้ว");

						return;
					}
				}
				catch (UserDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// **********************

				int zx = 5;
				int zz = 5;
				int x = (int) player.getLocation().getX();
				int z = (int) player.getLocation().getZ();
				boolean foundza = false;

				for (int gx = dew.min_b; gx <= dew.max_b; gx += 100) {
					for (int gz = dew.min_b; gz <= dew.max_b; gz += 100) {
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
					dprint.r.printAll("ptdew&dewdd: can't buy area  x < "
							+ dew.min_b + " or x > " + dew.max_b + " or z < "
							+ dew.min_b + " or z > " + dew.max_b);
					dprint.r.printAll("ptdew&dewdd: ห้ามซื้อที่ดินนอกเขต  "
							+ (-dew.min_b) + " ช่องจากจุด 0");
					return; // not protect
				}

				if (player.getWorld().getBlockAt(zx, 254, zz).getTypeId() == 63) {
					player.sendMessage("ptdew&dewdd: that is somebody home sorry... ");
					player.sendMessage("ptdew&dewdd: เขตนี้เป็นของใครบางคน...บินไปให้ไกลกว่านี้");
					return;
				}
				player.getWorld().getBlockAt(zx, 252, zz).setTypeId(7);
				player.getWorld().getBlockAt(zx, 253, zz).setTypeId(7);
				player.getWorld().getBlockAt(zx + 1, 253, zz).setTypeId(7);
				player.getWorld().getBlockAt(zx + 1, 254, zz).setTypeId(63);
				Sign sign = (Sign) player.getWorld()
						.getBlockAt(zx + 1, 254, zz).getState();
				sign.setLine(0, "ป้ายที่เหลือติด");
				sign.setLine(1, "ติดชื่อคนที่มี");
				sign.setLine(2, "คนมีสิทธัในเขต");
				sign.setLine(3, "ได้สี่บรรทัด");
				sign.update();
				player.getWorld().getBlockAt(zx + 2, 253, zz).setTypeId(7);
				player.getWorld().getBlockAt(zx + 3, 253, zz).setTypeId(7);

				player.getWorld().getBlockAt(zx, 254, zz).setTypeId(63);
				sign = (Sign) player.getWorld().getBlockAt(zx, 254, zz)
						.getState();
				// check permission
				// zx,254,zz
				dprint.r.printAll("buy area : (" + zx + "," + zz + ")");
				player.getWorld().getBlockAt(zx, 253, zz).setTypeId(7);
				player.getWorld().getBlockAt(zx, 254, zz).setTypeId(63);
				sign = (Sign) player.getWorld().getBlockAt(zx, 254, zz)
						.getState();
				sign.setLine(0, "[dewhome]");
				sign.setLine(1, player.getName());
				sign.update();
				Block block = null;

				int waid = dew.getwallid();
				dprint.r.printAll("wallid = " + waid);

				for (int dy = 251; dy >= 1; dy--) {
					block = player.getWorld().getBlockAt(zx, dy, zz);
					block.setTypeId(waid);
				}

				for (int dx = zx; dx <= (zx + 99); dx++) {
					for (int dz = zz; dz <= (zz + 99); dz++) {
						for (int dy = 254; dy >= 1; dy--) {
							block = player.getWorld().getBlockAt(dx, dy, dz);
							if (block.getChunk().isLoaded() == false) {
								block.getChunk().load();
							}

							if (block.getTypeId() != 0 && dy != 253) {
								if (dx == zx || dx == (zx + 99) || dz == zz
										|| dz == (zz + 99) || dx == zz
										|| dx == (zz + 99) || dz == zx
										|| dz == (zx + 99)) {
									if (dy >= 255) {
										continue;
									}
									block.getRelative(0, 1, 0).setTypeId(waid);
									break;
								}
							}

						}
					}
				}

				if (player.getWorld().getName().equalsIgnoreCase("world") == true) {
					try {
						Economy.setMoney(player.getName(), 1);
					}
					catch (UserDoesNotExistException | NoLoanPermittedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (player.getWorld().getName()
						.equalsIgnoreCase("world_nether") == true) {
					try {
						Economy.setMoney(player.getName(), 2);
					}
					catch (UserDoesNotExistException | NoLoanPermittedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				player.sendMessage("ptdew&dewdd: dewbuy complete!");
				player.sendMessage("ptdew&dewdd: ซื้อบ้านเสร็จแล้วครับ");
				dprint.r.printAll("ptdew&dewdd: ยินตีต้อนรับเพื่อนบ้านคนใหม่ชื่อ '"
						+ player.getName()
						+ "' ด้วยครับ บ้านเขาอยู่ที่ ("
						+ zx
						+ ","
						+ zz
						+ ") at '"
						+ player.getWorld().getName()
						+ "'");

				return;
			}

		}

	}

	public JavaPlugin	ac	= null;

	public Dewminecraft	dew	= null;

	public DigEventListener2() {

		delay eee = new delay();
		eee.start();
	}

	public int distance2d(int x1, int z1, int x2, int z2) {

		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		Player player = event.getPlayer();

		runpro("/" + event.getMessage(), player);

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd creative");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("creative reload")) {
			dew = new Dewminecraft();
			dew.loadmainfile();
			dew.ac = ac;
		}

		if (event.getMessage().equalsIgnoreCase("ac")) {
			dprint.r.printAll(ac == null ? "ac yes" : "ac no");
			dprint.r.printAll(dew == null ? "dew yes" : "dew no");
			dprint.r.printAll(dew.ac == null ? "dew ac yes" : "dew ac no");

		}

		if (event.getMessage().equalsIgnoreCase("dewdd creative") == true) {
			player.sendMessage("ปลั๊กอิน creative เป็น ปลั็กอินที่   ล็อกโพเทคทั้งโลกไว้เป็น สี่เหลี่ยม 100*256*100   สามารถซื้อโพเทคได้โดยการพิมพ์  .dewbuy");
			player.sendMessage("เริ่มแรกจะมีเงิน 29 บาท   ซื้อโพเทคแรกได้ที่ โลก world บินไปที่ชอบที่ีชอบแล้วพิมพ์  .dewbuy  เงินจะเป็น 1 บาท");
			player.sendMessage("ไปที่นรกแล้วพิมพ์ .dewbuy จะซื้อได้อีก  เงินจะกลายเป็น 2 บาท   , อย่างไรก็ตามโลก ender ไม่มีโพเทค จะสร้างไรก็ได้");
			player.sendMessage("วิธีเพิ่มคนเข้าโพเทค  ในเขตตัวเอง จะมีเสาสูงๆ  ให้บินขึ้นไป แล้วติดป้ายชื่อเพื่อนบนนั้น   ป้ายต้องต่อกันไปเรื่อยๆ ห้ามขาด");
			player.sendMessage("ปลั๊กอินนี้ มีระบบ dewset อยู่ด้วย   ใช้ได้ในเขตที่ตัวเองมีสิทธิเท่านั้น");
			player.sendMessage("เจ้าของเขตเท่านั้นที่จะเปลี่ยนรายชื่อคนในเขตได้");

			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("pcreative") == true) {
			player.sendMessage("changehost = "
					+ Boolean.toString(player.hasPermission(dew.perchangehost)));
			player.sendMessage("overide = "
					+ Boolean.toString(player.hasPermission(dew.peroveride)));
			player.sendMessage("doprotecty = "
					+ Boolean.toString(player.hasPermission(dew.perdoprotecty)));
			player.sendMessage("dounprotecty = "
					+ Boolean.toString(player
							.hasPermission(dew.perdounprotecty)));
			player.sendMessage("dewseteverywhere = "
					+ Boolean.toString(player
							.hasPermission(dew.perdewseteverywhere)));
			player.sendMessage("dewremove = "
					+ Boolean.toString(player.hasPermission(dew.perdewremove)));

			return;
		}

		if (event.getMessage().equalsIgnoreCase("?world") == true) {
			player.sendMessage("world = " + player.getWorld().getName());
			return;
		}

		// ***********************

		// **************** about dewset

		chatx abc = new chatx();
		abc.player = event.getPlayer();
		abc.message = event.getMessage();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, abc);

		event.setCancelled(abc.canc);
		abc = null;

		chatz ab2 = new chatz();
		ab2.player = event.getPlayer();
		ab2.message = event.getMessage();

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab2);

	}

	@EventHandler
	public void eventja(BlockBreakEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (dew.canbuild(event.getBlock().getX(), event.getBlock().getY(),
				event.getBlock().getZ(), event.getPlayer(), false) == false) {
			event.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(BlockDamageEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (dew.canbuild(event.getBlock().getX(), event.getBlock().getY(),
				event.getBlock().getZ(), event.getPlayer(), false) == false) {
			event.setCancelled(true);
			return;
		}

		// event.getPlayer().sendMessage("iteminhand= " +
		// event.getPlayer().getItemInHand().getTypeId());
		if (event.getPlayer().getItemInHand().getTypeId() == 55) {
			dew.amount2 = 0;
			dew.amount3 = 0;

			dew.item55delete(event.getBlock(), event.getPlayer(), event
					.getBlock().getTypeId(), event.getBlock().getData());

		}

	}

	@EventHandler
	public void eventja(BlockPlaceEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (dew.canbuild(event.getBlock().getX(), event.getBlock().getY(),
				event.getBlock().getZ(), event.getPlayer(), false) == false) {
			event.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(EntityChangeBlockEvent event) {

		if (event.getEntity() == null) {
			return;
		}

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName())) {
			return;
		}

		if (event.getEntity().getType() == EntityType.ENDERMAN
				&& dew.checkpermissionarea(event.getBlock()) == true) {
			event.setCancelled(true);
			return;
		}

		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player pal = (Player) event.getEntity();

			if (dew.checkpermissionarea(event.getBlock(), pal, "changeBlock") == true) {
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void eventja(EntityExplodeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void eventja(HangingBreakByEntityEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName())) {
			return;
		}

		if (event.getRemover().getType() == EntityType.PLAYER) {
			Player br = (Player) event.getRemover();
			if (dew.canbuild(event.getEntity().getLocation().getBlockX(), event
					.getEntity().getLocation().getBlockY(), event.getEntity()
					.getLocation().getBlockZ(), br, false) == false) {
				event.setCancelled(true);
			}
		}
		else {
			event.setCancelled(true);
		}
	}

	/*
	 * @EventHandler public void eventja(EntityExplodeEvent event) { if
	 * (event.getLocation
	 * ().getWorld().getName().equalsIgnoreCase("world")==true){
	 * event.setCancelled(true); } }
	 */

	@EventHandler
	public void eventja(HangingBreakEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName())) {
			return;
		}

		if (event.getCause() == RemoveCause.EXPLOSION == true) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(HangingPlaceEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player br = event.getPlayer();
		if (dew.canbuild(event.getEntity().getLocation().getBlockX(), event
				.getEntity().getLocation().getBlockY(), event.getEntity()
				.getLocation().getBlockZ(), br, false) == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerBucketEmptyEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (dew.canbuild(event.getBlockClicked().getX(), event
				.getBlockClicked().getY(), event.getBlockClicked().getZ(),
				event.getPlayer(), false) == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = event.getPlayer();
		runpro(event.getMessage(), player);

		/*
		 * if (event.getMessage().toLowerCase().startsWith("/l ") == false
		 * && event.getMessage().toLowerCase().startsWith("/login ") == false) {
		 * 
		 * dprint.r.printAdmin(player.getName() + " : " + event.getMessage());
		 * }
		 */

		chatx ab = new chatx();
		ab.player = event.getPlayer();
		ab.message = event.getMessage().substring(1);
		ab.canc = false;

		chatz ab2 = new chatz();
		ab2.player = event.getPlayer();
		ab2.message = event.getMessage().substring(1);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab2);
		event.setCancelled(ab.canc);

	}

	@EventHandler
	public void eventja(PlayerDropItemEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = event.getPlayer();
		dew.randomplaynote(event.getPlayer().getLocation());

		int idc = dew.getfreeselect(player);
		if (dew.chatever[idc] == false) {
			dew.chatever[idc] = true;
			player.sendMessage("ptdew&dewdd: *** Ok you can chat now ***");
			player.sendMessage("ptdew&dewdd: *** โอเค พูดได้แล้ว นะครับ ***");

		}
	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Action act;
		act = event.getAction();
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (block != null) {
			if (block.getTypeId() == 63 || block.getTypeId() == 68) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equalsIgnoreCase("[dewgo]") == true
						|| sign.getLine(0).equalsIgnoreCase("[dewteleport]") == true) {

					Location loca = player.getLocation();
					loca.setX(Integer.parseInt(sign.getLine(1)));
					loca.setY(Integer.parseInt(sign.getLine(2)));
					loca.setZ(Integer.parseInt(sign.getLine(3)));

					player.sendMessage("ptdew&dewdd: วาปผู้เล่นจากพิกัด ("
							+ player.getLocation().getBlockX() + ","
							+ player.getLocation().getBlockY() + ","
							+ player.getLocation().getBlockZ() + ") ไป ("
							+ loca.getBlockX() + "," + loca.getBlockY() + ","
							+ loca.getBlockZ() + ")");
					player.teleport(loca);

				}
			}
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

		if (player.getItemInHand().getTypeId() == 276 // diamondsword
				&& act == Action.RIGHT_CLICK_BLOCK) {
			// set x1y1z1

			int getid = dew.getfreeselect(player);
			dew.selectblock[getid] = block;
			dew.selectworldname[getid] = block.getWorld().getName();

			player.sendMessage("ptdew&dewdd : "
					+ tr.gettr("diamondsword_seted_dewa_block") + " ("
					+ dew.selectblock[getid].getX() + ","
					+ dew.selectblock[getid].getY() + ","
					+ dew.selectblock[getid].getZ() + ")");
			// event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		savew();
		event.getPlayer().sendMessage("xxx");
		event.getPlayer().sendMessage("ptdew&dewdd: Welcome to Creative-DewDD");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: ยินตีต้อนรับสู่เซิฟ สร้างสรรค์ โดย ptdew&dewdd");
		event.getPlayer()
				.sendMessage(
						"ptdew&dewdd: ผู้เล่นมาใหม่ บินไปที่ๆต้องการ แล้วพิมพ์ว่า DewBuy");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: จากนั้น sethome ด้วยครับ... : )");

		event.getPlayer().sendMessage("ptdew&dewdd: **************");
		event.getPlayer()
				.sendMessage(
						"ptdew&dewdd: >ระบบใหม่ ใช้ผงหินแดง หมายเลข 55 จำนวน 64 ชิ้น  ตีบล็อคไรก็ได้ จะเป็นการทำลายล้าง (ต้องอยู่ในโหมดผจญภัย)");
		event.getPlayer()
				.sendMessage(
						"ptdew&dewdd: >สามารถสร้างไรก็ได้ในโลก The End แล้วนะครับ  แต่ว่าไม่มีโพเทคให้");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: >ระเบิดจะไม่ทำงานในโลก โลกอื่นระเบิดได้");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: >ใช้คำสั่ง cleardrop เพื่อลบของที่ตกอยู่");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: >ใช้ระบบ dewset ได้แล้วครับ");
		event.getPlayer()
				.sendMessage(
						"ptdew&dewdd: > ติดป้ายบรรทัดแรก [dewgo] บรรทัดสองเป็นพิกัด X บรรทัดสาม เป็นพิกัด y บรรทัดสี่เป็นพิกัด Z  จากนั้นคลิกที่ป้าย");
		event.getPlayer()
				.sendMessage(
						"ptdew&dewdd: > ระบบใหม่ ทำไรก็ได้ในเขตที่ไม่มีใคเป็นเจ้าของ (แต่ใช้ dewset ไม่ได้)");

		int idc = dew.getfreeselect(event.getPlayer());
		dew.chatever[idc] = true;

		System.gc();
	}

	@EventHandler
	public void eventja(PlayerMoveEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (isbadarea((int) event.getPlayer().getLocation().getX(), (int) event
				.getPlayer().getLocation().getY(), (int) event.getPlayer()
				.getLocation().getZ()) == true) {
			Location xx = event.getPlayer().getLocation();
			xx.setX(0);
			xx.setY(255);
			xx.setZ(0);
			event.getPlayer().teleport(xx);
			dprint.r.printAll("ptdew&dewdd : ' move "
					+ event.getPlayer().getName() + "' don't go to bad area");
			dprint.r.printAll("ptdew&dewdd : '" + event.getPlayer().getName()
					+ "' ห้ามเข้าไปใกล้เขตไฟล์เสียหาย");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerRespawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (isbadarea((int) event.getRespawnLocation().getX(), (int) event
				.getRespawnLocation().getY(), (int) event.getRespawnLocation()
				.getZ()) == true) {
			Location xx = event.getRespawnLocation();
			xx.setX(0);
			xx.setY(255);
			xx.setZ(0);
			event.getPlayer().teleport(xx);
			dprint.r.printAll("ptdew&dewdd : 'respawn "
					+ event.getPlayer().getName() + "' don't go to bad area");
			dprint.r.printAll("ptdew&dewdd : '" + event.getPlayer().getName()
					+ "' ห้ามเข้าไปใกล้เขตไฟล์เสียหาย");

		}
	}

	@EventHandler
	public void eventja(PlayerTeleportEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (event.getFrom().getWorld().getName().equalsIgnoreCase("world") == true
				|| event.getFrom().getWorld().getName()
						.equalsIgnoreCase("world_nether") == true
				|| event.getFrom().getWorld().getName()
						.equalsIgnoreCase("world_the_end") == true) {

			if (event.getTo().getWorld().getName().equalsIgnoreCase("world") == false
					&& event.getTo().getWorld().getName()
							.equalsIgnoreCase("world_nether") == false
					&& event.getTo().getWorld().getName()
							.equalsIgnoreCase("world_the_end") == false) {

				event.getPlayer().getInventory().clear();
				event.getPlayer().setGameMode(GameMode.SURVIVAL);
				for (ItemStack itm : event.getPlayer().getInventory()
						.getArmorContents()) {
					itm.setTypeId(5);
					itm.setAmount(1);
				}

			}
		}

		if (isbadarea((int) event.getTo().getX(), (int) event.getTo().getY(),
				(int) event.getTo().getZ()) == true) {
			Location xx = event.getTo();
			xx.setX(0);
			xx.setY(255);
			xx.setZ(0);
			event.getPlayer().teleport(xx);
			dprint.r.printAll("ptdew&dewdd : ' teleport "
					+ event.getPlayer().getName() + "' don't go to bad area");
			dprint.r.printAll("ptdew&dewdd : '" + event.getPlayer().getName()
					+ "' ห้ามเข้าไปใกล้เขตไฟล์เสียหาย");
			event.setCancelled(true);
		}
	}

	public boolean isbadarea(int x, int y, int z) {

		if (Math.pow(
				Math.pow(x - 717, 2) + Math.pow(y - 64, 2) + Math.pow(z - 3, 2),
				0.5) < 100) {
			return true;
		}
		else if (Math.pow(
				Math.pow(x - (-225), 2) + Math.pow(y - 87, 2)
						+ Math.pow(z - 867, 2), 0.5) < 100) {
			return true;
		}

		return false;
	}

	public void runpro(String message, Player player)
			throws UserDoesNotExistException, NoLoanPermittedException {
		runproc abc = new runproc();
		abc.player = player;
		abc.message = message;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, abc);
		// dewremove

	}

	public void savew() {
		Bukkit.getWorld("world").save();
		for (Player px : Bukkit.getOnlinePlayers()) {
			px.saveData();
		}
	}

	// EntityExplodeEvent

	// PlayerDeathEvent

	// PlayerDropItemEvent

	// PlayerExpChangeEvent

	// PlayerInteractEvent

	// PlayerMoveEvent

	// PlayerMoveEvent

	// PlayerRespawnEvent

} // class

