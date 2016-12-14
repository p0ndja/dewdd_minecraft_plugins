package piston_ruin_the_world;

public class World {
	public static int mapSize = 1000;
	public static int mapHeight = 256;
	
	Block block[][][] = new Block[mapSize][mapHeight][mapSize];
	
	
	public Block getBlock(Location location) {
		Block b = this.block[location.getX()][location.getY()][location.getZ()];
		return b;
	}
}
