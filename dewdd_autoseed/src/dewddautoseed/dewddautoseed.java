/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddautoseed;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class dewddautoseed implements Listener {


	public JavaPlugin ac = null;


	@EventHandler
	public void eventja(BlockDispenseEvent e) {

		if (e.getBlock().getType() == Material.DROPPER) {
			Dropper dropper = (Dropper) e.getBlock().getState();
			Material toSet;

			switch (e.getItem().getType()) {
			case SEEDS:
				toSet = Material.CROPS;
				break;
			case PUMPKIN_SEEDS:
				toSet = Material.PUMPKIN_STEM;
				break;

			case MELON_SEEDS:
				toSet = Material.MELON_STEM;
				break;

			case POTATO_ITEM:
			case POTATO:
				toSet = Material.POTATO;
				break;

			case CARROT_ITEM:
			case CARROT:
				toSet = Material.CARROT;
				break;

			case NETHER_WARTS:

				toSet = Material.NETHER_WARTS;
				break;

			default:
				return;

			}

			Inventory inv = dropper.getInventory();

			// dprint.r.printAll("yap");

			int search = 12;
			Block block = e.getBlock().getRelative(0, -1, 0);

			for (int x = -search; x <= search; x++) {
				Block rera = block.getRelative(x, 0, 0);
				if (rera.getType() == Material.SOIL || rera.getType() == Material.SOUL_SAND) {
					Block upper = rera.getRelative(0, 1, 0);

					e.setCancelled(true);
					if (upper.getType() == Material.AIR) {

						int slot = inv.first(e.getItem().getType());

						if (slot == -1)
							continue;

						ItemStack eo = inv.getItem(slot);

						eo.setAmount(eo.getAmount() - 1);
						inv.setItem(slot, eo);
						dropper.update(true);
						upper.setType(toSet);
						return;

					}

				}
			}

			for (int x = -search; x <= search; x++) {
				Block rera = block.getRelative(0, x, 0);
				if (rera.getType() == Material.SOIL || rera.getType() == Material.SOUL_SAND) {
					Block upper = rera.getRelative(0, 1, 0);

					e.setCancelled(true);
					if (upper.getType() == Material.AIR) {

						int slot = inv.first(e.getItem().getType());

						if (slot == -1)
							continue;

						ItemStack eo = inv.getItem(slot);

						eo.setAmount(eo.getAmount() - 1);
						inv.setItem(slot, eo);
						dropper.update(true);
						upper.setType(toSet);
						return;

					}

				}
			}

			for (int x = -search; x <= search; x++) {
				Block rera = block.getRelative(0, 0, x);
				if (rera.getType() == Material.SOIL || rera.getType() == Material.SOUL_SAND) {
					Block upper = rera.getRelative(0, 1, 0);

					e.setCancelled(true);
					if (upper.getType() == Material.AIR) {

						int slot = inv.first(e.getItem().getType());

						if (slot == -1)
							continue;

						ItemStack eo = inv.getItem(slot);

						eo.setAmount(eo.getAmount() - 1);
						inv.setItem(slot, eo);
						dropper.update(true);
						upper.setType(toSet);
						return;

					}

				}
			}

		}

	}

	@EventHandler
	public void eventja(BlockPistonExtendEvent e) {

		Block block = e.getBlock();
		// search sign
		int search = 1;
		for (int x = -search; x <= search; x++)
			for (int y = -search; y <= search; y++)
				for (int z = -search; z <= search; z++) {
					Block bo = e.getBlock().getRelative(x, y, z);
					if (bo.getType() == Material.SIGN_POST || bo.getType() == Material.WALL_SIGN) {
						Sign sign = (Sign) bo.getState();
						if (sign.getLine(0).equalsIgnoreCase("dewbreak")) {
							sign.setLine(0, "[dewbreak]");
							sign.update(true);
						}

						if (sign.getLine(0).equalsIgnoreCase("[dewbreak]")) {
							block.getRelative(e.getDirection()).breakNaturally();

						}
					}

				}

	}
	
	public boolean decreseitem1(Player player, int itemid, int itemdata, boolean forcetruedata) {
		ItemStack itm = null;
		int lenl = 0;

		if (itemid == 0)
			return false;

		for (lenl = 0; lenl < player.getInventory().getContents().length; lenl++) {
			if (player.getInventory().getItem(lenl) == null) {
				continue;
			}

			itm = player.getInventory().getItem(lenl);

			if (itemid == 8 || itemid == 9 || itemid == 326)
				return true;
			else if (itemid == 44 || itemid == 43) {
				if (itm.getTypeId() != 44 && itm.getTypeId() != 43) {
					continue;
				}
			}

			// piston
			else if (itemid == 33 || itemid == 34 || itemid == 29) {
				if (itm.getTypeId() != 33 && itm.getTypeId() != 34 && itm.getTypeId() != 29) {
					continue;
				}
			}

			// dirt
			else if (itemid == 2 || itemid == 3 || itemid == 60) {
				if (itm.getTypeId() != 3 && itm.getTypeId() != 2 && itm.getTypeId() != 60) {
					continue;
				}
			}
			// repeater
			else if (itemid == 356 || itemid == 93 || itemid == 94) {
				if (itm.getTypeId() != 356 && itm.getTypeId() != 93 && itm.getTypeId() != 94) {
					continue;
				}
			}
			// redstone torch
			else if (itemid == 75 || itemid == 76) {
				if (itm.getTypeId() != 75 && itm.getTypeId() != 76) {
					continue;
				}
			}
			// redstone wire
			else if (itemid == 331 || itemid == 55) {
				if (itm.getTypeId() != 331 && itm.getTypeId() != 55) {
					continue;
				}
			}
			// pumpkin
			else if (itemid == 361 || itemid == 104) {
				if (itm.getTypeId() != 361 && itm.getTypeId() != 104) {
					continue;
				}
			}
			// melon
			else if (itemid == 362 || itemid == 105) {
				if (itm.getTypeId() != 362 && itm.getTypeId() != 105) {
					continue;
				}
			}

			// sugar
			else if (itemid == 338 || itemid == 83) {
				if (itm.getTypeId() != 338 && itm.getTypeId() != 83) {
					continue;
				}
			}

			// wheat
			else if (itemid == 295 || itemid == 59) {
				if (itm.getTypeId() != 295 && itm.getTypeId() != 59) {
					continue;
				}
			}
			// carrot
			else if (itemid == 391 || itemid == 141) {
				if (itm.getTypeId() != 391 && itm.getTypeId() != 141) {
					continue;
				}
			}
			// potato
			else if (itemid == 392 || itemid == 142) {
				if (itm.getTypeId() != 392 && itm.getTypeId() != 142) {
					continue;
				}
			} else if (itm.getTypeId() != itemid) {
				continue;
			}

			if (forcetruedata == true)
				if (itm.getData().getData() != itemdata) {
					continue;
				}

			if (itm.getAmount() != 1) {
				itm.setAmount(itm.getAmount() - 1);
				return true;
			} else {
				ItemStack emy = player.getItemInHand();
				emy.setTypeId(0);

				player.getInventory().setItem(lenl, emy);

				return true;
			}

		}
		return false;
	}
	
	class seedGlowByBoneMeal_c implements Runnable {
		private Block block = null;
		private Player player = null;

		public seedGlowByBoneMeal_c(Block block, Player player) {
			this.player = player;
			this.block = block;
		}

		@Override
		public void run() {

			int d = 5;
			Block b2 = null;

			for (int gx = -d; gx <= d; gx++) {
				for (int gy = -d; gy <= d; gy++) {
					for (int gz = -d; gz <= d; gz++) {
						b2 = block.getRelative(gx, gy, gz);
						switch (b2.getTypeId()) {

						case 142:
						case 141:
						case 59:
						case 104:
						case 105:

							if (b2.getData() >= 7) {
								continue;
							}
							if (decreseitem1(player, 351, 15, true) == false)
								return;
							b2.setData((byte) 7);

							seedGlowByBoneMeal(b2, player);

							break;

						}

					}
				}
			}

		}
	}


	public void seedGlowByBoneMeal(Block block, Player player) {
		seedGlowByBoneMeal_c arr = new seedGlowByBoneMeal_c(block, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, arr);
	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		Action act = e.getAction();

		if (b == null)
			return;

		if (p.getItemInHand().getTypeId() == 351 && p.getItemInHand().getData().getData() == 15
				&& act == Action.RIGHT_CLICK_BLOCK) {
			switch (b.getType()) {
			case POTATO:
			case CARROT:
			case CROPS:
			case PUMPKIN_SEEDS:
			case MELON_SEEDS:
				seedGlowByBoneMeal(b, p);
			}
		}
	}
} // class
