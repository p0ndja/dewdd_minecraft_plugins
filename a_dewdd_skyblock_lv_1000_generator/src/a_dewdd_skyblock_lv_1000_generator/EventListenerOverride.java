package a_dewdd_skyblock_lv_1000_generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import core_optimization_api.Chromosome;
import core_optimization_api.EventListener;

public class EventListenerOverride extends EventListener {
	public static String folder_Name = File.separator + "ramdisk" + File.separator + "skylv_generator";
	public static String theBest_fileName = "theBest.txt";

	@Override
	public void theBestAllTheTime(Chromosome cho, double fitness, int evo) {
		// write to file

		File dir = new File(folder_Name);
		dir.mkdir();

		String filena = folder_Name + File.separator + fitness + "_" + evo + "_" + theBest_fileName;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			d.pl("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

			fwriter.write(fitness + " " + evo + System.getProperty("line.separator"));

			for (int y = 0; y < Core.dnaSize; y++) {

				String wr = "" + cho.dna[y];

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			d.pl("ptdew&dewdd:saved " + filena);
			return;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void theBestOfEachEvolution(Chromosome cho, double fitness, int evo) {
		d.pl("bestOfEachEvolution event " + fitness + " , " + evo);

	}
}
