/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */

package dewddBlockBreakRandomPlayNote;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {

	public static String	version	= "1.0";
	Random					rnd		= new Random();
	public JavaPlugin		ac		= null;

	@EventHandler
	public void eventja(BlockBreakEvent e) {

		Block block = e.getBlock();
		Player player = e.getPlayer();

		this.randomplaynote(player.getLocation());

	}

	public void randomplaynote(Location player) {
		if (this.rnd.nextInt(100) > 10) return;

		for (Player pla : player.getWorld().getPlayers())
			pla.playSound(player, Sound.BLOCK_NOTE_HARP, this.rnd.nextInt(50),
					this.rnd.nextFloat() + 1);
	}
}