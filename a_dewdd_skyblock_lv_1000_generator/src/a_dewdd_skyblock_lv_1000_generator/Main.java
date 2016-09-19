package a_dewdd_skyblock_lv_1000_generator;

import java.util.ArrayList;
import java.util.LinkedList;

import core_optimization_api.Chromosome;

public class Main {

	public static Core co;
	public static int populationSize = 300;

	public static void main(String[] args) {

		co = new Core();

		d.pl("");
		d.pl("*******************");
		d.pl("");

		co.loadMissionBlockFile();

		co.loadSellableFile();
		// search the all block in game don't have block from sellable

		for (int i = 0; i < Core.sellAsList.size(); i++) {
			SellableType e = Core.sellAsList.get(i);
			AllBlockInGameType e2 = Core.allBlockInGameAsList[e.index];
			// search in
			boolean foundx = false;
			for (int j = 0; j < Core.allBlockInGameAsListSize; j++) {
				AllBlockInGameType f = Core.allBlockInGameAsList[j];

				if (e2.theName.equalsIgnoreCase(f.theName) && e2.data == f.data) {
					foundx = true;
					break;
				}
			}

			if (foundx == false) {
				d.pl("sellable not in the list " + e2.theName + ":" + e2.data);

			}
		}

		d.pl("*** mission size = " + Core.allBlockInGameAsListSize);

		HybridOverride hy = new HybridOverride();

		hy.setDnaLength(Core.dnaSize);
		hy.setHashMapMode(false);
		hy.setMaxEpochs(10000);
		hy.setExitError(-Double.MAX_VALUE);

		// set chromosome here

		ArrayList<Chromosome> seo = hy.loadAndSortTheBestFromRamdisk();

		if (args.length == 0) {
			args = new String[2];
			args[0] = "evu";
			args[1] = "10";
		}

		if (args[0].equalsIgnoreCase("evu")) {

			if (args.length == 1) {
				hy.setPopulationSize(populationSize);
			} else {
				hy.setPopulationSize(Integer.parseInt(args[1]));

			}

			hy.setAllChromosomeGen1(seo);
			hy.prepareToRunGA();

			EventListenerOverride evn = new EventListenerOverride();

			hy.registerEvent(evn);

			// hy.fitness(hy.population.get(0).dna);

			hy.run();

		} else if (args[0].equalsIgnoreCase("extract")) {
			// load the best

			LinkedList<AllShop> tmpAllShop = new LinkedList<AllShop>();
			LinkedList<SellableType> tmpSell = new LinkedList<SellableType>();
			LinkedList<LV1000Type> tmpLV = new LinkedList<LV1000Type>();

			Core code = new Core();
			code.dnaDecoder(seo.get(0).dna, tmpAllShop, tmpSell, tmpLV);
			code.save_tmpSell(tmpSell);
			code.save_tmpAllShop(tmpAllShop);
			code.save_tmpLV(tmpLV);

		}

		// hy.run();
	}

}
