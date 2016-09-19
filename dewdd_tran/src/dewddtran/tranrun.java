/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtran;

import java.io.File;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class tranrun implements Listener {

	public JavaPlugin	ac	= null;
	Random				rnd	= new Random();

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/dtran")) {
			if (m.length == 1) {
				p.sendMessage("/dtran reload");
				p.sendMessage("/dtran list");
				p.sendMessage("/dtran runworld");
				p.sendMessage("/dtran offline <day>");
				p.sendMessage("" + tr.runworld.size());
				return;
			}

			if (m[1].equalsIgnoreCase("runworld")) {
				p.sendMessage(tr.gettr("listed_of_runworld"));

				for (int i = 0; i < tr.runworld.size(); i++) {
					p.sendMessage(dprint.r.color(tr.runworld.get(i).pluginname));

					for (int j = 0; j < tr.runworld.get(i).runworld.size(); j++) {
						p.sendMessage(dprint.r.color(" > "
								+ tr.runworld.get(i).runworld.get(j)));

					}

				}

			}
			else if (m[1].equalsIgnoreCase("reload")) {
				dprint.r.printAll("tran reloading file...");
				tr.loadtrfile();

				tr.loadrunworldfile();
				dprint.r.printAll("tran reloaded...");

			}
			else if (m[1].equalsIgnoreCase("list")) {
				for (int i = 0; i < tr.dtmax; i++) {
					dprint.r.printAll("tr " + i + " = " + tr.dt[i].s + " = "
							+ tr.dt[i].an);
				}
			}
			else if (m[1].equalsIgnoreCase("offline")) {
				if (m.length != 3) {
					p.sendMessage("/dtran offline <day>");
					return;
				}

				int day = Integer.parseInt(m[2]);

				File ff = new File(System.getProperty("user.dir")
						+ File.separator + "plugins" + File.separator
						+ "essentials" + File.separator + "userdata");

				String me[] = tr.getlastonline(day, ff.getAbsolutePath());
				p.sendMessage(ff.getAbsolutePath());

				if (me == null) {
					p.sendMessage(tr.gettr("list_is_null"));
					return;
				}

				dprint.r.printAll(tr
						.gettr("list_of_offline_players_too_long_time"));

				for (int i = 0; i < me.length; i++) {
					dprint.r.printAll(me[i]);
				}

				dprint.r.printAll(tr.gettr("amount_all_is") + me.length);

			}

		}

	}
} // dig