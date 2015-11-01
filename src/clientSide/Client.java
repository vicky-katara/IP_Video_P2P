package clientSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client {
	
	//String serverIP;
	//int serverPort;
	private Socket socketToVideoServer;
	private HashMap<Integer, Integer> videoID;
	
	Client(String serverIP, int port){
		//*this.serverIP = serverIP;
		//this.serverPort = port;
		
		socketToVideoServer = returnSocketTo(serverIP, port);// connect to server
		
		// get list of files in user.home/videos
		String pathToVideosDir = System.getProperty("user.home")+"\\Videos";
		ArrayList<File> listOfFiles = ListFiles.returnListOfFilesInDirectory(pathToVideosDir);
		
		//prepare list of video file names
		String filenames = getConcatenatedFileNames(listOfFiles, ';');
		String videoListPayload = preparePayLoad(0, filenames); // FileNames is Option 0:
		
		System.out.println(videoListPayload);
		// send list of videos to server
		sendMesssageOn(socketToVideoServer, videoListPayload);
		interact();
		//closeSocketToServer(socketToVideoServer);
	}
	
	Socket returnSocketTo(String serverIP, int serverPort){
		try{
		return new Socket(serverIP, serverPort);
		}
		catch(IOException e){
			System.err.println("Error:");
			e.printStackTrace();
			return null;
		}
	}
	
	void closeSocketToServer(Socket s){
		try{
			s.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	String getConcatenatedFileNames(ArrayList<File> listOfFiles, char c){
		StringBuffer ret = new StringBuffer();
		for(int i=0;i<listOfFiles.size();i++){
			ret.append(listOfFiles.get(i).getName()).append(c);
		}
		return ret.toString();
	}
	
	String preparePayLoad(int option, String data){
		return "|option|"+option+"|/option||data|"+data+"|data|";
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
			System.out.println(e.getStackTrace());
			if(e.getMessage().contains("sendMessageOn"))
				System.exit(11); // exit 11 --> client to video Server abnormally disconnected
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
				System.exit(12); // exit 11 --> client to video Server abnormally disconnected
			return "No reply received";
		}
	}
	
	void interact(){
		Scanner scan = new Scanner(System.in);
		while(true){
			printMainMenu();
			int input = scan.nextInt();
			switch(input){
			case 0: System.out.println("Thank you for watching! Paying for movies is too mainstream! :P");
				return;
			case 1: String getVideoListPayload = preparePayLoad(1, "");
				sendMesssageOn(socketToVideoServer, getVideoListPayload);
				String videoList = receiveMessageOn(socketToVideoServer);
				printVideoList(videoList);
				askUserForVideo();
				break;
			case 2:
			}
		}
	}
	
	void printMainMenu(){
		System.out.println("---------------------------------------------------------------\n-----------------------------Menu------------------------------");
		System.out.println("\t1. View List of Movies available with the swarm");
		System.out.println("\t2. View List of Local Movies");
		System.out.println("\t0. Exit");
		System.out.println("---------------------------------------------------------------");
		System.out.print("Enter Option: ");
	}
	
	void askUserForVideo(){
		Scanner scan = new Scanner(System.in);
		while(true){
			System.out.println("Enter the Line Number of the Video you wish to watch.\nPress 0 to Exit.\nEnter Option:");
			int choice = scan.nextInt();
			if(choice==0)
				return;
			Integer id = videoID.get(choice);
			if(id==null){
				System.out.println("Please enter a valid Line Number");
				continue;
			}
			sendMesssageOn(socketToVideoServer, preparePayLoad(3, id.toString()));
			String opttion4message = this.receiveMessageOn(socketToVideoServer);
			ArrayList<Peer> peers = getPeersFromOption4Message(opttion4message);
			
			//
			// start_video_while collating packets
			//
			
			
		}
		
	}
	
	ArrayList<Peer> getPeersFromOption4Message(String option4message){
		try{
			if(option4message.contains("4")==false)
				throw new Exception("Message received as Option 4, is not option 4 message. Exiting");
			
			
			//
			
			///
			
			return null;
		}
		catch(Exception e){
			if(e.getMessage().contains("Message received as Option 4"))
				System.exit(0);
			e.printStackTrace();
		}
		return null;	
	}
	
	void printVideoList(String s){ //Also populate the HashMap videos with (index, videoID) format.
		
	}
	
	public static void main(String[] args) {
		//new Client("",0);
		int i = Integer.parseInt(null);
	}
}
