/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddcreative;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;
import li.LXRXLZRZType;

public class DigEventListener2 implements Listener {
	LinkedList<Location> allProtect = new LinkedList<Location>();

	class delay extends Thread {

		public void run() {
			while (ac == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			dew = new api_creative();
			dew.ac = ac;
		}
	}

	public void recusiveSearchBlock(Block cur, Block start, LinkedList<Location> list) {
		// add

		Block tmp = null;
		int searchSpace = 500;
		int betweenSpace = 100;

		for (int x = -searchSpace; x <= searchSpace; x += betweenSpace) {

			for (int z = -searchSpace; z <= searchSpace; z += betweenSpace) {
				// tmp = cur.getRelative(x, 0, z);
				tmp = cur.getWorld().getBlockAt(cur.getX() + x, api_creative.signY, cur.getZ() + z);

				// dprint.r.printAll("loca " +
				// tr.locationToString(tmp.getLocation()));

				double xxx = Math.abs(tmp.getX() - start.getX());
				double zzz = Math.abs(tmp.getZ() - start.getZ());

				double dis = (xxx * xxx) + (zzz * zzz);
				dis = Math.pow(dis, 0.5);

				if (tmp.getType() != Material.SIGN_POST) {
					continue;
				}

				// open it

				if (list.contains(tmp.getLocation())) {
					continue;
				}

				list.add(tmp.getLocation());

				// dprint.r.printAll("found block " +
				// tr.locationToString(tmp.getLocation())
				// + " size " + list.size());

				// call recursive

				this.recusiveSearchBlock(tmp, start, list);

			} // chest
		}

	}

	class PlayEffect implements Runnable {

		private Player player = null;

		public PlayEffect(Player player) {
			this.player = player;
		}

		public LinkedList<Location> findDot(Location a, Location b) {

			LinkedList<Location> dot = new LinkedList<Location>();
			dot.add(a);

			double distance = a.distance(b);

			Location c = a.clone();

			/*
			 * dprint.r.printAll("findDot (" + a.getBlockX() + "," +
			 * a.getBlockY() + a.getBlockZ() + ") to (" + b.getBlockX() + "," +
			 * b.getBlockY() + "," + b.getBlockZ() + ") = distance " +
			 * distance);
			 */

			int oldX = c.getBlockX();
			int oldZ = c.getBlockZ();


			while (distance > 0) {

				// x , y , z

				// 0 , -1 , +1

				boolean foundYet = false;

				int y = a.getBlockY();

				for (int x = -1; x <= 1; x++) {

					for (int z = -1; z <= 1; z++) {

						int newX = oldX + x;
						int newZ = oldZ + z;

						Location d = player.getLocation();
						d.setX(newX);
						d.setY(b.getBlockY());
						
						d.setZ(newZ);

						double newDistance = d.distance(b);
						if (newDistance < distance) {
							c.setX(d.getX());
							c.setZ(d.getZ());

							oldX = c.getBlockX();
							oldZ = c.getBlockZ();

							distance = newDistance;

							dot.add(d);
							
							/*  dprint.r.printAll("shoter part " + dot.size() +
							  " (" + c.getBlockX() + "," + c.getBlockY() + ","
							  + c.getBlockZ() + ") = distance " + newDistance);*/
							 

							foundYet = true;
							break;
						}
					}

					if (foundYet == true) {
						break;
					}
				}
			

			} // while

			// dprint.r.printAll("dot size " + dot.size());

			return dot;

		}

		public void run() {

			Location l1 = player.getLocation();

			for (int i = 0; i < 1; i++) {

				Location l2 = allProtect.get(i);

				LinkedList<Location> dots = findDot(l1, l2);

				for (int j = 0; j < dots.size(); j++) {
					Location dotLo = dots.get(j);
					dotLo.setY(player.getLocation().getY() );

					/* dprint.r.printC(j + " = " + dotLo.getBlockX() + "," +
					 dotLo.getBlockY() + "," + dotLo.getBlockZ());*/

					player.getWorld().playEffect(dotLo, Effect.HEART, 100);

				}
			}

		}

	}

	class RunPro_c implements Runnable {
		String message = "";
		Player player;

