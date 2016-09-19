/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddjoinsound;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	JavaPlugin	ac				= null;
	boolean		joinsoundallow	= false;
	boolean		callsoundallow	= false;
	String		callreason		= "";

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd joinsound");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd joinsound") == true) {
			player.sendMessage("�����Թ joinsound �繻����Թ����ǡѺ���§  ����ͼ���������Կ  ����Դ�Կ�������� mp3 �͡�ӹǹ����Կ");
			player.sendMessage("��������þ������� ptdew ���� dewdd ���� admin �к��������� mp3 ���¡�ʹ�Թ dew ��");
			player.sendMessage("���ҧ�á�� �ʹ�Թ dew ����ö�Դ�к�������¡����������˵ؼ�  , ��� �Դ�к�������Կ�������§�����");

			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("admin") == true
				|| event.getMessage().equalsIgnoreCase("ptdew") == true
				|| event.getMessage().equalsIgnoreCase("dewdd") == true) {
			runsoundfileallow();
			if (callsoundallow == false) {
				for (Player ad : Bukkit.getOnlinePlayers()) {
					ad.sendMessage("ptdew&dewdd: Admin dew don't allow someone to call him now...");
					ad.sendMessage("ptdew&dewdd: �ʹ�Թ��ǻԴ�к�������¡ ���Ǥ��� ������˵ؼ���� ...");
					ad.sendMessage(callreason);
				}
				return;
			}
			runsoundfile(Integer.toString(0));
			for (Player ad : Bukkit.getOnlinePlayers()) {
				ad.sendMessage("ptdew&dewdd: We are calling my lord 'dewdd'");
				ad.sendMessage("ptdew&dewdd: '"
						+ event.getPlayer().getName()
						+ "'��ҡ��ѧ���¡�ʹ�Թ 'dewdd' ���� ��������Թ�����ҧ�������... (���¡����ͨ��繨�ԧ��������ẹ)");
			}
		}

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		runsoundfileallow();
		if (joinsoundallow == false) {
			return;
		}
		runsoundfile(Integer.toString(Bukkit.getOnlinePlayers().length));

		dprint.r.printA("ptdew&dewdd: 'Dewdd JoinSound' : " +  tr.gettr("joinsound_if_you_need_to_call_admin_type") + " admin," + tr.gettr("admin_in_thai") + ",ptdew");
		
	}

	public void runsoundfile(String abcwrite) {
		try {

			String configfilename = "plugins\\dewddjoinsound\\dewddjoinsound.txt";

			File theDir = new File("plugins\\dewddjoinsound");

			// if the directory does not exist, create it
			if (!theDir.exists()) {

				boolean result = theDir.mkdir();
				if (result == true) {
					System.out.println("DIR Autosave dewddjoinsound created");
				}

			}

			// Open the file that is the first
			// command line parameter
			File inFile = new File(configfilename);
			FileWriter out;

			out = new FileWriter(inFile);

			out.write(abcwrite + System.getProperty("line.separator"));

			out.close();
			@SuppressWarnings("unused")
			/*
			 * Process process = new
			 * ProcessBuilder("C:\\Game\\Minecraft_Server-25566\\plugins" +
			 * "\\minecraft_joinsound.exe").start();
			 */
			Process process = new ProcessBuilder("plugins"
					+ "\\minecraft_joinsound.exe").start();
			/* do something */

		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error: join sound runsoundfile"
					+ e.getMessage());
		}
	}

	public void runsoundfileallow() {

		try {

			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					"d:\\dewddjoinsoundallow.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			strLine = br.readLine();
			joinsoundallow = Boolean.parseBoolean(strLine);

			strLine = br.readLine();
			callsoundallow = Boolean.parseBoolean(strLine);

			callreason = "";
			while ((strLine = br.readLine()) != null) {
				callreason = callreason + (strLine);
			}

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.out.println("error : join sound read fileallow "
					+ e.getMessage());

		}
	}

} // class

