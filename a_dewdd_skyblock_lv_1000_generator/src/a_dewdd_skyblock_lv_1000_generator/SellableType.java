package a_dewdd_skyblock_lv_1000_generator;

public class SellableType {
	public String theName;
	public byte data;
	public int maxStack;
	public double timeToGet;

	public double sellPerPrice;

	public SellableType copyIt() {
		SellableType abc = new SellableType();
		abc.theName = this.theName;
		abc.data = this.data;
		abc.maxStack = this.maxStack;
		abc.timeToGet = this.timeToGet;
		abc.sellPerPrice = this.sellPerPrice;

		return abc;
	}
}