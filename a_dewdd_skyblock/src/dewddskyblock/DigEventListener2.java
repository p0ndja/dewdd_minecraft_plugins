/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddskyblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import api_skyblock.Constant;
import api_skyblock.LV1000Type;
import api_skyblock.RSData;
import api_skyblock.api_skyblock;
import dewddtran.tr;
import li.Constant_Protect;
import net.minecraft.server.v1_9_R1.EntityPlayer;


public class DigEventListener2 implements Listener {

	public static void main(String[] abc) {

		api_skyblock.loadRSProtectFile2();
		ArrayList<RSData> ori = new ArrayList<RSData>();

		int count = 0;
		for (int i = 0; i < api_skyblock.rsMax; i++) {
			RSData rs = api_skyblock.rs[i];

			if (!(rs.x >= 0 && rs.x <= 6000 && rs.z >= 0 && rs.z <= 6000)) {
				count++;
				System.out.println((rs.x - 150) + "," + 0 + "," + (rs.z - 150) + " to " + (rs.x + 149) + "," + 0 + ","
						+ (rs.z + 149) + " original " + rs.x + "," + rs.y + "," + rs.z + " " + count);

			}

			ori.add(rs);

		}

	}

	class AutoAbsorb implements Runnable {
		private Block b;
		private int pid;
		private ItemStack sid;

		public AutoAbsorb(Block b, int pid, ItemStack sid) {
			this.b = b;
			this.pid = pid;
			this.sid = sid;
		}

		@Override
		public void run() {

			Block b2 = b.getWorld().getBlockAt(api_skyblock.rs[pid].x, 252, api_skyblock.rs[pid].z);

			b.getWorld().dropItem(b2.getLocation(), sid);
			dprint.r.printC("autoabsorb: " + sid.getType().name() + ":" + sid.getData() + " amount " + sid.getAmount()
					+ " at " + api_skyblock.rs[pid].p[0]);

		}
	}

	class Autocut implements Runnable {
		private Block b;
		private int curRSID;

		public Autocut(Block b, int pid, int sid) {
			this.b = b;
			this.curRSID = pid;
		}

		@Override
		public void run() {
			if (b == null)
				return;

			switch (b.getType()) {
			case PUMPKIN:
			case MELON_BLOCK:
				break;
			case BROWN_MUSHROOM:
			case RED_MUSHROOM:
				break;
			case COBBLESTONE:
				// dprint.r.printAll("0 autocut");
				return;

			case NETHER_WARTS:
				if (b.getData() != 3)
					return;

				b.breakNaturally();
				/*
				 * b.setType(Material.NETHER_WARTS); b.setData((byte) 0);
				 */

				for (Entity en : b.getWorld().getEntities()) {
					if (en == null)
						continue;
					if (en.getType() == EntityType.DROPPED_ITEM) {
						if (en.getLocation().distance(b.getLocation()) < 50) {
							Block b2 = b.getWorld().getBlockAt(api_skyblock.rs[curRSID].x, 252,
									api_skyblock.rs[curRSID].z);
							en.teleport(b2.getLocation());

						}
					}

				}

				return;

			case SEEDS:
			case CARROT:
			case POTATO:
			case CROPS:
				if (b.getData() != 7)
					return;

				switch (b.getType()) {
				case CROPS:
					b.breakNaturally();
					/*
					 * b.setType(Material.CROPS); b.setData((byte) 0);
					 */
					break;
				case CARROT:
					b.breakNaturally();
					/*
					 * b.setType(Material.CARROT); b.setData((byte) 0);
					 */
					break;

				case POTATO:
					b.breakNaturally();
					/*
					 * b.setType(Material.POTATO); b.setData((byte) 0);
					 */
					break;

				}

				// teleport droped item near these block to that location
				for (Entity en : b.getWorld().getEntities()) {
					if (en == null)
						continue;
					if (en.getType() == EntityType.DROPPED_ITEM) {
						if (en.getLocation().distance(b.getLocation()) < 50) {
							Block b2 = b.getWorld().getBlockAt(api_skyblock.rs[curRSID].x, 252,
									api_skyblock.rs[curRSID].z);
							en.teleport(b2.getLocation());
						}
					}

				}

				return;
			case SUGAR_CANE_BLOCK:
			case CACTUS:
				if (b.getRelative(0, -1, 0).getType() == Material.SUGAR_CANE_BLOCK
						|| b.getRelative(0, -1, 0).getType() == Material.CACTUS)
					break;
				return;
			default:
				return;
			}

			Block b2 = b.getWorld().getBlockAt(api_skyblock.rs[curRSID].x, 252, api_skyblock.rs[curRSID].z);

			long now = 0;

			for (ItemStack it : b.getDrops()) {

				// tax
				now = System.currentTimeMillis();
				if (now - api_skyblock.rs[curRSID].autoCutLastTime < 1000) {
					api_skyblock.rs[curRSID].autoCutCount++;
				} else {
					api_skyblock.rs[curRSID].autoCutCount = 0;
					api_skyblock.rs[curRSID].autoCutLastTime = now;
				}

				if (api_skyblock.rs[curRSID].autoCutCount > dew.maxautocut) {
					// retry it
					Autocut aee = new Autocut(b, curRSID, 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aee, 1);

					return;
				}

				b2.getWorld().dropItem(b2.getLocation(), it);
			}

			/*
			 * dprint.r.printC("autocut: " + b.getType().name() + ":" +
			 * b.getData() + " at " + api_skyblock.rs[curRSID].p[0] + "(" +
			 * b.getX() + "," + b.getY() + "," + b.getZ() + ") " +
			 * api_skyblock.rs[curRSID].autoCutCount);
			 */

			b.setType(Material.AIR);
		}
	}

