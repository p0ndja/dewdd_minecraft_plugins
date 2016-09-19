package dewdd_redstone_experimental;

public class RedstoneTimer implements Runnable {
	public RedstoneTimer() {
		
	}
	
	@Override
	public void run() {
		Redex.redstoneTimer ++;
		//dprint.r.printAll("RedStone Time " + Redex.redstoneTimer);
		
	}
}