/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddpl;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	JavaPlugin	ac			= null;
	String		pclear		= "dewdd.pl.clear";
	String		ptogglepl	= "dewdd.pl.toggle";

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event)
			throws UnknownDependencyException, InvalidPluginException,
			InvalidDescriptionException {
		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd pl");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd pl") == true) {
			player.sendMessage("ปลั๊กอิน pl สามารถปิดและเปิดปลั๊กอินได้    คาดว่า จะสามารถอัพเดตปลั๊กอินอื่นได้โดยไม่ต้องรีเซิฟ");
			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("ppl") == true) {
			player.sendMessage("clear = "
					+ Boolean.toString(player.hasPermission(pclear)));
			player.sendMessage("togglepl = "
					+ Boolean.toString(player.hasPermission(ptogglepl)));

		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event)
			throws UnknownDependencyException, InvalidPluginException,
			InvalidDescriptionException {
		Player player = event.getPlayer();
		String[] m = event.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/dewpl") == true) {
			if (m.length == 1) {
				for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
					dprint.r.printAll(pl.getName() + " | " + pl.isEnabled());
				}
			}
			else if (m.length == 3) {
				if (player.hasPermission(ptogglepl) == false) {
					player.sendMessage("you don't have permission for use 'toggle pl'");
					return;
				}

				if (m[1].equalsIgnoreCase("d") && m[2].equalsIgnoreCase("all")) {
					if (player.hasPermission(pclear) == false) {
						player.sendMessage("you don't have permission for use 'pl d all'");
						return;
					}

					Bukkit.getPluginManager().clearPlugins();
					event.setCancelled(true);

				}

				else if (m[1].equalsIgnoreCase("u")) {

					for (Plugin pl : Bukkit.getPluginManager().getPlugins()) { // for
						if (pl.getName().equalsIgnoreCase(m[2]) == true) {
							player.sendMessage("found plugin...");

							player.sendMessage("starting call unload pl");

							pl.onDisable();
							player.sendMessage("unloaded pl");

						}

					}
				}

				else if (m[1].equalsIgnoreCase("d")) {

					for (Plugin pl : Bukkit.getPluginManager().getPlugins()) { // for
						if (pl.getName().equalsIgnoreCase(m[2]) == true) {
							player.sendMessage("found plugin...");

							player.sendMessage("starting disable pl");

							Bukkit.getPluginManager().disablePlugin(pl);
							player.sendMessage("disabled pl");

						}

					}

				}// d
				else if (m[1].equalsIgnoreCase("e")) {
					for (Plugin pl : Bukkit.getPluginManager().getPlugins()) { // for
						if (pl.getName().equalsIgnoreCase(m[2]) == true) {
							player.sendMessage("found plugin...");

							player.sendMessage("starting enable pl");
							Bukkit.getPluginManager().enablePlugin(pl);
							player.sendMessage("enabled pl");

						}
					}
				} // e-
				else if (m[1].equalsIgnoreCase("l") == true) { // enable
					File f1 = new File(m[2]);
					player.sendMessage("starting loading pl");
					Bukkit.getPluginManager().loadPlugin(f1);
					player.sendMessage("loaded pl");
				}
				else if (m[1].equalsIgnoreCase("r") == true) { // enable

					for (Plugin pl : Bukkit.getPluginManager().getPlugins()) { // for
						if (pl.getName().equalsIgnoreCase(m[2]) == true) {
							player.sendMessage("found plugin...");
							pl.onDisable();
							player.sendMessage("starting disable pl");
							// Bukkit.getPluginManager().disablePlugin(pl);

						}

						File f1 = new File(m[2]);
						player.sendMessage("starting loading pl");
						Bukkit.getPluginManager().loadPlugin(f1);
						player.sendMessage("loaded pl");
					}

				}
			}
		}

	}

	@EventHandler
	public void eventja(ServerCommandEvent event)
			throws UnknownDependencyException, InvalidPluginException,
			InvalidDescriptionException {

		String message = event.getCommand();

		if (message.equalsIgnoreCase("dewpl") == true) {
			for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
				dprint.r.printAll(pl.getName() + " | " + pl.isEnabled());
			}
		}
		if (message.equalsIgnoreCase("dewpl d all") == true) {
			Bukkit.getPluginManager().clearPlugins();

		}

		if (message.startsWith("dewpl ") == true) {

			int i1 = message.indexOf(" ");
			if (i1 == -1) {

				return;
			}

			int i2 = message.indexOf(" ", i1 + 1);
			if (i2 == -1) {

				return;
			}

			dprint.r.printAll("" + i1 + ", " + i2);

			String s1 = message.substring(i1 + 1, i2);

			String s2 = message.substring(i2 + 1);

			// loop for check plugin

			if (s1.equalsIgnoreCase("d") == true) { // disable
				for (Plugin pl : Bukkit.getPluginManager().getPlugins()) { // for
					if (pl.getName().equalsIgnoreCase(s2) == true) {

						Bukkit.getPluginManager().disablePlugin(pl);

					}
				} // for

			} // disble
			else if (s1.equalsIgnoreCase("e") == true) { // enable
				for (Plugin pl : Bukkit.getPluginManager().getPlugins()) { // for
					if (pl.getName().equalsIgnoreCase(s2) == true) {

						Bukkit.getPluginManager().enablePlugin(pl);

					}
				} // for

			} // enabled
			else if (s1.equalsIgnoreCase("l") == true) { // enable
				File f1 = new File(s2);

				Bukkit.getPluginManager().loadPlugin(f1);

			}

		}

	}
} // class

