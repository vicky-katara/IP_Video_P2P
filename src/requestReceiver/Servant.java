package requestReceiver;

import java.net.Socket;

public class Servant extends Thread{

	Socket fromRequestor;
	Servant(Socket fromRequestor){
		this.fromRequestor = fromRequestor;
	}
	public void run() {
		// get packet from Peer and send appropriate chunks
	}

}
