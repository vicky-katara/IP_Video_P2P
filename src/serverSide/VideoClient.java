package serverSide;

import java.util.ArrayList;

public class VideoClient {
	
	int videoID,clientID;
	ArrayList<VideoClient> videoClientList=new ArrayList<VideoClient>();

	public void addVideoClientPair(int clientID, int videoID) 
	{
		this.clientID=clientID;
		this.videoID=videoID;
		videoClientList.add(this);
	}

}
