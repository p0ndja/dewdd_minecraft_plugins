package a_dewdd_skyblock_lv_1000_generator;

import java.util.LinkedList;
import java.util.Random;

import ga_optimization_api.Hybrid;
import ga_optimization_api.HybridMultithreading;

public class HybridOverride extends HybridMultithreading {

	public static int maxUnUnique = 0;

	@Override
	public double fitness(double dna[]) {
		LinkedList<AllShop> tmpAllShop = new LinkedList<AllShop>();
		LinkedList<SellableType> tmpSell = new LinkedList<SellableType>();
		LinkedList<LV1000Type> tmpLV = new LinkedList<LV1000Type>();

		Core code = new Core();
		code.dnaDecoder(dna, tmpAllShop, tmpSell, tmpLV);


		boolean printPls = false;

		for (int i = 0; i < tmpSell.size(); i++) {

			if (printPls == true) {
				AllBlockInGameType ec = Core.allBlockInGameAsList.get(tmpSell.get(i).index);
				d.pl("tmpSell : " + i + " = " + ec.theName + ":" + ec.data + ":" + tmpSell.get(i).timeToGet + ":"
						+ tmpSell.get(i).sellPerPrice);

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
					AllBlockInGameType eco = Core.allBlockInGameAsList.get(tmpsubLV.needIndex[j]);

					d.pl("need slot " + j + " = " + eco.theName + ":" + eco.data + ":" + tmpsubLV.needAmount[j]);
				}

			}

			for (int j = 0; j < tmpLV.get(i).rewardSize; j++) {
				LV1000Type tmpsubLV = tmpLV.get(i);

				if (printPls == true) {
					AllBlockInGameType eco = Core.allBlockInGameAsList.get(tmpsubLV.rewardIndex[j]);

					d.pl("reward slot " + j + " = " + eco.theName + ":" + eco.data + ":"
							+ tmpLV.get(i).rewardAmount[j]);
				}

			}
		}

		// check that all level include all unique item

		boolean needSlotUsedIt[] = new boolean[Core.allBlockInGameAsList.size()];
		for (int i = 0; i < Core.allBlockInGameAsList.size(); i++) {
			needSlotUsedIt[i] = false;
		}

		boolean rewardSlotUsedIt[] = new boolean[Core.allBlockInGameAsList.size()];
		for (int i = 0; i < Core.allBlockInGameAsList.size(); i++) {
			rewardSlotUsedIt[i] = false;
		}

		int duplicate = 0;

		for (int i = 0; i < tmpLV.size(); i++) {
			if (printPls == true) {
				d.pl("tmpLV ID " + i);
			}
			LV1000Type curLV = tmpLV.get(i);

			for (int j = 0; j < curLV.needSize; j++) {
				AllBlockInGameType need = Core.allBlockInGameAsList.get(curLV.needIndex[j]);

				if (printPls == true) {

					d.pl("need slot " + j + " = " + need.theName + ":" + need.data + ":" + curLV.needAmount[j]);
				}

				for (int k = 0; k < Core.allBlockInGameAsList.size(); k++) {

					if (Core.allBlockInGameAsList.get(k).data == need.data)
						if (Core.allBlockInGameAsList.get(k).theName.equalsIgnoreCase(need.theName)) {

							if (needSlotUsedIt[k] == true) {
								if (printPls == true) {
									d.pl(" **  need dupli " + need.theName + ":" + need.data);
								}
								duplicate++;
							}
							needSlotUsedIt[k] = true;
							break;
						}

				}

			}

			if (printPls == true) {
				d.pl(" >>> need duplicate " + duplicate);
			}
			// duplicate = 0;

			for (int j = 0; j < curLV.rewardSize; j++) {
				AllBlockInGameType reward = Core.allBlockInGameAsList.get(curLV.rewardIndex[j]);

				if (printPls == true) {
					d.pl("reward slot " + j + " = " + reward.theName + ":" + reward.data + ":" + curLV.rewardAmount[j]);
				}

				for (int k = 0; k < Core.allBlockInGameAsList.size(); k++) {

					if (Core.allBlockInGameAsList.get(k).data == reward.data)
						if (Core.allBlockInGameAsList.get(k).theName.equalsIgnoreCase(reward.theName)) {
							if (rewardSlotUsedIt[k] == true) {
								if (printPls == true) {
									d.pl(" *** ** reward dupli " + reward.theName + ":" + reward.data);
								}
								// duplicate++;
							}
							rewardSlotUsedIt[k] = true;
							break;
						}

				}

			}

			// d.pl(" >>> reward duplicate " + duplicate);
		}

		int countTrue = 0;
		for (int i = 0; i < Core.allBlockInGameAsList.size(); i++) {
			if (needSlotUsedIt[i] == true) {
				countTrue++;
			}

			if (rewardSlotUsedIt[i] == true) {
				// countTrue++;
			}

		}

		if (countTrue > maxUnUnique) {
			maxUnUnique = countTrue;

			if (printPls == true)d.pl("maxUnique " + countTrue);
		}

