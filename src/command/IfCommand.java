package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;


public class IfCommand extends Command {

    public IfCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }
    
    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() 
                != 0 ? loopExecute(getCommands().get(1)) : 0;
    }
}
