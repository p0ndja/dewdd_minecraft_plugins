/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddupdown;

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
import org.bukkit.event.player.PlayerMoveEvent;

public class DigEventListener2 implements Listener {

	class cleanthischunk {
		Chunk	chunk;

		public void main() {
			synchronized (cleanthischunk.class) {

				World world = chunk.getWorld();

				if (world.getName().equalsIgnoreCase(worldrun) == true) {
					Block block3 = null;
					block3 = chunk.getWorld().getBlockAt(chunk.getX() * 16,
							253, chunk.getZ() * 16);
					if (block3.getTypeId() == 19) {
						return;
					}

					// printAll("cleaning Area : " + (chunk.getX() *16) + ",?,"
					// + (chunk.getZ() *16));

					int ry = (randomG.nextInt(300) - 50);
					printC("ry = " + ry);
					Block bm = null;

					Block block = null;

					for (int y = 1; y <= 255; y++) {

						for (int x = 0; x <= 15; x++) {

							for (int z = 0; z <= 15; z++) {
								bm = chunk.getWorld().getBlockAt(x, y, z);
								bm.setTypeId(0);
							}
						}
					}

					// save near air list block

					for (int y = 1; y <= 255; y++) {

						// printC("cleaning... : " + (chunk.getX() *16) + "," +
						// y + "," + (chunk.getZ() *16)
						// +" > " + chunkdel_max);
						// printA("cleaning... : " + (chunk.getX() *16) + "," +
						// y + "," + (chunk.getZ() *16));

						for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 16); x++) {

							for (int z = ((chunk.getZ() * 16)); z <= ((chunk
									.getZ() * 16) + 16); z++) {

								block = world.getBlockAt(x, y, z);
								switch (block.getTypeId()) {
								case 52:
								case 54:
								case 23:
								case 61:
								case 62:
								case 84:
								case 146:
								case 158:
								case 19:
									continue;
								}

								if (y + ry > 255) {
									block.setTypeId(0);
									continue;
								}
								if (y + ry < 1) {
									block.setTypeId(0);
									continue;
								}

								bm = chunk.getWorld().getBlockAt(
										x - (chunk.getX() * 16), y + ry,
										z - (chunk.getZ() * 16));
								bm.setTypeId(block.getTypeId());
								bm.setData(block.getData());
								block.setTypeId(0);

							} // if
						}
					}

					for (int y = 1; y <= 255; y++) {

						for (int x = 0; x <= 15; x++) {

							for (int z = 0; z <= 15; z++) {
								bm = chunk.getWorld().getBlockAt(x, y, z);
								block = chunk.getWorld().getBlockAt(
										(chunk.getX() * 16) + x, y,
										(chunk.getZ() * 16) + z);
								block.setTypeId(bm.getTypeId());
								block.setData(bm.getData());
							}
						}
					}
					// add to new chunk

					printC("updown cleaned Area : " + (chunk.getX() * 16)
							+ ",?," + (chunk.getZ() * 16));
					block3 = world.getBlockAt(chunk.getX() * 16, 253,
							chunk.getZ() * 16);
					block3.setTypeId(19);
				}

			} // sync

		} // main

	} // project
	public int		lowest_canbuild	= 130;
	public int		city_area		= 62;

	public String	worldrun		= "updown";
	public int		maxest_canbuild	= 3000;

	public Random	randomG			= new Random();

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
		abc.chunk = chunk;
		abc.main();
		/*
		 * cleanthischunk ; = new cleanthischunk(); // call invertworld
		 * xx.chunk = chunk;
		 * 
		 * xx.setPriority(Thread.MIN_PRIORITY);
		 * if (Thread.activeCount() > 50) {
		 * try {
		 * xx.join(0);
		 * } catch (InterruptedException e) {
		 * // TODO Auto-generated catch block
		 * e.printStackTrace();
		 * }
		 * }
		 * xx.start();
		 */
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

		city_area = 62;
		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd updown");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd updown") == true) {
			player.sendMessage("ปลั๊กอิน dewdd updown ทำให้โลกชื่อ updown มีความสูงต่ำแบบสุ่ม");

			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockBreakEvent event) {
		if (event.getBlock().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}

		if (event.getBlock().getTypeId() == 19
				&& event.getBlock().getY() == 253) {
			event.setCancelled(true);
		}
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

	public boolean iscleanedchunk(int x, int z) {
		// Chunk chunk = Bukkit.getWorld("world").getChunkAt(x, z);
		// printA("chunk random : " + chunk.getX() + ",?," + chunk.getZ());
		// printC("chunk random : " + chunk.getX() + ",?," + chunk.getZ());

		int zx = 5000;
		int zz = 5000;

		boolean foundza = false;

		for (int gx = -2000; gx <= 2000; gx += 16) {
			for (int gz = -2000; gz <= 2000; gz += 16) {
				if (x >= gx && (x <= gx + 15)) {
					if (z >= gz && (z <= gz + 15)) {
						zx = gx;
						zz = gz; // save

						foundza = true;
						break;
					}
				}
			}
			if (foundza == true) {
				break;
			}
		}

		if (foundza == true) {
			Block block = Bukkit.getWorld(worldrun).getBlockAt(zx, 1, zz);
			if (block.getTypeId() == 19) {

				return true;
			}
			else {
				// printAll("not 19 foundza (" + zx + "," + zz + ") = " +
				// block.getTypeId()) ;
			}

		}
		else {
			// printAll("not found foundza (" + x + "," + z + ") ");
		}

		// chunkdel_x < x < 16

		return false;
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

} // class

