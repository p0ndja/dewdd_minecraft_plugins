package li;

import java.util.Random;

public class useful {
	public static int randomInteger(int min, int max) {
		Random rand = new Random();
		int randomNum = min + (int) (Math.random() * ((max - min) + 1));
		return randomNum;
	}
}
