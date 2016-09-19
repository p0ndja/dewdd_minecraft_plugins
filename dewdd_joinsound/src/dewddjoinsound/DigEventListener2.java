/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddjoinsound;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DigEventListener2 implements Listener {
	JavaPlugin ac = null;

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {

		String bip = File.separator + "mi" + File.separator + "lobby" + File.separator + "plugins" + File.separator
				+ "dewdd_joinsound" + File.separator + "join.wav";

		
		Media hit = new Media(bip);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		
	}

} // class
