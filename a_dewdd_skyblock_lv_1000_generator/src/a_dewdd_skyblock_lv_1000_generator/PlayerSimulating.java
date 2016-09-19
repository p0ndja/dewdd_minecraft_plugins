package a_dewdd_skyblock_lv_1000_generator;

import java.util.HashMap;

public class PlayerSimulating {

	public double money = 0;

	public HashMap<String, AllBlockInGameType> allMyInventory = new HashMap<String, AllBlockInGameType>();

	public HashMap<String, Farm> farmMap = new HashMap<String, Farm>();

	public HashMap<Byte, Animal> animalMap = new HashMap<Byte, Animal>(); // egg
																			// id
																			// :
																			// animal

	public static int MaxSizeOfFarm = 6000;

	public long curSecond = 0;

	public void letPlantAllThingAsItCan() {

		// check mush room

		String glowInGrass[] = new String[5];
		glowInGrass[0] = "WHEAT";
		glowInGrass[1] = "PUMPKIN";
		glowInGrass[2] = "POTATO_ITEM";
		glowInGrass[3] = "CARROT_ITEM";
		glowInGrass[4] = "MELON";

		int lowestID = 0;
		String lowestString = glowInGrass[lowestID] + ":0";

		for (int glowInGrassLoop = 1; glowInGrassLoop < 5; glowInGrassLoop++) {
			if (farmMap.get(glowInGrass[glowInGrassLoop] + ":0").sizeFarm < farmMap
					.get(glowInGrass[lowestID] + ":0").sizeFarm) {

				lowestID = glowInGrassLoop;

			}

		}

		lowestString = glowInGrass[lowestID] + ":0";

		AllBlockInGameType grass = allMyInventory.get("GRASS:0");
		if (grass == null) {
			grass = allMyInventory.get("DIRT:0");

		}

		if (grass == null) {
			grass = allMyInventory.get("DIRT:1");

		}
		if (grass == null) {
			grass = allMyInventory.get("DIRT:2");

		}

		if (grass != null) {

			if (grass.curAmount > 0) {

				// search

				AllBlockInGameType plant = allMyInventory.get(lowestString);
				if (plant != null) {
					if (plant.curAmount > 0) {

						// add new plant

						Farm tmpFarm = farmMap.get(lowestString);
						if (tmpFarm != null) {

							tmpFarm.itemIdData = lowestString;
							tmpFarm.countTick[tmpFarm.sizeFarm] = 0;
							tmpFarm.sizeFarm++;

							plant.curAmount--;
							grass.curAmount--;

							

							d.pl(curSecond +  " > buy : " + tmpFarm.itemIdData + " , sizeFarm " + tmpFarm.sizeFarm);

						}
					}

				}
			}

		}

		// ************************************
		// cactus

		// check mush room

		String glowInSand[] = new String[2];

		glowInSand[0] = "CACTUS";
		glowInSand[1] = "SUGAR_CANE";

		lowestID = 0;
		lowestString = glowInSand[lowestID] + ":0";

		for (int glowInGrassLoop = 1; glowInGrassLoop < 2; glowInGrassLoop++) {
			if (farmMap.get(glowInSand[glowInGrassLoop] + ":0").sizeFarm < farmMap
					.get(glowInSand[lowestID] + ":0").sizeFarm) {

				lowestID = glowInGrassLoop;

			}

		}

		lowestString = glowInSand[lowestID] + ":0";

		AllBlockInGameType sand = allMyInventory.get("GRASS:0");
		if (sand == null) {
			sand = allMyInventory.get("SAND:0");

		}

		if (sand != null) {

			if (sand.curAmount > 0) {

				// search

				AllBlockInGameType plant = allMyInventory.get(lowestString);
				if (plant != null) {
					if (plant.curAmount > 0) {

						// add new plant

						Farm tmpFarm = farmMap.get(lowestString);
						if (tmpFarm != null) {

							tmpFarm.itemIdData = lowestString;
							tmpFarm.countTick[tmpFarm.sizeFarm] = 0;
							tmpFarm.sizeFarm++;

							plant.curAmount--;
							sand.curAmount--;

						

							d.pl(curSecond + " > buy : " + tmpFarm.itemIdData + " , sizeFarm " + tmpFarm.sizeFarm);

						}
					}
				}

			}

		}

		// ************************************
		// mushroom

		// check mush room

		String glowInDark[] = new String[2];

		glowInDark[0] = "RED_MUSHROOM";
		glowInDark[1] = "BROWN_MUSHROOM";

		lowestID = 0;
		lowestString = glowInDark[lowestID] + ":0";

		for (int glowInGrassLoop = 1; glowInGrassLoop < 2; glowInGrassLoop++) {
			if (farmMap.get(glowInDark[glowInGrassLoop] + ":0").sizeFarm < farmMap
					.get(glowInDark[lowestID] + ":0").sizeFarm) {

				lowestID = glowInGrassLoop;

			}

		}

		lowestString = glowInDark[lowestID] + ":0";

		AllBlockInGameType darkBlock = allMyInventory.get("COBBLESTONE:0");
		if (darkBlock == null) {
			darkBlock = allMyInventory.get("STONE:0");

		}
		if (darkBlock == null) {
			darkBlock = allMyInventory.get("DIRT:0");

		}

		if (darkBlock != null) {

			if (darkBlock.curAmount > 0) {

				// search

				AllBlockInGameType plant = allMyInventory.get(lowestString);
				if (plant != null) {
					if (plant.curAmount > 0) {

						// add new plant

						Farm tmpFarm = farmMap.get(lowestString);
						if (tmpFarm != null) {

							tmpFarm.itemIdData = lowestString;
							tmpFarm.countTick[tmpFarm.sizeFarm] = 0;
							tmpFarm.sizeFarm++;

							plant.curAmount--;
							darkBlock.curAmount--;

					

							d.pl(curSecond + " > buy : " + tmpFarm.itemIdData + " , sizeFarm " + tmpFarm.sizeFarm);

						}
					}
				}
			}

		}
		
		// wart
		
		// ************************************
				// mushroom

				// check mush room

				String glowWart[] = new String[2];

				glowWart[0] = "RED_MUSHROOM";
				glowWart[1] = "BROWN_MUSHROOM";

				lowestID = 0;
				lowestString = glowWart[lowestID] + ":0";

				for (int glowInGrassLoop = 1; glowInGrassLoop < 2; glowInGrassLoop++) {
					if (farmMap.get(glowWart[glowInGrassLoop] + ":0").sizeFarm < farmMap
							.get(glowWart[lowestID] + ":0").sizeFarm) {

						lowestID = glowInGrassLoop;

					}

				}

				lowestString = glowWart[lowestID] + ":0";

				AllBlockInGameType wartBlock = allMyInventory.get("COBBLESTONE:0");
				if (wartBlock == null) {
					wartBlock = allMyInventory.get("STONE:0");

				}
				if (wartBlock == null) {
					wartBlock = allMyInventory.get("DIRT:0");

				}

				if (wartBlock != null) {

					if (wartBlock.curAmount > 0) {

						// search

						AllBlockInGameType plant = allMyInventory.get(lowestString);
						if (plant != null) {
							if (plant.curAmount > 0) {

								// add new plant

								Farm tmpFarm = farmMap.get(lowestString);
								if (tmpFarm != null) {

									tmpFarm.itemIdData = lowestString;
									tmpFarm.countTick[tmpFarm.sizeFarm] = 0;
									tmpFarm.sizeFarm++;

									plant.curAmount--;
									wartBlock.curAmount--;

									

									d.pl(curSecond + " > buy : " + tmpFarm.itemIdData + " , sizeFarm " + tmpFarm.sizeFarm);

								}
							}
						}
					}

				}

	}