	class CallNextMission implements Runnable {

		private int curRSID;
		private int curMission;

		public CallNextMission(int rsid, int curMission) {
			this.curMission = curMission;
			this.curRSID = rsid;
		}

		@Override
		public void run() {
			dew.nextMission(curRSID, curMission);
		}

	}

	class delay extends Thread {

		@Override
		public void run() {
			while (ac == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// dprint.r.printC("ft waiting ac != null");

			}

			dew.startMissionNotificationLoopShowing();

			MissionRepeatChecker ee = new MissionRepeatChecker();

			Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, ee, 1, 60);
		}
	}

	



	class LV5DestroyNetherRact implements Runnable {
		private Block signBlock;
		private int curRSID;

		public LV5DestroyNetherRact(Block signBlock, int rsID) {
			// dprint.r.printAll("lv1destroystone constructure");
			this.signBlock = signBlock;
			this.curRSID = rsID;
		}

		@Override
		public void run() {
			// dprint.r.printAll("lv5 class");

			if (signBlock.getType() == Material.SIGN_POST) {
				// dprint.r.printAll("lv5 class sign");

				Block nether = readBlockFromSign(signBlock);
				if (nether.getType() != Material.NETHERRACK) {

					// dprint.r.printAll("lv5 class nether");

					int fixhere = -1;
					CallNextMission oo = new CallNextMission(curRSID, -1);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, oo);
				}

			}

		}
	}

	class MissionRepeatChecker implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < api_skyblock.rsMax; i++) {

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player == null) {
						continue;
					}

					// dprint.r.printAll("misionrepeat i = " + i);

					int search = api_skyblock.getplayerinslot(player.getName(), i);
					if (search == -1) {
						continue;
					}

					if (api_skyblock.getplayerinslot(player.getName(), player.getLocation().getBlock()) == -1) {
						continue;
					}
					// check his mission

					int fixhere = -1;
					if (api_skyblock.rs[i].mission == fixhere) {

						/*
						 * dprint.r.printAll("misionrepeat lv 5 = " + i + " } "
						 * + dew.rs[i].x + ", " + 1 + ", " + dew.rs[i].z);
						 */
						// read sign

						Block bo = player.getWorld().getBlockAt(api_skyblock.rs[i].x, 1, api_skyblock.rs[i].z);

						if (bo.getType() == Material.SIGN_POST) {
							// dprint.r.printAll("misionrepeat lv signpost");

							Block cd = readBlockFromSign(bo);

							// dprint.r.printAll("misionrepeat lv 5 = " + x +
							// "," + y + "," + z);

							cd.setType(Material.NETHERRACK);
							cd.getRelative(BlockFace.UP).setType(Material.FIRE);

							if (cd.getType() == Material.NETHERRACK) {

								player.getWorld().setTime(16000);

								// search monster

								int count = countNearlyCreature(EntityType.ZOMBIE, cd, 10);
								if (count <= 5) {
									player.getWorld().spawnEntity(cd.getRelative(0, 5, 0).getLocation(),
											EntityType.ZOMBIE);

								}

							}

						}

					}

					break;

				}

			}
		}

	}

	class ShowCurStandProtect {
		public Player player;
		public int lastStandProtectID = -1;
	}

	

	public static JavaPlugin ac = null;

	public api_skyblock dew = null;

	Random rnd = new Random();

	public HashMap<String, ShowCurStandProtect> showCurStandProtect = new HashMap<String, ShowCurStandProtect>();

	public DigEventListener2() {
		delay dl = new delay();
		Thread dlt = new Thread(dl);
		dlt.start();

	}

	public int countNearlyCreature(EntityType en, Block startBlock, int radius) {

		int count = 0;

		for (Entity enn : startBlock.getWorld().getEntities()) {
			if (enn == null) {
				continue;
			}

			if (enn.getType() == en) {
				// check radius
				if (enn.getLocation().distance(startBlock.getLocation()) <= radius) {
					count++;
				}

			}
		}

		return count;
	}

	@EventHandler
	public synchronized void eventja(AsyncPlayerChatEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}
		Player player = e.getPlayer();
		
	

		dew.lastMessage = e.getMessage();

		String[] m = e.getMessage().split("\\s+");
		
		
		
		if (m[0].equalsIgnoreCase("maxautocut")) {

			if (m.length == 2) {
				dew.maxautocut = Integer.parseInt(m[1]);
				dprint.r.printAll("maxautocut = " + dew.maxautocut);
			}
		}

		if (e.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(dprint.r.color(">dewdd skyblock"));

			e.setCancelled(true);
		}

		System.gc();

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}
		Block block = e.getBlock();
		Player player = e.getPlayer();

		boolean cando = api_skyblock.cando(block, player, "break");
		if (cando == false) {
			e.setCancelled(true);
			return;
		}

		int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

		if (getid > -1) {
			// have protect

			// check you are in that home

			int gx = api_skyblock.getplayerinslot(player.getName(), getid);

			// dprint.r.printAll("blockbreak lv 1 gx = " + gx);

			if (gx > -1) {

				/*
				 * // search nearest stone Block bd =
				 * Bukkit.getWorld("world").getBlockAt(api_skyblock.rs[getid].x,
				 * api_skyblock.rs[getid].y, api_skyblock.rs[getid].z);
				 * 
				 * if (block.getType() == Material.COBBLESTONE) {
				 * CallNextMission bb = new CallNextMission(getid,
				 * Missional.LV_0_BREAK_COBBLESTONE);
				 * Bukkit.getScheduler().scheduleSyncDelayedTask(ac, bb, 1); }
				 */
			}

		}

	}

	@EventHandler
	public void eventja(BlockBurnEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getBlock().getWorld().getName()))
			return;

		Block ac = event.getBlock();
		int getid = api_skyblock.getProtectid(event.getBlock().getLocation().getBlock());

		if (getid > -1 || event.getBlock().getTypeId() == 35) {

			int d4 = 4;

			for (int ex = ac.getX() - d4; ex <= ac.getX() + d4; ex++) {
				for (int ey = ac.getY() - d4; ey <= ac.getY() + d4; ey++) {
					for (int ez = ac.getZ() - d4; ez <= ac.getZ() + d4; ez++) {
						Block ag = ac.getWorld().getBlockAt(ex, ey, ez);
						// dprint.r.printC (ag.getTypeId());
						if (ag.getType() == Material.FIRE) {
							ag.setType(Material.AIR);
						}
					}
				}
			}

			// event.getEntity().setFireTicks(1);
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockGrowEvent e) {
		if (!api_skyblock.isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}

		Block b = e.getBlock();

		int getid = api_skyblock.getProtectid(b);
		if (getid == -1)
			return;

		// search

		int se = api_skyblock.getplayerinslot(Constant_Protect.flag_autocut, getid);

		if (se == -1)
			return;

		for (int lop = 0; lop < api_skyblock.RSMaxPlayer; lop++)
			for (Player pl : Bukkit.getOnlinePlayers())
				if (api_skyblock.rs[getid].p[lop].equalsIgnoreCase(pl.getName())) {

					Autocut ax = new Autocut(b, getid, se);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);

					for (int xx = -5; xx <= 5; xx++) {
						for (int yy = -5; yy <= 5; yy++) {
							for (int zz = -5; zz <= 5; zz++) {

								if (b.getRelative(xx, yy, zz).getType() == Material.COBBLESTONE) {
									continue;
								}

								Autocut ax2 = new Autocut(b.getRelative(xx, yy, zz), getid, se);
								Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax2);

							}
						}
					}

					return;
				}

		// if found

	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		Block block = e.getBlock();

		boolean cando = api_skyblock.cando(block, player, "place");
		if (cando == false) {
			e.setCancelled(true);
			return;
		}

		int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

		if (getid > -1) {
			// have protect

			// check you are in that home

			int gx = api_skyblock.getplayerinslot(player.getName(), getid);

			// dprint.r.printAll("blockbreak lv 1 gx = " + gx);

			if (gx > -1) {

				/*
				 * if (player.getItemInHand().getType() == Material.INK_SACK) {
				 * api_skyblock.rs[getid].tmpForCountingBone1++;
				 * dew.printToAllPlayerOnRS(getid,
				 * tr.gettr("bone_meal_use_counting_=") +
				 * api_skyblock.rs[getid].tmpForCountingBone1 + "/" +
				 * Constant.LV_2_USE_BONE_MEAL_AMOUNT);
				 * 
				 * if (api_skyblock.rs[getid].tmpForCountingBone1 >=
				 * Constant.LV_2_USE_BONE_MEAL_AMOUNT) {
				 * 
				 * CallNextMission ee = new CallNextMission(getid,
				 * Missional.LV_2_USE_BONE_MEAL);
				 * Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ee); } }
				 * 
				 */
			}

		}

	}

	// @EventHandler
	public void eventja(ChunkLoadEvent e) {
		if (!api_skyblock.isrunworld(e.getChunk().getWorld().getName())) {
			return;
		}
	}

	// @EventHandler
	public void eventja(ChunkPopulateEvent e) {
		if (!api_skyblock.isrunworld(e.getChunk().getWorld().getName())) {
			return;
		}

	}

	@EventHandler
	public void eventja(ChunkUnloadEvent e) {
		if (!api_skyblock.isrunworld(e.getChunk().getWorld().getName())) {
			return;
		}
		Chunk chunk = e.getChunk();

		// is run world and at rs world
		Block block = chunk.getWorld().getBlockAt(chunk.getX() * 16, 50, chunk.getZ() * 16);

		int getid = api_skyblock.getProtectid(block);
		if (getid == -1)
			return;

		// loop all player is there in that zone ?

		for (int lop = 0; lop < api_skyblock.RSMaxPlayer; lop++)
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl == null) {
					continue;
				}
				if (api_skyblock.rs[getid].p[lop].equalsIgnoreCase(pl.getName())) {

					e.setCancelled(true);
					return;
				}

			}

	}

	@EventHandler
	public void eventja(CreatureSpawnEvent e) {
		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		EntityType et = e.getEntityType();
		
		if (e.getEntity() == null)
			return;
		if (et == null)
			return;

		
		if (et == EntityType.VILLAGER)
			return;

		if (et == EntityType.WOLF)
			return;

		if (et == EntityType.CHICKEN)
			return;

		if (et == EntityType.COW)
			return;

		if (et == EntityType.SHEEP)
			return;

		if (et == EntityType.MUSHROOM_COW)
			return;

		if (et == EntityType.PIG)
			return;

		if (et == EntityType.SNOWMAN)
			return;

		if (et == EntityType.SQUID)
			return;

		if (et == EntityType.WOLF)
			return;

		if (et == EntityType.RABBIT)
			return;

		int getid = api_skyblock.getProtectid(e.getLocation().getBlock());
		if (getid == -1) {
			e.setCancelled(true);
			return;
		}

		if (api_skyblock.getplayerinslot(Constant_Protect.flag_monster, getid) > -1) {
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(EntityChangeBlockEvent e) {
		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld().getName()) == false)
			return;

		if (e.getEntity() == null)
			return;

		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		if (e.getEntity().getType() == EntityType.ENDERMAN && api_skyblock.getProtectid(e.getBlock()) > -1) {
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(EntityDamageByEntityEvent e) {

		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld().getName()) == false)
			return;

		if (e.getDamager().getType() == EntityType.PLAYER) {
			if (!api_skyblock.isrunworld(e.getDamager().getWorld().getName())) {
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
				if (plvi.getItemInHand().getType() == Material.AIR
						|| plvi.getItemInHand().getType() == Material.FISHING_ROD) {
					e.setCancelled(true);
					return;
				}

				for (ItemStack itm : plp.getInventory().getArmorContents()) {
					if (itm == null)
						continue;

					for (Enchantment en : Enchantment.values()) {
						if (en == null)
							continue;

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

				int pvparea = api_skyblock.getProtectid(plvi.getLocation().getBlock());
				if (pvparea >= 0) {
					if (api_skyblock.getplayerinslot(Constant_Protect.flag_pvp, pvparea) == -1
							&& !plp.hasPermission(Constant.poveride)
							&& api_skyblock.getplayerinslot(plp.getName(), pvparea) == -1) {
						e.setDamage(0);
						e.setCancelled(true);
					}
				}

			} // target = player
			else if (e.getEntity().getType() == EntityType.VILLAGER || e.getEntity().getType() == EntityType.CHICKEN
					|| e.getEntity().getType() == EntityType.COW || e.getEntity().getType() == EntityType.HORSE
					|| e.getEntity().getType() == EntityType.IRON_GOLEM
					|| e.getEntity().getType() == EntityType.MUSHROOM_COW || e.getEntity().getType() == EntityType.SHEEP
					|| e.getEntity().getType() == EntityType.SNOWMAN || e.getEntity().getType() == EntityType.SQUID
					|| e.getEntity().getType() == EntityType.PIG || e.getEntity().getType() == EntityType.WOLF) {

				int pvparea = api_skyblock.getProtectid(e.getEntity().getLocation().getBlock());

				if (pvparea >= 0)
					if (api_skyblock.getplayerinslot(plp.getName(), pvparea) == -1
							&& !plp.hasPermission(Constant.poveride)) {

						e.setDamage(0);
						e.setCancelled(true);
					}
			} // target = cow

		}
	}

	@EventHandler
	public void eventja(EntityDamageEvent e) {
		if (api_skyblock.isrunworld(e.getEntity().getLocation().getWorld().getName()) == false)
			return;

		if (e.getEntity() instanceof EntityPlayer) {
			Player br = (Player) e.getEntity();
			if (api_skyblock.cando(br.getLocation().getBlock(), br, "entitydamageevent") == false) {

				e.setCancelled(true);
			}

		}

	}

	@EventHandler
	public void eventja(EntityExplodeEvent e) {
		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		/*
		 * if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase
		 * ("world")==false) { return; }
		 */

		Block block = e.getLocation().getBlock();
		// e.setCancelled(true);
		int getid = api_skyblock.getProtectid(block);
		if (getid > -1) {
			if (api_skyblock.getplayerinslot(Constant_Protect.flag_explode, getid) > -1)
				e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void eventja(EntityInteractEvent e) {
		if (e.getEntity() == null)
			return;

		if (!api_skyblock.isrunworld(e.getBlock().getWorld().getName())) {
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
		if (!api_skyblock.isrunworld(e.getRemover().getWorld().getName())) {
			return;
		}
		if (e.getRemover().getType() == EntityType.PLAYER) {
			Player br = (Player) e.getRemover();

			if (api_skyblock.cando(e.getEntity().getLocation().getBlock(), br, "HangingBreakByEntity") == false) {
				// br.sendMessage(dprint.r.color(tr.gettr("don't_breakbyentity_hanging_picture_not_yours")));

				e.setCancelled(true);
				return;
			}

		}
	}

	@EventHandler
	public void eventja(HangingBreakEvent e) {
		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		if (e.getEntity() instanceof EntityPlayer) {

			Player br = (Player) e.getEntity();

			if (api_skyblock.cando(br.getLocation().getBlock(), br, "HangingBreakEvent") == false) {
				e.setCancelled(true);
				// br.sendMessage(dprint.r.color(tr.gettr("don't_break_hanging_picture_not_yours")));
			}

		}

		if (e.getCause() == RemoveCause.EXPLOSION == true)
			if (api_skyblock.getProtectid(e.getEntity().getLocation().getBlock()) > -1) {
				e.setCancelled(true);
				return;
			}

	}

	@EventHandler
	public void eventja(HangingPlaceEvent e) {
		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		Player br = e.getPlayer();
		if (api_skyblock.cando(e.getPlayer().getLocation().getBlock(), br, "HangingPlaceEvent") == false) {
			// br.sendMessage(dprint.r.color(tr.gettr("don't_place_hanging_picture_not_yours")));

			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		if (inv == null) {
			return;
		}

		if (inv.getName().equalsIgnoreCase(api_skyblock.sky_c_inv_name)) {

			if (e.getSlot() >= 0 && e.getSlot() <= 10) {
				e.setCancelled(true);
			}

			if (e.getSlot() >= 44 && e.getSlot() <= 54) {
				e.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void eventja(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}

		if (inv.getName().equalsIgnoreCase(api_skyblock.sky_c_inv_name)) {

			Player p = (Player) e.getPlayer();

			int idx = api_skyblock.getProtectid(p.getLocation().getBlock());

			LV1000Type lv = api_skyblock.lv1000.get(api_skyblock.rs[idx].mission);

			boolean allDone = true;

			for (int i = 0; i < lv.needSize; i++) {
				boolean there = false;

				Material m1 = lv.getMaterial(lv.needNameData[i]);

				ItemStack needItem = new ItemStack(m1, lv.needAmount[i], lv.getData(lv.needNameData[i]));

				for (int j = 11; j < 44; j++) {
					ItemStack itemInInv = inv.getItem(j);
					if (itemInInv == null) {
						continue;
					}

					if (needItem.getType().name().equalsIgnoreCase(itemInInv.getType().name())) {
						if (needItem.getData().getData() == itemInInv.getData().getData()) {
							if (needItem.getAmount() <= itemInInv.getAmount()) {
								there = true;
								break;

							}
						}

					}

					if (there == true) {
						break;
					}
				}

				if (there == false) {
					allDone = false;
					break;
				}

			}

			// p.sendMessage("allDone " + allDone);
			if (allDone == false) {
				// p.sendMessage(dprint.r.color(tr.gettr("sky_c_item_not_enough_to_complete_c_so_return_all_item")));

				// drop item

				for (int j = 11; j < 44; j++) {
					ItemStack itemInInv = inv.getItem(j);
					if (itemInInv == null) {
						continue;
					}

					p.getWorld().dropItem(p.getLocation().getBlock().getRelative(0, 10, 0).getLocation(), itemInInv);
				}

				p.sendMessage(dprint.r
						.color(tr.gettr("sky_c_not_enough_item_to_complete_c")));
				
			} else {
				p.sendMessage(dprint.r
						.color(tr.gettr("sky_c_got_all_item_to_completed_cur_c_") + api_skyblock.rs[idx].mission));

				dprint.r.printAll(dprint.r
						.color(p.getName() + " = " + tr.gettr("sky_c_got_all_item_to_completed_cur_c_") + api_skyblock.rs[idx].mission));

				
				api_skyblock.rs[idx].mission++;
				dew.saveRSProtectFile();

				// give reward
				for (int j = 0; j < lv.rewardSize; j++) {
					ItemStack rewardIt = new ItemStack(lv.getMaterial(lv.rewardNameData[j]),

					lv.rewardAmount[j], lv.getData(lv.rewardNameData[j]));

					p.getWorld().dropItem(p.getLocation().getBlock().getRelative(0, 10, 0).getLocation(), rewardIt);
				}

			}

			// drop item
		}
	}

	@EventHandler
	public void eventja(InventoryOpenEvent e) {

	}

	@EventHandler
	public void eventja(ItemDespawnEvent e) {
		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		Block b = e.getEntity().getLocation().getBlock();
		if (api_skyblock.isrunworld(b.getWorld().getName()) == false)
			return;

		int getid = api_skyblock.getProtectid(b);
		if (getid == -1)
			return;

		// search

		int se = api_skyblock.getplayerinslot(Constant_Protect.flag_autoabsorb, getid);

		if (se == -1)
			return;

		// if found

		for (int lop = 0; lop < api_skyblock.RSMaxPlayer; lop++)
			for (Player pl : Bukkit.getOnlinePlayers())
				if (api_skyblock.rs[getid].p[lop].equalsIgnoreCase(pl.getName())) {
					AutoAbsorb ax = new AutoAbsorb(b, getid, e.getEntity().getItemStack());
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);

					return;
				}

	}

	@EventHandler
	public void eventja(PlayerBucketEmptyEvent e) {
		// e.getPlayer().sendMessage(dprint.r.color("ptdew&dewdd: Bucket empty "
		// +
		// e.getBucket().name());

		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		if (api_skyblock.cando(e.getBlockClicked(), e.getPlayer(), "build") == false)
			e.setCancelled(true);
	}

	@EventHandler
	public void eventja(PlayerBucketFillEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}
		if (api_skyblock.cando(e.getBlockClicked(), e.getPlayer(), "build") == false)
			e.setCancelled(true);
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		dew.commandRuning(m, player);
		
	}

	@EventHandler
	public void eventja(PlayerDropItemEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

		if (getid > -1) {
			// have protect

			// check you are in that home

			int gx = api_skyblock.getplayerinslot(player.getName(), getid);

			// dprint.r.printAll("blockbreak lv 1 gx = " + gx);

			if (gx > -1) {

				// check mission
				/*
				 * if (e.getItemDrop() != null) { if
				 * (e.getItemDrop().getItemStack().getType() == Material.TORCH)
				 * { if (e.getItemDrop().getItemStack().getAmount() >=
				 * Constant.LV_3_DROP_TOUCH_AMOUNT) {
				 * e.getItemDrop().getItemStack().setType(Material.CLAY);
				 * e.getItemDrop().getItemStack().setAmount(10);
				 * 
				 * CallNextMission ee = new CallNextMission(getid,
				 * Missional.LV_3_DROP_TOUCH);
				 * Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ee);
				 * return; } } } break;
				 */
			}

		}

	}

	@EventHandler
	public void eventja(PlayerInteractEntityEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}
		Block block = e.getPlayer().getLocation().getBlock();
		Player player = e.getPlayer();

		boolean cando = api_skyblock.cando(block, player, "break");
		if (cando == false) {
			e.setCancelled(true);
			return;
		}

		int getid = api_skyblock.getProtectid(player.getLocation().getBlock());

		if (getid > -1) {
			// have protect

			// check you are in that home

			int gx = api_skyblock.getplayerinslot(player.getName(), getid);

			// dprint.r.printAll("blockbreak lv 1 gx = " + gx);

			if (gx > -1) {

				/*
				 * // search nearest stone Block bd =
				 * Bukkit.getWorld("world").getBlockAt(api_skyblock.rs[getid].x,
				 * api_skyblock.rs[getid].y, api_skyblock.rs[getid].z);
				 * 
				 * if (block.getType() == Material.COBBLESTONE) {
				 * CallNextMission bb = new CallNextMission(getid,
				 * Missional.LV_0_BREAK_COBBLESTONE);
				 * Bukkit.getScheduler().scheduleSyncDelayedTask(ac, bb, 1); }
				 */
			}

		}
	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {

		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		Action act;
		act = e.getAction();

		if ((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)
			return;

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

		if (player.getItemInHand() != null) {
			if (player.getItemInHand().getType() == Material.COMPASS) {
				if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("/sky gorandom")) {

					int idid = rnd.nextInt(api_skyblock.rsMax);

					Block cdd = player.getWorld().getBlockAt(api_skyblock.rs[idid].x, api_skyblock.rs[idid].y + 10,
							api_skyblock.rs[idid].z);
					cdd.getChunk().load();
					player.teleport(cdd.getLocation());
					player.sendMessage(dprint.r.color("teleported you to (" + cdd.getX() + "," + cdd.getY() + ","
							+ cdd.getZ() + ") of " + api_skyblock.rs[idid].p[0]));
					return;
				}

			}
		}

		if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {

			Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equalsIgnoreCase("skypercent20")) {
				sign.setLine(0, "[skypercent20");
				sign.update(true);

			}
			if (sign.getLine(0).equalsIgnoreCase("[skypercent20]")) {
				// show

				int pid[] = new int[api_skyblock.rsMax];
				int pidmax = 0;

				for (int i = 0; i < api_skyblock.rsMax; i++) {
					if (api_skyblock.rs[i].mission != 0) {
						pid[pidmax] = i;
						pidmax++;

					}

				}

				// bubble sort
				int t = 0;
				for (int i = 0; i < pidmax; i++) {
					for (int j = 0; j < (pidmax - 1 - i); j++) {

						if (api_skyblock.rs[pid[j]].mission < api_skyblock.rs[pid[j + 1]].mission) {
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
							if (bb.getType() != Material.SIGN_POST && bb.getType() != Material.WALL_SIGN)
								continue;

							Sign ss = (Sign) bb.getState();

							if (ss.getLine(0).equalsIgnoreCase("[dewdd]") || ss.getLine(0).equalsIgnoreCase("[private]")

							) {
								continue;
							}

							ss.setLine(0, dprint.r.color("" + (nowrank + 1)));
							ss.setLine(1, dprint.r.color(api_skyblock.rs[pid[nowrank]].p[0]));
							ss.setLine(2, dprint.r.color("" + api_skyblock.rs[pid[nowrank]].mission));
							ss.setLine(3, dprint.r.color("%"));
							ss.update();

							nowrank++;
							if (nowrank > 19 || nowrank > pidmax)
								return;
						}

				// show
			}
		} // sign

		/*
		 * if (e.getClickedBlock().equals(Material.ITEM_FRAME)) { if
		 * (api_skyblock.cando(block, player, "playerInteractEvent") == false) {
		 * e.setCancelled(true); } }
		 */

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

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		final Player player = e.getPlayer();

		/*
		 * if (player.getInventory().first(83) >= 0) return;
		 * 
		 * ItemStack ite = new ItemStack(83, 1);
		 * player.getInventory().addItem(ite);
		 * 
		 * 
		 */

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, new Runnable() {

			@Override
			public void run() {
				player.sendMessage(tr.gettr("on_skyblock_playerjoin_tell_him_this_word"));
			}
		}, 100);

		int rsid = dew.getOWNIslandID(player.getName(), false);

		if (rsid == -1) {
			player.sendMessage(tr.gettr("you_dont_have_anyskyblock"));
			player.sendMessage(tr.gettr("type_this_command_for_create_new_skyblock"));

		} else {
			player.sendMessage(tr.gettr("cur_c_is") + (api_skyblock.rs[rsid].mission));

		}

	}

	@EventHandler
	public void eventja(PlayerMoveEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		if (rnd.nextInt(100) > 75) {

			Player p = e.getPlayer();

			ShowCurStandProtect i = showCurStandProtect.get(p.getName());
			if (i == null) {
				i = new ShowCurStandProtect();
				i.player = p;
				i.lastStandProtectID = -1;
				showCurStandProtect.put(p.getName(), i);

				return;

			} else {

				int curStand = api_skyblock.getProtectid(p.getLocation().getBlock());
				if (curStand == -1) { // no protect
					if (i.lastStandProtectID != -1) { // has protect

						p.sendMessage(dprint.r.color(tr.gettr("exit from sky protect of ")
								+ api_skyblock.rs[i.lastStandProtectID].p[0] + " id " + i.lastStandProtectID));
						i.lastStandProtectID = curStand; // be -1
						return;
					}

				} else { // cur has protect
							// cur there are protect
					if (curStand == i.lastStandProtectID) {

					} else {
						p.sendMessage(dprint.r.color(tr.gettr("enter to sky protect of ")
								+ api_skyblock.rs[curStand].p[0] + " id " + curStand));
						i.lastStandProtectID = curStand;
						return;

					}

				}
			}

		}

	}

	@EventHandler
	public void eventja(PlayerTeleportEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

		e.getPlayer().addPotionEffect(PotionEffectType.REGENERATION.createEffect(200, 1), false);

	}

	@EventHandler
	public void eventja(SignChangeEvent e) {
		if (!api_skyblock.isrunworld(e.getPlayer().getWorld().getName())) {
			return;
		}

	}

	// EntityExplodeEvent

	// Chat e.class

	

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent e) {
		if (!api_skyblock.isrunworld(e.getBlock().getWorld().getName())) {
			return;
		}

		Block block = e.getBlock();

		Block block2 = e.getToBlock();

		if (block.getTypeId() == 8 || block.getTypeId() == 9 || block.getTypeId() == 10 || block.getTypeId() == 11) {
			// get protect

			int getpro = api_skyblock.getProtectid(block);
			if (getpro == -1)
				e.setCancelled(true);
		}
		if (e.getToBlock().getTypeId() == 4 && block.getTypeId() == 11) {

			Block block3 = null;
			for (int x1 = -1; x1 <= 1; x1++)
				for (int y1 = -1; y1 <= 1; y1++)
					for (int z1 = -1; z1 <= 1; z1++) {
						block3 = block2.getRelative(x1, y1, z1);

						int getid = api_skyblock.getProtectid(block3);
						if (getid == -1)
							continue;

						// search

						int se = api_skyblock.getplayerinslot(Constant_Protect.flag_autocut, getid);

						if (se == -1)
							continue;

						for (int lop = 0; lop < api_skyblock.RSMaxPlayer; lop++)
							for (Player pl : Bukkit.getOnlinePlayers())
								if (api_skyblock.rs[getid].p[lop].equalsIgnoreCase(pl.getName())) {
									Autocut ax = new Autocut(block3, getid, se);
									Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);

									continue;
								}

					}

		}
	}

	@EventHandler
	public void onPlayerShootArrow(EntityShootBowEvent e) {
		if (!api_skyblock.isrunworld(e.getEntity().getWorld().getName())) {
			return;
		}

		LivingEntity plvii = e.getEntity();

		if (plvii instanceof Player) {
			Player plvi = (Player) plvii;
			int pvparea = api_skyblock.getProtectid(plvi.getLocation().getBlock());
			if (pvparea >= 0)
				if (api_skyblock.getplayerinslot(plvi.getName(), pvparea) == -1
						&& !plvi.hasPermission(Constant.poveride)
						&& api_skyblock.getplayerinslot(Constant_Protect.flag_pvp, pvparea) == -1) {
					Entity en = e.getProjectile();
					en.getVelocity();
					en.setVelocity(Vector.getRandom());

				}
		}
	}

	public Block readBlockFromSign(Block signBlock) {

		Sign sign = (Sign) signBlock.getState();
		int x = Integer.parseInt(sign.getLine(1));
		int y = Integer.parseInt(sign.getLine(2));
		int z = Integer.parseInt(sign.getLine(3));

		Block cd = signBlock.getWorld().getBlockAt(x, y, z);
		return cd;
	}

	

	

} // class
