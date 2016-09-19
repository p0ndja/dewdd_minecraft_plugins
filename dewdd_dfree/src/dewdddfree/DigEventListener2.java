/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdddfree;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddflower.dewset;
import dewddtran.tr;

public class DigEventListener2 implements Listener {
	class delay extends Thread {

		@Override
		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out.println("dew main waiting for create dewset sleeping ac +" + i);

				}

				dew = new dewset();
				/*
				 * while (dew == null) {
				 * 
				 * i++; Thread.sleep(1000); System.out .println(
				 * "dew dfree waiting for create dewset sleeping dew +" + i);
				 * 
				 * dew = new dewset();
				 * 
				 * }
				 * 
				 * while (dew.ac == null) {
				 * 
				 * i++; Thread.sleep(1000); System.out .println(
				 * "dew dfree waiting for create dewset sleeping dew ac +" + i);
				 * 
				 * dew.ac = ac;
				 * 
				 * } dew.loadmainfile();
				 */

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	JavaPlugin ac = null;

	String pcdfree = "dewdd.dfree.cdfree";
	String pcdenchant = "dewdd.dfree.cdenchant";

	String pudenchant = "dewdd.dfree.udenchant";
	String pcdexchange = "dewdd.dfree.cdexchange";

	String pudexchange = "dewdd.dfree.udexchange";
	String pfeed = "dewdd.dfree.feed";

	String psetamount = "dewdd.dfree.setamount";

	Random rnd = new Random();

	dewset dew = null;

