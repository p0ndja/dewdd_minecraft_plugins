/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddmonster;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

public class DigEventListener2 implements Listener {

	Random	randomG	= new Random();

	@EventHandler
	public void eventja(CreatureSpawnEvent event) {
		if (event.getEntity() == null) {
			return;
		}
		if (event.getCreatureType() == null) {
			return;
		}

		if ((event.getEntity().getWorld().getTime() > 13000 || event
				.getEntity().getWorld().getTime() < 0) == false) {
			return;
		}

		if (event.getEntity().getType() == EntityType.CREEPER) {
			if (randomG.nextInt(10000) < 40) {
				event.getEntity().getWorld()
						.strikeLightning(event.getEntity().getLocation());
			}
		}

		LivingEntity abc = event.getEntity();
		if (abc == null) {
			return;
		}

		event.getEntity().addPotionEffect(
				PotionEffectType.JUMP.createEffect(randomG.nextInt(3000),
						randomG.nextInt(10)));

		if (abc.getType() == EntityType.SLIME) {
			return;
		}
		if (abc.getType() == EntityType.MAGMA_CUBE) {
			return;
		}

		if (event.getCreatureType() == CreatureType.CHICKEN) {
			return;
		}
		if (event.getCreatureType() == CreatureType.COW) {
			return;
		}
		if (event.getCreatureType() == CreatureType.SHEEP) {
			return;
		}
		if (event.getCreatureType() == CreatureType.MUSHROOM_COW) {
			return;
		}
		if (event.getCreatureType() == CreatureType.PIG) {
			return;
		}
		if (event.getCreatureType() == CreatureType.SQUID) {
			return;
		}

		Location laco = event.getEntity().getLocation();

		int counthigh = 0;
		for (int gg = -1; gg >= -12; gg--) {
			Block ct = event.getEntity().getWorld().getBlockAt(laco)
					.getRelative(0, gg, 00);
			if (ct.getTypeId() == 0) {
				counthigh++;
				if (counthigh > 10) {
					event.setCancelled(true);
				}
			}
			else if (ct.getTypeId() == 9 || ct.getTypeId() == 8
					|| ct.getTypeId() == 10 || ct.getTypeId() == 11) {
				event.setCancelled(true);

			}
			else {
				break;
			}

		}

		laco.setZ(laco.getZ() + 2);
		if (laco.getY() < 1 || laco.getY() > 253) {
			laco.setY((double) 1);
		}
		while (event.getEntity().getWorld().getBlockAt(laco).getTypeId() != 0) {

			if (laco.getY() < 1 || laco.getY() > 253) {
				laco.setY((double) 1);
			}

			laco.setY(laco.getY() + 1);
			laco.setZ(laco.getZ() + 1);

		}

		if (randomG.nextInt(100) < 82) {

			/*
			 * if (Bukkit.getOnlinePlayers().length <= 0) {
			 * event.setCancelled(true);
			 * dew.printC("ptdew&dewdd : stop create monster cuz no player");
			 * return;
			 * }
			 */

			if (laco.getBlock().getLightLevel() <= 8) {

				event.getEntity().getWorld()
						.spawnCreature(laco, event.getCreatureType());

			}
			// dew.randomplaynote(event.getEntity().getLocation());

		}

		if (randomG.nextInt(100) > 80) {
			event.getEntity().addPotionEffect(
					PotionEffectType.INVISIBILITY.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 70) {
			event.getEntity().addPotionEffect(
					PotionEffectType.SPEED.createEffect(randomG.nextInt(3000),
							randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 60) {
			event.getEntity().addPotionEffect(
					PotionEffectType.FIRE_RESISTANCE.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 85) {
			event.getEntity().addPotionEffect(
					PotionEffectType.DAMAGE_RESISTANCE.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 60) {
			event.getEntity().addPotionEffect(
					PotionEffectType.ABSORPTION.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 60) {
			event.getEntity().addPotionEffect(
					PotionEffectType.HEAL.createEffect(randomG.nextInt(3000),
							randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 63) {
			event.getEntity().addPotionEffect(
					PotionEffectType.HEALTH_BOOST.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 92) {
			event.getEntity().addPotionEffect(
					PotionEffectType.INCREASE_DAMAGE.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 73) {
			event.getEntity().addPotionEffect(
					PotionEffectType.REGENERATION.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 73) {
			event.getEntity().addPotionEffect(
					PotionEffectType.SATURATION.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 56) {
			event.getEntity().addPotionEffect(
					PotionEffectType.SLOW.createEffect(randomG.nextInt(3000),
							randomG.nextInt(10)));
		}
		if (randomG.nextInt(100) > 47) {
			event.getEntity().addPotionEffect(
					PotionEffectType.WATER_BREATHING.createEffect(
							randomG.nextInt(3000), randomG.nextInt(10)));
		}

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {

		// event.getPlayer().sendMessage("dewdd monster");
		// event.getPlayer().sendMessage("facebook : https://www.facebook.com/dewddminecraft");
	}

	public void printC(String abc) {
		System.out.println(abc);
	}

}