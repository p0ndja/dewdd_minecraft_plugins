/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddlimitpet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DigEventListener2 implements Listener {
	int	redimax		= 60;
	int	countmax	= 50;

	String	limitpetfilename	= "ptdew_dewdd_limitpet.txt";

	public DigEventListener2() {
		loadlimitpetfile();
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();

		if (event.getMessage().startsWith("dewupdatep|") == true) {
			event.setMessage(event.getMessage().replace(" ", "."));
			player.sendMessage("'"
					+ event.getMessage().substring(
							event.getMessage().indexOf("|") + 1) + "'");
			player.sendMessage("starting download");
			dewddlimitpet.filedown.download(
					event.getMessage().substring(
							event.getMessage().indexOf("|") + 1), false);
			player.sendMessage("finshed downloading");
			event.setCancelled(true);
			event.setMessage("");
		}
		// update
		if (event.getMessage().startsWith("dewupdatep2|") == true) {
			event.setMessage(event.getMessage().replace(" ", "."));
			player.sendMessage("'"
					+ event.getMessage().substring(
							event.getMessage().indexOf("|") + 1) + "'");
			player.sendMessage("starting download");
			dewddlimitpet.filedown.download(
					event.getMessage().substring(
							event.getMessage().indexOf("|") + 1), true);
			player.sendMessage("finshed downloading");
			event.setCancelled(true);
			event.setMessage("");
		}

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd limitpet");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd limitpet") == true) {
			player.sendMessage("ปลั๊กอิน limitpet จะจำกัดจำนวนสัตว์เลี้ยงใน  รัศมีวงกลมวงกลมหนึ่ง ว่ามีสัตว์ได้มากสุดกี่ตัว");
			player.sendMessage(".dewdd limitpet reload   เพื่อโหลดไฟล์ใหม่");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd limitpet reload") == true) {
			loadlimitpetfile();
			event.setCancelled(true);
		}

	}

	@EventHandler(priority = EventPriority.LOW)
	public void eventja(CreatureSpawnEvent event) {

		int count = 0;

		for (Entity en : event.getLocation().getWorld().getEntities()) {
			if (istarget(en) == false) {

				continue;
			}

			count = 0; // count amount of pet neary

			for (Entity enx : event.getLocation().getWorld().getEntities()) {
				if (istarget(enx) == false) {

					continue;
				}
				if (enx.getLocation().distance(en.getLocation()) <= redimax) {
					count++;
				}

			} // count

			if (count > countmax) {

				printC("limit pet remove " + en.getType().getName() + ","
						+ en.getLocation().getBlockX() + ","
						+ en.getLocation().getBlockY() + ","
						+ en.getLocation().getBlockZ());

				en.remove();
				break;
			}

		} // for

	} // cre

	public boolean istarget(Entity ab) {

		if (ab.getType() != EntityType.ARROW
				&& ab.getType() != EntityType.COMPLEX_PART
				&& ab.getType() != EntityType.DROPPED_ITEM
				&& ab.getType() != EntityType.EGG
				&& ab.getType() != EntityType.ENDER_CRYSTAL
				&& ab.getType() != EntityType.ENDER_PEARL
				&& ab.getType() != EntityType.PAINTING &&

				ab.getType() != EntityType.ENDER_SIGNAL
				&& ab.getType() != EntityType.EXPERIENCE_ORB
				&& ab.getType() != EntityType.FALLING_BLOCK
				&& ab.getType() != EntityType.FIREBALL
				&& ab.getType() != EntityType.FIREWORK &&

				ab.getType() != EntityType.FISHING_HOOK
				&& ab.getType() != EntityType.IRON_GOLEM
				&& ab.getType() != EntityType.ITEM_FRAME
				&& ab.getType() != EntityType.LEASH_HITCH
				&& ab.getType() != EntityType.LIGHTNING &&

				ab.getType() != EntityType.PLAYER
				&& ab.getType() != EntityType.PRIMED_TNT
				&& ab.getType() != EntityType.SMALL_FIREBALL
				&& ab.getType() != EntityType.SNOWBALL
				&& ab.getType() != EntityType.SPLASH_POTION &&

				ab.getType() != EntityType.THROWN_EXP_BOTTLE
				&& ab.getType() != EntityType.UNKNOWN
				&& ab.getType() != EntityType.WEATHER
				&& ab.getType() != EntityType.WITHER_SKULL) {

			return true;
		}

		return false;

	}

	public boolean istarget(LivingEntity ab) {

		if (ab.getType() != EntityType.ARROW
				&& ab.getType() != EntityType.COMPLEX_PART
				&& ab.getType() != EntityType.DROPPED_ITEM
				&& ab.getType() != EntityType.EGG
				&& ab.getType() != EntityType.ENDER_CRYSTAL
				&& ab.getType() != EntityType.ENDER_PEARL
				&& ab.getType() != EntityType.PAINTING &&

				ab.getType() != EntityType.ENDER_SIGNAL
				&& ab.getType() != EntityType.EXPERIENCE_ORB
				&& ab.getType() != EntityType.FALLING_BLOCK
				&& ab.getType() != EntityType.FIREBALL
				&& ab.getType() != EntityType.FIREWORK &&

				ab.getType() != EntityType.FISHING_HOOK
				&& ab.getType() != EntityType.IRON_GOLEM
				&& ab.getType() != EntityType.ITEM_FRAME
				&& ab.getType() != EntityType.LEASH_HITCH
				&& ab.getType() != EntityType.LIGHTNING &&

				ab.getType() != EntityType.PLAYER
				&& ab.getType() != EntityType.PRIMED_TNT
				&& ab.getType() != EntityType.SMALL_FIREBALL
				&& ab.getType() != EntityType.SNOWBALL
				&& ab.getType() != EntityType.SPLASH_POTION &&

				ab.getType() != EntityType.THROWN_EXP_BOTTLE
				&& ab.getType() != EntityType.UNKNOWN
				&& ab.getType() != EntityType.WEATHER
				&& ab.getType() != EntityType.WITHER_SKULL) {

			return true;
		}

		return false;

	}

	public void loadlimitpetfile() {

		try {

			System.out.println("ptdeW&DewDD limitpet : Starting Loading "
					+ limitpetfilename + ".txt");
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(limitpetfilename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String nowmode = "";

			strLine = br.readLine();
			redimax = Integer.parseInt(strLine);

			strLine = br.readLine();
			countmax = Integer.parseInt(strLine);

			System.out.println("ptdew&DewDD limitpet: Loaded "
					+ limitpetfilename + ".txt");

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error load limitpet file: " + e.getMessage());
		}
		finally {
			printAll("dewdd echopet redians = " + redimax);
			printAll("dewdd echopet limited pet = " + countmax);

		}

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

