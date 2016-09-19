package a_dewdd_skyblock_lv_1000_generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Core {
	public static String sellablePath = File.separator + "ramdisk" + File.separator + "sellableblock.txt";
	public static String missionPath = File.separator + "ramdisk" + File.separator + "missionblock.txt";

	LinkedList<SellableType> sell = new LinkedList<SellableType>();
	LinkedList<MissionType> mission = new LinkedList<MissionType>();

	public static int dnaSize = 4000;

	public static int maxItemForCompleteMission = 10;
	public static int minItemForCompleteMission = 3;

	public static int maxRewardDiffBlockType = 10;
	public static int minRewardDiffBlockType = 1;
	public static int maxRewardDiffItemType = 10;
	public static int minRewardDiffItemType = 0;

	public static int maxShopSize = 10;
	public static int minShopSize = 3;

	public static int swapMultipy = 100;

	public static double SellMaxCost = 1000;
	public static double ShopMaxCost = 10000;

	LinkedList<AllShop> allShop = new LinkedList<AllShop>();

	// 415 unique item

	public void dnaDecoder(double[] chromosome, LinkedList<AllShop> tmpAllShop, LinkedList<SellableType> tmpSell,
			LinkedList<LV1000Type> tmpMission, LinkedList<Double> tmpReading, int tmpAllShopUniqueDone,
			int curMissionItemSwapPosition) {

		int curChro = 0;
		while (curChro < dnaSize) {
			// ****************************************************
			// deal with player sell price
			if (tmpSell.size() < sell.size()) {

				if (chromosome[curChro] <= 0) {
					curChro++;
					continue;

				} else {
					SellableType x = sell.get(tmpSell.size()).copyIt();
					x.sellPerPrice = chromosome[curChro] * SellMaxCost;

					tmpSell.add(x);

					d.pl("chro sell = " + curChro);
					curChro++;
					continue;
				}

			}

			// ****************************************************

			// price shift <amount> shift <amount> shift <amount>

			if (tmpAllShopUniqueDone < mission.size()) {

				if (tmpReading.size() >= (1 + (minShopSize * 2)) && tmpReading.size() <= (1 + maxShopSize * 2)
						&& tmpReading.size() % 2 == 1) {

					if (chromosome[curChro] <= 0) { // that mean do it now
						d.pl("size = " + tmpReading.size() + " .. " + curChro + "  unique " + tmpAllShopUniqueDone);

						AllShop x = new AllShop();

						x.PlayPrice = tmpReading.get(0) * ShopMaxCost;

						for (int i = 1; i < tmpReading.size(); i++) {
							int itemSlot = getNextUnuseMissionItem(
									(int) (tmpReading.get(i).doubleValue() * swapMultipy), curMissionItemSwapPosition);
							curMissionItemSwapPosition = itemSlot;

							x.Item[x.size] = mission.get(itemSlot).theName;
							x.data[x.size] = mission.get(itemSlot).data;
							x.amount[x.size] = (int) (tmpReading.get(i + 1).doubleValue()
									* mission.get(itemSlot).maxStack);
							i++;

							tmpAllShopUniqueDone++;
							x.size++;
						}

						tmpAllShop.add(x);
						tmpReading.clear();
						curChro++;
						continue;

					} else {
						if (chromosome[curChro] <= 0) {
							curChro++;
							dnaDecoder(chromosome, tmpAllShop, tmpSell, tmpMission, tmpReading, tmpAllShopUniqueDone,
									curMissionItemSwapPosition);
						} else {
							tmpReading.add(chromosome[curChro]);

							curChro++;
							continue;
						}
					}

				} else { // add to tmpReading

					// d.pl("chro length = " + chromosome.length + " " +
					// curChro);
					if (chromosome[curChro] <= 0) {
						curChro++;
						continue;
					} else {
						tmpReading.add(chromosome[curChro]);

						curChro++;
						continue;
					}

				}

			} else {
				break;
			}

		} // while

	}

	public int getNextUnuseMissionItem(int swapNextxTime, int curMissionItemSwapPosition) {

		int looping = curMissionItemSwapPosition;
		int returnId = -1;

		do {
			looping++;
			if (looping >= mission.size()) {
				looping = 0;
			}
			if (mission.get(looping).useIt == false) {
				if (swapNextxTime > 0) {
					swapNextxTime--;
					continue;
				}
				returnId = looping;
				break;
			}

		} while (true);

		return returnId;
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

	public void loadMissionBlockFile() {

		String filena = missionPath;
		File fff = new File(filena);

		try {

			mission.clear();

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

				MissionType miss = new MissionType();
				miss.theName = m[0];
				miss.data = Byte.parseByte(m[1]);
				miss.maxStack = Integer.parseInt(m[2]);
				miss.isBlock = Boolean.parseBoolean(m[3]);

				// d.pl("...");
				// rs[rsMax - 1].mission = 0;
				mission.add(miss);
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
				sell.add(sb);
			}

			d.pl(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			d.pl("Error load " + filena + e.getMessage());
		}
	}
}
