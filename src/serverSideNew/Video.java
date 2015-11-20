package serverSideNew;

import java.util.ArrayList;

public class Video {
	
	String videoTitle;
	int videoID;
	int numChunks;
	String format;//not in use
	ArrayList<Integer> clientIDList;
	
	Video(String videoTitle, int numChunks){//only comparison
		this.videoID = -1;
		this.videoTitle = videoTitle;
		this.numChunks = numChunks;
		this.format = "mp4";
	}
	
	Video(int id, String videoTitle, int numChunks, int clientID){
		this.videoID = id;
		this.videoTitle = videoTitle;
		this.numChunks = numChunks;
		this.format = "mp4";
		clientIDList = new ArrayList<Integer>();
		clientIDList.add(clientID);
	}
	
	Video(Video v){
		this.videoID = -1;
		this.videoTitle = v.videoTitle;
		this.numChunks = v.numChunks;
	}
	
	Video(String unformatted){ //only comparison
		try{
		String[] splitted = unformatted.split(":");
		this.videoTitle = splitted[0];
		this.numChunks = Integer.parseInt(splitted[1]);
		}
		catch(NumberFormatException nfe){System.err.println("Exception found in Video Class:");nfe.printStackTrace();}
	}
	
	public String toString(){
		return this.videoID+","+this.videoTitle+","+this.numChunks+","+this.format;
	}
	
	public String completeToString(){
		return this.videoID+","+this.videoTitle+","+this.numChunks+","+this.format+"--"+this.clientIDList;
	}
	
	public void addClient(int newClientID){
		try{
			if(this.clientIDList!=null)
				this.clientIDList.add(newClientID);
			else{
				throw new Exception("Comparison object being used improperly");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean equals(Object o){
		try{
			if(o instanceof Video ==false)
				throw new Exception(o+" is not an instance of Video");
			else{
				Video other = (Video)o;
				return (this.videoTitle+","+this.numChunks).equals(other.videoTitle+","+other.numChunks);
			}
		}
		catch(Exception e){e.printStackTrace(); return false;}
	}
	
	public static void main(String[] args) {
		Video v = new Video(1, " sda", 8, 7);
		System.out.println(v.completeToString());
		v.addClient(5);
		System.out.println(v.completeToString());
	}
	
}
