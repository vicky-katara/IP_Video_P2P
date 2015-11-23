package requestor;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import clientSide.Peer;
import commonLibrary.Chunk;
import commonLibrary.Video;

public class Requestor {
	
	Video requestedVideo;
	ArrayList<Peer> listOfPeers;
	PriorityBlockingQueue<Chunk> chunkQueue;
	
	public Requestor(Video requestedVideo, ArrayList<Peer> listOfPeers){
		this.requestedVideo = requestedVideo;
		this.listOfPeers = listOfPeers;
		this.chunkQueue = new PriorityBlockingQueue<Chunk>();
	}
	
	void dispatch(){
		for(int chunkFetcherNumber=0; chunkFetcherNumber<listOfPeers.size(); chunkFetcherNumber++)
			new ChunkFetcher(requestedVideo, chunkFetcherNumber, listOfPeers.size(), listOfPeers.get(chunkFetcherNumber), chunkQueue);
	}
	
	public static void main(String args[]){
		
	}
	
}
