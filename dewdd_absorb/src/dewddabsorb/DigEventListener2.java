/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddabsorb;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	JavaPlugin	ac		= null;
	int			amount	= 0;

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd absorb");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd absorb") == true) {
			player.sendMessage("ปลั๊กอิน dewddabsorb จะทำให้บางบล็อคป้องกันน้ำไหลเข้าได้  บางบล็อคป้องกันน้ำไหลออกได้");
			player.sendMessage("ฟองน้ำ และ คบไฟ  กันน้ำเข้า");
			player.sendMessage("แร่เหล็ก หรือเตาไฟ  กันน้ำออก");
			player.sendMessage("แร่เพชร  ป้องกันน้ำไหลออกและไหลเข้า");
			player.sendMessage("ฟักทองมีไฟ  หยุดน้ำที่ไหล");
			player.sendMessage("ดอกไม้ จะทำให้น้ำเป็นลาวา");
			player.sendMessage("ดอกกุหลาบจะทำให้ลาวาเป็นน้ำ");

			player.sendMessage("10*10*10");

			event.setCancelled(true);
		}
	}

	// Chat Event.class
	// BlockBreakEvent

	public boolean is8_10block(int impo) {

		switch (impo) {
		// sponge
		// not allow water in sponge flowing

		case 8:
		case 9:
		case 10:
		case 11:

			return true;
		default:
			return false;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent event) {

		Block block = event.getBlock();

		if (is8_10block(block.getTypeId()) == false) {
			return;
		}

		Block block2 = event.getToBlock();

		switch (block2.getTypeId()) {
		case 27:
		case 28:
		case 66:
		case 157:
		case 50:
		case 55:
		case 69:
		case 75:
		case 76:
		case 77:
		case 93:
		case 94:
		case 132:
		case 143:
		case 147:
		case 148:
		case 149:
		case 150:
		case 151:
			event.setCancelled(true);
			return;
		}

		// dew.printAll("water");
		if (block2.getTypeId() != 0) {
			return;
		}
		// dew.printAll("" + block2.getTypeId());
		boolean near1 = false;
		boolean near2 = false;
		int pow = 5;

		int x = 0;
		int y = 0;
		int z = 0;

		Block block3 = null;

		// craft
		near1 = false;
		near2 = false;
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block.getRelative(x, y, z);
					if (block3.getTypeId() == 91) {
						near1 = true;
						break;

					}

				}
			}
		}

		if (near1 == true) {
			event.setCancelled(true);

		}

		// flower water to lava

		// flower
		near1 = false;
		near2 = false;
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block.getRelative(x, y, z);
					if (block3.getTypeId() == 37) {
						near1 = true;
						break;

					}

				}
			}
		}

		if (near1 == true) {

			if (block.getTypeId() == Material.STATIONARY_WATER.getId()) {
				block.setTypeId(Material.STATIONARY_LAVA.getId());

			}
			if (block.getTypeId() == Material.WATER.getId()) {
				block.setTypeId(Material.LAVA.getId());

			}

		}

		// flower

		// rose lava to water
		near1 = false;
		near2 = false;
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block.getRelative(x, y, z);
					if (block3.getTypeId() == 38) {
						near1 = true;
						break;

					}

				}
			}
		}

		if (near1 == true) {

			if (block.getTypeId() == Material.STATIONARY_LAVA.getId()) {
				block.setTypeId(Material.STATIONARY_WATER.getId());

			}
			if (block.getTypeId() == Material.LAVA.getId()) {
				block.setTypeId(Material.WATER.getId());

			}

		}

		// rose

		near1 = false;
		near2 = false;

		// 19 1
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block.getRelative(x, y, z);
					if (block3.getTypeId() == 50) {
						near1 = true;
						break;

					}
					if (block3.getTypeId() == 19) {
						near1 = true;
						break;
					}

					if (block3.getTypeId() == Material.DIAMOND_ORE.getId()) {
						near1 = true;
						break;
					}

				}
			}
		}

		// 19 2
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block2.getRelative(x, y, z);
					if (block3.getTypeId() == 50) {
						near2 = true;
						break;

					}
					if (block3.getTypeId() == 19) {
						near2 = true;
						break;
					}
					if (block3.getTypeId() == Material.DIAMOND_ORE.getId()) {
						near2 = true;
						break;
					}

				}
			}
		}

		// out of sponge not flow to sponge
		if (near1 == false) {
			if (near2 == true) {
				event.setCancelled(true);
			}
		}

		near1 = false;
		near2 = false;

		// sponge
		// not allow water in sponge flowing

		// 19 1
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block.getRelative(x, y, z);
					if (block3.getTypeId() == Material.IRON_ORE.getId()) {
						near1 = true;
						break;

					}
					if (block3.getTypeId() == Material.DIAMOND_ORE.getId()) {
						near1 = true;
						break;
					}
					if (block3.getTypeId() == 61) {
						near1 = true;
						break;
					}
				}
			}
		}

		// ironore 2
		for (x = -pow; x <= pow; x++) {
			for (y = -pow; y <= pow; y++) {
				for (z = -pow; z <= pow; z++) {
					block3 = block2.getRelative(x, y, z);
					if (block3.getTypeId() == Material.IRON_ORE.getId()) {
						near2 = true;
						break;

					}
					if (block3.getTypeId() == Material.DIAMOND_ORE.getId()) {
						near2 = true;
						break;
					}
					if (block3.getTypeId() == 61) {
						near2 = true;
						break;
					}

				}
			}
		}

		// ironore
		// not allow water in ironore go out

		if (near1 == true) {
			if (near2 == false) {
				event.setCancelled(true);
			}
		}

	}

	public void printA(String abc) {
		for (Player pla : Bukkit.getOnlinePlayers()) {
			pla.sendMessage(abc);
		}
	}

	public void printAll(String abc) {
		printA(abc);
		printC(abc);
	}

	public void printC(String abc) {
		System.out.println(abc);
	}

} // class

