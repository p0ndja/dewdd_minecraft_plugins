/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddpublicchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	JavaPlugin ac = null;
	Socket clientSocket = null;
	DataOutputStream outToServer = null;

	class reconnectc implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

			try {
				clientSocket = new Socket("localhost", 6790);
				outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				// sentence = inFromUser.readLine();
				outToServer.writeBytes("join" + '\n');
				
				while (true) {
					String readFromServer = inFromServer.readLine();
					for (Player player :Bukkit.getOnlinePlayers()) {
						player.sendMessage(readFromServer);
						
					}
				}

				// System.out.println("FROM SERVER: " + modifiedSentence);
				// clientSocket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void reconnect() {
		reconnectc abc = new reconnectc();
		Thread abx = new Thread(abc);
		abx.start();
	}

	public DigEventListener2() {
		reconnect();

	}

	public String getServerName() {
		String serName = tr.gettr("CONFIG_Public_chat_Server_name");
		return serName;
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		try {

			Player p = event.getPlayer();
			String serName = getServerName();

			outToServer.writeBytes(serName + " " + p.getName() + " chat " + event.getMessage() + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reconnect();
		} catch (NullPointerException e) {
			reconnect();
		}
	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		try {

			Player p = event.getPlayer();
			String serName = getServerName();

			outToServer.writeBytes(serName + " " + p.getName() + " join" + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reconnect();
		} catch (NullPointerException e) {
			reconnect();
		}
	}

} // class
