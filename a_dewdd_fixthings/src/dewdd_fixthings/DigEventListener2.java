/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewdd_fixthings;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {

	class autofix implements Runnable {
		private int		amount;
		private int		oldamount;

		private Player	p;

		public autofix(int amount, Player player) {
			this.amount = amount;
			this.oldamount = amount;

			this.p = player;

			Bukkit.getScheduler().scheduleSyncDelayedTask(
					DigEventListener2.this.ac, this,
					DigEventListener2.this.rnd.nextInt(20) + 1);

		}

		@Override
		public void run() {

			// all armor
			for (ItemStack itm : this.p.getPlayer().getInventory()
					.getArmorContents()) {

				if (itm == null) continue;
				if (itm.getType().getMaxDurability() <= 0) continue;
				if (this.amount <= 0) break;

				// fix 1 times

				if (itm.getDurability() <= 0)
					continue;
				else {
					itm.setDurability((short) (itm.getDurability() - 1));
					this.amount--;

					continue;
				}

			}

			for (ItemStack itm : this.p.getPlayer().getInventory()
					.getContents()) {
				if (itm == null) continue;
				if (itm.getType().getMaxDurability() <= 0) continue;

				if (this.amount <= 0) break;

				if (itm.getDurability() <= 0)
					continue;
				else {
					itm.setDurability((short) (itm.getDurability() - 1));
					this.amount--;
					continue;
				}
			}

			if ((this.amount > 0) && (this.oldamount != this.amount))
				new autofix(this.amount, this.p);
		}
	}

	class delay extends Thread {

		@Override
		public void run() {
			while (DigEventListener2.this.ac == null)
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			DigEventListener2.this.reloadpl();
		}
	}

	class eachMinutes implements Runnable {
		@Override
		public void run() {

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p == null) continue;
				// permission
				if (p.hasPermission(DigEventListener2.this.pfix))
					new autofix(DigEventListener2.this.fixvalueIn1Minute, p);

			}
		}
	}

	public static String	version				= "1.0";

	public JavaPlugin		ac					= null;

	public int				fixvalueIn1Minute	= 30;
	public String			folder_name			= "plugins" + File.separator
			+ "dewdd_fixthings";
	String					pfix				= "dewdd.fixthings.fix";

	String					psetandreload		= "dewdd.fixthings.setandreload";

	public Random			rnd					= new Random();

	String					fileName			= "config_fixThings.txt";

	public DigEventListener2() {
		delay abc = new delay();
		abc.start();
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {

		String m[] = e.getMessage().split("\\s+");
		Player p = e.getPlayer();

		if (m[0].equalsIgnoreCase("/fixthings")) {
			p.sendMessage(
					"current fixValuePerMinutes=" + this.fixvalueIn1Minute);

			if (p.hasPermission(this.psetandreload) == false) {
				p.sendMessage("you need permission " + this.psetandreload);
				return;
			}

			if (m.length == 1) {
				p.sendMessage("/fixthings reload");
				p.sendMessage("/fixthings set <fixValuePerMinutes>");
				p.sendMessage("/fixthings info");
				this.reloadpl();
				return;
			}
			if (m.length == 2) {
				if (m[1].equalsIgnoreCase("reload")) {
					p.sendMessage("/fixthings reload");
					this.reloadpl();
					return;
				}
				if (m[1].equalsIgnoreCase("info")) {
					p.sendMessage("dewdd fixthings version "
							+ DigEventListener2.version);
					p.sendMessage("programer : ptdeWDewDD");
					p.sendMessage("e-mail : dewtx29@gmail.com");

					this.reloadpl();
					return;
				}

				if (m[1].equalsIgnoreCase("set")) {
					p.sendMessage(
							"/fixthings set <enter fixValuePerMinutes here>");
					// reloadpl();
					return;
				}
			}
			if (m.length == 3) if (m[1].equalsIgnoreCase("set")) {

				this.fixvalueIn1Minute = Integer.parseInt(m[2]);

				p.sendMessage(
						"fixthings set value to " + this.fixvalueIn1Minute);
				this.writeFixRateFile(this.fixvalueIn1Minute);

				this.reloadpl();

				return;
			}

		}
	}

	public void loadFixRateFile() {

		String filena = this.folder_name + File.separator + this.fileName;
		File fff = new File(filena);
		if (fff.exists() == false) // create staff list
			this.writeFixRateFile(this.fixvalueIn1Minute);

		try {

			fff.createNewFile();

			System.out.println("ptdeWDewDD ft : loading : " + filena);
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
				// Print the content on the console
				// m = strLine.split("\\s+");
				m = strLine.split("=");

				this.fixvalueIn1Minute = Integer.parseInt(m[1]);

				System.out.println(
						"ptdeWDewDD ft : loaded 'fixValuePerMinutes'  '"
								+ this.fixvalueIn1Minute + "'");
				break;
			}

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err
					.println("ptdeWDewDD ft : error load fixThings.txt files! "
							+ filena + " " + e.getMessage());
		}
	}

	public void reloadpl() {

		this.loadFixRateFile();

		Bukkit.getScheduler().cancelTasks(this.ac);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.ac,
				new eachMinutes(), 1, 1200);
	}

	public void writeFixRateFile(int value) {
		String filena = this.folder_name + File.separator + this.fileName;
		File fff = new File(filena);

		File fff2 = new File(this.folder_name);
		fff2.mkdir();
		// create staff list
		try {
			System.out.println("ptdewdewdd ft :loading " + this.fileName
					+ " : empty!@#!@# ");

			FileWriter fwriter = new FileWriter(fff);
			fwriter.write("fixValuePerMinute=" + this.fixvalueIn1Minute
					+ System.getProperty("line.separator"));
			fwriter.close();
			System.out.println("ptdeWDewDD ft : generated " + this.fileName);

		}
		catch (IOException e) {
			System.out.println(
					"ptdeWDewDD ft : generate file error ! " + e.getMessage());
			e.printStackTrace();
		}

	}

}