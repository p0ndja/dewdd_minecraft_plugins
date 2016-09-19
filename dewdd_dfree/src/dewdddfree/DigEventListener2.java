/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdddfree;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	JavaPlugin	ac			= null;
	String		pcdfree		= "dewdd.dfree.cdfree";

	String		pcdenchant	= "dewdd.dfree.cdenchant";
	String		pudenchant	= "dewdd.dfree.udenchant";

	String		pcdexchange	= "dewdd.dfree.cdexchange";
	String		pudexchange	= "dewdd.dfree.udexchange";

	String		pfeed		= "dewdd.dfree.feed";
	String		psetamount	= "dewdd.dfree.setamount";

	Random		rnd			= new Random();
	
	public DigEventListener2() {
		delay eee = new delay();
		eee.start();
	}
	
	class delay extends Thread {

		@Override
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

			/*	while (dew == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew dfree waiting for create dewset sleeping dew +"
									+ i);

					dew = new dewset();

				}

				while (dew.ac == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew dfree waiting for create dewset sleeping dew ac +"
									+ i);

					dew.ac = ac;

				}
				dew.loadmainfile();*/

			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}


	@EventHandler
	public void eventja(AsyncPlayerChatEvent e)
			throws UnknownDependencyException, InvalidPluginException,
			InvalidDescriptionException {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd dfree");
			e.setCancelled(true);
		}
		if (e.getMessage().equalsIgnoreCase("dewdd dfree") == true) {
			player.sendMessage("ปลั๊กอิน dfree ทำให้การติดป้าย  [dfree] ข้าง despenser จำนวนของในนั้นจะไม่หมด ,   ป้าย [dewenchant] เพื่ออัพของ  ");
			player.sendMessage("[dewexchange] แลกสิ่งของไปมา , ป้าย [dewlift] ขึ้นลิฟลงลิฟ  ");

			player.sendMessage("จำเป็นต้องมี permission...  ");

			e.setCancelled(true);
		}

		if (e.getMessage().equalsIgnoreCase("pdfree") == true) {
			player.sendMessage("c dfree = "
					+ Boolean.toString(player.hasPermission(pcdfree)));

			player.sendMessage("c d exchange = "
					+ Boolean.toString(player.hasPermission(pcdexchange)));
			player.sendMessage("u u exchange = "
					+ Boolean.toString(player.hasPermission(pudexchange)));

			player.sendMessage("c d enchant = "
					+ Boolean.toString(player.hasPermission(pcdenchant)));
			player.sendMessage("u u enchant = "
					+ Boolean.toString(player.hasPermission(pudenchant)));

		}

		

	}

	@EventHandler
	public void eventja(BlockDamageEvent e) throws NumberFormatException,
			IndexOutOfBoundsException, UserDoesNotExistException,
			NoLoanPermittedException {

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
				for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment
						.values()) {
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

						if (Economy.getMoney(player.getName()) <= Integer
								.parseInt(sign.getLine(3))) {
							player.sendMessage("ptdew&dewdd: enchant  you don't have enough money!");
							return;
						}
						Economy.subtract(player.getName(),
								Integer.parseInt(sign.getLine(3)));

						player.sendMessage("ptdew&dewdd: Enchanted '"
								+ en.getName() + "'");
						player.getItemInHand().addUnsafeEnchantment(en,
								Integer.parseInt(sign.getLine(2)));

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
		if (block.getTypeId() == 23) {

			// [dfree]
			// number
			// search sign
			Block block2 = null;

			for (int zx = -1; zx <= 1; zx++) {
				for (int zz = -1; zz <= 1; zz++) {
					if (((zx == 1 && zz == 0) || (zx == 0 && zz == 1)
							|| (zx == -1 && zz == 0) || (zx == 0 && zz == -1)) == false) {
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

								switch (e.getItem().getTypeId()) {
								case 8:
								case 9:
								case 10:
								case 11:
								case 14:
								case 15:
								case 16:
								case 19:
								case 21:
								case 22:
								case 35:
								case 41:
								case 42:
								case 46:
								case 48:
								case 49:
								case 51:
								case 52:
								case 55:
								case 56:
								case 57:
								case 59:
								case 73:
								case 74:
								case 79:
								case 81:
								case 83:
								case 86:
								case 89:
								case 91:
								case 93:
								case 94:
								case 103:
								case 129:
								case 133:
								case 138:
								case 141:
								case 142:
								case 147:
								case 148:
								case 149:
								case 152:
								case 153:
								case 155:

								case 263:
								case 264:
								case 265:
								case 266:
								case 296:
								case 318:
								case 319:
								case 320:
								case 326:
								case 327:
								case 331:
								case 334:
								case 338:
								case 341:
								case 344:
								case 348:
								case 349:
								case 350:
								case 351:
								case 352:
								case 353:
								case 356:
								case 363:
								case 364:
								case 365:
								case 366:
								case 367:
								case 368:
								case 369:
								case 370:
								case 371:
								case 372:
								case 375:
								case 376:
								case 377:
								case 378:
								case 381:
								case 382:
								case 384:
								case 388:
								case 391:
								case 392:
								case 393:
								case 394:
								case 399:
								case 404:
								case 360:
								case 297:

									dprint.r.printAll("dfree not't acceptable item id at ("
											+ block.getX()
											+ ","
											+ block.getY()
											+ ","
											+ block.getZ()
											+ ")"
											+ e.getItem().getTypeId());
									block.breakNaturally();
									break;
								}

								Dispenser abc = (Dispenser) e.getBlock()
										.getState();
								ItemStack itee = e.getItem();

								int amo = 1;

								if (sign.getLine(1).equalsIgnoreCase("")) {
									amo = 1;
								}
								else {
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
								}
								else {

									gn = Integer.parseInt(sign.getLine(1));
									itm = new ItemStack(gn, 1);
									e.setItem(itm);

								}

							}
							else {
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
				for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment
						.values()) {

					player.sendMessage("ptdew&dewdd: Enchant list id "
							+ en.getId() + " '" + en.getName() + "'");

				}

			}
			else if (m.length == 2) {

				if (player.getItemInHand() == null) {
					player.sendMessage("/dewenchant enchantname level");
					return;
				}

			}
			else if (m.length == 3) {
				if (player.getItemInHand() == null) {
					player.sendMessage("Please hold item in your hand");
					return;
				}

				Enchantment ar = null;
				;
				if (isNumeric(m[1]) == true) {
					ar = Enchantment.getById(Integer.parseInt(m[1]));
				}

				if (ar == null) {

					int coc = 0;

					for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment
							.values()) {

						if (en.getName().toLowerCase()
								.indexOf(m[1].toLowerCase()) > -1) {

							coc++;

						}

					}

					if (coc == 1) {
						for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment
								.values()) {

							if (en.getName().toLowerCase()
									.indexOf(m[1].toLowerCase()) > -1) {

								player.sendMessage("starting enchant "
										+ en.getName());
								player.getItemInHand().addUnsafeEnchantment(en,
										Integer.parseInt(m[2]));
								player.sendMessage("ptdew&dewdd: Enchanted '"
										+ en.getName() + "'");
								return;

							}
						}
					}
					else {

						for (org.bukkit.enchantments.Enchantment en : org.bukkit.enchantments.Enchantment
								.values()) {

							if (en.getName().equalsIgnoreCase(m[1])) {

								player.sendMessage("starting enchant "
										+ en.getName());
								player.getItemInHand().addUnsafeEnchantment(en,
										Integer.parseInt(m[2]));
								player.sendMessage("ptdew&dewdd: Enchanted '"
										+ en.getName() + "'");
								return;

							}
						}
					}
				}
			} // 3

		}

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
		
		
		if (block.getType() == Material.CHEST
				) { 
			
			int x = 0;
			int y = 0;
			int z = 0;
			Block block2 = null;

			for (x = -1; x <= 1; x++) {
				for (y = -1; y <= 1; y++) {
					for (z = -1; z <= 1; z++) {
						block2 = block.getRelative(x, y, z);
						if (block2.getTypeId() == 63
								|| block2.getTypeId() == 68) {
							Sign sign = (Sign) block2.getState();
							if (sign.getLine(0).equalsIgnoreCase("autosort")) {
								sign.setLine(0, "[autosort]");
								sign.update(true);
							}
							
							
							if (sign.getLine(0).equalsIgnoreCase("[autosort]") == true) {
								

									dewddflower.Main.ds.autosortchest2(block, player);
									
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
						if (block2.getTypeId() == 63
								|| block2.getTypeId() == 68) {
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
			
			if (sign.getLine(0) != null) {
				dewddflower.Main.ds.linkurl(player, sign.getLine(0));
			}
			
			if (sign.getLine(0).equalsIgnoreCase("dewtobox")) {
				sign.setLine(0, "[dewtobox]");
				sign.update(true);
			}
			if (sign.getLine(0).equalsIgnoreCase("[dewtobox]") == true) {
				// player.sendMessage("dewtobox run");
				dewddflower.Main.ds.chestabsorb();
			}
			
			if (sign.getLine(0).equalsIgnoreCase("dewsortbox")) {
				sign.setLine(0, "[dewsortbox]");
				sign.update(true);
			}

			if (sign.getLine(0).equalsIgnoreCase("[dewsortbox]")
					|| sign.getLine(0).equalsIgnoreCase("[dewsorttype]")) {
				// player.sendMessage("dewtobox run");
				dewddflower.Main.ds.chestabsorb2();
			}
			
			
			
			// exchange
			if (sign.getLine(0).equalsIgnoreCase("dewexchange")) {
				sign.setLine(0, "[dewexchange]");
				sign.update(true);
			}
			
			if (sign.getLine(0).endsWith("[dewexchange]") == true) {
				if (player.hasPermission(pudexchange) == false) {
					player.sendMessage("ptdew&dewdd: you don't have permission for use dew exchange");
					return;

				}

				if (player.getItemInHand() == null) {
					player.sendMessage("ptdew&dewdd: can't use [dewexchange] cuz your item in hand = empty");
					return;
				}

				int a1 = Integer.parseInt(sign.getLine(1));
				int a2 = Integer.parseInt(sign.getLine(2));
				if (act == Action.LEFT_CLICK_BLOCK) {
					if (player.getItemInHand().getTypeId() == a1) {
						player.getItemInHand().setTypeId(a2);
						player.sendMessage("ptdew&dewdd: exchange done!");
						e.setCancelled(true);
						return;
					}
					else {
						player.sendMessage("ptdew&dewdd: What!! this item can't exchange");
						e.setCancelled(true);
						return;
					}
				}
				else if (act == Action.RIGHT_CLICK_BLOCK) {
					if (player.getItemInHand().getTypeId() == a2) {
						player.getItemInHand().setTypeId(a1);
						player.sendMessage("ptdew&dewdd: exchange done!");
						e.setCancelled(true);
						return;
					}
					else {
						player.sendMessage("ptdew&dewdd: What!! this item can't exchange");
						e.setCancelled(true);
						return;
					}
				}

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
							if (block2.getTypeId() != 0
									&& block2.getRelative(0, 1, 0).getTypeId() == 0
									&& block2.getRelative(0, 2, 0).getTypeId() == 0) {

								Location ll = player.getLocation();
								ll.setY(yy + 1 + block.getY());
								player.teleport(ll);

								player.sendMessage("ptdew&dewdd: [dewlift] = up = ("
										+ block.getX()
										+ ","
										+ (yy + 1 + block.getY())
										+ ","
										+ block.getZ() + ")");
								return;
							}
						}
						player.sendMessage("ptdew&dewdd: [dewlift] up  y >253   (can't find free space area)");
						return;
					}
					else if (sign.getLine(1).equalsIgnoreCase("down") == true) {
						for (int yy = 1; yy <= 253; yy++) {
							if (block.getY() - yy < 1) {
								return;
							}
							block2 = block.getRelative(0, -yy, 0);
							if (block2.getTypeId() != 0
									&& block2.getRelative(0, 1, 0).getTypeId() == 0
									&& block2.getRelative(0, 2, 0).getTypeId() == 0) {
								Location ll = player.getLocation();
								ll.setY(block.getY() - yy + 1);
								player.teleport(ll);
								player.sendMessage("ptdew&dewdd: [dewlift] = down = ("
										+ block.getX()
										+ ","
										+ (block.getY() - yy)
										+ ","
										+ block.getZ() + ")");
								return;
							}
						}
						player.sendMessage("ptdew&dewdd: [dewlift] down y <1  (can't find free space area)");
						return;
					}
					else {
						player.sendMessage("ptdew&dewdd: [dewlift]   up & down   (only)");
						player.sendMessage("ptdew&dewdd: [dewlift]   up & down   (บรรทัดสองต้องใช้ up หรือ down เท่านั้น)");
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

		if (e.getLine(0).endsWith("[dewexchange]") == true
				|| e.getLine(0).equalsIgnoreCase("[dewexchange]")) {
			if (player.hasPermission(pcdexchange) == false) {
				e.setLine(0, "<dewexchange>");
				e.setLine(1, "only admin!");
				e.setLine(2, "Access Deny");
				e.setLine(3, "...");

				player.sendMessage("ptdew&dewdd: [dewexchange] you dan't have permission");
				player.sendMessage("ptdew&dewdd: [dewexchange] มีแค่แอดมินเท่านั้นที่สร้างได้");

				return;
			}
			else {
				if (e.getLine(1).equalsIgnoreCase("")) {
					e.setLine(0, "<dewexchange>");
					e.setLine(1, "source id?");
					return;
				}
				if (e.getLine(2).equalsIgnoreCase("")) {
					e.setLine(0, "<dewexchange>");
					e.setLine(2, "des id?");

					return;
				}

				player.sendMessage("ptdew&dewdd: dewexchange done");
			}

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
			}
			else {
				player.sendMessage("ptdew&dewdd: dfree done");
			}

		}

		if (e.getLine(0).endsWith("[dewlift]") == true) {

			if (e.getLine(1).equalsIgnoreCase("")) {
				e.setLine(0, "<dewlift>");
				e.setLine(1, "up|down?");
				return;
			}

			if (e.getLine(1).equalsIgnoreCase("up") == false
					&& e.getLine(1).equalsIgnoreCase("down") == false) {
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
			}
			else {
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
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}

} // class

