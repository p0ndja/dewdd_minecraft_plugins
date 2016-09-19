/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddupdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger	log;

	@Override
	public void onDisable() {
		getServer().getPluginManager().disablePlugin(this);
	}

	@Override
	public void onEnable() {

		log = this.getLogger();
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ "c"
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// -861880436
		if ((Double.parseDouble(result.trim()) != -636678967)
				&& (Double.parseDouble(result.trim()) != -1498240033)) { // naam

		}
		else {
			System.out.println("ptdew&dewdd : runing dewdd absorb");
			getServer().getPluginManager().registerEvents(
					new DigEventListener2(), this);
		}
	}

}
