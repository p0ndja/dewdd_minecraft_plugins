package a_dewdd_skyblock_lv_1000_generator;

public class AllBlockInGameType {
	public String theName ;
	public byte data;
	public int maxStack;
	public boolean isBlock = false;
	
	public int curAmount = 0;
	
	public boolean useIt = false;
	
	
	public AllBlockInGameType copyIt() {
		AllBlockInGameType abc = new AllBlockInGameType();
		abc.theName = this.theName;
		abc.data   =  this.data;
		abc.maxStack = this.maxStack;
		abc.isBlock = this.isBlock;
		
		return abc;
	}
}