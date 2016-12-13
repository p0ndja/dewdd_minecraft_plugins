package li;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Useful {
	
	public static double distance2Point3D(int x , int y , int z , int x2, int y2 , int z2) {
		
		double temp5 = 0;
		double temp2 = 0;
		double temp3 = 0;
		double temp1 = Math.pow((double) x - (double) x2, 2);

		temp2 = Math.pow((double) y - (double) y2, 2);

		temp3 = Math.pow((double) z - (double) z2, 2);

		temp5 = Math.pow(temp1 + temp2 + temp3, 0.5);

		return temp5;
	}
	
	public static int randomInteger(int min, int max) {
		Random rand = new Random();
		int randomNum = min + (int) (Math.random() * ((max - min) + 1));
		return randomNum;
	}

	public static Block getTopBlockHigh(Block block) {
		for (int i = 255; i >= 0; i--) {

			Block rela = block.getWorld().getBlockAt(block.getX(), i, block.getZ());
			if (rela.getType() != Material.AIR) {
				return rela.getRelative(0, 2, 0);
			}
		}

		return null;
	}

}