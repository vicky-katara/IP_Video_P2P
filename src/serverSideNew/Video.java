package serverSideNew;

public class Video {
	
	String videoTitle;
	int videoID;
	int numChunks;
	String format;//not in use
	
	Video(String videoTitle, int numChunks){
		this.videoID = -1;
		this.videoTitle = videoTitle;
		this.numChunks = numChunks;
		this.format = "mp4";
	}
	
	Video(int count, String videoTitle, int numChunks){
		this.videoID = count;
		this.videoTitle = videoTitle;
		this.numChunks = numChunks;
		this.format = "mp4";
	}
	
	Video(Video v){
		this.videoID = -1;
		this.videoTitle = v.videoTitle;
		this.numChunks = v.numChunks;
	}
	
	Video(String unformatted){
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
		Video v = new Video(" sda", 8);
		Video v2 = new Video(" sda", 8);
		System.out.println(v);
	}
	
}
