/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddflower;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class dewsetdatabase {
	public class dewsetlistblock {

		int	dewnotset[];					// list of block allow to dewset
		int	dewinventoryblock[];			// list of block not allow to dewset
		int	dewnotsetmax			= 0;
		int	dewinventoryblockmax	= 0;

		public dewsetlistblock() {
			dewnotset = new int[300];
			dewinventoryblock = new int[200];
			dewnotsetmax = 0;
			dewinventoryblockmax = 0;
		}
	}

	public dewsetlistblock	dslb		= new dewsetlistblock();

	public String			folder_name	= "plugins" + File.separator
												+ "dewdd_main";

	public boolean isdewinventoryblock(int blockid) {

		for (int i = 0; i < dslb.dewinventoryblockmax; i++) {
			if (blockid == dslb.dewinventoryblock[i]) {
				return true;
			}
		}
		return false;
	}

	public boolean isdewset(int blockid) {

		for (int i = 0; i < dslb.dewnotsetmax; i++) {
			if (blockid == dslb.dewnotset[i]) {
				return false;
			}
		}

		Material gid = Material.getMaterial(blockid);
		if (gid == null) {
			return false;
		}

		if (gid.isBlock() == false) {
			return false;
		}

		return true;
	}

	public boolean loaddewsetlistblockfile() {
		try { // try

			File dir = new File(folder_name);
			dir.mkdir();

			String filena = folder_name + File.separator
					+ "ptdew_dewdd_dewsetlistblock.txt";
			File fff = new File(filena);
			fff.createNewFile();

			// Open the file that is the first
			// command line parameter

			printC("ptdew&dewdd: Starting Loading " + filena);

			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			int moden = -1;

			dslb.dewnotsetmax = 0;
			dslb.dewinventoryblockmax = 0;

			while ((strLine = br.readLine()) != null) {

				// Print the content on the console

				if (strLine.equalsIgnoreCase("dewnotset:")) {
					printC("mode = dewnotset");
					moden = 0;
					continue;
				}
				if (strLine.equalsIgnoreCase("dewinventoryblock:")) {
					moden = 1;
					printC("mode = dewinventoryblock");
					continue;
				}

				switch (moden) {
				case 0: // dewnotset
					dslb.dewnotsetmax++;
					dslb.dewnotset[dslb.dewnotsetmax - 1] = Integer
							.parseInt(strLine);
					// printC("load dewnotset " + dslb.dewnotsetmax + " = " +
					// strLine);
					continue;
				case 1: // dewinventoryblock
					dslb.dewinventoryblockmax++;
					dslb.dewinventoryblock[dslb.dewinventoryblockmax - 1] = Integer
							.parseInt(strLine);
					// printC("load dewinventoryblock " +
					// dslb.dewinventoryblockmax + " = " + strLine);
					continue;
				default:
					// printC("ptdew&dewdd : error loading " + filena +
					// " cuz moden == -1");
					return false;
				}

			}

			printC("ptdew&dewdd: loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Load dewset list block file Error: "
					+ e.getMessage());
			return false;
		} // catch

		return true;

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

	public void showdewsetlistblock() {
		for (int i = 0; i < dslb.dewnotsetmax; i++) {
			printC("i = " + i + " , " + dslb.dewnotset[i]);
		}
	}

	public void showdewsetlistblock(Player p) {
		p.sendMessage("starting checking");
		Material ik = Material.getMaterial(p.getItemInHand().getTypeId());

		int a1 = p.getItemInHand().getTypeId();
		int a2 = p.getItemInHand().getData().getData();

		if (ik == null) {
			p.sendMessage("what the hell item? " + a1 + ":" + a2);
			return;
		}

		if (ik.isBlock() == false) {
			p.sendMessage("it's not a block ? " + a1 + ":" + a2);
			return;
		}

		if (isdewset(a1) == false) {
			p.sendMessage("this item my plugin not allow for dewset " + a1
					+ ":" + a2);
			return;
		}
	}
}
