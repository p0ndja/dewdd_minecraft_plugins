package dewdd_redstone_experimental;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JButton;

class Save extends Sum {
	public void saveScore(int score) {
		String path = "/ramdisk/nat.txt"; // "D:\\NAT !!\\score.txt";
		File file = new File(path);

		FileWriter writer;
		try {

			writer = new FileWriter(file, true); // True = Append to file,
													// false = Overwrite
			// Double score = null;
			writer.write("Your Score is " + score + System.lineSeparator());
			writer.close();

			JOptionPane.showMessageDialog(null, "Write success!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Sum {

	public static void main(String[] args) {
		JButton btnButton = new JButton("Open game");
		btnButton.setBounds(128, 93, 162, 23);
		Scanner scan = new Scanner(System.in);
		double num1, num2, ans, result = 0;
		boolean correct = true;
		char operator[] = { '+', '-', '*', '/' };
		char op;
		int id, score = 0;
		while (correct) {
			num1 = (int) (1 + (Math.random() * 12));
			num2 = (int) (1 + (Math.random() * 12));
			id = (int) (Math.random() * 4);
			op = operator[id];

			String s = (String) JOptionPane.showInputDialog(null,
					num1 + " " + op + " " + num2 + "      \n                          " + "Enter answer: ");

			try {
				ans = Double.parseDouble(s);
			} catch (java.lang.NumberFormatException error) {
				error.printStackTrace();
				continue;
			}

			switch (op) {
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case '*':
				result = num1 * num2;
				break;
			case '/':
				result = num1 / num2;
				break;
			}
			if (result == ans) {
				correct = true;
				JOptionPane.showMessageDialog(null, "Correct! Good job.");
				score += 1;
			} else {
				correct = false;
				JOptionPane.showMessageDialog(null, "Incorrect Game Over.");
			}
		}
		JOptionPane.showMessageDialog(null, "Score : " + score);

		Save save = new Save();
		save.saveScore(score);
	}

}
