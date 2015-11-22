package clientSide;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;


public class vlcMain {

	private static JFileChooser ourFileSelector = new JFileChooser();
	
	public static void main(String[] args)
	{
		String vlcPath="";
		String mediaPath="";
		File ourFile;
		
		ourFileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		ourFileSelector.showSaveDialog(null);
		ourFile=ourFileSelector.getSelectedFile();
		vlcPath=ourFile.getAbsolutePath();
		
		ourFileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ourFileSelector.showSaveDialog(null);
		ourFile=ourFileSelector.getSelectedFile();
		mediaPath=ourFile.getAbsolutePath();
		
		new MediaPlayer(vlcPath,mediaPath).run();
	
	}
	
}