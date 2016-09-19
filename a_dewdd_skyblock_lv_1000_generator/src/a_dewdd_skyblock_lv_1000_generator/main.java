package a_dewdd_skyblock_lv_1000_generator;

import java.util.LinkedList;



import ga_optimization_api.Hybrid;

class HybridOverride extends Hybrid{
	public Core co; 
	
	
	
	@Override
	public double fitness(double dna[]) {
		LinkedList<AllShop> tmpAllShop = new LinkedList<AllShop>();
		LinkedList<SellableType> tmpSell = new LinkedList<SellableType>();
		LinkedList<LV1000Type> tmpLV =  new LinkedList<LV1000Type>();
		LinkedList<Double> tmpReading  = new LinkedList<Double>();
		
		co.dnaDecoder(dna,   tmpAllShop,
				tmpSell,  tmpLV,  tmpReading,
				0, 0);
		
		for (int i = 0 ; i < tmpSell.size() ; i ++) {
			d.pl("tmpSell : " + i + " = " + tmpSell.get(i).theName + ":" + tmpSell.get(i).data + ":" + tmpSell.get(i).timeToGet +  ":"  + tmpSell.get(i).sellPerPrice);
		}
		
		
		// Shop
		for (int i = 0 ; i < tmpAllShop.size() ; i ++ ){
			
			d.pl("tmpAllShop ID " + i );
			
			d.pl("PlayPrice " + " = " + tmpAllShop.get(i).PlayPrice);
			
			for (int j = 0 ; j < tmpAllShop.get(i).size ; j ++ ) {
				d.pl("Slot " + " = " + tmpAllShop.get(i).Item[j] + ":" + tmpAllShop.get(i).data[j] 
						+ ":" + tmpAllShop.get(i).amount[j]);
				
			}
			
		}
		
		//LV
		
		for (int i = 0 ; i < tmpLV.size() ; i ++ ){
			
			d.pl("tmpLV ID " + i );
			
			
			for (int j = 0 ; j < tmpLV.get(i).needSize ; j ++ ) {
				d.pl("need slot " + j + " = " + tmpLV.get(i).needItem[j] + ":" + tmpLV.get(i).needData[j] 
						+ ":" + tmpLV.get(i).needAmount[j]);
				
			}
			
			for (int j = 0 ; j < tmpLV.get(i).rewardSize ; j ++ ) {
				d.pl("reward slot " + j + " = " + tmpLV.get(i).rewardItem[j] + ":" + tmpLV.get(i).rewardData[j] 
						+ ":" + tmpLV.get(i).rewardAmount[j]);
				
			}
		}
		
		// ...............................................
		// simulating
		
		
		
		
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
		/*	d.pl(sorted.get(i).theName +

			":" + sorted.get(i).data + ":" + sorted.get(i).maxStack + ":"
					+ co.convertTimeToString(sorted.get(i).timeToGet));*/
			
		}
		
		d.pl("");
		d.pl("*******************");
		d.pl("");
		
		co.loadMissionBlockFile();
		LinkedList<AllBlockInGameType>  mis = co.mission;
		
		d.pl("*** mission size = " + mis.size());
		
		/*for (int i = 0 ; i < mis.size() ; i++ ){
			d.pl( mis.get(i).theName  +
					
					":" + mis.get(i).data + ":" + mis.get(i).maxStack +
					":" + ( mis.get(i).isBlock) ) ;
			
		}*/
		
		
		
		
		HybridOverride hy = new HybridOverride();
		hy.co = co;
		
		hy.setChromosomeLength(Core.dnaSize);
		hy.setFitnessStop(100);
		hy.setPopulationSize(1000);
		hy.setRunCount(1000);

		hy.prepareToRunGA();
		
		hy.fitness( hy.population.get(0).dna);
		 
		
		
		
		//hy.run();
	}

}
