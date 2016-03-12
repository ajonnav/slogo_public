package command;

import java.util.List;
import model.IModelMap;


public class IfCommand extends Command {

    public IfCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 0 ? loopExecute(getCommands().get(1)) : 0;
    }
}
