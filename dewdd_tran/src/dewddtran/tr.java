/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtran;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dprint_tran.r;
import li.Constant_Protect;

class RunWorldType {
	public String pluginname;
	public ArrayList<String> runWorld;
	public ArrayList<String> skipWorld;
}

public class tr {

	public static int tr_maxest = 500;
	public static datat dt[];
	public static int dtmax = 0;

	public static String folder_name = "plugins" + File.separator + "dewdd_tran";

	public static ArrayList<RunWorldType> runworld = new ArrayList<RunWorldType>();

	public static void generateRunWorldFile() {

		System.out.println("generating_runworld_file");

		String[] r = new String[Bukkit.getPluginManager().getPlugins().length];

		int i = 0;

		for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
			if (pl == null) {
				continue;
			}

			r[i] = pl.getName() + " skywar";

			i++;

		}

		tr.writeFile("dewdd_tran", "drunworld.txt", r);
		tr.loadRunWorldFile();

	}

	public static String[] getLastOnline(int day, String path) {

		String[] re = new String[3000];
		int remax = 0;
		File dir = new File(path);

		// ****************************
		try { // try

			// Open the file that is the first
			// command line parameter
			File[] fff = dir.listFiles();

			for (File element : fff) {
				if (element.isFile() == false) {
					continue;
				}

				// p(fff[i].getName());

				FileInputStream fstream = new FileInputStream(element.getPath());
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				// Read File Line By Line
				String m[];

				while ((strLine = br.readLine()) != null) {
					strLine = strLine.trim();
					// Print the content on the console
					m = strLine.split("\\s+");
					if (m[0].equalsIgnoreCase("login:")) {
						long l = Long.parseLong(m[1]);

						long diff = System.currentTimeMillis() - l;
						diff = diff / 1000 / 60 / 60 / 24;
						if (diff > day) {
							remax++;
							re[remax - 1] = element.getName().replace(".yml", "") + " " + diff;

						}
					}

				}

				in.close();

			}

			String re2[] = new String[remax];
			for (int y = 0; y < remax; y++) {
				re2[y] = re[y];
			}

			int da = 0;
			int da2 = 0;

			String temp;
			String tr[];

			for (int y = 0; y < remax; y++) {

				for (int z = 0; z <= remax - 1 - y - 1; z++) {
					tr = re2[z + 1].split("\\s+");
					da = Integer.parseInt(tr[1]);
					tr = re2[z].split("\\s+");
					da2 = Integer.parseInt(tr[1]);

					if (da < da2) {

						temp = re2[z + 1];
						re2[z + 1] = re2[z];
						re2[z] = temp;

						// p(re2[z-1] + " and " + re2[z]);

					}

				}

			}

			return re2;

		} catch (Exception e) {// Catch exception if any
			System.err.println("LoadSignFile Error: " + e.getMessage());
			e.printStackTrace();
		} // catch

		return null;
	}

	public static String gettr(String so) {

		// search so
		so = so.replace(" ", "_");

		if (tr.dtmax == 0) {
			tr.loadTrFile();

		}

		if (tr.dtmax > 0) {
			for (int i = 0; i < tr.dtmax; i++) {
				if (tr.dt[i].s.equalsIgnoreCase(so))
					return tr.dt[i].an + " ";
			}

		}
		// not found
		// add new and save it

		tr.dtmax++;
		tr.dt[tr.dtmax - 1].s = so;
		tr.dt[tr.dtmax - 1].an = so;

		tr.saveTrFile();
		tr.loadTrFile();

		return so + " ";
	}

	public static double gettrint(String so) {
		// search so
		so = so.replace(" ", "_");

		if (tr.dtmax == 0) {
			tr.loadTrFile();

		}

		if (tr.dtmax > 0) {
			for (int i = 0; i < tr.dtmax; i++) {
				if (tr.dt[i].s.equalsIgnoreCase(so))
					return Double.parseDouble(tr.dt[i].an.trim());
			}

		}
		// not found
		// add new and save it

		tr.dtmax++;
		tr.dt[tr.dtmax - 1].s = so;
		tr.dt[tr.dtmax - 1].an = "-1";

		tr.saveTrFile();
		tr.loadTrFile();

		return -1;
	}

	public static boolean isrunworld(String pluginname, String worldname) {
		for (int j = 0; j < tr.runworld.size(); j++) {
			RunWorldType tmp = tr.runworld.get(j);

			if (tmp.pluginname.equalsIgnoreCase(pluginname)) {

				for (int k = 0; k < tmp.skipWorld.size(); k++) { // exclude
					if (tmp.skipWorld.get(k).equalsIgnoreCase(worldname)) {
						return false;

					}
				}

				for (int k = 0; k < tmp.runWorld.size(); k++) { // exclude

					if (tmp.runWorld.get(k).equalsIgnoreCase(worldname) || tmp.runWorld.get(k).equalsIgnoreCase("*")) {
						return true;

					}
				}
				
				return false;

			}
		}

		return true;
	}

	public static String[] loadfile(String folderpath, String filename) {
		String re[] = new String[5000];
		int remax = 0;

		File dir = new File("plugins" + File.separator + folderpath);
		dir.mkdir();

		String filena = dir.getPath() + File.separator + filename;
		File fff = new File(filena);

		try {

			fff.createNewFile();

			System.out.println("ptdeW&DewDD : " + "starting_loading_file" + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				remax++;
				re[remax - 1] = strLine;

			}

			// compact it

			String re2[] = new String[remax];
			for (int i = 0; i < remax; i++) {
				re2[i] = re[i];

			}

			System.out.println("ptdew&DewDD : " + "loaded_file" + filena);

			in.close();
			return re2;

		} catch (Exception e) {// Catch exception if any
			System.err.println("error_while_loading_file" + filena + " " + e.getMessage());

		}

		return null;
	}

	public static void loadRunWorldFile() {

		String lo[] = tr.loadfile("dewdd_tran", "drunworld.txt");
		System.out.println("lo size = " + lo.length);
		tr.runworld = new ArrayList<RunWorldType>();
		tr.runworld.clear();

		if (lo.length == 0) {
			System.out.println("drunworld_empty_generateing_it");
			tr.generateRunWorldFile();
			return;
		}

		// scan it
		System.out.println("converting_runworld_file_data");

		String m[] = null;

		for (int i = 0; i < lo.length; i++) {
			m = lo[i].split("\\s+");

			RunWorldType tmp = new RunWorldType();

			tmp.pluginname = m[0];
			dprint_tran.r.printAll("pluginname " + m[0]);

			tmp.runWorld = new ArrayList<String>();
			tmp.skipWorld = new ArrayList<String>();

			// cut STring

			if (m[1].equalsIgnoreCase("*")) {

				tmp.runWorld.add("*");
				dprint_tran.r.printAll("runWorld add " + "*");
				
				
				for (int j = 2; j < lo.length && j < m.length; j++) {
					dprint_tran.r.printAll("skipWorld add " + m[j]);

					tmp.skipWorld.add(m[j]);
				}

			} else {
				// tmp.skipWorld.add("*");

				for (int j = 1; j < lo.length && j < m.length ; j++) {
					dprint_tran.r.printAll("runWorld add " + m[j]);
					tmp.runWorld.add(m[j]);
				}
			}

			tr.runworld.add(tmp);
		}

		dprint_tran.r.printAll("runWorld Size =  " + tr.runworld.size());
		for (int i = 0; i < tr.runworld.size(); i++) {
			RunWorldType xxx = tr.runworld.get(i);

			dprint_tran.r.printAll("runWorld size " + xxx.runWorld.size());

			for (int j = 0; j < xxx.runWorld.size(); j++) {
				dprint_tran.r.printAll(xxx.runWorld.get(j));
			}

			dprint_tran.r.printAll("skipWorld size " + xxx.skipWorld.size());

			for (int j = 0; j < xxx.skipWorld.size(); j++) {
				dprint_tran.r.printAll(xxx.skipWorld.get(j));
			}
		}

		System.out.println("loaded_runworld_file_data");

	}

	public static void loadTrFile() {
		String worldf = "dew_tr.txt";

		File dir = new File(tr.folder_name);
		dir.mkdir();

		String filena = tr.folder_name + File.separator + worldf;
		File fff = new File(filena);

		tr.dt = new datat[tr_maxest];
		tr.dtmax = 0;

		try {

			for (int i = 0; i < tr_maxest; i++) {
				tr.dt[i] = new datat();
			}

			fff.createNewFile();

			System.out.println("ptdeW&DewDD tran : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String m[];

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				m = strLine.split("\\s+");
				tr.dtmax++;
				tr.dt[tr.dtmax - 1].s = m[0];
				tr.dt[tr.dtmax - 1].an = "";
				for (int j = 1; j < m.length; j++) {
					tr.dt[tr.dtmax - 1].an = tr.dt[tr.dtmax - 1].an + " " + m[j];

				}

			}

			System.out.println("ptdew&DewDD tran : Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error load tran " + filena + " " + e.getMessage());
		}
	}

	public static String locationToString(Location location) {
		String a = location.getX() + "," + location.getY() + "," + location.getZ();
		return a;
	}

	public static void saveTrFile() {

		File dir = new File(tr.folder_name);
		dir.mkdir();

		String filena = tr.folder_name + File.separator + "dew_tr.txt";
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			r.printC("ptdew&dewdd : Start saving " + filena);
			fwriter = new FileWriter(fff);

			boolean cs = false;

			for (int y = 0; y < tr.dtmax; y++) {

				cs = false;
				for (int z = y + 1; z < tr.dtmax; z++) {
					if (tr.dt[y].s.equalsIgnoreCase(tr.dt[z].s)) {
						cs = true;
						break;
					}
				}
				if (cs == true) {
					continue;
				}

				fwriter.write(tr.dt[y].s + " " + tr.dt[y].an + System.getProperty("line.separator"));

			}

			fwriter.close();
			r.printC("ptdew&dewdd : saved " + filena);
			return;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		// dprint.r.printC ("ptdew&dewdd: save Done...");

		// ***************************88

		// ******************************
	}

	public static void showFlagToPlayer(Player player) {

		player.sendMessage(r.color((Constant_Protect.flag_autoabsorb + " "
				+ tr.gettr("flag " + Constant_Protect.flag_autoabsorb + " meaning"))));
		player.sendMessage(r.color((Constant_Protect.flag_autocut + " "
				+ tr.gettr("flag " + Constant_Protect.flag_autocut + " meaning"))));
		player.sendMessage(r.color((Constant_Protect.flag_everyone + " "
				+ tr.gettr("flag " + Constant_Protect.flag_everyone + " meaning"))));
		player.sendMessage(r.color((Constant_Protect.flag_explode + " "
				+ tr.gettr("flag " + Constant_Protect.flag_explode + " meaning"))));
		player.sendMessage(r.color((Constant_Protect.flag_monster + " "
				+ tr.gettr("flag " + Constant_Protect.flag_monster + " meaning"))));
		player.sendMessage(r.color((Constant_Protect.flag_noprotect + " "
				+ tr.gettr("flag " + Constant_Protect.flag_noprotect + " meaning"))));
		player.sendMessage(r
				.color((Constant_Protect.flag_pvp + " " + tr.gettr("flag " + Constant_Protect.flag_pvp + " meaning"))));
		player.sendMessage(r.color((Constant_Protect.flag_protectanimal + " "
				+ tr.gettr("flag " + Constant_Protect.flag_protectanimal + " meaning"))));

	}

	public static boolean writeFile(String folderpath, String filename, String data[]) {

		File dir = new File("plugins" + File.separator + folderpath);
		dir.mkdir();

		String filena = dir.getPath() + File.separator + filename;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			System.out.println("ptdew&dewdd : " + "starting_save_file" + filena);
			fwriter = new FileWriter(fff);

			for (String element : data) {

				fwriter.write(element + System.getProperty("line.separator"));

			}

			fwriter.close();
			System.out.println("ptdew&dewdd : " + "saved_file" + filena);
			return true;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
