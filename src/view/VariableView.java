package view;

import java.util.Map;
import java.util.Observable;
import javafx.scene.Group;
import model.VariableModel;

public class VariableView implements IView{

	private Map<String, Double> vars;
	
	public VariableView(Map<String, Double> variables, Group root) {
		this.vars = variables;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof VariableModel){
			VariableModel vm = (VariableModel) o;
			vars = vm.getMap();
			
		}
		
	}

}
