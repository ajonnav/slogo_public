package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class IsShowingCommand extends Command {

    private boolean status;

    public IsShowingCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        status = modelMap.getTurtle().getShowStatus();
    }

    @Override
    public double execute () {
        return status ? 1 : 0;
    }

}
