package a_dewdd_skyblock_lv_1000_generator;

public class SellableType {
	public int index;
	public double timeToGet;

	public double sellPerPrice;
	public int allItemYouCanFind;

	public SellableType copyIt() {
		SellableType abc = new SellableType();

		abc.timeToGet = this.timeToGet;
		abc.sellPerPrice = this.sellPerPrice;
		abc.allItemYouCanFind = this.allItemYouCanFind;

		return abc;
	}
}