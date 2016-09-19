package a_dewdd_skyblock_lv_1000_generator;

import java.util.HashMap;

public class PlayerSimulating {
	
	public double money = 0;
	
	public HashMap<String , AllBlockInGameType> allMyInventory = new HashMap<String , AllBlockInGameType>();
	
	public HashMap<String , Farm> farmMap = new HashMap<String , Farm>();
	
	public HashMap<String , Animal> animalMap = new HashMap<String , Animal>();
	
	
	public PlayerSimulating() {
		
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

