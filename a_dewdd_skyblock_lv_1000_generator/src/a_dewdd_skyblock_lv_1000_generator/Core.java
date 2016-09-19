package a_dewdd_skyblock_lv_1000_generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
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

		public boolean[] usedItNeedList;
		public boolean[] usedItRewardList;

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

	public static String sellablePath = File.separator + "ramdisk" + File.separator + "sellableblock.txt";
	public static String missionPath = File.separator + "ramdisk" + File.separator + "missionblock.txt";

	public static int dnaSize = 2000;

	public static int maxItemForCompleteMission = 10;
	public static int minItemForCompleteMission = 3;

	public static int maxRewardDiffBlockType = 10;
	public static int minRewardDiffBlockType = 1;

	public static long MaxTickToCompleteAllLV = 60 * 60 * 24 * 31;

	public static int maxShopSize = 10;
	public static int minShopSize = 3;

	public static int swapMultipy = 100;

	public static double SellMaxCost = 1000;
	public static double ShopMaxCost = 10000;

	public static int maxLV = 100;

	private int curChro = 0;
	private double[] chromosome;
	private int curMissionItemSwapPosition;

	// 415 unique item

	HashMap<String, SellableType> sell = new HashMap<String, SellableType>();

	HashMap<String, AllBlockInGameType> allBlockInGame = new HashMap<String, AllBlockInGameType>();

	LinkedList<SellableType> sellAsList = new LinkedList<SellableType>();

	LinkedList<AllBlockInGameType> allBlockInGameAsList = new LinkedList<AllBlockInGameType>();

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

		// check is the last shop ( have enough item >= minshop)

		if (tmpType.amount[tmpType.shopSlotMax - 1] < minShopSize) {
			boolean foundx = false;
			for (int i = 0; i < tmpType.shopSlotMax - 1; i++) {
				if (tmpType.amount[i] < maxShopSize) {
					tmpType.amount[i]++;
					tmpType.amount[tmpType.shopSlotMax - 1] = 0;
					tmpType.price[tmpType.shopSlotMax - 1] = 0;
					tmpType.shopSlotMax--;

					foundx = true;
					break;

				}
			}

			if (foundx = true) {
				d.pl("shift yet, convert shopPrice to AllShop found last shopSlot have not enough item it last slot");

			} else {
				d.pl("weird things happends , can't shift last shopAmount,shopPrice  to another slot");
			}

		}

		// to time convert to All Shop

		int curItemIndex = 0;

		for (int i = 0; i < tmpType.shopSlotMax; i++) {
			d.pl("convert tmp loop " + i + "/" + tmpType.shopSlotMax);

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
				if (curItemIndex == allBlockInGameAsList.size()) {
					break;
				}
			}

			tmpType.outputAllShop.add(ab);

		}

		d.pl("converted tmpShop to AllShop ");
	}

	class ParameterRandomUniqueItem {
		// int [] index = new int[allBlockInGameAsList.size()];
		int[] index;
	}

	public void decodeRandomUniqueItem(ParameterRandomUniqueItem para) {
		para.index = new int[allBlockInGameAsList.size()];
		boolean tmpBoolean[] = new boolean[allBlockInGameAsList.size()];

		for (int i = 0; i < allBlockInGameAsList.size(); i++) {
			para.index[i] = 0;
			tmpBoolean[i] = false;
		}

		int countItem = 0;
		while (countItem < allBlockInGameAsList.size()) {
			double tmpReadChro = Math.abs(chromosome[curChro]);

			getNextUnuseMissionItem((int) (tmpReadChro * swapMultipy),

					tmpBoolean);

			curChro++;

			para.index[countItem] = curMissionItemSwapPosition;

			countItem++;

			continue;
		}

	}

	class ParameterRandomAmountItem {
		// int [] index = new int[allBlockInGameAsList.size()];
		int[] amount;

	}

	public void decodeRandomAmounteItem(ParameterRandomAmountItem para) {
		para.amount = new int[allBlockInGameAsList.size()];

		for (int i = 0; i < allBlockInGameAsList.size(); i++) {
			para.amount[i] = 0;
		}

		int countItem = 0;
		while (countItem < allBlockInGameAsList.size()) {

			double max = (maxShopSize - minShopSize); // 1 / 7

			double tmpReadChro = Math.abs(chromosome[curChro]);
			curChro++;

			double curAmount = (tmpReadChro * max) + minShopSize;
			para.amount[countItem] = (int) curAmount;

			countItem += para.amount[countItem];

			if (countItem > allBlockInGameAsList.size()) {
				int tmb = (countItem - allBlockInGameAsList.size());
				if (tmb < 0) {
					d.pl("tmb : " + tmb);
				}

				para.amount[countItem] = tmb;
				countItem = allBlockInGameAsList.size();

			}
		}

	}

	public int decodeRandomStackGive01_() {
		double tmpReadChro = 0;
		tmpReadChro = Math.abs(chromosome[curChro]);

		curChro++;

		if (tmpReadChro < 0) {
			tmpReadChro = 0;
		}

		if (tmpReadChro > 1) {
			tmpReadChro = 1;
		}

		int returner = (int) tmpReadChro;

		return returner;
	}

	public void decodeLV(ParameterLVType paraLV) {
		// add need item
		
	}

	public void decodeShopPriceAmountOfItemInShop(ParameterShopPriceAmountOfItemInShopType paraShopPrice) {

		
		
		int countItem = 0;
		while (countItem < allBlockInGameAsList.size()) {
			
			d.pl("sperateShop > curChro " + curChro);
			d.pl("sperateShop : shopSlotMax = " + paraShopPrice.shopSlotMax + ", " + countItem + " = "
					+ (allBlockInGameAsList.size()));
			
	// price
			double tmpReadChro = chromosome[curChro];
			tmpReadChro = Math.abs(tmpReadChro);

			d.pl(" the shop curchro " + curChro + " | " + tmpReadChro + " | "

					+ paraShopPrice.shopSlotMax);

			d.pl("size = " + tmpReadChro + " .. " + curChro + " unique " + paraShopPrice.shopSlotMax);

		
			paraShopPrice.price[paraShopPrice.shopSlotMax] = tmpReadChro * ShopMaxCost;
			curChro++;

			// amount

			tmpReadChro = Math.abs(tmpReadChro);

			if (tmpReadChro < 0) {
				tmpReadChro = 0;
			}
			if (tmpReadChro > 1) {
				tmpReadChro = 1;
			}

			// 3 - 10

			double max = (maxShopSize - minShopSize); // 1 / 7

			double curAmount = (tmpReadChro * max) + minShopSize;
			paraShopPrice.amount[paraShopPrice.shopSlotMax] = (int) curAmount;

			countItem += paraShopPrice.amount[paraShopPrice.shopSlotMax];

			if (countItem > allBlockInGameAsList.size()) {
				int tmb = (countItem - allBlockInGameAsList.size());
				if (tmb < 0) {
					d.pl("tmb : " + tmb);
				}

				paraShopPrice.amount[paraShopPrice.shopSlotMax] = tmb;
				countItem = allBlockInGameAsList.size();

			}

			paraShopPrice.shopSlotMax++;

			curChro++;

		} // mission

	}

	public void decodeTmpAllItemInShop(ParameterAllItemInShopType paraItemsInShop) {
		ParameterRandomUniqueItem para = new ParameterRandomUniqueItem();

		decodeRandomUniqueItem(para);

		while (paraItemsInShop.outputAllItemInShop.size() < allBlockInGameAsList.size()) {

			AllBlockInGameType bo = new AllBlockInGameType();



			int cur = para.index[paraItemsInShop.outputAllItemInShop.size()];

			bo.theName = allBlockInGameAsList.get(cur).theName;
			bo.data = allBlockInGameAsList.get(para.index[cur]).data;
			// ........

			int rendomStack01 = decodeRandomStackGive01_();

			bo.curAmount = (int) (rendomStack01 * allBlockInGameAsList.get(cur).maxStack);
	


			paraItemsInShop.outputAllItemInShop.add(bo);


		}

	}

	public int decodeTmpSell(LinkedList<SellableType> tmpSell) {
		while (tmpSell.size() < sell.size()) {

			double tmpReadChro = Math.abs(chromosome[curChro]);

			SellableType x = sellAsList.get(tmpSell.size()).copyIt();

			x.sellPerPrice = tmpReadChro * SellMaxCost;
			tmpSell.add(x);
			// d.pl("chro sell = " + curChro);
			curChro++;
			continue;

		}

		return curChro;
	}

	public void dnaDecoder(double[] chromosome,  LinkedList<AllShop> tmpAllShop, LinkedList<SellableType> tmpSell,
			LinkedList<LV1000Type> tmpLV) {
		
		this.chromosome  = chromosome;

		curChro = 0;

		// decond player sell
		decodeTmpSell(tmpSell);

		// ************************************************************************

		// decond all item in shop
		LinkedList<AllBlockInGameType> tmpAllItemInShop = new LinkedList<AllBlockInGameType>();
		tmpAllItemInShop.clear();

		ParameterAllItemInShopType tmp = new ParameterAllItemInShopType();

		tmp.outputAllItemInShop = tmpAllItemInShop;

		boolean tmpBoolean[] = new boolean[allBlockInGameAsList.size()];
		for (int i = 0; i < allBlockInGameAsList.size(); i++) {
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

		decodeShopPriceAmountOfItemInShop(tmp2);

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

		// decode level

		ParameterLVType tmp4 = new ParameterLVType();

		tmp4.outputLV = tmpLV;

		boolean usedItShopList[] = new boolean[Main.co.allBlockInGameAsList.size()];

		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			usedItShopList[i] = false;
		}

		boolean usedItNeedList[] = new boolean[Main.co.allBlockInGameAsList.size()];
		boolean usedItRewardList[] = new boolean[Main.co.allBlockInGameAsList.size()];

		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			usedItNeedList[i] = false;
			usedItRewardList[i] = false;
		}

		tmp4.usedItNeedList = usedItNeedList;
		tmp4.usedItRewardList = usedItRewardList;

		decodeLV(tmp4);

		d.pl("abc");
	}

	public void getNextBlockSwapMissionType(int swapNextxTime, int iNeedBlock, boolean usedItList[]) {
		// ineedblock 0 = block , 1 = item , 2 = both

		int returnId = -1;

		do {

			do {
				curMissionItemSwapPosition++;
				if (curMissionItemSwapPosition >= allBlockInGameAsList.size()) {
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
				if (allBlockInGameAsList.get(curMissionItemSwapPosition).isBlock == true) {
					if (swapNextxTime > 0) {
						swapNextxTime--;
						continue;
					}
					returnId = curMissionItemSwapPosition;
					usedItList[curMissionItemSwapPosition] = true;

					break;
				}
			} else if (iNeedBlock == 1) {
				if (allBlockInGameAsList.get(curMissionItemSwapPosition).isBlock == false) {
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
			if (curMissionItemSwapPosition >= allBlockInGameAsList.size()) {
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
			allBlockInGameAsList.clear();

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

				// d.pl("...");
				// rs[rsMax - 1].mission = 0;

				if (allBlockInGame.get(miss.theName + ":" + miss.data) != null) {
					d.pl("loading not null");

				}
				allBlockInGame.put(miss.theName + ":" + miss.data, miss);
				allBlockInGameAsList.add(miss);
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
				sb.theName = m[0];
				sb.data = Byte.parseByte(m[1]);
				sb.maxStack = Integer.parseInt(m[2]);
				sb.timeToGet = convertStringToTime(m[3]);
				// rs[rsMax - 1].mission = 0;

				sellAsList.add(sb);
				sell.put(sb.theName + ":" + sb.data, sb);
			}

			d.pl(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			d.pl("Error load " + filena + e.getMessage());
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
