package dewdd_redstone_experimental;

import core_optimization_api.Chromosome;
import core_optimization_api.InterfaceEvent;
import dewddtran.tr;
import li.LXRXLZRZType;

public class EventListenerX implements InterfaceEvent {
	private Redex redex ;
	
	public EventListenerX(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void theBestAllTheTime(Chromosome cho, double fitness, int evo) {
		dprint.r.printAll(">>> found new the best " + fitness + " , evo " + evo);
		
		if (redex.topBest == null) {
			redex.topBest = new AreaType();
			redex.topBest.world = redex.world;
			redex.topBest.chro = cho.copyChromosome();
			int space = 50;
			LXRXLZRZType loc = redex.start.loc;
			redex.topBest.loc = new LXRXLZRZType(loc.lx - space , loc.ly , loc.lz,
					loc.rx, loc.ry, loc.rz);
			
		}
		else {
			redex.topBest.chro = cho.copyChromosome();
		}
		// time to Rerendering
		
		dprint.r.printAll("best Location " + redex.topBest.loc.lx + "," + redex.topBest.loc.ly + "," + redex.topBest.loc.lz);
		
		CleanSubArea caa = new CleanSubArea(redex, redex.topBest);
		caa.run();
		DecodeSubDNA yes = new DecodeSubDNA(redex, redex.topBest);
		yes.run();
		
		
		
		
	}

	@Override
	public void theBestOfEachEvolution(Chromosome cho, double fitness,
			int evo) {
		// TODO Auto-generated method stub
		
	}

	
}