		if (countTrue < Core.allBlockInGameAsList.size()) {
			d.pl(" >>> reward duplicate " + duplicate);
			d.pl("countTrue < Max Unique Item " + countTrue + "/" + (Core.allBlockInGameAsList.size() * 2));
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

		// ...

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
		for (int i = 0; i < Core.sellAsList.size(); i++) {
			SellableType se = Core.sellAsList.get(i);
			SellableType seTmpSe = tmpSell.get(i);

			double tmpMoney = 0;
			tmpMoney = se.allItemYouCanFind * seTmpSe.sellPerPrice;
			if (printPls == true)d.pl("sell " + se.allItemYouCanFind + " * " + seTmpSe.sellPerPrice + " = " + (tmpMoney) +

			" index " + se.index);
			ps.money += tmpMoney;

		}

		if (printPls == true)d.pl("all money you have " + ps.money);

		double psMaxMoney = ps.money;

		int curLVLoop = 0;

		Random rnd = new Random();

		boolean moneyNotFoundxD = false;

		while (curLVLoop < tmpLV.size() && moneyNotFoundxD == false) {
if (printPls == true)d.pl("curLVLoop " + curLVLoop);
			LV1000Type curLV = tmpLV.get(curLVLoop);

			// bought = item to finish lv

			boolean notFill = true;
			while (notFill == true && moneyNotFoundxD == false) {
				if (printPls == true)d.pl("not fill " + notFill + " , moneyNotFound " + moneyNotFoundxD);
				// check are there item not fill
				notFill = false;

				for (int j = 0; j < curLV.needSize; j++) {
					AllBlockInGameType itm = Core.allBlockInGameAsList.get(curLV.needIndex[j]);

					AllBlockInGameType myInv = ps.allMyInventory.get(itm.getIDData());
					notFill = notFillyet(itm, myInv, curLV.needAmount[j]);
					if (notFill == true) {
						break;
					}

				}

				if (notFill == true) {
					// d.pl("not fill = true");

					// random bought item
					int ranbuy = -1;

					do {
						ranbuy = rnd.nextInt(curLV.needSize);

						AllBlockInGameType itm = Core.allBlockInGameAsList.get(curLV.needIndex[ranbuy]);
						AllBlockInGameType myInv = ps.allMyInventory.get(itm.getIDData());

						boolean notFillRan = notFillyet(itm, myInv, curLV.needAmount[ranbuy]);
						if (notFillRan == false) {
							ranbuy = -1;
						}

					} while (ranbuy < 0);

					if (printPls == true)d.pl("ranbuy done " + ranbuy);

					// after got random not fill index
					// trying to buy it

					// check money

					// search item in shop
					AllBlockInGameType itm = Core.allBlockInGameAsList.get(curLV.needIndex[ranbuy]);

					if (printPls == true)d.pl("searching this item in shop " + itm.getIDData());

					boolean foundInShop = false;
					for (int c = 0; c < tmpAllShop.size(); c++) {
						AllShop ex = tmpAllShop.get(c);

						for (int c2 = 0; c2 < ex.size; c2++) {
							if (ex.item[c2] == null) {
								d.pl("ex item == null");
							}
							if (ex.item[c2].equalsIgnoreCase(itm.theName)) {
								if (ex.data[c2] == itm.data) {
									foundInShop = true;
									// buy this item

									if (ps.money >= ex.playPrice) {
										// bet it xD (bet is bad thing it the
										// world , even in my server)

										// just + amount , - money
										int betid = rnd.nextInt(ex.size);

										ps.money -= ex.playPrice;
										if (printPls == true)d.pl("beting " + betid + " , price " + ex.playPrice + " , money left "
												+ ps.money);

										String itName = ex.item[betid] + ":" + ex.data[betid];

										AllBlockInGameType ff = ps.allMyInventory.get(itName);
										if (ff == null) {
											d.pl("ff == null");
										}

										ff.curAmount += ex.amount[c2];

										break;

									} else { // don't have money left
										moneyNotFoundxD = true;
										break;

									}

								}
							}
						}

					} // loop all shop

					if (printPls == true)d.pl("foundInShop " + foundInShop);
					if (foundInShop == false) {
						// print all item in shop
						for (int i = 0; i < tmpAllShop.size(); i++) {
							AllShop shp = tmpAllShop.get(i);
							for (int j = 0; j < shp.size; j++) {

								if (printPls == true)d.pl(i + "/" + j + "  = " + shp.item[j] + ":" + shp.data[j] + " ? searching "
										+ itm.getIDData());
								if ((shp.item + ":" + shp.data).equalsIgnoreCase(itm.getIDData())) {
									d.pl("found it !!");
									break;
								}
							}
						}

						Exception e = new Exception("error item lost");
						e.printStackTrace();
						System.exit(0);
					}

				} // not fill true trying to buy
			}

			if (notFill == false) {
				curLVLoop++;
			}
			ps.curSecond++;

		}

		// fitness
		// level done / max level
		// money left need to bo 0
		// after end game atTheEndItem need to be exactly
		// maxMoney should nearly at maxMoney

		double fitness = 0;

		double fitCurLV = curLVLoop * 100;
		double fitMoneyLeft = -Math.abs(ps.money);
		double fitMaxMoney = -Math.abs(psMaxMoney - Core.maxMoney);

		double fitAtTheEndItem = 0;

		for (int i = 0; i < Core.allBlockInGameAsList.size(); i++) {
			AllBlockInGameType eg = Core.allBlockInGameAsList.get(i);
			AllBlockInGameType myeg = ps.allMyInventory.get(eg.getIDData());

			if (eg.atTheEndNeed > 0) {
				double theDiff = Math.abs(eg.atTheEndNeed - myeg.curAmount);
				fitAtTheEndItem -= theDiff;
				continue;

			}
		}

		fitness = fitCurLV + fitMoneyLeft + fitMaxMoney + fitAtTheEndItem;
		d.pl("fitness " + fitness + ", f curLV " + fitCurLV + " , f money left " + fitMoneyLeft + " ,f Max money "
				+ fitMaxMoney + " , fit at the end " + fitAtTheEndItem);

		return fitness;

	}

	public boolean notFillyet(AllBlockInGameType itm, AllBlockInGameType myInv, int needAmount) {
		boolean notFill = false;
		if (myInv.curAmount < needAmount) {
			notFill = true;

		}

		return notFill;
	}

}
