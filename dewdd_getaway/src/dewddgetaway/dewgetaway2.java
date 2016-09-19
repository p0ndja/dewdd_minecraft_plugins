/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgetaway;

import java.util.Random;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class dewgetaway2 implements Listener {

	class chatx implements Runnable {
		Player	p		= null;
		String	message	= "";

		public void run() {
			String m[] = message.split("\\s+");

			if (m[0].equalsIgnoreCase("/dewgetawayrun")) {
				staticarea.dslb.loaddewsetlistblockfile();
				isruntick = !isruntick;
				dprint.r.printAll("isruntick = " + Boolean.toString(isruntick));
				getawayticktock time = new getawayticktock();
				time.setName("getaway");
				time.start();

				return;
			}

			if (m[0].equalsIgnoreCase("/dewgetaway")) {

				if (m.length == 1) { // it's mean toggle your self
					if (p.hasPermission(pgetaway) == false) {
						p.sendMessage("you don't have permission " + pgetaway);
						return;
					}

					int getid = getfreeselect(p.getName());
					nn.isrun[getid] = !nn.isrun[getid];
					p.sendMessage(nn.playername[getid] + " getaway mode = "
							+ Boolean.toString(nn.isrun[getid]));

				}
				else if (m.length == 2) { // it's mean have player name
					if (p.hasPermission(pgetaway) == false) {
						p.sendMessage("you don't have permission " + pgetaway);
						return;
					}

					if (m[1].equalsIgnoreCase(p.getName()) == false) {
						if (p.hasPermission(pgetawayother) == false) {
							p.sendMessage("you don't have permission "
									+ pgetaway);
							return;
						}
					}

					if (m[1].equalsIgnoreCase("@a") == true) {
						// it's mean toggle everyone
						for (Player p2 : Bukkit.getOnlinePlayers()) {
							int getid = getfreeselect(p2.getName());
							nn.isrun[getid] = !nn.isrun[getid];
							p.sendMessage(nn.playername[getid]
									+ " getaway mode = "
									+ Boolean.toString(nn.isrun[getid]));

						}
					}
					else {
						// find that player
						for (Player p2 : Bukkit.getOnlinePlayers()) {
							if (p2.getName().toLowerCase()
									.indexOf(m[1].toLowerCase()) > -1) {

								int getid = getfreeselect(p2.getName());
								nn.isrun[getid] = !nn.isrun[getid];
								p.sendMessage(nn.playername[getid]
										+ " getaway mode = "
										+ Boolean.toString(nn.isrun[getid]));
								break;

							}
						}
						return;
					}

				}
				else if (m.length == 3) {
					// it's mean player 0|1
					if (p.hasPermission(pgetaway) == false) {
						p.sendMessage("you don't have permission " + pgetaway);
						return;
					}

					if (m[1].equalsIgnoreCase(p.getName()) == false) {
						if (p.hasPermission(pgetawayother) == false) {
							p.sendMessage("you don't have permission "
									+ pgetaway);
							return;
						}
					}

					if (m[2].equalsIgnoreCase("0") == false
							&& m[2].equalsIgnoreCase("1") == false) {
						p.sendMessage("argument 3 must be 0 or 1");
						return;
					}

					boolean togglemode = false;
					if (m[2].equalsIgnoreCase("1") == true) togglemode = true;

					if (m[1].equalsIgnoreCase("@a") == true) {
						// it's mean toggle everyone
						for (Player p2 : Bukkit.getOnlinePlayers()) {
							int getid = getfreeselect(p2.getName());
							nn.isrun[getid] = togglemode;
							p.sendMessage(nn.playername[getid]
									+ " getaway mode = "
									+ Boolean.toString(nn.isrun[getid]));

						}
						return;
					}
					else {
						// find that player
						for (Player p2 : Bukkit.getOnlinePlayers()) {
							if (p2.getName().toLowerCase()
									.indexOf(m[1].toLowerCase()) > -1) {

								int getid = getfreeselect(p2.getName());
								nn.isrun[getid] = togglemode;
								p.sendMessage(nn.playername[getid]
										+ " getaway mode = "
										+ Boolean.toString(nn.isrun[getid]));
								break;
							}
						}
						return;
					}

				}

			}

		}
	}

	class ddata {

		public String	playername[];
		public boolean	isrun[];

	}

	class getawaytick2 implements Runnable {

		public void run() {

			long starttime = System.currentTimeMillis();
			long endtime = 0;
			// loop everyone
			// printAll("tick");

			for (int i = 0; i < ddatamax; i++) {
				if (nn.isrun[i] == true) {
					// printAll("found nn = true at " + i);

					if (nn.playername[i].equalsIgnoreCase("") == false) {
						// printAll(" nn name empty == false at " + i);

						// search that player
						Player p2 = null;

						for (Player p3 : Bukkit.getOnlinePlayers()) {
							if (p3.getName().equalsIgnoreCase(nn.playername[i])) {
								p2 = p3;
								break;
							}
						}

						if (p2 == null) {
							// printAll("p2 = null " + i);
							continue;
						}

						// printAll("foundn p2");

						double td = 0;
						Block b = null;
						Block b2 = null;

						for (int x = -ra; x <= ra; x++) {
							for (int y = -ra; y <= ra; y++) {
								for (int z = -ra; z <= ra; z++) {

									endtime = System.currentTimeMillis();
									if (endtime - starttime > 250) return;

									if (dewddtps.tps.getTPS() < 18) {

										return;
									}

									b = p2.getLocation().getBlock()
											.getRelative(x, y, z);
									// b2 is looking

									td = distance3d(b.getX(), b.getY(),
											b.getZ(),

											p2.getLocation().getBlockX(), p2
													.getLocation().getBlockY(),
											p2.getLocation().getBlockZ());

									// printAll("radi td " + td);
									if (td > ra) {

										continue;
									}

									// check this block
									// b =
									// p2.getLocation().getBlock().getRelative(x,y,z);

									boolean bll = blockdewset(b.getTypeId());
									if (bll == false) {
										continue;
									}
									// check sign
									for (int nx = -1; nx <= 1; nx++) {
										for (int ny = -1; ny <= 1; ny++) {
											for (int nz = -1; nz <= 1; nz++) {
												if (b.getRelative(nx, ny, nz)
														.getTypeId() == 0) {
													continue;
												}

												if (b.getRelative(nx, ny, nz)
														.getTypeId() == 63
														|| b.getRelative(nx,
																ny, nz)
																.getTypeId() == 68
														|| b.getRelative(nx,
																ny, nz)
																.getType()
																.isBlock() == false
														|| blockdewset(b
																.getRelative(
																		nx, ny,
																		nz)
																.getTypeId()) == false) {
													bll = false;
													if (bll == false) {
														break;
													}
												}

												if (bll == false) {
													break;
												}
											}

										}

										if (bll == false) {
											break;
										}
									}

									if (bll == false) {
										continue;
									}
									// printAll("adding " + b.getX() + "," +
									// b.getY() + "," + b.getZ());
									// move it
									b2 = getran(b, 1);
									saveb xx = new saveb();

									xx.b1id = b.getTypeId();
									xx.b1data = b.getData();
									xx.b1x = b.getX();
									xx.b1y = b.getY();
									xx.b1z = b.getZ();

									xx.b2id = b2.getTypeId();
									xx.b2data = b2.getData();
									xx.b2x = b2.getX();
									xx.b2y = b2.getY();
									xx.b2z = b2.getZ();

									xx.w = b.getWorld().getName();

									bd.push(xx);

									// added queue

									// switch that block
									b.setTypeId(xx.b2id);
									b.setData(xx.b2data);

									b2.setTypeId(xx.b1id);
									b2.setData(xx.b1data);

								}
							}
						}

						// if found player

						// search neary block at player it's sholud be move
						// or not

						// if yes add to queue

						// and loop again to should it's roll back or not
						// We should't use quere
						// We should use array

					}
				}

			}
			// after add quere

			dprint.r.printC("bd size = " + bd.size());

			for (int gx = 0; gx <= 300; gx++) {
				// this is rollback block
				if (bd.size() == 0) {
					bd.trimToSize();
					return;
				}

				endtime = System.currentTimeMillis();
				if (endtime - starttime > 250) return;
				if (dewddtps.tps.getTPS() < 18) {

					return;
				}

				// printC("before peek " + bd.size());
				saveb ttt = bd.peek();
				// printC("after peek " + bd.size());

				Block b3 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b1x, ttt.b1y,
						ttt.b1z);
				Block b4 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b2x, ttt.b2y,
						ttt.b2z);

				boolean isp = false;
				isp = isplayernearblock(b3, ra) || isplayernearblock(b4, ra);

				if (isp == true) {
					return;
				}

				ttt = bd.pop();

				b3 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b1x, ttt.b1y,
						ttt.b1z);
				b4 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b2x, ttt.b2y,
						ttt.b2z);

				b4.setTypeId(ttt.b2id);
				b4.setData(ttt.b2data);

				b3.setTypeId(ttt.b1id);
				b3.setData(ttt.b1data);

			}
			// if not player near rollback

			// check
			// is't there have player near that block ?

		}
	}

	class getawayticktock extends Thread {
		public void run() {

			while (isruntick == true) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				getawaytick2 eee = new getawaytick2();

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, eee);

			}
		}
	}

	class saveb {

		public int		b1id	= 0;
		public byte		b1data	= 0;
		public int		b1x		= 0;
		public int		b1y		= 0;
		public int		b1z		= 0;

		public int		b2id	= 0;
		public byte		b2data	= 0;
		public int		b2x		= 0;
		public int		b2y		= 0;
		public int		b2z		= 0;

		public String	w		= "";

	}

	boolean	isruntick	= false;

	JavaPlugin		ac				= null;

	int				ra				= 5;

	int				ddatamax		= 29;

	ddata			nn				= new ddata();
	String			pgetaway		= "dewdd.getaway.use";
	String			pgetawayother	= "dewdd.getaway.use.other";

	Stack<saveb>	bd				= new Stack<saveb>();
	// Queue<saveb> bd= new LinkedList<saveb>();

	Random			rnd				= new Random();

	public dewgetaway2() {
		nn.playername = new String[ddatamax];
		nn.isrun = new boolean[ddatamax];

		for (int i = 0; i < ddatamax; i++) {
			nn.playername[i] = "";
			nn.isrun[i] = false;

		}
	}

	public boolean blockdewset(int blockid) {
		return staticarea.dslb.isdewset(blockid)
				&& staticarea.dslb.isdewinventoryblock(blockid) == false
				&& blockid != 0 && blockid != 8 && blockid != 9
				&& blockid != 10 && blockid != 11;

	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	public double distance3d(int x1, int y1, int z1, int x2, int y2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(y1 - y2, 2);

		double t5 = Math.pow(t1 + t2 + t3, 0.5);
		return t5;
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		chatx a = new chatx();
		a.p = event.getPlayer();
		a.message = event.getMessage();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, a);
	}

	public int getfreeselect(String sname) {
		// clean exited player
		boolean foundx = false;
		for (int i = 0; i < ddatamax; i++) {

			foundx = false;

			for (Player pr : Bukkit.getOnlinePlayers()) {
				if (nn.playername[i].equalsIgnoreCase(pr.getName())) {
					foundx = true;
					break;
				}
			}

			if (foundx == false) {
				nn.playername[i] = "";
				nn.isrun[i] = false;
			}

		}
		// clean

		for (int i = 0; i < ddatamax; i++) {
			if (nn.playername[i].equalsIgnoreCase(sname)) {
				return i;
			}
		}

		for (int i = 0; i < ddatamax; i++) {
			if (nn.playername[i].equalsIgnoreCase("")) {
				nn.playername[i] = sname;
				return i;
			}
		}

		return -1;
	}

	public Block getran(Block b, int ra) {
		Block b2 = b;

		int tx = 0;
		int ty = 0;
		int tz = 0;

		int counttry = 0;

		do {
			counttry++;
			tx = rnd.nextInt(ra * 2) - (ra * 1);
			ty = rnd.nextInt(ra * 2) - (ra * 1);
			tz = rnd.nextInt(ra * 2) - (ra * 1);

			if (ty < 1) ty = 1;
			if (ty > 254) ty = 254;
			b2 = b.getRelative(tx, ty, tz);

			if (counttry >= 100) {
				counttry = 0;
				ra = ra + 1;
			}

		}
		while (b2.getLocation().distance(b.getLocation()) < ra || b2 == b
				|| b2.getTypeId() != 0);

		return b2;
	}

	public boolean isplayernearblock(Block bh, int ra) {

		for (Player uu : bh.getWorld().getPlayers()) {
			if (nn.isrun[getfreeselect(uu.getName())] == false) continue;

			if (uu.getLocation().distance(bh.getLocation()) <= ra) {
				return true;
			}
		}

		return false;
	}

} // class

