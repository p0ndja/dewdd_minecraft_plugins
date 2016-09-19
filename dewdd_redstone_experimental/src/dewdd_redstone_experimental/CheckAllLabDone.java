package dewdd_redstone_experimental;

public class CheckAllLabDone implements Runnable {
	private Redex redex;

	public CheckAllLabDone(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void run() {
		// check All Alea if everything done produce next gen

		for (int lop = 0; lop < Redex.maxPopulation; lop++) {
			AreaType at = this.redex.listEx.get(lop);

			if (at.isRunning == true) {
				dprint.r.printAll("check All lab done False");
				return;
			}

		}

		this.redex.curMode = 0; // back to setup mode

		dprint.r.printAll("check All lab done true");

		// if everything die , produce next gen

	}

}
