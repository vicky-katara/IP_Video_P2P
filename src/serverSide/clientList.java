package serverSide;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class clientList {
	
	private static int countID;
	int clientID;
	InetAddress clientIPAddress;
	int clientPort;
	static ArrayList<clientList> clientList=new ArrayList<clientList>();
	
	public clientList()
	{
		//default constructor
	}
	
	public clientList(Socket client)
	{
		this.clientIPAddress = client.getInetAddress();	// get ip address of client(i.e address to which the socket is connected) 
		this.clientPort = client.getPort();  // GET Port number to which socket is connected for test purposes only
		countID++;
		this.clientID = countID;
		clientList.add(this);
	}
	
	public ArrayList<clientList> getclientList()
	{
		return clientList;
	}
	
	public int getClientID()
	{
		return countID;
	}
	
	

}
