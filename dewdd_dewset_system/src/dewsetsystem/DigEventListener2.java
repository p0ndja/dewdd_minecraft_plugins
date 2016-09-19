/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewsetsystem;

import java.util.ArrayList;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldedit.BlockVector;

import dewddflower.dewset;
import dewddflower.dewset_interface;
import dewddtran.tr;
import li.IDDataType;

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
		

			if (message.equalsIgnoreCase("pmain") == true) {

				player.sendMessage(dprint.r
						.color(dewset_interface.pmaindelete + " " + Boolean.toString(player.hasPermission(dewset_interface.pmaindelete))));
				player.sendMessage(dprint.r
						.color(dewset_interface.pmaininfinite + " " + Boolean.toString(player.hasPermission(dewset_interface.pmaininfinite))));
					player.sendMessage(dprint.r.color(dewset_interface.pmainalsocopyinventoryblockwhenyouusedewset + " "
						+ Boolean.toString(player.hasPermission(dewset_interface.pmainalsocopyinventoryblockwhenyouusedewset))));

				return;

			}

			// ***********************

			int idc = dew.getfreeselect(player);

			

			if (message.equalsIgnoreCase("dewextend") == true || message.equalsIgnoreCase("de") == true
					|| message.equalsIgnoreCase("extend") == true ) {
				dew.dewextend(player);
				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("pos1") == true || message.equalsIgnoreCase("dewpos1") == true
					) {
				int getid = dew.getfreeselect(player);
				dew.selectx1[getid] = player.getLocation().getBlockX();
				dew.selecty1[getid] = player.getLocation().getBlockY();
				dew.selectz1[getid] = player.getLocation().getBlockZ();
				dew.showpos(player , idc);
				canc = true;
				return;
			}
			
			if (message.equalsIgnoreCase("pos2") == true || message.equalsIgnoreCase("dewpos2") == true
					) {
				int getid = dew.getfreeselect(player);
				dew.selectx2[getid] = player.getLocation().getBlockX();
				dew.selecty2[getid] = player.getLocation().getBlockY();
				dew.selectz2[getid] = player.getLocation().getBlockZ();
				dew.showpos(player , idc);
				canc = true;
				return;
			}

			
			
			if (m[0].equalsIgnoreCase("dewsety") == true || m[0].equalsIgnoreCase("sety") == true)
				if (m.length != 3) {
					player.sendMessage(tr.gettr("not enought argument please type" + " /sety y1 y2"));
					return;
				}
				else if (m.length == 3) {
					int tmpy1 = Integer.parseInt(m[1]);
					int tmpy2 = Integer.parseInt(m[2]);
					dew.selecty1[idc] = tmpy1;
					dew.selecty2[idc] = tmpy2;
					
					dew.showpos(player , idc);
				}
			
			if (m[0].equalsIgnoreCase("dewsetpos") == true || m[0].equalsIgnoreCase("setpos") == true)
				if (m.length != 7) {
					player.sendMessage(tr.gettr("not enought argument please type" + " /setpos x1 y1 z1 x2 y2 z2"));
					return;
				}
				else if (m.length == 7) {
					int tmpx1 = Integer.parseInt(m[1]);
					int tmpy1 = Integer.parseInt(m[2]);
					int tmpz1 = Integer.parseInt(m[3]);
					
					int tmpx2 = Integer.parseInt(m[4]);
					int tmpy2 = Integer.parseInt(m[5]);
					int tmpz2 = Integer.parseInt(m[6]);
					
					dew.selectx1[idc] = tmpx1;
					dew.selectx2[idc] = tmpx2;
					
					dew.selecty1[idc] = tmpy1;
					dew.selecty2[idc] = tmpy2;
					
					dew.selectz1[idc] = tmpz1;
					dew.selectz2[idc] = tmpz2;
					
					dew.showpos(player,idc);
				}
			
			

			if (m[0].equalsIgnoreCase("dewselectcube") == true || m[0].equalsIgnoreCase("selectcube") == true)
				if (m.length == 1) {

					player.sendMessage(tr.gettr("item_on_hand_is_radius") + player.getItemInHand().getAmount());
					dew.dewselectcube(player, player.getItemInHand().getAmount());
					dew.showpos(player,idc);
					canc = true;
					return;

				} else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage(tr.gettr("only_number_please"));
						return;
					}

					player.sendMessage(tr.gettr("argument_2_is_radius") + m[1]);
					dew.dewselectcube(player, Integer.parseInt(m[1]));
					dew.showpos(player ,idc);
					canc = true;
					return;
				}

			

		} // sync
	}

	class chatz extends Thread {
		String message = "";
		Player player = null;

		@Override
		public void run() {

			if (!tr.isrunworld(ac.getName(), player.getWorld().getName())) {
				return;
			}

			dew.runtime = (int) tr.gettrint("dewset runtime as milisecond");

			dew.sleeptime = (int) tr.gettrint("dewset sleeptime as tick");
			int value = ((int)tr.gettrint("CONFIG dewset only work in protected zone set to 1 for enable")) ;
			dew.CONFIG_NEED_PROTECT = value  == 1 ? true : false;
			//player.sendMessage("need protect " + dew.CONFIG_NEED_PROTECT);
			
			String[] m = message.split("\\s+");

			// down
			if (m[0].equalsIgnoreCase("down") || m[0].equalsIgnoreCase("dn")) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
				
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewdown(player, item);
		
				} else if (m.length == 2 ) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					
					item = IDDataType.longArgumentToListIDDataType(m[1]);
				

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewdown(player, item);
				}

			}
			
			// wall
			if (m[0].equalsIgnoreCase("wall") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetwall(player, item, itemSearch);
				
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetwall(player, item, itemSearch);
				}

			}

			// block
			if (m[0].equalsIgnoreCase("setblock")) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetblock(player, item, itemSearch);
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetblock(player, item, itemSearch);
				}

			}

			// room
			if (m[0].equalsIgnoreCase("room") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetroom(player, item, itemSearch);
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetroom(player, item, itemSearch);
				}

			}

			// dewset
			if (m[0].equalsIgnoreCase("set") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewset(player, item, itemSearch,false);
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewset(player, item, itemSearch, false);
				}

			}

			// dewxet
			if (m[0].equalsIgnoreCase("xet") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewset(player, item, itemSearch, true);
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewset(player, item, itemSearch, true);
				}

			}
			
			// dsq
			
			if (m[0].equalsIgnoreCase("spreadq") || m[0].equalsIgnoreCase("dsq") ) {
				
				
				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item,player);
					
					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewspreadq(player, item, itemSearch);
				}
				else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);
						
					}
					
					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewspreadq(player, item, itemSearch);
				}
				
				
			}
			
			
			// dewspreadCircle
			if (m[0].equalsIgnoreCase("spreadSphere")  ) {
				
				
				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item,player);
					
					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewSpreadSphere(player, item, itemSearch);
				}
				else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);
						
					}
					
					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewSpreadSphere(player, item, itemSearch);
				}
				
				
			}
			
			
			// dewsetLight
			if (m[0].equalsIgnoreCase("setlight") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetLight(player, item);
				} else if (m.length == 2) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					
					item = IDDataType.longArgumentToListIDDataType(m[1]);
					

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetLight(player, item);
				}

			}
			
			// dewsetCircle
			if (m[0].equalsIgnoreCase("setSphere") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetFullSphere(player, item, itemSearch);
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetFullSphere(player, item, itemSearch);
				}

			}
			
			// wallSphere
			if (m[0].equalsIgnoreCase("wallSphere") ) {

				if (m.length == 1) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();
					dew.addItemIfItemIsZero(item, player);

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetWallSphere(player, item, itemSearch);
				} else if (m.length >= 2 && m.length <= 3) {
					ArrayList<IDDataType> item = new ArrayList<IDDataType>();
					ArrayList<IDDataType> itemSearch = new ArrayList<IDDataType>();

					item = IDDataType.longArgumentToListIDDataType(m[1]);
					if (m.length == 3) {
						itemSearch = IDDataType.longArgumentToListIDDataType(m[2]);

					}

					if (item.size() == 0) {
						player.sendMessage(tr.gettr("item.size() == 0"));
						return;
					}
					dew.dewsetWallSphere(player, item, itemSearch);
				}

			}
			
			

			// dewcopy
			if (m[0].equalsIgnoreCase("copy") == true ) {
				dew.dewcopy(player);

				return;
			}

			// dewa
			if (m[0].equalsIgnoreCase("da") == true || m[0].equalsIgnoreCase("stack") == true || 
					m[0].equalsIgnoreCase("dewa") == true  ) {

				int amo = 0;
				if (m.length == 1) {
					dew.dewStack(player, 0);
				} else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("/stack amount(it's not number T_T)");
						return;
					}

					amo = Integer.parseInt(m[1]);
					dew.dewStack(player, amo);
				}

				return;
			}

		}
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
				//	System.out.println("dew main waiting for create dewset sleeping ac +" + i);

				}

				//System.out.println("***** main dew = " + dew == null ? "null" : "not null");
				//System.out.println("***** main dewddflower.Main.ds = "
				//+ dewddflower.Main.ds == null ? "null" : "not null");

				
				
				dew = new dewset();
				dew.ac = ac;

				//System.out.println("***** main dew = " + dew == null ? "null" : "not null");
				//System.out.println(
					//	"***** main dewddflower.Main.ds = " + dewddflower.Main.ds == null ? "null" : "not null");

				/*
				 * while (dew.ac == null) {
				 * 
				 * i++; Thread.sleep(1000); System.out .println(
				 * "dew main waiting for create dewset sleeping dew ac +" + i);
				 * 
				 * dew.ac = ac;
				 * 
				 * } dew.loadmainfile();
				 */

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}



	dewset dew;

	public JavaPlugin ac = null;


	private Random rnd = new Random();

	

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
			dprint.r.printAll(dewset.ac == null ? "dew ac yes" : "dew ac no");

		}

		chatz ar = new chatz();
		ar.player = event.getPlayer();
		ar.message = event.getMessage();
		ar.start();

		chatx ab = new chatx(event.getMessage(), event.getPlayer());

		String m[] = event.getMessage().split(" ");
		// deleterecursive x1 z1 x2 z2 id data
		
		
		
		event.setCancelled(ab.canc);

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
			goodc1 = dew.cando_all(block, player, "damage");
			if (goodc1 == false) { // don't have permission

				return;
			} else { // have permission

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
/*
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Block block = event.getBlock();
		Player player = event.getPlayer();
		block.getWorld();

		block.getX();
		block.getY();
		block.getZ();

		boolean goodc1 = false;

		goodc1 = dewset.cando_all(block, player, "build");

		if (goodc1 == false) {
			event.setCancelled(true);
		} else {

			dew.randomplaynote(player.getLocation());
			
		}
*/
		// for
	}


	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();

		
		chatx ab = new chatx(event.getMessage().substring(1), event.getPlayer());

		event.setCancelled(ab.canc);

		chatz ar = new chatz();
		ar.player = event.getPlayer();
		ar.message = event.getMessage().substring(1);
		ar.start();

		dprint.r.printC(event.getMessage());

	}

	// PlayerDropItemEvent
	@EventHandler
	public void eventja(PlayerDropItemEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		dew.randomplaynote(event.getPlayer().getLocation());

		

	}

	// PlayerDeathEvent

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
		

		Block block = event.getClickedBlock();
		
	

		boolean goodc1 = false;
		goodc1 = dew.cando_all(block, player, "right");

		if (goodc1 == false) {
			
		} else {


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



} // class