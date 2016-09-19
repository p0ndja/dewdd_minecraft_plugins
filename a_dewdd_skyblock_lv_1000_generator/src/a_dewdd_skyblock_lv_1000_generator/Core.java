package a_dewdd_skyblock_lv_1000_generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;




public class Core {
	public static String sellablePath = File.separator +
			"ramdisk" + File.separator + "sellableblock.txt";
	public static String missionPath = File.separator +
			"ramdisk"  + File.separator  + "missionblock.txt";
	
	LinkedList<SellableType> sell = new LinkedList<SellableType>();
	LinkedList<MissionType> mission = new LinkedList<MissionType>();
	
	public static int dnaSize = 1000;
	
	public LinkedList<SellableType> dnaDecoder(double[] chromosome) {
		for (int i = 0; i < dnaSize ; i ++ ) {
		   
			
		}
	}
	
	public LinkedList<SellableType> sortSell(LinkedList<SellableType> sortPls) {
		int dataSize = sell.size();
		
		int indexSorted[] = new int[sell.size()];
		
		for (int i = 0; i < dataSize ; i ++ ){
			indexSorted[i] = i;
		}
		
		
		
		
		
		for (int i = 0; i < dataSize; i++) {
			for (int j = 0; j < (dataSize - 1 - i); j++) {
				
				if (sortPls.get(indexSorted[j]).timeToGet   < sortPls.get(indexSorted[j+1]).timeToGet) {
					int t = indexSorted[j];
					

					indexSorted[j] = indexSorted[j+1];
					
					indexSorted[j+1] = t;

				}
			}

		}
		
		
		
		LinkedList<SellableType> tmp2 = new LinkedList<SellableType>();
		for (int i = 0 ; i < dataSize ; i ++ ){
			tmp2.add(sortPls.get(indexSorted[i]));
		}
		return tmp2;
	}
	
	public String convertTimeToString(double abc) {
		String letter = "";
		double ceo = 0;
		
		if (abc >= 60*60) {
			letter = "h";
			ceo = abc / 60 / 60;
			
		}
		else if (abc >= 60) {
			letter = "m";
			
			ceo = abc / 60 ;
		}
		else if (abc >= 0) {
			letter = "s";
			
			ceo = abc / 60;
		}
		
		
		
		return ceo + " " +letter;
		
	}
	
	public double convertStringToTime(String abc) {
		String m[] = abc.split(" ");
		
		double cd = Double.parseDouble(m[0]);
		
		if (m[1].equalsIgnoreCase("h")) {
			cd = cd*60*60;
			
		}
		else if (m[1].equalsIgnoreCase("m")) {
			cd = cd*60;
			
		}
		else if (m[1].equalsIgnoreCase("s")) {
			cd = cd*1;
			
		}
		
		return cd;
		
	}
	
	public void loadMissionBlockFile() {
		


		String filena = missionPath;
		File fff = new File(filena);

		try {

			mission.clear();

			fff.createNewFile();

			d.pl("loading mission file : " + filena);
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
				
				MissionType miss = new MissionType();
				miss.theName = m[0];
				miss.data = Byte.parseByte(m[1]);
				miss.maxStack = Integer.parseInt(m[2]);
				miss.isBlock =  Boolean.parseBoolean(m[3]);
				
			//	d.pl("...");
				// rs[rsMax - 1].mission = 0;
				mission.add(miss);
			}

			d.pl(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			d.pl("Error load " + filena + e.getMessage());
		}
	}
	
	public void loadSellableFile() {
	


		String filena = sellablePath;
		File fff = new File(filena);

		try {

			sell.clear();

			fff.createNewFile();

			d.pl("loading sellable file : " + filena);
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
				
				SellableType sb = new SellableType();
				sb.theName = m[0];
				sb.data = Byte.parseByte(m[1]);
				sb.maxStack = Integer.parseInt(m[2]);
				sb.timeToGet =  convertStringToTime(m[3]);
				// rs[rsMax - 1].mission = 0;
				sell.add(sb);
			}

			d.pl(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			d.pl("Error load " + filena + e.getMessage());
		}
	}
}
