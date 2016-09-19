package testUUID;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
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

public class main {
	public static void main(String abc[]) {
		UUID xx = new UUID();
		//xx.sendUUID("natt0880");
		//xx.allFileInFolder("/home/d/mis/survival/plugins/Essentials/userdata");
		
		xx.allFileInFolder("/ramdisk/survival");
		
		
		//String eee = xx.sendUUID("dewdd");
		//r.pl(xx.spliteUUID(eee));
	
	}

}
