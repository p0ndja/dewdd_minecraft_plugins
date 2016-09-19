package dewddflower;

public class RSData {
	public int x1;
	public int y1;
	public int z1;
	
	public int x2;
	public int y2;
	public int z2;

	public String p[] = null;
	public int mission = 0;
	public long lastUsed = 0;

	public int tmpForCountingBone1 = 0;
	public int tmpLongForRememberLastTime = 0;

	public int autoCutCount = 0;
	public long autoCutLastTime = 0;

	public RSData copyIt() {
		RSData tmp = new RSData();

		tmp.x1 = x1;
		tmp.y1 = y1;
		tmp.z1 = z1;
		
		tmp.x2 = x2;
		tmp.y2 = y2;
		tmp.z2 = z2;

		tmp.p = new String[dewset.FWMaxPlayer];
		for (int i = 0; i < dewset.FWMaxPlayer; i++) {
			tmp.p[i] = p[i];
		}

		tmp.mission = mission;
		this.lastUsed = mission;

		return tmp;

	}

}