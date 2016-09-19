package a_dewdd_skyblock_lv_1000_generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

public class Core {
	class ParameterAllItemInShopType {
		LinkedList<AllBlockInGameType> outputAllItemInShop;

		boolean tmpBoolean[];
	}

	class ParameterLVType {
		public LinkedList<LV1000Type> outputLV = new LinkedList<LV1000Type>();

	}

	class ParameterRandomAmountItem {
		// int [] index = new int[allBlockInGameAsListSize];
		int[] amount;

	}

	class ParameterRandomUniqueItem {
		// int [] index = new int[allBlockInGameAsListSize];
		int[] index;
	}

	class ParameterShopPriceAmountOfItemInShopType {
		int shopSlotMax;

		double[] price;
		int[] amount;

	}

	class ParameterShopPriceToAllShopType {
		LinkedList<AllBlockInGameType> inputAllItemInShop;
		double[] price;
		int[] amount;
		int shopSlotMax;

		LinkedList<AllShop> outputAllShop;
	}

	public static boolean printpls = false;

	public static String sellablePath = File.separator + "ramdisk" + File.separator + "sellableblock.txt";
	public static String tmpSellPath = File.separator + "ramdisk" + File.separator + "ptdew_dewdd_sell.txt";
	public static String tmpAllShopPath = File.separator + "ramdisk" + File.separator + "ptdew_dewdd_allshop.txt";

	public static String tmpLVPath = File.separator + "ramdisk" + File.separator + "ptdew_dewdd_lv.txt";

	public static String missionPath = File.separator + "ramdisk" + File.separator + "missionblock.txt";
	public static int dnaSize = 4300;

	public static int maxItemForCompleteMission = 5;
	public static int minItemForCompleteMission = 2;

	public static int maxRewardDiffBlockType = 10;

	public static int minRewardDiffBlockType = 1;

	public static int maxShopSize = 10;

	public static int minShopSize = 3;
	public static int swapMultipy = 100;

	public static double SellMaxCost = 10;

	public static double ShopMaxCost = 100;

	public static double maxMoney = 100000;
	public static double moneyLeft = 5000;

	public static int maxLV = 100;
	public static HashMap<String, SellableType> sell = new HashMap<String, SellableType>();

	// 415 unique item

	public static HashMap<String, AllBlockInGameType> allBlockInGame = new HashMap<String, AllBlockInGameType>();

	public static LinkedList<SellableType> sellAsList = new LinkedList<SellableType>();

	public static AllBlockInGameType[] allBlockInGameAsList = new AllBlockInGameType[1000];
	public static int allBlockInGameAsListSize = 0;

	private int curChro = 0;

	private double[] chromosome;

	private int curMissionItemSwapPosition;

	public void addNeedItemToLV(ParameterLVType paraLV) {
		ParameterRandomUniqueItem rUnique = new ParameterRandomUniqueItem();
		decodeRandomUniqueItem(rUnique);

		ParameterRandomAmountItem amountUniqueItemPerLV = new ParameterRandomAmountItem();
		decodeRandomSumAmount417ForLV(amountUniqueItemPerLV);

		// add need item

		// loop all level

		int cur = 0;
		int curLV = 0;
		while (cur < allBlockInGameAsListSize) {
			LV1000Type l = new LV1000Type();

			l.needAmount = new int[amountUniqueItemPerLV.amount[curLV]];
			l.needIndex = new int[amountUniqueItemPerLV.amount[curLV]];
			l.needSize = 0;

			/*
			 * d.pl("decode lv need " + curLV + " = " +
			 * amountUniqueItemPerLV.amount[curLV] + " cur chro " + curChro +
			 * " , cur " + cur);
			 */

			// add all item need it current level
			for (int j = 0; j < amountUniqueItemPerLV.amount[curLV] && cur < allBlockInGameAsListSize; j++) {
				AllBlockInGameType eof = allBlockInGameAsList[rUnique.index[cur]];

				l.needIndex[j] = rUnique.index[cur];
				l.needAmount[j] = (int) Math.round(decodeRandomGive01_() * eof.maxStack);
				if (l.needAmount[j] <= 0) {
					l.needAmount[j] = 1;
				}

				l.needSize++;
				cur++;

			}

			paraLV.outputLV.add(l);
			curLV++;
		}
	}

