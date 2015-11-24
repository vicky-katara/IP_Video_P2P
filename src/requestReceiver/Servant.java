package requestReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

import commonLibrary.Chunk;
import commonLibrary.Packet;
import commonLibrary.SenderReceiver;

public class Servant extends Thread{

	Socket socketFromRequestor;
	String pathToVideosDir;
	Servant(Socket fromRequestor){
		this.socketFromRequestor = fromRequestor;
		pathToVideosDir = System.getProperty("user.home")+File.separatorChar+"Videos";
	}
	public void run() {
		Packet option100Packet = null;
		while(true){
			try {
				option100Packet = new Packet(new SenderReceiver().receiveMessageOn(socketFromRequestor));
				if(option100Packet.getOption()!=100)
					throw new Exception("option100Packet has option "+option100Packet.getOption()+" : Servant.run()");
				String[] splitted = option100Packet.getData().split(":");
				Chunk chunkToBeReturned = getRequestedChunk(splitted[0], Integer.parseInt(splitted[1]));
				new SenderReceiver().sendMesssageOn(socketFromRequestor, new Packet(101, option100Packet.getData()+":"+chunkToBeReturned).getPayload());
			}
			catch(NullPointerException npe){
				System.err.println("Problem with option100Packet: "+option100Packet);npe.printStackTrace();
				currentThread().stop();
			}
			catch (Exception e) {e.printStackTrace();
			}
			
		}
	}
	
	Chunk getRequestedChunk(String fileName, int chunkNumber){
		try {
			byte[] b = new byte[256];
			RandomAccessFile raf = new RandomAccessFile(pathToVideosDir+File.separatorChar+fileName, "r");// doesn't have to be done every time!
			//System.out.println(raf.length());
			if(raf.length()<=chunkNumber*256)
				throw new Exception("Required chunk number "+chunkNumber+" does not exist in "+fileName);
			raf.seek(chunkNumber*256);
			raf.read(b);
			//System.out.println(Arrays.toString(b).replace("[", "").replace("]", "").replace(", ", newChar));
			raf.close();
			return new Chunk(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.home")+File.separatorChar+"Videos");
	}
}
