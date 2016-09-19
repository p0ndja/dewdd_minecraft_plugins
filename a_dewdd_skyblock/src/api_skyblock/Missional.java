package api_skyblock;

public enum  Missional {
	LV_NULL,
	LV_0_BREAK_COBBLESTONE,
	LV_1_BREAK_STONE,
	LV_2_USE_BONE_MEAL,
	LV_3_DROP_TOUCH,
	LV_4_PLACE_Y1,
	LV_5_ZOMBIE_ATTACK_1,
	LV_6_DROP_WHEAT // LV_6_DROP_WHEAT
	
	;
	
	
	public  int toID() {
		int e = getID(this);
		return e;
	}
	

	public static Missional idToMission(int m) {
		
		
		Missional a = null;
		
		switch (m) {
		case 0:
			a = Missional.LV_0_BREAK_COBBLESTONE;
			break;
		case 1:
			a = Missional.LV_1_BREAK_STONE;
			break;
		case 2:
			a = Missional.LV_2_USE_BONE_MEAL;
			break;
		case 3:
			a = Missional.LV_3_DROP_TOUCH;
			break;
		case 4:
			a = Missional.LV_4_PLACE_Y1;
			break;
		case 5:
			a = Missional.LV_5_ZOMBIE_ATTACK_1;
			break;
		
		case 6:
			a = Missional.LV_6_DROP_WHEAT;
			break;
		
			
		
		default:
			a = Missional.LV_NULL;
			break;
		}
		
		return a;
	}
	
	
	public static int getID(Missional m) {
		int id = -1;
		
		
		switch (m) {
		case LV_0_BREAK_COBBLESTONE:
			id = 0;
			break;
		case LV_1_BREAK_STONE:
			id = 1;
			break;
		case LV_2_USE_BONE_MEAL:
			id = 2;
			break;
		case LV_3_DROP_TOUCH:
			id = 3;
			break;
		case LV_4_PLACE_Y1:
			id = 4;
			break;
		case LV_5_ZOMBIE_ATTACK_1:
			id = 5;
			break;
		case LV_6_DROP_WHEAT:
			id =6;
			break;
			
			
		default:
			id = 5;
			break;
		}
		
		return id;
	}
	

}
