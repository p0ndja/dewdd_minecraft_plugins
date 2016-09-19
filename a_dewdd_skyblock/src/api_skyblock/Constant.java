package api_skyblock;

import java.io.File;

import dewddtran.tr;

public class Constant {

	public static String pskyblock = "dewdd.skyblock.skyblock";
	
	public static String flag_explode = "<!explode>";
	public static String flag_monster = "<!monster>";
	public static String flag_pvp = "<pvp>";
	public static String flag_autocut = "<autocut>";
	public static String flag_autoabsorb = "<autoabsorb>";
	public static String folder_name = "plugins" + File.separator + "dewdd_skyblock";
	
	
	public static String rsProtect_filename = "ptdew_dewdd_rs_protect.txt";

	public static String rsGenerateListBlock_filename = "ptdew_dewdd_rs_generatelistblock.txt";
	
	public static String poveride = "dewdd.skyblock.overide";
	public static String presetlv = "dewdd.skyblock.resetlv";
	
	public static String flag_everyone = "<everyone>";
	public static int LV_2_USE_BONE_MEAL_AMOUNT = 45;
	public static int LV_3_DROP_TOUCH_AMOUNT = 64;
	public static int LV_6_DROP_WHEAT_AMOUNT = 64;
	
	public static String getMissionHeader(int mission) {
		String header = "skyblock_mission_header_";
	
		String aa = "";

			aa = tr.gettr(header + mission);
	
			
			
	
		return aa;
	}
	public static boolean is8_10block(int impo) {
	
		switch (impo) {
	
		case 8:
		case 9:
		case 10:
		case 11:
	
			return true;
		default:
			return false;
		}
	}

}


