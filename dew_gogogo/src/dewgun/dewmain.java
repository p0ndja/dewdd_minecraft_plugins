/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewgun;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class dewmain implements Listener {
	class chatx implements Runnable {
		public Boolean	canc	= false;

		private String	message	= "";

		private Player	player	= null;

		public chatx(String message, Player player) {
			this.message = message;
			this.player = player;
			// Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			String[] m = message.split("\\s+");
			m = message.split("\\s+");

			if (m[0].equalsIgnoreCase("sethome")) {
				player.setBedSpawnLocation(player.getLocation(), true);
				player.sendMessage("set bed location complete...");
				return;
			}

			if (m[0].equalsIgnoreCase("fly")) {
				if (m[1].equalsIgnoreCase("true")) {
					player.setAllowFlight(true);
					return;
				}
				else {
					player.setAllowFlight(false);
					return;
				}

			}

			if (m[0].equalsIgnoreCase("1")) {

				player.setAllowFlight(true);
				return;
			}

			if (m[0].equalsIgnoreCase("0")) {

				player.setAllowFlight(false);
				return;
			}

			if (m[0].equalsIgnoreCase("random")) {
				player.teleport(player.getLocation().getBlock()
						.getRelative(200, 0, 200).getLocation());

				return;
			}

			if (m[0].equalsIgnoreCase("del***")) {

				for (int x = -496; x <= 496; x += 16) {
					for (int z = -496; z <= 496; z += 16) {
						Block b = player.getWorld().getBlockAt(x, 255, z);
						// b.setTypeId(0);
					}
				}
			}

			if (m[0].equalsIgnoreCase("delwater")) {

				for (int x = -100; x <= 100; x += 1) {
					for (int z = -100; z <= 100; z += 1) {
						for (int y = -100; y <= 100; y += 1) {
							Block b = player.getWorld().getBlockAt(
									player.getLocation().getBlockX() + x, y,

									player.getLocation().getBlockZ() + z);
							if (b.getTypeId() == 8 || b.getTypeId() == 9) {
								b.setType(Material.AIR);
							}
						}
					}
				}
			}

			if (m[0].equalsIgnoreCase("setspawn")) {
				player.getWorld().setSpawnLocation(
						player.getLocation().getBlockX(),
						player.getLocation().getBlockY(),
						player.getLocation().getBlockZ());
				player.sendMessage("set spawn location complete...");
				return;
			}

			if (m[0].equalsIgnoreCase("home")) {
				player.teleport(player.getBedSpawnLocation());
				player.sendMessage("teleported to home complete...");
				return;

			}

			if (m[0].equalsIgnoreCase("dig")) {
				player.addPotionEffect(PotionEffectType.FAST_DIGGING
						.createEffect(5000, 100));
				player.sendMessage("dig like a boss...");
				return;

			}

			if (m[0].equalsIgnoreCase("tp")) {

				player.teleport(Bukkit.getPlayer(m[1]));
				player.sendMessage("teleported to player..");
				return;

			}

			if (m[0].equalsIgnoreCase("givex")) {
				int id = 0;
				int data = -1;
				if (m.length == 2) {
					id = Integer.parseInt(m[1]);
					data = -1;
				}
				else if (m.length == 3) {
					id = Integer.parseInt(m[1]);
					data = Integer.parseInt(m[2]);
				}

				dgive dd = new dgive(player.getLocation().getBlock(), player,
						id, data);
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, dd);

			}

			if (m[0].equalsIgnoreCase("dg")) {
				for (Entity en : player.getWorld().getEntities()) {
					if (en.getType() == EntityType.DROPPED_ITEM) {

						en.teleport(player.getLocation());
					}

				}
			}

			if (m[0].equalsIgnoreCase("dood")) {

				if (m.length == 1) {
					for (EntityType en : EntityType.values()) {
						if (en.isAlive()) {

							player.sendMessage(en.name());
						}

					}

					return;
				}
				if (m.length == 2) {
					EntityType enn = EntityType.fromName(m[1]);

					for (Entity en2 : player.getWorld().getEntities()) {
						if (en2.getType() == enn) {
							en2.teleport(player.getLocation());
						}

					}
				}

			}

		}
	}

	class delayed implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(60000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dtorch dd = new dtorch();
			Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, dd, 0, 60);
		}
	}
	class dgive implements Runnable {
		private Block	b		= null;
		private Player	p		= null;
		private int		id		= 0;
		private int		data	= 0;

		public dgive(Block b2, Player p, int id, int data) {
			// TODO Auto-generated constructor stub
			this.b = b2;
			this.p = p;
			this.id = id;
			this.data = data;
		}

		@Override
		public void run() {

			la = System.currentTimeMillis();

			Block b2 = p.getLocation().getBlock();
			Block b3 = b2;

			for (int x = -16; x <= 16; x++) {

				for (int y = -80; y <= 30; y++) {

					for (int z = -16; z <= 16; z++) {

						b3 = b2.getRelative(x, y, z);

						if (b3.getTypeId() == id) {
							// p.sendMessage("give " + id + ":" + data +
							// ","+ b3.getData());

							if (data == -1 || data == b3.getData()) {
								// p.sendMessage("seem data");

								// teleport block
								Block b4 = b3;
								Boolean found = false;

								for (int x2 = -5; x2 <= 5; x2++) {
									for (int y2 = 5; y2 >= -5; y2--) {
										for (int z2 = -5; z2 <= 5; z2++) {
											b4 = b2.getRelative(x2, y2, z2);
											if (b4.getTypeId() == 0) {
												b4.setTypeId(b3.getTypeId());
												b4.setData(b3.getData());
												b3.setTypeId(0);

												p.sendMessage(b4.getX() + ","
														+ b4.getY() + ","
														+ b4.getZ());

												found = true;
												break;
											}

											if (found == true) {
												break;
											}
										}
										if (found == true) {
											break;
										}
									}
									if (found == true) {
										break;
									}
								}

							} // data -i

						}

					}

				}
			}

			// } // loop

		}
	}

	class dtorch implements Runnable {
		public dtorch() {
			// TODO Auto-generated constructor stub

		}

		@Override
		public void run() {

			// Bukkit.broadcastMessage("" + System.currentTimeMillis());

			// while (true) {
			if (System.currentTimeMillis() - la < 3000) {

				return;
			}

			la = System.currentTimeMillis();

			boolean done = false;

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (done == true) break;

				Block b2 = p.getLocation().getBlock();
				Block b3 = b2;

				for (int x = -2; x <= 2; x++) {
					if (done == true) break;
					for (int y = -2; y <= 2; y++) {
						if (done == true) break;
						for (int z = -2; z <= 2; z++) {

							if (done == true) break;

							b3 = b2.getRelative(x, y, z);

							if (b3.getLightLevel() < 7) {
								if (b3.getTypeId() == 0) {

									if (b3.getRelative(0, -1, 0).getTypeId() > 0) {

										b3.setTypeId(50);
										done = true;
										break;
									}

								}
							}

						}

					}
				}

			} // player

			// } // loop

		}
	}

	class runwork implements Runnable {

		@Override
		public void run() {
			// dewddtps.tps.getTPS() < 17

		}

	}

	class scatterb implements Runnable {

		@Override
		public void run() {
			// search all player

			if (System.currentTimeMillis() - lastactive <= 60000) {
				return;
			}

			lastactive = System.currentTimeMillis();

			for (Player p : Bukkit.getWorld("world").getPlayers()) {

				for (int lx = -100; lx <= 100; lx += 16)
					for (int lz = -100; lz <= 100; lz += 16) { // lx

						// scan that 255 is bed rock or not

						int st = 1600;

						int curx = 0;
						for (int x = -st; x <= st; x += 16) {
							if (p.getLocation().getBlockX() + lx <= x) {
								curx = x;
								break;
							}
						}

						int curz = 0;
						for (int x = -st; x <= st; x += 16) {
							if (p.getLocation().getBlockZ() + lz <= x) {
								curz = x;
								break;
							}
						}

						if (isrunyet(curx, curz) == true) {
							continue;
						}
						printa("cur " + curx + "," + curz);
						p.sendMessage("cur " + curx + "," + curz);
						// need to scatter map

						Block b1 = p.getLocation().getBlock();
						Block b2 = p.getLocation().getBlock();

						boolean found1 = true;

						// scan all blocks

						int sec = 20;

						for (int y2 = 254; y2 >= 0; y2--) {

							/*
							 * scatterbthread dd = new scatterbthread(); dd.curx
							 * = curx; dd.curz = curz; dd.p = p; dd.y2 = y2;
							 */

							Block bx = p.getWorld().getBlockAt(curx, y2, curz);

							queue.add(bx);

							/*
							 * Bukkit.getScheduler().scheduleSyncDelayedTask(ac,
							 * dd, sec); sec += 1;
							 */

							// p.sendMessage("y " + y2);
							// printa("y " + y2);

						} // y2

						// Block b4 = p.getWorld().getBlockAt(curx, 255, curz);
						// b4.setTypeId(7);
					} // lx lz

			} // p

		} // player

	} // lx

	class scatterbthread implements Runnable {

		public int		curx	= 0;
		public int		curz	= 0;
		public Player	p		= null;
		public int		y2		= 0;
		private int		st3		= 1;

		@Override
		public void run() {

			Block b1 = null;
			Block b2 = null;
			boolean found1;

			lastactive = System.currentTimeMillis();

			for (int x2 = curx; x2 <= curx + 15; x2++) {
				for (int z2 = curz; z2 <= curz + 15; z2++) {

					b1 = p.getWorld().getBlockAt(x2, y2, z2);

					// check that block is spbox or not
					if (b1.getTypeId() == 0) {
						continue;
					}

					if (spbox(b1.getTypeId()) == true) {
						continue;
					}

					found1 = false;

					for (int x3 = -st3; x3 <= st3; x3++) {
						for (int y3 = -st3; y3 <= st3; y3++) {
							for (int z3 = -st3; z3 <= st3; z3++) {
								b2 = b1.getRelative(x3, y3, z3);
								if (spbox(b2.getTypeId()) == true) {
									found1 = true;
									break;
								}

							}

							if (found1 == true) break;
						}

						if (found1 == true) break;
					}

					if (found1 == true) continue;

					// time to scatter
					boolean found2 = false;
					Block b3 = b2;
					Block b4 = b2;

					int errRate = 0;
					int inc = 16;

					while (found2 == false) {

						errRate++;

						if (errRate > 100) {
							errRate = 0;
							inc += 4;

						}

						int z4 = curz + inc - rnd.nextInt(inc * 2);
						int y4 = rnd.nextInt(80);
						int x4 = curx + inc - rnd.nextInt(inc * 2);

						while (x4 % 4 > 0) {
							x4 = curx + inc - rnd.nextInt(inc * 2);
						}
						while (z4 % 4 > 0) {
							z4 = curz + inc - rnd.nextInt(inc * 2);
						}
						while (y4 % 4 > 0) {
							y4 = 20 + rnd.nextInt(80);
						}

						b3 = p.getWorld().getBlockAt(x4, y4, z4);

						// check that block is spbox or not
						if (b3.getTypeId() != 0) {
							continue;
						}

						Boolean found3 = false;

						for (int x3 = -st3; x3 <= st3; x3++) {
							for (int y3 = -st3; y3 <= st3; y3++) {
								for (int z3 = -st3; z3 <= st3; z3++) {
									b4 = b3.getRelative(x3, y3, z3);
									if (spbox(b4.getTypeId()) == true) {
										found3 = true;
										break;
									}

								}

								if (found3 == true) break;
							}

							if (found3 == true) break;
						}

						if (found3 == true) continue;

						// found space warp them.
						boolean nopls = false;

						for (Entity en : p.getWorld().getEntities()) {
							if (en.getType().isAlive() == true) continue;

							if (b1.getLocation().distance(en.getLocation()) <= 5) {
								nopls = true;
								break;
							}
						}

						if (nopls == true) {
							found2 = true;
							continue;
						}

						if (b1.getTypeId() == 7 || b1.getTypeId() == 1
								|| b1.getTypeId() == 3 || b1.getTypeId() == 2) {
							if (rnd.nextDouble() >= 0.00000001) {
								p.getWorld().getBlockAt(b1.getLocation())
										.setType(Material.AIR);
							}
							else {
								b3.setTypeId(b1.getTypeId());
								b3.setData(b1.getData());

								p.getWorld().getBlockAt(b1.getLocation())
										.setType(Material.AIR);
							}
						}
						else {
							b3.setTypeId(b1.getTypeId());
							b3.setData(b1.getData());

							p.getWorld().getBlockAt(b1.getLocation())
									.setType(Material.AIR);
						}

						/*
						 * p.sendMessage(b1.getLocation().getBlockX () + "," +
						 * b1.getLocation().getBlockY() + "," +
						 * b1.getLocation().getBlockZ() + " > " +
						 * b3.getLocation().getBlockX() + "," +
						 * b3.getLocation().getBlockY() + "," +
						 * b3.getLocation().getBlockZ() );
						 */
						found2 = true;

					}

					// mark spacial block this chunk done
				}
			}

			if (y2 == 0) {
				Block b4 = p.getWorld().getBlockAt(curx, 255, curz);
				b4.setTypeId(7);
			}
		}
	}

	Queue<Block>	queue		= new LinkedList<Block>();

	long			lastactive	= 0;

	JavaPlugin		ac			= null;

	Random			rnd			= new Random();

	long			la			= 0;

	public dewmain() {
		Thread xx = new Thread(new delayed());
		xx.start();
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent e) {

		e.getMessage();

		chatx ab = new chatx(e.getMessage(), e.getPlayer());
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ab, 1);

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {

		if (rnd.nextInt(100) <= 20) {
			scatterb dd = new scatterb();
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, dd, 1);
		}

	}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {

		if (rnd.nextInt(100) <= 20) {
			scatterb dd = new scatterb();
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, dd, 1);
		}

	}

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		e.getPlayer().sendMessage("�Թ�յ�͹�Ѻ����Կ  dewdd.no-ip.org");
		e.getPlayer()
				.sendMessage("��Ἱ��� ������  http://dewdd.no-ip.org:27100");
		e.getPlayer().sendMessage("...");

		e.getPlayer().sendMessage("�ٴ���  sethome    ,  home   , tp <player>");
		e.getPlayer().sendMessage("...  ����ͧ�����  / ");

	}

	@EventHandler
	public void eventja(PlayerMoveEvent e) {

		if (rnd.nextInt(100) <= 10) {
			scatterb dd = new scatterb();
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, dd, 1);
		}

	}

	public boolean isrunyet(int x, int z) {
		Block b = Bukkit.getWorld("world").getBlockAt(x, 255, z);
		if (b.getTypeId() == 7) {
			return true;
		}

		return false;
	}

	public void printa(String str) {
		System.out.println(str);
	}

	public boolean spbox(int x) {
		switch (x) {

		case 6:
		case 8:
		case 9:
		case 10:
		case 11:
		case 18:
		case 23:
		case 25:
		case 26:
		case 161:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 37:
		case 38:
		case 39:
		case 40:
		case 44:
		case 47:
		case 46:
		case 50:
		case 51:
		case 52:
		case 54:
		case 55:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 75:
		case 48:
		case 76:
		case 77:
		case 81:
		case 83:
		case 84:
		case 85:
		case 92:
		case 93:
		case 94:
		case 96:
		case 99:
		case 100:
		case 101:
		case 102:
		case 104:
		case 105:
		case 107:
		case 106:
		case 108:
		case 109:
		case 110:
		case 111:
		case 113:
		case 115:
		case 116:
		case 117:
		case 118:
		case 119:
		case 120:
		case 121:
		case 122:
		case 123:
		case 124:
		case 126:
		case 127:
		case 128:
		case 130:
		case 131:
		case 132:
		case 134:
		case 135:
		case 136:
		case 137:
		case 138:
		case 139:
		case 140:
		case 141:
		case 142:
		case 143:
		case 144:
		case 145:
		case 146:
		case 147:
		case 148:
		case 149:
		case 150:
		case 151:
		case 152:
		case 154:
		case 157:
		case 158:
		case 171:
		case 175:
			return true;
		}

		return false;
	}

} // dig