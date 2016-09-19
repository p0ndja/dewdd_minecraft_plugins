/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddskywar;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import dewddflower.dewset;
import dewddtran.tr;

class Dewddminecraft extends dewset implements skywar_in {

	class skywar_go_c implements Runnable {
		// concept

		// run skywar 1 = 10000,80,10000 10300,80,10000

		@Override
		public void run() {
			// loop all skywar which one have all player
			clean_offline();
			World world = Bukkit.getWorld(skywar_in.worldrun);

			Block spawn[];
			int spawnmax = 0;

			for (int r1 = 0; r1 < rsmax; r1++)
				if (rs[r1].getonnow(r1) == rs[r1].maxp - 1 || r1 == runnowid) {
					if (runnowid == r1) runnowid = -1;

					dprint.r.printAll("skywar id " + r1
							+ tr.gettr("skywar_have_full_player")
							+ rs[r1].getonnow(r1) + "/" + (rs[r1].maxp - 1));
					dprint.r.printAll(tr.gettr("loading_skywar"));

					spawn = new Block[rs[r1].maxp];
					spawnmax = -1;
					// random position
					int x5 = getfreerun_x();
					dprint.r.printAll(tr.gettr("random_position") + x5
							+ ",128," + 10000);
					world.getBlockAt(x5, 128, 10000);

					// copy map

					int lx = rs[r1].x1 <= rs[r1].x2 ? rs[r1].x1 : rs[r1].x2;
					int mx = rs[r1].x1 <= rs[r1].x2 ? rs[r1].x2 : rs[r1].x1;

					int ly = rs[r1].y1 <= rs[r1].y2 ? rs[r1].y1 : rs[r1].y2;
					int my = rs[r1].y1 <= rs[r1].y2 ? rs[r1].y2 : rs[r1].y1;

					int lz = rs[r1].z1 <= rs[r1].z2 ? rs[r1].z1 : rs[r1].z2;
					int mz = rs[r1].z1 <= rs[r1].z2 ? rs[r1].z2 : rs[r1].z1;

					Block block2 = null;
					Block block3 = null;

					for (int amount = 1; amount <= 2; amount++)
						for (int g1 = lx - 1; g1 <= mx + 1; g1++)
							for (int g3 = lz - 1; g3 <= mz + 1; g3++)
								for (int g2 = 0; g2 <= 255; g2++) {
									if (g2 < ly || g2 > my) {
										block3 = world.getBlockAt(x5 + g1 - lx,
												g2, 10000 + g3 - lz);
										block3.setTypeId(0);
										continue;
									}
									if (g1 < lx || g1 > mx) {
										block3 = world.getBlockAt(x5 + g1 - lx,
												g2, 10000 + g3 - lz);
										block3.setTypeId(0);
										continue;
									}
									if (g3 < lz || g3 > mz) {
										block3 = world.getBlockAt(x5 + g1 - lx,
												g2, 10000 + g3 - lz);
										block3.setTypeId(0);
										continue;
									}

									block2 = world.getBlockAt(g1, g2, g3); // source
																			// block
									block3 = world.getBlockAt(x5 + g1 - lx, g2,
											10000 + g3 - lz);

									block3.setTypeId(block2.getTypeId());
									block3.setData(block2.getData());

									if (block3.getTypeId() == 54
											|| block3.getTypeId() == 146) {
										// chest copy item
										Chest c1 = (Chest) block2.getState();
										Chest c2 = (Chest) block3.getState();
										c2.getInventory().clear();
										for (ItemStack it : c1.getInventory()

										.getContents()) {
											if (it == null) continue;
											c2.getInventory().addItem(it);
										}

										c2.update();

									}

									if (block3.getTypeId() == 138
											&& amount == 2) {
										spawnmax++;
										if (spawnmax < rs[r1].maxp) {
											spawn[spawnmax] = block3;
											dprint.r.printAll("skywar id " + r1
													+ tr.gettr("found_spawner")
													+ spawnmax + " = "
													+ block3.getX() + ","
													+ block3.getY() + ","
													+ block3.getZ());
										}
									}
								} // y

					if (spawnmax < rs[r1].maxp - 1) {
						dprint.r.printAll("skywar id "
								+ r1
								+ tr.gettr("not_enough_spawner_place_call_admin"));
						return;
					}

					boolean nullall = false;
					int ra = 0;

					int nowplayer = -1;

					int getmaxonline = rs[r1].getonnow(r1);
					int nowteleyet = -1;

					while (nullall == false && nowteleyet < getmaxonline) {

						nullall = true;

						for (int g1 = 0; g1 <= spawnmax; g1++)
							if (spawn[g1] != null) {
								nullall = false;
								break;
							}

						if (nullall == true) {
							dprint.r.printAll("null all exit");
							break;
						}

						do {
							ra = rnd.nextInt(spawnmax + 1);
							dprint.r.printAll("randoming...  " + ra + "/ "
									+ spawnmax);

						}
						while (spawn[ra] == null);
						dprint.r.printAll("random spawning ra = " + ra);

						// teleport that player

						nowplayer++;

						Player pla = Bukkit.getPlayer(rs[r1].on[nowplayer]);
						dprint.r.printAll("player is " + pla.getName());

						for (int cl = 0; cl < pla.getInventory().getSize(); cl++)
							pla.getInventory().clear(cl);
						pla.getInventory().setBoots(new ItemStack(0));
						pla.getInventory().setChestplate(new ItemStack(0));
						pla.getInventory().setHelmet(new ItemStack(0));
						pla.getInventory().setLeggings(new ItemStack(0));
						pla.setFoodLevel(20);

						spawn[ra].getLocation().getChunk().load();
						Location loc = spawn[ra].getLocation();
						loc.setY(loc.getY() + 1);

						pla.addPotionEffect(PotionEffectType.BLINDNESS
								.createEffect(200, 1));

						telep ttt = new telep(pla, loc);

						nowteleyet++;

						dprint.r.printAll(pla.getName()
								+ tr.gettr("has_been_teleported_to_skywar_id")
								+ r1);
						pla.setGameMode(GameMode.SURVIVAL);
						pla.setAllowFlight(false);

						for (Entity en : world.getEntities()) {
							if (en == null) continue;
							if (en.getType() != EntityType.DROPPED_ITEM)
								continue;
							en.remove();
						}

						spawn[ra].setTypeId(0);
						spawn[ra] = null;

					}

					// clean online
					for (int r2 = 0; r2 < rs[r1].maxp; r2++)
						rs[r1].on[r2] = "";

					dprint.r.printAll("skywar id " + r1
							+ tr.gettr("skywar_has_been_started"));

					// teleport monster
					for (Entity en : world.getEntities()) {
						if (en == null) continue;

						if (en.getType() == EntityType.BAT
								|| en.getType() == EntityType.BLAZE
								|| en.getType() == EntityType.CAVE_SPIDER
								|| en.getType() == EntityType.CHICKEN
								|| en.getType() == EntityType.COW
								|| en.getType() == EntityType.CREEPER
								|| en.getType() == EntityType.ENDER_DRAGON
								|| en.getType() == EntityType.ENDERMAN
								|| en.getType() == EntityType.GHAST
								|| en.getType() == EntityType.GIANT
								|| en.getType() == EntityType.HORSE
								|| en.getType() == EntityType.IRON_GOLEM
								|| en.getType() == EntityType.MAGMA_CUBE
								|| en.getType() == EntityType.MUSHROOM_COW
								|| en.getType() == EntityType.OCELOT
								|| en.getType() == EntityType.PIG
								|| en.getType() == EntityType.PIG_ZOMBIE
								|| en.getType() == EntityType.SHEEP
								|| en.getType() == EntityType.SILVERFISH
								|| en.getType() == EntityType.SKELETON
								|| en.getType() == EntityType.SLIME
								|| en.getType() == EntityType.SNOWMAN
								|| en.getType() == EntityType.SPIDER
								|| en.getType() == EntityType.SQUID
								|| en.getType() == EntityType.VILLAGER
								|| en.getType() == EntityType.WITCH
								|| en.getType() == EntityType.WOLF
								|| en.getType() == EntityType.ZOMBIE

						) {

							Block bs = world.getBlockAt(rs[r1].x1, rs[r1].y1,
									rs[r1].z1);
							Block bd = world.getBlockAt(x5 + bs.getX() - lx,
									bs.getY(), 10000 + bs.getZ() - lz);

							if (en.getLocation().distance(bs.getLocation()) < 100)
								world.spawnCreature(bd.getLocation(),
										en.getType());

						}
					}

				}

		}
	}

