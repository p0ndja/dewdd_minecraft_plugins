package testUUID;

import java.io.File;

public class Constant {

	public static String pskyblock = "dewdd.skyblock.skyblock";

	public static String folder_name = "/home/d/mis/skyblock/plugins" + File.separator + "dewdd_skyblock";

	public static String rsProtect_filename = "ptdew_dewdd_rs_protect.txt";

	public static String rsGenerateListBlock_filename = "ptdew_dewdd_rs_generatelistblock.txt";
	public static String rsLV_filename = "ptdew_dewdd_lv.txt";

	public static String poveride = "dewdd.skyblock.overide";
	public static String presetc = "dewdd.skyblock.resetlv";

	public static int LV_2_USE_BONE_MEAL_AMOUNT = 45;
	public static int LV_3_DROP_TOUCH_AMOUNT = 64;
	public static int LV_6_DROP_WHEAT_AMOUNT = 64;

	public static int rsBuffer = 1000;

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
