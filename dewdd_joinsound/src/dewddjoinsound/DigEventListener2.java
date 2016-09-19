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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	JavaPlugin ac = null;
Socket clientSocket = null;
DataOutputStream outToServer = null;
String sentence = "...";


class reconnectc implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub


		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			clientSocket = new Socket("localhost", 6789);
			 outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//sentence = inFromUser.readLine();
			outToServer.writeBytes("just join" + '\n');
			
			
			//System.out.println("FROM SERVER: " + modifiedSentence);
			//clientSocket.close();

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

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		event.getPlayer();

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		try {
			outToServer.writeBytes(sentence + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reconnect();
		}
		catch (NullPointerException e) {
			reconnect();
		}
	}

} // class
