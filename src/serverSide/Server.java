package serverSide;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Server implements Runnable{
		public Socket client;
		private static int countID;
		public static List<String> table = new ArrayList<>();
				
		public Server(Socket peer){
			client = peer;	
			}

				
		public void run() {
					// TODO Auto-generated method stub
		 try {
				 System.out.println("Inside run");
				 System.out.println("\n\nClient connected to Registration Server");
							
				 BufferedReader clientData = new BufferedReader(new InputStreamReader(client.getInputStream()));
				 PrintStream pw = new PrintStream(client.getOutputStream());
				 String Data = clientData.readLine();
				 Packet OpPacket = new Packet(Data);
				 try { 
					 	  if (OpPacket.option == 0) 
					  {
						  Video V = new Video(OpPacket.data);
					  }
						  else if (OpPacket.option == 1)
						  {
							  VideoMenu obj = new VideoMenu(); 
							  pw.println(obj.returnMenu()); // sends videomenu through the socket to client
							  
						  }
						  else if (OpPacket.option == 3)
						  {
							  // extract the videoID from the option and check for the peers which have the video and send the list of peers which have that video
							  data_splitting(OpPacket);
						  }
						  else {
							  throw new Exception();
						  }
					 
					 
				 	}
					 catch(Exception E){
						 System.out.println("Invalid Option");
					 }
					
					 
				 
				 
				 InetAddress address = client.getInetAddress();	// get ip address of client(i.e address to which the socket is connected)
				 System.out.println("IP address of client : " + address.toString()); // to test 
				 int port = client.getPort();  // GET Port number to which socket is connected for test purposes only
				 System.out.println("Port address on which client is connected :" + port);
				 countID++;
				 
					 }
				catch(Exception e)
					 {
						 // yet to insert 
						System.out.println("Error while connecting to Server");
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
