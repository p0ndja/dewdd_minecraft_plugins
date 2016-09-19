/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddautoseed;

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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dewddflower.dewset;

public class dewddautoseed implements Listener {

	class delay_dewset extends Thread {

		@Override
		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out.println("dew ft waiting for create dewset sleeping ac +" + i);

				}

				while (dewddflower.Main.ds == null) {

					i++;
					Thread.sleep(1000);
					System.out.println("dew ft waiting for create dewset sleeping dew +" + i);

					// dew = dewddflower.Main.ds;

				}
				dew = dewddflower.Main.ds;

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}

	public JavaPlugin ac = null;

	dewset dew;

	public dewddautoseed() {

		delay_dewset abc2 = new delay_dewset();
		abc2.start();

	}

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

	@EventHandler
	public void eventja(BlockPlaceEvent event) {

		/*
		 * Player player = event.getPlayer(); Block block = event.getBlock();
		 * 
		 * switch (player.getItemInHand().getType()) { case CROPS: case CARROT:
		 * case POTATO: case PUMPKIN_SEEDS: case MELON_SEEDS:
		 * 
		 * dew.soiladdseedrecusive(block, player, player.getItemInHand()
		 * .getType(), true);
		 * 
		 * return; }
		 */
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
				dew.seedglow(b, p);
			}
		}
	}
} // class
