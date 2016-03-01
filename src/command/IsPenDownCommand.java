package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class IsPenDownCommand extends Command {

    private boolean status;

    public IsPenDownCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        status = modelMap.getTurtle().getPenStatus();
    }
    
    @Override
    public double execute () {
        return status ? 1 : 0;
    }

}
