package serverSideNew;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import clientSide.Packet;

public class Server implements Runnable {

	Socket csocket;
	Server(Socket csocket) {
		this.csocket = csocket;
		System.out.println("New thread "+Thread.currentThread().getId()+" started to service "+csocket.getInetAddress()+":"+csocket.getPort());
	}
	
	public void run() {
		while(true){
			Packet receivedPacket = new Packet(receiveMessageOn(csocket));
			switch(receivedPacket.getOption()){
				case 0:
					
					break;
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
