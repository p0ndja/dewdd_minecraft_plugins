package api_skyblock;

public enum  Missional {
	LV_NULL,
	LV_0_COBBLESTONE_MACHINE,
	LV_1_Break_STONE,
	LV_2_USE_BONE_MEAL,
	LV_3_DROP_TOUCH,
	LV_4_Place_y1,
	LV_5_ZOMBIE_ATTACK_1
	
	;
	
	
	public  int toID() {
		int e = getID(this);
		return e;
	}
	

	public static Missional idToMission(int m) {
		
		
		Missional a = null;
		
		switch (m) {
		case 0:
			a = Missional.LV_0_COBBLESTONE_MACHINE;
			break;
		case 1:
			a = Missional.LV_1_Break_STONE;
			break;
		case 2:
			a = Missional.LV_2_USE_BONE_MEAL;
			break;
		case 3:
			a = Missional.LV_3_DROP_TOUCH;
			break;
		case 4:
			a = Missional.LV_4_Place_y1;
			break;
		case 5:
			a = Missional.LV_5_ZOMBIE_ATTACK_1;
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
		case LV_0_COBBLESTONE_MACHINE:
			id = 0;
			break;
		case LV_1_Break_STONE:
			id = 1;
			break;
		case LV_2_USE_BONE_MEAL:
			id = 2;
			break;
		case LV_3_DROP_TOUCH:
			id = 3;
			break;
		case LV_4_Place_y1:
			id = 4;
			break;
		case LV_5_ZOMBIE_ATTACK_1:
			id = 5;
			break;
			
		default:
			id = 5;
			break;
		}
		
		return id;
	}
	

}
