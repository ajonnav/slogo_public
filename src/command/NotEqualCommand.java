package command;

import java.util.List;
import model.IModelMap;


public class NotEqualCommand extends Command {

    public NotEqualCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != getCommands().get(1).get(0).execute() ? 1 : 0;
    }
}
