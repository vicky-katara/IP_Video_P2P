package serverSide;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Server implements Runnable {
	public Socket client;
	public static List<String> table = new ArrayList<>();

	public Server(Socket peer) {
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
				 Packet OpPacket = new Packet();
				 OpPacket.returnOptionAndData(Data);
				 clientList clientObject = new clientList(client);
				 
				 try { 
					 	  if (OpPacket.option == 0) 
					  {
					 	  int clientID = clientObject.getClientID();
						  Video V = new Video(OpPacket.data,clientID);
					  }
						  else if (OpPacket.option == 1)
						  {
							  // sends videomenu through the socket to client
							  
						  }
						  else if (OpPacket.option == 4)
						  {
							  // extract the videoID from the option and check for the peers which have the video and send the list of peers which have that video
							  //data_splitting(OpPacket);
							  int videoID = Integer.parseInt(OpPacket.data);
							  VideoClient videoObject = new VideoClient();
							  String peers = videoObject.getPeers(videoID);
							  
						  }
						
					 	  else{
							  throw new Exception();
						  }
					 
					 
				 	}
					 catch(Exception E){
						 System.out.println("Invalid Option");
					 }
				 
					 }
				catch(Exception e)
					 {
						 // yet to insert 
						System.out.println("Error while connecting to Server");
					 }
		} 



	public static void main(String[] args) throws IOException {

		ServerSocket regser = new ServerSocket(65423);
		Socket connectionSocket;

		System.out.println("Server Started!!");

		while (true) {
			connectionSocket = regser.accept(); // listening to a new client
			Server s = new Server(connectionSocket);
			Thread t = new Thread(s);
			t.start();
		}
	}
}
