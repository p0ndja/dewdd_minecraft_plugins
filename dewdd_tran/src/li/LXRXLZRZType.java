package li;

public class LXRXLZRZType {
	public int lx;
	public int rx;

	public int ly;
	public int ry;

	public int lz;
	public int rz;

	public LXRXLZRZType(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.set(x1, y1, z1, x2, y2, z2);

	}

	public int[] getmiddle() {
		int re[] = new int[3];
		re[0] = (lx + rx) / 2;
		re[1] = (ly + ry) / 2;
		re[2] = (lz + rz) / 2;

		return re;

	}

	public void set(int x1, int y1, int z1, int x2, int y2, int z2) {
		if (x1 <= x2) {
			this.lx = x1;
			this.rx = x2;
		} else {
			this.lx = x2;
			this.rx = x1;
		}

		if (y1 <= y2) {
			this.ly = y1;
			this.ry = y2;
		} else {
			this.ly = y2;
			this.ry = y1;
		}

		if (z1 <= z2) {
			this.lz = z1;
			this.rz = z2;
		} else {
			this.lz = z2;
			this.rz = z1;
		}
	}
}
