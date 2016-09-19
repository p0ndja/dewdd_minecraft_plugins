/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgoldhoe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class addblock_c implements Runnable {
		private Block	block;

		public addblock_c(Block block) {
			this.block = block;

		}

		public void run() {
			Block b2 = null;

			if (block != null) {
				for (int x = -5; x <= 5; x++) {
					for (int y = -5; y <= 5; y++) {
						for (int z = -5; z <= 5; z++) {
							b2 = block.getRelative(x, y, z);

							bd.add(b2);

						}
					}
				}
			}
		}
	}

	class addhopperc implements Runnable {
		Block	block	= null;

		public void run() {

			if (block.getTypeId() == Material.HOPPER.getId()) {

				Hopper h = (Hopper) block.getState();

				ItemStack itm = new ItemStack(97);

				itm.setAmount(1);
				h.getInventory().setItem(1, itm);
				h.getInventory().setItem(2, itm);
				h.getInventory().setItem(3, itm);
				itm.setAmount(18);
				h.getInventory().setItem(4, itm);

				for (int x = 300; x >= -300; x--) {
					if (block.getRelative(x, 0, 0).getTypeId() == Material.HOPPER
							.getId()) {
						h = (Hopper) block.getRelative(x, 0, 0).getState();
						itm = new ItemStack(97);
						itm.setAmount(1);
						h.getInventory().setItem(1, itm);
						h.getInventory().setItem(2, itm);
						h.getInventory().setItem(3, itm);
						itm.setAmount(18);
						h.getInventory().setItem(4, itm);

					}
				}

				for (int z = 300; z >= -300; z--) {
					if (block.getRelative(0, 0, z).getTypeId() == Material.HOPPER
							.getId()) {
						h = (Hopper) block.getRelative(0, 0, z).getState();
						itm = new ItemStack(97);
						itm.setAmount(1);
						h.getInventory().setItem(1, itm);
						h.getInventory().setItem(2, itm);
						h.getInventory().setItem(3, itm);
						itm.setAmount(18);
						h.getInventory().setItem(4, itm);

					}
				}

			}

		}
	}

	class breake_c implements Runnable {
		private Player	player;
		private Block	block;

		public breake_c(Player player, Block block) {
			this.player = player;
			this.block = block;

		}

		@EventHandler
		public void eventja(BlockBreakEvent event) {
			Player player = event.getPlayer();
			Block block = event.getBlock();

			// add monster egg to machine
			if (player.getItemInHand().getTypeId() == Material.HOPPER.getId()) {
				if (block.getTypeId() == Material.HOPPER.getId()) {
					if (player.hasPermission("dewdd.addhopper") == true) {
						player.sendMessage("you are using dewdd.addhoper");
						event.setCancelled(true);

						addhopperc ab = new addhopperc();
						ab.block = block;

						Bukkit.getServer().getScheduler()
								.scheduleSyncDelayedTask(ac, ab);

						player.sendMessage("add hopper done");
						return;

					}

				}
				return;
			}

			breake_c o = new breake_c(player, block);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, o, 5);

		}

		public void run() {
			if (player.getItemInHand().getTypeId() == Material.DIAMOND_HOE
					.getId()) {
				if (player.hasPermission("dewdd.deletenear") == false) {
					player.sendMessage("you don't have permission for use deletenear");
					return;

				}
				if (dewddtps.tps.getTPS() < 17) {
					dprint.r.printAll("ptdew&dewdd: dew d near system need to pause cuz tps of server below than 17 try again!"
							+ dewddtps.tps.getTPS());

					return;
				}

				if (radiusb == null) {
					radiusb = player.getLocation().getBlock();
				}

				boolean xrx = false;

				for (int x = -bfar; x <= bfar; x++) {
					for (int y = -bfar; y <= bfar; y++) {

						for (int z = -bfar; z <= bfar; z++) {

							xrx = false;
							for (int jr = 0; jr < bdele.length; jr++) {
								if (block.getRelative(x, y, z).getTypeId() == bdele[jr]) {
									xrx = true;
									break;
								}
							}

							if (xrx == false) continue;

							bd.add(block.getRelative(x, y, z));
							deleteneari(block.getRelative(x, y, z), player
									.getInventory().first(7) == -1);
						}
					}
				}

				player.sendMessage("dewdnear done");

			}
		}
	}

	class deletenearc implements Runnable {

		public void run() {

			Block b2 = null;

			// printAll("runinng bd size = " + bd.size());
			// bd.clear();

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			if (bdele.length == 1) {
				if (bdele[0] == 0) {

					bd.clear();

				}
			}

			boolean ne = false;

			int x = 0;
			int y = 0;
			int z = 0;
			int jr = 0;

			Block b3 = null;
			Block lastbreak = null;
			String lastbreakname = "";

			boolean xrx = false;

			while (bd.size() > 0) { // bll

				endtime = System.currentTimeMillis();
				if (endtime - starttime > 200 || dewddtps.tps.getTPS() < 18) {
					deletenearc ee = new deletenearc();

					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ee,
							rnd.nextInt(200) + 50);

					if (lastbreak != null) {

						dprint.r.printC("dewdnear = " + bd.size() + " at "
								+ lastbreak.getX() + "," + lastbreak.getY()
								+ "," + lastbreak.getZ() + " = "
								+ lastbreakname);
					}
					else {
						dprint.r.printC("dewdnear = " + bd.size());
					}

					return;
				}

				b3 = bd.poll();

				/*
				 * if (b3.getTypeId() != bdele) { continue; }
				 */

				xrx = false;
				for (jr = 0; jr < bdele.length; jr++) {
					if (b3.getTypeId() == bdele[jr]) {
						lastbreak = b3;
						lastbreakname = lastbreak.getType().name();
						xrx = true;
						break;
					}
				}

				if (xrx == false) continue;

				ne = false;

				for (x = -bfar; x <= bfar; x++) {
					for (y = -bfar; y <= bfar; y++) {
						if (y < 1 || y > 254) {
							continue;
						}

						for (z = -bfar; z <= bfar; z++) {

							b2 = b3.getRelative(x, y, z);

							/*
							 * if (b2.getTypeId() == bsear) { ne = true; break;
							 * }
							 */

							for (jr = 0; jr < bsear.length; jr++) {
								if (b2.getTypeId() == bsear[jr]) {
									ne = true;
									break;
								}
							}

						}
						if (ne == true) {
							break;
						}
					}

					if (ne == true) {
						break;
					}
				}

				if (ne == false) {
					continue;
				}

				if (replacewith == -1) {
					if (is8_10block(b3.getTypeId()) == false) {
						b3.breakNaturally();

					}
					else {

						if (b3.getTypeId() == 8) {
							ItemStack it = new ItemStack(Material.WATER_BUCKET,
									1);
							b3.getWorld().dropItemNaturally(b3.getLocation(),
									it);
						}
						else if (b3.getTypeId() == 10) {
							ItemStack it = new ItemStack(Material.LAVA_BUCKET,
									1);
							b3.getWorld().dropItemNaturally(b3.getLocation(),
									it);
						}

						b3.setTypeId(0);

					}
				}
				else {
					b3.setTypeId(replacewith);
				}

				/*
				 * for ( x = -bfar ; x <= bfar ; x ++) { for ( y = -bfar ; y <=
				 * bfar ; y ++) { for ( z = -bfar ; z <= bfar ; z ++) { b2 =
				 * block.getRelative(x, y, z); if (b2.getTypeId() != bdele) {
				 * continue; }
				 * 
				 * if (bd.size() < 10000000) { bd.push(b2); } else { continue; }
				 * } } }
				 */

				for (x = -bfar; x <= bfar; x++) {

					for (y = -bfar; y <= bfar; y++) {

						if (y + b3.getY() < 0 || y + b3.getY() > 254) {
							continue;
						}
						if (y + b3.getY() < ylow || y + b3.getY() > yhigh) {
							continue;
						}

						for (z = -bfar; z <= bfar; z++) {

							b2 = b3.getRelative(x, y, z);
							xrx = false;
							for (jr = 0; jr < bdele.length; jr++) {
								if (b2.getTypeId() == bdele[jr]) {
									xrx = true;
									break;
								}
							}

							if (xrx == false) continue;

							if (b2.getLocation()
									.distance(radiusb.getLocation()) > radius) {
								continue;
							}

							if (bd.size() >= bdmaxsize) {
								break;
							}

							bd.add(b2);

						}

						if (bd.size() >= bdmaxsize) {
							break;
						}

					}

					if (bd.size() >= bdmaxsize) {
						break;
					}
				} // x

			} // bll

			dprint.r.printC("dew d near done");

		}
	}
	class dnearrandom implements Runnable {

		public void run() {
			// must sleep every 1 second
			long now = System.currentTimeMillis();

			while (true) {

				dprint.r.printC("dnear random runing...");

				if (bdele.length == 1) {
					if (bdele[0] == 0) {

						bd.clear();
						dprint.r.printC("exit dewdnear random run");
						return;
					}
				}

				if (now - lastrandom < 1000) {
					dnearrandom abc = new dnearrandom();
					Bukkit.getScheduler()
							.scheduleSyncDelayedTask(ac, abc, 1000);
					return;
				}

				// run

				int y2 = 256;
				do {
					y2 = rnd.nextInt(255);
				}
				while (y2 > yhigh || y2 < ylow);

				Block block3 = radiusb.getRelative(
						(rnd.nextInt(radius) * (rnd.nextInt(1) == 1 ? 1 : -1)),
						y2, rnd.nextInt(radius)
								* (rnd.nextInt(1) == 1 ? 1 : -1));

				dprint.r.printC("rnd block is " + block3.getX() + ","
						+ block3.getY() + "," + block3.getZ());

				boolean xrx = false;

				for (int x = -bfar; x <= bfar; x++) {
					for (int y = -bfar; y <= bfar; y++) {
						if (y + block3.getY() < ylow
								|| y + block3.getY() > yhigh) {
							continue;
						}

						for (int z = -bfar; z <= bfar; z++) {

							xrx = false;
							for (int jr = 0; jr < bdele.length; jr++) {
								if (block3.getRelative(x, y, z).getTypeId() == bdele[jr]) {
									xrx = true;
									break;
								}
							}

							if (xrx == false) continue;

							bd.add(block3.getRelative(x, y, z));

						}
					}
				}

				if (bd.size() > 0) {
					deletenearc ab = new deletenearc();

					// printAll("running ... " + bd.size());
					Bukkit.getServer()
							.getScheduler()
							.scheduleSyncDelayedTask(ac, ab,
									rnd.nextInt(500) + 1);

					lastrandom = System.currentTimeMillis();

				}

			}

		}
	}

	class dnearrandom_c implements Runnable {

		public void run() {
			dnearrandom abc = new dnearrandom();
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, 100);
		}
	}
	JavaPlugin	ac			= null;
	int			bsear[];
	int			bdele[];

	int			bfar		= 0;
	Random		rnd			= new Random();

	int			bdmaxsize	= 10000000;

	int			replacewith	= 0;

	int			radius		= 1000;

	Block		radiusb		= null;

	int			ylow		= 0;

	int			yhigh		= 255;

	// Chat Event.class
	// BlockBreakEvent

	long		lastrandom	= 0;

	Queue<Block>	bd	= new LinkedList<Block>();

	public void addblock(Block block) {
		addblock_c er = new addblock_c(block);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, er);
	}

	public void deleteneari(Block block, boolean drop) {
		addblock(block);

		deletenearc ab = new deletenearc();

		// printAll("running ... " + bd.size());
		Bukkit.getServer().getScheduler()
				.scheduleSyncDelayedTask(ac, ab, rnd.nextInt(500) + 1);
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd goldhoe");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd goldhoe") == true) {
			player.sendMessage("ปลั๊กอิน goldhoe ถูกสร้างขึ้นมาเพื่อใช้ gold hoe ดูดน้ำและลาวา บนท้องฟ้าของ เซิฟ skyblock");
			player.sendMessage("แต่ตอนนี้ได้แยกออกมาเป็นปลั๊กอินหนึ่งแล้ว");
			player.sendMessage("วิธีการใช้งานง่ายๆ  ใช้ goldhoe ตีบล็อคที่ติดกับน้ำหรือลาวาให้แตก มันจะดูดน้ำต่อเนื่องไปจนหมด");
			player.sendMessage("น้ำและลาวาต่ำกว่าหรือเท่ากับระดับน้ำทะเล จะไม่โดนดูด");

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		String m[] = message.split("\\s+");

		// /dewdnear 0 8 5 replacewith maxsize

		if (m[0].equalsIgnoreCase("/dewdnear") == true) {

			if (m.length == 1) {
				player.sendMessage("/dewdnear blocksearch blockdelete far replacewith maxsize radius ylow yhigh <random>");
				return;
			}
			else if (m.length == 4) {

				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);

				replacewith = 0;

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");

				player.sendMessage("y = " + ylow + " to " + yhigh);

			}
			else if (m.length == 5) {

				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);
				replacewith = Integer.parseInt(m[4]);

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");
				player.sendMessage("y = " + ylow + " to " + yhigh);

			}
			else if (m.length == 6) {
				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);
				replacewith = Integer.parseInt(m[4]);
				bdmaxsize = Integer.parseInt(m[5]);

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");
				player.sendMessage("y = " + ylow + " to " + yhigh);

			}
			else if (m.length == 7) {

				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);
				replacewith = Integer.parseInt(m[4]);
				bdmaxsize = Integer.parseInt(m[5]);
				radius = Integer.parseInt(m[6]);

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");
				player.sendMessage("y = " + ylow + " to " + yhigh);

				radiusb = player.getLocation().getBlock();
			}
			else if (m.length == 8) {

				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);
				replacewith = Integer.parseInt(m[4]);
				bdmaxsize = Integer.parseInt(m[5]);
				radius = Integer.parseInt(m[6]);

				ylow = Integer.parseInt(m[7]);

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");
				player.sendMessage("y = " + ylow + " to " + yhigh);

				radiusb = player.getLocation().getBlock();
			}
			else if (m.length == 9) {

				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);
				replacewith = Integer.parseInt(m[4]);
				bdmaxsize = Integer.parseInt(m[5]);
				radius = Integer.parseInt(m[6]);
				ylow = Integer.parseInt(m[7]);
				yhigh = Integer.parseInt(m[8]);

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");
				player.sendMessage("y = " + ylow + " to " + yhigh);

				radiusb = player.getLocation().getBlock();
			}
			else if (m.length == 10) {

				bsear = getvalue((m[1]));

				bdele = getvalue(m[2]);

				bfar = Integer.parseInt(m[3]);
				replacewith = Integer.parseInt(m[4]);
				bdmaxsize = Integer.parseInt(m[5]);
				radius = Integer.parseInt(m[6]);
				ylow = Integer.parseInt(m[7]);
				yhigh = Integer.parseInt(m[8]);

				player.sendMessage("far delete = '" + bfar + "'");
				player.sendMessage("replacewith = '" + replacewith + "'");
				player.sendMessage("max size = '" + bdmaxsize + "'");
				player.sendMessage("radius = '" + radius + "'");
				player.sendMessage("y = " + ylow + " to " + yhigh);

				radiusb = player.getLocation().getBlock();

				dnearrandom_c er = new dnearrandom_c();
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, er, 5);
			}

			String mrx = "sear = ";
			for (int i = 0; i < bsear.length; i++) {
				mrx = mrx + " " + bsear[i];
			}

			player.sendMessage(mrx);

			mrx = "dele = ";
			for (int i = 0; i < bdele.length; i++) {
				mrx = mrx + " " + bdele[i];
			}

			player.sendMessage(mrx);

		}

	}

	public int[] getvalue(String str) {
		String r[] = str.split(",");
		int x[] = new int[r.length];

		for (int i = 0; i < r.length; i++) {

			x[i] = Integer.parseInt(r[i]);
		}

		return x;
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

} // class