	public DigEventListener2() {
		delay eee = new delay();
		eee.start();
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent e)
			throws UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd dfree");
			e.setCancelled(true);
		}
		if (e.getMessage().equalsIgnoreCase("dewdd dfree") == true) {
			player.sendMessage(
					"ปลั๊กอิน dfree ทำให้การติดป้าย  [dfree] ข้าง despenser จำนวนของในนั้นจะไม่หมด ,   ป้าย [dewenchant] เพื่ออัพของ  ");
			player.sendMessage("[dewexchange] แลกสิ่งของไปมา , ป้าย [dewlift] ขึ้นลิฟลงลิฟ  ");

			player.sendMessage("จำเป็นต้องมี permission...  ");

			e.setCancelled(true);
		}

		if (e.getMessage().equalsIgnoreCase("pdfree") == true) {
			player.sendMessage("c dfree = " + Boolean.toString(player.hasPermission(pcdfree)));

			player.sendMessage("c d exchange = " + Boolean.toString(player.hasPermission(pcdexchange)));
			player.sendMessage("u u exchange = " + Boolean.toString(player.hasPermission(pudexchange)));

			player.sendMessage("c d enchant = " + Boolean.toString(player.hasPermission(pcdenchant)));
			player.sendMessage("u u enchant = " + Boolean.toString(player.hasPermission(pudenchant)));

		}

	}

	@EventHandler
	public void eventja(BlockDamageEvent e) throws NumberFormatException, IndexOutOfBoundsException,
			UserDoesNotExistException, NoLoanPermittedException {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Block block = e.getBlock();
		Player player = e.getPlayer();

		Player p = e.getPlayer();

		if (block.getTypeId() == 63 || block.getTypeId() == 68) {

			if (player.getItemInHand() == null) {
				return;
			}
			if (player.getItemInHand().getTypeId() == 0) {
				return;
			}

			e.setCancelled(true);
			Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equalsIgnoreCase("dewenchant")) {
				sign.setLine(0, "[dewenchant]");
				sign.update(true);
			}
			if (sign.getLine(0).equalsIgnoreCase("dewenchant")) {
				sign.setLine(0, "[dewenchant]");
				sign.update(true);
			}

			if (sign.getLine(0).endsWith("[dewenchant]") == true) {
				if (player.hasPermission(pudenchant) == false) {
					player.sendMessage("ptdew&dewdd: you can't use enchant sign");
					return;
				}

				player.sendMessage("starting");
				player.sendMessage("li 1 : " + sign.getLine(1));
				for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment.values()) {
					player.sendMessage("searching : " + en.getName());
					if (en.getName().toLowerCase().startsWith(sign.getLine(1)) == true) {
						if (player.getItemInHand() == null) {
							player.sendMessage("ptdew&dewdd: enchant please hold your item...");
							return;
						}
						if (player.getItemInHand().getTypeId() == 0) {
							player.sendMessage("ptdew&dewdd: enchant please hold your item...");
							return;
						}

						if (Economy.getMoney(player.getName()) <= Integer.parseInt(sign.getLine(3))) {
							player.sendMessage("ptdew&dewdd: enchant  you don't have enough money!");
							return;
						}
						Economy.subtract(player.getName(), Integer.parseInt(sign.getLine(3)));

						player.sendMessage("ptdew&dewdd: Enchanted '" + en.getName() + "'");
						player.getItemInHand().addUnsafeEnchantment(en, Integer.parseInt(sign.getLine(2)));

						return;
					}
				}

			}

		}

	}

	@EventHandler
	public void eventja(BlockDispenseEvent e) {
		// dew.printC("dispen");

		if (!tr.isrunworld(ac.getName(), e.getBlock().getWorld().getName())) {
			return;
		}

		Block block = e.getBlock();
		if (block.getType() == Material.DROPPER || block.getType() == Material.DISPENSER) {

			// [dfree]
			// number
			// search sign
			Block block2 = null;

			for (int zx = -1; zx <= 1; zx++) {
				for (int zz = -1; zz <= 1; zz++) {
					if (((zx == 1 && zz == 0) || (zx == 0 && zz == 1) || (zx == -1 && zz == 0)
							|| (zx == 0 && zz == -1)) == false) {
						continue;
					}

					block2 = block.getRelative(zx, 0, zz);
					if (block2.getTypeId() == 63 || block2.getTypeId() == 68) {

						Sign sign = (Sign) block2.getState();
						if (sign.getLine(0).equalsIgnoreCase("dfree")) {
							sign.setLine(0, "[dfree]");
							sign.update(true);
						}

						if (sign.getLine(0).equalsIgnoreCase("[dfree]") == true) {
							int c = 1;
							if (c == 1) {

								/*
								 * switch (e.getItem().getTypeId()) { case 8:
								 * case 9: case 10: case 11: case 14: case 15:
								 * case 16: case 19: case 21: case 22: case 35:
								 * case 41: case 42: case 46: case 48: case 49:
								 * case 51: case 52: case 55: case 56: case 57:
								 * case 59: case 73: case 74: case 79: case 81:
								 * case 83: case 86: case 89: case 91: case 93:
								 * case 94: case 103: case 129: case 133: case
								 * 138: case 141: case 142: case 147: case 148:
								 * case 149: case 152: case 153: case 155:
								 * 
								 * case 263: case 264: case 265: case 266: case
								 * 296: case 318: case 319: case 320: case 326:
								 * case 327: case 331: case 334: case 338: case
								 * 341: case 344: case 348: case 349: case 350:
								 * case 351: case 352: case 353: case 356: case
								 * 363: case 364: case 365: case 366: case 367:
								 * case 368: case 369: case 370: case 371: case
								 * 372: case 375: case 376: case 377: case 378:
								 * case 381: case 382: case 384: case 388: case
								 * 391: case 392: case 393: case 394: case 399:
								 * case 404: case 360: case 297:
								 * 
								 * dprint.r.printAll(
								 * "dfree not't acceptable item id at (" +
								 * block.getX() + "," + block.getY() + "," +
								 * block.getZ() + ")" +
								 * e.getItem().getTypeId());
								 * block.breakNaturally(); break; }
								 */

								Dispenser abc = (Dispenser) e.getBlock().getState();
								ItemStack itee = e.getItem();

								int amo = 1;

								if (sign.getLine(1).equalsIgnoreCase("")) {
									amo = 1;
								} else {
									amo = Integer.parseInt(sign.getLine(1));

								}

								itee.setAmount(amo);
								abc.getInventory().addItem(itee);

								e.setItem(itee);

								// dew.printAll("hello");
								return;
							}

							int gn = 0;

							// dew.printC("d free");

							ItemStack itm = null;

							if (sign.getLine(1).equalsIgnoreCase("xxx") == false) {
								gn = Integer.parseInt(sign.getLine(2));

								if (gn != 0) {
									gn = Integer.parseInt(sign.getLine(1));
									itm = new ItemStack(gn, 1);
									byte gn2 = Byte.parseByte(sign.getLine(2));

									itm.getData().setData(gn2);
									e.setItem(itm.getData().toItemStack(1));
								} else {

									gn = Integer.parseInt(sign.getLine(1));
									itm = new ItemStack(gn, 1);
									e.setItem(itm);

								}

							} else {
								itm = e.getItem().getData().toItemStack(1);
								itm.setAmount(2);
								e.setItem(itm);
							}

						}

					}

				}

			}
		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Player player = e.getPlayer();

		String[] m = e.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/setamount")) {
			if (m.length != 2) {
				player.sendMessage("/setamount <number>");
				return;
			}

			if (!player.hasPermission(psetamount)) {
				player.sendMessage("need permission " + psetamount);
				return;
			}

			ItemStack abc = player.getItemInHand();
			abc.setAmount(Integer.parseInt(m[1]));
			player.setItemInHand(abc);
			player.sendMessage("set amount done");
			return;

		}

		if (m[0].equalsIgnoreCase("/dewenchant")) {

			if (player.hasPermission(pcdenchant) == false) {
				player.sendMessage("you don't have permission for enchanting item");
				return;
			}

			if (m.length == 1) {
				// show all list
				for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment.values()) {

					player.sendMessage("ptdew&dewdd: Enchant list id " + en.getId() + " '" + en.getName() + "'");

				}

			} else if (m.length == 2) {

				if (player.getItemInHand() == null) {
					player.sendMessage("/dewenchant enchantname level");
					return;
				}

			} else if (m.length == 3) {
				if (player.getItemInHand() == null) {
					player.sendMessage("Please hold item in your hand");
					return;
				}

				int thelv = Integer.parseInt(m[2]);
				if (thelv > 100 || thelv < -100) {
					player.sendMessage(" -100 < x < 100");
					return;
				}

				int coc = 0;

				for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment.values()) {

					if (en.getName().toLowerCase().indexOf(m[1].toLowerCase()) > -1) {

						coc++;

					}

				}

				if (coc == 1) {
					for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment.values()) {

						if (en.getName().toLowerCase().indexOf(m[1].toLowerCase()) > -1) {

							player.sendMessage("starting enchant " + en.getName());
							player.getItemInHand().addUnsafeEnchantment(en, thelv);
							player.sendMessage("ptdew&dewdd: Enchanted '" + en.getName() + "'");
							return;

						}
					}
				} else {

					for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment.values()) {

						if (en.getName().equalsIgnoreCase(m[1])) {

							player.sendMessage("starting enchant " + en.getName());
							player.getItemInHand().addUnsafeEnchantment(en, thelv);
							player.sendMessage("ptdew&dewdd: Enchanted '" + en.getName() + "'");
							return;

						}
					}
				}

				if (m[1].equalsIgnoreCase("*")) {
					for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment.values()) {

						player.sendMessage("starting enchant " + en.getName());
						player.getItemInHand().addUnsafeEnchantment(en, thelv);
						player.sendMessage("ptdew&dewdd: Enchanted '" + en.getName() + "'");

					}
				}
			}
		} // 3

	}

	public boolean isunsortid(ItemStack impo) {

		if (impo.getType().getMaxDurability() > 0)
			return true;

		if (impo.getEnchantments().size() > 0)
			return true;

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
		case 351:// ink

			return true;
		default:
			return false;
		}
	}

	class autosortchest2_class implements Runnable {
		private Block block;
		private Player player;

		public autosortchest2_class(Block block, Player player) {
			this.block = block;
			this.player = player;
		}

		@Override
		public void run() {

			if (block.getTypeId() != Material.CHEST.getId() && block.getTypeId() != Material.TRAPPED_CHEST.getId()) {

				player.sendMessage(dprint.r.color(tr.gettr("auto_sort_chest_2_is_not_chest")));
				return;
			}

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

				// add to my array
				// find

				boolean founded = false;

				for (l1 = 0; l1 < leng; l1++)
					// player.sendMessage(dprint.r.color("finding old data " +
					// l1);
					if (sid[l1] == it.getTypeId())
						// player.sendMessage(dprint.r.color("ax " + sid[l1]);
						if (sdata[l1].getData().getData() == it.getData().getData()) {

							founded = true;

							// player.sendMessage(dprint.r.color("s=" + l1 +
							// ",id:" + sid[l1] +
							// ",data:"
							// + sdata[l1] + ",amount" + samount[l1]);
							samount[l1] += it.getAmount();
							break;
						}

				// if not found
				if (founded == false) {
					// player.sendMessage(dprint.r.color("can't find old slot");

					founded = false;
					for (l1 = 0; l1 < leng; l1++)
						// find empty
						if (sid[l1] == -1) {
							sid[l1] = it.getTypeId();
							sdata[l1] = it;
							samount[l1] = it.getAmount();
							founded = true;
							break;
						}

					if (founded = false) {
						player.sendMessage(dprint.r.color(tr.gettr("error_auto_sort_chest2_can't_find_empty_slot")));
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
				while (samount[l1] > 0)
					if (samount[l1] >= 64) {
						// player.sendMessage(dprint.r.color("adding > " +
						// sid[l1] +
						// tr.gettr("amount") + "= " +
						// samount[l1]);
						sdata[l1].setAmount(64);
						chest.getInventory().addItem(sdata[l1]);
						samount[l1] -= 64;
					} else {
						// player.sendMessage(dprint.r.color("adding > " +
						// sid[l1] +
						// tr.gettr("amount") + " = " +
						// samount[l1]);

						sdata[l1].setAmount(samount[l1]);
						chest.getInventory().addItem(sdata[l1]);

						samount[l1] -= samount[l1];
					}

				// player.sendMessage(dprint.r.color("x data " +
				// chest.getInventory().getItem(0).getData().getData());

			}

			for (@SuppressWarnings("unused")
			Object obj : sid) {
				obj = null;
			}
			sid = null;
			for (@SuppressWarnings("unused")
			Object obj : sdata) {
				obj = null;
			}
			sdata = null;
			for (@SuppressWarnings("unused")
			Object obj : samount) {
				obj = null;
			}
			samount = null;

		}
	}

	public void autosortchest2(Block block, Player player) {
		autosortchest2_class ar = new autosortchest2_class(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);
	}

	public long sleeptime = 20;

	class chestabsorb_c implements Runnable {

		public chestabsorb_c() {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {
			int d4 = 20;
			Block block = null;
			Block block2 = null;
			int d5 = 1;
			Chest chest = null;
			int slotp = -1;

			for (Player player : Bukkit.getOnlinePlayers()) {

				// search nearby box and sign ... ummm yes
				block = player.getLocation().getBlock();

				/*
				 * if (checkpermissionarea(block)== false) { not protect area
				 * continue; }
				 */
				if (dew.cando_all(block, player, "build") == false) {
					// build
					continue;
				}

				for (int gx = 0 - d4; gx <= 0 + d4; gx++) {
					for (int gy = 0 - d4; gy <= 0 + d4; gy++) {
						for (int gz = 0 - d4; gz <= 0 + d4; gz++) {
							// first search sign
							block = player.getLocation().getBlock().getRelative(gx, gy, gz);
							if (block == null) {
								continue;
							}

							if (block.getTypeId() != 63 && block.getTypeId() != 68) {
								continue;
							}

							Sign sign = (Sign) block.getState();
							if (sign.getLine(0).equalsIgnoreCase("dewtobox")) {
								sign.setLine(0, "[dewtobox]");
								sign.update(true);
							}

							if (sign.getLine(0).equalsIgnoreCase("[dewtobox]") == true) {
								// player.sendMessage(dprint.r.color("found
								// dewtobox sign : "
								// +
								// block.getLocation().getBlockX() + ","
								// + block.getLocation().getBlockY() + "," +
								// block.getLocation().getBlockZ());

								int intb = Integer.parseInt(sign.getLine(1));
								if (intb == 0) {
									continue;
								}

								// after found sign so find box

								// box
								for (int ax = 0 - d5; ax <= 0 + d5; ax++) {
									for (int ay = 0 - d5; ay <= 0 + d5; ay++) {
										for (int az = 0 - d5; az <= 0 + d5; az++) {
											block2 = block.getRelative(ax, ay, az);
											if (block2 == null) {
												continue;
											}

											if (block2.getTypeId() != 54) {
												continue;
											}

											// player.sendMessage(dprint.r.color("found
											// dewtobox chest : "
											// +
											// block2.getLocation().getBlockX()
											// +
											// ","
											// +
											// block2.getLocation().getBlockY()
											// +
											// "," +
											// block2.getLocation().getBlockZ());

											slotp = player.getInventory().first(intb);
											if (slotp == -1) {
												continue;
											}

											chest = (Chest) block2.getState();

											int chestslot = -1;
											chestslot = chest.getInventory().firstEmpty();
											if (chestslot == -1) {
												continue;
											}

											// ready to move
											chest.getInventory().addItem(player.getInventory().getItem(slotp));

											player.getInventory().clear(slotp);

											player.sendMessage(
													dprint.r.color("[dewtobox] " + tr.gettr("moved") + intb));

											// added

											// if true
											// check is empty
											// check item of player

										}
									}
								}

							} // dew to box

						} // loop
					}
				}

			} // player
		}
	}

	public Block chestnearsign(Block temp) {
		int d5 = 1;
		Block typebox = null;

		Block b3 = null;
		for (int ax = -d5; ax <= d5; ax++) {
			for (int ay = -d5; ay <= d5; ay++) {
				for (int az = -d5; az <= +d5; az++) {

					b3 = temp.getRelative(ax, ay, az);

					if (b3.getTypeId() != 54) {
						continue;
					}

					/*
					 * dprint.r.printAll("chestnearsign " + b3.getTypeId() +
					 * " > " + b3.getX() + "," + b3.getZ());
					 */
					// search chest type
					typebox = b3;
					break;
				}
			}
		}

		return typebox;
	}

	public Block protochest(Block block, int d4, String sorttype) {
		Block temp = null;
		Sign sign2 = null;
		Block typebox = null;

		// search sign

		for (int gx2 = 0 - d4; gx2 <= 0 + d4; gx2++) {
			for (int gy2 = 0 - d4; gy2 <= 0 + d4; gy2++) {
				for (int gz2 = 0 - d4; gz2 <= 0 + d4; gz2++) {
					temp = block.getRelative(gx2, gy2, gz2);
					if (temp.getTypeId() != 63 && temp.getTypeId() != 68) {
						continue;
					}

					sign2 = (Sign) temp.getState();
					if (sign2.getLine(0).equalsIgnoreCase("dewsorttype")) {
						sign2.setLine(0, "[dewsorttype]");
						sign2.update(true);
					}

					if (sign2.getLine(0).equalsIgnoreCase("[dewsorttype]")) {
						if (sign2.getLine(1).equalsIgnoreCase(sorttype)) {
							// found proto type

							// searh box of proto sign
							typebox = chestnearsign(temp);
							if (typebox != null) {
								return typebox;
							}

						}
					}
				} // search sign
			}
		}

		return typebox;

	}

	class chestabsorb_c2 implements Runnable {

		private long lastsort2;

		public chestabsorb_c2() {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, sleeptime);
		}

		@Override
		public void run() {

			long nn = System.currentTimeMillis();

			if (nn - lastsort2 < 100) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 10);
				return;
			}

			lastsort2 = nn;

			int d4 = 30;
			Block block = null;

			for (Player player : Bukkit.getOnlinePlayers()) {

				// search nearby box and sign ... ummm yes
				block = player.getLocation().getBlock();

				/*
				 * if (checkpermissionarea(block)== false) { not protect area
				 * continue; }
				 */
				if (dew.cando_all(block, player, "build") == false) {
					// build
					continue;
				}

				// player.sendMessage(dprint.r.color("searching... dewsortbox
				// sign");

				// search any sign near player
				for (int gx = 0 - d4; gx <= 0 + d4; gx++) {
					for (int gy = 0 - d4; gy <= 0 + d4; gy++) {
						for (int gz = 0 - d4; gz <= 0 + d4; gz++) {
							lastsort2 = nn;

							// first search sign
							block = player.getLocation().getBlock().getRelative(gx, gy, gz);

							if (block.getTypeId() != 63 && block.getTypeId() != 68) {
								continue;
							}

							// dewsortbox
							// dewsorttype

							Sign sign = (Sign) block.getState();
							if (sign.getLine(0).equalsIgnoreCase("dewsortbox")) {
								sign.setLine(0, "[dewsortbox]");
								sign.update(true);
							}

							if (sign.getLine(0).equalsIgnoreCase("[dewsortbox]") == true) {

								/*
								 * player.sendMessage(dprint.r.color(
								 * "cur found dewsortbox sign at " +
								 * block.getX() + "," + block.getY() + "," +
								 * block.getZ());
								 */

								String sorttype = sign.getLine(1);
								if (sorttype.equalsIgnoreCase("")) {
									player.sendMessage(dprint.r.color(tr.gettr("sorttype_name_must_not_null")));
									continue;
								}

								// got sign type
								// search current chest
								Block curchest = chestnearsign(block);

								if (curchest == null) {
									player.sendMessage(dprint.r.color("curchest == null"));

									continue;

								}

								Block curprochest = protochest(block, d4, sorttype);
								if (curprochest == null) {
									player.sendMessage(
											dprint.r.color("curprochest == null > " + d4 + " , " + sorttype));

									continue;

								}

								// player.sendMessage(dprint.r.color("opening
								// curchest..."+
								// curchest.getTypeId());
								Chest curchest1 = (Chest) curchest.getState();
								// player.sendMessage(dprint.r.color("opening
								// curchest done");

								// player.sendMessage(dprint.r.color("opening
								// curprochest..."+
								// curprochest.getTypeId());

								Chest curchestin = (Chest) curprochest.getState();
								// player.sendMessage(dprint.r.color("opening
								// curprochest done");

								// player.sendMessage(dprint.r.color("opened
								// both chest");
								// after got cur chest
								// search another dewsortbox for swap
								// *********************
								Block temp = null;
								for (int jx = 0 - d4; jx <= 0 + d4; jx++) {
									for (int jy = 0 - d4; jy <= 0 + d4; jy++) {
										for (int jz = 0 - d4; jz <= 0 + d4; jz++) {
											if (jx == 0 && jy == 0 && jz == 0) {
												continue;
											}
											// first search sign
											temp = block.getLocation().getBlock().getRelative(jx, jy, jz);

											if (temp.getTypeId() != 63 && temp.getTypeId() != 68) {
												continue;
											}

											// dewsortbox
											// dewsorttype

											Sign js = (Sign) temp.getState();

											if (js.getLine(0).equalsIgnoreCase("[dewsortbox]") == true) {

												/*
												 * player.sendMessage(dprint.r.
												 * color (
												 * "swap found dewsortbox at " +
												 * temp.getX() + "," +
												 * temp.getY() + "," +
												 * temp.getZ());
												 */

												String jsorttype = js.getLine(1);
												if (jsorttype.equalsIgnoreCase("")) {
													// player.sendMessage(dprint.r.color("swap_sorttype_name_must_not_null");
													continue;
												}

												// got sign type
												// search current chest
												Block swapchest = chestnearsign(temp);
												if (swapchest == null) {
													// player.sendMessage(dprint.r.color("swapchest
													// == null");
													continue;
												}

												if (swapchest.getLocation().distance(curchest.getLocation()) <= 1) {
													continue;
												}

												Block swapprochest = protochest(temp, d4, jsorttype);
												if (swapprochest == null) {
													// player.sendMessage(dprint.r.color("swapprochest
													// == null");
													continue;
												}

												// player.sendMessage(dprint.r.color("opening
												// swapprochest...");

												Chest swapchestin = (Chest) swapprochest.getState();
												// player.sendMessage(dprint.r.color("opening
												// swapchest...");

												Chest swapchest1 = (Chest) swapchest.getState();

												// player.sendMessage(dprint.r.color("opened
												// both chest swap");

												for (int ikn = 0; ikn < curchest1.getInventory().getSize(); ikn++) {
													ItemStack i1 = curchest1.getInventory().getItem(ikn);

													if (i1 == null) {
														continue;
													}

													// search 1 is not in
													// 1prototype
													int i1proslot = curchestin.getInventory().first(i1.getType());
													if (i1proslot > -1) {
														// player.sendMessage(dprint.r.color("i1proslot
														// > -1");
														continue;
													}

													// if not found it's mean
													// wrong item in cur chest

													// check it that item in
													// second prototype

													if (swapchestin.getInventory().first(i1.getType()) == -1) {
														// player.sendMessage(dprint.r.color("swapchestin
														// can't found i1
														// item");
														continue;
													}

													// time to swap item
													// search free item on
													// second

													int freeslot = swapchest1.getInventory().firstEmpty();
													if (freeslot == -1) {
														// player.sendMessage(dprint.r.color("free
														// slot of swapchest 1
														// is -1");
														continue;
													}

													// time to move
													// player.sendMessage(dprint.r.color("moviing
													// item");
													swapchest1.getInventory().setItem(freeslot, i1);
													curchest1.getInventory().setItem(ikn, new ItemStack(0));

													player.sendMessage(dprint.r.color(
															"swaped item " + i1.getType().name() + " " + i1.getAmount())
															+ " from " + curchest.getX() + "," + curchest.getY() + ","
															+ curchest.getZ() + "[" + sorttype + "]" + " to "
															+ swapchest.getX() + "," + swapchest.getY() + ","
															+ swapchest.getZ() + "[" + jsorttype + "]");

													lastsort2 = nn;

													new chestabsorb_c2();
													return;

												}
												// how to swap
												// loop all of item of source

											}
										} // search another chest
									}
								}

								// **********************
								// search another chest block for swap item

							} // dew to box

						} // searh any sign near player
					}
				}

			} // player
		}
	}

	public void chestabsorb() {

		new chestabsorb_c();
	}

	public void chestabsorb2() {

		new chestabsorb_c2();
	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Action act;
		act = e.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)) {
			return;
		}

		Block block = e.getClickedBlock();
		Player player = e.getPlayer();

		if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {

			int x = 0;
			int y = 0;
			int z = 0;
			Block block2 = null;

			for (x = -1; x <= 1; x++) {
				for (y = -1; y <= 1; y++) {
					for (z = -1; z <= 1; z++) {
						block2 = block.getRelative(x, y, z);
						if (block2.getTypeId() == 63 || block2.getTypeId() == 68) {
							Sign sign = (Sign) block2.getState();
							if (sign.getLine(0).equalsIgnoreCase("autosort")) {
								sign.setLine(0, "[autosort]");
								sign.update(true);
							}

							if (sign.getLine(0).equalsIgnoreCase("[autosort]") == true) {

								autosortchest2(block, player);

								return;

							}
						}

					}
				}
			}

		}

		if (block.getType() == Material.DISPENSER) { // dis
			// [dfree]
			// number
			// search sign
			int x = 0;
			int y = 0;
			int z = 0;
			Block block2 = null;

			for (x = -1; x <= 1; x++) {
				for (y = -1; y <= 1; y++) {
					for (z = -1; z <= 1; z++) {
						block2 = block.getRelative(x, y, z);
						if (block2.getTypeId() == 63 || block2.getTypeId() == 68) {
							Sign sign = (Sign) block2.getState();
							if (sign.getLine(0).equalsIgnoreCase("dfree")) {
								sign.setLine(0, "[dfree]");
								sign.update(true);
							}

							if (sign.getLine(0).equalsIgnoreCase("[dfree]") == true) {
								if (player.hasPermission(pcdfree) == false) {

									e.setCancelled(true);
									player.sendMessage("ptdew&dewdd: only admin for open free dispense");
									return;
								}
							}
						}

					}
				}
			}

		} // dis

		if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {

			Sign sign = (Sign) block.getState();

			

			if (sign.getLine(0).equalsIgnoreCase("dewtobox")) {
				sign.setLine(0, "[dewtobox]");
				sign.update(true);
			}
			if (sign.getLine(0).equalsIgnoreCase("[dewtobox]") == true) {
				// player.sendMessage("dewtobox run");
				chestabsorb();
			}

			if (sign.getLine(0).equalsIgnoreCase("dewsortbox")) {
				sign.setLine(0, "[dewsortbox]");
				sign.update(true);
			}

			if (sign.getLine(0).equalsIgnoreCase("[dewsortbox]") || sign.getLine(0).equalsIgnoreCase("[dewsorttype]")) {
				// player.sendMessage("dewtobox run");
				chestabsorb2();
			}

			// exchange

			if (act == Action.RIGHT_CLICK_BLOCK) {
				// lift
				if (sign.getLine(0).equalsIgnoreCase("dewlift")) {
					sign.setLine(0, "[dewlift]");
					sign.update(true);
				}

				if (sign.getLine(0).equalsIgnoreCase("[dewlift]") == true) {
					Block block2 = null;

					if (sign.getLine(1).equalsIgnoreCase("up") == true) {
						for (int yy = 1; yy <= 253; yy++) {
							if (yy + block.getY() > 253) {
								return;
							}
							block2 = block.getRelative(0, yy, 0);
							if (block2.getTypeId() != 0 && block2.getRelative(0, 1, 0).getTypeId() == 0
									&& block2.getRelative(0, 2, 0).getTypeId() == 0) {

								Location ll = player.getLocation();
								ll.setY(yy + 1 + block.getY());
								player.teleport(ll);

								player.sendMessage("ptdew&dewdd: [dewlift] = up = (" + block.getX() + ","
										+ (yy + 1 + block.getY()) + "," + block.getZ() + ")");
								return;
							}
						}
						player.sendMessage("ptdew&dewdd: [dewlift] up  y >253   (can't find free space area)");
						return;
					} else if (sign.getLine(1).equalsIgnoreCase("down") == true) {
						for (int yy = 1; yy <= 253; yy++) {
							if (block.getY() - yy < 1) {
								return;
							}
							block2 = block.getRelative(0, -yy, 0);
							if (block2.getTypeId() != 0 && block2.getRelative(0, 1, 0).getTypeId() == 0
									&& block2.getRelative(0, 2, 0).getTypeId() == 0) {
								Location ll = player.getLocation();
								ll.setY(block.getY() - yy + 1);
								player.teleport(ll);
								player.sendMessage("ptdew&dewdd: [dewlift] = down = (" + block.getX() + ","
										+ (block.getY() - yy) + "," + block.getZ() + ")");
								return;
							}
						}
						player.sendMessage("ptdew&dewdd: [dewlift] down y <1  (can't find free space area)");
						return;
					} else {
						player.sendMessage("ptdew&dewdd: [dewlift]   up & down   (only)");
						player.sendMessage(
								"ptdew&dewdd: [dewlift]   up & down   (บรรทัดสองต้องใช้ up หรือ down เท่านั้น)");
					}
				}
			} // action right

		} // sign

	}

	@EventHandler
	public void eventja(SignChangeEvent e) {
		Player player = e.getPlayer();

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		if (e.getLine(0).endsWith("[dfree]") == true) {
			if (player.hasPermission(pcdfree) == false) {
				e.setLine(0, "<dfree>");
				e.setLine(1, "only admin!");
				e.setLine(2, "Access Deny");
				e.setLine(3, "...");

				player.sendMessage("ptdew&dewdd: [dfree] you don't have permission c dfree");
				player.sendMessage("ptdew&dewdd: [dfree] คุณไม่มีสิทธิสร้างป้ายนี้");

				return;
			} else {
				player.sendMessage("ptdew&dewdd: dfree done");
			}

		}

		if (e.getLine(0).endsWith("[dewlift]") == true) {

			if (e.getLine(1).equalsIgnoreCase("")) {
				e.setLine(0, "<dewlift>");
				e.setLine(1, "up|down?");
				return;
			}

			if (e.getLine(1).equalsIgnoreCase("up") == false && e.getLine(1).equalsIgnoreCase("down") == false) {
				e.setLine(0, "<dewlift>");
				e.setLine(1, "up|down?");
				return;
			}

			player.sendMessage("ptdew&dewdd: dewlift done");

		}

		if (e.getLine(0).endsWith("[dewenchant]") == true) {
			if (player.hasPermission(pcdenchant) == false) {
				e.setLine(0, "<dewenchant>");
				e.setLine(1, "only admin!");
				e.setLine(2, "Access Deny");
				e.setLine(3, "...");

				player.sendMessage("ptdew&dewdd: [dewenchant] you don't have permission c denchant");
				player.sendMessage("ptdew&dewdd: [dewenchant] คุณไม่มีสิทธิสร้างป้ายนี้");

				return;
			} else {
				if (e.getLine(1).equalsIgnoreCase("")) {
					e.setLine(0, "<dewenchant>");
					e.setLine(1, "enchant name?");
					return;
				}
				if (e.getLine(2).equalsIgnoreCase("")) {
					e.setLine(0, "<dewenchant>");
					e.setLine(2, "level?");
					return;
				}
				if (e.getLine(3).equalsIgnoreCase("")) {
					e.setLine(0, "<dewenchant>");
					e.setLine(3, "price?");
					return;
				}

				player.sendMessage("ptdew&dewdd: dewenchant done");
			}

		}

	}

	public boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

} // class
