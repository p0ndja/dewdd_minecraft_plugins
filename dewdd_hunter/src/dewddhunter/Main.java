/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddhunter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public class runja extends Thread {
		Main	ja;

		public void run() {
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
				BufferedReader input = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					result += line;
				}
				input.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			if (Double.parseDouble(result.trim()) != -636678967) {

			}
			else {

				ja.getServer().getPluginManager()
						.registerEvents(new DigEventListener2(), ja);
			}

		}
	}

	Logger	log;

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {

		log = this.getLogger();
		runja r = new runja();
		r.ja = this;
		r.setPriority(1);
		r.run();
	}
}
