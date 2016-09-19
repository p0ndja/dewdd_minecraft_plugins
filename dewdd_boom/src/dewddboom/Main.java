/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddboom;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public class runja extends Thread {
		Main	ja;

		public void run() {

			ja.getServer().getPluginManager()
					.registerEvents(new DigEventListener2(), ja);

		}

	}

	Logger	log;

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {

		log = this.getLogger();
		runja r = new runja();
		r.ja = this;
		r.setPriority(1);
		r.run();
	}
}