	public void addRewardItemToLV(ParameterLVType paraLV) {

		// each lv
		for (int i = 0; i < paraLV.outputLV.size(); i++) {
			LV1000Type e = paraLV.outputLV.get(i);

			int amountReward = 0;
			double tmp01 = decodeRandomGive01_();

			// 10 - 1 = 9
			double maxmin = (maxRewardDiffBlockType - minRewardDiffBlockType);

			amountReward = (int) (Math.round((tmp01 * maxmin)) + minRewardDiffBlockType);

			if (amountReward > maxRewardDiffBlockType)
				amountReward = maxRewardDiffBlockType;
			if (amountReward < minRewardDiffBlockType)
				amountReward = minRewardDiffBlockType;

			// after know amount

			e.rewardSize = 0;

			e.rewardAmount = new int[amountReward];
			e.rewardIndex = new int[amountReward];

			/*
			 * d.pl("decode lv reward " + i + " = " + amountReward +
			 * " cur chro " + curChro);
			 */
			for (int j = 0; j < amountReward; j++) {
				double g = decodeRandomGive01_() * allBlockInGameAsListSize;

				int index = (int) (g);
				if (index >= allBlockInGameAsListSize)
					index = allBlockInGameAsListSize - 1;

				e.rewardIndex[j] = index;
				e.rewardAmount[j] = (int) Math.round(decodeRandomGive01_() * allBlockInGameAsList[index].maxStack);

				if (e.rewardAmount[j] <= 0)
					e.rewardAmount[j] = 1;

			}

			e.rewardSize = amountReward;

		}
	}

	public double convertStringToTime(String abc) {
		String m[] = abc.split(" ");

		double cd = Double.parseDouble(m[0]);

		if (m[1].equalsIgnoreCase("h")) {
			cd = cd * 60 * 60;

		} else if (m[1].equalsIgnoreCase("m")) {
			cd = cd * 60;

		} else if (m[1].equalsIgnoreCase("s")) {
			cd = cd * 1;

		}

		return cd;

	}

	public String convertTimeToString(double abc) {
		String letter = "";
		double ceo = 0;

		if (abc >= 60 * 60) {
			letter = "h";
			ceo = abc / 60 / 60;

		} else if (abc >= 60) {
			letter = "m";

			ceo = abc / 60;
		} else if (abc >= 0) {
			letter = "s";

			ceo = abc / 60;
		}

		return ceo + " " + letter;

	}

	public void convertTmpShopPriceAmountOfItemInShopToAllShopType(ParameterShopPriceToAllShopType tmpType) {
		tmpType.outputAllShop.clear();

		// count before convert

		// check is the last shop ( have enough item >= minshop)

		if (tmpType.amount[tmpType.shopSlotMax - 1] < minShopSize) {

			for (int i = 0; i < tmpType.shopSlotMax - 1; i++) {
				if (tmpType.amount[i] < maxShopSize) {
					tmpType.amount[i]++;
					tmpType.amount[tmpType.shopSlotMax - 1]--;
					tmpType.price[tmpType.shopSlotMax - 1] = 0;

					if (tmpType.amount[tmpType.shopSlotMax - 1] == 0) {

						break;

					}

				}
			}

			tmpType.shopSlotMax--;

		}

		// to time convert to All Shop

		int curItemIndex = 0;

		for (int i = 0; i < tmpType.shopSlotMax; i++) {
			// d.pl("convert tmp loop " + i + "/" + tmpType.shopSlotMax);

			AllShop ab = new AllShop();

			ab.playPrice = tmpType.price[i];
			ab.size = tmpType.amount[i];

			ab.item = new String[ab.size];
			ab.data = new byte[ab.size];
			ab.amount = new int[ab.size];

			for (int j = 0; j < tmpType.amount[i]; j++) {
				AllBlockInGameType abigt = tmpType.inputAllItemInShop.get(curItemIndex);
				ab.item[j] = abigt.theName;
				ab.data[j] = abigt.data;
				ab.amount[j] = abigt.curAmount;

				curItemIndex++;
				/*
				 * if (curItemIndex == allBlockInGameAsListSize) { break; }
				 */
			}

			// curItemIndex += tmpType.amount[i];

			tmpType.outputAllShop.add(ab);

		}

		// check that all item in shop has all item in game

		if (printpls)
			d.pl("curItemIndex " + curItemIndex);

		// check item Lost Again
		/*
		 * for (int a = 0; a < Main.co.allBlockInGameAsListSize; a++) {
		 * AllBlockInGameType edo = Main.co.allBlockInGameAsList.get(a); boolean
		 * found = false; for (int i = 0; i < tmpType.outputAllShop.size(); i++)
		 * { AllShop as = tmpType.outputAllShop.get(i); for (int j = 0; j <
		 * as.size; j++) { if (as.item[j].equalsIgnoreCase(edo.theName)) { if
		 * (as.data[j] == edo.data) { found = true; break; } }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } if (found == false) { d.pl("a " + a + " , " + edo.getIDData());
		 * Exception exp = new Exception("error item lost");
		 * exp.printStackTrace(); System.exit(0); } }
		 */

		if (printpls)
			d.pl("converted tmpShop to AllShop ");
	}

