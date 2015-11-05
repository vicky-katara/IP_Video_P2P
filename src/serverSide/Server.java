package serverSide;
import java.net.*;
import java.io.*;

public class Server extends Thread  {
	public static Socket connectionSocket;
	
		
		   Server(Socket connectionSocket) {
		      this.connectionSocket = connectionSocket;
		   }
				
		public void run() {
	      try {
	         PrintStream pstream = new PrintStream
	         (connectionSocket.getOutputStream());
	         for (int i = 100; i >= 0; i--) {
	            pstream.println(i + 
	            " bottles of beer on the wall");
	         }
	         pstream.close();
	         connectionSocket.close();
	      }
	      catch (IOException e) {
	         System.out.println(e);
	      }
	   }
	public static void main(String[] args) throws Exception {
		InetAddress inetAddress = InetAddress.getByName("192.168.1.1");
		ServerSocket welcomeSocket = new ServerSocket(65423, 65535, inetAddress );
		
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Connected");
			new Server(connectionSocket).start();
			
		}
		
		
	}

}
