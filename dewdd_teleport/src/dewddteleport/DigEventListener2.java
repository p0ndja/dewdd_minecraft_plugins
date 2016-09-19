/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddteleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddflower.dewset;
import dewddtran.tr;

public class DigEventListener2 implements Listener {
	dewset dew = null;
	
	class runp implements Runnable {
		private Player p;
		private String m[];

		public runp(String a[], Player p) {
			m = a;
			this.p = p;
		}

		@Override
		public void run() {
			if (m[0].equalsIgnoreCase("/dtp")) {
				if (m.length == 1) {
					p.sendMessage("/dtp <player>");
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

			if (m[0].equalsIgnoreCase("/dewsaveteleport")) {
				if (p.hasPermission(pbuildsign) == false) {
					p.sendMessage(dprint.r.color(tr.gettr("you don't have permission") + pbuildsign));
					return;
				}
				sx = p.getLocation().getBlockX();
				sy = p.getLocation().getBlockY();
				sz = p.getLocation().getBlockZ();
				sworld = p.getWorld();
				p.sendMessage(dprint.r.color(tr.gettr("save teleport completed")));
				return;
			}
			if (m[0].equalsIgnoreCase("/dewloadteleport")) {
				if (dew == null) {
					dew = new dewset();
				}
				if (p.hasPermission(pbuildsign) == false) {
					p.sendMessage(dprint.r.color(tr.gettr("you don't have permission") + pbuildsign));
					return;
				}

				if (sworld == null) {
					p.sendMessage(dprint.r.color(tr.gettr("you need to type /dewsaveteleport first")));
					return;
				}

				for (int i = 0; i >= -3; i--) {
					Block b = p.getLocation().getBlock().getRelative(0, i, 0);
					if (dew.cando_all(b, p, "build") == false) {
						p.sendMessage(dprint.r.color(tr.gettr("this zone is protected you can't build")));
						return;
					}
				}

				Block b = p.getLocation().getBlock().getRelative(0, -2, 0);
				b.setTypeId(68, false);

				Sign se = (Sign) b.getState();

				se.setLine(0, "[dewteleport]");
				se.setLine(1, "" + sx);
				se.setLine(2, "" + sy);
				se.setLine(3, "" + sz);
				se.update();

				b = p.getLocation().getBlock().getRelative(0, -3, 0);
				b.setTypeId(68, false);
				se = (Sign) b.getState();
				se.setLine(0, "[dewteleport2]");
				se.setLine(1, sworld.getName());
				se.update();

				dprint.r.printAll("ptdew&dewdd : load teleport completed");
				return;
			}
		}
	}

	JavaPlugin ac = null;

	String pbuildsign = "dewdd.teleport.buildsign";
	int sx = 0;
	int sy = 0;
	int sz = 0;

	World sworld = null;

	public void dewteleport(Player player) {
		Block block = player.getLocation().getBlock().getRelative(0, -2, 0);
		if (block.getTypeId() == 68 || block.getTypeId() == 68) {
			Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equalsIgnoreCase("dewteleport")) {
				sign.setLine(0, "[dewteleport]");
				sign.update(true);
			}

			if (sign.getLine(0).equalsIgnoreCase("[dewteleport]") == true) {

				int xh = Integer.parseInt(sign.getLine(1));
				int yh = Integer.parseInt(sign.getLine(2));
				int zh = Integer.parseInt(sign.getLine(3));

				Location loca = player.getLocation();
				loca.setX(xh);
				loca.setY(yh);
				loca.setZ(zh);
				World wname = loca.getWorld();

				Block block2 = player.getLocation().getBlock().getRelative(0, -3, 0);
				if (block2.getTypeId() == 68 || block2.getTypeId() == 68) {
					Sign sign2 = (Sign) block2.getState();
					if (sign2.getLine(0).equalsIgnoreCase("dewteleport2")) {
						sign2.setLine(0, "[dewteleport2]");
						sign2.update(true);
					}

					if (sign2.getLine(0).equalsIgnoreCase("[dewteleport2]") == true) {
						wname = Bukkit.getWorld(sign2.getLine(1));
					}
				}
				loca.setWorld(wname);

				boolean abc = player.teleport(loca);
				if (abc == true) {
					dprint.r.printAll("ptdew&dewdd: teleported '" + player.getName() + "' to " + wname.getName()
							+ " = (" + loca.getBlockX() + "," + loca.getBlockY() + "," + loca.getBlockZ() + ")");

				} else {
					dprint.r.printAll("ptdew&dewdd: error to '" + player.getName() + "' to " + wname.getName() + " = ("
							+ loca.getBlockX() + "," + loca.getBlockY() + "," + loca.getBlockZ() + ")");

				}

			}
		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		runp ar = new runp(m, player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, ar);

	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {

		Action act;
		act = event.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)) {
			return;
		}

		dewteleport(event.getPlayer());
	}

	@EventHandler
	public void eventja(PlayerMoveEvent event) {

		dewteleport(event.getPlayer());
	}

} // class
