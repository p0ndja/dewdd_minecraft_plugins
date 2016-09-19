/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_redstone_experimental;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class Main extends JavaPlugin {
	Logger				log;
	DigEventListener2	ax	= new DigEventListener2();

	@Override
	public void onDisable() {
		this.getServer().getPluginManager().disablePlugin(this);
		dprint.r.printAll("ptdew&dewdd : " + tr.gettr("unloaded_plugin")
				+ " dewdd redstone experimental");
	}

	@Override
	public void onEnable() {

		this.log = this.getLogger();

		DigEventListener2.ac = this;

		this.getServer().getPluginManager().registerEvents(this.ax, this);
		dprint.r.printAll("ptdew&dewdd : " + tr.gettr("loaded_plugin")
				+ " dewdd redstone experimental");

	}

}