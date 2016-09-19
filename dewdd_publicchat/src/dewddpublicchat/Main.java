/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddpublicchat;

import java.util.logging.Logger;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	Logger				log;
	DigEventListener2	ax	= new DigEventListener2();

	@Override
	public void onDisable() {
		getProxy().getPluginManager().unregisterListener(ax);
		System.out.println("ptdew&dewdd : unloaded dewdd public Chat");
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		ax.ac = this;
		getProxy().getPluginManager().registerListener(this, ax);
		
		System.out.println("ptdew&dewdd : loaded dewdd public Chat");

	}

}
