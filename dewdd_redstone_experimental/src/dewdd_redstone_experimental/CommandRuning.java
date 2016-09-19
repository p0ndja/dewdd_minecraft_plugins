package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dewddtran.tr;

public class CommandRuning implements Runnable {
	private String m[];
	private Player p;
	private Redex redex;

	public CommandRuning(String m[], Player p, Redex redex) {
		this.m = m;
		this.p = p;
		this.redex = redex;

	}

	@Override
	public void run() {
		if (m[0].equalsIgnoreCase("/redex")) {
			if (p.hasPermission(Redex.predex) == false) {
				p.sendMessage(tr.gettr("you don't have permission" + Redex.predex));
				return;
			} else {
				if (m.length == 1) {
					p.sendMessage("/redex start");
					p.sendMessage("/redex clean");
					p.sendMessage("/redex decode");

				} else if (m.length >= 2) {

					// if (rene)
					dprint.r.printAll("command run redex null == " + (redex == null ? "yes" : "no") + " ");

					if (m[1].equalsIgnoreCase("start")) {

						// start process
						redex = new Redex(p.getWorld(), p);
						redex.CleanAllArea();

					} else if (m[1].equalsIgnoreCase("clean")) {
						if (m.length == 2) {

							// start process
							redex = new Redex(p.getWorld(), p);
							redex.CleanAllArea();
						} else if (m.length == 3) {
							redex = new Redex(p.getWorld(), p);
							CleanSubArea cc = new CleanSubArea(redex, Integer.parseInt(m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);
						}

					} else if (m[1].equalsIgnoreCase("decode")) {
						if (m.length == 2) {
							// start process
							redex = new Redex(p.getWorld(), p);
							redex.DecodeAllArea();
						} else if (m.length == 3) {
							redex = new Redex(p.getWorld(), p);
							DecodeSubDNA cc = new DecodeSubDNA(redex, Integer.parseInt(m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);

						}
					}

				}

			}
		}

	}

}