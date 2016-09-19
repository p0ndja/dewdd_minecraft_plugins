/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgetaway;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class dewddanimation implements Listener {

	class animation implements Runnable {

		public void run() {
			long starttime = System.currentTimeMillis();
			long endtime = 0;
			// loop everyone
			// dprint.r.printAll("tick");

			// how it's work
			// loop every block near bt as radius range

			Block b = null;
			Block b2 = null;
			// look near block

			int plustime = 0;
			int maxtime = 100;
			int doittime = 10;

			for (int protect = 0; protect < dani.animax; protect++) { // loop
																		// protect

				boolean isplayerthere = false;

				for (Player pp : Bukkit.getWorld(dani.worldname[protect])
						.getPlayers()) {
					if (distance3d(pp.getLocation().getBlockX(), pp
							.getLocation().getBlockY(), pp.getLocation()
							.getBlockZ(), dani.x[protect], dani.y[protect],
							dani.z[protect]) < dani.radius[protect]) {
						isplayerthere = true;
						break;
					}
				}

				if (isplayerthere == true || isruntick == false) { // if found
																	// player so
																	// rollback

					while (dani.bd[protect].size() > 0) {
						// dprint.r.printC("bd[" + protect + "] size = " +
						// dani.bd[protect].size());

						// rollback untill end of time
						endtime = System.currentTimeMillis();
						plustime += (endtime - starttime);
						if (plustime > maxtime) {
							break;
						}

						if (endtime - starttime > doittime) {
							starttime = System.currentTimeMillis();
							break;
						}
						else {
							starttime = System.currentTimeMillis();
						}
						// this is rollback block
						if (dani.bd[protect].size() == 0) {
							dani.bd[protect].size();
							dprint.r.printC("animation back completed ["
									+ protect + "]");
							// isruntick = false; // stoptime
							break;
						}

						Block b3 = null;
						Block b4 = null;
						saveb ttt = dani.bd[protect].pop();

						b3 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b1x,
								ttt.b1y, ttt.b1z);
						b4 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b2x,
								ttt.b2y, ttt.b2z);

						b4.setTypeId(ttt.b2id);
						b4.setData(ttt.b2data);

						b3.setTypeId(ttt.b1id);
						b3.setData(ttt.b1data);

						if (Bukkit.getOfflinePlayers().length == 0) {
							isruntick = false;
							dprint.r.printAll("animation stoped");
						}

					}

				}
				else { // so animation
					if (isruntick == true) { // go mode

						Block bt = Bukkit.getWorld(dani.worldname[protect])
								.getBlockAt(dani.x[protect], dani.y[protect],
										dani.z[protect]);

						for (int x = -dani.radius[protect]; x <= dani.radius[protect]; x++) {
							for (int y = -dani.radius[protect]; y <= dani.radius[protect]; y++) {
								for (int z = -dani.radius[protect]; z <= dani.radius[protect]; z++) {

									endtime = System.currentTimeMillis();

									plustime += (endtime - starttime);
									if (plustime > maxtime && isruntick == true) {
										// dprint.r.printAll("add block = " +
										// dani.bd[protect].size() + " pt = " +
										// plustime);
										break;
									}

									if (endtime - starttime > doittime) {
										starttime = System.currentTimeMillis();
										break;
									}
									else {
										starttime = System.currentTimeMillis();
									}

									b = bt.getRelative(x, y, z);
									if (b.getLocation().distance(
											bt.getLocation()) > dani.radius[protect]) {
										continue;
									}

									if (b.getTypeId() == 0) {
										continue;
									}

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

									// move it
									b2 = getran(b, bt);
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

									dani.bd[protect].push(xx);

									// added queue

									// switch that block

									if (b2.getLocation().distance(
											bt.getLocation()) >= 2) {
										b.setTypeId(xx.b2id);
										b.setData(xx.b2data);

										b2.setTypeId(xx.b1id);
										b2.setData(xx.b1data);
									}
									else {
										b.setTypeId(0);
										b2.setTypeId(0);
									}

									// this block need to move
									// so we need to give a way

								} // z
								if (endtime - starttime > doittime) {
									starttime = System.currentTimeMillis();
									break;
								}
								if (plustime > maxtime && isruntick == true) {
									// dprint.r.printAll("add block = " +
									// dani.bd[protect].size() + " pt = " +
									// plustime);
									break;
								}
							} // y
							if (endtime - starttime > doittime) {
								starttime = System.currentTimeMillis();
								break;
							}
							if (plustime > maxtime && isruntick == true) {
								// dprint.r.printAll("add block = " +
								// dani.bd[protect].size() + " pt = " +
								// plustime);
								break;
							}
						} // x

					} // time

					if (plustime > maxtime && isruntick == true) {
						// dprint.r.printAll("add block = " +
						// dani.bd[protect].size() +
						// " pt = " + plustime);
						continue;
					}
				} // animation

			}// protect
		}
	}

	class animationticktock extends Thread {
		public void run() {

			boolean countdown = false;
			for (int ee = 0; ee < dani.animax; ee++) {
				if (dani.bd[ee].size() > 0) {
					countdown = true;
					break;
				}
			}

			while (isruntick == true || countdown == true) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				animation eee = new animation();

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, eee);

			}
		}
	}

	class chatx implements Runnable {
		Player	p		= null;
		String	message	= "";

		public void run() {
			String m[] = message.split("\\s+");

			if (m[0].equalsIgnoreCase("/dewanimation")) {
				if (p.hasPermission(panimation) == false) {
					p.sendMessage("you don't have permission " + panimation);
					return;
				}

				isruntick = !isruntick;
				if (isruntick == true) {
					loadanimationfile();
				}
				animationticktock aen = new animationticktock();
				aen.start();
				dprint.r.printAll("dewdd animation running mode = "
						+ Boolean.toString(isruntick));
			}

		}
	} // chatx

	class danimation {
		public Stack<saveb>	bd[]		= new Stack[bdmax];
		public String		worldname[]	= new String[bdmax];
		public int			x[]			= new int[bdmax];
		public int			y[]			= new int[bdmax];
		public int			z[]			= new int[bdmax];
		public int			radius[]	= new int[bdmax];
		public int			animax		= 0;
	}

	/*
	 * worldname x y z radius
	 */

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

	int	bdmax	= 50;

	danimation	dani	= new danimation();

	boolean	isruntick	= false;

	String	panimation	= "dewdd.getaway.toggle.animation";

	JavaPlugin	ac	= null;

	Random		rnd	= new Random();

	public dewddanimation() {
		for (int ix = 0; ix < bdmax; ix++) {
			dani.bd[ix] = new Stack<saveb>();
		}
		dani.animax = 0;
		loadanimationfile();
	}

	public boolean blockdewset(int blockid) {
		return staticarea.dslb.isdewset(blockid)
				&& staticarea.dslb.isdewinventoryblock(blockid) == false
				&& blockid != 0 && blockid != 8 && blockid != 9
				&& blockid != 10 && blockid != 11;

	}

	// Queue<saveb> bd= new LinkedList<saveb>();

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

	public Block getran(Block b, Block bt) {
		Block b2 = b;

		double oldfar = b.getLocation().distance(bt.getLocation());
		int ra = 1;

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
		while (!(b2.getLocation().distance(b.getLocation()) < oldfar
				|| b2 == bt || b2.getLocation().distance(b.getLocation()) <= 1));

		return b2;
	}

	public boolean loadanimationfile() {
		try { // try

			// Open the file that is the first
			// command line parameter
			String filena = "ptdew_dewdd_dewanimation.txt";

			dprint.r.printC("ptdew&dewdd: Starting Loading " + filena);

			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			dani.animax = 0;

			String m[] = null;

			while ((strLine = br.readLine()) != null) {
				m = strLine.split("\\s+");
				if (m.length != 5) {
					dprint.r.printAll("error loading animation file   we need 5 argument per line");
					return false;
				}

				dani.animax++;
				dani.worldname[dani.animax - 1] = m[0];
				dani.x[dani.animax - 1] = Integer.parseInt(m[1]);
				dani.y[dani.animax - 1] = Integer.parseInt(m[2]);
				dani.z[dani.animax - 1] = Integer.parseInt(m[3]);
				dani.radius[dani.animax - 1] = Integer.parseInt(m[4]);
				dprint.r.printAll("load id =  " + (dani.animax - 1) + "> "
						+ m[0] + " " + m[1] + " " + m[2] + " " + m[3] + " "
						+ m[4]);
			}

			dprint.r.printC("ptdew&dewdd: loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Load dew animation error : " + e.getMessage());
			return false;
		} // catch

		return true;

	}

} // class

