/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddskyblock;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import api_skyblock.api_skyblock;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	class autoabsorb implements Runnable {
		private Block		b;
		private int			pid;
		private ItemStack	sid;

		public autoabsorb(Block b, int pid, ItemStack sid) {
			this.b = b;
			this.pid = pid;
			this.sid = sid;
		}

		@Override
		public void run() {

			Block b2 = b.getWorld().getBlockAt(api_skyblock.rs[pid].x, 252,
					api_skyblock.rs[pid].z);

			b.getWorld().dropItem(b2.getLocation(), sid);
			dprint.r.printC("autoabsorb: " + sid.getTypeId() + ":"
					+ sid.getData() + " amount " + sid.getAmount() + " at "
					+ api_skyblock.rs[pid].p[0]);

		}
	}

	class autocut implements Runnable {
		private Block	b;
		private int		pid;

		public autocut(Block b, int pid, int sid) {
			this.b = b;
			this.pid = pid;
		}

		@Override
		public void run() {
			if (b == null) return;

			switch (b.getTypeId()) {
			case 86:
			case 103:
			case 39:
			case 40:
			case 4:

				break;

			case 115:
				if (b.getData() != 3) return;

				b.breakNaturally();
				b.setTypeId(115);
				b.setData((byte) 0);

				for (Entity en : b.getWorld().getEntities()) {
					if (en == null) continue;
					if (en.getType() == EntityType.DROPPED_ITEM) {
						if (en.getLocation().distance(b.getLocation()) < 50) {
							Block b2 = b.getWorld().getBlockAt(
									api_skyblock.rs[pid].x, 252,
									api_skyblock.rs[pid].z);
							en.teleport(b2.getLocation());

						}
					}

				}

				return;

			case 59:
			case 141:
			case 142:
				if (b.getData() != 7) return;

				switch (b.getTypeId()) {
				case 59:
					b.breakNaturally();
					b.setTypeId(59);
					b.setData((byte) 0);
					break;
				case 141:
					b.breakNaturally();
					b.setTypeId(141);
					b.setData((byte) 0);
					break;

				case 142:
					b.breakNaturally();
					b.setTypeId(142);
					b.setData((byte) 0);
					break;
				}

				// teleport droped item near these block to that location
				for (Entity en : b.getWorld().getEntities()) {
					if (en == null) continue;
					if (en.getType() == EntityType.DROPPED_ITEM) {
						if (en.getLocation().distance(b.getLocation()) < 50) {
							Block b2 = b.getWorld().getBlockAt(
									api_skyblock.rs[pid].x, 252,
									api_skyblock.rs[pid].z);
							en.teleport(b2.getLocation());
						}
					}

				}

				return;
			case 83:
			case 81:
				if (b.getRelative(0, -1, 0).getTypeId() == 83
						|| b.getRelative(0, -1, 0).getTypeId() == 81) break;
				return;
			default:
				return;
			}

			Block b2 = b.getWorld().getBlockAt(api_skyblock.rs[pid].x, 252,
					api_skyblock.rs[pid].z);

			long now = 0;

			for (ItemStack it : b.getDrops()) {

				// tax
				now = System.currentTimeMillis();
				if (now - api_skyblock.rs[pid].autocutl < 1000) {
					api_skyblock.rs[pid].autocutc++;
				}
				else {
					api_skyblock.rs[pid].autocutc = 0;
					api_skyblock.rs[pid].autocutl = now;
				}

				if (api_skyblock.rs[pid].autocutc > dew.maxautocut) {
					// retry it
					autocut aee = new autocut(b, pid, 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aee, 1);

					return;
				}

				b2.getWorld().dropItem(b2.getLocation(), it);
			}

			dprint.r.printC("autocut: " + b.getTypeId() + ":" + b.getData()
					+ " at " + api_skyblock.rs[pid].p[0] + "(" + b.getX() + ","
					+ b.getY() + "," + b.getZ() + ") "
					+ api_skyblock.rs[pid].autocutc);

			b.setTypeId(0);
		}
	}

	public JavaPlugin	ac	= null;

	public api_skyblock	dew	= null;

	@EventHandler
	public synchronized void eventja(AsyncPlayerChatEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Player player = e.getPlayer();

		dew.lastmessage = e.getMessage();

		String[] eo = e.getMessage().split("\\s+");
		if (eo[0].equalsIgnoreCase("maxautocut")) {

			if (eo.length == 2) {
				dew.maxautocut = Integer.parseInt(eo[1]);
				dprint.r.printAll("maxautocut = " + dew.maxautocut);
			}
		}

		if (e.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(dprint.r.color(">dewdd skyblock"));

			e.setCancelled(true);
		}

		if (e.getMessage().equalsIgnoreCase("iscleanedchunk") == true)
			dprint.r.printAll(dew.iscleanedchunk((int) player.getLocation()
					.getX(), (int) player.getLocation().getZ(), player
					.getWorld()) == true ? "true" : "false");

		if (e.getMessage().equalsIgnoreCase("skyblock") == true)
			Bukkit.dispatchCommand(player, "warp skyblock");

		System.gc();

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		Block block = e.getBlock();
		Player player = e.getPlayer();

		if (api_skyblock.isrunworld(block.getWorld().getName()) == true)
			if (api_skyblock.isrunworld(block.getWorld().getName(), true) == false) {

				if (block.getY() == 1 && block.getTypeId() == dew.fongnam) {
					dprint.r.printAll("ptdew&dewdd: " + tr.gettr("alert")
							+ " '" + player.getName()
							+ tr.gettr("don't_break_protected_stone_at_y1")
							+ block.getX() + "," + block.getZ() + " to 0,0 = "
							+ dew.distance2d(block.getX(), block.getZ(), 0, 0));
					e.setCancelled(true);
				}
			}
			else { // check protect
				boolean cando = api_skyblock.cando(block, player, "break");
				if (cando == false) {
					e.setCancelled(true);
					return;
				}

			}
	}

	@EventHandler
	public void eventja(BlockGrowEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getBlock().getWorld().getName())) {
			return;
		}

		Block b = e.getBlock();

		if (api_skyblock.isrunworld(b.getWorld().getName()) == false) return;
		if (api_skyblock.isrunworld(b.getWorld().getName(), true) == false)
			return;

		int getid = dew.getprotectid(b);
		if (getid == -1) return;

		// search

		int se = api_skyblock.getplayerinslot(dew.flag_autocut, getid);

		if (se == -1) return;

		// dprint.r.printC("block glow " + e.getBlock().getTypeId() + " to " +
		// e.getNewState().getTypeId());

		for (int lop = 0; lop < api_skyblock.rsmaxp; lop++)
			for (Player pl : Bukkit.getOnlinePlayers())
				if (api_skyblock.rs[getid].p[lop]
						.equalsIgnoreCase(pl.getName())) {

					autocut ax = new autocut(b, getid, se);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);

					for (int xx = -5; xx <= 5; xx++) {
						for (int yy = -5; yy <= 5; yy++) {
							for (int zz = -5; zz <= 5; zz++) {

								autocut ax2 = new autocut(b.getRelative(xx, yy,
										zz), getid, se);
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										ac, ax2);

							}
						}
					}

					return;
				}

		// if found

	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		Block block = e.getBlock();
		if (api_skyblock.isrunworld(block.getWorld().getName(), true) == false) {

		}
		else { // check protect
			boolean cando = api_skyblock.cando(block, player, "place");
			if (cando == false) {
				e.setCancelled(true);
				return;
			}

		}
	}

	// @EventHandler
	public void eventja(ChunkLoadEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getChunk().getWorld().getName())) {
			return;
		}

		if (e.isNewChunk() == false) {
			Chunk chunk = e.getChunk();

			if (api_skyblock.isrunworld(chunk.getWorld().getName()) == false)
				return;

			int dis = dew
					.distance2d(chunk.getX() * 16, chunk.getZ() * 16, 0, 0);

			if (dis > dew.lowest_canbuild && dis < dew.maxest_canbuild)
				if (dew.iscleanedchunk(chunk.getX() * 16, chunk.getZ() * 16,
						chunk.getWorld()) == false)
					dew.cleanthischunkt(chunk, false);
		}

	}

	// @EventHandler
	public void eventja(ChunkPopulateEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getChunk().getWorld().getName())) {
			return;
		}

		Chunk chunk = e.getChunk();

		if (api_skyblock.isrunworld(chunk.getWorld().getName()) == false)
			return;

		if (api_skyblock.isrunworld(chunk.getWorld().getName(), true) == false) {

		}
		else
			// dew.cleanthischunkrs(e.getChunk(), false);
			dew.cleanthischunkrs0(e.getChunk());
	}

	@EventHandler
	public void eventja(ChunkUnloadEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getChunk().getWorld().getName())) {
			return;
		}
		Chunk chunk = e.getChunk();

		if (api_skyblock.isrunworld(chunk.getWorld().getName()) == false)
			return;
		if (api_skyblock.isrunworld(chunk.getWorld().getName(), true) == false)
			return;

		// is run world and at rs world
		Block block = chunk.getWorld().getBlockAt(chunk.getX() * 16, 50,
				chunk.getZ() * 16);

		int getid = api_skyblock.getprotectid(block);
		if (getid == -1) return;

		// loop all player is there in that zone ?

		for (int lop = 0; lop < api_skyblock.rsmaxp; lop++)
			for (Player pl : Bukkit.getOnlinePlayers())
				if (api_skyblock.rs[getid].p[lop]
						.equalsIgnoreCase(pl.getName())) {

					e.setCancelled(true);
					return;
				}

	}

	@EventHandler
	public void eventja(CreatureSpawnEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		if (e.getEntity() == null) return;
		if (e.getCreatureType() == null) return;

		if (e.getCreatureType() == CreatureType.VILLAGER) return;

		if (e.getCreatureType() == CreatureType.WOLF) return;

		if (e.getCreatureType() == CreatureType.CHICKEN) return;

		if (e.getCreatureType() == CreatureType.COW) return;

		if (e.getCreatureType() == CreatureType.SHEEP) return;

		if (e.getCreatureType() == CreatureType.MUSHROOM_COW) return;

		if (e.getCreatureType() == CreatureType.PIG) return;

		if (e.getCreatureType() == CreatureType.SNOWMAN) return;

		if (e.getCreatureType() == CreatureType.SQUID) return;

		if (e.getCreatureType() == CreatureType.WOLF) return;

		if (api_skyblock.isrunworld(e.getLocation().getWorld().getName()) == false)
			return;

		if (api_skyblock.isrunworld(e.getLocation().getWorld().getName(), true) == false)
			return;

		int getid = api_skyblock.getprotectid(e.getLocation().getBlock());
		if (getid == -1) {
			e.setCancelled(true);
			return;
		}

		if (api_skyblock.getplayerinslot(dew.flag_monster, getid) > -1) {
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(EntityChangeBlockEvent e) {
		if (e.getEntity() == null) return;

		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		if (e.getEntity().getType() == EntityType.ENDERMAN
				&& api_skyblock.getprotectid(e.getBlock()) > -1) {
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(EntityDamageByEntityEvent e)
			throws UserDoesNotExistException, NoLoanPermittedException {

		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld()
				.getName()) == false) return;

		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld()
				.getName(), true) == false) return;

		if (e.getDamager().getType() == EntityType.PLAYER) {
			if (!tr.isrunworld(ac.getName(), e.getDamager().getWorld()
					.getName())) {
				return;
			}
			Player plp = (Player) e.getDamager();

			// plp.getPassenger().remove();

			if (e.getEntity().getType() == EntityType.PLAYER) {

				Player plvi = (Player) e.getEntity();

				if (plvi.getItemInHand() == null) {
					e.setCancelled(true);
					return;
				}
				if (plvi.getItemInHand().getTypeId() == 0
						|| plvi.getItemInHand().getType() == Material.FISHING_ROD) {
					e.setCancelled(true);
					return;
				}

				for (ItemStack itm : plp.getInventory().getArmorContents()) {
					if (itm == null) continue;

					for (Enchantment en : Enchantment.values()) {
						if (en == null) continue;

						if (itm.getEnchantmentLevel(en) >= 100) {
							itm.removeEnchantment(en);
						}
					}
				}

				// staff can't hit player
				/*
				 * if (dew.isspyname(plvi.getName()) == true) {
				 * event.setCancelled(true); return; }
				 */

				int pvparea = api_skyblock.getprotectid(plvi.getLocation()
						.getBlock());
				if (pvparea >= 0) {
					if (api_skyblock.getplayerinslot(dew.flag_pvp, pvparea) == -1
							&& !plp.hasPermission(api_skyblock.poveride)
							&& api_skyblock.getplayerinslot(plp.getName(),
									pvparea) == -1) {
						e.setDamage((double) 0);
						e.setCancelled(true);
					}
					else if (Economy.hasMore(plvi.getName(), e.getDamage()) == true) {
						Economy.subtract(plvi.getName(), e.getDamage());
						Economy.add(plp.getName(), e.getDamage());
						plvi.sendMessage(dprint.r.color(Economy.getMoney(plvi
								.getName()) + " -= " + e.getDamage()));
						plp.sendMessage(dprint.r.color(Economy.getMoney(plp
								.getName()) + " += " + e.getDamage()));

					}
				}
				else if (pvparea == -1)
					if (Economy.hasMore(plvi.getName(), e.getDamage()) == true) {
						Economy.subtract(plvi.getName(), e.getDamage());
						Economy.add(plp.getName(), e.getDamage());
						plvi.sendMessage(dprint.r.color(Economy.getMoney(plvi
								.getName()) + " -= " + e.getDamage()));
						plp.sendMessage(dprint.r.color(Economy.getMoney(plp
								.getName()) + " += " + e.getDamage()));

					}

			} // target = player
			else if (e.getEntity().getType() == EntityType.VILLAGER
					|| e.getEntity().getType() == EntityType.CHICKEN
					|| e.getEntity().getType() == EntityType.COW
					|| e.getEntity().getType() == EntityType.HORSE
					|| e.getEntity().getType() == EntityType.IRON_GOLEM
					|| e.getEntity().getType() == EntityType.MUSHROOM_COW
					|| e.getEntity().getType() == EntityType.SHEEP
					|| e.getEntity().getType() == EntityType.SNOWMAN
					|| e.getEntity().getType() == EntityType.SQUID
					|| e.getEntity().getType() == EntityType.PIG
					|| e.getEntity().getType() == EntityType.WOLF) {

				int pvparea = api_skyblock.getprotectid(e.getEntity()
						.getLocation().getBlock());

				if (pvparea >= 0)
					if (api_skyblock.getplayerinslot(plp.getName(), pvparea) == -1
							&& !plp.hasPermission(api_skyblock.poveride)) {

						e.setDamage((double) 0);
						e.setCancelled(true);
					}
			} // target = cow

		}
	}

	@EventHandler
	public void eventja(EntityExplodeEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		/*
		 * if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase
		 * ("world")==false) { return; }
		 */

		Block block = e.getLocation().getBlock();
		// e.setCancelled(true);
		int getid = api_skyblock.getprotectid(block);
		if (getid > -1) {
			if (api_skyblock.getplayerinslot(dew.flag_explode, getid) > -1)
				e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(EntityInteractEvent e) {
		if (e.getEntity() == null) return;

		if (!tr.isrunworld(ac.getName(), e.getBlock().getWorld().getName())) {
			return;
		}

		if (e.getEntity().getType() == EntityType.PLAYER) {
			Player prp = (Player) e.getEntity();
			if (api_skyblock.cando(e.getBlock(), prp, "EntityInteract") == false)
				e.setCancelled(true);

		}
	}

	@EventHandler
	public void eventja(HangingBreakByEntityEvent e) {

		if (e.getRemover() == null) {
			e.setCancelled(true);
			return;
		}
		if (!tr.isrunworld(ac.getName(), e.getRemover().getWorld().getName())) {
			return;
		}
		if (e.getRemover().getType() == EntityType.PLAYER) {
			Player br = (Player) e.getRemover();

			if (api_skyblock.cando(e.getEntity().getLocation().getBlock(), br,
					"HangingBreakByEntity") == false) {
				br.sendMessage(dprint.r.color(tr
						.gettr("don't_break_hanging_picture_not_yours")));

				e.setCancelled(true);
				return;
			}

		}
		else {

			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(HangingBreakEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		if (e.getCause() == RemoveCause.EXPLOSION == true)
			if (api_skyblock.getprotectid(e.getEntity().getLocation()
					.getBlock()) > -1) {
				e.setCancelled(true);
				return;
			}

	}

	@EventHandler
	public void eventja(HangingPlaceEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		Player br = e.getPlayer();
		if (api_skyblock.cando(e.getPlayer().getLocation().getBlock(), br,
				"HangingPlaceEvent") == false) {
			br.sendMessage(dprint.r.color(tr
					.gettr("don't_place_hanging_picture_not_yours")));

			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(ItemDespawnEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		Block b = e.getEntity().getLocation().getBlock();
		if (api_skyblock.isrunworld(b.getWorld().getName()) == false) return;
		if (api_skyblock.isrunworld(b.getWorld().getName(), true) == false)
			return;

		int getid = dew.getprotectid(b);
		if (getid == -1) return;

		// search

		int se = api_skyblock.getplayerinslot(dew.flag_autoabsorb, getid);

		if (se == -1) return;

		// if found

		for (int lop = 0; lop < api_skyblock.rsmaxp; lop++)
			for (Player pl : Bukkit.getOnlinePlayers())
				if (api_skyblock.rs[getid].p[lop]
						.equalsIgnoreCase(pl.getName())) {
					autoabsorb ax = new autoabsorb(b, getid, e.getEntity()
							.getItemStack());
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);

					return;
				}

	}

	@EventHandler
	public void eventja(PlayerBucketEmptyEvent e) {
		// e.getPlayer().sendMessage(dprint.r.color("ptdew&dewdd: Bucket empty "
		// +
		// e.getBucket().name());

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		if (api_skyblock.cando(e.getBlockClicked(), e.getPlayer(), "build") == false)
			e.setCancelled(true);
	}

	@EventHandler
	public void eventja(PlayerBucketFillEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		if (api_skyblock.cando(e.getBlockClicked(), e.getPlayer(), "build") == false)
			e.setCancelled(true);
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e)
			throws UserDoesNotExistException, NoLoanPermittedException {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/setsky") == true) {

			if (player.hasPermission(dew.psetsky) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("you_don't_have_permission") + dew.psetsky));
				return;
			}

			dew.addsign(player.getLocation().getBlock());
			player.sendMessage(dprint.r
					.color("ptdew&dewdd: set skyblock done!"));

			return;
		}

		if (m[0].equalsIgnoreCase("/skyblock") || m[0].equalsIgnoreCase("/sky")) {

			if (api_skyblock.isrunworld(player.getLocation().getWorld()
					.getName()) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("this_world_is_not_skyblock")));
				return;
			}

			if (m.length == 1) {
				player.sendMessage(dprint.r.color("/skyblock new"));
				player.sendMessage(dprint.r.color("/skyblock reload"));

				if (api_skyblock.isrunworld(player.getWorld().getName(), true) == true) {
					player.sendMessage(dprint.r.color("/skyblock home [name]"));
					player.sendMessage(dprint.r.color("/skyblock add <player>"));
					player.sendMessage(dprint.r
							.color("/skyblock remove <player>"));
					player.sendMessage(dprint.r.color("/skyblock list"));
					player.sendMessage(dprint.r.color("/skyblock go <player>"));
					player.sendMessage(dprint.r.color("/skyblock buyhere"));
					player.sendMessage(dprint.r
							.color("/skyblock owner <player>"));
					player.sendMessage(dprint.r
							.color("/skyblock percent [new]"));

					player.sendMessage(dprint.r.color("/skyblock percent20"));

				}

				return;

			}
			else if (m.length == 2 || m.length == 3)
				if (m[1].equalsIgnoreCase("percent20")) {

					int pid[] = new int[dew.rsmax];
					int pidmax = 0;

					for (int i = 0; i < dew.rsmax; i++) {
						if (dew.rs[i].percent > 0) {
							pid[pidmax] = i;
							pidmax++;

						}

					}

					// bubble sort
					int t = 0;
					for (int i = 0; i < pidmax; i++) {
						for (int j = 0; j < (pidmax - 1 - i); j++) {

							if (dew.rs[pid[j]].percent < dew.rs[pid[j + 1]].percent) {
								t = pid[j];

								pid[j] = pid[j + 1];
								pid[j + 1] = t;

							}
						}

					}

					dprint.r.printAll(tr.gettr("top20_of_rate_island_is"));

					int i = 0;
					while (i < 20 && i < pidmax) {
						dprint.r.printAll((i + 1) + " : " + dew.rs[pid[i]].p[0]
								+ " = " + dew.rs[pid[i]].percent + "%");
						i++;
					}

				}
				else if (m[1].equalsIgnoreCase("percent")) {

					int getid = dew.getprotectid(player.getLocation()
							.getBlock());

					if (m.length == 2) {

						if (getid == -1) {
							player.sendMessage(tr
									.gettr("this_zone_don't_have_protect"));
							return;
						}

						dprint.r.printAll(tr.gettr("percent_of_is_of_this")
								+ dew.rs[getid].p[0] + " = "
								+ dew.rs[getid].percent + "%");
						return;
					}
					if (m.length == 3) {

						if (m[2].equalsIgnoreCase("new")) {
							dew.checkrateis(player.getLocation().getBlock());
						}
						else if (m[2].equalsIgnoreCase("reset")) {
							if (player.isOp() == false) {
								return;
							}

							player.sendMessage("reseting all island %");

							for (int i = 0; i < dew.rsmax; i++) {
								dew.rs[i].percent = 0.0;

							}

							dew.saversprotectfile();
							dew.loadrsprotectfile();
						}

					}

				}
				else if (m[1].equalsIgnoreCase("deleteofline")) {
					if (m.length != 3) {
						player.sendMessage(dprint.r
								.color("/sky deleteofline <day>"));
						return;
					}

					if (player.isOp() == false) {
						player.sendMessage(dprint.r.color("you are not op"));
						return;
					}

					int day = Integer.parseInt(m[2]);

					String me[] = tr.getlastonline(day,
							"plugins\\Essentials\\userdata");
					// check all sky block that all of that player out of
					// limited time

					for (int skyid = 0; skyid < dew.rsmax; skyid++) {

						// loop player on that slot that all in the list

						boolean nono = true;

						for (int slotid = 0; slotid < dew.rsmaxp; slotid++) {
							// search that name on the list

							if (dew.rs[skyid].p[slotid]
									.equalsIgnoreCase("null")) continue;
							if (dew.rs[skyid].p[slotid].startsWith("<"))
								continue;

							// search name in the silst
							boolean found = false;

							for (int l = 0; l < me.length; l++) {
								// if found on list it mean don't delete
								if (me[l].toLowerCase().indexOf(
										dew.rs[skyid].p[slotid].toLowerCase()) > -1) {
									player.sendMessage(dprint.r.color(me[l]));
									found = true; // found that player
									break;
								}

							}

							if (found == false) {
								// not found it's mean this skyblock should'nt
								// delete
								nono = false;
							}
						}

						if (nono == true) {
							// they not online
							player.sendMessage(dprint.r
									.color("this skyblock offline too longtime "
											+ skyid));
							dew.rs[skyid].p[0] = "";
							dew.saversprotectfile();
							dew.loadrsprotectfile();
							dprint.r.printAll(tr
									.gettr("this_skyblock_deleted_cuz_ofline_too_long_time")
									+ day);

						}

					}

				}
				else if (m[1].equalsIgnoreCase("rsmax"))
					player.sendMessage(dprint.r.color("rsmax = "
							+ api_skyblock.rsmax));
				else if (m[1].equalsIgnoreCase("buyhere")) {
					// for buy these zone
					if (api_skyblock.isrunworld(player.getLocation().getWorld()
							.getName(), true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_world_is_not_real_skyblock")));
						return;
					}

					int getid = api_skyblock.getprotectid(player.getLocation()
							.getBlock());
					if (getid > -1) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_zone_someone_bought_it")));
						return;
					}

					// buy this zone

					api_skyblock.rsmax++;
					api_skyblock.rs[api_skyblock.rsmax - 1].p = new String[api_skyblock.rsmaxp];
					for (int lop = 0; lop < api_skyblock.rsmaxp; lop++)
						api_skyblock.rs[api_skyblock.rsmax - 1].p[lop] = "null";

					api_skyblock.rs[api_skyblock.rsmax - 1].p[0] = player
							.getName();
					// time to find x and z

					api_skyblock.rs[api_skyblock.rsmax - 1].y = player
							.getLocation().getBlockY();

					// x
					double i3 = player.getLocation().getBlockX() / 300;

					i3 = Math.floor(i3) * 300;

					int i4 = (int) (i3 + 300);

					if (Math.abs(player.getLocation().getBlockX() - i3) < Math
							.abs(player.getLocation().getBlockX() - i4))
						api_skyblock.rs[api_skyblock.rsmax - 1].x = (int) i3;
					else
						api_skyblock.rs[api_skyblock.rsmax - 1].x = i4;

					// z
					i3 = player.getLocation().getBlockZ() / 300;

					i3 = Math.floor(i3) * 300;

					i4 = (int) (i3 + 300);

					if (Math.abs(player.getLocation().getBlockZ() - i3) < Math
							.abs(player.getLocation().getBlockZ() - i4))
						api_skyblock.rs[api_skyblock.rsmax - 1].z = (int) i3;
					else
						api_skyblock.rs[api_skyblock.rsmax - 1].z = i4;

					//
					player.sendMessage(dprint.r
							.color("ptdew&dewdd : bought island at ("
									+ api_skyblock.rs[api_skyblock.rsmax - 1].x
									+ ","
									+ api_skyblock.rs[api_skyblock.rsmax - 1].y
									+ ","
									+ api_skyblock.rs[api_skyblock.rsmax - 1].z
									+ ") host is "
									+ api_skyblock.rs[api_skyblock.rsmax - 1].p[0]));

					dew.saversprotectfile();
					return;
				}
				else if (m[1].equalsIgnoreCase("go")) {
					// go

					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == true) {
						if (m.length != 3) {
							player.sendMessage(dprint.r
									.color("need 3 arguments   /skyblock go <player>"));
							for (int lop2 = 0; lop2 < api_skyblock.rsmax; lop2++) { // lop2
								Block block = player.getWorld().getBlockAt(
										api_skyblock.rs[lop2].x,
										api_skyblock.rs[lop2].y + 10,
										api_skyblock.rs[lop2].z);
								player.sendMessage(dprint.r.color("Island at ("
										+ block.getX() + "," + block.getY()
										+ "," + block.getZ() + ") of "
										+ api_skyblock.rs[lop2].p[0]));

							} // lop2

							return;
						}

						for (int lop2 = 0; lop2 < api_skyblock.rsmax; lop2++)
							if (api_skyblock.rs[lop2].p[0].toLowerCase()
									.indexOf(m[2].toLowerCase()) > -1) {
								Block block = player.getWorld().getBlockAt(
										api_skyblock.rs[lop2].x,
										api_skyblock.rs[lop2].y + 10,
										api_skyblock.rs[lop2].z);
								block.getChunk().load();
								player.teleport(block.getLocation());
								player.sendMessage(dprint.r
										.color("teleported you to ("
												+ block.getX() + ","
												+ block.getY() + ","
												+ block.getZ() + ") of "
												+ api_skyblock.rs[lop2].p[0]));
								return;
							}

					}
					else {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_is_not_real_skyblock")));
						return;
					}

				}
				else if (m[1].equalsIgnoreCase("home")) {
					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == false) {
						Bukkit.dispatchCommand(player, "home");
						return;
					}
					else { // real skyblock

						int manyhome[] = new int[100];
						int manyhomemax = 0;

						for (int lop = 0; lop < api_skyblock.rsmax; lop++)
							for (int lop2 = 0; lop2 < api_skyblock.rsmaxp; lop2++)
								if (api_skyblock.rs[lop].p[lop2]
										.equalsIgnoreCase(player.getName())) {
									manyhomemax++;
									manyhome[manyhomemax - 1] = lop;
								}

						if (manyhomemax == 0) {
							player.sendMessage(dprint.r.color(tr
									.gettr("you_don't_have_any_skyblock_create_it_or_join_friend")));
							return;
						}

						if (manyhomemax == 1) {
							// found one auto teleport

							// if found his skyblock teleport him
							Block block = player.getWorld().getBlockAt(
									api_skyblock.rs[manyhome[0]].x,
									api_skyblock.rs[manyhome[0]].y + 10,
									api_skyblock.rs[manyhome[0]].z);
							block.getChunk().load();
							player.teleport(block.getLocation());
							player.sendMessage(dprint.r.color(tr
									.gettr("teleported_to_skyblock")
									+ " ("
									+ block.getX()
									+ ","
									+ block.getY()
									+ ","
									+ block.getZ()));
							return;

						}
						else if (manyhomemax > 1)
							if (m.length == 2) {
								// show list
								player.sendMessage(dprint.r.color(tr
										.gettr("you_have_more_than_1_home_so_choose_it")
										+ " /skyblock home <name>"));
								for (int lop = 0; lop < manyhomemax; lop++)
									player.sendMessage(dprint.r
											.color("/skyblock home "
													+ api_skyblock.rs[manyhome[lop]].p[0]));
								return;
							}
							else if (m.length == 3) {
								for (int lop = 0; lop < manyhomemax; lop++)
									if (api_skyblock.rs[manyhome[lop]].p[0]
											.toLowerCase().indexOf(
													m[2].toLowerCase()) > -1) {
										Block block = player
												.getWorld()
												.getBlockAt(
														api_skyblock.rs[manyhome[lop]].x,
														api_skyblock.rs[manyhome[lop]].y + 10,
														api_skyblock.rs[manyhome[lop]].z);
										block.getChunk().load();
										player.teleport(block.getLocation());
										player.sendMessage(dprint.r.color(tr
												.gettr("teleported_to_skyblock")
												+ " ("
												+ block.getX()
												+ ","
												+ block.getY()
												+ ","
												+ block.getZ()));
										return;
									}

								player.sendMessage(dprint.r.color(tr
										.gettr("not_found_skyblock_of") + m[2]));
								return;
							}

						/*
						 * int getid = dew.getnewrsid(player, false); if (getid
						 * == -1) { player.sendMessage(dprint.r.color(
						 * "you don't have real skyblock  type /skyblock new");
						 * return; }
						 */

					}
				}

				else if (m[1].equalsIgnoreCase("owner")) {
					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_world_is_not_realskyblock")));
						return;
					}

					int getid = api_skyblock.getprotectid(player.getLocation()
							.getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_zone_don't_have_protect")));
						return;
					}
					else {
						// check host
						boolean ch = api_skyblock.rs[getid].p[0]
								.equalsIgnoreCase(player.getName());

						if (ch == false)
							if (player.hasPermission(api_skyblock.poveride) == false) {
								player.sendMessage(dprint.r.color(tr
										.gettr("host_is")
										+ api_skyblock.rs[getid].p[0]
										+ tr.gettr("not_you!")));

								return;
							}
							else
								player.sendMessage(dprint.r.color(tr
										.gettr("overide_this_zone")));

						if (m.length != 3) {
							player.sendMessage(dprint.r
									.color("/skyblock owner <playername>"));
							return;
						}

						// check that player online

						api_skyblock.rs[getid].p[0] = m[2];
						player.sendMessage(dprint.r.color(tr
								.gettr("this_skyblock_owner_is")
								+ api_skyblock.rs[getid].p[0]));
						dew.saversprotectfile();
						return;

					}
				}

				else if (m[1].equalsIgnoreCase("add")) {
					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_world_is_not_real_skyblock")));
						return;
					}

					int getid = api_skyblock.getprotectid(player.getLocation()
							.getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_zone_don't_have_any_protect")));
						return;
					}
					else {
						// check host
						boolean ch = api_skyblock.rs[getid].p[0]
								.equalsIgnoreCase(player.getName());

						if (ch == false)
							if (player.hasPermission(api_skyblock.poveride) == false) {
								player.sendMessage(dprint.r.color(tr
										.gettr("host_is")
										+ api_skyblock.rs[getid].p[0]
										+ tr.gettr("not_you")));

								return;
							}
							else
								player.sendMessage(dprint.r.color(tr
										.gettr("overide_this_zone")));

					}

					if (m.length != 3) {
						player.sendMessage(dprint.r
								.color("/skyblock add <playername>"));
						return;
					}
					// if found his skyblock teleport him

					// check free slot

					for (int i = 1; i < api_skyblock.rsmaxp; i++)
						if (api_skyblock.rs[getid].p[i].equalsIgnoreCase(m[2])) {
							player.sendMessage(dprint.r.color(tr
									.gettr("already_added_this_name_so_not_work")));
							return;
						}

					for (int i = 1; i < api_skyblock.rsmaxp; i++)
						if (api_skyblock.rs[getid].p[i]
								.equalsIgnoreCase("null")) {
							api_skyblock.rs[getid].p[i] = m[2];
							player.sendMessage(dprint.r.color(tr.gettr("added")
									+ m[2] + tr.gettr("to_your_skyblock")));
							dew.saversprotectfile();
							return;
						}

				}
				else if (m[1].equalsIgnoreCase("remove")) {
					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_world_is_not_real_skyblock")));
						return;
					}

					int getid = api_skyblock.getprotectid(player.getLocation()
							.getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_zone_don't_have_protect")));
						return;
					}
					else {
						// check host
						boolean ch = api_skyblock.rs[getid].p[0]
								.equalsIgnoreCase(player.getName());

						if (ch == false)
							if (player.hasPermission(api_skyblock.poveride) == false) {
								player.sendMessage(dprint.r.color(tr
										.gettr("host_is")
										+ api_skyblock.rs[getid].p[0]
										+ tr.gettr("not_you")));
								return;
							}
							else
								player.sendMessage(dprint.r.color(tr
										.gettr("overide_this_zone")));

					}

					if (m.length != 3) {
						player.sendMessage(dprint.r
								.color("/skyblock remove <playername>"));
						return;
					}

					if (m[2].equalsIgnoreCase(player.getName())) {
						player.sendMessage(dprint.r.color(tr
								.gettr("you_can't_remove_your_name_from_your_zone")));
						return;
					}
					// if found his skyblock teleport him

					// check free slot

					for (int i = 1; i < api_skyblock.rsmaxp; i++)
						if (api_skyblock.rs[getid].p[i].equalsIgnoreCase(m[2])) {
							api_skyblock.rs[getid].p[i] = "null";
							player.sendMessage(dprint.r.color(tr
									.gettr("removed")
									+ m[2]
									+ tr.gettr("from_your_skyblock")));
							dew.saversprotectfile();
							return;
						}

					player.sendMessage(dprint.r.color(tr
							.gettr("this_protected_don't_have_this_name")
							+ m[2]));
					return;
				}
				else if (m[1].equalsIgnoreCase("list")) {
					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_world_is_not_real_skyblock")));
						return;
					}

					int getid = api_skyblock.getprotectid(player.getLocation()
							.getBlock());

					if (getid == -1) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_zone_don't_have_any_protect")));
						return;
					}

					for (int i = 0; i < api_skyblock.rsmaxp; i++)
						if (api_skyblock.rs[getid].p[i]
								.equalsIgnoreCase("null") == false)
							player.sendMessage(dprint.r.color("Member " + i
									+ " = " + api_skyblock.rs[getid].p[i]));
				}
				else if (m[1].equalsIgnoreCase("new")) { // new

					if (player.hasPermission(dew.pskyblock) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("you_don't_have_permission")
								+ dew.pskyblock));
						return;
					}

					// check item on player

					boolean hav = false;

					for (ItemStack itm : player.getInventory().getContents()) {
						if (itm != null) {
							hav = true;
							break;
						}
					}

					if (hav == true) {
						player.sendMessage(dprint.r.color(tr
								.gettr("can_not_skynew_if_have_item")));
						return;
					}

					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == false) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_is_normal_skyblock")));
						dew.createskyblock(player);
					}
					else {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_is_hardcore_skyblock")));
						dew.createskyblockrs(player);
					}
					return;
				}
				else if (m[1].equalsIgnoreCase("clean") == true) {

					if (player.isOp() == false) {
						player.sendMessage(dprint.r.color("only op"));
						return;
					}

					if (api_skyblock.isrunworld(player.getWorld().getName(),
							true) == true) {
						player.sendMessage(dprint.r.color(tr
								.gettr("this_world_in_not_normal_skyblock")));
						return;
					}

					for (int x = -100; x <= 100; x += 16)
						for (int z = -100; z <= 100; z += 16)
							dew.cleanthischunkt(

							player.getLocation().getBlock()
									.getRelative(x, 0, z).getChunk(), false);

				}
				else if (m[1].equalsIgnoreCase("reload") == true) {
					dew.loadskyblockfile();
					dew.loadrsprotectfile();
				}
		}

		if (e.getMessage().equalsIgnoreCase("/skybox") == true) {

			if (player.hasPermission(dew.pskybox) == false) {
				player.sendMessage(dprint.r.color(tr
						.gettr("you_don't_have_permission") + dew.pskybox));
				return;
			}

			dew.addbox(player);

		} // skyblock

	}

	@EventHandler
	public void eventja(PlayerInteractEvent e)
			throws UserDoesNotExistException, NoLoanPermittedException {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Action act;
		act = e.getAction();

		if ((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)
			return;

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

		if (api_skyblock.isrunworld(block.getWorld().getName(), true) == true) {
			if (block.getTypeId() == 63 || block.getTypeId() == 68) {

				Sign sign = (Sign) block.getState();

				if (sign.getLine(0).equalsIgnoreCase("[skypercent20]")) {
					// show

					int pid[] = new int[dew.rsmax];
					int pidmax = 0;

					for (int i = 0; i < dew.rsmax; i++) {
						if (dew.rs[i].percent > 0) {
							pid[pidmax] = i;
							pidmax++;

						}

					}

					// bubble sort
					int t = 0;
					for (int i = 0; i < pidmax; i++) {
						for (int j = 0; j < (pidmax - 1 - i); j++) {

							if (dew.rs[pid[j]].percent < dew.rs[pid[j + 1]].percent) {
								t = pid[j];

								pid[j] = pid[j + 1];
								pid[j + 1] = t;

							}
						}

					}

					Block bb;

					int nowrank = 0;

					for (int y = -1; y >= -4; y--)
						for (int x = -5; x <= 5; x++)
							for (int z = -5; z <= 5; z++) {
								bb = block.getRelative(x, y, z);
								if (bb.getTypeId() != 68
										&& bb.getTypeId() != 63) continue;

								Sign ss = (Sign) bb.getState();

								if (ss.getLine(0).equalsIgnoreCase("[dewdd]")
										|| ss.getLine(0).equalsIgnoreCase(
												"[private]")

								) {
									continue;
								}

								ss.setLine(0,
										dprint.r.color("" + (nowrank + 1)));
								ss.setLine(1, dprint.r
										.color(dew.rs[pid[nowrank]].p[0]));
								ss.setLine(
										2,
										dprint.r.color(""
												+ dew.rs[pid[nowrank]].percent));
								ss.setLine(3, dprint.r.color("%"));
								ss.update();

								nowrank++;
								if (nowrank > 19 || nowrank > pidmax) return;
							}

					// show
				}
			} // sign

			boolean cando = false;
			if (act == Action.RIGHT_CLICK_BLOCK)
				cando = api_skyblock.cando(block, player, "right");
			else if (act == Action.LEFT_CLICK_BLOCK)
				cando = api_skyblock.cando(block, player, "left");

			if (cando == false) {
				e.setCancelled(true);
				return;
			}

		}

	}

	// EntityExplodeEvent

	// Chat e.class

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();

		if (player.getInventory().first(83) >= 0) return;

		ItemStack ite = new ItemStack(83, 1);
		player.getInventory().addItem(ite);

		/*
		 * for (int i = 0; i < dew.rsmax; i++) {
		 * 
		 * if (dew.rs[i].p[0].equalsIgnoreCase(player.getName())) { // load
		 * chunk
		 * 
		 * for (int x = -170; x <= 170; x += 16) { for (int z = -170; z <= 170;
		 * z += 16) { player.getWorld().loadChunk((dew.rs[i].x + x) / 16,
		 * (dew.rs[i].z + z) / 16);
		 * 
		 * }
		 * 
		 * } } }
		 */

		dew.autocalpercent0_run();
	}

	@EventHandler
	public void eventja(PlayerMoveEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		if (api_skyblock.isrunworld(player.getWorld().getName()) == false
				|| api_skyblock.isrunworld(player.getWorld().getName(), true) == true)
			return;
		Block block = player.getLocation().getBlock();

		for (int x = block.getX() - 16; x <= block.getX() + 16; x += 16)
			for (int z = block.getZ() - 16; z <= block.getZ() + 16; z += 16) {
				int dis = dew.distance2d(x, z, 0, 0);
				Block block2 = player.getLocation().getWorld()
						.getBlockAt(x, 1, z);
				block2.getChunk().load(true);

				if (dis > dew.lowest_canbuild && dis < dew.maxest_canbuild)
					if (dew.iscleanedchunk(x, z, player.getLocation()
							.getWorld()) == false) {
						if (dew.randomG.nextInt(100) <= 80) return;

						Chunk chunk = block2.getChunk();

						dew.cleanthischunkt(chunk, false);

					}
			}

	}

	@EventHandler
	public void eventja(PlayerTeleportEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		e.getPlayer().addPotionEffect(
				PotionEffectType.REGENERATION.createEffect(200, 1), false);

	}

	@EventHandler
	public void eventja(SignChangeEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();

		if (e.getLine(0).equalsIgnoreCase("[dewskyblock]") == true) {
			if (player.hasPermission(dew.psetsky) == false) {
				player.sendMessage(dprint.r
						.color("you don't have permission for create sign setsky"));
				return;
			}

			e.setLine(0, "<dewskyblock>");
			e.setLine(1, "access deny");
			e.setLine(2, "...");
			e.setLine(3, "ptdew dewdd");

			for (int cd = 0; cd < 100; cd++) {
				Location loa = player.getLocation();
				loa.setX(player.getLocation().getX()
						+ (50 - dew.randomG.nextInt(100)));
				loa.setY(player.getLocation().getY()
						+ (50 - dew.randomG.nextInt(100)));
				loa.setZ(player.getLocation().getZ()
						+ (50 - dew.randomG.nextInt(100)));
				player.getWorld().strikeLightningEffect(loa);
			}
			player.getWorld().createExplosion(player.getLocation(),
					dew.randomG.nextInt(50));

			dprint.r.printAll("ptdew&dewdd: " + player.getName()
					+ " try to create [dewskyblock] sign !");

		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getBlock().getWorld().getName())) {
			return;
		}

		Block block = e.getBlock();
		if (api_skyblock.isrunworld(block.getWorld().getName()) == false)
			return;
		if (api_skyblock.isrunworld(block.getWorld().getName(), true) == false)
			return;

		Block block2 = e.getToBlock();

		if (block.getTypeId() == 8 || block.getTypeId() == 9
				|| block.getTypeId() == 10 || block.getTypeId() == 11) {
			// get protect

			int getpro = api_skyblock.getprotectid(block);
			if (getpro == -1) e.setCancelled(true);
		}
		if (e.getToBlock().getTypeId() == 4 && block.getTypeId() == 11) {

			Block block3 = null;
			for (int x1 = -1; x1 <= 1; x1++)
				for (int y1 = -1; y1 <= 1; y1++)
					for (int z1 = -1; z1 <= 1; z1++) {
						block3 = block2.getRelative(x1, y1, z1);

						int getid = dew.getprotectid(block3);
						if (getid == -1) continue;

						// search

						int se = api_skyblock.getplayerinslot(dew.flag_autocut,
								getid);

						if (se == -1) continue;

						for (int lop = 0; lop < api_skyblock.rsmaxp; lop++)
							for (Player pl : Bukkit.getOnlinePlayers())
								if (api_skyblock.rs[getid].p[lop]
										.equalsIgnoreCase(pl.getName())) {
									autocut ax = new autocut(block3, getid, se);
									Bukkit.getScheduler()
											.scheduleSyncDelayedTask(ac, ax);

									continue;
								}

					}

		}
	}

	@EventHandler
	public void onPlayerShootArrow(EntityShootBowEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld()
				.getName()) == false) return;

		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld()
				.getName(), true) == false) return;

		LivingEntity plvii = e.getEntity();

		if (plvii instanceof Player) {
			Player plvi = (Player) plvii;
			int pvparea = api_skyblock.getprotectid(plvi.getLocation()
					.getBlock());
			if (pvparea >= 0)
				if (api_skyblock.getplayerinslot(plvi.getName(), pvparea) == -1
						&& !plvi.hasPermission(api_skyblock.poveride)
						&& api_skyblock.getplayerinslot(dew.flag_pvp, pvparea) == -1) {
					Entity en = e.getProjectile();
					en.setVelocity(en.getVelocity().getRandom());

				}
		}
	}

} // class

