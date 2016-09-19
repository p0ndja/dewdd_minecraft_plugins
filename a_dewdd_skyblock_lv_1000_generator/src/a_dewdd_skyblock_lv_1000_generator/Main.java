package a_dewdd_skyblock_lv_1000_generator;

import java.util.LinkedList;

import ga_optimization_api.Hybrid;

class HybridOverride extends Hybrid {

	public static int maxUnUnique = 0;

	@Override
	public double fitness(double dna[]) {
		LinkedList<AllShop> tmpAllShop = new LinkedList<AllShop>();
		LinkedList<SellableType> tmpSell = new LinkedList<SellableType>();
		LinkedList<LV1000Type> tmpLV = new LinkedList<LV1000Type>();

		
		
		Main.co.dnaDecoder(dna, tmpAllShop, tmpSell, tmpLV);

		boolean printPls = true;

		for (int i = 0; i < tmpSell.size(); i++) {

			if (printPls == true) {
				AllBlockInGameType ec = Main.co.allBlockInGameAsList.get(tmpSell.get(i).index);
				d.pl("tmpSell : " + i + " = " + ec.theName + ":" + ec.data + ":"
						+ tmpSell.get(i).timeToGet + ":" + tmpSell.get(i).sellPerPrice);
				
				
			}
		}

		// Shop
		for (int i = 0; i < tmpAllShop.size(); i++) {

			if (printPls == true) {
				d.pl("tmpAllShop ID " + i);
			}

			if (printPls == true) {
				d.pl("PlayPrice " + " = " + tmpAllShop.get(i).playPrice);
			}
			for (int j = 0; j < tmpAllShop.get(i).size; j++) {
				if (printPls == true) {
					d.pl("Slot " + " = " + tmpAllShop.get(i).item[j] + ":" + tmpAllShop.get(i).data[j] + ":"
							+ tmpAllShop.get(i).amount[j]);
				}

			}

		}

		// LV

		for (int i = 0; i < tmpLV.size(); i++) {
			if (printPls == true) {
				d.pl("tmpLV ID " + i);
			}

			for (int j = 0; j < tmpLV.get(i).needSize; j++) {
				LV1000Type tmpsubLV = tmpLV.get(i);
				
				
				if (printPls == true) {
					AllBlockInGameType eco = Main.co.allBlockInGameAsList.get(tmpsubLV.needIndex[j]);
					
					d.pl("need slot " + j + " = " + eco.theName + ":" + eco.data + ":"
							+ tmpsubLV.needAmount[j]);
				}
				
				

			}

			for (int j = 0; j < tmpLV.get(i).rewardSize; j++) {
				LV1000Type tmpsubLV = tmpLV.get(i);
				
				if (printPls == true) {
					AllBlockInGameType eco = Main.co.allBlockInGameAsList.get(tmpsubLV.rewardIndex[j]);
					
					
					d.pl("reward slot " + j + " = " + eco.theName + ":" + eco.data
							+ ":" + tmpLV.get(i).rewardAmount[j]);
				}

			}
		}

		// check that all level include all unique item

		boolean needSlotUsedIt[] = new boolean[Main.co.allBlockInGameAsList.size()];
		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			needSlotUsedIt[i] = false;
		}

