/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddrandom;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class cleanthischunk implements Runnable {
		Chunk	chunk;

		public void run() {

			World world = Bukkit.getWorld(worldrun);
			if (world == null) {
				return;
			}
			if (world.getName().equalsIgnoreCase(worldrun) == false) {
				return;
			}

			// printA("cleaning Area : " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16)+ " > " + chunkdel_max );
			// printC("cleaning Area : " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16) );
			// printA("cleaning area... " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16)+ " > " + chunkdel_max );

			Block block = null;
			Block block2 = null;

			int id = 0;
			byte data = 0;
			Block block3 = null;
			block3 = chunk.getWorld().getBlockAt(chunk.getX() * 16, 253,
					chunk.getZ() * 16);
			if (block3.getTypeId() == speci) {
				return;
			}

			if (dewddtps.tps.getTPS() <= 18) {
				dprint.r.printC("random clean chuck need to pause cuz tps < 18   = "
						+ dewddtps.tps.getTPS());
				return;
			}

			long starttime = System.currentTimeMillis();
			long endtime = 0;

			dprint.r.printC("random " + chunk.getX() * 16 + "," + chunk.getZ()
					* 16);

			while (t2time <= 2) {
				// printA("cleaning area phase... " + (chunk.getX() *16) + ",?,"
				// + (chunk.getZ() *16) + " (" + t2time + ")");

				while (runy >= 0) { // y

					// printC("cleaning... : " + (chunk.getX() *16) + "," + y +
					// "," + (chunk.getZ() *16)
					// +" > " + chunkdel_max);
					// printA("cleaning... : " + (chunk.getX() *16) + "," + y +
					// "," + (chunk.getZ() *16));

					while (runz <= ((chunk.getZ() * 16) + 15)) {

						while (runx <= ((chunk.getX() * 16) + 15)) {

							block = world.getBlockAt(runx, runy, runz);
							if (block.getTypeId() == 0) {
								runx++;
								continue;
							}
							if (block.getTypeId() == speci) {
								runx++;
								continue;
							}
							// 23 dispenser
							switch (block.getTypeId()) {
							case 52:
							case 54:
							case 23:
							case 61:
							case 62:
							case 84:
							case 146:
							case 158:
								runx++;
								continue;
							}

							if (t2time == 1) { // time 1
								if (block.getType().isBlock() == false) {
									runx++;
									continue;
								}

								if (is8_10block(block.getTypeId()) == true) { // water
									// check up
									block.setTypeId(0);
								} // if be water

								block2 = getranfree(block);
								id = block.getTypeId();
								data = block.getData();

								block.setTypeId(block2.getTypeId());
								block.setData(block2.getData());

								block2.setTypeId(id);
								block2.setData(data);

							} // time 1
							else if (t2time == 2) {
								if (block.getType().isBlock() == true) {
									runx++;
									continue;
								}
								if (is8_10block(block.getTypeId()) == true) { // water
									// check up
									block.setTypeId(0);
								} // if be water

								block2 = getranfree(block);
								id = block.getTypeId();
								data = block.getData();

								block.setTypeId(block2.getTypeId());
								block.setData(block2.getData());

								block2.setTypeId(id);
								block2.setData(data);
							} // time 2

							// random

							endtime = System.currentTimeMillis();
							if (endtime - starttime > 100
									|| dewddtps.tps.getTPS() <= 18) {
								cleanthischunk are = new cleanthischunk();
								are.chunk = runchunk;
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										ac, are, 1000);
								return;
							}

							runx++;

						} // x

						runx = (chunk.getX() * 16);

						runz++;
					} // z
					runz = (chunk.getZ() * 16);

					runy--;

				} // y
				runy = 254;

				t2time++;

			} // time

			// add to new chunk
			block2 = world
					.getBlockAt(chunk.getX() * 16, 253, chunk.getZ() * 16);
			block2.setTypeId(speci);
			runchunk = null;

			dprint.r.printC("random cleaned Area : " + (chunk.getX() * 16)
					+ ",?," + (chunk.getZ() * 16));

		} // main

	} // project
	public JavaPlugin	ac			= null;
	public String		worldrun	= "random";
	public Random		randomG		= new Random();
	int					speci		= 62;

	int					ra			= 1;
	Chunk				runchunk	= null;
	int					runx		= 0;
	int					runy		= 0;
	int					runz		= 0;

	int					t2time		= 0;

	/*
	 * class cleanthischunk extends Thread{ public Chunk chunk = null;
	 * synchronized public void run() {
	 */

	public boolean checkitfree(Block block) {

		for (int i = -ra; i <= ra; i++) {
			for (int i2 = -ra; i2 <= ra; i2++) {
				for (int i3 = -ra; i3 <= ra; i3++) {
					if (i == 0 && i2 == 0 && i3 == 0) {
						continue;
					}

					if (block.getRelative(i, i2, i3).getTypeId() != 0) {
						return false;
					}

				}
			}
		}

		return true;
	}

	public void cleannearchunk(Player player) {
		for (int gx = -17; gx <= 17; gx += 16) {
			for (int gz = -17; gz <= 17; gz += 16) {
				cleanthischunkt(Bukkit.getWorld(worldrun).getChunkAt(
						(int) ((player.getLocation().getX() + gx) / 16),
						(int) ((player.getLocation().getZ() + gz) / 16)));
			}
		}
	}

	public void cleanthischunkt(Chunk chunk) {
		// cleanthischunkt2(chunk); // call normal

		cleanthischunk abc = new cleanthischunk(); // call invert
		if (runchunk != null) {
			return;
		}

		runchunk = chunk;
		abc.chunk = runchunk;
		runx = (chunk.getX() * 16);
		runy = 254;
		runz = (chunk.getZ() * 16);
		t2time = 1;
		ra = randomG.nextInt(3) + 1;

		Bukkit.getServer().getScheduler()
				.scheduleSyncDelayedTask(ac, abc, 1000);

	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd random");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd random") == true) {
			player.sendMessage("dewdd random  for clean world");
			event.setCancelled(true);
		}

		if (event.getPlayer().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}

		cleannearchunk(player);
	}

	@EventHandler
	public void eventja(BlockBreakEvent event) {
		if (event.getBlock().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}

		if (event.getBlock().getTypeId() == speci
				&& event.getBlock().getY() == 253) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}
		Player player = event.getPlayer();
		cleannearchunk(player);
	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}
		Player player = event.getPlayer();
		player.sendMessage("ptdew&dewdd: Welcome to Random World");
		Bukkit.getWorld(worldrun).save();
	}

	@EventHandler
	public void eventja(PlayerMoveEvent event) {
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}
		if (randomG.nextInt(100) > 90) {
			Player player = event.getPlayer();
			cleannearchunk(player);
		}
	}

	public Block getranfree(Block block) {
		Block bm = block;

		int random = 1;
		int count = 0;

		int y = 0;
		int x = 0;
		int z = 0;

		do {
			count++;

			y = randomG.nextInt(random * 2) - random;
			x = randomG.nextInt(random * 2) - random;
			z = randomG.nextInt(random * 2) - random;

			if (y > 250) y = 250;
			if (y < 5) y = 5;

			if (x < -32) x = -32;
			if (x > 32) x = 32;

			if (z < -32) z = -32;
			if (z > 32) z = 32;

			bm = block.getRelative(x, y, z);

			if (count > 100) {
				count = 0;
				random++;
			}

		}
		while (checkitfree(bm) == false || bm.getTypeId() != 0);
		return bm;

	}

	// Chat Event.class
	// BlockBreakEvent

	public boolean is8_10block(int impo) {

		switch (impo) {

		case 8:
		case 9:
		case 10:
		case 11:

			return true;
		default:
			return false;
		}
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

