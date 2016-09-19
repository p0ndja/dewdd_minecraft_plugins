/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddwalkair;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

class Dewminecraft {
	int				countShow		= 0;
	int				countLoop		= 0;

	int				dewnamemax		= 10;
	String			dewname[]		= new String[dewnamemax];
	boolean			dewfly[]		= new boolean[dewnamemax];
	boolean			dewhide[]		= new boolean[dewnamemax];
	public Random	randomG			= new Random();

	Random			randomGenerator	= new Random();
	int				randomInt		= randomGenerator.nextInt(500);

	public void randomplaynote(Location player) {
		if (randomG.nextInt(100) > 10) {
			return;
		}

		for (Player pla : player.getWorld().getPlayers()) {
			pla.playSound(player, Sound.NOTE_PIANO, randomG.nextInt(50),
					randomG.nextFloat() + (float) 1);
		}
	}

}

public class DigEventListener2 implements Listener {
	class walkicethread implements Runnable {
		Player	player;
		Block	block;

		public void run() {
			int d4 = 2;
			try {

				for (Player pp : player.getWorld().getPlayers()) {
					if (pp.getName().equalsIgnoreCase(player.getName()) == true) {
						continue;
					}

					if (pp.getLocation().distance(player.getLocation()) < 10) {

						return;
					}
				}

				int sb = Material.ENDER_STONE.getId();

				int digx = block.getX();
				int digy = block.getY() - 1;
				int digz = block.getZ();

				Block blockcut = block;

				d4 += 2;
				for (int x = digx - d4; x <= digx + d4; x++) {
					for (int y = digy - d4; y <= digy + d4; y++) {
						for (int z = digz - d4; z <= digz + d4; z++) {
							blockcut = block.getWorld().getBlockAt(x, y - 1, z);

							if (blockcut.getTypeId() == sb) {
								/*
								 * if
								 * (PreciousStones.API().isFieldProtectingArea
								 * (FieldFlag.PREVENT_PLACE,
								 * player.getLocation())==true) {
								 * continue;
								 * }
								 */

								blockcut.setTypeId(0);

							}
						}
					}
				}
				d4 -= 2;

				for (int x = digx - d4; x <= digx + d4; x++) {
					for (int y = digy; y <= digy; y++) {
						for (int z = digz - d4; z <= digz + d4; z++) {

							if (player.getItemInHand() == null) {
								return;
							}
							else {
								if (player.getItemInHand().getTypeId() == 288
										|| player.getItemInHand().getTypeId() == 261) {
									blockcut = block.getWorld().getBlockAt(x,
											y, z);
								}
								else if (player.getItemInHand().getTypeId() == 352) {
									blockcut = block.getWorld().getBlockAt(x,
											y - 1, z);
								}
								else {
									return;
								}
							}

							if (blockcut.getTypeId() == 0) {

								if ((x == digx - d4 || x == digx + d4
										|| z == digz - d4 || z == digz + d4) == false) {
									/*
									 * if
									 * (PreciousStones.API().isFieldProtectingArea
									 * (FieldFlag.PREVENT_PLACE,
									 * player.getLocation())==true) {
									 * continue;
									 * }
									 */

									dew.randomplaynote(player.getLocation());
									blockcut.setTypeId(sb);
								}
								else if (blockcut.getTypeId() == sb) {
									if (x == digx - d4 || x == digx + d4
											|| z == digz - d4 || z == digz + d4) {
										if (blockcut.getRelative(0, 1, 0)
												.getTypeId() != 0) {
											continue;

										}
										/*
										 * if (PreciousStones.API().
										 * isFieldProtectingArea
										 * (FieldFlag.PREVENT_PLACE,
										 * player.getLocation())==true) {
										 * continue;
										 * }
										 */

										blockcut.setTypeId(0);
									}

									break;
								}
							}
						}

					}

				}

			}
			catch (Exception e) {
				// Auto-generated catch block
				// e.printStackTrace();
			}
		} // run

	}
	public JavaPlugin		ac	= null;

	private Dewminecraft	dew	= new Dewminecraft();

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("ptdew&dewdd: dewdd walkair");
		player.sendMessage("https://www.facebook.com/dewddminecraft");

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void eventja(PlayerMoveEvent event) {
		try {

			/*
			 * if (PreciousStones.API().canPlace(event.getPlayer(),
			 * event.getPlayer().getLocation()) == false ){
			 * return;
			 * }
			 */

			walkicerun(event.getPlayer().getLocation().getBlock(),
					event.getPlayer());

		}
		catch (Exception e) {
			// Auto-generated catch block
			// e.printStackTrace();
			event.setCancelled(true);
		}
	}

	public void walkicerun(Block block, Player player) {

		walkicethread abc = new walkicethread();
		abc.player = player;
		abc.block = block;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, abc);
	}

} // class

