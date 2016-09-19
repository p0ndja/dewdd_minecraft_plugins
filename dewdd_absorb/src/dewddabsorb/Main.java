/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddabsorb;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger				log;
	DigEventListener2	ax	= new DigEventListener2();

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);
		printAll("ptdew&dewdd : unloaded dewddabsorb");
	}

	@Override
	public void onEnable() {

		log = this.getLogger();

		/*
		 * String result = "";
		 * try {
		 * File file = File.createTempFile("realhowto",".vbs");
		 * file.deleteOnExit();
		 * FileWriter fw = new java.io.FileWriter(file);
		 * 
		 * String vbs =
		 * "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
		 * +"Set colDrives = objFSO.Drives\n"
		 * +"Set objDrive = colDrives.item(\"" + "c" + "\")\n"
		 * +"Wscript.Echo objDrive.SerialNumber"; // see note
		 * fw.write(vbs);
		 * fw.close();
		 * Process p = Runtime.getRuntime().exec("cscript //NoLogo " +
		 * file.getPath());
		 * BufferedReader input =
		 * new BufferedReader
		 * (new InputStreamReader(p.getInputStream()));
		 * String line;
		 * while ((line = input.readLine()) != null) {
		 * result += line;
		 * }
		 * input.close();
		 * }
		 * catch(Exception e){
		 * e.printStackTrace();
		 * }
		 * 
		 * // -861880436
		 * // otop -2105933757
		 * // i3 -1071656046
		 * 
		 * long x[] = new long[3];
		 * x[0] = -636678967; // my com
		 * x[1] = -2105933757; //otop
		 * x[2] = -1071656046; // i3
		 * 
		 * int xmax = 3;
		 * 
		 * boolean y = false;
		 * 
		 * for (int c = 0 ; c < xmax ;c ++) {
		 * if (Double.parseDouble(result.trim()) == x[c]) {
		 * y = true;
		 * break;
		 * }
		 * }
		 * 
		 * if ( y == false){
		 * 
		 * }
		 * else {
		 */
		ax.ac = this;
		getServer().getPluginManager().registerEvents(ax, this);
		printAll("ptdew&dewdd : loaded dewddabsorb");
		// }
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
}
