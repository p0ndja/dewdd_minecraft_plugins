/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddmonster;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger	log;

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		System.out.println("ptdew&dewdd : runing dewdd Nightmare Monster");
		getServer().getPluginManager().registerEvents(new DigEventListener2(),
				this);

	}

}
