/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddcreative;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

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

class Dewminecraft {
	public String	perdewremove		= "dewdd.creative.dewremove";
	public String	perchangehost		= "dewdd.creative.changehost";
	public String	perdoprotecty		= "dewdd.creative.doprotecty";
	public String	perdounprotecty		= "dewdd.creative.dounprotecty";
	public String	peroveride			= "dewdd.creative.overide";

	public int		min_b				= -20000;
	public int		max_b				= 19900;
	private Random randomG = new Random();


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
