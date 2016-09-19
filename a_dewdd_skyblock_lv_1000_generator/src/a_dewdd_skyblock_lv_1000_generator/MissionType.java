package a_dewdd_skyblock_lv_1000_generator;

public class MissionType {
	public String theName ;
	public byte data;
	public int maxStack;
	public boolean isBlock = false;
	
	
	public MissionType copyIt() {
		MissionType abc = new MissionType();
		abc.theName = this.theName;
		abc.data   =  this.data;
		abc.maxStack = this.maxStack;
		abc.isBlock = this.isBlock;
		
		return abc;
	}
}