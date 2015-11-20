package serverSideNew;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	Socket csocket;
	Server(Socket csocket) {
		this.csocket = csocket;
		System.out.println("New thread "+Thread.currentThread().getId()+" started to service "+csocket.getInetAddress()+":"+csocket.getPort());
	}
	
	public void run() {
		try {
	         PrintStream pstream = new PrintStream
	         (csocket.getOutputStream());
	         for (int i = 100; i >= 0; i--) {
	            pstream.println(i + 
	            " bottles of beer on the wall");
	         }
	         pstream.close();
	         csocket.close();
	      }
	      catch (IOException e) {
	         System.out.println(e);
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
