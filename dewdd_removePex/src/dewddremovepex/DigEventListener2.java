/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddremovepex;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	public static String perenchant = "dewdd.removepex.holdunsafeenchantment";
	

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
	
	

	class clearItem1000 implements Runnable {
		private Inventory inv;
		private Player player;

		public clearItem1000(Inventory inv,String player) {
			this.inv = inv;
			this.player = Bukkit.getPlayer(player);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 1);
		}

		@Override
		public void run() {

			if (inv == null) {
				return;
			}

			for (int i = 0; i < inv.getSize(); i++) {
				ItemStack itm = inv.getItem(i);
				if (itm == null) {

					continue;
				}

				// dprint.r.printAll("ii " + i);

				// Enchantment ench;
				boolean found = false;
				for (Enchantment ench : org.bukkit.enchantments.Enchantment.values()) {

					if (itm.getEnchantmentLevel(ench) > 30 || itm.getEnchantmentLevel(ench) < -30) {
						// dprint.r.printAll("fnoud true " + i);
						found = true;
						break;
					}
					
					if (itm.getEnchantmentLevel(ench) > 6 && player.hasPermission(perenchant) == false && itm.getType() != Material.CHEST) {
						found = true;
						dprint.r.printAll(player.getName() + tr.gettr("this_player_hold_unsafe_enchant_without_this_permission" + perenchant));
						break;
					}
				}

				if (found == true) {
					// dprint.r.printAll("remove " + i);
					inv.remove(itm);
				}
			}

			
		}

	}
	
	@EventHandler
	public void eventja(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		// if (inv.getType() != InventoryType.CREATIVE) {
		clearItem1000 xxx = new clearItem1000(inv,e.getWhoClicked().getName());
		// }
	}

	@EventHandler
	public void eventja(InventoryOpenEvent e) {
		Inventory inv = e.getInventory();
		// if (inv.getType() != InventoryType.CREATIVE) {
		clearItem1000 xxx = new clearItem1000(inv,e.getPlayer().getName());
		
		// }
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
						pr.setGameMode(GameMode.SURVIVAL);
						
						pr.kickPlayer(tr.gettr("banned cuz you can be game mode but not Admin Or GameMode Group"));
						//pr.setBanned(true);

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