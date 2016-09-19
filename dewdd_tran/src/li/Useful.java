package li;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Useful {
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