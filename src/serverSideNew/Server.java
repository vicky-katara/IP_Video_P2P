package serverSideNew;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import clientSide.Packet;

public class Server implements Runnable {

	Socket csocket;
	static ClientVideoDatabase database = new ClientVideoDatabase();
	Server(Socket csocket) {
		this.csocket = csocket;
		System.out.println("New thread "+Thread.currentThread().getId()+" started to service "+csocket.getInetAddress()+":"+csocket.getPort());
	}
	
	public void run() {
		while(true){
			Packet receivedPacket = new Packet(receiveMessageOn(csocket));
			System.out.println("Option "+receivedPacket.getOption()+" - "+receivedPacket.getData()+" received!");
			switch(receivedPacket.getOption()){
				case 0:
					System.out.println("From: "+csocket.getInetAddress()+":");
					String[] breakUp = receivedPacket.getData().split(":");
					try{
						if(breakUp.length!=2)
							throw new Exception("Packet 0 not in expected format:"+receivedPacket);
						Server.database.storeAllVideosOfClient(new Client(csocket.getInetAddress()+":"+breakUp[0]), breakUp[1]);
						
						// no reply sent
						break;
					}catch(Exception e){System.out.println("Exception raised in class Server: "); e.printStackTrace();}
				case 1:
					Packet toBeSent2 = new Packet(2, Server.database.returnAllVideos());
					System.out.println("Sending Option 2 reply"+toBeSent2);
					sendMesssageOn(csocket, toBeSent2.getPayload());
					break;
				case 3:
					int videoID = Integer.parseInt(receivedPacket.getData());
					Packet toBeSent4 = new Packet(4, Server.database.fetchListOfClientsHavingVideo(videoID));
					System.out.println("Sending Option 4 reply"+toBeSent4);
					sendMesssageOn(csocket, toBeSent4.getPayload());
			}
		}
	}
	
	String receiveMessageOn(Socket socket){
		try{
			if(socket.isClosed())
				throw new Exception("receiveMessageOn:"+socket.toString()+" is closed. Cannot continue");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			return dis.readUTF();
		}
		catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().contains("receiveMessageOn"))
				System.exit(12); // exit 12 --> client to video Server abnormally disconnected
			return "No reply received";
		}
	}
	
	void sendMesssageOn(Socket socket, String payload){
		try{
			if(socket.isClosed())
				throw new Exception("sendMesssageOn:"+socket.toString()+" is closed. Cannot continue");

			System.out.println("Trying to send |"+payload.substring(0, 5)+"...| to "+socket.getInetAddress()+":"+socket.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(payload);
		}
		catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().contains("sendMessageOn"))
				System.exit(11); // exit 11 --> client to video Server abnormally disconnected
		}
	}
	
	public static void main(String args[]) 
	   throws Exception {
		int serverPort = 65423; // changed by Vicky
		ServerSocket ssock = new ServerSocket(serverPort); // changed by Vicky
		Socket connectionSocket;
		
		//Get IP Address of 'enp0s8'
		String ipAddress = NetworkAddress.getIPAddress("enp0s8");
		// Upload IPAddress:Port number at http://www4.ncsu.edu/~vpkatara/server.txt
		new UploadToServer().upload(ipAddress, serverPort);
		
		System.out.println("Server Started!! at "+ipAddress+":"+serverPort);
		
		while (true) {
			Socket sock = ssock.accept();
			new Thread(new Server(sock)).start();
		}
	}
	
}
