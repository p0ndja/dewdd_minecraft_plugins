package dewdd_redstone_experimental;

import org.bukkit.Material;
import org.bukkit.block.Block;

import core_optimization_api.Chromosome;

public class DecodeSubDNA implements Runnable {
	private Redex redex;
	private int curId = 0;

	public DecodeSubDNA(Redex redex, int curId) {
		this.redex = redex;
		this.curId = curId;
	}

	@Override
	public void run() {
		/*
		 * if (curId >= 1) { return; }
		 */
		// re copying start pattern to them
		Block hostBlock = null;
		Block setBlock = null;

		if (curId % 100 == 0) {
		dprint.r.printAll("Decoding : curid " + curId);

		}
		
		
		//dprint.r.printAll("dnaList Length " + redex.dnaList.size());
		AreaType at = redex.listEx.get(curId);
		at.isRunning = true;
		Chromosome dna = redex.dnaList.get(curId);

		int diffX = redex.output.loc.rx - redex.output.loc.lx;
		int diffY = redex.output.loc.ry - redex.output.loc.ly;
		int diffZ = redex.output.loc.rz - redex.output.loc.lz;

		// decoding DNA in these range

		int startPoint = 0;

		// 0 piston
		// 1 sticky Piston
		// 2 Slime Block
		// 3 RedStone Block

		Block leftTopBlock = at.getBlocklxlylz();

		while (startPoint + 4 < Redex.dnaLength) {
			double tmpx = Math.abs(dna.dna[startPoint]) * diffX;
			tmpx = tmpx % diffX;
			int posx = (int) tmpx;

			double tmpy = Math.abs(dna.dna[startPoint + 1]) * diffY;
			tmpy = tmpy % diffY;
			int posy = (int) tmpy;

			double tmpz = Math.abs(dna.dna[startPoint + 2]) * diffZ;
			tmpx = tmpz % diffZ;
			int posz = (int) tmpz;

			double tmpb = Math.abs(dna.dna[startPoint + 3]) * 4;
			tmpb = tmpb % 4;
			int tmpb2 = (int) tmpb;

			byte pistonFace = 0; // piston face

			Material setType = null;
			switch (tmpb2) {
			case 0:

				if (startPoint + 4 >= Redex.dnaLength) {
					return;
				}

				setType = Material.PISTON_BASE;

				// dprint.r.printAll("piston grep " +
				// Math.abs(dna.dna[startPoint + 4]));

				int tmpFace = (int) Math.round(Math.abs(dna.dna[startPoint + 4]) * 6);
				if (tmpFace < 0) {
					tmpFace = 0;
				}
				if (tmpFace > 5) {
					tmpFace = 5;
				}
				pistonFace = (byte) tmpFace;

				// dprint.r.printAll("piston face " + pistonFace);

				break;
			case 1:
				if (startPoint + 4 >= Redex.dnaLength) {
					return;
				}

				setType = Material.PISTON_STICKY_BASE;

				// dprint.r.printAll("piston grep " +
				// Math.abs(dna.dna[startPoint + 4]));

				tmpFace = (int) Math.round(Math.abs(dna.dna[startPoint + 4]) * 6);
				if (tmpFace < 0) {
					tmpFace = 0;
				}
				if (tmpFace > 5) {
					tmpFace = 5;
				}
				pistonFace = (byte) tmpFace;
				// dprint.r.printAll("piston face " + pistonFace);

				break;
			case 2:
				setType = Material.SLIME_BLOCK;
				break;
			case 3:
				setType = Material.REDSTONE_BLOCK;
				break;
			default:
				setType = Material.AIR;
				break;

			}

			// time to set Block
			Block b2 = leftTopBlock.getRelative(posx, posy, posz);

			if (b2.getType() == Material.AIR) {
				b2.setType(setType);
				b2.setData(pistonFace);

			}
			//dprint.r.printC(posx + "," + posy + "," + posz + " = " + setType.name());

			startPoint += 4;
		}

	}
}