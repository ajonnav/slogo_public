package command;

import java.util.List;
import model.ModelMap;


public class IfCommand extends Command {

    public IfCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 0 ? loopExecute(getCommands().get(1)) : 0;
    }
}
