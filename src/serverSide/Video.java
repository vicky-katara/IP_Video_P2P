package serverSide;

import java.util.ArrayList;
import java.util.Arrays;

public class Video {

	int videoID, numChunks;
	String videoTitle, format;
	static int videoIDCount = 0;
	static ArrayList<Video> videoList=new ArrayList<Video>();

	public String toString()
	{
		return videoID+","+videoTitle+","+numChunks+","+format;
	}

	public boolean equals(Object o){
		if(o instanceof Video == false)
			return false;
		Video other = (Video) o;
		return other.videoID == this.videoID;
	}
	
	public static void print()
	{

		//how to print the whole arraylist?
		System.out.println(videoList.toString());
	}
	
	Video(int videoID, String videoTitle, String format, int numChunks){
		this.videoID = videoID;
		this.videoTitle = videoTitle;
		this.format = format;
		this.numChunks = numChunks;
	}
	
	Video(String str,int clientID) {
		// intro to algos:8;intro to ai:4
		int i, j;
		String[] val;
		ArrayList<String> name = new ArrayList<String>();
		String[] val_split = str.split(";");
		int start=0;
		int length = val_split.length;
		val = new String[length*2];
		for (i = 0; i < length;i++) 
		{
				String[]arr = val_split[i].split(":");
				val[start++] = arr[0];
				val[start++] = arr[1];
				if(!videoList.contains(arr[0]))
				{
					this.videoID = videoIDCount++;
					this.format="mp4";
					this.videoTitle=arr[0];
					this.numChunks=Integer.valueOf(arr[1]);
					videoList.add(this);
				}
				VideoClient obj = new VideoClient();
				obj.addVideoClientPair(clientID,videoID);
				//how to print the whole arraylist?
				print();
				
		}
	}
	
	void Video2(String str,int clientID) {
		// Divide into params and insert into the video list and video client list
		// intro to algos:8;intro to ai:4
		String[] videos = str.split(";");
		for(int vid_num=0;vid_num<videos.length;vid_num++){
			String[] values = videos[vid_num].split(":");
			Video newV = new Video(-1, values[0], "mp4", Integer.parseInt(values[1]));
			if(this.videoList.contains(newV)){
				
			}
			else{
				
			}
		}
		this.videoList.toString();
	}
	
	public static void main(String[] args)
	{
		Video v = new Video("intro to algos:8;intro to ai:4",3);
	}
}
