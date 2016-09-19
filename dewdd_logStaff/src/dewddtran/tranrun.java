/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtran;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class tranrun implements Listener {

	public JavaPlugin	ac	= null;
	Random				rnd	= new Random();

	@EventHandler
	public void eventja(AsyncPlayerChatEvent e) {
		if (tr.isThisStaff(e.getPlayer().getName()) == false) return;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		tr.logList.add(dateFormat.format(date) + " " + e.getPlayer().getName()
				+ " type command " + e.getMessage());

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		e.getPlayer();

		Player p = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/dlog")) {
			if (m.length == 1) {
				p.sendMessage("/dlog reload");
				p.sendMessage("/dlog save");

				return;
			}
			else if (m.length == 2) {

				if (m[1].equalsIgnoreCase("reload")) {
					tr.loadStaffFile();
				}
				if (m[1].equalsIgnoreCase("save")) {
					tr.saveLogFile();
				}

			}

		}

		if (tr.isThisStaff(e.getPlayer().getName()) == false) return;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		tr.logList.add(dateFormat.format(date) + " " + e.getPlayer().getName()
				+ " type command " + e.getMessage());

	}

	@EventHandler
	public void eventja(PlayerDropItemEvent e) {

		if (tr.isThisStaff(e.getPlayer().getName()) == false) return;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		tr.logList.add(dateFormat.format(date) + " " + e.getPlayer().getName()
				+ " dropItem at " + e.getPlayer().getWorld().getName() + "-"
				+ e.getPlayer().getLocation().getBlockX() + ","
				+ e.getPlayer().getLocation().getBlockY() + ","
				+ e.getPlayer().getLocation().getBlockZ() + " item "
				+ e.getItemDrop().getItemStack().getType().name() + " amount "
				+ e.getItemDrop().getItemStack().getAmount());

	}

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		if (tr.isThisStaff(e.getPlayer().getName()) == false) return;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		tr.logList.add(dateFormat.format(date) + " " + e.getPlayer().getName()
				+ " joining to server ");

		tr.saveLogFile();
	}

	@EventHandler
	public void eventja(PlayerQuitEvent e) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		tr.logList.add(dateFormat.format(date) + " " + e.getPlayer().getName()
				+ " quit " + e.getQuitMessage());

		tr.saveLogFile();
	}
} // dig