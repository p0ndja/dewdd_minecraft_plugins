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

public class dewtransformer implements Listener {

	class chatx implements Runnable {
		Player	p		= null;
		String	message	= "";

		public void run() {
			String m[] = message.split("\\s+");

			if (m[0].equalsIgnoreCase("/dewtransformerrun")) {
				staticarea.dslb.loaddewsetlistblockfile();
				isruntick = !isruntick;
				dprint.r.printAll("isruntick = " + Boolean.toString(isruntick));
				transformerticktock time = new transformerticktock();
				time.setName("transformer");
				time.start();

				return;
			}

			// /dewtransformer radius go|back
			if (m[0].equalsIgnoreCase("/dewtransformer")) {

				if (m.length == 1) { // it's mean toggle your self
					if (p.hasPermission(ptransformer) == false) {
						p.sendMessage("you don't have permission "
								+ ptransformer);
						return;
					}

					bt = p.getLocation().getBlock();
					istransformer = !istransformer;
					p.sendMessage("radius = " + radius + " transformer mode = "
							+ Boolean.toString(istransformer));

				}
				else if (m.length == 2) { // it's mean have player name
					if (p.hasPermission(ptransformer) == false) {
						p.sendMessage("you don't have permission "
								+ ptransformer);
						return;
					}

					bt = p.getLocation().getBlock();
					radius = Integer.parseInt(m[1]);

					istransformer = !istransformer;

					if (istransformer == true) {
						p.sendMessage("radius = " + radius
								+ " transformer mode = "
								+ Boolean.toString(istransformer));
					}
					else {
						radius = bd.size() / radius;

						if (radius < 50) radius = 50;
						p.sendMessage("rollback blocks = " + radius
								+ " in 1 second ,transformer mode = "
								+ Boolean.toString(istransformer));

					}
					return;

				}
				else if (m.length == 3) { // it's mean have player name
					if (p.hasPermission(ptransformer) == false) {
						p.sendMessage("you don't have permission "
								+ ptransformer);
						return;
					}

					bt = p.getLocation().getBlock();
					radius = Integer.parseInt(m[1]);

					istransformer = Integer.parseInt(m[2]) == 1;

					if (istransformer == true) {
						p.sendMessage("radius = " + radius
								+ " transformer mode = "
								+ Boolean.toString(istransformer));
					}
					else {
						radius = bd.size() / radius;

						if (radius < 50) radius = 50;
						p.sendMessage("rollback blocks = " + radius
								+ " in 1 second ,transformer mode = "
								+ Boolean.toString(istransformer));

					}
					return;

				}

			}

		}
	} // chatx
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
	class transformer implements Runnable {

		public void run() {
			long starttime = System.currentTimeMillis();
			long endtime = 0;
			// loop everyone
			// dprint.r.printAll("tick");

			// how it's work
			// loop every block near bt as radius range

			if (bt == null) {
				dprint.r.printAll("bt = null  , transformer paused");
				isruntick = false;
				return;
			}

			if (radius <= 0) {
				dprint.r.printAll("radius <= 0  can't run");
				isruntick = false;
				return;
			}

			if (istransformer == true) { // go mode
				Block b = null;
				Block b2 = null;
				// look near block

				for (int x = -radius; x <= radius; x++) {
					for (int y = -radius; y <= radius; y++) {
						for (int z = -radius; z <= radius; z++) {

							endtime = System.currentTimeMillis();
							if (endtime - starttime > 250) {
								return;
							}

							if (dewddtps.tps.getTPS() < 18) {

								return;
							}

							b = bt.getRelative(x, y, z);
							if (b.getLocation().distance(bt.getLocation()) > radius) {
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
												|| b.getRelative(nx, ny, nz)
														.getTypeId() == 68
												|| b.getRelative(nx, ny, nz)
														.getType().isBlock() == false
												|| blockdewset(b.getRelative(
														nx, ny, nz).getTypeId()) == false) {
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
							b2 = getran(b);
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

							// this block need to move
							// so we need to give a way

						} // z
					} // y
				} // x

			}// is transformer
			else {

				dprint.r.printC("bd size = " + bd.size());

				for (int gx = 0; gx <= radius; gx++) {
					// this is rollback block
					if (bd.size() == 0) {
						bd.trimToSize();
						dprint.r.printC("transformer back completed ");
						isruntick = false;
						return;
					}

					endtime = System.currentTimeMillis();
					if (endtime - starttime > 250) {
						return;
					}
					if (dewddtps.tps.getTPS() < 18) {

						return;
					}

					Block b3 = null;
					Block b4 = null;
					saveb ttt = bd.pop();

					b3 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b1x, ttt.b1y,
							ttt.b1z);
					b4 = Bukkit.getWorld(ttt.w).getBlockAt(ttt.b2x, ttt.b2y,
							ttt.b2z);

					b4.setTypeId(ttt.b2id);
					b4.setData(ttt.b2data);

					b3.setTypeId(ttt.b1id);
					b3.setData(ttt.b1data);

				}

			} // is transformer
		}
	}
	class transformerticktock extends Thread {
		public void run() {

			while (isruntick == true) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				transformer eee = new transformer();

				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, eee);

			}
		}
	}

	boolean	isruntick		= false;

	boolean	istransformer	= false;

	int		radius			= 0;

	Block	bt				= null;

	JavaPlugin		ac				= null;

	String			ptransformer	= "dewdd.getaway.transformer";

	Stack<saveb>	bd				= new Stack<saveb>();
	// Queue<saveb> bd= new LinkedList<saveb>();

	Random			rnd				= new Random();

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

	public Block getran(Block b) {
		Block b2 = b;

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
		while (b2.getLocation().distance(bt.getLocation()) < ra || b2 == b
				|| b2.getTypeId() != 0);

		return b2;
	}

} // class

