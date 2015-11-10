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
		clientList clientObj = new clientList();
		ArrayList<clientList> clientList=new ArrayList<clientList>();
		clientList = clientObj.getclientList();
		for(int i=0;i<videoClientList.size();i++)
		{
			VideoClient entry = new VideoClient();
			entry = videoClientList.get(i);
			if(entry.videoID==vID)
			{
				for(int j=0;j<clientList.size();j++)
				{
					clientObj = clientList.get(j);
					if(entry.clientID==clientObj.clientID)
					{
						peers=peers+clientObj.clientIPAddress+":"+clientObj.clientPort+";";
					}
				}
			} 
		}
		return peers;
	}
}
