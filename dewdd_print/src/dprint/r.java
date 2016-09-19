/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dprint;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class r {
	static Random	rnd	= new Random();
	
	
	public static int randomColorInt( ) {
		int x =  rnd.nextInt(8)+ 1;
		return x;
	}

	public static String color(String abc) {
		return "§" + randomColorInt() + abc;
	}

	public static void printA(String abc) {

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage("§" + randomColorInt() + abc);
		}
	}

	public static void printAdmin(String abc) {
		for (Player pla : Bukkit.getOnlinePlayers()) {
			if (api_admin.dewddadmin.is2admin(pla) == true) {
				pla.sendMessage("§" + randomColorInt() + abc);
			}
		}
	}

	public static void printAll(String abc) {
		printC(abc);
		printA(abc);
	}

	public static void printC(String abc) {
		System.out.println(abc);
	}

	public static void printFly(String abc) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.getAllowFlight() == true) {
				pl.sendMessage("§" + randomColorInt() + abc);
			}
		}

	}

	public static void printP(String abc, Player p) {
		p.sendMessage("§" + randomColorInt() + abc);

	}

} // dew minecraft