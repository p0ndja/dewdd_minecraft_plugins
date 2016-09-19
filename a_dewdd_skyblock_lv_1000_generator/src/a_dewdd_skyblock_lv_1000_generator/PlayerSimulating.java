package a_dewdd_skyblock_lv_1000_generator;

import java.util.HashMap;



public class PlayerSimulating {

	public static int MaxSizeOfFarm = 6000;

	public double money = 0;

	public HashMap<String, AllBlockInGameType> allMyInventory = new HashMap<String, AllBlockInGameType>();

	public long curSecond = 0;

	public PlayerSimulating() {

	
		// setup allMyInventory

		allMyInventory.clear();

		for (int i = 0; i < Main.co.allBlockInGame.size(); i++) {
			AllBlockInGameType x = Main.co.allBlockInGameAsList.get(i).copyIt();
			x.curAmount = 0;

			allMyInventory.put(x.theName + ":" + x.data, x);

		}

	}


}
