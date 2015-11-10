package serverSide;

import java.util.ArrayList;
import java.util.Arrays;

public class Video {

	int videoID, numChunks;
	String videoTitle, format;
	static int videoIDCount = 0;
	static ArrayList<Video> videoList=new ArrayList<Video>();
	
	Video()
	{
		//default constructor
	}
	
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
	
	Video(int videoID, String videoTitle, String format, int numChunks){
		this.videoID = videoID;
		this.videoTitle = videoTitle;
		this.format = format;
		this.numChunks = numChunks;
	}
	
	public static void print()
	{

		//how to print the whole arraylist?
		System.out.println(videoList.toString());
	}
	
	public void Video2(String str,int clientID) 
	{
		// Divide into params and insert into the video list and video client list
		// intro to algos:8;intro to ai:4
		String[] videos = str.split(";");
		for(int vid_num=0;vid_num<videos.length;vid_num++)
		{
			String[] values = videos[vid_num].split(":");
			Video newV = new Video(-1, values[0], "mp4", Integer.parseInt(values[1]));
			if(!(newV.videoList.contains(newV)))
			{
				newV.videoID = videoIDCount++;
				newV.format="mp4";
				newV.videoTitle=values[0];
				newV.numChunks=Integer.valueOf(values[1]);
				videoList.add(newV);
			}
			VideoClient obj = new VideoClient();
			obj.addVideoClientPair(clientID,videoID);
			//how to print the whole arraylist?
			
		}
		print();
		this.videoList.toString();
	}
	
	Video(String str,int clientID) 
	{
		Video2("intro to algos:8;intro to ai:4",3);
	}
	

	public String returnVideolist()
	{	return videoList.toString();
	}
	
	public static void main(String[] args)
	{
		Video v = new Video("intro to algos:8;intro to ai:4",3);
	}

	

}
