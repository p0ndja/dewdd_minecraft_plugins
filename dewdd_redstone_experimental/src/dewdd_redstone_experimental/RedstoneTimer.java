package dewdd_redstone_experimental;

import org.bukkit.Bukkit;

public class RedstoneTimer implements Runnable {
	public static long curNoRedstoneActivityInTick = 0;
	public static long curNothingBetterInTick = 0;

	public RedstoneTimer() {

	}

	@Override
	public void run() {
		Redex.redstoneTimer++;
		// dprint.r.printAll("RedStone Time " + Redex.redstoneTimer);

		curNoRedstoneActivityInTick++;
		curNothingBetterInTick++;

		if (curNoRedstoneActivityInTick > Redex.maxNoRedstoneActivityInTick) {
			curNoRedstoneActivityInTick = 0;
			// recheck

			CheckNoRedstoneActivity caa = new CheckNoRedstoneActivity(DigEventListener2.redex);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa);

		}

		if (curNothingBetterInTick > Redex.maxNothingBetterInTick) {
			curNothingBetterInTick = 0;
			// recheck

			CheckNothingBetter caa = new CheckNothingBetter(DigEventListener2.redex);

			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, caa);

		}

	}
}