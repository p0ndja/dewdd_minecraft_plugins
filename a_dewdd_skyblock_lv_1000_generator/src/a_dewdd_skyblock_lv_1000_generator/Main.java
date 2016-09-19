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

		boolean printPls = false;

		for (int i = 0; i < tmpSell.size(); i++) {

			if (printPls == true)
				d.pl("tmpSell : " + i + " = " + tmpSell.get(i).theName + ":" + tmpSell.get(i).data + ":"
						+ tmpSell.get(i).timeToGet + ":" + tmpSell.get(i).sellPerPrice);
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
				if (printPls == true) {
					d.pl("need slot " + j + " = " + tmpLV.get(i).needItem[j] + ":" + tmpLV.get(i).needData[j] + ":"
							+ tmpLV.get(i).needAmount[j]);
				}

			}

			for (int j = 0; j < tmpLV.get(i).rewardSize; j++) {
				if (printPls == true) {
					d.pl("reward slot " + j + " = " + tmpLV.get(i).rewardItem[j] + ":" + tmpLV.get(i).rewardData[j]
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

			for (int j = 0; j < tmpLV.get(i).needSize; j++) {
				if (printPls == true) {
					d.pl("need slot " + j + " = " + tmpLV.get(i).needItem[j] + ":" + tmpLV.get(i).needData[j] + ":"
							+ tmpLV.get(i).needAmount[j]);
				}

				for (int k = 0; k < Main.co.allBlockInGameAsList.size(); k++) {
					if (Main.co.allBlockInGameAsList.get(k).theName.equalsIgnoreCase(tmpLV.get(i).needItem[j])) {

						if (needSlotUsedIt[k] == true) {
							duplicate++;
						}
						needSlotUsedIt[k] = true;
						break;
					}

				}

			}

			for (int j = 0; j < tmpLV.get(i).rewardSize; j++) {

				if (printPls == true) {
					d.pl("reward slot " + j + " = " + tmpLV.get(i).rewardItem[j] + ":" + tmpLV.get(i).rewardData[j]
							+ ":" + tmpLV.get(i).rewardAmount[j]);
				}

				for (int k = 0; k < Main.co.allBlockInGameAsList.size(); k++) {
					if (Main.co.allBlockInGameAsList.get(k).theName.equalsIgnoreCase(tmpLV.get(i).rewardItem[j])) {
						if (rewardSlotUsedIt[k] == true) {
							duplicate++;
						}
						rewardSlotUsedIt[k] = true;
						break;
					}

				}

			}
		}

		int countTrue = 0;
		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			if (needSlotUsedIt[i] == true) {
				countTrue++;
			}

			if (rewardSlotUsedIt[i] == true) {
				countTrue++;
			}

		}

		if (countTrue > maxUnUnique) {
			maxUnUnique = countTrue;

			d.pl("maxUnique " + countTrue);
		}

		if (countTrue < Main.co.allBlockInGameAsList.size() * 2) {
			d.pl("duplicate " + duplicate);
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

		// grass , dirt

		while (ps.curSecond <= Core.MaxTickToCompleteAllLV) {
			// d.pl("curSecond " + ps.curSecond);
			ps.letPlantAllThingAsItCan();

			// glow all plant

			for (Farm f : ps.farmMap.values()) {
				for (int i = 0; i < f.sizeFarm; i++) {
					f.countTick[i]++;

					SellableType tmpTimeToGlow = Main.co.sell.get(f.itemIdData);
					if (tmpTimeToGlow == null) {
						d.pl("ERROR > fitness   tmpTimeToGlow == null ");
						// Exception e = new Exception();
						return 0;

					} else {
						if (f.countTick[i] >= tmpTimeToGlow.timeToGet) {
							// now can be harvest
							f.countTick[i] = 0;
							ps.allMyInventory.get(f.itemIdData).curAmount++;

						}
					}

				}
			}

			ps.curSecond++;
		}
		return 0;

	}

}

public class Main {

	public static Core co;

	public static void main(String[] args) {

		co = new Core();

		co.loadSellableFile();
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
