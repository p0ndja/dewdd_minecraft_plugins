package dewdd_redstone_experimental;

public class CheckNothingBetter implements Runnable {

	private Redex redex;

	public CheckNothingBetter(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void run() {
		// loop all

		for (int lop = 0; lop < Redex.maxPopulation; lop++) {

			AreaType at = redex.listEx.get(lop);
			
			if (at.isRunning == false) {
				continue;
			}

			// call check score
			FitnessSubArea sub = new FitnessSubArea(redex, lop);
			sub.run();

			if (at.lastCheckScore <= at.score) { // nothing better
				at.isRunning = false;
				dprint.r.printAll("CheckNothingBetter : " + lop + " die " + at.lastCheckScore + " | " + at.score);

			}

			at.lastCheckScore = at.score;
		}
		
		dprint.r.printAll("CheckNothingBetter Done");
		

		redex.CheckAllLabDone();
	}
}