	public double decodeRandomGive01_() {
		double tmpReadChro = 0;
		tmpReadChro = decodeRandomGiveDouble_();

		if (tmpReadChro < 0) {
			tmpReadChro = 0;
		}

		if (tmpReadChro > 1) {
			tmpReadChro = 1;
		}

		return tmpReadChro;

	}

	public double decodeRandomGiveDouble_() {
		double tmpReadChro = 0;
		tmpReadChro = Math.abs(chromosome[curChro]);
		curChro++;

		return tmpReadChro;
	}

	public void decodeRandomSumAmount417ForAllShop(ParameterRandomAmountItem para) {
		para.amount = new int[allBlockInGameAsListSize];

		for (int i = 0; i < allBlockInGameAsListSize; i++) {
			para.amount[i] = 0;
		}

		int countItem = 0;
		while (countItem < allBlockInGameAsListSize) {
			// d.pl("countItem , " + countItem + " , curChro " + curChro);

			double max = (maxShopSize - minShopSize); // 1 / 7

			double tmpReadChro = decodeRandomGive01_(); // random

			double curAmount = Math.round(tmpReadChro * max) + minShopSize; // 3
																			// -
																			// 7

			para.amount[countItem] = (int) curAmount;
			if (para.amount[countItem] <= 0) {
				para.amount[countItem] = 1;
			}

			countItem++;

			// 417 > 417
			if (countItem > allBlockInGameAsListSize) {
				int tmb = (countItem - allBlockInGameAsListSize);

				if (printpls)
					d.pl("decodeRandumSumAmount Shouldn't be here : " + tmb);

				countItem = allBlockInGameAsListSize - 1;
				para.amount[countItem] = tmb;

			}
		}

	}

	public void decodeRandomSumAmount417ForLV(ParameterRandomAmountItem para) {
		para.amount = new int[allBlockInGameAsListSize];

		for (int i = 0; i < allBlockInGameAsListSize; i++) {
			para.amount[i] = 0;
		}

		int countItem = 0;
		while (countItem < allBlockInGameAsListSize) {
			// d.pl("countItem , " + countItem + " , curChro " + curChro);

			double max = (maxItemForCompleteMission - minItemForCompleteMission); // 1
																					// /
																					// 7

			double tmpReadChro = decodeRandomGive01_(); // random

			double curAmount = Math.round(tmpReadChro * max) + minItemForCompleteMission; // 3
			// -
			// 7

			para.amount[countItem] = (int) curAmount;
			if (para.amount[countItem] <= 0) {
				para.amount[countItem] = 1;
			}

			countItem++;

			// 417 > 417
			if (countItem > allBlockInGameAsListSize) {
				int tmb = (countItem - allBlockInGameAsListSize);

				if (printpls)
					d.pl("decodeRandumSumAmount Shouldn't be here : " + tmb);

				countItem = allBlockInGameAsListSize - 1;
				para.amount[countItem] = tmb;

			}
		}

	}

