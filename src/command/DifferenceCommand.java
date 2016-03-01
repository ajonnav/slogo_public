package command;

import java.util.List;
import model.ModelMap;


public class DifferenceCommand extends Command {
    
    public DifferenceCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }
    
    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() - getCommands().get(1).get(0).execute();
    }
}
