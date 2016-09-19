/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddautoseed;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddflower.dewset;

public class dewddautoseed implements Listener {

	public JavaPlugin	ac	= null;

	dewset				dew	= new dewset();

	@EventHandler
	public void eventja(BlockPlaceEvent event) {

		Player player = event.getPlayer();
		Block block = event.getBlock();

		switch (player.getItemInHand().getTypeId()) {
		case 295:
		case 391:
		case 392:

			dew.soiladdseedrecusive(block, player, player.getItemInHand()
					.getTypeId(), true);

			return;
		}
	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		Action act = e.getAction();

		if (b == null) return;

		if (p.getItemInHand().getTypeId() == 351
				&& p.getItemInHand().getData().getData() == 15
				&& act == Action.RIGHT_CLICK_BLOCK) {
			switch (b.getTypeId()) {
			case 142:
			case 141:
			case 59:
			case 104:
			case 105:
				dew.seedglow(b, p);
			}
		}
	}
} // class
