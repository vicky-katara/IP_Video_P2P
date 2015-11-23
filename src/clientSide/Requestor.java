package clientSide;
import java.util.ArrayList;

import commonLibrary.Video;

public class Requestor {
	
	Video requestedVideo;
	ArrayList<Peer> listOfPeers;

	Requestor(Video requestedVideo, ArrayList<Peer> listOfPeers){
		this.requestedVideo = requestedVideo;
		this.listOfPeers = listOfPeers;
	}
	
	public static void main(String args[]){
		
	}
	
}
