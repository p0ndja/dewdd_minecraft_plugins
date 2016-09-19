/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddreplacespawner;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger log;
	DigEventListener2 ax = new DigEventListener2();

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);

	}

	@Override
	public void onEnable() {

		log = getLogger();

		ax.ac = this;
		// ax.dew = new Dewddminecraft();
		// ax.dew.loadmainfile();
		// ax.dew.ac = this;

		getServer().getPluginManager().registerEvents(ax, this);

	}

}
