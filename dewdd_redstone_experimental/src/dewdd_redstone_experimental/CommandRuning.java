package dewdd_redstone_experimental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dewddtran.tr;
import li.LXRXLZRZType;

public class CommandRuning implements Runnable {
	private String	m[];
	private Player	p;
	private Redex	redex;

	public CommandRuning(String m[], Player p, Redex redex) {
		this.m = m;
		this.p = p;
		this.redex = redex;

	}

	@Override
	public void run() {
		if (this.m[0].equalsIgnoreCase("/redex")) {
			if (this.p.hasPermission(Redex.predex) == false) {
				this.p.sendMessage(
						tr.gettr("you don't have permission" + Redex.predex));
				return;
			}
			else {
				if (this.m.length == 1) {
					this.p.sendMessage("/redex start");
					this.p.sendMessage("/redex clean");
					this.p.sendMessage("/redex decode");
					this.p.sendMessage("/redex activate");
					this.p.sendMessage("/redex fitness");

				}
				else if (this.m.length >= 2) {

					// if (rene)
					dprint.r.printAll("command run redex null == "
							+ (this.redex == null ? "yes" : "no") + " ");

					if (this.m[1].equalsIgnoreCase("start")) {

						// start process

						this.redex.CleanAllArea(true);

					}
					else if (this.m[1].equalsIgnoreCase("clean")) {
						if (this.m.length == 2) {

							// start process

							this.redex.CleanAllArea(false);
						}
						else if (this.m.length == 3) {

							CleanSubArea cc = new CleanSubArea(this.redex,
									Integer.parseInt(this.m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									DigEventListener2.ac, cc);
						}

					}
					else if (this.m[1].equalsIgnoreCase("decode")) {
						if (this.m.length == 2) {
							// start process

							this.redex.DecodeAllArea(false);
						}
						else if (this.m.length == 3) {

							DecodeSubDNA cc = new DecodeSubDNA(this.redex,
									Integer.parseInt(this.m[2]));
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									DigEventListener2.ac, cc);

						}
					}
					else if (this.m[1].equalsIgnoreCase("activate")) {
						if (this.m.length == 2) {
							// start process

							this.redex.ActivateAllArea(false);
						}
						else if (this.m.length == 3) {

							int curid = Integer.parseInt(this.m[2]);
							ActivateSubArea cc = new ActivateSubArea(this.redex,
									curid);
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									DigEventListener2.ac, cc);

							dprint.r.printAll("activate " + curid + " = "
									+ this.redex.listEx.get(curid).score);
						}
					}
					else if (this.m[1].equalsIgnoreCase("fitness")) {
						if (this.m.length == 2) {
							// start process

							this.redex.FitnessAllArea();
						}
						else if (this.m.length == 3) {

							int curid = Integer.parseInt(this.m[2]);
							FitnessSubArea cc = new FitnessSubArea(this.redex,
									curid);
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									DigEventListener2.ac, cc);

							dprint.r.printAll("Fitness " + curid + " = "
									+ this.redex.listEx.get(curid).score);
						}
					}
					else if (this.m[1].equalsIgnoreCase("space")) {
						
							LXRXLZRZType all = new LXRXLZRZType(0, 0, 0, 100, 256, 100);
							LXRXLZRZType cur = new LXRXLZRZType(0, 0, 0, 0, 0, 0);
							CleanSpace cs = new CleanSpace(redex, all, cur);

							Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
									cs, 1);
						
					}

				}

			}
		}

	}

}