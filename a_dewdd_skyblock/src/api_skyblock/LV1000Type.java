package api_skyblock;

import org.bukkit.Material;

public class LV1000Type {

	// to done
	public String[] needNameData = new String[10];
	public int[] needAmount = new int[10];

	public int needSize = 0;

	public String[] rewardNameData = new String[10];
	public int[] rewardAmount = new int[10];

	public int rewardSize = 0;

	public Byte getData(String idData) {
		String m[] = idData.split(":");
		Byte b = Byte.parseByte(m[1]);

		return b;
	}

	public Material getMaterial(String idData) {
		String m[] = idData.split(":");
		Material mo = Material.getMaterial(m[0]);

		return mo;
	}
}
