package dewdd_public_chat_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

class SubListener implements Runnable {
	public Socket getConnectionSocket() {
		return connectionSocket;
	}

	public BufferedReader getInFromClient() {
		return inFromClient;
	}

	public DataOutputStream getOutToClient() {
		return outToClient;
	}

	private Socket connectionSocket = null;
	private BufferedReader inFromClient = null;
	private DataOutputStream outToClient = null;
	private PackedSocket  regis = null;

	public SubListener(Socket connectionSocket, BufferedReader inFromClient, DataOutputStream outToClient) {
		this.connectionSocket = connectionSocket;
		this.inFromClient = inFromClient;
		this.outToClient = outToClient;
		this.regis = new PackedSocketClient(this);

	}

	@Override
	public void run() {

		while (true) {
			String clientSentence;
			try {
				clientSentence = inFromClient.readLine();

				System.out.println("Received: " + clientSentence);

				//String toClient = "";

				//outToClient.writeBytes(toClient);
				regis.sendToClient(clientSentence, this.regis.getid());
				

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}

interface PackedSocket {
	public int getid();

	public void sendToClient(String abc, int yourid);
}

class PackedSocketClient implements PackedSocket {

	private SubListener sub;

	public PackedSocketClient(SubListener sub) {
		this.sub = sub;
		this.curid = PublicChatTCPServer.getNewid();
	}

	private int curid = 0;

	@Override
	public int getid() {
		return this.curid;

	}

	@Override
	public void sendToClient(String abc, int yourid) {
		if (yourid == curid) {
			return;
		}

		try {
			sub.getOutToClient().writeBytes(abc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class PackedSocketHost implements PackedSocket {
	private LinkedList<PackedSocket> list = new LinkedList<PackedSocket>();
	private int curid = 0;

	public PackedSocketHost() {
		this.curid = PublicChatTCPServer.getNewid();
	}

	public void addListener(PackedSocket ab) {
		list.add(ab);
	}

	@Override
	public void sendToClient(String abc, int yourid) {
		for (PackedSocket b : list) {
			if (b == null) {
				continue;
			}
			if (yourid == b.getid()) {
				continue;
			}
			b.sendToClient(abc, yourid);

		}

	}

	@Override
	public int getid() {
		// TODO Auto-generated method stub
		return 0;
	}
}

public class PublicChatTCPServer implements Runnable {
	public static int countid = 0;

	public static int getNewid() {
		countid++;
		return countid;
	}

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

		ServerSocket welcomeSocket;

		try {
			welcomeSocket = new ServerSocket(6790);

			while (true) {

				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));

				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

				SubListener sl = new SubListener(connectionSocket, inFromClient, outToClient);
				Thread abc = new Thread(sl);
				abc.start();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
