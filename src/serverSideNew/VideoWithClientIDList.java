package serverSideNew;

import java.util.ArrayList;

public class VideoWithClientIDList {
	ArrayList<Integer> clientIDList;
	Video v;
	VideoWithClientIDList(String videoTitle, int numChunks){
		v = new Video(videoTitle, numChunks);
	}
	VideoWithClientIDList(int id, String videoTitle, int numChunks, int clientID){
		//v = new Video(id, videoTitle, numChunks);
	}
}
