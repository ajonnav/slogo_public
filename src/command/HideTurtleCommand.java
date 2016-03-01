package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class HideTurtleCommand extends Command {

    private ModelMap modelMap;

    public HideTurtleCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        modelMap.getTurtle().hide();
        return 0;
    }
    
}
