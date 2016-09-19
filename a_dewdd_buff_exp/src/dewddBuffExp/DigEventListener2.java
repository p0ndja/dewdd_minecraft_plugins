/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddBuffExp;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class DigEventListener2 implements Listener {

	public static String	version			= "1.1";
	public static String	permissionBuff	= "dewdd.buffexp.use";
	Random					rnd				= new Random();
	public JavaPlugin		ac				= null;

	@EventHandler
	public void eventja(PlayerExpChangeEvent e) {

		if (e.getPlayer().hasPermission(permissionBuff) == false) {
			return;
		}

		e.getPlayer().addPotionEffect(
				PotionEffectType.SPEED.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);
		e.getPlayer().addPotionEffect(
				PotionEffectType.FIRE_RESISTANCE.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);

		e.getPlayer().addPotionEffect(
				PotionEffectType.INCREASE_DAMAGE.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);
		e.getPlayer().addPotionEffect(
				PotionEffectType.DAMAGE_RESISTANCE.createEffect(
						rnd.nextInt(150), rnd.nextInt(5)), false);

		e.getPlayer().addPotionEffect(
				PotionEffectType.FAST_DIGGING.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);
		e.getPlayer().addPotionEffect(
				PotionEffectType.JUMP.createEffect(rnd.nextInt(100),
						rnd.nextInt(5)), false);

		// e.getPlayer().sendMessage("+=" + money);

	}
}