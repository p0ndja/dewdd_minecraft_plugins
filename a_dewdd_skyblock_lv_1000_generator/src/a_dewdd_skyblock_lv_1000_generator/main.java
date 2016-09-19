package a_dewdd_skyblock_lv_1000_generator;

import java.util.LinkedList;

import ga_optimization_api.Hybrid;

public class main {
	
	

	public static void main(String[] args) {
		
		Core co = new Core();
		co.loadAndSortSellableFileAndSaveIt();
		LinkedList<SellableType> sorted = co.sortSell(co.sell);
		
		d.pl("");
		
		for (int i = 0 ; i < sorted.size() ; i++ ){
			d.pl("" + i + " = " + sorted.get(i).theName  +
					
					":" + sorted.get(i).data + ":" + sorted.get(i).maxStack +
					":" + co.convertTimeToString( sorted.get(i).timeToGet) ) ;
			
		}
		
		
		Hybrid hy = new Hybrid();
		hy.setChromosomeLength(5);
		hy.setFitnessStop(100);
		hy.setPopulationSize(1000);
		hy.setRunCount(1000);

		hy.prepareToRunGA();

		//hy.run();
	}

}
