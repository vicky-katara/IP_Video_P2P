package commonLibrary;

import java.util.Arrays;

public class Chunk implements Comparable<Chunk>{
	int chunkID;
	String byteString;
	
	public Chunk(byte[] b){
		this.byteString = Arrays.toString(b);
	}
	Chunk(String byteString){
		this.byteString = byteString;
	}
	public Chunk(int chunkID, String byteString){
		this.chunkID = chunkID;
		this.byteString = byteString;
	}
	
	public int getChunkID(){
		return this.chunkID;
	}
	
	public String toString(){
		return (byteString);//.replace(oldChar, newChar);
	}
	
	public byte[] returnBytes(){
		String[] stringByte = byteString.replace("[", "").replace("]", "").split(", ");
		byte[] byteArr= new byte[stringByte.length];
		for(int index=0;index < byteArr.length; index++)
			byteArr[index] = Byte.parseByte(stringByte[index]);
		return byteArr;
	}
	@Override
	public int compareTo(Chunk o) {
		return this.chunkID - o.chunkID;
	}
}
