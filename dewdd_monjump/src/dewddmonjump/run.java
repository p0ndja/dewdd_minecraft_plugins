/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddmonjump;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;

public class run implements Listener {

	@EventHandler
	public void eventja(CreatureSpawnEvent event) {
		Random rnd = new Random();

		if (event.getEntity() == null) {
			return;
		}
		if (event.getCreatureType() == null) {
			return;
		}

		event.getEntity().addPotionEffect(
				PotionEffectType.JUMP.createEffect(rnd.nextInt(3000),
						rnd.nextInt(10)));

	}
} // dig