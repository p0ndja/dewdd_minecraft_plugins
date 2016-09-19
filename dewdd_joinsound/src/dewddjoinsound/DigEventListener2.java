/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddjoinsound;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


class Reconnect_c implements Runnable {
	Socket clientSocket = null;
	DataOutputStream outToServer = null;
	String sentence = "...";

	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		try {
			clientSocket = new Socket("localhost", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// sentence = inFromUser.readLine();
			outToServer.writeBytes("connected" + '\n');

			// System.out.println("FROM SERVER: " + modifiedSentence);
			// clientSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class DigEventListener2 implements Listener {
	JavaPlugin ac = null;
	Reconnect_c abc = null;


	public void Reconnect() {
		 abc = new Reconnect_c();
		Thread abx = new Thread(abc);
		abx.start();
	}

	public DigEventListener2() {
		Reconnect();

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		try {
			abc.outToServer.writeBytes(event.getPlayer().getName() + " joining \n");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reconnect();
		} catch (NullPointerException e) {
			e.printStackTrace();
			Reconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reconnect();
		}
	}

} // class
