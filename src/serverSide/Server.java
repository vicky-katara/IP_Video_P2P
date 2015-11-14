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
						  else if (OpPacket.option == 2)
						  {
							  // sends videomenu through the socket to client
							  Video videoObj = new Video();
							  String videoList;
							  videoList = videoObj.returnVideolist();
							  pw.println(videoList);
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
							  throw new Exception(); //put message here
						  }
					 
					 
				 	}
					 catch(Exception E){
						 System.out.println("Invalid Option");
						 E.printStackTrace();
					 }
				 
					 }
				catch(Exception e)
					 {
						 // yet to insert 
						System.out.println("Error while connecting to Server");
						e.printStackTrace();
					 }
		} 



	public static void main(String[] args) throws IOException {

		int serverPort = 65423; // changed by Vicky
		ServerSocket regser = new ServerSocket(serverPort); // changed by Vicky
		Socket connectionSocket;
		
		/* Making Changes here - Vicky */
		
		InetAddress ipAddr = InetAddress.getLocalHost();
		new UploadToServer().upload(ipAddr.getHostAddress(), serverPort);
		
		/* Awesome stuff over! */
		
		System.out.println("Server Started!! at "+ipAddr.getLocalHost()+":"+serverPort);

		while (true) {
			connectionSocket = regser.accept(); // listening to a new client
			Server s = new Server(connectionSocket);
			Thread t = new Thread(s);
			t.start();
		}
	}
}
