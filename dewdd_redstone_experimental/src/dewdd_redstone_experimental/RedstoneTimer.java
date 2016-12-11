package dewdd_redstone_experimental;

import org.bukkit.Bukkit;

public class RedstoneTimer implements Runnable {
	public static long	curNoRedstoneActivityInTick	= 0;
	public static long	curNothingBetterInTick		= 0;

	public RedstoneTimer() {

	}

	@Override
	public void run() {

		// if in setup mode
		if (DigEventListener2.redex.curMode == 0) {
			Redex.redstoneTimer = 0;
			RedstoneTimer.curNoRedstoneActivityInTick = 0;
			RedstoneTimer.curNothingBetterInTick = 0;
			return;
		}

		Redex.redstoneTimer++;
		// dprint.r.printAll("RedStone Time " + Redex.redstoneTimer);

		RedstoneTimer.curNoRedstoneActivityInTick++;
		RedstoneTimer.curNothingBetterInTick++;

		if (RedstoneTimer.curNoRedstoneActivityInTick > Redex.maxNoRedstoneActivityInTick) {
			RedstoneTimer.curNoRedstoneActivityInTick = 0;
			// recheck

			CheckNoRedstoneActivity caa = new CheckNoRedstoneActivity(
					DigEventListener2.redex);
			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					caa);

		}

		if (RedstoneTimer.curNothingBetterInTick > Redex.maxNothingBetterInTick) {
			RedstoneTimer.curNothingBetterInTick = 0;
			// recheck

			CheckNothingBetter caa = new CheckNothingBetter(
					DigEventListener2.redex);

			Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac,
					caa);

		}

	}
}