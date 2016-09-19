/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddboom;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class DigEventListener2 implements Listener {

	public Random	randomGenerator	= new Random();
	public int		dewboomvalue	= 1;

	// Chat Event.class
	// BlockBreakEvent

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	@EventHandler(ignoreCancelled = true)
	public void eventja(EntityDamageByBlockEvent event) {

	}

	@EventHandler(ignoreCancelled = true)
	public void eventja(EntityExplodeEvent event) {
		if (randomGenerator.nextInt(100) > 10) {
			return;
		}

		event.getEntity()
				.getWorld()
				.createExplosion(event.getLocation(),
						(float) (randomGenerator.nextInt(10) + 1),
						randomGenerator.nextInt(1) == 1);

	}

	@EventHandler(ignoreCancelled = true)
	public void eventja(PlayerDeathEvent event) {
		for (Player ppp : Bukkit.getOnlinePlayers()) {
			ItemStack xxx = null;
			if (ppp.getInventory().getHelmet().getTypeId() == Material.DIAMOND_HELMET
					.getId()) {
				xxx = new ItemStack(Material.DIAMOND_HELMET, 1);
				ppp.getInventory().setHelmet(xxx.getData().toItemStack());
			}

			if (ppp.getInventory().getChestplate().getTypeId() == Material.DIAMOND_CHESTPLATE
					.getId()) {
				xxx = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
				ppp.getInventory().setChestplate(xxx.getData().toItemStack());
			}

			if (ppp.getInventory().getBoots().getTypeId() == Material.DIAMOND_BOOTS
					.getId()) {
				xxx = new ItemStack(Material.DIAMOND_BOOTS, 1);
				ppp.getInventory().setBoots(xxx.getData().toItemStack());
			}

			if (ppp.getInventory().getLeggings().getTypeId() == Material.DIAMOND_LEGGINGS
					.getId()) {
				xxx = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
				ppp.getInventory().setLeggings(xxx.getData().toItemStack());
			}

			// sword
			if (ppp.getInventory().first(Material.DIAMOND_SWORD.getId()) == -1) {
				xxx = new ItemStack(Material.DIAMOND_SWORD, 1);
				ppp.getInventory().addItem(xxx.getData().toItemStack());
			}

			// BOW
			if (ppp.getInventory().first(Material.BOW.getId()) == -1) {
				xxx = new ItemStack(Material.BOW, 1);
				ppp.getInventory().addItem(xxx.getData().toItemStack());
			}

			// Arrow
			if (ppp.getInventory().first(Material.ARROW.getId()) == -1) {
				xxx = new ItemStack(Material.ARROW, 64);
				ppp.getInventory().addItem(xxx.getData().toItemStack());
			}
		}

		for (Entity ent : event.getEntity().getPlayer().getWorld()
				.getEntities()) {
			if (ent.getType() == org.bukkit.entity.EntityType.CREEPER) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.SKELETON) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.ZOMBIE) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.SPIDER) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.CAVE_SPIDER) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.BLAZE) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.BAT) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.SLIME) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.GHAST) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.GIANT) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.PIG_ZOMBIE) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.WITCH) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.MAGMA_CUBE) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.WITHER) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.ENDER_DRAGON) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.SILVERFISH) {
				ent.remove();
			}
			if (ent.getType() == org.bukkit.entity.EntityType.DROPPED_ITEM) {
				ent.remove();
			}

			if (ent.getType() == org.bukkit.entity.EntityType.FIREBALL) {
				ent.remove();
			}

			if (ent.getType() == org.bukkit.entity.EntityType.ARROW) {
				ent.remove();
			}

			if (ent.getType() == org.bukkit.entity.EntityType.SMALL_FIREBALL) {
				ent.remove();
			}

		}
	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		printAll("ptdew&dewdd: dewdd boom plugins by dewdd!");
		printAll("ptdew&dewdd: ยินดีต้อนรับเข้าสู่เซิฟที่ใช้ปลั๊กอิน  จิ๊บหายวายวอดของ ptdew&dewdd");
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true)
	public void eventja(PlayerMoveEvent event) {
		if (randomGenerator.nextInt(200) > dewboomvalue) {
			return;
		}

		// enter armor

		// give sword
		for (Player ppp : Bukkit.getOnlinePlayers()) {

			// heal player
			ppp.addPotionEffect(PotionEffectType.REGENERATION.createEffect(
					3000, 5));

			Block block = ppp.getLocation().getBlock().getRelative(0, 10, 0);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.GHAST);

			block = ppp.getLocation().getBlock().getRelative(0, 3, 10);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.ZOMBIE);

			block = ppp.getLocation().getBlock().getRelative(0, 3, -10);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.SKELETON);

			block = ppp.getLocation().getBlock().getRelative(10, 3, 0);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.CREEPER);

			block = ppp.getLocation().getBlock().getRelative(-10, 3, 0);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.SPIDER);

			block = ppp.getLocation().getBlock().getRelative(10, 3, 10);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.CAVE_SPIDER);

			block = ppp.getLocation().getBlock().getRelative(10, 3, -10);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.MAGMA_CUBE);

			block = ppp.getLocation().getBlock().getRelative(-10, 3, 10);
			block.getWorld().spawnCreature(block.getLocation(),
					EntityType.SLIME);

			// make tnt
			for (int x = -5; x <= 5; x++) {
				for (int z = -5; z <= 5; z++) {
					for (int y = -5; y <= 5; y++) {
						block = block.getRelative(x, y, z);

						boolean fr = true; // search up free
						for (int y2 = 0; y2 <= 5; y2++) {
							if (block.getRelative(0, y2, 0).getTypeId() != 0) {
								fr = false;
								break;
							}
						}

						if (fr == true) {
							block.setTypeId(46);
						}
					}

				}
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

	// EntityExplodeEvent

	// PlayerDeathEvent

	// PlayerDropItemEvent

	// PlayerExpChangeEvent

	// PlayerInteractEvent

	// PlayerMoveEvent

	// PlayerMoveEvent

	// PlayerRespawnEvent

} // class

