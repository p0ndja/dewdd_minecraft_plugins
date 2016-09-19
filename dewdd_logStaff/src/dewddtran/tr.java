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
import java.util.LinkedList;

class runworldtype {
	public String				pluginname;
	public ArrayList<String>	runworld;
}

public class tr {

	public static String				folder_name	= "plugins"
															+ File.separator
															+ "dewdd_logstaff";

	public static String				staffFile	= "dew_staff.txt";
	public static String				logFile		= "dew_log.txt";

	public static LinkedList<String>	staffList	= new LinkedList<String>();

	public static LinkedList<String>	logList		= new LinkedList<String>();

	public static boolean isThisStaff(String name) {
		// search

		boolean fx = false;
		for (int i = 0; i < staffList.size(); i++) {
			if (staffList.get(i).equalsIgnoreCase(name)) {
				fx = true;
				break;
			}
		}

		return fx;
	}

	public static void loadStaffFile() {

		String worldf = staffFile;

		File dir = new File(tr.folder_name);
		dir.mkdir();

		String filena = tr.folder_name + File.separator + worldf;
		File fff = new File(filena);

		if (fff.exists() == false) {
			// create staff list
			try {
				System.out
						.println("dewdd : log staff file not found , generating..");
				FileWriter fwriter = new FileWriter(fff, true);
				fwriter.write("ptdew" + System.getProperty("line.separator"));
				fwriter.write("pondjaKung"
						+ System.getProperty("line.separator"));
				fwriter.close();
				System.out.println("dewdd : log staff generated file");

			}
			catch (IOException e) {
				System.out
						.println("dewdd : log staff generating file error !!");
				e.printStackTrace();
			}

		}

		try {

			fff.createNewFile();

			System.out
					.println("ptdeW&DewDD log start loading file : " + filena);
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

			staffList.clear();

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// m = strLine.split("\\s+");
				staffList.add(strLine);
				System.out.println("ptdew&dewdd : Log loading staff name  '"
						+ strLine + "'");
			}

			System.out.println("ptdew&DewDD :  Log Staff : Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error load tran " + filena + " "
					+ e.getMessage());
		}
	}

	public static void saveLogFile() {

		File dir = new File(tr.folder_name);
		dir.mkdir();

		String filena = tr.folder_name + File.separator + logFile;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			System.out.println("ptdew&dewdd : Start saving " + filena);
			fwriter = new FileWriter(fff, true);

			boolean cs = false;

			for (int i = 0; i < logList.size(); i++) {

				fwriter.write(logList.get(i)
						+ System.getProperty("line.separator"));

			}

			logList.clear();

			fwriter.close();
			System.out.println("ptdew&dewdd : saved " + filena);

			return;

		}
		catch (IOException e) {
			System.out.println("dewdd : save Log File erro!" + e.getMessage());
			e.printStackTrace();
		}

	}

	public static boolean writefile(String folderpath, String filename,
			String data[]) {

		File dir = new File("plugins" + File.separator + folderpath);
		dir.mkdir();

		String filena = dir.getPath() + File.separator + filename;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			System.out.println("ptdew&dewdd : " + "savin g" + filena);
			fwriter = new FileWriter(fff);

			for (String element : data) {

				fwriter.write(element + System.getProperty("line.separator"));

			}

			fwriter.close();
			System.out.println("ptdew&dewdd : " + "saved_file" + filena);
			return true;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
