/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddremovepex;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class DigEventListener2 implements Listener {

	class delay extends Thread {

		@Override
		public void run() {
			while (ac == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dprint.r.printAll("ft waiting ac != null");

			}
			
			RemovePex a = new RemovePex();

			Bukkit.getScheduler().cancelTasks(ac);
			Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, a, 0,1200);
			
		}
	}

	class delay_dewset extends Thread {

		@Override
		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out.println("dew ft watiing +" + i);

				}


			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	class RemovePex implements Runnable {
		
		public RemovePex() {
			
		}

		@Override
		public void run() {

			for (Player pr : Bukkit.getOnlinePlayers()) {
				if (pr.getGameMode() == GameMode.CREATIVE) {
					if (!pr.hasPermission("essentials.gamemode")) {
						pr.kickPlayer(tr.gettr("banned cuz you can be game mode but not Admin Or GameMode Group"));
						pr.setBanned(true);

						continue;
					}
				}

				if (pr.isFlying() && pr.getGameMode() != GameMode.SPECTATOR) {
					if (!pr.hasPermission("essentials.fly")) {
						int moneyNeed = (int) tr.gettrint("CONFIG_FLY_WITHOUT_PERMISSION_PRICE_USE_EACH_MINUTES");

						try {
							if (Economy.hasEnough(pr.getName(), moneyNeed)) {
								Economy.subtract(pr.getName(), moneyNeed);
							} else {
								pr.setFlying(false);
								pr.setAllowFlight(false);
								// pr.kickPlayer(tr.gettr("kick_cuz can fly but
								// not vip"));
								// continue;
							}
						} catch (UserDoesNotExistException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoLoanPermittedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

				

			}
		}
	}

	public JavaPlugin ac = null;

	public Random rnd = new Random();

	public DigEventListener2() {
		delay abc = new delay();
		abc.start();

		delay_dewset abc2 = new delay_dewset();
		abc2.start();

	}

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		
		Player player = e.getPlayer();

		if (!player.hasPermission("essentials.god")) {
			ac.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god " + player.getName() + " 0");
		}
	}

}