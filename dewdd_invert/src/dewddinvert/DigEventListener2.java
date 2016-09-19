/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddinvert;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	/*
	 * class cleanthischunk extends Thread{ public Chunk chunk = null;
	 * synchronized public void run() {
	 */
	public long lastClean = 0;
	public long MaxDelay = 10000;

	public static boolean isRunWorld(String worldName) {
		return tr.isrunworld(ac.getName(), worldName);
	}

	public static Block getBlockOfChunk(Chunk chunk) {
		Block block3 = chunk.getWorld().getBlockAt(chunk.getX() * 16, 253, chunk.getZ() * 16);
		return block3;
	}

	public static boolean isCleanedChunk(Chunk chunk) {
		Block block3 = getBlockOfChunk(chunk);

		if (block3.getTypeId() == 19) {
			return true;
		}

		return false;

	}
	
	class SpawnerBlockType {
		public CreatureSpawner abc ;
		public int x;
		public int y ; 
		public int z;
	}

	class CleanThisChunk_c implements Runnable {
		Chunk chunk;

		public void run() {

			World world = chunk.getWorld();
			if (world == null) {
				return;
			}
			if (!isRunWorld(world.getName())) {
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

			if (isCleanedChunk(chunk) == true) {
				return;
			}

			Block block3 = null;
			block3 = getBlockOfChunk(chunk);

			dprint.r.printC("invert " + chunk.getX() * 16 + "," + chunk.getZ() * 16);
			dprint.r.printC("invert " + chunk.getX() * 16 + "," + chunk.getZ() * 16);

			int bid[][][] = new int[16][256][16];
			/*for (int i = 0   ; i < 16 ; i ++ ) {
				bid[i] = new int[256][];
				for (int j = 0 ; j < 256 ; j++ ) {
					bid[i][j] = new int[16];
				}
			}*/
			
			
			byte bdata[][][] = new byte[16][256][16];
			
			
			LinkedList<SpawnerBlockType> spawner  = new LinkedList<SpawnerBlockType>();
			
			
			/*for (int i = 0   ; i < 16 ; i ++ ) {
				bdata[i] = new byte[256][];
				for (int j = 0 ; j < 256 ; j++ ) {
					bdata[i][j] = new byte[16];
				}
			}*/
			for (int y = 0; y < 256; y++) {

				// printC("cleaning... : " + (chunk.getX() *16) + "," +
				// y + "," + (chunk.getZ() *16)
				// +" > " + chunkdel_max);
				// printA("cleaning... : " + (chunk.getX() *16) + "," +
				// y + "," + (chunk.getZ() *16));
				
				int gz = -1;
				int gx = -1 ;

				for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 15); z++) {
					gz ++;
					gx = -1;
					for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 15); x++) {
						gx ++;
					//	dprint.r.printAll(gx + "," + gz);
						

						block = world.getBlockAt(x, y, z);
						
						bid[gx][y][gz]  = block.getTypeId();
						bdata[gx][y][gz] = block.getData();
						
						if (block.getType() == Material.MOB_SPAWNER) {
							SpawnerBlockType sbt = new SpawnerBlockType();
							sbt.x = gx;
							sbt.y = y;
							sbt.z  = gz;
							CreatureSpawner cc = (CreatureSpawner)block.getState();
							sbt.abc = cc;
							
							spawner.add(sbt);
							
						}
						
						block.setType(Material.AIR);
					}
				}

			} // time
			
			int curY = 255;
			for (int y = 0; y < 256; y++) {
				curY = 255 - y;
				// printC("cleaning... : " + (chunk.getX() *16) + "," +
				// y + "," + (chunk.getZ() *16)
				// +" > " + chunkdel_max);
				// printA("cleaning... : " + (chunk.getX() *16) + "," +
				// y + "," + (chunk.getZ() *16));

				int gz = -1;
				int gx = -1;
				for (int z = ((chunk.getZ() * 16)); z <= ((chunk.getZ() * 16) + 15); z++) {
					gz ++ ;
					gx = -1;
					for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 15); x++) {
						gx ++;
						block = world.getBlockAt(x, curY, z);
						/*bid[x][y][z]  = block.getTypeId();
						bdata[x][y][z] = block.getData();*/
						
						block.setTypeIdAndData(bid[gx][y][gz], bdata[gx][y][gz], false);
						
						if (block.getType() == Material.MOB_SPAWNER) {
							// load spawner
							for (int lop = 0 ; lop < spawner.size() ; lop ++ ) {
								SpawnerBlockType tmp = spawner.get(lop);
								if (tmp.x == gx && tmp.y == y && tmp.z == gz) {
									CreatureSpawner cc = (CreatureSpawner)block.getState();
									cc.setSpawnedType(tmp.abc.getSpawnedType());
									cc.update(true);
								}
							}
							
						}
						
					}
				}

			} // time
			
			

			// add to new chunk
			block2 = world.getBlockAt(chunk.getX() * 16, 253, chunk.getZ() * 16);
			block2.setTypeId(19);

			/*
			 * for (Entity en: chunk.getWorld().getEntities()){ if (en.getType()
			 * == EntityType.DROPPED_ITEM){ en.remove(); } }
			 */

			dprint.r.printC("invert cleaned Area : " + (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16));
			// printA("cleaned Area : " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16)+ " > " + chunkdel_max );
			// printA("Cleaned Area " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16)+ " > " + chunkdel_max );

		} // sync

	} // project

	class CleanThisChunk_c2 implements Runnable {
		Chunk chunk;

		public void run() {

			World world = chunk.getWorld();
			if (world == null) {
				return;
			}
			if (!isRunWorld(world.getName())) {
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

			if (isCleanedChunk(chunk) == true) {
				return;
			}

			Block block3 = null;
			block3 = getBlockOfChunk(chunk);

			dprint.r.printC("invert " + chunk.getX() * 16 + "," + chunk.getZ() * 16);
			dprint.r.printC("invert " + chunk.getX() * 16 + "," + chunk.getZ() * 16);

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

						for (int x = ((chunk.getX() * 16)); x <= ((chunk.getX() * 16) + 15); x++) {

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
										block2 = block.getRelative(0, gg, 0);
										if (block2.getTypeId() != 0 && is8_10block(block2.getTypeId()) == false) {
											allwater = false;
											break;
										}

									}

									for (int gg = -1; gg >= -5; gg--) {
										if (gg + block.getY() < 1) {
											break;
										}
										block2 = block.getRelative(0, gg, 0);
										if (block2.getTypeId() != 0 && is8_10block(block2.getTypeId()) == false) {
											allwater2 = false;
											break;
										}

									}

									// if true true == long water
									// so remove

									if (allwater == false || allwater2 == false) {
										block2 = world.getBlockAt(x, 253 - (y - (126 + 1)), z);
										block2.setTypeId(block.getTypeId());
										block2.setData(block.getData());

										block.setTypeId(0);

										continue;
									} else { // long water

										block.setTypeId(0);

									}
								} // if be water

								block2 = world.getBlockAt(x, 253 - (y - (126 + 1)), z);
								block2.setTypeId(block.getTypeId());
								block2.setData(block.getData());

							} // time 1
							else if (t2time == 2) {
								/*
								 * if (block.getType().isBlock() == true) {
								 * continue; }
								 */

								block2 = world.getBlockAt(x, 253 - (y - (126 + 1)), z);
								block2.setTypeId(block.getTypeId());
								block2.setData(block.getData());
								block.setTypeId(0);
							} // time 2

						} // x
					} // z

					/*
					 * printC("cleaning Area progessing : " + (chunk.getX() *16)
					 * + ",?," + (chunk.getZ() *16) + " > " + (y-127) + " (" + (
					 * ((y-127)*100)/127 ) + "%)   "+ " (" + t2time + ")");
					 */

				} // y

			} // time

			// add to new chunk
			block2 = world.getBlockAt(chunk.getX() * 16, 253, chunk.getZ() * 16);
			block2.setTypeId(19);

			/*
			 * for (Entity en: chunk.getWorld().getEntities()){ if (en.getType()
			 * == EntityType.DROPPED_ITEM){ en.remove(); } }
			 */

			dprint.r.printC("invert cleaned Area : " + (chunk.getX() * 16) + ",?," + (chunk.getZ() * 16));
			// printA("cleaned Area : " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16)+ " > " + chunkdel_max );
			// printA("Cleaned Area " + (chunk.getX() *16) + ",?," +
			// (chunk.getZ() *16)+ " > " + chunkdel_max );

		} // sync

	} // project

	public static JavaPlugin ac = null;

	public Random randomGenerator = new Random();

	public void cleannearchunk(Player player) {
		if (!isRunWorld(player.getWorld().getName())) {
			return;
		}

		for (int gx = -17; gx <= 17; gx += 16) {
			for (int gz = -17; gz <= 17; gz += 16) {
				Chunk chunk = player.getWorld().getChunkAt((int) ((player.getLocation().getX() + gx) / 16),
						(int) ((player.getLocation().getZ() + gz) / 16));

				if (isCleanedChunk(chunk) == true) {
					continue;
				}

				if (lastClean == 0) {
					lastClean = System.currentTimeMillis();
				}

				if (System.currentTimeMillis() - lastClean <= MaxDelay) {
					return;
				} else {
					lastClean = System.currentTimeMillis();
				}

				cleanthischunkt(chunk);
			}
		}
	}

	public void cleanthischunkt(Chunk chunk) {
		// cleanthischunkt2(chunk); // call normal

		CleanThisChunk_c abc = new CleanThisChunk_c(); // call invert
		abc.chunk = chunk;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, abc, 1);
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
	public void eventja(AsyncPlayerChatEvent e) {

		Player player = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd invert");
			e.setCancelled(true);
		}
		if (e.getMessage().equalsIgnoreCase("dewdd invert") == true) {
			player.sendMessage(
					"ปลั๊กอิน invert เป็นปลั๊กอินที่ทำให้แมพทั้งแมพกลับหัว  โดยจะทำงานในโลกที่ชื่อว่า  invert");
			player.sendMessage(
					"ปลั๊กอิน invert พื้นที่ๆกลับหัวแล้ว ด้านบนจะมีฟองน้ำ เป็นสัญลักษณ์บอกว่า chunk นี้ล้างแมพแล้ว ");
			e.setCancelled(true);
		}

		if (!isRunWorld(e.getPlayer().getWorld().getName())) {
			return;
		}

		cleannearchunk(player);
	}

	@EventHandler
	public void eventja(BlockFormEvent e) {
		if (!isRunWorld(e.getBlock().getWorld().getName())) {
			return;
		}

		if (isCleanedChunk(e.getBlock().getChunk()) == false) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockFromToEvent e) {
		if (!isRunWorld(e.getBlock().getWorld().getName())) {
			return;
		}

		if (isCleanedChunk(e.getBlock().getChunk()) == false) {
			e.setCancelled(true);

		}

		if (isCleanedChunk(e.getToBlock().getChunk()) == false) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockPhysicsEvent e) {
		if (!isRunWorld(e.getBlock().getWorld().getName())) {
			return;
		}

		if (isCleanedChunk(e.getBlock().getChunk()) == false) {
			e.setCancelled(true);

		}
	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (!isRunWorld(e.getBlock().getWorld().getName())) {
			return;
		}

		if (e.getBlock().getTypeId() == 19 && e.getBlock().getY() == 253) {
			e.setCancelled(true);
			return;
		}
		
		if (isCleanedChunk(e.getPlayer().getLocation().getChunk()) == true) {
			return;
		}

		Player player = e.getPlayer();
		cleannearchunk(player);
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!isRunWorld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		cleannearchunk(player);
	}

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		if (!isRunWorld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		player.sendMessage("ptdew&dewdd: Welcome to Inverted-World-DewDD");
		player.sendMessage("ptdew&dewdd: ยินดีต้อนรับเข้าสู่ โลกกลับหัว ของ DewDD");
		e.getPlayer().getWorld().save();
	}

	// Chat Event.class
	// BlockBreakEvent

	@EventHandler
	public void eventja(PlayerMoveEvent e) {
		if (!isRunWorld(e.getPlayer().getWorld().getName())) {
			return;
		}

		if (randomGenerator.nextInt(100) > 110) {

			if (isCleanedChunk(e.getPlayer().getLocation().getChunk()) == true) {
				return;
			}

			Player player = e.getPlayer();
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
