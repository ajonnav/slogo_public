package view;

import java.util.Map;
import java.util.Observable;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public class VariablesView implements IView{

	private Map<String, String> vars;
	
	public VariablesView(Map<String, String> variables, Group root) {
		this.vars = variables;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof VariablesModel){
			VariablesModel = (VariablesModel) o;
			
		}
		
	}

}
