package command;

import java.util.List;
import model.ModelMap;


public class IfElseCommand extends Command {

    public IfElseCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(3);
    }
    
    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 
                0 ? loopExecute(getCommands().get(1)) : loopExecute(getCommands().get(2));
    }
}
