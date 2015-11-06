package serverSide;

import java.util.ArrayList;
import java.util.Arrays;

public class Video {

	int videoID, numChunks;
	String videoTitle, format;
	static int videoIDCount = 0;

	Video(String str) {
		// intro to algos:8;intro to ai:4
		int i, j;
		String[] val;
		this.videoID = videoIDCount++;
		String[] val_split = str.split(";");
		int start=0;
		int length = val_split.length;
		val = new String[length*2];
		for (i = 0; i < length;i++) {
				String[]arr = val_split[i].split(":");
				val[start++] = arr[0];
				val[start++] = arr[1];
		}
		for(String str1 : val){
			System.out.println(str1);
		}
	}


}
