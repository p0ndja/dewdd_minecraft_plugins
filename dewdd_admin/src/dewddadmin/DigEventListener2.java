/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddadmin;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {

	JavaPlugin	ac	= null;

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd admin");

			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("dewdd admin") == true) {
			player.sendMessage("ปลั๊กอิน admin เป็นที่รวบรวมรายชื่อ แอดมิน  นักสร้าง และ วีไอพี  โดยปลั๊กอินอื่นๆ  จะมาดึงข้อมูลจากปลั๊กอินนี้");
			player.sendMessage(".dewreloadadminfile   เพื่อโหลดไฟล์แอดมิน ใหม่");
			player.sendMessage(".dreloadaf   เพื่อโหลดไฟล์แอดมิน ใหม่");
			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("dewreloadadminfile") == true
				|| event.getMessage().equalsIgnoreCase("dreloadaf") == true) {
			api_admin.dewddadmin.loadadminfile();
			player.sendMessage("ptdew&dewdd: Reloaded admin File");
			return;
		}

		if (message.equalsIgnoreCase("adminlist") == true) {
			api_admin.dewddadmin.showadminlist(player);
			event.setCancelled(true);
		}

		if (message.equalsIgnoreCase("stafflist") == true) {
			api_admin.dewddadmin.showstafflist(player);
			event.setCancelled(true);
		}
		if (message.equalsIgnoreCase("viplist") == true) {
			api_admin.dewddadmin.showviplist(player);
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/dewreloadadminfile")
				|| event.getMessage().equalsIgnoreCase("/reloadadmin")) {
			api_admin.dewddadmin.loadadminfile();
		}

	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {

		Action act;
		act = event.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)) {
			return;
		}

		Block block = event.getClickedBlock();

		if (block.getTypeId() == 63 || block.getTypeId() == 68) {

			Sign sign = (Sign) block.getState();

			if (sign.getLine(0).endsWith("[adminlist]") == true) {

				api_admin.dewddadmin.showadminlist(event.getPlayer());
				event.setCancelled(true);
			}
			if (sign.getLine(0).endsWith("[stafflist]") == true) {
				api_admin.dewddadmin.showstafflist(event.getPlayer());
				event.setCancelled(true);
			}
			if (sign.getLine(0).endsWith("[viplist]") == true) {
				api_admin.dewddadmin.showviplist(event.getPlayer());
				event.setCancelled(true);
			}

		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventja(PlayerJoinEvent event) {
		api_admin.dewddadmin.loadadminfile();
	}

	@EventHandler
	public void eventja(ServerCommandEvent event) {
		if (event.getCommand().equalsIgnoreCase("dewreloadadminfile") == true
				|| event.getCommand().equalsIgnoreCase("dreloadaf") == true) {
			api_admin.dewddadmin.loadadminfile();
			dprint.r.printAll("ptdew&dewdd: Reloaded admin File");
			return;
		}
	}

} // dig