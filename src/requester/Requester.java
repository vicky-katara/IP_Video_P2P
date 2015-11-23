package requester;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import clientSide.Peer;
import commonLibrary.Chunk;
import commonLibrary.Video;

public class Requester {
	
	Video requestedVideo;
	ArrayList<Peer> listOfPeers;
	PriorityBlockingQueue<Chunk> chunkQueue;
	ChunkFetcher[] chunkFetcherArr;
	
	public Requester(Video requestedVideo, ArrayList<Peer> listOfPeers){
		this.requestedVideo = requestedVideo;
		this.listOfPeers = listOfPeers;
		this.chunkQueue = new PriorityBlockingQueue<Chunk>();
		dispatch();
		new FileAssembler(requestedVideo, chunkQueue).start();
	}
	
	void dispatch(){
		for(int chunkFetcherNumber=0; chunkFetcherNumber<Math.min(listOfPeers.size(),requestedVideo.getNumChunks()); chunkFetcherNumber++){
			chunkFetcherArr[chunkFetcherNumber] = new ChunkFetcher(requestedVideo, chunkFetcherNumber, listOfPeers.size(), listOfPeers.get(chunkFetcherNumber), chunkQueue);
			chunkFetcherArr[chunkFetcherNumber].start();
		}
	}
	
	void resumeAnyWaitingChunkFetcher(){
		for(int chunkFetcherNumber=0; chunkFetcherNumber<listOfPeers.size(); chunkFetcherNumber++){
			chunkFetcherArr[chunkFetcherNumber].resumeIfWaiting();
		}
	}
	
	public static void main(String args[]){
		
	}
	
}
