/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package stopdamageentity;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	
	public JavaPlugin ac = null;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventja(EntityDamageEvent e) {
		Entity ee = e.getEntity();
		if (ee == null) {
			e.setCancelled(true);

			return;
		} else {
			if (ee instanceof LivingEntity) {
				int search = 5;

				boolean found = false;
				for (int i = -search; i <= search; i++) {
					if (ee.getLocation().getBlock().getRelative(0	, i, 0).getType() == Material.BARRIER) {
						found = true;
						break;
					}
				}

				if (found == true) {
					e.setCancelled(true);

					return;
				}
			}
		}

	}
} // class