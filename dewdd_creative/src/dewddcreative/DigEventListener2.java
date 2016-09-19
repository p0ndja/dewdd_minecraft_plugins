/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddcreative;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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

public class DigEventListener2 implements Listener {
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

	class runproc implements Runnable {
		String message = "";
		Player player;

		public void run() {
			String m[] = message.split("\\s+");
			if (m[0].equalsIgnoreCase("/cre") || m[0].equalsIgnoreCase("/creative")) {
				if (m.length == 1) {
					player.sendMessage(dprint.r.color("/cre buy"));
					player.sendMessage(dprint.r.color("/cre freezone"));
					player.sendMessage(dprint.r.color("/cre remove"));

					return;
				} else if (m.length == 2 || m.length == 3) {
					if (m[1].equalsIgnoreCase("freezone") == true) {
						if (m.length == 2) {
							dew.gotofreezone(player);
							return;
						} else if (m.length == 4) {
							if (m[2].equalsIgnoreCase("ph") && !m[3].equalsIgnoreCase("max")) {

								int id = Integer.parseInt(m[2]);
								// goto that player
								// -5000 to 5000

								int count = -1;
								Block b = null;

								for (int x = -5000; x <= 5000; x += 100) {
									for (int z = -5000; z <= 5000; z += 100) {
										b = player.getWorld().getBlockAt(x, 254, z);
										if (b.getTypeId() == 63 || b.getTypeId() == 68) {
											count++;
											Sign sign = (Sign) b.getState();
											dprint.r.printAll(sign.getLine(1) + " at " + b.getX() + "," + b.getZ()
													+ "  id " + count);

											if (count == id) {
												// teleport
												player.teleport(b.getLocation());

												return;
											}
										}

									}

								}

							} else if (m[2].equalsIgnoreCase("ph") && m[3].equalsIgnoreCase("max")) {
								int count = 0;
								Block b = null;

								for (int x = -5000; x <= 5000; x += 100) {
									for (int z = -5000; z <= 5000; z += 100) {
										b = player.getWorld().getBlockAt(x, 254, z);
										if (b.getTypeId() == 63 || b.getTypeId() == 68) {
											count++;

											Sign sign = (Sign) b.getState();
											dprint.r.printAll(sign.getLine(1) + " at " + b.getX() + "," + b.getZ()
													+ "  id " + count);
										}

									}

								}
							} else if (m[2].equalsIgnoreCase("ph") && m[3].equalsIgnoreCase("load")) {
								int count = 0;
								Block b = null;

								for (int x = -5000; x <= 5000; x += 100) {
									for (int z = -5000; z <= 5000; z += 100) {
										b = player.getWorld().getBlockAt(x, 254, z);
										if (b.getTypeId() == 63 || b.getTypeId() == 68) {
											count++;

											Sign sign = (Sign) b.getState();
											dprint.r.printAll(sign.getLine(1) + " at " + b.getX() + "," + b.getZ()
													+ "  id " + count);
											b.getChunk().load();
										}

									}

								}
							}

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

						if (player.getWorld().getBlockAt(zx, 254, zz).getTypeId() == 63) {
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
						player.getWorld().getBlockAt(zx + 1, 254, zz).setTypeId(63);
						Sign sign = (Sign) player.getWorld().getBlockAt(zx + 1, 254, zz).getState();
						sign.setLine(0, "ป้ายที่เหลือติด");
						sign.setLine(1, "ติดชื่อคนที่มี");
						sign.setLine(2, "คนมีสิทธัในเขต");
						sign.setLine(3, "ได้สี่บรรทัด");
						sign.update();
						player.getWorld().getBlockAt(zx + 2, 253, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx + 3, 253, zz).setTypeId(7);

						player.getWorld().getBlockAt(zx, 254, zz).setTypeId(63);
						sign = (Sign) player.getWorld().getBlockAt(zx, 254, zz).getState();
						// check permission
						// zx,254,zz
						dprint.r.printAll("buy area : (" + zx + "," + zz + ")");
						player.getWorld().getBlockAt(zx, 253, zz).setTypeId(7);
						player.getWorld().getBlockAt(zx, 254, zz).setTypeId(63);
						sign = (Sign) player.getWorld().getBlockAt(zx, 254, zz).getState();
						sign.setLine(0, "[dewhome]");
						sign.setLine(1, player.getName());
						sign.update();
						Block block = null;

						Random rnd = new Random();
						int waid = 0;

						do {
							do {

								waid = rnd.nextInt(30000);
							} while (Material.getMaterial(waid) == null);

						} while (Material.getMaterial(waid).isBlock() == false);

						dprint.r.printAll("wallid = " + waid);

						for (int dy = 251; dy >= 1; dy--) {
							block = player.getWorld().getBlockAt(zx, dy, zz);
							block.setTypeId(waid);
						}

						for (int dx = zx; dx <= (zx + 99); dx++) {
							for (int dz = zz; dz <= (zz + 99); dz++) {
								for (int dy = 254; dy >= 1; dy--) {
									block = player.getWorld().getBlockAt(dx, dy, dz);
									if (block.getChunk().isLoaded() == false) {
										block.getChunk().load();
									}

									if (block.getTypeId() != 0 && dy != 253) {
										if (dx == zx || dx == (zx + 99) || dz == zz || dz == (zz + 99) || dx == zz
												|| dx == (zz + 99) || dz == zx || dz == (zx + 99)) {
											if (dy >= 255) {
												continue;
											}
											block.getRelative(0, 1, 0).setTypeId(waid);
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
			player.sendMessage("dounprotecty = " + Boolean.toString(player.hasPermission(api_creative.perdounprotecty)));
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
		if (!tr.isrunworld(ac.getName(), event.getLocation().getWorld().getName())) {
			return;
		}

		event.getEntity().getItemStack().setType(Material.AIR);

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
	public void eventja(EntityExplodeEvent event){
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

		if (isbadarea((int) event.getPlayer().getLocation().getX(), (int) event.getPlayer().getLocation().getY(),
				(int) event.getPlayer().getLocation().getZ()) == true) {
			Location xx = event.getPlayer().getLocation();
			xx.setX(0);
			xx.setY(255);
			xx.setZ(0);
			event.getPlayer().teleport(xx);
			dprint.r.printAll("ptdew&dewdd : ' move " + event.getPlayer().getName() + "' don't go to bad area");

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerRespawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		if (isbadarea((int) event.getRespawnLocation().getX(), (int) event.getRespawnLocation().getY(),
				(int) event.getRespawnLocation().getZ()) == true) {
			Location xx = event.getRespawnLocation();
			xx.setX(0);
			xx.setY(255);
			xx.setZ(0);
			event.getPlayer().teleport(xx);
			dprint.r.printAll("ptdew&dewdd : 'respawn " + event.getPlayer().getName() + "' don't go to bad area");

		}
	}
	
	@EventHandler
	public void eventja(InventoryOpenEvent event) {
		if (tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
				event.getPlayer().sendMessage(dprint.r.color(tr.gettr("Creative Don't allow to open ender chest as this world")));
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void eventja(PlayerTeleportEvent event) {
	/*	if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
*/
		World fromWorld = event.getFrom().getWorld();
		World toWorld = event.getTo().getWorld();
		Player player = event.getPlayer();

		if (tr.isrunworld(ac.getName(), fromWorld.getName())) { // creative to 

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
					
					//player.sendMessage(itm.getType().name());
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

		if (isbadarea((int) event.getTo().getX(), (int) event.getTo().getY(), (int) event.getTo().getZ()) == true) {
			Location xx = event.getTo();
			xx.setX(0);
			xx.setY(255);
			xx.setZ(0);
			event.getPlayer().teleport(xx);
			dprint.r.printAll("ptdew&dewdd : ' teleport " + event.getPlayer().getName() + "' don't go to bad area");
			event.setCancelled(true);
		}
	}

	public boolean isbadarea(int x, int y, int z) {

		if (Math.pow(Math.pow(x - 717, 2) + Math.pow(y - 64, 2) + Math.pow(z - 3, 2), 0.5) < 100) {
			return true;
		} else if (Math.pow(Math.pow(x - (-225), 2) + Math.pow(y - 87, 2) + Math.pow(z - 867, 2), 0.5) < 100) {
			return true;
		}

		return false;
	}

	public void runpro(String message, Player player) throws UserDoesNotExistException, NoLoanPermittedException {
		runproc abc = new runproc();
		abc.player = player;
		abc.message = message;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, abc);
		// dewremove

	}

} // class
