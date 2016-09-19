/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddnotnt;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	public JavaPlugin ac = null;

	// Chat Event.class
	// BlockBreakEvent

	// BlockBreakEvent

	// EntityInteractEvent

	// BlockPlaceEvent

	// BlockDamageEvent

	// EntityExplodeEvent

	// EntityExplodeEvent

	@EventHandler
	public void eventja(EntityChangeBlockEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getLocation().getWorld().getName()) )
			return;

		if (e.getEntity() == null)
			return;

		if (e.getEntity().getType() == EntityType.ENDERMAN) {
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(EntityExplodeEvent event) throws InterruptedException {

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		event.setCancelled(true);

	}

	// PlayerDeathEvent

	// PlayerInteractEvent

	// PlayerRespawnEvent

} // class