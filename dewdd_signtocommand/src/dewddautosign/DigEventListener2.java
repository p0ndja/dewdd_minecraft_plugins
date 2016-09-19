/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddautosign;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {

	JavaPlugin	ac	= null;

	@EventHandler
	public void eventja(PlayerInteractEvent event) {

		Action act;
		act = event.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)) {
			return;
		}

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (block.getTypeId() == 63 || block.getTypeId() == 68) {

			Block b2 = block.getRelative(0, -2, 0);
			if (b2.getTypeId() == 63 || b2.getTypeId() == 68) {
				Sign s2 = (Sign) b2.getState();
				Bukkit.dispatchCommand(player, s2.getLine(0) + s2.getLine(1)
						+ s2.getLine(2) + s2.getLine(3));

			}
		}

	}

} // class

