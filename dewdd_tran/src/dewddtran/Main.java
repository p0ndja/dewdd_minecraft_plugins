/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtran;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dprint_tran.r;

public class Main extends JavaPlugin {
	Logger log;
	tranrun ax = new tranrun();

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);
		r.printAll("ptdew&dewdd : unloaded dewdd tran");
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		ax.ac = this;
		getServer().getPluginManager().registerEvents(ax, this);
		r.printAll("ptdew&dewdd : loaded dewdd tran");

		tr.loadRunWorldFile();
		tr.loadTrFile();

	}

}
