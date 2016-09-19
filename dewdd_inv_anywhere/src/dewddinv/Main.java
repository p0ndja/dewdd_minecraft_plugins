/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddinv;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger				log;
	DigEventListener2	ax	= new DigEventListener2();

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			ax.saveinventoryfile(p.getName());
		}
		getServer().getPluginManager().disablePlugin(this);
		dprint.r.printAll("ptdew&dewdd : unloaded dewdd inv");
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		ax.ac = this;
		getServer().getPluginManager().registerEvents(ax, this);
		dprint.r.printAll("ptdew&dewdd : loaded dewdd inv");

		for (Player p : Bukkit.getOnlinePlayers()) {
			ax.loadinventoryfile(p.getName());
		}
	}

}
