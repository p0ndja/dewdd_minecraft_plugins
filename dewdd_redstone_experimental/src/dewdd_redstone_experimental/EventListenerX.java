package dewdd_redstone_experimental;

import core_optimization_api.Chromosome;
import core_optimization_api.InterfaceEvent;

public class EventListenerX implements InterfaceEvent {
	private Redex redex ;
	
	public EventListenerX(Redex redex) {
		this.redex = redex;
	}

	@Override
	public void theBestAllTheTime(Chromosome cho, double fitness, int evo) {
		dprint.r.printAll("found new the best " + fitness + " , evo " + evo);
		
		if (redex.topBest == null) {
			redex.topBest = new AreaType();
			redex
		}
		
		
		
		
	}

	@Override
	public void theBestOfEachEvolution(Chromosome cho, double fitness,
			int evo) {
		// TODO Auto-generated method stub
		
	}

	
}
