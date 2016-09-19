/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtps;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

public class tps implements Runnable {

	public static Random rnd = new Random();
	public static int maxtick = 600;

	public static int TICK_COUNT = 0;

	public static long[] TICKS = new long[maxtick];

	static long lastshowtps = 0;

	static long lasttps = 0;
	static long now = 0;

	static long temptps = 0;

	public static double getTPS() {
		return getTPS(100);
	}

	public static double getTPS(int ticks) {
		int cu = TICK_COUNT;
		int target = cu - 1;
		if (target == -1) {
			target = maxtick - 1;
		}

		int target2 = target - 2;
		if (target2 == -1) {
			target2 = maxtick - 1;
		}
		if (target2 == -2) {
			target2 = maxtick - 2;
		}
		int cu2 = 0;
		double plusall = 0;

		do {

			// count

			cu++;
			if (cu >= maxtick) {
				cu = 0;
			}

			cu2 = cu + 1;
			if (cu2 >= maxtick) {
				cu2 = 0;
			}

			plusall += (TICKS[cu2] - TICKS[cu]);

		} while (cu != target2);

		// 1000

		double re = (((maxtick) / (plusall / 1000)));

		// return re <= 5 ? rnd.nextInt(20) + 1 : re;
		return re <= 5 ? 20 : re;
	}

	JavaPlugin ac = null;

	@Override
	public void run() {
		now = System.currentTimeMillis();
		TICKS[TICK_COUNT] = now;

		TICK_COUNT++;
		if (TICK_COUNT >= maxtick) {
			TICK_COUNT = 0;
		}

		temptps = Math.round(getTPS());

		if (now - lastshowtps > 5000) { // 3 second
			if (temptps != lasttps) {
				lasttps = temptps;
				lastshowtps = now;

				/*
				 * for (Player p : Bukkit.getOnlinePlayers()) {
				 * p.sendMessage(dprint.r.color("tps = " + ((int)
				 * (Math.round(((100 * lasttps) / 20.1)))) + "%")); }
				 */

				System.out.println("tps = " + ((int) (Math.round(((100 * lasttps) / 20.1)))) + "%");
			}

		}

	}

} // dig