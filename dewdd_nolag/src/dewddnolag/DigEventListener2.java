/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddnolag;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class dsl implements Runnable {
		private Block block;

		public dsl(Block block) {
			this.block = block;
		}

		@Override
		public void run() {

			long st = System.currentTimeMillis();
			if (st - lastrun <= 50) {
				lastrun = System.currentTimeMillis();
				dsl ab = new dsl(block);
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ab, rnd.nextInt(100) + 50);
				return;
			}

			int c = 5;
			Block blb = null;
			boolean ne = false;
			for (int x = -c; x < c; x++) {
				for (int y = -c; y < c; y++) {

					for (int z = -c; z < c; z++) {

						blb = block.getRelative(x, y, z);
						if (blb.getTypeId() != 0 && blb.getTypeId() != 78 && blb.getTypeId() != 79) {
							continue;
						}

						if (blb.getLightLevel() >= 9) {
							continue;
						}

						ne = false;
						ne = blb.getRelative(-1, 0, 0).getTypeId() != 0;
						ne = ne || blb.getRelative(1, 0, 0).getTypeId() != 0;
						ne = ne || blb.getRelative(0, 0, -1).getTypeId() != 0;
						ne = ne || blb.getRelative(0, 0, 1).getTypeId() != 0;
						ne = ne || blb.getRelative(0, -1, 0).getTypeId() != 0;

						if (ne == false) {
							continue;
						}

						blb.setTypeId(50);
						lastrun = System.currentTimeMillis();
						return;

					}

				}

			}
		}
	}

	String pnolag = "dewdd.nolag";
	JavaPlugin ac = null;
	boolean bhop = false;
	boolean bred = false;
	boolean bitem = false;
	boolean bminecart = false;
	boolean bwater = false;
	boolean bhopmove = false;
	boolean bbreak = false;
	boolean bmon = false;
	boolean bmonlight = false;

	boolean bchunk = false;
	Random rnd = new Random();

	long lastrun = 0;

	@EventHandler
	public void eventja(BlockRedstoneEvent event) {
		if (bred == true) {
			Block block2 = event.getBlock();

			dprint.r.printAll("bred  block at = " + block2.getX() + "," + block2.getY() + "," + block2.getZ() + " at "
					+ block2.getWorld().getName());

			if (bbreak == true) {

				dprint.r.printAll("bbreak  block at = " + block2.getX() + "," + block2.getY() + "," + block2.getZ()
						+ " at " + block2.getWorld().getName());
				block2.breakNaturally();
			}

		}

	}

	@EventHandler
	public void eventja(ChunkUnloadEvent e) {
		if (bchunk == true) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(CreatureSpawnEvent e) {
		if (bmon == false) {
			return;
		}

		if (e.getEntity() == null) {
			return;
		}
		if (e.getCreatureType() == null) {
			return;
		}

		if (e.getCreatureType() == CreatureType.VILLAGER) {
			return;
		}

		if (e.getCreatureType() == CreatureType.WOLF) {
			return;
		}

		if (e.getCreatureType() == CreatureType.CHICKEN) {
			return;
		}

		if (e.getCreatureType() == CreatureType.COW) {
			return;
		}

		if (e.getCreatureType() == CreatureType.SHEEP) {
			return;
		}

		if (e.getCreatureType() == CreatureType.MUSHROOM_COW) {
			return;
		}

		if (e.getCreatureType() == CreatureType.PIG) {
			return;
		}

		if (e.getCreatureType() == CreatureType.SNOWMAN) {
			return;
		}

		if (e.getCreatureType() == CreatureType.SQUID) {
			return;
		}

		if (e.getCreatureType() == CreatureType.WOLF) {
			return;
		}

		dprint.r.printC("Creature Spawn Event at " + (int) e.getLocation().getX() + "," + (int) e.getLocation().getY()
				+ "," + (int) e.getLocation().getZ() + "  " + e.getLocation().getWorld().getName() + " type = "
				+ e.getCreatureType().getName());
		e.setCancelled(true);

		if (bmonlight == true) {

			dsl abc = new dsl(Bukkit.getWorld(e.getLocation().getWorld().getName()).getBlockAt(e.getLocation()));
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc, rnd.nextInt(100) + 50);
			e.setCancelled(true);
		} // light

	}

	@EventHandler
	public void eventja(InventoryMoveItemEvent event) {

		if (bhopmove == true) {
			if (event.getDestination().getType() == InventoryType.HOPPER) {
				InventoryHolder holder = event.getDestination().getHolder();
				if (holder != null && holder instanceof Hopper) {
					Hopper chest = (Hopper) holder;
					Block block2 = chest.getBlock();
					dprint.r.printAll("bhop move hopper at = " + block2.getX() + "," + block2.getY() + ","
							+ block2.getZ() + " at " + block2.getWorld().getName());
				}
			}
		}
	}

	@EventHandler
	public void eventja(InventoryPickupItemEvent event) {
		if (bhop == true) {
			Block block2 = event.getItem().getLocation().getBlock();

			dprint.r.printAll("bhop break hopper at = " + block2.getX() + "," + block2.getY() + "," + block2.getZ()
					+ " at " + block2.getWorld().getName());

		}
	}

	@EventHandler
	public void eventja(ItemSpawnEvent event) {
		if (bitem == true) {
			dprint.r.printAll("bitem at " + event.getLocation().getX() + "," + event.getLocation().getY() + ","
					+ event.getLocation().getZ() + " at " + event.getLocation().getWorld().getName());
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String st = event.getMessage().substring(1);

		if (st.equalsIgnoreCase("bfalse") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("btrue") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bhopmove") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bminecart") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bwater") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bitem") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}
		if (st.equalsIgnoreCase("bred") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}
		if (st.equalsIgnoreCase("bhop") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bmon") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bbreak") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}

		if (st.equalsIgnoreCase("bmonlight") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}
		if (st.equalsIgnoreCase("bchunk") == true) {
			if (player.hasPermission(pnolag) == false) {
				player.sendMessage("you need permission pnolag");
				return;
			}
		}
		nolag(event.getMessage().substring(1));
	}

	@EventHandler
	public void eventja(ServerCommandEvent event) {
		nolag(event.getCommand());
	}

	@EventHandler
	public void eventja(VehicleMoveEvent event) {
		if (bminecart == true) {
			Vehicle block2 = event.getVehicle();
			dprint.r.printAll("b minecart at = " + block2.getLocation().getX() + "," + block2.getLocation().getY() + ","
					+ block2.getLocation().getZ() + " at " + block2.getLocation().getWorld().getName());
			block2.remove();
		}

	}

	public void nolag(String st) {

		if (st.equalsIgnoreCase("bfalse") == true) {

			bhop = false;
			bred = false;
			bitem = false;
			bminecart = false;
			bwater = false;
			bhopmove = false;
			bbreak = false;
			bmon = false;
			bmonlight = false;
			bchunk = false;

			dprint.r.printAll("b red = " + Boolean.toString(bred));
			dprint.r.printAll("b hop = " + Boolean.toString(bhop));
			dprint.r.printAll("b item = " + Boolean.toString(bitem));
			dprint.r.printAll("b minecart = " + Boolean.toString(bminecart));
			dprint.r.printAll("b water = " + Boolean.toString(bwater));
			dprint.r.printAll("b bhopmove = " + Boolean.toString(bhopmove));

			dprint.r.printAll("b break  = " + Boolean.toString(bbreak));
			dprint.r.printAll("b mon = " + Boolean.toString(bmon));
			dprint.r.printAll("b mon light = " + Boolean.toString(bmonlight));
			dprint.r.printAll("b chunk = " + Boolean.toString(bchunk));

		}

		if (st.equalsIgnoreCase("btrue") == true) {
			bhop = true;
			bred = true;
			bitem = true;
			bminecart = true;
			bwater = true;
			bhopmove = true;
			bmon = true;
			bchunk = true;

			dprint.r.printAll("b red = " + Boolean.toString(bred));
			dprint.r.printAll("b hop = " + Boolean.toString(bhop));
			dprint.r.printAll("b item = " + Boolean.toString(bitem));
			dprint.r.printAll("b minecart = " + Boolean.toString(bminecart));
			dprint.r.printAll("b water = " + Boolean.toString(bwater));
			dprint.r.printAll("b bhopmove = " + Boolean.toString(bhopmove));
			dprint.r.printAll("b break  = " + Boolean.toString(bbreak));
			dprint.r.printAll("b mon = " + Boolean.toString(bmon));
			dprint.r.printAll("b mon light = " + Boolean.toString(bmonlight));
			dprint.r.printAll("b chunk = " + Boolean.toString(bchunk));

		}

		if (st.equalsIgnoreCase("bhopmove") == true) {
			bhopmove = !bhopmove;
			dprint.r.printAll("b bhopmove = " + Boolean.toString(bhopmove));

		}

		if (st.equalsIgnoreCase("bmon") == true) {
			bmon = !bmon;
			dprint.r.printAll("b mon = " + Boolean.toString(bmon));

		}

		if (st.equalsIgnoreCase("bmonlight") == true) {
			bmonlight = !bmonlight;
			dprint.r.printAll("b mon light = " + Boolean.toString(bmonlight));

		}

		if (st.equalsIgnoreCase("bbreak") == true) {
			bbreak = !bbreak;
			dprint.r.printAll("b bbreak = " + Boolean.toString(bbreak));

		}

		if (st.equalsIgnoreCase("bminecart") == true) {
			bminecart = !bminecart;
			dprint.r.printAll("b minecart = " + Boolean.toString(bminecart));

		}

		if (st.equalsIgnoreCase("bwater") == true) {
			bwater = !bwater;
			dprint.r.printAll("b water = " + Boolean.toString(bwater));

		}

		if (st.equalsIgnoreCase("bitem") == true) {
			bitem = !bitem;
			dprint.r.printAll("b item = " + Boolean.toString(bitem));

		}
		if (st.equalsIgnoreCase("bred") == true) {
			bred = !bred;
			dprint.r.printAll("b red = " + Boolean.toString(bred));

		}
		if (st.equalsIgnoreCase("bchunk") == true) {
			bchunk = !bchunk;
			dprint.r.printAll("b chunk = " + Boolean.toString(bchunk));

		}
		if (st.equalsIgnoreCase("bhop") == true) {
			bhop = !bhop;
			dprint.r.printAll("b hop = " + Boolean.toString(bhop));

		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent event) {
		if (bwater == true) {

			Block block = event.getBlock();
			Block block2 = event.getToBlock();

			if ((block.getTypeId() == 8 || block.getTypeId() == 9 || block.getTypeId() == 10
					|| block.getTypeId() == 11)) {

				event.setCancelled(true);
			}

		}
	}
}