package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.VariableModel;

public class VariableView implements IView{

	private Map<String, Double> vars;
	public VBox varBox;
	
	public VariableView(VBox vb) {
		vars = new HashMap<String, Double>();
		varBox = vb;
		
	//	tester();
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
			
			for(String item : vars.keySet()){
				varBox.getChildren().add(new Text(item + " = " + vars.get(item)));
			}
		}
		
	}

}
