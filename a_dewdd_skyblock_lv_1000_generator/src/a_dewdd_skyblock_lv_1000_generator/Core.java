package a_dewdd_skyblock_lv_1000_generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

public class Core {
	class TmpAllItemInShopType {
		LinkedList<AllBlockInGameType> tmpAllItemInShop;
		double[] chromosome;
		int curChro;
		int curMissionItemSwapPosition;
		boolean tmpBoolean[];
	}
	class TmpLVType {
		public LinkedList<LV1000Type> tmpLV = new LinkedList<LV1000Type>();
		public int curChro;
		public int tmpUsedItNeedUniqueCount;
		public double[] chromosome;
		public int curMissionItemSwapPosition;
		public boolean[] usedItNeedList;
		public boolean[] usedItRewardList;
		public int tmpUsedItRewardUniqueCount;

	}

	class TmpShopPriceAmountOfItemInShopType {
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

	private Object tmp4;

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

	public void decodeLV(TmpLVType tmpType) {

		while (tmpType.tmpLV.size() <= maxLV) {
			// d.pl("tmpLV " + tmpLV.size() + " , tmpReading " +
			// tmpReading.size() + " , curChro " + curChro);

			LV1000Type x = new LV1000Type();
			// break
			// it's mean LV break drop place MODE

			// add lv need item list
			for (int i = 0; i < maxItemForCompleteMission && tmpType.curChro < dnaSize
					&& tmpType.tmpUsedItNeedUniqueCount < allBlockInGameAsList.size(); i++) {
				double tmpReadChro = tmpType.chromosome[tmpType.curChro];
				if (i < minItemForCompleteMission) {
					tmpReadChro = Math.abs(tmpReadChro);
				}

				// next Add part
				if (tmpReadChro <= 0) {
					continue;
				}

				int itemSlot = getNextBlockSwapMissionType((int) (tmpReadChro * swapMultipy),
						tmpType.curMissionItemSwapPosition, 0, tmpType.usedItNeedList);
				tmpType.curMissionItemSwapPosition = itemSlot;

				x.needItem[x.needSize] = allBlockInGameAsList.get(itemSlot).theName;
				x.needData[x.needSize] = allBlockInGameAsList.get(itemSlot).data;

				double tmpAmount = Math.abs(tmpType.chromosome[tmpType.curChro]);
				tmpType.curChro++;

				if (tmpAmount > 1) {
					tmpAmount = 1;
				}
				double tmpAmountInt = tmpAmount * allBlockInGameAsList.get(itemSlot).maxStack;
				if ((int) tmpAmountInt < 0) {
					tmpAmountInt = 1;
				}

				x.needAmount[x.needSize] = (int) (tmpAmountInt);

				x.needSize++;
				tmpType.tmpUsedItNeedUniqueCount++;
				if (tmpType.tmpUsedItNeedUniqueCount >= allBlockInGameAsList.size()) {
					tmpType.tmpUsedItNeedUniqueCount = 0;
					for (int kk = 0; kk < allBlockInGameAsList.size(); kk++) {
						tmpType.usedItNeedList[kk] = false;
					}
				}
			}

			// add reward part

			// add reward block part
			for (int i = 0; i < maxRewardDiffBlockType && tmpType.curChro < dnaSize; i++) {
				double tmpReadChro = tmpType.chromosome[tmpType.curChro];
				if (i == minRewardDiffBlockType) {
					tmpReadChro = Math.abs(tmpReadChro);
				}

				// next Add part
				if (tmpReadChro <= 0) {
					continue;
				}

				int itemSlot = getNextBlockSwapMissionType((int) (tmpReadChro * swapMultipy),
						tmpType.curMissionItemSwapPosition, 0, tmpType.usedItRewardList);
				tmpType.curMissionItemSwapPosition = itemSlot;

				x.rewardItem[x.rewardSize] = allBlockInGameAsList.get(itemSlot).theName;
				x.rewardData[x.rewardSize] = allBlockInGameAsList.get(itemSlot).data;

				double tmpAmount = Math.abs(tmpType.chromosome[tmpType.curChro]);
				tmpType.curChro++;

				if (tmpAmount > 1) {
					tmpAmount = 1;
				}
				double tmpAmountInt = tmpAmount * allBlockInGameAsList.get(itemSlot).maxStack;
				if ((int) tmpAmountInt < 0) {
					tmpAmountInt = 1;
				}

				x.rewardAmount[x.rewardSize] = (int) (tmpAmountInt);

				x.rewardSize++;
				tmpType.tmpUsedItRewardUniqueCount++;
				if (tmpType.tmpUsedItRewardUniqueCount >= allBlockInGameAsList.size()) {
					tmpType.tmpUsedItRewardUniqueCount = 0;
					for (int kk = 0; kk < allBlockInGameAsList.size(); kk++) {
						tmpType.usedItRewardList[kk] = false;
					}
				}
			}

			tmpType.tmpLV.add(x);

			// curChro++;
			continue;

		}
	}

