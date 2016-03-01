package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class IfElseCommand extends Command {

    public IfElseCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(3);
    }
    
    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 
                0 ? loopExecute(getCommands().get(1)) : loopExecute(getCommands().get(2));
    }
}
