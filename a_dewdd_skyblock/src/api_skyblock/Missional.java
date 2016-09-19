package api_skyblock;

public enum  Missional {
	LV_NULL,
	LV_0_COBBLESTONE_MACHINE,
	LV_1_Break_STONE,
	LV_2_USE_BONE_MEAL,
	LV_3_DROP_TOUCH,
	LV_4_Place_y1
	
	;
	
	
	public  int toID() {
		int e = getID(this);
		return e;
	}
	

	public static Missional idToMission(int m) {
		int id = -1;
		
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
		default:
			id = -1;
			break;
		}
		
		return id;
	}
	

}
