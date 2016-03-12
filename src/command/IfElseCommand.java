package command;

import java.util.List;
import model.IModelMap;


public class IfElseCommand extends Command {

    public IfElseCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(3);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 0 ? loopExecute(getCommands().get(1))
                                                          : loopExecute(getCommands().get(2));
    }
}