	class skywardata {
		public String[]	builder;
		public int		maxp;
		public String[]	on;

		public int		x1;
		public int		x2;
		public int		y1;

		public int		y2;

		public int		z1;

		public int		z2;

		public int getonnow(int id) {

			int count = -1;
			for (int g1 = 0; g1 < rs[id].maxp; g1++)
				if (rs[id].on[g1].equalsIgnoreCase("") == false) count++;

			return count;

		}

	}

	class telep implements Runnable {
		private Player		pla;
		private Location	loc;

		public telep(Player pla, Location loc) {
			this.loc = loc;
			this.pla = pla;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this,
					rnd.nextInt(40));

		}

		@Override
		public void run() {

			if (lasttp == 0) lasttp = System.currentTimeMillis();

			long gx = System.currentTimeMillis();
			if (gx - lasttp <= 2000) {

				telep abr = new telep(pla, loc);
				pla.sendMessage(tr.gettr("waiting_teleporting") + (gx - lasttp));

				return;
			}

			pla.teleport(loc);
			lasttp = System.currentTimeMillis();
			dprint.r.printAll("teleported " + pla.getName() + " to "
					+ loc.getBlockX() + "," + loc.getBlockY() + ","
					+ loc.getBlockZ());

			for (PotionEffectType aee : PotionEffectType.values())
				pla.removePotionEffect(aee);

		}
	}

	int					runnowid	= -1;			// force run

	long				lasttp		= 0;

	public Random		rnd			= new Random();

	public skywardata[]	rs;

	public int			rsmax		= 0;

	public int			x1			= 0;
	public int			x2			= 0;
	public int			y1			= 0;

	public int			y2			= 0;

	public int			z1			= 0;

	public int			z2			= 0;

	public Dewddminecraft() {
		loaddewsetlistblockfile();
	}

	@Override
	public boolean add(Player p, int rsid, String name) {

		if (!p.hasPermission(skywar_in.poveride)) {
			if (!rs[rsid].builder[0].equalsIgnoreCase(p.getName())) {
				p.sendMessage(tr.gettr("skywar_can't_add_not_yours"));
				return false;
			}
		}
		else if (!rs[rsid].builder[0].equalsIgnoreCase(p.getName()))
			p.sendMessage(tr.gettr("overide_adding_skywar"));

		for (int g1 = 0; g1 < rs[rsid].maxp; g1++)
			if (rs[rsid].builder[g1].equalsIgnoreCase(name)) {
				p.sendMessage("this skywar zone already has " + name);
				return false;
			}

		// search empty
		for (int g1 = 0; g1 < skywar_in.maxbuilder; g1++)
			if (rs[rsid].builder[g1].equalsIgnoreCase("")) {
				rs[rsid].builder[g1] = name;
				saveskywar();
				reload();
				dprint.r.printAll(p.getName() + " added " + name
						+ " to skywar id " + rsid + " at slot " + g1);
				return true;
			}

		p.sendMessage(tr.gettr("don't_have_empty_slot_for_add"));
		return false;
	}

	@Override
	public void autoaddname() {

		String m[];
		boolean found = false;

		for (int i = 0; i < rsmax; i++) {
			found = false;

			for (int j = 0; j < skywar_in.maxbuilder; j++) {
				m = rs[i].builder[j].split(":");

				if (m[0].equalsIgnoreCase("name")) {
					if (m.length != 2) continue;

					found = true;

				}

			}

			if (found == false) {
				// add to last

				rs[i].builder[skywar_in.maxbuilder - 1] = "name:" + i;
				saveskywar();
				continue;
			}

		}
	}

	@Override
	public void bp(int id, Player p) {
		// id ok teleport player to bp zone
		Block block = p.getLocation().getWorld()
				.getBlockAt(rs[id].x2, rs[id].y2, rs[id].z2);
		block.getChunk().load();
		p.teleport(block.getLocation());
		p.sendMessage(tr.gettr("teleported_to_skywar_id_blueprint") + id);
		return;
	}

	@Override
	public boolean canbuild(Block block, Player p) {
		int getid = getrsid(block);
		if (getid == -1) return true;

		int slotid = isplayerinrs(getid, p.getName());
		if (slotid == -1) return false;

		return true;
	}

	@Override
	public boolean canbuild(Block block, String p) {
		int getid = getrsid(block);
		if (getid == -1) return true;

		int slotid = isplayerinrs(getid, p);
		if (slotid == -1) return false;

		return true;
	}

	@Override
	public boolean checkpermissionarea(Block block, Player player,
			String modeevent) {
		if (modeevent.equalsIgnoreCase("dewset")) {
			// check rs protect
			if (player.hasPermission(skywar_in.poveride)) return false;

			int getid = getrsid(block);
			if (getid == -1) return true;

			int getslot = isplayerinrs(getid, player.getName());
			if (getslot == -1) return true;

			return false;
		}

		return false;

	}

	@Override
	public void clean_noplayer_skywar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clean_offline() {
		for (int r1 = 0; r1 < rsmax; r1++)
			for (int g1 = 0; g1 < rs[r1].maxp; g1++) {
				if (rs[r1].on[g1].equalsIgnoreCase("")) continue;

				if (Bukkit.getPlayer(rs[r1].on[g1]) == null) {
					dprint.r.printAll(rs[r1].on[g1] + " is offline " + r1
							+ " = " + rs[r1].getonnow(r1) + "/"
							+ (rs[r1].maxp - 1));
					rs[r1].on[g1] = "";
				}
			}
	}

	@Override
	public int create(int maxplayer, Player p) {
		if (maxplayer < 1 || maxplayer > 10) {
			p.sendMessage("max player must be 1 - 10");
			return -1;
		}

		if (x1 == 0 && y1 == 0 && z1 == 0) {
			p.sendMessage("skywar please set block 1");
			return -1;
		}

		if (x2 == 0 && y2 == 0 && z2 == 0) {
			p.sendMessage("skywar please set block 2");
			return -1;
		}

		p.sendMessage("creating skywar ");

		rsmax++;
		rs[rsmax - 1].builder = new String[skywar_in.maxbuilder];
		for (int i = 0; i < skywar_in.maxbuilder; i++)
			rs[rsmax - 1].builder[i] = "";

		int lx = x1 <= x2 ? x1 : x2;
		int mx = x1 <= x2 ? x2 : x1;

		int ly = y1 <= y2 ? y1 : y2;
		int my = y1 <= y2 ? y2 : y1;

		int lz = z1 <= z2 ? z1 : z2;
		int mz = z1 <= z2 ? z2 : z1;

		rs[rsmax - 1].builder[0] = p.getName();
		rs[rsmax - 1].maxp = maxplayer;
		rs[rsmax - 1].x1 = lx;
		rs[rsmax - 1].y1 = ly;
		rs[rsmax - 1].z1 = lz;
		rs[rsmax - 1].x2 = mx;
		rs[rsmax - 1].y2 = my;
		rs[rsmax - 1].z2 = mz;

		p.sendMessage("saving this skywar to id " + (rsmax - 1));
		p.sendMessage("position = " + x1 + "," + y1 + "," + z1 + " to " + x2
				+ "," + y2 + "," + z2);
		p.sendMessage("max player = " + maxplayer);

		saveskywar();
		p.sendMessage("saved new skywar");
		reload();

		return rsmax - 1;
	}

	@Override
	public boolean delete(int rsid) {
		rs[rsid].maxp = 0;
		saveskywar();
		loadskywar();
		dprint.r.printAll("removed this skywar");

		return false;
	}

	@Override
	public boolean forceplay(Player p, String target, int toid) {

		// search player
		Player tar = Bukkit.getPlayer(target);
		if (tar == null) {
			p.sendMessage("not found player for force play");
			return false;
		}

		join(toid, tar);

		return false;
	}

	public int getfreerun_x() {
		World world = Bukkit.getWorld(skywar_in.worldrun);
		Block block = world.getBlockAt(10000, 128, 10000);

		boolean nearp = false;

		for (int xx = 10000; xx < 15000; xx += 300) {

			block = world.getBlockAt(xx, 128, 10000);

			nearp = false;

			for (Player pr : world.getPlayers())
				if (pr.getLocation().distance(block.getLocation()) < 150) {
					nearp = true;
					break;
				}

			if (nearp == false) return xx;

		}

		return -1;
	}

	@Override
	public int getidfromname(String sgr) {
		String m[];

		for (int i = 0; i < rsmax; i++)
			for (int j = 0; j < skywar_in.maxbuilder; j++) {
				m = rs[i].builder[j].split(":");

				if (m[0].equalsIgnoreCase("name")) {
					if (m.length != 2) continue;

					if (m[1].indexOf(sgr) > -1) return i;

				}

			}

		return -1;
	}

	@Override
	public String getnamefromid(int i) {
		String m[];

		for (int j = 0; j < rs[i].maxp; j++) {
			m = rs[i].builder[j].split(":");

			if (m[0].equalsIgnoreCase("name")) {
				if (m.length != 2) continue;

				return m[1];

			}

		}

		return "-1";
	}

	@Override
	public int getrsid(Block block) {

		if (block.getWorld().getName().equalsIgnoreCase(skywar_in.worldrun) == false)
			return -1;

		for (int l = 0; l < rsmax; l++)
			if (block.getLocation().getX() >= rs[l].x1
					&& block.getLocation().getX() <= rs[l].x2
					&& block.getLocation().getY() >= rs[l].y1
					&& block.getLocation().getY() <= rs[l].y2
					&& block.getLocation().getZ() >= rs[l].z1
					&& block.getLocation().getZ() <= rs[l].z2) return l;

		return -1;
	}

	@Override
	public int isplayerinrs(int rsid, String name) {

		for (int i = 0; i < skywar_in.maxbuilder; i++)
			if (rs[rsid].builder[i].equalsIgnoreCase(name)) return i;

		return -1;
	}

	@Override
	public int join(int id, Player p) {

		// check that name in that zone
		for (int g1 = 0; g1 < rs[id].maxp; g1++)
			if (rs[id].on[g1].equalsIgnoreCase(p.getName())) {
				p.sendMessage("you already join at " + id);
				return -1;
			}

		// check online max

		if (rs[id].getonnow(id) == rs[id].maxp - 1) {
			p.sendMessage("this skywar id " + id + " is full "
					+ rs[id].getonnow(id) + "/" + (rs[id].maxp - 1));
			return -1;
		}

		// remove these player from another skywar
		for (int r1 = 0; r1 < rsmax; r1++)
			for (int g1 = 0; g1 < rs[r1].maxp; g1++)
				if (rs[r1].on[g1].equalsIgnoreCase(p.getName())) {
					rs[r1].on[g1] = "";
					dprint.r.printAll(p.getName()
							+ " has been removed from skywar " + r1 + " = "
							+ rs[r1].getonnow(r1) + "/" + (rs[r1].maxp - 1));
				}

		// add player
		for (int g1 = 0; g1 < rs[id].maxp; g1++)
			if (rs[id].on[g1].equalsIgnoreCase("")) {
				rs[id].on[g1] = p.getName();
				dprint.r.printAll(p.getName() + " joined to skywar " + id
						+ " = " + rs[id].getonnow(id) + "/" + (rs[id].maxp - 1));

				// auto join
				for (Player plr : p.getWorld().getPlayers())
					if (plr.getInventory().first(83) > -1) join(id, plr);

				skywar_go();
				return id;
			}

		return -1;
	}

	@Override
	public void list(Block b, Player p) {
		int getid = getrsid(b);
		if (getid == -1) {
			p.sendMessage("don't have protect");
			return;
		}

		p.sendMessage("this id = " + getid + " maxp " + rs[getid].maxp);
		for (int i = 0; i < skywar_in.maxbuilder; i++) {
			if (rs[getid].builder[i].equalsIgnoreCase("")) continue;
			if (rs[getid].builder[i].equalsIgnoreCase("")) continue;

			p.sendMessage(rs[getid].builder[i]);
		}
	}

	@Override
	public void loadskywar() {
		String worldf = skywar_in.file_name;

		File dir = new File(skywar_in.folder_name_skywar);
		dir.mkdir();

		String filena = skywar_in.folder_name_skywar + File.separator + worldf;
		File fff = new File(filena);

		try {

			rs = new skywardata[100];
			for (int lop = 0; lop < 100; lop++) {
				rs[lop] = new skywardata();
				rs[lop].on = new String[rs[lop].maxp];

				for (int lop2 = 0; lop2 < rs[lop].maxp; lop2++)
					rs[lop].on[lop2] = "";

				rs[lop].builder = new String[skywar_in.maxbuilder];
				for (int lop2 = 0; lop2 < rs[lop].maxp; lop2++)
					rs[lop].builder[lop2] = "";

				rs[lop].on = new String[10];
				for (int lop2 = 0; lop2 < 10; lop2++)
					rs[lop].on[lop2] = "";

			}
			rsmax = 0;

			fff.createNewFile();

			dprint.r.printAll("ptdeW&DewDD skywar : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				// Print the content on the console
				rsmax++;
				rs[rsmax - 1].x1 = Integer.parseInt(m[0]);
				rs[rsmax - 1].y1 = Integer.parseInt(m[1]);
				rs[rsmax - 1].z1 = Integer.parseInt(m[2]);

				rs[rsmax - 1].x2 = Integer.parseInt(m[3]);
				rs[rsmax - 1].y2 = Integer.parseInt(m[4]);
				rs[rsmax - 1].z2 = Integer.parseInt(m[5]);

				rs[rsmax - 1].maxp = Integer.parseInt(m[6]);

				for (int r = 0; r < skywar_in.maxbuilder; r++)
					rs[rsmax - 1].builder[r] = "";
				for (int r = 7; r < m.length; r++)
					rs[rsmax - 1].builder[r - 7] = m[r];

			}

			dprint.r.printAll("ptdew&DewDD : " + tr.gettr("loaded_skywar_file")
					+ filena);
			autoaddname();

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + "  " + e.getMessage());
		}
	}

	@Override
	public boolean owner(int rsid, String name) {
		rs[rsid].builder[0] = name;
		dprint.r.printAll("reset owner of skywar id " + rsid + " = " + name);
		saveskywar();
		reload();
		return true;
	}

	@Override
	public void reload() {

		dprint.r.printAll("reloading...");
		loadskywar();
		dprint.r.printAll("reloaded skywar file");
	}

	@Override
	public boolean remove(Player p, int rsid, String name) {
		if (!p.hasPermission(skywar_in.poveride)) {

			if (!rs[rsid].builder[0].equalsIgnoreCase(p.getName())) {
				p.sendMessage("you are not owner of this skywar zone so can't removing member");
				return false;
			}
		}
		else if (!rs[rsid].builder[0].equalsIgnoreCase(p.getName()))
			p.sendMessage("overide this zone removing");

		if (name.equalsIgnoreCase(p.getName())) {
			dprint.r.printAll(p.getName()
					+ " try to remove himself from his skywar zone : O");
			return false;
		}

		for (int g1 = 0; g1 < skywar_in.maxbuilder; g1++)
			if (rs[rsid].builder[g1].equalsIgnoreCase(name)) {
				rs[rsid].builder[g1] = "";
				saveskywar();
				reload();
				dprint.r.printAll(p.getName() + " removed " + name
						+ " from skywar id " + rsid + " at slot " + g1);

				return true;
			}

		p.sendMessage("can't found that member to removing");
		return false;

	}

	@Override
	public void runnow(String pname) {
		// search player

		for (int e = 0; e < rsmax; e++)
			for (int f = 0; f < rs[e].maxp; f++)
				if (rs[e].on[f].equalsIgnoreCase(pname)) {

					dprint.r.printAll(tr.gettr("force_runnow_find_player")
							+ pname + " found at " + e);
					runnowid = e;

					skywar_go();
					return;
				}

	}

	@Override
	public void saveskywar() {

		File dir = new File(skywar_in.folder_name_skywar);
		dir.mkdir();

		String filena = skywar_in.folder_name_skywar + File.separator
				+ skywar_in.file_name;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd:Start saving skywar " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < rsmax; y++) {
				if (rs[y].maxp == 0) continue;

				String wr = "";
				wr = rs[y].x1 + " " + rs[y].y1 + " " + rs[y].z1 + " "
						+ rs[y].x2 + " " + rs[y].y2 + " " + rs[y].z2 + " "
						+ rs[y].maxp;

				for (int r = 0; r < skywar_in.maxbuilder; r++)
					wr = wr + " " + rs[y].builder[r];

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			dprint.r.printC("ptdew&dewdd:saved " + filena);
			return;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void setnametoid(int id, String name, Player p) {
		boolean found = false;
		String[] m;

		found = false;

		for (int j = 0; j < skywar_in.maxbuilder; j++) {
			m = rs[id].builder[j].split(":");

			if (m[0].equalsIgnoreCase("name")) {
				if (m.length != 2) continue;

				found = true;
				// if found name
				// set it

				rs[id].builder[j] = "name:" + name;
				p.sendMessage("edited skywar name "
						+ rs[id].builder[skywar_in.maxbuilder - 1]);
				saveskywar();
				return;
			}

		}

		if (found == false) {
			// not found

			rs[id].builder[skywar_in.maxbuilder - 1] = "name:" + name;
			p.sendMessage("set done skywar name "
					+ rs[id].builder[skywar_in.maxbuilder - 1]);
			saveskywar();
			return;
		}

		p.sendMessage("can't add");
		return;
	}

	@Override
	public void skywar_go() {
		skywar_go_c ax = new skywar_go_c();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ax);
	}
}

