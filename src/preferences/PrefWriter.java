package preferences;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;

public class PrefWriter {
	
	private String path;
	
	public PrefWriter(String fileName){
		path = "src/preferences/" + fileName + ".xml";
	}
	
	public void writeToTxt(){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(path));
			
			//insert methods and set parameters to write to file
			
			writer.close();
		}
		catch (IOException e){
			System.err.println(e);
		}
	}
}
