package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class PenDownCommand extends Command {

    private ModelMap modelMap;

    public PenDownCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        modelMap.getTurtle().penDown();
        return 1;
    }

}