	public void decodeRandomUniqueItem(ParameterRandomUniqueItem para) {
		para.index = new int[allBlockInGameAsListSize];
		boolean tmpBoolean[] = new boolean[allBlockInGameAsListSize];

		for (int i = 0; i < allBlockInGameAsListSize; i++) {
			para.index[i] = 0;
			tmpBoolean[i] = false;
		}

		int countItem = 0;
		while (countItem < allBlockInGameAsListSize) {
			double tmpReadChro = decodeRandomGiveDouble_();

			getNextUnuseMissionItem((int) (tmpReadChro * swapMultipy),

					tmpBoolean);

			para.index[countItem] = curMissionItemSwapPosition;

			countItem++;

			continue;
		}

	}

	public void decodeTmpAllItemInShop(ParameterAllItemInShopType paraItemsInShop) {
		ParameterRandomUniqueItem para = new ParameterRandomUniqueItem();

		decodeRandomUniqueItem(para);

		while (paraItemsInShop.outputAllItemInShop.size() < allBlockInGameAsListSize) {

			AllBlockInGameType bo = new AllBlockInGameType();

			int cur = para.index[paraItemsInShop.outputAllItemInShop.size()];

			bo.theName = allBlockInGameAsList[cur].theName;
			bo.data = allBlockInGameAsList[cur].data;

			// ........

			double rendomStack01 = decodeRandomGive01_();

			if (allBlockInGameAsList[cur].limitSell > 0) {
				// bo.curAmount = (int) Math.round(rendomStack01 *
				// allBlockInGameAsList[cur].maxStack);
				bo.curAmount = (int) Math.round(rendomStack01 * allBlockInGameAsList[cur].limitSell);
			} else {

				bo.curAmount = (int) Math.round(rendomStack01 * allBlockInGameAsList[cur].maxStack);
			}

			if (bo.curAmount <= 0)
				bo.curAmount = 1;

			paraItemsInShop.outputAllItemInShop.add(bo);

		}

	}

	public void decodeTmpLV(ParameterLVType paraLV) {

		// random unique item

		addNeedItemToLV(paraLV);

		addRewardItemToLV(paraLV);

	}

	public int decodeTmpSell(LinkedList<SellableType> tmpSell) {
		while (tmpSell.size() < sell.size()) {

			double tmpReadChro = decodeRandomGiveDouble_();

			SellableType x = sellAsList.get(tmpSell.size()).copyIt();

			x.sellPerPrice = tmpReadChro * SellMaxCost;
			tmpSell.add(x);

			continue;

		}

		return curChro;
	}

	public void decodeTmpShopPriceAmountOfItemInShop(ParameterShopPriceAmountOfItemInShopType paraShopPrice) {

		int countItem = 0;
		while (countItem < allBlockInGameAsListSize) {

			// price
			double tmpReadChro = decodeRandomGiveDouble_();

			paraShopPrice.price[paraShopPrice.shopSlotMax] = tmpReadChro * ShopMaxCost;

			// amount

			tmpReadChro = decodeRandomGive01_();

			// 3 - 10

			double max = (maxShopSize - minShopSize); // 1 / 7

			double curAmount = Math.round(tmpReadChro * max) + minShopSize;
			paraShopPrice.amount[paraShopPrice.shopSlotMax] = (int) curAmount;

			countItem += paraShopPrice.amount[paraShopPrice.shopSlotMax];

			if (countItem > allBlockInGameAsListSize) {
				int tmb = (countItem - allBlockInGameAsListSize);
				if (tmb < 0) {
					d.pl("tmb : " + tmb);
				}

				paraShopPrice.amount[paraShopPrice.shopSlotMax] -= tmb;
				countItem -= tmb;

			}

			paraShopPrice.shopSlotMax++;

		} // mission

		// count all item again

	}

