package dewddft;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class AllShopCore {
	public void loadAllShop() {
		String worldf = "ptdew_dewdd_allshop.txt";

		File dir = new File(DigEventListener2.folder_name);
		dir.mkdir();

		String filena = DigEventListener2.folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			DigEventListener2.allShop.clear();

			fff.createNewFile();

			System.out.println("ptdeW&DewDD ft allShop loading : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save

			String m[];

			while ((strLine = br.readLine()) != null) {
				m = strLine.split("\\s+");
				AllShop aso = new AllShop();
				aso.size = 0;

				aso.playPrice = Double.parseDouble(m[0]);
				int amount = Integer.parseInt(m[1]);

				for (int i = 0; i < amount; i++) {
					strLine = br.readLine();
					m = strLine.split("\\s+");
					String m2[] = m[0].split(":");

					aso.item[aso.size] = m2[0];
					aso.data[aso.size] = Byte.parseByte(m2[1]);
					aso.amount[aso.size] = Integer.parseInt(m[1]);

					aso.size++;
				}

				DigEventListener2.allShop.add(aso);

			}

			System.out.println("ptdew&DewDD ft : all Shop loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error load " + filena + e.getMessage());
		}
	}
}
