package a_dewdd_skyblock_lv_1000_generator;

import java.io.File;
import java.io.FileWriter;

public class d {
	public static String logFile = File.separator + "ramdisk" + File.separator + "logfile.txt";
	static FileWriter fwriter;

	public static void interlization() {

	}

	public static void pl(String abc) {
		/*
		 * if (fwriter == null) { String filena = logFile; File fff = new
		 * File(filena);
		 * 
		 * 
		 * 
		 * try { fff.createNewFile(); fwriter = new FileWriter(fff); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 * 
		 * try { fwriter.write(abc + System.getProperty("line.separator"));
		 * fwriter.flush(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
		System.out.println(abc);
	}
}
