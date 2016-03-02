package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import command.Command;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.CommandsModel;
import model.VariableModel;

public class CommandsView implements IView{

	private Map<String, List<Command>> vars;
	public VBox varBox;
	
	public CommandsView(VBox vb) {
		vars = new HashMap<String, List<Command>>();
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
		if(o instanceof CommandsModel){
			CommandsModel vm = (CommandsModel) o;
			vars = vm.getImmutableCommandsMap();
			varBox.getChildren().clear();
			for(String item : vars.keySet()){
				varBox.getChildren().add(new Text(item + " = " + vars.get(item)));
			}
		}
		
	}

}
