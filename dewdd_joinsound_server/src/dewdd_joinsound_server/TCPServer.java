package dewdd_joinsound_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class TCPServer {
	public static void runSound() {

		String bip = File.separator + "mi" + File.separator + "lobby" + File.separator + "plugins" + File.separator
				+ "dewdd_joinsound" + File.separator + "join.mp3";

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(bip);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Player playMP3;
		try {
			playMP3 = new javazoom.jl.player.Player(fis);
			playMP3.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public static void main(String argv[]) throws Exception {
		
		/*runSound();
		if (1+1 == 2) {
			return;
		}*/
		
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			while (true) {
			clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
			runSound();
			}
		}
	}
}
