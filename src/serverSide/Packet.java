package serverSide;

import java.net.Socket;
import java.util.Arrays;

public class Packet {
	int option;
	String data;

	public void packetSplit(String unformatted) {
		String[] val_split = unformatted.split("\\|");
		this.option = Integer.parseInt(val_split[2]);
		//String[] port_data = val_split[6].split(":");
		this.data = val_split[6];
	}

	Packet returnOptionAndData(String str) {
		Packet p = new Packet();
		p.packetSplit(str);
		return p;
	}
	

}