	public void decodeShopPriceAmountOfItemInShop(TmpShopPriceAmountOfItemInShopType tmpType) {
		
		int countItem = 0;
		while (countItem < allBlockInGameAsList.size()) {
			d.pl("sperateShop > curChro " + tmpType.curChro);
			d.pl("sperateShop : shopSlotMax = " + tmpType.shopSlotMax + ", " + countItem + " = " + (allBlockInGameAsList.size()));

			double tmpReadChro = tmpType.chromosome[tmpType.curChro];
			tmpReadChro = Math.abs(tmpReadChro);

			d.pl(" the shop curchro " + tmpType.curChro + " | " + tmpReadChro + " | "

			+ tmpType.shopSlotMax);

			d.pl("size = " + tmpReadChro + " .. " + tmpType.curChro + " unique " + tmpType.shopSlotMax);

			// price 
			tmpType.price[tmpType.shopSlotMax] = tmpReadChro * ShopMaxCost;
			tmpType.curChro++;
			
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
			tmpType.amount[tmpType.shopSlotMax] = (int)curAmount;
			
			countItem += tmpType.amount[tmpType.shopSlotMax];
			
			if (countItem > allBlockInGameAsList.size()) {
				int tmb =(countItem -  allBlockInGameAsList.size() );
				if (tmb < 0) {
					d.pl("tmb : " + tmb);
				}
				
				tmpType.amount[tmpType.shopSlotMax]  = tmb;
				countItem = allBlockInGameAsList.size();
				
			}
					

			tmpType.shopSlotMax++;
			
			
			tmpType.curChro++;


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

	public TmpAllItemInShopType decodeTmpAllItemInShop(TmpAllItemInShopType tmpType) {
		while (tmpType.tmpAllItemInShop.size() < allBlockInGameAsList.size()) {
			double tmpReadChro = Math.abs(tmpType.chromosome[tmpType.curChro]);

			AllBlockInGameType bo = new AllBlockInGameType();

			int itemSlot = getNextUnuseMissionItem((int) (tmpReadChro * swapMultipy),
					tmpType.curMissionItemSwapPosition, tmpType.tmpBoolean);

			tmpType.curChro++;

			tmpType.curMissionItemSwapPosition = itemSlot;

			bo.theName = allBlockInGameAsList.get(itemSlot).theName;
			bo.data = allBlockInGameAsList.get(itemSlot).data;
			// ........
			tmpReadChro = Math.abs(tmpType.chromosome[tmpType.curChro]);

			bo.curAmount = (int) (tmpReadChro * allBlockInGameAsList.get(itemSlot).maxStack);
			tmpType.curChro++;

			tmpType.tmpAllItemInShop.add(bo);

			continue;
		}

		return tmpType;
	}
	
	class TmpTmpShopPriceToAllShopType {
		LinkedList<AllBlockInGameType> tmpAllItemInShop ;
		double[] price;
		int[] amount;
		int shopSlotMax;
		
		LinkedList<AllShop> tmpAllShop;
	}
	
	public void convertTmpShopPriceAmountOfItemInShopToAllShopType(TmpTmpShopPriceToAllShopType tmpType) {
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

		TmpAllItemInShopType tmp = new TmpAllItemInShopType();
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

		tmp = decodeTmpAllItemInShop(tmp);
		curChro = tmp.curChro;
		curMissionItemSwapPosition = tmp.curMissionItemSwapPosition;

		// **********************************************************************************

		// decond shop price , and amount of items in shop
		int shopSlotMax = 0;

		int maxShopBuffer = 1000;

		TmpShopPriceAmountOfItemInShopType tmp2 = new TmpShopPriceAmountOfItemInShopType();
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
		
		TmpTmpShopPriceToAllShopType tmp3 = new TmpTmpShopPriceToAllShopType();
		tmp3.amount = tmp2.amount;
		tmp3.price = tmp2.price;
		tmp3.shopSlotMax = shopSlotMax;
		tmp3.tmpAllItemInShop = tmp.tmpAllItemInShop;
		tmp3.tmpAllShop = tmpAllShop;
		
		convertTmpShopPriceAmountOfItemInShopToAllShopType(tmp3);
		
		
		
		// ******************************************************************************************

		
		// decode level
				
		TmpLVType tmp4  = new TmpLVType();
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
