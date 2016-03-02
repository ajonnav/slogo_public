package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.Group;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.VariableModel;

public class VariableView implements IView{

	private Map<String, Double> vars;
	private VBox varBox;
	private TextArea myTA;
	
	public VariableView(VBox vb, TextArea ta) {
		vars = new HashMap<String, Double>();
		varBox = vb;
		myTA = ta;
	}
	/*
	public void tester(){
		vars.put("a", 50.0);
		for(String item : vars.keySet()){
			varBox.getChildren().add(new Text(item + " = " + vars.get(item)));
		}
	}
	*/
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof VariableModel){
			VariableModel vm = (VariableModel) o;
			vars = vm.getImmutableVariableMap();
			varBox.getChildren().clear();
			for(String item : vars.keySet()){
				String temp = item + " = " + vars.get(item);
				//String make = 
				String editable = "make " + item + " ";
				Hyperlink pastVar = new Hyperlink(temp);
		        pastVar.setOnAction(event -> {
		            myTA.appendText(editable);
		        });
				varBox.getChildren().add(pastVar);
			}
		}
	}

}
