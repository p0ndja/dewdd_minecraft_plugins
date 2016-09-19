package a_dewdd_skyblock_lv_1000_generator;

import java.util.HashMap;

public class PlayerSimulating {
	
	public double money = 0;
	
	public HashMap<String , AllBlockInGameType> allMyInventory = new HashMap<String , AllBlockInGameType>();
	
	public HashMap<String , Farm> farmMap = new HashMap<String , Farm>();
	
	public HashMap<Byte , Animal> animalMap = new HashMap<Byte , Animal>(); // egg id : animal
	
	
	public PlayerSimulating() {
		
		
		// setup farmMap
		String tmpAdd [] = new String [11];
		tmpAdd[0] = "SEEDS";
		tmpAdd[1] = "RED_MUSHROOM";
		tmpAdd[2] = "BROWN_MUSHROOM";
		tmpAdd[3] = "CACTUS";
		tmpAdd[4] = "SUGAR_CANE";
		tmpAdd[5] = "PUMPKIN";
		tmpAdd[6] = "POTATO";
		tmpAdd[7] = "WHEAT";
		tmpAdd[8] = "CARROT_ITEM";
		tmpAdd[9] = "MELON";
		tmpAdd[10] = "NETHER_STALK";

		for (int i = 0; i < tmpAdd.length ; i ++) {
			Farm x = new Farm();
			x.itemId = tmpAdd[i];
			x.countTick = 0;
			x.amount = 0;
			
			farmMap.put(tmpAdd[i], x);
		}
		
		// setup animalMap

		byte tmpAdd2 [] = new byte [5];
		tmpAdd2[0] = 99 ; // pig
		tmpAdd2[1] = 91 ; // sheep
		tmpAdd2[2] = 92 ; // cow
		tmpAdd2[3] = 93 ; // chicken
		tmpAdd2[4] = 96 ; // cow mush room
		
		
		for (int i = 0 ; i < tmpAdd2.length ; i ++ ){
			Animal x  = new Animal ();
			x.itemId = "MONSTER_EGG";
			x.data = tmpAdd2[i];
			x.countTick = 0;
			
			animalMap.put(x.data, x);
		}
		
		
		// setup allMyInventory
		
		allMyInventory.clear();
		
		for (int i = 0 ; i < Main.co.allBlockInGame.size() ; i++ ){
			AllBlockInGameType x = Main.co.allBlockInGame.get(i).copyIt();
			allMyInventory.put(x.theName + ":" + x.data, x);
			
		}
		
		
	}
	

}

class Farm{
	public String itemId ="";  // SEEDS:0 , RED_MUSHROOM:0 , BROWN_MUSHROOM , CACTUS , 
	  							// SUGAR_CANE , PUMPKIN , POTATO , WHEAT , CARROT_ITEM , MELON , NETHER_STALK
	
	// amount always be 1
	public int amount = 0;
	
	public long countTick = 0;
	
}

class Animal {
	
	public String itemId = "";
	public byte data = 0;
	
	public int amount = 0;
	public long countTick = 0;
	
	// COOKED_CHICKEN , EGG
	// WOOL
	// COOKED_BEEF    cow , mushroom
	// GRILLED_PORK  pig
	
	// MONSTER_EGG:90   pig
	// MONSTER_EGG:91 sheep
	// MONSTER_EGG:92 cow
	// MONSTER_EGG:93 chicken
	// MONSTER_EGG:96  cow mushroom
	
	
	
}

