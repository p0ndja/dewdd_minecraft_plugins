package dewdd_public_chat_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


class SubListener implements Runnable {
	private Socket connectionSocket =  null;
	private BufferedReader inFromClient = null;
	
	public SubListener(Socket connectionSocket, BufferedReader inFromClient) {
		this.connectionSocket = connectionSocket;
		this.inFromClient = inFromClient;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}


public class PublicChatTCPServer implements Runnable {
	public static void startListener() {
		PublicChatTCPServer xxx = new PublicChatTCPServer();
		Thread ab = new Thread(xxx);
		ab.start();
	}

	public static void main(String argv[]) throws Exception {

		startListener();
	}

	@Override
	public void run() {

		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket;

		try {
			welcomeSocket = new ServerSocket(6789);

			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				
				while (true) {
					clientSentence = inFromClient.readLine();
					System.out.println("Received: " + clientSentence);
					capitalizedSentence = clientSentence.toUpperCase() + '\n';
					outToClient.writeBytes(capitalizedSentence);

				}
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
