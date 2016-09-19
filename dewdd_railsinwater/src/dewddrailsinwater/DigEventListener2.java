/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddrailsinwater;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	JavaPlugin	ac	= null;

	@EventHandler
	public void eventja(PlayerJoinEvent event) {

		// event.getPlayer().sendMessage("dewdd rails in water and redstone circuits plugins...");
		// event.getPlayer().sendMessage("facebook : https://www.facebook.com/dewddminecraft");
	}

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

	}

} // class

