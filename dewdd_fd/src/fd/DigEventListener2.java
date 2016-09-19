/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package fd;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DigEventListener2 implements Listener {

	class autofix implements Runnable {
		private int	oldamount;
		private int	amount;

		public autofix(int amount) {
			this.amount = amount;
			oldamount = amount;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this,
					rnd.nextInt(20) + 1);
		}

		@Override
		public void run() {
			for (Player p : Bukkit.getOnlinePlayers()) {

				for (ItemStack itm : p.getPlayer().getInventory()
						.getArmorContents()) {
					if (itm == null) {
						continue;
					}
					if (itm.getType().getMaxDurability() <= 0) {
						continue;
					}

					for (int gx = 1; gx <= amount; gx++)
						if (itm.getDurability() == 0) {
							break;
						}
						else {
							itm.setDurability((short) (itm.getDurability() - 1));
							amount--;
							break;
						}

					itm.setDurability((short) 0);

				}

				for (ItemStack itm : p.getPlayer().getInventory().getContents()) {
					if (itm == null) {
						continue;
					}
					if (itm.getType().getMaxDurability() <= 0) {
						continue;
					}

					for (int gx = 1; gx <= amount; gx++)
						if (itm.getDurability() == 0) {
							break;
						}
						else {
							itm.setDurability((short) (itm.getDurability() - 1));
							amount--;
							break;
						}
				}
			}

			if (amount > 0 && oldamount != amount) {
				new autofix(amount);
			}
		}
	}
	class cut implements Runnable {
		private int		x, y, z, lx, ly, lz, mx, my, mz;
		private int		id;
		private byte	data;

		public cut(int id, byte data, int x, int y, int z, int lx, int ly,
				int lz, int mx, int my, int mz) {

			this.id = id;
			this.data = data;
			this.x = x;
			this.y = y;
			this.z = z;
			this.lx = lx;
			this.ly = ly;
			this.lz = lz;
			this.mx = mx;
			this.my = my;
			this.mz = mz;

		}

		public void run() {
			World w = Bukkit.getWorld("world");
			Block b;

			int count = 0;

			while (x <= mx) {
				while (y <= my) {

					while (z <= mz) {
						b = w.getBlockAt(x, y, z);
						if (b.getTypeId() == id && b.getData() == data) {
							b.breakNaturally();
							count++;

							if (count > 10) {
								cut aa = new cut(id, data, x, y, z, lx, ly, lz,
										mx, my, mz);
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										ac, aa, 20);
								return;
							}
						}

						z++;
					}
					z = lz;

					y++;
				}
				y = ly;

				x++;
			}

			return;

		}
	}

	class delay extends Thread {

		@Override
		public void run() {
			while (ac == null) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			reloadpl();
		}
	}

	class giveft implements Runnable {
		private String	give	= "";
		private int		amount	= 0;

		public giveft(String give, int amount) {
			this.give = give;
			this.amount = amount;
		}

		@Override
		public void run() {
			if (give.equalsIgnoreCase("fix")) {
				new autofix(amount);
			}

		}
	}

	class runp implements Runnable {
		private Player	p;
		private String	m[];

		public runp(String a[], Player p) {
			m = a;
			this.p = p;
		}

		public void run() {
			if (m[0].equalsIgnoreCase("dtp")) {
				if (m.length == 1) {
					p.sendMessage("dtp <player>");
					return;
				}

				// teleport
				Player pla = Bukkit.getPlayer(m[1]);
				if (pla == null) {
					p.sendMessage("not found player");
					return;
				}

				p.teleport(pla.getLocation());
				p.sendMessage("teleported to " + pla.getName());
				return;

			}
			else if (m[0].equalsIgnoreCase("cut")) {
				int id = -1;
				byte data = -1;
				if (m.length == 1) {
					// item in hand
					if (p.getItemInHand() == null) {
						return;
					}

					id = p.getItemInHand().getTypeId();
					data = p.getItemInHand().getData().getData();

				}
				else if (m.length == 3) {
					id = Integer.parseInt(m[1]);
					data = Byte.parseByte(m[2]);
				}

				int x, y, z, lx, ly, lz, mx, my, mz;
				x = p.getLocation().getBlockX();
				y = p.getLocation().getBlockY();
				z = p.getLocation().getBlockZ();

				int dx = 25;

				lx = x - dx;
				ly = y - dx;
				lz = z - dx;
				x = lx;
				y = ly;
				z = lz;
				mx = x + dx;
				my = y + dx;
				mz = z + dx;

				if (ly < 1) ly = 1;
				if (ly > 253) ly = 253;
				if (my > 253) my = 253;
				if (my < 1) my = 1;

				cut aa = new cut(id, data, x, y, z, lx, ly, lz, mx, my, mz);
				Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aa, 20);

				p.sendMessage("cut program started " + id + ":" + data);

			}

		}
	}

	Random				rnd	= new Random();

	public JavaPlugin	ac	= null;

	Location			lo;

	public DigEventListener2() {
		delay x = new delay();
		x.start();
	}

	@EventHandler
	public synchronized void eventja(AsyncPlayerChatEvent e) {

		Player player = e.getPlayer();

		String[] eo = e.getMessage().split("\\s+");

		if (e.getMessage().equalsIgnoreCase("digx") == true) {
			PotionEffect p = PotionEffectType.FAST_DIGGING.createEffect(10000,
					100);
			player.addPotionEffect(p, true);
		}
		if (e.getMessage().equalsIgnoreCase("digy") == true) {
			PotionEffect p = PotionEffectType.FAST_DIGGING.createEffect(1, 100);
			player.addPotionEffect(p, true);
		}

		System.gc();

		String m[] = e.getMessage().split("\\s+");

		runp ar = new runp(m, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (e.getBlock().getTypeId() == 52) e.setCancelled(true);
	}

	@EventHandler
	public void eventja(ItemDespawnEvent e) {
		lo = Bukkit.getWorld("world").getBlockAt(279, 90, 51).getLocation();
		e.getEntity().teleport(lo);
		e.setCancelled(true);
	}

	public void reloadpl() {
		giveft a = new giveft("fix", 30);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, a, 1, 1200);
	}

} // class

