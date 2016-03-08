package preferences;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;

import display.WorkSpace;
import model.ModelMap;

public class PrefWriter {
	
	private String path;
	private ModelMap state;
	private saveState save;
	private Map<Double, String> images;
	
	public PrefWriter(ModelMap modelMap, String fileName, String myLang){
		path = "src/preferences/" + fileName + ".srl";
		state = modelMap;
		
		save = new saveState();
		save.colorMap = state.getColorMap();
		save.backColorIndex = (int) state.getDisplay().getBackgroundColorIndex();
		save.images = modelMap.getImageMap();
		save.language = myLang;
		//save.imageFile = 
		save.turtleNumber = modelMap.getNumTurtles();
		//save.loadFile = null;
	}
	
	public void writeToSrl(){
	      try
	      {
	         FileOutputStream fileOut = new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(save);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in" + path);
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
}