	public void dnaDecoder(double[] chromosome, LinkedList<AllShop> tmpAllShop, LinkedList<SellableType> tmpSell,
			LinkedList<LV1000Type> tmpLV) {

		this.chromosome = chromosome;

		curChro = 0;

		// decond player sell
		decodeTmpSell(tmpSell);

		// ************************************************************************

		// decond all item in shop
		LinkedList<AllBlockInGameType> tmpAllItemInShop = new LinkedList<AllBlockInGameType>();
		tmpAllItemInShop.clear();

		ParameterAllItemInShopType tmp = new ParameterAllItemInShopType();

		tmp.outputAllItemInShop = tmpAllItemInShop;

		boolean tmpBoolean[] = new boolean[allBlockInGameAsListSize];
		for (int i = 0; i < allBlockInGameAsListSize; i++) {
			tmpBoolean[i] = false;
		}
		tmp.tmpBoolean = tmpBoolean;

		decodeTmpAllItemInShop(tmp);

		// **********************************************************************************

		// decond shop price , and amount of items in shop
		int shopSlotMax = 0;

		int maxShopBuffer = 1000;

		ParameterShopPriceAmountOfItemInShopType tmp2 = new ParameterShopPriceAmountOfItemInShopType();
		tmp2.amount = new int[maxShopBuffer];

		tmp2.price = new double[maxShopBuffer];
		tmp2.shopSlotMax = shopSlotMax;

		decodeTmpShopPriceAmountOfItemInShop(tmp2);

		shopSlotMax = tmp2.shopSlotMax;

		// *****************************************************************************************
		// convert all data to AllShop

		ParameterShopPriceToAllShopType tmp3 = new ParameterShopPriceToAllShopType();
		tmp3.amount = tmp2.amount;
		tmp3.price = tmp2.price;
		tmp3.shopSlotMax = shopSlotMax;
		tmp3.inputAllItemInShop = tmp.outputAllItemInShop;
		tmp3.outputAllShop = tmpAllShop;

		convertTmpShopPriceAmountOfItemInShopToAllShopType(tmp3);

		// ******************************************************************************************
		// check item Lost Again
		/*
		 * for (int a = 0; a < Main.co.allBlockInGameAsListSize; a++) {
		 * AllBlockInGameType edo = Main.co.allBlockInGameAsList.get(a); boolean
		 * found = false; for (int i = 0; i < tmpAllShop.size(); i++) { AllShop
		 * as = tmpAllShop.get(i); for (int j = 0; j < as.size; j++) { if
		 * (as.item[j].equalsIgnoreCase(edo.theName)) { if (as.data[j] ==
		 * edo.data) { found = true; break; } }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } if (found == false) { d.pl("a " + a + " , " + edo.getIDData());
		 * Exception exp = new Exception("error item lost");
		 * exp.printStackTrace(); System.exit(0); } }
		 */
		// decode level

		ParameterLVType tmp4 = new ParameterLVType();

		tmp4.outputLV = tmpLV;

		boolean usedItShopList[] = new boolean[Core.allBlockInGameAsListSize];

		for (int i = 0; i < Core.allBlockInGameAsListSize; i++) {
			usedItShopList[i] = false;
		}

		boolean usedItNeedList[] = new boolean[Core.allBlockInGameAsListSize];
		boolean usedItRewardList[] = new boolean[Core.allBlockInGameAsListSize];

		for (int i = 0; i < Core.allBlockInGameAsListSize; i++) {
			usedItNeedList[i] = false;
			usedItRewardList[i] = false;
		}

		decodeTmpLV(tmp4);

		// check item Lost Again
		/*
		 * for (int a = 0; a < Main.co.allBlockInGameAsListSize; a++) {
		 * AllBlockInGameType edo = Main.co.allBlockInGameAsList.get(a); boolean
		 * found = false; for (int i = 0; i < tmpAllShop.size(); i++) { AllShop
		 * as = tmpAllShop.get(i); for (int j = 0; j < as.size; j++) { if
		 * (as.item[j].equalsIgnoreCase(edo.theName)) { if (as.data[j] ==
		 * edo.data) { found = true; break; } }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } if (found == false) { d.pl("a " + a + " , " + edo.getIDData());
		 * Exception exp = new Exception("error item lost");
		 * exp.printStackTrace(); System.exit(0); } }
		 */

		if (printpls)
			d.pl("abc");
		d.pl("end curChro " + curChro);
	}

