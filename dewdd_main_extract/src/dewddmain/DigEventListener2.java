/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddmain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import dewddflower.MainLib;
import dewddtran.tr;
import li.Constant_Protect;
import li.IDDataType;
import li.LXRXLZRZType;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class DigEventListener2 implements Listener {

	class chatx implements Runnable {
		public Boolean canc = false;

		private String message = "";

		private Player player = null;

		public chatx(String message, Player player) {
			this.message = message;
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		

		@Override
		public void run() {
			if (!tr.isrunworld(ac.getName(), player.getWorld().getName())) {
				return;
			}

			String[] m = message.split("\\s+");
			m = message.split("\\s+");
		


			if (m[0].equalsIgnoreCase("cleardrop") == true 
					|| m[0].equalsIgnoreCase("cd") == true) {

				for (Entity ent : player.getWorld().getEntities()) {

					if (ent.getType() == org.bukkit.entity.EntityType.DROPPED_ITEM) {
						Location lo2 = dew.giftblock.getLocation();

						ent.teleport(lo2);

						/*
						 * lo2.setWorld(Bukkit.getWorld("world")); if
						 * (player.getWorld().getName().equalsIgnoreCase(
						 * "world") == true) {
						 * 
						 * ent.teleport(lo2);
						 * 
						 * } else {
						 * 
						 * ent.remove();
						 * 
						 * }
						 */

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
				player.sendMessage("ptdew&dewdd:ClearDroped");
				canc = true;
				return;

			}

			if (m[0].equalsIgnoreCase("gotohell") == true) {
				// search player
				if (player.hasPermission(pgotohell)) {
					player.sendMessage("ptdew&dewdd : " + tr.gettr("you don't have permission") + pgotohell);
					return;
				}

				if (m.length != 2) {
					player.sendMessage(tr.gettr("gotohell_need_argument_target"));
					return;
				}

				String goth = m[1];

				for (Player gotb : Bukkit.getOnlinePlayers())
					if (gotb.getName().toLowerCase().startsWith(goth) == true) {
						Location lo1 = gotb.getLocation();
						Location lo2 = lo1;
						lo2.setWorld(Bukkit.getWorld("world_nether"));
						dew.gotohell(gotb, lo1, lo2);
						dprint.r.printC("gotohell : " + gotb.getName());
						break;
					}
			}


			if (m[0].equalsIgnoreCase("compact")) {
				player.sendMessage("compacting ");

				int nit = 0;
				int nit2 = 0;
				for (ItemStack it : player.getInventory().getContents()) {
					nit++;

					if (it == null) {
						continue;
					}

					/*if (it.getType().getMaxDurability() <= 0) {
						continue;
					}*/

					nit2 = 0;
					for (ItemStack it2 : player.getInventory().getContents()) {
						nit2++;

						if (it2 == null) {
							continue;
						}
						if (it2 == it) {
							continue;
						}

						if (it2.getTypeId() != it.getTypeId()) {
							continue;
						}
						
						if (it2.getData() != it.getData()) {
							continue;
						}

						/*if (it2.getType().getMaxDurability() <= 0) {
							continue;
						}*/

						if (nit2 <= nit) {
							continue;
						}

						it.setAmount(it.getAmount() + 1);
						it2.setTypeId(5);
						it2.setAmount(1);

					}
				}

				player.sendMessage("compacted");

			}

		

			if (message.equalsIgnoreCase("loadmainfile") == true) {
				dprint.r.printAll("loading main file");
				dew.loadmainfile();
				dprint.r.printAll("loaded main file");
			}

			


			if (m[0].equalsIgnoreCase("gift") == true)
				if (m.length == 1) {
					dew.gift(player, player.getItemInHand().getTypeId(), player.getItemInHand().getData().getData());
				} else if (m.length == 2) { // gift 5:1 , gift cobblestone:?
					String[] m2 = m[1].split(":");

					int itemid = 0;
					byte dataid = -29;

					player.sendMessage("m2[0] = " + m2[0] + " search.. = " + dew.getmaterialrealname(m2[0]));

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("what_the_hell_item"));
						} else {
							itemid = Material.getMaterial(Integer.parseInt(m2[0])).getId();
							if (m2.length == 2) {
								dataid = Byte.parseByte(m2[1]);
							}

							player.sendMessage("itemid = " + itemid + ", dataid = " + dataid);
							dew.gift(player, itemid, dataid);
						}
					} else {
						for (Material en : Material.values())
							if (en.name().toLowerCase().indexOf(m2[0].toLowerCase()) > -1) {

								player.sendMessage(dprint.r.color("found material real name = " + en.name()));

								itemid = Material.getMaterial(en.name()).getId();
								if (m2.length == 2) {
									dataid = Byte.parseByte(m2[1]);
								}

								player.sendMessage("itemid = " + itemid + ", dataid = " + dataid);
								dew.gift(player, itemid, dataid);
							}
					}

				}

			// dewinvert

			// dewfillwall

			// dewfly อยากบินได้

			// hi
			

			// d4

			// d4

			String strword1 = message;

			// add player to home
			// dewallow2=ptdew
			// dewallow3=candyman
			// d40123

			// "dewadd="

		
				if (message.equalsIgnoreCase("clear") == true) {
					player.getInventory().clear();
					player.sendMessage("ptdew&dewdd : " + tr.gettr("cleared_your_inventory"));
					return;
				}
				// dewgoxxx


			

			

			// pt dew
			

				// dewpicturemap

				if (m[0].equalsIgnoreCase("setamo")) {
					if (player.hasPermission(psetamo) == false) {
						player.sendMessage(dprint.r.color(tr.gettr("you don't have permission") + psetamo));
						return;
					}
					
					player.getItemInHand().setAmount(Integer.parseInt(m[1]));
					player.sendMessage("set " + Integer.parseInt(m[1]) + " done");
					return;
				}

				// hide

				// getalldrop

				// flyspeed

			

			// *****************************************

		} // sync
	}

	class delay extends Thread {

		@Override
		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out.println("dew main waiting for create dewset sleeping ac +" + i);

				}
				dew = new MainLib();
				dew.loadmainfile();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	class server_c implements Runnable {
		private String message;

		public server_c(String message) {
			this.message = message;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			String m[] = message.split("\\s+");

		    chatx  xxx = new chatx(message, null);
		

		}
	}

	MainLib dew;

	public JavaPlugin ac = null;

	public String pseecommand = "dewdd.main.seecommand";
	public String pgotohell = "dewdd.main.gotohell";
	public String psetamo = "dewdd.main.setamo";

	private Random rnd = new Random();

	
	private boolean toggleBlockPhysics = true;

	// Chat Event.class
	// BlockBreakEvent

	public DigEventListener2() {
		// dew = new dewddflower.dewset();

		delay eee = new delay();
		eee.start();

	}



	// synchronized
	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		event.getMessage();
		if (event.getMessage().equalsIgnoreCase("ac")) {
			dprint.r.printAll(ac == null ? "ac yes" : "ac no");
			dprint.r.printAll(dew == null ? "dew yes" : "dew no");
			dprint.r.printAll(MainLib.ac == null ? "dew ac yes" : "dew ac no");

		}



		chatx ab = new chatx(event.getMessage(), event.getPlayer());

		String m[] = event.getMessage().split(" ");
		// deleterecursive x1 z1 x2 z2 id data
		
		
		if (m[0].equalsIgnoreCase("toggleBlockPhysics")) {
			toggleBlockPhysics = !toggleBlockPhysics;
			dprint.r.printAll("toggleBlockPhysics = " + toggleBlockPhysics);
		}
		
		
		if (m[0].equalsIgnoreCase("deleterecursive")) {

			if (m.length == 1) {
				event.getPlayer().sendMessage("deleterecursive x1 y1 z1 x2 y2 z2 blockToDelete maxChunk search");
			} else if (m.length == 10) {

				if (event.getPlayer().isOp() == true) {

					int x1 = Integer.parseInt(m[1]);
					int y1 = Integer.parseInt(m[2]);
					int z1 = Integer.parseInt(m[3]);

					int x2 = Integer.parseInt(m[4]);
					int y2 = Integer.parseInt(m[5]);
					int z2 = Integer.parseInt(m[6]);

					LXRXLZRZType lll = new LXRXLZRZType(x1, y1, z1, x2, y2, z2);

					ArrayList<IDDataType> item = IDDataType.longArgumentToListIDDataType(m[7]);

					int chunklimit = Integer.parseInt(m[8]);
					int search = Integer.parseInt(m[9]);

					HashMap<String, Location> bd = new HashMap<String, Location>();
					dew.DeleteRecursive_mom(bd, event.getPlayer().getWorld(), 1000, lll, item, chunklimit, search);

				}

			}

		}

		event.setCancelled(ab.canc);

	}

	@EventHandler
	public void eventja(BlockPhysicsEvent event) {
		if (toggleBlockPhysics == false) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void eventja(BlockDamageEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;
		// 4
		try {

			Block block = event.getBlock();
			Player player = event.getPlayer();

			// check host block

			boolean goodc1 = false;
			goodc1 = MainLib.cando_all(block, player, "damage");
			if (goodc1 == false) { // don't have permission

				event.setCancelled(true);
				return;
			} else { // have permission

				if (player.getItemInHand().getType() == Material.FEATHER) {
					if (block.getType() != Material.MOB_SPAWNER)
						block.breakNaturally();
				}

				if (player.getItemInHand() != null)
					if (player.getItemInHand().getItemMeta() != null)
						if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
							String itName[] = player.getItemInHand().getItemMeta().getDisplayName().split(" ");

							// player.sendMessage("itName " + itName[0] + " " +
							// itName[1]);

							if (!player.hasPermission(dew.puseitem55)) {
								player.sendMessage(
										dprint.r.color(tr.gettr("you don't have permission ") + dew.puseitem55));

								return;
							}

							if (itName.length == 3) {
								if (itName[0].equalsIgnoreCase("55")) {

									dew.amountRecursiveCount = 0;

									int ig1 = block.getTypeId();
									byte ig2 = block.getData();

									// player.sendMessage("ptdew&dewdd : 55
									// runing..." + ig1 + ":" + ig2);
									dew.item55delete(block, player, ig1, ig2, Integer.parseInt(itName[1]),
											Integer.parseInt(itName[2]), 20);
									// player.sendMessage("ptdew&dewdd : 55
									// done...");
								}

							}
						}

				if (player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_OFF.getId()
						|| player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_ON.getId())
					if (block.getTypeId() == 54) {
						// auto give item for all player on server
						dew.redtorchchest(block, player);
					}

				if (player.getItemInHand().getType() == Material.TORCH) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					IDDataType tmp1 = new IDDataType(50, (byte) 0);
					item.add(tmp1);

					dew.dewsetLightAround(player, item);
				}

				

			} // have permission

		} catch (Exception e) {
			System.err.println("BlockDamageEvent error: Damage event " + e.getMessage());
		}

	}

	// BlockBreakEvent

	// EntityInteractEvent

	// BlockPlaceEvent

	// BlockDamageEvent

	// BlockPlaceEvent
	@EventHandler
	public void eventja(BlockPlaceEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Block block = event.getBlock();
		Player player = event.getPlayer();
		block.getWorld();

		block.getX();
		block.getY();
		block.getZ();

		boolean goodc1 = false;

		goodc1 = MainLib.cando_all(block, player, "build");

		if (goodc1 == false) {
			event.setCancelled(true);
		} else {

			dew.randomplaynote(player.getLocation());
			// A
			// *************************************

			// rice block Iron = rice

			// rice block Iron = rice
			/*
			 * switch (player.getItemInHand().getTypeId()) { case 295: case 391:
			 * case 392:
			 * 
			 * dew.soiladdseedrecusive(block, player, player.getItemInHand()
			 * .getTypeId(), true);
			 * 
			 * return; }
			 */
			// gold = pumkin

			// block gold = sugar cane

			/*
			 * if (player.getItemInHand().getTypeId() == 46 &&
			 * api_admin.dewddadmin.is2moderator(player.getName()) == false) {
			 * event.setCancelled(true); player.getItemInHand().setTypeId(0);
			 * player.sendMessage("ptdew&dewdd : Can't place TNT");
			 * player.setGameMode(GameMode.SURVIVAL); }
			 */

			// A
			// have permission
		}

		// for
	}

	@EventHandler
	public void eventja(ChunkUnloadEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getWorld().getName()))
			return;

	}

	// EntityExplodeEvent

	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventja(CreatureSpawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity() == null)
			return;
		if (event.getCreatureType() == null)
			return;

		event.getEntity().addPotionEffect(
				PotionEffectType.JUMP.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));

		if (event.getCreatureType() == CreatureType.CHICKEN)
			return;
		if (event.getCreatureType() == CreatureType.COW)
			return;
		if (event.getCreatureType() == CreatureType.SHEEP)
			return;
		if (event.getCreatureType() == CreatureType.MUSHROOM_COW)
			return;
		if (event.getCreatureType() == CreatureType.PIG)
			return;
		if (event.getCreatureType() == CreatureType.SQUID)
			return;
		if (event.getCreatureType() == CreatureType.RABBIT)
			return;

		RSWorld worldid = MainLib.getWorld(event.getLocation().getWorld().getName());
		int getid = MainLib.getProtectid(event.getEntity().getLocation().getBlock(), worldid);
		if (getid > -1) {
			int flagMonFound = MainLib.getplayerinslot(Constant_Protect.flag_monster, getid, worldid);

			if (flagMonFound > -1) {
				event.setCancelled(true);
				return;
			}

		}

		LivingEntity abc = event.getEntity();
		if (abc == null)
			return;

		if (abc.getType() == EntityType.VILLAGER)
			return;
		if (abc.getType() == EntityType.CHICKEN)
			return;
		if (abc.getType() == EntityType.COW)
			return;
		if (abc.getType() == EntityType.SHEEP)
			return;
		if (abc.getType() == EntityType.SQUID)
			return;
		if (abc.getType() == EntityType.SLIME)
			return;
		if (abc.getType() == EntityType.MAGMA_CUBE)
			return;

		if (abc.getType() == EntityType.PIG)
			return;
		if (abc.getType() == EntityType.WITHER)
			return;
		if (abc.getType() == EntityType.ENDER_DRAGON)
			return;
		if (abc.getType() == EntityType.SILVERFISH)
			return;

		Location laco = event.getEntity().getLocation();

		int counthigh = 0;
		for (int gg = -1; gg >= -12; gg--) {
			Block ct = event.getEntity().getWorld().getBlockAt(laco).getRelative(0, gg, 00);
			if (ct.getTypeId() == 0) {
				counthigh++;
				if (counthigh > 10) {
					event.setCancelled(true);
				}
			} /*
				 * else if (ct.getTypeId() == 9 || ct.getTypeId() == 8 ||
				 * ct.getTypeId() == 10 || ct.getTypeId() == 11) {
				 * event.setCancelled(true);
				 * 
				 * }
				 */
			else {
				break;
			}

		}

		laco.setZ(laco.getZ() + 2);
		if (laco.getY() < 1 || laco.getY() > 253) {
			laco.setY(1);
		}
		while (event.getEntity().getWorld().getBlockAt(laco).getTypeId() != 0) {

			if (laco.getY() < 1 || laco.getY() > 253) {
				laco.setY(1);
			}

			laco.setY(laco.getY() + 1);
			laco.setZ(laco.getZ() + 1);

		}

		if (dew.randomG.nextInt(100) < tr.gettrint("main_mon_duplicate_chance"))
			if (laco.getBlock().getLightLevel() <= 8) {
				event.getEntity().getWorld().spawnCreature(laco, event.getCreatureType());
			}

		for (PotionEffectType pet : PotionEffectType.values()) {
			if (pet == null) {
				continue;
			}
			double chance = tr.gettrint("main mon potion " + pet.getName() + "_chance");
			if (dew.randomG.nextInt(100) < chance) {
				event.getEntity().addPotionEffect(pet.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));

			}
		}

	}

	@EventHandler
	public void eventja(EntityChangeBlockEvent event) {

		if (event.getEntity() == null)
			return;

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity().getType() == EntityType.ENDERMAN
				&& MainLib.getProtectid(event.getBlock(), MainLib.getWorld(event.getBlock().getWorld().getName())) > -1) {

			event.setCancelled(true);
			return;
		}

		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player pal = (Player) event.getEntity();

			if (MainLib.cando_all(event.getBlock(), pal, "changeBlock") == false) {
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void eventja(EntityDamageEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName()))
			return;

		if (e.getEntity() instanceof EntityPlayer) {
			Player br = (Player) e.getEntity();
			if (MainLib.cando_all(br.getLocation().getBlock(), br, "EntityDamageEvent") == false) {
				// br.sendMessage("ptdew&dewdd : " +
				// tr.gettr("don't_place_hanging_picture_not_yours"));

				e.setCancelled(true);
			}
		}
	}

	// EntityExplodeEvent

	@EventHandler
	public void eventja(EntityExplodeEvent event) throws InterruptedException {

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		Block block = event.getLocation().getBlock();
		// event.setCancelled(true);

		RSWorld worldid = MainLib.getWorld(block.getWorld().getName());
		int getid = MainLib.getProtectid(block, worldid);

		if (getid == -1) {
			return;
		}

		int hasFlagExplode = MainLib.getplayerinslot(Constant_Protect.flag_explode, getid, worldid);

		if (hasFlagExplode > -1) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(EntityInteractEvent event) {
		if (event.getEntity() == null)
			return;

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player prp = (Player) event.getEntity();
			if (MainLib.cando_all(event.getBlock(), prp, "EntityInteract") == false) {
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void eventja(HangingBreakByEntityEvent event) {

		if (event.getRemover() == null) {
			event.setCancelled(true);
			return;
		}

		if (!tr.isrunworld(ac.getName(), event.getRemover().getWorld().getName()))
			return;

		if (event.getRemover().getType() == EntityType.PLAYER) {
			Player br = (Player) event.getRemover();

			if (MainLib.cando_all(event.getEntity().getLocation().getBlock(), br, "HangingBreakByEntity") == false) {
				// br.sendMessage("ptdew&dewdd : " +
				// tr.gettr("don't_break_hanging_picture_not_yours"));

				event.setCancelled(true);
				return;
			}

		}
	}

	// Chat Event.class
	@EventHandler
	public void eventja(HangingBreakEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		Block b = event.getEntity().getLocation().getBlock();

		RSWorld worldid = MainLib.getWorld(b.getWorld().getName());
		int getid = MainLib.getProtectid(b, worldid);

		if (event.getCause() == RemoveCause.EXPLOSION == true) {
			if (getid > -1) {
				event.setCancelled(true);
				return;
			}
		} else {

			if (getid > -1) {
				// check near player

				double dist = 10000;
				double temp = 10000;
				Player pl = null;

				for (Player pd : event.getEntity().getWorld().getPlayers()) {
					temp = pd.getLocation().distance(event.getEntity().getLocation());
					if (temp < dist) {
						dist = temp;
						pl = pd;
					}
				}

				if (dist < 10000)
					if (MainLib.cando_all(event.getEntity().getLocation().getBlock(), pl, "break") == false) {
						event.setCancelled(true);
						return;
					}

			}

		}

	}

	@EventHandler
	public void eventja(HangingPlaceEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player br = event.getPlayer();
		if (MainLib.cando_all(event.getPlayer().getLocation().getBlock(), br, "HangingPlaceEvent") == false) {
			// br.sendMessage("ptdew&dewdd : " +
			// tr.gettr("don't_place_hanging_picture_not_yours"));

			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(InventoryOpenEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		

	}

	@EventHandler
	public void eventja(InventoryPickupItemEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getItem().getWorld().getName()))
			return;

		// search teleport sign
		int x = 0;
		int y = 0;
		int z = 0;
		Block block2 = null;
		int dx = 3;

		for (x = -dx; x <= dx; x++) {
			for (y = -dx; y <= dx; y++) {
				for (z = -dx; z <= dx; z++) {
					block2 = event.getItem().getLocation().getBlock().getRelative(x, y, z);
					if (block2.getType() == Material.SIGN_POST || block2.getType() == Material.WALL_SIGN) {
						Sign sign = (Sign) block2.getState();
						if (sign.getLine(0).equalsIgnoreCase("dewhopper")) {
							sign.setLine(0, "[dewhopper]");
							sign.update(true);
						}

						if (sign.getLine(0).equalsIgnoreCase("[dewhopper]") == true) {

							int tx = Integer.parseInt(sign.getLine(1));
							int ty = Integer.parseInt(sign.getLine(2));
							int tz = Integer.parseInt(sign.getLine(3));
							Location loc = event.getItem().getLocation();
							loc.setX(tx);
							loc.setY(ty);
							loc.setZ(tz);
							event.getItem().teleport(loc);
							event.setCancelled(true);
							return;
						}
					}

				}
			}
		}

		// fitter item
		for (x = -dx; x <= dx; x++) {
			for (y = -dx; y <= dx; y++) {
				for (z = -dx; z <= dx; z++) {
					block2 = event.getItem().getLocation().getBlock().getRelative(x, y, z);
					if (block2.getType() == Material.SIGN_POST || block2.getType() == Material.WALL_SIGN) {
						Sign sign = (Sign) block2.getState();
						if (sign.getLine(0).equalsIgnoreCase("dewhopperx")) {
							sign.setLine(0, "[dewhopperx]");
							sign.update(true);
						}

						if (sign.getLine(0).equalsIgnoreCase("[dewhopperx]") == true) {

							String tx = (sign.getLine(1)); // ID
							int ty = Byte.parseByte(sign.getLine(2)); // data

							if (event.getItem().getItemStack() == null)
								return;

							if (event.getItem().getItemStack().getType().name().equalsIgnoreCase(tx)) {
								// dprint.r.printA("seem id");
								// dprint.r.printA("data = " +
								// event.getItem().getItemStack().getData().getData());
								if ((ty == -29 || ty == event.getItem().getItemStack().getData().getData()) == false) {
									// dprint.r.printA("pause");
									event.setCancelled(true);
									return;
								}
							} else {
								event.setCancelled(true);
								return;
							}

						}
					}

				}
			}
		}

	}

	@EventHandler
	public void eventja(ItemDespawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;
		
		if (tr.isrunworld("dewddcreative", event.getEntity().getWorld().getName() ))
			return;

		if (event.getEntity().getItemStack().getTypeId() == 344)
			return;

		Location lo2 = event.getLocation();

		if (dew.giftblock == null) {
			dew.loadgiftfile();
		}

		lo2 = dew.giftblock.getLocation();

		ItemStack abx = event.getEntity().getItemStack();
		abx.setAmount(event.getEntity().getItemStack().getAmount());

		// lo2.getChunk().load();
		lo2.getWorld().dropItem(lo2, abx);

		dprint.r.printC("ptdew&dewdd : teleported item '" + event.getEntity().getItemStack().getType().name()
				+ "' amount = '" + event.getEntity().getItemStack().getAmount() + "'" + " from "
				+ event.getLocation().getWorld().getName());

	}

	@EventHandler
	public void eventja(ItemSpawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

	}

	@EventHandler
	public void eventja(PlayerBucketEmptyEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		if (MainLib.cando_all(event.getBlockClicked(), event.getPlayer(), "build") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerBucketFillEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		if (MainLib.cando_all(event.getBlockClicked(), event.getPlayer(), "build") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();

		



		if (event.getMessage().toLowerCase().startsWith("/l ") == false
				&& event.getMessage().toLowerCase().startsWith("/login ") == false
				&& event.getMessage().toLowerCase().startsWith("/authme ") == false
				&& event.getMessage().toLowerCase().startsWith("/changepassword ") == false

		) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.hasPermission(pseecommand)) {
					pl.sendMessage(dprint.r.color(player.getName() + " : " + event.getMessage()));
				}
			}

		}

		chatx ab = new chatx(event.getMessage().substring(1), event.getPlayer());

		event.setCancelled(ab.canc);

		chatz ar = new chatz();
		ar.player = event.getPlayer();
		ar.message = event.getMessage().substring(1);
		ar.start();

		dprint.r.printC(event.getMessage());

	}

	// PlayerDeathEvent
	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventja(PlayerDeathEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		final Player player = event.getEntity().getPlayer();
		player.setAllowFlight(false);
		player.setGameMode(GameMode.SURVIVAL);

		event.getDeathMessage();

		
		player.sendMessage("ptdew&dewdd : Wak!!!!!");

	}

	// PlayerDropItemEvent
	@EventHandler
	public void eventja(PlayerDropItemEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();
		dew.randomplaynote(event.getPlayer().getLocation());

		

	}

	// PlayerDeathEvent

	@EventHandler
	public void eventja(PlayerGameModeChangeEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();
		if (event.getNewGameMode() == GameMode.CREATIVE) {
			boolean xgz = player.hasPermission("essentials.gamemode");

			if ((!xgz)) {
				event.setCancelled(true);
				player.setGameMode(GameMode.SURVIVAL);
				/*
				 * Economy.setMoney(player.getName(),
				 * Economy.getMoney(player.getName()) - 500);
				 */

				dprint.r.printAll("ptdew&dewdd :" + player.getName() + tr.gettr("can_be_creative_mode_not_admin"));

			}
		}
	}

	@EventHandler
	public void eventja(PlayerInteractEntityEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Player br = e.getPlayer();
		if (MainLib.cando_all(e.getPlayer().getLocation().getBlock(), br, "HangingPlaceEvent") == false) {
			// br.sendMessage("ptdew&dewdd : " +
			// tr.gettr("don't_interact_not_your"));

			e.setCancelled(true);
		}

	}

	// PlayerInteractEvent

	@EventHandler
	public void eventja(PlayerInteractEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Action act;
		act = event.getAction();

		if ((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)
			return;

		Player player = event.getPlayer();
		if (player.getInventory().first(50) == -1)
			if (player.getLocation().getBlock().getLightLevel() < 7) {
				player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(dew.randomG.nextInt(5000) + 1000,
						dew.randomG.nextInt(5)), true);
			} else {
				player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			}

		Block block = event.getClickedBlock();
		if (player.getItemInHand().getType() == Material.SAPLING && act == Action.LEFT_CLICK_BLOCK) {

			dew.fw_list(player);

			event.setCancelled(true);
			return;
		}

		if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
			Sign sd = (Sign) block.getState();
			if (sd.getLine(0).toLowerCase().endsWith("[buy]") || sd.getLine(0).toLowerCase().endsWith("[sell]")
					|| sd.getLine(0).toLowerCase().endsWith("[trade]") || sd.getLine(0).toLowerCase().endsWith("[free]")

			) {
				block.breakNaturally();
			}

			if (sd.getLine(0).toLowerCase().endsWith("admin shop")) {
				block.breakNaturally();
			}

		}

		boolean goodc1 = false;
		goodc1 = MainLib.cando_all(block, player, "right");

		if (goodc1 == false) {
			event.setCancelled(true);
		} else {

			if (player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_OFF.getId()
					|| player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_ON.getId())
				if (block.getTypeId() == 54) {
					// auto give item for all player on server
					dew.redtorchchest(block, player);
				}

			if (player.getItemInHand().getType() == Material.WOOD_HOE && act == Action.LEFT_CLICK_BLOCK) {
				// set x1y1z1
				int getid = dew.getfreeselect(player);
				dew.selectx1[getid] = block.getX();
				dew.selecty1[getid] = block.getY();
				dew.selectz1[getid] = block.getZ();
				dew.selectworldname[getid] = block.getWorld().getName();

				int countblock = Math.abs(1 + dew.selectx1[getid] - dew.selectx2[getid])
						* Math.abs(1 + dew.selecty1[getid] - dew.selecty2[getid])
						* Math.abs(1 + dew.selectz1[getid] - dew.selectz2[getid]);

				player.sendMessage("ptdew&dewdd : Block 1 = (" + dew.selectx1[getid] + "," + dew.selecty1[getid] + ","
						+ dew.selectz1[getid] + ") to (" + dew.selectx2[getid] + "," + dew.selecty2[getid] + ","
						+ dew.selectz2[getid] + ") = " + countblock);
				event.setCancelled(true);
				return;
			}

			if (player.getItemInHand().getType() == Material.WOOD_HOE && act == Action.RIGHT_CLICK_BLOCK) {
				// set x1y1z1

				int getid = dew.getfreeselect(player);
				dew.selectx2[getid] = block.getX();
				dew.selecty2[getid] = block.getY();
				dew.selectz2[getid] = block.getZ();
				dew.selectworldname[getid] = block.getWorld().getName();

				int countblock = Math.abs(1 + dew.selectx1[getid] - dew.selectx2[getid])
						* Math.abs(1 + dew.selecty1[getid] - dew.selecty2[getid])
						* Math.abs(1 + dew.selectz1[getid] - dew.selectz2[getid]);

				player.sendMessage("ptdew&dewdd : Block 2 = (" + dew.selectx1[getid] + "," + dew.selecty1[getid] + ","
						+ dew.selectz1[getid] + ") to (" + dew.selectx2[getid] + "," + dew.selecty2[getid] + ","
						+ dew.selectz2[getid] + ") = " + countblock);
				event.setCancelled(true);
			}

			if (player.getItemInHand().getType() == Material.DIAMOND_SWORD // diamondsword
					&& act == Action.RIGHT_CLICK_BLOCK) {
				// set x1y1z1

				int getid = dew.getfreeselect(player);
				dew.selectblock[getid] = block;
				dew.selectworldname[getid] = block.getWorld().getName();

			}

		} // else

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		dew.saveworld();
		final Player player = event.getPlayer();

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, new Runnable() {

			@Override
			public void run() {
				player.sendMessage(tr.gettr("on_main_playerjoin_tell_him_this_word"));
			}
		}, 100);

		

		int idc = dew.getfreeselect(player);

	

		System.gc();

	}

	@EventHandler
	public void eventja(PlayerMoveEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;
		Player player = e.getPlayer();

		/*
		 * if (player.isOp() ) {
		 * 
		 * 
		 * int xx = player.getLocation().getDirection().getBlockX();
		 * 
		 * int yy = player.getLocation().getDirection().getBlockY(); int zz =
		 * player.getLocation().getDirection().getBlockZ();
		 * 
		 * player.getPlayer().sendMessage("velo " + xx + "," + yy+ "," + zz);
		 * 
		 * Block cd = player.getLocation().getBlock().getRelative(xx, yy, zz);
		 * cd.setTypeId(4);
		 * 
		 * }
		 */

		// .getPlayer().sendMessage("velo " + e.getPlayer().getVelocity());

		if (rnd.nextInt(100) > 75) {

			Player p = e.getPlayer();
			RSWorld curworldid = MainLib.getWorld(p.getWorld().getName());
			if (curworldid == null) {
				return;
			}

			ShowCurStandProtect i = showCurStandProtect.get(p.getName());
			if (i == null) {
				i = new ShowCurStandProtect();
				i.player = p;
				i.lastStandProtectID = -1;
				i.worldID = curworldid;

				showCurStandProtect.put(p.getName(), i);

				return;

			} else {

				int curStandID = MainLib.getProtectid(p.getLocation().getBlock(), curworldid);

				if (curStandID == -1) { // no protect
					if (i.lastStandProtectID != -1) { // has protect

						RSData tmprs = i.worldID.rs.get(i.lastStandProtectID);
						p.sendMessage(dprint.r.color(tr.gettr("exit from main protect of ") + tmprs.p[0] + " id "
								+ i.lastStandProtectID + " world " + i.worldID.worldName));

						i.lastStandProtectID = curStandID; // be -1
						i.worldID = curworldid;

						return;
					}

				} else { // cur has protect
							// cur there are protect
					if (curStandID == i.lastStandProtectID && curworldid == i.worldID) {

					} else {
						RSData tmprs = curworldid.rs.get(curStandID);
						if (tmprs.p[0].equalsIgnoreCase(Constant_Protect.flag_sell)) {
							int price = Integer.parseInt(tmprs.p[1]);

							p.sendMessage(dprint.r.color(tr.gettr("this zone sell as price") + price));
							p.sendMessage(dprint.r.color(tr.gettr("if you wanna buy it type") + "/fw buyzone"));

						} else {
							p.sendMessage(dprint.r.color(tr.gettr("enter to main protect of ") + tmprs.p[0] + " id "
									+ curStandID + " world " + curworldid.worldName));

						}

						i.lastStandProtectID = curStandID;
						i.worldID = curworldid;
						return;

					}

				}
			}

		}
	}

	@EventHandler
	public void eventja(PlayerQuitEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		int idc = dew.getfreeselect(event.getPlayer());

		dew.saveworld();
		Player player = event.getPlayer();

		event.setQuitMessage("");
		System.gc();
	}

	@EventHandler
	public void eventja(PlayerTeleportEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		event.getPlayer().addPotionEffect(PotionEffectType.REGENERATION.createEffect(200, 1), false);

		int perhell = dew.randomG.nextInt(6000);
		if (perhell <= 5)
			if (event.getFrom().getWorld().getName().equalsIgnoreCase("world") == true
					&& event.getTo().getWorld().getName().equalsIgnoreCase("world") == true) {

				Location lo1 = event.getTo();
				Location lo2 = lo1;
				lo2.setWorld(Bukkit.getWorld("world_nether"));
				dew.gotohell(event.getPlayer(), lo1, lo2);
			}

	

	}

	public void eventja(ServerCommandEvent e) {
		new server_c(e.getCommand());
	}

	@EventHandler
	public void eventja(SignChangeEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (event.getLine(0).equalsIgnoreCase("[dewbuy]") == true
				|| event.getLine(0).equalsIgnoreCase("[dewhome]") == true) {

			player.sendMessage("checking radius");
			int ra = 5;

			if (event.getLine(1).equalsIgnoreCase("") == true) {
				event.setLine(1, "5");
			} else {

				ra = Integer.parseInt(event.getLine(1));
				if (ra > 50) {
					ra = 50;
				}
				if (ra < 5) {
					ra = 5;
				}
				event.setLine(1, Integer.toString(ra));

			}

			int pi = dew.getfreeselect(player);
			dew.selectx1[pi] = (int) block.getLocation().getX() - (ra + 1);
			dew.selecty1[pi] = (int) block.getLocation().getY() - (ra + 1);
			dew.selectz1[pi] = (int) block.getLocation().getZ() - (ra + 1);

			if (dew.selecty1[pi] < 1) {
				dew.selecty1[pi] = 1;
			}

			dew.selectx2[pi] = (int) block.getLocation().getX() + ra + 1;
			dew.selecty2[pi] = (int) block.getLocation().getY() + ra + 1;
			dew.selectz2[pi] = (int) block.getLocation().getZ() + ra + 1;

			if (dew.selecty2[pi] > 255) {
				dew.selecty2[pi] = 255;
			}

			boolean gx = dew.dewbuy(player);

			if (gx == true) {

				player.sendMessage("dewbuy " + ra + tr.gettr("dewbuy_complete"));
				int c = dew.getwallid();
				ItemStack it = new ItemStack(c);
				it.setAmount(300);
				player.setItemInHand(it);

			} else {
				player.sendMessage(tr.gettr("dewbuy_not_complete_need_money"));
				event.setLine(0, "<dewbuy>");
				event.setLine(1, "no money!");
				event.setLine(2, "need money!");
				event.setLine(3, "@_@");

			}

		}

	}

	// PlayerRespawnEvent

	public void signprotectrail(Block block, Player player) {


	}

} // class