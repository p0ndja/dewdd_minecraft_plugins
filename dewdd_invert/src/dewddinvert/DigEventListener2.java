/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddinvert;

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
	/*
	 * class cleanthischunk extends Thread{ public Chunk chunk = null;
	 * synchronized public void run() {
	 */
	class cleanthischunk implements Runnable {
		Chunk	chunk;

		public void run() {
			synchronized (cleanthischunk.class) {
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

				Block block3 = null;
				block3 = chunk.getWorld().getBlockAt(chunk.getX() * 16, 253,
						chunk.getZ() * 16);
				if (block3.getTypeId() == 19) {
					return;
				}
				if (dewddtps.tps.getTPS() < 18) {
					dprint.r.printC("ptdew&dewdd: dew invert will pause cuz tps below than 18 try again!"
							+ dewddtps.tps.getTPS());

					return;
				}

				dprint.r.printC("invert " + chunk.getX() * 16 + ","
						+ chunk.getZ() * 16);

				for (int t2time = 1; t2time <= 2; t2time++) {
					// printA("cleaning area phase... " + (chunk.getX() *16) +
					// ",?," + (chunk.getZ() *16) + " (" + t2time + ")");

					for (int y = 127; y <= 254; y++) {

						// printC("cleaning... : " + (chunk.getX() *16) + "," +
						// y + "," + (chunk.getZ() *16)
						// +" > " + chunkdel_max);
						// printA("cleaning... : " + (chunk.getX() *16) + "," +
						// y + "," + (chunk.getZ() *16));

						for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 15); z++) {

							for (int x = ((chunk.getX() * 16)); x <= ((chunk
									.getX() * 16) + 15); x++) {

								block = world.getBlockAt(x, y - 127, z);
								if (block.getTypeId() == 0) {
									continue;
								}

								// 23 dispenser
								switch (block.getTypeId()) {
								case 23:
								case 54:
								case 61:
								case 62:
								case 84:
								case 117:
								case 118:
								case 120:
								case 130:
								case 146:
								case 154:
								case 158:
								case 52:
									continue;
								}

								if (t2time == 1) { // time 1
									if (block.getType().isBlock() == false) {
										continue;
									}

									if (is8_10block(block.getTypeId()) == true) { // water
										// check up
										boolean allwater = true;
										boolean allwater2 = true;

										allwater = true;
										for (int gg = 1; gg <= 5; gg++) {
											if (gg + block.getY() > 253) {
												break;
											}
											block2 = block
													.getRelative(0, gg, 0);
											if (block2.getTypeId() != 0
													&& is8_10block(block2
															.getTypeId()) == false) {
												allwater = false;
												break;
											}

										}

										for (int gg = -1; gg >= -5; gg--) {
											if (gg + block.getY() < 1) {
												break;
											}
											block2 = block
													.getRelative(0, gg, 0);
											if (block2.getTypeId() != 0
													&& is8_10block(block2
															.getTypeId()) == false) {
												allwater2 = false;
												break;
											}

										}

										// if true true == long water
										// so remove

										if (allwater == false
												|| allwater2 == false) {
											block2 = world.getBlockAt(x,
													253 - (y - (126 + 1)), z);
											block2.setTypeId(block.getTypeId());
											block2.setData(block.getData());

											block.setTypeId(0);

											continue;
										}
										else { // long water

											block.setTypeId(0);

										}
									} // if be water

									block2 = world.getBlockAt(x,
											253 - (y - (126 + 1)), z);
									block2.setTypeId(block.getTypeId());
									block2.setData(block.getData());

								} // time 1
								else if (t2time == 2) {
									if (block.getType().isBlock() == true) {
										continue;
									}

									block2 = world.getBlockAt(x,
											253 - (y - (126 + 1)), z);
									block2.setTypeId(block.getTypeId());
									block2.setData(block.getData());
									block.setTypeId(0);
								} // time 2

							} // x
						} // z

						/*
						 * printC("cleaning Area progessing : " + (chunk.getX()
						 * *16) + ",?," + (chunk.getZ() *16) + " > " + (y-127) +
						 * " (" + ( ((y-127)*100)/127 ) + "%)   "+ " (" + t2time
						 * + ")");
						 */

					} // y

				} // time

				// add to new chunk
				block2 = world.getBlockAt(chunk.getX() * 16, 253,
						chunk.getZ() * 16);
				block2.setTypeId(19);

				/*
				 * for (Entity en: chunk.getWorld().getEntities()){ if
				 * (en.getType() == EntityType.DROPPED_ITEM){ en.remove(); } }
				 */

				dprint.r.printC("invert cleaned Area : " + (chunk.getX() * 16)
						+ ",?," + (chunk.getZ() * 16));
				// printA("cleaned Area : " + (chunk.getX() *16) + ",?," +
				// (chunk.getZ() *16)+ " > " + chunkdel_max );
				// printA("Cleaned Area " + (chunk.getX() *16) + ",?," +
				// (chunk.getZ() *16)+ " > " + chunkdel_max );

			} // sync

		} // main

	} // project
	public JavaPlugin	ac				= null;
	public String		worldrun		= "invert";

	public Random		randomGenerator	= new Random();

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

		Bukkit.getServer().getScheduler()
				.scheduleSyncDelayedTask(ac, abc, 5000L);
		/*
		 * cleanthischunk ; = new cleanthischunk(); // call invertworld xx.chunk
		 * = chunk;
		 * 
		 * xx.setPriority(Thread.MIN_PRIORITY); if (Thread.activeCount() > 50) {
		 * try { xx.join(0); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } xx.start();
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

		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd invert");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd invert") == true) {
			player.sendMessage("ปลั๊กอิน invert เป็นปลั๊กอินที่ทำให้แมพทั้งแมพกลับหัว  โดยจะทำงานในโลกที่ชื่อว่า  invert");
			player.sendMessage("ปลั๊กอิน invert พื้นที่ๆกลับหัวแล้ว ด้านบนจะมีฟองน้ำ เป็นสัญลักษณ์บอกว่า chunk นี้ล้างแมพแล้ว ");
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

		if (event.getBlock().getTypeId() == 19
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
		player.sendMessage("ptdew&dewdd: Welcome to Inverted-World-DewDD");
		player.sendMessage("ptdew&dewdd: ยินดีต้อนรับเข้าสู่ โลกกลับหัว ของ DewDD");
		Bukkit.getWorld(worldrun).save();
	}

	// Chat Event.class
	// BlockBreakEvent

	@EventHandler
	public void eventja(PlayerMoveEvent event) {
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase(worldrun) == false) {
			return;
		}
		if (randomGenerator.nextInt(100) > 90) {
			Player player = event.getPlayer();
			cleannearchunk(player);
		}
	}

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

