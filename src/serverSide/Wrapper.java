package serverSide;

import java.io.BufferedReader;
import java.util.Arrays;

public class Wrapper {
	
	public static void data_splitting(String clientData)
	{
		String str = clientData;
		String[] val_split = str.split("\\|");
		System.out.println(Arrays.toString(val_split));
		System.out.println("length:"+val_split.length);
		String option = val_split[2];
		String[] port_data = val_split[6].split(":");
		System.out.println(Arrays.toString(port_data));
		String port = port_data[0];
		String[] videos = port_data[1].split(";");
		System.out.println(Arrays.toString(videos));
		
	}

}
