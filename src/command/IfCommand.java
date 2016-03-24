package command;

import java.util.List;
import model.IModelMap;


public class IfCommand extends Command {

    public IfCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 0 ? loopExecute(getCommands().get(1)) : 0;
    }
}
