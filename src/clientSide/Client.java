package clientSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		//socketToVideoServer = returnSocketTo(serverIP, port);// connect to server
		
		// get list of files in user.home/videos
		String pathToVideosDir = System.getProperty("user.home")+"\\Videos";
		ArrayList<File> listOfFiles = ListFiles.returnListOfFilesInDirectory(pathToVideosDir);
		
		//prepare list of video file names
		String fileInfo = getConcatenatedFileInfo(listOfFiles);
		String videoListPayload = new Packet(0, fileInfo).getPayload();//preparePayLoad(0, fileInfo); // FileNames is Option 0:
		
		System.out.println(videoListPayload);
		// send list of videos to server
		//sendMesssageOn(socketToVideoServer, videoListPayload);
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
	
	String getConcatenatedFileInfo(ArrayList<File> listOfFiles){
		StringBuffer ret = new StringBuffer();
		for(int i=0;i<listOfFiles.size();i++){
			ret.append(listOfFiles.get(i).getName()).append(":").append(listOfFiles.get(i).length()/(256*1024)).append(';');
		}
		return ret.toString();
	}
	
//	String preparePayLoad(int option, String data){
//		return "|option|"+option+"|/option||data|"+data+"|data|";
//	}
	
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
				System.exit(12); // exit 12 --> client to video Server abnormally disconnected
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
			case 1: //String getVideoListPayload = preparePayLoad(1, "");
				String getVideoListPayload = new Packet(1, "").getPayload();
				sendMesssageOn(socketToVideoServer, getVideoListPayload);
				Packet videosPacket = new Packet(receiveMessageOn(socketToVideoServer));
				printVideoList(videosPacket);
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
			//sendMesssageOn(socketToVideoServer, preparePayLoad(3, id.toString()));
			sendMesssageOn(socketToVideoServer, new Packet(3, id.toString()).getPayload());
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
	
	
	
	void  printVideoList(Packet videosPacket){ //Also populate the HashMap videos with (index, videoID) format.
		try{
			if(videosPacket.option!=2)
				throw new Exception("Message received as Option 2, is not option 2 message. Exiting");
			String videoData = videosPacket.data;
			
			String[] list=videoData.split(";");
			//System.out.println(Arrays.toString(list));
		   
			System.out.println(".............Video Details................");
			System.out.println("Option\t\t Video title\t\t No. of chunks \t      Format");
			for (int i = 0; i < list.length; i++)
			{ 
				//System.out.println(list[i]);
				String[] videoDetails= list[i].split(",");
				//System.out.println(Arrays.toString(videoDetails));
				StringBuffer s = new StringBuffer(videoDetails[1]);
				s.append("                    ");
				String title = s.substring(0, 20);
			   System.out.println(videoDetails[0]+"\t\t"+title+"\t\t"+videoDetails[2]+"\t\t"+videoDetails[3]);
					
	
				
				
			}
		
		
			
		}
		catch(Exception e){
			if(e.getMessage().contains("Message received as Option 2"))
				System.exit(0);
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
//new Client("",0);
		//int i = Integer.parseInt(null);
		
		//Packet p = new Packet("|option|2|/option||data|1,Intro to Algos,8,mp3;2,Intro to networks,5,mp4;3,Intro to AI,6,mp4|data|");
		//System.out.println(p);
		//printVideoList(p);
	}
}
