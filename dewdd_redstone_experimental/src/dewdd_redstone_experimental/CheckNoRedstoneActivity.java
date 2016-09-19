package dewdd_redstone_experimental;

public class CheckNoRedstoneActivity implements Runnable {

	private Redex redex;

	public CheckNoRedstoneActivity(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void run() {
		// loop all

		for (int lop = 0; lop < Redex.maxPopulation; lop++) {

			AreaType at = this.redex.listEx.get(lop);

			if (at.isRunning == false) {
				continue;
			}

			long diff = Redex.redstoneTimer - at.lastRedstoneActivity;

			if (diff > Redex.maxNoRedstoneActivityInTick) {
				at.isRunning = false;
				/*dprint.r.printAll("CheckNoRedstoneActivity : " + lop + " die "
						+ (Redex.redstoneTimer) + " - "
						+ at.lastRedstoneActivity + " = " + diff);*/

				// check score
				FitnessSubArea fs = new FitnessSubArea(this.redex, lop);
				fs.run();

			}
		}

		dprint.r.printAll("CheckNoRedstoneActivity Done");

		this.redex.CheckAllLabDone();
	}
}
