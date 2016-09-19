package testUUID;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import print_api.r;

class UUID {

	public String  sendUUID(String user) {

		URL name;
		try {
			name = new URL("http://api.mcusername.net/playertouuid/" + user);
			BufferedReader in = new BufferedReader(new InputStreamReader(name.openStream()));
			String string = in.readLine();
			in.close();
			System.out.println(string);
			return string;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";

	}
	
	public String getNameFromEssentialsFile (String filePath) {

	
		String filena = filePath;

		File fff = new File(filena);

		try {

		
			String theName = "";

			fff.createNewFile();

			r.pl("loading essentials file : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				m = strLine.split(":");
				// Print the content on the console

				// lastAccountName: DewDD
				//r.pl("strLine " + strLine);
				if (m[0].trim().equalsIgnoreCase("lastAccountName")) {
					theName = m[1].trim();
					r.pl("theName = " + m[1]);
				}
			}

			r.pl(" Loaded " + filena);

			in.close();

			
			return theName;
		} catch (Exception e) {// Catch exception if any
			r.pl("Error load " + filena + e.getMessage());
			return null;
		}
	}


	class CheckItNow implements Runnable {
		private File sub ;
		public CheckItNow(File sub) {
			this.sub = sub;
			
		}
		@Override
		public void run() {
			
			String oldName = getNameFromEssentialsFile(sub.getAbsolutePath());
			String newName = sendUUID(oldName);
		

			
			String desFolder = "real";
			
			if (newName.equalsIgnoreCase("not premium")) {
				desFolder = "crack";
				
				
				String folder = "/ramdisk/" + desFolder ;
				File fol = new File(folder);
				fol.mkdirs();
				
				File out = new File(folder + "/" + sub.getName());
			
				
				 try {
					Files.copy(sub.toPath(), out.toPath() );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
			else {
			
			String folder = "/ramdisk/" + desFolder ;
			File fol = new File(folder);
			fol.mkdirs();
			
			
			
			File out = new File(folder + "/" + spliteUUID(newName)  + ".yml");
		
			 try {
				Files.copy(sub.toPath(), out.toPath() );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			}
		}
	}
	
	public  void allFileInFolder(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			
			ExecutorService executor = Executors.newFixedThreadPool(10);
			       
			
			for (File sub : file.listFiles()) {
				if (sub.isFile()) {
					
					r.pl(sub.getName());
					
					
			            Runnable worker = new CheckItNow(sub);
			            executor.execute(worker);
			          
			     
					  

				}
			}
			
			 executor.shutdown();
			 
		}
		

	}
	
	public String spliteUUID(String old) {
		String fff = old.substring(0, 8) + "-" +  old.substring(8, 12) +  "-" +  old.substring(12, 16) + "-"  +  old.substring(16);
		return fff;
	}

	
}

class api_skyblock {
	public static   int RSMaxPlayer = 20;

	public  RSData rs[] = new RSData[100];

	public static int rsMax = 0;


	public  ArrayList<LV1000Type> lv1000 = new ArrayList<LV1000Type>();

	public void saveRSProtectFile() {

		File dir = new File(Constant.folder_name);
		dir.mkdir();

		String filena = "/ramdisk/" + Constant.rsProtect_filename;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			r.pl("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < rsMax; y++) {
				if (rs[y].p[0].equalsIgnoreCase("")) {
					continue;
				}
				String wr = "";
				wr = rs[y].x + " " + rs[y].y + " " + rs[y].z + " ";

				for (int y2 = 0; y2 < RSMaxPlayer; y2++) {
					wr = wr + rs[y].p[y2];

					if (y2 != RSMaxPlayer)
						wr = wr + " ";

				}

				wr = wr + " " + rs[y].mission;
				wr = wr + " " + rs[y].lastUsed;

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			r.pl("ptdew&dewdd:saved " + filena);
			return;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void loadRSProtectFile() {
		String worldf = Constant.rsProtect_filename;

		File dir = new File(Constant.folder_name);
		dir.mkdir();
		System.out.println(dir.getAbsolutePath());

		String filena = Constant.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			rs = new RSData[Constant.rsBuffer];
			for (int lop = 0; lop < Constant.rsBuffer; lop++) {
				rs[lop] = new RSData();
			}
			rsMax = 0;

			fff.createNewFile();

			r.pl("ptdeW&DewDD Skyblock : " + filena);
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

				m = strLine.split("\\s+");
				// Print the content on the console
				rsMax++;
				rs[rsMax - 1].x = Integer.parseInt(m[0]);
				rs[rsMax - 1].y = Integer.parseInt(m[1]);
				rs[rsMax - 1].z = Integer.parseInt(m[2]);
				rs[rsMax - 1].p = new String[RSMaxPlayer];

				for (int i = 0; i < RSMaxPlayer; i++) {
					rs[rsMax - 1].p[i] = m[i + 3];

				}

				if (m.length >= 24) {
					int bb = (int) Double.parseDouble(m[23]);

					// bb = 0;
					rs[rsMax - 1].mission = (bb);

				}
				if (m.length == 25) {
					long bb = Long.parseLong(m[24]);

					// bb = 0;
					rs[rsMax - 1].lastUsed = (bb);
				}

				// rs[rsMax - 1].mission = 0;

			}

			// rename All Duplicate owner
			for (int i = 0; i < rsMax; i++) {
				boolean fou = false;

				for (int j = 0; j < rsMax; j++) {
					if (j == i) {
						continue;
					}

					if (rs[i].p[0].equalsIgnoreCase(rs[j].p[0])) {
						fou = true;
						break;
					}
				}

				if (fou == true) { // duplicate
					// rename it
					Random rnd = new Random();
					String abc = "dupi" + rnd.nextInt(10000);

					// search

					boolean kfou = false;

					do {
						abc = "dupi" + rnd.nextInt(10000);
						kfou = false;

						for (int k = 0; k < rsMax; k++) {
							if (rs[k].p[0].equalsIgnoreCase(abc)) {
								kfou = true;
								break;
							}

						}

					} while (kfou == true);

					r.pl("duplicate rs owner name > " + rs[i].p[0] + " renamed to " + abc);
					rs[i].p[0] = abc;
				}

			}

			r.pl("ptdew&DewDD Skyblock: Loaded " + filena);

			for (int i = rsMax - 1; i >= 0; i--) {

				for (int j = 0; j < i; j++) {

					if (rs[i].x == rs[j].x && rs[i].z == rs[j].z) {
						r.pl("duplicate x y on id " + j + " and " + i);
						continue;
					}
				}

			}

			in.close();
		} catch (Exception e) {// Catch exception if any
			r.pl("Error load " + filena + e.getMessage());
			e.printStackTrace();
		}
	}
}

public class main {
	public static void main(String abc[]) {
		int mode = 2;
			UUID xx = new UUID();
			
		switch (mode) {
		case 1:
		
			//xx.sendUUID("natt0880");
			//xx.allFileInFolder("/home/d/mis/survival/plugins/Essentials/userdata");
			
			xx.allFileInFolder("/ramdisk/survival");
			
			
			//String eee = xx.sendUUID("dewdd");
			//r.pl(xx.spliteUUID(eee));
		
			break;
		case 2:
			api_skyblock sky = new api_skyblock();
			sky.loadRSProtectFile();
			
			for (int i = 0 ; i < api_skyblock.rsMax ; i ++ ) {
				RSData rs = sky.rs[i];
				
				// loop all player
				for (int j = 0 ; j < api_skyblock.RSMaxPlayer ; j ++ ) {
					String cur = rs.p[j];
					if (cur.equalsIgnoreCase("null")) {
						continue;
					}
					
					String uuid = xx.sendUUID(cur);
					if (uuid.equalsIgnoreCase("not premium") || uuid.length() < 32) {
						rs.p[j] = "null";
						r.pl(rs.p[j]);
						
					}
					else if (uuid.equalsIgnoreCase("<autocut>") || uuid.equalsIgnoreCase("<autoabsorb>")) {
						continue;
					}
					else {
						
						rs.p[j] = rs.p[j] + "=" + uuid;
						r.pl(rs.p[j]);
						
					
					}
					
				
				
				}
				
				
			} // all rs
			
			
			sky.saveRSProtectFile();
			
			break;
		}
	
	}

}
