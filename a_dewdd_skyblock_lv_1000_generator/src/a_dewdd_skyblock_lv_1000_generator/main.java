package a_dewdd_skyblock_lv_1000_generator;

import java.util.LinkedList;



import ga_optimization_api.Hybrid;

class HybridOverride extends Hybrid{
	
	@Override
	public double fitness(double dna[]) {
		return 0;
	}
}

public class main {
	
	

	public static void main(String[] args) {
		
	
		
		Core co = new Core();
		co.loadSellableFile();
		LinkedList<SellableType> sorted = co.sortSell(co.sell);
		
			d.pl("");
			d.pl("");
		d.pl("sellable max " + sorted.size());
		
		for (int i = 0 ; i < sorted.size() ; i++ ){
			d.pl( sorted.get(i).theName  +
					
					":" + sorted.get(i).data + ":" + sorted.get(i).maxStack +
					":" + co.convertTimeToString( sorted.get(i).timeToGet) ) ;
			
		}
		
		d.pl("");
		d.pl("*******************");
		d.pl("");
		
		co.loadMissionBlockFile();
		LinkedList<MissionType>  mis = co.mission;
		
		d.pl("*** mission size = " + mis.size());
		
		/*for (int i = 0 ; i < mis.size() ; i++ ){
			d.pl( mis.get(i).theName  +
					
					":" + mis.get(i).data + ":" + mis.get(i).maxStack +
					":" + ( mis.get(i).isBlock) ) ;
			
		}*/
		
		
		
		
		Hybrid hy = new Hybrid();
		hy.setChromosomeLength(5);
		hy.setFitnessStop(100);
		hy.setPopulationSize(1000);
		hy.setRunCount(1000);

		hy.prepareToRunGA();

		//hy.run();
	}

}