	public PlayerSimulating() {

		// setup farmMap
		String tmpAdd[] = new String[10];
		tmpAdd[0] = "WHEAT";
		tmpAdd[1] = "RED_MUSHROOM";
		tmpAdd[2] = "BROWN_MUSHROOM";
		tmpAdd[3] = "CACTUS";
		tmpAdd[4] = "SUGAR_CANE";
		tmpAdd[5] = "PUMPKIN";
		tmpAdd[6] = "POTATO_ITEM";
		tmpAdd[7] = "CARROT_ITEM";
		tmpAdd[8] = "MELON";
		tmpAdd[9] = "NETHER_STALK";

		for (int i = 0; i < tmpAdd.length; i++) {
			Farm x = new Farm();
			x.itemIdData = tmpAdd[i] + ":0";
			x.countTick[x.sizeFarm] = 0;

			x.sizeFarm++;
			/*
			 * x.countTick = 0; x.amount = 0;
			 */

			farmMap.put(tmpAdd[i] + ":0", x);
		}

		// setup animalMap

		byte tmpAdd2[] = new byte[5];
		tmpAdd2[0] = 99; // pig
		tmpAdd2[1] = 91; // sheep
		tmpAdd2[2] = 92; // cow
		tmpAdd2[3] = 93; // chicken
		tmpAdd2[4] = 96; // cow mush room

		for (int i = 0; i < tmpAdd2.length; i++) {
			Animal x = new Animal();
			x.itemId = "MONSTER_EGG";
			x.data = tmpAdd2[i];
			x.countTick = 0;

			animalMap.put(x.data, x);
		}

		// setup allMyInventory

		allMyInventory.clear();

		for (int i = 0; i < Main.co.allBlockInGame.size(); i++) {
			AllBlockInGameType x = Main.co.allBlockInGameAsList.get(i).copyIt();
			x.curAmount = 0;

			allMyInventory.put(x.theName + ":" + x.data, x);

		}

	}

}

// each plant has own hashmap
class Farm {

	// 1 block 1 plant
	public String itemIdData = ""; // SEEDS:0 , RED_MUSHROOM:0 , BROWN_MUSHROOM ,
								// CACTUS ,
								// SUGAR_CANE , PUMPKIN , POTATO , WHEAT ,
								// CARROT_ITEM , MELON , NETHER_STALK

	public long countTick[] = new long[PlayerSimulating.MaxSizeOfFarm];

	public int sizeFarm = 0;

}

class Animal {

	public String itemId = "";
	public byte data = 0;

	public int amount = 0;
	public long countTick = 0;

	// COOKED_CHICKEN , EGG
	// WOOL
	// COOKED_BEEF cow , mushroom
	// GRILLED_PORK pig

	// MONSTER_EGG:90 pig
	// MONSTER_EGG:91 sheep
	// MONSTER_EGG:92 cow
	// MONSTER_EGG:93 chicken
	// MONSTER_EGG:96 cow mushroom

}
