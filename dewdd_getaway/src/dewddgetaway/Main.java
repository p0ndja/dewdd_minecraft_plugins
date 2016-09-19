/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgetaway;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger			log;
	dewgetaway2		ax	= new dewgetaway2();

	dewtransformer	ax2	= new dewtransformer();
	dewddanimation	ax3	= new dewddanimation();

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);
		dprint.r.printAll("ptdew&dewdd : unloaded dewdd getaway");
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		ax.ac = this;
		getServer().getPluginManager().registerEvents(ax, this);

		ax2.ac = this;
		getServer().getPluginManager().registerEvents(ax2, this);

		ax3.ac = this;
		getServer().getPluginManager().registerEvents(ax3, this);

		dprint.r.printAll("ptdew&dewdd : loaded dewdd getaway");

	}

}
