package serverSide;

import java.util.ArrayList;

public class VideoClient {
	
	int videoID,clientID;
	ArrayList<VideoClient> videoClientList=new ArrayList<VideoClient>();

	public VideoClient()
	{//default constructor
	}
	
	public void addVideoClientPair(int clientID, int videoID) 
	{
		this.clientID=clientID;
		this.videoID=videoID;
		videoClientList.add(this);
	}

	public String getPeers(int vID) {
		String peers=" ";
		for(int i=0;i<videoClientList.size();i++)
		{
			VideoClient entry = new VideoClient();
			entry = videoClientList.get(i);
			if(entry.videoID==vID)
			{
				peers=peers+entry.clientID+","+entry.videoID+",";
			} 
		}
		return peers;
	}
}
