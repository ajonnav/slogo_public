package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;

import addons.Features;
import constants.UIConstants;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.VariableModel;

public class VariableView implements IView{

	private Map<String, Double> vars;
	private VBox paneBox;
	private VBox varBox;
	private TextArea myTA;
	private ResourceBundle myBundle;
	private ResourceBundle myResources;
	
	public VariableView(VBox vbMain, VBox vbVar, TextArea ta, String language) {
		vars = new HashMap<String, Double>();
		paneBox = vbMain;
		varBox = vbVar;
		myTA = ta;
		String myLang = language;
		myBundle = ResourceBundle.getBundle(UIConstants.RSRC_LANG + myLang);
		myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.SCREEN_LANG);
				
		vbMain.getChildren().add(Features.makeText(myResources.getString("Var")));
		vbMain.getChildren().add(varBox);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof VariableModel){
			VariableModel vm = (VariableModel) o;
			vars = vm.getImmutableVariableMap();
			varBox.getChildren().clear();
			for(String item : vars.keySet()){
				String temp = item + " = " + vars.get(item);
				String editable = getMakeCall(item);
				Hyperlink pastVar = new Hyperlink(temp);
		        pastVar.setOnAction(event -> {
		            myTA.appendText(editable);
		        });
				varBox.getChildren().add(pastVar);
			}
		}
	}
	
	private String getMakeCall(String variable){
		String maker = myBundle.getString("MakeVariable");
		maker = maker.split("\\|")[0];
		return maker + " " + variable + " ";
	}
}
