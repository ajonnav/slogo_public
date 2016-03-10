package preferences;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import model.ModelMap;

public class PrefWriter {
	
	private String path;
	private ModelMap state;
	private saveState save;
	
	public PrefWriter(ModelMap modelMap, String fileName, String myLang){
		path = "src/preferences/" + fileName + ".srl";
		state = modelMap;
		save = new saveState();
		save.colorMap = state.getDisplay().getColorMap();
		save.backColorIndex = (int) state.getDisplay().getBackgroundColorIndex();
		save.images = state.getDisplay().getImageMap();
		save.language = myLang;
		save.imageFile = fileName;
		save.turtleNumber = modelMap.getDisplay().getNumTurtles();
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