		public void run() {
			String m[] = message.split("\\s+");
			if (m[0].equalsIgnoreCase("/cre") || m[0].equalsIgnoreCase("/creative")) {
				Block block = player.getLocation().getBlock();

				if (m.length == 1) {
					player.sendMessage(dprint.r.color("/cre buy"));
					player.sendMessage(dprint.r.color("/cre freezone"));
					player.sendMessage(dprint.r.color("/cre remove"));

					return;
				} else if (m.length == 2 || m.length == 3) {
					if (m[1].equalsIgnoreCase("warp") == true) {

						Location lo = allProtect.get(Integer.parseInt(m[2]));
						dprint.r.printAll("" + lo.getBlockX() + "," + lo.getBlockY() + "," + lo.getBlockZ());
						lo.getChunk().load();
						player.teleport(lo);

					} else if (m[1].equalsIgnoreCase("ef") == true) {

						player.getWorld().playEffect(player.getLocation(), Effect.CLOUD, 200);
					}

					else if (m[1].equalsIgnoreCase("ef2") == true) {
						PlayEffect pe = new PlayEffect(player);
						pe.run();

					} else if (m[1].equalsIgnoreCase("search") == true) {
						allProtect.clear();

						LinkedList<Location> list = new LinkedList<Location>();

						Block start = block.getWorld().getBlockAt(0, 0, 0);

						recusiveSearchBlock(start, start, list);

						LXRXLZRZType o = new LXRXLZRZType(0, 0, 0, 0, 0, 0);

						for (int i = 0; i < list.size(); i++) {
							Block tmp = list.get(i).getBlock();

							if (tmp.getX() > o.lx) {
								o.lx = tmp.getX();
							}
							if (tmp.getX() < o.rx) {
								o.rx = tmp.getX();
							}

							if (tmp.getZ() > o.lz) {
								o.lz = tmp.getZ();
							}
							if (tmp.getZ() < o.rz) {
								o.rz = tmp.getZ();
							}

							dprint.r.printAll(i + " = " + tr.locationToString(tmp.getLocation()));
							allProtect.add(tmp.getLocation());
							// dprint.r.printAll(allProtect.size() + " , " +
							// list.size() + " , " + i);

						}

						dprint.r.printAll("left light " + o.lx + "," + o.lz + " to " + o.rx + "," + o.rz);

					} else if (m[1].equalsIgnoreCase("position") == true) {
						LinkedList<Location> list = new LinkedList<Location>();

						Block start = block.getWorld().getBlockAt(0, 0, 0);

						recusiveSearchBlock(start, start, list);

						LXRXLZRZType o = new LXRXLZRZType(0, 0, 0, 0, 0, 0);

						for (int i = 0; i < list.size(); i++) {
							Block tmp = list.get(i).getBlock();

							if (tmp.getX() > o.lx) {
								o.lx = tmp.getX();
							}
							if (tmp.getX() < o.rx) {
								o.rx = tmp.getX();
							}

							if (tmp.getZ() > o.lz) {
								o.lz = tmp.getZ();
							}
							if (tmp.getZ() < o.rz) {
								o.rz = tmp.getZ();
							}

							dprint.r.printAll(i + " = " + tr.locationToString(tmp.getLocation()));
						}

						dprint.r.printAll("left light " + o.lx + "," + o.lz + " to " + o.rx + "," + o.rz);

					} else if (m[1].equalsIgnoreCase("freezone") == true) {
						if (m.length == 2) {
							dew.gotofreezone(player);
							return;
						}
					}

					if (m[1].equalsIgnoreCase("remove") == true) {

						if (player.hasPermission(api_creative.perdewremove) == false) {
							player.sendMessage(
									dprint.r.color(tr.gettr("you don't have permission " + api_creative.perdewremove)));
							return;
						}
						int zx = 5;
						int zz = 5;
						int x = (int) player.getLocation().getX();
						int z = (int) player.getLocation().getZ();
						boolean foundza = false;

						for (int gx = -api_creative.max_b; gx <= api_creative.max_b; gx += 100) {
							for (int gz = -api_creative.max_b; gz <= api_creative.max_b; gz += 100) {
								if (x >= gx && (x <= gx + 99)) {
									if (z >= gz && (z <= gz + 99)) {
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

						if (zx == 5 && zz == 5) {
							dprint.r.printAll("ptdew&dewdd: Creative : " + player.getName() + "  can't remove area  "
									+ -api_creative.max_b + " < x < " + api_creative.max_b);

							return; // not protect
						}

						dprint.r.printAll("ptdew&dewdd: Creative : deleting... " + zx + "," + zz);
						Block bc;

						for (int ix = zx; ix <= zx + 99; ix++) {
							for (int iz = zz; iz <= zz + 99; iz++) {
								for (int iy = 255; iy >= 250; iy--) {
									bc = player.getWorld().getBlockAt(ix, iy, iz);
									bc.setTypeId(0);
								}
							}
						}

						for (int iy = 249; iy >= 0; iy--) {
							for (int ix = zx; ix <= zx + 99; ix++) {
								for (int iz = zz; iz <= zz + 99; iz++) {

									bc = player.getWorld().getBlockAt(ix, iy, iz);
									if (bc.getTypeId() == 20) {
										bc.setTypeId(0);
										continue;
									}
								}
							}
						}

						dprint.r.printAll("ptdew&dewdd : creative : removeed protect area ");
						return;

					}

					// dewbuy

					if (m[1].equalsIgnoreCase("buy") == true) {

						// **********************

						int zx = 5;
						int zz = 5;
						int x = (int) player.getLocation().getX();
						int z = (int) player.getLocation().getZ();
						boolean foundza = false;

						for (int gx = -api_creative.max_b; gx <= api_creative.max_b; gx += 100) {
							for (int gz = -api_creative.max_b; gz <= api_creative.max_b; gz += 100) {
								if (x >= gx && (x <= gx + 99)) {
									if (z >= gz && (z <= gz + 99)) {
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

						if (zx == 5 && zz == 5) {
							dprint.r.printAll(
									"ptdew&dewdd: " + tr.gettr("Creative can't buy zone too for from this position ")
											+ (-api_creative.max_b) + " < x < " + (api_creative.max_b));
							return; // not protect
						}

						if (player.getWorld().getBlockAt(zx, api_creative.signY, zz).getTypeId() == 63) {
							player.sendMessage("ptdew&dewdd: "
									+ tr.gettr("Creative can't buy cuz that is somebody home sorry...  "));
							return;
						}

						// check money
						double creativePrice = tr.gettrint("CONFIG Creative buy Zone Price");

						try {
							if (Economy.getMoney(player.getName()) < creativePrice) {
								player.sendMessage(dprint.r.color(
										tr.gettr("creative you don't have enough money to buy zone") + creativePrice));
								return;
							} else {
								Economy.subtract(player.getName(), creativePrice);

							}

						} catch (UserDoesNotExistException | NoLoanPermittedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return;
						}

						player.getWorld().getBlockAt(zx, 252, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx, 253, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx + 1, 253, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx + 1, api_creative.signY, zz).setTypeId(63);
						Sign sign = (Sign) player.getWorld().getBlockAt(zx + 1, api_creative.signY, zz).getState();
						sign.setLine(0, "ป้ายที่เหลือติด");
						sign.setLine(1, "ติดชื่อคนที่มี");
						sign.setLine(2, "คนมีสิทธัในเขต");
						sign.setLine(3, "ได้สี่บรรทัด");
						sign.update();
						player.getWorld().getBlockAt(zx + 2, 253, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx + 3, 253, zz).setTypeId(7);

						player.getWorld().getBlockAt(zx, api_creative.signY, zz).setTypeId(63);
						sign = (Sign) player.getWorld().getBlockAt(zx, api_creative.signY, zz).getState();
						// check permission
						// zx,api_creative.signY,zz
						dprint.r.printAll("buy area : (" + zx + "," + zz + ")");
						player.getWorld().getBlockAt(zx, 253, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx, api_creative.signY, zz).setTypeId(63);
						sign = (Sign) player.getWorld().getBlockAt(zx, api_creative.signY, zz).getState();
						sign.setLine(0, "[dewhome]");
						sign.setLine(1, player.getName());
						sign.update();
						Block b2 = null;

						Random rnd = new Random();
						int waid = 0;

						do {
							do {

								waid = rnd.nextInt(30000);
							} while (Material.getMaterial(waid) == null);

						} while (Material.getMaterial(waid).isBlock() == false);

						dprint.r.printAll("wallid = " + waid);

						for (int dy = 251; dy >= 1; dy--) {
							b2 = player.getWorld().getBlockAt(zx, dy, zz);
							b2.setTypeId(waid);
						}

						for (int dx = zx; dx <= (zx + 99); dx++) {
							for (int dz = zz; dz <= (zz + 99); dz++) {
								for (int dy = api_creative.signY; dy >= 1; dy--) {
									b2 = player.getWorld().getBlockAt(dx, dy, dz);
									if (b2.getChunk().isLoaded() == false) {
										b2.getChunk().load();
									}

									if (b2.getTypeId() != 0 && dy != 253 || dy == 0) {
										if (dx == zx || dx == (zx + 99) || dz == zz || dz == (zz + 99) || dx == zz
												|| dx == (zz + 99) || dz == zx || dz == (zx + 99)) {
											if (dy >= 255) {
												continue;
											}
											b2.getRelative(0, 1, 0).setTypeId(waid);
											break;
										}
									}

								}
							}
						}

						player.sendMessage("ptdew&dewdd: " + tr.gettr("Creative dewbuy Comepleted"));
						dprint.r.printAll(
								"ptdew&dewdd: " + tr.gettr("Creative dew buy completed Please glad to new friend ")
										+ player.getName() + tr.gettr("Creative dewbuy completed his home at ") + "  ("
										+ zx + "," + zz + ") at '" + player.getWorld().getName() + "'");

						return;
					}
				}
			} // cre

		} // run

	}

	public JavaPlugin ac = null;

	public api_creative dew = null;

	public DigEventListener2() {

		delay eee = new delay();
		eee.start();
	}

	public int distance2d(int x1, int z1, int x2, int z2) {

		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) throws UserDoesNotExistException, NoLoanPermittedException {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = event.getPlayer();

		runpro("/" + event.getMessage(), player);

		if (event.getMessage().equalsIgnoreCase("pcreative") == true) {
			player.sendMessage("changehost = " + Boolean.toString(player.hasPermission(api_creative.perchangehost)));
			player.sendMessage("overide = " + Boolean.toString(player.hasPermission(api_creative.peroveride)));
			player.sendMessage("doprotecty = " + Boolean.toString(player.hasPermission(api_creative.perdoprotecty)));
			player.sendMessage(
					"dounprotecty = " + Boolean.toString(player.hasPermission(api_creative.perdounprotecty)));
			player.sendMessage("dewremove = " + Boolean.toString(player.hasPermission(api_creative.perdewremove)));

			return;
		}

		if (event.getMessage().equalsIgnoreCase("?world") == true) {
			player.sendMessage("world = " + player.getWorld().getName());
			return;
		}

	}

	@EventHandler
	public void eventja(ItemDespawnEvent event) {
		/*
		 * if (!tr.isrunworld(ac.getName(),
		 * event.getLocation().getWorld().getName())) { return; }
		 * 
		 * event.getEntity().getItemStack().setType(Material.AIR);
		 */
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) throws UserDoesNotExistException, NoLoanPermittedException {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = event.getPlayer();
		runpro(event.getMessage(), player);

	}

	@EventHandler
	public void eventja(EntityExplodeEvent event) {
		if (tr.isrunworld(ac.getName(), event.getLocation().getWorld().getName())) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(BlockExplodeEvent event) {
		if (tr.isrunworld(ac.getName(), event.getBlock().getWorld().getName())) {
			event.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(PlayerMoveEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		/*
		 * if (isbadarea((int) event.getPlayer().getLocation().getX(), (int)
		 * event.getPlayer().getLocation().getY(), (int)
		 * event.getPlayer().getLocation().getZ()) == true) { Location xx =
		 * event.getPlayer().getLocation(); xx.setX(0); xx.setY(255);
		 * xx.setZ(0); event.getPlayer().teleport(xx); dprint.r.printAll(
		 * "ptdew&dewdd : ' move " + event.getPlayer().getName() +
		 * "' don't go to bad area");
		 * 
		 * event.setCancelled(true); }
		 */
	}

	@EventHandler
	public void eventja(PlayerBucketEmptyEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Block block = e.getBlockClicked();
		Player player = e.getPlayer();

		boolean cando = api_creative.cando(block, player);
		if (cando == false) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(PlayerBucketFillEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Block block = e.getBlockClicked();
		Player player = e.getPlayer();

		boolean cando = api_creative.cando(block, player);
		if (cando == false) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(PlayerRespawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		/*
		 * if (isbadarea((int) event.getRespawnLocation().getX(), (int)
		 * event.getRespawnLocation().getY(), (int)
		 * event.getRespawnLocation().getZ()) == true) { Location xx =
		 * event.getRespawnLocation(); xx.setX(0); xx.setY(255); xx.setZ(0);
		 * event.getPlayer().teleport(xx); dprint.r.printAll(
		 * "ptdew&dewdd : 'respawn " + event.getPlayer().getName() +
		 * "' don't go to bad area");
		 * 
		 * }
		 */
	}

	@EventHandler
	public void eventja(InventoryOpenEvent event) {
		/*
		 * if (tr.isrunworld(ac.getName(),
		 * event.getPlayer().getWorld().getName())) { if
		 * (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
		 * event.getPlayer().sendMessage( dprint.r.color(tr.gettr(
		 * "Creative Don't allow to open ender chest as this world")));
		 * event.setCancelled(true); } }
		 */
	}

	@EventHandler
	public void eventja(PlayerTeleportEvent event) {
		/*
		 * if (!tr.isrunworld(ac.getName(),
		 * event.getPlayer().getWorld().getName())) { return; }
		 */
		World fromWorld = event.getFrom().getWorld();
		World toWorld = event.getTo().getWorld();
		Player player = event.getPlayer();

		if (tr.isrunworld(ac.getName(), fromWorld.getName())) { // creative
																// to

			if (tr.isrunworld(ac.getName(), toWorld.getName()) == false) { // survival

				player.getInventory().clear();
				player.setGameMode(GameMode.SURVIVAL);
				for (ItemStack itm : event.getPlayer().getInventory().getArmorContents()) {
					itm.setTypeId(5);
					itm.setAmount(1);
				}

			}
		} else {
			if (tr.isrunworld(ac.getName(), toWorld.getName()) == true) {

				for (ItemStack itm : player.getInventory().getContents()) {
					if (itm == null) {
						continue;
					}

					// player.sendMessage(itm.getType().name());
					if (itm.getType() != Material.AIR) {
						player.sendMessage(
								dprint.r.color(tr.gettr("creative remove all item before warp to creative world")));
						event.setCancelled(true);

						return;
					}
				}
				for (ItemStack itm : player.getInventory().getArmorContents()) {
					if (itm == null) {
						continue;
					}
					if (itm.getType() != Material.AIR) {
						player.sendMessage(
								dprint.r.color(tr.gettr("creative remove all item before warp to creative world")));
						event.setCancelled(true);

						return;
					}
				}

				player.setGameMode(GameMode.CREATIVE);

			}
		}

		/*
		 * if (isbadarea((int) event.getTo().getX(), (int) event.getTo().getY(),
		 * (int) event.getTo().getZ()) == true) { Location xx = event.getTo();
		 * xx.setX(0); xx.setY(255); xx.setZ(0); event.getPlayer().teleport(xx);
		 * dprint.r.printAll( "ptdew&dewdd : ' teleport " +
		 * event.getPlayer().getName() + "' don't go to bad area");
		 * event.setCancelled(true); }
		 */
	}

	/*
	 * public boolean isbadarea(int x, int y, int z) {
	 * 
	 * if (Math.pow(Math.pow(x - 717, 2) + Math.pow(y - 64, 2) + Math.pow(z - 3,
	 * 2), 0.5) < 100) { return true; } else if (Math.pow(Math.pow(x - (-225),
	 * 2) + Math.pow(y - 87, 2) + Math.pow(z - 867, 2), 0.5) < 100) { return
	 * true; }
	 * 
	 * return false; }
	 */

	public void runpro(String message, Player player) throws UserDoesNotExistException, NoLoanPermittedException {
		RunPro_c abc = new RunPro_c();
		abc.player = player;
		abc.message = message;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, abc);
		// dewremove

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Block block = e.getBlock();
		Player player = e.getPlayer();

		boolean cando = api_creative.cando(block, player);
		if (cando == false) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Block block = e.getBlock();
		Player player = e.getPlayer();

		boolean cando = api_creative.cando(block, player);
		if (cando == false) {
			e.setCancelled(true);
			return;
		}
	}
} // class
