package dewdd_joinsound_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

class runSound implements Runnable {
	@Override
	public void run() {

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
}

class SubListener implements Runnable {
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;

	public SubListener(Socket connectionSocket, BufferedReader inFromClient, DataOutputStream outToClient) {
		this.connectionSocket = connectionSocket;
		this.inFromClient = inFromClient;
		this.outToClient = outToClient;

	}

	public void runSound() {
		runSound xx = new runSound();
		Thread yy = new Thread(xx);
		yy.start();
	}

	@Override
	public void run() {

		String clientSentence;
		String capitalizedSentence;

		while (true) {

			try {
				clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
				runSound();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	}
}

class StartListener implements Runnable {
	private ServerSocket welcomeSocket;

	public void startServer() {
		System.out.println("startServer");
		try {
			welcomeSocket = new ServerSocket(6789);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		if (welcomeSocket == null) {
			startServer();
		}

		try {

			while (true) {

				Socket connectionSocket;
				BufferedReader inFromClient;
				DataOutputStream outToClient;

				connectionSocket = welcomeSocket.accept();
				inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				
				SubListener sl  = new SubListener(connectionSocket, inFromClient, outToClient);
				Thread abx = new Thread(sl);
				abx.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			startServer();
		}

	}
}

public class TCPServer {

	public static void main(String argv[]) throws Exception {

		StartListener sl = new StartListener();
		Thread th = new Thread(sl);
		th.start();

	}
}