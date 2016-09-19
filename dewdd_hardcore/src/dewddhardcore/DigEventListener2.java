/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddhardcore;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	Random				random	= new Random();
	public JavaPlugin	ac		= null;

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		if (event.getMessage().equalsIgnoreCase("?h") == true
				|| event.getMessage().equalsIgnoreCase("where") == true
				|| event.getMessage().equalsIgnoreCase("?hardcore") == true
				|| event.getMessage().equalsIgnoreCase("?where") == true) {
			printAll("ptdew&dewdd: players position...");
			for (Player pd : Bukkit.getOnlinePlayers()) {
				printAll(pd.getName() + " (" + pd.getLocation().getBlockX()
						+ "," + pd.getLocation().getBlockY() + ","
						+ pd.getLocation().getBlockZ() + ")");

			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(BlockDamageEvent event) {
		if (random.nextInt(100) <= event.getPlayer().getLevel()) {
			event.getBlock().breakNaturally();
		}

	}

	@EventHandler
	public void eventja(ItemDespawnEvent event) {

		int allp = Bukkit.getOnlinePlayers().length;

		for (Player xx : Bukkit.getOnlinePlayers()) {
			if (random.nextInt(100) <= (100 / allp)) {
				Location lo2 = event.getLocation();
				lo2.getChunk().load();
				event.getEntity().teleport(xx.getLocation());
				event.setCancelled(true);
				return;
			}
		}

		event.setCancelled(true);
		// }

	}

	// PlayerDeathEvent
	@EventHandler
	public void eventja(PlayerDeathEvent event) {
		// event.getEntity().setBanned(true);
		printAll("ptdew&dewdd : " + event.getEntity().getName()
				+ " Game Over...");

		// event.getEntity().setBanned(true);
		// event.getEntity().kickPlayer("ptdew&dewdd : " +
		// event.getEntity().getName() + " Game Over...");
	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("ptdew&dewdd: Welcome to Hardcore-Dewdd");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: type ?h  for get all player position");
		event.getPlayer().sendMessage(
				"ptdew&dewdd: พิมพ์ว่า ?h เพื่อรับข้อมูลตำแหน่งทุกคนในเซิฟ");
	}

	public void printA(String abc) {
		for (Player pla : Bukkit.getOnlinePlayers()) {
			pla.sendMessage(abc);
		}
	}

	public void printAll(String abc) {
		printA(abc);
		printC(abc);
	}

	public void printC(String abc) {
		System.out.println(abc);
	}

} // class