		boolean rewardSlotUsedIt[] = new boolean[Main.co.allBlockInGameAsList.size()];
		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			rewardSlotUsedIt[i] = false;
		}

		int duplicate = 0;

		for (int i = 0; i < tmpLV.size(); i++) {
			if (printPls == true) {
				d.pl("tmpLV ID " + i);
			}
	LV1000Type curLV = tmpLV.get(i);
	
			for (int j = 0; j < curLV.needSize; j++) {
				AllBlockInGameType need = Main.co.allBlockInGameAsList.get( curLV.needIndex[j]);
				
				if (printPls == true) {
					
				
					d.pl("need slot " + j + " = " + need.theName + ":" + need.data + ":"
							+ curLV.needAmount[j]);
				}

				for (int k = 0; k < Main.co.allBlockInGameAsList.size(); k++) {
					
					if (Main.co.allBlockInGameAsList.get(k).data == need.data)
					if (Main.co.allBlockInGameAsList.get(k).theName.equalsIgnoreCase(need.theName)) {

						if (needSlotUsedIt[k] == true) {
							d.pl(" **  need dupli " + need.theName + ":" + need.data);
							duplicate++;
						}
						needSlotUsedIt[k] = true;
						break;
					}

				}

			}
			
			d.pl(" >>> need duplicate " + duplicate);
		//	duplicate = 0;

			for (int j = 0; j < curLV.rewardSize; j++) {
				AllBlockInGameType reward = Main.co.allBlockInGameAsList.get( curLV.rewardIndex[j]);
				

				if (printPls == true) {
					d.pl("reward slot " + j + " = " + reward.theName + ":" + reward.data
							+ ":" + curLV.rewardAmount[j]);
				}

				for (int k = 0; k < Main.co.allBlockInGameAsList.size(); k++) {
					
					if (Main.co.allBlockInGameAsList.get(k).data == reward.data)
					if (Main.co.allBlockInGameAsList.get(k).theName.equalsIgnoreCase(reward.theName)) {
						if (rewardSlotUsedIt[k] == true) {
							d.pl(" *** ** reward dupli " + reward.theName + ":" + reward.data);
							//duplicate++;
						}
						rewardSlotUsedIt[k] = true;
						break;
					}

				}

			}
			
			//d.pl(" >>> reward duplicate " + duplicate);
		}
		
		

		int countTrue = 0;
		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			if (needSlotUsedIt[i] == true) {
				countTrue++;
			}

			if (rewardSlotUsedIt[i] == true) {
				//countTrue++;
			}

		}

		if (countTrue > maxUnUnique) {
			maxUnUnique = countTrue;

			d.pl("maxUnique " + countTrue);
		}

		if (countTrue < Main.co.allBlockInGameAsList.size() ) {
			d.pl(" >>> reward duplicate " + duplicate);
			d.pl("countTrue < Max Unique Item " + countTrue + "/" + (Main.co.allBlockInGameAsList.size() * 2));
			return countTrue;
			// break;
		}

		// ...............................................
		// simulating

		PlayerSimulating ps = new PlayerSimulating();

		AllBlockInGameType a = ps.allMyInventory.get("SAPLING:0");
		a.curAmount = 3;

		a = ps.allMyInventory.get("SAPLING:1");
		a.curAmount = 3;

		a = ps.allMyInventory.get("SAPLING:2");
		a.curAmount = 3;

		a = ps.allMyInventory.get("SAPLING:3");
		a.curAmount = 3;

		a = ps.allMyInventory.get("SAPLING:4");
		a.curAmount = 3;

		a = ps.allMyInventory.get("SAPLING:5");
		a.curAmount = 3;

		a = ps.allMyInventory.get("PUMPKIN:0");
		a.curAmount = 10;

		a = ps.allMyInventory.get("WHEAT:0");
		a.curAmount = 10;

		a = ps.allMyInventory.get("POTATO_ITEM:0");
		a.curAmount = 10;
		
		//...

		a = ps.allMyInventory.get("SUGAR_CANE:0");
		a.curAmount = 10;

		a = ps.allMyInventory.get("BROWN_MUSHROOM:0");
		a.curAmount = 2;

		a = ps.allMyInventory.get("CACTUS:0");
		a.curAmount = 1;

		a = ps.allMyInventory.get("SAND:0");
		a.curAmount = 7;

		a = ps.allMyInventory.get("CARROT_ITEM:0");
		a.curAmount = 10;

		a = ps.allMyInventory.get("LAVA_BUCKET:0");
		a.curAmount = 10;

		a = ps.allMyInventory.get("RED_MUSHROOM:0");
		a.curAmount = 2;
		a = ps.allMyInventory.get("MELON:0");
		a.curAmount = 10;
		a = ps.allMyInventory.get("TORCH:0");
		a.curAmount = 3;

		a = ps.allMyInventory.get("GRASS:0");
		a.curAmount = 50;

		

		// get all money
		ps.money = 0;
		for (int i = 0; i < Main.co.sellAsList.size() ; i ++ ) {
			SellableType se = Main.co.sellAsList.get(i);
			SellableType seTmpSe = tmpSell.get(i);
			
			d.pl("sell " + se.allItemYouCanFind + " * " + seTmpSe.sellPerPrice + " index " + se.index);
			
			if (se.allItemYouCanFind > 0 ) {
				double tmpMoney  = 0;
				tmpMoney = se.allItemYouCanFind * seTmpSe.sellPerPrice;
				ps.money += tmpMoney;
				continue;
				
			}
		}
		
		d.pl("all money you have " + ps.money);
		
		boolean isGameDone = false;
		while (isGameDone == false) {
			
			

			ps.curSecond++;
			
			isGameDone = isGameDone(tmpAllShop,tmpSell, tmpLV);
		}
		return 0;

	}
	
	
	public boolean isGameDone(LinkedList<AllShop> tmpAllShop ,
	LinkedList<SellableType> tmpSell ,
	LinkedList<LV1000Type> tmpLV ) {
		
		return false;
	}

}

public class Main {

	public static Core co;

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
		
		for (int i = 0 ; i < co.sellAsList.size() ; i ++ ) {
			SellableType e = co.sellAsList.get(i);
			AllBlockInGameType e2 = co.allBlockInGameAsList.get(e.index);
			// search in
			boolean foundx = false;
			for (int j = 0; j < co.allBlockInGameAsList.size() ; j ++ ) {
				AllBlockInGameType f = co.allBlockInGameAsList.get(j);
				
				
				if (e2.theName.equalsIgnoreCase(f.theName) && e2.data == f.data) {
					foundx = true;
					break;
				}
			}
			
			if (foundx == false ) {
				d.pl("sellable not in the list " + e2.theName + ":" + e2.data);
				
			}
		}
		
		
		LinkedList<AllBlockInGameType> mis = co.allBlockInGameAsList;

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
		hy.setFitnessStop(100);
		hy.setPopulationSize(1000);
		hy.setRunCount(1000);

		hy.prepareToRunGA();

		hy.fitness(hy.population.get(0).dna);

		// hy.run();
		// hy.run();
	}

}
