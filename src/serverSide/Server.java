package serverSide;


import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Server implements Runnable{
		public Socket client;
		
		public static List<String> table = new ArrayList<>();
				
		public Server(Socket peer){
			client = peer;	
			}

				
		public void run() {
					// TODO Auto-generated method stub
		 try {
				 System.out.println("inside run");
				 System.out.println("\n\nClient connected to Registration Server");
							
				 BufferedReader br1= new BufferedReader(new InputStreamReader(client.getInputStream()));
				 PrintStream pw = new PrintStream(client.getOutputStream());
				 InetAddress address = client.getInetAddress();	// get ip address of client(i.e address to which the socket is connected)
				 System.out.println("IP address of client : " + address.toString()); // to test 
				 int port = client.getPort();
				 System.out.println("Port  address on which client is connected :" + port);
					 }
				catch(Exception e)
					 {
						 // yet to insert 
					 }
					
				}
				
			

	public static void main(String[] args)throws IOException {
						
					
					
		ServerSocket regser = new ServerSocket(65423);
		Socket connectionSocket;
					
		System.out.println("Server Started!!");
					
		while(true)
		{
			connectionSocket=regser.accept(); //listening to a new client
			Server s =new Server(connectionSocket);
			Thread t = new Thread(s);
			t.start();
		}
	}
}
