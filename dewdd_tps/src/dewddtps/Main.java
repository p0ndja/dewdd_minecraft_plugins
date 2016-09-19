/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtps;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger log;
	gettps ax = new gettps();

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);
		dprint.r.printAll("ptdew&dewdd : unloaded dewdd tps");
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		ax.ac = this;
		getServer().getPluginManager().registerEvents(ax, this);

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new tps(), 100L, 1L);
		dprint.r.printAll("ptdew&dewdd : loaded dewdd tps");

	}

}
