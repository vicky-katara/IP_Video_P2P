package requester;

import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;

import clientSide.Peer;
import commonLibrary.Chunk;
import commonLibrary.Packet;
import commonLibrary.SenderReceiver;
import commonLibrary.Video;

public class ChunkFetcher extends Thread{
	
	Socket socketToRequestReceiver;
	Video video;
	int startNum;
	int offset;
	PriorityBlockingQueue<Chunk> chunkQueue;
	
	ChunkFetcher(Video video, int startNum, int offset, Peer requestReceiver, PriorityBlockingQueue<Chunk> chunkQueue){
		System.out.println("Starting new ChunkFetcher thread for chunks "+startNum+"+ "+offset+"*k to fetch "+video+ " from "+requestReceiver);
		this.video = video;
		this.startNum = startNum;
		this.offset = offset;
		this.socketToRequestReceiver = new SenderReceiver().returnSocketTo(requestReceiver.getIpAddress(), requestReceiver.getPortNumber());
		this.chunkQueue = chunkQueue;
	}
	
	public void run(){
		for(int chunkNumber=startNum; chunkNumber<video.getNumChunks(); chunkNumber+=offset){
			new SenderReceiver().sendMesssageOn(socketToRequestReceiver, new Packet(100, video.getVideoTitle()+":"+chunkNumber).getPayload());
			Packet option101Packet = new Packet(new SenderReceiver().receiveMessageOn(socketToRequestReceiver));
			if(option101Packet.getOption()!=101)
				try {
					throw new Exception("option101Packet has option "+option101Packet.getOption()+" . Something went wrong!: ChunkFetcher.run()");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			chunkQueue.add(new Chunk(chunkNumber, option101Packet.getData()));
			while(chunkQueue.size() > 50000)
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void resumeIfWaiting(){
		notify();
	}
	
}
