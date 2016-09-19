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
		LinkedList<AllBlockInGameType> tmpAllItemInShop;
		double[] chromosome;
		int curChro;
		int curMissionItemSwapPosition;
		boolean tmpBoolean[];
	}
	class ParameterLVType {
		public LinkedList<LV1000Type> tmpLV = new LinkedList<LV1000Type>();
		public int curChro;
		
		public double[] chromosome;
		public int curMissionItemSwapPosition;
		
		public int tmpUsedItNeedUniqueCount;
		public boolean[] usedItNeedList;
		public boolean[] usedItRewardList;
		public int tmpUsedItRewardUniqueCount;
		
		public ParameterShopPriceToAllShopType tt ;
		

	}

	class ParameterShopPriceAmountOfItemInShopType {
		int shopSlotMax;
		int curChro;
		double[] chromosome;

		double[] price;
		int[] amount;

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

	HashMap<String, SellableType> sell = new HashMap<String, SellableType>();

	// 415 unique item

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

	public void decodeLV(ParameterLVType paraLV) {
		// add need item
		ParameterAllItemInShopType t1 = new ParameterAllItemInShopType();
		t1.tmpBoolean = new boolean[allBlockInGameAsList.size()];
		for (int i = 0 ; i < allBlockInGameAsList.size() ; i++ ) {
			t1.tmpBoolean[i] = false;
		}
		
		t1.chromosome = paraLV.chromosome;
		t1.curChro = paraLV.curChro;
		t1.curMissionItemSwapPosition = paraLV.curMissionItemSwapPosition;
		t1.tmpAllItemInShop = new LinkedList<AllBlockInGameType>();
		
		decodeTmpAllItemInShop(t1);
		
		// after have all unique item
		
		
		

		while (paraLV.tmpLV.size() <= maxLV) {
			// d.pl("tmpLV " + tmpLV.size() + " , tmpReading " +
			// tmpReading.size() + " , curChro " + curChro);

			LV1000Type x = new LV1000Type();
			// break
			// it's mean LV break drop place MODE

			// add lv need item list
			for (int i = 0; i < maxItemForCompleteMission && paraLV.curChro < dnaSize
					&& paraLV.tmpUsedItNeedUniqueCount < allBlockInGameAsList.size(); i++) {
				double tmpReadChro = paraLV.chromosome[paraLV.curChro];
				if (i < minItemForCompleteMission) { 
					tmpReadChro = Math.abs(tmpReadChro);
				}

				// next Add part
				if (tmpReadChro <= 0) {
					continue;
				}

				int itemSlot = getNextBlockSwapMissionType((int) (tmpReadChro * swapMultipy),
						paraLV.curMissionItemSwapPosition, 0, paraLV.usedItNeedList);
				paraLV.curMissionItemSwapPosition = itemSlot;

				x.needItem[x.needSize] = allBlockInGameAsList.get(itemSlot).theName;
				x.needData[x.needSize] = allBlockInGameAsList.get(itemSlot).data;

				double tmpAmount = Math.abs(paraLV.chromosome[paraLV.curChro]);
				paraLV.curChro++;

				if (tmpAmount > 1) {
					tmpAmount = 1;
				}
				double tmpAmountInt = tmpAmount * allBlockInGameAsList.get(itemSlot).maxStack;
				if ((int) tmpAmountInt < 0) {
					tmpAmountInt = 1;
				}

				x.needAmount[x.needSize] = (int) (tmpAmountInt);

				x.needSize++;
				paraLV.tmpUsedItNeedUniqueCount++;
				if (paraLV.tmpUsedItNeedUniqueCount >= allBlockInGameAsList.size()) {
					paraLV.tmpUsedItNeedUniqueCount = 0;
					for (int kk = 0; kk < allBlockInGameAsList.size(); kk++) {
						paraLV.usedItNeedList[kk] = false;
					}
				}
			}

			// add reward part

			// add reward block part
			for (int i = 0; i < maxRewardDiffBlockType && paraLV.curChro < dnaSize; i++) {
				double tmpReadChro = paraLV.chromosome[paraLV.curChro];
				if (i == minRewardDiffBlockType) {
					tmpReadChro = Math.abs(tmpReadChro);
				}

				// next Add part
				if (tmpReadChro <= 0) {
					continue;
				}

				int itemSlot = getNextBlockSwapMissionType((int) (tmpReadChro * swapMultipy),
						paraLV.curMissionItemSwapPosition, 0, paraLV.usedItRewardList);
				paraLV.curMissionItemSwapPosition = itemSlot;

				x.rewardItem[x.rewardSize] = allBlockInGameAsList.get(itemSlot).theName;
				x.rewardData[x.rewardSize] = allBlockInGameAsList.get(itemSlot).data;

				double tmpAmount = Math.abs(paraLV.chromosome[paraLV.curChro]);
				paraLV.curChro++;

				if (tmpAmount > 1) {
					tmpAmount = 1;
				}
				double tmpAmountInt = tmpAmount * allBlockInGameAsList.get(itemSlot).maxStack;
				if ((int) tmpAmountInt < 0) {
					tmpAmountInt = 1;
				}

				x.rewardAmount[x.rewardSize] = (int) (tmpAmountInt);

				x.rewardSize++;
				paraLV.tmpUsedItRewardUniqueCount++;
				if (paraLV.tmpUsedItRewardUniqueCount >= allBlockInGameAsList.size()) {
					paraLV.tmpUsedItRewardUniqueCount = 0;
					for (int kk = 0; kk < allBlockInGameAsList.size(); kk++) {
						paraLV.usedItRewardList[kk] = false;
					}
				}
			}

			paraLV.tmpLV.add(x);

			// curChro++;
			continue;

		}
	}

	public void decodeShopPriceAmountOfItemInShop(ParameterShopPriceAmountOfItemInShopType paraShopPrice) {
		
		int countItem = 0;
		while (countItem < allBlockInGameAsList.size()) {
			d.pl("sperateShop > curChro " + paraShopPrice.curChro);
			d.pl("sperateShop : shopSlotMax = " + paraShopPrice.shopSlotMax + ", " + countItem + " = " + (allBlockInGameAsList.size()));

			double tmpReadChro = paraShopPrice.chromosome[paraShopPrice.curChro];
			tmpReadChro = Math.abs(tmpReadChro);

			d.pl(" the shop curchro " + paraShopPrice.curChro + " | " + tmpReadChro + " | "

			+ paraShopPrice.shopSlotMax);

			d.pl("size = " + tmpReadChro + " .. " + paraShopPrice.curChro + " unique " + paraShopPrice.shopSlotMax);

			// price 
			paraShopPrice.price[paraShopPrice.shopSlotMax] = tmpReadChro * ShopMaxCost;
			paraShopPrice.curChro++;
			
			// amount
			
			tmpReadChro = Math.abs(tmpReadChro);
			
			if (tmpReadChro < 0) {
				tmpReadChro = 0;
			}
			if (tmpReadChro > 1) {
				tmpReadChro = 1;
			}
			
			// 3 - 10
			
			double max = (maxShopSize - minShopSize); //  1 / 7
			
			
			double curAmount = (tmpReadChro * max)+ minShopSize;
			paraShopPrice.amount[paraShopPrice.shopSlotMax] = (int)curAmount;
			
			countItem += paraShopPrice.amount[paraShopPrice.shopSlotMax];
			
			if (countItem > allBlockInGameAsList.size()) {
				int tmb =(countItem -  allBlockInGameAsList.size() );
				if (tmb < 0) {
					d.pl("tmb : " + tmb);
				}
				
				paraShopPrice.amount[paraShopPrice.shopSlotMax]  = tmb;
				countItem = allBlockInGameAsList.size();
				
			}
					

			paraShopPrice.shopSlotMax++;
			
			
			paraShopPrice.curChro++;


		} // mission

	}

	public int decodeTmpSell(LinkedList<SellableType> tmpSell, final double[] chromosome, int curChro) {
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

	public void decodeTmpAllItemInShop(ParameterAllItemInShopType paraItemsInShop) {
		while (paraItemsInShop.tmpAllItemInShop.size() < allBlockInGameAsList.size()) {
			double tmpReadChro = Math.abs(paraItemsInShop.chromosome[paraItemsInShop.curChro]);

			AllBlockInGameType bo = new AllBlockInGameType();

			int itemSlot = getNextUnuseMissionItem((int) (tmpReadChro * swapMultipy),
					paraItemsInShop.curMissionItemSwapPosition, paraItemsInShop.tmpBoolean);

			paraItemsInShop.curChro++;

			paraItemsInShop.curMissionItemSwapPosition = itemSlot;

			bo.theName = allBlockInGameAsList.get(itemSlot).theName;
			bo.data = allBlockInGameAsList.get(itemSlot).data;
			// ........
			tmpReadChro = Math.abs(paraItemsInShop.chromosome[paraItemsInShop.curChro]);

			bo.curAmount = (int) (tmpReadChro * allBlockInGameAsList.get(itemSlot).maxStack);
			paraItemsInShop.curChro++;

			paraItemsInShop.tmpAllItemInShop.add(bo);

			continue;
		}

	}
	
	class ParameterShopPriceToAllShopType {
		LinkedList<AllBlockInGameType> tmpAllItemInShop ;
		double[] price;
		int[] amount;
		int shopSlotMax;
		
		LinkedList<AllShop> tmpAllShop;
	}
	
	public void convertTmpShopPriceAmountOfItemInShopToAllShopType(ParameterShopPriceToAllShopType tmpType) {
		tmpType.tmpAllShop.clear();
		
		// check is the last shop ( have enough item >= minshop)
		
		if (tmpType.amount[tmpType.shopSlotMax - 1 ]  < minShopSize) {
			boolean foundx = false;
			for (int i = 0; i < tmpType.shopSlotMax -1 ; i ++ ) {
				if (tmpType.amount[i] < maxShopSize) {
					tmpType.amount[i] ++;
					tmpType.amount[tmpType.shopSlotMax-1] = 0;
					tmpType.price[tmpType.shopSlotMax - 1] = 0;
					tmpType.shopSlotMax --;
					
					
					foundx = true;
					break;
					
				}
			}
			
			if (foundx = true) {
				d.pl("shift yet, convert shopPrice to AllShop found last shopSlot have not enough item it last slot");
				
			}
			else {
				d.pl("weird things happends , can't shift last shopAmount,shopPrice  to another slot");
			}
			
		}
		
		// to time convert to All Shop
		
		int curItemIndex = 0;
		
		for (int i = 0 ; i < tmpType.shopSlotMax ; i ++ ) {
			d.pl("convert tmp loop " + i + "/" + tmpType.shopSlotMax);
			
			AllShop ab = new AllShop();
			
			ab.playPrice = tmpType.price[i];
			ab.size = tmpType.amount[i];
			
			ab.item = new String[ab.size];
			ab.data = new byte[ab.size];
			ab.amount = new int[ab.size];
			
			for (int j = 0; j < tmpType.amount[i] ; j ++ ) {
				AllBlockInGameType abigt = tmpType.tmpAllItemInShop.get(curItemIndex);
				ab.item[j] = abigt.theName;
				ab.data[j] = abigt.data;
				ab.amount[j] = abigt.curAmount;
				
				curItemIndex ++;
				if (curItemIndex == allBlockInGameAsList.size() ) {
					break;
				}
			}
		
			tmpType.tmpAllShop.add(ab);
			
		}
		
		d.pl("converted tmpShop to AllShop ");
	}

	public void dnaDecoder(final double[] chromosome, LinkedList<AllShop> tmpAllShop, LinkedList<SellableType> tmpSell,
			LinkedList<LV1000Type> tmpLV) {

		int curChro = 0;

		// decond player sell
		curChro = decodeTmpSell(tmpSell, chromosome, curChro);

		// ************************************************************************

		// decond all item in shop
		LinkedList<AllBlockInGameType> tmpAllItemInShop = new LinkedList<AllBlockInGameType>();
		tmpAllItemInShop.clear();

		ParameterAllItemInShopType tmp = new ParameterAllItemInShopType();
		int curMissionItemSwapPosition = 0;

		tmp.chromosome = chromosome;
		tmp.curChro = curChro;
		tmp.curMissionItemSwapPosition = curMissionItemSwapPosition;
		tmp.tmpAllItemInShop = tmpAllItemInShop;

		boolean tmpBoolean[] = new boolean[allBlockInGameAsList.size()];
		for (int i = 0; i < allBlockInGameAsList.size(); i++) {
			tmpBoolean[i] = false;
		}
		tmp.tmpBoolean = tmpBoolean;

		decodeTmpAllItemInShop(tmp);
		curChro = tmp.curChro;
		curMissionItemSwapPosition = tmp.curMissionItemSwapPosition;

		// **********************************************************************************

		// decond shop price , and amount of items in shop
		int shopSlotMax = 0;

		int maxShopBuffer = 1000;

		ParameterShopPriceAmountOfItemInShopType tmp2 = new ParameterShopPriceAmountOfItemInShopType();
		tmp2.amount = new int[maxShopBuffer];
		tmp2.chromosome = chromosome;
		tmp2.curChro = curChro;
		tmp2.price = new double[maxShopBuffer];
		tmp2.shopSlotMax = shopSlotMax;

		decodeShopPriceAmountOfItemInShop(tmp2);

		curChro = tmp2.curChro;
		shopSlotMax = tmp2.shopSlotMax;

		// *****************************************************************************************
		// convert all data to AllShop
		
		ParameterShopPriceToAllShopType tmp3 = new ParameterShopPriceToAllShopType();
		tmp3.amount = tmp2.amount;
		tmp3.price = tmp2.price;
		tmp3.shopSlotMax = shopSlotMax;
		tmp3.tmpAllItemInShop = tmp.tmpAllItemInShop;
		tmp3.tmpAllShop = tmpAllShop;
		
		convertTmpShopPriceAmountOfItemInShopToAllShopType(tmp3);
		
		
		
		// ******************************************************************************************

		
		// decode level
				
		ParameterLVType tmp4  = new ParameterLVType();
		tmp4.chromosome = chromosome;
		tmp4.curChro = curChro;
		tmp4.curMissionItemSwapPosition = curMissionItemSwapPosition;
		tmp4.tmpLV = tmpLV;

		boolean usedItShopList[] = new boolean[Main.co.allBlockInGameAsList.size()];

		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			usedItShopList[i] = false;
		}

		boolean usedItNeedList[] = new boolean[Main.co.allBlockInGameAsList.size()];
		boolean usedItRewardList[] = new boolean[Main.co.allBlockInGameAsList.size()];
		int tmpUsedItNeedUniqueCount = 0;
		int tmpUsedItRewardUniqueCount = 0;

		for (int i = 0; i < Main.co.allBlockInGameAsList.size(); i++) {
			usedItNeedList[i] = false;
			usedItRewardList[i] = false;
		}

		tmp4.tmpUsedItNeedUniqueCount = tmpUsedItNeedUniqueCount;
		tmp4.tmpUsedItRewardUniqueCount = tmpUsedItRewardUniqueCount;
		tmp4.usedItNeedList = usedItNeedList;
		tmp4.usedItRewardList = usedItRewardList;
		
		tmp4.tt =  tmp3;
		
		decodeLV(tmp4);

		d.pl("abc");
	}

	public int getNextBlockSwapMissionType(int swapNextxTime, int curMissionItemSwapPosition, int iNeedBlock,
			boolean usedItList[]) {
		// ineedblock 0 = block , 1 = item , 2 = both
		int looping = curMissionItemSwapPosition;
		int returnId = -1;

		do {

			do {
				looping++;
				if (looping >= allBlockInGameAsList.size()) {
					looping = 0;
				}

			} while (usedItList[looping] == true);

			if (iNeedBlock == 2) { // both
				if (swapNextxTime > 0) {
					swapNextxTime--;
					continue;
				}
				returnId = looping;
				usedItList[looping] = true;
				break;
			} else if (iNeedBlock == 0) {
				if (allBlockInGameAsList.get(looping).isBlock == true) {
					if (swapNextxTime > 0) {
						swapNextxTime--;
						continue;
					}
					returnId = looping;
					usedItList[looping] = true;

					break;
				}
			} else if (iNeedBlock == 1) {
				if (allBlockInGameAsList.get(looping).isBlock == false) {
					if (swapNextxTime > 0) {
						swapNextxTime--;
						continue;
					}
					returnId = looping;
					usedItList[looping] = true;

					break;
				}
			}

		} while (true);

		return returnId;
	}

	public int getNextUnuseMissionItem(int swapNextxTime, int curMissionItemSwapPosition, boolean usedItList[]) {

		int looping = curMissionItemSwapPosition;
		int returnId = -1;

		do {
			looping++;
			if (looping >= allBlockInGameAsList.size()) {
				looping = 0;
			}

			if (usedItList[looping] == false) {
				if (swapNextxTime > 0) {
					swapNextxTime--;
					continue;
				}
				returnId = looping;
				usedItList[looping] = true;

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
