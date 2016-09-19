package dewdd_redstone_experimental;

import core_optimization_api.Chromosome;
import dewsort_api.BubbleSort;

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

		// add the best

		double unsort[] = new double[Redex.maxPopulation];

		// extract all fitness
		for (int lop = 0; lop < Redex.maxPopulation; lop++) {
			AreaType at = redex.listEx.get(lop);

			Chromosome cho = at.chro;
			unsort[lop] = cho.fitness;

		}
		
		// sorting them
		
		BubbleSort bs = new BubbleSort();
		int index[] = bs.sortIndex(unsort);
		// after we got index    
		// search the best fitness top ten 
		
		if (redex.topBest == null ) {
			redex.topBest = new AreaType();
			redex.topBest.chro = redex.listEx.get(index[0]).chro.copyChromosome();
			
			
			
		}
		

		// if everything die , produce next gen

	}

}
