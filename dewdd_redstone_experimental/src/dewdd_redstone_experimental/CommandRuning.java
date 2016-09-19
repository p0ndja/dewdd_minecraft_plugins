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
					p.sendMessage("/redex activate");
					p.sendMessage("/redex fitness");

				} else if (m.length >= 2) {

					// if (rene)
					dprint.r.printAll("command run redex null == " + (redex == null ? "yes" : "no") + " ");

					if (m[1].equalsIgnoreCase("start")) {

						// start process
					
						redex.CleanAllArea();

					} else if (m[1].equalsIgnoreCase("clean")) {
						if (m.length == 2) {

							// start process
							
							redex.CleanAllArea();
						} else if (m.length == 3) {
							
							CleanSubArea cc = new CleanSubArea(redex, Integer.parseInt(m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);
						}

					} else if (m[1].equalsIgnoreCase("decode")) {
						if (m.length == 2) {
							// start process
							
							redex.DecodeAllArea();
						} else if (m.length == 3) {
							
							DecodeSubDNA cc = new DecodeSubDNA(redex, Integer.parseInt(m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);

						}
					}
					else if (m[1].equalsIgnoreCase("activate")) {
						if (m.length == 2) {
							// start process
							
							redex.ActivateAllArea();
						} else if (m.length == 3) {
							
							int curid = Integer.parseInt(m[2]);
							ActivateSubArea cc = new ActivateSubArea(redex, curid );
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);
							
							dprint.r.printAll("activate " + curid + " = " + redex.listEx.get(curid).score);
						}
					}
					else if (m[1].equalsIgnoreCase("fitness")) {
						if (m.length == 2) {
							// start process
							
							redex.FitnessAllArea();
						} else if (m.length == 3) {
							
							int curid = Integer.parseInt(m[2]);
							FitnessSubArea cc = new FitnessSubArea(redex, curid );
							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, cc);
							
							dprint.r.printAll("Fitness " + curid + " = " + redex.listEx.get(curid).score);
						}
					}

				}

			}
		}

	}

}