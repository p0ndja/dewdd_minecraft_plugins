/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_empty;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class dewdd_empty extends JavaPlugin {

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldname, String id) {
		return new dewdd_empty_run();
	}
}