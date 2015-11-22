package clientSide;

import java.io.File;
import java.util.ArrayList;

public class ListFiles 
{
	static ArrayList<File> returnListOfFilesInDirectory(String path){
		// Creating path for the '\Video' folder inside the User home
		  //String path = System.getProperty("user.home")+"\\Videos";
		  
		  String files;
		  File folder = new File(path);
		  
		  //Error handling to quit if the 'Video' folder doesn't exist in the User home
		  
		  if(folder.exists()==false){
			  System.err.println("The folder "+path+" does not exist");
			  System.exit(0);
		  }
		  
		  ArrayList<File> arr = new ArrayList<File>();
		  File[] listOfFiles = folder.listFiles();
		  
		  for (int i = 0; i < listOfFiles.length; i++){
			  if (listOfFiles[i].isFile() || listOfFiles[i].getName().contains("Desktop")==false){
				   arr.add(listOfFiles[i]);
			  }
		  }
		  return arr;
	}
	public static void main(String[] args) {
		System.out.println(returnListOfFilesInDirectory(System.getProperty("user.home")+File.separatorChar+"Videos"));
	}
}