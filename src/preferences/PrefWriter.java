package preferences;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import model.ModelMap;

public class PrefWriter {
	
	private String path;
	private ModelMap state;
	private saveState save;
	
	public PrefWriter(ModelMap modelMap, String fileName, String myLang){
		path = "src/preferences/" + fileName + ".srl";
		state = modelMap;
		save = new saveState();
		save.setColorMap(state.getDisplay().getColorMap());
		save.setBackColorIndex((int) state.getDisplay().getBackgroundColorIndex());
		save.setImages(state.getDisplay().getImageMap());
		save.setLanguage(myLang);
		save.setImageFile(fileName);
		save.setTurtleNumber(modelMap.getDisplay().getNumTurtles());
		save.setBackColor(state.getDisplay().getBackgroundColorIndex());
		save.setHistory((state.getHistory().getImmutableHistoryList()));
		save.setVariables(state.getVariable().getImmutableVariableMap());
		//save.setTurtleModel(state.getTurtleModel());
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
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
}
