package api_skyblock;

public class RSData {
	public int x;
	public int y;
	public int z;
	public String p[] = null;
	public int mission = 0;
	public long lastUsed = 0;

	public int tmpForCountingBone1 = 0;
	public int tmpLongForRememberLastTime = 0;

	public int autoCutCount = 0;
	public long autoCutLastTime = 0;

	public RSData copyIt() {
		RSData tmp = new RSData();

		tmp.x = x;
		tmp.y = y;
		tmp.z = z;
		

		tmp.p = new String[api_skyblock.RSMaxPlayer];
		for (int i = 0; i < api_skyblock.RSMaxPlayer; i++) {
			tmp.p[i] = p[i];
		}

		tmp.mission = mission;
		this.lastUsed = mission;
		
		return tmp;

	}

}