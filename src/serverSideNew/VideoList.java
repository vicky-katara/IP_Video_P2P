package serverSideNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class VideoList {

	private int count;
	private HashMap<Integer, Video> videoList;
	VideoList(){
		videoList = new HashMap<Integer, Video>();
		count = 0;
	}
	
	int addAndReturnID(Video v){
		if(videoList.containsValue(v)){
			for (Entry<Integer, Video> entry : videoList.entrySet()) // horrible efficiency
	            if (entry.getValue().equals(v))
	                return entry.getKey();
			return Integer.MIN_VALUE;
		}
		else{
			Video newVideo = new Video(++count, v.videoTitle, v.numChunks);
			videoList.put(newVideo.videoID, newVideo);
			return newVideo.videoID;
		}
	}
	
	ArrayList<Integer> addAllAndReturnAllID(String unformattedList){
		ArrayList<Integer> list = new ArrayList<Integer>();
		String[] unformattedArray = unformattedList.split(";");
		for(int i=0; i<unformattedArray.length;i++)
			list.add(addAndReturnID(new Video(unformattedArray[i])));
		return list;
	}
	
	String get(){
		return videoList.values().toString().replace("[","").replace("]", "").replace(", ", ";");
	}
	
	public static void main(String[] args) {
		VideoList v = new VideoList();
//		System.out.println(v.addAndReturnID(new Video("men in black:5")));
//		System.out.println(v.get());
//		System.out.println(v.addAndReturnID(new Video("avatar:7")));
//		System.out.println("should return some value:"+v.addAndReturnInteger(new Video("men in black:5")));
//		System.out.println(v.get());
		System.out.println(v.addAllAndReturnAllID("men in black:4;avatar:6;men in black2:3"));
		System.out.println(v.addAllAndReturnAllID("men in black:2;avatar:6;men in black2:3"));
	}
	
	
	
}
