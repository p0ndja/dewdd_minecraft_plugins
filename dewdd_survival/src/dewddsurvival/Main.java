/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddsurvival;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger	log;

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		log = this.getLogger();
		getServer().getPluginManager().registerEvents(new DigEventListener2(),
				this);

	}
}
