package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class BackwardCommand extends Command {

    private ModelMap modelMap;

    public BackwardCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double dist = getCommands().get(0).get(0).execute();
        modelMap.getTurtle().forward(-dist);
        return dist;
    }
}
