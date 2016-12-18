package piston_ruin_the_world;

public class Location {
	private World world = null;
	public Location(World world ) {
		this.world = world;
	}
	
	public Block getBlock() {
		return world.getBlock(this);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	private int x ;
	private int y; 
	private int z;
	
}