public class skywar implements Listener {

	class chatx implements Runnable {
		private String	message	= "";
		private Player	player	= null;

		public chatx(String message, Player player) {
			this.message = message;
			this.player = player;
		}

		@Override
		public void run() {
			String[] m = message.split("\\s+");

			if (m[0].equalsIgnoreCase("dewextend") == true
					|| message.equalsIgnoreCase("de") == true) {
				dew.dewextend(player);

				return;
			}

			if (m[0].equalsIgnoreCase("dewselectcube") == true)
				if (m.length == 1) {

					player.sendMessage("item on your hand = radius  = "
							+ player.getItemInHand().getAmount());
					dew.dewselectcube(player, player.getItemInHand()
							.getAmount());
					return;

				}
				else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("only number please");
						return;
					}

					player.sendMessage("argument 2 = radius  = " + m[1]);
					dew.dewselectcube(player, Integer.parseInt(m[1]));
					return;
				}

		}

	}

	class chatz extends Thread {
		private String	message	= "";
		private Player	player	= null;

		public chatz(String message, Player player) {
			this.message = message;
			this.player = player;
		}

		@Override
		public void run() {
			String[] m = message.split("\\s+");

			String h[] = new String[50];
			h[0] = "dewsetwall";
			h[1] = "dewfillwall";
			h[2] = "dsw";
			h[3] = "dfw";

			h[4] = "dewsetblock";
			h[5] = "dsb";
			h[6] = "dewfillblock";
			h[7] = "dfb";

			h[8] = "dewspreadq";
			h[9] = "dsq";

			h[10] = "dewdown";
			h[11] = "dn";
			h[12] = "down";

			h[13] = "dewsetroom";
			h[14] = "dewfillroom";
			h[15] = "dsr";
			h[16] = "dfr";

			h[17] = "dewspreadcircle";
			h[18] = "dsc";

			h[19] = "dewwallcircle";
			h[20] = "dwc";

			h[21] = "dewbreak";
			h[22] = "dewsetlight";
			h[23] = "dsl";

			h[24] = "dewdelete";
			h[25] = "dd";

			h[26] = "dewfill";
			h[27] = "df";

			h[28] = "dewsetcircle";
			h[29] = "dewfillcircle";
			h[30] = "dfc";

			for (String element : h)
				if (m[0].equalsIgnoreCase(element) == true) {
					if (m.length == 1)
						dew.runter(element, player, player.getItemInHand()
								.getTypeId(), player.getItemInHand().getData()
								.getData());
					else if (m.length == 2) {
						String[] m2 = m[1].split(":");

						int itemid = 0;
						byte dataid = 0;

						player.sendMessage("m2[0] = " + m2[0] + " search.. = "
								+ dew.getmaterialrealname(m2[0]));

						if (dew.isNumeric(m2[0]) == true) {
							if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
								player.sendMessage(tr
										.gettr("argument_1_what_the_hell_item"));
							else
								itemid = Material.getMaterial(
										Integer.parseInt(m2[0])).getId();
						}
						else if (Material.getMaterial(dew
								.getmaterialrealname(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							itemid = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();

						if (m2.length == 2) dataid = Byte.parseByte(m2[1]);

						player.sendMessage("itemid = " + itemid + ", dataid = "
								+ dataid);
						dew.runter(element, player, itemid, dataid);
					}

					return;
				}

			// dewset
			if (m[0].equalsIgnoreCase("dewset") == true
					|| m[0].equalsIgnoreCase("ds") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1)
					dew.dewset(player, -29, (byte) -29, player.getItemInHand()
							.getTypeId(), player.getItemInHand().getData()
							.getData(), false);
				else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
					}
					else if (Material.getMaterial(dew
							.getmaterialrealname(m2[0])) == null)
						player.sendMessage(tr
								.gettr("argument_1_what_the_hell_item"));
					else
						a3 = Material.getMaterial(
								dew.getmaterialrealname(m2[0])).getId();

					// data if 2
					if (m2.length == 2) a4 = Byte.parseByte(m2[1]);

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and "
							+ a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, false);
				}
				else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
					}
					else if (Material.getMaterial(dew
							.getmaterialrealname(m2[0])) == null)
						player.sendMessage(tr
								.gettr("argument_1_what_the_hell_item"));
					else
						a3 = Material.getMaterial(
								dew.getmaterialrealname(m2[0])).getId();

					// a2
					if (m2.length == 2) a4 = Byte.parseByte(m2[1]);

					// a3
					m2 = m[2].split(":");
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							a1 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
					}
					else if (Material.getMaterial(dew
							.getmaterialrealname(m2[0])) == null)
						player.sendMessage(tr
								.gettr("argument_1_what_the_hell_item"));
					else
						a1 = Material.getMaterial(
								dew.getmaterialrealname(m2[0])).getId();

					// a2
					if (m2.length == 2) a2 = Byte.parseByte(m2[1]);

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and "
							+ a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, false);
				}

				return;
			} // dewset

			// dewset 444 00 555 00
			if (m[0].equalsIgnoreCase("dewxet") == true
					|| m[0].equalsIgnoreCase("dx") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1)
					dew.dewset(player, -29, (byte) -29, player.getItemInHand()
							.getTypeId(), player.getItemInHand().getData()
							.getData(), true);
				else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
					}
					else if (Material.getMaterial(dew
							.getmaterialrealname(m2[0])) == null)
						player.sendMessage(tr
								.gettr("argument_1_what_the_hell_item"));
					else
						a3 = Material.getMaterial(
								dew.getmaterialrealname(m2[0])).getId();

					// data if 2
					if (m2.length == 2) a4 = Byte.parseByte(m2[1]);

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and "
							+ a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, true);
				}
				else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
					}
					else if (Material.getMaterial(dew
							.getmaterialrealname(m2[0])) == null)
						player.sendMessage(tr
								.gettr("argument_1_what_the_hell_item"));
					else
						a3 = Material.getMaterial(
								dew.getmaterialrealname(m2[0])).getId();

					// a2
					if (m2.length == 2) a4 = Byte.parseByte(m2[1]);

					// a3
					m2 = m[2].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null)
							player.sendMessage(tr
									.gettr("argument_1_what_the_hell_item"));
						else
							a1 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
					}
					else if (Material.getMaterial(dew
							.getmaterialrealname(m2[0])) == null)
						player.sendMessage(tr
								.gettr("argument_1_what_the_hell_item"));
					else
						a1 = Material.getMaterial(
								dew.getmaterialrealname(m2[0])).getId();

					// a2
					if (m2.length == 2) a2 = Byte.parseByte(m2[1]);

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and "
							+ a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, true);
				}

				return;
			} // dewset

			// dewdig
			if (message.equalsIgnoreCase("dewdddig") == true
					|| message.equalsIgnoreCase("ddd") == true) {

				dew.dewdig(player);

				return;
			}

			// dewcopy
			if (message.equalsIgnoreCase("dewcopy") == true
					|| message.equalsIgnoreCase("dc") == true) {
				dew.dewcopy(player);

				return;
			}

			// dewa
			if (m[0].equalsIgnoreCase("dewa") == true
					|| m[0].equalsIgnoreCase("da") == true) {

				int amo = 0;
				if (m.length == 1)
					dew.dewa(player, 0);
				else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("/dewa amount(it's not number T_T)");
						return;
					}

					amo = Integer.parseInt(m[1]);
					dew.dewa(player, amo);
				}

				return;
			}

			if (message.equalsIgnoreCase("dewsetprivate") == true
					|| message.equalsIgnoreCase("dsp") == true) {
				dew.dewsetprivate(player);

				return;
			}

		}
	}

	class commandrun implements Runnable {
		private String	message	= "";
		private Player	p;

		public commandrun(String message, Player p) {
			this.message = message;
			this.p = p;
		}

		@Override
		public void run() {

			String m[] = message.split("\\s++");

			if (p.getWorld().getName().equalsIgnoreCase("skywar")) {
				if (message.toLowerCase().startsWith("/l ") == false
						&& message.toLowerCase().startsWith("/login ") == false
						&& message.toLowerCase().startsWith("/authme ") == false
						&& message.toLowerCase().startsWith("/changepassword ") == false
						&& message.toLowerCase().startsWith("/register ") == false

				) dprint.r.printAll(p.getName() + " : " + message);

				for (World w : Bukkit.getWorlds())
					w.save();
			}

			chatz ar = new chatz(message.substring(1), p);
			ar.start();

			chatx ar2 = new chatx(message.substring(1), p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar2);

			if (m[0].equalsIgnoreCase("/skywar")) {

				if (p.getWorld().getName().equalsIgnoreCase(skywar_in.worldrun) == false) {
					p.sendMessage(tr.gettr("this_world_is_not_skywar"));
					return;
				}

				if (m.length == 1) {

					p.sendMessage("*** everyone ***");
					p.sendMessage("/skywar join <skywar's name>"); // dew.join
																	// id

					p.sendMessage("/skywar bp <skywar's name>"); // see blue
																	// print
					p.sendMessage("/skywar runnow <player's name>");
					p.sendMessage("/skywar autojoin");

					p.sendMessage("...");

					p.sendMessage("*** Builder ***");
					p.sendMessage("/skywar add <friend's name>");
					p.sendMessage("/skywar remove <friend's name>");
					p.sendMessage("/skywar list");
					p.sendMessage("/skywar setname <name of skywar>");
					p.sendMessage("/skywar myzone [name]");

					p.sendMessage("...");
					p.sendMessage("*** Admin ***");
					p.sendMessage("/skywar create <max player>"); // create
					p.sendMessage("/skywar delete"); // create
					p.sendMessage("/skywar reload");
					p.sendMessage("/skywar owner <player's name>");
					p.sendMessage("/skywar forceplay <player's name> <skywar's name>");

					return;
				} // 1
				else if (m[1].equalsIgnoreCase("myzone")) {
					if (!p.hasPermission(skywar_in.peditmember)) {
						p.sendMessage("need permission "
								+ skywar_in.peditmember);
						return;
					}
					String tname = "";
					if (m.length == 2) {
						tname = p.getName();
					}
					else if (m.length == 3) {
						tname = m[2];
					}

					for (int i = 0; i < dew.rsmax; i++)
						for (int j = 0; j < skywar_in.maxbuilder; j++)
							if (dew.rs[i].builder[j].equalsIgnoreCase(tname)) {

								p.sendMessage(">" + dew.getnamefromid(i));
								continue;

							}

				}
				else if (m[1].equalsIgnoreCase("forceplay")) {
					if (!p.hasPermission(skywar_in.pforceplay)) {
						p.sendMessage("need permission " + skywar_in.pforceplay);
						return;
					}
					if (m.length != 4) {
						p.sendMessage("/skywar forceplay <playername> <skywarname>");
						return;
					}

					int id = dew.getidfromname(m[3]);
					if (id == -1) {
						p.sendMessage("not found skywar name, try to force join to that integer id zone");

						dew.forceplay(p, m[2], Integer.parseInt(m[3]));

						return;
					}
					dew.forceplay(p, m[2], id);

				}
				else if (m[1].equalsIgnoreCase("runnow")) {
					if (!p.hasPermission(skywar_in.prunnow)) {
						p.sendMessage("need permission " + skywar_in.prunnow);
						return;
					}
					if (m.length == 2) {
						dew.runnow(p.getName());
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar runnow <playername>");
						return;
					}

					dew.runnow(m[2]);

				}
				else if (m[1].equalsIgnoreCase("cube")) {
					if (!p.hasPermission(skywar_in.pcreate)) {
						p.sendMessage("need permission " + skywar_in.pcreate);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar cube <radius>");
						return;
					}

					int ra = Integer.parseInt(m[2]);

					dew.x1 = (int) (p.getLocation().getX() - ra);
					dew.x2 = (int) (p.getLocation().getX() + ra);

					dew.y1 = (int) (p.getLocation().getY() - ra);
					dew.y2 = (int) (p.getLocation().getY() + ra);

					dew.z1 = (int) (p.getLocation().getZ() - ra);
					dew.z2 = (int) (p.getLocation().getZ() + ra);

					p.sendMessage("cube selected = " + dew.x1 + "," + dew.y1
							+ "," + dew.z1 + " to " + dew.x2 + "," + dew.y2
							+ "," + dew.z2);

					return;

				}

				else if (m[1].equalsIgnoreCase("autojoin")) {
					if (!p.hasPermission(skywar_in.pjoin)) {
						p.sendMessage("need permission " + skywar_in.pjoin);
						return;
					}

					p.setItemInHand(new ItemStack(83, 1));
				}
				else if (m[1].equalsIgnoreCase("force")) {
					if (!p.hasPermission(skywar_in.pjoin)) {
						p.sendMessage("need permission " + skywar_in.pjoin);
						return;
					}

					if (m.length != 4) {
						p.sendMessage("/skywar force <skywar name> <radius>");
						p.sendMessage("id =  0 to " + (dew.rsmax - 1));
						return;
					}
					else { // 4 arrguments

						dew.clean_offline();

						int id = dew.getidfromname(m[2]);
						if (id == -1) {
							p.sendMessage("not found skywar name");

							id = Integer.parseInt(m[2]);
							int rad = Integer.parseInt(m[3]);
							for (Player plr : p.getWorld().getPlayers())
								if (plr.getLocation().distance(p.getLocation()) < rad) {

									if (plr.getGameMode() != GameMode.SURVIVAL) {
										plr.sendMessage("you can't join skywar id "
												+ id + " cuz Creative Mode");
										continue;
									}
									dew.join(id, plr);
								}

							return;
						}

						int rad = Integer.parseInt(m[3]);
						for (Player plr : p.getWorld().getPlayers())
							if (plr.getLocation().distance(p.getLocation()) < rad) {

								if (plr.getGameMode() != GameMode.SURVIVAL) {
									plr.sendMessage("you can't join skywar id "
											+ id + " cuz Creative Mode");
									continue;
								}
								dew.join(id, plr);
							}

						return;

					}
				}
				else if (m[1].equalsIgnoreCase("max")) {
					// show all list
					dprint.r.printAll("skywar max = " + dew.rsmax);
					for (int i = 0; i < dew.rsmax; i++) {
						dprint.r.printAll("position = " + dew.rs[i].x1 + ","
								+ dew.rs[i].y1 + "," + dew.rs[i].z1 + " to "
								+ dew.rs[i].x2 + "," + dew.rs[i].y2 + ","
								+ dew.rs[i].z2 + " max player is "
								+ dew.rs[i].maxp + "  name: "
								+ dew.getnamefromid(i));

						for (int j = 0; j < skywar_in.maxbuilder; j++)
							if (dew.rs[i].builder[j].equalsIgnoreCase(""))
								continue;
					}

				}
				else if (m[1].equalsIgnoreCase("list"))
					// show all list
					dew.list(p.getLocation().getBlock(), p);
				else if (m[1].equalsIgnoreCase("reload")) {
					if (!p.hasPermission(skywar_in.preload)) {
						p.sendMessage("need permission " + skywar_in.preload);
						return;
					}

					dew.reload();

					return;
				}
				else if (m[1].equalsIgnoreCase("setname")) {
					if (!p.hasPermission(skywar_in.peditmember)) {
						p.sendMessage("need permission "
								+ skywar_in.peditmember);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar setname <skywar name>");
						return;
					}

					int id = dew.getrsid(p.getLocation().getBlock());
					if (id == -1) {
						p.sendMessage("this block is not any skywar zone");
						return;
					}

					if (dew.isplayerinrs(id, p.getName()) == -1) {

						p.sendMessage("this is not your skywar can't reset name of skywar");
						if (p.hasPermission(skywar_in.poveride))
							p.sendMessage("overide this zone");
						else
							return;
					}

					dew.setnametoid(id, m[2], p);
				}
				else if (m[1].equalsIgnoreCase("add")) {
					if (!p.hasPermission(skywar_in.peditmember)) {
						p.sendMessage("need permission "
								+ skywar_in.peditmember);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar add <name>");
						return;
					}

					int id = dew.getrsid(p.getLocation().getBlock());
					if (id == -1) {
						p.sendMessage("this block is not any skywar zone");
						return;
					}

					dew.add(p, id, m[2]);
				}
				else if (m[1].equalsIgnoreCase("remove")) {
					if (!p.hasPermission(skywar_in.peditmember)) {
						p.sendMessage("need permission "
								+ skywar_in.peditmember);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar remove <name>");
						return;
					}

					int id = dew.getrsid(p.getLocation().getBlock());
					if (id == -1) {
						p.sendMessage("this block is not any skywar zone");
						return;
					}

					dew.remove(p, id, m[2]);
				}
				else if (m[1].equalsIgnoreCase("owner")) {
					if (!p.hasPermission(skywar_in.powner)) {
						p.sendMessage("need permission " + skywar_in.powner);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar owner <name>");
						return;
					}

					int id = dew.getrsid(p.getLocation().getBlock());
					if (id == -1) {
						p.sendMessage("this block is not any skywar zone");
						return;
					}

					dew.owner(id, m[2]);
				}
				else if (m[1].equalsIgnoreCase("create")) {
					if (!p.hasPermission(skywar_in.pcreate)) {
						p.sendMessage("need permission " + skywar_in.pcreate);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar create <maxplayer>");
						return;
					}

					int id = 0;
					try {
						id = Integer.parseInt(m[2]);
						if (id < 1 || id >= 10) {
							p.sendMessage("out of range player must be 1 - 10");
							return;
						}
					}
					catch (NumberFormatException err) {
						p.sendMessage("Please enter only int number");
						return;
					}

					p.sendMessage("call create mothod " + id);
					dew.create(id, p);

				}
				else if (m[1].equalsIgnoreCase("delete")) {
					if (!p.hasPermission(skywar_in.pdelete)) {
						p.sendMessage("need permission " + skywar_in.pdelete);
						return;
					}

					int getj = dew.getrsid(p.getLocation().getBlock());
					if (getj == -1) {
						p.sendMessage("this area is not skywar zone");
						return;
					}

					dew.delete(getj);

				}
				else if (m[1].equalsIgnoreCase("join")) {
					if (!p.hasPermission(skywar_in.pjoin)) {
						p.sendMessage("need permission " + skywar_in.pjoin);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar join <skywar name>");
						p.sendMessage("id =  0 to " + (dew.rsmax - 1));
						return;
					}
					else { // 3 arrguments

						dew.clean_offline();
						int id = dew.getidfromname(m[2]);
						if (id == -1) {
							p.sendMessage("not found skywar name");
							return;
						}

						dew.join(id, p);

					}
				}
				else if (m[1].equalsIgnoreCase("bp")) {
					if (!p.hasPermission(skywar_in.pbp)) {
						p.sendMessage("need permission " + skywar_in.pbp);
						return;
					}

					if (m.length != 3) {
						p.sendMessage("/skywar bp <skywar name>");
						p.sendMessage("id =  0 to " + (dew.rsmax - 1));
						return;
					}
					else { // if 3 arguments
						int id = dew.getidfromname(m[2]);
						if (id == -1) {
							p.sendMessage("not found skywar name");
							dew.bp(Integer.parseInt(m[2]), p);

							return;
						}

						dew.bp(id, p);

					}
				}

			} // skywardn

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
					System.out
							.println("dew main waiting for create dewset sleeping ac +"
									+ i);

				}

				while (dew == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping dew +"
									+ i);

					dew = new Dewddminecraft();

				}

				while (dew.ac == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping dew ac +"
									+ i);

					dew.ac = ac;

				}
				// dew.loadmainfile();
				dew.loadskywar();

			}
			catch (InterruptedException e) {

				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	class resp implements Runnable {

		private Player	p;

		public resp(Player p) {
			this.p = p;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 100);
		}

		public void run() {
			for (int cl = 0; cl < p.getInventory().getSize(); cl++)
				p.getInventory().clear(cl);
			p.getInventory().setBoots(new ItemStack(0));
			p.getInventory().setChestplate(new ItemStack(0));
			p.getInventory().setHelmet(new ItemStack(0));
			p.getInventory().setLeggings(new ItemStack(0));

			dprint.r.printAll(tr.gettr("autorespawn_skywar") + p.getName());

			Bukkit.dispatchCommand(p, "warp skywar");
		}
	}

	class resplastplayer implements Runnable {

		private Player	p;

		public resplastplayer(Player p) {
			this.p = p;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 100);
		}

		public void run() {
			for (int cl = 0; cl < p.getInventory().getSize(); cl++)
				p.getInventory().clear(cl);
			p.getInventory().setBoots(new ItemStack(0));
			p.getInventory().setChestplate(new ItemStack(0));
			p.getInventory().setHelmet(new ItemStack(0));
			p.getInventory().setLeggings(new ItemStack(0));

			dprint.r.printAll(tr.gettr("lastplayer_teleperted_back_to_skywar")
					+ p.getName());

			Bukkit.dispatchCommand(p, "warp skywar");
		}
	}

	public JavaPlugin	ac	= null;

	Dewddminecraft		dew	= null;

	public skywar() {

		delay eee = new delay();
		eee.start();
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		chatz ar = new chatz(e.getMessage(), e.getPlayer());
		ar.start();

		chatx ar2 = new chatx(e.getMessage(), e.getPlayer());
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar2);

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		if (e.getPlayer().hasPermission(skywar_in.poveride)) return;
		// check protect
		if (!dew.canbuild(e.getBlock(), e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		if (e.getPlayer().hasPermission(skywar_in.poveride)) return;

		// check protect
		if (!dew.canbuild(e.getBlock(), e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void eventja(InventoryOpenEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		if (InventoryType.ENDER_CHEST == e.getInventory().getType()) {
			Player p = (Player) e.getPlayer();
			p.sendMessage(tr.gettr("skywar_don't_allow_ender_chest"));
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		commandrun arx = new commandrun(e.getMessage(), e.getPlayer());
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, arx);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventja(PlayerDeathEvent e) {

		Player p = e.getEntity().getPlayer();
		if (!p.getLocation().getWorld().getName().equalsIgnoreCase("skywar"))
			return;

		resp rr = new resp(p);
		if (e.getEntity().getKiller() != null) {
			if (e.getEntity().getKiller() instanceof Player) {
				resplastplayer rr2 = new resplastplayer(p);
			}
		}

	}

	@EventHandler
	public void eventja(PlayerInteractEvent e) {

		if (e.hasBlock() == false) return;

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		// check protect

		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (e.getAction().equals(Action.PHYSICAL))
			if (e.getClickedBlock().getTypeId() == 72
					|| e.getClickedBlock().getTypeId() == 70) {
				Sign sign = null;

				for (int rx = -1; rx <= 1; rx++)
					for (int ry = -1; ry <= 1; ry++)
						for (int rz = -1; rz <= 1; rz++) {
							b = p.getLocation().getBlock()
									.getRelative(rx, ry, rz);

							if (b.getTypeId() == 63 || b.getTypeId() == 68) {
								sign = (Sign) b.getState();

								if (sign.getLine(0)
										.equalsIgnoreCase("[skywar]"))
									if (sign.getLine(1)
											.equalsIgnoreCase("join"))
										dew.join(dew.getidfromname(sign
												.getLine(2)), p);
							}
						}

			}

		if (p.getItemInHand().getTypeId() == 19) {
			if (!p.hasPermission(skywar_in.pcreate)) {
				p.sendMessage("need permission " + skywar_in.pcreate);
				e.setCancelled(true);
				return;
			}

			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				dew.x1 = b.getX();
				dew.y1 = b.getY();
				dew.z1 = b.getZ();
				p.sendMessage("ptdew&dewdd : skywar set block 1 = " + dew.x1
						+ "," + dew.y1 + "," + dew.z1 + " to " + dew.x2 + ","
						+ dew.y2 + "," + dew.z2);
				return;
			}
			else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				dew.x2 = b.getX();
				dew.y2 = b.getY();
				dew.z2 = b.getZ();
				p.sendMessage("ptdew&dewdd : skywar set block 2 = " + dew.x1
						+ "," + dew.y1 + "," + dew.z1 + " to " + dew.x2 + ","
						+ dew.y2 + "," + dew.z2);
				e.setCancelled(true);
				return;
			}
		}

		if (p.getItemInHand().getTypeId() == 290
				&& e.getAction() == Action.LEFT_CLICK_BLOCK) {
			// set x1y1z1
			int getid = dew.getfreeselect(p);
			dew.selectx1[getid] = b.getX();
			dew.selecty1[getid] = b.getY();
			dew.selectz1[getid] = b.getZ();
			dew.selectworldname[getid] = b.getWorld().getName();

			int countblock = Math.abs(1 + dew.selectx1[getid]
					- dew.selectx2[getid])
					* Math.abs(1 + dew.selecty1[getid] - dew.selecty2[getid])
					* Math.abs(1 + dew.selectz1[getid] - dew.selectz2[getid]);

			p.sendMessage("ptdew&dewdd: Block 1 = (" + dew.selectx1[getid]
					+ "," + dew.selecty1[getid] + "," + dew.selectz1[getid]
					+ ") to (" + dew.selectx2[getid] + ","
					+ dew.selecty2[getid] + "," + dew.selectz2[getid] + ") = "
					+ countblock);
			e.setCancelled(true);
			return;
		}

		if (p.getItemInHand().getTypeId() == 290
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			// set x1y1z1

			int getid = dew.getfreeselect(p);
			dew.selectx2[getid] = b.getX();
			dew.selecty2[getid] = b.getY();
			dew.selectz2[getid] = b.getZ();
			dew.selectworldname[getid] = b.getWorld().getName();

			int countblock = Math.abs(1 + dew.selectx1[getid]
					- dew.selectx2[getid])
					* Math.abs(1 + dew.selecty1[getid] - dew.selecty2[getid])
					* Math.abs(1 + dew.selectz1[getid] - dew.selectz2[getid]);

			p.sendMessage("ptdew&dewdd: Block 2 = (" + dew.selectx1[getid]
					+ "," + dew.selecty1[getid] + "," + dew.selectz1[getid]
					+ ") to (" + dew.selectx2[getid] + ","
					+ dew.selecty2[getid] + "," + dew.selectz2[getid] + ") = "
					+ countblock);
			e.setCancelled(true);
		}

		if (p.getItemInHand().getTypeId() == 276 // diamondsword
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			// set x1y1z1

			int getid = dew.getfreeselect(p);
			dew.selectblock[getid] = b;
			dew.selectworldname[getid] = b.getWorld().getName();

			p.sendMessage("ptdew&dewdd: dew a selectblock with sword = block at ("
					+ dew.selectblock[getid].getX()
					+ ","
					+ dew.selectblock[getid].getY()
					+ ","
					+ dew.selectblock[getid].getZ() + ")");
			// event.setCancelled(true);
		}

		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getPlayer().hasPermission(skywar_in.poveride)) return;

			if (!dew.canbuild(e.getClickedBlock(), e.getPlayer()))
				e.setCancelled(true);

		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (b.getTypeId() == 63 || b.getTypeId() == 68) {
				Sign sign = (Sign) b.getState();
				if (sign.getLine(0).equalsIgnoreCase("[skywar]"))
					if (sign.getLine(1).equalsIgnoreCase("join"))
						dew.join(Integer.parseInt(sign.getLine(2)), p);
			}

			if (e.getPlayer().hasPermission(skywar_in.poveride)) return;

			if (!dew.canbuild(e.getClickedBlock(), e.getPlayer())
					&& !dew.canbuild(e.getClickedBlock(),
							skywar_in.flagrightclick)) e.setCancelled(true);

			if (b.getTypeId() == 63 || b.getTypeId() == 68) {
				Sign sign = (Sign) b.getState();

				if (sign.getLine(0).equalsIgnoreCase("[skywar]"))
					if (sign.getLine(1).equalsIgnoreCase("join"))
						dew.join(Integer.parseInt(sign.getLine(2)), p);
			}

		}

	}

	@EventHandler
	public void eventja(PlayerTeleportEvent e) {
		Player p = e.getPlayer();

		if (e.getFrom().getWorld().getName().equalsIgnoreCase("skywar")) {
			if (!e.getTo().getWorld().getName().equalsIgnoreCase("skywar")) {

				// clear item

				for (int cl = 0; cl < p.getInventory().getSize(); cl++)
					p.getInventory().clear(cl);
				p.getInventory().setBoots(new ItemStack(0));
				p.getInventory().setChestplate(new ItemStack(0));
				p.getInventory().setHelmet(new ItemStack(0));
				p.getInventory().setLeggings(new ItemStack(0));
				p.setGameMode(GameMode.SURVIVAL);

			}

		}
		else if (e.getTo().getWorld().getName().equalsIgnoreCase("skywar")) {
			// check item
			for (int cl = 0; cl < p.getInventory().getSize(); cl++)
				if (p.getInventory().getItem(cl) != null) {
					p.sendMessage(tr
							.gettr("can't_go_to_skywar_world_empty_your_inven"));
					e.setCancelled(true);
					return;
				}

			if (p.getInventory().getBoots() != null
					|| p.getInventory().getChestplate() != null
					|| p.getInventory().getHelmet() != null
					|| p.getInventory().getLeggings() != null

			) {
				p.sendMessage(tr
						.gettr("can't_go_to_skywar_world_empty_your_inven"));
				e.setCancelled(true);
				return;
			}
		}

	}

} // class

