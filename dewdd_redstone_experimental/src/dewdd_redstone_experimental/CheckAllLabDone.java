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
			AreaType at = redex.listEx.get(lop);

			if (at.isRunning == true) {
				dprint.r.printAll("check All lab done False");
				return;
			}

		}

		dprint.r.printAll("check All lab done true");

		// if everything die , produce next gen

	}

}
