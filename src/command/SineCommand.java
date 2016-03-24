package command;

import java.util.List;
import model.IModelMap;


public class SineCommand extends Command {

    public SineCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.sin(Math.toRadians(getCommands().get(0).get(0).execute()));
    }

}
