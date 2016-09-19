/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddautomessage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

class Dewminecraft {

	public String folder_name = "plugins" + File.separator + "dewdd_automessage";

	Random randomGenerator = new Random();
	int randomInt = randomGenerator.nextInt(9);

	// loadsignfile
	int countLoop = 0;
	int countShow = 0;

	ArrayList<String> autostr = new ArrayList<String>();
	int autostrnow = -1;
	int countmax = 200;

	public Dewminecraft() {
		loadautomessagefile();
	}

	// *********************************************************

	void automessage(Player player) {

		countLoop++;
		if (countLoop > countmax) {

			countLoop = -1;
			autostrnow++;
			if (autostrnow >= autostr.size()) {
				autostrnow = -1;
			}

			if (autostrnow > -1) {
				for (World wor : Bukkit.getWorlds()) {
					// wor.save();
					for (Player pla : wor.getPlayers()) {
						randomInt = randomGenerator.nextInt(9);
						pla.sendMessage("§" + randomInt + "ptdew&dewdd : " + autostr.get(autostrnow));
						// pla.saveData();
					}
				}
			}
		}
	}

	// §
	void loadautomessagefile() {

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + ("dewddautomessage.txt");
		File fff = new File(filena);

		try {
			fff.createNewFile();

			dprint.r.printAll("ptdeW&DewDD Automessage: Starting Loading " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String strAll = "";

			autostr = new ArrayList<String>();
			// sthae
			// aosthoeau
			// * save
			

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console

				
					if (strLine.equalsIgnoreCase("*") == true) {

						autostr.add(strAll);
						strAll = "";
					} else {
						strAll = strAll + strLine;
					}
				
			}

			dprint.r.printAll("ptdew&DewDD AutoMessage: Loaded " + filena + " size " + autostr.size());

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}

public class DigEventListener2 implements Listener {

	Dewminecraft dew = new Dewminecraft();
	JavaPlugin ac = null;

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		dew.automessage(player);

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd automessage");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd automessage") == true) {
			player.sendMessage(">>dewdd automessage help");
			player.sendMessage("ปลั๊กอิน automessage เป็นปลั๊กอิน  แสดงข้อความ วนไปวนมา  ตามที่เก็บไว้ในไฟล์    , "
					+ "โดยปลั๊กอินนี้ จะไม่ทำงานตามช่วงเวลาแน่นอน   แต่ ความเร็วข้อความแสดงผลจะขึ้นอยู่กับ  การกระทำของคนในเซิฟว่าบ่อยแค่ไหน เช่น วางบล็อค ทำลายบล็อคมาก ข้อความจะขึ้นเร็ว");

			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd automessage help") == true) {
			player.sendMessage("./dewddreloadmessagefile   >   เป็นคำสั่งโหลดไฟล์ข้อความใหม่");
			event.setCancelled(true);
		}

	}

	// *******************************

	@EventHandler
	public void eventja(BlockBreakEvent event) {
		dew.automessage(event.getPlayer());
	}

	@EventHandler
	public void eventja(BlockPlaceEvent event) {
		dew.automessage(event.getPlayer());
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		runpro(event.getMessage());
	}

	
	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, new Runnable() {

			@Override
			public void run() {
				player.sendMessage(tr.gettr("on_automessage_playerjoin_tell_him_this_word"));
			}
		}, 100);
	}
	
	@EventHandler
	public void eventja(PlayerDropItemEvent event) {
		dew.automessage(event.getPlayer());
	}

	// **********************************

	@EventHandler
	public void eventja(PlayerExpChangeEvent event) {
		dew.automessage(event.getPlayer());
	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {
		dew.automessage(event.getPlayer());
	}

	@EventHandler
	public void eventja(ServerCommandEvent event) {
		runpro("/" + event.getCommand());
	}

	public void runpro(String message) {

		if (message.equalsIgnoreCase("/dewddreloadmessagefile") == true) {

			dprint.r.printAll("ptdew&dewdd: starting reload message file");
			dew.loadautomessagefile();
			dprint.r.printAll("ptdew&dewdd: finished reload message file");
		}
	}
}