	public int getIndexFromKeyAlBlockInGame(String abc) {
		int index = -1;
		for (int i = 0; i < allBlockInGameAsListSize; i++) {
			AllBlockInGameType e = allBlockInGameAsList[i];

			String eo = e.theName + ":" + e.data;
			if (eo.equalsIgnoreCase(abc)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public void getNextBlockSwapMissionType(int swapNextxTime, int iNeedBlock, boolean usedItList[]) {
		// ineedblock 0 = block , 1 = item , 2 = both

		int returnId = -1;

		do {

			do {
				curMissionItemSwapPosition++;
				if (curMissionItemSwapPosition >= allBlockInGameAsListSize) {
					curMissionItemSwapPosition = 0;
				}

			} while (usedItList[curMissionItemSwapPosition] == true);

			if (iNeedBlock == 2) { // both
				if (swapNextxTime > 0) {
					swapNextxTime--;
					continue;
				}
				returnId = curMissionItemSwapPosition;
				usedItList[curMissionItemSwapPosition] = true;
				break;
			} else if (iNeedBlock == 0) {
				if (allBlockInGameAsList[curMissionItemSwapPosition].isBlock == true) {
					if (swapNextxTime > 0) {
						swapNextxTime--;
						continue;
					}
					returnId = curMissionItemSwapPosition;
					usedItList[curMissionItemSwapPosition] = true;

					break;
				}
			} else if (iNeedBlock == 1) {
				if (allBlockInGameAsList[curMissionItemSwapPosition].isBlock == false) {
					if (swapNextxTime > 0) {
						swapNextxTime--;
						continue;
					}
					returnId = curMissionItemSwapPosition;
					usedItList[curMissionItemSwapPosition] = true;

					break;
				}
			}

		} while (true);

	}

	public int getNextUnuseMissionItem(int swapNextxTime, boolean usedItList[]) {

		int returnId = -1;

		do {
			curMissionItemSwapPosition++;
			if (curMissionItemSwapPosition >= allBlockInGameAsListSize) {
				curMissionItemSwapPosition = 0;
			}

			if (usedItList[curMissionItemSwapPosition] == false) {
				if (swapNextxTime > 0) {
					swapNextxTime--;
					continue;
				}
				returnId = curMissionItemSwapPosition;
				usedItList[curMissionItemSwapPosition] = true;

				break;
			}

		} while (true);

		return returnId;
	}

	public void loadMissionBlockFile() {

		String filena = missionPath;
		File fff = new File(filena);

		try {

			allBlockInGame.clear();
			allBlockInGameAsListSize = 0;

			fff.createNewFile();

			d.pl("loading mission file : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				m = strLine.split(":");
				// Print the content on the console

				AllBlockInGameType miss = new AllBlockInGameType();
				miss.theName = m[0];
				miss.data = Byte.parseByte(m[1]);

				miss.maxStack = Integer.parseInt(m[2]);
				miss.isBlock = Boolean.parseBoolean(m[3]);

				if (m.length == 5) {
					miss.limitSell = Integer.parseInt(m[4]);
				} else {
					miss.limitSell = 0;
				}

				// d.pl("...");
				// rs[rsMax - 1].mission = 0;

				if (allBlockInGame.get(miss.theName + ":" + miss.data) != null) {
					d.pl("loading not null");

				}
				allBlockInGame.put(miss.theName + ":" + miss.data, miss);
				allBlockInGameAsList[allBlockInGameAsListSize] = miss;
				allBlockInGameAsListSize++;
			}

			d.pl(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			d.pl("Error load " + filena + e.getMessage());
		}
	}

	public void loadSellableFile() {

		String filena = sellablePath;
		File fff = new File(filena);

		try {

			sell.clear();
			sellAsList.clear();

			fff.createNewFile();

			d.pl("loading sellable file : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				m = strLine.split(":");
				// Print the content on the console

				SellableType sb = new SellableType();
				/*
				 * sb.theName = m[0]; sb.data = Byte.parseByte(m[1]);
				 * sb.maxStack = Integer.parseInt(m[2]);
				 */

				AllBlockInGameType eco = allBlockInGame.get(m[0] + ":" + m[1]);
				if (eco == null) {
					d.pl("load error ");
					d.pl("allBlockInGame error " + m[0] + ":" + m[1]);
				} else {
					sb.index = getIndexFromKeyAlBlockInGame(m[0] + ":" + m[1]);
					sb.timeToGet = convertStringToTime(m[3]);

					if (m.length == 5) {
						sb.allItemYouCanFind = Integer.parseInt(m[4]);
					} else {
						sb.allItemYouCanFind = 0;
					}
					// rs[rsMax - 1].mission = 0;

					sellAsList.add(sb);
					sell.put(eco.theName + ":" + eco.data, sb);
				}

			}

			d.pl(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			d.pl("Error load " + filena + e.getMessage());
		}
	}

	public void save_tmpAllShop(LinkedList<AllShop> tmpAllShop) {

		String filena = tmpAllShopPath;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			d.pl("ptdew&dewdd: Start saving " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < tmpAllShop.size(); y++) {
				AllShop sty = tmpAllShop.get(y);
				// price , size

				String wr = "" + sty.playPrice + " " + sty.size;
				fwriter.write(wr + System.getProperty("line.separator"));

				for (int z = 0; z < sty.size; z++) {

					wr = "" + sty.item[z] + ":" + sty.data[z] + " " + sty.amount[z];
					fwriter.write(wr + System.getProperty("line.separator"));

				}

			}

			fwriter.close();
			d.pl("ptdew&dewdd:saved " + filena);
			return;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void save_tmpLV(LinkedList<LV1000Type> tmpLV) {

		String filena = tmpLVPath;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			d.pl("ptdew&dewdd: Start saving " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < tmpLV.size(); y++) {
				LV1000Type sty = tmpLV.get(y);

				// price , size

				String wr = "" + sty.needSize + " " + sty.rewardSize;
				fwriter.write(wr + System.getProperty("line.separator"));

				for (int z = 0; z < sty.needSize; z++) {
					AllBlockInGameType ooo = Core.allBlockInGameAsList[sty.needIndex[z]];

					wr = "" + ooo.getIDData() + " " + sty.needAmount[z];

					fwriter.write(wr + System.getProperty("line.separator"));

				}

				for (int z = 0; z < sty.rewardSize; z++) {
					AllBlockInGameType ooo = Core.allBlockInGameAsList[sty.rewardIndex[z]];

					wr = "" + ooo.getIDData() + " " + sty.rewardAmount[z];

					fwriter.write(wr + System.getProperty("line.separator"));

				}

			}

			fwriter.close();
			d.pl("ptdew&dewdd:saved " + filena);
			return;

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void save_tmpSell(LinkedList<SellableType> tmpSell) {

		String filena = tmpSellPath;
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			d.pl("ptdew&dewdd: Start saving " + filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < tmpSell.size(); y++) {
				SellableType sty = tmpSell.get(y);
				AllBlockInGameType abt = Core.allBlockInGameAsList[sty.index];

				String wr = "" + abt.getIDData() + " " + sty.sellPerPrice;

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

	public LinkedList<SellableType> sortSell(LinkedList<SellableType> sortPls) {
		int dataSize = sell.size();

		int indexSorted[] = new int[sell.size()];

		for (int i = 0; i < dataSize; i++) {
			indexSorted[i] = i;
		}

		for (int i = 0; i < dataSize; i++) {
			for (int j = 0; j < (dataSize - 1 - i); j++) {

				if (sortPls.get(indexSorted[j]).timeToGet < sortPls.get(indexSorted[j + 1]).timeToGet) {
					int t = indexSorted[j];

					indexSorted[j] = indexSorted[j + 1];

					indexSorted[j + 1] = t;

				}
			}

		}

		LinkedList<SellableType> tmp2 = new LinkedList<SellableType>();
		for (int i = 0; i < dataSize; i++) {
			tmp2.add(sortPls.get(indexSorted[i]));
		}
		return tmp2;
	}

}
