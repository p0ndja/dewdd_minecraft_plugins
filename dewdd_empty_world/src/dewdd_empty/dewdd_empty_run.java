/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_empty;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class dewdd_empty_run extends ChunkGenerator {

	@Override
	public byte[][] generateBlockSections(World world, Random random,
			int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		byte[][] result = new byte[world.getMaxHeight() / 16][]; // world height
																	// / chunk
																	// part
																	// height
																	// (=16,
																	// look
																	// above)
		// System.out.println(" !@#!@#@#@!#@! #@! #!@ #@! #!@ @# !@#@!# @# !@#! dewddempty @!#!@#@!# @! #@#");
		return result;
	}

}
