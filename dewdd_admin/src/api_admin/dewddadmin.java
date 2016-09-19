/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package api_admin;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.entity.Player;

public class dewddadmin {

	static String	pdefault	= "dewdd.admin.default";
	static String	pvip		= "dewdd.admin.vip";
	static String	pmoderator	= "dewdd.admin.moderator";
	static String	padmin		= "dewdd.admin.admin";

	public static String	adminlist[]	= new String[100];

	public static int		adminlistn	= -1;

	public static String	stafflist[]	= new String[100];

	public static int		stafflistn	= -1;

	public static String	viplist[]	= new String[100];
	public static int		viplistn	= -1;
	public static String[] getadminlist() {

		int cn = -1;
		String clist[] = new String[100];

		// load file
		String deadf = "plugins\\GroupManager\\worlds\\world\\users.yml";
		try {

			System.out
					.println("ptdeW&DewDD Main : Starting users.yml  load adminlist "
							+ deadf);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(deadf);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = "";
			// Read File Line By Line

			String tname = "";

			while ((strLine = br.readLine()) != null) {

				strLine = strLine.replace(" ", "");

				if (strLine.equalsIgnoreCase("users:") == true) {
					continue;
				}
				if (strLine.startsWith("subgroups:") == true) {
					continue;
				}
				if (strLine.startsWith("permissions:") == true) {
					continue;
				}
				if (strLine.startsWith("info:") == true) {
					continue;
				}
				if (strLine.startsWith("prefix:") == true) {
					continue;
				}
				if (strLine.startsWith("suffix:") == true) {
					continue;
				}
				if (strLine.startsWith("-") == true) {
					continue;
				}

				if (strLine.equalsIgnoreCase("group:admin") == true) {

					cn++;
					clist[cn] = tname;
					tname = "";

				}
				else {
					tname = strLine;
					tname = tname.replace("'", "");
					tname = tname.replace(":", "");
				}

			}

			System.out.println("ptdew&DewDD Main : Loaded " + deadf);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error : while loading " + deadf + " | "
					+ e.getMessage());
		}

		String abc[] = new String[cn + 1];
		for (int x = 0; x <= cn; x++) {
			abc[x] = clist[x];
		}
		return abc;
	}
	public static String[] getstafflist() {

		int cn = -1;
		String clist[] = new String[100];

		// load file
		String deadf = "plugins\\GroupManager\\worlds\\world\\users.yml";
		try {

			System.out
					.println("ptdeW&DewDD Main : Starting users.yml  load stafflist "
							+ deadf);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(deadf);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String tname = "";

			while ((strLine = br.readLine()) != null) {

				strLine = strLine.replace(" ", "");
				// printAll(">" + strLine);

				if (strLine.equalsIgnoreCase("users:") == true) {
					continue;
				}
				if (strLine.startsWith("subgroups:") == true) {
					continue;
				}
				if (strLine.startsWith("permissions:") == true) {
					continue;
				}
				if (strLine.startsWith("info:") == true) {
					continue;
				}
				if (strLine.startsWith("prefix:") == true) {
					continue;
				}
				if (strLine.startsWith("suffix:") == true) {
					continue;
				}
				if (strLine.startsWith("-") == true) {
					continue;
				}

				if (strLine.equalsIgnoreCase("group:moderator") == true) {

					cn++;
					clist[cn] = tname;
					tname = "";

				}
				else {
					tname = strLine;
					tname = tname.replace("'", "");
					tname = tname.replace(":", "");
				}

			}

			System.out.println("ptdew&DewDD Main : Loaded " + deadf);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error : while loading " + deadf
					+ e.getMessage());
		}

		String abc[] = new String[cn + 1];
		for (int x = 0; x <= cn; x++) {
			abc[x] = clist[x];
		}
		return abc;
	}
	public static String[] getviplist() {

		int cn = -1;
		String clist[] = new String[100];

		// load file
		String deadf = "plugins\\GroupManager\\worlds\\world\\users.yml";
		try {

			System.out
					.println("ptdeW&DewDD Main : Starting users.yml  load viplist "
							+ deadf);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(deadf);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String tname = "";

			while ((strLine = br.readLine()) != null) {

				strLine = strLine.replace(" ", "");
				// printAll(">" + strLine);

				if (strLine.equalsIgnoreCase("users:") == true) {
					continue;
				}
				if (strLine.startsWith("subgroups:") == true) {
					continue;
				}
				if (strLine.startsWith("permissions:") == true) {
					continue;
				}
				if (strLine.startsWith("info:") == true) {
					continue;
				}
				if (strLine.startsWith("prefix:") == true) {
					continue;
				}
				if (strLine.startsWith("suffix:") == true) {
					continue;
				}
				if (strLine.startsWith("-") == true) {
					continue;
				}

				if (strLine.equalsIgnoreCase("group:vip") == true) {

					cn++;
					clist[cn] = tname;
					tname = "";

				}
				else {
					tname = strLine;
					tname = tname.replace("'", "");
					tname = tname.replace(":", "");
				}

			}

			System.out.println("ptdew&DewDD Main : Loaded " + deadf);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error : while loading " + deadf
					+ e.getMessage());
		}

		String abc[] = new String[cn + 1];
		for (int x = 0; x <= cn; x++) {
			abc[x] = clist[x];
		}
		return abc;
	}
	public static boolean is2admin(Player nn) {
		return (nn.hasPermission(padmin) == true);
	}

	public static boolean is2default(Player nn) {
		return (nn.hasPermission(pdefault) == true && nn.hasPermission(pvip) == false);
	}

	public static boolean is2moderator(Player nn) {
		return (nn.hasPermission(pmoderator) == true && nn
				.hasPermission(padmin) == false);
	}

	public static boolean is2vip(Player nn) {
		return (nn.hasPermission(pvip) == true && nn.hasPermission(pmoderator) == false);
	}

	public static boolean isadminname(String playername) {
		for (String ax : adminlist) {
			if (playername.equalsIgnoreCase(ax) == true) {
				return true;
			}
		}

		return false;
	}

	public static boolean issubsubadminname(String playername) {
		for (String ax : stafflist) {
			if (playername.equalsIgnoreCase(ax) == true) {
				return true;
			}
		}

		return false;
	}

	public static boolean isvipname(String playername) {
		for (String ax : viplist) {
			if (playername.equalsIgnoreCase(ax) == true) {
				return true;
			}
		}

		return false;
	}

	public static void loadadminfile() {

		adminlist = getadminlist();
		adminlistn = adminlist.length;

		stafflist = getstafflist();
		stafflistn = stafflist.length;

		viplist = getviplist();
		viplistn = viplist.length;

	}

	public static void showadminlist(Player player) {
		for (int i = 0; i < adminlistn; i++) {
			player.sendMessage("admin : " + adminlist[i]);
		}
	}

	public static void showstafflist(Player player) {
		for (int i = 0; i < stafflistn; i++) {
			player.sendMessage("staff : " + stafflist[i]);
		}
	}

	public static void showviplist(Player player) {
		for (int i = 0; i < viplistn; i++) {
			player.sendMessage("vip : " + viplist[i]);
		}
	}

	Random					randomG		= new Random();

	public dewddadmin() {
		// if (firstrun19 == false){

		loadadminfile();

		// firstrun19 = true;
		// }
	}
}