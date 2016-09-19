package dewdd_redstone_experimental;

import java.util.ArrayList;

import core_optimization_api.Chromosome;

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
		/*	dprint.r.printAll("CheckAllLab lop 1 at " + (at == null ? "yes" : "no") + " , cho " 
					+ ((at.chro == null) ? "yes" : "no"));*/
			
			if (at.isRunning == true) {
				dprint.r.printAll("Lab ID "+ lop +" is running");
				dprint.r.printAll("check All lab done False");
				return;
			}

		}

		this.redex.curMode = 0; // back to setup mode

		dprint.r.printAll("check All lab done true");

		// add the best
		
		ArrayList <Chromosome> tmp  = new ArrayList<Chromosome>();
		for (int lop = 0; lop < Redex.maxPopulation; lop++) {
			
			AreaType at = this.redex.listEx.get(lop);
		/*	dprint.r.printAll("CheckAllLab lop 2 at " + (at == null ? "yes" : "no") + " , cho " 
			+ ((at.chro == null) ? "yes" : "no"));*/
			at.chro.fitness = 1 /(at.chro.fitness+1);
			tmp.add(at.chro);
		}
		
		if (redex.topBest != null) {
		if (redex.topBest.chro.fitness == (1 / (double)(101)) ) {
			
			dprint.r.printAll("done found the best");
			return;
		}
		}
		
		this.redex.hybrid.setAllChromosomeGen1(tmp);
		this.redex.hybrid.produceNextGen(redex.evolutionCount ++);
		dprint.r.printAll("next Evolution : " + redex.evolutionCount);
		
		
		if (redex.isAutoMode == true) {
		
			redex.CleanAllArea(true);
		
		}

		/*double unsort[] = new double[Redex.maxPopulation];

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
			
			
			
		}*/
		

		// if everything die , produce next gen

	}

}
