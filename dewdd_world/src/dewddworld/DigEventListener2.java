/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddworld;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class delay extends Thread {

		@Override
		public void run() {

			try {

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					dprint.r.printC("dewworld sleeping +" + i);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			loadworldfile();
		}
	}

	class loadworldfilec implements Runnable {

		@Override
		public void run() {

			String filex = "ptdew_dewdd_worlds.txt";

			try {

				dprint.r.printAll("ptdeW&DewDD World : Starting Loading " + filex);
				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream(filex);

				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				// Read File Line By Line

				// sthae
				// aosthoeau
				// * save
				String nowmode = "";

				while ((strLine = br.readLine()) != null) {
					String m[] = strLine.split("\\s+");

					dprint.r.printAll("Starting load " + strLine + " world");
					WorldCreator w = new WorldCreator(m[0]);
					// Print the content on the console

					w.seed(randomG.nextLong());

					if (m.length == 1) {
						w.environment();
						w.environment(Environment.NORMAL);
					} else {
						if (m[1].toLowerCase().indexOf("flat") > -1) {
							w.type(WorldType.FLAT);
						} else if (m[1].toLowerCase().indexOf("nether") > -1) {
							w.environment();
							w.environment(Environment.NETHER);
						} else if (m[1].toLowerCase().indexOf("large") > -1) {
							w.type(WorldType.LARGE_BIOMES);
						} else if (m[1].toLowerCase().indexOf("end") > -1) {
							w.environment();
							w.environment(Environment.THE_END);
						} else if (m[1].toLowerCase().indexOf("1_1") > -1) {
							w.type(WorldType.VERSION_1_1);
						} else {
							w.type(WorldType.NORMAL);

						}

					}

					w.createWorld();
					Bukkit.createWorld(w);
					dprint.r.printAll("finished load " + strLine + " world");
				}

				dprint.r.printAll("ptdew&DewDD world : Loaded " + filex);

				in.close();
			} catch (Exception e) {// Catch exception if any
				dprint.r.printAll("Error load world file: " + e.getMessage());
			}
		}
	}

	class runworldc implements Runnable {
		Player player = null;
		String message = "";

		@Override
		public void run() {
			String m[] = message.split("\\s+");
			// ****************************
			if (message.equalsIgnoreCase("dewworld u all") == true) {
				if (player != null) {
					if (player.hasPermission(punloadall) == false) {
						player.sendMessage("you don't have permission for unload all world");
						return;
					}
				}

				dprint.r.printAll("starting unload all world...as possible...");

				for (World www : Bukkit.getWorlds()) {
					/*
					 * if (www.getName().equalsIgnoreCase("world") == true ||
					 * www.getName().equalsIgnoreCase("world_nether") == true ||
					 * www.getName().equalsIgnoreCase("world_the_end") == true)
					 * { dprint.r.printAll("these worlds can't be unload");
					 * continue; }
					 */

					boolean ff = false;
					for (Player pj : www.getPlayers()) {
						dprint.r.printAll(pj.getName());
						ff = true;
					}

					if (ff == true) {
						dprint.r.printAll("can't unload " + www.getName() + " cuz there are some player there...");
						continue;
					}

					Bukkit.unloadWorld(www, true);
				}

				dprint.r.printAll("unloaded all world...as possible...");
				return;
			}

			if (player != null) {
				if (player.isOp() == true) {
					for (World ww : Bukkit.getWorlds()) {
						if (ww.getName().equalsIgnoreCase(message) == true) {
							Location loc = player.getLocation();
							loc.setWorld(Bukkit.getWorld(message));

							player.sendMessage(
									"teleported  from " + player.getWorld().getName() + " to " + ww.getName());
							player.teleport(loc);
							return;
						}
					}
				}
			}

			if (message.startsWith("dewworld ") == true) {
				int j = message.indexOf(" ");

				if (j == -1) {

					player.sendMessage("/dewworld load/unload worldname");
					return;
				}

				int k = message.indexOf(" ", j + 1);
				if (k == -1) {
					player.sendMessage("need world name");
					return;
				}

				String pl = message.substring(j + 1, k);
				String com = message.substring(k + 1);

				dprint.r.printC("l/u = '" + pl + "'");
				dprint.r.printC("com = '" + com + "'");

				if (pl.equalsIgnoreCase("load") == true || pl.equalsIgnoreCase("l") == true) {
					if (player != null) {
						if (player.hasPermission(pload) == false) {
							player.sendMessage("you don't have permission for load world");
							return;
						}
					}

					dprint.r.printAll("running load '" + com + "' world");
					WorldCreator w = new WorldCreator(m[2]);

					if (m.length == 3) {
						w.environment();
						w.environment(Environment.NORMAL);
					} else if (m.length == 4) {
						if (m[3].toLowerCase().indexOf("flat") > -1) {
							w.type(WorldType.FLAT);
						} else if (m[3].toLowerCase().indexOf("nether") > -1) {
							w.environment();
							w.environment(Environment.NETHER);
						} else if (m[3].toLowerCase().indexOf("large") > -1) {
							w.type(WorldType.LARGE_BIOMES);
						} else if (m[3].toLowerCase().indexOf("end") > -1) {
							w.environment();
							w.environment(Environment.THE_END);
						} else if (m[3].toLowerCase().indexOf("1_1") > -1) {
							w.type(WorldType.VERSION_1_1);
						} else {
							w.type(WorldType.NORMAL);

						}

					}

					w.seed(randomG.nextLong());

					w.seed(randomG.nextLong());
					w.createWorld();
					Bukkit.createWorld(w);
					dprint.r.printAll("finished load '" + com + "' world");
				} else if (pl.equalsIgnoreCase("unload") == true || pl.equalsIgnoreCase("u") == true) {

					if (player != null) {
						if (player.hasPermission(punload) == false) {
							player.sendMessage("you don't have permission for unload world");
							return;
						}
					}

					/*
					 * if (com.equalsIgnoreCase("world") == true ||
					 * com.equalsIgnoreCase("world_nether") == true ||
					 * com.equalsIgnoreCase("world_the_end") == true) {
					 * dprint.r.printAll("these worlds can't be unload");
					 * return; }
					 */

					boolean ff = false;
					for (Player pj : Bukkit.getWorld(com).getPlayers()) {
						dprint.r.printAll(pj.getName());
						ff = true;
					}

					if (ff == true) {
						dprint.r.printAll("can't unload " + com + " cuz there are some player there...");
						return;
					}

					dprint.r.printAll("Starting unload '" + com + "' world");
					Bukkit.unloadWorld(com, true);
					dprint.r.printAll("finished unload '" + com + "' world");
				} else {
					dprint.r.printAll("please enter l/u");
				}

				return;
			}

			if (m[0].equalsIgnoreCase("unloadchunk") == true) {
				unloadChunk uc = new unloadChunk();

				return;
			}

			if (m[0].equalsIgnoreCase("loadchunk") == true) {
				dprint.r.printC("ptdew&dewdd: loading all chunk neary you");
				World wx = player.getWorld();

				for (int x = -200; x < 200; x += 16) {

					for (int z = -200; z < 200; z += 16) {

						wx.loadChunk((int) ((x + player.getLocation().getX()) / 16),
								(int) ((z + player.getLocation().getZ()) / 16), true);
						dprint.r.printC("ptdew&dewdd: loaded chunk at (" + (x + player.getLocation().getX()) + ","
								+ (z + player.getLocation().getZ()) + ")");
					}

				}

				return;
			}
			// ****************************8

		}
	}

	class unloadChunk implements Runnable {

		public unloadChunk() {
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 60);
		}

		@Override
		public void run() {
			//dprint.r.printC("ptdew&dewdd: unloading all chunk with no player");

			for (World wx : Bukkit.getWorlds()) {
				boolean worldHasPlayer = false;

				for (Chunk cj : wx.getLoadedChunks()) {
					worldHasPlayer = false;

					for (Player pj : wx.getPlayers()) {
						if (Math.pow((Math.pow(((cj.getX() * 16) - pj.getLocation().getBlockX()), 2))
								+ (Math.pow(((cj.getZ() * 16) - pj.getLocation().getBlockZ()), 2)), 0.5)

						<= 2000) {
							worldHasPlayer = true;
							break;
						}

					}

					if (worldHasPlayer == false) {
						cj.unload(true);
						dprint.r.printC(
								"ptdew&dewdd: unloaded chunk at (" + cj.getX() * 16 + "," + cj.getZ() * 16 + ")" + " world " + cj.getWorld().getName());
					}

				}
			}
		}
	}

	JavaPlugin ac = null;
	Random randomG = new Random();

	String pload = "dewdd.dewworld.load";

	String punload = "dewdd.dewworld.unload";

	String punloadall = "dewdd.dewworld.unloadall";

	public DigEventListener2() {
		delay eee = new delay();
		eee.start();
	}

	@EventHandler(priority = EventPriority.LOW)
	public synchronized void eventja(AsyncPlayerChatEvent event) {

		// dprint.r.printAll("ทดสอบ");

		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd world");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd world") == true) {
			player.sendMessage(
					"ปลั๊กอิน world สามารถ  โหลดโลก ปิดโลกได้ตามใจชอบ   ปลั๊กอินเกิดขึ้นเพราะ เราอยากรวมแมพเก่าๆในเซิฟ skyblock");

			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("pdewworld") == true) {
			player.sendMessage("load  = " + Boolean.toString(player.hasPermission(pload)));
			player.sendMessage("unload = " + Boolean.toString(player.hasPermission(punload)));
			player.sendMessage("unload all = " + Boolean.toString(player.hasPermission(punloadall)));
		}

		runworld(event.getPlayer(), event.getMessage());

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		runworld(event.getPlayer(), event.getMessage().substring(1));

	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR)
			return;

		if (event.getAction() == Action.RIGHT_CLICK_AIR)
			return;

		Block block = event.getClickedBlock();

		if (block.getTypeId() == 63 || block.getTypeId() == 68) {
			Sign sign = (Sign) block.getState();

			if (sign.getLine(0).equalsIgnoreCase("gd") == true) {

				dprint.r.printC("run gd");
				String sto = "";

				for (int cc = 1; cc <= 10; cc++) {
					Block baa = block.getRelative(0, -cc, 0);
					if (baa.getTypeId() != 63 && baa.getTypeId() != 68) {
						break;
					}

					Sign sii = (Sign) baa.getState();
					for (int sloop = 0; sloop <= 3; sloop++) {
						if (sii.getLine(sloop) == null) {
							break;
						} else {
							sto = sto + sii.getLine(sloop);
						}
					}

				}
				if (sto.equalsIgnoreCase("") == false) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), sto);
					dprint.r.printC("sto=" + sto);
					return;
				}

			} // gd

			if (sign.getLine(0).equalsIgnoreCase("gd2") == true) {

				dprint.r.printC("run gd2");
				String sto = "";

				for (int cc = 1; cc <= 10; cc++) {
					Block baa = block.getRelative(0, -cc, 0);
					if (baa.getTypeId() != 63 && baa.getTypeId() != 68) {
						break;
					}

					Sign sii = (Sign) baa.getState();
					for (int sloop = 0; sloop <= 3; sloop++) {
						if (sii.getLine(sloop) == null) {
							break;
						} else {
							sto = sto + sii.getLine(sloop);
						}
					}

				}

				if (sto.equalsIgnoreCase("") == false) {

					Player pdx = null;
					boolean gotnear = false;
					double dist = 0;
					double oldd = 5000;

					for (Player pq : block.getWorld().getPlayers()) {
						gotnear = true;
						dist = pq.getLocation().distance(block.getLocation());
						if (dist < oldd) {
							oldd = dist;
						}
						pdx = pq;
					}

					pdx.sendMessage(pdx.getName());
					Bukkit.getServer().dispatchCommand(pdx, sto);
					dprint.r.printC("sto=" + sto);
					return;
				}

			} // gd2

			if (sign.getLine(0).equalsIgnoreCase("gd3") == true) {

				dprint.r.printC("run gd3");
				String sto = "";

				for (int cc = 1; cc <= 10; cc++) {
					Block baa = block.getRelative(0, -cc, 0);
					if (baa.getTypeId() != 63 && baa.getTypeId() != 68) {
						break;
					}

					Sign sii = (Sign) baa.getState();
					for (int sloop = 0; sloop <= 3; sloop++) {
						if (sii.getLine(sloop) == null) {
							break;
						} else {
							sto = sto + sii.getLine(sloop);
						}
					}

				}

				if (sto.equalsIgnoreCase("") == false) {

					Player pdx = null;
					boolean gotnear = false;
					double dist = 0;
					double oldd = 5000;

					for (Player pq : block.getWorld().getPlayers()) {
						gotnear = true;
						dist = pq.getLocation().distance(block.getLocation());
						if (dist < oldd) {
							oldd = dist;
						}
						pdx = pq;
					}

					pdx.sendMessage(pdx.getName());
					pdx.chat(sto);
					dprint.r.printC("sto=" + sto);
					return;
				}

			} // gd3
		}

	}

	@EventHandler
	public void eventja(PlayerQuitEvent event) {
		unloadChunk uc = new unloadChunk();
	}

	@EventHandler
	public void eventja(PlayerTeleportEvent event) {
		unloadChunk uc = new unloadChunk();
	}

	@EventHandler
	public void eventja(ServerCommandEvent event) {
		runworld(null, event.getCommand());

	}

	@EventHandler
	public void eventja(SignChangeEvent event) {

		Player player = event.getPlayer();

		if (event.getLine(0).equalsIgnoreCase("gd") == true) {
			if (player.getName().equalsIgnoreCase("ptdew") == false
					&& player.getName().equalsIgnoreCase("pondja_kung") == false) {
				event.setLine(0, "<gd>");
				event.setLine(1, "error");
				event.setLine(0, "only admin");
				event.setLine(0, "access deny");
				return;
			}
		}

		if (event.getLine(0).equalsIgnoreCase("gd2") == true) {
			if (player.getName().equalsIgnoreCase("ptdew") == false
					&& player.getName().equalsIgnoreCase("pondja_kung") == false) {
				event.setLine(0, "<gd2>");
				event.setLine(1, "error");
				event.setLine(0, "only admin");
				event.setLine(0, "access deny");
				return;
			}
		}
		if (event.getLine(0).equalsIgnoreCase("gd3") == true) {
			if (player.getName().equalsIgnoreCase("ptdew") == false
					&& player.getName().equalsIgnoreCase("pondja_kung") == false) {
				event.setLine(0, "<gd2>");
				event.setLine(1, "error");
				event.setLine(0, "only admin");
				event.setLine(0, "access deny");
				return;
			}
		}
	}

	public void loadworldfile() {

		loadworldfilec ax = new loadworldfilec();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);
	}

	public void runworld(Player player, String message) {
		runworldc ab = new runworldc();
		ab.player = player;
		ab.message = message;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

} // class
