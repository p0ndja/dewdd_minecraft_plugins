package a_dewdd_skyblock_lv_1000_generator;

import java.util.LinkedList;

import ga_optimization_api.Chromosome;

public class Main {

	public static Core co;
	public static int populationSize = 10;
	
	public static void main(String[] args) {

		co = new Core();

		/*
		 * LinkedList<SellableType> sorted = co.sortSell(co.sell);
		 * 
		 * d.pl(""); d.pl(""); d.pl("sellable max " + sorted.size());
		 * 
		 * for (int i = 0; i < sorted.size(); i++) {
		 * 
		 * d.pl(sorted.get(i).theName +
		 * 
		 * ":" + sorted.get(i).data + ":" + sorted.get(i).maxStack + ":" +
		 * co.convertTimeToString(sorted.get(i).timeToGet));
		 * 
		 * 
		 * }
		 */

		d.pl("");
		d.pl("*******************");
		d.pl("");

		co.loadMissionBlockFile();

		co.loadSellableFile();
		// search the all block in game don't have block from sellable

		for (int i = 0; i < Core.sellAsList.size(); i++) {
			SellableType e = Core.sellAsList.get(i);
			AllBlockInGameType e2 = Core.allBlockInGameAsList.get(e.index);
			// search in
			boolean foundx = false;
			for (int j = 0; j < Core.allBlockInGameAsList.size(); j++) {
				AllBlockInGameType f = Core.allBlockInGameAsList.get(j);

				if (e2.theName.equalsIgnoreCase(f.theName) && e2.data == f.data) {
					foundx = true;
					break;
				}
			}

			if (foundx == false) {
				d.pl("sellable not in the list " + e2.theName + ":" + e2.data);

			}
		}

		LinkedList<AllBlockInGameType> mis = Core.allBlockInGameAsList;

		d.pl("*** mission size = " + mis.size());

		/*
		 * for (int i = 0 ; i < mis.size() ; i++ ){ d.pl( mis.get(i).theName +
		 * 
		 * ":" + mis.get(i).data + ":" + mis.get(i).maxStack + ":" + (
		 * mis.get(i).isBlock) ) ;
		 * 
		 * }
		 */

		HybridOverride hy = new HybridOverride();

		hy.setChromosomeLength(Core.dnaSize);
		hy.setPopulationSize(populationSize);
		hy.setRunCount(1000000);

		// set chromosome here

		LinkedList<Chromosome> seo = hy.loadAndSortTheBestFromRamdisk();
		hy.setAllChromosomeGen1(seo);
		hy.prepareToRunGA();

		EventListenerOverride evn = new EventListenerOverride();

		hy.registerEvent(evn);

		// hy.fitness(hy.population.get(0).dna);

		hy.run();
		// hy.run();
	}

}