interface skywar_in {
	public String	file_name			= "ptdew_dewdd_skywar_location.txt";
	public String	flagrightclick		= "<right>";

	public String	folder_name_skywar	= "plugins" + File.separator
												+ "dewdd_skywar";

	public int		fongnam				= 19;
	public int		maxbuilder			= 10;
	public String	pbp					= "dewdd.skywar.bp";
	public String	pcreate				= "dewdd.skywar.create";
	public String	pdelete				= "dewdd.skywar.delete";

	public String	peditmember			= "dewdd.skywar.editmember";

	public String	pjoin				= "dewdd.skywar.join";
	public String	poveride			= "dewdd.skywar.overide";
	public String	powner				= "dewdd.skywar.owner";
	public String	preload				= "dewdd.skywar.reload";
	public String	prunnow				= "dewdd.skywar.runnow";
	public String	pforceplay			= "dewdd.skywar.forceplay";

	public String	worldrun			= "skywar";

	public boolean add(Player p, int rsid, String name);

	public void autoaddname();

	public void bp(int rsid, Player player);

	public boolean canbuild(Block block, Player p);

	public boolean canbuild(Block block, String p);

	public void clean_noplayer_skywar();

	public void clean_offline();

	public int create(int maxplayer, Player p); // return rs id

	public boolean delete(int rsid);

	public boolean forceplay(Player p, String target, int toid);

	public int getidfromname(String sgr);

	public String getnamefromid(int id);

	public int getrsid(Block block);

	public int isplayerinrs(int rsid, String name);

	public int join(int rsid, Player player); // return id of that player

	public void list(Block b, Player p);

	public void loadskywar();

	public boolean owner(int rsid, String name);

	public void reload(); // reload file just call loadskywar()

	public boolean remove(Player p, int rsid, String name);

	public void runnow(String pname);

	public void saveskywar();

	public void setnametoid(int id, String name, Player p);

	public void skywar_go();

}
