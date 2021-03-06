package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import constants.UIConstants;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.IVariableModel;
import model.ViewableVariableModel;

public class VariableView extends ScrollView{

	private Map<String, Double> vars;
	private VBox varBox;
	private TextArea myTA;
	private ResourceBundle myBundle;
	
	public VariableView(TextArea ta, String language) {
		getMyPane().setLayoutX(UIConstants.BORDER_WIDTH);
		getMyPane().setLayoutY(UIConstants.LOWER_PANE_Y);
		getMyPane().setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
		getMyPane().setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
		vars = new HashMap<>();
		varBox = getMyBox();
		myTA = ta;
		String myLang = language;
		myBundle = ResourceBundle.getBundle(UIConstants.RSRC_LANG + myLang);
		setMyName(getResources().getString("UVars"));
		refresh();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ViewableVariableModel){
			ViewableVariableModel vm = (ViewableVariableModel) o;
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
