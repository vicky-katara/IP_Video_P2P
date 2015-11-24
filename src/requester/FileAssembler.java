package requester;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.PriorityBlockingQueue;

import commonLibrary.Chunk;
import commonLibrary.Video;

public class FileAssembler extends Thread{

	private boolean chunkQueueHasNewElements;
	private boolean done[];
	private Video video;
	private int nextChunkToBeAssembled;
	PriorityBlockingQueue<Chunk> chunkQueue;
	private String outputFileName;
	
	FileAssembler(Video video, PriorityBlockingQueue<Chunk> chunkQueue){
		this.video = video;
		this.chunkQueue = chunkQueue;
		done = new boolean[video.getNumChunks()];
		this.nextChunkToBeAssembled = 0;
		outputFileName = video.hashCode()+"-"+System.currentTimeMillis()+".mp4";
	}
	
/*	private boolean chunkQueueHasNewElements(){
		return this.chunkQueueHasNewElements;
	}*/
	
	public void run(){
		while(this.nextChunkToBeAssembled < this.video.getNumChunks()){
			try{
				while(chunkQueue.isEmpty())
					wait();
				while(chunkQueue.peek().getChunkID() != this.nextChunkToBeAssembled)
					wait();
				assemble(chunkQueue.poll());
				if(assemblyComplete())
					System.out.println("Exit or whatever: FileAssembler.run()...");
			}catch(InterruptedException ie){ie.printStackTrace();}
		}
	}
	
	private synchronized void assemble(Chunk toBeAppended){
		try {
			if(done[toBeAppended.getChunkID()] == true)
				throw new Exception("Chunk already written in file");
			RandomAccessFile raf = new RandomAccessFile(outputFileName, "rw");
			raf.seek(toBeAppended.getChunkID()*256);
			raf.write(toBeAppended.returnBytes());
			done[toBeAppended.getChunkID()] = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean assemblyComplete(){
		if(++nextChunkToBeAssembled>=video.getNumChunks())
			return true;
		return false;
	}
	
	public void handleNewChunks(){
		this.chunkQueueHasNewElements = true;
		notify();
	}
	
	
}
