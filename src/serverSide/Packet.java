package serverSide;

import java.net.Socket;
import java.util.Arrays;

public class Packet {
	int option;
	String data;
	Packet(String unformatted){
		String[] val_split = unformatted.split("\\|");
		System.out.println(Arrays.toString(val_split));
		
		this.option = Integer.parseInt(val_split[2]);
		String[] port_data = val_split[6].split(":");
		System.out.println(Arrays.toString(port_data));
		
	}
	
	Packet returnOptionAndData(String str)
	{
			Packet p = new Packet(str);
			return p;
	}
	
}
